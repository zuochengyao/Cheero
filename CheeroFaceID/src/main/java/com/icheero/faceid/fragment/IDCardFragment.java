package com.icheero.faceid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.icheero.common.base.BaseFragment;
import com.icheero.faceid.R;
import com.icheero.faceid.listener.OnFragmentListener;
import com.megvii.api.FaceIDApi;

import java.io.File;

public class IDCardFragment extends BaseFragment implements View.OnClickListener
{
    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String PATH_IDCARD_FRONT_0 = BASE_PATH + "/tencent/MicroMsg/WeiXin/mmexport1532576175564.jpg";
    private static final String PATH_IDCARD_FRONT_90 = BASE_PATH + "/tencent/MicroMsg/WeiXin/IMG_20180808_171947.jpg";
    private static final String PATH_IDCARD_FRONT_180 = BASE_PATH + "/tencent/MicroMsg/WeiXin/IMG_20180808_174229.jpg";
    private static final String PATH_IDCARD_FRONT_270 = BASE_PATH + "/tencent/MicroMsg/WeiXin/IMG_20180808_174451.jpg";
    private static final String PATH_IDCARD_BACK = BASE_PATH + "/tencent/MicroMsg/WeiXin/mmexport1532593355508.jpg";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btnIDCardFront;
    private Button btnIDCardBack;

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
    public static IDCardFragment newInstance(String param1, String param2)
    {
        IDCardFragment fragment = new IDCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentListener)
            mListener = (OnFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnFragmentAttachListener");
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
        View root = inflater.inflate(R.layout.fragment_idcard, container, false);
        btnIDCardBack = root.findViewById(R.id.faceid_orc_idcard_back_v1);
        btnIDCardBack.setOnClickListener(this);
        btnIDCardFront = root.findViewById(R.id.faceid_orc_idcard_front_v1);
        btnIDCardFront.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.faceid_orc_idcard_back_v1)
        {
            File idcard = new File(PATH_IDCARD_FRONT_0);
            FaceIDApi.getInstance().IDCardOCR_V1(idcard, "1");
        }
        else if (i == R.id.faceid_orc_idcard_front_v1)
        {
            File idcard = new File(PATH_IDCARD_FRONT_0);
            FaceIDApi.getInstance().IDCardOCR_V1(idcard, "1");
        }
        if (mListener != null)
            mListener.OnButtonPressed(v.getId());
    }
}
