package com.mustafa.clinicappointmentsystem.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.databinding.RecyclerAllAppointmentRowBinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AllAppointmentsAdapter extends RecyclerView.Adapter<AllAppointmentsAdapter.ViewHolder> {

    private ArrayList<AppointmentInfo> arrayList;

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;


    public AllAppointmentsAdapter(ArrayList<AppointmentInfo> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AllAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerAllAppointmentRowBinding binding = RecyclerAllAppointmentRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAppointmentsAdapter.ViewHolder holder, int position) {
        holder.binding.appointmentIDText.setText(arrayList.get(position).getAppointment_id());
        holder.binding.appointmentDateText.setText(String.valueOf(arrayList.get(position).getAppointment_date()));

        checkDoctorName(holder.binding.appointmentDoctorNameText,arrayList.get(position).getDoctor_id());
        checkPatientName(holder.binding.appointmentPatientNameText,arrayList.get(position).getPatient_id());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerAllAppointmentRowBinding binding;
        public ViewHolder(RecyclerAllAppointmentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void checkDoctorName(TextView doctorName,String doctorID){
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Doctor\";");
                while (resultSet.next()){

                    String id = resultSet.getString("doctor_id");

                    if(id.equals(doctorID)){
                        doctorName.setText("Doctor's name: "+resultSet.getString("doctor_name") + " " + resultSet.getString("doctor_surname"));
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void checkPatientName(TextView patientName,String patientID){
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Patient\";");
                while (resultSet.next()){

                    String id = resultSet.getString("patient_id");

                    if(id.equals(patientID)){
                        patientName.setText("Patient's name: "+resultSet.getString("patient_name") + " " + resultSet.getString("patient_surname"));
                    }


                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }
}
