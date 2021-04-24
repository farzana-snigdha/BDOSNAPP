package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
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
    DatabaseReference reference,ref;
String ownId;
FirebaseUser user;
FirebaseAuth auth;

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
                finish();
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();

         user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.d("dfghjbhb", String.valueOf(user.getEmail()));
                    for (DataSnapshot ds:snapshot.getChildren()){

                        if(ds.child("email").getValue(String.class).equals(user.getEmail())){
                            code = ds.child("code").getValue(int.class);
                            codeView.setText(String.valueOf(code));

                        }
                    }
                }
              }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendBtn=findViewById(R.id.send_code_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsService();
            }
        });
    }
    private void smsService() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Log.d("dfghjbhb1", String.valueOf(user.getEmail()));
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        if (ds1.child("email").getValue(String.class).equals(user.getEmail())) {

                            ownId = ds1.child("userId").getValue(String.class);
                            ref = FirebaseDatabase.getInstance().getReference().child("Users").child(ownId);
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String num1 = "88"+snapshot.child("em1").getValue(String.class);
                                    String num2 = "88"+snapshot.child("em2").getValue(String.class);
                                    String num3 = "88"+snapshot.child("em3").getValue(String.class);
                                    int cod=snapshot.child("code").getValue(int.class);
                                    String message="Hey! Add me in RESCUE.\n My code is: "+String.valueOf(cod);

                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(num1, null, message, null, null);
                                    SmsManager smsManager2 = SmsManager.getDefault();
                                    smsManager2.sendTextMessage(num2, null, message, null, null);
                                    SmsManager smsManager3 = SmsManager.getDefault();
                                    smsManager3.sendTextMessage(num3, null, message, null, null);
                                    Log.d("sqx",num1);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        Query checkUser = reference.orderByChild("email").equalTo(username);
        // ds1.child("email").getValue(String.class).equals(user.getEmail())
        // reference = FirebaseDatabase.getInstance().getReference().child("Users");
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//

//                    String phoneNumber = "88" + eContact1FromDB;
//                    String eContact2FromDB = snapshot.child(username).child("em2").getValue(String.class);
//                    String phoneNumber2 = "88" + eContact2FromDB;
//                    String eContact3FromDB = snapshot.child(username).child("em3").getValue(String.class);
//                    String phoneNumber3 = "88" + eContact3FromDB;
//
//                    String dangerMessage = "**User is in DANGER**\n";
//                    String link = "\nLink = https://www.google.com/maps/search/?api=1&query=" + addresses.get(0).getLatitude() +
//                            "," + addresses.get(0).getLongitude();
//                    String message = dangerMessage + "Address = " + addresses.get(0).getAddressLine(0) +
//                            "\nLocality = " + addresses.get(0).getLocality() +
//                            "\nCountry = " + addresses.get(0).getCountryName() +
//                            "\nLatitude = " + addresses.get(0).getLatitude() +
//                            "\nLongitude = " + addresses.get(0).getLongitude() + link;
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//                    SmsManager smsManager2 = SmsManager.getDefault();
//                    smsManager2.sendTextMessage(phoneNumber2, null, message, null, null);
//                    SmsManager smsManager3 = SmsManager.getDefault();
//                    smsManager3.sendTextMessage(phoneNumber3, null, message, null, null);
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}