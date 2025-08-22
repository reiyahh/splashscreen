package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Consult extends AppCompatActivity {

    ListView doctorList;
    String[] doctors = {"Dr. Luwe", "Dr. Mingsie", "Dr. Renzie"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        doctorList = findViewById(R.id.doctorList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctors);
        doctorList.setAdapter(adapter);

        doctorList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDoctor = doctors[position];
            Intent intent = new Intent(Consult.this, MessagesActivity.class);
            intent.putExtra("doctor_name", selectedDoctor);
            startActivity(intent);
        });
    }
}
