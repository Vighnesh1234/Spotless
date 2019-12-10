package com.example.cleanv2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetail extends AppCompatActivity {

    @Nullable
    @BindView(R.id.upName)
    TextView upName;

    @Nullable
    @BindView(R.id.upPhoneNum)
    TextView upPhoneNum;

    @Nullable
    @BindView(R.id.upAddress)
    TextView upAddress;

    @Nullable
    @BindView(R.id.updated)
    Button updated;

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind( this );

        String userId = mAuth.getCurrentUser().getUid();

        DocumentReference docRef = db.collection("user4").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> user = new HashMap<>();
                        user=   document.getData();
                        String name= (String) user.get("name");
                        String phoNum= (String) user.get("Phone Number");
                        String add= (String) user.get("Address");
                        upName.setText(name);
                        upPhoneNum.setText(phoNum);
                        upAddress.setText(add);
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = mAuth.getCurrentUser().getUid();

                DocumentReference docRef = db.collection("user4").document(userId);

                String name=upName.getText().toString();
                String PhoneNum=upPhoneNum.getText().toString();
                String Address=upAddress.getText().toString();


                //Validations for null values

                if(name.equalsIgnoreCase(""))
                {

                    upName.setError("please enter username");//it gives user to info message //use any one //
                }
                else if(PhoneNum.equalsIgnoreCase(""))
                {

                    upPhoneNum.setError("please enter Number");//it gives user to info message //use any one //
                }
                else if(Address.equalsIgnoreCase(""))
                {

                    upAddress.setError("please enter Address");//it gives user to info message //use any one //
                }

                else {
                    docRef.update("name", name);
                    docRef.update("Phone Number", PhoneNum);
                    docRef.update("Address", Address);
                    Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserDetail.this,Home.class));
                }
            }
        });

    }









}
