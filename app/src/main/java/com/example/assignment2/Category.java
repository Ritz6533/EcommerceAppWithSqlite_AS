package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Category extends AppCompatActivity {
    DbHelper DB;
    FloatingActionButton addcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addrecycleview();
        getCategoryList();
    }

    private void addrecycleview() {
        RecyclerView categoryList = findViewById(R.id.recycleViewCategory);
        ArrayList<String> categoryNameList = new ArrayList<>();
        ArrayList<String> categoryIdList = new ArrayList<>();
        // get the category names and IDs from the getCategoryList() method
        DB = new DbHelper(this);

        Cursor cursor = DB.getAllCategories();



        // iterate through the cursor and add the values to the array lists
        while (cursor.moveToNext()) {
            String categoryId = cursor.getString(cursor.getColumnIndexOrThrow("category_id"));
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("categoryName"));

            categoryIdList.add(categoryId);
            categoryNameList.add(categoryName);
        }



        RecycleviewCategory recycleviewCategory = new RecycleviewCategory(categoryNameList, categoryIdList);

        categoryList.setLayoutManager(new LinearLayoutManager(Category.this));
        categoryList.setAdapter(recycleviewCategory);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getCategoryList() {

        addcategory=findViewById(R.id.addCategorybtn);
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addcategory bannerCategory = new Addcategory();
                bannerCategory.show(getSupportFragmentManager(), "fragment_addcategory");
            }
        });
        // now you can use the array lists to populate your UI or perform other operations
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addrecycleview();
        getCategoryList();

    }


}