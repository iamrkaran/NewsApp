package com.clutchx.newsapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class FullScreenNews extends AppCompatActivity {
    TextView tv_head,tv_desc,tv_time,tv_date;
    ImageView news_img;
    ImageView gmail,whatsapp,share,twitter;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_news);
        tv_head=findViewById(R.id.f_tv_head);
        tv_desc=findViewById(R.id.f_tv_desc);
        tv_date=findViewById(R.id.f_date);
        tv_time=findViewById(R.id.f_time);
        news_img=findViewById(R.id.f_img);
        tv_head.setText(getIntent().getStringExtra("head"));
        tv_desc.setText(getIntent().getStringExtra("desc"));
        tv_date.setText(getIntent().getStringExtra("date"));
        tv_time.setText(getIntent().getStringExtra("time"));
        dbref= dbref= FirebaseDatabase.getInstance().getReference().child("News Record").child("Favourite");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_desc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
       }
        Picasso.get().load(getIntent().getStringExtra("img")).into(news_img);
        gmail=findViewById(R.id.btn_gmail);
        whatsapp=findViewById(R.id.btn_whatsapp);
        share=findViewById(R.id.btn_share);
        twitter=findViewById(R.id.btn_twitter);
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,getIntent().getStringExtra("head")+getIntent().getStringExtra("desc"));
                i.setPackage("com.google.android.gm");
                startActivity(i);

            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FullScreenNews.this, "Share Successful", Toast.LENGTH_SHORT).show();
            Intent obj=new Intent(Intent.ACTION_SEND);
            obj.setType("text/plain");
            obj.putExtra(Intent.EXTRA_TEXT,"*"+getIntent().getStringExtra("head")+"* "+getIntent().getStringExtra("desc"));
            obj.setPackage("com.whatsapp");
            startActivity(obj);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,getIntent().getStringExtra("head")+getIntent().getStringExtra("desc"));

                startActivity(i);


            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head, desc;
                final String uniquekey=dbref.push().getKey();
                head = tv_head.getText().toString().trim();
                desc = tv_desc.getText().toString().trim();
                HashMap hp = new HashMap();
                hp.put("Description", desc);
                hp.put("Headline", head);
                hp.put("imgurl", getIntent().getStringExtra("img"));

                dbref.child(uniquekey).updateChildren(hp).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {


                                if (task.isSuccessful()) {
                                    Toast.makeText(FullScreenNews.this, "Successfully Record Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FullScreenNews.this, "Try again Some error Occurred", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                }
            });
    }
}
