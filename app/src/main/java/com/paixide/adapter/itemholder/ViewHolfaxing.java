package com.paixide.adapter.itemholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.paixide.BasActivity.BaseHolder;
import com.paixide.R;
import com.paixide.listener.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolfaxing extends BaseHolder {
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.msgtext)
    TextView msgtext;
    @BindView(R.id.title)
    TextView title;
    public ViewHolfaxing(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.item_view_img_fragmentfaxing, null));
    }

    public void bind(Object object, int position) {
        title.setText(object.toString());
        if (paymnets != null) {
            itemView.setOnClickListener(v -> paymnets.status(position));
        }

    }

    @Override
    public void bind(Object object, int position, Callback callback) {

    }

    @Override
    public void bind(Context context, Object object, int position, Callback callback) {

    }

    @Override
    public void OnClick(View v) {

    }
}
