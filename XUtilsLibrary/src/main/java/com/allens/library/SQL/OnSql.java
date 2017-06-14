package com.allens.library.SQL;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by allens on 2017/6/10.
 */
public interface OnSql {
    interface OnSqlInsertListener {
        void onInsert(SQLiteDatabase db, ContentValues cv);
    }

    interface OnSqlSearchSqlListener {
        void onThreadIO(SQLiteDatabase db, ContentValues cv);

        void onThreadMain();
    }

    interface OnSqlUpDataListener {
        void onUpData(SQLiteDatabase db, ContentValues cv);
    }

}
