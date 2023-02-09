package com.example.tally.more;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tally.R;
import com.example.tally.db.DBManager;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_back_iv:
                finish();
                break;
            case R.id.setting_clean_tv:
                showCleanDialog();
                break;
        }
    }

    private void showCleanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除提示")
                .setMessage("确定删除所有记录吗？\n警告：删除后无法恢复")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBManager.deleteAllRecord();
                        Toast.makeText(SettingActivity.this, "删除完成", Toast.LENGTH_SHORT).show();
                    }
                })
                .create().show();
    }
}