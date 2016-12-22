package com.home.dab.datum.demo.net.download;

/**
 * Created by DAB on 2016/12/9 15:38.
 */

public interface IDownloadCallback {

    void onProgressChange(long progress, long total);

    void onPauseDownload(DownloadInfo info);
}
