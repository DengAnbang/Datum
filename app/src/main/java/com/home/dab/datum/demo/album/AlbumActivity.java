package com.home.dab.datum.demo.album;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.home.dab.datum.R;
import com.home.dab.gallery.ScanPhoto;
import com.home.dab.gallery.bean.AlbumFolder;

import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private static final String TAG = "AlbumActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Log.e(TAG, "onCreate: " );
        List<AlbumFolder> scan = ScanPhoto.scan(this);
        Log.e(TAG, "onCreate: " + scan.size());
    }
}
