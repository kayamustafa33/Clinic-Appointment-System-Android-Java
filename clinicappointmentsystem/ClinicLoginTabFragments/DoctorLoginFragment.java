package com.mustafa.clinicappointmentsystem.ClinicLoginTabFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.ClinicView.MainActivity;
import com.mustafa.clinicappointmentsystem.databinding.FragmentDoctorLoginBinding;
import java.sql.*;

public class DoctorLoginFragment extends Fragment {

    private FragmentDoctorLoginBinding binding;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ConnectToServer connect;
    private int flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoctorLoginBinding.inflate(inflater,container,false);

        connect = new ConnectToServer();
        connection = connect.getConnection();

        login();

        return binding.getRoot();
    }

    private void login(){

        binding.doctorLoginBtn.setOnClickListener(item -> {
            String doctorId = binding.doctorIdEditText.getText().toString().trim();
            String doctorPassword = binding.doctorPasswordEditText.getText().toString().trim();
            flag = 0;

            if(doctorId.equals("") && doctorPassword.equals("")) {
                Toast.makeText(binding.getRoot().getContext(), "Enter ID and Password!", Toast.LENGTH_LONG).show();
            }else {
                Thread thread = new Thread(() -> {
                    try {
                        statement = connection.createStatement();
                        resultSet = statement.executeQuery("SELECT * FROM public.\"Doctor\";");
                        while (resultSet.next()){
                            if(resultSet.getString("doctor_id").equals(doctorId) &&
                                    resultSet.getString("doctor_password").equals(doctorPassword)){
                                SharedPreferences sharedPreferences = binding.getRoot().getContext().getSharedPreferences("ID", Context.MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("ID",doctorId);
                                myEdit.apply();
                                Intent intent = new Intent(binding.getRoot().getContext(),MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                flag = 1;
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
            if(flag == 0){
                Toast.makeText(binding.getRoot().getContext(), "Invalid!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(binding.getRoot().getContext(), "Successfully", Toast.LENGTH_LONG).show();
            }
            });



    }
}