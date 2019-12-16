package com.example.cleanv2;

import android.content.Intent;
import android.os.Bundle;
import com.example.cleanv2.Service.BookingCleaner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;

public class Booked extends AppCompatActivity {


    @Nullable
    @BindView(R.id.updateBookingDetails)
    Button updateBookingDetails;


    @Nullable
    @BindView(R.id.onHome)
    Button onHome;

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);
        String userId = mAuth.getCurrentUser().getUid();
        ButterKnife.bind( this );


        //Retrieving  booking details from firebase and displaying in the list view
        DocumentReference docRef = db.collection("userInfo").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Map<String, Object> user = new HashMap<>();
                        user = document.getData();
                        String bookDetails =(String) user.get("Booking Details");
                        List<String> myList = new ArrayList<String>(Arrays.asList(bookDetails.split("\\s+")));
                        ListView bookingList= findViewById( R.id.bookingList );
                        ArrayAdapter arrayAdapter=new ArrayAdapter( Booked.this,android.R.layout.simple_list_item_1,myList );
                        bookingList.setAdapter( arrayAdapter );
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



        onHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Booked.this,Home.class));
            }
        });


        updateBookingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Toast.makeText(getApplicationContext(), "Please update new booking", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Booked.this, BookingCleaner.class));
            }
        });
    }
}
