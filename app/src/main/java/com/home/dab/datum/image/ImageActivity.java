package com.home.dab.datum.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.home.dab.datum.R;
import com.home.dab.datum.view.PinchImageView;

public class ImageActivity extends AppCompatActivity {
    private PinchImageView mPinchImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mPinchImageView = (PinchImageView) findViewById(R.id.pv);
        Glide.with(this).load("http://www.pp3.cn/uploads/allimg/111113/1F42Q917-10.jpg")
                .into(mPinchImageView);
    }
}
