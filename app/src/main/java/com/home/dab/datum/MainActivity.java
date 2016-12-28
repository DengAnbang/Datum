package com.home.dab.datum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.dab.datum.demo.changeIcon.ChangeIcon;
import com.home.dab.datum.demo.crypto.Crypto;
import com.home.dab.datum.demo.download.Download;
import com.home.dab.datum.demo.java2js.Java2Js;
import com.home.dab.datum.demo.md.MaterialDesign;
import com.home.dab.datum.demo.shoppingcart.ShoppingMainActivity;

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

    /**
     * 更换图标
     *
     * @param view
     */
    public void changeIcon(View view) {
        startActivity(new Intent(MainActivity.this, ChangeIcon.class));
    }

    public void shoppingCart(View view) {
        startActivity(new Intent(MainActivity.this, ShoppingMainActivity.class));
    }

    public void md(View view) {
        startActivity(new Intent(MainActivity.this, MaterialDesign.class));
    }

    public void java2js(View view) {
        startActivity(new Intent(MainActivity.this, Java2Js.class));
    }

    public void crypto(View view) {
        startActivity(new Intent(MainActivity.this, Crypto.class));
    }
}
