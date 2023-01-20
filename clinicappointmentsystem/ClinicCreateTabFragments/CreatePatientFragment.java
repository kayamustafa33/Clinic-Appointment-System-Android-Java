package com.mustafa.clinicappointmentsystem.ClinicCreateTabFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mustafa.clinicappointmentsystem.Model.PatientInfo;
import com.mustafa.clinicappointmentsystem.R;
import com.mustafa.clinicappointmentsystem.databinding.FragmentCreatePatientBinding;

import java.util.ArrayList;

public class CreatePatientFragment extends Fragment {

    private FragmentCreatePatientBinding binding;
    private ArrayAdapter<String> adapterItems;
    private ArrayList<String> arrayList;
    private String gender = "M";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePatientBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();
        arrayList.add("M");
        arrayList.add("F");

        adapterItems = new ArrayAdapter<String>(binding.getRoot().getContext(), R.layout.list_item,arrayList);
        binding.genderAutoCompleteText.setAdapter(adapterItems);

        binding.genderAutoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }
        });

        createPatient();

        return binding.getRoot();
    }

    private void createPatient(){
        binding.createPatientBtn.setOnClickListener(item -> {
            PatientInfo patient = new PatientInfo();
            patient.createPatient(binding.getRoot().getContext(),
                    binding.createPatientId.getText().toString().trim(),
                    binding.createPatientName.getText().toString(),
                    binding.createPatientSurname.getText().toString(),
                    binding.createPatientPhone.getText().toString().trim(),
                    gender.charAt(0),
                    Integer.parseInt(binding.createPatientAge.getText().toString()),
                    binding.createPatientPassword.getText().toString().trim(),
                    binding.createPatientPasswordConfirm.getText().toString().trim());
        });
    }
}