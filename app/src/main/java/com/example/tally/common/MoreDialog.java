package com.example.tally.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.tally.R;
import com.example.tally.more.AboutActivity;
import com.example.tally.more.BillHistoryActivity;
import com.example.tally.more.SettingActivity;

/**
 * @PackageName: com.example.tally.common
 * @ClassName: MoreDialog
 * @Author: winwa
 * @Date: 2023/2/6 19:56
 * @Description:
 **/
public class MoreDialog extends Dialog implements View.OnClickListener {
    private Button mAboutBTN;
    private Button mSettingBTN;
    private Button mBillHistoryBTN;
    private Button mBillDetailBTN;

    private ImageView mCloseIV;

    public MoreDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_main_more);

        initView();
    }

    private void initView() {
        mAboutBTN = findViewById(R.id.dialog_main_more_about_btn);
        mSettingBTN = findViewById(R.id.dialog_main_more_setting_btn);
        mBillHistoryBTN = findViewById(R.id.dialog_main_more_bill_history_btn);
        mBillDetailBTN = findViewById(R.id.dialog_main_more_bill_detail_btn);
        mCloseIV = findViewById(R.id.dialog_main_more_close_iv);

        mAboutBTN.setOnClickListener(this);
        mSettingBTN.setOnClickListener(this);
        mBillHistoryBTN.setOnClickListener(this);
        mBillDetailBTN.setOnClickListener(this);
        mCloseIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.dialog_main_more_about_btn:
                intent.setClass(getContext(), AboutActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_main_more_setting_btn:
                intent.setClass(getContext(), SettingActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_main_more_bill_history_btn:
                intent.setClass(getContext(), BillHistoryActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_main_more_bill_detail_btn:
                break;
            case R.id.dialog_main_more_close_iv:
                break;
        }

        cancel();
    }

    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        attributes.width = defaultDisplay.getWidth();
        attributes.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);
    }
}
