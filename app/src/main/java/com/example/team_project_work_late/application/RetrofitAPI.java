package com.example.team_project_work_late.application;

import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclLendData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 * @FileName     RetrofitAPI
 * @madeDate     21.04.10
 * @update       21.05.06
 * @made         전희훈
 * @role         Retrofit2 사용을 위한 interface
 * @method       getLendData, getDpstryData
 */

public interface RetrofitAPI {

//    @GET("openapi/tn_pubr_public_bcycl_lend_api?serviceKey=GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D&pageNo=+0&numOfRows=100&type=json")
    @GET("openapi/tn_pubr_public_bcycl_lend_api")
    Call<BcyclLendData> getLendData(@Query("serviceKey")String key, @Query("pageNo")String pageNo, @Query("numOfRows")String numOfRows, @Query("type")String type);

//    int parseDpstry_pageNo = 0;
//    int parseDpstry_numOfRows = 100;

    //@GET("openapi/tn_pubr_public_bcycl_dpstry_api?serviceKey=GyqT8YmA2REYKelkbYuNW0Ke%2BoU7ArbS5pBRj4Dj2wj5mm1%2Fmz8VwfSVYh59ymnCYpvCd01Dqem1zImM4QfGyQ%3D%3D&pageNo="+parseDpstry_pageNo+"&numOfRows="+parseDpstry_numOfRows+"&type=json")
    @GET("openapi/tn_pubr_public_bcycl_dpstry_api")
    Call<BcyclDpstryData> getDpstryData(@Query("serviceKey")String key, @Query("pageNo")String pageNo, @Query("numOfRows")String numOfRows, @Query("type")String type);
}