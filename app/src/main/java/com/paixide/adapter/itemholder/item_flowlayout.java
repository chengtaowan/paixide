package com.paixide.adapter.itemholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paixide.BasActivity.BaseHolder;
import com.paixide.R;
import com.paixide.listener.Callback;

import butterknife.BindView;

public class item_flowlayout extends BaseHolder {
    @BindView(R.id.tv_name)
    TextView tv_name;

    public item_flowlayout(Context content, ViewGroup parent) {
        super(LayoutInflater.from(content).inflate(R.layout.item_fowlayout_aa, null));
    }

    @Override
    public void bind(Object object, int position, Callback callback) {
        this.position = position;
        this.callback = callback;
        tv_name.setText(object.toString());
        if (callback != null) {
            itemView.setOnClickListener(this::OnClick);
        }
    }

    @Override
    public void bind(Context context, Object object, int position, Callback callback) {

    }

    @Override
    public void OnClick(View v) {
        callback.OnClickListener(position);

    }
}
