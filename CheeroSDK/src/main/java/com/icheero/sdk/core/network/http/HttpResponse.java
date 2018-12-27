package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IConvert;
import com.icheero.sdk.core.network.listener.IResponseListener;
import com.icheero.sdk.util.Common;

import java.util.List;

public class HttpResponse implements IResponseListener<String>
{
    private IResponseListener mResponse;
    private List<IConvert> mConvertList;

    public HttpResponse(IResponseListener response, List<IConvert> convertList)
    {
        this.mResponse = response;
        this.mConvertList = convertList;
    }

    @Override
    public void onSuccess(HttpRequest request, String data)
    {
        for (IConvert convert : mConvertList)
        {
            if (convert.isSupportParse(request.getContentType()))
            {
                Object obj = convert.parse(data, Common.getGenericInterfaceType(mResponse.getClass()));
                mResponse.onSuccess(request, obj);
                return;
            }
        }
        mResponse.onSuccess(request, data);
    }

    @Override
    public void onFailure(int errorCode, String errorMessage)
    {
        mResponse.onFailure(errorCode, errorMessage);
    }
}
