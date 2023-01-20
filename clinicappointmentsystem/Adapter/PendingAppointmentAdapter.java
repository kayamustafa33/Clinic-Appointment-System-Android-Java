package com.mustafa.clinicappointmentsystem.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mustafa.clinicappointmentsystem.Model.AppointmentInfo;
import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.databinding.RecyclerCheckAppointmentRowBinding;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PendingAppointmentAdapter extends RecyclerView.Adapter<PendingAppointmentAdapter.ViewHolder> {

    private ArrayList<AppointmentInfo> arrayList;

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;

    private String totalPatientName,totalDoctorName;

    public PendingAppointmentAdapter(ArrayList<AppointmentInfo> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PendingAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerCheckAppointmentRowBinding binding = RecyclerCheckAppointmentRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAppointmentAdapter.ViewHolder holder, int position) {
        holder.binding.checkPatientIDText.setText("ID: "+arrayList.get(position).getPatient_id());
        patientName(holder.binding.checkPatientNameText,arrayList.get(position).getPatient_id());
        doctorName(holder.binding.checkPatientDoctorIDText,arrayList.get(position).getDoctor_id());
        holder.binding.checkAppointmentDate.setText(String.valueOf(arrayList.get(position).getAppointment_date()));

        holder.binding.acceptAppointmentBtn.setOnClickListener(item -> {
            AppointmentInfo appointment = new AppointmentInfo();
            appointment.AcceptAppointment(arrayList.get(position).getPatient_id());
            item.setVisibility(View.INVISIBLE);
        });

        holder.binding.rejectAppointmentBtn.setOnClickListener(item -> {
            AppointmentInfo appointment = new AppointmentInfo();
            appointment.RejectAppointment(arrayList.get(position).getPatient_id());
            item.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerCheckAppointmentRowBinding binding;
        public ViewHolder(RecyclerCheckAppointmentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void patientName(TextView textView,String patientID){
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Patient\";");
                while (resultSet.next()){

                    if(patientID.equals(resultSet.getString("patient_id"))){
                        totalPatientName = resultSet.getString("patient_name") + " " + resultSet.getString("patient_surname");
                        textView.setText(totalPatientName);
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

    private void doctorName(TextView textView,String doctorID){
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Doctor\";");
                while (resultSet.next()){

                    if(doctorID.equals(resultSet.getString("doctor_id"))){
                        totalDoctorName = resultSet.getString("doctor_name") + " " + resultSet.getString("doctor_surname");
                        textView.setText(totalDoctorName);
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
