package com.lwd.qjtv.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.SaveUserInfo;
import com.lwd.qjtv.app.utils.ScreenTools;
import com.lwd.qjtv.mvp.ui.message.ChatMessage;
import com.lwd.qjtv.mvp.ui.message.IChatMessage;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.UserInfo;


/**
 * 聊天信息界面
 * Created by admin on 2016/9/29.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter {

    //最大缓存消息数量
    private final static int MAX_MSG_COUNT = 500;
    //系统消息
    private final static int MSG_SYSTEM = 0x0303;
    //登陆消息
    private final static int MSG_LOGIN = 0x0305;
    //文本消息
    private final static int MSG_TEXT = 0x0304;

    private final LinkedList<IChatMessage> mList;
    private final LayoutInflater mInflater;
    private String textColor;//内容文本颜色
    private String superUserTextColor;//超管内容文本颜色
    private int userNameColor;//用户名颜色
    private OnItemClickListener mOnItemClickListener;
    private ClickUserName clickUserName;
    private int selfNameColor;

    public ChatMessageAdapter(Context context) {
        this.textColor = "#000000";
        this.superUserTextColor = "#015734";
        this.mList = new LinkedList<>();
        this.userNameColor = context.getResources().getColor(R.color.color666666);
        this.mInflater = LayoutInflater.from(context);
        this.selfNameColor = context.getResources().getColor(R.color.colorOrigin);
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


    public void setOnClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnClickNameCallback(ClickUserName listener) {
        this.clickUserName = listener;
    }

    private void notifyItemInserted() {
        if (mList.size() > 0) {
            notifyItemInserted(mList.size() - 1);
            notifyDataSetChanged();
        }
    }

    public void addSystemMsgItem(final String string) {
        checkItemCount();
        mList.add(new IChatMessage() {
            @Override
            public int getType() {
                return MSG_SYSTEM;
            }

            @Override
            public String getContent() {
                return string;
            }
        });
        notifyItemInserted();
    }

    public void addTextMsgItem(UserInfo userInfo, final TextContent message) {
        checkItemCount();
        ChatMessage message1 = new ChatMessage();
        message1.setMsgType(MSG_TEXT);
        if (userInfo != null) {
            //获取携带的信息（用户等级）
//            String extra = message.getExtra();
//            message1.setUserLevel(extra);
//            JSONObject jsonObject;
//            String level = null;
//            if (!TextUtils.isEmpty(extra)) {
//                try {
//                    jsonObject = new JSONObject(extra);
//                    if (jsonObject.has("level")) {
//                        level = jsonObject.getString("level");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (!TextUtils.isEmpty(level)) {
//                message1.setUserLevel(level);
//            } else {
            message1.setUserLevel("1");
//            }
//            message1.setUserLevel(extra);
            message1.setUserId(userInfo.getUserID());
            message1.setUserName(userInfo.getNickname());
            message1.setUserAvatar(userInfo.getAvatar());
        } else {
            Random random = new Random();
            message1.setUserName("游客" + random.nextInt(10000));
            message1.setUserLevel("1");
        }
        message1.setContentText(message.getText());
        mList.add(message1);
        notifyItemInserted();
    }


    public void addLoginMsgItem(final String string) {
        checkItemCount();
        mList.clear();
        mList.add(new IChatMessage() {
            @Override
            public int getType() {
                return MSG_LOGIN;
            }

            @Override
            public String getContent() {
                return string;
            }
        });
        notifyItemInserted();
    }

    //检查数组大小，如果大于上限则删除
    private synchronized void checkItemCount() {
        if (mList.size() >= MAX_MSG_COUNT) {
            for (int i = 0; i < 50; i++) {
                mList.removeFirst();
            }
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(IChatMessage e);
    }

    public interface ClickUserName {
        void clickName(String name, long uid);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = mInflater;
        View view;
        switch (viewType) {
            case MSG_SYSTEM://系统消息
                view = inflater.inflate(R.layout.item_msg_system, parent, false);
                return new HolderSystem(view);
            case MSG_LOGIN://登陆消息
                view = inflater.inflate(R.layout.item_msg_login, parent, false);
                return new HolderLogin(view);
            case MSG_TEXT://用户文本消息
                view = inflater.inflate(R.layout.item_msg_text, parent, false);
                return new UserTextHolder(view);
            default:
                view = inflater.inflate(R.layout.item_msg_system, parent, false);
                return new HolderSystem(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((AbsHolder) holder).onBindViewHolder(mList.get(position));
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener((v) ->
                    mOnItemClickListener.onItemClick(mList.get(holder.getAdapterPosition())));
        }
        if (holder instanceof UserTextHolder) {
            ((UserTextHolder) holder).content.setOnClickListener(view -> {
                IChatMessage content = mList.get(position);
                ChatMessage msg = (ChatMessage) content;
                clickUserName.clickName(msg.getUserName(), msg.getUserId());
            });
        } else if (holder instanceof HolderLogin) {
            ((HolderLogin) holder).textView.setText(mList.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    abstract class AbsHolder extends RecyclerView.ViewHolder {

        AbsHolder(View itemView) {
            super(itemView);
        }

        abstract void onBindViewHolder(IChatMessage message);
    }

    private class UserTextHolder extends AbsHolder {
        private final TextView content;

        UserTextHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.item_msg_user_text_content);
        }

        @Override
        void onBindViewHolder(IChatMessage message) {
            int size = ScreenTools.dipTopx(content.getContext(), 17);
            ChatMessage msg = (ChatMessage) message;
            String html = null;
            if ("20".equals(msg.getUserLevel())) {
                html = "<img src=\"" + msg.getUserLevel() + "\"/>" +
                        "<font color=\"" + superUserTextColor + "\">&nbsp;" + msg.getUserName() + "：</font><font color=\"" + superUserTextColor + "\">" + msg.getContent() + "</font>";
            } else {
                if (msg.getUserName() != null && msg.getUserName().equals(SaveUserInfo.getUserName())) {
                    html = "<img src=\"" + msg.getUserLevel() + "\"/>" +
                            "<font color=\"" + selfNameColor + "\">&nbsp;" + msg.getUserName() + "：</font><font color=\"" + textColor + "\">" + msg.getContent() + "</font>";
                } else {
                    String levelColor = textColor;
                    int levelUserName = userNameColor;
//                    String userLevel = msg.getUserLevel();
//                    switch (userLevel == null ? "1" : userLevel) {
//                        case "1":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "2":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "3":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "4":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "5":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "6":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "7":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "8":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "9":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                        case "10":
//                            levelColor = "#0000ff";
//                            levelUserName = content.getRootView().getContext().getResources().getColor(R.color.colorOrigin);
//                            break;
//                    }
                    String level = msg.getUserLevel() == null ? 1 + "" : msg.getUserLevel();
                    html = "<img src=\"" + level + "\"/>" +
                            "<font color=\"" + levelUserName + "\">&nbsp;" + msg.getUserName() + "：</font><font color=\"" + levelColor + "\">" + msg.getContent() + "</font>";
                }
            }

            content.setText(Html.fromHtml(html, s -> {
                Drawable drawable = null;
                try {
                    if (Integer.valueOf(s) <= 0) {
                        s = "1";
                    } else if (Integer.valueOf(s) == 20) {
                        s = "20";
                    } else if (Integer.valueOf(s) > 10) {
                        s = "10";
                    }
                    InputStream is = content.getContext().getAssets().open("level_" + s + ".png");
                    drawable = Drawable.createFromStream(is, ""); //获取图片
                } catch (Exception e) {
                    drawable = content.getContext().getResources().getDrawable(R.drawable.ic_launcher);
                }
                drawable.setBounds(0, 0, ScreenTools.dipTopx(content.getContext(), 42), size);
                return drawable;
            }, null));
        }
    }

    private class HolderSystem extends AbsHolder {
        private final TextView textView;

        HolderSystem(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_msg_connect_text);
        }

        @Override
        void onBindViewHolder(IChatMessage message) {
            textView.setText(message.getContent());
        }
    }

    private class HolderLogin extends AbsHolder {
        private final TextView textView;

        HolderLogin(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_login_content);
        }

        @Override
        void onBindViewHolder(IChatMessage message) {
            textView.setText(message.getContent());

        }
    }

}

