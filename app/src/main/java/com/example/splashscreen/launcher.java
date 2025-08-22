package com.example.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class launcher extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String password = prefs.getString("password", null);

        if (email == null || password == null) {

            startActivity(new Intent(this, create_acc.class));
        } else {

            startActivity(new Intent(this, acc_create.class));
        }


        finish();
    }
}
