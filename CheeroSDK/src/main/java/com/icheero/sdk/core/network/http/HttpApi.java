package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IConvert;
import com.icheero.sdk.core.network.http.implement.convert.JsonConvert;

import java.util.ArrayList;
import java.util.List;

public class HttpApi
{
    public static final String ENCODING_UTF8 = "utf-8";
    /**
     * "com.icheero.application/x-www-form-urlencoded"，是默认的MIME内容编码类型，一般可以用于所有的情况，但是在传输比较大的二进制或者文本数据时效率低。
     * 这时候应该使用"multipart/form-data"。如上传文件或者二进制数据和非ASCII数据。
     */
    public static final String MEDIA_TYPE_NORMAL = "application/x-www-form-urlencoded;charset=utf-8";
    /**
     * 既可以提交普通键值对，也可以提交(多个)文件键值对。
     */
    public static final String MEDIA_TYPE_MULTIPART = "multipart/form-data;charset=utf-8";
    /**
     * 只能提交二进制，而且只能提交一个二进制，如果提交文件的话，只能提交一个文件,后台接收参数只能有一个，而且只能是流（或者字节数组）
     */
    public static final String MEDIA_TYPE_STREAM = "application/octet-stream";
    /**
     * 只能提交文本参数和字符集
     */
    public static final String MEDIA_TYPE_TEXT = "text/plain;charset=utf-8";
    /**
     * 只能提交json格式参数
     */
    public static final String MEDIA_TYPE_JSON = "application/json;charset=utf-8";

    private static final List<IConvert> mConvertList = new ArrayList<>();

    static
    {
        mConvertList.add(new JsonConvert());
    }

    protected static HttpRequest newRequest(String url, HttpMethod method, AbstractHttpEntity entity, String mediaType, HttpResponse response)
    {
        HttpRequest request = new HttpRequest();
        request.setUrl(url);
        request.setMethod(method);
        request.setData(entity);
        request.setMediaType(mediaType);
        request.setResponse(response);
        return request;
    }
}
