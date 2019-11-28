package com.example.cleanv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDexApplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    EditText editEmail,editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase object
        mAuth = FirebaseAuth.getInstance();
       // Button login=(Button) findViewById(R.id.login);



        editEmail=findViewById(R.id.editEmail);
        editPassword=findViewById(R.id.editPassword);




    }

    public void onClickLogin(View view) {

String email= editEmail.getText().toString();

String pwd=editPassword.getText().toString();


//add validations for nulls or whatever

        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });







    }


    public void onClickSignup(View view) {

startActivity(new Intent(this,SignUp.class));
    }

    public void registered(View view) {
    }

    public void onUpdate(View view) {
    }

    public void onHistory(View view) {
    }

    public void onShopping(View view) {
    }

    public void onNotification(View view) {
    }


    public void onBooking(View view) {
    }
}