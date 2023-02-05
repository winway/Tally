package com.example.tally.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tally.R;

/**
 * @PackageName: com.example.tally.common
 * @ClassName: BudgetDialog
 * @Author: winwa
 * @Date: 2023/2/5 7:42
 * @Description:
 **/
public class BudgetDialog extends Dialog implements View.OnClickListener {
    private ImageView mCancelIV;
    private EditText mBudgetET;
    private Button mOkBTN;

    private OnDialogOKPressedListener mOKPressedListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    public BudgetDialog(@NonNull Context context) {
        super(context);
    }

    public void setOKPressedListener(OnDialogOKPressedListener OKPressedListener) {
        mOKPressedListener = OKPressedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main_budget);

        mCancelIV = findViewById(R.id.dialog_main_budget_cancel_iv);
        mBudgetET = findViewById(R.id.dialog_main_budget_input_et);
        mOkBTN = findViewById(R.id.dialog_main_budget_ok_btn);

        mCancelIV.setOnClickListener(this);
        mOkBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_main_budget_cancel_iv:
                cancel();
                break;
            case R.id.dialog_main_budget_ok_btn:
                String input = mBudgetET.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getContext(), "输入数据不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                float money = Float.parseFloat(input);
                if (money <= 0) {
                    Toast.makeText(getContext(), "预算金额必须大于0!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mOKPressedListener != null) {
                    mOKPressedListener.onOK(money);
                }

                cancel();
                break;
        }
    }

    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        attributes.width = defaultDisplay.getWidth();
        attributes.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);

        mHandler.sendEmptyMessageDelayed(1, 100);
    }

    public interface OnDialogOKPressedListener {
        public void onOK(float budget);
    }
}
