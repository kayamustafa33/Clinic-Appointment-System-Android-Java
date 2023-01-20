package com.mustafa.clinicappointmentsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafa.clinicappointmentsystem.Model.PatientInfo;
import com.mustafa.clinicappointmentsystem.databinding.RecyclerAllPatientsRowBinding;

import java.util.ArrayList;

public class AllPatientsAdapter extends RecyclerView.Adapter<AllPatientsAdapter.ViewHolder> {

    private ArrayList<PatientInfo> arrayList;
    private Context context;

    public AllPatientsAdapter(ArrayList<PatientInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllPatientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerAllPatientsRowBinding binding = RecyclerAllPatientsRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPatientsAdapter.ViewHolder holder, int position) {
        holder.binding.patientIdText.setText("Patient ID: "+arrayList.get(position).getPatientID());
        holder.binding.patientNameAndSurnameText.setText(arrayList.get(position).getPatientName() + " " + arrayList.get(position).getPatientSurname());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerAllPatientsRowBinding binding;
        public ViewHolder(RecyclerAllPatientsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
