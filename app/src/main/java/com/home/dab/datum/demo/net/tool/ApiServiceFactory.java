package com.home.dab.datum.demo.net.tool;

import android.util.Log;

import com.home.dab.datum.demo.net.download.DownloadResponseBody;
import com.home.dab.datum.demo.net.download.IDownloadCallback;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.home.dab.datum.Constant.baseEncryptUrl;
import static com.home.dab.datum.Constant.baseUrl;

/**
 * Created by DAB on 2016/12/21 09:03.
 */

public class ApiServiceFactory {
    private static final String TAG = "ApiServiceFactory";


    public static ApiService getEncryptApiService() {
        return new Retrofit.Builder()
                .baseUrl(baseEncryptUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getDecodeOkHttpClient())
                .build()
                .create(ApiService.class);
    }

    /**
     * 获取一个下载的ApiService
     * @param downloadCallback
     * @return
     */
    public static ApiService getDownloadApiService(IDownloadCallback downloadCallback) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(getDownloadOkHttpClient(downloadCallback))
                .build()
                .create(ApiService.class);
    }

    /**
     * 获取解密的OkHttpClient
     * @return
     */
    private static OkHttpClient getDecodeOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.e(TAG, "intercept: "+request.url().toString());
                        //                        String string = proceed.body().string();

                        return chain.proceed(request);
                    }
                })
                .build()
                ;
    }

    /**
     * 获取一个下载时候的OkHttpClient
     * @param downloadCallback
     * @return
     */
    private static OkHttpClient getDownloadOkHttpClient(IDownloadCallback downloadCallback) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(chain.request()).newBuilder()
                        .body(new DownloadResponseBody(chain.proceed(chain.request()),downloadCallback))
                        .build()).build();
    }
}
