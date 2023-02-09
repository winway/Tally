package com.example.tally.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tally.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName: com.example.tally.adapter
 * @ClassName: CalendarGridAdapter
 * @Author: winwa
 * @Date: 2023/2/8 8:50
 * @Description:
 **/
public class CalendarGridAdapter extends BaseAdapter {
    private Context mContext;
    private int mYear;
    private List<String> mData;
    private int mSelectedPos;

    public CalendarGridAdapter(Context context, int year, int selectedPos) {
        mContext = context;

        mData = new ArrayList<>();
        setYear(year);

        mSelectedPos = selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        mSelectedPos = selectedPos;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.gvitem_bill_history_calendar, null);
        TextView tv = view.findViewById(R.id.gvitem_year_month_tv);
        tv.setText(mData.get(i));
        if (i == mSelectedPos) {
            tv.setBackgroundResource(R.color.green_200);
            tv.setTextColor(Color.WHITE);
        } else {
            tv.setBackgroundResource(R.color.grey_200);
            tv.setTextColor(Color.BLACK);
        }

        return view;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
        mData.clear();
        for (int i = 0; i < 12; i++) {
            mData.add(mYear + "/" + (i + 1));
        }
        notifyDataSetChanged();
    }
}
