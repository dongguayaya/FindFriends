package com.dongua.framework.cloud;

import android.content.Context;

import com.dongua.framework.utils.LogUtils;

import io.rong.imlib.RongIMClient;

public class CloudManager {
    //url
    public static final String TOKEN_URL="TOKEN_URL";
    //key
    public static final String CLOUD_KEY="CLOUD_KEY";
    public static final String CLOUD_SECRET="CLOUD_SECRET";
    private static volatile CloudManager mInstnce=null;
    private CloudManager(){

    }
    public static CloudManager getInstance(){
        if(mInstnce==null){
            synchronized (CloudManager.class){
                if(mInstnce==null){
                    mInstnce=new CloudManager();
                }
            }
        }
        return mInstnce;
    }
    public void initCloud(Context mContext){
        RongIMClient.init(mContext);
    }
    public void connect(String token){
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                LogUtils.e("Token Error");
            }

            @Override
            public void onSuccess(String s) {
                LogUtils.e("连接成功"+s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.e("连接失败"+errorCode);
            }
        });
    }

    /**
     * 断开连接
     */
    public void disconnect(){
        RongIMClient.getInstance().disconnect();
    }

    /**
     * 退出登录
     */
    public void logout(){
        RongIMClient.getInstance().logout();
    }

    /**
     * 接受消息
     * @param listener
     */
    public void setOnReceiveMessageListener(RongIMClient.OnReceiveMessageListener listener){
        RongIMClient.setOnReceiveMessageListener(listener);
    }

}
