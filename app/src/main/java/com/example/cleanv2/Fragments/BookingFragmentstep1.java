package com.example.cleanv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cleanv2.R;

public class BookingFragmentstep1 extends Fragment {


    public static BookingFragmentstep1 getInstance() {
        if(instance==null){
       instance=new BookingFragmentstep1();
            return instance;
        }
        return instance;
    }

    public static BookingFragmentstep1 instance;








    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         return inflater.inflate( R.layout.booking_step_1,container,false );
    }
}
