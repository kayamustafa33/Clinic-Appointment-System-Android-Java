package com.mustafa.clinicappointmentsystem.ClinicBottomFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.mustafa.clinicappointmentsystem.Adapter.CreateTabLayoutAdapter;
import com.mustafa.clinicappointmentsystem.databinding.FragmentCreateBinding;

public class CreateFragment extends Fragment {

    private FragmentCreateBinding binding;
    private CreateTabLayoutAdapter tabLayoutAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateBinding.inflate(inflater,container,false);

        setTabLayout();

        return binding.getRoot();
    }

    private void setTabLayout(){

        tabLayoutAdapter =  new CreateTabLayoutAdapter(requireActivity());
        binding.viewPager2.setAdapter(tabLayoutAdapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayout.getTabAt(position).select();
            }
        });
    }
}