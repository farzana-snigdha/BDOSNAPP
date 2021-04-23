package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriend extends AppCompatActivity {
    TextView backbtn;
    Pinview pinview;
    DatabaseReference reference, circleRef;
    FirebaseUser user;
    FirebaseAuth auth;
    String userID, joinUserId;
    Button submitBtn;
    String ownId;
    ArrayList<String> ar1 = new ArrayList<>();
    ArrayList<String> ar2 = new ArrayList<>();
    int flag = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        pinview = findViewById(R.id.add_pin);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        userID = user.getEmail();

        backbtn = findViewById(R.id.back_to_main);
        submitBtn = findViewById(R.id.submit_code_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent3);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButtonClicked(view);
            }
        });

    }

    public void submitButtonClicked(View view) {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        if (ds1.child("email").getValue(String.class).equals(userID)) {
                            ownId = ds1.child("userId").getValue(String.class);
                        }
                    }

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        final long[] maxid = {0};

                        ar1.add(String.valueOf(ds.child("code").getValue(int.class)));
                        ar2.add(String.valueOf(ds.child("userId").getValue(String.class)));

                        if (String.valueOf(ds.child("code").getValue(int.class)).equals(String.valueOf(pinview.getValue()))) {
                            joinUserId = ds.child("userId").getValue(String.class);

                            circleRef = FirebaseDatabase.getInstance().getReference().child("Users").child(ownId).child("CircleMembers");
                            final int[] finalCnt = new int[1];
                            circleRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        maxid[0] = snapshot.getChildrenCount();
                                        finalCnt[0] = (int) snapshot.getChildrenCount();
                                        for (DataSnapshot ds : snapshot.getChildren()) {

                                            if (snapshot.hasChild("1")) {

                                                if (snapshot.child("1").getValue(String.class).equals(joinUserId)) {
                                                    Log.d("wcwc", "already added");
                                                    flag = 1;
                                                    Toast.makeText(getApplicationContext(), "The Contact Is Already Added", Toast.LENGTH_SHORT).show();
                                                    setContentView(R.layout.activity_add_friend);

                                                }
                                            } else if (snapshot.hasChild("2")) {
                                                if (snapshot.child("2").getValue(String.class).equals(joinUserId)) {
                                                    flag = 1;
                                                    Log.d("eccqecqec", "already added");
                                                    Toast.makeText(getApplicationContext(), "The Contact Is Already Added", Toast.LENGTH_SHORT).show();
                                                    setContentView(R.layout.activity_add_friend);

                                                }
                                            }
                                        }
                                    }
                                    Log.d("gvgcgtctc", ownId + "     " + joinUserId + "      " + maxid[0]);
                                    if (flag == 10) {
                                        circleRef.child(String.valueOf((maxid[0] + 1))).setValue(joinUserId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (finalCnt[0] > 2) {
                                                    Toast.makeText(getApplicationContext(), "3 people already have added you in their Contact", Toast.LENGTH_SHORT).show();
                                                    setContentView(R.layout.activity_add_friend);

                                                } else if (flag == 10 && task.isSuccessful()) {

                                                    Toast.makeText(getApplicationContext(), "Emergency Contact Is Added", Toast.LENGTH_SHORT).show();

                                                    Intent intent5 = new Intent(getApplicationContext(), ViewEmergencyContactList.class);
                                                    startActivity(intent5);
                                                    finish();

                                                }

                                            }
                                        });
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Log.d("dfghjbhb111", ownId + "     " + joinUserId + "      " + maxid[0]);

                            break;
                        }


                    }
                    if (!(ar1.contains(String.valueOf(pinview.getValue())))) {
                        setContentView(R.layout.activity_add_friend);
                        Toast.makeText(getApplicationContext(), "Invitation Code is invalid", Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}