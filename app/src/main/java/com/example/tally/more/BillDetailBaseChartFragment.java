package com.example.tally.more;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tally.R;
import com.example.tally.adapter.BillDetailChartListAdapter;
import com.example.tally.bean.BillDetailChartItemBean;
import com.example.tally.bean.BillDetailChartListItemBean;
import com.example.tally.db.DBManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

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

    private BarChart mBR;
    private TextView mEmptyTV;
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

        addListHeaderView();

        return view;
    }

    private void addListHeaderView() {
        View v = getLayoutInflater().inflate(R.layout.lvheader_bill_detail_chart, null);
        mLV.addHeaderView(v);

        mBR = v.findViewById(R.id.lvheader_bill_detail_chart_br);
        mEmptyTV = v.findViewById(R.id.lvheader_bill_detail_chart_empty_tv);

        mBR.getDescription().setEnabled(false);
        mBR.setExtraOffsets(20, 20, 20, 20);

        setChartAxis(mYear, mMonth);

        setChartAxisData(mYear, mMonth);
    }

    private void setChartAxisData(int year, int month) {
        List<BillDetailChartItemBean> chartItemBeanList = DBManager.getDayMoneyByYM(year, month, getType());
        if (chartItemBeanList.size() == 0) {
            mBR.setVisibility(View.GONE);
            mEmptyTV.setVisibility(View.VISIBLE);
            return;
        } else {
            mBR.setVisibility(View.VISIBLE);
            mEmptyTV.setVisibility(View.GONE);
        }

        List<IBarDataSet> iBarDataSetList = new ArrayList<>();

        List<BarEntry> barEntryList = new ArrayList<>();
        for (int i = 0; i < 31; i++) { //  TODO: 动态获取当月天数
            barEntryList.add(new BarEntry(i, 0.0f));
        }
        for (int i = 0; i < chartItemBeanList.size(); i++) {
            BillDetailChartItemBean chartItemBean = chartItemBeanList.get(i);
            int day = chartItemBean.getDay();
            BarEntry barEntry = barEntryList.get(day - 1);
            barEntry.setY(chartItemBean.getMoney());
        }

        BarDataSet barDataSet = new BarDataSet(barEntryList, "");
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(8f);
        if (getType() == 0) {
            barDataSet.setColor(Color.RED);
        } else {
            barDataSet.setColor(Color.parseColor("#006400"));
        }
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if (value == 0) {
                    return "";
                } else {
                    return value + "";
                }
            }
        });

        iBarDataSetList.add(barDataSet);

        BarData barData = new BarData(iBarDataSetList);
        barData.setBarWidth(0.2f);
        mBR.setData(barData);
    }

    private void setChartAxis(int year, int month) {
        setChartXAxis(year, month);
        setChartYAxis(year, month);
    }

    private void setChartXAxis(int year, int month) {
        XAxis xAxis = mBR.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(31); // TODO: 动态获取当月天数，首日/中间日/末日position，或者根据数据动态显示x轴label
        xAxis.setTextSize(12f);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int pos = (int) value;
                if (pos == 0) {
                    return month + "-1";
                }
                if (pos == 14) {
                    return month + "-15";
                }
                if (pos == 27 && month == 2) {
                    return month + "-28";
                } else if (pos == 29 && (month == 4 || month == 6 || month == 9 || month == 11)) {
                    return month + "-30";
                } else if (pos == 30 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) {
                    return month + "-31";
                }

                return "";
            }
        });

        xAxis.setYOffset(10);
    }

    private void setChartYAxis(int year, int month) {
        float maxMoney = (float) Math.ceil(DBManager.getMaxDayMoneyByYM(year, month, getType()));

        YAxis axisLeft = mBR.getAxisLeft();
        axisLeft.setAxisMaximum(maxMoney);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setEnabled(false);

        YAxis axisRight = mBR.getAxisRight();
        axisRight.setAxisMaximum(maxMoney);
        axisRight.setAxisMinimum(0f);
        axisRight.setEnabled(false);

        mBR.getLegend().setEnabled(false);
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

        mBR.clear();
        mBR.invalidate();
        setChartAxis(year, month);
        setChartAxisData(year, month);
    }
}