package com.icheero.sdk.core.api;

import com.icheero.network.http.HttpApi;
import com.icheero.network.http.HttpRequest;
import com.icheero.network.http.HttpResponse;
import com.icheero.network.http.encapsulation.HttpMethod;
import com.icheero.network.http.implement.HttpHeader;
import com.icheero.network.http.implement.convert.JsonConvert;
import com.icheero.network.http.implement.entity.FormEntity;
import com.icheero.network.listener.IResponseListener;
import com.icheero.sdk.core.manager.HttpManager;

public class CheeroApi extends HttpApi
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
        HttpManager.getInstance().enqueue(request);
    }

    public static void getFaceIDConfig(String url, IResponseListener response)
    {
        // Create HttpRequest
        HttpRequest request = newRequest(url, HttpMethod.POST, null, MEDIA_TYPE_NORMAL, new HttpResponse(response, new JsonConvert()));
        // Set HttpHeaders
        HttpHeader header = request.getHeader();
        header.put("CheeroHeader", "Cheero");
        // Do request
        HttpManager.getInstance().enqueue(request);
    }

    public static void checkPatchUpdate(String url, IResponseListener response)
    {
        HttpRequest request = newRequest(url, HttpMethod.POST, null, MEDIA_TYPE_NORMAL, new HttpResponse(response, new JsonConvert()));
        request.getHeader().setUserAgent("Cheero-Android");
        HttpManager.getInstance().enqueue(request);
    }
}
