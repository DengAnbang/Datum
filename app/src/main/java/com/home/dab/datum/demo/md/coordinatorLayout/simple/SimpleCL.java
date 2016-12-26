package com.home.dab.datum.demo.md.coordinatorLayout.simple;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.home.dab.datum.R;

/**
 * 参考http://blog.csdn.net/huachao1001/article/details/51554608
 */
public class SimpleCL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_cl);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件
                                Toast.makeText(SimpleCL.this, "dasda", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }
}
