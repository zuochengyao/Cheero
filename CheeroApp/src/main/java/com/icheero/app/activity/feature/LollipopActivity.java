package com.icheero.app.activity.feature;

import android.os.Bundle;
import android.transition.Slide;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.icheero.app.R;
import com.icheero.app.custom.adapter.FragmentAdapter;
import com.icheero.app.custom.fragment.CardViewFragment;
import com.icheero.app.custom.fragment.RecyclerViewFragment;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.base.BaseFragment;
import com.icheero.sdk.util.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LollipopActivity extends BaseActivity
{
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tab_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.navigation_lollipop)
    NavigationView mNavigationView;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private String[] mFeatures = new String[]{"CardView", "RecycleView", "TabLayout"};
    private List<BaseFragment> mTabFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lollipop);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        doInitActionBar();
        doInitFab();
        doInitNavigation();
        doInitTabLayout();
    }

    private void doInitActionBar()
    {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void doInitFab()
    {
        mFloatingActionButton.setOnClickListener(v -> Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).show());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
        {
            Slide slide = new Slide();
            slide.setDuration(700);
            getWindow().setExitTransition(slide);
        }
    }

    private void doInitNavigation()
    {
        if (mNavigationView != null)
        {
            mNavigationView.setNavigationItemSelectedListener(menuItem -> {
                menuItem.setCheckable(true);
                String title = menuItem.getTitle().toString();
                Common.toast(LollipopActivity.this, title, Toast.LENGTH_SHORT);
                mDrawerLayout.closeDrawers();
                return true;
            });
        }
    }

    private void doInitTabLayout()
    {
        for (String feature : mFeatures)
            mTabLayout.addTab(mTabLayout.newTab().setText(feature));
        mTabFragments.add(new CardViewFragment());
        mTabFragments.add(new RecyclerViewFragment());
        mTabFragments.add(new RecyclerViewFragment());
        // 创建Bundle对象
        Bundle data = new Bundle();
        data.putString("name", "Cheero");
        data.putInt("age", 28);
        mTabFragments.get(0).setArguments(data);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mTabFragments, Arrays.asList(mFeatures));
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager, false);
    }

    /**
     * 监听ToolBar的菜单选择，否则Navigation出不来
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
