package com.example.team_project_work_late.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.team_project_work_late.R;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

public class CustomBalloonAdapter implements CalloutBalloonAdapter {

    private View mBalloon;
    private TextView tv_ball;
    private TextView tv_add;

    public CustomBalloonAdapter(Context context) {
        mBalloon = View.inflate(context,R.layout.balloon_layout,null);
        tv_ball = mBalloon.findViewById(R.id.ball_tv_name);
        tv_add = mBalloon.findViewById(R.id.ball_tv_address);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem mapPOIItem) {
        tv_ball.setText(mapPOIItem.getItemName());
        tv_add.setText("주소");
        return mBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem mapPOIItem) {
        return mBalloon;
    }
}
