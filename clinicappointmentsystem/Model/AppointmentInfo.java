package com.mustafa.clinicappointmentsystem.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mustafa.clinicappointmentsystem.Adapter.AllAppointmentsAdapter;
import com.mustafa.clinicappointmentsystem.Adapter.MyAppointmentHistoryAdapter;
import com.mustafa.clinicappointmentsystem.Adapter.PendingAppointmentAdapter;
import com.mustafa.clinicappointmentsystem.Interfaces.AppointmentITF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentInfo implements AppointmentITF {

    private String appointment_id;
    private String doctor_id;
    private String patient_id;
    private Date appointment_date;
    private String health_issue;
    private String status;

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;
    private String userID;
    private Context context;

    public AppointmentInfo(String appointment_id, String doctor_id, String patient_id, Date appointment_date, String health_issue, String status) {
        this.appointment_id = appointment_id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.appointment_date = appointment_date;
        this.health_issue = health_issue;
        this.status = status;
    }

    public AppointmentInfo(){

    }


    @Override
    public void getAllAppointments(ArrayList<AppointmentInfo> arrayList, AllAppointmentsAdapter adapter) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Appointment\";");
                while (resultSet.next()){

                     appointment_id = resultSet.getString("appointment_id");
                     doctor_id = resultSet.getString("doctor_id");
                     patient_id = resultSet.getString("patient_id");
                     appointment_date = resultSet.getDate("appointment_date");
                     health_issue = resultSet.getString("health_issue");
                     status = resultSet.getString("status");

                     AppointmentInfo appointmentInfo = new AppointmentInfo(appointment_id,doctor_id,patient_id,appointment_date,health_issue,status);
                     arrayList.add(appointmentInfo);

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
    public void checkAppointments(ArrayList<AppointmentInfo> arrayList, PendingAppointmentAdapter adapter, TextView textView) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Appointment\";");
                while (resultSet.next()){

                    appointment_id = resultSet.getString("appointment_id");
                    doctor_id = resultSet.getString("doctor_id");
                    patient_id = resultSet.getString("patient_id");
                    appointment_date = resultSet.getDate("appointment_date");
                    health_issue = resultSet.getString("health_issue");
                    status = resultSet.getString("status");

                    if(status.equals("Pending")){
                        AppointmentInfo appointmentInfo = new AppointmentInfo(appointment_id,doctor_id,patient_id,appointment_date,health_issue,status);
                        arrayList.add(appointmentInfo);
                    }

                }

                if(arrayList.isEmpty()){
                    textView.setVisibility(View.VISIBLE);
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
    public void createAppointment(Context context,String appointment_id, String doctor_id, String patient_id, String health_issue, String status,String appointment_date) {
        if(appointment_id.equals("") || patient_id.equals("") || appointment_date.equals("") || status.equals("")){
            Toast.makeText(context, "Fill in the required fields!", Toast.LENGTH_LONG).show();
        }else{
            connect = new ConnectToServer();
            connection = connect.getConnection();

            Thread thread = new Thread(() -> {
                try {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("INSERT INTO public.\"Appointment\"(appointment_id,doctor_id,patient_id,health_issue,status,appointment_date) " +
                            "VALUES('"+appointment_id+"','"+doctor_id+"','"+patient_id+"','"+health_issue+"','"+status+"','"+appointment_date+"');");
                    Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

    @Override
    public void getMyAppointmentHistory(Context context,ArrayList<AppointmentInfo> arrayList, MyAppointmentHistoryAdapter adapter) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        SharedPreferences sh = context.getSharedPreferences("PatientID", Context.MODE_PRIVATE);
        userID = sh.getString("patientID","");

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Appointment\";");
                while (resultSet.next()){

                    appointment_id = resultSet.getString("appointment_id");
                    doctor_id = resultSet.getString("doctor_id");
                    patient_id = resultSet.getString("patient_id");
                    appointment_date = resultSet.getDate("appointment_date");
                    health_issue = resultSet.getString("health_issue");
                    status = resultSet.getString("status");


                    if(userID.equals(patient_id)){
                        AppointmentInfo appointmentInfo = new AppointmentInfo(appointment_id,doctor_id,patient_id,appointment_date,health_issue,status);
                        arrayList.add(appointmentInfo);
                    }

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
    public void requestAppointment(Context context,String appointment_id, String doctor_id, String health_issue, String appointment_date) {

       if(!appointment_id.equals("") && !doctor_id.equals("") && !appointment_date.equals("")){
           if(appointment_id.length() <= 11){
               SharedPreferences sh = context.getSharedPreferences("PatientID", Context.MODE_PRIVATE);
               userID = sh.getString("patientID","");

               connect = new ConnectToServer();
               connection = connect.getConnection();

               Thread thread = new Thread(() -> {
                   try {
                       statement = connection.createStatement();
                       resultSet = statement.executeQuery("INSERT INTO public.\"Appointment\"(appointment_id,doctor_id,patient_id,health_issue,status,appointment_date) " +
                               "VALUES('"+appointment_id+"','"+doctor_id+"','"+userID+"','"+health_issue+"','Pending','"+appointment_date+"');");
                       Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               });
               thread.start();
           }else{
               Toast.makeText(context, "Appointment ID should be less than 11 character!", Toast.LENGTH_LONG).show();
           }
       }else{
           Toast.makeText(context, "Fill in the requirements!", Toast.LENGTH_LONG).show();
       }


    }

    @Override
    public void AcceptAppointment(String patientID) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("UPDATE public.\"Appointment\" SET status='Active' WHERE patient_id='"+patientID+"';");

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
    public void RejectAppointment(String patientID) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("DELETE FROM public.\"Appointment\" WHERE patient_id='"+patientID+"';");

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


    public String getAppointment_id() {
        return appointment_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public Date getAppointment_date() {
        return appointment_date;
    }

    public String getHealth_issue() {
        return health_issue;
    }

    public String getStatus() {
        return status;
    }

    public Statement getStatement() {
        return statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public Connection getConnection() {
        return connection;
    }

    public ConnectToServer getConnect() {
        return connect;
    }
}
