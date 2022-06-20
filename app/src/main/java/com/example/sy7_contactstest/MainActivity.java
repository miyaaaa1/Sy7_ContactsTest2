package com.example.sy7_contactstest;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atcivity_main);
        //建立一个ListView用来显示读到的数据（contactList）
        ListView contactsView = (ListView) findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);

        //创建database
        //super(context, name, factory, version);
        DatabaseHelper dbHelper = new DatabaseHelper(this, "db.db", null, 1);

        //检查是否有读取通讯录的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts(dbHelper);
        }
        //按钮
        Button findone = findViewById(R.id.findone);
        Button findall = findViewById(R.id.findall);
        findone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查找数据
                Uri uri = Uri.parse("content://com.example.sy7_contactstest.provider/contacts/" + "1");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        Log.d("findone", "name: " + name + "   sex: " + sex + "   phone: " + phone);
                    }
                    cursor.close();
                }
            }
        });

        findall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查找数据
                Uri uri = Uri.parse("content://com.example.sy7_contactstest.provider/contacts");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        Log.d("findall", "name: " + name + "   sex: " + sex + "   phone: " + phone);
                    }
                    cursor.close();
                }

            }
        });

    }


    //读取通讯录
    private void readContacts(DatabaseHelper dbHelper) {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String displayName = (String) cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    @SuppressLint("Range") String displayNumber = (String) cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //性别
                    String sex = "";
                    if (displayName.contains("Miss."))
                        sex = "女";
                    else
                        sex = "男";
                    //listview显示
                    contactsList.add(displayName + "                    " + displayNumber + sex);

                    //数据库插入
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();

                    //插入数据
                    values.put("name", displayName);
                    values.put("phone", displayNumber);
                    values.put("sex", sex);

                    //提交插入操作
                    db.insert("contacts", null, values);
                }
                //通知刷新ListView
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

//    //    询问权限
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    readContacts();
//                } else {
//                    Toast.makeText(this, "You denied permission", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            }
//            default:
//                break;
//        }
//    }
}
