package com.paixide.Fragment.page6;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.paixide.BasActivity.BasFragment;
import com.paixide.Fragment.page6.fragment.pagevideo;
import com.paixide.R;
import com.paixide.Receiver.MyService;
import com.paixide.Util.Constants;
import com.paixide.Util.config;
import com.paixide.ViewPager.setViewPager;
import com.paixide.activity.Searchactivity.SearchActivity;
import com.paixide.activity.activity_item.fragment_load;
import com.paixide.app.DemoApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页短视频主页
 */
public class page6 extends BasFragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public View getview(LayoutInflater inflater) {
        return inflater.inflate(R.layout.home_viewpager, null);
    }

    @Override
    public void iniview() {
        viewPager.setAdapter(setViewPager = new setViewPager(getChildFragmentManager(), getlistfragmentslist(), setViewPager.oneindex));
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void initData() {
        //在Activity中停止后台服务
        if (config.isServiceRunning(Constants.className)) {
            activity.stopService(new Intent(DemoApplication.instance(), MyService.class));
        }
    }

    @Override
    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.select,R.id.tv4})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv4:
                fragment_load.starsetAction(context);
                break;
            case R.id.select:
                SearchActivity.starsetAction(context);
                break;
        }

    }

    @Override
    public void OnEorr() {

    }

    @Override
    public void onRefresh() {

    }

    /**
     * 加载3个不频的视频类型
     * @return
     */
    public List<Fragment> getlistfragmentslist() {
        if (fragmentslist.size() == 0) {
            fragmentslist.add(pagevideo.show(1));
            fragmentslist.add(pagevideo.show(2));
            fragmentslist.add(pagevideo.show(3));
        }
        return fragmentslist;
    }


}
