package com.mustafa.clinicappointmentsystem.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mustafa.clinicappointmentsystem.ClinicLoginTabFragments.DoctorLoginFragment;
import com.mustafa.clinicappointmentsystem.ClinicLoginTabFragments.SecretaryLoginFragment;

public class ClinicLoginTabLayoutAdapter extends FragmentStateAdapter {
    public ClinicLoginTabLayoutAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SecretaryLoginFragment();
            case 0:
            default:
                return new DoctorLoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
