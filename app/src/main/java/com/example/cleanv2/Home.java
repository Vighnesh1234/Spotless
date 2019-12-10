package com.example.cleanv2;

import android.content.Intent;
import android.os.Bundle;
import com.example.cleanv2.Service.BookingCleaner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Home extends AppCompatActivity {


    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = FirebaseAuth.getInstance().getUid();
    private static final String FIRE_LOG ="Fire+Log" ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @BindView(R.id.onBooking)
    Button onBooking;

    @Nullable
    @BindView(R.id.onUserDetails)
    Button onUserDetails;

    @Nullable
    @BindView(R.id.onBookingDetails)
    Button onBookingDetails;

    @Nullable
    @BindView(R.id.onLogout)
    Button onLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TextView placing =findViewById(R.id.username);
        ButterKnife.bind( this );



      onUserDetails.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(Home.this,UserDetail.class));
          }
      });



        onBookingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Booked.class));
            }
        });




        onBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,BookingCleaner.class));
            }
        });
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("user4").document(userId);

        onLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "successfully logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this,MainActivity.class));
            }
        });




        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> user = new HashMap<>();
                        user=   document.getData();
                        String names= (String) user.get("name");
                        placing.setText(names);
                    } else {
                        Log.d("onComplete", "Documents doesnt exist");
                    }
                } else {
                    Log.d("Failure", "failed to save data");
                }
            }
        });
    }
}

