/**
 * Description :
 * 开发者 小清新 QQ804031885
 *
 * @author WSoban
 * @date 2021/3/2 0002
 */


package com.paixide.activity.edit;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.paixide.Util.RandomName;
import com.tencent.qcloud.tim.tuikit.live.BuildConfig;
import com.paixide.BasActivity.BasActivity2;
import com.paixide.Util.StatusBarUtil;
import com.tencent.opensource.model.Mesresult;
import com.paixide.widget.itembackTopbr;
import com.paixide.R;
import com.paixide.activity.sesemys.activity_sesemys;
import com.paixide.Util.Constants;
import com.paixide.Util.Toashow;
import com.paixide.Util.config;
import com.paixide.listener.Paymnets;
import com.paixide.getHandler.PostModule;
import com.paixide.widget.myeditview;
import com.tencent.opensource.model.UserInfo;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 修改昵称
 */
public class activity_nickname1 extends BasActivity2 {
    private final String TAG = "activity_nickname1";
    @BindView(R.id.itemback)
    itembackTopbr itemback;
    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.myeditview)
    myeditview myeditview;
    String truename;
    int TYPE;

    @Override
    protected int getview() {
        StatusBarUtil.setStatusBar(this, getResources().getColor(R.color.transparent));
        return R.layout.activity_nickname;
    }

    @Override
    public void iniview() {
        userInfo = UserInfo.getInstance();
        TYPE = getIntent().getIntExtra(Constants.POSITION, -1);
        itemback.settitle(getString(R.string.tv_msg141));
        itemback.setHaidtopBackgroundColor(true);
        itemback.righttext.setText(R.string.tv_msg142);
        itemback.righttext.setVisibility(View.VISIBLE);
        myeditview.setNickname(userInfo.getName());
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.button, R.id.tv3title})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv3title:
                nickname.setText(RandomName.Name()); //随机名称
                break;
            case R.id.button:
                truename = myeditview.getNickname();
                if (TextUtils.isEmpty(truename)) {
                    Toashow.show(getString(R.string.tv_msg116));
                    return;
                }
                if (truename.length() > 32) {
                    Toashow.show(getString(R.string.tv_msg117));
                    return;
                }
                PostModule();
                break;
        }

    }

    @Override
    public void OnEorr() {

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            return;
        }
        // 在 Android Q（10）中，应用在前台的时候才可以获取到剪切板内容。
        // https://www.jianshu.com/p/8f2100cd1cc5

        String shareText = getShareText();
        if (!TextUtils.isEmpty(shareText) && shareText.contains(" https://v.douyin.com/")) {
            // myeditview.nickname.setText(shareText);
        }


    }

    /**
     * 获取剪切版内容
     *
     * @return 剪切版内容
     */
    public String getShareText() {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }


    /**
     * 提交更新
     */
    private void PostModule() {
        if (!config.isNetworkAvailable()) {
            Toashow.show(getResources().getString(R.string.eorrfali));
            return;
        }
        RequestBody requestBody = new FormBody.Builder()
                .add("userid", userInfo.getUserId())
                .add("token", userInfo.getToken())
                .add("type", String.valueOf(TYPE))
                .add("truename", truename).build();
        PostModule.postModule(BuildConfig.HTTP_API + "/toeditmember", requestBody, new Paymnets() {
            @Override
            public void success(String date) {
                try {
                    Mesresult mesresult = new Gson().fromJson(date, Mesresult.class);
                    ToastUtil.toastShortMessage(mesresult.getMsg());
                    if (mesresult.getStatus().equals("1")) {
                        userInfo.setName(truename);
                        Intent intent = new Intent();
                        intent.putExtra(Constants.POSITION, TYPE);
                        setResult(config.setResult, intent);

                        //提交给腾讯IM修改个人资料
                        activity_sesemys.updateProfile(userInfo);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fall(int code) {

            }
        });
    }

    /**
     * 通知消息
     */
    private void Notmessage() {
        if (!config.isNetworkAvailable()) {
            Toashow.show(getResources().getString(R.string.eorrfali));
            return;
        }
        String text = "恭喜您，昵称更新成功啦！";
        if (userInfo.getUserId().equals("2200")) {
            return;
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("userid", userInfo.getUserId())
                .add("token", userInfo.getToken())
                .add("touserid", "2200")
                .add("text", text).build();
        PostModule.postModule(BuildConfig.HTTP_API + "/Notmessage", requestBody, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
