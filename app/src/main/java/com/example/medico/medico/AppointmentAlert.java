package com.example.medico.medico;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class AppointmentAlert extends AppCompatActivity {

    Button okBtn;
    int reso=R.raw.chec;
    MediaPlayer mp;

    private TextView alertTitle,alertDoc,alertLoaction,exitBtn;

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    ContentValues cv=new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_alert);

        alertTitle = findViewById(R.id.alertMedName);
        alertDoc = findViewById(R.id.alertShape);
        alertLoaction = findViewById(R.id.alertColor);
        exitBtn = (TextView) findViewById(R.id.alertExitBtn);

        okBtn = (Button) findViewById(R.id.alertTakeBtn);
        mp=MediaPlayer.create(getApplicationContext(),reso);
        mp.start();

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        String time = String.format("%02d:%02d", hour, min);

        Cursor cursor = db.rawQuery("select * from " + dbHelper.APPO_TABLE_NAME + " where " + dbHelper.appoCol6 + " = " +"\""+time+"\"", null);

        cursor.moveToFirst();
        alertTitle.setText(cursor.getString(cursor.getColumnIndex(dbHelper.appoCOL1)));
        alertDoc.setText(cursor.getString(cursor.getColumnIndex(dbHelper.appoCOL2)));
        alertLoaction.setText(cursor.getString(cursor.getColumnIndex(dbHelper.appoCOL4)));
        cursor.close();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppointmentAlert.this.finish();
            }
        });
    }
    @Override

    public void onDestroy() {

        super.onDestroy();

        mp.release();

    }
}
