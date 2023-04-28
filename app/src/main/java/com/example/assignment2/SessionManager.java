package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    // Declare variables
    private Context context;
     SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Login method
    public void login(String emailkey) {
        editor.putString("emailkey", emailkey);
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    // Logout method
    public void logout() {
        editor.clear();
        editor.apply();
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}
