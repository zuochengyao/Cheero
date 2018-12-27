package com.icheero.sdk.core.network.http.api;

import com.icheero.sdk.base.BaseApi;
import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.HttpRequestEngine;
import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.listener.IResponseListener;
import com.icheero.sdk.util.Common;

import java.util.Map;

public class CheeroApi extends BaseApi
{
    public static void helloWorld(String url, Map<String, String> value, IResponseListener response)
    {
        HttpResponse cheeroResponse = new HttpResponse(response, mConvertList);
        HttpRequest request = new HttpRequest();
        request.setUrl(url);
        request.setMethod(HttpMethod.POST);
        request.setData(Common.encodeParam(value, ENCODE_UTF8));
        request.setResponse(cheeroResponse);
        request.setMediaType(MEDIA_TYPE_NORMAL);
        HttpRequestEngine.getInstance().enqueue(request);
    }
}
