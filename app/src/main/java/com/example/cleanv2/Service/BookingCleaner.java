package com.example.cleanv2.Service;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.cleanv2.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.cleanv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    List<String> userDetailsList = new ArrayList<>();

    String Cleaner_name = "";
    String city="";
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking_cleaner );
        ButterKnife.bind( this );

        Calendar cal=Calendar.getInstance();

        //Calendar variables
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
                        bookingList.add("From"+hourOfDay+":" +minute);
                    }
                },from_hour,from_minute,android.text.format.DateFormat.is24HourFormat( context ) );
                timePickerDialog.show();
            }
        } );


        buttonTo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog2= new TimePickerDialog( context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        buttonTo.setText( hourOfDay+":" +minute);
                        bookingList.add("To:"+hourOfDay+":" +minute);
                    }
                },to_hour,to_minute,android.text.format.DateFormat.is24HourFormat( context ) );
                timePickerDialog2.show();
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

                textDate.setText(  dateText);
                bookingList.add("Booking_Date:"+dateText);
            }
        };

        selectCity();

        buttonConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingDetails(bookingList);

                sendEmail();

            Toast.makeText(getApplicationContext(), "Booking confrimed", Toast.LENGTH_LONG).show();

                startActivity(new Intent(BookingCleaner.this, Home.class));
            }
        });

    }


    //Function for extracting the cities values from firebase
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
                                    city=item;
                                    bookingList.add("City:"+item);
                                }
                            } );
                        }

                    }
                } );
    }



    //Function for extracting the cleaner values from firebase
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
                                    Cleaner_name=item;
                                }
                            } );
                        }
                    }
                } );
    }


    //Function for storing the booking details to firebase
    private void bookingDetails(List<String> bookingList) {
        String listString = "";
        for (String s : bookingList)
        {
            listString += s + "\t";
        }
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("userInfo").document(userId);

        docRef.update("Booking Details",listString);
    }

    private void sendEmail() {
        //Fetching Cleaner details for sending email message to the client/user
        DocumentReference docRef = db.collection("Cleaners").document(city).collection("list").document(Cleaner_name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //hash map for user object
                        Map<String, Object> Cleaner = new HashMap<>();
                        Cleaner = document.getData();
                        String cleanerEmail =(String) Cleaner.get("Email");
                        String CleanerInfo=Cleaner.toString();
                       // CleanerInfo=CleanerInfo.substring(1, CleanerInfo.length()-1);
                        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        //Extracting user id and email from the session
                        String userId = mAuth.getCurrentUser().getUid();
                        String userEmail = mAuth.getCurrentUser().getEmail();

                        //Fetching user details for sending email message to the cleaner
                        DocumentReference docRef1 = db.collection("userInfo").document(userId);
                        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        //hash map for user object
                                        Map<String, Object> user = new HashMap<>();
                                        user = document.getData();
                                        String content = user.toString();
                                        //trimming the { and } symbols from the string
                                        content=content.substring(1, content.length()-1);
                                        String username= (String) user.get("name");
                                        String cleanerSubject = "Spotless: Client Details";
                                        String messageToCleaner = "You have a client cleaning appointment. Following is your client booking information. "+content;
                                        //Creating SendMail object for cleaner
                                        SendMail emailToStaff = new SendMail(BookingCleaner.this, cleanerEmail, cleanerSubject, messageToCleaner);
                                        String ClientSubject = "Spotless: Cleaner Details";
                                        String messageForClient = "Hi "+username+","+" Successfully booked. Following are your cleaner details"+CleanerInfo;
                                        //Creating SendMail object for Client/user
                                        SendMail emailToClient = new SendMail(BookingCleaner.this, userEmail, ClientSubject, messageForClient);
                                        //Executing sendmail to send email
                                        emailToStaff.execute();
                                        emailToClient.execute();
                                    }
                                }
                            }
                        });


                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("onFailure", e.getMessage());
            }
        });
         }
    }




