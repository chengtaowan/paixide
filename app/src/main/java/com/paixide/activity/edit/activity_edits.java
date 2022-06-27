package com.paixide.activity.edit;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paixide.getHandler.Webrowse;
import com.tencent.qcloud.tim.tuikit.live.BuildConfig;
import com.paixide.BasActivity.BasActivity2;
import com.paixide.Util.StatusBarUtil;
import com.paixide.widget.itembackTopbr;
import com.tencent.opensource.model.Mesresult;
import com.paixide.Module.api.reguserinfo;
import com.paixide.activity.sesemys.activity_sesemys;
import com.paixide.getHandler.PostModule;
import com.paixide.R;
import com.paixide.Util.Toashow;
import com.paixide.Util.config;
import com.paixide.listener.Paymnets;
import com.paixide.dialog.Dialog_Loading;
import com.tencent.opensource.model.UserInfo;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 修改微信电话QQ号
 */
public class activity_edits extends BasActivity2 {
    private static final String TAG = activity_edits.class.getSimpleName();
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.itemback)
    itembackTopbr itemback;
    @BindView(R.id.tv_name)
    EditText tv_name;
    @BindView(R.id.wx)
    EditText wx;
    @BindView(R.id.qq)
    EditText qq;
    @BindView(R.id.senbnt)
    TextView senbnt;

    @Override
    protected int getview() {
        StatusBarUtil.setStatusBar(this, getResources().getColor(R.color.transparent));
        return R.layout.activity_edits;
    }

    @Override
    public void iniview() {
        userInfo = UserInfo.getInstance();
        itemback.settitle(getString(R.string.ev_msg262));
        itemback.setHaidtopBackgroundColor(true);
    }

    @Override
    public void initData() {
        getjson();
    }

    @OnClick({R.id.senbnt})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.senbnt:
                postlogin();
                break;
        }

    }

    @Override
    public void OnEorr() {

    }

    /**
     * 初始化用户基本信息
     */
    private void getjson() {
        if (!config.isNetworkAvailable()) {
            showuserinfo();
            Toashow.show(getString(R.string.isNetworkAvailable));
            return;
        }
       datamodule.getmember(new Paymnets() {
           @Override
           public void onSuccess() {
               showuserinfo();
           }

           @Override
           public void onFail() {
               showuserinfo();
           }
       });
    }

    /**
     * 提交修改
     */
    public void postlogin() {
        String name = tv_name.getText().toString().trim();
        String wxs = wx.getText().toString().trim();
        String qqs = qq.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toashow.show(this, "昵称不能空");
            return;
        }
        if (TextUtils.isEmpty(wxs)) {
            Toashow.show(this, "微信不能空");

            return;
        }
        if (TextUtils.isEmpty(qqs)) {

            Toashow.show(this, "QQ不能空");
            return;
        }
        dialogshow(getString(R.string.update_a1));
        datamodule.activityedits(name, wxs, qqs, new Paymnets() {
            @Override
            public void success(String msg) {
                dismisshide(msg, 1);
            }

            @Override
            public void isNetworkAvailable() {
                dialogLoadings();
            }

            @Override
            public void onFail(String msg) {
                dismisshide(msg, 0);

            }
        });

    }

    private void dismisshide(String msg, int result) {
        tv_name.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    dialogLoadings();
                    Toashow.show(context, msg);
                    if (result == 1) {
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    private void showuserinfo() {
        username.setText(userInfo.getPhone());
        tv_name.setText(userInfo.getName());
        qq.setText(userInfo.getQq());
        wx.setText(userInfo.getWx());
        if (!TextUtils.isEmpty(userInfo.getWx()) && !TextUtils.isEmpty(userInfo.getQq())) {
            senbnt.setEnabled(false);
            senbnt.setText("不可编辑");
            tv_name.setEnabled(false);
            qq.setEnabled(false);
            wx.setEnabled(false);
        }
        if (config.isNetworkAvailable()) {
            //交给腾讯IM修改个人资料
            activity_sesemys.updateProfile(userInfo);
        }
    }



}

