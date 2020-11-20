package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourse extends AppCompatActivity {

    EditText et_course_id, et_course_grade, et_course_weight;
    Button btn_confirm;
    Course c;
    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);

        db_helper = new DatabaseHelper(AddCourse.this);

        et_course_id = findViewById(R.id.et_course_id);
        et_course_grade = findViewById(R.id.et_course_grade);
        et_course_weight = findViewById(R.id.et_course_weight);

        btn_confirm = findViewById(R.id.btn_confirm);
    }

    public void confirmClicked(View view){

        /* grabbing values and creating couorse object */
        String course_id = et_course_id.getText().toString();
        double course_grade = Double.parseDouble(et_course_grade.getText().toString());
        int course_weight = Integer.parseInt(et_course_weight.getText().toString());
        c = new Course("user", "Semester1", course_id, course_weight, course_grade);

        /* adding course to database and displaying result */
        if(db_helper.addCourse(c)){
            Toast.makeText( AddCourse.this, "Course added.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText( AddCourse.this, "ERROR: Adding course failed.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}