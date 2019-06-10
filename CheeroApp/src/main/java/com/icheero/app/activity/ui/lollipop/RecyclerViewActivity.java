package com.icheero.app.activity.ui.lollipop;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.app.custom.adapter.RecyclerViewAdapter;
import com.icheero.sdk.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends BaseActivity
{
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        // 设置 item 增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            stringList.add(i + "");
        mRecyclerView.setAdapter(new RecyclerViewAdapter(this, stringList));
        // 设置分割线，现在有库提供的，还可以自定义分割线
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.HORIZONTAL));
    }
}
