package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleviewCategory extends RecyclerView.Adapter<RecycleviewCategory.ViewHolder> {

    private final String[] categoryName;
    private final String[] categoryid;


    public RecycleviewCategory(ArrayList<String> arrayList,ArrayList<String> categoryid) {

        this.categoryName = arrayList.toArray(new String[0]);
        this.categoryid = categoryid.toArray(new String[0]);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        // private Button

        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    @NonNull
    @Override
    public RecycleviewCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycleview_category, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleviewCategory.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView3.setText(categoryName[position]);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((Category) v.getContext()).getSupportFragmentManager();
                Editcategory editFragment = new Editcategory();
                editFragment.show(fragmentManager, "fragment_editcategory");
            }


        });
    }



    @Override
    public int getItemCount() {
        return categoryName.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView3;
        private Button btnEdit;
        // private TextView txtEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView3 = itemView.findViewById(R.id.textCategory);
            btnEdit = itemView.findViewById(R.id.btnCategoryView);


        }

    }
}
