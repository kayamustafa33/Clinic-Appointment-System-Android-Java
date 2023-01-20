package com.mustafa.clinicappointmentsystem.ClinicCreateTabFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustafa.clinicappointmentsystem.Adapter.PendingAppointmentAdapter;
import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.databinding.FragmentCheckAppointmentBinding;

import java.util.ArrayList;

public class CheckAppointmentFragment extends Fragment {

    private FragmentCheckAppointmentBinding binding;
    private ArrayList<AppointmentInfo> arrayList;
    private PendingAppointmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCheckAppointmentBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();
        binding.checkAppointmentRV.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        adapter = new PendingAppointmentAdapter(arrayList);
        binding.checkAppointmentRV.setAdapter(adapter);

        AppointmentInfo appointment = new AppointmentInfo();
        appointment.checkAppointments(arrayList,adapter,binding.thereIsNoData);

        return binding.getRoot();
    }
}