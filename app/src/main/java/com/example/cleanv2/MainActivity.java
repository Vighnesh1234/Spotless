package com.example.cleanv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText editEmail,editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        editEmail=findViewById(R.id.editEmail);
        editPassword=findViewById(R.id.editPassword);
    }

    public void onClickSignup(View view) {
startActivity(new Intent(this,SignUp.class));
    }

    public void onClickLogin(View view) {
        String email= editEmail.getText().toString();
        String pwd=editPassword.getText().toString();
        //validations
        if(email.equalsIgnoreCase("")) {

            editEmail.setError("please enter email");
        }
       else if(pwd.equalsIgnoreCase("")) {
            editPassword.setError("please enter password");
        }
       else {
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
    }

}
