package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

    public class recyclevieworderhistory extends RecyclerView.Adapter<com.example.assignment2.recyclevieworderhistory.ViewHolder> {

        private final String[] orderNumber;
        private final String[] orderDate;
        private final String[] orderEmaill;
        private final String[] orderPrice;
        private final String[] orderitemNo;
        private final String[] orderProductName;
        private final String[] orderStatus;
        private String emailkey;
        DbHelper DB;

        public recyclevieworderhistory(ArrayList<String> orderNumber, ArrayList<String> orderDate , ArrayList<String> orderEmaill,
                                       ArrayList<String> orderPrice, ArrayList<String> orderitemNo, ArrayList<String> orderProductName, ArrayList<String> orderStatus) {

            this.orderNumber = orderNumber.toArray(new String[0]);
            this.orderDate = orderDate.toArray(new String[0]);
            this.orderEmaill = orderEmaill.toArray(new String[0]);
            this.orderPrice = orderPrice.toArray(new String[0]);
            this.orderitemNo = orderitemNo.toArray(new String[0]);
            this.orderProductName = orderProductName.toArray(new String[0]);
            this.orderStatus = orderStatus.toArray(new String[0]);
        }

        public static class viewHolder extends RecyclerView.ViewHolder {


            public viewHolder(@NonNull View itemView) {
                super(itemView);
            }

        }

        @NonNull
        @Override
        public com.example.assignment2.recyclevieworderhistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclevieworderhistory, parent, false);
            DB = new DbHelper(view.getContext());
            SessionManager sessionManager = new SessionManager(view.getContext());

            if (sessionManager.isLoggedIn()) {
                emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");}


                return new com.example.assignment2.recyclevieworderhistory.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.assignment2.recyclevieworderhistory.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


            holder.orderid.setText(orderNumber[position]);
            holder.orderamount.setText(orderPrice[position]);
            holder.orderdates.setText(orderDate[position]);
            holder.orderitemnumb.setText(orderitemNo[position]);
            holder.orderemailis.setText(orderEmaill[position]);
            holder.orderproductnameis.setText(orderProductName[position]);
            String pg= orderStatus[position];
            holder.progressBtn.setProgress(Integer.parseInt(pg));

            holder.recived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emailkey.equals("root")){
                        String num ="1";
                        holder.progressBtn.setProgress(1);
                        DB.updateorder(orderNumber[position],num);
                    }
                }
            });
            holder.processed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emailkey.equals("root")){
                        holder.progressBtn.setProgress(2);
                        String num ="2";
                        DB.updateorder(orderNumber[position],num);
                    }
                }
            });
            holder.delivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emailkey.equals("root")){
                        holder.progressBtn.setProgress(3);
                        String num ="3";
                        DB.updateorder(orderNumber[position],num);
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return orderNumber.length;
        }



        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView orderid,orderamount,orderdates,orderitemnumb,orderemailis,orderproductnameis;

            private TextView recived, processed, delivered;
            private ProgressBar progressBtn;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                orderid = itemView.findViewById(R.id.orderTextView);
                orderamount = itemView.findViewById(R.id.totalItemPriceTextView);
                orderdates = itemView.findViewById(R.id.textorderdateis);
                orderitemnumb = itemView.findViewById(R.id.numberofitemView);
                orderemailis = itemView.findViewById(R.id.textemailis);
                orderproductnameis = itemView.findViewById(R.id.textview_productname1);
                progressBtn = itemView.findViewById(R.id.progressBar);

                recived = itemView.findViewById(R.id.textrecived);
                processed = itemView.findViewById(R.id.textprocessed);
                delivered = itemView.findViewById(R.id.textdelivered);



            }

        }
    }