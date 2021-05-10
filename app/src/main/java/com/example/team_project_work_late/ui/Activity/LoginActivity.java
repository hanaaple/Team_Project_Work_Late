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
import com.example.team_project_work_late.application.RetrofitAPI;
import com.example.team_project_work_late.model.Data;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    public static Data data; // 파싱용 데아터
    //gps
    private Button Locationbutton;
    private TextView textView;

    void CheckLocation(Location location) {
        double longitude = location.getLongitude();     //위도
        double latitude = location.getLatitude();       //경도
        double altitude = location.getAltitude();       //고도
        textView.setText("위도 : " + longitude + "\n" +
                "경도 : " + latitude + "\n" +
                "고도 : " + altitude);
    }
//

    private SignInButton btn_google_login;
    private Button btn_google_logout;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int REQ_SIGN_GOOGLE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 파싱을 위한 설정
        parsingStart();

        //gps
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Locationbutton = (Button) findViewById(R.id.locationButton);
        textView = (TextView) findViewById(R.id.locationText);

        //위도가 바뀔때마다 사용되는 Listioner
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                CheckLocation(location);
            }
        };

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
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
//


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        btn_google_login = (SignInButton) findViewById(R.id.google_login);
        btn_google_logout = (Button) findViewById(R.id.google_Logout);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_google_login.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_SIGN_GOOGLE);
        });
        btn_google_logout.setOnClickListener(v -> {
            googleSignInClient.signOut();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //여기서 resultCode가 0 or -1로 나오며 오류가 뜨는데 없어도 파이어베이스에 성공적으로 연동된다. 내 3시간 ㅇㄷ?
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

    private void parsingStart(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.data.go.kr/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        retrofitAPI.getData().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                data = response.body();
                Log.e("TEST","성공성공");
                Log.e("Test", data.getResponse().getBody().getItems().get(0).getLatitude()+"");
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("Test","실패실패");
                t.printStackTrace();
            }
        });
    }
}