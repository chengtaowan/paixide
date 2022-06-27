package com.paixide.activity.picenter;

import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paixide.BasActivity.BasActivity2;
import com.paixide.Util.Constants;
import com.paixide.Util.Glideload;
import com.paixide.Util.StatusBarUtil;
import com.paixide.ViewPager.pageadapter;
import com.paixide.widget.HackyViewPager;
import com.tencent.opensource.model.imglist;
import com.paixide.R;
import com.paixide.app.DemoApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * 图片浏览
 */
public class activity_picbage extends BasActivity2 {
    private static final String TAG = activity_picbage.class.getName();
    @BindView(R.id.viewpager)
    HackyViewPager viewPager;
    @BindView(R.id.pages)
    TextView pages;
    @BindView(R.id.title)
    TextView title;
    pageadapter myviewadapter;
    List<View> list = new ArrayList<>();
    List<imglist> perimg;

    @Override
    protected int getview() {
        StatusBarUtil.transparencyBar(this);
        return R.layout.activity_viewpages;
    }

    @Override
    public void iniview() {
        int position = getIntent().getIntExtra(Constants.POSITION, 0);
        perimg = (List<imglist>) getIntent().getSerializableExtra(Constants.perimg);
        viewPager.setAdapter(myviewadapter = new pageadapter(context, getListimg(perimg)));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                pageposition(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setCurrentItem(position);
        pageposition(position);
    }

    @Override
    public void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
        }
    }

    @Override
    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void OnEorr() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list.clear();

    }

    /**
     * 返回VIEW集合
     *
     * @param perimgList
     * @return
     */
    private List<View> getListimg(List<imglist> perimgList) {
        for (imglist perimg : perimgList) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.show_item_image, null);
            String presignedURL = perimg.getTencent() == Constants.TENCENT ? DemoApplication.presignedURL(perimg.getPic()) : perimg.getPic();
            ImageView photo_view = inflate.findViewById(R.id.photo_view);
            ProgressBar progressBar = inflate.findViewById(R.id.progressBar);
            progressBar.setVisibility(GONE);
            Glideload.loadImage(photo_view, presignedURL);
            list.add(inflate);
        }
        return list;
    }

    /**
     * page页码数
     *
     * @param position
     */
    private void pageposition(int position) {
        imglist perimg = this.perimg.get(position);
        if (perimg == null) {
            return;
        }
        title.setVisibility(!TextUtils.isEmpty(perimg.getTitle()) ? View.VISIBLE : GONE);
        title.setText(perimg.getTitle());
        pages.setText(String.format("%s/%s", position + 1, list.size()));
    }

}