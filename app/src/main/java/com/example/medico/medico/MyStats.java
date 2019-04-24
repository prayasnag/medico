package com.example.medico.medico;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MyStats extends AppCompatActivity {
    private TextView weightBtn;
    private TextView heightBtn;
    private TextView weightText;
    private TextView heightText;
    private EditText heightVal;
    private TextView BMItext,BMIStatus;
    private float height,weight,bmi;
    private ImageView bmiWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stats);
        heightBtn = findViewById(R.id.heightBtn);
        weightBtn = findViewById(R.id.weightBtn);
        weightText = findViewById(R.id.weightText);
        heightText = findViewById(R.id.heightText);
        heightVal = findViewById(R.id.heightValue);

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

                        //code for BMI---Move out of this section after connecting database---
                        BMItext = findViewById(R.id.BMIText);
                        BMIStatus = findViewById(R.id.BMIStatus);
                        bmi = weight/((height/100)*(height/100));
                        String bmiText=Float.toString(bmi);
                        BMItext.setText(bmiText);
                        bmiWarning = findViewById(R.id.bmiWarn);
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


    }
}
