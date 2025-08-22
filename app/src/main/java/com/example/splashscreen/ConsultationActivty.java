package com.example.splashscreen;

import android.content.Intent; // ✅ Added import for Intent
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ConsultationActivty extends AppCompatActivity {

    EditText searchBar;
    RecyclerView doctorRecyclerView;
    DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultation_activty);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        searchBar = findViewById(R.id.searchBar);
        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<doctor> doctors = new ArrayList<>();
        doctors.add(new doctor("Dr. Luwe", "Hematologist", "luwe.kingkong@hospital.org"));
        doctors.add(new doctor("Dr. Mingsie", "Transfusion Specialist", "+1234567890"));
        doctors.add(new doctor("Dr. Renzie", "Blood Donation Consultant", "liu.wei@healthcare.com"));


        adapter = new DoctorAdapter(doctors, selectedDoctor -> {
            Intent intent = new Intent(ConsultationActivty.this, MessagesActivity.class);
            intent.putExtra("doctor_name", selectedDoctor.getName());
            startActivity(intent);
        });

        doctorRecyclerView.setAdapter(adapter);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
