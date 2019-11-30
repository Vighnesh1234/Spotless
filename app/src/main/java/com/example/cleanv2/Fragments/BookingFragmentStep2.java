package com.example.cleanv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cleanv2.R;

public class BookingFragmentStep2 extends Fragment {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static BookingFragmentStep2 getInstance() {
        if(instance==null){
            instance=new BookingFragmentStep2();
            return instance;
        }
        return instance;
    }

    public static BookingFragmentStep2 instance;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate( R.layout.booking_step_2,container,false );
    }
}
