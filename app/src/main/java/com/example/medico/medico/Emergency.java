package com.example.medico.medico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Emergency extends AppCompatActivity {

    private ImageView collapse1,collapse2,collapse3,collapse4;
    private TextView text1,text2,text3,text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        collapse1 = findViewById(R.id.collapse1);
        collapse2 = findViewById(R.id.collapse2);
        collapse3 = findViewById(R.id.collapse3);
        collapse4 = findViewById(R.id.collapse4);

        text1 = findViewById(R.id.Text1);
        text2 = findViewById(R.id.Text2);
        text3 = findViewById(R.id.Text3);
        text4 = findViewById(R.id.Text4);

        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);
        text4.setVisibility(View.GONE);

        collapse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text1.getVisibility()==View.VISIBLE)
                    text1.setVisibility(View.GONE);
                else
                    text1.setVisibility(View.VISIBLE);
            }
        });

        collapse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text2.getVisibility()==View.VISIBLE)
                    text2.setVisibility(View.GONE);
                else
                    text2.setVisibility(View.VISIBLE);
            }
        });

        collapse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text3.getVisibility()==View.VISIBLE)
                    text3.setVisibility(View.GONE);
                else
                    text3.setVisibility(View.VISIBLE);
            }
        });

        collapse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text4.getVisibility()==View.VISIBLE)
                    text4.setVisibility(View.GONE);
                else
                    text4.setVisibility(View.VISIBLE);
            }
        });
    }
}
