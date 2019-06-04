package com.example.medico.medico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";

//Variable for Medicine Table
    public static final String DATABASE_NAME = "MedicoList.db";
    public static final String TABLE_NAME = "med_table";
    public static final String COL0 = "_id";
    public static final String COL1 = "med_name";
    public static final String COL2 = "time1";
    public static final String COL3 = "time2";
    public static final String COL4 = "time3";
    public static final String COL5 = "time4";
    public static final String COL6 = "s_date";
    public static final String COL7 = "duration";
    public static final String COL8 = "intervals";
    public static final String COL9 = "color";
    public static final String COL10 = "shape";
    public static final String COL11 = "doc_name";
    public static final String COL12 = "ins";

//variables for bmi table
    private static final String BMI_TABLE_NAME = "bmi_table";
    private static final String bmiCOL0 = "_id";
    private static final String bmiCOL1 = "weight";
    private static final String bmiCOL2 = "height";
    private static final String bmiCOL3 = "bmi";

//Variables for Appointment Table
    public static final String APPO_TABLE_NAME = "appo_table";
    public static final String appoCOL0 = "_id";
    public static final String appoCOL1 = "appo_title";
    public static final String appoCOL2 = "appo_doc_name";
    public static final String appoCOL3 = "appo_date";
    public static final String appoCOL4 = "appo_location";
    public static final String appoCOL5 = "appo_note";
    public static final String appoCol6 = "appo_notify_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Med Table
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT," + COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT," + COL5 + " TEXT," + COL6 + " TEXT," + COL7 + " INTEGER," + COL8 + " INTEGER," + COL9 + " TEXT," + COL10 + " TEXT," + COL11 + " TEXT," + COL12 + " TEXT)";
        db.execSQL(createTable);

        //create bmi table
        String createBMITable = "CREATE TABLE " + BMI_TABLE_NAME + " (" + bmiCOL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                bmiCOL1 + " TEXT, " + bmiCOL2 + " TEXT, " + bmiCOL3 + " TEXT)";
        db.execSQL(createBMITable);

        //Create Appointment Table
        String createAPPOTable = "CREATE TABLE " + APPO_TABLE_NAME + " (" + appoCOL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                appoCOL1 + " TEXT," + appoCOL2 + " TEXT," + appoCOL3 + " TEXT," + appoCOL4 + " TEXT," + appoCOL5 + " TEXT," + appoCol6 + " TEXT)";
        db.execSQL(createAPPOTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Returns all the data from database
     * @return
     */

    // bmi methods
    public boolean addBmiData(String weight,String height,String bmi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(bmiCOL1,weight);
        contentValues.put(bmiCOL2,height);
        contentValues.put(bmiCOL3,bmi);
        Log.d(TAG, "addData: Adding " + weight + " to " + BMI_TABLE_NAME);

        long result = db.insert(BMI_TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getBmiData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + BMI_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAppoData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + APPO_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}