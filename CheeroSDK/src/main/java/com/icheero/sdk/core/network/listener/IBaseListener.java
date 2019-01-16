package com.icheero.sdk.core.network.listener;

public interface IBaseListener
{
    void onFailure(int errorCode, String errorMessage);
}
