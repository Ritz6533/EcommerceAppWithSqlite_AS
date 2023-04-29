package com.example.assignment2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleviewCategory extends RecyclerView.Adapter<RecycleviewCategory.ViewHolder> {

    private String[] arrayList;

    public RecycleviewCategory(ArrayList<String> arrayList) {

        this.arrayList = arrayList.toArray(new String[0]);
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
    public void onBindViewHolder(@NonNull RecycleviewCategory.ViewHolder holder, int position) {

        holder.textView3.setText(arrayList[position]);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Shop.class);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.length;
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
