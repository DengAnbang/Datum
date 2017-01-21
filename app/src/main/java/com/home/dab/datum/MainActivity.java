package com.home.dab.datum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.dab.datum.demo.album.AlbumActivity;
import com.home.dab.datum.demo.changeIcon.ChangeIcon;
import com.home.dab.datum.demo.crypto.Crypto;
import com.home.dab.datum.demo.download.Download;
import com.home.dab.datum.demo.java2js.Java2Js;
import com.home.dab.datum.demo.md.MaterialDesign;
import com.home.dab.datum.demo.myView.MyView;
import com.home.dab.datum.demo.okioTest.OkIo;
import com.home.dab.datum.demo.pickerView.PickerView;
import com.home.dab.datum.demo.recyclerDemo.RecyclerDemo;
import com.home.dab.datum.demo.share.ShareActivity;
import com.home.dab.datum.demo.shoppingcart.ShoppingMainActivity;
import com.home.dab.datum.demo.toolbar.ToolbarActivity;

/**
 * 微信:
 * AppID：wxdd736775d8c546a2
 * AppSecret:cb251fe019716641c7c70e8e40619957
 */
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

    public void recyclerView(View view) {
        startActivity(new Intent(MainActivity.this, RecyclerDemo.class));
    }

    public void share(View view) {
        startActivity(new Intent(MainActivity.this, ShareActivity.class));
    }

    public void myView(View view) {
        startActivity(new Intent(MainActivity.this, MyView.class));
    }

    public void okio(View view) {
        startActivity(new Intent(MainActivity.this, OkIo.class));
    }

    public void toolbar(View view) {
        startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
    }

    public void album(View view) {
        startActivity(new Intent(MainActivity.this, AlbumActivity.class));
    }

    public void picker(View view) {
        startActivity(new Intent(MainActivity.this, PickerView.class));
    }
}
