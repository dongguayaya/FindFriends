package com.dongua.framework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Bmob管理类
 */
public class BmobManager {
    /**
     * 单例封装
     */
    private static final String BMOB_SDK_ID="BMOB_SDK_ID";
    private volatile static BmobManager mInstance=null;
    private BmobManager(){

    }
    public static BmobManager getInstance(){
        if(mInstance==null){
            synchronized (BmobManager.class){
                if (mInstance==null){
                    mInstance=new BmobManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化bmob
     * @param mContext
     */
    public void initBmob(Context mContext){
        Bmob.initialize(mContext, BMOB_SDK_ID);
    }

    /**
     * 判断是否登录
     * @return
     */
    public boolean isLogin(){
        return BmobUser.isLogin();
    }
    /**
     * 获取本地对象
     * @return
     */
    public IMUser getUser(){
        return BmobUser.getCurrentUser(IMUser.class);
    }

    /**
     * 发送短信验证码
     * @param phone 手机号码
     * @param listener 回调
     */
    public void requestSMS(String phone, QueryListener<Integer> listener){
        BmobSMS.requestSMSCode(phone,"",listener);
    }

    /**
     * 通过手机号码注册或者登录
     * @param phone 手机号
     * @param code 短信验证码
     *@param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone,String code,LogInListener<IMUser> listener){
        BmobUser.signOrLoginByMobilePhone(phone,code,listener);
    }
}
