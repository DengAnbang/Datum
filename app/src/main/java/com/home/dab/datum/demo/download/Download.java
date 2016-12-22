package com.home.dab.datum.demo.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.home.dab.datum.Constant;
import com.home.dab.datum.R;
import com.home.dab.datum.demo.net.download.DownloadInfo;
import com.home.dab.datum.demo.net.tool.NetClass;
import com.home.dab.datum.demo.net.download.IDownloadCallback;
import com.home.dab.datum.tool.SPTool;


public class Download extends AppCompatActivity {
    private static final String TAG = "Download";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
    }

    public void download(View view) {
        NetClass.getInstance().downloadFile(Constant.DOWNLOAD_URL, Constant.fileStoreDir, Constant.fileName, new IDownloadCallback() {
            @Override
            public void onProgressChange(long progress, long total) {
                Log.e(TAG, "onProgressChange: " + progress + "****" + total);

            }

            @Override
            public void onPauseDownload(DownloadInfo info) {
                Log.e(TAG, "onPauseDownload: " + info.getBytesReaded());
                SPTool.saveObject(info.getUrl(),info);
            }


        });
    }

    /**
     * 暂停
     * @param view
     */
    public void pause(View view) {
        NetClass.getInstance().mDisposable.dispose();
    }
}
