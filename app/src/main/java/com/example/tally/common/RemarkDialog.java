package com.example.tally.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.tally.R;

/**
 * @PackageName: com.example.tally.common
 * @ClassName: RemarkDialog
 * @Author: winwa
 * @Date: 2023/2/4 7:37
 * @Description:
 **/
public class RemarkDialog extends Dialog implements View.OnClickListener {
    private EditText mET;
    private Button mCancelBTN;
    private Button mOkBTN;

    private OnDialogOKPressedListener mOKPressedListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_remark);

        mET = findViewById(R.id.dialog_record_remark_et);
        mCancelBTN = findViewById(R.id.dialog_record_remark_cancel_btn);
        mOkBTN = findViewById(R.id.dialog_record_remark_ok_btn);

        mCancelBTN.setOnClickListener(this);
        mOkBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_record_remark_cancel_btn:
                cancel();
                break;
            case R.id.dialog_record_remark_ok_btn:
                if (mOKPressedListener != null) {
                    mOKPressedListener.onOK();
                }
                break;
        }
    }

    public void setOKPressedListener(OnDialogOKPressedListener OKPressedListener) {
        mOKPressedListener = OKPressedListener;
    }

    public String getEditText() {
        return mET.getText().toString().trim();
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
        public void onOK();
    }
}
