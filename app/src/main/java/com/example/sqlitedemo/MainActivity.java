package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.io.File;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(MainActivity.this,"users.db",null,1);
        final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

        Button button1 = findViewById(R.id.button2); //add
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建存放数据的ContentValues对象
                ContentValues values = new ContentValues();
                values.put("username","test");
                values.put("password","123456");
                values.put("age",21);
                //数据库执行插入命令
                db.insert("user", null, values);
            }
        });
        Button button2 = findViewById(R.id.button3); //删
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("user", "username=?", new String[]{"test"});
            }
        });
        Button button3= findViewById(R.id.button4); //改
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values2 = new ContentValues();
                values2.put("username", "admin123");
                db.update("user", values2, "username=?", new String[]{"admin"});
            }
        });
        Button button4 = findViewById(R.id.button5); //查
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建游标对象
                Cursor cursor = db.query("user", new String[]{"id","username","password","age"}, "username=?or age =?", new String[]{"test","20"}, null, null, null);
                //利用游标遍历所有数据对象
                while(cursor.moveToNext()){
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                   @SuppressLint("Range") String password=cursor.getString(cursor.getColumnIndex("password"));
                    @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex("age"));
                    Log.i("Mainactivity","result: id="  + id +" username: " + username +" password: "+password+" age: "+ age);
                }
                // 关闭游标，释放资源
                cursor.close();
            }
        });
    }
}

