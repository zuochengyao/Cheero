package com.icheero.faceid.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import com.icheero.common.base.BaseActivity;
import com.icheero.common.base.BaseFragment;
import com.icheero.common.manager.ViewManager;
import com.icheero.common.widget.NoScrollViewPager;
import com.icheero.faceid.R;
import com.icheero.faceid.adapter.FragmentAdapter;
import com.icheero.faceid.fragment.IDCardFragment;
import com.icheero.faceid.fragment.LiteFragment;
import com.icheero.faceid.fragment.MegLiveFragment;

import java.util.List;

public class MainActivity extends BaseActivity
{
    private BottomNavigationView navigation;
    private NoScrollViewPager mViewPager;

    private List<BaseFragment> mFragments;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doInitView();
    }

    private void doInitView()
    {
        navigation = $(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId())
            {
                case R.id.navigation_idcard:
                {
                    mViewPager.setCurrentItem(0);
                    return true;
                }
                case R.id.navigation_meglive:
                {
                    mViewPager.setCurrentItem(1);
                    return true;
                }
                case R.id.navigation_lite:
                {
                    mViewPager.setCurrentItem(2);
                    return true;
                }
                default:
                    return false;
            }
        });
        mViewPager = $(R.id.container_pager);
        mFragments = ViewManager.getInstance().getFragments();
        BaseFragment faceIdFragment = new IDCardFragment();
        BaseFragment megliveFragment = new MegLiveFragment();
        BaseFragment liteFragment = new LiteFragment();
        mFragments.add(faceIdFragment);
        mFragments.add(megliveFragment);
        mFragments.add(liteFragment);
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setPageEnabled(true);
        mViewPager.setAdapter(mFragmentAdapter);
    }

    /**
     * 在News模块中寻找实现的Fragment
     *
     * @return Fragment
     */
    private BaseFragment getNewsFragment() {
        BaseFragment newsFragment = null;
//        List<IViewDelegate> viewDelegates = ClassUtils.getObjectsWithInterface(this, IViewDelegate.class, "com.guiying.module.news");
//        if (viewDelegates != null && !viewDelegates.isEmpty()) {
//            newsFragment = viewDelegates.get(0).getFragment("");
//        }
        return newsFragment;
    }
}
