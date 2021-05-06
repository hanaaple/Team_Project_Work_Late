package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * @FileName  BcyclDpstryData_response
 * @madeDate  21.05.06
 * @update    21.05.06
 * @made      전희훈
 * @role      보관소 json 파일의 Response
 * @method    header, body 의 getter
 * @implement Serializable
 * @etc       setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclDpstryData_response implements Serializable {

    // header
    // String resultCode, String resultMsg, String type
    @SerializedName("header")
    private BcyclDpstryData_responseHeader bcyclDpstryData_responseHeader;

    // body
    // List<Items> items, int totalCount, int numOfRows, int pageNo
    @SerializedName("body")
    private BcyclDpstryData_responseBody bcyclDpstryData_responseBody;

    public BcyclDpstryData_responseHeader getBcyclDpstryData_responseHeader() {
        return bcyclDpstryData_responseHeader;
    }

    private void setBcyclDpstryData_responseHeader(BcyclDpstryData_responseHeader bcyclDpstryData_responseHeader) {
        this.bcyclDpstryData_responseHeader = bcyclDpstryData_responseHeader;
    }

    public BcyclDpstryData_responseBody getBcyclDpstryData_responseBody() {
        return bcyclDpstryData_responseBody;
    }

    private void setBcyclDpstryData_responseBody(BcyclDpstryData_responseBody bcyclDpstryData_responseBody) {
        this.bcyclDpstryData_responseBody = bcyclDpstryData_responseBody;
    }

}
