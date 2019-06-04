package com.example.medico.medico;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class appointmentAdd extends AppCompatActivity {

    private EditText appoTitle,appoDocName,appoLocation,appoNote;
    private TextView appoDate,notifyTime;
    private Button saveBtn;
    private Switch remSwitch;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private String sTitle,sDocName,sLocation,sNote,sDate,sNotifyTime;

//DataBase Variable
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    ContentValues cv=new ContentValues();

//Alarm Variable
    Calendar myCal=Calendar.getInstance();
    Calendar myCal1=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add);

//Variable Declaration
    dbHelper = new DatabaseHelper(this);
    db = dbHelper.getWritableDatabase();

//Variable Declare
        appoTitle = (EditText) findViewById(R.id.appointmentTitle);
        appoDocName = (EditText) findViewById(R.id.appoDoctorsName);
        appoLocation = (EditText) findViewById(R.id.appointmentLocation);
        appoNote = (EditText) findViewById(R.id.appointmentNote);
        appoDate = (TextView) findViewById(R.id.appointmentDate);
        saveBtn = (Button) findViewById(R.id.addAppointmentBtn);
        notifyTime = (TextView) findViewById(R.id.appointmentNotifyTime);
        remSwitch = (Switch) findViewById(R.id.appoAlarmSwitch);

        notifyTime.setVisibility(View.GONE);


//for hiding soft keypad
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//code for date selector
        appoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(cal.YEAR);
                int month = cal.get(cal.MONTH);
                int day = cal.get(cal.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(appointmentAdd.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String monthText;
               // month = month+1;
                if(month == 0)
                    monthText = "Jan";
                else if(month == 1 )
                    monthText = "Feb";
                else if(month == 2 )
                    monthText = "Mar";
                else if(month == 3 )
                    monthText = "Apr";
                else if(month == 4 )
                    monthText = "May";
                else if(month == 5 )
                    monthText = "Jun";
                else if(month == 6 )
                    monthText = "Jul";
                else if(month == 7 )
                    monthText = "Aug";
                else if(month == 8 )
                    monthText = "Sept";
                else if(month == 9 )
                    monthText = "Oct";
                else if(month == 10 )
                    monthText = "Nov";
                else
                    monthText = "Dec";
                String date = monthText +" "+day+", "+year;
                appoDate.setText(date);

                myCal.set(Calendar.MONTH, month);
                myCal.set(Calendar.DAY_OF_MONTH,day);
                myCal.set(Calendar.YEAR, year);

                myCal1.set(Calendar.MONTH, month);
                myCal1.set(Calendar.DAY_OF_MONTH,day);
                myCal1.set(Calendar.YEAR, year);
            }
        };
//for displaying current date
        String currDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        appoDate.setText(currDate);

//Setting Default time for Notification

        myCal1.set(Calendar.HOUR_OF_DAY,9);
        myCal1.set(Calendar.MINUTE, 00);
        myCal1.set(Calendar.SECOND, 00);


        remSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    notifyTime.setVisibility(View.VISIBLE);
                }
                else{
                    notifyTime.setVisibility(View.GONE);
                }
            }
        });

        notifyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(appointmentAdd.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        notifyTime.setText(time);
                        myCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCal.set(Calendar.MINUTE, minute);
                        myCal.set(Calendar.SECOND, 00);

                    }
                },hour,min,true);
                timePickerDialog.show();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sTitle = appoTitle.getText().toString();
                cv.put(dbHelper.appoCOL1,sTitle);

                sDocName = appoDocName.getText().toString();
                cv.put(dbHelper.appoCOL2,sDocName);

                sDate = appoDate.getText().toString();
                cv.put(dbHelper.appoCOL3,sDate);


                if(remSwitch.isChecked()==true){
                    sNotifyTime = notifyTime.getText().toString();
                }else{
                    sNotifyTime = "No Alarm";
                }

                cv.put(dbHelper.appoCol6,sNotifyTime);

                if(appoLocation.length() != 0){
                    sLocation = appoLocation.getText().toString();
                    cv.put(dbHelper.appoCOL4,sLocation);
                }else{
                    cv.put(dbHelper.appoCOL4,"Not Defined");
                }


                if(appoNote.length() != 0){
                    sNote = appoNote.getText().toString();
                    cv.put(dbHelper.appoCOL5,sNote);
                }else{
                    cv.put(dbHelper.appoCOL5,"Not Defined");
                }


                if (appoTitle.length() != 0 && appoDocName.length()!= 0) {

                    long insertData=db.insert(dbHelper.APPO_TABLE_NAME,null,cv);

                    if (insertData==-1) {
                        toastMessage("Something went wrong!");
                    } else {
                        toastMessage("Data Successfully Inserted");
                        if(remSwitch.isChecked()==true)
                            setAlarm(myCal);
                        notifyMe(myCal1);
                    }

                    startActivity(new Intent(appointmentAdd.this,MainActivity.class));
                } else {
                    toastMessage("You must put a Medicine Name and a Doctor Name!");
                }
            }
        });

    }

    void notifyMe(Calendar cal)

    {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);

        String alertTitle = appoTitle.getText().toString();
        String appoDoc = appoDocName.getText().toString();
        String appoLoc = appoLocation.getText().toString();

        intent.putExtra(getString(R.string.alert_title), alertTitle);
        intent.putExtra(getString(R.string.appo_doc), appoDoc);
        intent.putExtra(getString(R.string.appo_location), appoLoc);

        final int i = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    void setAlarm(Calendar cal) {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(appointmentAdd.this, AppointmentAlarmReceiver.class);

        String alertTitle = appoTitle.getText().toString();
        intent.putExtra(getString(R.string.alert_title), alertTitle);
        final int i = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(appointmentAdd.this, i, intent, 0);

        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DATE, 1);
        }
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
