package com.example.cleanv2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    EditText editEmail,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        editEmail=findViewById(R.id.editEmail);
        editPassword=findViewById(R.id.editPassword);
        findViewById(R.id.signup);
    }

    public void onClickLogin(View view) {

        String email = editEmail.getText().toString();
        String pwd = editPassword.getText().toString();


        //validations for empty login
        if (email.equalsIgnoreCase("")) {
            editEmail.setError("please enter email");//it gives user to info message for missing email //
        } else if (pwd.equalsIgnoreCase("")) {
            editPassword.setError("please enter password");//it gives user to info message for missing password //
        } else {
            //Authenticating user
            mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        finish();
                        startActivity(new Intent(SignUp.this, UserInfo.class));

                        Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_SHORT).show();

                    } else {

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                            Toast.makeText(getApplicationContext(), "You are already registered please click sign in", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }
    }
    public void registered(View view) {
        startActivity(new Intent(this,MainActivity.class));

    }
}
