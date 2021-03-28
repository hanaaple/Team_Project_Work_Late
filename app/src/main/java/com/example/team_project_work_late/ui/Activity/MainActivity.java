package com.example.team_project_work_late.ui.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.service.SessionCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MainActivity extends AppCompatActivity {
    private Button btn_custom_login;
    private Button btn_custom_login_out;
    private Button btn_test;

    private SessionCallBack sessionCallBack = new SessionCallBack();
    private FirebaseAuth mAuth;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //토큰 가져오는 버튼
        btn_test = (Button) findViewById(R.id.button_test);
        //카카오톡 로그인하는 버튼
        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        //카카오톡 로그아웃하는 버튼
        btn_custom_login_out = (Button) findViewById(R.id.btn_custom_login_out);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        super.onStart();
        session = Session.getCurrentSession();
        session.addCallback(sessionCallBack);


        FirebaseUser currentUser =  mAuth.getCurrentUser();

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //파이어베이스 연결 디버그용도
                System.out.println(currentUser);


                //현재 방식은 로그인하여 token을 받아오는 방식으로 API에 체크할 필요가 없을 듯하다

                //AccessToken 받아오기 -성공
                //이후 파이어베이스 서버로 전송한다. - 아직 안함
                //그리고 kakao api에 토큰을 넘겨 정보를 받아오는지 체크 (받아올 경우 토큰이 동일한 경우이다)
                //성공할 경우 firebase admin sdk를 이용해 firebase auth에 user를 생성
                //생성된 user의 UID를 이용해 firebase custom token 생성 후 클라이언트에 반환
                //Firebase Auth에서 제공하는 signInWithCustomToken 메서드의 인자로 Custom Token을 넘겨 로그인을 처리한다.


                //카카오톡 토큰 정보 요청하는 메소드
                AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "토큰 정보 요청 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(AccessTokenInfoResponse result) {
                        System.out.println(result.toString());
                    }
                });
            }
        });

        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);
            }
        });

        btn_custom_login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserManagement.getInstance()
                .requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}