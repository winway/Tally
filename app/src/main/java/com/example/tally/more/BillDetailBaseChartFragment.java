package com.example.tally.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tally.R;
import com.example.tally.adapter.BillDetailChartListAdapter;
import com.example.tally.bean.BillDetailChartListItemBean;
import com.example.tally.db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillDetailBaseChartFragment#} factory method to
 * create an instance of this fragment.
 */
public abstract class BillDetailBaseChartFragment extends Fragment {

    protected static final String ARG_YEAR = "year";
    protected static final String ARG_MONTH = "month";

    private ListView mLV;

    private BillDetailChartListAdapter mListAdapter;
    private List<BillDetailChartListItemBean> mListAdapterData;

    private int mYear;
    private int mMonth;

    public BillDetailBaseChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYear = getArguments().getInt(ARG_YEAR);
            mMonth = getArguments().getInt(ARG_MONTH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_detail_chart, container, false);
        mLV = view.findViewById(R.id.bill_detail_chart_lv);

        mListAdapterData = new ArrayList<>();
        mListAdapter = new BillDetailChartListAdapter(getContext(), mListAdapterData);
        mLV.setAdapter(mListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListData(mYear, mMonth, getType());
    }

    protected abstract int getType();

    private void refreshListData(int year, int month, int type) {
        List<BillDetailChartListItemBean> typeStatics = DBManager.getTypeStaticsByYM(year, month, type);
        mListAdapterData.clear();
        mListAdapterData.addAll(typeStatics);
        mListAdapter.notifyDataSetChanged();
    }

    public void setDate(int year, int month) {
        mYear = year;
        mMonth = month;
        refreshListData(year, month, getType());
    }
}