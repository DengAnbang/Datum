package com.home.dab.datum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.home.dab.datum.demo.download.Download;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 下载
     * @param view
     */
    public void download(View view) {
        startActivity(new Intent(MainActivity.this, Download.class));
    }
}
