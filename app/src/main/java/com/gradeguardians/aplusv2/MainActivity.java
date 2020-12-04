package com.gradeguardians.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences shared_pref;
    DatabaseHelper db_helper;

    TextView tv_title, tv_cumGPA, tv_targetGPA;
    EditText et_targetGPA;
    Button btn_targetGPA;

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
        tv_targetGPA = findViewById(R.id.tv_targetGPA);
        et_targetGPA = findViewById(R.id.et_targetGPA);
        btn_targetGPA = findViewById(R.id.btn_targetGPA);

        curr_user = shared_pref.getString(getString(R.string.USER_KEY), "error");
        u = db_helper.grabOneUser(curr_user);

        tv_targetGPA.setText("0.0");

        updateViews();

    }
    @Override
    protected void onRestart() {
        super.onRestart();

        updateViews();
    }
    @Override
    protected void onResume(){
        super.onResume();

        updateViews();

    }

    public void viewSemesters(View view){
        Intent intent = new Intent(this, viewSemesters.class);
        startActivity(intent);
    }

    public void targetGpaClicked(View view){
        double target = -1;
        try {
            target = Double.parseDouble(et_targetGPA.getText().toString());
        }catch(NumberFormatException e){
            Toast.makeText(this, "Must Enter Valid GPA", Toast.LENGTH_SHORT).show();
        }

        /* avoid extra computation if the input is wrong */
        if(target < 0.0 || target > 4.0){
            et_targetGPA.setError("Invalid target GPA");
            return;
        }

        double total_sems = db_helper.getAllSemester(curr_user).size();
        double GPA = db_helper.calcAllButOneGPA(curr_user);

        double needed_gpa = target * total_sems - GPA;

        if(needed_gpa < 0.0 || needed_gpa > 4.0){
            et_targetGPA.setError("That's impossible for you this semester!");
            return;
        }
        else{
            tv_targetGPA.setText(String.format("%.2f", needed_gpa));
        }
    }

    /* updates values for views */
    public void updateViews(){
        tv_title.setText(curr_user + "'s Homepage");
        db_helper.calcCumGPA(curr_user);
        u = db_helper.grabOneUser(curr_user);
        tv_cumGPA.setText(u.getGPA());
    }
}