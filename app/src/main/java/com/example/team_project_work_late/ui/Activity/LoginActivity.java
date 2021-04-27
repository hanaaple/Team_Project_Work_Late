package com.example.team_project_work_late.ui.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team_project_work_late.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    //gps
    private Button Locationbutton;
    private TextView textView;

    private SignInButton btn_google_login;
    private Button btn_google_logout;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int REQ_SIGN_GOOGLE = 100;


    // 받은 location을 체크하여 텍스트뷰에 보여주는 함수
    void CheckLocation(Location location) {
        double longitude = location.getLongitude();     //위도
        double latitude = location.getLatitude();       //경도
        double altitude = location.getAltitude();       //고도
        textView.setText("위도 : " + longitude + "\n" +
                "경도 : " + latitude + "\n" +
                "고도 : " + altitude);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //1-1. 위치 버튼, 텍스트뷰 할당
        Locationbutton = (Button) findViewById(R.id.locationButton);
        textView = (TextView) findViewById(R.id.locationText);

        //1-2. LocationManager 설정
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        //2. 위도가 바뀔때마다 사용되는 Listioner 만들고 CheckLocation 함수 실행(location을 업데이트하여 텍스트뷰에 보여줌)
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                CheckLocation(location);
            }
        };

        //3. 버튼을 누르면 Location 매니저를 이용해 위치 정보를 얻고 CheckLocation 함수 실행(location을 업데이트하여 텍스트뷰에 보여줌)
        Locationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                    if (locationManager != null) {
                        Log.d("GPSTracker", "LocationManger is Enable");
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            CheckLocation(location);
                        }
                    }
                }
            }
        });


        //1-1. 로그인 로그아웃 버튼 할당
        btn_google_login = (SignInButton) findViewById(R.id.google_login);
        btn_google_logout = (Button) findViewById(R.id.google_Logout);

        //2-1. 구글 로그인 설정 및 파이어 베이스 연동 설정
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


        //3-1. 로그인 버튼 Listener 구글 로그인 설정 및 성공 시 파이어베이스 연동
        btn_google_login.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_SIGN_GOOGLE);
        });

        //3-2. 로그아웃 버튼 Listener 설정
        btn_google_logout.setOnClickListener(v -> {
            googleSignInClient.signOut();
        });

    }

    //4. 구글 로그인을 실행하는 함수이며 성공 시 파이어베이스 연동함수를 실행
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if(resultCode == REQ_SIGN_GOOGLE){
        System.out.println("구글 성공");
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
        }
//        }else{
//            System.out.println(resultCode);
//            System.out.println(data);
//        }
    }

    //5. 파이어베이스에 연동하는 함수
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "성공", Toast.LENGTH_SHORT).show();
                            System.out.println("성공");
                        } else {
                            System.out.println("실패");
                        }
                    }
                });
    }
}