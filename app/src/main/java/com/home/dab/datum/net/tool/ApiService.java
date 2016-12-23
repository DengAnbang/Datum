package com.home.dab.datum.net.tool;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by DAB on 2016/12/7 09:10.
 *
 */

public interface ApiService {
    @Streaming
    @GET("{fileName}")
    Observable<ResponseBody> downloadFile(@Path("fileName") String fileName);

    @Streaming
    @GET("{fileName}")
    Call<ResponseBody> loadFile(@Path("fileName") String fileName);


    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @POST("api/userlogin")
    Observable<ResultContent> login(@Query("mobile") String mobile,
                                    @Query("password") String password,
                                    @Query("data") String data);
}
