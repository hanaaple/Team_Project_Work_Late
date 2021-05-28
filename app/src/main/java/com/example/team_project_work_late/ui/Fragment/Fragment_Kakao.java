package com.example.team_project_work_late.ui.Fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.adapter.BookMarkAdapter;
import com.example.team_project_work_late.adapter.CustomBalloonAdapter;
import com.example.team_project_work_late.application.DpstryDBHelper;
import com.example.team_project_work_late.application.LendDBHelper;
import com.example.team_project_work_late.listener.AddItemListener;
import com.example.team_project_work_late.model.BcyclDpstryData_responseBody_items;
import com.example.team_project_work_late.model.BcyclLendData_responseBody_items;
import com.example.team_project_work_late.model.BookMarkItem;
import com.example.team_project_work_late.model.DpstryMarker;
import com.example.team_project_work_late.model.LendMarker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

/*
 * @FileName Fragment_Kakao
 * @date 21.04.06
 * @role Fragment
 * @Inheritance Fragment
 * @Method onCreateView
 * */

public class Fragment_Kakao extends Fragment implements MapView.POIItemEventListener, MapView.MapViewEventListener{

    // 보관소 및 대여소 데이터는 DB를 통해서 불러옴 But, getLendDataList() 및 getDpstryDataList() 사용 비추 -> 내부 데이터양이 너무 많아서 앱이 멈추는 경우 매우 많음
    private LendDBHelper mLendDBHelper;
    private DpstryDBHelper mDpstryDBHelper;
    private AddItemListener addItemListener;
    private double mLatitude, mLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLendDBHelper = new LendDBHelper(getContext());
        mDpstryDBHelper = new DpstryDBHelper(getContext());
        Bundle bundle = getArguments();
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
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mLatitude, mLongitude),true);

        // 줌 정도 설정
        mapView.setZoomLevel(2,true);

        // 벌룬 마커 적용
        mapView.setCalloutBalloonAdapter(new CustomBalloonAdapter(getContext()));
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

