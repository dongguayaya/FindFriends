package com.dongua.findfriends.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dongua.framework.cloud.CloudManager;
import com.dongua.framework.entity.Constants;
import com.dongua.framework.utils.LogUtils;
import com.dongua.framework.utils.SpUtils;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

public class CloudService extends Service {
    public CloudService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        linkCloudServer();
    }

    private void linkCloudServer() {
        //获取token
        String token=SpUtils.getInstance().getString(Constants.SP_TOKEN,"");
        LogUtils.e("token:"+token);
        //连接服务
        CloudManager.getInstance().connect(token);
        //接受消息
        CloudManager.getInstance().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                LogUtils.i("message:"+message);
                return false;
            }
        });
    }
}
