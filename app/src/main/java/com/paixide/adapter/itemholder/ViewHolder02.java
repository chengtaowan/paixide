package com.paixide.adapter.itemholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.paixide.BasActivity.BaseHolder;
import com.paixide.R;
import com.paixide.Util.glide.ImageLoadHelper;
import com.paixide.adapter.Radapter;
import com.paixide.listener.Callback;
import com.paixide.listener.OnItemChildClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * 多图片显示
 */
public class ViewHolder02 extends BaseHolder {
    @BindView(R.id.image)
    ImageView image;
    public static View view(Context context,ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.item_recityiew_img, parent, false);
    }

    public ViewHolder02(@NonNull View itemView) {
        super(itemView);
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

    public void bind(Context context, List<Object> list, String path, int position, int TYPE, OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
        this.TYPE = TYPE;
        if (TYPE == Radapter.fmessage_recyview2) {
            ViewGroup.LayoutParams params = image.getLayoutParams();
            params.height = 400;
            //image.setLayoutParams(params);
        }
        image.setId(position);
        ImageLoadHelper.glideShowCornerImageWithUrl(context, path, image);
        if (onItemChildClickListener != null) {
            itemView.setOnClickListener(v -> onItemChildClickListener.OnClickListener(list, v, position)); //设置监听回调
        }
    }

}
