package com.mustafa.clinicappointmentsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mustafa.clinicappointmentsystem.ClinicView.MainActivity;
import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.PatientView.PatientMainActivity;
import com.mustafa.clinicappointmentsystem.databinding.ActivityPatientLoginBinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientLoginActivity extends AppCompatActivity {

    private ActivityPatientLoginBinding binding;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ConnectToServer connect;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientLoginBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        connect = new ConnectToServer();
        connection = connect.getConnection();
    }

    public void patientLoginClicked(View view){
        String patientID = binding.patientIdEditText.getText().toString().trim();
        String patientPassword = binding.patientPasswordEditText.getText().toString().trim();

        flag = 0;

        if(patientID.equals("") && patientPassword.equals("")) {
            Toast.makeText(binding.getRoot().getContext(), "Enter ID and Password!", Toast.LENGTH_LONG).show();
        }else {
            Thread thread = new Thread(() -> {
                try {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM public.\"Patient\";");
                    while (resultSet.next()){
                        if(resultSet.getString("patient_id").equals(patientID) &&
                                resultSet.getString("patient_password").equals(patientPassword)){
                            SharedPreferences sharedPreferences = getSharedPreferences("PatientID",MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("patientID",patientID);
                            myEdit.apply();
                            Intent intent = new Intent(binding.getRoot().getContext(),PatientMainActivity.class);
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
    }

    public void goBackPatientBtn(View view){
        onBackPressed();
    }
}