package com.icheero.faceid.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icheero.sdk.base.BaseFragment;
import com.icheero.faceid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiteFragment extends BaseFragment
{
    public LiteFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lite, container, false);
    }

}
