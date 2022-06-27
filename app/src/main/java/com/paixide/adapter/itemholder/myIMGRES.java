package com.paixide.adapter.itemholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paixide.BasActivity.BaseHolder;
import com.paixide.R;
import com.paixide.Util.glide.ImageLoadHelper;
import com.paixide.app.DemoApplication;
import com.paixide.listener.Callback;

import butterknife.BindView;

public class myIMGRES extends BaseHolder {
    @BindView(R.id.image)
    ImageView image;

    public myIMGRES(Context content, ViewGroup parent, boolean b) {
        super(LayoutInflater.from(content).inflate(R.layout.item_img_pic,null));
    }

    @Override
    public void bind(Object object, int position, Callback callback) {
        String pic = (String) object;
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 180);
        //image.setLayoutParams(params);
        DemoApplication.presignedURL(pic);
        ImageLoadHelper.glideShowCornerImageWithUrl(DemoApplication.instance(), pic, image);
    }

    @Override
    public void bind(Context context, Object object, int position, Callback callback) {

    }

    @Override
    public void OnClick(View v) {

    }
}
