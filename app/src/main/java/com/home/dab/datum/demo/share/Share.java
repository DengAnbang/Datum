package com.home.dab.datum.demo.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.home.dab.datum.R;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import static com.home.dab.datum.Constant.APP_KEY_QQ;
import static com.home.dab.datum.Constant.APP_KEY_SINA;
import static com.home.dab.datum.Constant.APP_KEY_WX;
import static com.home.dab.datum.Constant.APP_SECRET_QQ;
import static com.home.dab.datum.Constant.APP_SECRET_SINA;
import static com.home.dab.datum.Constant.APP_SECRET_WX;

/**
 * Created by DAB on 2016/9/20 11:06.
 */
public class Share {
    {
        PlatformConfig.setWeixin(APP_KEY_WX, APP_SECRET_WX);
        PlatformConfig.setQQZone(APP_KEY_QQ, APP_SECRET_QQ);
        PlatformConfig.setSinaWeibo(APP_KEY_SINA, APP_SECRET_SINA);
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/calback";
        Config.DEBUG = true;
    }
    private static final String TAG = "Share";
    private UMShareListener mUMShareListener;

    public Share( UMShareListener umShareListener) {
        mUMShareListener = umShareListener;
    }

    public void share(Activity activity,SHARE_MEDIA share_media) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
        UMImage image = new UMImage(activity, bitmap);
        ShareAction shareAction = new ShareAction(activity)
                .setPlatform(share_media)
                .setCallback(mUMShareListener)
                .withText("Hello,我的分享!")
                .withTargetUrl("http://www.baidu.com")
                .withMedia(image);
        shareAction.share();
    }


}
