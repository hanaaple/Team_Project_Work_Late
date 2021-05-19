package com.example.team_project_work_late.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.example.team_project_work_late.R;
import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclLendData;
import com.example.team_project_work_late.ui.Fragment.Fragment_Kakao;
import com.example.team_project_work_late.ui.Fragment.Fragment_bookMark;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";

    private BcyclLendData bcyclLendData;     // 대여소 파싱용 데아터
    private BcyclDpstryData bcyclDpstryData; // 보관소 파싱용 데이터
    private BottomNavigationView mBottomNavigationView;
    private Location mlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        InitializeGPS();
        //첫 화면 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,fragment_kakao).commit();

        //case 함수를 통해 클릭 받을 때마다 화면 변경하기
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_1 :
                        Fragment_Kakao fragment_kakao = new Fragment_Kakao();
                        Bundle bundle_kakao = new Bundle();
                        fragment_kakao.setArguments(bundle_kakao);
                        bundle_kakao.putSerializable("bcyclLendData",bcyclLendData);
                        bundle_kakao.putSerializable("bcyclDpstryData",bcyclDpstryData);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment_kakao).commit();
                        break;
                    case R.id.action_2:
                        Fragment_bookMark fragment_bookMark = new Fragment_bookMark();
                        Bundle bundle_bookmark = new Bundle();
                        fragment_bookMark.setArguments(bundle_bookmark);
                        bundle_bookmark.putSerializable("bcyclLendData",bcyclLendData);
                        bundle_bookmark.putSerializable("bcyclDpstryData",bcyclDpstryData);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment_bookMark).commit();
                        break;
                    case R.id.action_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Frag3()).commit();
                        break;

                }
                return true;
            }
        });
    }

    private void init(){
        mBottomNavigationView=findViewById(R.id.bottom_navigation);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bcyclLendData = (BcyclLendData) bundle.getSerializable("bcyclLendData");
        bcyclDpstryData = (BcyclDpstryData) bundle.getSerializable("bcyclDpstryData");
    }
    private void InitializeGPS(){
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //위도가 바뀔때마다 사용되는 Listioner
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                mlocation = location;
            }
        };
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            if (locationManager != null) {
                Log.d("GPSTracker", "LocationManger is Enable");
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    mlocation = location;
                }
            }
        }
    }

    // 해시키 받아오는 메소드
//    private void getHashKey(){
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("KeyHash", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT)); 
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//    }
}