package com.example.bdosn_app_rescue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddMissingPerson extends AppCompatActivity {
    private ImageView missingPhoto;
    Uri imageUri;
    private FirebaseStorage storage;
    EditText name, location, age, height, gender, relation, description, contact, lastSeen;
    Button confirm;
    Bitmap bitmap;
    long maxid = 0;
    DatabaseReference ref;
    private RequestQueue requestQueue;
    private String url = "https://fcm.googleapis.com/fcm/send";
    public String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_person);
        missingPhoto = findViewById(R.id.missing_photo);
        confirm = findViewById(R.id.confirm_button);

        name = (EditText) findViewById(R.id.missing_name_text);
        location = (EditText) findViewById(R.id.missing_location_text);
        age = (EditText) findViewById(R.id.age_text);
        height = (EditText) findViewById(R.id.height_text);
        gender = (EditText) findViewById(R.id.gender_text);
        relation = (EditText) findViewById(R.id.relation_text);
        description = (EditText) findViewById(R.id.general_desc_text);
        contact = (EditText) findViewById(R.id.contact_text);
        lastSeen = (EditText) findViewById(R.id.last_seen_text);
        requestQueue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        ref = FirebaseDatabase.getInstance().getReference().child("MissingPersons");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        missingPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadInformation();
               // sendNotification();
            }
        });
    }

    private void sendNotification() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Query query = database.getReference().child("MissingPersons").limitToFirst(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String loc = ds.child("location").getValue(String.class);
                    img = ds.child("image").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    String contact = ds.child("contact").getValue(String.class);
                    String lastSeen = ds.child("last_seen").getValue(String.class);
                    String gender = ds.child("gender").getValue(String.class);
                    String relation = ds.child("relation").getValue(String.class);
                    String height = ds.child("height").getValue(String.class);
                    String age = ds.child("age").getValue(String.class);
                    String desc = ds.child("desc").getValue(String.class);
                    JSONObject json = new JSONObject();
                    try {
                        Log.d("njqejqb", String.valueOf(query));
                        json.put("to", "/topics/" + "news");
                        JSONObject notificationObj = new JSONObject();
                        notificationObj.put("title", "Missing Alert");
                        notificationObj.put("body", "Location : " + loc);

                        JSONObject extraData = new JSONObject();
                        extraData.put("location", loc);
                        extraData.put("image", img);
                        extraData.put("name", name);
                        extraData.put("contact", contact);
                        extraData.put("last_seen", lastSeen);
                        extraData.put("gender", gender);
                        extraData.put("relation", relation);
                        extraData.put("height", height);
                        extraData.put("age", age);
                        extraData.put("desc", desc);


                        json.put("notification", notificationObj);
                        json.put("data", extraData);

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                                json,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> header = new HashMap<>();
                                header.put("content-type", "application/json");
                                header.put("authorization", "key=AAAA1q9xn2M:APA91bH3yhL5J7eDVOMW1KAwJdMMegSS-t5OWLo-84z8KM2zkU1txg4bOBdx4OmfOG3DtONUA2JgBQLKVz2o1t4sEdS6H5tpObj7BbmwdJkI9Kcv8fmgNq5wu7n1NjUSzo3W-o9N1tqy");
                                Log.d("MUR", String.valueOf(header));
                                return header;
                            }
                        };
                        requestQueue.add(request);
                        Log.d("MUR", "123456789");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
                Intent i = new Intent(this, ViewEmergencyContactList.class);
                this.startActivity(i);
                finish();
                // startActivity();
               // Toast.makeText(this, "current location", Toast.LENGTH_SHORT).show();
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

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                missingPhoto.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }
    }

    private void uploadInformation() {

        final String randomKey = UUID.randomUUID().toString();
        storage = FirebaseStorage.getInstance();
        StorageReference riversRef = storage.getReference("image" + randomKey);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Information...");
        pd.show();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference("MissingPersons");
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String fAge, fContact, fDesc, fLoc, fGender, fHeight, fLastSeen, fName, fRelation, fImg;

                                pd.dismiss();

                                MissingPerson missingPerson = new MissingPerson(age.getText().toString().trim(), contact.getText().toString(),
                                        description.getText().toString(), gender.getText().toString(), height.getText().toString(),
                                        uri.toString(), lastSeen.getText().toString(), location.getText().toString(), name.getText().toString(),
                                        relation.getText().toString());
                                root.child(String.valueOf((maxid + 1) * (-1))).setValue(missingPerson);
//                                root.child(contact.getText().toString()+new Random().nextInt(1200)).setValue(missingPerson);
                                Toast.makeText(AddMissingPerson.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                                Intent intent3 = new Intent(AddMissingPerson.this, MainActivity.class);
                                AddMissingPerson.this.startActivity(intent3);
//                                name.setText("");
//                                age.setText("");
//                                height.setText("");
//                                location.setText("");
//                                lastSeen.setText("");
//                                relation.setText("");
//                                gender.setText("");
//                                description.setText("");
//                                contact.setText("");
//                                missingPhoto.setImageResource(R.drawable.ic_person);
                                sendNotification();

                            }
                        });

                    }
                }
)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }
}
