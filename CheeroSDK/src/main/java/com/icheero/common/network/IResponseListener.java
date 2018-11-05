package com.icheero.common.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zuochengyao on 2018/3/6.
 */

public interface IResponseListener
{
    void onResponse(int id, Response response, String path);
    void onFailure(int id, Call call, IOException e);
    void onProgress(int id, long byteRW, long contentLength, boolean done);
}
