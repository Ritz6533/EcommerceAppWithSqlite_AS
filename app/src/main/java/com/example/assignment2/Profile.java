package com.example.assignment2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;

public class Profile extends AppCompatActivity {

    private CountryCodePicker countrycode;
    private TextView email, updatedDate;
    private Button back, save;
    private EditText fullName, address, postcode, password, repassword, phoneNumber;

    private FloatingActionButton addImage;
    private ImageView imgDisplay;

    private String stringUri, emailkey;

    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
             emailkey = sessionManager.sharedPreferences.getString("emailkey", "");

        }

        initaliseViewProfile();
        initaliseonclicklisteners();
        DB = new DbHelper(this);


        Cursor cursor = DB.getUserDataById(emailkey);

// iterate through the cursor and display the data in your UI
        while (cursor.moveToNext()) {
            String getemail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            email.setText(getemail);

            String getpassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            password.setText(getpassword);

            String getcountry = cursor.getString(cursor.getColumnIndexOrThrow("country"));
            //countrycode

            String getfullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
            fullName.setText(getfullname);

            String getaddress = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            address.setText(getaddress);

            String getpostcode = cursor.getString(cursor.getColumnIndexOrThrow("postcode"));
            postcode.setText(getpostcode);

            String getphoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"));
            phoneNumber.setText(getphoneNumber);

            String getimagepath = cursor.getString(cursor.getColumnIndexOrThrow("imagepath"));
            //  Convert the Uri string back to a Uri object
            try{
                Uri uriImg = Uri.parse(getimagepath);
            imgDisplay.setImageURI(uriImg);
        } catch (Exception e) {
                e.printStackTrace();
        }


            String getdateUpdated = cursor.getString(cursor.getColumnIndexOrThrow("dateUpdate"));
            updatedDate.setText(getdateUpdated);
        }





    }

    private void initaliseViewProfile() {
        countrycode = findViewById(R.id.newregCountrycode);
        back = findViewById(R.id.btnBack);
        save = findViewById(R.id.newbtnSave);
        email = findViewById(R.id.gettextemail);
        fullName = findViewById(R.id.newregFullName);
        address = findViewById(R.id.newregAddress);
        postcode = findViewById(R.id.categorydropdownspinner);
        password = findViewById(R.id.newregPassword);
        repassword = findViewById(R.id.newregrePassword);
        phoneNumber = findViewById(R.id.newregPhoneno);
        addImage = findViewById(R.id.floatingActionButton);
        imgDisplay = findViewById(R.id.userimageview);
        updatedDate = findViewById(R.id.updatedateis);



    }
    private void initaliseonclicklisteners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                startActivity(intent);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("msg","heyy uriaddressis "+ stringUri );



                //get ids
                String code= countrycode.getSelectedCountryCode();
                String countryis = countrycode.getSelectedCountryEnglishName();
                String num =phoneNumber.getText().toString();
                String phoneNumberis= phoneNumber.getText().toString();

                String emailis = email.getText().toString();
                String fullNameis = fullName.getText().toString().toUpperCase();
                String addressis = address.getText().toString().toUpperCase();
                String postcodeis = postcode.getText().toString().toUpperCase();

                String passwordis = password.getText().toString();
                String repasswordis = repassword.getText().toString();

                Log.d("MGD", "emailis = " + emailis);
                Log.d("MGD", "fullNameis = " + fullNameis);
                Log.d("MGD", "addressis = " + addressis);
                Log.d("MGD", "postcodeis = " + postcodeis);
                Log.d("MGD", "phoneNumberis = " + phoneNumberis);
                Log.d("MGD", "countryis = " + countryis);
                Log.d("MGD", "passwordis = " + passwordis);
                Log.d("MGD", "repasswordis = " + repasswordis);
                Log.d("MGD", "stringUri = " + stringUri);


                //validate code
                if (  passwordis.equals("")  || repasswordis.equals("") || postcodeis.equals("") || fullNameis.equals("") || addressis.equals("") || num.equals("")) {
                    Toast.makeText(Profile.this, "Please fill all the fields with valid values", Toast.LENGTH_SHORT).show();

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

                        Boolean insert = DB.updateData(emailis, passwordis, countryis, fullNameis, addressis, postcodeis, phoneNumberis,stringUri);
                        Log.d("MGD", "stringUri = " + stringUri);
                        if (insert == true) {
                            Toast.makeText(Profile.this, "Details Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Shop.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Profile.this, "Error failed", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Profile.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }


                }

                //
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(Profile.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();


            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, Shop.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();

                imgDisplay.setImageURI(uri);
                stringUri = uri.toString();
                Log.d("msg", "value of uri is  " + stringUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}




