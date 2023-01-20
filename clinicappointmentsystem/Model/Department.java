package com.mustafa.clinicappointmentsystem.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.mustafa.clinicappointmentsystem.Interfaces.DepartmentITF;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Department implements DepartmentITF {
    private String  department_id;
    private String department_name;

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;
    private ConnectToServer connect;
    private String userID,departmentID;
    private int flag = 0;
    private Context context;

    public Department(String department_id, String department_name) {
        this.department_id = department_id;
        this.department_name = department_name;
    }

    public Department(){

    }

    public String getDepartment_id() {
        return department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    @Override
    public void getDepartmentName(Context context,TextView textView) {
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
                        departmentID = resultSet.getString("department_id");

                        flag = 1;

                        if(departmentID != null){
                            Thread thread1 = new Thread(() -> {
                                try {
                                    statement = connection.createStatement();
                                    resultSet = statement.executeQuery("SELECT * FROM public.\"Department\";");
                                    while (resultSet.next()){
                                        if(departmentID.equals(resultSet.getString("department_id"))){
                                            textView.setText(resultSet.getString("department_name"));
                                            break;
                                        }
                                    }
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                            });
                            thread1.start();
                            try {
                                thread1.join();
                            }
                            catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }

                if(flag == 0){
                    textView.setText("Secretary");
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
