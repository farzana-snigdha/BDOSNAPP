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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddFriend extends AppCompatActivity {
    TextView backbtn;
    Pinview pinview;
    DatabaseReference reference,  circleRef;
    FirebaseUser user;
    FirebaseAuth auth;
    String userID, joinUserId;
    Button submitBtn;
    String ownId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        pinview = findViewById(R.id.add_pin);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
userID=user.getEmail();

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
                    Log.d("dfghjbhb1", String.valueOf(user.getEmail()));
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        if (ds1.child("email").getValue(String.class).equals(userID)) {
                            ownId = ds1.child("userId").getValue(String.class);
                        }
                    }
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        final long[] maxid = {0};



                        if (String.valueOf(ds.child("code").getValue(int.class)).equals(String.valueOf(pinview.getValue()))) {
                            joinUserId = ds.child("userId").getValue(String.class);
//                            Log.d("dfghjbwcwchb111", joinUserId);
                            Log.d("dfghjbhb11", String.valueOf(ds.child("code").getValue(int.class)));
                            Log.d("dfghjbhb111", ownId);

                            circleRef = FirebaseDatabase.getInstance().getReference().child("Users").child(ownId).child("CircleMembers");
                            circleRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        maxid[0] = snapshot.getChildrenCount();}
                                        Log.d("gvgcgtctc", ownId + "     " + joinUserId + "      " + maxid[0]);
                                        circleRef.child(String.valueOf((maxid[0] + 1))).setValue(joinUserId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("gvgcgtctc", ownId + "     " + joinUserId + "      " + maxid[0]);

                                                    Toast.makeText(getApplicationContext(), "Emergency Contact Is Added", Toast.LENGTH_SHORT).show();

                                                    Intent intent5 = new Intent(getApplicationContext(), ViewEmergencyContactList.class);
                                                    startActivity(intent5);
                                                    finish();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Invitation Code is invalid", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        });


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Log.d("dfghjbhb111", ownId + "     " + joinUserId + "      " + maxid[0]);




                        }


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}