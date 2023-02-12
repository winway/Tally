package com.example.tally.more;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillDetailPayoutChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillDetailPayoutChartFragment extends BillDetailBaseChartFragment {

    public BillDetailPayoutChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year  Parameter 1.
     * @param month Parameter 2.
     * @return A new instance of fragment BillDetailPayoutChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillDetailPayoutChartFragment newInstance(int year, int month) {
        BillDetailPayoutChartFragment fragment = new BillDetailPayoutChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getType() {
        return 0;
    }
}