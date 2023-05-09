package com.example.assignment2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class fragment_cart extends Fragment {

    private RecyclerView productList;
    private ArrayList<String> productNameList;
    private ArrayList<String> productpriceList;
    private ArrayList<String> productdescList;
    private ArrayList<String> productidList;
    private ArrayList<String> productimgList;
    DbHelper DB;
    private Button checkout;
    private TextView totalprice;
    private TextView totalitems;
    private Integer val = 0,totalpriceis=0;
    private String productIds="", emailkey;


    public fragment_cart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        SessionManager sessionManager = new SessionManager(view.getContext());

        DB = new DbHelper(getActivity());

        // Initialize the RecyclerView and the ArrayLists
        productList = view.findViewById(R.id.view4);
        checkout = view.findViewById(R.id.checkoutButton);
        totalprice = view.findViewById(R.id.totalItemPriceTextView);
        totalitems = view.findViewById(R.id.numberofitemView);

        productidList = new ArrayList<>();
        productNameList = new ArrayList<>();
        productpriceList = new ArrayList<>();
        productdescList = new ArrayList<>();
        productidList = new ArrayList<>();
        productimgList = new ArrayList<>();



        // Call the method to populate the RecyclerView with product data

        if (sessionManager.isLoggedIn()) {
            emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");
            getproduct(emailkey);
            addproductrecycleview();
            totalitems.setText(String.valueOf(val));
            totalprice.setText(String.valueOf(totalpriceis));}

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean addcart = DB.addorder(emailkey,String.valueOf(val),String.valueOf(totalpriceis),productIds);

                if (addcart == true) {
                    Toast.makeText(view.getContext(), "Product Added", Toast.LENGTH_SHORT).show();
                    Boolean deletecart = DB.deletecart(emailkey);

                    Intent intent = new Intent(view.getContext(), Shop.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "Error Check the carts", Toast.LENGTH_SHORT).show();
                }




            }

        });







        return view;
    }

    private void getproduct(String emailkey) {
        // Get the product data from the database using a cursor
        Cursor cursor = DB.getcheckcartpid(emailkey);
        if (!cursor.moveToFirst()) {
            // If the cursor is empty, log a message and return from the method
            Log.d("MyActivity", "No data found in cursor");
            return;
        }

        // Iterate through the cursor and add the values to the ArrayLists
        do {
            String productid = cursor.getString(cursor.getColumnIndexOrThrow("product_id"));

            productidList.add(productid);
            val++;
            productIds += productid + ",";

        } while (cursor.moveToNext());
        Log.d("MSG","ids is "+productIds);

    }

    private void addproductrecycleview() {

        for (String productId : productidList) {
            Cursor cursor = DB.getproductById(productId);
            if (!cursor.moveToFirst()) {
                // If the cursor is empty, log a message and continue with the next product id
                Log.d("MyActivity", "No data found in cursor for product id: " + productId);
                continue;
            }

            String productprice = cursor.getString(cursor.getColumnIndexOrThrow("price"));

            String productName = cursor.getString(cursor.getColumnIndexOrThrow("productName"));

            String productdesc = cursor.getString(cursor.getColumnIndexOrThrow("description"));

            String productimage = cursor.getString(cursor.getColumnIndexOrThrow("imagelocation"));

            productNameList.add(productName);
            productdescList.add(productdesc);
            productpriceList.add(productprice);
            productimgList.add(productimage);
            // Create separate adapters for each RecyclerView and pass the corresponding data
            totalpriceis+= Integer.parseInt(productprice);
        }
        RecycleViewProductLists recycleviewproductlistis1 = new RecycleViewProductLists(productNameList, productimgList, productpriceList, productdescList, productidList, getActivity().getSupportFragmentManager(),true);
        // Set up the RecyclerView with a LinearLayoutManager and the recycleviewProducts adapter
        this.productList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.productList.setAdapter(recycleviewproductlistis1);
        //totalitems.setText(String.valueOf(val));
    }

    }