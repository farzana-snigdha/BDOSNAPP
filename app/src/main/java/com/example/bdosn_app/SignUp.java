package com.example.bdosn_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ThreadLocalRandom;


public class SignUp extends AppCompatActivity {
     EditText NameEditText,EmailEditText,PasswordEditText,n1EditText,n2EditText,n3EditText,AgeEditText,PhoneEditText;
     Button RegBtn;
     FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseReference =FirebaseDatabase.getInstance().getReference("Users");
        NameEditText=findViewById(R.id.name);
        EmailEditText=findViewById(R.id.email);
        PasswordEditText=findViewById(R.id.c_password);
        n1EditText=findViewById(R.id.n1);
        n2EditText=findViewById(R.id.n2);
        n3EditText=findViewById(R.id.n3);
        AgeEditText=findViewById(R.id.age);
        PhoneEditText=findViewById(R.id.uphone);
        RegBtn=findViewById(R.id.signupbtn);
         fAuth=FirebaseAuth.getInstance();

         if(fAuth.getCurrentUser() != null)
         {
             startActivity(new Intent(getApplicationContext(),MainActivity.class));
              finish();
         }
         RegBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String name = NameEditText.getText().toString().trim();
                 String email = EmailEditText.getText().toString().trim();
                 String password= PasswordEditText.getText().toString().trim();
                 String age = AgeEditText.getText().toString().trim();
                 String em1 = n1EditText.getText().toString().trim();
                 String em2 = n2EditText.getText().toString().trim();
                 String em3 = n3EditText.getText().toString().trim();
                 String phone=PhoneEditText.getText().toString().trim();

                 //Generates random number
                 int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
                 if(TextUtils.isEmpty(email))
                 {
                     EmailEditText.setError("Email is Required");
                     return;
                 }
                 if(TextUtils.isEmpty(password))
                 {
                     PasswordEditText.setError("Password is required");
                 }
                 if(password.length() < 6)
                 {
                     PasswordEditText.setError("Password");
                 }
                 fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(SignUp.this,"User Created", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(),Profile.class));
                         }
                         else
                         {
                             Toast.makeText(SignUp.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

                 //Saves data into Database
                 String key =databaseReference.push().getKey();
                 Userclass user=new Userclass(name,age,email,phone,password,em1,em2,em3,null,code,false,null,null);
                 databaseReference.child(key).setValue(user);
                 Toast.makeText(getApplicationContext(),"User info added",Toast.LENGTH_SHORT).show();

             }

         });


    }
}