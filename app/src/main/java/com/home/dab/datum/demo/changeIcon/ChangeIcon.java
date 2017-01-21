package com.home.dab.datum.demo.changeIcon;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.home.dab.datum.R;

public class ChangeIcon extends AppCompatActivity {
    private ComponentName mDefault;
    private ComponentName mOlmeca, mSvgIauncher;
    private PackageManager mPackageManager;
    private static final String TAG = "ChangeIcon";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_icon);

        mDefault = new ComponentName(getBaseContext(),
                "com.home.dab.datum.MainActivity"
        );
        mOlmeca = new ComponentName(getBaseContext(),
                "com.home.dab.datum.svg_olmeca"
        );
        mSvgIauncher = new ComponentName(getBaseContext(),
                "com.home.dab.datum.svg_launcher"
        );
        mPackageManager = getApplicationContext().getPackageManager();
        Log.e("sadasdasd", "onCreate: " +mDefault+"****"+mOlmeca+"***"+mSvgIauncher);
    }

    public void svgLauncher(View view) {
        disableComponent(mDefault);
        disableComponent(mOlmeca);
        enableComponent(mSvgIauncher);
    }

    public void olmeca(View view) {
        disableComponent(mDefault);
        disableComponent(mSvgIauncher);
        enableComponent(mOlmeca);
    }

    public void defaultIcon(View view) {
        disableComponent(mOlmeca);
        disableComponent(mSvgIauncher);
        enableComponent(mDefault);
    }

    /**
     * 开启这个activity-alias
     * @param componentName
     */
    private void enableComponent(ComponentName componentName) {
        mPackageManager.setComponentEnabledSetting(componentName
                , PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
    /**
     * 禁用这个activity-alias
     * @param componentName
     */
    private void disableComponent(ComponentName componentName) {
        mPackageManager.setComponentEnabledSetting(componentName
                , PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

}
