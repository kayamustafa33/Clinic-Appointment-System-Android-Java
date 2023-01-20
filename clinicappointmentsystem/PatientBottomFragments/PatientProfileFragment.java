package com.mustafa.clinicappointmentsystem.PatientBottomFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.Model.PatientInfo;
import com.mustafa.clinicappointmentsystem.R;
import com.mustafa.clinicappointmentsystem.databinding.FragmentPatientProfileBinding;

public class PatientProfileFragment extends Fragment {

    private FragmentPatientProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientProfileBinding.inflate(inflater,container,false);

        PatientInfo profileData = new PatientInfo();
        profileData.getPatientInformation(binding.getRoot().getContext(),binding.patientProfilePatientID,binding.patientProfilePatientName);


        return binding.getRoot();
    }
}