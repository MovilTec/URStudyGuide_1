package com.example.bbvacontrol.uranitexpert;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    private SectionsPagerAdapter mSections;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTabLayout = findViewById(R.id.main_second_tabs);
        ViewPager mViewPage = findViewById(R.id.tabPager);
        mSections = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPage.setAdapter(mSections);


    }
}
