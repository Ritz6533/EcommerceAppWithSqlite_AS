package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Shop extends AppCompatActivity {

    DbHelper DB;

    Button s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        DB = new DbHelper(this);


        s= findViewById(R.id.s);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);

                startActivity(intent);
                Log.d("RUN ","Activity Started ");
            }
        });

    }
    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }
}