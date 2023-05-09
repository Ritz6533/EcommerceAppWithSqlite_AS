package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class orderhistory extends AppCompatActivity {
    private String emailkey;
    DbHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DB = new DbHelper(this);
        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");
        }
        addorderview();

        }
    private void addorderview() {
        RecyclerView productList = findViewById(R.id.vieworderhistory);
        ArrayList<String> orderNumberlist = new ArrayList<>();
        ArrayList<String> orderEmaillidt = new ArrayList<>();
        ArrayList<String> orderPricelist = new ArrayList<>();
        ArrayList<String> orderitemNolist = new ArrayList<>();
        ArrayList<String> orderProductNamelist = new ArrayList<>();
        ArrayList<String> orderStatuslist = new ArrayList<>();
        ArrayList<String> orderDatelist = new ArrayList<>();
        // get the category names and IDs from the getCategoryList() method



        Cursor cursor = DB.getOrdersbyemail(emailkey);

        if (!cursor.moveToFirst()) {
            // If the cursor is empty, log a message and return from the method
            Log.d("MyActivity", "No data found in cursor");
            return;
        }

        // iterate through the cursor and add the values to the array lists
        do {
            String odereid = cursor.getString(cursor.getColumnIndexOrThrow("order_id"));
            String orderDate = cursor.getString(cursor.getColumnIndexOrThrow("orderDate"));
            String orderEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String ordertotalprice = cursor.getString(cursor.getColumnIndexOrThrow("totalprice"));
            String ordertotalitems = cursor.getString(cursor.getColumnIndexOrThrow("totalitems"));
            String orderproductNameall = cursor.getString(cursor.getColumnIndexOrThrow("product_idsofall"));
            String orderstat = cursor.getString(cursor.getColumnIndexOrThrow("orderstatus"));


            orderNumberlist.add(odereid);
            orderDatelist.add(orderDate);
            orderEmaillidt.add(orderEmail);
            orderPricelist.add(ordertotalprice);
            orderitemNolist.add(ordertotalitems);
            orderProductNamelist.add(orderproductNameall);
            orderStatuslist.add(orderstat);
        } while (cursor.moveToNext());


        recyclevieworderhistory recyclevieworders = new recyclevieworderhistory(orderNumberlist,orderDatelist ,orderEmaillidt, orderPricelist, orderitemNolist,orderProductNamelist,orderStatuslist);


        productList.setLayoutManager(new LinearLayoutManager(orderhistory.this));
        productList.setAdapter(recyclevieworders);


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