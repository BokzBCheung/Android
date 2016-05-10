package com.example.sqlAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;

/**
 * Created by Administrator on 2016/5/5.
 */
public class SqlAdapter extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="db2";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String HEADICON ="picnum";

    public SqlAdapter(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists constants(_id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Phone TEXT,PicNum TEXT)");
        ContentValues cv = new ContentValues();

        cv.put(NAME, "张博1");
        cv.put(PHONE, "15258789532");
        cv.put(HEADICON, R.drawable.p1);
        db.insert("constants", HEADICON , cv);

        cv.put(NAME, "张博2");
        cv.put(PHONE, "15381432415");
        cv.put(HEADICON, "/sdcard/Download/zhangbo.jpg");
        db.insert("constants", HEADICON , cv);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS constants");
        onCreate(db);
    }
}

