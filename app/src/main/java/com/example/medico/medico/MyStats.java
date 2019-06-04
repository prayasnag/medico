package com.example.medico.medico;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyStats extends AppCompatActivity {
    DatabaseHelper statsDatabaseHelper;
    private TextView weightBtn,heightBtn,weightText,heightText,BMItext,BMIStatus,statsAppointment,statsMedCount,statsStock,statsMissed;
    private float height,weight,bmi;
    private int missedCount,medStockValue;
    private String bmiText;
    private ImageView bmiWarning,collapseStat,collapseVital;
    private Button clearBMI,clearMedStats,saveBMI,saveVitals,sendMail;
    private EditText heightVal,bloodPressureField,bloodGlucoseField,cholesterolField,creatinineField;
    private LinearLayout statsLayout,vitalLayout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stats);
        heightBtn = findViewById(R.id.heightBtn);
        weightBtn = findViewById(R.id.weightBtn);
        weightText = findViewById(R.id.weightText);
        heightText = findViewById(R.id.heightText);
        heightVal = findViewById(R.id.heightValue);
        clearBMI = findViewById(R.id.clearBMI);
        saveBMI = findViewById(R.id.saveBMI);
        collapseVital = findViewById(R.id.collapseVital);
        collapseStat = findViewById(R.id.collapseStat);
        statsLayout = findViewById(R.id.stats);
        vitalLayout = findViewById(R.id.vital);
        statsDatabaseHelper = new DatabaseHelper(this);

        statsStock = findViewById(R.id.statsStock);
        statsMissed = findViewById(R.id.statsMissed);
        statsAppointment = findViewById(R.id.statsAppointment);
        statsMedCount = findViewById(R.id.statsMedCount);

        bloodPressureField =findViewById(R.id.bloodPressure);
        bloodGlucoseField =findViewById(R.id.bloodGlucose);
        cholesterolField =findViewById(R.id.cholesterol);
        creatinineField =findViewById(R.id.creatinine);
        saveVitals = findViewById(R.id.saveVitals);

        sendMail = findViewById(R.id.sendMail);

        viewBmiData();
        statsLayout.setVisibility(View.GONE);
        vitalLayout.setVisibility(View.GONE);

        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MyStats.this);
                dialog.setContentView(R.layout.dialogue_weight);
               // TextView text = (TextView) dialog.findViewById(R.id.text);
                Button dialogButton = (Button) dialog.findViewById(R.id.saveWeight);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText weightVal = dialog.findViewById(R.id.weightValue);
                        weightText.setHint(null);
                        weight = Float.parseFloat(weightVal.getText().toString());
                        String weight = weightVal.getText().toString()+" KG";
                        weightText.setText(weight);
                        dialog.dismiss();

            }
        });
                dialog.show();
            }
        });

        heightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MyStats.this);
                dialog.setContentView(R.layout.dialogue_height);
                Button dialogueButton = dialog.findViewById(R.id.saveHeight);
                dialogueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText heightVal = dialog.findViewById(R.id.heightValue);
                        heightText.setHint(null);
                        height = Float.parseFloat(heightVal.getText().toString());
                        String heightStr = heightVal.getText().toString()+" cm";
                        heightText.setText(heightStr);

                        //code for BMI
                        BMItext = findViewById(R.id.BMIText);
                        BMIStatus = findViewById(R.id.BMIStatus);
                        bmi = weight/((height/100)*(height/100));
                        bmiText=Float.toString(bmi);
                        BMItext.setText(bmiText);
                        bmiWarning = findViewById(R.id.bmiWarn);
                        bmiWarning.setVisibility(View.VISIBLE);
                        if(bmi<16) {
                            bmiWarning.setBackgroundResource(R.drawable.warning);
                            BMIStatus.setText("Heavily Underweight");
                            }
                        else if(bmi>=16 && bmi<18.5){
                            bmiWarning.setBackgroundResource(R.drawable.yellowwarn);
                            BMIStatus.setText("underweight");
                        }
                        else if(bmi>= 18.5 && bmi<30){
                            bmiWarning.setBackgroundResource(R.drawable.correct);
                            BMIStatus.setText("Healthy Weight");
                        }

                        else if(bmi>=30 && bmi<=35){
                            bmiWarning.setBackgroundResource(R.drawable.yellowwarn);
                            BMIStatus.setText("Moderately Heavy");
                        }

                        else {
                            bmiWarning.setBackgroundResource(R.drawable.warning);
                            BMIStatus.setText("Obese");
                        }
                        dialog.dismiss();
                    }
                });
                    dialog.show();
            }
        });

        clearBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weightText.setText(null);
                weightText.setHint("Enter weight by clicking on the plus icon");
                heightText.setText(null);
                heightText.setHint("Enter height by clicking on the plus icon");
                BMItext.setText(null);
                BMIStatus.setText(null);
                bmiWarning.setVisibility(View.GONE);
            }
        });
        addBmiData();

        //code for collapsing stats
        collapseStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statsLayout.getVisibility()==View.VISIBLE)
                    statsLayout.setVisibility(View.GONE);
                else
                    statsLayout.setVisibility(View.VISIBLE);
            }
        });

        //code for collapsing vitals
        collapseVital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vitalLayout.getVisibility()==View.VISIBLE)
                    vitalLayout.setVisibility(View.GONE);
                else
                    vitalLayout.setVisibility(View.VISIBLE);
            }
        });

        //get vitals from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("Vitalsdata",Context.MODE_PRIVATE);
        String BP = sharedPreferences.getString("BP","0");
        String glucose = sharedPreferences.getString("glucose","0");
        String cholesterol = sharedPreferences.getString("cholesterol","0");
        String creatinine = sharedPreferences.getString("creatinine","0");
        if(BP.equals("0") && cholesterol.equals("0") && glucose.equals("0") && creatinine.equals("0")) {
            toastMessage("No data found");
        }
        else {
            bloodPressureField.setText(BP);
            bloodGlucoseField.setText(glucose);
            cholesterolField.setText(cholesterol);
            creatinineField.setText(creatinine);
        }

        //setting appo count and med count and missedMeds
        Cursor appoData = statsDatabaseHelper.getAppoData();
        Cursor medData = statsDatabaseHelper.getData();

        int medCount = medData.getCount();
        int appoCount = appoData.getCount();

        if(appoCount!=0)
            statsAppointment.setText(Integer.toString(appoCount));
        if(medCount!=0)
            statsMedCount.setText(Integer.toString(medCount));

        sharedPreferences = getSharedPreferences("medStats",Context.MODE_PRIVATE);
        missedCount = sharedPreferences.getInt("missedMeds",0);
        statsMissed.setText(Integer.toString(missedCount));

        medStockValue = sharedPreferences.getInt("medStock",0);
        if(medStockValue<5) {
            statsStock.setText("Low Stock");
            statsStock.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else
            statsStock.setText("Available");

    //code for mail
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "My stats");
                i.putExtra(Intent.EXTRA_TEXT   , "weight: "+weightText.getText()+"\nheight: "+heightText.getText()
                            +"\nblood pressure: "+bloodPressureField.getText()+"\nblood glucose: "+bloodGlucoseField.getText()
                            +"\ncreatinine: "+creatinineField.getText()+"\ncholesterol: "+cholesterolField.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MyStats.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //code for fetching the last data from database and viewing the data when the activity loads
    public void viewBmiData() {
        Cursor data = statsDatabaseHelper.getBmiData();
        if (data.getCount() == 0)
            toastMessage("enter height and weight");
        else {
            data.moveToLast();
            weightText = findViewById(R.id.weightText);
            heightText = findViewById(R.id.heightText);
            BMItext = findViewById(R.id.BMIText);
            BMIStatus = findViewById(R.id.BMIStatus);
            weightText.setText(data.getString(1));
            heightText.setText(data.getString(2));
            BMItext.setText(data.getString(3));
            bmiWarning = findViewById(R.id.bmiWarn);

            if (Float.parseFloat(BMItext.getText().toString()) < 16) {
                bmiWarning.setBackgroundResource(R.drawable.warning);
                BMIStatus.setText("Heavily Underweight");
            } else if (Float.parseFloat(BMItext.getText().toString()) >= 16 && Float.parseFloat(BMItext.getText().toString()) < 18.5) {
                bmiWarning.setBackgroundResource(R.drawable.yellowwarn);
                BMIStatus.setText("underweight");
            } else if (Float.parseFloat(BMItext.getText().toString()) >= 18.5 && Float.parseFloat(BMItext.getText().toString()) < 30) {
                bmiWarning.setBackgroundResource(R.drawable.correct);
                BMIStatus.setText("Healthy Weight");
            } else if (Float.parseFloat(BMItext.getText().toString()) >= 30 && Float.parseFloat(BMItext.getText().toString()) <= 35) {
                bmiWarning.setBackgroundResource(R.drawable.yellowwarn);
                BMIStatus.setText("Moderately Heavy");
            } else {
                bmiWarning.setBackgroundResource(R.drawable.warning);
                BMIStatus.setText("Obese");
            }
        }
    }
    //code for adding the data to the database
    public void addBmiData() {
        saveBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String saveHeight = heightText.getText().toString();
                String saveWeight = weightText.getText().toString();
                boolean insertData = statsDatabaseHelper.addBmiData(saveWeight, saveHeight, bmiText);
                if (insertData) {
                    toastMessage("Data Successfully Inserted!");
                } else {
                    toastMessage("Something went wrong");
                }
            }

        });
    }
    //saving vitals in shared preferences
    public void addVitals(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Vitalsdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BP",bloodPressureField.getText().toString());
        editor.putString("glucose",bloodGlucoseField.getText().toString());
        editor.putString("cholesterol",cholesterolField.getText().toString());
        editor.putString("creatinine",creatinineField.getText().toString());

        editor.commit();
        toastMessage("vitals saved successfully");

    }
    //clear med Stats
    public void clearMedStats(View view){
        statsMissed.setText("0");
        statsStock.setText("Not defined");
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    public void collapseStat(){

    }

}
