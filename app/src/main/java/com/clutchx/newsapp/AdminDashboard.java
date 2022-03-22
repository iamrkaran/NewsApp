package com.clutchx.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
    public void addPost(View view){
        Intent i = new Intent(AdminDashboard.this,AddPost.class);
        startActivity(i);
    }
    public void updelData(View view){
        Intent i=new Intent(AdminDashboard.this, DeleteDashboard.class);
        startActivity(i);
    }
    public void Logout(View view){
       FirebaseAuth mauth=FirebaseAuth.getInstance();
       mauth.signOut();
        Intent i =new Intent(AdminDashboard.this, AdminLogin.class);
        startActivity(i);
        finish();
    }
}
