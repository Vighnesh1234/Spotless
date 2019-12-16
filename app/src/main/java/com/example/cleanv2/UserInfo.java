package com.example.cleanv2;

import android.content.Intent;
import android.os.Bundle;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;



public class UserInfo extends AppCompatActivity {

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = FirebaseAuth.getInstance().getUid();
    EditText editName, editPhoneNum, editAddress;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Button update = findViewById(R.id.update);
        editName = findViewById(R.id.editName);
        editPhoneNum = findViewById(R.id.editPhoneNum);
        editAddress = findViewById(R.id.editAddress);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=editName.getText().toString();
                String PhoneNum=editPhoneNum.getText().toString();
                String Address=editAddress.getText().toString();
                String Bookingdetails="booking_pending";

                //Validations for null values of user details
                if(name.equalsIgnoreCase(""))
                {
                    editName.setError("please enter username");//it gives user to info message for missing username //
                }
                else if(PhoneNum.equalsIgnoreCase(""))
                {
                    editPhoneNum.setError("please enter Number");//it gives user to info message for missing phone number //
                }
                else if(Address.equalsIgnoreCase(""))
                {
                    editAddress.setError("please enter Address");//it gives user to info message for missing address //
                }

                else
                {
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("Phone Number", PhoneNum);
                    user.put("Address", Address);
                    user.put("Booking Details",Bookingdetails);

                    String userId = mAuth.getCurrentUser().getUid();
                    // pushing userdetails to firebase
                    db.collection("userInfo").document(userId).set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("onSuccess", "data stored successfully");
                                    startActivity(new Intent(UserInfo.this,Home.class));
                                    Toast.makeText(getApplicationContext(), "User details saved", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("onFailure", "Exception"+e);
                            Toast.makeText(getApplicationContext(), "Please re-enter details", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}






