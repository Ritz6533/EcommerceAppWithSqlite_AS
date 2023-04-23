package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;


public class Register extends AppCompatActivity {

    private CountryCodePicker countrycode;
    private Button login, register;
    private EditText email, fullName, address, postcode, password, repassword, phoneNumber;

    private String phoneNumberFinal;

    //initialize database
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initaliseView();
        setListeners();
        DB = new DbHelper(this);

    }

    private void initaliseView() {
        countrycode = findViewById(R.id.regCountrycode);
        login = findViewById(R.id.newbtnLogin);
        register = findViewById(R.id.btnNewReg);
        email = findViewById(R.id.regEmail);
        fullName = findViewById(R.id.regFullName);
        address = findViewById(R.id.regAddress);
        postcode = findViewById(R.id.regPostcode);
        password = findViewById(R.id.regPassword);
        repassword = findViewById(R.id.regrePassword);
        phoneNumber = findViewById(R.id.regPhoneno);

    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Email must contain an @ symbol
        if (!email.contains("@")) {
            return false;
        }
        // Email must contain at least one period after the @ symbol
        int index = email.indexOf("@");
        return email.indexOf(".", index) > index;
    }

        private void setListeners() {

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String code= countrycode.getSelectedCountryCode();
                    String countryis = countrycode.getSelectedCountryEnglishName();
                    String num =phoneNumber.getText().toString();
                    phoneNumberFinal= phoneNumber.getText().toString();

                    String emailis = email.getText().toString().toLowerCase();
                    String fullNameis = fullName.getText().toString().toUpperCase();
                    String addressis = address.getText().toString().toUpperCase();
                    String postcodeis = postcode.getText().toString().toUpperCase();
                    String phoneNumberis = phoneNumberFinal;
                    String passwordis = password.getText().toString();
                    String repasswordis = repassword.getText().toString();

                    Log.d("MGD", "emailis = " + emailis);
                    Log.d("MGD", "fullNameis = " + fullNameis);
                    Log.d("MGD", "addressis = " + addressis);
                    Log.d("MGD", "postcodeis = " + postcodeis);
                    Log.d("MGD", "phoneNumberis = " + phoneNumberis);
                    Log.d("MGD", "countryis = " + countryis);
                    Log.d("MGD", "passwordis = " + passwordis);
                    Log.d("MGD", "repasswordis = +" + repasswordis);


                    if (emailis.equals("") || !isValidEmail(emailis) || passwordis.equals("")  || repasswordis.equals("") || postcodeis.equals("") || fullNameis.equals("") || addressis.equals("") || num.equals("")) {
                        Toast.makeText(Register.this, "Please fill all the fields with valid values", Toast.LENGTH_SHORT).show();
                        if (emailis.equals("") || !isValidEmail(emailis)) {
                            email.setError("Invalid email address");
                        }
                        if (passwordis.equals("")) {
                            password.setError("Invalid password");
                        }
                        if (repasswordis.equals("")) {
                            repassword.setError("Invalid Re-password");
                        }
                        if (postcodeis.equals("")) {
                            postcode.setError("Invalid postcode");
                        }
                        if (fullNameis.equals("")) {
                            fullName.setError("Invalid Name");
                        }
                        if (addressis.equals("")) {
                            address.setError("Invalid address");
                        }
                        if (num.equals("")) {
                            phoneNumber.setError("Invalid Phone Number");
                        }
                    }
                    else {
                        if (passwordis.equals(repasswordis)) {
                            Boolean checkuser = DB.checkemail(emailis);
                            if (checkuser == false) {
                                Boolean insert = DB.insertData(emailis, passwordis, countryis, fullNameis, addressis, postcodeis, phoneNumberis);
                                if (insert == true) {
                                    Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Shop.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Register.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }
