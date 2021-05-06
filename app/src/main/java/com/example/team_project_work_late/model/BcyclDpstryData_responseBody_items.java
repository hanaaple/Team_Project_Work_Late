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
 * @method    dpstryNm, rdnmadr, lnmadr, latitude, longitude, cstdyCo, installationYear, installationStle, awningsYn
 *            airInjectorYn, airInjectorType, repairStandYn, phoneNumber, institutionNm, referenceDate, instt_code
 * @etc       setter 의 경우 내용 수정이 불가능하게 private 설정
 */

public class BcyclDpstryData_responseBody_items implements Serializable {

    // 자전거 보관소 명
    @SerializedName("dpstryNm")
    private String dpstryNm;

    // 소재지 도로명 주소
    @SerializedName("rdnmadr")
    private String rdnmadr;

    // 소재지 지번 주소
    @SerializedName("lnmadr")
    private String lnmadr;

    // 위도
    @SerializedName("latitude")
    private String latitude;

    // 경도
    @SerializedName("longitude")
    private String longitude;

    // 보관대 수
    @SerializedName("cstdyCo")
    private String cstdyCo;

    // 설치년도
    @SerializedName("installationYear")
    private String installationYear;

    // 설치 형태
    @SerializedName("installationStle")
    private String installationStle;

    // 차양막 설치 여부
    @SerializedName("awningsYn")
    private String awningsYn;

    // 공기 주입기 비치 여부
    @SerializedName("airInjectorYn")
    private String airInjectorYn;

    // 공기 주입기 유형
    @SerializedName("airInjectorType")
    private String airInjectorType;

    // 수리대 설치 여부
    @SerializedName("repairStandYn")
    private String repairStandYn;

    // 관리 기관 전화번호
    @SerializedName("phoneNumber")
    private String phoneNumber;

    // 관리 기관 명
    @SerializedName("institutionNm")
    private String institutionNm;

    // 데이터 기준 일자
    @SerializedName("referenceDate")
    private String referenceDate;

    // 제공 기관 코드
    @SerializedName("instt_code")
    private String instt_code;

    public String getDpstryNm() {
        return dpstryNm;
    }

    private void setDpstryNm(String dpstryNm) {
        this.dpstryNm = dpstryNm;
    }

    public String getRdnmadr() {
        return rdnmadr;
    }

    private void setRdnmadr(String rdnmadr) {
        this.rdnmadr = rdnmadr;
    }

    public String getLnmadr() {
        return lnmadr;
    }

    private void setLnmadr(String lnmadr) {
        this.lnmadr = lnmadr;
    }

    public String getLatitude() {
        return latitude;
    }

    private void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    private void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCstdyCo() {
        return cstdyCo;
    }

    private void setCstdyCo(String cstdyCo) {
        this.cstdyCo = cstdyCo;
    }

    public String getInstallationYear() {
        return installationYear;
    }

    private void setInstallationYear(String installationYear) {
        this.installationYear = installationYear;
    }

    public String getInstallationStle() {
        return installationStle;
    }

    private void setInstallationStle(String installationStle) {
        this.installationStle = installationStle;
    }

    public String getAwningsYn() {
        return awningsYn;
    }

    private void setAwningsYn(String awningsYn) {
        this.awningsYn = awningsYn;
    }

    public String getAirInjectorYn() {
        return airInjectorYn;
    }

    private void setAirInjectorYn(String airInjectorYn) {
        this.airInjectorYn = airInjectorYn;
    }

    public String getAirInjectorType() {
        return airInjectorType;
    }

    private void setAirInjectorType(String airInjectorType) {
        this.airInjectorType = airInjectorType;
    }

    public String getRepairStandYn() {
        return repairStandYn;
    }

    private void setRepairStandYn(String repairStandYn) {
        this.repairStandYn = repairStandYn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInstitutionNm() {
        return institutionNm;
    }

    private void setInstitutionNm(String institutionNm) {
        this.institutionNm = institutionNm;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    private void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getInstt_code() {
        return instt_code;
    }

    private void setInstt_code(String instt_code) {
        this.instt_code = instt_code;
    }

}
