package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class create_acc extends AppCompatActivity {

    Button log_in, sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acc);

        log_in = findViewById(R.id.log_in);
        sign_in = findViewById(R.id.sign_in);

        log_in.setOnClickListener(v -> {
            startActivity(new Intent(create_acc.this, login.class));
        });

        sign_in.setOnClickListener(v -> {
            Intent intent = new Intent(create_acc.this, acc_create.class);
            startActivity(intent);
        });
    }
}
