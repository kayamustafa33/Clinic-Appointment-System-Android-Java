package com.mustafa.clinicappointmentsystem.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mustafa.clinicappointmentsystem.ClinicCreateTabFragments.CheckAppointmentFragment;
import com.mustafa.clinicappointmentsystem.ClinicCreateTabFragments.CreateAppointmentFragment;
import com.mustafa.clinicappointmentsystem.ClinicCreateTabFragments.CreatePatientFragment;

public class CreateTabLayoutAdapter extends FragmentStateAdapter {
    public CreateTabLayoutAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 2:
                return new CheckAppointmentFragment();
            case 1:
                return new CreatePatientFragment();
            case 0:
            default:
                return new CreateAppointmentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
