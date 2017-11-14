package com.lanwei.governmentstar.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/30.
 */

public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {

        private static String name = "governmentstar.db";
        private static Integer version = 1;

        public RecordSQLiteOpenHelper(Context context) {
            super(context, "gov", null, version);
        }

        @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
    }

        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}