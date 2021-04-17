package com.example.bdosn_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ViewMissingPersonList extends AppCompatActivity {
    ListView listView;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_missing_person_list);
        listView = findViewById(R.id.missing_list);
        Query query = FirebaseDatabase.getInstance().getReference().child("MissingPersons");
        FirebaseListOptions<MissingPerson> options = new FirebaseListOptions.Builder<MissingPerson>()
                .setLayout(R.layout.row_item)
                .setQuery(query, MissingPerson.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name = v.findViewById(R.id.textViewMissingName);
                TextView location = v.findViewById(R.id.textViewMissingLocation);
                TextView contact = v.findViewById(R.id.textViewMissingContact);
                ImageView img = v.findViewById(R.id.imageViewMissingPerson);

                MissingPerson missingPerson = (MissingPerson) model;
                name.setText("Name: " + missingPerson.getName());
                location.setText("Location: " + missingPerson.getLocation());
                contact.setText("Contact: " + missingPerson.getContact());
                Picasso.get().load(missingPerson.getImage().toString()).into(img);


            }
        };
        listView.setAdapter(adapter);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.map_menu:
                // startActivity();
                Toast.makeText(this, "current location", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_person_sub_menu:
                Intent intent1 = new Intent(this, AddMissingPerson.class);
                this.startActivity(intent1);
                return true;
            case R.id.view_list_sub_menu:
                Intent intent2 = new Intent(this, ViewMissingPersonList.class);
                this.startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
