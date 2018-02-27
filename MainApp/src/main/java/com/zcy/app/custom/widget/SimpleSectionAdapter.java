package com.zcy.app.custom.widget;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zcy.app.R;
import com.zcy.app.model.SectionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuochengyao on 2018/2/26.
 */

public abstract class SimpleSectionAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater mLayoutInflater;
    private int mHeaderResource;
    private int mItemResource;

    // 所以节的唯一集合
    private List<SectionItem<T>> mSections;
    // 节的分组，按其初始位置设置键
    private SparseArray<SectionItem<T>> mKeyedSections;

    public SimpleSectionAdapter(ListView parent, int headerResId, int itemResId)
    {
        mLayoutInflater = LayoutInflater.from(parent.getContext());
        mHeaderResource = headerResId;
        mItemResource = itemResId;

        // 创建包含自动排序键的集合
        mSections = new ArrayList<>();
        mKeyedSections = new SparseArray<>();

        // 将自身附加为列表的单击处理程序
        parent.setOnItemClickListener(this);
    }

    /**
     * 向列表添加新的带标题的节，或者更新现有的节
     */
    public void addSection(String title, T[] items)
    {
        SectionItem<T> sectionItem = new SectionItem<>(title, items);
        int currentIndex = mSections.indexOf(sectionItem);
        if (currentIndex >= 0)
        {
            mSections.remove(sectionItem);
            mSections.add(currentIndex, sectionItem);
        }
        else
            mSections.add(sectionItem);
        reOrderSections();
        notifyDataSetChanged();
    }

    /**
     * 将带有初始全局位置的节标记为可引用的键
     */
    private void reOrderSections()
    {
        mKeyedSections.clear();
        int startPosition = 0;
        for (SectionItem<T> item : mSections)
        {
            mKeyedSections.put(startPosition, item);
            startPosition += item.getCount();
        }
    }

    /**
     * 检测是否是代表节标题的全局位置值
     */
    private boolean isHeaderAtPosition(int position)
    {
        for (int i = 0; i < mKeyedSections.size(); i++)
        {
            if (position == mKeyedSections.keyAt(i))
                return true;
        }
        return false;
    }

    /**
     * 返回给定全局位置的显示列表项
     */
    private T findSectionItemAtPosition(int position)
    {
        int firstIndex, lastIndex;
        for (int i = 0; i < mKeyedSections.size(); i++)
        {
            firstIndex = mKeyedSections.keyAt(i);
            lastIndex = firstIndex + mKeyedSections.valueAt(i).getCount();
            if (position >= firstIndex && position < lastIndex)
            {
                int sectionPosition = position - firstIndex - 1;
                return mKeyedSections.valueAt(i).getItem(sectionPosition);
            }
        }
        return null;
    }

    private View getHeaderView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(mHeaderResource, parent, false);
        SectionItem<T> item = mKeyedSections.get(position);
        TextView textView = convertView.findViewById(R.id.list_header_text);
        textView.setText(item.getTitle());
        return convertView;
    }

    private View getItemView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(mItemResource, parent, false);
        T item = findSectionItemAtPosition(position);
        TextView textView = convertView.findViewById(/* android.R.id.text1 */ R.id.list_item_text);
        textView.setText(item.toString());
        return convertView;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        for (SectionItem<T> item : mSections)
            count += item.getCount();
        return count;
    }

    @Override
    public int getViewTypeCount()
    {
        // 两种试图类型：header + item
        return 2;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isHeaderAtPosition(position))
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    @Override
    public Object getItem(int position)
    {
        return findSectionItemAtPosition(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * 告诉ListView，不是全部都能点击
     */
    @Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    /**
     * 告诉ListView，头部是不可点击的
     */
    @Override
    public boolean isEnabled(int position)
    {
        return !isHeaderAtPosition(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        switch (getItemViewType(position))
        {
            case TYPE_HEADER:
                return getHeaderView(position, convertView, parent);
            case TYPE_ITEM:
                return getItemView(position, convertView, parent);
            default:
                return convertView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        T item = findSectionItemAtPosition(position);
        if (item != null)
            onSectionItemClick(item);
    }

    /**
     * 处理特定元素的单击事件
     * @param item
     */
    public abstract void onSectionItemClick(T item);
}
