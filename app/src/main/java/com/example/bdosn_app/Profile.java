package com.example.bdosn_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private String TAG = " ";
    //variables for textviews
    private TextView NameTextView,AgeTextView,EmailTextView,PhoneTextView;
    private TextView EmTextView1,EmTextView2,EmTextView3;

    //Firebase Database
    private FirebaseDatabase mFireDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        NameTextView = findViewById(R.id.name);
        AgeTextView = findViewById(R.id.age);
        EmailTextView = findViewById(R.id.email);
        PhoneTextView = findViewById(R.id.uphone);
        EmTextView1 = findViewById(R.id.n1);
        EmTextView2 = findViewById(R.id.n2);
        EmTextView3 = findViewById(R.id.n3);

        //declare database reference object

        mAuth= FirebaseAuth.getInstance();
        mFireDatabase = FirebaseDatabase.getInstance();
        myRef=mFireDatabase.getReference();
        //FirebaseUser user = mAuth.getCurrentUser();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        if(user != null){
            // User is signed in

        }else{
            Log.d(TAG,"User isnt logged in");
            // No user is signed in
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void showData(DataSnapshot snapshot) {
        for(DataSnapshot ds : snapshot.getChildren())
        {
            UserInfo uInfo = new UserInfo();
            uInfo.setName(ds.child(userID).getValue(UserInfo.class).getName());
            uInfo.setAge(ds.child(userID).getValue(UserInfo.class).getAge());
            uInfo.setEmail(ds.child(userID).getValue(UserInfo.class).getEmail());
            uInfo.setPhone(ds.child(userID).getValue(UserInfo.class).getPhone());
            uInfo.setCode(ds.child(userID).getValue(UserInfo.class).getCode());
            uInfo.setPassword(ds.child(userID).getValue(UserInfo.class).getPassword());
            uInfo.setEm1(ds.child(userID).getValue(UserInfo.class).getEm1());
            uInfo.setEm2(ds.child(userID).getValue(UserInfo.class).getEm2());
            uInfo.setEm3(ds.child(userID).getValue(UserInfo.class).getEm3());

            Log.d(TAG,"showData: name "+ uInfo.getName());
            Log.d(TAG,"showData: age "+ uInfo.getAge());
            Log.d(TAG,"showData: email "+ uInfo.getEmail());
            Log.d(TAG,"showData: phone "+ uInfo.getPhone());
            Log.d(TAG,"showData: code "+ uInfo.getCode());
            Log.d(TAG,"showData: password "+ uInfo.getPassword());
            Log.d(TAG,"showData: EM1" + uInfo.getEm1());
            Log.d(TAG,"showData: EM2" + uInfo.getEm2());
            Log.d(TAG,"showData: EM3" + uInfo.getEm3());

            NameTextView.setText(uInfo.getName());
            AgeTextView.setText(uInfo.getAge());
            EmailTextView.setText(uInfo.getEmail());
            PhoneTextView.setText(uInfo.getPhone());
            EmTextView1.setText(uInfo.getEm1());
            EmTextView2.setText(uInfo.getEm2());
            EmTextView3.setText(uInfo.getEm3());

        }
    }
}