package com.icheero.app.activity.network;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.api.CheeroApi;
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
        map.put("password", "123456");

        findViewById(R.id.request_btn).setOnClickListener(v -> CheeroApi.helloWorld("http://10.155.2.130:8080/cheero/hello.action", map, new IResponseListener<Person>()
        {
            @Override
            public void onSuccess(HttpRequest request, Person data)
            {
                Log.i(TAG, "success:[" + data.toString() + "]");
            }

            @Override
            public void onFailure(int errorCode, String errorMessage)
            {
                Log.i(TAG, "error:[" + errorCode + ":" + errorMessage + "]");
            }
        }));
    }

    class Person
    {
        public int id;
        public String username;
        public String password;
        public short type;

        @Override
        public String toString()
        {
            return "Person{" +
                    "id=" + id + "," +
                    "username=" + username + "," +
                    "password=" + password + "," +
                    "type=" + type +
                    "}";
        }
    }
}
