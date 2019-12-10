package com.example.cleanv2.Service;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.cleanv2.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.cleanv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingCleaner extends AppCompatActivity {

    @Nullable
    @BindView(R.id.spinCity)
    MaterialSpinner spinCity;

    @Nullable
    @BindView(R.id.spinCleaner)
    MaterialSpinner spinCleaner;

    @Nullable
   @BindView( R.id.textCity )
    TextView textCity;

    @Nullable
    @BindView( R.id.textCleaner )
            TextView textCleaner;

    @Nullable
    @BindView( R.id.buttonDate )
    Button buttonDate;

    @Nullable
    @BindView( R.id.buttonFrom )
            Button buttonFrom;

    @Nullable
    @BindView( R.id.buttonTo )
            Button buttonTo;

    @Nullable
    @BindView( R.id.buttonConfirm )
    Button buttonConfrim;


    @Nullable
    @BindView( R.id.textDate )
    TextView textDate;

    private DatePickerDialog datePickerDialog ;
    boolean click=false;

    Context context= this;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    List<String> cityList = new ArrayList<>();
    List<String> cleanerList = new ArrayList<>();
    List<String> bookingList = new ArrayList<>();

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking_cleaner );
        ButterKnife.bind( this );

        Calendar cal=Calendar.getInstance();

        final  int year=cal.get( Calendar.YEAR );
        final  int month=cal.get( Calendar.MONTH );
        final  int day=cal.get( Calendar.DAY_OF_MONTH );

        //timepicker variables for from-time and to-time
        final int to_minute=cal.get( Calendar.MINUTE );
        final  int to_hour=cal.get( Calendar.HOUR_OF_DAY );
        final int from_minute=cal.get( Calendar.MINUTE );
        final  int from_hour=cal.get( Calendar.HOUR_OF_DAY );



        buttonFrom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog( context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        buttonFrom.setText( hourOfDay+":" +minute);
                        bookingList.add("Date_from"+hourOfDay+":" +minute);
                    }
                },from_hour,from_minute,android.text.format.DateFormat.is24HourFormat( context ) );
                timePickerDialog.show();
            }
        } );


        buttonTo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog( context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        buttonFrom.setText( hourOfDay+":" +minute);
                        bookingList.add("Date_to:"+hourOfDay+":" +minute);
                    }
                },to_hour,to_minute,android.text.format.DateFormat.is24HourFormat( context ) );
                timePickerDialog.show();
            }
        } );


        buttonDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click=true;
                DatePickerDialog datePickerDialog =new DatePickerDialog(
                        BookingCleaner.this,
                        android.R.style.Theme_Material_Light_Dialog,
                        onDateSetListener,
                        year, month, day);
                datePickerDialog.show();
            }
        } );

        onDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateText=year+"/"+month+"/"+dayOfMonth;

                textDate.setText(  year+"/"+month+"/"+dayOfMonth);
                bookingList.add("Booking_Date:"+year+"/"+month+"/"+dayOfMonth);
            }
        };

        selectCity();

        buttonConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingDetails(bookingList);
                startActivity(new Intent(BookingCleaner.this, Home.class));
            }
        });

    }
    private void selectCity() {
        Task<QuerySnapshot> allCleanerRef = FirebaseFirestore.getInstance()
                .collection( "Cleaners" )
                .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cityList.add( " " );
                            for (QueryDocumentSnapshot queryDocumentSnapshots : task.getResult()) {
                                cityList.add( queryDocumentSnapshots.getId() );
                            }
                            spinCity.setItems( cityList );
                            spinCity.setOnItemSelectedListener( new MaterialSpinner.OnItemSelectedListener<String>() {
                                @Override
                                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                                    Snackbar.make( view, "Selected " + item, Snackbar.LENGTH_LONG ).show();
                                    selectCleaner( item );
                                    bookingList.add("City:"+item);
                                }
                            } );
                        }

                    }
                } );
    }

    private void selectCleaner(String item) {
        Task<QuerySnapshot> allCleanerRef = FirebaseFirestore.getInstance()
                .collection( "Cleaners" )
                .document(item)
                .collection( "list" )
                .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cleanerList.add( " " );
                            if(cleanerList.size()>1){
                                cleanerList.clear();
                            }
                            for (QueryDocumentSnapshot queryDocumentSnapshots : task.getResult()) {
                                cleanerList.add( queryDocumentSnapshots.getId() );
                            }
                            spinCleaner.setItems( cleanerList );
                            spinCleaner.setOnItemSelectedListener( new MaterialSpinner.OnItemSelectedListener<String>() {
                                @Override
                                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                                    Snackbar.make( view, "Selected " + item, Snackbar.LENGTH_LONG ).show();
                                    bookingList.add("Cleaner:"+item);
                                }
                            } );
                        }
                    }
                } );
    }

    private void bookingDetails(List<String> bookingList) {
        String listString = "";
        for (String s : bookingList)
        {
            listString += s + "\t";
        }
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("user4").document(userId);
        docRef.update("Booking Details",listString);
    }
    }




