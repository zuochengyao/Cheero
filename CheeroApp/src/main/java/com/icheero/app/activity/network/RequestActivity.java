package com.icheero.app.activity.network;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.network.http.api.CheeroApi;
import com.icheero.sdk.core.network.http.api.CheeroRequest;
import com.icheero.sdk.core.network.listener.IResponseListener;
import com.icheero.sdk.util.Log;

import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Map<String, String> map = new HashMap<>();
        map.put("username", "Cheero");
        map.put("age", "28");

        findViewById(R.id.request_btn).setOnClickListener(v -> CheeroApi.helloWorld("http://10.155.2.130:8080/cheero/hello.action", map, new IResponseListener<String>()
        {
            @Override
            public void onSuccess(CheeroRequest request, String data)
            {
                Log.i(TAG, "success:[" + data + "]");
            }

            @Override
            public void onFailure(int errorCode, String errorMessage)
            {
                Log.i(TAG, "error:[" + errorCode + ":" + errorMessage + "]");
            }
        }));
    }
}
