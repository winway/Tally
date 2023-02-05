package com.example.tally.record;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tally.R;
import com.example.tally.adapter.RecordPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private TabLayout mTL;
    private ViewPager mVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mTL = findViewById(R.id.record_tl);
        mVP = findViewById(R.id.record_vp);

        initPager();
    }

    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PayoutFragment());
        fragmentList.add(new IncomeFragment());

        RecordPagerAdapter recordPagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), fragmentList);
        mVP.setAdapter(recordPagerAdapter);
        mTL.setupWithViewPager(mVP);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_cancel_iv:
                finish();
                break;
        }
    }
}