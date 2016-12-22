package com.home.dab.datum.demo.net.tool;

import android.util.Log;

import com.home.dab.datum.demo.net.download.DownloadInfo;
import com.home.dab.datum.demo.net.download.DownloadTool;
import com.home.dab.datum.demo.net.download.IDownloadCallback;
import com.home.dab.datum.tool.SPTool;

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
        Observable<ResponseBody> responseBodyObservable;
//        ApiService apiService = ApiServiceFactory.getDownloadApiService(downloadCallback, downloadInfo);
        if (downloadInfo == null) {
            Log.e(TAG, "downloadFile: null");
            downloadInfo = new DownloadInfo();
            downloadInfo.setSavePath(fileStoreDir);
            downloadInfo.setUrl(url);
            downloadInfo.setContentLength(1);
            responseBodyObservable = ApiServiceFactory.getDownloadApiService(downloadCallback, downloadInfo).download(url);
        } else {
            Log.e(TAG, "downloadFile: no null");
            responseBodyObservable = ApiServiceFactory.getDownloadApiService(downloadCallback, downloadInfo).download("bytes=" + downloadInfo.getBytesReaded() + "-", downloadInfo.getUrl());
        }

        DownloadInfo finalDownloadInfo = downloadInfo;
        responseBodyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(responseBody -> {
                    DownloadTool.saveFile(finalDownloadInfo.getBytesReaded(), responseBody.byteStream()
                                , fileStoreDir, fileName);
                    Log.e(TAG, "getThreadDownload: 完成" + finalDownloadInfo.getBytesReaded());
                        return responseBody;
                })

                .subscribe(new Observer<ResponseBody>() {
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
                            //下载完成,则清空保存的下载状态
                            SPTool.remove(url);
                            Log.e(TAG, "onComplete: ");
                        }
                    });

    }

    /**
     * 读取数据库里上次下载的进度,如果没有下载,就为null
     *
     * @param url
     * @return
     */
    private DownloadInfo getDownloadInfo(String url) {
        return SPTool.getObject(url, DownloadInfo.class);
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
