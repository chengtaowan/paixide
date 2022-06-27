package com.paixide.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.paixide.BasActivity.BasActivity2;
import com.paixide.R;
import com.paixide.Util.StatusBarUtil;

public class activity_new_boon extends BasActivity2 {

    public static void starsetAction(Context context){
        context.startActivity(new Intent(context, activity_new_boon.class));
    }

    @Override
    protected int getview() {
        return R.layout.activity_new_boon;
    }

    @Override
    public void iniview() {
        StatusBarUtil.hideBottomUIMenu(activity);
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
