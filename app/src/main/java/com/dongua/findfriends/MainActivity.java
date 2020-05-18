package com.dongua.findfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dongua.framework.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.i("HelloWorld");
        LogUtils.e("HelloWorld");
    }
}