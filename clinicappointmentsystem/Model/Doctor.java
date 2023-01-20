package com.mustafa.clinicappointmentsystem.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.mustafa.clinicappointmentsystem.Interfaces.DoctorITF;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Doctor implements DoctorITF {
    private String doctor_id,doctor_name,doctor_surname,department_id,sex,age,phone,doctor_password;

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;
    private String userID;
    private int flag = 0;

    public Doctor(String doctor_id, String doctor_name, String doctor_surname, String department_id, String sex, String age, String phone, String doctor_password) {
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.doctor_surname = doctor_surname;
        this.department_id = department_id;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.doctor_password = doctor_password;
    }

    public Doctor(){

    }

    public String getDoctorID() {
        return doctor_id;
    }

    public void setDoctorID(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_surname() {
        return doctor_surname;
    }

    public void setDoctor_surname(String doctor_surname) {
        this.doctor_surname = doctor_surname;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDoctor_password() {
        return doctor_password;
    }

    public void setDoctor_password(String doctor_password) {
        this.doctor_password = doctor_password;
    }

    @Override
    public void getDoctorName(Context context, TextView textView) {
        connect = new ConnectToServer();
        connection = connect.getConnection();

        SharedPreferences sh = context.getSharedPreferences("ID", Context.MODE_PRIVATE);
        userID = sh.getString("ID","");

        Thread thread = new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.\"Doctor\";");
                while (resultSet.next()){

                    if(userID.equals(resultSet.getString("doctor_id"))){
                        textView.setText( resultSet.getString("doctor_name") + " " + resultSet.getString("doctor_surname"));
                        flag = 1;
                        connection.close();
                        break;
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

        if(flag == 0){
            Thread thread2 = new Thread(() -> {
                try {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM public.\"Secretary\";");
                    while (resultSet.next()){

                        if(userID.equals(resultSet.getString("secretary_id"))){
                            textView.setText( resultSet.getString("secretary_name") + " " + resultSet.getString("secretary_surname"));
                            flag = 1;
                            connection.close();
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            thread2.start();
            try {
                thread2.join();
            }
            catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
