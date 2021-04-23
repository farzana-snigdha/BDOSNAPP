package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    TextView e1, e2, e3, back,loc;
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
        loc=findViewById(R.id.textViewMyLocation);
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
                            loc.setText(ds1.child("name").getValue(String.class));
                            loc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                                    i.putExtra("name",ds1.child("name").getValue(String.class));
                                    i.putExtra("email",ds1.child("email").getValue(String.class));
                                    i.putExtra("userId",ds1.child("userId").getValue(String.class));
                                    startActivity(i);
                                }
                            });                        }
                    }
                    for (DataSnapshot ds : st.getChildren()) {


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
Log.d("fghj",st.child(arr.get(0)).child("userId").getValue(String.class));
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                // startActivity();
                if (user == null) {
                    Intent i = new Intent(this, SignUp.class);
                    this.startActivity(i);
                } else {
                    Intent i31 = new Intent(this, Profile.class);
                    this.startActivity(i31);
                }
                // Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.map_menu:
                Intent i2 = new Intent(this, ViewEmergencyContactList.class);
                this.startActivity(i2);
                return true;
            case R.id.add_person_sub_menu:
                Intent intent1 = new Intent(this, AddMissingPerson.class);
                this.startActivity(intent1);
                finish();
                return true;
            case R.id.view_list_sub_menu:
                Intent intent2 = new Intent(this, ViewMissingPersonList.class);
                this.startActivity(intent2);
                finish();
                return true;
            case R.id.add_friend_sub_menu:
                Intent intent3 = new Intent(this, AddFriend.class);
                this.startActivity(intent3);
                finish();
                return true;
            case R.id.my_code_sub_menu:
                Intent intent4 = new Intent(this, ShareCode.class);
                this.startActivity(intent4);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

}