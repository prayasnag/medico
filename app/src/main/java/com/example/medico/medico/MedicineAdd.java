package com.example.medico.medico;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MedicineAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Data Input Variables
    private static final String TAG = "MedicineAdd";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd;
    private EditText medNameTxt,docNameTxt,medColorTxt,medShapeTxt,mDurationTxt,mIntervalTxt,medStockValue;
    private TextView mDisplayDate;
    private TextView mDisplayTime1,mDisplayTime2,mDisplayTime3,mDisplayTime4;
    private RadioGroup mRadioDurationGrp,mRadioIntervalGrp,mRadioInstGrp;
    private RadioButton mRadioDurationBtn,mRadioIntervalBtn,mRadioInstBtn;
    private DatePickerDialog.OnDateSetListener mDateSetListener,mTimeSetListener1,mTimeSetListener2,mTimeSetListener3,mTimeSetListener4;
    private String sMedName,time1,time2,time3,time4,sdate,color,shape,sDocName,instruction;
    private int duration,interval,medStock;

    //DATABSE Variables
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    ContentValues cv=new ContentValues();

//Alarm Variables
    Calendar myCal1=Calendar.getInstance();
    Calendar myCal2=Calendar.getInstance();
    Calendar myCal3=Calendar.getInstance();
    Calendar myCal4=Calendar.getInstance();


    //Intended Extras

      private String   selectedMedName,selectedDocName;
      private int selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);

    //Variables Declaration
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        medNameTxt = (EditText) findViewById(R.id.editMedName);     //Medicine Name
        btnAdd = (Button) findViewById(R.id.addMedBtn);             //Add Button
        mDatabaseHelper = new DatabaseHelper(this);         //DataBase Class

        mRadioDurationGrp = (RadioGroup)findViewById(R.id.radioDuration);
        mRadioIntervalGrp = (RadioGroup) findViewById(R.id.radioDays);
        mRadioInstGrp = (RadioGroup) findViewById(R.id.radioInst);

        mDurationTxt = (EditText) findViewById(R.id.MedDuration);
        mDurationTxt.setVisibility(View.GONE);
        mIntervalTxt = (EditText) findViewById(R.id.MedInterval);
        mIntervalTxt.setVisibility(View.GONE);

        docNameTxt = (EditText) findViewById(R.id.DoctorName);
        medColorTxt = (EditText) findViewById(R.id.MedColor);
        medShapeTxt = (EditText) findViewById(R.id.MedShape);

        medStockValue = findViewById(R.id.medStockInput);

    //for hiding duration and interval Edit Text
        mRadioDurationGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioDurationCont){
                    mDurationTxt.setVisibility(View.GONE);
                }
                else if(i==R.id.radioDurationDays){
                    mDurationTxt.setVisibility(View.VISIBLE);
                }
            }
        });

        mRadioIntervalGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioDaysEveryDay){
                    mIntervalTxt.setVisibility(View.GONE);
                }
                else if(i==R.id.radioDaysInterval){
                    mIntervalTxt.setVisibility(View.VISIBLE);
                }
            }
        });

        //for hiding soft keypad
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Start of code for spinner for reminder

        Spinner spinner = findViewById(R.id.spinnerRem);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.rem_time,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        String spinnerText = spinner.getSelectedItem().toString();

    //code for time selector
        //first
        mDisplayTime1 = (TextView)findViewById(R.id.timeOne);
