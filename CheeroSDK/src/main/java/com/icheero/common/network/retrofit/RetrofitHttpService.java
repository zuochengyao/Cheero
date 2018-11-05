package com.icheero.common.network.retrofit;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitHttpService
{
    @GET("{controller}/{action}")
    Observable<Response<ResponseBody>> callGetResult(@Path("controller") String controller, @Path("action") String action);

    @POST("faceid/notify.action")
    Observable<ResponseBody> callNotify(@Body String sign, @Body String data);
}
