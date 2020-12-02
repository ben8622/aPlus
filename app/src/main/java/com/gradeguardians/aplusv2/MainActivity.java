package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences shared_pref;
    DatabaseHelper db_helper;
    TextView tv_title, tv_cumGPA;
    String curr_user;
    User u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared_pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        db_helper = new DatabaseHelper(MainActivity.this);
        tv_title = findViewById(R.id.tv_title);
        tv_cumGPA = findViewById(R.id.tv_cumGPA);

        curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");
        u = db_helper.grabOneUser(curr_user);

        tv_title.setText(u.getUserID());
        tv_cumGPA.setText(u.getGPA());

    }
    @Override
    protected void onRestart() {
        super.onRestart();

        /* update cumGPA on return from other viewSemester (what if things were added or deleted */
        db_helper.calcCumGPA(curr_user);
        u = db_helper.grabOneUser(curr_user);
        tv_cumGPA.setText(u.getGPA());
    }
    @Override
    protected void onResume(){
        super.onResume();

        db_helper.calcCumGPA(curr_user);
        u = db_helper.grabOneUser(curr_user);
        tv_cumGPA.setText(u.getGPA());

    }

    public void showNeededGPA(View view) {

        Toast.makeText(this, "Target GPA Shown", Toast.LENGTH_SHORT).show();
    }

    public void viewSemesters(View view){
        Intent intent = new Intent(this, viewSemesters.class);
        startActivity(intent);
    }

}