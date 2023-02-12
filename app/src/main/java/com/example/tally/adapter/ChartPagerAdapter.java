package com.example.tally.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @PackageName: com.example.tally.adapter
 * @ClassName: ChartPagerAdapter
 * @Author: winwa
 * @Date: 2023/2/10 8:46
 * @Description:
 **/
public class ChartPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public ChartPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
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
}
