package com.example.team_project_work_late.application;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.team_project_work_late.model.BcyclLendData_responseBody_items;

import java.util.ArrayList;

public class LendDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "LendItem.db";

    public LendDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS LendList (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "bcyclLendNm TEXT NOT NULL," +
                        "bcyclLendSe TEXT NOT NUll," +
                        "rdnmadr TEXT NOT NULL," +
                        "lnmadr TEXT NOT NULL," +
                        "latitude TEXT NOT NULL," +
                        "longitude TEXT NOT NULL," +
                        "operOpenHm TEXT NOT NULL," +
                        "operCloseHm TEXT NOT NULL," +
                        "rstde TEXT NOT NULL," +
                        "chrgeSe TEXT NOT NULL," +
                        "bcyclUseCharge TEXT NOT NULL," +
                        "bcyclHoldCharge TEXT NOT NULL," +
                        "holderCo TEXT NOT NULL," +
                        "airInjectorYn TEXT NOT NULL," +
                        "airInjectorType TEXT NOT NULL," +
                        "repairStandY TEXT NOT NULL," +
                        "phoneNumber TEXT NOT NULL," +
                        "institutionNm TEXT NOT NULL," +
                        "referenceDate TEXT NOT NULL," +
                        "insttCode TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public ArrayList<BcyclLendData_responseBody_items> getLendDataList(){
        ArrayList<BcyclLendData_responseBody_items> lendList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LendList ORDER BY bcyclLendNm DESC", null);
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String bcyclLendNm = cursor.getString(cursor.getColumnIndex("bcyclLendNm"));
                String bcyclLendSe = cursor.getString(cursor.getColumnIndex("bcyclLendSe"));
                String rdnmadr = cursor.getString(cursor.getColumnIndex("rdnmadr"));
                String lnmadr = cursor.getString(cursor.getColumnIndex("lnmadr"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                String operOpenHm = cursor.getString(cursor.getColumnIndex("operOpenHm"));
                String operCloseHm = cursor.getString(cursor.getColumnIndex("operCloseHm"));
                String rstde = cursor.getString(cursor.getColumnIndex("rstde"));
                String chrgeSe = cursor.getString(cursor.getColumnIndex("chrgeSe"));
                String bcyclUseCharge = cursor.getString(cursor.getColumnIndex("bcyclUseCharge"));
                String bcyclHoldCharge = cursor.getString(cursor.getColumnIndex("bcyclHoldCharge"));
                String holderCo = cursor.getString(cursor.getColumnIndex("holderCo"));
                String airInjectorYn = cursor.getString(cursor.getColumnIndex("airInjectorYn"));
                String airInjectorType = cursor.getString(cursor.getColumnIndex("airInjectorType"));
                String repairStandY = cursor.getString(cursor.getColumnIndex("repairStandY"));
                String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                String institutionNm = cursor.getString(cursor.getColumnIndex("institutionNm"));
                String referenceDate = cursor.getString(cursor.getColumnIndex("referenceDate"));

                BcyclLendData_responseBody_items lendItem = new BcyclLendData_responseBody_items();
                lendItem.setBcyclLendNm(bcyclLendNm);
                lendItem.setBcyclLendSe(bcyclLendSe);
                lendItem.setRdnmadr(rdnmadr);
                lendItem.setLnmadr(lnmadr);
                lendItem.setLatitude(latitude);
                lendItem.setLongitude(longitude);
                lendItem.setOperOpenHm(operOpenHm);
                lendItem.setOperCloseHm(operCloseHm);
                lendItem.setRstde(rstde);
                lendItem.setChrgeSe(chrgeSe);
                lendItem.setBcyclUseCharge(bcyclUseCharge);
                lendItem.setBcyclHoldCharge(bcyclHoldCharge);
                lendItem.setHolderCo(holderCo);
                lendItem.setAirInjectorYn(airInjectorYn);
                lendItem.setAirInjectorType(airInjectorType);
                lendItem.setRepairStandY(repairStandY);
                lendItem.setPhoneNumber(phoneNumber);
                lendItem.setInstitutionNm(institutionNm);
                lendItem.setReferenceDate(referenceDate);

                lendList.add(lendItem);
            }
        }

        cursor.close();

        return lendList;
    }

    public ArrayList<BcyclLendData_responseBody_items> getLendDataList(double aLatitude, double aLongitude, double bLatitude, double bLongitude) {
        ArrayList<BcyclLendData_responseBody_items> lendList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LendList WHERE latitude BETWEEN '"+ aLatitude +"' AND '"+ bLatitude +"' AND longitude BETWEEN '"+ aLongitude +"' AND '"+ bLongitude +"'", null);
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                if (!latitude.isEmpty() && !longitude.isEmpty()){
                    double latitude2 = Double.parseDouble(latitude);
                    double longitude2 = Double.parseDouble(longitude);if ((latitude2 > aLatitude && latitude2 < bLatitude) && (longitude2 > aLongitude && longitude2 < bLongitude)){
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String bcyclLendNm = cursor.getString(cursor.getColumnIndex("bcyclLendNm"));
                        String bcyclLendSe = cursor.getString(cursor.getColumnIndex("bcyclLendSe"));
                        String rdnmadr = cursor.getString(cursor.getColumnIndex("rdnmadr"));
                        String lnmadr = cursor.getString(cursor.getColumnIndex("lnmadr"));
                        String operOpenHm = cursor.getString(cursor.getColumnIndex("operOpenHm"));
                        String operCloseHm = cursor.getString(cursor.getColumnIndex("operCloseHm"));
                        String rstde = cursor.getString(cursor.getColumnIndex("rstde"));
                        String chrgeSe = cursor.getString(cursor.getColumnIndex("chrgeSe"));
                        String bcyclUseCharge = cursor.getString(cursor.getColumnIndex("bcyclUseCharge"));
                        String bcyclHoldCharge = cursor.getString(cursor.getColumnIndex("bcyclHoldCharge"));
                        String holderCo = cursor.getString(cursor.getColumnIndex("holderCo"));
                        String airInjectorYn = cursor.getString(cursor.getColumnIndex("airInjectorYn"));
                        String airInjectorType = cursor.getString(cursor.getColumnIndex("airInjectorType"));
                        String repairStandY = cursor.getString(cursor.getColumnIndex("repairStandY"));
                        String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                        String institutionNm = cursor.getString(cursor.getColumnIndex("institutionNm"));
                        String referenceDate = cursor.getString(cursor.getColumnIndex("referenceDate"));

                        BcyclLendData_responseBody_items lendItem = new BcyclLendData_responseBody_items();
                        lendItem.setBcyclLendNm(bcyclLendNm);
                        lendItem.setBcyclLendSe(bcyclLendSe);
                        lendItem.setRdnmadr(rdnmadr);
                        lendItem.setLnmadr(lnmadr);
                        lendItem.setLatitude(latitude);
                        lendItem.setLongitude(longitude);
                        lendItem.setOperOpenHm(operOpenHm);
                        lendItem.setOperCloseHm(operCloseHm);
                        lendItem.setRstde(rstde);
                        lendItem.setChrgeSe(chrgeSe);
                        lendItem.setBcyclUseCharge(bcyclUseCharge);
                        lendItem.setBcyclHoldCharge(bcyclHoldCharge);
                        lendItem.setHolderCo(holderCo);
                        lendItem.setAirInjectorYn(airInjectorYn);
                        lendItem.setAirInjectorType(airInjectorType);
                        lendItem.setRepairStandY(repairStandY);
                        lendItem.setPhoneNumber(phoneNumber);
                        lendItem.setInstitutionNm(institutionNm);
                        lendItem.setReferenceDate(referenceDate);

                        lendList.add(lendItem);
                    }
                }
            }
        }

        cursor.close();


        return lendList;
    }

    public void insertLendItem(BcyclLendData_responseBody_items item){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO LendList (bcyclLendNm, bcyclLendSe, rdnmadr, lnmadr," +
                " latitude, longitude, operOpenHm, operCloseHm, rstde, chrgeSe, bcyclUseCharge, bcyclHoldCharge," +
                " holderCo, airInjectorYn, airInjectorType, repairStandY, phoneNumber, institutionNm, referenceDate, insttCode) " +
                "VALUES('"+ item.getBcyclLendNm() +"', '"+ item.getBcyclLendSe() +"', '"+ item.getRdnmadr() +"', '"+ item.getLnmadr() +"'," +
                "'"+ item.getLatitude() +"', '"+ item.getLongitude() +"', '"+ item.getOperOpenHm() +"', '"+ item.getOperCloseHm() +"'," + "'"+ item.getRstde() +"'," +
                "'"+ item.getChrgeSe() +"', '"+ item.getBcyclUseCharge() +"', '"+ item.getBcyclHoldCharge() +"', '"+ item.getHolderCo() +"' , '"+ item.getAirInjectorYn() +"', '"+ item.getAirInjectorType() +"'," +
                "'"+ item.getRepairStandY() +"', '"+ item.getPhoneNumber() +"', '"+ item.getInstitutionNm() +"', '"+ item.getReferenceDate() +"', '"+ item.getInsttCode() +"');");
    }

}
