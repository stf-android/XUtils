package com.allens.library.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.allens.library.Glide.GlideUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.os.Looper.getMainLooper;

/**
 * Created by allens on 2017/6/10.
 */

public class SqlUtil {

    private static SqlUtil glideUtil;
    private int threadSize = 5;

    public static SqlUtil create() {
        if (glideUtil == null) {
            synchronized (GlideUtil.class) {
                if (glideUtil == null) {
                    glideUtil = new SqlUtil();
                }
            }
        }
        return glideUtil;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/2/28 下午5:38
     * @方法作用： 数据库异步查询
     */
    private Handler handler = new Handler(getMainLooper());

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadSize);

    public void searchdb(final SQLiteDatabase database, final OnSql.OnSqlSearchSqlListener listener) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues cv = new ContentValues();
                listener.onThreadIO(database, cv);
                cv.clear();
//                database.close();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onThreadMain();
                    }
                });
            }
        });
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }


    /**
     * @作者 ：  allens
     * @创建日期 ：2017/3/8 下午2:16
     * @方法作用： 跟新数据库
     */
    public void sqlUpData(final SQLiteDatabase db, OnSql.OnSqlUpDataListener listener) {
        ContentValues cv = new ContentValues();
        db.beginTransaction();
        listener.onUpData(db, cv);
        cv.clear();
        db.setTransactionSuccessful();
//        if (db.isOpen()) {
        db.endTransaction();
//            db.close();
//        }
    }

    public void sqlInsert(final SQLiteDatabase db, OnSql.OnSqlInsertListener listener) {
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        listener.onInsert(db, cv);
        cv.clear();
        db.setTransactionSuccessful();
//        if (db.isOpen()) {
        db.endTransaction();
//            db.close();
//        }
    }

    public void sqlDelete(final SQLiteDatabase db, String table, String whereClause, String[] whereArgs) {
        db.delete(table, whereClause, whereArgs);
//        if (db.isOpen())
//            db.close();
    }

    public void sqlDeleteSQL(Context context, String dataBaseName) {
        context.deleteDatabase(dataBaseName);
    }

    public void closeDb(SQLiteDatabase db) {
        if (db.isOpen())
            db.close();
    }
}
