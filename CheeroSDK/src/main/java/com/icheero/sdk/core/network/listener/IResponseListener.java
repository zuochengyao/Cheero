package com.icheero.sdk.core.network.listener;

import com.icheero.sdk.core.network.http.HttpRequest;

/**
 * Created by zuochengyao on 2018/3/6.
 */

public interface IResponseListener<T>
{
    void onSuccess(HttpRequest request, T data);

    void onFailure(int errorCode, String errorMessage);
}
