package com.example.medico.medico;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicineLIst extends AppCompatActivity {

    //variables
    SQLiteDatabase db;
    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private FloatingActionButton floatbtn;
    TextView noMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        db= mDatabaseHelper.getWritableDatabase();

        noMed =  (TextView) findViewById(R.id.noMedData);

        String[] from = {mDatabaseHelper.COL1, mDatabaseHelper.COL11, mDatabaseHelper.COL9, mDatabaseHelper.COL2, mDatabaseHelper.COL12};
        final String[] column = {mDatabaseHelper.COL0, mDatabaseHelper.COL1, mDatabaseHelper.COL11, mDatabaseHelper.COL9, mDatabaseHelper.COL2, mDatabaseHelper.COL12};
        int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mDatabaseHelper.TABLE_NAME, column, null, null ,null, null, null);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to, 0);

        mListView.setAdapter(adapter);

        //search method
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = mDatabaseHelper.getData();

        if(data.getCount() == 0){
            Toast.makeText(MedicineLIst.this, "No data to show!",Toast.LENGTH_LONG).show();
            noMed.setVisibility(View.VISIBLE);
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));}
                noMed.setVisibility(View.GONE);
        //set an onItemClickListener to the ListView
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> listView, View view, int position, long id){
                    Intent intent = new Intent(MedicineLIst.this, MedicineEdit.class);
                    intent.putExtra(getString(R.string.rodId), id);
                    startActivity(intent);
                }

            });

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




// Log.d(TAG, "onItemClick: The ID is: " + itemID);
