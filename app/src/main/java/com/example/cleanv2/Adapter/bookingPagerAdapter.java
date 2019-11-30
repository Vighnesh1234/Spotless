package com.example.cleanv2.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cleanv2.Fragments.BookingFragmentStep2;
import com.example.cleanv2.Fragments.BookingFragmentstep1;

public class bookingPagerAdapter extends FragmentPagerAdapter {


    public bookingPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public bookingPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {



        switch (position){
            case 0:
                System.out.println( "+++++++++++++++++++case1" );
                return BookingFragmentstep1.getInstance();

            case 1:
                System.out.println( "+++++++++++++++++++case2" );
                return BookingFragmentStep2.getInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


}
