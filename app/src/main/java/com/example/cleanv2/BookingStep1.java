package com.example.cleanv2;

import android.os.Bundle;

import com.example.cleanv2.Interface.IAllCleanerLoadListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class BookingStep1 extends AppCompatActivity implements IAllCleanerLoadListener {

    Task<QuerySnapshot> allCleanerRef;
    CollectionReference cityRef;
IAllCleanerLoadListener iAllCleanerLoadListener;
    private MaterialSpinner spinner;
//   MaterialSpinner spinner=findViewById( R.id.spinner );
 //   RecyclerView recyclerCleaner=findViewById( R.id.recyclerCleaner );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking_step1 );


        Task<QuerySnapshot> allCleanerRef;
        CollectionReference cityRef;

      MaterialSpinner spinner;
        spinner = findViewById( R.id.spinner );
        RecyclerView recyclerCleaner=findViewById( R.id.recyclerCleaner );

        iAllCleanerLoadListener=this;

        allCleanerRef= FirebaseFirestore.getInstance().collection( "Cleaners" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    System.out.println( "++++++++++++++++++++onComplete1" );
                   List<String> list =new ArrayList<>(  );
                   list.add( "Please select City" );
                   for(QueryDocumentSnapshot queryDocumentSnapshots: task.getResult()){
list.add( queryDocumentSnapshots.getId() );
                       System.out.println( "++++++++++++++++++++PrintingLIST" );
System.out.println( list );
                       iAllCleanerLoadListener.onAllCleanerLoadSuccess( list );
System.out.println( "++++++++++++++++++++onComplete2" );
                   }

                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllCleanerLoadListener.onAllCleanerLoadFailed( e.getMessage() );
                System.out.println( "++++++++++++++++++++onFailure" );
            }
        } );


        }


     //       loadAllClean();


    private void loadAllClean() {
    }

    @Override
    public void onAllCleanerLoadSuccess(List<String> cityNameList) {
spinner.setItems(cityNameList );


    }

    @Override
    public void onAllCleanerLoadFailed(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}