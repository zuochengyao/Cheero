package com.icheero.faceid.activity;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.base.BaseFragment;
import com.icheero.sdk.core.manager.ViewManager;
import com.icheero.sdk.widget.NoScrollViewPager;
import com.icheero.faceid.R;
import com.icheero.faceid.adapter.FragmentAdapter;
import com.icheero.faceid.fragment.IDCardFragment;
import com.icheero.faceid.fragment.LiteFragment;
import com.icheero.faceid.fragment.MegLiveFragment;
import com.icheero.faceid.listener.OnFragmentListener;
import com.megvii.api.FaceIDApi;
import com.megvii.api.FaceIDApiCallback;
import com.megvii.api.FaceIDConfig;

import java.io.IOException;
import java.util.List;

@Route(path = "/faceid/index")
public class FaceIDActivity extends BaseActivity implements FaceIDApiCallback, OnFragmentListener
{
    private static final String TAG = "FaceID";
    private BottomNavigationView navigation;
    private NoScrollViewPager mViewPager;

    private List<BaseFragment> mFragments;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faceid);
        FaceIDConfig config = new FaceIDConfig.Builder()
                .setApiKey("56ymDEP38_Z1uwDoyRSUEUr_ukBJfEzG")
                .setApiSecret("Lmy0aNaQ2fXg_ZaliQtvhjcPrgmxGq5y")
                .setConnectTimeout(20)
                .setReadTimeout(30)
                .setWriteTimeout(30)
                .build();
        FaceIDApi.getInstance().init(config);
        FaceIDApi.getInstance().setFaceIDApiCallback(this);
        doInitView();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FaceIDApi.getInstance().setFaceIDApiCallback(null);
    }

    private void doInitView()
    {
        navigation = $(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener((item) -> {
            int i = item.getItemId();
            if (i == R.id.navigation_idcard)
            {
                mViewPager.setCurrentItem(0);
                return true;
            }
            else if (i == R.id.navigation_meglive)
            {
                mViewPager.setCurrentItem(1);
                return true;
            }
            else if (i == R.id.navigation_lite)
            {
                mViewPager.setCurrentItem(2);
                return true;
            }
            else return false;
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
        mViewPager.setPageEnabled(false);
        mViewPager.setAdapter(mFragmentAdapter);
    }

    @Override
    public void onResponse(int i, int i1, String s)
    {
        Log.d(TAG, String.format("ApiID:%d, StatusCode:%d, Json:%s", i, i1, s));
    }

    @Override
    public void onFailure(int i, IOException e)
    {
        Log.d(TAG, String.format("ApiID:%d, error:%s", i, e.getMessage()));
    }

    @Override
    public void OnButtonPressed(int viewId)
    {
        Log.d(TAG, String.format("viewId:%d", viewId));
    }
}
