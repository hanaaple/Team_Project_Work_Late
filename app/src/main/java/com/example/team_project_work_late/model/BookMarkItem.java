package com.example.team_project_work_late.model;

public class BookMarkItem {

    private int id;
    private String bcyclLendNm;     // 자전거 대여소 명 (e.g. 다문화 지원센터)
    private String bcyclLendSe;     // 자전거 대여소 구분 (e.g. 무인 대여소)
    private String rdnmadr;         // 소재지 도로명 주 (e.g. 경기도 안산시 단원구 화정로 26)
    private String lnmadr;          // 소재지 지번 주 (e.g. 경기도 안산시 단원구 초지동 667-2)
    private String latitude;        // 위도 (e.g. 37.331170)
    private String longitude;       // 경도 (e.g. 126.815352)
    private String operOpenHm;      // 운영 시작 시간 (e.g. 00:00)
    private String operCloseHm;     // 운영 종료 시간 (e.g. 23:59)
    private String rstde;           // 휴무일 (e.g. 연중 무휴)
    private String chrgeSe;         // 요금 구분 (e.g. 유료)
    private String bcyclUseCharge;  // 자전거 이용 요금 (e.g. 년회원(12개월) 3만원 + 반기회원(6개월) 2만원 + 월회원(30일) 4천원 + 일회원(1일) 천원)
    private String airInjectorYn;   // 공기 주입기 비치 여부 (e.g. N)
    private String repairStandY;    // 수리대 설치 여부 (e.g. N)
    private String phoneNumber;     // 관리 기관 전화 번호 (e.g. 1544-6339)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getAirInjectorYn() {
        return airInjectorYn;
    }

    public void setAirInjectorYn(String airInjectorYn) {
        this.airInjectorYn = airInjectorYn;
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
}
