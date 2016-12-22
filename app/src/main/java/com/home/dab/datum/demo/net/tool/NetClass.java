package com.home.dab.datum.demo.net.tool;

import android.util.Log;

import com.home.dab.datum.demo.net.download.DownloadInfo;
import com.home.dab.datum.demo.net.download.DownloadTool;
import com.home.dab.datum.demo.net.download.IDownloadCallback;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;




/**
 * Created by DAB on 2016/12/7 09:18.
 *
 */

public class NetClass {
    private static final String TAG = "NetClass";
    private static volatile NetClass instance;
    public DownloadInfo mInfo;

    private NetClass() {

    }

    public static NetClass getInstance() {
        if (instance == null) {
            synchronized (NetClass.class) {
                if (instance == null) {
                    instance = new NetClass();
                }
            }
        }
        return instance;
    }

    public Disposable mDisposable;


    public void downloadFile(String url, String fileStoreDir, String fileName, IDownloadCallback downloadCallback) {
        DownloadInfo downloadInfo = getDownloadInfo(url);
        ApiService apiService = ApiServiceFactory.getDownloadApiService(downloadCallback);
        Observable<ResponseBody> responseBodyObservable;
        if (downloadInfo == null) {
            responseBodyObservable = apiService.download(url)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.io())
                    .map(responseBody -> {
                        DownloadTool.saveFile(0, responseBody.byteStream()
                                , fileStoreDir, fileName);
                        return responseBody;
                    });
        } else {
            responseBodyObservable = apiService.download("bytes=" + downloadInfo.getBytesReaded() + "-", downloadInfo.getUrl())
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(responseBody -> {
                        DownloadTool.saveFile(downloadInfo.getBytesReaded(), responseBody.byteStream()
                                , fileStoreDir, fileName);
                        Log.e(TAG, "getThreadDownload: 完成" + downloadInfo.getBytesReaded());
                        return responseBody;
                    });
        }

        responseBodyObservable.subscribe(new Observer<ResponseBody>() {
            Disposable d;
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.e(TAG, "onSubscribe: ");
                            mDisposable = d;
                            this.d = d;
                        }

                        @Override
                        public void onNext(ResponseBody value) {
                            Log.e(TAG, "onNext:sada ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            d.dispose();
                            Log.e(TAG, "onComplete: ");
                        }
                    });

    }

    private DownloadInfo getDownloadInfo(String url) {
        return mInfo;
    }


    public void login() {
        ApiServiceFactory.getEncryptApiService().login("mobile", "password", "data")
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResultContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultContent value) {
                        Log.e(TAG, "onNext: "+value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " );
                    }
                });
    }
}
