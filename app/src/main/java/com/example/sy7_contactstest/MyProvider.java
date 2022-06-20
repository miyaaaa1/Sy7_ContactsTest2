package com.example.sy7_contactstest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {
    public static final int contacts_DIR=0;
    public static final int contacts_ITEM=1;
    public static final String AUTHORITY="com.example.sy7_contactstest.provider";
    private static UriMatcher uriMatcher;
    private DatabaseHelper databaseHelper;
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"contacts",contacts_DIR);
        uriMatcher.addURI(AUTHORITY,"contacts/#",contacts_ITEM);
    }
    @Override
    public boolean onCreate() {
        databaseHelper=new DatabaseHelper(getContext(),"db.db",null,3);
        Log.d("创建数据库", "创建成功！！");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        //查询数据
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor =null;
        switch (uriMatcher.match(uri)){
            case contacts_DIR:
                //查询所有
                cursor=db.query("contacts",strings, s, strings1,null,null,s1);
                break;

            case contacts_ITEM:
                //查询单条
                String id=uri.getPathSegments().get(1);
                cursor=db.query("contacts",strings, "id=?", new String[]{id},null,null,s1);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
//        //添加数据
//        SQLiteDatabase db=databaseHelper.getWritableDatabase();
//        Uri uri1=null;
//        switch (uriMatcher.match(uri)){
//            case contacts_DIR:
//            case contacts_ITEM:
//                long new
//        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
