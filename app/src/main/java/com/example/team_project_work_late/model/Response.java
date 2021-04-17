package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

/*
 * @FileName Response
 * @date 21.04.10
 * @made 전희훈
 * @role 대여소 json 파일의 Response
 * @method header, body 의 getter
 * @etc setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class Response {

    // header
    // String resultCode, String resultMsg, String type
    @SerializedName("header")
    private Header header;

    // body
    // List<Items> items, int totalCount, int numOfRows, int pageNo
    @SerializedName("body")
    private Body body;


    public Header getHeader() {
        return header;
    }

    private void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    private void setBody(Body body) {
        this.body = body;
    }
}