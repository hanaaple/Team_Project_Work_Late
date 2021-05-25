package com.example.team_project_work_late.ui.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private List<BcyclLendData_responseBody_items> bcyclLendData;     // 대여소 파싱용 데아터
    private final String LEND_KEY_ENCODING = "GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D";
    private final String LEND_KEY_DECODING = "GyqT8YmA2REYKelkbYuNW0Ke+oU7ArbS5pBRj4Dj2wj5mm1/mz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ==";
    private List<BcyclDpstryData_responseBody_items> bcyclDpstryData; // 보관소 파싱용 데이터
    private final String DPSTRY_KEY_ENCODING = "GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D";
    private final String DPSTRY_KEY_DECODING = "GyqT8YmA2REYKelkbYuNW0Ke+oU7ArbS5pBRj4Dj2wj5mm1/mz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ==";


    private boolean parse_Lend = false;
    private boolean parse_Dpstry = false;
    private Button btn_location;

    private ImageButton startButton;
    private Button btn_google_logout;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int REQ_SIGN_GOOGLE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(LoginActivity.this, "위치정보 권한 허용", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(LoginActivity.this, "위치정보 권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("구글 로그인을 하기 위해서는 위치 접근 권한이 필요합니다")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 설정할 수 있습니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check(); //권한체크

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
        btn_location.setOnClickListener(v -> {
            btn_location.setEnabled(false);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("bcyclLendData", (Serializable) bcyclLendData);
            intent.putExtra("bcyclDpstryData", (Serializable) bcyclDpstryData);
            startActivity(intent);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        if (task.isSuccessful()) {
            Log.d("구글", "로그인 성공");
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
            }
        } else {
            Log.e("구글", "로그인 실패");
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("파이어베이스", "연동 성공");
                        } else {
                            Log.e("파이어베이스", "연동 실패");
                        }
                    }
                });
    }

    private void parsingStart() {
        // FAILED BINDER TRANSACTION 오류로 인한 대여소 200개, 보관소 200개 고정
        parsingLend(LEND_KEY_DECODING, 0);
        parsingDpstry(LEND_KEY_DECODING, 0);
    }

    private void parsingLend(String serviceKey, int pageNo) {
        NetRetrofit.getInstance().getAPI().getLendData(serviceKey, String.valueOf(pageNo), "100", "json").enqueue(new Callback<BcyclLendData>() {
            @Override
            public void onResponse(Call<BcyclLendData> call, Response<BcyclLendData> response) {
                if (response.body().getBcyclLendDataresponse().getBcyclLendDataresponseHeader().getResultCode().equals("00")) {
                    bcyclLendData.addAll(response.body().getBcyclLendDataresponse().getBcyclLendDataresponseBody().getItems());
                    if (pageNo < 1) {
                        parsingLend(serviceKey, pageNo + 1);
                    } else {
                        parse_Lend = true;
                        parsingEnd();
                    }
                } else if (response.body().getBcyclLendDataresponse().getBcyclLendDataresponseHeader().getResultCode().equals("03")) {
                    parse_Lend = true;
                    parsingEnd();
                    Log.e("대여소", "파싱완료");
                } else if (response.body().getBcyclLendDataresponse().getBcyclLendDataresponseHeader().getResultCode().equals("30")) {
                    if (serviceKey.equals(LEND_KEY_DECODING)) {
                        parsingLend(LEND_KEY_ENCODING, pageNo);
                    } else {
                        parsingLend(LEND_KEY_DECODING, pageNo);
                    }
                } else {
                    Log.e("TAG", response.body().getBcyclLendDataresponse().getBcyclLendDataresponseHeader().getResultCode());
                }
            }

            @Override
            public void onFailure(Call<BcyclLendData> call, Throwable t) {
                Log.e("Test", "실패실패");
                t.printStackTrace();
            }
        });
    }

    private void parsingDpstry(String serviceKey, int pageNo) {
        NetRetrofit.getInstance().getAPI().getDpstryData(serviceKey, String.valueOf(pageNo), "100", "json").enqueue(new Callback<BcyclDpstryData>() {
            @Override
            public void onResponse(Call<BcyclDpstryData> call, Response<BcyclDpstryData> response) {
                if (response.body().getBcyclDpstryData_response().getBcyclDpstryData_responseHeader().getResultCode().equals("00")) {
                    bcyclDpstryData.addAll(response.body().getBcyclDpstryData_response().getBcyclDpstryData_responseBody().getItems());
                    if (pageNo < 1) {
                        parsingDpstry(serviceKey, pageNo + 1);
                    } else {
                        parse_Dpstry = true;
                        parsingEnd();
                    }
                } else if (response.body().getBcyclDpstryData_response().getBcyclDpstryData_responseHeader().getResultCode().equals("03")) {
                    parse_Dpstry = true;
                    parsingEnd();
                    Log.e("보관소", "파싱완료");
                } else if (response.body().getBcyclDpstryData_response().getBcyclDpstryData_responseHeader().getResultCode().equals("30")) {
                    if (serviceKey.equals(DPSTRY_KEY_DECODING)) {
                        parsingDpstry(DPSTRY_KEY_ENCODING, pageNo);
                    } else {
                        parsingDpstry(DPSTRY_KEY_DECODING, pageNo);
                    }

                } else {
                    Log.e("TAG", response.body().getBcyclDpstryData_response().getBcyclDpstryData_responseHeader().getResultCode());
                }
            }

            @Override
            public void onFailure(Call<BcyclDpstryData> call, Throwable t) {
                Log.e("Test2", "실패실패");
                t.printStackTrace();
            }
        });
    }

    private void parsingEnd() {
        if (parse_Lend && parse_Dpstry) {
            btn_location.setVisibility(View.VISIBLE);
        }
    }
}