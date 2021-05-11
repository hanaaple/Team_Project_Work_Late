package com.example.team_project_work_late.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_project_work_late.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/*
 * @FileName Fragment_Kakao
 * @date 21.04.06
 * @role Fragment
 * @Inheritance Fragment
 * @Method onCreateView
 * */

public class Fragment_Kakao extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        // 마커 설정 -> 커스텀 마커
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName("custom Marker");
        customMarker.setTag(1);
        customMarker.setMapPoint(MARKER_POINT);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        customMarker.setCustomImageResourceId(R.drawable.ic_baseline_location_on_24);
        customMarker.setCustomImageAutoscale(false);
        customMarker.setCustomImageAnchor(0.5f, 1.0f);
        customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(customMarker);

        return v;
    }

}