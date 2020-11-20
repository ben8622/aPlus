package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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


    @Override
    protected void onRestart(){
        super.onRestart();

        users_courses.clear();
        users_courses = db_helper.getAllCourses("user", "Semester1");

        initArrayListData();
    }


    public void delButtonClicked(View view) {

        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    /*  NEED TO UPDATE, LIST DOESN'T UPDATE ON RETURN
    *
    *
     */
    public void addButtonClicked(View view) {
        Intent intent = new Intent(this, AddCourse.class);
        startActivity(intent);

    }

    // This function we would update with a function to extract our User's info
    private void initArrayListData(){
        Log.d(TAG, "initArrayListData: prepping array data");

        mClassNames.clear();
        mClassGrades.clear();
        mClassWeights.clear();

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
        RecyclerViewAdapterClasses adapter = new RecyclerViewAdapterClasses(this, mClassNames, mClassGrades, mClassWeights);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}