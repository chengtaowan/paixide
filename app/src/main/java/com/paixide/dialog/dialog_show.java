package com.paixide.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.paixide.BasActivity.BaseDialog;
import com.paixide.Module.McallBack;
import com.paixide.listener.Paymnets;
import com.tencent.opensource.model.member;
import com.paixide.R;

import butterknife.OnClick;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import androidx.annotation.NonNull;

public class dialog_show extends BaseDialog {
    private String TAG = "dialog_show";
    private TextView chartmsg;
    private member member;

    public static void dialogshow(Context context, member member, Paymnets paymnets) {
        dialog_show dialogShow = new dialog_show(context, member, paymnets);
        dialogShow.show();
    }

    public dialog_show(@NonNull Context context, member member, Paymnets paymnets) {
        super(context, paymnets);
        this.member = member;
        chartmsg = findViewById(R.id.chartmsg);
        chartmsg.setText(getContext().getResources().getString(R.string.dialoged));
    }

    @Override
    public int getview() {
        return R.layout.dialog_ed;
    }

    @OnClick({R.id.kankan, R.id.exit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.kankan:
                McallBack.starsetAction(context);
                break;
            case R.id.exit:
                dialog_Config.dialogBottom(context,paymnets);
                break;
        }
        dismiss();

    }

}
