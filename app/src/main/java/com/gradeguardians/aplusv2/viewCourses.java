package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gradeguardians.aplusv2.Course;
import com.gradeguardians.aplusv2.DatabaseHelper;
import com.gradeguardians.aplusv2.R;
import com.gradeguardians.aplusv2.RecyclerViewAdapterClasses;

import java.util.ArrayList;
import java.util.List;

public class viewCourses extends AppCompatActivity {

    DatabaseHelper db_helper;
    SharedPreferences shared_pref;

    String curr_user;
    String curr_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);


        db_helper = new DatabaseHelper(viewCourses.this);
        shared_pref = PreferenceManager.getDefaultSharedPreferences(viewCourses.this);

        curr_user = shared_pref.getString("username", "error");
        curr_semester = shared_pref.getString(getString(R.string.SEM_KEY), "error");

        /* make sure semester's gpa always up to date */
        db_helper.calcSemGpa(curr_user, curr_semester);

        /* Called from onCreate and onRestart to always have an updated list */
        initRecyclerView();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        db_helper.calcSemGpa(curr_user, curr_semester);
        initRecyclerView();
    }

    public void addButtonClicked(View view) {
        Intent intent = new Intent(this, AddCourse.class);
        startActivity(intent);
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);

        /* Adapter initialized with out course data */
        RecyclerViewAdapterClasses adapter = new RecyclerViewAdapterClasses(this, curr_user, curr_semester);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}