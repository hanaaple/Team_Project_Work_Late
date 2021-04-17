package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

/*
 * @FileName Data
 * @date 21.04.10
 * @made 전희훈
 * @role 대여소 json 파일의 전체 Data
 * @method response 의 getter
 * @etc setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class Data {

    // response
    // Header header, Body body
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    private void setResponse(Response response) {
        this.response = response;
    }
}
