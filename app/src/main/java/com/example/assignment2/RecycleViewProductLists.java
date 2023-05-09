
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
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import java.util.ArrayList;
        //shoppagevieew
        public class RecycleViewProductLists extends RecyclerView.Adapter<RecycleViewProductLists.ViewHolder> {


            private final ArrayList<String> productName;
            private final ArrayList<String> productImg;
            private final ArrayList<String> productPrice;
            private final ArrayList<String> productDesc;
            private final ArrayList<String> productId;
            private FragmentManager fragmentManager;
            private Boolean visibility;
            private String emailkey;
            DbHelper DB;

            public RecycleViewProductLists(ArrayList<String> productName, ArrayList<String> productImg, ArrayList<String> productPrice,
                                           ArrayList<String> productDesc, ArrayList<String> productId, FragmentManager fragmentManager, Boolean visibility) {
                this.productName = productName;
                this.productImg = productImg;
                this.productPrice = productPrice;
                this.productDesc = productDesc;
                this.productId = productId;
                this.fragmentManager = fragmentManager;
                this.visibility = visibility;



            }

            public static class viewHolder extends RecyclerView.ViewHolder {


                public viewHolder(@NonNull View itemView) {
                    super(itemView);
                }

            }

            @NonNull
            @Override
            public RecycleViewProductLists.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycleviewproductlists, parent, false);
                SessionManager sessionManager = new SessionManager(view.getContext());
                if (sessionManager.isLoggedIn()) {
                    emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");

                }
                DB = new DbHelper(view.getContext());
                return new RecycleViewProductLists.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecycleViewProductLists.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


                String pricval = "Price $" + productPrice.get(position);
                holder.name.setText(productName.get(position));
                holder.price.setText(pricval);
                holder.description.setText(productDesc.get(position));

                if ((productImg.get(position) != null)) {
                    try {
                        Uri uriImg = Uri.parse(productImg.get(position));
                        holder.productImg.setImageURI(uriImg);
                        String stringUri = uriImg.toString();
                        Log.d("msg", "value of uri initially onrecycleview is " + stringUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                holder.productView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putString("pid", productId.get(position));
                        Log.d("MSG", "ID is =" + productId.get(position));


                        // Create a new instance of the fragment
                        single_ProductView fragment = new single_ProductView();

                        // Set the bundle as the fragment's arguments
                        fragment.setArguments(args);

                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentshopview, fragment)
                                .setReorderingAllowed(true)
                                .addToBackStack("name") // Name can be null
                                .commit();
                    }


                });

                if (visibility){
                    holder.removebtn.setVisibility(View.VISIBLE);
                }else{holder.removebtn.setVisibility(View.GONE);}

                holder.removebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Boolean delete = DB.deletecart(emailkey,productId.get(position));
                        Toast.makeText(v.getContext(), "Removed", Toast.LENGTH_SHORT).show();
                        fragment_cart fragment1 = new fragment_cart();


                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentshopview, fragment1)
                                .setReorderingAllowed(true)
                                .addToBackStack("name") // Name can be null
                                .commit();
                    }
                });
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }

            @Override
            public int getItemCount() {
                return productName.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                private TextView name, price, description;
                private LinearLayout productView;
                private ImageView productImg;
                private FloatingActionButton removebtn;
                //FloatingActionButton removed;


                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.textview_productname1);
                    price = itemView.findViewById(R.id.textview_productprice1);
                    description = itemView.findViewById(R.id.textview_productdescription1);
                    productView = itemView.findViewById(R.id.productbox1);
                    productImg = itemView.findViewById(R.id.imageview_product1);
                    removebtn = itemView.findViewById(R.id.removefromcart);



                }
            }
        }
