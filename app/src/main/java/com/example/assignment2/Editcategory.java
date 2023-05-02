package com.example.assignment2;

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

public class Editcategory extends DialogFragment {

    private EditText nameEditText;

    DbHelper DB;
    public Editcategory() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcategory, container, false);

        DB = new DbHelper(getContext());
        nameEditText = view.findViewById(R.id.editCategoryName);
        Button editButton = view.findViewById(R.id.buttonEditcat);
        FloatingActionButton exitButton = view.findViewById(R.id.exitfloatingbtn1);
        Button deleteButton= view.findViewById(R.id.buttondeleteCat);

        // Retrieve the arguments passed from the RecyclerView adapter
        Bundle args = getArguments();
        String categoryId = args.getString("categoryId");
        String categoryName = args.getString("categoryName");
        nameEditText.setText(categoryName);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedCategory = nameEditText.getText().toString();

                try{
                    Boolean success = DB.updateCategory(categoryId,editedCategory);
                    Log.d("MSG", "VALUES "+categoryId+" "+editedCategory);

                    if(success){

                        Toast.makeText(getContext(), "Category Edited successfully", Toast.LENGTH_SHORT).show();
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Boolean success = DB.deleteCategory(categoryId);
                    Log.d("MSG", "VALUES "+categoryId+" "+categoryName);

                    if(success){

                        Toast.makeText(getContext(), "Category Deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Category.class);
                        startActivity(intent);
                    }
                    else {
                        nameEditText.setError("Invalid Error");
                    }
                }  catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something to delete the name, e.g. delete it from a database
                dismiss();
            }
        });

        return view;
    }

}