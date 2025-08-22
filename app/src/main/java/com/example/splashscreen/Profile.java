package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private ShapeableImageView profileImage;
    private ImageView logout_btn, policy_btn, help_icon;
    private TextView userEmailText, setting_btn;
    private Uri selectedImageUri;
    private SharedPreferences prefs;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    profileImage.setImageURI(selectedImageUri);
                    saveProfileImageUri();
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profileImage = findViewById(R.id.profile_image);
        logout_btn = findViewById(R.id.logout_btn);
        policy_btn = findViewById(R.id.policy_btn);
        setting_btn = findViewById(R.id.setting_btn);
        userEmailText = findViewById(R.id.userEmailText);
        help_icon = findViewById(R.id.help_icon);

        prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String email = prefs.getString("email", "No user logged in");
        String imageUri = prefs.getString("profile_image_uri", null);

        userEmailText.setText("Logged in as: " + email);

        if (imageUri != null) {
            profileImage.setImageURI(Uri.parse(imageUri));
        }

        profileImage.setOnClickListener(v -> openImagePicker());

        policy_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, privacy.class);
            intent.putExtra("source", "policy_button");
            startActivity(intent);
        });

        setting_btn.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(Profile.this, SETTINGS.class);
            startActivity(settingsIntent);
        });

        help_icon.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, HelpActivity.class);
            startActivity(intent);
        });

        logout_btn.setOnClickListener(v -> showLogoutDialog());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void saveProfileImageUri() {
        String imagePath = (selectedImageUri != null) ? selectedImageUri.toString() : null;
        prefs.edit().putString("profile_image_uri", imagePath).apply();
        Toast.makeText(this, "Profile image saved", Toast.LENGTH_SHORT).show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("You want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    prefs.edit().clear().apply();
                    Toast.makeText(Profile.this, "Logged out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile.this, acc_create.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
