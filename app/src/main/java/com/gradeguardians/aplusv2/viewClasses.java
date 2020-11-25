package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class viewClasses extends AppCompatActivity {

    private static final String TAG = "viewClasses"; //debugging log

    // variables (this should come from our user object)
    private ArrayList<String> mClassNames = new ArrayList<>();
    private ArrayList<String> mClassWeights = new ArrayList<>();
    private ArrayList<String> mClassGrades = new ArrayList<>();
    List<Course> users_courses;
    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);
        Log.d(TAG, "onCreate:  started");

        db_helper = new DatabaseHelper(viewClasses.this);

        users_courses = db_helper.getAllCourses("user", "Semester1");

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

        for(int i = 0; i < users_courses.size(); i++){
            mClassNames.add(users_courses.get(i).getCourseID());
            mClassGrades.add(String.format("%.2f", users_courses.get(i).getCourseGrade()));
            mClassWeights.add(Integer.toString(users_courses.get(i).getCourseWeight()));
        }

        // call this after you get all data
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        // Passing the adapter our info on its construction
        RecyclerViewAdapterClasses adapter = new RecyclerViewAdapterClasses(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}