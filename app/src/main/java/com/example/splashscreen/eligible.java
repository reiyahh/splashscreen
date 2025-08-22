package com.example.splashscreen;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class eligible extends AppCompatActivity {

    private Button aplus, anegative, abplus, abnegative, oplus, onegative, bplus, bnegative, submitBtn;
    private EditText ageInput, weightInput;
    private boolean isRedCalendar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eligible);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize all buttons
        aplus = findViewById(R.id.aplus);
        anegative = findViewById(R.id.anegative);
        abplus = findViewById(R.id.abplus);
        abnegative = findViewById(R.id.abnegative);
        oplus = findViewById(R.id.oplus);
        onegative = findViewById(R.id.onegative);
        bplus = findViewById(R.id.bplus);
        bnegative = findViewById(R.id.bnegative);

        // Initialize input and button
        ageInput = findViewById(R.id.age_input);
        weightInput = findViewById(R.id.weight_input);
        submitBtn = findViewById(R.id.submit_btn);

        // Set toggle listeners
        setToggleColor(aplus);
        setToggleColor(anegative);
        setToggleColor(abplus);
        setToggleColor(abnegative);
        setToggleColor(oplus);
        setToggleColor(onegative);
        setToggleColor(bplus);
        setToggleColor(bnegative);

        // Validate on submit
        submitBtn.setOnClickListener(v -> {
            String ageText = ageInput.getText().toString().trim();
            String weightText = weightInput.getText().toString().trim();

            if (ageText.isEmpty() || weightText.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageText);
            int weight = Integer.parseInt(weightText);

            if (age < 15) {
                Toast.makeText(this, "You must be at least 15 years old to proceed.", Toast.LENGTH_SHORT).show();
            } else if (weight < 50) {
                Toast.makeText(this, "You are not eligible because of weight.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You are eligible! Proceed to appointment.", Toast.LENGTH_LONG).show();

                // ➤ Redirect to home activity
                Intent intent = new Intent(eligible.this, home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setToggleColor(Button button) {
        button.setOnClickListener(v -> {
            int color = isRedCalendar
                    ? ContextCompat.getColor(this, R.color.orange)
                    : ContextCompat.getColor(this, R.color.red);
            button.setBackgroundTintList(ColorStateList.valueOf(color));
            isRedCalendar = !isRedCalendar;
        });
    }
}
