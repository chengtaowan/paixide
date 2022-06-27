package com.paixide.BasActivity;

import android.content.Context;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.paixide.app.DemoApplication;
import com.paixide.listener.Paymnets;

import butterknife.ButterKnife;

public abstract class BasitemBaseAdapter {
    public Context context;
    public AMapLocation samapLocation;
    public Paymnets paymnets;
    public Object object;
    public int position;

    public BasitemBaseAdapter(View itemview) {
        ButterKnife.bind(this, itemview);
        this.context = DemoApplication.instance();
    }

    public abstract void bind(Object object, int position);

    public abstract void bind(Object object, int position, Paymnets paymnets);

    public abstract void bind(Context context, Object object, int position, Paymnets paymnets);

    public abstract void bind(Context context, Object object, int position, int ii, Paymnets paymnets, AMapLocation aMapLocation);

}
