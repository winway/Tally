package com.example.tally.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @PackageName: com.example.tally.adapter
 * @ClassName: RecordPagerAdapter
 * @Author: winwa
 * @Date: 2023/2/2 7:50
 * @Description:
 **/
public class RecordPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    private String[] mPageTitles = {"支出", "收入"};

    public RecordPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitles[position];
    }
}
