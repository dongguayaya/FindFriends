package com.dongua.findfriends;

import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.dongua.framework.base.BaseUIActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.bmob.IMUser;
import com.dongua.framework.manager.MediaPlayerManager;
import com.dongua.framework.utils.LogUtils;

public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IMUser imUser=BmobManager.getInstance().getUser();
        Toast.makeText(this,"imUser"+imUser.getMobilePhoneNumber(),Toast.LENGTH_SHORT).show();
    }
}