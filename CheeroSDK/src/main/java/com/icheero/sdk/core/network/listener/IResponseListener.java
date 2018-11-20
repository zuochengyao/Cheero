package com.icheero.sdk.core.network.listener;

/**
 * Created by zuochengyao on 2018/3/6.
 */

public interface IResponseListener
{
    void onSuccess(String message);

    void onFailure(int errorCode, String errorMsg);
}
