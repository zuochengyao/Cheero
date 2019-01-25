package com.icheero.sdk.core.api;

import com.icheero.network.http.HttpApi;
import com.icheero.network.http.HttpRequest;
import com.icheero.network.http.HttpResponse;
import com.icheero.network.http.encapsulation.HttpMethod;
import com.icheero.network.http.implement.convert.JsonConvert;
import com.icheero.network.http.implement.entity.MultipartEntity;
import com.icheero.network.listener.IResponseListener;
import com.icheero.sdk.core.manager.HttpManager;

import java.io.File;

public class FaceIDApi extends HttpApi
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
        HttpRequest request = newRequest(url, HttpMethod.POST, entity, MEDIA_TYPE_MULTIPART, new HttpResponse(response, new JsonConvert()));
        // Do request
        HttpManager.getInstance().enqueue(request);
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
        HttpRequest request = newRequest(url, HttpMethod.POST, entity, MEDIA_TYPE_MULTIPART, new HttpResponse(response, new JsonConvert()));
        // Create HttpHeader
        request.getHeader().setConnection("Keep-Alive");
        request.getHeader().setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
        request.getHeader().setContentType("multipart/form-data; boundary=" + entity.getBoundary());
        request.getHeader().setCharset("UTF-8");
        // Do request
        HttpManager.getInstance().enqueue(request);
    }

    public static void detect(String url, String key, String secret, byte[] image, String multiOrientedDetection, IResponseListener response)
    {
        // Create MultipartEntity
        MultipartEntity entity = new MultipartEntity();
        entity.addString("api_key", key);
        entity.addString("api_secret", secret);
        entity.addBinaryPart("image", image);
        entity.addString("multi_oriented_detection", multiOrientedDetection);
        // Create HttpRequest
        HttpRequest request = newRequest(url, HttpMethod.POST, entity, MEDIA_TYPE_MULTIPART, new HttpResponse(response, new JsonConvert()));
        // Create HttpHeader
        request.getHeader().setConnection("Keep-Alive");
        request.getHeader().setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
        request.getHeader().setContentType("multipart/form-data; boundary=" + entity.getBoundary());
        request.getHeader().setCharset("UTF-8");
        // Do request
        HttpManager.getInstance().enqueue(request);
    }
}
