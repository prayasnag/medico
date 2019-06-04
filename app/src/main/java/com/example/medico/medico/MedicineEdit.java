package com.example.medico.medico;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MedicineEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Data Input Variables
    private static final String TAG = "MedicineEdit";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd,btnDlt;
    private EditText medNameTxt,docNameTxt,medColorTxt,medShapeTxt,mDurationTxt,mIntervalTxt;
    private TextView mDisplayDate;
    private TextView mDisplayTime1,mDisplayTime2,mDisplayTime3,mDisplayTime4;
    private RadioGroup mRadioDurationGrp,mRadioIntervalGrp,mRadioInstGrp;
    private RadioButton mRadioDurationBtn,mRadioIntervalBtn,mRadioInstBtn;
    private DatePickerDialog.OnDateSetListener mDateSetListener,mTimeSetListener1,mTimeSetListener2,mTimeSetListener3,mTimeSetListener4;
    private String sMedName,time1,time2,time3,time4,sdate,color,shape,sDocName,instruction;
    private int duration,interval;


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
        setContentView(R.layout.activity_medicine_edit);

    //Get the Value of ID from Extras
        final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

    //Variables Declaration
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        medNameTxt = (EditText) findViewById(R.id.editMedName);     //Medicine Name
        btnAdd = (Button) findViewById(R.id.addMedBtn);             //Add Button
        btnDlt = (Button) findViewById(R.id.dltMedBtn);
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
        mDisplayTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineEdit.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
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
        mDisplayTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineEdit.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
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
        mDisplayTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineEdit.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
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
        mDisplayTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineEdit.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
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

                DatePickerDialog dialog = new DatePickerDialog(MedicineEdit.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
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
                setAlarm(myCal1);                           //Calling Alarm Method to set Alarm
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


                if (medNameTxt.length() != 0 ) {

                    long insertData= db.update(mDatabaseHelper.TABLE_NAME, cv, mDatabaseHelper.COL0 + "=" + id, null);
                    startActivity(new Intent(MedicineEdit.this,MainActivity.class));
                    if (insertData==-1) {
                        toastMessage("Something went wrong!");
                    } else {
                        toastMessage("Data Successfully Updated");
                    }
                } else {
                    toastMessage("You must put a Medicine Name!");
                }

            }
        });


        btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MedicineEdit.this,R.style.AlertDialogStyle);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Long id = getIntent().getExtras().getLong(getString(R.string.rodID));
                                db.delete(mDatabaseHelper.TABLE_NAME, mDatabaseHelper.COL0 + "=" + id, null);
                                db.close();
                                toastMessage("Data Deleted");
                                Intent openMainActivity = new Intent(MedicineEdit.this, MedicineLIst.class);
                                startActivity(openMainActivity);

                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)                        //Do nothing on no
                        .show();
            }
        });

//Start of code for date picker

        String currDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView date = (TextView)findViewById(R.id.StartDate);
        date.setText(currDate);

//Data Search
        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME + " where " + dbHelper.COL0 + "=" + id, null);

//DATA PUT
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                medNameTxt.setText(cursor.getString(cursor.getColumnIndex(dbHelper.COL1)));

                mDisplayTime1.setText(cursor.getString(cursor.getColumnIndex(dbHelper.COL2)));
                String tm2=(cursor.getString(cursor.getColumnIndex(dbHelper.COL3)));
                String tm3=(cursor.getString(cursor.getColumnIndex(dbHelper.COL4)));
                String tm4=(cursor.getString(cursor.getColumnIndex(dbHelper.COL5)));
                if(!tm2.equalsIgnoreCase("0")){
                    mDisplayTime2.setVisibility(View.VISIBLE);
                    mDisplayTime2.setText(tm2);
                }
                if(!tm3.equalsIgnoreCase("0")){

                    mDisplayTime3.setVisibility(View.VISIBLE);
                    mDisplayTime3.setText(tm3);
                }
                if(!tm4.equalsIgnoreCase("0")){

                    mDisplayTime4.setVisibility(View.VISIBLE);
                    mDisplayTime4.setText(tm4);
                }


                mDisplayDate.setText(cursor.getString(cursor.getColumnIndex(dbHelper.COL6)));

                String d=cursor.getString(cursor.getColumnIndex(dbHelper.COL7));
                if(d.equalsIgnoreCase("0")){
                    mRadioDurationGrp.check(R.id.radioDurationCont);
                }else{
                    mRadioDurationGrp.check(R.id.radioDurationDays);
                    mDurationTxt.setVisibility(View.VISIBLE);
                    mDurationTxt.setText(d);
                }

                String in=cursor.getString(cursor.getColumnIndex(dbHelper.COL8));
                if(in.equalsIgnoreCase("0")){
                    mRadioIntervalGrp.check(R.id.radioDaysEveryDay);
                }else{
                    mRadioIntervalGrp.check(R.id.radioDaysInterval);
                    mIntervalTxt.setVisibility(View.VISIBLE);
                    mIntervalTxt.setText(in);
                }

                String mc=cursor.getString(cursor.getColumnIndex(dbHelper.COL9));
                if(!mc.equalsIgnoreCase("Not Defined")){
                   medColorTxt.setText(mc);
                }

                String ms=cursor.getString(cursor.getColumnIndex(dbHelper.COL10));
                if(!ms.equalsIgnoreCase("Not Defined")){
                    medShapeTxt.setText(ms);
                }

                String dn=cursor.getString(cursor.getColumnIndex(dbHelper.COL11));
                if(!dn.equalsIgnoreCase("Not Defined")) {
                    docNameTxt.setText(dn);
                }

                String x=cursor.getString(cursor.getColumnIndex(dbHelper.COL12));
                switch (x){
                    case "before food":
                        mRadioInstGrp.check(R.id.radioInstBefore);
                        break;
                    case "with food":
                        mRadioInstGrp.check(R.id.radioInstWith);
                        break;
                    case "after food":
                        mRadioInstGrp.check(R.id.radioInstAfter);
                        break;
                }

            }
            cursor.close();
        }

    }

//Method for Setting Alarm

    void setAlarm(Calendar alarmCal){
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MedicineEdit.this, AlarmReceiver.class);

        String alertTitle = medNameTxt.getText().toString();
        intent.putExtra(getString(R.string.alert_title), alertTitle);
        final int i= (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MedicineEdit.this, i, intent, 0);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pendingIntent);
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
