package com.mustafa.clinicappointmentsystem.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.mustafa.clinicappointmentsystem.Adapter.ClinicLoginTabLayoutAdapter;
import com.mustafa.clinicappointmentsystem.Adapter.CreateTabLayoutAdapter;
import com.mustafa.clinicappointmentsystem.databinding.ActivityClinicLoginBinding;

public class ClinicLoginActivity extends AppCompatActivity {

    private ActivityClinicLoginBinding binding;
    private ClinicLoginTabLayoutAdapter tabLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClinicLoginBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        setTabLayout();
    }

    private void setTabLayout(){

        tabLayoutAdapter =  new ClinicLoginTabLayoutAdapter(ClinicLoginActivity.this);
        binding.ClinicLoginViewPager2.setAdapter(tabLayoutAdapter);

        binding.clinicLoginTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.ClinicLoginViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.ClinicLoginViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.clinicLoginTabLayout.getTabAt(position).select();
            }
        });
    }

    public void goBackClinicBtn(View view){
        onBackPressed();
    }
}