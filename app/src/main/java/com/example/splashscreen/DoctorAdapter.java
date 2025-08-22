package com.example.splashscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private List<doctor> doctorList;
    private List<doctor> filteredList;
    private OnDoctorClickListener listener;

    // Interface for click handling
    public interface OnDoctorClickListener {
        void onDoctorClick(doctor selectedDoctor);
    }

    public DoctorAdapter(List<doctor> doctors, OnDoctorClickListener listener) {
        this.doctorList = doctors;
        this.filteredList = new ArrayList<>(doctors);
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        doctor current = filteredList.get(position);
        holder.name.setText(current.getName());
        holder.specialty.setText(current.getSpecialty());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDoctorClick(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(doctorList);
        } else {
            for (doctor d : doctorList) {
                if (d.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(d);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView name, specialty;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctorName);
            specialty = itemView.findViewById(R.id.doctorSpecialty);
        }
    }
}
