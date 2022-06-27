package com.paixide.activity.register;

import static com.paixide.activity.DouYing.activity_jsonvideo.douyin;
import static com.paixide.activity.DouYing.activity_jsonvideo.immomo;
import static com.paixide.activity.DouYing.activity_jsonvideo.kuaishou;

import androidx.annotation.Nullable;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.paixide.BasActivity.BasActivity2;
import com.paixide.R;
import com.paixide.Util.config;
import com.paixide.activity.DouYing.activity_jsonvideo;
import com.paixide.listener.Paymnets;
import com.paixide.dialog.dialog_sex;
import com.paixide.widget.Backtitle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册性别导航页
 */
public class activity_register extends BasActivity2 {
    @BindView(R.id.backtitle)
    Backtitle backtitle;
    @BindView(R.id.sex1)
    ImageView sex1;
    @BindView(R.id.sex2)
    ImageView sex2;

    @Override
    protected int getview() {
        return R.layout.activity_register;
    }

    @Override
    public void iniview() {
        backtitle.setconter(getString(R.string.tv_reg_msg1));
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.sex1, R.id.sex2, R.id.login})
    public void OnClick(View v) {
        ImageResource();
        switch (v.getId()) {
            case R.id.sex1:
                sex = 1;
                sex1.setImageResource(R.mipmap.ic_man_choose);
                sex1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity_reg.starsetAction(activity, sex);
                    }
                }, 200);
                break;
            case R.id.sex2:
                sex = 2;
                sex2.setImageResource(R.mipmap.icon_woman_choose);
                dialog_sex.dialogsex(context, sex, new Paymnets() {
                    @Override
                    public void onRefresh() {

                    }

                    @Override
                    public void onLoadMore() {
                        activity_reg.starsetAction(activity, sex);
                    }
                });
                break;
            case R.id.login:
                finish();
                break;

        }
    }

    @Override
    public void OnEorr() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == config.sussess && data != null) {
            setResult(resultCode, data);
            finish();
        }

    }

    protected void ImageResource() {
        sex1.setImageResource(R.mipmap.icon_man);
        sex2.setImageResource(R.mipmap.icon_woman);
    }


}