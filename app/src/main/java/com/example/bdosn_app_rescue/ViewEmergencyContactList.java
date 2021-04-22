package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewEmergencyContactList extends AppCompatActivity {
    TextView e1, e2, e3, back;
    long maxid = 0;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference, circleRef;
    String ownId;
    String userID;
    ArrayList<String> arr = new ArrayList<>(Arrays.asList(" ", " ", " ", " "));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emergency_contact_list);
        e1 = findViewById(R.id.textViewEm1);
        e2 = findViewById(R.id.textViewEm2);
        e3 = findViewById(R.id.textViewEm3);
        back = findViewById(R.id.textViewBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        userID = user.getEmail();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot st) {
                if (st.exists()) {
                    Log.d("dfghjbhb1", String.valueOf(user.getEmail()));
                    for (DataSnapshot ds1 : st.getChildren()) {
                        if (ds1.child("email").getValue(String.class).equals(userID)) {
                            ownId = ds1.child("userId").getValue(String.class);
                        }
                    }
                    for (DataSnapshot ds : st.getChildren()) {

//                        if (String.valueOf(ds.child("code").getValue(int.class)).equals(String.valueOf(pinview.getValue()))) {
//                            joinUserId = ds.child("userId").getValue(String.class);
////                            Log.d("dfghjbwcwchb111", joinUserId);
//                            Log.d("dfghjbhb11", String.valueOf(ds.child("code").getValue(int.class)));
//                            Log.d("dfghjbhb111", ownId);

                        circleRef = FirebaseDatabase.getInstance().getReference().child("Users").child(ownId).child("CircleMembers");
                        circleRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    maxid = snapshot.getChildrenCount();
                                }
                                int k = 0;
                                for (int i = 1; i <= maxid; i++) {
                                    arr.set(k, snapshot.child(String.valueOf(i)).getValue(String.class));
                                    k = k + 1;
                                }
                                e1.setText(st.child(arr.get(0)).child("name").getValue(String.class));
                                e2.setText(st.child(arr.get(1)).child("name").getValue(String.class));
                                e3.setText(st.child(arr.get(2)).child("name").getValue(String.class));
                                e1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                                        i.putExtra("name",st.child(arr.get(0)).child("name").getValue(String.class));
                                        i.putExtra("email",st.child(arr.get(0)).child("email").getValue(String.class));
                                        i.putExtra("userId",st.child(arr.get(0)).child("userId").getValue(String.class));
                                        startActivity(i);
                                    }
                                });
                                e2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                                        i.putExtra("name",st.child(arr.get(1)).child("name").getValue(String.class));
                                        i.putExtra("email",st.child(arr.get(1)).child("email").getValue(String.class));
                                        i.putExtra("userId",st.child(arr.get(1)).child("userId").getValue(String.class));
                                        startActivity(i);
                                    }
                                });
                                e3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                                        i.putExtra("name",st.child(arr.get(2)).child("name").getValue(String.class));
                                        i.putExtra("email",st.child(arr.get(2)).child("email").getValue(String.class));
                                        i.putExtra("userId",st.child(arr.get(2)).child("userId").getValue(String.class));
                                        startActivity(i);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //    Log.d("dfghjbhb111", ownId + "     " + joinUserId + "      " + maxid[0]);


                    }


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}