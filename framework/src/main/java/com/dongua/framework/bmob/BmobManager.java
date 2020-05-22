package com.dongua.framework.bmob;

import android.content.Context;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Bmob管理类
 */
public class BmobManager {
    /**
     * 单例封装
     */
    private static final String BMOB_SDK_ID="884ea29111708743552a3ac72f2836ed";
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
    public void uploadFirstPhoto(final String nikeName, File file, final OnUploadPhotoListener listener){
        /**
         * 上传文件拿到地址
         * 更新用户信息
         */
        final IMUser imUser=getUser();
        final BmobFile bmobFile=new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //上传成功
                    imUser.setNickName(nikeName);
                    imUser.setPhoto(bmobFile.getFileUrl());

                    imUser.setTokenNickName(nikeName);
                    imUser.setTokenPhoto(bmobFile.getFileUrl());

                    //更新用户信息
                    imUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                listener.OnUpdateDone();
                            }else{
                                listener.OnUpdateFail(e);
                            }
                        }
                    });
                }else{
                    listener.OnUpdateFail(e);
                }
            }
        });
    }
    public interface  OnUploadPhotoListener{
        void OnUpdateDone();
        void OnUpdateFail(BmobException e);
    }

    /**
     * 根据电话号码查询用户
     * @param phone
     */
    public void queryPhoneUser(String phone, FindListener<IMUser> listener){
        baseQuery("mobilePhoneNumber",phone,listener);
    }
    public void baseQuery(String key,String values,FindListener<IMUser> listener){
        BmobQuery<IMUser> query=new BmobQuery<>();
        query.addWhereEqualTo(key,values);
        query.findObjects(listener);
    }

}
