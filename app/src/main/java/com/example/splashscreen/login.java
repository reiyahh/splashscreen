package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private Button login_btn;
    private EditText inputemail, passwordinput;
    private TextView forgot_Password;


    private final String DUMMY_USERNAME = "myenzie";
    private final String DUMMY_PASSWORD = "0610";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if already logged in
        boolean isLoggedIn = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                .getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Toast.makeText(this, "You are now successfully logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(login.this, home.class));
            finish();
            return;
        }

        setContentView(R.layout.login);

        inputemail = findViewById(R.id.inputemail);
        passwordinput = findViewById(R.id.passwordinput);
        login_btn = findViewById(R.id.login_btn);
        forgot_Password = findViewById(R.id.forgot_password);

        login_btn.setOnClickListener(v -> {
            String username = inputemail.getText().toString().trim();
            String password = passwordinput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both Email/Phonenumber and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (username.equals(DUMMY_USERNAME) && password.equals(DUMMY_PASSWORD)) {
                getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                        .edit()
                        .putBoolean("isLoggedIn", true)
                        .apply();

                Toast.makeText(this, "You are successfully logged in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(login.this, home.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        forgot_Password.setOnClickListener(v -> {
            String userInput = inputemail.getText().toString().trim();

            if (userInput.isEmpty()) {
                Toast.makeText(this, "Please enter your email or phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userInput.contains("@")) {
                sendEmail(userInput);
            } else {
                sendSMS(userInput);
            }
        });
    }


    private void sendEmail(String email) {
        Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_LONG).show();
    }


    private void sendSMS(String phoneNumber) {
        Toast.makeText(this, "Verification code sent to " + phoneNumber, Toast.LENGTH_LONG).show();
    }
}
