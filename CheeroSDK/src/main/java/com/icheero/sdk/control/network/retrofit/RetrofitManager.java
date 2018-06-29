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
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager
{
    private static final Class TAG = RetrofitManager.class;
    private static final String URL_BAST_CLOUD = "https://www.icheero.com/";
    private static final String URL_BAST_LOCAL = "http://10.155.2.171:8080/";

    private static volatile RetrofitManager mInstance;

    private RetrofitManager()
    { }

    public static RetrofitManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (RetrofitManager.class)
            {
                if (mInstance == null)
                    mInstance = new RetrofitManager();
            }
        }
        return mInstance;
    }

    private Retrofit create(String baseUrl)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);
        return new Retrofit.Builder().baseUrl(baseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void callGetResult()
    {
        Retrofit retrofit = create(URL_BAST_LOCAL);
        RetrofitHttpService api = retrofit.create(RetrofitHttpService.class);
        api.callGetResult("faceid", "get_result.action")
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Response<ResponseBody>>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Response<ResponseBody> response)
            {
                Log.d(TAG, "onNext");
                ResponseBody body = response.body();
                try
                {
                    String str = body.string();
                    Log.i(RetrofitManager.class, str);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e)
            {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete()
            {
                Log.d(TAG, "onComplete");
            }
        });
    }

    public static void doNotify()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder().client(builder.build()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(URL_BAST_LOCAL).build();
        RetrofitHttpService httpService = retrofit.create(RetrofitHttpService.class);
        Observable<ResponseBody> observable = httpService.callNotify("","");
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
