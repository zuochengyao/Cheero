package com.icheero.sdk.core.network.http.api;

import com.icheero.sdk.base.BaseApi;
import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.HttpRequestEngine;
import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.implement.HttpHeader;
import com.icheero.sdk.core.network.http.implement.convert.JsonConvert;
import com.icheero.sdk.core.network.http.implement.entity.FormEntity;
import com.icheero.sdk.core.network.listener.IResponseListener;

public class CheeroApi extends BaseApi
{
    public static void hello(String url, String username, String password, IResponseListener response)
    {
        // Create FormEntity
        FormEntity entity = new FormEntity();
        entity.addString("username", username);
        entity.addString("password", password);
        // Create HttpRequest
        HttpRequest request = newRequest(url, HttpMethod.POST, entity, MEDIA_TYPE_NORMAL, new HttpResponse(response, new JsonConvert()));
        // Set HttpHeaders
        HttpHeader header = request.getHeader();
        header.put("CheeroHeader", "Cheero");
        // Do request
        HttpRequestEngine.getInstance().enqueue(request);
    }

    public static void getFaceIDConfig(String url, IResponseListener response)
    {
        // Create HttpRequest
        HttpRequest request = newRequest(url, HttpMethod.POST, null, MEDIA_TYPE_NORMAL, new HttpResponse(response, new JsonConvert()));
        // Set HttpHeaders
        HttpHeader header = request.getHeader();
        header.put("CheeroHeader", "Cheero");
        // Do request
        HttpRequestEngine.getInstance().enqueue(request);
    }
}
