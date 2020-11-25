package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper db_helper = new DatabaseHelper(Register.this);
    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }


    public void registerClicked(View view){
        try{

            String username = et_username.getText().toString();
            String password = et_password.getText().toString();
            User u = new User(username, password);

            db_helper = new DatabaseHelper(Register.this);

            db_helper.addUser(u);

            Toast.makeText( Register.this, "Added User succesfully.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText( Register.this, "USERNAME ALREADY EXISTS: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    public void returnClicked(View view){
        finish();
    }
}