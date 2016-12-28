package com.home.dab.datum.demo.crypto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.dab.datum.R;

public class Crypto extends AppCompatActivity {
    private EditText mInput;
    private TextView mTvShow;
    private int encryptFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);
        mInput = (EditText) findViewById(R.id.et_crypto);
        mTvShow = (TextView) findViewById(R.id.tv_crypto_show);
    }

    public void md5(View view) {
        String plaintext = mInput.getText().toString();
        String md5Value = EncryptionFactory.getMD5Value(plaintext);
        mTvShow.setText(md5Value);
        encryptFormat = 1;
    }

    public void sha256(View view) {
        String plaintext = mInput.getText().toString();
        String sha256Value = EncryptionFactory.SHA256Encrypt(plaintext);
        mTvShow.setText(sha256Value);
        encryptFormat = 2;
    }

    public void rsa(View view) {
        String plaintext = mInput.getText().toString();
        String rsaEncrypt = EncryptionFactory.getRSAEncrypt(plaintext);
        mTvShow.setText(rsaEncrypt);
        encryptFormat = 3;
    }
    public void aes(View view) {
        String plaintext = mInput.getText().toString();
        String rsaEncrypt = null;
        try {
            rsaEncrypt = EncryptionFactory.getAESEncrypt(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTvShow.setText(rsaEncrypt);
        encryptFormat = 4;
    }
    public void decode(View view) {
        String cipherText = mTvShow.getText().toString();
        String decode = "";
        switch (encryptFormat) {
            case 1:
            case 2:
                Toast.makeText(this, "这种加密方式不支持解密", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                try {
                    decode = EncryptionFactory.getRSADecrypt(cipherText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    decode = EncryptionFactory.getAESDecrypt(cipherText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        mTvShow.setText(decode);
    }

}
