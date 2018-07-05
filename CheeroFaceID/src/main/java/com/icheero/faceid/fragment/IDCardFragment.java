package com.icheero.faceid.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icheero.common.base.BaseFragment;
import com.icheero.faceid.R;
import com.icheero.faceid.listener.OnFragmentListener;

public class IDCardFragment extends BaseFragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentListener mListener;

    public IDCardFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IDCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IDCardFragment newInstance(String param1, String param2)
    {
        IDCardFragment fragment = new IDCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
            mListener.OnFragmentAttachListener(uri);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentListener)
            mListener = (OnFragmentListener) context;
//        else
//            throw new RuntimeException(context.toString() + " must implement OnFragmentAttachListener");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idcard, container, false);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
}
