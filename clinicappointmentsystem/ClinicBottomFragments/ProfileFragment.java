package com.mustafa.clinicappointmentsystem.ClinicBottomFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mustafa.clinicappointmentsystem.Model.Department;
import com.mustafa.clinicappointmentsystem.Model.Doctor;
import com.mustafa.clinicappointmentsystem.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);

        SharedPreferences sh = binding.getRoot().getContext().getSharedPreferences("ID",Context.MODE_PRIVATE);
        userID = sh.getString("ID","");
        binding.userIdTextView.setText("ID: "+userID);


        Department department = new Department();
        department.getDepartmentName(binding.getRoot().getContext(),binding.userDepartmentTextView);

        Doctor doctor = new Doctor();
        doctor.getDoctorName(binding.getRoot().getContext(),binding.usernameTextView);

        return binding.getRoot();
    }
}