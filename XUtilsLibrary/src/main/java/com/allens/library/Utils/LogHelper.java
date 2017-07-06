package com.allens.library.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * 记录日志
 *
 * @author tx
 */
public class LogHelper {
    public static final String LOG_PATH = Environment.getExternalStorageDirectory().getPath() + "/Log/";

    public static void write(String tag, String context) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            Date date = new Date();
            String format = new SimpleDateFormat("yy-MM-dd", Locale.CHINA).format(date);
            String pathName = LOG_PATH + format + "/";
            String fileName = tag + "_log.txt";
            String format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
            context = format1 + ":" + context
                    + "\r\n-------------------------\r\n";
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    return;
                }
            }
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return;
                }
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(context.getBytes("GBK"));
            raf.close();
        } catch (Exception e) {
            Log.d("log", e.getMessage());
        }
    }

    public void delete(int day) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            File path = new File(LOG_PATH);
            if (path.exists()) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_YEAR, -day);
                int remain = Integer.parseInt(new SimpleDateFormat("yyMMdd", Locale.CHINA).format(c.getTime()));
                File[] files = path.listFiles();
                for (File dir : files) {
                    if (dir.isDirectory()) {
                        int date = Integer.parseInt(dir.getName().replace("_", ""));
                        if (date <= remain) {
                            for (File f : dir.listFiles()) {
                                f.delete();
                            }
                            dir.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("log", e.getMessage());
        }
    }
}
