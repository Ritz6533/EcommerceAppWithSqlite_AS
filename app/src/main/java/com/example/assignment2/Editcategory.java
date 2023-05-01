package com.example.assignment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Editcategory extends DialogFragment {

    private EditText nameEditText;


    public Editcategory() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcategory, container, false);

        nameEditText = view.findViewById(R.id.editCategoryName);
        Button editButton = view.findViewById(R.id.buttonEditcat);
        FloatingActionButton exitButton = view.findViewById(R.id.exitfloatingbtn1);
        Button deleteButton= view.findViewById(R.id.buttondeleteCat);

        //String getcategory = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        //nameEditText.setText(getcategory);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEditText.getText().toString();
                // Do something with the new name, e.g. save it to a database
                dismiss();
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