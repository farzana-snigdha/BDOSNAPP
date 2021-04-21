package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    DatabaseReference reference, currentRef, circleRef;
    FirebaseUser user;
    FirebaseAuth auth;
    String userID, joinUserId;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        pinview = findViewById(R.id.add_pin);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        currentRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        userID = user.getUid();

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

            }
        });

    }

    public void submitButtonClicked(View view) {
        Query query = reference.orderByChild("code").equalTo(pinview.getValue());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CreateUser createUser = null;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        createUser = ds.getValue(CreateUser.class);
                        joinUserId = createUser.getUserId();

                        circleRef = FirebaseDatabase.getInstance().getReference().child("Users").child(joinUserId).child("CircleMembers");
                        CircleJoin circleJoin = new CircleJoin(userID);
                        CircleJoin circleJoin1 = new CircleJoin(joinUserId);
                        circleRef.child(user.getUid()).setValue(circleJoin).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Emergency Contact Is Added", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invitation Code is invalid", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}