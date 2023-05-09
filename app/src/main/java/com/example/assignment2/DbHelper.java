package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {
    //creating the database for the list of user
    public static final String DBNAME = "DatabaseEcom.db";

    public DbHelper(Context context) {
        super(context, "DatabaseEcom.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        try {
            MyDB.execSQL("CREATE TABLE userData (email TEXT PRIMARY KEY , password TEXT , country TEXT , fullname TEXT , address TEXT, postcode TEXT, phoneNumber TEXT, imagepath TEXT, dateRegister TEXT DEFAULT (datetime('now')), dateUpdate TEXT DEFAULT (datetime('now')));");
            MyDB.execSQL("CREATE TABLE product (product_id INTEGER PRIMARY KEY AUTOINCREMENT, productName TEXT NOT NULL, price TEXT NOT NULL, description TEXT NOT NULL, listprice TEXT NOT NULL, retailprice TEXT NOT NULL, imagelocation TEXT, productAddeddate TEXT DEFAULT (datetime('now')), productupdateddate TEXT DEFAULT (datetime('now')), category_id TEXT NOT NULL, FOREIGN KEY (category_id) REFERENCES category(category_id));");
            MyDB.execSQL("CREATE TABLE category (category_id INTEGER PRIMARY KEY AUTOINCREMENT, categoryName TEXT NOT NULL);");
            MyDB.execSQL("CREATE TABLE orders (order_id INTEGER PRIMARY KEY AUTOINCREMENT, orderDate TEXT DEFAULT (datetime('now')), email TEXT NOT NULL, totalprice TEXT NOT NULL, totalitems TEXT NOT NULL, product_idsofall TEXT NOT NULL, orderstatus TEXT DEFAULT (1), FOREIGN KEY (email) REFERENCES userData(email) );");
            MyDB.execSQL("CREATE TABLE likeditems (liked_id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, email TEXT NOT NULL, FOREIGN KEY (email) REFERENCES userData(email), FOREIGN KEY (product_id) REFERENCES product(product_id) );");
            MyDB.execSQL("CREATE TABLE cartlist (cartlistid INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, email TEXT NOT NULL, FOREIGN KEY (email) REFERENCES userData(email), FOREIGN KEY (product_id) REFERENCES product(product_id) );");

            Log.d("database", "added tables");

            // Add admin user
            String email = "root";
            String password = "root";
            String country = "Nepal";
            String fullname = "Admin";
            String address = "Northampton";
            String postcode = "NN32JW";
            String phoneNumber = "07436588420";
            String newDate = "23-Apr-1999";

            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("country", country);
            contentValues.put("fullname", fullname);
            contentValues.put("address", address);
            contentValues.put("postcode", postcode);
            contentValues.put("phoneNumber", phoneNumber);
            contentValues.put("dateRegister", newDate);


            long result = MyDB.insert("userData", null, contentValues);

            Log.d("database", "added tables and default admin user");

        } catch (Exception e) {
            Log.e("database", "error creating tables", e);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {

        MyDB.execSQL("drop  Table  if  exists userData");
        MyDB.execSQL("drop  Table  if  exists product");


    }

    public Boolean insertData(String email, String password, String country, String fullname, String address, String postcode, String phoneNumber) {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String newDate = df.format(c);

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("email", email);
        ContentValues.put("password", password);
        ContentValues.put("country", country);
        ContentValues.put("fullname", fullname);
        ContentValues.put("address", address);
        ContentValues.put("postcode", postcode);
        ContentValues.put("phoneNumber", phoneNumber);
        ContentValues.put("dateRegister", newDate);


        long result = MyDB.insert("userData", null, ContentValues);

        if (result == -1) return false;
        else
            return true;


    }

    public Boolean checkemail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from userData where email = ?", new String[]{email});
        try {
            if (cursor.getCount() > 0)
                //if user exist return true
                return true;
            else
                return false;
        } finally {
            cursor.close();
        }

    }

    public Boolean checkemailpassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from userData where email = ? and password = ?", new String[]{email, password});
        try {
            if (cursor.getCount() > 0)

                return true;
            else
                return false;
        } finally {
            cursor.close();
        }

    }

    public Boolean insertcategory(String categoryName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from category where categoryName = ?", new String[]{categoryName});
        try {
            if (cursor.getCount() > 0)
                //if user exist return true
                return false;
            else {
                ContentValues ContentValues = new ContentValues();

                ContentValues.put("categoryName", categoryName);


                long result = MyDB.insert("category", null, ContentValues);
                if (result == -1) return false;
                else
                    return true;


            }
        } finally {
            cursor.close();
        }

    }

    public Boolean updateData(String email, String password, String country, String fullname, String address, String postcode, String phoneNumber, String imagepath) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("password", password);
        ContentValues.put("country", country);
        ContentValues.put("fullname", fullname);
        ContentValues.put("address", address);
        ContentValues.put("postcode", postcode);
        ContentValues.put("phoneNumber", phoneNumber);
        ContentValues.put("imagepath", imagepath);
        long result = MyDB.update("userData", ContentValues, "email = ? ", new String[]{email});

        if (result == -1) return false;
        else
            return true;
    }

    public Cursor getUserDataById(String email) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = {"email", "password", "country", "fullname", "address", "postcode", "phoneNumber", "imagepath", "dateUpdate"};
        String selection = "email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = MyDB.query("userData", projection, selection, selectionArgs, null, null, null);
        return cursor;
    }


    public Cursor getAllCategories() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = { "category_id", "categoryName" };
        Cursor cursor = MyDB.query("category", projection, null, null, null, null, null);
        return cursor;
    }
    public Cursor getcategoryyId(String category_id) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = { "category_id", "categoryName" };
        String selection = "category_id = ?";
        String[] selectionArgs = {category_id};
        Cursor cursor = MyDB.query("category", projection, selection, selectionArgs, null, null, null);
        return cursor;
    }
    public Cursor getLikedProductIds(String email) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = { "product_id" };
        String selection = "email = ?";
        String[] selectionArgs = { email };

        Cursor cursor = MyDB.query("likeditems", projection, selection, selectionArgs, null, null, null);

        return cursor;
    }







    public Boolean updateCategory(String category_id, String categoryName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("categoryName", categoryName);

        long result = MyDB.update("category", ContentValues, "category_id = ? ", new String[]{category_id});

        if (result == -1) return false;
        else
            return true;
    }
    public boolean deleteCategory(String category_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        int result = MyDB.delete("category", "category_id=?", new String[]{category_id});
        return result > 0;
    }


    public Boolean insertproduct(String productName, String price, String description, String listprice, String retailprice, String imagelocation, String category_id) {

        Date cd = Calendar.getInstance().getTime();
        System.out.println("Current time => " + cd);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String newDateis = df.format(cd);

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("productName", productName);
        ContentValues.put("price", price);
        ContentValues.put("description", description);
        ContentValues.put("listprice", listprice);
        ContentValues.put("retailprice", retailprice);
        ContentValues.put("imagelocation", imagelocation);
        ContentValues.put("category_id", category_id);


        long result = MyDB.insert("product", null, ContentValues);

        if (result == -1) return false;
        else
            return true;


    }
    public Cursor getproductById(String product_id) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = {"productName", "price", "description", "listprice", "retailprice", "imagelocation", "category_id"};
        String selection = "product_id = ?";
        String[] selectionArgs = {product_id};
        Cursor cursor = MyDB.query("product", projection, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public Boolean updateProduct(String product_id,String productName, String price, String description, String listprice, String retailprice, String imagelocation, String category_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("productName", productName);
        ContentValues.put("price", price);
        ContentValues.put("description", description);
        ContentValues.put("listprice", listprice);
        ContentValues.put("retailprice", retailprice);
        ContentValues.put("imagelocation", imagelocation);
        ContentValues.put("category_id", category_id);
        long result = MyDB.update("product", ContentValues, "product_id = ? ", new String[]{product_id});

        if (result == -1) return false;
        else
            return true;
    }
    public boolean deleteProduct(String product_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        int result = MyDB.delete("product", "product_id=?", new String[]{product_id});
        return result > 0;
    }
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { "product_id", "productName", "price", "description", "listprice", "retailprice", "imagelocation", "category_id" };
        Cursor cursor = db.query("product", projection, null, null, null, null, null);
        return cursor;
    }

    public Boolean insertlikeditem(String email, String product_id) {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("email", email);
        ContentValues.put("product_id", product_id);

        long result = MyDB.insert("likeditems", null, ContentValues);

        if (result == -1) return false;
        else
            return true;


    }
    public Boolean insertcart(String email, String product_id) {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("email", email);
        ContentValues.put("product_id", product_id);

        long result = MyDB.insert("cartlist", null, ContentValues);

        if (result == -1) return false;
        else
            return true;


    }
    public boolean deletecart(String email, String product_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        int result = MyDB.delete("cartlist", "email=? AND product_id=?", new String[]{email, product_id});
        return result > 0;
    }
    public boolean deletelikeditems(String email, String product_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        int result = MyDB.delete("likeditems", "email=? AND product_id=?", new String[]{email, product_id});
        return result > 0;
    }
    public Boolean checklikeditem(String email, String product_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from likeditems where email = ? and product_id = ?", new String[]{email, product_id});
        try {
            if (cursor.getCount() > 0)

                return true;
            else
                return false;
        } finally {
            cursor.close();
        }

    }
    public Cursor getcheckcartpid(String email) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = { "product_id" };
        String selection = "email = ?";
        String[] selectionArgs = { email };

        Cursor cursor = MyDB.query("cartlist", projection, selection, selectionArgs, null, null, null);
        return cursor;


    }
    public Boolean addorder(String email, String totalprice, String totalitems, String product_idsofall) {

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String newDate = df.format(c);

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("email", email);
        ContentValues.put("totalprice", totalprice);
        ContentValues.put("totalitems", totalitems);
        ContentValues.put("orderDate", newDate);
        ContentValues.put("product_idsofall", product_idsofall);



        long result = MyDB.insert("orders", null, ContentValues);

        if (result == -1) return false;
        else
            return true;


    }
    public boolean deletecart(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        int result = MyDB.delete("cartlist", "email=? ", new String[]{email});
        return result > 0;
    }
    public Cursor getOrdersbyemail(String email) {


        if(email.equals("root")){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { "order_id", "orderDate", "email", "totalprice", "totalitems", "product_idsofall", "orderstatus" };
        Cursor cursor = db.query("orders", projection, null, null, null, null, null);
            return cursor;
        }
        else{
            SQLiteDatabase MyDB = this.getReadableDatabase();
            String[] projection = { "order_id", "orderDate", "email", "totalprice", "totalitems", "product_idsofall", "orderstatus" };
            String selection = "email = ?";
            String[] selectionArgs = {email};
            Cursor cursor = MyDB.query("orders", projection, selection, selectionArgs, null, null, null);
            return cursor;
        }
    }
    public Boolean updateorder(String order_id, String orderstatus) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues ContentValues = new ContentValues();
        ContentValues.put("orderstatus", orderstatus);

        long result = MyDB.update("orders", ContentValues, "order_id = ? ", new String[]{order_id});

        if (result == -1) return false;
        else
            return true;
    }
}


