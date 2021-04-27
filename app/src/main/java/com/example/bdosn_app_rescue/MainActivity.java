package com.example.bdosn_app_rescue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    PermissionManager manager;
    FusedLocationProviderClient fusedLocationProviderClient;
    DatabaseReference reference, circleRef, ref;
    String ownId, ownId1;
    private static final int REQUEST_CALL = 1;
    Button EmBtn;
    Button Emn1;
    Button Emn2;
    private String number;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        Emn1 = findViewById(R.id.h999);
        Emn2 = findViewById(R.id.h109);
        EmBtn = findViewById(R.id.emergency);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EmBtn.setBackground(getResources().getDrawable(R.drawable.round_button));
        //      Log.d("user1234",auth.getUid());
        user = auth.getCurrentUser();
        if (user == null) {

            manager = new PermissionManager() {
            };
            manager.checkAndRequestPermissions(this);

        } else {
        }


        EmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Create account first", Toast.LENGTH_SHORT).show();
                } else {
                    if (isLocationEnabled(getApplicationContext())) {
                        getLocation();
                        Log.d("kjikhi", "ji");

                    } else {
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(MainActivity.this);

                        builder.setMessage("Turn On Your Location");

                        builder.setTitle("Location Alert !");

                        builder.setCancelable(false);
                        builder.setNegativeButton(
                                "OK",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });

                        // Create the Alert dialog
                        AlertDialog alertDialog = builder.create();

                        // Show the Alert Dialog box
                        alertDialog.show();

                        Log.d("xnsxn", "xnjwbnx")

                        ;
                    }
                }
            }
        });

        Emn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("999");
            }
        });
        Emn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("109");
            }
        });
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    private void makePhoneCall(String number1) {
        number = number1;

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        builder.setMessage("Do you want to exit ?");

        builder.setTitle("Alert !");

        builder.setCancelable(false);


        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // When the user click yes button
                                // then app will close
                                finish();
                            }
                        });


        builder.setNegativeButton(
                "No",
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
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
                        smsService();
                        Log.d("xdcfvgbhjn", addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
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
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String num1 = "88" + snapshot.child("em1").getValue(String.class);
                                    String num2 = "88" + snapshot.child("em2").getValue(String.class);
                                    String num3 = "88" + snapshot.child("em3").getValue(String.class);
                                    String name = snapshot.child("name").getValue(String.class);
                                    String message = name + " is in danger.";

                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(num1, null, message, null, null);
                                    SmsManager smsManager2 = SmsManager.getDefault();
                                    smsManager2.sendTextMessage(num2, null, message, null, null);
                                    SmsManager smsManager3 = SmsManager.getDefault();
                                    smsManager3.sendTextMessage(num3, null, message, null, null);
                                    Log.d("sqx", num1);
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

    private void insertDataIntoDatabase(double latitude, double longitude) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Log.d("dfghjbhb1", String.valueOf(user.getEmail()));
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        if (ds1.child("email").getValue(String.class).equals(user.getEmail())) {

                            ownId = ds1.child("userId").getValue(String.class);
                            circleRef = FirebaseDatabase.getInstance().getReference().child("Users").child(ownId);
                            circleRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    circleRef.child("Latitude").setValue((latitude));
                                    circleRef.child("Longitude").setValue((longitude));

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
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(number);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
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
                    finish();
                } else {
                    Intent i31 = new Intent(this, Profile.class);
                    this.startActivity(i31);
                    finish();
                }
                // Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.map_menu:
                Intent i2 = new Intent(this, ViewEmergencyContactList.class);
                this.startActivity(i2);
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


    @Override
    protected void onStart() {
        super.onStart();
        //getLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  getLocation();
    }
}