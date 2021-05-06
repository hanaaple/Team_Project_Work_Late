package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * @FileName  BcyclLendData_response
 * @madeDate  21.04.10
 * @update    21.05.06
 * @made      전희훈
 * @role      대여소 json 파일의 Response
 * @method    header, body 의 getter
 * @implement Serializable
 * @etc       setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclLendData_response implements Serializable {

    // header
    // String resultCode, String resultMsg, String type
    @SerializedName("header")
    private BcyclLendData_responseHeader bcyclLendDataresponseHeader;

    // body
    // List<Items> items, int totalCount, int numOfRows, int pageNo
    @SerializedName("body")
    private BcyclLendData_responseBody bcyclLendDataresponseBody;


    public BcyclLendData_responseHeader getBcyclLendDataresponseHeader() {
        return bcyclLendDataresponseHeader;
    }

    private void setBcyclLendDataresponseHeader(BcyclLendData_responseHeader bcyclLendDataresponseHeader) {
        this.bcyclLendDataresponseHeader = bcyclLendDataresponseHeader;
    }

    public BcyclLendData_responseBody getBcyclLendDataresponseBody() {
        return bcyclLendDataresponseBody;
    }

    private void setBcyclLendDataresponseBody(BcyclLendData_responseBody bcyclLendDataresponseBody) {
        this.bcyclLendDataresponseBody = bcyclLendDataresponseBody;
    }
}