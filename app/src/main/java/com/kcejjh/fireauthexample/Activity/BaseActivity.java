package com.kcejjh.fireauthexample.Activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * jhChoi - 20200918
 * 베이스가 되는 Activity 입니다.
 * */
public class BaseActivity extends AppCompatActivity {

    /**
     * jhChoi - 20200918
     * 토스트를 출력합니다.
     * */
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
