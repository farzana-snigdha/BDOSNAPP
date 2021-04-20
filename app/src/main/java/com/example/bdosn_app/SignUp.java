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


public class SignUp extends AppCompatActivity {
     EditText NameEditText,EmailEditText,PasswordEditText,n1EditText,n2EditText,n3EditText;
     Button RegBtn;
     FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        NameEditText=findViewById(R.id.name);
        EmailEditText=findViewById(R.id.email);
        PasswordEditText=findViewById(R.id.c_password);
        n1EditText=findViewById(R.id.n1);
        n2EditText=findViewById(R.id.n2);
        n3EditText=findViewById(R.id.n3);
        RegBtn=findViewById(R.id.signupbtn);
         fAuth=FirebaseAuth.getInstance();

         RegBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String email = EmailEditText.getText().toString().trim();
                 String password= PasswordEditText.getText().toString().trim();

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
             }

         });


    }
}