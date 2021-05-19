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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private boolean parse_one = false;
    private boolean pares_two = false;
    private Button btn_location;

    private ImageButton startButton;
    private DatabaseReference myRef;
    private Button tempButton;
    private Button btn_google_logout;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int REQ_SIGN_GOOGLE = 100;


//필요한 Arguments - 대여소인지 보관소인지(필요 없을 수도 있음) - 고유번호 - 평가목록(리뷰만) - 내용
    void prar() {


        //즐겨찾기 - Lend or Archive - user id - 해당 보관소 or 대여소의 고유번호 혹은 이름
        myRef.child("즐겨찾기").child(mAuth.getUid()).child("Lend").child("번호1").setValue(true);
        myRef.child("즐겨찾기").child(mAuth.getUid()).child("Lend").child("번호2").setValue(true);
        myRef.child("즐겨찾기").child(mAuth.getUid()).child("Archive").child("번호3").setValue(true);

        //불러올 때 이대로 사용하면 됨
        myRef.child("즐겨찾기").child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    // Lend 번호1 ~ 번호3의 내용
                    for (DataSnapshot data : task.getResult().child("Lend").getChildren()) {
                        System.out.println(data.getKey());
                    }
                    // Archive 번호1 ~ 번호3의 내용
                    for (DataSnapshot data : task.getResult().child("Archive").getChildren()) {
                        System.out.println(data.getValue().toString());
                    }
                }
            }
        });


        //리뷰 - Lend or Archive - user id - 해당 보관소 or 대여소의 고유번호 혹은 이름 - 내용
        myRef.child("Review").child(mAuth.getUid()).child("Lend").child("번호1").setValue("Lend 내용1");
        myRef.child("Review").child(mAuth.getUid()).child("Lend").child("번호2").setValue("Lend 내용2");

        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("평가 목록 : 비싼가요").setValue(1);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("평가 목록 : 형편없나요").setValue(1);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("평가 목록 : 제대로 관리 되고 있나요").setValue(1);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("평가 목록 : 좋은 자전거가 많나요").setValue(1);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("평가 목록 : 더럽나요").setValue(1);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("평가 작성").setValue("1번 불편");

        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("평가 목록 : 비싼가요").setValue(2);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("평가 목록 : 형편없나요").setValue(2);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("평가 목록 : 제대로 관리 되고 있나요").setValue(2);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("평가 목록 : 좋은 자전거가 많나요").setValue(2);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("평가 목록 : 더럽나요").setValue(2);
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("평가 작성").setValue("2번 불편");


        myRef.child("Review").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    // Lend 번호1 ~ 번호3의 내용
                    for (DataSnapshot data : task.getResult().child(mAuth.getUid()).child("Lend").getChildren()) {
                        System.out.println(data.getValue().toString());
                    }
                    // Archive 번호1 ~ 번호3의 내용
                    for (DataSnapshot data : task.getResult().child(mAuth.getUid()).child("Archive").getChildren()) {
                        System.out.println(data.getKey());
                        System.out.println(Integer.parseInt(data.child("평가 목록 : 비싼가요").getValue().toString()));
                        System.out.println(Integer.parseInt(data.child("평가 목록 : 형편없나요").getValue().toString()));
                        System.out.println(Integer.parseInt(data.child("평가 목록 : 제대로 관리 되고 있나요").getValue().toString()));
                        System.out.println(Integer.parseInt(data.child("평가 목록 : 좋은 자전거가 많나요").getValue().toString()));
                        System.out.println(Integer.parseInt(data.child("평가 목록 : 더럽나요").getValue().toString()));
                        System.out.println(data.child("평가 작성").getValue().toString());
                    }

                    //모든 리뷰에 접근
                   for(DataSnapshot data : task.getResult().getChildren()){
                       for(DataSnapshot temp : data.child("Archive").getChildren()){

                       }
                       for(DataSnapshot temp : data.child("Lend").getChildren()){
                           
                       }
                   }
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tempButton = (Button)findViewById(R.id.tempButton);
        tempButton.setOnClickListener(v ->{
            //데이터베이스 루트 얻기
            myRef = FirebaseDatabase.getInstance().getReference();
            prar();


            //외부에서 데이터베이스가 바뀔 경우 발생하는 listner - 다른 디바이스에서 Review 업데이트될 때 쓰면 좋을듯
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


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


        startButton.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_SIGN_GOOGLE);
        });

        btn_google_logout.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "구글 및 파이어베이스 연결 끊음", Toast.LENGTH_SHORT).show();
            System.out.println("구글 및 파이어베이스 연결 끊음");
            googleSignInClient.signOut();
            mAuth.signOut();
        });

        // 테스트용
        btn_location = findViewById(R.id.locationButton);
        btn_location.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bcyclLendData",bcyclLendData);
                bundle.putSerializable("bcyclDpstryData",bcyclDpstryData);
                intent.putExtras(bundle);
                startActivity(intent);
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
        } else {
            Toast.makeText(LoginActivity.this, "구글 로그인 실패", Toast.LENGTH_SHORT).show();
            System.out.println("구글 로그인 실패");
//            System.out.println(data);
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
                pares_two = true;
                parsingEnd();
                call.cancel();
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
                parse_one = true;
                parsingEnd();
                call.cancel();
                Log.e("TEST2","성공성공");
            }

            @Override
            public void onFailure(Call<BcyclDpstryData> call, Throwable t) {
                Log.e("Test2","실패실패");
                t.printStackTrace();
            }
        });
    }

    private void parsingEnd(){
        if (parse_one && pares_two)
            btn_location.setVisibility(View.VISIBLE);
    }
}