package com.icheero.sdk.control.network.retrofit;

import com.icheero.sdk.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager
{
    private static final String URL_BAST_CLOUD = "https://www.icheero.com/";
    private static final String URL_BAST_LOCAL = "http://10.155.2.171:8080/";

    public static void doRequest()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_BAST_LOCAL).build();
        RetrofitHttpService api = retrofit.create(RetrofitHttpService.class);
        Call<ResponseBody> call = api.callGetResult("faceid", "get_result.action");
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                ResponseBody responseBody;
                int code = response.code();
                if (code != 200)
                    responseBody = response.errorBody();
                else
                    responseBody = response.body();
                try
                {
                    String body = responseBody.string();
                    Log.i(RetrofitManager.class, body);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }

    public static void doNotify()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder().client(builder.build()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(URL_BAST_LOCAL).build();
        RetrofitHttpService httpService = retrofit.create(RetrofitHttpService.class);
        Observable<ResponseBody> observable = httpService.notify(true);
        observable.unsubscribeOn(Schedulers.io()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {

            }

            @Override
            public void onNext(ResponseBody responseBody)
            {

            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onComplete()
            {

            }
        });

    }
}
