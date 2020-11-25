package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences shared_pref;
    TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared_pref = MainActivity.this.getSharedPreferences(getString(R.string.preference_file), MODE_PRIVATE);
        tv_title = findViewById(R.id.tv_title);

        String curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");

        tv_title.setText(curr_user);
    }

    public void showNeededGPA(View view) {

        Toast.makeText(this, "Target GPA Shown", Toast.LENGTH_SHORT).show();
    }

    public void viewSemesters(View view){
        Intent intent = new Intent(this, viewSemesters.class);
        startActivity(intent);
    }
}