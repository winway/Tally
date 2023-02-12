package com.example.tally.more;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tally.R;
import com.example.tally.adapter.ChartPagerAdapter;
import com.example.tally.common.CalendarDialog;
import com.example.tally.db.DBManager;
import com.example.tally.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDetailActivity extends AppCompatActivity {

    private TextView mDateTV;
    private TextView mPayoutTV;
    private TextView mIncomeTV;
    private Button mPayoutBTN;
    private Button mIncomeBTN;
    private ViewPager mChartVP;

    private List<Fragment> mChartFragmentList;
    private BillDetailPayoutChartFragment mPayoutChartFragment;
    private BillDetailIncomeChartFragment mIncomeChartFragment;

    private int mYear;
    private int mMonth;
    private int mSelectedYearPos = -1;
    private ChartPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        Date date = new Date();
        mYear = DateUtils.getYear(date);
        mMonth = DateUtils.getMonth(date);

        initView();

        setSummaryStatistics(mYear, mMonth);

        setChartPager();
    }

    private void setChartPager() {
        mChartFragmentList = new ArrayList<>();
        mPayoutChartFragment = BillDetailPayoutChartFragment.newInstance(mYear, mMonth);
        mIncomeChartFragment = BillDetailIncomeChartFragment.newInstance(mYear, mMonth);
        mChartFragmentList.add(mPayoutChartFragment);
        mChartFragmentList.add(mIncomeChartFragment);

        mPagerAdapter = new ChartPagerAdapter(getSupportFragmentManager(), mChartFragmentList);
        mChartVP.setAdapter(mPagerAdapter);

        mChartVP.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setButtonBackGround(position);
            }
        });
    }

    private void setSummaryStatistics(int year, int month) {
        int payoutCount = DBManager.getCountByYM(year, month, 0);
        float payoutMoney = DBManager.getTotalMoneyByYM(year, month, 0);
        int incomeCount = DBManager.getCountByYM(year, month, 1);
        float incomeMoney = DBManager.getTotalMoneyByYM(year, month, 1);

        mDateTV.setText(year + "年" + month + "月账单");
        mPayoutTV.setText("共" + payoutCount + "笔收入，¥ " + payoutMoney);
        mIncomeTV.setText("共" + incomeCount + "笔支出，¥ " + incomeMoney);
    }

    private void initView() {
        mDateTV = findViewById(R.id.bill_detail_date_tv);
        mPayoutTV = findViewById(R.id.bill_detail_payout_tv);
        mIncomeTV = findViewById(R.id.bill_detail_income_tv);
        mPayoutBTN = findViewById(R.id.bill_detail_payout_btn);
        mIncomeBTN = findViewById(R.id.bill_detail_income_btn);
        mChartVP = findViewById(R.id.bill_detail_chart_vp);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bill_detail_back_iv:
                finish();
                break;
            case R.id.bill_detail_calendar_iv:
                showCalendarDialog();
                break;
            case R.id.bill_detail_payout_btn:
                setButtonBackGround(0);
                mChartVP.setCurrentItem(0);
                break;
            case R.id.bill_detail_income_btn:
                setButtonBackGround(1);
                mChartVP.setCurrentItem(1);
                break;
        }
    }

    private void showCalendarDialog() {
        CalendarDialog calendarDialog = new CalendarDialog(this, mSelectedYearPos, mMonth);
        calendarDialog.show();
        calendarDialog.setDialogSize();
        calendarDialog.setOnCalendarSelectListener(new CalendarDialog.OnCalendarSelectListener() {
            @Override
            public void onSelect(int selectPos, int year, int month) {
                mSelectedYearPos = selectPos;
                mYear = year;
                mMonth = month;

                setSummaryStatistics(year, month);

                mPayoutChartFragment.setDate(year, month);
                mIncomeChartFragment.setDate(year, month);
            }
        });
    }

    private void setButtonBackGround(int type) {
        // 0 - 支出; 1 - 收入
        if (type == 0) {
            mPayoutBTN.setBackgroundResource(R.drawable.btn_dialog_ok_background);
            mPayoutBTN.setTextColor(Color.WHITE);
            mIncomeBTN.setBackgroundResource(R.drawable.btn_dialog_cancel_background);
            mIncomeBTN.setTextColor(Color.BLACK);
        } else {
            mIncomeBTN.setBackgroundResource(R.drawable.btn_dialog_ok_background);
            mIncomeBTN.setTextColor(Color.WHITE);
            mPayoutBTN.setBackgroundResource(R.drawable.btn_dialog_cancel_background);
            mPayoutBTN.setTextColor(Color.BLACK);
        }
    }
}