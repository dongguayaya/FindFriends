package com.dongua.findfriends;

import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.dongua.framework.base.BaseUIActivity;
import com.dongua.framework.manager.MediaPlayerManager;
import com.dongua.framework.utils.LogUtils;

public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}