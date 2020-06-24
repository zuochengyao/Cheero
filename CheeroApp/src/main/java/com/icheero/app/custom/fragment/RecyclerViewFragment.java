package com.icheero.app.custom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icheero.app.R;
import com.icheero.app.custom.adapter.RecyclerStringAdapter;
import com.icheero.sdk.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewFragment extends BaseFragment
{
    private String[] nameDataList = new String[]{"智能", "红润", "日系", "自然", "艺术黑白", "甜美", "蜜粉", "清新", "夏日阳光", "唯美", "蜜粉"};

    @BindView(R.id.tab_recycler)
    RecyclerView mTabRecycler;

    private RecyclerStringAdapter mStringAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        mTabRecycler.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        // 设置 item 增加和删除时的动画
        mTabRecycler.setItemAnimator(new DefaultItemAnimator());
        // 直接使用Arrays.asList(nameDataList)时，执行删除时，报错UnsupportedOperationException
        mStringAdapter = new RecyclerStringAdapter(getContext(), new ArrayList<>(Arrays.asList(nameDataList)));
        mTabRecycler.setAdapter(mStringAdapter);
        // 设置分割线，现在有库提供的，还可以自定义分割线
        // mTabRecycler.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        // mTabRecycler.addItemDecoration(new DividerItemDecoration(this, RecyclerView.HORIZONTAL));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
}
