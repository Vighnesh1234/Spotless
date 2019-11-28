package com.example.cleanv2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shuhart.stepview.StepView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;

public class Booking extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);


        StepView stepView=findViewById(R.id.step_view);
        ViewPager viewPager=findViewById(R.id.view_pager);
        Button previous=findViewById(R.id.previous);
        Button next=findViewById(R.id.next);


        createStepView();

    }

    private void createStepView() {


    }

}
