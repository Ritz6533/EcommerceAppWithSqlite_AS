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

public class Editproducts extends AppCompatActivity {
    DbHelper DB;
    Spinner namesSpinner;
    ImageView imgDisplay;
    Button save, delete;
    String stringUri, categoryidis, pid;
    FloatingActionButton addImagebtn;
    EditText productName, retailPrice, listprice, marketprice, productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproducts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DB = new DbHelper(this);
        pid = getIntent().getStringExtra("pid");
        settheview();


        setlistener();
        adddropdown();

    }

    private void settheview() {
        // productName, productPrice, listprice, markedprice,productDescription;
        namesSpinner = findViewById(R.id.categorydropdownspinner1);
        retailPrice = findViewById(R.id.newRetailprice1);
        listprice = findViewById(R.id.newListprice1);
        marketprice = findViewById(R.id.newmarketprice1);
        productName = findViewById(R.id.newProductname1);
        productDescription = findViewById(R.id.txtdesc1);
        save = findViewById(R.id.newbtnaddproduct1);
        addImagebtn = findViewById(R.id.fabproductimg1);
        imgDisplay = findViewById(R.id.productimageview1);
        delete = findViewById(R.id.deleteproductbtn);

        //get the view from db//

        Cursor cursor = DB.getproductById(pid);


// iterate through the cursor and display the data in your UI
        while (cursor.moveToNext()) {
            String rp = cursor.getString(cursor.getColumnIndexOrThrow("retailprice"));
            retailPrice.setText(rp);

            String lp = cursor.getString(cursor.getColumnIndexOrThrow("listprice"));
            listprice.setText(lp);

            String mp = cursor.getString(cursor.getColumnIndexOrThrow("price"));
            marketprice.setText(mp);

            String pname = cursor.getString(cursor.getColumnIndexOrThrow("productName"));
            productName.setText(pname);

            String pdes = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            productDescription.setText(pdes);

            String getimagepath = cursor.getString(cursor.getColumnIndexOrThrow("imagelocation"));
            //  Convert the Uri string back to a Uri object
            try {
                Uri uriImg = Uri.parse(getimagepath);
                imgDisplay.setImageURI(uriImg);
                stringUri = uriImg.toString();
                Log.d("msg", "value of uri initially " + stringUri);


            } catch (Exception e) {
                e.printStackTrace();
            }

            categoryidis = cursor.getString(cursor.getColumnIndexOrThrow("category_id"));

        }

    }

    private void adddropdown() {
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
        int defaultPosition = namesAdapter.getPosition(categoryIdToNameMap.get(categoryidis));
        namesSpinner.setSelection(defaultPosition);
        namesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategoryName = parent.getItemAtPosition(position).toString();
                String selectedCategoryId = getKeyFromValue(categoryIdToNameMap, selectedCategoryName);
                categoryidis = selectedCategoryId;
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

    private void setlistener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productname = productName.getText().toString().toUpperCase();
                String listPrice = listprice.getText().toString().toUpperCase();
                String retailprice = retailPrice.getText().toString().toUpperCase();
                String marketPrice = marketprice.getText().toString();
                String pDescription = productDescription.getText().toString();
                Log.d("msg", "value of uri while adding " + stringUri);
                Log.d("msg", "value of cat id is " + categoryidis);


                //set error if the price value is not number
                try {
                    double price = Double.parseDouble(marketPrice);
                } catch (NumberFormatException e) {
                    marketprice.setError("Invalid! Add Number");
                    return;


                }
                if (categoryidis.equals("") || productname.equals("") || listPrice.equals("") || retailprice.equals("") || marketPrice.equals("") || (stringUri == null) || pDescription.equals("")) {
                    Toast.makeText(Editproducts.this, "Please fill all the fields with valid values", Toast.LENGTH_SHORT).show();


                } else {


                    Boolean addproduct = DB.updateProduct(pid,productname, marketPrice, pDescription, listPrice, retailprice, stringUri, categoryidis);

                    if (addproduct == true) {
                        Toast.makeText(Editproducts.this, "Product Edited", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Products.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Editproducts.this, "Error failed Editing products", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });

        addImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(Editproducts.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean delete = DB.deleteProduct(pid);

                if (delete == true) {
                    Toast.makeText(Editproducts.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Products.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Editproducts.this, "Error Deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    }
