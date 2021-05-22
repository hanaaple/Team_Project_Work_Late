package com.example.team_project_work_late.application;

import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclLendData;

import retrofit2.Call;
import retrofit2.http.GET;

/*
 * @FileName     RetrofitAPI
 * @madeDate     21.04.10
 * @update       21.05.06
 * @made         전희훈
 * @role         Retrofit2 사용을 위한 interface
 * @method       getLendData, getDpstryData
 */

public interface RetrofitAPI {


    @GET("openapi/tn_pubr_public_bcycl_lend_api?serviceKey=GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D&pageNo=+0&numOfRows=100&type=json")
    Call<BcyclLendData> getLendData();

    int parseDpstry_pageNo = 0;
    int parseDpstry_numOfRows = 100;

    @GET("openapi/tn_pubr_public_bcycl_dpstry_api?serviceKey=GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D&pageNo="+parseDpstry_pageNo+"&numOfRows="+parseDpstry_numOfRows+"&type=json")
    Call<BcyclDpstryData> getDpstryData();
}