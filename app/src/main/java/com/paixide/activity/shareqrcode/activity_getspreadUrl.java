package com.paixide.activity.shareqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paixide.BasActivity.BasActivity2;
import com.paixide.R;
import com.paixide.Util.Constants;
import com.paixide.Util.Glideload;
import com.paixide.Util.Toashow;
import com.paixide.Util.glide.ImageLoadHelper;
import com.paixide.activity.Aboutus.activity_joinin;
import com.paixide.adapter.Radapter;
import com.paixide.adapter.Tiktokholder.mysetAdapter;
import com.paixide.adapter.itemholder.bgviewpager_item;
import com.paixide.dialog.dialog_shert;
import com.paixide.listener.Callback;
import com.paixide.listener.Paymnets;
import com.paixide.wxapi.WXpayObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.opensource.model.ShareDate;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.OnClick;

public class activity_getspreadUrl extends BasActivity2 {
    private static final String TAG = activity_getspreadUrl.class.getSimpleName();
    @BindView(R.id.viewpager)
    ViewPager2 viewpager;
    @BindView(R.id.image)
    ImageView image;
    ShareDate shareDate;

    public static void starAction(Context context, int type) {
        Intent intent = new Intent();
        intent.setClass(context, activity_getspreadUrl.class);
        intent.putExtra(Constants.TYPE, type);
        context.startActivity(intent);
    }

    public static void starAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, activity_getspreadUrl.class);
        context.startActivity(intent);
    }

    @Override
    protected int getview() {
        return R.layout.activity_getspread_url;
    }

    @Override
    public void iniview() {
        TYPE = getIntent().getIntExtra(Constants.TYPE, 0);
        Glideload.loadImage(image, R.mipmap.ic_welcome1, 10, 25);
        viewpager.setAdapter(radapter = new Radapter(context, list, Radapter.bgviewPager));
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager.setTag(position);
                shareDate = (ShareDate) list.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //overridePendingTransition(R.anim.book_dialog_enter, R.anim.book_dialog_exit);
    }

    @Override
    public void initData() {
        datamodule.share(TYPE, paymnets);
    }

    @Override
    @OnClick({R.id.backstage})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.backstage:
                dialog_shert.show(context, bitmapview);
                break;
        }
    }

    @Override
    public void OnEorr() {

    }

    private Paymnets paymnets = new Paymnets() {
        @Override
        public void onFail() {

        }

        @Override
        public void isNetworkAvailable() {

        }

        @Override
        public void onSuccess(Object object) {
            List<ShareDate> shareDates = (List<ShareDate>) object;
            list.addAll(shareDates);
            radapter.notifyDataSetChanged();
        }
    };

    private Paymnets bitmapview = new Paymnets() {
        @Override
        public void onFail() {

        }

        @Override
        public void onItemClick(int position) {
            switch (position) {
                case 1: {
                    shareWx(0);
                    break;
                }
                case 2: {
                    shareWx(1);
                    break;
                }
                case 3: {
                    try {
                        //保存二维码图片code
                        activity_shareqrcode.viewSaveToImage1(context, getviewview());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 4: {
                    //复制链接
                    copy(context, shareDate.getShareUrl());
                    break;

                }
            }
        }
    };

    private Bitmap getviewbitmap() {
        View view = viewpager.findViewWithTag(viewpager.getCurrentItem());
        View bitmapview = view.findViewById(R.id.constraint);
        Bitmap bitmap = activity_shareqrcode.loadBitmapFromView(bitmapview);
        return bitmap;
    }

    private View getviewview() {
        View view = viewpager.findViewWithTag(viewpager.getCurrentItem());
        View bitmapview = view.findViewById(R.id.constraint);
        return bitmapview;
    }

    private void shareWx(int type) {
        new Thread() {
            @Override
            public void run() {
                //分享到微信
                WXpayObject.getsWXpayObject().sharewechat(context, getviewbitmap(), type);
            }
        }.start();
    }

    public void copy(Context context, String share) {
        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", share);
        myClipboard.setPrimaryClip(myClip);
        Toashow.show(getString(R.string.coyptoast));
    }

}