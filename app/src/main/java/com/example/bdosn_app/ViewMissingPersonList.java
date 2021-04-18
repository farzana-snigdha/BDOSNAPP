package com.example.bdosn_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewMissingPersonList extends AppCompatActivity {
    ListView listView;
    FirebaseListAdapter adapter;
    AutoCompleteTextView search;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_missing_person_list);
        listView = findViewById(R.id.missing_list);
        search = findViewById(R.id.missing_person_search);

        ref = FirebaseDatabase.getInstance().getReference("MissingPersons");

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(event);

        Query query = FirebaseDatabase.getInstance().getReference().child("MissingPersons");

        populateListView(query);
//        listView.setOnItemClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void populateListView(Query query) {
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
    }

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> searchResult = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                String name = ds.child("name").getValue(String.class);
                String loc = ds.child("location").getValue(String.class);
                searchResult.add(name);
                searchResult.add(loc);
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResult);
            search.setAdapter(adapter);
            search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String res = search.getText().toString();
                    getSearchedResult(res);
                }
            });
        } else {
            Log.d("missing person", "no data found");
        }
    }

    private void getSearchedResult(String res) {
        Query query = ref.orderByChild("name").equalTo(res);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        MissingPerson missingPerson = new MissingPerson(ds.child("contact").getValue(String.class),
                                ds.child("image").getValue(String.class), ds.child("location").getValue(String.class),
                                ds.child("name").getValue(String.class));
                        arrayList.add("NAME     : " + missingPerson.getName() +
                                "\nLOCATION : " + missingPerson.getLocation() +
                                "\nCONTACT  : " + missingPerson.getContact());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(ViewMissingPersonList.this, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(adapter);

                }
                Query query1 = ref.orderByChild("location").equalTo(res);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                        } else {
                            Toast.makeText(ViewMissingPersonList.this, "No Data Found", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
