package com.clutchx.newsapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AddPost extends AppCompatActivity {
    EditText et_head,et_des;
    ImageView opencamera;
    Button btn_submit;
    DatabaseReference dbref,newsref;
    String headline,description;
    Spinner cat_spinner;
    Bitmap bitmap;
    StorageReference storageReference;
    ProgressDialog pd;
    String category;
    String downloadurl;
    AlertDialog alertDialog;
    ArrayAdapter adapter;
    String [] data=new String[]{"Select a Category","Sports News","Technology","Weather","Crime News","Breaking News","Government"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Objects.requireNonNull(getSupportActionBar()).hide();
        et_des=findViewById(R.id.et_des);
        et_head=findViewById(R.id.et_head);
        pd=new ProgressDialog(this);
        opencamera=findViewById(R.id.opencamera);
        btn_submit=findViewById(R.id.btn_submit);
        storageReference= FirebaseStorage.getInstance().getReference();
        dbref= FirebaseDatabase.getInstance().getReference();
        cat_spinner=findViewById(R.id.cat_spinner);
        ActivityCompat.requestPermissions(AddPost.this,new String[]{Manifest.permission.CAMERA},0);
        adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,data);
        cat_spinner.setAdapter(adapter);
        checkmyvalue();
        opencamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmydialog();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                headline=et_head.getText().toString().trim();
                description=et_des.getText().toString().trim();
                if(headline.isEmpty()){
                    et_head.setError("Enter The headline");
                    et_head.requestFocus();
                }
                else if (description.isEmpty())
                {
                    et_des.setError("Enter The Description");
                    et_des.requestFocus();
                }
                else{
                    pd.setMessage("UpLoading...");
                    pd.show();
                    uploadImage();
                }


            }
        });
    }
//for checking category value
    private void checkmyvalue() {
        cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item=parent.getItemAtPosition(position);
                if(item.toString().equals("Sports News")){
                    newsref=dbref.child("News Record").child("Sports News");
                    category="Sports News";
                }
                else if(item.toString().equals("Technology")){
                    newsref=dbref.child("News Record").child("Technology");
                    category="Technology";
                }
                else if (item.toString().equals("Weather")){
                    newsref=dbref.child("News Record").child("Weather");
                    category="Weather";
                }
                else if (item.toString().equals("Crime News")){
                    newsref=dbref.child("News Record").child("Crime News");
                    category="Crime News";
                }
                else if (item.toString().equals("Breaking News")){
                    newsref=dbref.child("News Record").child("Breaking News");
                    category="Breaking News";
                }
                else if (item.toString().equals("Government")){
                    newsref=dbref.child("News Record").child("Government");
                    category="Government";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void uploadImage() {

        ByteArrayOutputStream bios= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bios);
        byte[] finalimg=bios.toByteArray();
        final StorageReference filepath=storageReference.child("News Images").child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddPost.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                   // Toast.makeText(AddPost.this, "Image Upload Successfull", Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl=String.valueOf(uri);
                                   // Toast.makeText(AddPost.this, downloadurl, Toast.LENGTH_LONG).show();
                                    insertdata();
                                }
                            });
                        }
                    });
                }
                else{
                    Toast.makeText(AddPost.this, "Image Not Upload", Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                }

            }
        });
    }
//for inserting data in dbms.
    private void insertdata() {
        final String uniquekey=newsref.push().getKey();
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("dd-MM-yyyy");
        String date=currentdate.format(calfordate.getTime());
        Calendar calfortime=Calendar.getInstance();
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm");
        String time=currenttime.format(calfortime.getTime());
        HashMap hp=new HashMap();

        hp.put("Headline",headline);
        hp.put("Description",description);
        hp.put("Imgurl",downloadurl);
        hp.put("time",time);
        hp.put("date",date);
        hp.put("category",category);
        hp.put("Key",uniquekey);
        newsref.child(uniquekey).updateChildren(hp).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    pd.dismiss();
                    et_des.setText("");
                    et_head.setText("");
                    opencamera.setImageResource(R.drawable.camera_icon);
                    cat_spinner.setAdapter(adapter);
                    Toast.makeText(AddPost.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    pd.dismiss();
                    Toast.makeText(AddPost.this, "Data Insertition Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showmydialog() {
        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        View dialogview=inflater.inflate(R.layout.custom_camera,null);
        dialog.setView(dialogview);
        alertDialog=dialog.create();
        alertDialog.show();
        LinearLayout camera_btn,galary_btn;
        camera_btn=alertDialog.findViewById(R.id.camera_btn);
        galary_btn=alertDialog.findViewById(R.id.galary_btn);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeimg=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takeimg,0);


            }
        });
        galary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickgallery =new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickgallery,1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            opencamera.setImageBitmap(bitmap);
            alertDialog.dismiss();
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            opencamera.setImageBitmap(bitmap);
            alertDialog.dismiss();
        }
    }
}
