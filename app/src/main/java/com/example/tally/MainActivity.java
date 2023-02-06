package com.example.tally;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tally.adapter.TradeListAdapter;
import com.example.tally.bean.RecordBean;
import com.example.tally.common.BudgetDialog;
import com.example.tally.db.DBManager;
import com.example.tally.record.RecordActivity;
import com.example.tally.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ImageView mSearchIV;
    private ListView mLV;
    private ImageButton mMoreBTN;
    private Button mRecordBTN;

    private View mListHeaderV;
    private TextView mPayoutTV;
    private TextView mIncomeTV;
    private TextView mBudgetTV;
    private ImageView mHideIV;
    private TextView mTodaySummaryTV;

    private TradeListAdapter mListAdapter;
    private List<RecordBean> mListAdapterData;

    private SharedPreferences mPreferences;

    private boolean mIsSHow = true;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date date = new Date();
        mYear = DateUtils.getYear(date);
        mMonth = DateUtils.getMonth(date);
        mDay = DateUtils.getDay(date);

        mPreferences = getSharedPreferences("budget", MODE_PRIVATE);

        mSearchIV = findViewById(R.id.main_search_iv);
        mLV = findViewById(R.id.main_lv);
        mMoreBTN = findViewById(R.id.main_more_btn);
        mRecordBTN = findViewById(R.id.main_record_btn);

        mSearchIV.setOnClickListener(this);
        mMoreBTN.setOnClickListener(this);
        mRecordBTN.setOnClickListener(this);

        initListView();
    }

    private void initListView() {
        mListAdapterData = new ArrayList<>();
        mListAdapter = new TradeListAdapter(this, mListAdapterData);
        mLV.setAdapter(mListAdapter);

        mLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    return false;
                }
                RecordBean recordBean = mListAdapterData.get(i - 1);
                showDeleteDialog(recordBean);
                return false;
            }
        });

        addListHeaderView();
    }

    private void showDeleteDialog(RecordBean recordBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
                .setMessage("确定删除此记录吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBManager.deleteRecordById(recordBean.getId());
                        mListAdapterData.remove(recordBean);
                        mListAdapter.notifyDataSetChanged();
                        refreshListHeaderView();
                    }
                }).create().show();
    }

    private void addListHeaderView() {
        mListHeaderV = getLayoutInflater().inflate(R.layout.lvheader_main, null);
        mLV.addHeaderView(mListHeaderV);

        mPayoutTV = mListHeaderV.findViewById(R.id.lvheader_main_payout_amount_tv);
        mIncomeTV = mListHeaderV.findViewById(R.id.lvheader_main_income_amount_tv);
        mBudgetTV = mListHeaderV.findViewById(R.id.lvheader_main_budget_amount_tv);
        mHideIV = mListHeaderV.findViewById(R.id.lvheader_main_hide_iv);
        mTodaySummaryTV = mListHeaderV.findViewById(R.id.lvheader_main_today_summary_tv);

        mBudgetTV.setOnClickListener(this);
        mListHeaderV.setOnClickListener(this);
        mHideIV.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshListHeaderView();

        refreshListView();
    }

    private void refreshListHeaderView() {
        float monthPayout = DBManager.getTotalMoneyByYM(mYear, mMonth, 0);
        float monthIncome = DBManager.getTotalMoneyByYM(mYear, mMonth, 1);
        mPayoutTV.setText("¥ " + monthPayout);
        mIncomeTV.setText("¥ " + monthIncome);

        float budget = mPreferences.getFloat("budget", 0);
        if (budget == 0) {
            mBudgetTV.setText("¥ 0");
        } else {
            float leftBudget = budget - monthPayout;
            mBudgetTV.setText("¥ " + leftBudget);
        }

        float dayPayout = DBManager.getTotalMoneyByYMD(mYear, mMonth, mDay, 0);
        float dayIncome = DBManager.getTotalMoneyByYMD(mYear, mMonth, mDay, 1);
        mTodaySummaryTV.setText(String.format("今日支出 ¥ %s 收入 ¥ %s", dayPayout, dayIncome));
    }

    private void refreshListView() {
        List<RecordBean> recordList = DBManager.getRecordListByYMD(mYear, mMonth, mDay);
        mListAdapterData.clear();
        mListAdapterData.addAll(recordList);
        mListAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.main_search_iv:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.main_record_btn:
                intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.main_more_btn:
                break;
            case R.id.lvheader_main_budget_amount_tv:
                showBudgetDialog();
                break;
            case R.id.lvheader_main_hide_iv:
                toggleHide();
                break;
        }

        if (view == mListHeaderV) {

        }
    }

    private void showBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog(this);
        budgetDialog.show();
        budgetDialog.setDialogSize();

        budgetDialog.setOKPressedListener(new BudgetDialog.OnDialogOKPressedListener() {
            @Override
            public void onOK(float budget) {
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putFloat("budget", budget);
                edit.apply();

                float leftBudget = budget - DBManager.getTotalMoneyByYM(mYear, mMonth, 0);
                mBudgetTV.setText("¥ " + leftBudget);
            }
        });
    }

    private void toggleHide() {
        if (mIsSHow) {
            PasswordTransformationMethod instance = PasswordTransformationMethod.getInstance();
            mPayoutTV.setTransformationMethod(instance);
            mIncomeTV.setTransformationMethod(instance);
            mBudgetTV.setTransformationMethod(instance);
            mHideIV.setImageResource(R.mipmap.ih_hide);
            mIsSHow = false;
        } else {
            HideReturnsTransformationMethod instance = HideReturnsTransformationMethod.getInstance();
            mPayoutTV.setTransformationMethod(instance);
            mIncomeTV.setTransformationMethod(instance);
            mBudgetTV.setTransformationMethod(instance);
            mHideIV.setImageResource(R.mipmap.ih_show);
            mIsSHow = true;
        }
    }
}