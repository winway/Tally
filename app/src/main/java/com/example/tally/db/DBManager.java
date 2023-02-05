package com.example.tally.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tally.bean.MoneyTypeBean;
import com.example.tally.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName: com.example.tally.db
 * @ClassName: DBManager
 * @Author: winwa
 * @Date: 2023/2/2 8:36
 * @Description:
 **/
public class DBManager {
    private static final String TAG = "DBManager";

    private static SQLiteDatabase sDatabase;

    public static void initDB(Context context) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        sDatabase = dbOpenHelper.getWritableDatabase();
    }

    public static List<MoneyTypeBean> getMoneyTypeList(int t) {
        List<MoneyTypeBean> typeList = new ArrayList<>();

        String sql = "select id, name, image_id, image_id_selected, type from money_type where type = " + t;

        Cursor cursor = sDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int imageId = cursor.getInt(cursor.getColumnIndex("image_id"));
            int imageIdSelected = cursor.getInt(cursor.getColumnIndex("image_id_selected"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            typeList.add(new MoneyTypeBean(id, name, imageId, imageIdSelected, type));
        }

        return typeList;
    }

    public static void insertRecord(RecordBean recordBean) {
        ContentValues values = new ContentValues();
        values.put("name", recordBean.getName());
        values.put("image_id", recordBean.getImageId());
        values.put("money", recordBean.getMoney());
        values.put("remark", recordBean.getRemark());
        values.put("time", recordBean.getTime());
        values.put("year", recordBean.getYear());
        values.put("month", recordBean.getMonth());
        values.put("day", recordBean.getDay());
        values.put("type", recordBean.getType());

        long nRows = sDatabase.insert("record", null, values);
        Log.e(TAG, "insertRecord: " + nRows);
    }

    public static List<RecordBean> getRecordListByYMD(int year, int month, int day) {
        List<RecordBean> recordBeanList = new ArrayList<>();

        String sql = "select * from record where year=? and month=? and day=? order by id desc";

        Cursor cursor = sDatabase.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int imageId = cursor.getInt(cursor.getColumnIndex("image_id"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));

            recordBeanList.add(new RecordBean(id, name, imageId, money, remark, time, year, month, day, type));
        }

        return recordBeanList;
    }

    public static float getTotalMoneyByYMD(int year, int month, int day, int type) {
        String sql = "select sum(money) as m from record where year=? and month=? and day=? and type=?";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(type)});

        float money = 0.0f;
        if (cursor.moveToNext()) {
            money = cursor.getFloat(cursor.getColumnIndex("m"));
        }

        return money;
    }

    public static float getTotalMoneyByYM(int year, int month, int type) {
        String sql = "select sum(money) as m from record where year=? and month=? and type=?";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(type)});

        float money = 0.0f;
        if (cursor.moveToNext()) {
            money = cursor.getFloat(cursor.getColumnIndex("m"));
        }

        return money;
    }

    public static float getTotalMoneyByY(int year, int type) {
        String sql = "select sum(money) as m from record where year=? and type=?";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(type)});

        float money = 0.0f;
        if (cursor.moveToNext()) {
            money = cursor.getFloat(cursor.getColumnIndex("m"));
        }

        return money;
    }
}
