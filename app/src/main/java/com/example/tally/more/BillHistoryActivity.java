package com.example.tally.more;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tally.R;
import com.example.tally.adapter.TradeListAdapter;
import com.example.tally.bean.RecordBean;
import com.example.tally.common.CalendarDialog;
import com.example.tally.db.DBManager;
import com.example.tally.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillHistoryActivity extends AppCompatActivity {
    private TextView mSelectedDateTV;
    private ListView mLV;

    private TradeListAdapter mListAdapter;
    private List<RecordBean> mListAdapterData;

    private int mSelectedYear;
    private int mSelectedMonth;
    private int mSelectedYearPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);

        Date date = new Date();
        mSelectedYear = DateUtils.getYear(date);
        mSelectedMonth = DateUtils.getMonth(date);

        mSelectedDateTV = findViewById(R.id.bill_history_date_tv);
        mLV = findViewById(R.id.bill_history_lv);

        mListAdapterData = new ArrayList<>();
        mListAdapter = new TradeListAdapter(this, mListAdapterData);
        mLV.setAdapter(mListAdapter);

        mLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final RecordBean recordBean = mListAdapterData.get(i);
                final int delId = recordBean.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(BillHistoryActivity.this);
                builder.setTitle("提示信息")
                        .setMessage("确定删除此记录吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBManager.deleteRecordById(delId);
                                mListAdapterData.remove(recordBean);
                                mListAdapter.notifyDataSetChanged();
                            }
                        })
                        .create().show();

                return false;
            }
        });

        refreshData();
    }

    private void refreshData() {
        mSelectedDateTV.setText(mSelectedYear + "年" + mSelectedMonth + "月");

        mListAdapterData.clear();
        mListAdapterData.addAll(DBManager.getRecordListByYM(mSelectedYear, mSelectedMonth));
        mListAdapter.notifyDataSetChanged();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bill_history_back_iv:
                finish();
                break;
            case R.id.bill_history_calendar_iv:
                CalendarDialog calendarDialog = new CalendarDialog(this, mSelectedYearPos, mSelectedMonth);
                calendarDialog.show();
                calendarDialog.setDialogSize();
                calendarDialog.setOnCalendarSelectListener(new CalendarDialog.OnCalendarSelectListener() {
                    @Override
                    public void onSelect(int selectPos, int year, int month) {
                        mSelectedYearPos = selectPos;
                        mSelectedYear = year;
                        mSelectedMonth = month;
                        refreshData();
                    }
                });
                break;
        }
    }
}