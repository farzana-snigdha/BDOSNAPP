package com.example.bdosn_app_rescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ViewMissingPersonList extends AppCompatActivity {
    RecyclerView recview;
    MyAdapter adapter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_missing_person_list);

        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseRecyclerOptions<MissingPerson> options =
                new FirebaseRecyclerOptions.Builder<MissingPerson>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("MissingPersons"), MissingPerson.class)
                        .build();

        adapter = new MyAdapter(options, getApplicationContext());
        recview.setAdapter(adapter);
//        Log.d("snxjsnxjbc",new AddMissingPerson().img);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getSearchResult(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getSearchResult(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                // startActivity();
                if (user == null) {
                    Intent i = new Intent(this, SignUp.class);
                    this.startActivity(i);
                    finish();
                } else {
                    Intent i31 = new Intent(this, Profile.class);
                    this.startActivity(i31);
                    finish();
                }
                return true;
            case R.id.map_menu:
                Intent intent5 = new Intent(this, ViewEmergencyContactList.class);
                this.startActivity(intent5);
                finish();
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

    private void getSearchResult(String s) {
        FirebaseRecyclerOptions<MissingPerson> options =
                new FirebaseRecyclerOptions.Builder<MissingPerson>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("MissingPersons").orderByChild("location").startAt(s).endAt(s + "\uf8ff"), MissingPerson.class)
                        .build();

        adapter = new MyAdapter(options, getApplicationContext());
        adapter.startListening();
        recview.setAdapter(adapter);

    }
}
