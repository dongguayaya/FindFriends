package com.dongua.framework;

import android.content.Context;

import com.dongua.framework.manager.BmobManager;
import com.dongua.framework.utils.SpUtils;

/**
 * FileName:Framework
 * Profile:Framework入口
 *
 */
public class Framework {
    private volatile static Framework mFramework;
    private Framework(){

    }
    public static Framework getFramework(){
        if(mFramework==null){
            synchronized (Framework.class){
                if(mFramework==null){
                    mFramework=new Framework();
                }
            }
        }
        return mFramework;
    }
    public void initFramework(Context mContext){
        SpUtils.getInstance().initSp(mContext);
        BmobManager.getInstance().initBmob(mContext);
    }
}
