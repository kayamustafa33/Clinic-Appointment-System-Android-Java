package com.mustafa.clinicappointmentsystem.Interfaces;

import android.content.Context;
import android.widget.TextView;

import com.mustafa.clinicappointmentsystem.Adapter.AllAppointmentsAdapter;
import com.mustafa.clinicappointmentsystem.Adapter.MyAppointmentHistoryAdapter;
import com.mustafa.clinicappointmentsystem.Adapter.PendingAppointmentAdapter;
import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.Model.PatientInfo;

import java.util.ArrayList;

public interface AppointmentITF {

    void getAllAppointments(ArrayList<AppointmentInfo> arrayList, AllAppointmentsAdapter adapter);
    void checkAppointments(ArrayList<AppointmentInfo> arrayList, PendingAppointmentAdapter adapter, TextView textView);
    void createAppointment(Context context, String appointment_id, String doctor_id, String patient_id, String health_issue, String status,String appointment_date);
    void getMyAppointmentHistory(Context context,ArrayList<AppointmentInfo> arrayList, MyAppointmentHistoryAdapter adapter);
    void requestAppointment(Context context,String appointment_id,String doctor_id,String health_issue,String appointment_date);
    void AcceptAppointment(String patientID);
    void RejectAppointment(String patientID);
}
