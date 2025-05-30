package com.example.debuggerapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    TextView textViewDebuggerTitle;
    Button buttonAvailability, buttonPicture, buttonScreen, buttonQR, buttonGyroscopeTest, buttonKeyboard, buttonDelete;
    ImageView imageViewPicSave;

    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        textViewDebuggerTitle = findViewById(R.id.textViewDebuggerTitle);
        buttonAvailability = findViewById(R.id.buttonAvailability);
        buttonPicture = findViewById(R.id.buttonPicture);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonScreen = findViewById(R.id.buttonScreen);
        buttonQR = findViewById(R.id.buttonQR);
        buttonGyroscopeTest = findViewById(R.id.buttonGyroscopeTest);
        buttonKeyboard = findViewById(R.id.buttonKeyboard);
        imageViewPicSave = findViewById(R.id.imageViewPicSave);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
        } else {

            loadImageFromSharedPreferences();
        }

        buttonDelete.setOnClickListener(v -> {
            imageViewPicSave.setImageURI(null);
            buttonDelete.setVisibility(View.GONE);
            SharedPreferences sharedPreferences1 = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.remove("image_uri");
            editor.apply();
            Toast.makeText(this, "Image Deleted!", Toast.LENGTH_LONG).show();
        });

        buttonAvailability.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SensorsActivity.class);
            startActivity(intent);
        });
        buttonPicture.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PictureTestActivity.class);
            startActivity(intent);
        });
        buttonScreen.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScreenTestActivity.class);
            startActivity(intent);
        });
        buttonQR.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QRActivity.class);
            startActivity(intent);
        });
        buttonGyroscopeTest.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GyroscopeActivity.class);
            startActivity(intent);
        });
        buttonKeyboard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KeyboardActivity.class);
            startActivity(intent);
        });
    }

    private void loadImageFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String imageUriString = sharedPreferences.getString("image_uri", null);

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            imageViewPicSave.setImageURI(imageUri);
            buttonDelete.setVisibility(View.VISIBLE);
        } else {
            buttonDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImageFromSharedPreferences();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
