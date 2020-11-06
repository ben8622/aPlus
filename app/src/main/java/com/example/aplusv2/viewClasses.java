package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    public void delButtonClicked(View view) {

        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }
    public void addButtonClicked(View view) {

        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
    }

    // This function we would update with a function to extract our User's info
    private void initArrayListData(){
        Log.d(TAG, "initArrayListData: prepping array data");

        mClassNames.add("CSE3310");
        mClassGrades.add("99.99");
        mClassWeights.add("3");

        mClassNames.add("IE3301");
        mClassGrades.add("86.40");
        mClassWeights.add("3");

        mClassNames.add("CSE1220");
        mClassGrades.add("91.69");
        mClassWeights.add("3");

        mClassNames.add("CSE4420");
        mClassGrades.add("56.00");
        mClassWeights.add("4");

        // call this after you get all data
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        // Passing the adapter our info on its construction
        RecyclerViewAdapterClasses adapter = new RecyclerViewAdapterClasses(this, mClassNames, mClassGrades, mClassWeights);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}