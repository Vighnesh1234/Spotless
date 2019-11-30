package com.example.cleanv2;

import android.content.Intent;
import android.os.Bundle;

import com.example.cleanv2.Model.Name;
import com.example.cleanv2.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import  com.example.cleanv2.UserInfo;
public class Home extends AppCompatActivity {


    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
  //  FirebaseFirestore db = FirebaseFirestore.getInstance();
 // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    String uid = FirebaseAuth.getInstance().getUid();

    private static final String FIRE_LOG ="Fire+Log" ;

    TextView placing;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

      //  FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        final TextView placing =findViewById(R.id.username);

System.out.println("USER IS ++++++++"+uid);


       /*db.collection("users").document("test").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                System.out.println("+++++++++++++trace oncomplete");
                if(task.isSuccessful()){
                    DocumentSnapshot   documentSnapshot=task.getResult();



                    if(documentSnapshot.exists()&& documentSnapshot!=null) {
                        String username = documentSnapshot.getString("name");
placing.setText(username);
                        System.out.println("+++++++++++++else oncomplete:::"+username);
                    }
                }
                else
                {
                    System.out.println("+++++++++++++else oncomplete");
                    Log.d(FIRE_LOG,"++++++++++++FIREBASE retireve fail"+task.getException().getMessage());
                }
            }
        });
*/

       Name name=new Name();
System.out.println("++++++++++++++++++++++++name:"+name.getName());

            placing.setText(  name.getName());





       /* Query query = reference.child("userData").orderByChild(uid).equalTo(0);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

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

        startActivity(new Intent(this,BookingStep1.class));
    }

}
