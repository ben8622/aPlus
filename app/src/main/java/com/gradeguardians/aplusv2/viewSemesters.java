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
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import java.util.List;

public class viewSemesters extends AppCompatActivity {

    SharedPreferences shared_pref;
    DatabaseHelper db_helper;

    String curr_user;

    TextView tv_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_semesters);

        db_helper = new DatabaseHelper(viewSemesters.this);
        shared_pref = PreferenceManager.getDefaultSharedPreferences(viewSemesters.this);

        curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");

        tv_semester = findViewById(R.id.tv_semester);
        tv_semester.setText("Semester:         GPA:");

        db_helper.calcCumGPA(curr_user);

        initRecyclerView();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        db_helper.calcCumGPA(curr_user);

        initRecyclerView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        db_helper.calcCumGPA(curr_user);
    }

    public void delButtonClicked(View view) {
        //Intent intent = new Intent(this, DeleteSemester.class);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
       }

    public void addButtonClicked(View view) {
        Intent intent = new Intent(this, AddSemester.class);
        startActivity(intent);
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);

        // Passing the adapter our info on its construction
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, curr_user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}