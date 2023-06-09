package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Shop extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DbHelper DB;

    String TheChannelID = "channel id";
    String TheChannelName = "ce";
    String Descriptionis = "a";
    int notificationid = 0;
    String emailkey;

    ImageView menuIcon;
    ConstraintLayout contentView;
    Button cartsIcon;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Button s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        DB = new DbHelper(this);
        cartsIcon = findViewById(R.id.carticon);
        fragmentview();
        //Hooks
        menuIcon = findViewById(R.id.menu_icon);

        contentView = findViewById(R.id.content);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");

        }else{emailkey="000";}
        setupNavigationDrawer(emailkey);


        if (!emailkey.equals("000"))
            notificationadd();



        DB.insertcategory("Books");



    }

    public void notificationadd() {
        createNotificationChannel();

        Intent intent = new Intent(this, Shop.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, TheChannelID)
                .setSmallIcon(R.drawable.logobk)
                .setContentTitle("WELCOME TO SHOPPY APP")
                .setContentText(" Hello!! ID- "+emailkey)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(" Hello!! ID- "+emailkey))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationid, builder.build());


    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = TheChannelName;
            String description = Descriptionis;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(TheChannelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }




    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_shop:
                FragmentManager ss = getSupportFragmentManager();
                ss.beginTransaction()
                        .replace(R.id.fragmentshopview, fragment_Shopping.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
                onBackPressed();
                break;
            case R.id.nav_profile:
                Intent intentx = new Intent(getApplicationContext(),Profile.class);
                startActivity(intentx);
                break;
            case R.id.nav_all_notification:
                Toast.makeText(Shop.this, "Clicked the navigation", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
            case R.id.nav_liked_Items:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentshopview, fragment_fav_items.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
                onBackPressed();
                break;
            case R.id.nav_order_history:
                Intent xx = new Intent(Shop.this, orderhistory.class);
                startActivity(xx);
                break;
            case R.id.nav_cart:

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentshopview, fragment_cart.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
                onBackPressed();
                break;
            case R.id.nav_about_us:
                Toast.makeText(Shop.this, "Clicked about us", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
            case R.id.nav_faq:
                Toast.makeText(Shop.this, "Clicked the FAQ", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
            case R.id.nav_exit:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
            case R.id.nav_login:
                Intent login = new Intent(Shop.this, Login.class);
                startActivity(login);
                break;
            case R.id.nav_register:
                Intent reg = new Intent(Shop.this, Register.class);
                startActivity(reg);
                break;
            case R.id.nav_logout:
                Intent logout = new Intent(Shop.this, Shop.class);
                SessionManager sessionManager = new SessionManager(Shop.this);
                sessionManager.logout();
                finishAffinity();

                startActivity(logout);
                break;
            case R.id.nav_category:
                Intent category = new Intent(Shop.this, Category.class);
                startActivity(category);
                break;
            case R.id.nav_order_List:
                Intent ol = new Intent(Shop.this, orderhistory.class);
                startActivity(ol);
                break;
            case R.id.nav_products:
                Intent p = new Intent(Shop.this, Products.class);
                p.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(p);
                break;
        }
        return true;
    }

    private void setupNavigationDrawer(String userType) {

        Menu menu = navigationView.getMenu();
        // Clear the existing menu items
        menu.clear();
        menu.close();
        // View appropriate menu based on the user type
        if (userType.equals("root")) {
            navigationView.inflateMenu(R.menu.admin_navigation_menu);

            menu.close();

        } else if(userType.equals("000")) {
            navigationView.inflateMenu(R.menu.def_navigation_menu);        menu.close();

        } else {
            navigationView.inflateMenu(R.menu.userlogged_menu);        menu.close();

        }
        //adding details in nav header


        //navigationView.setVisibility(View.VISIBLE);
        navigationView.bringToFront();

        // Set the item selection listener
        navigationView.setNavigationItemSelectedListener(this);
        // Set the text of the username TextView in the header
        if(!userType.equals("000")) {

            Cursor cursor = DB.getUserDataById(userType);
            View headerView = navigationView.getHeaderView(0);
            TextView emailis = headerView.findViewById(R.id.nav_email);
            TextView usernameis = headerView.findViewById(R.id.nav_username);
            ImageView imgDisplay = headerView.findViewById(R.id.nav_userimg);
// iterate through the cursor and display the data in your UI
            while (cursor.moveToNext()) {
                String getemail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                emailis.setText(getemail);

                String getfullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
                usernameis.setText(getfullname);

                String getimagepath = cursor.getString(cursor.getColumnIndexOrThrow("imagepath"));
                //  Convert the Uri string back to a Uri object
                try{
                    Uri uriImg = Uri.parse(getimagepath);
                    imgDisplay.setImageURI(uriImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }}

            // Set the default checked item
        navigationView.setCheckedItem(R.id.nav_shop);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void fragmentview(){

        //default fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentshopview, fragment_Shopping.class, null)
                .commit();

        Button shoppy = findViewById(R.id.fra_shopping);
        Button carts = findViewById(R.id.fra_cart);
        Button likeditems = findViewById(R.id.fra_liked);
        shoppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentshopview, fragment_Shopping.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();

            }
        });
        carts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentshopview, fragment_cart.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();

            }
        });
        likeditems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentshopview, fragment_fav_items.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();

            }
        });


    }
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        fragmentview();
        DB = new DbHelper(this);
        //Hooks
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            emailkey = sessionManager.sharedPreferences.getString("emailkey", "000");

        }else{emailkey="000";}
        setupNavigationDrawer(emailkey);





    }

    public void cartlog(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentshopview, fragment_cart.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // Name can be null
                .commit();
    }
    }

