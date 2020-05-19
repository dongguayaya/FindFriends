package com.dongua.framework.manager;

import android.content.Context;

import cn.bmob.v3.Bmob;

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
        Bmob.initialize(mContext, "BMOB_SDK_ID");

    }
}
