package com.home.dab.datum.demo.myView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.home.dab.datum.R;
import com.home.dab.datum.view.SlideButton;

public class MyView extends AppCompatActivity {
    private SlideButton mSlideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        mSlideButton = (SlideButton) findViewById(R.id.sb);
        mSlideButton.setOnContentClickListener(new SlideButton.OnContentClickListener() {
            @Override
            public void onRightClick(View view) {
                Toast.makeText(MyView.this, "点击了右边", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftClick(View view) {
                Toast.makeText(MyView.this, "点击了左边", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void start(View view) {
        mSlideButton.open();
    }

    public void close(View view) {
        mSlideButton.close();
    }
}
