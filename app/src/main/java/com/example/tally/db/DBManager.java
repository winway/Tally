package com.example.tally.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tally.bean.BillDetailChartItemBean;
import com.example.tally.bean.BillDetailChartListItemBean;
import com.example.tally.bean.MoneyTypeBean;
import com.example.tally.bean.RecordBean;

import java.math.BigDecimal;
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
        cursor.close();

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
        cursor.close();

        return recordBeanList;
    }

    public static List<RecordBean> getRecordListByYM(int year, int month) {
        List<RecordBean> recordBeanList = new ArrayList<>();

        String sql = "select * from record where year=? and month=? order by id desc";

        Cursor cursor = sDatabase.rawQuery(sql, new String[]{year + "", month + ""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int imageId = cursor.getInt(cursor.getColumnIndex("image_id"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));

            recordBeanList.add(new RecordBean(id, name, imageId, money, remark, time, year, month, day, type));
        }
        cursor.close();

        return recordBeanList;
    }

    public static float getTotalMoneyByYMD(int year, int month, int day, int type) {
        String sql = "select sum(money) as m from record where year=? and month=? and day=? and type=?";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(type)});

        float money = 0.0f;
        if (cursor.moveToNext()) {
            money = cursor.getFloat(cursor.getColumnIndex("m"));
        }
        cursor.close();

        return money;
    }

    public static float getTotalMoneyByYM(int year, int month, int type) {
        String sql = "select sum(money) as m from record where year=? and month=? and type=?";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(type)});

        float money = 0.0f;
        if (cursor.moveToNext()) {
            money = cursor.getFloat(cursor.getColumnIndex("m"));
        }
        cursor.close();

        return money;
    }

    public static int getCountByYM(int year, int month, int type) {
        String sql = "select count(*) as n from record where year=? and month=? and type=?";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(type)});

        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndex("n"));
        }
        cursor.close();

        return count;
    }

    public static float getMaxDayMoneyByYM(int year, int month, int type) {
        String sql = "select sum(money) as m from record where year=? and month=? and type=? group by day order by sum(money) desc";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(type)});

        float money = 0.0f;
        if (cursor.moveToNext()) {
            money = cursor.getFloat(cursor.getColumnIndex("m"));
        }
        cursor.close();

        return money;
    }

    public static float getTotalMoneyByY(int year, int type) {
        String sql = "select sum(money) as m from record where year=? and type=?";
        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(type)});

        float money = 0.0f;
        if (cursor.moveToNext()) {
            money = cursor.getFloat(cursor.getColumnIndex("m"));
        }
        cursor.close();

        return money;
    }

    public static int deleteRecordById(int id) {
        int nRows = sDatabase.delete("record", "id=?", new String[]{id + ""});
        return nRows;
    }

    public static void deleteAllRecord() {
        sDatabase.execSQL("delete from record");
    }

    public static List<RecordBean> getRecordListByRemark(String remark) {
        List<RecordBean> recordBeanList = new ArrayList<>();

        String sql = "select * from record where remark like ? order by id desc";

        Cursor cursor = sDatabase.rawQuery(sql, new String[]{"%" + remark + "%"});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int imageId = cursor.getInt(cursor.getColumnIndex("image_id"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));

            recordBeanList.add(new RecordBean(id, name, imageId, money, remark, time, year, month, day, type));
        }
        cursor.close();

        return recordBeanList;
    }

    public static List<Integer> getYearList() {
        List<Integer> yearList = new ArrayList<>();

        String sql = "select distinct(year) from record order by year asc";

        Cursor cursor = sDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int year = cursor.getInt(cursor.getColumnIndex("year"));

            yearList.add(year);
        }
        cursor.close();

        return yearList;
    }

    public static List<BillDetailChartListItemBean> getTypeStaticsByYM(int year, int month, int type) {
        List<BillDetailChartListItemBean> itemBeanList = new ArrayList<>();

        float totalMoney = getTotalMoneyByYM(year, month, type);

        String sql = "select name, image_id, sum(money) as total_money from record where year=? and month=? and type=? " +
                "group by image_id, name order by total_money desc";

        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(type)});
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int imageId = cursor.getInt(cursor.getColumnIndex("image_id"));
            float money = cursor.getFloat(cursor.getColumnIndex("total_money"));
            float percent = new BigDecimal(money / totalMoney).setScale(4, 4).floatValue();

            itemBeanList.add(new BillDetailChartListItemBean(imageId, name, percent, money));
        }
        cursor.close();

        return itemBeanList;
    }

    public static List<BillDetailChartItemBean> getDayMoneyByYM(int year, int month, int type) {
        List<BillDetailChartItemBean> itemBeanList = new ArrayList<>();

        String sql = "select day, sum(money) as total_money from record where year=? and month=? and type=? group by day order by day";

        Cursor cursor = sDatabase.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(type)});
        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            float money = cursor.getFloat(cursor.getColumnIndex("total_money"));

            itemBeanList.add(new BillDetailChartItemBean(year, month, day, money));
        }
        cursor.close();

        return itemBeanList;
    }
}
