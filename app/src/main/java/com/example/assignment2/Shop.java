package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Shop extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DbHelper DB;

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

    Button s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
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

        }
        emailkey= "000";
        setupNavigationDrawer(emailkey);



        if (!emailkey.equals("000"))
            notificationadd();

        s=findViewById(R.id.s);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(Shop.this, Category.class);
                startActivity(x);
            }
        });


        DB.insertcategory("category9");
        DB.insertcategory("category10");
        DB.insertcategory("category11");
        DB.insertcategory("category12");
        DB.insertcategory("category13");
        DB.insertcategory("category14");


    }

    private void notificationadd() {
        createNotificationChannel();

        Intent intent = new Intent(this, Shop.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, TheChannelID)
                .setSmallIcon(R.drawable.logobk)
                .setContentTitle("WELCOME TO SHOPPY APP")
                .setContentText("")
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
                Toast.makeText(Shop.this, "Clicked the shop", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
            case R.id.nav_profile:
                Intent intentx = new Intent(getApplicationContext(),Profile.class);
                startActivity(intentx);
                break;
            case R.id.nav_all_notification:
                Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_liked_Items:
                // Handle this case
                break;
            case R.id.nav_order_history:
                Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_cart:
                Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about_us:
                Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_faq:
                Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();                break;
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
                startActivity(logout);
                finishAffinity();
                System.exit(0);
                break;
            case R.id.nav_category:
                Intent category = new Intent(Shop.this, Category.class);
                startActivity(category);
                break;
            case R.id.nav_order_List:
                Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_products:
                Toast.makeText(Shop.this, "Clicked the profile", Toast.LENGTH_SHORT).show();

                break;
        }
        return true;
    }

    private void setupNavigationDrawer(String userType) {

        Menu menu = navigationView.getMenu();

        // Clear the existing menu items
        menu.clear();
        menu.close();
        // Inflate the appropriate menu based on the user type
        if (userType.equals("root")) {
            navigationView.inflateMenu(R.menu.admin_navigation_menu);        menu.close();

        } else if(userType.equals("000")) {
            navigationView.inflateMenu(R.menu.def_navigation_menu);        menu.close();

        } else {
            navigationView.inflateMenu(R.menu.userlogged_menu);        menu.close();

        }
        //menu.close();


        //navigationView.setVisibility(View.VISIBLE);
        navigationView.bringToFront();

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



}