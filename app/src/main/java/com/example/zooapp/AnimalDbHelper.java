package com.example.zooapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

public class AnimalDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "animals.db";
    private static final int DATABASE_VERSION = 1;

    public AnimalDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ANIMALS_TABLE = "CREATE TABLE " + AnimalEntry.TABLE_NAME + " ("
                + AnimalEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AnimalEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + AnimalEntry.COLUMN_CONTINENT + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_ANIMALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
    public class AnimalEntry implements BaseColumns {
        public static final String TABLE_NAME = "animals";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CONTINENT = "continent";
        public static final String COLUMN_ID = "id";
    }
    public void insertAnimal(Animal animal) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AnimalEntry.COLUMN_NAME, animal.getName());
        values.put(AnimalEntry.COLUMN_CONTINENT, animal.getContinent());
        db.insert(AnimalEntry.TABLE_NAME, null, values);
    }
}
