package com.mustafa.clinicappointmentsystem.ClinicView;

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

import com.mustafa.clinicappointmentsystem.ClinicBottomFragments.AllAppointmentsFragment;
import com.mustafa.clinicappointmentsystem.ClinicBottomFragments.AllPatientsFragment;
import com.mustafa.clinicappointmentsystem.ClinicBottomFragments.CreateFragment;
import com.mustafa.clinicappointmentsystem.ClinicBottomFragments.ProfileFragment;
import com.mustafa.clinicappointmentsystem.Model.ConnectToServer;
import com.mustafa.clinicappointmentsystem.R;
import com.mustafa.clinicappointmentsystem.View.StartScreenActivity;
import com.mustafa.clinicappointmentsystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ConnectToServer connect = new ConnectToServer();
        
        ReplaceFragment(new AllAppointmentsFragment());
        bottomNavigationSelector();

    }

    private void bottomNavigationSelector(){
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.all_appointment_bottom:
                    ReplaceFragment(new AllAppointmentsFragment());
                    break;
                case R.id.createAppointmentOrPatient:
                    ReplaceFragment(new CreateFragment());
                    break;

                case R.id.all_patients_bottom:
                    ReplaceFragment(new AllPatientsFragment());
                    break;
                case R.id.profile:
                    ReplaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
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
                Intent intent = new Intent(MainActivity.this, StartScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}