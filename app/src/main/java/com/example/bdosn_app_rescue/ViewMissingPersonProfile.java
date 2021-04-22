package com.example.bdosn_app_rescue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewMissingPersonProfile extends AppCompatActivity {
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);
        backBtn = findViewById(R.id.profile_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ViewMissingPersonProfile.this, ViewMissingPersonList.class);
                ViewMissingPersonProfile.this.startActivity(intent1);
            }
        });
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d("pkp", "1234567");
        if (getIntent().hasExtra("name") && getIntent().hasExtra("location") &&
                getIntent().hasExtra("contact") && getIntent().hasExtra("image")) {
            String imageUrl = getIntent().getStringExtra("image");
            String name = getIntent().getStringExtra("name");
            String location = getIntent().getStringExtra("location");
            String contact = getIntent().getStringExtra("contact");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference().child("MissingPersons");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.child("image").getValue(String.class).equals(imageUrl)) {
                                String lastSeen = ds.child("last_seen").getValue(String.class);
                                String gender = ds.child("gender").getValue(String.class);
                                String relation = ds.child("relation").getValue(String.class);
                                String height = ds.child("height").getValue(String.class);
                                String age = ds.child("age").getValue(String.class);
                                String desc = ds.child("desc").getValue(String.class);
                                setImage(imageUrl, name, lastSeen, location, age, height, relation, gender, desc, contact);

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


    private void setImage(String imageUrl, String imageName, String lastSeen, String location, String age, String height, String relation, String gender, String desc, String contact) {

        TextView name = findViewById(R.id.profile_missing_name_text);
        TextView lastSeen1 = findViewById(R.id.profile_last_seen_text);
        TextView location1 = findViewById(R.id.profile_missing_location_text);
        TextView age1 = findViewById(R.id.profile_age_text);
        TextView height1 = findViewById(R.id.profile_height_text);
        TextView relation1 = findViewById(R.id.profile_relation_text);
        TextView gender1 = findViewById(R.id.profile_gender_text);
        TextView desc1 = findViewById(R.id.profile_general_desc_text);
        TextView contact1 = findViewById(R.id.profile_contact_text);
        ImageView img = findViewById(R.id.profile_missing_photo);

        name.setText(imageName);
        lastSeen1.setText(lastSeen);
        location1.setText(location);
        age1.setText(age);
        height1.setText(height);
        relation1.setText(relation);
        gender1.setText(gender);
        desc1.setText(desc);
        contact1.setText(contact);
        Picasso.get().load(imageUrl).into(img);
        //  Log.d("pkp", contact);

    }
}
