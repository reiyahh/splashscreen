package com.example.splashscreen;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        backBtn = findViewById(R.id.help_back_btn);

        backBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Returning to previous screen", Toast.LENGTH_SHORT).show();
            finish(); // Go back to previous screen
        });
    }
}
