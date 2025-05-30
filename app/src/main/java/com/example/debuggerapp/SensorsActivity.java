package com.example.debuggerapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class SensorsActivity extends AppCompatActivity {
    Button buttonQuit;
    SensorManager  sensorManager;
    List<Sensor> sensorList;
    LinearLayout sensorListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensors);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        buttonQuit = findViewById(R.id.buttonQuit);
        buttonQuit.setOnClickListener(v -> {
            Intent intent = new Intent(SensorsActivity.this,MainActivity.class);
            startActivity(intent);
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorListLayout = findViewById(R.id.sensorListLayout);

        for(Sensor sensor : sensorList) {
            Typeface typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
            TextView tv = new TextView(this);
            tv.setText(sensor.getName());
            tv.setTextSize(20);
            tv.setTypeface(typeface);
            tv.setPadding(10,15,10,15);
            tv.setTextColor(Color.BLACK);
            sensorListLayout.addView(tv);
        }

    }

}