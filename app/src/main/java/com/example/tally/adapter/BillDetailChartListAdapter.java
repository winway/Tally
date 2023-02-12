package com.example.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tally.R;
import com.example.tally.bean.BillDetailChartListItemBean;

import java.util.List;

/**
 * @PackageName: com.example.tally.adapter
 * @ClassName: ChartListAdapter
 * @Author: winwa
 * @Date: 2023/2/11 8:20
 * @Description:
 **/
public class BillDetailChartListAdapter extends BaseAdapter {
    private Context mContext;
    private List<BillDetailChartListItemBean> mData;

    public BillDetailChartListAdapter(Context context, List<BillDetailChartListItemBean> data) {
        mContext = context;
        mData = data;
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
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.lvitem_bill_detail_chart, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BillDetailChartListItemBean billDetailChartListItemBean = mData.get(i);
        holder.mTypeIV.setImageResource(billDetailChartListItemBean.getImageId());
        holder.mTypeTV.setText(billDetailChartListItemBean.getName());
        holder.mPercentTV.setText(String.format("%.2f%%", billDetailChartListItemBean.getPercent() * 100));
        holder.mMoneyTV.setText("¥ " + billDetailChartListItemBean.getMoney());

        return view;
    }

    class ViewHolder {
        ImageView mTypeIV;
        TextView mTypeTV;
        TextView mPercentTV;
        TextView mMoneyTV;

        public ViewHolder(View view) {
            mTypeIV = view.findViewById(R.id.lvitem_bill_detail_chart_type_iv);
            mTypeTV = view.findViewById(R.id.lvitem_bill_detail_chart_type_tv);
            mPercentTV = view.findViewById(R.id.lvitem_bill_detail_chart_percent_tv);
            mMoneyTV = view.findViewById(R.id.lvitem_bill_detail_chart_money_tv);
        }
    }
}
