package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * @FileName      BcyclLendData_responseHeader
 * @madeDate      21.04.10
 * @update        21.05.06
 * @made          전희훈
 * @role          대여소 json 파일 Header
 * @implement     Serializable
 * @method        resultCode, resultMsg, type 의 getter
 * * @etc         setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclLendData_responseHeader implements Serializable {

    // 결과 코드 ( 00 이 아닐 경우 오류 )
    @SerializedName("resultCode")
    private String resultCode;

    // 오류 메시지 ( 오류 아닐 경우 NORMAL_SERVICE )
    @SerializedName("resultMsg")
    private String resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    private void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    private void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    // 출력물 타입 ( xml, json 중 택 1 )
    @SerializedName("type")
    private String type;

}