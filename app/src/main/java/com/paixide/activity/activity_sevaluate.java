package com.paixide.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.paixide.adapter.Radapter;
import com.paixide.getHandler.PostModule;
import com.tencent.qcloud.tim.tuikit.live.BuildConfig;
import com.paixide.BasActivity.BasActivity2;
import com.paixide.R;
import com.paixide.Util.Constants;
import com.paixide.Util.Toashow;
import com.paixide.Util.config;
import com.paixide.listener.Paymnets;
import com.paixide.getHandler.JsonUitl;
import com.paixide.widget.EndLessOnScrollListener;
import com.paixide.widget.itembackTopbr;
import com.tencent.opensource.model.UserInfo;
import com.tencent.opensource.model.getevaluate;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务评价列表
 */
public class activity_sevaluate extends BasActivity2 implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "activity_sevaluate";
    @BindView(R.id.itemback)
    itembackTopbr itemback;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @Override
    protected int getview() {

        return R.layout.activity_sevaluate;
    }

    @Override
    public void iniview() {
        getuserid = getIntent().getStringExtra(Constants.USERID);
        userInfo = UserInfo.getInstance();
        itemback.settitle(getString(R.string.tv_msg122));
        itemback.setHaidtopBackgroundColor(true);
        radapter = new Radapter(this, list, Radapter.activity_sevaluate);
        radapter.setPaymnets(new Paymnets() {
            @Override
            public void status(int position) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(radapter);
        recyclerview.addOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                initData();
            }
        });
    }

    @Override
    public void initData() {
        if (!config.isNetworkAvailable()) {
            Toashow.show(getString(R.string.eorrfali2));
            return;
        }
        totalPage++;
        PostModule.getModule(BuildConfig.HTTP_API + "/getevaluate?userid=" + getuserid + "&page=" + totalPage + "&token=" + userInfo.getToken(), new Paymnets() {
            @Override
            public void success(String date) {
                try {
                    List<getevaluate> mydate = JsonUitl.stringToList(date, getevaluate.class);
                    if (mydate.size() == 0) {
                        totalPage--;
                        if (totalPage > 0) {
                            Toashow.show(getString(R.string.eorrtext));
                        }
                        OnEorr();
                        return;
                    }

                    list.addAll(mydate);
                    radapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                OnEorr();
            }

            @Override
            public void fall(int code) {
                OnEorr();
            }
        });
    }

    @OnClick({R.id.eorr})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.eorr:
                totalPage = 0;
                initData();
                break;
        }

    }

    @Override
    public void OnEorr() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setVisibility(list.size() > 0 ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        totalPage = 0;
        list.clear();
        radapter.notifyDataSetChanged();
        initData();
    }
}