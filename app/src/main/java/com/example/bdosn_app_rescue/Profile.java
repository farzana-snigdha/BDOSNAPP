package com.example.bdosn_app_rescue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private String TAG = " ";
    //variables for textviews
    private TextView NameTextView, AgeTextView, EmailTextView, codeTV;
    EditText PhoneTextView;
    private EditText EmTextView1, EmTextView2, EmTextView3;
Button updateBtn;
    //Firebase Database
    private FirebaseDatabase mFireDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef, userRef;
    private String userID;
    String ownId;
    FirebaseUser user;

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
        codeTV = findViewById(R.id.code);
        updateBtn = findViewById(R.id.profile_update);
        //declare database reference object

        mAuth = FirebaseAuth.getInstance();
        mFireDatabase = FirebaseDatabase.getInstance();
        myRef = mFireDatabase.getReference().child("Users");
        //FirebaseUser user = mAuth.getCurrentUser();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getEmail();
        if (user != null) {
            // User is signed in

        } else {
            Log.d(TAG, "User isnt logged in");
            // No user is signed in
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                                        showData(snapshot);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

updateBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        updateData();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
});
    }
    private void updateData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.child("email").getValue(String.class).equals(userID)) {
                            ownId = ds.child("userId").getValue(String.class);
                        }
                    }
                    userRef = mFireDatabase.getReference().child("Users");
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot ds) {
userRef.child(ownId).child("em1").setValue(EmTextView1.getText().toString());
                            userRef.child(ownId).child("em2").setValue(EmTextView2.getText().toString());
                            userRef.child(ownId).child("em3").setValue(EmTextView3.getText().toString());
                            userRef.child(ownId).child("phone").setValue(PhoneTextView.getText().toString());


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void showData(DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()) {
            if (ds.child("email").getValue(String.class).equals(userID)) {
                ownId = ds.child("userId").getValue(String.class);
            }
        }
        userRef = mFireDatabase.getReference().child("Users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {

                NameTextView.setText("Name: " + ds.child(ownId).child("name").getValue(String.class));
                AgeTextView.setText("Age: " + ds.child(ownId).child("age").getValue(String.class));
                EmailTextView.setText("Email: " + ds.child(ownId).child("email").getValue(String.class));
                PhoneTextView.setText( ds.child(ownId).child("phone").getValue(String.class));
                EmTextView1.setText( ds.child(ownId).child("em1").getValue(String.class));
                EmTextView2.setText(ds.child(ownId).child("em2").getValue(String.class));
                EmTextView3.setText( ds.child(ownId).child("em3").getValue(String.class));
                String code = String.valueOf(ds.child(ownId).child("code").getValue(Integer.class));
                codeTV.setText("Code: " +String.valueOf(code));
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
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}
