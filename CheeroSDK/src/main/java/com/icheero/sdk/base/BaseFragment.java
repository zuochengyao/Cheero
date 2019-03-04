package com.icheero.sdk.base;

import android.content.Context;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment
{
    protected BaseActivity mBaseActivity;

    // region Fragment's Lifecycle

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.mBaseActivity = (BaseActivity) context;
    }

    // endregion

    // region Fragment Method

    /**
     * 获取宿主（Base）Activity
     * @return BaseActivity
     */
    protected BaseActivity getBaseActivity()
    {
        return mBaseActivity;
    }

    // endregion
}
