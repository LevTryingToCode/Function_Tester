package com.example.debuggerapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class KeyboardActivity extends AppCompatActivity {
    Button buttonQuit,buttonDelete,buttonCopy;
    EditText editTextText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_keyboard);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonQuit = findViewById(R.id.buttonQuit);
        editTextText = findViewById(R.id.editTextText);
        buttonCopy = findViewById(R.id.buttonCopy);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        buttonQuit.setOnClickListener(v -> {
            Intent intent = new Intent(KeyboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        buttonCopy.setOnClickListener(v->{
            String textToCopy = editTextText.getText().toString();

            if(!textToCopy.isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label",textToCopy);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(KeyboardActivity.this, "Text copied into the clipboard", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(KeyboardActivity.this, "There's no text to copy!", Toast.LENGTH_SHORT).show();
            }
        });
        buttonDelete.setOnClickListener(v -> {
            editTextText.setText("");
            Toast.makeText(this,"Text Deleted!",Toast.LENGTH_SHORT).show();
        });
    }
}
