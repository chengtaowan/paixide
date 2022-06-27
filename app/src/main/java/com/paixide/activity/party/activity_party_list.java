package com.paixide.activity.party;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.paixide.BasActivity.BasActivity2;
import com.paixide.Fragment.page1.fragment.one6;
import com.paixide.R;
import com.paixide.ViewPager.setViewPager;
import com.paixide.activity.party.fragment.party_page1;
import com.paixide.activity.party.fragment.party_page2;
import com.paixide.widget.Backtitle;

import butterknife.BindView;

/**
 * 聚会列表
 */
public class activity_party_list extends BasActivity2 {
    @BindView(R.id.backtitle)
    Backtitle backtitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private setViewPager adapager;

    public static void setAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, activity_party_list.class);
        context.startActivity(intent);
    }

    @Override
    protected int getview() {
        return R.layout.activity_party_list;
    }

    @Override
    public void iniview() {
        backtitle.setconter(getString(R.string.jhgl));
        if (fragments.size() == 0) {
            fragments.add(one6.show(1));             //聚会列表
            fragments.add(party_page1.show(1)); //我发布的
            fragments.add(party_page2.show(1)); //我参加的
        }
        adapager = new setViewPager(getSupportFragmentManager(), fragments, setViewPager.tabs6);
        viewPager.setAdapter(adapager);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
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