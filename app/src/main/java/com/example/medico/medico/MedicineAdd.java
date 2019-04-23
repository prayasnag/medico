package com.example.medico.medico;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MedicineAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private Button addBtn;
    private static final String TAG = "MedicineAdd";
    DatabaseHelper mDatabaseHelper;
    private EditText editText;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayTime1;
    private TextView mDisplayTime2;
    private TextView mDisplayTime3;
    private TextView mDisplayTime4;
    private DatePickerDialog.OnDateSetListener mTimeSetListener1;
    private DatePickerDialog.OnDateSetListener mTimeSetListener2;
    private DatePickerDialog.OnDateSetListener mTimeSetListener3;
    private DatePickerDialog.OnDateSetListener mTimeSetListener4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);

        //Variables Declaration
        editText = findViewById(R.id.editMedName);
        mDatabaseHelper = new DatabaseHelper(this);

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
                int hour = cal.get(Calendar.HOUR);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime1.setText(time);
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
                int hour = cal.get(Calendar.HOUR);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime2.setText(time);
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
                int hour = cal.get(Calendar.HOUR);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime3.setText(time);
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
                int hour = cal.get(Calendar.HOUR);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(MedicineAdd.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        mDisplayTime4.setText(time);
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
                month = month+1;
                if(month == 1)
                    monthText = "Jan";
                else if(month == 2 )
                    monthText = "Feb";
                else if(month == 3 )
                    monthText = "Mar";
                else if(month == 4 )
                    monthText = "Apr";
                else if(month == 5 )
                    monthText = "May";
                else if(month == 6 )
                    monthText = "Jun";
                else if(month == 7 )
                    monthText = "Jul";
                else if(month == 8 )
                    monthText = "Aug";
                else if(month == 9 )
                    monthText = "Sept";
                else if(month == 10 )
                    monthText = "Oct";
                else if(month == 11 )
                    monthText = "Nov";
                else
                    monthText = "Dec";
                String date = monthText +" "+day+", "+year;
                mDisplayDate.setText(date);
            }
        };

        //Data Collection Method
        try {
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newEntry = editText.getText().toString();
                    System.out.print(newEntry);
                    if (editText.length() != 0) {
                        AddData(newEntry);
                        editText.setText("");
                    } else {
                        Toast.makeText(MedicineAdd.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch(Exception e){

        }


        //Start of code for date picker

        String currDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView date = findViewById(R.id.StartDate);
        date.setText(currDate);


    }
    private void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            Toast.makeText(MedicineAdd.this, "Data Successfully Inserted!",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MedicineAdd.this, "Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
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
