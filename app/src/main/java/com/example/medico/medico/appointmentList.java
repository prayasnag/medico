package com.example.medico.medico;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class appointmentList extends AppCompatActivity {

    //variables
    SQLiteDatabase db;
    private static final String TAG = "ApointmentListActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private FloatingActionButton floatbtn;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();

        noData = (TextView) findViewById(R.id.noAppoData);

        String[] from = {mDatabaseHelper.appoCOL1, mDatabaseHelper.appoCOL2, mDatabaseHelper.appoCOL4, mDatabaseHelper.appoCOL3, mDatabaseHelper.appoCol6};
        final String[] column = {mDatabaseHelper.appoCOL0, mDatabaseHelper.appoCOL1, mDatabaseHelper.appoCOL2, mDatabaseHelper.appoCOL4, mDatabaseHelper.appoCOL3, mDatabaseHelper.appoCol6};
        int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mDatabaseHelper.APPO_TABLE_NAME, column, null, null, null, null, null);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to, 0);

        mListView.setAdapter(adapter);

        //search method
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = mDatabaseHelper.getAppoData();

        if (data.getCount() == 0) {
            Toast.makeText(appointmentList.this, "No data to show!", Toast.LENGTH_LONG).show();
            noData.setVisibility(View.VISIBLE);
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));
                noData.setVisibility(View.GONE);
            }
        }
            //set an onItemClickListener to the ListView
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                    Intent intent = new Intent(appointmentList.this, appointmentEdit.class);
                    intent.putExtra(getString(R.string.rodId), id);
                    startActivity(intent);
                }

            });

            floatbtn = (FloatingActionButton) findViewById(R.id.floatAppointment);
            floatbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(appointmentList.this, appointmentAdd.class));
                }
            });
        }
}

