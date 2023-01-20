package com.mustafa.clinicappointmentsystem.ClinicBottomFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mustafa.clinicappointmentsystem.Adapter.AllPatientsAdapter;
import com.mustafa.clinicappointmentsystem.Model.PatientInfo;
import com.mustafa.clinicappointmentsystem.databinding.FragmentAllPatientsBinding;
import java.util.ArrayList;

public class AllPatientsFragment extends Fragment {

    private FragmentAllPatientsBinding binding;
    private ArrayList<PatientInfo> arrayList;
    private AllPatientsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllPatientsBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();
        binding.AllPatientsRV.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        adapter = new AllPatientsAdapter(arrayList,binding.getRoot().getContext());
        binding.AllPatientsRV.setAdapter(adapter);

        PatientInfo patientInfo = new PatientInfo();
        patientInfo.getAllPatients(arrayList,adapter);

        return binding.getRoot();
    }
}