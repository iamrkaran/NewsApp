package com.clutchx.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_dashboard);
    }
    public void Sports(View view){
        Intent i=new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Sports News");
        startActivity(i);
    }
    public void BreakingNews(View view){
        Intent i=new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Breaking News");
        startActivity(i);
    }
    public void Government(View view){
        Intent i=new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Government");
        startActivity(i);
    }
    public void Technology(View view){
        Intent i=new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Technology");
        startActivity(i);
    }
    public void Crime(View view){
        Intent i=new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Crime News");
        startActivity(i);
    }
    public void Weather(View view){
        Intent i=new Intent(DeleteDashboard.this,Deletelist.class);
        i.putExtra("category","Weather");
        startActivity(i);
    }
}

