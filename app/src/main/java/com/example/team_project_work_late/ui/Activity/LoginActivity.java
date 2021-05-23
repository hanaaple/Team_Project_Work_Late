package com.example.team_project_work_late.ui.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.application.NetRetrofit;
import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclDpstryData_responseBody_items;
import com.example.team_project_work_late.model.BcyclLendData;
import com.example.team_project_work_late.model.BcyclLendData_responseBody_items;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private List<BcyclLendData_responseBody_items> bcyclLendData;     // 대여소 파싱용 데아터
    private List<BcyclDpstryData_responseBody_items> bcyclDpstryData; // 보관소 파싱용 데이터

    private boolean parse_Lend = false;
    private boolean parse_Dpstry = false;
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
        //myRef.child("Review").child(mAuth.getUid()).child("Lend").child("번호1").setValue("Lend 내용1");
        //myRef.child("Review").child(mAuth.getUid()).child("Lend").child("번호2").setValue("Lend 내용2");


        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("내용").setValue("1번 불편");
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("점수").setValue(1);

        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("내용").setValue("2번 불편");
        myRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호2").child("점수").setValue(2);


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
        tempButton = (Button) findViewById(R.id.tempButton);

//        tempButton.setOnClickListener(v -> {
//            dialogView = (ConstraintLayout) View.inflate(LoginActivity.this, R.layout.dialog_review, null);
//
//
//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
//
//            dialogBuilder.setView(dialogView);
//            //dialogBuilder.set
//
//            AlertDialog a = dialogBuilder.show();
//
//            a.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            cancelButton = dialogView.findViewById(R.id.cancelButton);
//            cancelButton.setOnClickListener(v1 -> {
//                a.dismiss();
//                dialogView.removeView(dialogView);
//            });
//
//        });

        // 파싱을 위한 설정
        bcyclLendData = new ArrayList<>();
        bcyclDpstryData = new ArrayList<>();
        parsingStart();
        startButton = (ImageButton) findViewById(R.id.startButton);
        btn_google_logout = (Button) findViewById(R.id.google_Logout);


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
            btn_location.setEnabled(false);
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bcyclLendData", (Serializable) bcyclLendData);
            bundle.putSerializable("bcyclDpstryData", (Serializable) bcyclDpstryData);
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
                bcyclLendData.addAll(response.body().getBcyclLendDataresponse().getBcyclLendDataresponseBody().getItems());
                parse_Lend = true;
                parsingEnd();
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
                bcyclDpstryData.addAll(response.body().getBcyclDpstryData_response().getBcyclDpstryData_responseBody().getItems());
                parse_Dpstry = true;
                parsingEnd();
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
        if (parse_Lend && parse_Dpstry){
            btn_location.setVisibility(View.VISIBLE);
        }
    }
}