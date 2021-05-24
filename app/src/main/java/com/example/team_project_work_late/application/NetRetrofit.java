package com.example.team_project_work_late.application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * @FileName  NetRetrofit
 * @madeDate  21.05.06
 * @update    21.05.06
 * @made      전희훈
 * @role      네트워크 통신용 싱글톤
 * @method    getInstance, getAPI
 * */

public class NetRetrofit {

    private NetRetrofit(){}

    private static class NetRetrofitInner{
        // NetRetrofit 클래스 초기화 과정에서 JVM 이 Thread-Safe 하게 instance 를 생성
        public static final NetRetrofit INSTANCE = new NetRetrofit();
    }

    // NetRetrofit 의 instance 에 접근하여 반환
    public static NetRetrofit getInstance(){
        return NetRetrofitInner.INSTANCE;
    }

//    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(1, TimeUnit.MINUTES)
//            .readTimeout(1, TimeUnit.MINUTES)
//            .writeTimeout(1, TimeUnit.MINUTES)
//            .build();

    private final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://api.data.go.kr/")
//            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
            .build();

    private final RetrofitAPI RETROFITAPI = RETROFIT.create(RetrofitAPI.class);

    public RetrofitAPI getAPI() {
        return RETROFITAPI;
    }

}
