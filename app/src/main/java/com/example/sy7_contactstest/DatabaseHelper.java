package com.example.sy7_contactstest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public String create_Book="create table contacts (" +
            "id integer primary key autoincrement," +
            "name text," +
            "sex text," +
            "phone text)";
    public Context mcontext;
    String TAG="DatabaseHelper";

    public DatabaseHelper(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_Book);
        Log.d(TAG, "onCreate: successful");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
