package com.icheero.network.listener;

/**
 * Created by zuochengyao on 2018/3/6.
 */

public interface IResponseListener<T> extends IBaseListener
{
    void onSuccess(T data);
}
