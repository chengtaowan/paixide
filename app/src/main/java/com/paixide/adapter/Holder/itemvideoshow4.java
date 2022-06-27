package com.paixide.adapter.Holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.paixide.BasActivity.BaseHolder;
import com.paixide.R;
import com.paixide.Util.Glideload;
import com.paixide.Util.glide.ImageLoadHelper;
import com.paixide.app.DemoApplication;
import com.paixide.listener.Callback;
import com.tencent.opensource.model.member;
import com.tencent.opensource.model.personal;

import butterknife.BindView;

public class itemvideoshow4 extends BaseHolder {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tips1)
    TextView tips1;
    @BindView(R.id.tips2)
    TextView tips2;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.address)
    TextView address;

    public itemvideoshow4(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.item_video_show4, null));
    }


    public void bind(Object object, int position, Callback callback, AMapLocation amapLocation) {
        this.callback = callback;
        this.samapLocation = amapLocation;
        member member = (member) object;
        personal personal = member.getPersonal();
        if (member == null) {
            return;
        }
        if (!TextUtils.isEmpty(member.getPicture())) {
            image.setBackground(null);
            ImageLoadHelper.glideShowCornerImageWithUrl(DemoApplication.instance(), member.getPicture(), image, 8);
        } else {
            int head2 = member.getSex() == 2 ? R.mipmap.icon_cert_head2 : R.mipmap.avapic;
            ImageLoadHelper.glideShowCornerImageWithUrl(context, head2, image, 8);
        }
        name.setText(member.getTruename());
        address.setText(TextUtils.isEmpty(member.getCity()) ? "" : member.getCity());
        if (personal != null) {
            content.setText(String.format("%s岁|%scm|%s", personal.getAge(), personal.getWeight(), personal.getOccupation()));
            if (!TextUtils.isEmpty(personal.getCity())) {
                address.setText(personal.getCity());
            }
        }

        if (callback != null) {
            itemView.setOnClickListener(v -> callback.OnClickListener(position));
        }

    }

    @Override
    public void bind(Object object, int position, Callback callback) {

    }

    @Override
    public void bind(Context context, Object object, int position, Callback callback) {

    }

    @Override
    public void OnClick(View v) {

    }
}
