package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
//creating the database for the list of user
    public static  final  String DBNAME = "DatabaseEcom.db";

    public DbHelper(Context context) {
        super(context, "DatabaseEcom.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        try {
            MyDB.execSQL("CREATE TABLE userData (email TEXT PRIMARY KEY , password TEXT , country TEXT , fullname TEXT , address TEXT, postcode TEXT, phoneNumber TEXT, image_path TEXT, dateRegister TEXT DEFAULT (datetime('now')), dateUpdate TEXT DEFAULT (datetime('now')));");
            MyDB.execSQL("CREATE TABLE product (product_id INTEGER PRIMARY KEY AUTOINCREMENT, productName TEXT NOT NULL, price TEXT NOT NULL, description TEXT NOT NULL, listprice TEXT NOT NULL, retailprice TEXT NOT NULL, image BLOB, productAddeddate TEXT DEFAULT (datetime('now')), productupdateddate TEXT DEFAULT (datetime('now')), category_id TEXT NOT NULL, FOREIGN KEY (category_id) REFERENCES category(category_id));");
            MyDB.execSQL("CREATE TABLE category (category_id INTEGER PRIMARY KEY AUTOINCREMENT, categoryName TEXT NOT NULL);");
            MyDB.execSQL("CREATE TABLE orders (order_id INTEGER PRIMARY KEY AUTOINCREMENT, orderDate TEXT DEFAULT (datetime('now')), email TEXT NOT NULL, FOREIGN KEY (email) REFERENCES userData(email) );");

            Log.d("database", "added tables");

            // Add admin user
            String email = "root";
            String password = "root";
            String country = "Nepal";
            String fullname = "Admin";
            String address = "Northampton";
            String postcode = "NN32JW";
            String phoneNumber = "07436588420";

            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("country", country);
            contentValues.put("fullname", fullname);
            contentValues.put("address", address);
            contentValues.put("postcode", postcode);
            contentValues.put("phoneNumber", phoneNumber);




            long result = MyDB.insert("userData", null, contentValues);

            Log.d("database", "added tables and default admin user");

        } catch (Exception e) {
            Log.e("database", "error creating tables", e);
        }
    }





    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {

       MyDB.execSQL( "drop  Table  if  exists userData");
        MyDB.execSQL( "drop  Table  if  exists product");


    }

    public Boolean insertData(String email, String password, String country, String fullname, String address, String postcode, String phoneNumber){
        SQLiteDatabase MyDB = this. getWritableDatabase();
        ContentValues ContentValues= new ContentValues();
        ContentValues.put("email", email);
        ContentValues.put("password", password);
        ContentValues.put("country", country);
        ContentValues.put("fullname", fullname);
        ContentValues.put("address", address);
        ContentValues.put("postcode", postcode);
        ContentValues.put("phoneNumber", phoneNumber);


        long result = MyDB.insert("userData", null, ContentValues);

        if(result==-1) return false;
        else
            return true;


    }

    public Boolean checkemail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from userData where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            //if user exist return true
            return true;
        else
            return false;
    }

    public Boolean checkemailpassword(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from userData where email = ? and password = ?", new String[] {email,password});
        if(cursor.getCount()>0)

            return true;
        else
            return false;


    }

    public Boolean insertcategory(String categoryName){
        SQLiteDatabase MyDB = this. getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from category where categoryName = ?", new String[]{categoryName});
        if (cursor.getCount() > 0)
            //if user exist return true
            return false;
        else{
            ContentValues ContentValues= new ContentValues();

            ContentValues.put("categoryName", categoryName);


            long result = MyDB.insert("category", null, ContentValues);
            if(result==-1) return false;
            else
                return true;


        }




    }


}


