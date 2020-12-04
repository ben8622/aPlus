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

    List<Course> m_users_courses;
    DatabaseHelper db_helper;
    SharedPreferences shared_pref;
    String curr_user;
    String curr_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);

        buildRecyclerView();

        db_helper = new DatabaseHelper(viewCourses.this);

        shared_pref = PreferenceManager.getDefaultSharedPreferences(viewCourses.this);
        curr_user = shared_pref.getString("username", "error");
        curr_semester = shared_pref.getString(getString(R.string.SEM_KEY), "error");

        db_helper.calcSemGpa(curr_user, curr_semester);

        m_users_courses = grabCourses("user", "Semester1");

        /* Called from onCreate and onRestart to always have an updated list */
        initRecyclerView();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        /* reset list that RecyclerView is displaying */
        m_users_courses.clear();
        m_users_courses = grabCourses(curr_user, curr_semester);

        db_helper.calcSemGpa(curr_user, curr_semester);

        initRecyclerView();
    }


    public void addButtonClicked(View view) {
        Toast.makeText(this, "|" + curr_user + "||" + curr_semester + "|", Toast.LENGTH_SHORT).show();
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

    /* grab user_id and semester_id from shared_pref file */
    public List<Course> grabCourses(String user_id, String semester_id){
        List<Course> tmp = db_helper.getAllCourses(user_id, semester_id);
        return tmp;
    }

    public void buildRecyclerView(){}
}