package com.icheero.sdk.core.network.http.api;

import com.icheero.sdk.core.network.http.encapsulation.IConvert;
import com.icheero.sdk.core.network.listener.IResponseListener;

import java.util.List;

public abstract class WrapperResponse implements IResponseListener<String>
{
    private IResponseListener mResponse;
    private List<IConvert> mConvertList;

    public WrapperResponse(IResponseListener response, List<IConvert> convertList)
    {
        this.mResponse = response;
        this.mConvertList = convertList;
    }

    @Override
    public void onSuccess(CheeroRequest request, String data)
    {

    }

    @Override
    public void onFailure(int errorCode, String errorMessage)
    {

    }
}
