package com.icheero.sdk.core.network.listener;

/**
 * Created by zuochengyao on 2018/3/6.
 */

public interface IResponseListener<T>
{
    void onSuccess(T data);

    void onFailure(int errorCode, String errorMessage);
}
