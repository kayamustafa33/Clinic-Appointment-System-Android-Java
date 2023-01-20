package com.mustafa.clinicappointmentsystem.PatientBottomFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustafa.clinicappointmentsystem.Adapter.MyAppointmentHistoryAdapter;
import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.R;
import com.mustafa.clinicappointmentsystem.databinding.FragmentAppointmentHistoryBinding;

import java.util.ArrayList;

public class AppointmentHistoryFragment extends Fragment {

    private FragmentAppointmentHistoryBinding binding;
    private ArrayList<AppointmentInfo> arrayList;
    private MyAppointmentHistoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentHistoryBinding.inflate(inflater,container,false);

        ConnectToServer connect = new ConnectToServer();
        arrayList = new ArrayList<>();
        binding.patientHistoryRV.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        adapter = new MyAppointmentHistoryAdapter(arrayList);
        binding.patientHistoryRV.setAdapter(adapter);

        AppointmentInfo myAppointment = new AppointmentInfo();
        myAppointment.getMyAppointmentHistory(binding.getRoot().getContext(),arrayList,adapter);

        return binding.getRoot();
    }
}