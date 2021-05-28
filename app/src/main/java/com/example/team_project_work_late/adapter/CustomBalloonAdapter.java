package com.example.team_project_work_late.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.model.BookMarkItem;
import com.example.team_project_work_late.model.DpstryMarker;
import com.example.team_project_work_late.model.LendMarker;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

public class CustomBalloonAdapter implements CalloutBalloonAdapter {

    private View mBalloon;
    private TextView tv_ball;
    private TextView tv_set;
    private TextView tv_add;
    private TextView tv_touch;

    public CustomBalloonAdapter(Context context) {
        mBalloon = View.inflate(context,R.layout.balloon_layout,null);
        tv_ball = mBalloon.findViewById(R.id.ball_tv_name);
        tv_add = mBalloon.findViewById(R.id.ball_tv_address);
        tv_set = mBalloon.findViewById(R.id.ball_tv_set);
        tv_touch = mBalloon.findViewById(R.id.ball_tv_touch);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem mapPOIItem) {
        if (mapPOIItem instanceof LendMarker){

            tv_ball.setText(((LendMarker) mapPOIItem).getItem().getBcyclLendNm());
            tv_set.setText("대여소");
            if (!((LendMarker) mapPOIItem).getItem().getLnmadr().isEmpty() && !((LendMarker) mapPOIItem).getItem().getRdnmadr().isEmpty()){
                if (((LendMarker) mapPOIItem).getItem().getLnmadr().length() > ((LendMarker) mapPOIItem).getItem().getRdnmadr().length()){
                    tv_add.setText(((LendMarker) mapPOIItem).getItem().getLnmadr());
                }else{
                    tv_add.setText(((LendMarker) mapPOIItem).getItem().getRdnmadr());
                }
            } else if (((LendMarker) mapPOIItem).getItem().getLnmadr().isEmpty()){
                tv_add.setText(((LendMarker) mapPOIItem).getItem().getRdnmadr());
            } else if (((LendMarker) mapPOIItem).getItem().getRdnmadr().isEmpty()){
                tv_add.setText(((LendMarker) mapPOIItem).getItem().getLnmadr());
            }
        }else if (mapPOIItem instanceof DpstryMarker){
            tv_ball.setText(((DpstryMarker) mapPOIItem).getItem().getDpstryNm());
            tv_set.setText("보관소");
            if (!((DpstryMarker) mapPOIItem).getItem().getLnmadr().isEmpty() && !((DpstryMarker) mapPOIItem).getItem().getRdnmadr().isEmpty()){
                if (((DpstryMarker) mapPOIItem).getItem().getLnmadr().length() > ((DpstryMarker) mapPOIItem).getItem().getRdnmadr().length()){
                    tv_add.setText(((DpstryMarker) mapPOIItem).getItem().getLnmadr());
                }else{
                    tv_add.setText(((DpstryMarker) mapPOIItem).getItem().getRdnmadr());
                }
            }else if (((DpstryMarker) mapPOIItem).getItem().getLnmadr().isEmpty()){
                tv_add.setText(((DpstryMarker) mapPOIItem).getItem().getRdnmadr());
            }else if(((DpstryMarker) mapPOIItem).getItem().getRdnmadr().isEmpty()){
                tv_add.setText(((DpstryMarker) mapPOIItem).getItem().getLnmadr());
            }
        }
        return mBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem mapPOIItem) {
        return mBalloon;
    }
}
