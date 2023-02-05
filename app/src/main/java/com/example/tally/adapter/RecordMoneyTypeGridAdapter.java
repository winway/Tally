package com.example.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tally.R;
import com.example.tally.bean.MoneyTypeBean;

import java.util.List;

/**
 * @PackageName: com.example.tally.adapter
 * @ClassName: RecordMoneyTypeListAdapter
 * @Author: winwa
 * @Date: 2023/2/2 8:47
 * @Description:
 **/
public class RecordMoneyTypeGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<MoneyTypeBean> mData;
    private int mSelectedPos = 0;

    public RecordMoneyTypeGridAdapter(Context context, List<MoneyTypeBean> data) {
        mContext = context;
        mData = data;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.gvitem_record_money_type, null);
        ImageView typeIv = view.findViewById(R.id.gvitem_record_money_type_iv);
        TextView typeTv = view.findViewById(R.id.gvitem_record_money_type_tv);

        MoneyTypeBean moneyType = mData.get(i);
        if (mSelectedPos == i) {
            typeIv.setImageResource(moneyType.getImageIdSelected());
        } else {
            typeIv.setImageResource(moneyType.getImageId());
        }
        typeTv.setText(moneyType.getName());

        return view;
    }
}
