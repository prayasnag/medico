package com.example.medico.medico;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton androidBtn1;
    private ImageButton androidBtn2;
    private ImageButton androidBtn3;
    private ImageButton androidBtn4;
    private TextView nameText,ageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.textName);

        SharedPreferences prefs = getSharedPreferences("launcher",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);

        if(firstStart)
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));

        String name = prefs.getString("name","GUEST");
        String age = prefs.getString("age","20");
        String gender = prefs.getString("gender","MALE");
        nameText.setText(name+","+age);

        androidBtn1 = (ImageButton) findViewById(R.id.button1);

        androidBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "good job",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,MedicineLIst.class));
            }
        });

        androidBtn2 = (ImageButton) findViewById(R.id.button2);
        androidBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Emergency.class));
            }
        });

        androidBtn3 = (ImageButton) findViewById(R.id.button3);
        androidBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,appointmentList.class));
            }
        });


        androidBtn4 = (ImageButton) findViewById(R.id.button4);
        androidBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MyStats.class));
            }
        });



    }
}
