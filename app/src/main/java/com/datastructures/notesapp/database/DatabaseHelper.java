package com.datastructures.notesapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.datastructures.notesapp.pojo.Note;
import com.datastructures.notesapp.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Note.CREATE_NOTE_TABLE);
        db.execSQL(User.CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        db.execSQL(User.DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String note, String details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Note.COLUMN_NOTE, note);
        values.put(Note.COLUMN_NOTE_DETAILS, details);

        long id = db.insert(Note.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_NOTE,Note.COLUMN_NOTE_DETAILS, Note.COLUMN_TIMESTAMP},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        @SuppressLint("Range") Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_DETAILS)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP))

        );

        cursor.close();

        return note;
    }

    @SuppressLint("Range")
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                note.setDetails(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_DETAILS)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();

        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getNote());
        values.put(Note.COLUMN_NOTE_DETAILS, note.getDetails());

        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_USER_NAME, user.getName());
        values.put(User.COLUMN_USER_EMAIL, user.getEmail());
        values.put(User.COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(User.TABLE_USER, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<User> getAllUser() {
        String[] columns = {
                User.COLUMN_USER_ID,
                User.COLUMN_USER_EMAIL,
                User.COLUMN_USER_NAME,
                User.COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                User.COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE_USER, //Table to query
                columns,null,null,null,null,sortOrder);


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_USER_NAME, user.getName());
        values.put(User.COLUMN_USER_EMAIL, user.getEmail());
        values.put(User.COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(User.TABLE_USER, values, User.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(User.TABLE_USER, User.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                User.COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = User.COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        Cursor cursor = db.query(User.TABLE_USER,columns,selection,selectionArgs,null,null,null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                User.COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = User.COLUMN_USER_EMAIL + " = ?" + " AND " + User.COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        Cursor cursor = db.query(User.TABLE_USER,columns,selection,selectionArgs,null,null,null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}