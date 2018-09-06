package com.hanyutech.hanyuiot01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingDBHelper extends SQLiteOpenHelper{
    //Database名稱
    public final static String DATABASE_NAME="Setting.db";
    //Database版本
    public final static int DATASE_VERSION=1;

    public SettingDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " +"SETTING" +
                "(_id INTEGER PRIMARY KEY  NOT NULL," +
                "_PM INTEGER," +
                "_HT INTEGER," +
                "_GPS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insert(int PM,int HT,int GPS){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("_PM",PM);
        cv.put("_HT",HT);
        cv.put("_GPS",GPS);
        return db.insert("SETTING",null,cv);
    }
    public long updatePM(int PM){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("_PM",PM);
        return db.update("SETTING",cv,"_id=1",null);
    }
    public long updateHT(int HT){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("_HT",HT);
        return db.update("SETTING",cv,"_id=1",null);
    }
    public long updateGPS(int GPS){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("_GPS",GPS);
        return db.update("SETTING",cv,"_id=1",null);
    }
    public Cursor selectfromID(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("SETTING",null,"_id = "+Integer.toString(ID),null,null,null,null);
        return cursor;
    }
}
