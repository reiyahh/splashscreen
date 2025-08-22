package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class calendar extends AppCompatActivity {

    private Button calendar_color, button_color, noon_time, hapon, button_am, fourpm;
    private Button selectedDayButton = null;
    private Button selectedTimeButton = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);

        // Request Notification Permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        // Handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Intent data from bottom_sheet_calendar
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String bloodType = intent.getStringExtra("bloodType");
        String phone = intent.getStringExtra("phone");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        Log.d("Appointment", name + " " + date + " " + time);

        // Display in TextView
        TextView details = findViewById(R.id.details_text);
        details.setText("Name: " + name +
                "\nEmail: " + email +
                "\nBlood Type: " + bloodType +
                "\nPhone: " + phone +
                "\nDate: " + date +
                "\nTime: " + time);

        // Schedule notification
        scheduleNotification(name, date, time);

        // Button initialization
        calendar_color = findViewById(R.id.calendar_color); // Day 1
        button_color = findViewById(R.id.button_color);     // Day 2
        noon_time = findViewById(R.id.noon_time);           // Time 1
        hapon = findViewById(R.id.hapon);                   // Time 2
        button_am = findViewById(R.id.button_am);           // Time 3
        fourpm = findViewById(R.id.fourpm);                 // Time 4

        // Setup button behavior
        setupDaySelection(calendar_color);
        setupDaySelection(button_color);
        setupTimeSelection(noon_time);
        setupTimeSelection(hapon);
        setupTimeSelection(button_am);
        setupTimeSelection(fourpm);
    }

    private void scheduleNotification(String name, String dateStr, String timeStr) {
        try {
            String dateTime = dateStr + " " + timeStr; // e.g. "05/30/2025 14:00"
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
            Date parsedDate = sdf.parse(dateTime);

            if (parsedDate != null) {
                Calendar notifyTime = Calendar.getInstance();
                notifyTime.setTime(parsedDate);

                Intent notificationIntent = new Intent(this, Notification_reciever.class);
                notificationIntent.putExtra("name", name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        notifyTime.getTimeInMillis(),
                        pendingIntent
                );

                Log.d("Notification", "Scheduled for: " + notifyTime.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to schedule notification.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupDaySelection(Button dayButton) {
        dayButton.setOnClickListener(v -> {
            if (selectedDayButton != null) {
                selectedDayButton.setBackgroundTintList(
                        ColorStateList.valueOf(getResources().getColor(R.color.fuchsia))
                );
            }

            dayButton.setBackgroundTintList(
                    ColorStateList.valueOf(getResources().getColor(R.color.red))
            );

            selectedDayButton = dayButton;
            Toast.makeText(this, "Selected Day: " + dayButton.getText(), Toast.LENGTH_SHORT).show();
        });
    }

    private void setupTimeSelection(Button timeButton) {
        timeButton.setOnClickListener(v -> {
            if (selectedTimeButton != null) {
                selectedTimeButton.setBackgroundTintList(
                        ColorStateList.valueOf(getResources().getColor(R.color.fuchsia))
                );
            }

            timeButton.setBackgroundTintList(
                    ColorStateList.valueOf(getResources().getColor(R.color.red))
            );

            selectedTimeButton = timeButton;
            Toast.makeText(this, "Selected Time: " + timeButton.getText(), Toast.LENGTH_SHORT).show();
        });
    }
}