//      마커는 이거 바탕으로 맨 아래 MapViewEventListener 내부 Marker 컨트롤로 들어감
//        for (BcyclLendData_responseBody_items item : bcyclLendData){
//            if (!item.getLatitude().isEmpty()&& !item.getLongitude().isEmpty()){
//                LendMarker customMarker = new LendMarker();
//                customMarker.setItemName(item.getBcyclLendNm());
//                customMarker.setTag(1);
//                MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()),Double.valueOf(item.getLongitude()));
//                customMarker.setMapPoint(point);
//                customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
//                customMarker.setCustomImageResourceId(R.drawable.marker_rental_s);
//                customMarker.setCustomImageAutoscale(true); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
//                customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
//                customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
//                customMarker.setCustomSelectedImageResourceId(R.drawable.selected_marker_rental_s);
//                BookMarkItem bookMarkItem = new BookMarkItem(item);
//                customMarker.setItem(bookMarkItem);
//                mapView.addPOIItem(customMarker);
//            }
//        }
//        for (BcyclDpstryData_responseBody_items item : bcyclDpstryData){
//            if (!item.getLatitude().isEmpty()&& !item.getLongitude().isEmpty()){
//                DpstryMarker customMarker = new DpstryMarker();
//                customMarker.setItemName(item.getDpstryNm());
//                customMarker.setTag(1);
//                MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()),Double.valueOf(item.getLongitude()));
//                customMarker.setMapPoint(point);
//                customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
//                customMarker.setCustomImageResourceId(R.drawable.marker_dpstry_s);
//                customMarker.setCustomImageAutoscale(true); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
//                customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
//                customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
//                customMarker.setCustomSelectedImageResourceId(R.drawable.selected_marker_dpstry_s);
//                customMarker.setItem(item);
//                mapView.addPOIItem(customMarker);
//            }
//        }

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


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        if (mapPOIItem instanceof LendMarker){
            LendMarker lendMarker = (LendMarker) mapPOIItem;

            String[] arr = {"즐겨찾기 등록", "리뷰", "길찾기", "취소"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
            dialog.setTitle("원하는 작업을 선택해주세요");
            dialog.setItems(arr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0: {
                            addItemListener.addItemSet(lendMarker.getItem());
                            Toast.makeText(mapView.getContext(), "즐겨찾기 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 1:{
                            Fragment_Review fragment_review = new Fragment_Review();
                            Bundle bundle_review = new Bundle();
                            fragment_review.setArguments(bundle_review);
                            bundle_review.putString("ItemName", lendMarker.getItem().getBcyclLendNm());
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment_review).commit();
//                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack().commit();
                            break;
                        }
                        case 2:{

                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://route?sp="+mLatitude+","+mLongitude+"&ep="+lendMarker.getItem().getLatitude()+","+lendMarker.getItem().getLongitude()+"&by=bike"));
                                startActivity(intent);
                            }catch (ActivityNotFoundException e){
                                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=net.daum.android.map"));
                                startActivity(intent);
                                Toast.makeText(getContext(), "카카오맵 설치가 필요합니다.", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                        case 3: {
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            });
            dialog.show();
        }else if (mapPOIItem instanceof DpstryMarker){
            DpstryMarker dpstryMarker = (DpstryMarker) mapPOIItem;

            String[] arr = {"길찾기", "취소"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
            dialog.setTitle("원하는 작업을 선택해주세요");
            dialog.setItems(arr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0:{
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://route?sp="+mLatitude+","+mLongitude+"&ep="+dpstryMarker.getItem().getLatitude()+","+dpstryMarker.getItem().getLongitude()+"&by=bike"));
                                startActivity(intent);
                            }catch (ActivityNotFoundException e){
                                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=net.daum.android.map"));
                                startActivity(intent);
                                Toast.makeText(getContext(), "카카오맵 설치가 필요합니다.", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                        case 1: {
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            });
            dialog.show();

        }
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        drawMarker(mapView);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        drawMarker(mapView);
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    private void drawMarker(MapView mapView) {
        mapView.removeAllPOIItems();
        if (mapView.getZoomLevel() < 4){
            MapPointBounds bounds = mapView.getMapPointBounds();
            List<BcyclLendData_responseBody_items> bcyclLendData = mLendDBHelper.getLendDataList(bounds.bottomLeft.getMapPointGeoCoord().latitude, bounds.bottomLeft.getMapPointGeoCoord().longitude, bounds.topRight.getMapPointGeoCoord().latitude, bounds.topRight.getMapPointGeoCoord().longitude);
            List<BcyclDpstryData_responseBody_items> bcyclDpstryData = mDpstryDBHelper.getDpstryDataList(bounds.bottomLeft.getMapPointGeoCoord().latitude, bounds.bottomLeft.getMapPointGeoCoord().longitude, bounds.topRight.getMapPointGeoCoord().latitude, bounds.topRight.getMapPointGeoCoord().longitude);

            for (BcyclLendData_responseBody_items item : bcyclLendData) {
                if (!item.getLatitude().isEmpty() && !item.getLongitude().isEmpty()) {
                    LendMarker customMarker = new LendMarker();
                    customMarker.setItemName(item.getBcyclLendNm());
                    customMarker.setTag(1);
                    MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()), Double.valueOf(item.getLongitude()));
                    customMarker.setMapPoint(point);
                    customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    customMarker.setCustomImageResourceId(R.drawable.marker_rental_s);
                    customMarker.setCustomImageAutoscale(true); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
                    customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
                    customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                    customMarker.setCustomSelectedImageResourceId(R.drawable.selected_marker_rental_s);
                    BookMarkItem bookMarkItem = new BookMarkItem(item);
                    customMarker.setItem(bookMarkItem);
                    mapView.addPOIItem(customMarker);
                }
            }
            for (BcyclDpstryData_responseBody_items item : bcyclDpstryData) {
                if (!item.getLatitude().isEmpty() && !item.getLongitude().isEmpty()) {
                    DpstryMarker customMarker = new DpstryMarker();
                    customMarker.setItemName(item.getDpstryNm());
                    customMarker.setTag(1);
                    MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(item.getLatitude()), Double.valueOf(item.getLongitude()));
                    customMarker.setMapPoint(point);
                    customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    customMarker.setCustomImageResourceId(R.drawable.marker_dpstry_s);
                    customMarker.setCustomImageAutoscale(true); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
                    customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
                    customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                    customMarker.setCustomSelectedImageResourceId(R.drawable.selected_marker_dpstry_s);
                    customMarker.setItem(item);
                    mapView.addPOIItem(customMarker);
                }
            }
        }
    }
}