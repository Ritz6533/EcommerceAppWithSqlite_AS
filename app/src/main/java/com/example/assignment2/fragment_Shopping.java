package com.example.assignment2;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
public class fragment_Shopping extends Fragment {

    private RecyclerView productList;
    private ArrayList<String> productNameList;
    private ArrayList<String> productpriceList;
    private ArrayList<String> productdescList;
    private ArrayList<String> productidList;
    private ArrayList<String> productimgList;
    DbHelper DB;

    public fragment_Shopping() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        DB = new DbHelper(getActivity());

        // Initialize the RecyclerView and the ArrayLists
        productList = view.findViewById(R.id.view2);

        productNameList = new ArrayList<>();
        productpriceList = new ArrayList<>();
        productdescList = new ArrayList<>();
        productidList = new ArrayList<>();
        productimgList = new ArrayList<>();

        // Call the method to populate the RecyclerView with product data
        addproductrecycleview2();

        return view;
    }

    private void addproductrecycleview2() {
        // Get the product data from the database using a cursor
        Cursor cursor = DB.getAllProducts();
        if (!cursor.moveToFirst()) {
            // If the cursor is empty, log a message and return from the method
            Log.d("MyActivity", "No data found in cursor");
            return;
        }

        // Iterate through the cursor and add the values to the ArrayLists
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

        // Create separate adapters for each RecyclerView and pass the corresponding data

        RecycleViewProductLists recycleviewproductlistis = new RecycleViewProductLists(productNameList, productimgList, productpriceList, productdescList, productidList,getActivity().getSupportFragmentManager(),false);
        // Set up the RecyclerView with a LinearLayoutManager and the recycleviewProducts adapter
        this.productList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.productList.setAdapter(recycleviewproductlistis);
    }
}

