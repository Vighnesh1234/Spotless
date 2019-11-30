package com.example.cleanv2;

import android.content.Intent;
import android.os.Bundle;

import com.example.cleanv2.Model.Name;
import com.example.cleanv2.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Future;
import  com.example.cleanv2.Home;

public class UserInfo extends AppCompatActivity {

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();


    String uid = FirebaseAuth.getInstance().getUid();

   // String userID = mAuth.auth().

    EditText editName, editPhoneNum, editAddress;
    // editEmail=findViewById(R.id.editEmail);
    //editPassword=findViewById(R.id.editPassword);

    FirebaseFirestore db = FirebaseFirestore.getInstance();

   // CollectionReference userInfo = FirebaseFirestore.getInstance().collection("User");


    // CollectionReference userInfo = FirebaseFirestore.getInstance().collection("User");
    //  DatabaseReference db;
    // @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
      /*  Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //  CollectionReference userInfo = FirebaseFirestore.getInstance().collection("User");

        Button update = findViewById(R.id.update);
        editName = findViewById(R.id.editName);
        editPhoneNum = findViewById(R.id.editPhoneNum);

        editAddress = findViewById(R.id.editAddress);




    /* User user = new User("tired","of ","trying");




        try {
            System.out.println("------------------------>in try main");

            db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                    System.out.println("------------------------->Sucss");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                    System.out.println("------------------------->Failed lol ");
                }
            });
        }
        catch(Exception e){
            System.out.println("++++++++++++++++++++++++in catch");
        }
*/
    }

    public void onUpdate(View view) {


        System.out.println("------------------------>trace1");
      User user=  setUserDetails();




      try{
            System.out.println("------------------------>in try");


           /* try {
                System.out.println("------------------------>in try Onclick");

                db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        System.out.println("------------------------->Sucss");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                        System.out.println("------------------------->Failed lol ");
                    }
                });
            }
            catch(Exception e){
                System.out.println("++++++++++++++++++++++++in catch");
            }


            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        }
*/


       try {
                System.out.println("------------------------>in try Onclick");

                db.collection("userData").document(uid).collection(uid).add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        System.out.println("------------------------->Sucss");
                        startActivity(new Intent(UserInfo.this, Home.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                        System.out.println("------------------------->Failed lol ");
                    }
                });
            }
            catch(Exception e){
                System.out.println("++++++++++++++++++++++++in catch");
            }


            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        }


        catch(Exception e){

            System.out.println("------------------------>trace2");

        }

        }

    private User setUserDetails() {
        User user = new User(editName.getText().toString(), editAddress.getText().toString(), editPhoneNum.getText().toString());




        return  user;
    }




}






