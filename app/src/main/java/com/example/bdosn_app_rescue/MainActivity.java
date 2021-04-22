package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karan.churi.PermissionManager.PermissionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    FirebaseAuth auth;
    FirebaseUser user;
    PermissionManager manager;
    FusedLocationProviderClient fusedLocationProviderClient;
    DatabaseReference reference, circleRef;
    String ownId;

    private GoogleMap mMap;
    GoogleApiClient client;
    LocationRequest request;
    LatLng latLng;
    double lat, longi;
    public static int distance = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        //      Log.d("user1234",auth.getUid());
        if (user == null) {

            manager = new PermissionManager() {
            };
            manager.checkAndRequestPermissions(this);
        } else {

        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //getLocation();





        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(24000);
        request.setFastestInterval(24000);
        request.setSmallestDisplacement(distance);

















    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        insertDataIntoDatabase(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                        Log.d("xdcfvgbhjn", addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        manager.checkResult(requestCode, permissions, grantResults);
        ArrayList<String> ps = manager.getStatus().get(0).denied;
        if (ps.isEmpty()) {

        }
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


    //set current location
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        client.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(24000);
        request.setFastestInterval(24000);
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
            Log.d("hhihio", location.getLatitude() + " " + location.getLongitude());
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
            insertDataIntoDatabase(location.getLatitude(), location.getLongitude());

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //  client.connect();

    }

    @Override
    protected void onStop() {

        if (user != null) {
            client.connect();
        }
        super.onStop();
    }
}