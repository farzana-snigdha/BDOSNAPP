package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    FirebaseAuth auth;
    GoogleApiClient client;
    LocationRequest request;
    LatLng latLng;
    DatabaseReference reference, circleRef;
    FirebaseUser user;
    double lat, longi;
    String ownId;
    public static int distance=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        getIncomingIntent();

    }

    private void getIncomingIntent() {
        Log.d("pkp", "1234567");
        if (getIntent().hasExtra("name") && getIntent().hasExtra("email") &&
                getIntent().hasExtra("userId")) {
            String email = getIntent().getStringExtra("email");
            String name = getIntent().getStringExtra("name");
            String userId = getIntent().getStringExtra("userId");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference().child("Users");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.child("userId").getValue(String.class).equals(userId) && ds.child("Latitude").exists() && ds.child("Longitude").exists()) {
                                lat = ds.child("Latitude").getValue(double.class);
                                longi = ds.child("Longitude").getValue(double.class);

                                {
                                    mMap.clear();
                                    // Add a marker in Sydney and move the camera
                                    LatLng sydney = new LatLng(lat, longi);
                                    mMap.addMarker(new MarkerOptions().position(sydney).title("Location Of "+ds.child("name").getValue(String.class)));
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Couldn't Get The Location",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Log.d("njni", lat + "  " + longi);

        }
    }


    private void insertDataIntoDatabase(double latitude, double longitude) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("dfghjbhb1", String.valueOf(user.getEmail()));
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        if (ds1.child("email").getValue(String.class).equals(user.getEmail())) {

                            ownId = ds1.child("userId").getValue(String.class);
                            circleRef = FirebaseDatabase.getInstance().getReference().child("Users").child(ownId);
                            circleRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    circleRef.child("Latitude").setValue(String.valueOf(latitude));
                                    circleRef.child("Longitude").setValue(String.valueOf(longitude));

                                    Log.d("fwtsftftxc", String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
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
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        client.connect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                // startActivity();
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.map_menu:
//                Intent i=new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_TEXT,"Location : "+"https://www.google.com/maps/@"+latLng.latitude+","+latLng.longitude+",17z");
//                startActivity(i.createChooser(i,"Share using: "));

                Intent i = new Intent(this, ViewEmergencyContactList.class);
                this.startActivity(i);
                return true;
            case R.id.add_person_sub_menu:
                Intent intent1 = new Intent(this, AddMissingPerson.class);
                this.startActivity(intent1);
                return true;
            case R.id.view_list_sub_menu:
                Intent intent2 = new Intent(this, ViewMissingPersonList.class);
                this.startActivity(intent2);
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

    //set current location
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(14000);
        request.setSmallestDisplacement(distance);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
client.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Marker marker;

        if (location == null) {
            Toast.makeText(getApplicationContext(), "Could not get location", Toast.LENGTH_SHORT).show();

        } else {

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();

            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
            insertDataIntoDatabase(location.getLatitude(), location.getLongitude());

        }
    }


}