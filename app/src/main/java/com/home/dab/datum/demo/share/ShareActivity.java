package com.home.dab.datum.demo.share;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.home.dab.datum.R;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ShareActivity";
    private View mViewShare;
    private PopupWindow mPopShare;
    private Button mBtnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mBtnShare = (Button) findViewById(R.id.btn_share);
        mBtnShare.setOnClickListener(this);
    }

    public void share() {
        if (mViewShare == null) {
            mViewShare = LayoutInflater.from(this).inflate(R.layout.pop_share, null);
        }
        mPopShare = new PopupWindow(mViewShare, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mViewShare.findViewById(R.id.ib_pop_share_weixin).setOnClickListener(this);
        mViewShare.findViewById(R.id.ib_pop_share_pyq).setOnClickListener(this);
        mViewShare.findViewById(R.id.ib_pop_share_weibo).setOnClickListener(this);
        mViewShare.findViewById(R.id.ib_pop_share_qq).setOnClickListener(this);
        mViewShare.findViewById(R.id.ib_pop_share_qqkj).setOnClickListener(this);
        mPopShare.setOnDismissListener(() -> {
            mPopShare = null;
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.alpha = 1f;
            getWindow().setAttributes(attributes);
        });
        mViewShare.setOnTouchListener((v, event) -> {
            if (mPopShare != null && mPopShare.isShowing()) {
                mPopShare.dismiss();
            }
            return false;
        });
        mPopShare.showAtLocation(mBtnShare, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.2f;
        getWindow().setAttributes(params);
    }

    private void share(SHARE_MEDIA qq) {
        Share share = new Share(new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(ShareActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "分享成功: ");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Log.e(TAG, "分享失败: ");
                throwable.printStackTrace();
                Toast.makeText(ShareActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Log.e(TAG, "取消分享: ");
                Toast.makeText(ShareActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
            }
        });
        share.share(this, qq);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                share();
                break;
            case R.id.ib_pop_share_weixin:
                Log.e(TAG, "onClick: " );
                share(SHARE_MEDIA.WEIXIN);
                break;
        }
    }
}
