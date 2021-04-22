package com.example.bdosn_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    private TextView NameTextView,AgeTextView,EmailTextView,PhoneTextView;
    private TextView EmTextView1,EmTextView2,EmTextView3;
    private DatabaseReference userRef;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String email = getIntent().getExtras().getString("Email");
        NameTextView = findViewById(R.id.name);
        AgeTextView = findViewById(R.id.age);
        EmailTextView = findViewById(R.id.email);
        PhoneTextView = findViewById(R.id.uphone);
        EmTextView1 = findViewById(R.id.n1);
        EmTextView2 = findViewById(R.id.n2);
        EmTextView3 = findViewById(R.id.n3);

        database = FirebaseDatabase.getInstance();
         userRef = database.getReference("Users");

         userRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot ds: snapshot.getChildren())
                 {
                     if(email.equals(ds.child("email").getValue()))
                     {
                         NameTextView.setText(ds.child("name").getValue(String.class));
                         AgeTextView.setText(ds.child("age").getValue(String.class));
                         EmailTextView.setText(email);
                         PhoneTextView.setText(ds.child("phone").getValue(String.class));
                         EmTextView1.setText(ds.child("em1").getValue(String.class));
                         EmTextView2.setText(ds.child("em2").getValue(String.class));
                         EmTextView3.setText(ds.child("em3").getValue(String.class));

                     }
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(getApplicationContext(),"Cannot Retrieve Info",Toast.LENGTH_SHORT).show();
             }
         });

    }
}