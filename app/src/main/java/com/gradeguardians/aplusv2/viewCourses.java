package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

    /* Lists for our RecylclerView */
    List<Course> m_users_courses;
    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);

        buildRecyclerView();

        db_helper = new DatabaseHelper(viewCourses.this);

        m_users_courses = grabCourses("user", "Semester1");

        /* Called from onCreate and onRestart to always have an updated list */
        initRecyclerView();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        /* reset list that RecyclerView is displaying */
        m_users_courses.clear();
        m_users_courses = grabCourses("user", "Semester1");

        initRecyclerView();
    }

    /* ADD FUNCTIONALITY */
    public void delButtonClicked(View view) {


        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    public void addButtonClicked(View view) {
        Intent intent = new Intent(this, AddCourse.class);
        startActivity(intent);
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);

        /* Adapter initialized with out course data */
        RecyclerViewAdapterClasses adapter = new RecyclerViewAdapterClasses(this);

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