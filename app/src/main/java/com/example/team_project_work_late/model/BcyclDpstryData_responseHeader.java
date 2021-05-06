package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * @FileName      BcyclDpstryData_responseHeader
 * @madeDate      21.05.06
 * @update        21.05.06
 * @made          전희훈
 * @role          보관소 json 파일 Header
 * @implement     Serializable
 * @method        resultCode, resultMsg, type 의 getter
 * * @etc         setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclDpstryData_responseHeader implements Serializable {

    @SerializedName("resultCode")
    private String resultCode;

    // 오류 메시지 ( 오류 아닐 경우 NORMAL_SERVICE )
    @SerializedName("resultMsg")
    private String resultMsg;

    @SerializedName("type")
    private String type;

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

}
