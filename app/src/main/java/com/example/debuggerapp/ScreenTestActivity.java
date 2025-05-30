package com.example.debuggerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScreenTestActivity extends AppCompatActivity {
    Button buttonQuit, buttonStop, buttonStart;
    TextView textViewTitle;
    RelativeLayout main;
    private final Handler handler = new Handler();
    private Runnable colorChange;
    private boolean isRun = false;
    private int colorIDX = 0;
    private final int[] colors = {
            Color.WHITE,
            Color.BLACK,
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW
    };
    private int stopButtonClicks = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_screen_test);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        main = findViewById(R.id.main);
        main.setContentDescription("Screen test area");
        textViewTitle = findViewById(R.id.textViewTitle);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonQuit = findViewById(R.id.buttonQuit);


        buttonQuit.setOnClickListener(v -> {
            Intent intent = new Intent(ScreenTestActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        buttonStop.setOnClickListener(v -> {
            if (isRun) {
                stopButtonClicks++;
                if (stopButtonClicks >= 3) {
                    isRun = false;
                    handler.removeCallbacks(colorChange);
                    buttonStop.setVisibility(View.GONE);
                    buttonStart.setVisibility(View.VISIBLE);
                    textViewTitle.setVisibility(View.VISIBLE);
                    buttonQuit.setVisibility(View.VISIBLE);
                }
                main.setBackgroundColor(Color.WHITE);
            }
        });

        buttonStart.setOnClickListener(v -> {
            if (!isRun) {
                isRun = true;
                handler.post(colorChange);
                stopButtonClicks = 0;
                buttonStop.setVisibility(View.GONE);
                textViewTitle.setVisibility(View.GONE);
                buttonQuit.setVisibility(View.GONE);
                buttonStart.setVisibility(View.GONE);
            } else {
                buttonStop.setVisibility(View.GONE);
                buttonStart.setVisibility(View.VISIBLE);
                textViewTitle.setVisibility(View.VISIBLE);
                buttonQuit.setVisibility(View.VISIBLE);
            }
        });

        main.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                stopButtonClicks++;
                if (stopButtonClicks >= 3) {
                    buttonStop.setVisibility(View.VISIBLE);
                }
                v.performClick();
            }
            return true;
        });

        colorChange = new Runnable() {
            @Override
            public void run() {
                main.setBackgroundColor(colors[colorIDX]);
                colorIDX = (colorIDX + 1) % colors.length;
                handler.postDelayed(this, 2000);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(colorChange);
    }
}
