package com.paixide.activity.party;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paixide.BasActivity.BasActivity2;
import com.paixide.R;
import com.paixide.Util.Constants;
import com.paixide.Util.Glideload;
import com.paixide.Util.Imagecompressiontool;
import com.paixide.Util.StatusBarUtil;
import com.paixide.Util.Toashow;
import com.paixide.Util.config;
import com.paixide.activity.picenter.activity_picenter;
import com.paixide.listener.Paymnets;
import com.paixide.widget.FlowLayout;
import com.tencent.opensource.model.Party;
import com.tencent.opensource.model.member;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 聚会报名
 */
public class activity_party_imge extends BasActivity2 {
    private static final String TAG = activity_party_imge.class.getName();
    Party party;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.ucrop)
    ImageView ucrop;
    @BindView(R.id.perres)
    TextView perres;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.decode)
    TextView decode;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.datetime)
    TextView datetime;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.buy)
    TextView buy;
    @BindView(R.id.FlowLayout)
    com.paixide.widget.FlowLayout flowLayout;
    String ICON_URL;
    String id;

    public static void setAction(Context context) {
        Intent intent = new Intent(context, activity_party_imge.class);
        context.startActivity(intent);
    }

    public static void setAction(Context context, Party party, int type) {
        Intent intent = new Intent(context, activity_party_imge.class);
        intent.putExtra(Constants.JSON, String.valueOf(party.getId()));
        intent.putExtra(Constants.ICON_URL, party.getCover());
        intent.putExtra(Constants.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int getview() {
        StatusBarUtil.transparencyBar(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
        }
        return R.layout.activity_party_imge;
    }

    @Override
    public void iniview() {
        id = getIntent().getStringExtra(Constants.JSON);
        ICON_URL = getIntent().getStringExtra(Constants.ICON_URL);
        int type = getIntent().getIntExtra(Constants.TYPE, 0);
        if (type == 0) {
            party = gson.fromJson(id, Party.class);
            glides(party);
        }

        if (type == 1) {
            datamodule.partyfind(id, new Paymnets() {
                @Override
                public void isNetworkAvailable() {

                }

                @Override
                public void onSuccess(Object object) {
                    party = (Party) object;
                    glides(party);
                }

                @Override
                public void onFail() {

                }
            });
        }
    }

    @Override
    public void initData() {

    }


    @Override
    @OnClick({R.id.back, R.id.ucrop, R.id.sned})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.ucrop:
                activity_picenter.setActionactivity(context, String.valueOf(party.getMember().getId()));
                break;
            case R.id.sned:
                activity_party_toName.setAction(activity, party, 0);
                break;
        }

    }

    @Override
    public void OnEorr() {

    }

    private void glides(Party party) {
        com.tencent.opensource.model.member member = party.getMember();
        if (!TextUtils.isEmpty(member.getPicture())) {
            Glide.with(context).load(member.getPicture()).into(ucrop);
        }

        Glideload.loadImage(image, ICON_URL, 5, 25);
        title.setText(member.getTruename());
        decode.setText(String.format("%s(限%s人)", party.getTitle(), party.getPartynumbe()));
        address.setText(party.getAddress());
        datetime.setText(party.getPartytime());
        tag.setText(party.getMsg());
        perres.setText(String.valueOf(party.getPartyenumbe()));
        String[] split = party.getTdesc().split(",");
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_flowlayout, flowLayout, false);
                tv.setText(split[i]);
                tv.setTextColor(Color.WHITE);
                flowLayout.addView(tv);
            }
        } else {
            TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_flowlayout, flowLayout, false);
            tv.setText(party.getTdesc());
            tv.setTextColor(Color.WHITE);
            flowLayout.addView(tv);
        }

        if (userInfo.getUserId().equals(String.valueOf(party.getUserid()))) {
            if (party.getCode() == 0) {
                buy.setText(getString(R.string.ta14));
            } else {
                buy.setText(getString(R.string.ta13));
            }
        } else {
            if (party.getCode() == 0) {
                buy.setText(getString(R.string.ta16));
            } else {
                buy.setText(getString(R.string.ta13));
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == config.setResult) {
            datamodule.partyfind(id, new Paymnets() {
                @Override
                public void isNetworkAvailable() {

                }

                @Override
                public void onSuccess(Object object) {
                    party = (Party) object;
                    glides(party);
                }

                @Override
                public void onFail() {

                }
            });
        }

    }
}