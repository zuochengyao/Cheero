package com.icheero.sdk.network.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 回调主线程接口
 * Created by zuochengyao on 2018/3/1.
 */

public interface OkHttpUICallback
{
    void onResponse(int id, Response response, String path);
    void onFailure(int id, Call call, IOException e);
    void onProgress(int id, long byteRW, long contentLength, boolean done);
}
