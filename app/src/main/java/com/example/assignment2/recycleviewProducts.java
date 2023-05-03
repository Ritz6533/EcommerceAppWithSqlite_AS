package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class recycleviewProducts extends RecyclerView.Adapter<recycleviewProducts.ViewHolder> {

    private final String[] productName;
    private final String[] productprice;
    private final String[] productdesc;
    private final String[] productid;
    private final String[] productimg;


    public recycleviewProducts(ArrayList<String> productName,ArrayList<String> productimg ,ArrayList<String> productprice,ArrayList<String> productdesc, ArrayList<String> productid) {

        this.productName = productName.toArray(new String[0]);
        this.productprice = productprice.toArray(new String[0]);
        this.productdesc = productdesc.toArray(new String[0]);
        this.productid = productid.toArray(new String[0]);
        this.productimg = productimg.toArray(new String[0]);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {


        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    @NonNull
    @Override
    public recycleviewProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycleview_products, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleviewProducts.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(productName[position]);
        holder.price.setText(productprice[position]);
        holder.description.setText(productdesc[position]);

        if((productimg[position]!= null)){
            try{
            Uri uriImg = Uri.parse(productimg[position]);
            holder.productimg.setImageURI(uriImg);
            String stringUri = uriImg.toString();
            Log.d("msg", "value of uri initially onrecycleview is " + stringUri);
        } catch (Exception e) {
            e.printStackTrace();
        }}
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pid= productid[position];

                Intent intent = new Intent(v.getContext(), Editproducts.class);

                intent.putExtra("pid", pid);
                v.getContext().startActivity(intent);

            }


        });
    }

    @Override
    public int getItemCount() {
        return productName.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,price,description;
        private Button btnEdit;
        private ImageView productimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_productname);
            price = itemView.findViewById(R.id.textview_productprice);
            description = itemView.findViewById(R.id.textview_productdescription);
            btnEdit = itemView.findViewById(R.id.button_viewproduct);
            productimg = itemView.findViewById(R.id.imageview_product);



        }

    }
}