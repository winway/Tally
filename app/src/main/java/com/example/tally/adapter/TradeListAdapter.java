package com.example.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tally.R;
import com.example.tally.bean.RecordBean;
import com.example.tally.utils.DateUtils;

import java.util.List;

/**
 * @PackageName: com.example.tally.adapter
 * @ClassName: TradeListAdapter
 * @Author: winwa
 * @Date: 2023/2/4 19:34
 * @Description:
 **/
public class TradeListAdapter extends BaseAdapter {
    private Context mContext;
    private List<RecordBean> mData;

    public TradeListAdapter(Context context, List<RecordBean> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.lvitem_main, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        RecordBean recordBean = mData.get(i);
        holder.mTypeIV.setImageResource(recordBean.getImageId());
        holder.mTypeTV.setText(recordBean.getName());
        holder.mRemarkTV.setText(recordBean.getRemark());
        holder.mMoneyTV.setText("¥ " + recordBean.getMoney());
        if (DateUtils.isToday(recordBean.getYear(), recordBean.getMonth(), recordBean.getDay())) {
            holder.mTimeTV.setText("今天 " + recordBean.getTime().split(" ")[1]);
        } else {
            holder.mTimeTV.setText(recordBean.getTime());
        }

        return view;
    }

    class ViewHolder {
        private ImageView mTypeIV;
        private TextView mTypeTV;
        private TextView mRemarkTV;
        private TextView mMoneyTV;
        private TextView mTimeTV;

        public ViewHolder(View view) {
            mTypeIV = view.findViewById(R.id.lvitem_main_type_iv);
            mTypeTV = view.findViewById(R.id.lvitem_main_type_tv);
            mRemarkTV = view.findViewById(R.id.lvitem_main_remark_tv);
            mMoneyTV = view.findViewById(R.id.lvitem_main_money_tv);
            mTimeTV = view.findViewById(R.id.lvitem_main_time_tv);
        }
    }
}
