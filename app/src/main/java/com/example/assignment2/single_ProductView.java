package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class single_ProductView  extends Fragment {

    DbHelper DB;
    ImageView imgDisplay;
    Button addCartbtn, addLikebtn;
    String stringUri, categoryidis, pid;
    TextView productName, categoryName, marketprice, productDescription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_single_product_view, container, false);
        DB = new DbHelper(getContext());

        // return view;
        Bundle args = getArguments();
        if (args != null) {
            pid = args.getString("pid");
        }
        Log.d("MSG", "ID is is =" + pid);

        categoryName = view.findViewById(R.id.category_name);
        marketprice = view.findViewById(R.id.product_price);
        productName = view.findViewById(R.id.product_name);
        productDescription = view.findViewById(R.id.product_description);
        addCartbtn = view.findViewById(R.id.add_to_cart_button);
        imgDisplay = view.findViewById(R.id.product_image);
        addLikebtn = view.findViewById(R.id.btnlikes);

        SessionManager sessionManager = new SessionManager(view.getContext());

        if (sessionManager.isLoggedIn()) {
            String emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");
            Boolean checklikeditem = DB.checklikeditem(emailkey, pid);
            if (checklikeditem) {
                addLikebtn.setText("Liked");
                addLikebtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
            }}
            //getpid

            Cursor cursor = DB.getproductById(pid);



// iterate through the cursor and display the data in your UI
            while (cursor.moveToNext()) {

                String mp = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                marketprice.setText("Price $" + mp);

                String pname = cursor.getString(cursor.getColumnIndexOrThrow("productName"));
                productName.setText(pname);

                String pdes = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                productDescription.setText(pdes);

                String getimagepath = cursor.getString(cursor.getColumnIndexOrThrow("imagelocation"));
                //  Convert the Uri string back to a Uri object
                try {
                    Uri uriImg = Uri.parse(getimagepath);
                    imgDisplay.setImageURI(uriImg);
                    stringUri = uriImg.toString();
                    Log.d("msg", "value of uri initially " + stringUri);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                categoryidis = cursor.getString(cursor.getColumnIndexOrThrow("category_id"));
                Cursor x = DB.getcategoryyId(categoryidis);

                while (x.moveToNext()) {
                    String namez = x.getString(x.getColumnIndexOrThrow("categoryName"));
                    categoryName.setText(namez);
                }


            }
            addCartbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sessionManager.isLoggedIn()) {
                        String emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");

                            Boolean inserttocart = DB.insertcart(emailkey, pid);

                            Toast.makeText(view.getContext(), "Added To Cart", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(view.getContext(), "Please Login To Buy items", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            addLikebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (sessionManager.isLoggedIn()) {
                        String emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");

                        Boolean checklikeditem = DB.checklikeditem(emailkey, pid);
                        if (!checklikeditem) {
                            Boolean insert = DB.insertlikeditem(emailkey, pid);
                            addLikebtn.setText("Liked");
                            addLikebtn.setBackgroundColor(getResources().getColor(R.color.teal_200));


                            Toast.makeText(view.getContext(), "Liked", Toast.LENGTH_SHORT).show();}

                    else{
                            Boolean delete = DB.deletelikeditems(emailkey,pid);
                            Toast.makeText(view.getContext(), "Liked removed", Toast.LENGTH_SHORT).show();
                            addLikebtn.setText("Like");
                            addLikebtn.setBackgroundColor(getResources().getColor(R.color.grey));

                        }


                    } else {
                        Toast.makeText(view.getContext(), "Login To Add items", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return view;

        }

    }