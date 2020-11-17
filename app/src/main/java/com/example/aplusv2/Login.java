package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences shared_pref;
    private final String USER_KEY = "username";

    User dummy_user = new User("user", "password");
    Semester dummy_semester = new Semester(dummy_user.getUserID(), "Semester1");
    Course dummy_course = new Course(dummy_user.getUserID(), dummy_semester.getSemesterID(), "1101CLASS", 4, 88.3, "B");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        shared_pref = Login.this.getSharedPreferences(getString(R.string.preference_file), MODE_PRIVATE);

        db_helper = new DatabaseHelper(Login.this);
    }

    public void onLogin(User u){
        SharedPreferences.Editor prefEditor = shared_pref.edit();
        try{
            prefEditor.putString(getString(R.string.USER_KEY), u.getUserID());
            prefEditor.apply();
        }
        catch (Exception e){
            Toast.makeText( Login.this, "ERROR in adding user to preferences", Toast.LENGTH_LONG).show();
        }

    }

    public void registerClicked(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void loginClicked(View view){

        db_helper = new DatabaseHelper(Login.this);

        /* grabs text from input fields */
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        List<User> user_list = db_helper.getAllUsers();

        for(int i = 0; i < user_list.size(); i++){
            if(username.equals(user_list.get(i).getUserID()) && password.equals(user_list.get(i).getPass())){
                onLogin(user_list.get(i));
                Toast.makeText( Login.this, "User logged in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return;
            }
        }
        String error = "Invalid username and password.";
        Toast.makeText( Login.this, error, Toast.LENGTH_SHORT).show();
    }

    /** This is called when "View Semesters" button is hit */
    public void viewGPA(View view){

    }
}