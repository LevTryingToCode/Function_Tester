package com.example.debuggerapp;
import android.util.Log;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class GyroscopeActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private TextView textViewXaxis,textViewYaxis,textViewZaxis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        Button buttonQuit = findViewById(R.id.buttonQuit);
        textViewXaxis = findViewById(R.id.textViewXaxis);
        textViewYaxis = findViewById(R.id.textViewYaxis);
        textViewZaxis = findViewById(R.id.textViewZaxis);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager != null) {
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
        else {
            Toast.makeText(GyroscopeActivity.this,"There is no gyroscope available on this device!",Toast.LENGTH_LONG).show();
        }
        buttonQuit.setOnClickListener(v -> {
            Intent intent = new Intent(GyroscopeActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gyroscopeSensor != null) {
            sensorManager.registerListener(this,gyroscopeSensor,SensorManager.SENSOR_DELAY_UI);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            x = (Math.abs(x) < 0.005f) ? 0.0f : x;
            y = (Math.abs(y) < 0.005f) ? 0.0f : y;
            z = (Math.abs(z) < 0.005f) ? 0.0f : z;


            textViewXaxis.setText(String.format("%.2f", x));
            textViewYaxis.setText(String.format("%.2f", y));
            textViewZaxis.setText(String.format("%.2f", z));

            Log.d("GyroDebug", String.format("x: %.2f, y: %.2f, z: %.2f", x, y, z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
