package com.home.dab.datum.demo.download.download;


/**
 * Created by DAB on 2016/12/9 10:12.
 * 保存了下载的信息
 */

public class DownloadInfo {

    private String savePath;
    private String url;
    private long contentLength;
    private long bytesReaded;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getBytesReaded() {
        return bytesReaded;
    }

    public void setBytesReaded(long bytesReaded) {
        this.bytesReaded = bytesReaded;
    }
}
