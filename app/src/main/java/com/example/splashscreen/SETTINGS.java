package com.example.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SETTINGS extends AppCompatActivity {

    private Switch switchNotifications;
    private Button btnChangePassword, btnDeleteAccount;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        switchNotifications = findViewById(R.id.switch_notifications);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnDeleteAccount = findViewById(R.id.btn_delete_account);
        prefs = getSharedPreferences("user_data", MODE_PRIVATE);

        // DARK MODE switch
        Switch darkModeSwitch = findViewById(R.id.switch_dark_mode);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        darkModeSwitch.setChecked(isDarkMode);
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });

        // LANGUAGE spinner
        Spinner languageSpinner = findViewById(R.id.language_dropdown);
        String savedLang = prefs.getString("selected_language", "English");
        String[] languages = getResources().getStringArray(R.array.language_options);
        int selectedIndex = java.util.Arrays.asList(languages).indexOf(savedLang);
        languageSpinner.setSelection(selectedIndex);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = languages[position];
                prefs.edit().putString("selected_language", selectedLanguage).apply();
                Toast.makeText(SETTINGS.this, "Language set to " + selectedLanguage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // CONTACT SUPPORT
        Button supportButton = findViewById(R.id.btn_contact_support);
        supportButton.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@yourapp.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello,\n\nI need help with...");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(SETTINGS.this, "No email app installed.", Toast.LENGTH_SHORT).show();
            }
        });

        // Load notification setting
        boolean isNotificationsEnabled = prefs.getBoolean("notifications_enabled", true);
        switchNotifications.setChecked(isNotificationsEnabled);

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply();
            Toast.makeText(SETTINGS.this, "Notifications " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });

        // Change Password
        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(SETTINGS.this, changepassword.class);
            startActivity(intent);
        });

        // Delete Account
        btnDeleteAccount.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("This will permanently delete your account data. Continue?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    prefs.edit().clear().apply();
                    Toast.makeText(SETTINGS.this, "Account deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SETTINGS.this, acc_create.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
