package com.icheero.app.custom.adapter;

import com.icheero.sdk.base.BaseFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter
{
    private List<BaseFragment> mFragments;
    private List<String> mPageTitles;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragments)
    {
        this(fm, fragments, null);
    }

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> pageTitles)
    {
        super(fm);
        this.mFragments = fragments;
        this.mPageTitles = pageTitles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mPageTitles.get(position);
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
