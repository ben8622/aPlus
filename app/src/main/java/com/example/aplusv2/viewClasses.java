package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class viewClasses extends AppCompatActivity {

    private static final String TAG = "viewClasses"; //debugging log

    // variables (this should come from our user object)
    private ArrayList<String> mClassNames = new ArrayList<>();
    private ArrayList<String> mClassWeights = new ArrayList<>();
    private ArrayList<String> mClassGrades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);
        Log.d(TAG, "onCreate:  started");

        initArrayListData();
    }

    // This function we would update with a function to extract our User's info
    private void initArrayListData(){
        Log.d(TAG, "initArrayListData: prepping array data");

        mClassNames.add("Class1");
        mClassGrades.add("90.00");
        mClassWeights.add("3");

        mClassNames.add("Class2");
        mClassGrades.add("90.00");
        mClassWeights.add("3");

        mClassNames.add("Class3");
        mClassGrades.add("90.00");
        mClassWeights.add("3");

        mClassNames.add("Class4");
        mClassGrades.add("90.00");
        mClassWeights.add("3");

        // call this after you get all data
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        // Passing the adapter our info on its construction
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mClassNames, mClassGrades);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}