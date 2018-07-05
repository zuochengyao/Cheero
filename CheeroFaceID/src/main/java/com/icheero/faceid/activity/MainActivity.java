package com.icheero.faceid.activity;

import android.os.Bundle;

import com.icheero.common.base.BaseActivity;
import com.icheero.common.base.BaseFragment;
import com.icheero.common.widget.NoScrollViewPager;
import com.icheero.faceid.R;
import com.icheero.faceid.adapter.FragmentAdapter;

import java.util.List;

public class MainActivity extends BaseActivity
{
    private NoScrollViewPager mViewPager;
    private List<BaseFragment> mFragments;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
