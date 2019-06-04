package com.example.medico.medico;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {
    private Button signUpBtn;
    private EditText name,age;
    private RadioGroup mRadioGrp;
    private RadioButton male,female;
    private String genderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpBtn = findViewById(R.id.signUpBtn);
        name = findViewById(R.id.nameSignUp);
        age = findViewById(R.id.ageSignUp);
        mRadioGrp = findViewById(R.id.gender);

        mRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.male){
                    genderText = "MALE";
                }
                else if(i==R.id.female){
                    genderText = "FEMALE";
                }
            }
        });

        SharedPreferences prefs = getSharedPreferences("launcher",MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.commit();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("launcher",MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name",name.getText().toString());
                editor.putString("gender",genderText);
                editor.putString("age",age.getText().toString());
                editor.commit();

                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });

    }
}
