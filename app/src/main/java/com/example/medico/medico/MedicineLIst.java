package com.example.medico.medico;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicineLIst extends AppCompatActivity {

    //variables
    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private FloatingActionButton floatbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        //search method
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = mDatabaseHelper.getData();

        if(data.getCount() == 0){
            Toast.makeText(MedicineLIst.this, "No data to show!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter mListAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                mListView.setAdapter(mListAdapter);
            }
        }





        floatbtn = (FloatingActionButton) findViewById(R.id.float1);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicineLIst.this,MedicineAdd.class));
            }
        });
    }


}
