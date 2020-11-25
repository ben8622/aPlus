package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

import java.util.List;

public class viewSemesters extends AppCompatActivity {

    SharedPreferences shared_pref;
    DatabaseHelper db_helper;

    private static final String TAG = "viewSemesters"; //debugging log

    // variables (this should come from our user object)
    private ArrayList<String> mSemesterNames = new ArrayList<>();
    private ArrayList<String> mSemesterGrades = new ArrayList<>();
    List<Semester> m_user_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_semesters);

        buildRecyclerView();

        Log.d(TAG, "onCreate:  started");

        //String curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");

        /* get semesters data from user object by using database helper */
        db_helper = new DatabaseHelper(viewSemesters.this);
        m_user_semester = db_helper.getAllSemester("user");

        // When this activity is created, list data is initialized
        initArrayListData();
    }

    protected void onRestart(){
        super.onRestart();

        m_user_semester.clear();
        m_user_semester = grabSemester("user");
        //m_user_semester = db_helper.getAllSemester("user");

        initRecyclerView();
    }


    public void delButtonClicked(View view) {
        //Intent intent = new Intent(this, DeleteSemester.class);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
       }

    public void addButtonClicked(View view) {
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddSemester.class);
        startActivity(intent);
    }

    // This function we would update with a function to extract our User's info
    private void initArrayListData(){
        Log.d(TAG, "initArrayListData: prepping array data");
        shared_pref = viewSemesters.this.getSharedPreferences(getString(R.string.preference_file), MODE_PRIVATE);

        //String curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");

        /* get semesters data from user object by using database helper */
        //user_sems = db_helper.getAllSemester("user");
        for(int i = 0 ; i < m_user_semester.size() ; i++){
            String id = m_user_semester.get(i).getSemesterID();
           String gpa = String.valueOf(m_user_semester.get(i).getSemesterGPA());
            mSemesterNames.add(id);
            mSemesterGrades.add(gpa);
        }
        mSemesterNames.add("FALL 2021");
        mSemesterGrades.add("3.0");

        // call this after you get all data
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        // Passing the adapter our info on its construction
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mSemesterNames, mSemesterGrades);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Semester> grabSemester(String user_id){
        List<Semester> tmp = db_helper.getAllSemester(user_id);
        return tmp;
    }

    private void buildRecyclerView() {
    }
}