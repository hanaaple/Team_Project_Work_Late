package com.example.team_project_work_late.application;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.team_project_work_late.model.BookMarkItem;

import java.util.ArrayList;

/*
 * @FileName DBHelper
 * @date 21.04.12
 * @made 전희훈
 * @role 대여소 즐겨찾기 리스트 SQLite db
 * @inheritance SQLiteOpenHelper
 * @field DB_VERSION ( static final ), DB_NAME ( static final )
 * @constructor DBHelper(Context context)
 * @method getBookMarkList(), InsertRental( BookMarkItem 에 들어갈 변수 ), deleteRental(int id)
 * @overrideMethod onCreate(SQLiteDatabase db), onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
 */

public class BMDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "BookMarkList.db";

    public BMDBHelper(@Nullable Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터 베이스가 생성이 될 때 호출
        // 데이터베이스 -> 테이블 -> 칼럼 -> 값
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS BookMarkList (id INTEGER PRIMARY KEY AUTOINCREMENT," +
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
                        "airInjectorYn TEXT NOT NULL," +
                        "repairStandY TEXT NOT NULL," +
                        "phoneNumber TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT 문 ( 즐겨찾기 목록 조회 )
    public ArrayList<BookMarkItem> getBookMarkList(){
        ArrayList<BookMarkItem> bookMarkList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BookMarkList ORDER BY bcyclLendNm DESC", null);
        if (cursor.getCount() != 0){
            // 조회된 데이터가 있을 때 내부 수행
            while (cursor.moveToNext()){
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
                String airInjectorYn = cursor.getString(cursor.getColumnIndex("airInjectorYn"));
                String repairStandY = cursor.getString(cursor.getColumnIndex("repairStandY"));
                String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));

                BookMarkItem bookMark = new BookMarkItem();
                bookMark.setBcyclLendNm(bcyclLendNm);
                bookMark.setBcyclLendSe(bcyclLendSe);
                bookMark.setRdnmadr(rdnmadr);
                bookMark.setLnmadr(lnmadr);
                bookMark.setLatitude(latitude);
                bookMark.setLongitude(longitude);
                bookMark.setOperOpenHm(operOpenHm);
                bookMark.setOperCloseHm(operCloseHm);
                bookMark.setRstde(rstde);
                bookMark.setChrgeSe(chrgeSe);
                bookMark.setBcyclUseCharge(bcyclUseCharge);
                bookMark.setAirInjectorYn(airInjectorYn);
                bookMark.setRepairStandY(repairStandY);
                bookMark.setPhoneNumber(phoneNumber);

                bookMarkList.add(bookMark);
            }
        }
        cursor.close();

        return bookMarkList;
    }


    // INSERT 문 ( 즐겨찾기 목록을 DB에 넣는다 )
    public void insertBookMark(String bcyclLendNm, String bcyclLendSe, String rdnmadr, String lnmadr,
                               String latitude, String longitude, String operOpenHm, String operCloseHm,
                               String rstde, String chrgeSe, String bcyclUseCharge, String airInjectorYn,
                               String repairStandY, String phoneNumber) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO BookMarkList (bcyclLendNm, bcyclLendSe, rdnmadr, lnmadr," +
                " latitude, longitude, operOpenHm, operCloseHm, rstde, chrgeSe, bcyclUseCharge, airInjectorYn, repairStandY, phoneNumber) " +
                "VALUES('"+ bcyclLendNm +"', '"+ bcyclLendSe +"', '"+ rdnmadr +"', '"+ lnmadr +"'," +
                "'"+ latitude +"', '"+ longitude +"', '"+ operOpenHm +"', '"+ operCloseHm +"'," + "'"+ rstde +"'," +
                "'"+ chrgeSe +"', '"+ bcyclUseCharge +"', '"+ airInjectorYn +"', '"+ repairStandY +"', '"+ phoneNumber +"');");
    }

    // DELETE 문 ( 즐겨찾기 목록 제거 )
    public void deleteRental(String bcyclLendNm){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM BookMarkList WHERE bcyclLendNm = '"+ bcyclLendNm +"'");
    }
}
