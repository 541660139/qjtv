package com.lwd.qjtv.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwd.qjtv.R;
import com.lwd.qjtv.app.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/6/19.
 */

public class GiftBoard extends LinearLayout {
    private static final String TAG = "EmojiPanel";
    private static final int ROW = 3;
    private static final int COLUMN = 7;
    private List<Integer> rList = new ArrayList<>();

    private ViewPager viewPager;
    private GiftBoard.Indicator indicator;
    private GiftBoard.EmojiPageAdapter.EmojiGridAdapter adapter;

    private GiftBoard.OnEmojiItemClickListener listener;

    public interface OnEmojiItemClickListener {
        void onClick(int id);
    }

    public GiftBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.input_emoji_board, this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        indicator = new GiftBoard.Indicator((ViewGroup) findViewById(R.id.indicator));

        viewPager.setAdapter(new GiftBoard.EmojiPageAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "pos = " + position);
                indicator.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setList(List<Integer> list) {
        this.rList = list;
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    public void setItemClickListener(GiftBoard.OnEmojiItemClickListener l) {
        listener = l;
    }

    private int getPageSize() {
        return getTotalSize() / (ROW * COLUMN);
    }

    private int getTotalSize() {
        return rList == null ? 0 : rList.size();
    }

    private class EmojiPageAdapter extends PagerAdapter {

        private ArrayList<View> viewContainer = new ArrayList<>();

        public EmojiPageAdapter() {
            int pageSize = getPageSize();
            for (int i = 0; i < pageSize; i++) {
                GridView gridView = (GridView) LayoutInflater.from(getContext()).inflate(R.layout.input_emoji_gridview, null);
                adapter = new GiftBoard.EmojiPageAdapter.EmojiGridAdapter();
//                int start = i * (ROW * COLUMN - 1);
//                int count;
//                if (i < pageSize) {
//                    count = (ROW * COLUMN - 1);
//                } else {
//                    count = EmojiManager.getSize() - start;
//                }
//
//                if (isInEditMode()) {
//                    return;
//                }
//                final List<Integer> list = EmojiManager.getResourceList(start, count);
                adapter.setResList(rList);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (listener != null) {
                            //// TODO: 2017/6/19 传入giftId
                            int giftId = 0;
                            listener.onClick(giftId);
                        }
                    }
                });
                viewContainer.add(gridView);
            }
        }

        @Override
        public int getCount() {
            return viewContainer.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewContainer.get(position));
            return viewContainer.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private class EmojiGridAdapter extends BaseAdapter {
            List<Integer> resList;

            public void setResList(List<Integer> list) {
                resList = list;
            }

            @Override
            public int getCount() {
                return resList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewGroup view;
                if (convertView == null) {
                    view = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.input_emoji_griditem, null);
                    view.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(40)));
                } else {
                    view = (ViewGroup) convertView;
                }
                ImageView image = (ImageView) view.findViewById(R.id.image);
                image.setImageResource(resList.get(position));
                return view;
            }
        }
    }

    private class Indicator {
        private ViewGroup rootView;
        private ArrayList<ImageView> imageList = new ArrayList<>();

        public Indicator(ViewGroup root) {
            rootView = root;
            int pageSize = getPageSize();
            for (int i = 0; i < pageSize; i++) {
                ImageView imageView = new ImageView(getContext());
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int px = CommonUtil.dip2px(4);
                params.setMargins(px, 0, px, 0);
                imageView.setLayoutParams(params);
                if (i == 0) {
                    imageView.setImageResource(R.mipmap.input_emoji_indicator_hover);
                } else {
                    imageView.setImageResource(R.mipmap.input_emoji_indicator);
                }
                imageList.add(imageView);
                rootView.addView(imageView);
            }
        }

        public void setSelected(int position) {
            for (int i = 0; i < imageList.size(); i++) {
                if (i != position) {
                    imageList.get(i).setImageResource(R.mipmap.input_emoji_indicator);
                } else {
                    imageList.get(i).setImageResource(R.mipmap.input_emoji_indicator_hover);
                }
            }
        }
    }
}
