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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TabFragment extends BaseFragment
{
    private String[] nameDataList = new String[]{"智能","红润","日系","自然","艺术黑白","甜美","蜜粉","清新","夏日阳光","唯美","蜜粉"};

    @BindView(R.id.tab_recycler)
    RecyclerView mTabRecycler;

    private RecyclerStringAdapter mStringAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        mTabRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // 直接使用Arrays.asList(nameDataList)时，执行删除时，报错UnsupportedOperationException
        mStringAdapter = new RecyclerStringAdapter(getContext(), new ArrayList<>(Arrays.asList(nameDataList)));
        mTabRecycler.setAdapter(mStringAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
}
