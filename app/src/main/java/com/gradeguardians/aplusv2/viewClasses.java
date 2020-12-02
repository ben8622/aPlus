package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    SharedPreferences shared_pref;
    String curr_user;
    String curr_semester;
    List<Course> users_courses;
    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);
        Log.d(TAG, "onCreate:  started");

        db_helper = new DatabaseHelper(viewClasses.this);

        shared_pref = PreferenceManager.getDefaultSharedPreferences(viewClasses.this);
        curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");
        curr_semester = shared_pref.getString(getString(R.string.SEM_KEY), "error");

        users_courses = db_helper.getAllCourses("user", "Semester1");

        initArrayListData();
    }

    public void delButtonClicked(View view) {

        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }
    public void addButtonClicked(View view) {

    Toast.makeText(this, "|" + curr_user + "||" + curr_semester + "|", Toast.LENGTH_SHORT).show();
    }

    // This function we would update with a function to extract our User's info
    private void initArrayListData(){
        Log.d(TAG, "initArrayListData: prepping array data");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        // Passing the adapter our info on its construction
        RecyclerViewAdapterClasses adapter = new RecyclerViewAdapterClasses(this, curr_user, curr_semester);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}