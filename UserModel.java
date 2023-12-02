package com.example.appassignment;

import static com.example.appassignment.DatabaseHelper.COLUMN_EMAIL;
import static com.example.appassignment.DatabaseHelper.COLUMN_ID;
import static com.example.appassignment.DatabaseHelper.COLUMN_NAME;
import static com.example.appassignment.DatabaseHelper.COLUMN_PASSWORD;
import static com.example.appassignment.DatabaseHelper.TABLE_USERS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserModel {

    String name;
    String email;
    String password;

    //array list to store the information for register and login

    private static ArrayList<UserModel> userList = new ArrayList<>();
    public UserModel(String name, String email, String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //to return the userlist for login to access
    public static ArrayList<UserModel> getUserList() {
        return userList;
    }

    //to add information or data to the arraylist from the registration form
    public static void addUser(UserModel user) {
        userList.add(user);
    }
    public long saveToDatabase(Context context) {
        // Open a connection to the database
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Prepare the data to be inserted
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        // Insert data into the database
        long newRowId = db.insert(TABLE_USERS, null, values);

        // Close the database connection
        db.close();

        return newRowId;
    }

    public void addUserToDatabase(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long newRowId = db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public static UserModel getUserFromDatabase(DatabaseHelper dbHelper, String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        };

        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        UserModel user = null;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String userEmail = cursor.getString(emailIndex);
            String userPassword = cursor.getString(passwordIndex);

            user = new UserModel(name, userEmail, userPassword);
        }

        cursor.close();
        db.close();

        return user;
    }

}
