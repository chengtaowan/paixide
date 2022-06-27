package com.paixide.adapter.itemholder;

import android.content.Context;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paixide.BasActivity.BaseHolder;
import com.paixide.R;
import com.paixide.Util.Glideload;
import com.paixide.Util.Imagecompressiontool;
import com.paixide.listener.Callback;
import com.paixide.utils.MySocket;
import com.tencent.opensource.model.Socket.data;
import com.tencent.opensource.model.member;

import butterknife.BindView;

public class item_caht extends BaseHolder {
    private static final String TAG = item_caht.class.getSimpleName();
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;

    public item_caht(Context content, ViewGroup parent) {
        super(LayoutInflater.from(content).inflate(R.layout.item_chat_list, parent, false));
    }

    public item_caht(Context content, ViewGroup parent, int type) {
        super(LayoutInflater.from(content).inflate(R.layout.item_chat_02, parent, false));
    }

    public item_caht(Context content, ViewGroup parent, int type, boolean b) {
        super(LayoutInflater.from(content).inflate(R.layout.item_chat_03, parent, false));
    }

    public item_caht(Context content, ViewGroup parent, String type) {
        super(LayoutInflater.from(content).inflate(R.layout.item_chat_04, parent, false));
    }

    @Override
    public void bind(Object object, int position, Callback callback) {
        data data = (data) object;
        if (!TextUtils.isEmpty(data.getAvatar())) {
            Glideload.loadImage(icon, data.getAvatar());
        } else {
            Glideload.loadImage(icon, data.getSex() == 1 ? R.mipmap.boy_off : R.mipmap.girl_off);
        }
        title.setText(data.getName());
        content.setText(data.getMessage());
        if (callback != null) {
            icon.setOnClickListener(v -> callback.OndeleteListener(position));
            itemView.setOnClickListener(v -> callback.OnClickListener(position));
            itemView.setOnLongClickListener(v -> {
                callback.LongClickListener(position);
                return false;
            });
        }
    }

    @Override
    public void bind(Context context, Object object, int position, Callback callback) {

    }

    @Override
    public void OnClick(View v) {

    }


}
