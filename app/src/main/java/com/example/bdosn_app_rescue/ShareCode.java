package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

public class ShareCode extends AppCompatActivity {
    int code;
    TextView codeView, backbtn;
    Button sendBtn;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_code);
        codeView = findViewById(R.id.my_code);
        backbtn = findViewById(R.id.back_to_main);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ShareCode.this, MainActivity.class);
                ShareCode.this.startActivity(intent3);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.d("dfghjbhb", String.valueOf(user.getEmail()));
                    for (DataSnapshot ds:snapshot.getChildren()){
                        Log.d("dfghjbhb", String.valueOf(ds.child("email").getValue(String.class)));
                        Log.d("dfghjbhb", String.valueOf(user.getEmail()));

                        if(ds.child("email").getValue(String.class).equals(user.getEmail())){
                            code = ds.child("code").getValue(int.class);
                            Log.d("dfghjbhbcode", String.valueOf(code));
                            codeView.setText(String.valueOf(code));

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