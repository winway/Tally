package com.example.tally;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tally.adapter.TradeListAdapter;
import com.example.tally.bean.RecordBean;
import com.example.tally.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText mInputET;
    private ListView mLV;
    private TextView mEmptyTV;

    private TradeListAdapter mListAdapter;
    private List<RecordBean> mListAdapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    private void initListView() {
        mListAdapterData = new ArrayList<>();
        mListAdapter = new TradeListAdapter(this, mListAdapterData);
        mLV.setAdapter(mListAdapter);
        mLV.setEmptyView(mEmptyTV);
    }

    private void initView() {
        mInputET = findViewById(R.id.search_input_et);
        mLV = findViewById(R.id.search_lv);
        mEmptyTV = findViewById(R.id.search_empty_tv);

        initListView();
    }

    private void refreshListView(List<RecordBean> recordBeanList) {
        mListAdapterData.clear();
        mListAdapterData.addAll(recordBeanList);
        mListAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_iv:
                finish();
                break;
            case R.id.search_submit_iv:
                String query = mInputET.getText().toString().trim();
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(this, "输入不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<RecordBean> recordBeanList = DBManager.getRecordListByRemark(query);

                refreshListView(recordBeanList);
                break;
        }
    }
}