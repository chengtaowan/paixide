/**
 * Description :
 * 开发者 小清新 QQ804031885
 *
 * @author WSoban
 * @date 2021/4/24 0024
 */


package com.paixide.activity.ZYservices;

import android.view.View;
import android.widget.ImageView;

import com.paixide.BasActivity.BasActivity2;
import com.paixide.R;
import com.paixide.Util.Constants;
import com.paixide.Util.Glideload;
import com.paixide.widget.itembackTopbr;

import butterknife.BindView;

/**
 * 图片浏览放大缩小比酌
 */
public class activity_photo_album extends BasActivity2 {
    @BindView(R.id.photo_view)
    ImageView zoomImageview;
    @BindView(R.id.itemback)
    itembackTopbr itemback;

    @Override
    protected int getview() {
        return R.layout.activity_coursetype;
    }

    @Override
    public void iniview() {
        itemback.settitle("相册图片");
        Glideload.loadImage(zoomImageview,getIntent().getStringExtra(Constants.PATHVIDEO),4);
    }

    @Override
    public void initData() {

    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public void OnEorr() {

    }
}
