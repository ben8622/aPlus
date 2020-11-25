package com.example.aplusv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;

    // variables
    Animation topAnim, bottomAnim;
    ImageView logo;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);

        // animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // hooks
        description = findViewById(R.id.textView1);
        logo = findViewById(R.id.img_logo);

        description.setAnimation(bottomAnim);
        logo.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splashscreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}