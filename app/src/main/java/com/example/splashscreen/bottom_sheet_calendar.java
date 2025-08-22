package com.example.splashscreen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

public class bottom_sheet_calendar extends BottomSheetDialogFragment {

    private String selectedDate = "";
    private String selectedTime = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_bottom_sheet_calendar, container, false);

        Button selectDate = view.findViewById(R.id.select_date);
        Button selectTime = view.findViewById(R.id.select_time);
        Button confirmBtn = view.findViewById(R.id.confirm_btn);

        selectDate.setOnClickListener(v -> showDatePicker());
        selectTime.setOnClickListener(v -> showTimePicker());

        confirmBtn.setOnClickListener(v -> {
            EditText nameInput = view.findViewById(R.id.name_input);
            EditText emailInput = view.findViewById(R.id.email_input);
            EditText bloodInput = view.findViewById(R.id.blood_type_input);
            EditText phoneInput = view.findViewById(R.id.phone_input);

            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String bloodType = bloodInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();

            if (selectedDate.isEmpty() || selectedTime.isEmpty()
                    || name.isEmpty() || email.isEmpty() || bloodType.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity(), calendar.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("bloodType", bloodType);
                intent.putExtra("phone", phone);
                intent.putExtra("date", selectedDate);
                intent.putExtra("time", selectedTime);
                startActivity(intent);
                dismiss();
            }
        });

        return view;
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) ->
                        selectedDate = (month + 1) + "/" + dayOfMonth + "/" + year,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(requireContext(),
                (view, hourOfDay, minute) ->
                        selectedTime = String.format("%02d:%02d", hourOfDay, minute),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true).show();
    }
}
