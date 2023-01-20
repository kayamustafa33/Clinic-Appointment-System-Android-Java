package com.mustafa.clinicappointmentsystem.ClinicBottomFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mustafa.clinicappointmentsystem.Adapter.AllAppointmentsAdapter;
import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.databinding.FragmentAllAppointmentsBinding;

import java.util.ArrayList;

public class AllAppointmentsFragment extends Fragment {

    private FragmentAllAppointmentsBinding binding;
    private ArrayList<AppointmentInfo> arrayList;
    private AllAppointmentsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllAppointmentsBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();
        binding.allAppointmentsRV.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        adapter = new AllAppointmentsAdapter(arrayList);
        binding.allAppointmentsRV.setAdapter(adapter);

        AppointmentInfo appointmentInfo = new AppointmentInfo();
        appointmentInfo.getAllAppointments(arrayList,adapter);

        return binding.getRoot();
    }
}