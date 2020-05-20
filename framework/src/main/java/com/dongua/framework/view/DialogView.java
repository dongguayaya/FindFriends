package com.dongua.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * 自定义提示框
 */
public class DialogView extends Dialog {
    public DialogView(Context mcontext,int layout, int style,int gravity){
        super(mcontext,style);
        setContentView(layout);
        Window window=getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity=gravity;
        window.setAttributes(layoutParams);

    }
}
