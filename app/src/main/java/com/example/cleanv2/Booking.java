package com.example.cleanv2;

import android.content.Context;
import android.os.Bundle;

import com.example.cleanv2.Adapter.bookingPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shuhart.stepview.StepView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Booking extends AppCompatActivity {

    StepView step_view;
    ViewPager viewPager;
    Button previous;
    Button next;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);


        StepView step_view=findViewById(R.id.step_view);
        ViewPager viewPager=findViewById(R.id.view_pager);
        final Button previous=findViewById(R.id.previous);
        Button next=findViewById(R.id.next);

        if(step_view==null){
            System.out.println("its null?");
        }
        else
        {
            System.out.println("+++++++++++++step_view is not null");
        }

        Context context;
        context=this;
    createStepView();


        viewPager.setAdapter(new bookingPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    previous.setEnabled( false );
                }

                    else
                    {
                        previous.setEnabled( true );
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );
        }



private void createStepView() {


        List<String> stepList=new ArrayList<String>();
        stepList.add("City");
        stepList.add("Cleaners");
        stepList.add("Time");
        stepList.add( "confirm" );


        try{
            System.out.println("++++++++++++++++++trace1");
            step_view.setSteps(stepList);
            System.out.println("++++++++++++++++++trace2");
        }
        catch (Exception e){
            System.out.println( e );
        }
    }


}

