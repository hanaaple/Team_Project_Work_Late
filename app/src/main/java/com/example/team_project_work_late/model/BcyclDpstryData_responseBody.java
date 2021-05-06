package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * @FileName  BcyclDpstryData_responseBody
 * @madeDate  21.05.06
 * @update    21.05.06
 * @made      전희훈
 * @role      보관소 json 파일의 Body
 * @implement Serializable
 * @method    items, totalCount, numOfRows, pageNo 의 getter
 * @etc       setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclDpstryData_responseBody implements Serializable {

    @SerializedName("items")
    private List<BcyclDpstryData_responseBody_items> items = new ArrayList<>();

    // 모르겠음
    @SerializedName("totalCount")
    private int totalCount;

    // 한 페이지 결과 수
    @SerializedName("numOfRows")
    private int numOfRows;

    // 페이지 번호
    @SerializedName("pageNo")
    private int pageNo;

    public List<BcyclDpstryData_responseBody_items> getItems() {
        return items;
    }

    private void setItems(List<BcyclDpstryData_responseBody_items> items) {
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
