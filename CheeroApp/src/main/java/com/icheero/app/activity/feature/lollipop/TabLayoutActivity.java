package com.icheero.app.activity.feature.lollipop;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.icheero.app.R;
import com.icheero.app.custom.adapter.FragmentAdapter;
import com.icheero.app.custom.fragment.TabFragment;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutActivity extends BaseActivity
{
    @BindView(R.id.tab_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private FragmentAdapter mAdapter;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private String[] mTitles = new String[]{"智能","红润","日系","自然","艺术黑白","甜美","蜜粉","清新","夏日阳光","唯美","蜜粉"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        for (int i = 0; i < mTitles.length; i++)
        {
            mFragments.add(new TabFragment());
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager, false);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        for (int i = 0; i < mTitles.length; i++)
        {
            mTabLayout.getTabAt(i).setText(mTitles[i]);
        }
    }
}
