package com.example.tally.common;

import android.app.Application;

import com.example.tally.db.DBManager;

/**
 * @PackageName: com.example.tally.common
 * @ClassName: MyApplication
 * @Author: winwa
 * @Date: 2023/2/2 8:38
 * @Description:
 **/
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}
