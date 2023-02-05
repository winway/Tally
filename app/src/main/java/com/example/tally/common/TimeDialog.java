package com.example.tally.common;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.tally.R;

import java.util.Locale;

/**
 * @PackageName: com.example.tally.common
 * @ClassName: TimeDialog
 * @Author: winwa
 * @Date: 2023/2/4 10:20
 * @Description:
 **/
public class TimeDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "TimeDialog";

    private DatePicker mDP;
    private EditText mHourET;
    private EditText mMinuteET;
    private Button mOkBTN;
    private Button mCancelBTN;

    private OnDialogOKPressedListener mOnDialogOKPressedListener;

    public TimeDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_record_time);

        mDP = findViewById(R.id.dialog_record_time_dp);
        mHourET = findViewById(R.id.dialog_record_time_hour_et);
        mMinuteET = findViewById(R.id.dialog_record_time_minute_et);
        mOkBTN = findViewById(R.id.dialog_record_time_ok_btn);
        mCancelBTN = findViewById(R.id.dialog_record_time_cancel_btn);

        mOkBTN.setOnClickListener(this);
        mCancelBTN.setOnClickListener(this);

        hideDatePickerHeader();
    }

    public void setOnDialogOKPressedListener(OnDialogOKPressedListener onDialogOKPressedListener) {
        mOnDialogOKPressedListener = onDialogOKPressedListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_record_time_ok_btn:
                if (mOnDialogOKPressedListener != null) {
                    int year = mDP.getYear();
                    int month = mDP.getMonth() + 1;
                    int day = mDP.getDayOfMonth();
                    int hour = 0;
                    try {
                        hour = Integer.parseInt(mHourET.getText().toString()) % 24;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int minute = 0;
                    try {
                        minute = Integer.parseInt(mMinuteET.getText().toString()) % 60;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String timeString = String.format(Locale.CHINA, "%d年%02d月%02d日 %02d:%02d", year, month, day, hour, minute);

                    mOnDialogOKPressedListener.onOK(timeString, year, month, day);
                    cancel();
                }
                break;
            case R.id.dialog_record_time_cancel_btn:
                cancel();
                break;
        }
    }

    private void hideDatePickerHeader() {
        ViewGroup rootView = (ViewGroup) mDP.getChildAt(0);
        if (rootView == null) {
            return;
        }

        ViewGroup headerView = (ViewGroup) rootView.getChildAt(0);
        if (headerView == null) {
            return;
        }

        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams paramsRoot = rootView.getLayoutParams();
            paramsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(paramsRoot);

            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
            ViewGroup.LayoutParams paramsAnimator = animator.getLayoutParams();
            paramsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(paramsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams paramsChild = child.getLayoutParams();
            paramsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(paramsChild);
        } else {
            headerId = getContext().getResources().getIdentifier("date_picker_header", "id", "android");
            if (headerId == headerView.getId()) {
                headerView.setVisibility(View.GONE);
            }
        }
    }

    public interface OnDialogOKPressedListener {
        public void onOK(String time, int year, int month, int day);
    }
}
