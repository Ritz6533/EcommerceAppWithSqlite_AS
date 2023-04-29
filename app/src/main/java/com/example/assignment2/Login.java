package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Button btnLogin, btnReg;
    private EditText email, password;

    //initialize database
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.usernamex);
        password = findViewById(R.id.passwordx);
        btnReg = findViewById(R.id.btnReg);


        DB = new DbHelper(this);
        onclicklisteners();

    }
        private void onclicklisteners() {
            btnReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), Register.class);
                    startActivity(intent);
                }
            });


            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailis = email.getText().toString().toLowerCase();
                    String passwordis = password.getText().toString();
                    Log.d("MSG", "ID/PSW " + emailis + " " + passwordis);

                    if (emailis.equals("") || passwordis.equals("")){
                        Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                        email.setError("Invalid email password");
                        password.setError("Invalid email password");}
                    else {
                        Boolean checkemailpass = DB.checkemailpassword(emailis, passwordis);
                        if (checkemailpass == true) {
                            Toast.makeText(Login.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Shop.class);
                            //clear any activity infront of login ex. register so that it cannot come back from dashboard on back click
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            SessionManager sessionManager = new SessionManager(Login.this);
                            sessionManager.login(emailis);
                            Log.d("user id is passed", "the user id is " + emailis);

                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            email.setError("Invalid email password");
                            password.setError("Invalid email password");


                        }
                    }
                }
            });

        }
    }

