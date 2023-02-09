package com.example.tally.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tally.R;
import com.example.tally.adapter.CalendarGridAdapter;
import com.example.tally.db.DBManager;
import com.example.tally.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @PackageName: com.example.tally.common
 * @ClassName: CalendarDialog
 * @Author: winwa
 * @Date: 2023/2/7 8:21
 * @Description:
 **/
public class CalendarDialog extends Dialog implements View.OnClickListener {

    private LinearLayout mYearLL;
    private GridView mGV;
    private ImageView mCloseIV;

    private List<Integer> mYearList;
    private List<TextView> mYearTVList;
    private int mSelectedYearPos;
    private int mSelectedMonth;

    private CalendarGridAdapter mGridAdapter;

    private OnCalendarSelectListener mOnCalendarSelectListener;

    public CalendarDialog(@NonNull Context context, int selectedYearPos, int selectedMonth) {
        super(context);
        mSelectedYearPos = selectedYearPos;
        mSelectedMonth = selectedMonth;
    }

    public void setOnCalendarSelectListener(OnCalendarSelectListener onCalendarSelectListener) {
        mOnCalendarSelectListener = onCalendarSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bill_history_calendar);

        mYearLL = findViewById(R.id.dialog_bill_history_year_ll);
        mGV = findViewById(R.id.dialog_bill_history_gv);
        mCloseIV = findViewById(R.id.dialog_bill_history_close_iv);

        mCloseIV.setOnClickListener(this);

        initCalendarYearLL();

        initGridView();
    }

    private void initCalendarYearLL() {
        mYearList = DBManager.getYearList();
        mYearTVList = new ArrayList<>();

        if (mYearList.size() == 0) {
            mYearList.add(DateUtils.getYear(new Date()));
        }

        for (int i = 0; i < mYearList.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.hsvitem_bill_history_calendar, null);
            mYearLL.addView(view);
            TextView yearTV = view.findViewById(R.id.hsvitem_year_tv);
            yearTV.setText(String.valueOf(mYearList.get(i)));
            mYearTVList.add(yearTV);
        }

        if (mSelectedYearPos == -1) {
            mSelectedYearPos = mYearTVList.size() - 1;
        }

        setYearTVBackground(mSelectedYearPos);

        setYearTVClickListener();
    }

    private void setYearTVBackground(int selectedPos) {
        for (int i = 0; i < mYearTVList.size(); i++) {
            TextView textView = mYearTVList.get(i);
            if (i == selectedPos) {
                textView.setBackgroundResource(R.drawable.btn_dialog_ok_background);
                textView.setTextColor(Color.WHITE);
            } else {
                textView.setBackgroundResource(R.drawable.btn_dialog_cancel_background);
                textView.setTextColor(Color.BLACK);
            }
        }
    }

    private void setYearTVClickListener() {
        for (int i = 0; i < mYearTVList.size(); i++) {
            TextView textView = mYearTVList.get(i);
            final int pos = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedYearPos = pos;
                    setYearTVBackground(pos);
                    mGridAdapter.setYear(mYearList.get(pos));
                }
            });
        }
    }

    private void initGridView() {
        mGridAdapter = new CalendarGridAdapter(getContext(), mYearList.get(mSelectedYearPos), mSelectedMonth - 1);
        mGV.setAdapter(mGridAdapter);

        mGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mGridAdapter.setSelectedPos(i);
                mGridAdapter.notifyDataSetInvalidated();

                int year = mGridAdapter.getYear();
                int month = i + 1;

                if (mOnCalendarSelectListener != null) {
                    mOnCalendarSelectListener.onSelect(mSelectedYearPos, year, month);
                }

                cancel();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_bill_history_close_iv:
                cancel();
                break;
        }
    }

    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        attributes.width = defaultDisplay.getWidth();
        attributes.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);
    }

    public interface OnCalendarSelectListener {
        public void onSelect(int selectPos, int year, int month);
    }
}
