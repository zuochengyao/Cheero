package com.zcy.app.ui.model;

import android.text.TextUtils;

/**
 * Created by zuochengyao on 2018/2/26.
 */

public class SectionItem<T>
{
    private String mTitle;
    private T[] mItems;

    public SectionItem(String title, T[] items)
    {
        if (TextUtils.isEmpty(title))
            title = "None";
        mTitle = title;
        mItems = items;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public T getItem(int position)
    {
        return mItems[position];
    }

    public int getCount()
    {
        return (mItems == null ? 1 : 1 + mItems.length);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof SectionItem)
            return ((SectionItem) obj).getTitle().equals(mTitle);
        return false;
    }
}
