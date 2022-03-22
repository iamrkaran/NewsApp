package com.clutchx.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {
    EditText et_email, et_password;
    FirebaseAuth auth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent i=new Intent(AdminLogin.this,AdminDashboard.class);
            startActivity(i);
            finish();
        }
        pd=new ProgressDialog(this);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        FirebaseApp.initializeApp(AdminLogin.this);
        auth = FirebaseAuth.getInstance();
        Button btn=findViewById(R.id.login_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_email.getText().toString().isEmpty()) {
                    et_email.setError("Empty");
                    et_email.requestFocus();
                } else {
                    if (et_password.getText().toString().isEmpty()) {
                        et_password.setError("empty");
                        et_password.requestFocus();
                    } else {
                        pd.setMessage("Loading...");
                        pd.show();
                        pd.setCanceledOnTouchOutside(false);
                        String emailaddress = et_email.getText().toString().trim();
                        String password = et_password.getText().toString().trim();
                        auth.signInWithEmailAndPassword(emailaddress,password).addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    pd.dismiss();
                                    Intent i=new Intent(AdminLogin.this,AdminDashboard.class);
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    pd.dismiss();
                                    Toast.makeText(AdminLogin.this, "In Valid User Id Or Password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }

            }
        });

    }


}