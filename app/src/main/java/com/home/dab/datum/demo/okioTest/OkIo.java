package com.home.dab.datum.demo.okioTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.home.dab.datum.R;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static com.home.dab.datum.Constant.fileStoreDir;

public class OkIo extends AppCompatActivity {
    private static final String TAG = "OkIo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_io);
    }

    public void read(View view) {
        BufferedSource bufferedSource = null;
        File file = new File(fileStoreDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(file, "test.txt");
        try {
            Source source = Okio.source(file1);

            bufferedSource= Okio.buffer(source);
            String s = bufferedSource.readUtf8();
            Log.e(TAG, "read: " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedSource != null) {
                try {
                    bufferedSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void write(View view) {
        BufferedSink buffer = null;
        File file = new File(fileStoreDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(file, "test.txt");
        try {
            Sink sink = Okio.sink(file1);
            buffer = Okio.buffer(sink);
            buffer.writeUtf8("hhh哈哈哈哈");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
