package com.home.dab.datum.demo.net.download;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by DAB on 2016/12/7 15:12.
 * 自定义下载进度的ResponseBody
 */
public class DownloadResponseBody extends ResponseBody {
    private Response mResponse;
    private IDownloadCallback mDownloadCallback;
    public DownloadResponseBody(Response response, IDownloadCallback downloadCallback) {
        mResponse = response;
        mDownloadCallback = downloadCallback;
    }

    @Override
    public MediaType contentType() {
        return mResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return mResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        Log.e(TAG, "source: " );

        return Okio.buffer(new ForwardingSource(mResponse.body().source()) {
            long bytesReaded = 0;//下載的进度
            @Override
            public long read(Buffer sink, long byteCount)  {
                long bytesRead = 0;
                try {
                    bytesRead = super.read(sink, byteCount);
                    bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                    Log.e(TAG, "read: " + bytesReaded + "****" + contentLength());
                    if (mDownloadCallback != null) {
                        mDownloadCallback.onProgressChange(bytesReaded,contentLength());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "read: 结束"+bytesReaded );
                    if (mDownloadCallback != null) {
                        mDownloadCallback.onPauseDownload(bytesReaded,contentLength());
                    }

                }
                return bytesRead;
            }
        });
    }

    private static final String TAG = "DownloadResponseBody";
}
