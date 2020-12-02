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
import java.util.ArrayList;

import java.util.List;

public class viewSemesters extends AppCompatActivity {

    SharedPreferences shared_pref;
    DatabaseHelper db_helper;
    String curr_user;

    private static final String TAG = "viewSemesters"; //debugging log

    // variables (this should come from our user object)
    List<Semester> m_user_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_semesters);

        Log.d(TAG, "onCreate:  started");

        //String curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");

        /* get semesters data from user object by using database helper */
        db_helper = new DatabaseHelper(viewSemesters.this);
        shared_pref = PreferenceManager.getDefaultSharedPreferences(viewSemesters.this);
        curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");
        m_user_semester = db_helper.getAllSemester(curr_user);

        db_helper.calcCumGPA(curr_user);


        // When this activity is created, list data is initialized
        initArrayListData();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        db_helper.calcCumGPA(curr_user);

        m_user_semester.clear();

        m_user_semester = db_helper.getAllSemester("user");

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
        Toast.makeText(this, "|" + curr_user + "|", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddSemester.class);
        startActivity(intent);
    }

    // This function we would update with a function to extract our User's info
    private void initArrayListData(){
        Log.d(TAG, "initArrayListData: prepping array data");
        shared_pref = viewSemesters.this.getSharedPreferences(getString(R.string.preference_file), MODE_PRIVATE);

        // call this after you get all data
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        // Passing the adapter our info on its construction
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, m_user_semester, curr_user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Semester> grabSemester(String user_id){
        List<Semester> tmp = db_helper.getAllSemester(user_id);
        return tmp;
    }
}