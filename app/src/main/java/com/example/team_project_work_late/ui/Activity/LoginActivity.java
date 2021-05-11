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
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.application.NetRetrofit;
import com.example.team_project_work_late.application.RetrofitAPI;
import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclLendData;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private BcyclLendData bcyclLendData;      // 대여소 파싱용 데아터
    private BcyclDpstryData bcyclDpstryData;  // 보관소 파싱용 데이터

    private ImageButton startButton;
    private DatabaseReference myRef;
    private Button tempButton;

    void CheckLocation(Location location) {
        double longitude = location.getLongitude();     //위도
        double latitude = location.getLatitude();       //경도
        double altitude = location.getAltitude();       //고도
        System.out.println("위도 : " + longitude + "\n" +
                "경도 : " + latitude + "\n" +
                "고도 : " + altitude);
    }
//
    private Button btn_google_logout;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int REQ_SIGN_GOOGLE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tempButton = (Button)findViewById(R.id.tempButton);
        tempButton.setOnClickListener(v ->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            //데이터베이스 루트 얻기
            myRef = database.getReference();

            //쓰는 방법
            if(myRef != null){
                myRef.child(mAuth.getUid()).child("new").setValue("1번째");
                myRef.child(mAuth.getUid()).child("new").setValue("2번째");
                myRef.child(mAuth.getUid()).child("two").setValue("1번째");
                myRef.child(mAuth.getUid()).child("two").setValue("2번째");
//                Toast.makeText(LoginActivity.this, "myRef는 살아있다", Toast.LENGTH_SHORT).show();
            }

            //읽는 방법
            myRef.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                        System.out.println(task.getResult().getValue().toString());
                        //key-value형태로 저장, 기본 자체는 json파일 형태의 string  {new=2번째, two=2번째}
                    }
                }
            });

            //외부에서 데이터베이스가 바뀔 경우 발생하는 listner
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    String value = dataSnapshot.getValue(String.class);
//                    Toast.makeText(LoginActivity.this, value, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                }
//            });
        });

        // 파싱을 위한 설정
        parsingStart();
        startButton = (ImageButton)findViewById(R.id.startButton);
        btn_google_logout = (Button)findViewById(R.id.google_Logout);
        //gps
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //위도가 바뀔때마다 사용되는 Listioner
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                CheckLocation(location);
            }
        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


        startButton.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_SIGN_GOOGLE);

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
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("bcyclLendData",bcyclLendData);
//                bundle.putSerializable("bcyclDpstryData",bcyclDpstryData);
//                intent.putExtras(bundle);
//                startActivity(intent);
        });

        btn_google_logout.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "구글 및 파이어베이스 연결 끊음", Toast.LENGTH_SHORT).show();
            System.out.println("구글 및 파이어베이스 연결 끊음");
            googleSignInClient.signOut();
            mAuth.signOut();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //여기서 resultCode가 0 or -1로 나오며 오류가 뜨는데 task 자체는 성공
        //if(resultCode == REQ_SIGN_GOOGLE){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        if(task.isSuccessful()) {
            System.out.println("구글 로그인 task 성공");
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
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "파이어베이스 연동 성공", Toast.LENGTH_SHORT).show();
                            System.out.println("파이어베이스 연동 성공");
                        } else {
                            Toast.makeText(LoginActivity.this, "파이어베이스 연동 실패", Toast.LENGTH_SHORT).show();
                            System.out.println("파이어베이스 연동 실패");
                        }
                    }
                });
    }

    private void parsingStart(){
        NetRetrofit.getInstance().getAPI().getLendData().enqueue(new Callback<BcyclLendData>() {
            @Override
            public void onResponse(Call<BcyclLendData> call, Response<BcyclLendData> response) {
                bcyclLendData = response.body();
                Log.e("TEST","성공성공");
            }

            @Override
            public void onFailure(Call<BcyclLendData> call, Throwable t) {
                Log.e("Test","실패실패");
                t.printStackTrace();
            }
        });
        NetRetrofit.getInstance().getAPI().getDpstryData().enqueue(new Callback<BcyclDpstryData>() {
            @Override
            public void onResponse(Call<BcyclDpstryData> call, Response<BcyclDpstryData> response) {
                bcyclDpstryData = response.body();
                Log.e("TEST2","성공성공");
            }

            @Override
            public void onFailure(Call<BcyclDpstryData> call, Throwable t) {
                Log.e("Test2","실패실패");
                t.printStackTrace();
            }
        });
    }
}