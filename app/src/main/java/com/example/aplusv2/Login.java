package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Login extends AppCompatActivity {

    Button btn_login, btn_register;
    EditText et_username, et_password;
    DatabaseHelper db_helper;

    User dummy_user = new User("user", "password");
    Semester dummy_semester = new Semester(dummy_user.getUserID(), "Semester1");
    Course dummy_course = new Course(dummy_user.getUserID(), dummy_semester.getSemesterID(), "1101CLASS", 4, 88.3, "B");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_login =findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        db_helper = new DatabaseHelper(Login.this);



    }

    public void registerClicked(View view){
        try{

            db_helper = new DatabaseHelper(Login.this);

            db_helper.addUser(dummy_user);
            db_helper.addSemester(dummy_semester);
            db_helper.addCourse(dummy_course);

            Toast.makeText( Login.this, "Added User succesfully.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText( Login.this, e.toString(), Toast.LENGTH_SHORT).show();
            String TAG = "reg_clicked";
            Log.e(TAG, "registerClicked: ", e);
        }
    }

    /** This is called when "View Semesters" button is hit */
    public void viewGPA(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}