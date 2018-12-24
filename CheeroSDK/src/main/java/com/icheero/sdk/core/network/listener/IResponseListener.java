package com.icheero.sdk.core.network.listener;

import com.icheero.sdk.core.network.http.api.CheeroRequest;

/**
 * Created by zuochengyao on 2018/3/6.
 */

public interface IResponseListener<T>
{
    void onSuccess(CheeroRequest request, T data);

    void onFailure(int errorCode, String errorMessage);
}
