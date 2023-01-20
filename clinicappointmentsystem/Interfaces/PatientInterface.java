package com.mustafa.clinicappointmentsystem.Interfaces;

import android.content.Context;
import android.widget.TextView;

import com.mustafa.clinicappointmentsystem.Adapter.AllPatientsAdapter;
import com.mustafa.clinicappointmentsystem.Model.PatientInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;

public interface PatientInterface {

    void getAllPatients(ArrayList<PatientInfo> arrayList, AllPatientsAdapter adapter);
    void createPatient(Context context,String patient_id,String patient_name,String patient_surname,String phone,char sex,int age,String patient_password,String confirmPassword);
    void getPatientInformation(Context context, TextView id, TextView name);
}
