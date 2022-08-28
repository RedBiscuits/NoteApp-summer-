package com.example.test.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.test.Model.User;

import Utils.Utils;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE ="CREATE TABLE " + Utils.TABLE_NAME+ " ("
                + Utils.KEY_ID + " INTEGER PRIMARY KEY,"
                + Utils.KEY_FIRSTNAME + " TEXT,"
                + Utils.KEY_LASTNAME + " TEXT,"
                + Utils.KEY_EMAIL + " TEXT,"
                + Utils.KEY_PASSWORD + " TEXT)";

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME);
        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.KEY_FIRSTNAME, user.getFirstName());
        contentValues.put(Utils.KEY_LASTNAME, user.getLastName());
        contentValues.put(Utils.KEY_EMAIL, user.getEmail());
        contentValues.put(Utils.KEY_PASSWORD, user.getPassword());

        database.insert(Utils.TABLE_NAME, null, contentValues);
        database.close();
    }

    public User getUser(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(Utils.TABLE_NAME,
                new String[]{Utils.KEY_ID, Utils.KEY_FIRSTNAME,
                        Utils.KEY_LASTNAME, Utils.KEY_EMAIL,
                        Utils.KEY_PASSWORD},
                Utils.KEY_EMAIL + "=?", new String[]{email},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(Integer.parseInt((cursor.getString(0))),
                (cursor.getString(1)),
                (cursor.getString(2)),
                (cursor.getString(3)),
                (cursor.getString(4)) );
        return user;

    }


}
