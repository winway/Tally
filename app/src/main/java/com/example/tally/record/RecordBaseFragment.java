package com.example.tally.record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tally.R;
import com.example.tally.adapter.RecordMoneyTypeGridAdapter;
import com.example.tally.bean.MoneyTypeBean;
import com.example.tally.bean.RecordBean;
import com.example.tally.common.MyKeyBoard;
import com.example.tally.common.RemarkDialog;
import com.example.tally.common.TimeDialog;
import com.example.tally.db.DBManager;
import com.example.tally.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordBaseFragment#} factory method to
 * create an instance of this fragment.
 */
public abstract class RecordBaseFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RecordBaseFragment";
    protected RecordBean mRecordBean;
    private ImageView mTypeIV;
    private TextView mTypeTV;
    private EditText mMoneyET;
    private GridView mTypeGV;
    private TextView mRemarkTV;
    private TextView mTimeTV;
    private KeyboardView mKeyboardView;
    private List<MoneyTypeBean> mAdapterDataGV;
    private RecordMoneyTypeGridAdapter mAdapterGV;

    public abstract int getType();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecordBean = new RecordBean();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        initView(view);

        initTypeGV();

        initTime();

        return view;
    }

    private void initTime() {
        Date date = new Date();
        String dateString = DateUtils.getDateString(date, "yyyy年MM月dd日 HH:mm");
        mTimeTV.setText(dateString);
        mRecordBean.setTime(dateString);
        mRecordBean.setYear(DateUtils.getYear(date));
        mRecordBean.setMonth(DateUtils.getMonth(date));
        mRecordBean.setDay(DateUtils.getDay(date));
    }

    private void initTypeGV() {
        mAdapterDataGV = new ArrayList<>();
        mAdapterGV = new RecordMoneyTypeGridAdapter(getContext(), mAdapterDataGV);
        mTypeGV.setAdapter(mAdapterGV);

        mAdapterDataGV.addAll(DBManager.getMoneyTypeList(getType()));
        mAdapterGV.notifyDataSetChanged();

        mTypeGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapterGV.setSelectedPos(i);
                mAdapterGV.notifyDataSetInvalidated();

                MoneyTypeBean moneyType = mAdapterDataGV.get(i);
                mTypeIV.setImageResource(moneyType.getImageIdSelected());
                mTypeTV.setText(moneyType.getName());

                mRecordBean.setName(moneyType.getName());
                mRecordBean.setImageId(moneyType.getImageIdSelected());
            }
        });
    }

    private void initView(View view) {
        mTypeIV = view.findViewById(R.id.record_input_type_iv);
        mTypeTV = view.findViewById(R.id.record_input_type_tv);
        mMoneyET = view.findViewById(R.id.record_input_money_et);
        mTypeGV = view.findViewById(R.id.record_type_gv);
        mRemarkTV = view.findViewById(R.id.record_remark_tv);
        mTimeTV = view.findViewById(R.id.record_time_tv);
        mKeyboardView = view.findViewById(R.id.record_kv);

        mTypeTV.setText("其他");
        mRecordBean.setName("其他");

        if (getType() == 0) {
            mTypeIV.setImageResource(R.mipmap.ic_qita_fs);
            mRecordBean.setImageId(R.mipmap.ic_qita_fs);
        } else {
            mTypeIV.setImageResource(R.mipmap.in_qt_fs);
            mRecordBean.setImageId(R.mipmap.in_qt_fs);
        }

        MyKeyBoard myKeyBoard = new MyKeyBoard(mKeyboardView, mMoneyET);
        myKeyBoard.show();
        myKeyBoard.setOKPressedListener(new MyKeyBoard.OnKeyboardOKPressedListener() {
            @Override
            public void onOK() {
                String money = mMoneyET.getText().toString();
                if (TextUtils.isEmpty(money) || money.equals("0")) {
                    getActivity().finish();
                    return;
                }

                mRecordBean.setMoney(Float.parseFloat(money));

                mRecordBean.setType(getType());

                DBManager.insertRecord(mRecordBean);

                getActivity().finish();
            }
        });

        mRemarkTV.setOnClickListener(this);
        mTimeTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_remark_tv:
                showRemarkDialog();
                break;
            case R.id.record_time_tv:
                showTimeDialog();
                break;
        }
    }

    private void showTimeDialog() {
        TimeDialog timeDialog = new TimeDialog(getContext());
        timeDialog.show();
        timeDialog.setOnDialogOKPressedListener(new TimeDialog.OnDialogOKPressedListener() {
            @Override
            public void onOK(String time, int year, int month, int day) {
                mTimeTV.setText(time);
                mRecordBean.setTime(time);
                mRecordBean.setYear(year);
                mRecordBean.setMonth(month);
                mRecordBean.setDay(day);
            }
        });
    }

    private void showRemarkDialog() {
        RemarkDialog remarkDialog = new RemarkDialog(getContext());
        remarkDialog.show();
        remarkDialog.setDialogSize();

        remarkDialog.setOKPressedListener(new RemarkDialog.OnDialogOKPressedListener() {
            @Override
            public void onOK() {
                String msg = remarkDialog.getEditText();
                if (!TextUtils.isEmpty(msg)) {
                    mRemarkTV.setText(msg);
                    mRecordBean.setRemark(msg);
                }
                remarkDialog.cancel();
            }
        });
    }
}