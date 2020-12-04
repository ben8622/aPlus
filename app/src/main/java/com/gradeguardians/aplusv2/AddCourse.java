package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/* CALLED WHEN btn_add CLICKED ON viewCourses */
public class AddCourse extends AppCompatActivity {

    EditText et_course_id, et_course_grade, et_course_weight;
    Button btn_confirm;

    Course c;

    DatabaseHelper db_helper;
    SharedPreferences shared_pref;

    String curr_user;
    String curr_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);

        db_helper = new DatabaseHelper(AddCourse.this);
        shared_pref = PreferenceManager.getDefaultSharedPreferences(AddCourse.this);

        curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");
        curr_semester = shared_pref.getString(getString(R.string.SEM_KEY), "error");

        et_course_id = findViewById(R.id.et_course_id);
        et_course_grade = findViewById(R.id.et_course_grade);
        et_course_weight = findViewById(R.id.et_course_weight);

        btn_confirm = findViewById(R.id.btn_confirm);
    }

    public void confirmClicked(View view){

        /* grabbing values and creating course object */
        String course_id = et_course_id.getText().toString();
        double course_grade = -1;
        try {
            course_grade = Double.parseDouble(et_course_grade.getText().toString());
        }catch (NumberFormatException e){

        }
        int course_weight = -1;
        try {
            course_weight = Integer.parseInt(et_course_weight.getText().toString());
        }catch (NumberFormatException e) {

        }
        c = new Course(curr_user, curr_semester, course_id, course_weight, course_grade);

        /* adding course to database and displaying result */
        if(course_grade == -1 || course_weight == -1 ) {
            Toast.makeText( AddCourse.this, "ERROR: Course Weight or Grade not valid.", Toast.LENGTH_SHORT).show();
        }else if(db_helper.addCourse(c)){
            Toast.makeText( AddCourse.this, "Course added.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText( AddCourse.this, "ERROR: Adding course failed.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}