package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * @FileName  BcyclLendData
 * @madeDate  21.04.10
 * @update    21.05.06
 * @made      전희훈
 * @role      대여소 json 파일의 전체 Data
 * @method    response 의 getter
 * @implement Serializable
 * @etc       setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclLendData implements Serializable {

    // response
    // Header header, Body body
    @SerializedName("response")
    private BcyclLendData_response bcyclLendDataresponse;

    public BcyclLendData_response getBcyclLendDataresponse() {
        return bcyclLendDataresponse;
    }

    private void setBcyclLendDataresponse(BcyclLendData_response bcyclLendDataresponse) {
        this.bcyclLendDataresponse = bcyclLendDataresponse;
    }
}
