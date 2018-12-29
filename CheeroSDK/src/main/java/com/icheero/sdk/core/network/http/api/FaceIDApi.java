package com.icheero.sdk.core.network.http.api;

import com.icheero.sdk.base.BaseApi;
import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.HttpRequestEngine;
import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.implement.convert.JsonConvert;
import com.icheero.sdk.core.network.http.implement.entity.MultipartEntity;
import com.icheero.sdk.core.network.listener.IResponseListener;

import java.io.File;

public class FaceIDApi extends BaseApi
{
    public static void idCardOCR_V2(String url, String key, String secret, File image, String returnPortrait, IResponseListener response)
    {
        // Create MultipartEntity
        MultipartEntity entity = new MultipartEntity();
        entity.addString("api_key", key);
        entity.addString("api_secret", secret);
        entity.addFilePart("image", image);
        entity.addString("return_portrait", returnPortrait);
        // Create HttpRequest
        HttpRequest request = newRequest(url, HttpMethod.POST, entity, MEDIA_TYPE_NORMAL, new HttpResponse(response, new JsonConvert()));
        // Do request
        HttpRequestEngine.getInstance().enqueue(request);
    }

    public static void detect(String url, String key, String secret, File image, String multiOrientedDetection, IResponseListener response)
    {
        // Create MultipartEntity
        MultipartEntity entity = new MultipartEntity();
        entity.addString("api_key", key);
        entity.addString("api_secret", secret);
        entity.addFilePart("image", image);
        entity.addString("multi_oriented_detection", multiOrientedDetection);
        // Create HttpRequest
        HttpRequest request = newRequest(url, HttpMethod.POST, entity, MEDIA_TYPE_NORMAL, new HttpResponse(response, new JsonConvert()));
        // Do request
        HttpRequestEngine.getInstance().enqueue(request);
    }
}
