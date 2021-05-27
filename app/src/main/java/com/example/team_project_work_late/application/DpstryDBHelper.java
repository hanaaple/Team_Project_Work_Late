package com.example.team_project_work_late.application;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.team_project_work_late.model.BcyclDpstryData_responseBody_items;

import java.util.ArrayList;

public class DpstryDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DpstryItem.db";

    public DpstryDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS DpstryList (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "dpstryNm TEXT NOT NULL," +
                        "rdnmadr TEXT NOT NULL," +
                        "lnmadr TEXT NOT NULL," +
                        "latitude TEXT NOT NULL," +
                        "longitude TEXT NOT NULL," +
                        "cstdyCo TEXT NOT NULL," +
                        "installationYear TEXT NOT NULL," +
                        "installationStle TEXT NOT NULL," +
                        "awningsYn TEXT NOT NULL," +
                        "airInjectorYn TEXT NOT NULL," +
                        "airInjectorType TEXT NOT NULL," +
                        "repairStandY TEXT NOT NULL," +
                        "phoneNumber TEXT NOT NULL," +
                        "institutionNm TEXT NOT NULL," +
                        "referenceDate TEXT NOT NULL," +
                        "instt_code TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public ArrayList<BcyclDpstryData_responseBody_items> getDpstryDataList(){
        ArrayList<BcyclDpstryData_responseBody_items> dpstryList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DpstryList ORDER BY dpstryNm DESC", null);
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String dpstryNm = cursor.getString(cursor.getColumnIndex("dpstryNm"));
                String rdnmadr = cursor.getString(cursor.getColumnIndex("rdnmadr"));
                String lnmadr = cursor.getString(cursor.getColumnIndex("lnmadr"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                String cstdyCo = cursor.getString(cursor.getColumnIndex("cstdyCo"));
                String installationYear = cursor.getString(cursor.getColumnIndex("installationYear"));
                String installationStle = cursor.getString(cursor.getColumnIndex("installationStle"));
                String awningsYn = cursor.getString(cursor.getColumnIndex("awningsYn"));
                String airInjectorYn = cursor.getString(cursor.getColumnIndex("airInjectorYn"));
                String airInjectorType = cursor.getString(cursor.getColumnIndex("airInjectorType"));
                String repairStandY = cursor.getString(cursor.getColumnIndex("repairStandY"));
                String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                String institutionNm = cursor.getString(cursor.getColumnIndex("institutionNm"));
                String referenceDate = cursor.getString(cursor.getColumnIndex("referenceDate"));

                BcyclDpstryData_responseBody_items dpstryItem = new BcyclDpstryData_responseBody_items();
                dpstryItem.setDpstryNm(dpstryNm);
                dpstryItem.setRdnmadr(rdnmadr);
                dpstryItem.setLnmadr(lnmadr);
                dpstryItem.setLatitude(latitude);
                dpstryItem.setLongitude(longitude);
                dpstryItem.setCstdyCo(cstdyCo);
                dpstryItem.setInstallationYear(installationYear);
                dpstryItem.setInstallationStle(installationStle);
                dpstryItem.setAwningsYn(awningsYn);
                dpstryItem.setAirInjectorYn(airInjectorYn);
                dpstryItem.setAirInjectorType(airInjectorType);
                dpstryItem.setRepairStandYn(repairStandY);
                dpstryItem.setPhoneNumber(phoneNumber);
                dpstryItem.setInstitutionNm(institutionNm);
                dpstryItem.setReferenceDate(referenceDate);

                dpstryList.add(dpstryItem);
            }
        }

        cursor.close();

        return dpstryList;
    }

    public ArrayList<BcyclDpstryData_responseBody_items> getDpstryDataList(double aLatitude, double aLongitude, double bLatitude, double bLongitude){
        ArrayList<BcyclDpstryData_responseBody_items> dpstryList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DpstryList WHERE latitude BETWEEN '"+ aLatitude +"' AND '"+ bLatitude +"' AND '"+ bLatitude +"' AND longitude BETWEEN '"+ aLongitude +"' AND '"+ bLongitude +"'", null);

        if (cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                if (!latitude.isEmpty() && !longitude.isEmpty()){
                    if ((Double.parseDouble(latitude) > aLatitude && Double.parseDouble(latitude) < bLatitude) && (Double.parseDouble(longitude) > aLongitude && Double.parseDouble(longitude) < bLongitude)){
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String dpstryNm = cursor.getString(cursor.getColumnIndex("dpstryNm"));
                        String rdnmadr = cursor.getString(cursor.getColumnIndex("rdnmadr"));
                        String lnmadr = cursor.getString(cursor.getColumnIndex("lnmadr"));
                        String cstdyCo = cursor.getString(cursor.getColumnIndex("cstdyCo"));
                        String installationYear = cursor.getString(cursor.getColumnIndex("installationYear"));
                        String installationStle = cursor.getString(cursor.getColumnIndex("installationStle"));
                        String awningsYn = cursor.getString(cursor.getColumnIndex("awningsYn"));
                        String airInjectorYn = cursor.getString(cursor.getColumnIndex("airInjectorYn"));
                        String airInjectorType = cursor.getString(cursor.getColumnIndex("airInjectorType"));
                        String repairStandY = cursor.getString(cursor.getColumnIndex("repairStandY"));
                        String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                        String institutionNm = cursor.getString(cursor.getColumnIndex("institutionNm"));
                        String referenceDate = cursor.getString(cursor.getColumnIndex("referenceDate"));

                        BcyclDpstryData_responseBody_items dpstryItem = new BcyclDpstryData_responseBody_items();
                        dpstryItem.setDpstryNm(dpstryNm);
                        dpstryItem.setRdnmadr(rdnmadr);
                        dpstryItem.setLnmadr(lnmadr);
                        dpstryItem.setLatitude(latitude);
                        dpstryItem.setLongitude(longitude);
                        dpstryItem.setCstdyCo(cstdyCo);
                        dpstryItem.setInstallationYear(installationYear);
                        dpstryItem.setInstallationStle(installationStle);
                        dpstryItem.setAwningsYn(awningsYn);
                        dpstryItem.setAirInjectorYn(airInjectorYn);
                        dpstryItem.setAirInjectorType(airInjectorType);
                        dpstryItem.setRepairStandYn(repairStandY);
                        dpstryItem.setPhoneNumber(phoneNumber);
                        dpstryItem.setInstitutionNm(institutionNm);
                        dpstryItem.setReferenceDate(referenceDate);

                        dpstryList.add(dpstryItem);
                    }
                }
            }
        }

        return dpstryList;
    }

    public void insertDpstryItem(BcyclDpstryData_responseBody_items item){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DpstryList (dpstryNm, rdnmadr, lnmadr," +
                " latitude, longitude, cstdyCo, installationYear, installationStle," +
                " awningsYn, airInjectorYn, airInjectorType, repairStandY, phoneNumber, institutionNm, referenceDate, instt_code) " +
                "VALUES('"+ item.getDpstryNm() +"', '"+ item.getRdnmadr() +"', '"+ item.getLnmadr() +"'," +
                "'"+ item.getLatitude() +"', '"+ item.getLongitude() +"', '"+ item.getCstdyCo() +"'," +
                "'"+ item.getInstallationYear() +"', '"+ item.getInstallationStle() +"', '"+ item.getAwningsYn() +"', '"+ item.getAirInjectorYn() +"', '"+ item.getAirInjectorType() +"'," +
                "'"+ item.getRepairStandYn() +"', '"+ item.getPhoneNumber() +"', '"+ item.getInstitutionNm() +"', '"+ item.getReferenceDate() +"', '"+ item.getInstt_code() +"');");
    }
}
