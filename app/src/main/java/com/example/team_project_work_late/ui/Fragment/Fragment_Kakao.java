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

<<<<<<< Updated upstream
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
=======
        // 마커 설정 -> 기본적인 세팅값
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);


        // 커스텀 마커 수정 필요 추가로 Dpstry도 마커 추가필요
        for (BcyclLendData_responseBody_items item : bcyclLendData.getBcyclLendDataresponse().getBcyclLendDataresponseBody().getItems()){
            if (!item.getLatitude().isEmpty()&& !item.getLongitude().isEmpty()){
                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName(item.getBcyclLendNm());
                customMarker.setTag(1);
                MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()),Double.valueOf(item.getLongitude()));
                customMarker.setMapPoint(point);
//                customMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                customMarker.setCustomImageResourceId(R.drawable.ic_baseline_location_on_24_lend);
                customMarker.setCustomImageAutoscale(false); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
                customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
                customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(customMarker);
            }
        }
        for (BcyclDpstryData_responseBody_items item : bcyclDpstryData.getBcyclDpstryData_response().getBcyclDpstryData_responseBody().getItems()){
            if (!item.getLatitude().isEmpty()&& !item.getLongitude().isEmpty()){
                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName(item.getDpstryNm());
                customMarker.setTag(1);
                MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()),Double.valueOf(item.getLongitude()));
                customMarker.setMapPoint(point);
//                customMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                customMarker.setCustomImageResourceId(R.drawable.ic_baseline_location_on_24_dpstry);
                customMarker.setCustomImageAutoscale(false); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
                customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
                customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(customMarker);
            }
        }
>>>>>>> Stashed changes

        return v;
    }

}