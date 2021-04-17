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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {
    private Button Locationbutton;
    private TextView textView;



    void CheckLocation(Location location){
        double longitude = location.getLongitude();     //위도
        System.out.println(textView);
        double latitude = location.getLatitude();       //경도
        double altitude = location.getAltitude();       //고도
        textView.setText("위도 : " + longitude + "\n" +
                "경도 : " + latitude + "\n" +
                "고도 : " + altitude);
    }







    private SignInButton btn_google_login;
    private Button btn_google_logout;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int REQ_SIGN_GOOGLE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);;
        Locationbutton = (Button)findViewById(R.id.LocationButton);
        textView = (TextView)findViewById(R.id.LocationText);


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
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                    if(locationManager != null){
                        Log.d("GPSTracker", "LocationManger is Enable");
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location != null){
                            CheckLocation(location);
//                            System.out.println(location);
//                            System.out.println("요거임");
                        }
                    }
                        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
                }
            }
        });


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
    protected void onStart(){
        super.onStart();
        btn_google_login.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_SIGN_GOOGLE);
        });
        btn_google_logout.setOnClickListener(v ->{
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
                            Toast.makeText(MainActivity.this, "성공", Toast.LENGTH_SHORT).show();
                            System.out.println("성공");
                        } else {
                            System.out.println("실패");
                        }
                    }
                });
    }


//    private void ResultLogin(GoogleSignInAccount account) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //파이어베이스 연동 성공
//                            Toast.makeText(MainActivity.this, "파이어베이스 연동 성공", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(MainActivity.this, "파이어베이스 연동 실패", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

//    @Override
//    public void onStart(){
//        super.onStart();


//        btn_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btn_custom_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                //startActivityForResult(intent, REQ_SIGN_GOOGLE);
//            }
//        });
//
//        btn_custom_login_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
}