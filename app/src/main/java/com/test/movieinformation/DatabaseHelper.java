package com.test.movieinformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "HELPER";
    private static final String TableName = "MovieInfo";

    /**
     * Create constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, TableName, null, 1);
    }

    /**
     * It will create table in database.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, YEAR TEXT, GRNRE TEXT, " +
                "DIRECTOR TEXT, COUNTRY TEXT)";
        db.execSQL(createTable);
    }

    /**
     * It will drop table if exists in database.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    /**
     * addData() is use to insert data into table.
     *
     * @param title
     * @param year
     * @param genre
     * @param director
     * @param country
     * @return it will return true or false. If data will inserted successfully into database then it will return true otherwise false.
     */
    public boolean addData(String title, String year, String genre, String director, String country) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", title);
        contentValues.put("YEAR", year);
        contentValues.put("GRNRE", genre);
        contentValues.put("DIRECTOR", director);
        contentValues.put("COUNTRY", country);
        long result = sqLiteDatabase.insert(TableName, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method is to fetch data from SQLiteDatabase.
     *
     * @return it will return the data from database.
     */
    public Cursor getData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TableName;
        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }

    /**
     * This is execute when user wants to delete the data from database.
     *
     * @param id ID is contain the particular primary key value, which is user wants to delete.
     */
    public void delData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TableName, "ID=?", new String[]{id});
        sqLiteDatabase.close();
    }
}
