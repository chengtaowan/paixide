package com.paixide.dialog;

import android.app.Dialog;
import android.content.Context;

import com.paixide.R;

public class Dialog_Video extends Dialog {

    public Dialog_Video(Context context) {
        super(context,R.style.BottomDialog);
        setContentView(R.layout.activity_svideo);
    }
}
