package com.dongua.findfriends;

import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.dongua.framework.base.BaseUIActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.bmob.IMUser;
import com.dongua.framework.manager.MediaPlayerManager;
import com.dongua.framework.utils.LogUtils;

import java.util.List;

public class MainActivity extends BaseUIActivity {

    //申请运行时的权限
    private static final int PERSSION_REQUEST_CODE=1000;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!checkWindowPermissions()){
            requestWindowPermissions(1001);
        }
        requestPermiss();

    }

    /**
     * 请求权限
     */
    private void requestPermiss() {
        request(PERSSION_REQUEST_CODE, new OnPermissionsResult() {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnFail(List<String> noPermissions) {

            }
        });
    }
}