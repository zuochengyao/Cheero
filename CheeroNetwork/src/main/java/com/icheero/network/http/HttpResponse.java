package com.icheero.network.http;

import com.icheero.network.http.encapsulation.IConvert;
import com.icheero.network.listener.IResponseListener;
import com.icheero.util.Common;

import java.lang.reflect.Type;

public class HttpResponse// implements IResponseListener<String>
{
    private IResponseListener mResponse;
    private IConvert mConvert;

    public HttpResponse(IResponseListener response, IConvert convert)
    {
        this.mResponse = response;
        this.mConvert = convert;
    }

    public void onSuccess(String contentType, String data)
    {
        if (mConvert != null && mConvert.isSupportParse(contentType))
        {
            Type type = Common.getGenericInterfaceType(mResponse.getClass());
            if (type != null)
            {
                Object obj = mConvert.parse(data, type);
                mResponse.onSuccess(obj);
            }
            else
                mResponse.onSuccess(data);
        }
        else
            mResponse.onSuccess(data);
    }

    public void onFailure(int errorCode, String errorMessage)
    {
        mResponse.onFailure(errorCode, errorMessage);
    }
}
