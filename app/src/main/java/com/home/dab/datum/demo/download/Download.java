package com.home.dab.datum.demo.download;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.home.dab.datum.Constant;
import com.home.dab.datum.R;
import com.home.dab.datum.demo.download.download.DownloadInfo;
import com.home.dab.datum.demo.download.download.IDownloadCallback;
import com.home.dab.datum.net.NetClass;
import com.home.dab.datum.tool.SPTool;

import java.io.File;

import static com.home.dab.datum.Constant.fileName;
import static com.home.dab.datum.Constant.fileStoreDir;


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
        NetClass.getInstance().downloadFile(Constant.DOWNLOAD_URL, fileStoreDir, fileName, new IDownloadCallback() {
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
        File dir = new File(fileStoreDir);
        File file = new File(dir, fileName);
        if (dir.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            this.startActivity(intent);
        } else {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
