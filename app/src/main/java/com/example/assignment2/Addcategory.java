package com.example.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Addcategory extends DialogFragment {
    DbHelper DB;
    private EditText nameEditText;


    public Addcategory() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addcategory, container, false);

        nameEditText = view.findViewById(R.id.addCategoryName);
        Button save = view.findViewById(R.id.buttonSavecat);
        FloatingActionButton close = view.findViewById(R.id.exitfloatingbtn);
        DB = new DbHelper(getContext());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategoryName = nameEditText.getText().toString();
                Log.d("MSG", "Category name is "+newCategoryName);
                // Do something with the new name, e.g. save it to a database
                try{
                    Boolean success = DB.insertcategory(newCategoryName);
                    if(success){

                        Toast.makeText(getContext(), "Category saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Category.class);
                        startActivity(intent);
                    }
                    else {
                        nameEditText.setError("Invalid Category Name");
                    }
                }  catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Close the fragment
                dismiss();
            }
        });

        return view;
    }

}

