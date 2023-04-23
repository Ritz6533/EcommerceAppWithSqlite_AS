package com.example.assignment2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile extends AppCompatActivity {

    private CountryCodePicker countrycode;
    private TextView email;
    private Button back, save;
    private EditText fullName, address, postcode, password, repassword, phoneNumber;

    private FloatingActionButton saveImg;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mImageUri;

    //initialize database
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initaliseViewProfile();
        initaliseonclicklisteners();
    }

    private void initaliseViewProfile() {
        countrycode = findViewById(R.id.newregCountrycode);
        back = findViewById(R.id.btnBack);
        save = findViewById(R.id.newbtnSave);
        email = findViewById(R.id.gettextemail);
        fullName = findViewById(R.id.newregFullName);
        address = findViewById(R.id.newregAddress);
        postcode = findViewById(R.id.newregPostcode);
        password = findViewById(R.id.newregPassword);
        repassword = findViewById(R.id.newregrePassword);
        phoneNumber = findViewById(R.id.newregPhoneno);
        saveImg = findViewById(R.id.floatingActionButton);


    }
    private void initaliseonclicklisteners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }



    }