package com.icheero.sdk.core.network.http.api;

import com.icheero.sdk.core.network.http.encapsulation.IConvert;
import com.icheero.sdk.core.network.listener.IResponseListener;
import com.icheero.sdk.util.Common;

import java.util.List;

public class CheeroResponse implements IResponseListener<String>
{
    private IResponseListener mResponse;
    private List<IConvert> mConvertList;

    public CheeroResponse(IResponseListener response, List<IConvert> convertList)
    {
        this.mResponse = response;
        this.mConvertList = convertList;
    }

    @Override
    public void onSuccess(CheeroRequest request, String data)
    {
        for (IConvert convert : mConvertList)
        {
            if (convert.isSupportParse(request.getContentType()))
            {
                Object obj = convert.parse(data, Common.getGenericInterfaceType(mResponse.getClass()));
                mResponse.onSuccess(request, obj);
                break;
            }
        }
    }

    @Override
    public void onFailure(int errorCode, String errorMessage)
    {

    }
}
