package com.clutchx.newsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Deletelist extends AppCompatActivity {
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    DatabaseReference postref;
    private final List<newsdata> examplist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletelist);
        recyclerView=findViewById(R.id.deletelist);
        progressBar = findViewById(R.id.delprogressbar);
        postref= FirebaseDatabase.getInstance().getReference().child("News Record").child(getIntent().getStringExtra("category"));
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DataMethod();
    }

    private void DataMethod() {
        postref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);
                for (DataSnapshot newsnapshot:dataSnapshot.getChildren()){
                    newsdata post=newsnapshot.getValue(newsdata.class);
                    examplist.add(0,post);
                }
                DeleteAdapter adapter=new DeleteAdapter(Deletelist.this,examplist);

                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Deletelist.this, "Data Fatching failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

