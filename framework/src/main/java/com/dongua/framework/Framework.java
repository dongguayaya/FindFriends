package com.dongua.framework;

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
}