package com.mustafa.clinicappointmentsystem.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;
import com.mustafa.clinicappointmentsystem.Adapter.AllPatientsAdapter;
import com.mustafa.clinicappointmentsystem.Interfaces.PatientInterface;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PatientInfo implements PatientInterface {

    private String PatientID,PatientName,PatientSurname;
    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;
    private String userID;

    public PatientInfo(String patientID, String patientName, String patientSurname) {
        PatientID = patientID;
        PatientName = patientName;
        PatientSurname = patientSurname;
    }

    public PatientInfo(){

    }

    public String getPatientID() {
        return PatientID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public String getPatientSurname() {
        return PatientSurname;
    }


    @Override
    public void getAllPatients(ArrayList<PatientInfo> arrayList, AllPatientsAdapter adapter) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Patient\";");
                while (resultSet.next()){

                    PatientID = resultSet.getString("patient_id");
                    PatientName = resultSet.getString("patient_Name");
                    PatientSurname = resultSet.getString("patient_surname");

                    PatientInfo patientInfo = new PatientInfo(PatientID,PatientName,PatientSurname);
                    arrayList.add(patientInfo);

                }
                adapter.notifyDataSetChanged();
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

    @Override
    public void createPatient(Context context, String patient_id, String patient_name, String patient_surname, String phone, char sex, int age, String patient_password,String confirmPassword) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        if(!patient_id.equals("") && !patient_name.equals("") && !patient_surname.equals("") && !phone.equals("") && !String.valueOf(age).equals("") && !patient_password.equals("")){
            if(patient_password.equals(confirmPassword)){
                connect = new ConnectToServer();
                connection = connect.getConnection();

                Thread thread = new Thread(() -> {
                    try {
                        statement = connection.createStatement();
                        resultSet = statement.executeQuery("INSERT INTO public.\"Patient\"(patient_id,patient_name,patient_surname,phone,sex,age,patient_password) " +
                                "VALUES('"+patient_id+"','"+patient_name+"','"+patient_surname+"','"+phone+"','"+sex+"','"+age+"','"+patient_password+"');");
                        Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }else{
                Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Fill in the required fields!", Toast.LENGTH_SHORT).show();
        }
        
    }

    @Override
    public void getPatientInformation(Context context, TextView id, TextView name) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        SharedPreferences sh = context.getSharedPreferences("PatientID", Context.MODE_PRIVATE);
        userID = sh.getString("patientID","");

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Patient\";");
                while (resultSet.next()){

                    PatientID = resultSet.getString("patient_id");
                    PatientName = resultSet.getString("patient_Name");
                    PatientSurname = resultSet.getString("patient_surname");

                    if(PatientID.equals(userID)){
                        id.setText(PatientID);
                        name.setText(PatientName + " " + PatientSurname);
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
