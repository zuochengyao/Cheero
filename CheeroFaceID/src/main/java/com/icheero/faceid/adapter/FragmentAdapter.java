package com.icheero.faceid.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.icheero.sdk.base.BaseFragment;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter
{
    private List<BaseFragment> mFragments;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragments)
    {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object)
    {
        return PagerAdapter.POSITION_NONE;
    }
}
