package com.example.team_project_work_late.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclLendData;

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
    private BcyclLendData bcyclLendData;        // 대여소 파싱용 데아터
    private BcyclDpstryData bcyclDpstryData;    // 보관소 파싱용 데이터

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        bcyclLendData = (BcyclLendData) bundle.getSerializable("bcyclLendData");
        bcyclDpstryData = (BcyclDpstryData) bundle.getSerializable("bcyclDpstryData");

        // findViewById를 위한 View 재 할당
        View v = inflater.inflate(R.layout.fragment__kakao, container, false);

        // 카카오맵 설정
        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 센터포인트 설정
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304),true);

        // 줌 정도 설정
        mapView.setZoomLevel(4,true);

        // 마커용 맵포인트 설정
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);

        // 마커 설정 -> 기본적인 세팅값
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);

        return v;
    }

}