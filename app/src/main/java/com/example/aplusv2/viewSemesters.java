package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class viewSemesters extends AppCompatActivity {

    private static final String TAG = "viewSemesters"; //debugging log

    // variables (this should come from our user object)
    private ArrayList<String> mSemesterNames = new ArrayList<>();
    private ArrayList<String> mSemesterGrades = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_semesters);
        Log.d(TAG, "onCreate:  started");

        // When this activity is created, list data is initialized
        initArrayListData();
    }

    public void delButtonClicked(View view) {

        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }
    public void addButtonClicked(View view) {

        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
    }

    // This function we would update with a function to extract our User's info
    private void initArrayListData(){
        Log.d(TAG, "initArrayListData: prepping array data");

        mSemesterNames.add("SPRING21");
        mSemesterGrades.add("2.9");

        mSemesterNames.add("FALL20");
        mSemesterGrades.add("3.2");

        mSemesterNames.add("SPRING20");
        mSemesterGrades.add("4.0");

        mSemesterNames.add("FALL19");
        mSemesterGrades.add("3.1");

        // call this after you get all data
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        // Passing the adapter our info on its construction
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mSemesterNames, mSemesterGrades);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}