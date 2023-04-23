package com.example.connect_my_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    EditText etfullname,etemail,etpassword,etusername;
    Button btsignup;
    TextView txtlogin;
    ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        etemail=findViewById(R.id.email);
        etfullname=findViewById(R.id.fullname);
        etusername=findViewById(R.id.username);
        etpassword=findViewById(R.id.password);
        btsignup=findViewById(R.id.buttonsignup);
        txtlogin=findViewById(R.id.textView5);
        progressBar=findViewById(R.id.progress);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signup.this, login.class);
                startActivity(intent);
                finish();
            }
        });
        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, username, password, email;
                fullname = etfullname.getText().toString();
                username = etusername.getText().toString();
                password = etpassword.getText().toString();
                email = etemail.getText().toString();
                if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putData = new PutData("http://192.168.110.36/loginsignup/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(signup.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(), login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(signup.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(signup.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}