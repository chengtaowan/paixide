package com.paixide.activity.party;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paixide.BasActivity.BasActivity2;
import com.paixide.R;
import com.paixide.Util.Constants;
import com.paixide.Util.Toashow;
import com.paixide.Util.config;
import com.paixide.activity.Aboutus.activity_viecode;
import com.paixide.activity.edit.activity_updateedit;
import com.paixide.dialog.ddialog_jinbi;
import com.paixide.dialog.ddialog_patry;
import com.paixide.getHandler.Webrowse;
import com.paixide.listener.Paymnets;
import com.paixide.widget.party_weide;
import com.tencent.opensource.model.Party;
import com.tencent.opensource.model.UserInfo;
import com.tencent.opensource.model.partyname;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 聚会报名
 */
public class activity_party_toName extends BasActivity2 {
    @BindView(R.id.e1)
    EditText e1;
    @BindView(R.id.t1)
    party_weide t1;
    @BindView(R.id.t2)
    party_weide t2;
    @BindView(R.id.t3)
    party_weide t3;
    @BindView(R.id.t4)
    party_weide t4;
    @BindView(R.id.t5)
    TextView t5;
    @BindView(R.id.check_login)
    CheckBox check_login;
    @BindView(R.id.clockwise)
    ImageView clockwise;
    Party party;
    partyname partyname;

    public static void setAction(Context context) {
        Intent intent = new Intent(context, activity_party_toName.class);
        context.startActivity(intent);
    }

    public static void setAction(Activity context, Party party, int type) {
        Intent intent = new Intent(context, activity_party_toName.class);
        intent.putExtra(Constants.JSON, String.valueOf(party.getId()));
        intent.putExtra(Constants.ICON_URL, party.getCover());
        intent.putExtra(Constants.TYPE, type);
        context.startActivityForResult(intent, config.setResult);
    }

    @Override
    protected int getview() {
        return R.layout.activity_party_to_name;
    }

    @Override
    public void iniview() {
        partyname = new partyname();
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clockwise.setVisibility(s.length() > 1 ? VISIBLE : GONE);
            }


        });
        String id = getIntent().getStringExtra(Constants.JSON);
        partyname.setTid(Integer.parseInt(id));
        partyname.setUserid(Integer.parseInt(UserInfo.getInstance().getUserId()));
        datamodule.partyfind(id, new Paymnets() {
            @Override
            public void isNetworkAvailable() {

            }

            @Override
            public void onSuccess(Object object) {
                party = (Party) object;
                t1.setMtag(String.format("%s(限%s人)", party.getTitle(), party.getPartynumbe()));
                t2.setMtag(party.getPartytime());
                t3.setMtag(party.getAddress());
            }

            @Override
            public void onFail() {

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.button, R.id.t5, R.id.t4, R.id.clockwise})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.button: {
                String trim = e1.getText().toString().trim();
                if (TextUtils.isEmpty(t4.getMtag()) || t4.getMtag().equals("请选择")) {
                    Toashow.show(getString(R.string.toname01));
                    return;

                }
                if (TextUtils.isEmpty(trim)) {
                    Toashow.show(getString(R.string.tsa12));
                    return;

                }
                if (!check_login.isChecked()) {
                    Toashow.show(getString(R.string.patry_a4));
                    return;
                }
                if (userInfo.getUserId().equals(String.valueOf(party.getUserid()))) {
                    Toashow.show(getString(R.string.toast));
                    return;
                }
                ddialog_patry.myshow(context, party, new Paymnets() {
                    @Override
                    public void onSuccess() {
                        partyname.setMsg(t4.getMtag());
                        partyname.setRemarks(e1.getText().toString());
                        showDialog();
                        datamodule.partyname(partyname, new Paymnets() {
                            @Override
                            public void isNetworkAvailable() {
                                dismissDialog();
                            }

                            @Override
                            public void onSuccess() {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissDialog();
                                        Toashow.show(getString(R.string.toname));
                                        finish();
                                    }
                                }, 500);
                            }

                            @Override
                            public void onFail(String msg) {
                                Toashow.show(msg);
                                dismissDialog();
                            }
                        });
                    }

                    @Override
                    public void onFail() {

                    }

                });
                break;

            }
            case R.id.t5: {
                activity_viecode.WebbookUrl(context, Webrowse.tips);
                break;
            }
            case R.id.clockwise: {
                e1.setText(null);
                break;
            }
            case R.id.t4: {
                activity_updateedit.initTimePicker5(context, t4.getMtitle(), new Paymnets() {
                    @Override
                    public void onSuccess(String msg) {
                        t4.setMtag(msg);
                    }
                });
                break;
            }
        }
    }

    @Override
    public void OnEorr() {

    }

}