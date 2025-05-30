    package com.example.debuggerapp;

    import android.content.ClipData;
    import android.content.ClipboardManager;
    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.app.AppCompatDelegate;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.journeyapps.barcodescanner.ScanContract;
    import com.journeyapps.barcodescanner.ScanOptions;

    public class QRActivity extends AppCompatActivity {
        Button buttonQuit, buttonCopy, buttonOpen, buttonOpenReader;
        TextView textViewLink;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_qractivity);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            buttonOpenReader = findViewById(R.id.buttonOpenReader);
            buttonCopy = findViewById(R.id.buttonCopy);
            buttonQuit = findViewById(R.id.buttonQuit);
            buttonOpen = findViewById(R.id.buttonOpen);
            textViewLink = findViewById(R.id.textViewLink);

            buttonQuit.setOnClickListener(v -> {
                Intent intent = new Intent(QRActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
            buttonOpen.setOnClickListener(v ->{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(textViewLink.getText().toString()));
                startActivity(intent);
            });
            buttonCopy.setOnClickListener(v->{
                String textToCopy = textViewLink.getText().toString();
                if(!textToCopy.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label",textToCopy);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(QRActivity.this, "Text copied into the clipboard", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(QRActivity.this, "There's no text to copy!", Toast.LENGTH_SHORT).show();
                }
            });
            buttonOpenReader.setOnClickListener(v->startQrReader());
        }

        private final androidx.activity.result.ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
                    if (result.getContents() != null) {
                        textViewLink.setText(result.getContents());
                        buttonCopy.setVisibility(View.VISIBLE);
                        buttonOpen.setVisibility(View.VISIBLE);
                    } else {
                        buttonCopy.setVisibility(View.GONE);
                        buttonOpen.setVisibility(View.GONE);
                    }
                });

        private void startQrReader() {
            ScanOptions options = new ScanOptions();
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CameraActivity.class);
            barcodeLauncher.launch(options);
        }
    }