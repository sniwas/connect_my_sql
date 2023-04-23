package com.example.connect_my_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText etxtpassword,etxtusername;
    Button btlogin;
    TextView txtsignup;
    ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        etxtpassword=findViewById(R.id.loginpassword);
        etxtusername=findViewById(R.id.loginusername);
        btlogin=findViewById(R.id.buttonlogin);
        progressBar=findViewById(R.id.progressBar2);
        txtsignup=findViewById(R.id.textView2);
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this, signup.class);
                startActivity(intent);
                finish();
            }
        });
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = etxtusername.getText().toString();
                password = etxtpassword.getText().toString();
                if ( !username.equals("") && !password.equals("") ) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];

                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.110.36/loginsignup/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")){
                                        Toast.makeText(login.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(login.this, Profile.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(login.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}