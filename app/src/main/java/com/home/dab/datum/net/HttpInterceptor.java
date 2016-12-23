package com.home.dab.datum.net;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpEngine;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by DAB on 2016/12/5 09:01.
 * 网络请求拦截
 */

public class HttpInterceptor implements Interceptor {
    private static final String TAG = "HttpInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static boolean isShowLog = true;

    public static void setIsShowLog(boolean isShowLog) {
        HttpInterceptor.isShowLog = isShowLog;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "请求--> \nmethod:" + request.method() + "\nurl:" + request.url() + "\nprotocol:" + protocol;
        stringBuffer.append(requestStartMessage);
        if (requestBody != null) {
            if (requestBody.contentType() != null) {
                String contentType = "\nContent-Type: " + requestBody.contentType();
                stringBuffer.append(contentType);
            }
            if (requestBody.contentLength() != -1) {
                String contentLength = "\nContent-Length: " + requestBody.contentLength();
                stringBuffer.append(contentLength);
            }
        }

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (HttpEngine.hasBody(response)) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                    return response;
                }
            }
            String s = "\n请求结果为:<--" + buffer.clone().readString(charset);
            stringBuffer.append(s);
            if (isShowLog)
            Log.e(TAG, NetUtlis.decodeUnicode(stringBuffer.toString()));
        }
        return response;
    }


}
