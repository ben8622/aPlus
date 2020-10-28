package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** This is called when "View Semesters" button is hit */
    public void viewSemesters(View view){
        Intent intent = new Intent(this, viewSemesters.class);
        startActivity(intent);
    }
}