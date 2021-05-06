package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * @FileName  BcyclDpstryData
 * @madeDate  21.05.06
 * @update    21.05.06
 * @made      전희훈
 * @role      보관소 json 파일의 전체 Data
 * @method    response 의 getter
 * @implement Serializable
 * @etc       setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclDpstryData implements Serializable {

    // response
    // Header header, Body body
    @SerializedName("response")
    private BcyclDpstryData_response bcyclDpstryData_response;

    public BcyclDpstryData_response getBcyclDpstryData_response() {
        return bcyclDpstryData_response;
    }

    private void setBcyclDpstryData_response(BcyclDpstryData_response bcyclDpstryData_response) {
        this.bcyclDpstryData_response = bcyclDpstryData_response;
    }

}