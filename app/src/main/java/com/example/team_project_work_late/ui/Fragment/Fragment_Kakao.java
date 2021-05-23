package com.example.team_project_work_late.ui.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.listener.AddItemListener;
import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclDpstryData_responseBody_items;
import com.example.team_project_work_late.model.BcyclLendData;
import com.example.team_project_work_late.model.BcyclLendData_responseBody_items;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

/*
 * @FileName Fragment_Kakao
 * @date 21.04.06
 * @role Fragment
 * @Inheritance Fragment
 * @Method onCreateView
 * */

public class Fragment_Kakao extends Fragment {

    // 보관소 데이터 접근법 예시
    // List<BcyclLendData_responseBody_items> list = bcyclLendData.getBcyclLendDataresponse().getBcyclLendDataresponseBody().getItems()
    // list.get(0).getLatitude()
    private List<BcyclLendData_responseBody_items> bcyclLendData;     // 대여소 파싱용 데아터
    private List<BcyclDpstryData_responseBody_items> bcyclDpstryData; // 보관소 파싱용 데이터
    private AddItemListener addItemListener;
    private double mLatitude, mLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        bcyclLendData = (List<BcyclLendData_responseBody_items>) bundle.getSerializable("bcyclLendData");
        bcyclDpstryData = (List<BcyclDpstryData_responseBody_items>) bundle.getSerializable("bcyclDpstryData");
        mLatitude = bundle.getDouble("latitude");
        mLongitude = bundle.getDouble("longitude");

        // findViewById를 위한 View 재 할당
        View v = inflater.inflate(R.layout.fragment__kakao, container, false);

        // 카카오맵 설정
        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 센터포인트 설정
        //MapPoint.mapPointWithGeoCoord(Double.valueOf(bcyclDpstryData.get(0).getLatitude()), Double.valueOf(bcyclDpstryData.get(0).getLongitude())),true
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(bcyclDpstryData.get(0).getLatitude()), Double.valueOf(bcyclDpstryData.get(0).getLongitude())),true);

        // 줌 정도 설정
        mapView.setZoomLevel(4,true);

        // 마커용 맵포인트 설정
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);


        for (BcyclLendData_responseBody_items item : bcyclLendData){
            if (!item.getLatitude().isEmpty()&& !item.getLongitude().isEmpty()){
                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName(item.getBcyclLendNm());
                customMarker.setTag(1);
                MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()),Double.valueOf(item.getLongitude()));
                customMarker.setMapPoint(point);
                customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                customMarker.setCustomImageResourceId(R.drawable.marker_rental_s);
                customMarker.setCustomImageAutoscale(true); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
                customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
                customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                customMarker.setCustomSelectedImageResourceId(R.drawable.selected_marker_rental_s);
                mapView.addPOIItem(customMarker);
            }
        }
        for (BcyclDpstryData_responseBody_items item : bcyclDpstryData){
            if (!item.getLatitude().isEmpty()&& !item.getLongitude().isEmpty()){
                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName(item.getDpstryNm());
                customMarker.setTag(1);
                MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()),Double.valueOf(item.getLongitude()));
                customMarker.setMapPoint(point);
                customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                customMarker.setCustomImageResourceId(R.drawable.marker_dpstry_s);
                customMarker.setCustomImageAutoscale(true); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
                customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
                customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                customMarker.setCustomSelectedImageResourceId(R.drawable.selected_marker_dpstry_s);
                mapView.addPOIItem(customMarker);
            }
        }


        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddItemListener){
            addItemListener = (AddItemListener) context;
        }else{
            throw new RuntimeException(context.toString() + "must implement AddItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        addItemListener = null;
    }



}