package com.dongua.framework.view;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongua.framework.R;
import com.dongua.framework.manager.DialogManager;
import com.dongua.framework.utils.AnimUtils;

/**
 * 加载提示框
 */
public class LoadingView {
    private DialogView mLoadingView;
    private ImageView iv_dialoding;
    private TextView mText;
    private ObjectAnimator mAnimi;
    public  LoadingView(Context mContext){
        mLoadingView= DialogManager.getInstance().initView(mContext, R.layout.dialog_loding);
        iv_dialoding=mLoadingView.findViewById(R.id.iv_loding);
        mText=mLoadingView.findViewById(R.id.tv_loding_text);
        mAnimi= AnimUtils.rotation(iv_dialoding);
    }

    /**
     * 设置加载提示文本
     * @param text
     */
    public void setLodingText(String text){
        if(!TextUtils.isEmpty(text)){
            mText.setText(text);
        }
    }
    public void show(){
        mAnimi.start();
        DialogManager.getInstance().show(mLoadingView);
    }
    public void show(String text){
        mAnimi.start();
        setLodingText(text);
        DialogManager.getInstance().show(mLoadingView);
    }
    public void hide(){
        mAnimi.pause();
        DialogManager.getInstance().hide(mLoadingView);
    }
    public void setCancelable(boolean flag){
        mLoadingView.setCancelable(flag);
    }
}
