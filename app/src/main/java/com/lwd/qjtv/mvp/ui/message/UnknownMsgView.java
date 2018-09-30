package com.lwd.qjtv.mvp.ui.message;

import android.content.Context;
import android.view.LayoutInflater;

import com.lwd.qjtv.R;

import io.rong.imlib.model.MessageContent;

public class UnknownMsgView extends BaseMsgView {

    public UnknownMsgView(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.msg_unknown_view, this);
    }

    @Override
    public void setContent(MessageContent msgContent) {
    }
}
