package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        shared_pref = PreferenceManager.getDefaultSharedPreferences(Login.this);

        db_helper = new DatabaseHelper(Login.this);
    }

    /* succesful login, update username key */
    public void onLogin(User u){
        SharedPreferences.Editor prefEditor = shared_pref.edit();
        try{
            prefEditor.putString("username", u.getUserID());
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

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        List<User> user_list = db_helper.getAllUsers();

        /* check username and pass against database */
        for(int i = 0; i < user_list.size(); i++){
            if(username.equals(user_list.get(i).getUserID()) && password.equals(user_list.get(i).getPass())){
                onLogin(user_list.get(i));
                Toast.makeText( Login.this, "User logged in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return;
            }
        }
        /* no match */
        String error = "Invalid username and password.";
        Toast.makeText( Login.this, error, Toast.LENGTH_SHORT).show();
    }

}