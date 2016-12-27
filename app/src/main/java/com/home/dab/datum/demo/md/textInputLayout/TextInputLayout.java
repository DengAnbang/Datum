package com.home.dab.datum.demo.md.textInputLayout;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.dab.datum.R;

public class TextInputLayout extends AppCompatActivity {
    android.support.design.widget.TextInputLayout mTextInputLayout,mTextInputLayoutPwd;
    TextInputEditText mTextInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);
        mTextInputLayout = (android.support.design.widget.TextInputLayout) findViewById(R.id.til_phone);
        mTextInputEditText = (TextInputEditText) findViewById(R.id.tie_phone);
        mTextInputLayoutPwd = (android.support.design.widget.TextInputLayout) findViewById(R.id.til_password);
        mTextInputLayout.setCounterEnabled(true);
        mTextInputLayout.setCounterMaxLength(11);

    }

    public void sure(View view) {
        if (mTextInputEditText.getText().toString().length() != 11) {
            mTextInputLayout.setError("格式错误");
        } else {
            mTextInputLayout.setErrorEnabled(false);
        }
    }
}
