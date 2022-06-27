package com.paixide.activity.Withdrawal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.paixide.Util.Constants;
import com.paixide.adapter.setAdapter;
import com.paixide.dialog.dialog_Blocked;
import com.tencent.opensource.model.item;
import com.paixide.BasActivity.BasActivity2;
import com.paixide.Module.api.qrcode;
import com.paixide.Module.api.reward;
import com.paixide.Module.api.wmoney;
import com.paixide.Util.StatusBarUtil;
import com.paixide.widget.itembackTopbr;
import com.paixide.R;
import com.paixide.Util.Toashow;
import com.paixide.Util.config;
import com.paixide.listener.Paymnets;
import com.paixide.dialog.dialog_Msg;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请 提现
 */
public class Withdrawals extends BasActivity2 implements AdapterView.OnItemClickListener {
    private static final String TAG = Withdrawals.class.getSimpleName();
    @BindView(R.id.itemback)
    itembackTopbr itemback;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.button)
    Button button;
    List<item> list = new ArrayList<>();
    wmoney wmoney;
    reward r;
    qrcode qrcode;

    public static void setAction(Activity context) {
        Intent intent = new Intent(context, Withdrawals.class);
        intent.putExtra(Constants.TYPE, 1);
        context.startActivityForResult(intent, com.paixide.Util.Constants.requestCode);
    }

    @Override
    protected int getview() {
        StatusBarUtil.setStatusBar(activity, getResources().getColor(R.color.home3));
        return R.layout.activity_withdrawal;
    }

    @Override
    public void iniview() {
        itemback.settitle(getString(R.string.moneytv2));
        itemback.contertext.setTextColor(getResources().getColor(R.color.homeback));
        itemback.righttext.setText(R.string.momeysstv1);
        itemback.setHaidtopBackgroundColor(true);
        gridview.setAdapter(adappter = new setAdapter(context, list));
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        datamodule.wmoney(paymnets1);//提现列表
        datamodule.reward(paymnets2);
        datamodule.getonCreate(paymnets3);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        item item = list.get(position);
        wmoney = (wmoney) item.object;
        if (wmoney == null) {
            return;
        }
        adappter.setClickPosition(position);
        adappter.notifyDataSetChanged();
        button.setText(String.format(getString(R.string.tv_195), wmoney.getMoney()));

    }

    @OnClick({R.id.tvtitle, R.id.tv3title, R.id.button})
    public void OnClick(View v) {
        if (!config.isNetworkAvailable()) {
            ToastUtil.toastShortMessage(getResources().getString(R.string.eorrfali2));
            return;
        }
        switch (v.getId()) {
            case R.id.tvtitle:
                startActivity(new Intent(context, ordermoeny.class));
                break;
            case R.id.tv3title:
                dialog_Msg.dialogmsg(context);
                break;
            case R.id.button:
                sendwmoney();
                break;
        }
    }

    @Override
    public void OnEorr() {

    }

    public void sendwmoney() {
        if (userInfo.getState() == 3) {
            dialog_Blocked.myshow(context);
            return;
        }

        if (wmoney == null && list.size() > 0) {
            item item = list.get(0);
            wmoney = (wmoney) item.object;
        }
        if (r == null || wmoney == null) {
            Toashow.show(getString(R.string.tv_msg36));
            return;
        }
        double money = Double.parseDouble(r.getMoney());       //可用余额
        double money1 = Double.parseDouble(wmoney.getMoney());  //金额
        if (money < money1) {
            Toashow.show(String.format(getString(R.string.tv_msg37), "" + money1));
            return;
        }
        addreward(money1);
    }

    /**
     * 提交申请
     */
    public void addreward(double money) {
        int m = (int) money;
        datamodule.addreward(m, paymnets4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == config.sussess) {
            list.clear();

            datamodule.wmoney(paymnets1);
            datamodule.reward(paymnets2);
        }
    }

    public Paymnets paymnets1 = new Paymnets() {
        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onFail() {

        }

        @Override
        public void onSuccess(Object object) {
            List<wmoney> ts = (List<com.paixide.Module.api.wmoney>) object;
            for (wmoney t : ts) {
                item item = new item();
                item.type = com.paixide.adapter.setAdapter.Withdrawals;
                item.object = t;
                list.add(item);
            }
            adappter.notifyDataSetChanged();
        }
    };

    public Paymnets paymnets2 = new Paymnets() {
        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onFail() {

        }

        @Override
        public void onSuccess(Object object) {
            r = (reward) object;
            money.setText(r.getMoney());
        }
    };

    public Paymnets paymnets3 = new Paymnets() {
        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onFail() {

        }

        @Override
        public void onSuccess(Object object) {
            qrcode = (com.paixide.Module.api.qrcode) object;
        }
    };

    public Paymnets paymnets4 = new Paymnets() {
        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onFail() {

        }

        @Override
        public void onSuccess() {
            datamodule.reward(paymnets2);
        }
    };

}