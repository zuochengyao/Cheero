package com.icheero.sdk.control.network.retrofit;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitHttpService
{
    @GET("{controller}/{action}")
    Call<ResponseBody> callGetResult(@Path("controller") String controller, @Path("action") String action);

    @POST("faceid/notify")
    Observable<ResponseBody> notify(@Body boolean b);
}