//setting to default time
        myCal1.set(Calendar.HOUR_OF_DAY,8);
        myCal1.set(Calendar.MINUTE,00);
        myCal1.set(Calendar.SECOND,00);

        mDisplayTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime1.setText(time);
                        myCal1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCal1.set(Calendar.MINUTE, minute);
                        myCal1.set(Calendar.SECOND, 00);

                    }
                },hour,min,true);
                timePickerDialog.show();
            }
        });
        //two
        mDisplayTime2 = (TextView)findViewById(R.id.timeTwo);

        myCal2.set(Calendar.HOUR_OF_DAY,12);
        myCal2.set(Calendar.MINUTE,00);
        myCal2.set(Calendar.SECOND,00);

        mDisplayTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime2.setText(time);
                        myCal2.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCal2.set(Calendar.MINUTE, minute);
                        myCal2.set(Calendar.SECOND, 00);
                    }
                },hour,min,true);
                timePickerDialog.show();
            }
        });

        //three
        mDisplayTime3 = (TextView)findViewById(R.id.timeThree);

        myCal1.set(Calendar.HOUR_OF_DAY,18);
        myCal1.set(Calendar.MINUTE,00);
        myCal1.set(Calendar.SECOND,00);

        mDisplayTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime3.setText(time);
                        myCal3.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCal3.set(Calendar.MINUTE, minute);
                        myCal3.set(Calendar.SECOND, 00);
                    }
                },hour,min,true);
                timePickerDialog.show();
            }
        });

        //four
        mDisplayTime4 = (TextView)findViewById(R.id.timeFour);

        myCal1.set(Calendar.HOUR_OF_DAY,22);
        myCal1.set(Calendar.MINUTE,00);
        myCal1.set(Calendar.SECOND,00);

        mDisplayTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime4.setText(time);
                        myCal4.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCal4.set(Calendar.MINUTE, minute);
                        myCal4.set(Calendar.SECOND, 00);
                    }
                },hour,min,true);
                timePickerDialog.show();
            }
        });
        //code for date selector
        mDisplayDate = (TextView)findViewById(R.id.StartDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(cal.YEAR);
                int month = cal.get(cal.MONTH);
                int day = cal.get(cal.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MedicineAdd.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String monthText;
                //month = month+1;
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
                mDisplayDate.setText(date);
                myCal1.set(Calendar.MONTH, month);
                myCal1.set(Calendar.DAY_OF_MONTH,day);
                myCal1.set(Calendar.YEAR, year);

                myCal2.set(Calendar.MONTH, month);
                myCal2.set(Calendar.DAY_OF_MONTH,day);
                myCal2.set(Calendar.YEAR, year);

                myCal3.set(Calendar.MONTH, month);
                myCal3.set(Calendar.DAY_OF_MONTH,day);
                myCal3.set(Calendar.YEAR, year);

                myCal4.set(Calendar.MONTH, month);
                myCal4.set(Calendar.DAY_OF_MONTH,day);
                myCal4.set(Calendar.YEAR, year);
            }
        };


        //Data Collection Method

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sMedName = medNameTxt.getText().toString();
                cv.put(mDatabaseHelper.COL1,sMedName);

    //getting the data of time
                //ONE is always Visible
                time1 = mDisplayTime1.getText().toString();
                cv.put(mDatabaseHelper.COL2,time1);
                setAlarm(myCal1);
                //TWO
                if(mDisplayTime2.getVisibility()==View.VISIBLE){
                    time2 = mDisplayTime2.getText().toString();
                    cv.put(mDatabaseHelper.COL3,time2);
                    setAlarm(myCal2);
                }else{
                    time2 = "0";
                    cv.put(mDatabaseHelper.COL3,time2);
                }
                //THREE
                if(mDisplayTime3.getVisibility()==View.VISIBLE){
                    time3 = mDisplayTime3.getText().toString();
                    cv.put(mDatabaseHelper.COL4,time3);
                    setAlarm(myCal3);
                }else{
                    time3 = "0";
                    cv.put(mDatabaseHelper.COL4,time3);
                }
                //FOUR
                if(mDisplayTime4.getVisibility()==View.VISIBLE){
                    time4 = mDisplayTime4.getText().toString();
                    cv.put(mDatabaseHelper.COL5,time4);
                    setAlarm(myCal4);
                }else{
                    time4 = "0";
                    cv.put(mDatabaseHelper.COL5,time4);
                }

        //Getting date
                sdate = mDisplayDate.getText().toString();
                cv.put(mDatabaseHelper.COL6,sdate);

        //Getting Duration Period
                if(mDurationTxt.getVisibility()==View.VISIBLE){
                    duration = Integer.parseInt(mDurationTxt.getText().toString());
                    cv.put(mDatabaseHelper.COL7,duration);
                }else{
                    duration=0;
                    cv.put(mDatabaseHelper.COL7,duration);
                }

        //Getting Interval
                if(mIntervalTxt.getVisibility()==View.VISIBLE){
                    interval = Integer.parseInt(mIntervalTxt.getText().toString());
                    cv.put(mDatabaseHelper.COL8,interval);
                }else{
                    interval=0;
                    cv.put(mDatabaseHelper.COL8,interval);
                }

        //Shape And Color

                if(medShapeTxt.length() != 0){
                    shape = medShapeTxt.getText().toString();
                    cv.put(mDatabaseHelper.COL10,shape);
                }else{
                    shape = "Not Defined";
                    cv.put(mDatabaseHelper.COL10,shape);
                }

                if(medColorTxt.length() !=0){
                    color = medColorTxt.getText().toString();
                    cv.put(mDatabaseHelper.COL9,color);
                }else {
                    color = "Not Defined";
                    cv.put(mDatabaseHelper.COL9,color);
                }

        //Doctor Name
                if(docNameTxt.length() != 0){
                    sDocName = docNameTxt.getText().toString();
                    cv.put(mDatabaseHelper.COL11,sDocName);
                }else{
                    sDocName = "Not Defined";
                    cv.put(mDatabaseHelper.COL11,sDocName);
                }

        //Instruction
                int selectedId = mRadioInstGrp.getCheckedRadioButtonId();
                mRadioInstBtn = (RadioButton) findViewById(selectedId);
                instruction = mRadioInstBtn.getText().toString();
                cv.put(mDatabaseHelper.COL12,instruction);

        //add stock in shared pref

                SharedPreferences sharedPreferences = getSharedPreferences("medStats", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (medNameTxt.length() != 0 && medStockValue.length() !=0)
                {
                    medStock=sharedPreferences.getInt("medStock",0);
                    editor.putInt("medStock", Integer.parseInt(medStockValue.getText().toString())+medStock);
                    editor.commit();
                }

                if (medNameTxt.length() != 0 ) {

                   long insertData=db.insert(mDatabaseHelper.TABLE_NAME,null,cv);

                    if (insertData==-1) {
                        toastMessage("Something went wrong!");
                    } else {
                        toastMessage("Data Successfully Inserted");
                    }

                    startActivity(new Intent(MedicineAdd.this,MainActivity.class));
                } else {
                    toastMessage("You must put a Medicine Name!");
                }

            }
        });


    //Start of code for date picker

        String currDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView date = (TextView)findViewById(R.id.StartDate);
        date.setText(currDate);
        
    }



    void setAlarm(Calendar alarmCal){
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MedicineAdd.this, AlarmReceiver.class);

        String alertTitle = medNameTxt.getText().toString();
        intent.putExtra(getString(R.string.alert_title), alertTitle);
        final int i=(int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MedicineAdd.this, i, intent, 0);

        if (alarmCal.getTimeInMillis() < System.currentTimeMillis()) {
            alarmCal.add(Calendar.DATE, 1);
        }

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(),24*60*60*1000, pendingIntent);
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
     /*   String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
     */
        TextView timePicker1 = findViewById(R.id.timeOne);
        timePicker1.setVisibility(View.GONE);

        TextView timePicker2 = findViewById(R.id.timeTwo);
        timePicker2.setVisibility(View.GONE);

        TextView timePicker3 = findViewById(R.id.timeThree);
        timePicker3.setVisibility(View.GONE);

        TextView timePicker4 = findViewById(R.id.timeFour);
        timePicker4.setVisibility(View.GONE);
        String spinnerText = parent.getItemAtPosition(position).toString();
        switch (spinnerText) {
            case "once a day":
                timePicker1.setVisibility(View.VISIBLE);
                break;
            case "twice a day":
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                break;
            case "thrice a day":
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                break;
            default:
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
