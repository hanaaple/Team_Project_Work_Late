package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/*
 * @FileName Body
 * @date 21.04.10
 * @made 전희훈
 * @role 대여소 json 파일의 Body
 * @method items, totalCount, numOfRows, pageNo 의 getter
 * @etc setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class Body {

    // Items
    // String bcyclLendNm, String bcyclLendSe, String rdnmadr, String lnmadr, double latitude
    // double longitude, String operOpenHm, String operCloseHm, String rstde, String chrgeSe
    // String bcyclUseCharge, int bcyclHoldCharge, int holderCo, String airInjectorYn, String airInjectorType
    // String repairStandY, String phoneNumber, String institutionNm, String referenceDate, String insttCode
    @SerializedName("items")
    private List<Items> items = new ArrayList<>();

    public List<Items> getItems() {
        return items;
    }

    // 모르겠음 (2021.04.10)
    @SerializedName("totalCount")
    private int totalCount;

    // 한 페이지 결과 수
    @SerializedName("numOfRows")
    private int numOfRows;

    // 페이지 번호
    @SerializedName("pageNo")
    private int pageNo;

    private void setItems(List<Items> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    private void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    private void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    private void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

}