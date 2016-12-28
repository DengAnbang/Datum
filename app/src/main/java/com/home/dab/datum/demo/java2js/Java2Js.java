package com.home.dab.datum.demo.java2js;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.home.dab.datum.R;

public class Java2Js extends AppCompatActivity {
    private WebView mWebView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java2_js);
        mTextView = (TextView) findViewById(R.id.tv_show_url);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        String path = "file:///android_asset/html.html";
        mWebView.loadUrl(path);
//        mWebView.addJavascriptInterface(new MyJavascriptInterface(), "imageClick");
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.addJavascriptInterface(new MyJavascriptInterface(), "upperCaseJava");

    }


    /**
     * 遍历 <img> 标签, 添加图片点击事件, 将图片 Url 地址回调给 Java 方法
     */
    private void addImageClickListner() {
        //给页面所有的img添加点击事件
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imageClick.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");

    }

    public void html(View view) {

        //java执行js代码
        mWebView.loadUrl("javascript:\n" +
                "        (\n" +
                "\t\t\n" +
                "\t\tfunction upperCaseJs() {\n" +
                "\t\tvar x=document.getElementById(\"fname\");\n" +
                "\t\tx.value=x.value.toUpperCase();\n" +
                "\t\t}\n" +
                "\t\n" +
                "\t\t)()");
    }


    public class MyJavascriptInterface {

        public MyJavascriptInterface() {

        }

        @android.webkit.JavascriptInterface
        public void upperCaseJava(String upperCase) {
            Log.e("imageUrl", upperCase);
            runOnUiThread(() -> mTextView.append(upperCase + "\n"));
            // TODO 获取图片地址后, 通过原生控件 ImageView 展示, 添加缩放、保存等功能
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            return super.shouldOverrideUrlLoading(view, request);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            addImageClickListner();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
