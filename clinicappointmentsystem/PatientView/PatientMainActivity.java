package com.mustafa.clinicappointmentsystem.PatientView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.PatientBottomFragments.AppointmentHistoryFragment;
import com.mustafa.clinicappointmentsystem.PatientBottomFragments.GetAppointmentFragment;
import com.mustafa.clinicappointmentsystem.PatientBottomFragments.PatientProfileFragment;
import com.mustafa.clinicappointmentsystem.R;
import com.mustafa.clinicappointmentsystem.View.StartScreenActivity;
import com.mustafa.clinicappointmentsystem.databinding.ActivityPatientMainBinding;

public class PatientMainActivity extends AppCompatActivity {

    private ActivityPatientMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ConnectToServer connect = new ConnectToServer();

        ReplaceFragment(new AppointmentHistoryFragment());

        bottomNavigationSelected();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_menu:
                Intent intent = new Intent(PatientMainActivity.this, StartScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.patientFrameLayout,fragment);
        fragmentTransaction.commit();
    }

    private void bottomNavigationSelected(){
        binding.patientBottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.patientAppointmentHistory:
                     ReplaceFragment(new AppointmentHistoryFragment());
                    break;
                case R.id.getAppointment:
                     ReplaceFragment(new GetAppointmentFragment());
                    break;
                case R.id.patientProfileMenu:
                     ReplaceFragment(new PatientProfileFragment());
                    break;
            }
            return true;

        });
    }
}