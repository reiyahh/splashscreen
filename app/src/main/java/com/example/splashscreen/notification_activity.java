package com.example.splashscreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class notification_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification); // Your layout file

        // Bind views
        TextView messageView = findViewById(R.id.notification_message);
        Button clearBtn = findViewById(R.id.clear_button);

        // Load notifications from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("notifications", MODE_PRIVATE);
        String allMessages = prefs.getString("all_notifications", "No notifications yet.");
        messageView.setText(allMessages);

        // Clear button functionality
        clearBtn.setOnClickListener(v -> {
            prefs.edit().remove("all_notifications").apply();
            messageView.setText("Notifications cleared.");
        });
    }
}
