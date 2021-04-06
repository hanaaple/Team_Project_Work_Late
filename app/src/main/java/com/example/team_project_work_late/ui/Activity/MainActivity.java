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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.security.Key;
import java.security.interfaces.RSAKey;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import static io.jsonwebtoken.SignatureAlgorithm.RS256;
import static io.jsonwebtoken.SignatureAlgorithm.forSigningKey;


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
        FirebaseApp.initializeApp(this);

        //토큰 가져오는 버튼
        btn_test = (Button) findViewById(R.id.button_test);
        //카카오톡 로그인하는 버튼
        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        //카카오톡 로그아웃하는 버튼
        btn_custom_login_out = (Button) findViewById(R.id.btn_custom_login_out);

        mAuth = FirebaseAuth.getInstance();
    }






    public void log(AccessTokenInfoResponse kakaoResult){
        //AuthCredential credential = OAuthProvider.newCredentialBuilder("Kakao").setIdToken(Long.toString(kakaoResult.getUserId())).setAccessToken(Long.toString(kakaoResult.getUserId())).build();
        Date cur_time = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 3600);
        Date exp_time = calendar.getTime();
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", RS256);
        //claim.put("uid", UUID.fromString(Long.toString(kakaoResult.getUserId())).toString().replace("-",""));
        //claim.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        String token = Jwts.builder()
                .setHeader(header)
                .setIssuer("firebase-adminsdk-ek0z0@worklate-69638.iam.gserviceaccount.com")
                .setSubject("firebase-adminsdk-ek0z0@worklate-69638.iam.gserviceaccount.com")
                .setAudience("https://identitytoolkit.googleapis.com/google.identity.identitytoolkit.v1.IdentityToolkit")
                .setIssuedAt(cur_time)
                .setExpiration(exp_time)
                .setId(Long.toString(kakaoResult.getUserId()))
                .signWith(Keys.hmacShaKeyFor("\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDNqHCIJtUzUyc5\\n0FXEpGJUQvC811fHHdf+Bc34AJCSkTKNbKpaJjh8+sdxkYpFmgvPJZgh2w8pcRMr\\n4lYxLgPMLgMRKFIVjFquJPgTZfMapp/bQV+xerLeGncgTt5HjcdI3Xi+FAayFaUe\\nX5ZTf5rk9VtcK4c54KgcXqweX0/+nOSX5cCGefNPiIl2hSMT+KoyXbbElNQdl7dh\\nVVLgx1h7Y1C4OB9XbGl3wfeDULVZ4xS5Eh0z4+yTs0Cb6VtB334hJhrOmbn9lBLU\\nocNALTLQoRPbXTrNHFm7nwVxVRNbp+7HBm09TOTxm6PlsLHw5T8FPjBlzwjNDMv7\\njJgFWCXtAgMBAAECggEAG5XAxlpjFXfNVqFdp7se30t9S+8cfH5BxvZTWrUEr0wf\\nej29mcrvn7/peY+6erx/YgEaZ0whO/9JQYUh54XTB5OSYL5GSFKjpSEbT0rI8WWx\\nInEh20XocQcevGwnv3RCa0EdnW1FOqTRmYHbwYZnqddJMlM6V6aNFgUS1B4XtkBq\\nicM4US+KZ3OYnHyitRdTDBnNHlq+yVEGOc5Jw+5b+KcZc+m6VVGYM+Tmpv7Pr9GM\\nwtkqi26U79kvw+ikcpkT/PccY0bJ4OB9jMS0Trb3CfeL3f1NSKJN85y74g1Yy+JL\\nRgqCqXyCBDLaCWhE6YjB5cKltdY+nnxp7erwuNjGcQKBgQDsmKAB1+fw9cVY7P0k\\nvD8uMuS1qinl0iFsZ9h2V/eL/WECpPfFU8eKRLuBmWF+fQbjE2xyncy6tSO9Dd+L\\nIou9KnzpDt9LGuwRaGTsKuUf/dsmKmfa4wI5cQLnnWLNDblLeJL6JEGrz7QSt6En\\nnxPO/mOBYDdv3Klks4x7QOXvuQKBgQDehkL2Gr/cFYde1t98aB6X11E+9FvXY2RA\\nFnbNTAtXn7dP1jSm3eOWEsxf7fCXoeyWZhb4T5xgB8PfX/R0twKaEcGh29OAwv1S\\nVOr14eMgyhtVqDAQE9E3QcBQ088DCiW0JNsiQRjAG8c0kDVrT13tATt5ABbgv32s\\nnKX5gaO51QKBgQCjodNYMwj5QCGjJRTXKVLREuXXNr8Pccsn/JJbFu/gY/eKKqoq\\nUY059dtxALHLF5GBz1c71iNYJht3j3bB9byLsiz9ywloGlCWoYrbQ7d/7sR4mu+F\\nFWfebmjB47oHc6xppBSS6Cx7NYWnRFUy3/SFPq93NSJiPUzylrNcM0BUEQKBgHEJ\\nTLyNbAaVXRWdGxusHFZPhzLumDS6hXNUtfaleWGCfXDtxAM71d8nH3BfgwbTt0XT\\nCDoM3sedSi+PI7OiP40aFf1tmGvhISOQhWZv35uJbwc9D2UrW+yw1st4PMEzh2GS\\nE0q8PKFhuviFhM1FHNZG+PoHRJfYTjO/w0QSSaQdAoGBAKZuv3ALN90f8iETWyiP\\njqAKMup0Ldde0gKqajA5JCl3Qkcj8Tbo11tI366sy6Ogci9tPo9jW+fVJTlRrF9j\\nQOMFVPSF/h1xpMjm1ldphp0sZh60/pHjBN1T3pN4wqXeeVyAhU9zhj+GQ0DRRmHv\\nhsF1FgPxemDoaj26wMU9BwQ9\\n".getBytes()))
                .compact();
        //sign - 회원가입 or 로그인
        mAuth.signInWithCustomToken(token).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    System.out.println("파이어베이스 로그인 성공");
                } else {
                    System.out.println("파이어베이스 로그인 실패");
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//                            System.out.println("파이어베이스 OAuth 로그인 성공");
//                        }
//                        System.out.println(credential);
//                        System.out.println(mAuth);
//                        System.out.println(mAuth.getCurrentUser());
//                    }
//                });


        //익명로그인후 연결
//        mAuth.signInAnonymously()
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful())
//                            System.out.println("파이어베이스 익명 로그인 성공");
//                    }
//                });

//        AuthCredential credential = OAuthProvider.newCredentialBuilder("").build();
//        mAuth.getCurrentUser().linkWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            System.out.println("파이어베이스 로그인 성공");
//                        } else {
//                            System.out.println("파이어베이스 로그인 실패");
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });



//        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()) {
//                    FirebaseUser currentUser = task.getResult().getUser();
//                    System.out.println("파이어베이스 로그인 성공");
//                }else {
//                    System.out.println("파이어베이스 로그인 실패");
//                    Toast.makeText(MainActivity.this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public void onStart(){
        super.onStart();
        session = Session.getCurrentSession();
        session.addCallback(sessionCallBack);


        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        System.out.println(result);
                        log(result);
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
                //mAuth.signOut();
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