package com.dongua.findfriends.base;

import android.app.Application;

import com.dongua.framework.Framework;


/**
 * App
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Framework.getFramework().initFramework(this);
    }
}
