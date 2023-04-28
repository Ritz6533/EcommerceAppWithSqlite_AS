package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Shop extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DbHelper DB;

    Button s;
    String TheChannelID = "channel id";
    String TheChannelName = "ce";
    String Descriptionis = "a";
    int notificationid = 0;
    String emailkey= "000";
    ImageView menuIcon;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);
        DB = new DbHelper(this);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        setupNavigationDrawer(emailkey);




        s = findViewById(R.id.s);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);

                startActivity(intent);
                Log.d("RUN ", "Activity Started ");
            }
        });


        if (!emailkey.equals("000"))
            notificationadd();


    }

    private void notificationadd() {
        createNotificationChannel();

        Intent intent = new Intent(this, Shop.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, TheChannelID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("WELCOME TO SHOPPY APP")
                .setContentText("")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(" THE USER DETAILS IS "))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

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

    private void setupNavigationDrawer(String userType) {
        // Get the navigation menu
        Menu menu = navigationView.getMenu();

        // Clear the existing menu items
        menu.clear();

        // Inflate the appropriate menu based on the user type
        if (userType.equals("root")) {
            navigationView.inflateMenu(R.menu.admin_navigation_menu);
        } else if(userType.equals("000")) {
            navigationView.inflateMenu(R.menu.def_navigation_menu);
        }
        else {
            navigationView.inflateMenu(R.menu.userlogged_menu);
        }

        // Set the item selection listener
        navigationView.setNavigationItemSelectedListener(this);

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_shop:
                    onBackPressed();
                    break;
                case R.id.nav_profile:
                    Intent intent = new Intent(Shop.this, Profile.class);
                    startActivity(intent);
                case R.id.nav_all_notification:
                    Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();
                case R.id.nav_liked_Items:

                    break;
                case R.id.nav_order_history:
                    Intent orderhistory = new Intent(Shop.this, Login.class);
                    startActivity(orderhistory);
                case R.id.nav_cart:
                    Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();
                case R.id.nav_about_us:
                    Intent about = new Intent(Shop.this, Login.class);
                    startActivity(about);
                    case R.id.nav_faq:

                    break;
                case R.id.nav_logout:
                    break;
                case R.id.nav_exit:
                    break;//
                case R.id.nav_login:
                    Intent login = new Intent(Shop.this, Login.class);
                    startActivity(login);
                    case R.id.nav_category:

                    break;
                case R.id.nav_order_List:
                    Intent orderListAdmin = new Intent(Shop.this, Login.class);
                    startActivity(orderListAdmin);
                case R.id.nav_products:
                    Intent productListAdmin = new Intent(Shop.this, Login.class);
                    startActivity(productListAdmin);
                    //creating the onclick action for the nav menu
            }
            return true;

    }
}