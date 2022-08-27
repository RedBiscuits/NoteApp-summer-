package com.datastructures.notesapp.pojo;


import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.Random;

public class Note {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_NOTE_DETAILS = "details";
    public static final String COLUMN_TIMESTAMP = "timestamp";



    private int id;
    private String note;
    private String details;
    private String timestamp;
    private ColorStateList backgroundColor;

    // Create table SQL query
    public static final String CREATE_NOTE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_NOTE_DETAILS + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Note() {
        backgroundColor = getColor();
    }

    public ColorStateList getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(ColorStateList backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Note(int id, String note, String details, String timestamp) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
        this.details = details;
        backgroundColor = getColor();
    }

    private ColorStateList getColor() {
        Random random = new Random();
        return ColorStateList.valueOf(Color.argb(100
                , random.nextInt()% 256
                , random.nextInt()% 256
                , random.nextInt()% 256));
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", details='" + details + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", backgroundColor=" + backgroundColor +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}