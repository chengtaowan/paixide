package com.paixide.activity.Withdrawal;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paixide.activity.Aboutus.activity_viecode;
import com.paixide.activity.activity_svip;
import com.paixide.adapter.Radapter;
import com.paixide.BasActivity.BasActivity2;
import com.paixide.Util.Toashow;
import com.paixide.Module.api.moneylist;
import com.paixide.alipay.cs_alipay;
import com.paixide.widget.itembackTopbr;
import com.paixide.R;
import com.paixide.Util.config;
import com.paixide.listener.Paymnets;
import com.paixide.wxapi.WXpayObject;
import com.tencent.opensource.model.UserInfo;
import com.tencent.opensource.model.info;
import com.tencent.qcloud.tim.tuikit.live.BuildConfig;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.paixide.activity.activity_svip.unknown;
import static com.paixide.activity.activity_svip.wx;
import static com.paixide.activity.activity_svip.zfb;

/**
 * 我的金币
 */
public class Detailedlist extends BasActivity2 {
    private static final String TAG = Detailedlist.class.getSimpleName();
    @BindView(R.id.itemback)
    itembackTopbr itemback;
    @BindView(R.id.myoney)
    TextView myoney;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.senbnt)
    TextView senbnt;
    @BindView(R.id.lay1)
    LinearLayout lay1;
    @BindView(R.id.lay2)
    LinearLayout lay2;
    @BindView(R.id.lay3)
    LinearLayout lay3;
    @BindView(R.id.l2)
    LinearLayout l2;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.wxpaly)
    TextView wxpaly;
    @BindView(R.id.alipay)
    TextView alipay;
    private moneylist moneylist;
    private cs_alipay csAlipay;

    public static void starsetAction(Context content) {
        content.startActivity(new Intent(content, Detailedlist.class));
    }

    @Override
    protected int getview() {
        return R.layout.dactivitymainlist;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        //获取金币余额
        datamodule.getbalance(paymnets1);
    }

    @Override
    public void iniview() {
        itemback.settitle(getString(R.string.orderlogmyod));
        itemback.setrighttext(getString(R.string.tv_msg150));
        itemback.setOnClickRight(activity_detailed.class);
        radapter = new Radapter(context, list, Radapter.Detailedlist);
        radapter.setPaymnets(paymnets2);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerview.setAdapter(radapter);
        senbnt.setEnabled(false);

        if (userInfo.getZfboff() == 1) {
            lay1.setVisibility(View.GONE);
        }
        if (userInfo.getWxoff() == 1) {
            lay2.setVisibility(View.GONE);
        }
        if (userInfo.getZfboff() == 1 && userInfo.getWxoff() == 1) {
            t2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        //金币充值列表
        datamodule.moneylist(paymnets0);
        //初始化支付宝SDK
        csAlipay = new cs_alipay(context, paymnets3);
    }

    @OnClick({R.id.senbnt, R.id.lay1, R.id.lay2, R.id.lay3, R.id.sendsvip, R.id.pay_ok})
    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.lay1:
                TYPE = zfb;
                OnEorr(v, TYPE);
                break;
            case R.id.lay2:
                TYPE = wx;
                OnEorr(v, TYPE);
                break;
            case R.id.lay3:
                TYPE = unknown;
                OnEorr(v, TYPE);
                break;
            case R.id.senbnt:
                postdata();
                break;
            case R.id.sendsvip:
                activity_svip.starsetAction(context);
                break;
            case R.id.pay_ok:
                String url = BuildConfig.HTTP_API + "/invitefriends?type=%s&userid=" + UserInfo.getInstance().getUserId() + "&token=" + UserInfo.getInstance().getToken();
                activity_viecode.WebbookUrl(context, String.format(url, 7));
                break;
        }
    }

    /**
     * 确认发起支付走起
     */
    public void postdata() {
        if (!config.isNetworkAvailable()) {
            ToastUtil.toastLongMessage(context.getString(R.string.eorrfali2));
            return;
        }

        if (moneylist == null) {
            Toashow.show(context.getString(R.string.tv_msg136));
            return;
        }
        if (TYPE == 0) {
            Toashow.show(context.getString(R.string.tv_msg137));
            return;
        }

        if (!config.isNetworkAvailable()) {
            ToastUtil.toastLongMessage(context.getString(R.string.eorrfali2));
            return;
        }

        //进入发起支付数据
        switch (TYPE) {
            case zfb:
                //发起支付宝
                csAlipay.Paymoney(moneylist);
                break;
            case wx:
                //发起微信支付
                WXpayObject.getsWXpayObject().WXpay(moneylist);
                break;
            default:
                break;
        }


    }

    @Override
    public void OnEorr() {

    }

    public void OnEorr(View v, int type) {
        lay1.setBackground(getDrawable(R.drawable.activity014));
        lay2.setBackground(getDrawable(R.drawable.activity014));
        lay3.setBackground(getDrawable(R.drawable.activity014));
        alipay.setTextColor(getResources().getColor(R.color.teal006));
        wxpaly.setTextColor(getResources().getColor(R.color.teal006));
        switch (type) {
            case 1:
                alipay.setTextColor(getResources().getColor(R.color.white));
                v.setBackground(getDrawable(R.drawable.diis_bgzfb));
                break;
            case 2:
                wxpaly.setTextColor(getResources().getColor(R.color.white));
                v.setBackground(getDrawable(R.drawable.diis_bgwx));
                break;
            case 3:
                v.setBackground(getDrawable(R.drawable.diis_bg));
                break;
        }
        senbnt.setBackground(getDrawable(R.drawable.bg_radius_bottom_pink5));
        senbnt.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list.clear();
    }

    private Paymnets paymnets0 = new Paymnets() {
        @Override
        public void onFail() {

        }

        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onSuccess(Object object) {
            List<moneylist> moneylistList = (List<com.paixide.Module.api.moneylist>) object;
            list.addAll(moneylistList);
            radapter.notifyDataSetChanged();
        }
    };

    private Paymnets paymnets1 = new Paymnets() {
        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onFail() {

        }

        @Override
        public void onSuccess(Object object) {
            try {
                info info = (com.tencent.opensource.model.info) object;
                userInfo.setJinbi(info.getMoney());
                myoney.setText(String.valueOf(info.getMoney()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Paymnets paymnets2 = new Paymnets() {
        @Override
        public void onFail() {

        }

        @Override
        public void onClick(Object object) {
            moneylist = (moneylist) object;
            senbnt.setBackground(getDrawable(R.drawable.bg_radius_bottom_pink5));
            senbnt.setEnabled(true);
            senbnt.setText(String.format("%s元 确认充值", moneylist.getMoney()));

        }
    };

    private Paymnets paymnets3 = new Paymnets() {
        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onFail() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cs_alipay.showAlert(context, getString(R.string.tv_msg166));
                }
            });
        }

        @Override
        public void activity(String str) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cs_alipay.showAlert(context, str);
                }
            });
        }

        @Override
        public void onSuccess() {
            //支付成功刷新金币
            datamodule.getbalance(paymnets1);
        }
    };

}