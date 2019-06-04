package com.example.medico.medico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class Alert extends Activity {
    MediaPlayer mp;
    int reso=R.raw.chec,missedCount,medStock;
    Button take,skip;

    private TextView alertMedName,alertShape,alertColor,alertInst,exitBtn;

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    ContentValues cv=new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        mp=MediaPlayer.create(getApplicationContext(),reso);
        mp.start();
        take = (Button) findViewById(R.id.alertTakeBtn);
        skip = (Button) findViewById(R.id.alertSkipBtn);
        alertMedName = findViewById(R.id.alertMedName);
        alertShape = findViewById(R.id.alertShape);
        alertColor = findViewById(R.id.alertColor);
        alertInst = findViewById(R.id.alertInstruction);
        exitBtn = (TextView) findViewById(R.id.alertExitBtn);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        String time = String.format("%02d:%02d", hour, min);

        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME + " where " + dbHelper.COL2 + " = " +"\""+time+"\" or " + dbHelper.COL3 +" = " +"\""+time+"\" or " +dbHelper.COL4 +" = " +"\""+time+"\" or " +dbHelper.COL5 +" = " +"\""+time+"\"", null);

            cursor.moveToFirst();
            alertMedName.setText(cursor.getString(cursor.getColumnIndex(dbHelper.COL1)));
            alertColor.setText(cursor.getString(cursor.getColumnIndex(dbHelper.COL9)));
            alertShape.setText(cursor.getString(cursor.getColumnIndex(dbHelper.COL10)));
            alertInst.setText(cursor.getString(cursor.getColumnIndex(dbHelper.COL12)));
            cursor.close();


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medStock();
                Alert.this.finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missedMed();
                Alert.this.finish();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert.this.finish();
            }
        });



    }
    @Override

    public void onDestroy() {

        super.onDestroy();

        mp.release();

    }
    public void missedMed() {
        SharedPreferences sharedPreferences = getSharedPreferences("medStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        missedCount = sharedPreferences.getInt("missedMeds",0);
        editor.putInt("missedMeds",missedCount+1);
        editor.commit();
    }
    public void medStock(){
        SharedPreferences sharedPreferences = getSharedPreferences("medStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        medStock = sharedPreferences.getInt("medStock",0);
        editor.putInt("medStock",medStock-1);
        editor.commit();
    }

}