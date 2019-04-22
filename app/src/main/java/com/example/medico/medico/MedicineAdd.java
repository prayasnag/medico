package com.example.medico.medico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedicineAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private Button addBtn;
    private static final String TAG = "MedicineAdd";
    DatabaseHelper mDatabaseHelper;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);

        //Variables Declaration
        editText =(EditText) findViewById(R.id.editMedName);
        mDatabaseHelper = new DatabaseHelper(this);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //update for git

        //Start of code for spinner for reminder

        Spinner spinner = findViewById(R.id.spinnerRem);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.rem_time,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        String spinnerText = spinner.getSelectedItem().toString();


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
        if(spinnerText.equals("once a day"))
            timePicker1.setVisibility(View.VISIBLE);
        else if(spinnerText.equals("twice a day"))
        {
            timePicker1.setVisibility(View.VISIBLE);
            timePicker2.setVisibility(View.VISIBLE);
        }
        else if(spinnerText.equals("thrice a day"))
        {
            timePicker1.setVisibility(View.VISIBLE);
            timePicker2.setVisibility(View.VISIBLE);
            timePicker3.setVisibility(View.VISIBLE);
        }
        else
        {
            timePicker1.setVisibility(View.VISIBLE);
            timePicker2.setVisibility(View.VISIBLE);
            timePicker3.setVisibility(View.VISIBLE);
            timePicker4.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
