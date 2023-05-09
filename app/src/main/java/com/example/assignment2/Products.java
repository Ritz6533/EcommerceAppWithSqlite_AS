package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Products extends AppCompatActivity {

    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        DB = new DbHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addproductrecycleview();
        FloatingActionButton addproduct= findViewById(R.id.addProductbtn);

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Products.this, Addproducts.class);
                startActivity(intent);
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

    private void addproductrecycleview() {
        RecyclerView productList = findViewById(R.id.recycleViewProduct);
        ArrayList<String> productNameList = new ArrayList<>();
        ArrayList<String> productpriceList = new ArrayList<>();
        ArrayList<String> productdescList = new ArrayList<>();
        ArrayList<String> productidList = new ArrayList<>();
        ArrayList<String> productimgList = new ArrayList<>();
        // get the category names and IDs from the getCategoryList() method


            Cursor cursor = DB.getAllProducts();
            if (!cursor.moveToFirst()) {
                // If the cursor is empty, log a message and return from the method
                Log.d("MyActivity", "No data found in cursor");
                return;
            }

            // iterate through the cursor and add the values to the array lists
            do {
            String productid = cursor.getString(cursor.getColumnIndexOrThrow("product_id"));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow("productName"));
            String productdesc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String productprice = cursor.getString(cursor.getColumnIndexOrThrow("price"));
            String productimage = cursor.getString(cursor.getColumnIndexOrThrow("imagelocation"));


            productidList.add(productid);
            productNameList.add(productName);
            productdescList.add(productdesc);
            productpriceList.add(productprice);
            productimgList.add(productimage);
            } while (cursor.moveToNext());


        recycleviewProducts recycleviewproduct = new recycleviewProducts(productNameList,productimgList ,productpriceList, productdescList, productidList);


            productList.setLayoutManager(new LinearLayoutManager(Products.this));
        productList.setAdapter(recycleviewproduct);


    }

}