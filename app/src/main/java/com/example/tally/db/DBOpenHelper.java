package com.example.tally.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tally.R;

/**
 * @PackageName: com.example.tally.db
 * @ClassName: DBOpenHelper
 * @Author: winwa
 * @Date: 2023/2/2 8:22
 * @Description:
 **/
public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "tally.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSql = "create table money_type(id integer primary key autoincrement, " +
                "name varchar(10), " +
                "image_id integer, " +
                "image_id_selected integer, " +
                "type integer)";

        sqLiteDatabase.execSQL(createSql);

        String insertSql = "insert into money_type(name, image_id, image_id_selected, type) values(?,?,?,?)";

        sqLiteDatabase.execSQL(insertSql, new Object[]{"其他", R.mipmap.ic_qita, R.mipmap.ic_qita_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"餐饮", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"交通", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"购物", R.mipmap.ic_gouwu, R.mipmap.ic_gouwu_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"服饰", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"日用品", R.mipmap.ic_riyongpin, R.mipmap.ic_riyongpin_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"娱乐", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"零食", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"烟酒茶", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"学习", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"医疗", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"住宅", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"水电煤", R.mipmap.ic_shuidianfei, R.mipmap.ic_shuidianfei_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"通讯", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, 0});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"人情往来", R.mipmap.ic_renqingwanglai, R.mipmap.ic_renqingwanglai_fs, 0});

        sqLiteDatabase.execSQL(insertSql, new Object[]{"其他", R.mipmap.in_qt, R.mipmap.in_qt_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"薪资", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"奖金", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"借入", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"收债", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"利息收入", R.mipmap.in_lixifuji, R.mipmap.in_lixifuji_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"投资回报", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"二手交易", R.mipmap.in_ershoushebei, R.mipmap.in_ershoushebei_fs, 1});
        sqLiteDatabase.execSQL(insertSql, new Object[]{"意外所得", R.mipmap.in_yiwai, R.mipmap.in_yiwai_fs, 1});

        createSql = "create table record(id integer primary key autoincrement, " +
                "name varchar(10), " +
                "image_id integer, " +
                "money float, " +
                "remark varchar(80), " +
                "time varchar(60), " +
                "year integer, " +
                "month integer, " +
                "day integer, " +
                "type integer)";

        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
