package com.gradeguardians.aplusv2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSemester extends AppCompatActivity {

    EditText et_course_id;
    Button btn_confirm;
    Semester s;
    DatabaseHelper db_helper;
    String curr_user;
    SharedPreferences shared_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_semester);

        shared_pref = PreferenceManager.getDefaultSharedPreferences(AddSemester.this);

        curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");

        db_helper = new DatabaseHelper(AddSemester.this);

        et_course_id = findViewById(R.id.et_course_id);

        btn_confirm = findViewById(R.id.btn_confirm);
    }

    public void confirmClicked(View view){

        /* grabbing values and creating semester object */
        Toast.makeText( AddSemester.this, "Semester test added.", Toast.LENGTH_SHORT).show();
        String course_id = et_course_id.getText().toString();
        s = new Semester(curr_user, course_id);

        /* adding semester to database and displaying result */
        if(db_helper.addSemester(s)){
            Toast.makeText( AddSemester.this, "Semester added.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText( AddSemester.this, "ERROR: Adding semester failed.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
