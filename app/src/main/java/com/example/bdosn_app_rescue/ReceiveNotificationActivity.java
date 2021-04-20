package com.example.bdosn_app_rescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ReceiveNotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);

        TextView name1 = findViewById(R.id.profile_missing_name_text);
        TextView lastSeen1 = findViewById(R.id.profile_last_seen_text);
        TextView location1 = findViewById(R.id.profile_missing_location_text);
        TextView age1 = findViewById(R.id.profile_age_text);
        TextView height1 = findViewById(R.id.profile_height_text);
        TextView relation1 = findViewById(R.id.profile_relation_text);
        TextView gender1 = findViewById(R.id.profile_gender_text);
        TextView desc1 = findViewById(R.id.profile_general_desc_text);
        TextView contact1 = findViewById(R.id.profile_contact_text);
        ImageView img = findViewById(R.id.profile_missing_photo);
        Button btn = findViewById(R.id.profile_back_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ReceiveNotificationActivity.this, ViewMissingPersonList.class);
                ReceiveNotificationActivity.this.startActivity(intent1);
            }
        });
        if (getIntent().hasExtra("name")) {


            String location = getIntent().getStringExtra("location");
            String image = getIntent().getStringExtra("image");
            String name = getIntent().getStringExtra("name");
            location1.setText(location);
            name1.setText(name);
            String contact = getIntent().getStringExtra("contact");
            String lastSeen = getIntent().getStringExtra("last_seen");
            contact1.setText(contact);
            lastSeen1.setText(lastSeen);

            String gender = getIntent().getStringExtra("gender");
            String age = getIntent().getStringExtra("age");
            gender1.setText(gender);
            age1.setText(age);

            String height = getIntent().getStringExtra("height");
            String relation = getIntent().getStringExtra("relation");
            relation1.setText(relation);
            height1.setText(height);
            String desc = getIntent().getStringExtra("desc");
            desc1.setText(desc);

            Picasso.get().load(image).into(img);
        }
    }

}
