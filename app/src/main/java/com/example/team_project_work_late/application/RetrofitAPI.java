package com.example.team_project_work_late.application;

import com.example.team_project_work_late.model.Data;

import retrofit2.Call;
import retrofit2.http.GET;

/*
 * @FileName RetrofitAPI
 * @date 21.04.10
 * @made 전희훈
 * @role Retrofit2 사용을 위한 interface
 * @method getData().enqueue() 로 데이터 통신
 * @etc setter 의 경우 내용 수정이 불가능하게 private 설정
 */

public interface RetrofitAPI {
    @GET("openapi/tn_pubr_public_bcycl_lend_api?serviceKey=GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D&pageNo=0&numOfRows=100&type=json")
    Call<Data> getData();
}