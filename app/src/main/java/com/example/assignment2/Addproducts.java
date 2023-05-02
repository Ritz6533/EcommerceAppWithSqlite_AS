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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Addproducts extends AppCompatActivity {
    DbHelper DB;
    Spinner namesSpinner;
    ImageView imgDisplay;
    Button save;
    String stringUri,categoryidis;
    FloatingActionButton addImagebtn;
    EditText productName, retailPrice, listprice, marketprice,productDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproducts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DB = new DbHelper(this);
        createview();


        setlistener();
        adddropdown();

    }

    private void createview(){
       // productName, productPrice, listprice, markedprice,productDescription;
        namesSpinner = findViewById(R.id.categorydropdownspinner);
        retailPrice = findViewById(R.id.newRetailprice);
        listprice = findViewById(R.id.newListprice);
        marketprice = findViewById(R.id.newmarketprice);
        productName = findViewById(R.id.newProductname);
        productDescription = findViewById(R.id.txtdesc);
        save = findViewById(R.id.newbtnaddproduct);
        addImagebtn = findViewById(R.id.fabproductimg);
        imgDisplay = findViewById(R.id.productimageview);
    }
    private void adddropdown(){
        Cursor cursor = DB.getAllCategories();

        List<String> categoryNames = new ArrayList<>();
        final Map<String, String> categoryIdToNameMap = new HashMap<>();

        while (cursor.moveToNext()) {
            int categoryIndex = cursor.getColumnIndexOrThrow("categoryName");
            String categoryName = cursor.getString(categoryIndex);
            String categoryId = cursor.getString(cursor.getColumnIndexOrThrow("category_id"));
            categoryIdToNameMap.put(categoryId, categoryName);
            categoryNames.add(categoryName);
        }


// Create the ArrayAdapter with the retrieved names
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categoryNames);

// Set the adapter for the Spinner

        namesSpinner.setAdapter(namesAdapter);
        namesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategoryName = parent.getItemAtPosition(position).toString();
                String selectedCategoryId = getKeyFromValue(categoryIdToNameMap, selectedCategoryName);
                categoryidis =selectedCategoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private String getKeyFromValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void setlistener(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productname = productName.getText().toString().toUpperCase();
                String listPrice = listprice.getText().toString().toUpperCase();
                String retailprice = retailPrice.getText().toString().toUpperCase();
                String marketPrice = marketprice.getText().toString();
                String pDescription = productDescription.getText().toString();
                Log.d("msg","value of uri is "+stringUri);
                Log.d("msg","value of cat id is "+categoryidis);


                if (  categoryidis.equals("")  || productname.equals("") || listPrice.equals("") || retailprice.equals("") || marketPrice.equals("") || (stringUri== null) || pDescription.equals("")) {
                    Toast.makeText(Addproducts.this, "Please fill all the fields with valid values", Toast.LENGTH_SHORT).show();


                } else {


                     Boolean addproduct = DB.insertproduct(productname,marketPrice,pDescription,listPrice,retailprice,stringUri,categoryidis);

                        if (addproduct == true) {
                        Toast.makeText(Addproducts.this, "Product Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Products.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Addproducts.this, "Error failed adding products", Toast.LENGTH_SHORT).show();
                    }

                }}

        });

        addImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(Addproducts.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            Uri uri= data.getData();

        imgDisplay.setImageURI(uri);
        stringUri = uri.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}