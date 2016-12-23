package com.home.dab.datum.demo.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.home.dab.datum.Constant;
import com.home.dab.datum.R;
import com.home.dab.datum.demo.download.download.DownloadInfo;
import com.home.dab.datum.demo.download.download.IDownloadCallback;
import com.home.dab.datum.net.NetClass;
import com.home.dab.datum.tool.SPTool;


public class Download extends AppCompatActivity {
    private static final String TAG = "Download";
    private TextView mTvDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mTvDownload = (TextView) findViewById(R.id.tv_download);
    }

    public void download(View view) {
        NetClass.getInstance().downloadFile(Constant.DOWNLOAD_URL, Constant.fileStoreDir, Constant.fileName, new IDownloadCallback() {
            @Override
            public void onProgressChange(long progress, long total) {
                runOnUiThread(() -> mTvDownload.setText("总大小" + total + "***下载进度:" + progress));
            }

            @Override
            public void onPauseDownload(DownloadInfo info) {
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

    public void install(View view) {

    }
}
