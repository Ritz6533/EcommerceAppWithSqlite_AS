package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        addrecycleview();
    }

    private void addrecycleview(){

        RecyclerView CategoryList = findViewById(R.id.recycleViewCategory);
        ArrayList<String> categoryname = new ArrayList<>();

        categoryname.add("HEY");

        RecycleviewCategory recycleviewcategory = new RecycleviewCategory(categoryname);
        CategoryList.setLayoutManager(new LinearLayoutManager(Category.this));

        CategoryList.setAdapter(recycleviewcategory);
        FloatingActionButton addCatagory= findViewById(R.id.addCategorybtn);



        addCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Shop.class);
                startActivity(intent);
            }
        });

    }

}