package com.paixide.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.paixide.BasActivity.BasActivity2;
import com.paixide.Fragment.fragment.Fromnetfollowlist;
import com.paixide.Util.StatusBarUtil;
import com.paixide.widget.itembackTopbr;
import com.paixide.R;

import butterknife.BindView;

/**
 * 我的关注
 */
public class activity_follow extends BasActivity2 {
    private static final String TAG =activity_follow.class.getSimpleName();
    @BindView(R.id.imgback)
    itembackTopbr imgback;

    public static void starsetAction(Context context) {
        Intent intent = new Intent(context, activity_follow.class);
        context.startActivity(intent);
    }
    @Override
    protected int getview() {
        StatusBarUtil.setStatusBar(this, getResources().getColor(R.color.home3));
        return R.layout.activity_follow;
    }

    @Override
    public void iniview() {
        imgback.settitle(getString(R.string.myfollow));
        imgback.setHaidtopBackgroundColor(getResources().getColor(R.color.home3));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, Fromnetfollowlist.args(1), "").commit();

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