package com.codewithharry.chatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codewithharry.chatsapp.Models.Users;
import com.codewithharry.chatsapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().hide();

//        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating User");
        progressDialog.setMessage("We are Creating Your Account");


        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etUsername.getText().toString().isEmpty()){
                    binding.etUsername.setError("Enter Your Username");
                    return;
                }
                if(binding.etEmail.getText().toString().isEmpty()){
                    binding.etEmail.setError("Enter Your Email");
                    return;
                }
                if(binding.etPassword.getText().toString().isEmpty()){
                    binding.etPassword.setError("Enter Your Password");
                    return;
                }



                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.etEmail.getText().toString()
                        ,binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Users user = new Users(binding.etPassword.getText().toString(),binding.etUsername.getText().toString()
                                    ,binding.etEmail.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);

                            HashMap< String , Object> hashMap = new HashMap<>();

                            hashMap.put("onlineStatus","online");
                            hashMap.put("uid",id);
                            database.getReference().child("Users").child(id).updateChildren(hashMap);


                            Toast.makeText(SignUpActivity.this, "User Created Successfully Welcome to ConnectIn", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                            finish();
//                            binding.etPassword.setText("");
//                            binding.etUsername.setText("");
//                            binding.etEmail.setText("");

                        }else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        binding.tvalreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                overridePendingTransition(R.transition.slideinleft,R.transition.slideoutright);
                finish();
            }
        });
    }
}