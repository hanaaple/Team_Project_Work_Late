package com.example.team_project_work_late.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * @FileName  BcyclLendData_responseBody_items
 * @madeDate  21.04.10
 * @update    21.05.06
 * @made      전희훈
 * @role      대여소 json 파일의 Body 속 items : ArrayList
 * @implement Serializable
 * @method    bcyclLendNm, bcyclLendSe, rdnmadr, lnmadr, latitude, longitude, operOpenHm, operCloseHm, rstde, chrgeSe, bcyclUseCharge,
 *            bcyclHoldCharge, holderCo, airInjectorYn, airInjectorType, repairStandY, phoneNumber, institutionNm, referenceDate, insttCode 의 getter
 * @etc       setter 의 경우 내용 수정이 불가능하게 private 설정
 * */

public class BcyclLendData_responseBody_items implements Serializable {

    // 자전거 대여소 명 (e.g. 다문화 지원센터)
    // SQLite db 포함
    @SerializedName("bcyclLendNm")
    private String bcyclLendNm;

    // 자전거 대여소 구분 (e.g. 무인 대여소)
    // SQLite db 포함
    @SerializedName("bcyclLendSe")
    private String bcyclLendSe;

    // 소재지 도로명 주 (e.g. 경기도 안산시 단원구 화정로 26)
    // SQLite db 포함
    @SerializedName("rdnmadr")
    private String rdnmadr;

    // 소재지 지번 주 (e.g. 경기도 안산시 단원구 초지동 667-2)
    // SQLite db 포함
    @SerializedName("lnmadr")
    private String lnmadr;

    // 위도 (e.g. 37.331170)
    // SQLite db 포함
    @SerializedName("latitude")
    private String latitude;

    // 경도 (e.g. 126.815352)
    // SQLite db 포함
    @SerializedName("longitude")
    private String longitude;

    // 운영 시작 시간 (e.g. 00:00)
    // SQLite db 포함
    @SerializedName("operOpenHm")
    private String operOpenHm;

    // 운영 종료 시간 (e.g. 23:59)
    // SQLite db 포함
    @SerializedName("operCloseHm")
    private String operCloseHm;

    // 휴무일 (e.g. 연중 무휴)
    // SQLite db 포함
    @SerializedName("rstde")
    private String rstde;

    // 요금 구분 (e.g. 유료)
    // SQLite db 포함
    @SerializedName("chrgeSe")
    private String chrgeSe;

    // 자전거 이용 요금 (e.g. 년회원(12개월) 3만원 + 반기회원(6개월) 2만원 + 월회원(30일) 4천원 + 일회원(1일) 천원)
    // SQLite db 포함
    @SerializedName("bcyclUseCharge")
    private String bcyclUseCharge;

    // 자전거 보유 대수 (e.g. 20)
    @SerializedName("bcyclHoldCharge")
    private String bcyclHoldCharge;

    // 거치대 수 (e.g. 20)
    @SerializedName("holderCo")
    private String holderCo;

    // 공기 주입기 비치 여부 (e.g. N)
    // SQLite db 포함
    @SerializedName("airInjectorYn")
    private String airInjectorYn;

    // 공기 주입기 유형 (e.g. "")
    @SerializedName("airInjectorType")
    private String airInjectorType;

    // 수리대 설치 여부 (e.g. N)
    // SQLite db 포함
    @SerializedName("repairStandY")
    private String repairStandY;

    // 관리 기관 전화 번호 (e.g. 1544-6339)
    // SQLite db 포함
    @SerializedName("phoneNumber")
    private String phoneNumber;

    // 관리 기관 명 (e.g. 안산도시공사)
    @SerializedName("institutionNm")
    private String institutionNm;

    // 데이터 기준 일자 (e.g. 2020-03-31)
    @SerializedName("referenceDate")
    private String referenceDate;

    // 제공 기관 코드 (e.g. 3930000)
    @SerializedName("insttCode")
    private String insttCode;

    public String getBcyclLendNm() {
        return bcyclLendNm;
    }

    public void setBcyclLendNm(String bcyclLendNm) {
        this.bcyclLendNm = bcyclLendNm;
    }

    public String getBcyclLendSe() {
        return bcyclLendSe;
    }

    public void setBcyclLendSe(String bcyclLendSe) {
        this.bcyclLendSe = bcyclLendSe;
    }

    public String getRdnmadr() {
        return rdnmadr;
    }

    public void setRdnmadr(String rdnmadr) {
        this.rdnmadr = rdnmadr;
    }

    public String getLnmadr() {
        return lnmadr;
    }

    public void setLnmadr(String lnmadr) {
        this.lnmadr = lnmadr;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOperOpenHm() {
        return operOpenHm;
    }

    public void setOperOpenHm(String operOpenHm) {
        this.operOpenHm = operOpenHm;
    }

    public String getOperCloseHm() {
        return operCloseHm;
    }

    public void setOperCloseHm(String operCloseHm) {
        this.operCloseHm = operCloseHm;
    }

    public String getRstde() {
        return rstde;
    }

    public void setRstde(String rstde) {
        this.rstde = rstde;
    }

    public String getChrgeSe() {
        return chrgeSe;
    }

    public void setChrgeSe(String chrgeSe) {
        this.chrgeSe = chrgeSe;
    }

    public String getBcyclUseCharge() {
        return bcyclUseCharge;
    }

    public void setBcyclUseCharge(String bcyclUseCharge) {
        this.bcyclUseCharge = bcyclUseCharge;
    }

    public String getBcyclHoldCharge() {
        return bcyclHoldCharge;
    }

    public void setBcyclHoldCharge(String bcyclHoldCharge) {
        this.bcyclHoldCharge = bcyclHoldCharge;
    }

    public String getHolderCo() {
        return holderCo;
    }

    public void setHolderCo(String holderCo) {
        this.holderCo = holderCo;
    }

    public String getAirInjectorYn() {
        return airInjectorYn;
    }

    public void setAirInjectorYn(String airInjectorYn) {
        this.airInjectorYn = airInjectorYn;
    }

    public String getAirInjectorType() {
        return airInjectorType;
    }

    public void setAirInjectorType(String airInjectorType) {
        this.airInjectorType = airInjectorType;
    }

    public String getRepairStandY() {
        return repairStandY;
    }

    public void setRepairStandY(String repairStandY) {
        this.repairStandY = repairStandY;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInstitutionNm() {
        return institutionNm;
    }

    public void setInstitutionNm(String institutionNm) {
        this.institutionNm = institutionNm;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getInsttCode() {
        return insttCode;
    }

    public void setInsttCode(String insttCode) {
        this.insttCode = insttCode;
    }
}