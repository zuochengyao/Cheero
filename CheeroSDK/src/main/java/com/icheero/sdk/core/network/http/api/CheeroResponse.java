package com.icheero.sdk.core.network.http.api;

public interface CheeroResponse<T>
{
    void success(CheeroRequest request, T data);

    void failure(int errorCode, String errorMessage);
}
