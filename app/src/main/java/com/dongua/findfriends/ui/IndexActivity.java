package com.dongua.findfriends.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.dongua.findfriends.MainActivity;
import com.dongua.findfriends.R;
import com.dongua.framework.entity.Constants;
import com.dongua.framework.utils.SpUtils;

/**
 * 1.把启动页全屏
 * 2.延迟进入主页
 * 3.根据具体逻辑是否进入主页还是引导页还是登录页
 * 4.适配刘海屏
 * index启动页
 */
public class IndexActivity extends AppCompatActivity {

    private static final int SKIP_MAIN=1000;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SKIP_MAIN:
                    startMain();
                    break;
            }
            return false;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mHandler.sendEmptyMessageDelayed(SKIP_MAIN,2*1000);
    }

    /**
     * 进入主页
     */
    private void startMain() {
        //1.判断App是否第一次启动 install->first run
        boolean isFirst=SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP,true);
        Intent intent=new Intent();
        if(isFirst){
            //跳转到引导页
            intent.setClass(this,GuideActivity.class);
            //非第一次启动
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP,false);
        }else{
            //2.如果非第一次启动、判断是否曾经登陆过
            String token=SpUtils.getInstance().getString(Constants.SP_TOKEN,"");
            if(TextUtils.isEmpty(token)){
                //跳转到登录页
                intent.setClass(this,LoginActivity.class);
            }else{
                //跳转到主页
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}