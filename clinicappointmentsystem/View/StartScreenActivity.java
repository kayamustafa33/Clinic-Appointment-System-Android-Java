package com.mustafa.clinicappointmentsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mustafa.clinicappointmentsystem.databinding.ActivityStartScreenBinding;

public class StartScreenActivity extends AppCompatActivity {

    private ActivityStartScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartScreenBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }

    public void goToClinicLogin(View view){
        startActivity(new Intent(StartScreenActivity.this,ClinicLoginActivity.class));
    }

    public void goToPatientLogin(View view){
        startActivity(new Intent(StartScreenActivity.this,PatientLoginActivity.class));
    }
}