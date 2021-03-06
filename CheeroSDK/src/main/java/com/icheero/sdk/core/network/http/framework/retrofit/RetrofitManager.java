package com.icheero.sdk.core.network.http.framework.retrofit;

import com.icheero.sdk.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager
{
    private static final Class TAG = RetrofitManager.class;
    private static final String URL_BAST_CLOUD = "https://www.icheero.com/";
    private static final String URL_BAST_LOCAL = "http://10.155.2.171:8080/";

    private Retrofit mRetrofit;
    private static volatile RetrofitManager mInstance;

    private RetrofitManager()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_BAST_LOCAL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

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

    public Retrofit getRetrofit()
    {
        return mRetrofit;
    }

    public void callGetResult()
    {
        mRetrofit.create(RetrofitHttpService.class)
                 .callGetResult("faceid", "get_result.action")
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

    public void doNotify()
    {
        RetrofitHttpService httpService = mRetrofit.create(RetrofitHttpService.class);
        Observable<ResponseBody> observable = httpService.callNotify("", "");
        observable.unsubscribeOn(Schedulers.io())
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<ResponseBody>()
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
