package com.icheero.app.activity.feature;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.icheero.app.R;
import com.icheero.app.custom.adapter.FragmentAdapter;
import com.icheero.app.custom.fragment.CardViewFragment;
import com.icheero.app.custom.fragment.RecyclerViewFragment;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LollipopActivity extends BaseActivity
{
    @BindView(R.id.tab_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private String[] mFeatures = new String[]{"CardView", "RecycleView", "TabLayout"};
    private List<BaseFragment> mTabFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lollipop);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        for (String feature : mFeatures)
            mTabLayout.addTab(mTabLayout.newTab().setText(feature));
        mTabFragments.add(new CardViewFragment());
        mTabFragments.add(new RecyclerViewFragment());
        mTabFragments.add(new RecyclerViewFragment());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mTabFragments, Arrays.asList(mFeatures));
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager, false);
    }
}
