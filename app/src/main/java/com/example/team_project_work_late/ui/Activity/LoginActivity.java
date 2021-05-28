package com.example.team_project_work_late.ui.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.application.DpstryDBHelper;
import com.example.team_project_work_late.application.LendDBHelper;
import com.example.team_project_work_late.application.NetRetrofit;
import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclDpstryData_responseBody_items;
import com.example.team_project_work_late.model.BcyclLendData;
import com.example.team_project_work_late.model.BcyclLendData_responseBody_items;
import com.example.team_project_work_late.ui.dialog.ProgressDialog;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private LendDBHelper lendDBHelper;
    private final String LEND_KEY_ENCODING = "GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D";
    private final String LEND_KEY_DECODING = "GyqT8YmA2REYKelkbYuNW0Ke+oU7ArbS5pBRj4Dj2wj5mm1/mz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ==";
    private DpstryDBHelper dpstryDBHelper;
    private final String DPSTRY_KEY_ENCODING = "GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D";
    private final String DPSTRY_KEY_DECODING = "GyqT8YmA2REYKelkbYuNW0Ke+oU7ArbS5pBRj4Dj2wj5mm1/mz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ==";


    private boolean parse_Lend = false;
    private boolean parse_Dpstry = false;
    private ProgressDialog customProgressDialog;

    private ImageButton startButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int REQ_SIGN_GOOGLE = 100;

    private View decorView;
    private int	uiOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //소프트바 숨기기//
        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );
        //소프트바 숨기기 끝//

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
        lendDBHelper = new LendDBHelper(getApplicationContext());
        dpstryDBHelper = new DpstryDBHelper(getApplicationContext());

        if (lendDBHelper.getLendDataList().size() == 0 && dpstryDBHelper.getDpstryDataList().size() == 0){
            // 파싱하는 동안 다른거 못하게 막음
            customProgressDialog = new ProgressDialog(this);
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            customProgressDialog.show();
            parsingStart();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        startButton = (ImageButton) findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQ_SIGN_GOOGLE);
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
                            Toast.makeText(LoginActivity.this, "구글 및 파이어베이스 연결 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
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
                    for (BcyclLendData_responseBody_items item: response.body().getBcyclLendDataresponse().getBcyclLendDataresponseBody().getItems()){
                        lendDBHelper.insertLendItem(item);
                    }
                    parsingLend(serviceKey, pageNo + 1);
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
                    for (BcyclDpstryData_responseBody_items item: response.body().getBcyclDpstryData_response().getBcyclDpstryData_responseBody().getItems()){
                        dpstryDBHelper.insertDpstryItem(item);
                    }
                    parsingDpstry(serviceKey, pageNo + 1);
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
            customProgressDialog.dismiss();
        }
    }
}