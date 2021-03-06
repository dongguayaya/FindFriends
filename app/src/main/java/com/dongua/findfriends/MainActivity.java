package com.dongua.findfriends;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dongua.findfriends.fragment.ChatFragment;
import com.dongua.findfriends.fragment.MyFragment;
import com.dongua.findfriends.fragment.SquareFragment;
import com.dongua.findfriends.fragment.StarFragment;
import com.dongua.findfriends.service.CloudService;

import com.dongua.findfriends.ui.FirstUploadActivity;

import com.dongua.framework.base.BaseUIActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.entity.Constants;
import com.dongua.framework.gson.TokenBean;
import com.dongua.framework.java.SimulationData;
import com.dongua.framework.manager.DialogManager;

import com.dongua.framework.manager.HttpManager;
import com.dongua.framework.utils.LogUtils;

import com.dongua.framework.utils.SpUtils;
import com.dongua.framework.view.DialogView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseUIActivity implements View.OnClickListener {

    //星球
    private ImageView ivstar;
    private TextView tvstar;
    private LinearLayout llstar;
    private StarFragment mStarFragment=null;
    private FragmentTransaction mStarTransaction=null;

    //聊天
    private ImageView ivchat;
    private TextView tvchat;
    private LinearLayout llchat;
    private ChatFragment mChatFragment=null;
    private FragmentTransaction mChatTransaction=null;

    //广场
    private ImageView ivsquare;
    private TextView tvsquare;
    private LinearLayout llsquare;
    private SquareFragment mSquareFragment=null;
    private FragmentTransaction mSquareTransaction=null;

    //我的
    private ImageView ivmy;
    private TextView tvmy;
    private LinearLayout llmy;
    private MyFragment mMyFragment=null;
    private FragmentTransaction mMyTransaction=null;
    private Disposable mDisposable;

    //跳转上传的回调
    public static final int UPLOAD_REQUEST_CODE=1002;
    //申请运行时的权限
    private static final int PERSSION_REQUEST_CODE=1000;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        requestPermiss();
        ivstar=findViewById(R.id.iv_star);
        tvstar=findViewById(R.id.tv_star);
        llstar=findViewById(R.id.ll_star);
        ivsquare=findViewById(R.id.iv_square);
        tvsquare=findViewById(R.id.tv_square);
        llsquare=findViewById(R.id.ll_square);
        ivchat=findViewById(R.id.iv_chat);
        tvchat=findViewById(R.id.tv_chat);
        llchat=findViewById(R.id.ll_chat);
        ivmy=findViewById(R.id.iv_my);
        tvmy=findViewById(R.id.tv_my);
        llmy=findViewById(R.id.ll_my);

        llstar.setOnClickListener(this);
        llsquare.setOnClickListener(this);
        llchat.setOnClickListener(this);
        llmy.setOnClickListener(this);

        //设置文本
        tvstar.setText(getString(R.string.star));
        tvsquare.setText(getString(R.string.square));
        tvchat.setText(getString(R.string.chat));
        tvmy.setText(getString(R.string.my));

        initFragment();
        //切换默认的选项卡
        checkMainTab(0);
        //检查TOKEN


        //检查Token
        checkToken();
        //模拟数据
        //SimulationData.testData();


    }


    /**
     * 检查token
     */
    private void checkToken() {
        //获取Token需要三个参数，1.用户ID 2.头像地址 3.昵称
        String token= SpUtils.getInstance().getString(Constants.SP_TOKEN,"");
        if(!TextUtils.isEmpty(token)){
            //启动云服务去连接融云服务
            startCloudService();
        }else{
            //1.有三个参数
            String tokenPhoto=BmobManager.getInstance().getUser().getTokenPhoto();
            String tokenName=BmobManager.getInstance().getUser().getTokenNickName();
            if(!TextUtils.isEmpty(tokenPhoto)&&!TextUtils.isEmpty(tokenName)){
                //创建Token
                createToken();
            }else{
                //创建上传提示框
                createUploadDialog();
            }
        }
    }

    /**
     * 创建TOKEN
     */
    private void createUploadDialog() {
        final DialogView mUpLoadView=DialogManager.getInstance().initView(this,R.layout.dialog_first_upload);
        //外部点击不能消失
        mUpLoadView.setCancelable(false);
        ImageView mLoadView=mUpLoadView.findViewById(R.id.iv_go_upload);
        mLoadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.getInstance().hide(mUpLoadView);
                FirstUploadActivity.startActivity(MainActivity.this,UPLOAD_REQUEST_CODE);
            }
        });
        DialogManager.getInstance().show(mUpLoadView);
    }
    private void startCloudService(){
        LogUtils.i("startCloudService");
        //启动云服务去连接融云服务
        startService(new Intent(this,CloudService.class));

    }

    /**
     * 创建上传提示框
     */
    private void createToken() {
        LogUtils.i("createToken");
        /**
         * 获取融云Token
         */
        final HashMap<String,String> map=new HashMap<>();
        map.put("userId",BmobManager.getInstance().getUser().getObjectId());
        map.put("name",BmobManager.getInstance().getUser().getTokenNickName());
        map.put("portraitUri",BmobManager.getInstance().getUser().getTokenPhoto());

        //通过Okhttp请求Token
        mDisposable=Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //执行请求过程
                String json=HttpManager.getInstance().postCloudToken(map);
                emitter.onNext(json);
                emitter.onComplete();
            }
            //线程调度
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.i("s"+s);
                        parsingCloudToken(s);
                    }
                });
    }

    /**
     * 解析token
     * @param s
     */
    private void parsingCloudToken(String s) {
        LogUtils.i("parsingCloudToken");
        TokenBean tokenBean=new Gson().fromJson(s, TokenBean.class);
        if(tokenBean.getCode()==200){
            if(!TextUtils.isEmpty(tokenBean.getToken())){
                //保存token
                SpUtils.getInstance().putString(Constants.SP_TOKEN,tokenBean.getToken());
                startCloudService();
            }
        }
    }


    /**
     * 初始化fragment
     */
    private void initFragment() {
        //星球
        if(mStarFragment==null){
            mStarFragment=new StarFragment();
            mStarTransaction=getSupportFragmentManager().beginTransaction();
            mStarTransaction.add(R.id.mMainLayout,mStarFragment);
            mStarTransaction.commit();
        }
        //广场
        if(mSquareFragment==null){
            mSquareFragment=new SquareFragment();
            mSquareTransaction=getSupportFragmentManager().beginTransaction();
            mSquareTransaction.add(R.id.mMainLayout,mSquareFragment);
            mSquareTransaction.commit();
        }
        //聊天
        if(mChatFragment==null){
            mChatFragment=new ChatFragment();
            mChatTransaction=getSupportFragmentManager().beginTransaction();
            mChatTransaction.add(R.id.mMainLayout,mChatFragment);
            mChatTransaction.commit();
        }
        //我的
        if(mMyFragment==null){
            mMyFragment=new MyFragment();
            mMyTransaction=getSupportFragmentManager().beginTransaction();
            mMyTransaction.add(R.id.mMainLayout,mMyFragment);
            mMyTransaction.commit();
        }

    }

    /**
     * 显示fragment
     * @param fragment
     */
    private void showFragment(Fragment fragment){
        if(fragment!=null){
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 隐藏所有的Fragment
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction){
        if(mStarTransaction!=null){
            transaction.hide(mStarFragment);
        }
        if(mSquareTransaction!=null){
            transaction.hide(mSquareFragment);
        }
        if(mChatTransaction!=null){
            transaction.hide(mChatFragment);
        }
        if(mMyTransaction!=null){
            transaction.hide(mMyFragment);
        }
    }

    /**
     * 切换主页选项 0星球 1广场 2 聊天 3 我的
     * @param index
     */
    private void checkMainTab(int index){
        switch (index){
            case 0:
                showFragment(mStarFragment);
                ivstar.setImageResource(R.drawable.star_light);
                ivsquare.setImageResource(R.drawable.square);
                ivchat.setImageResource(R.drawable.chat);
                ivmy.setImageResource(R.drawable.my);

                tvstar.setTextColor(getResources().getColor(R.color.colorAccent));
                tvsquare.setTextColor(Color.BLACK);
                tvchat.setTextColor(Color.BLACK);
                tvmy.setTextColor(Color.BLACK);

                break;
            case 1:
                showFragment(mSquareFragment);
                ivstar.setImageResource(R.drawable.star);
                ivsquare.setImageResource(R.drawable.square_right);
                ivchat.setImageResource(R.drawable.chat);
                ivmy.setImageResource(R.drawable.my);

                tvstar.setTextColor(Color.BLACK);
                tvsquare.setTextColor(getResources().getColor(R.color.colorAccent));
                tvchat.setTextColor(Color.BLACK);
                tvmy.setTextColor(Color.BLACK);
                break;
            case 2:
                showFragment(mChatFragment);
                ivstar.setImageResource(R.drawable.star);
                ivsquare.setImageResource(R.drawable.square);
                ivchat.setImageResource(R.drawable.chat_right);
                ivmy.setImageResource(R.drawable.my);

                tvstar.setTextColor(Color.BLACK);
                tvsquare.setTextColor(Color.BLACK);
                tvchat.setTextColor(getResources().getColor(R.color.colorAccent));
                tvmy.setTextColor(Color.BLACK);
                break;
            case 3:
                showFragment(mMyFragment);
                ivstar.setImageResource(R.drawable.star);
                ivsquare.setImageResource(R.drawable.square);
                ivchat.setImageResource(R.drawable.chat);
                ivmy.setImageResource(R.drawable.my_right);

                tvstar.setTextColor(Color.BLACK);
                tvsquare.setTextColor(Color.BLACK);
                tvchat.setTextColor(Color.BLACK);
                tvmy.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
        }

    }

    /**
     * 防止重叠
     * 当应用内存紧张的时候，系统会回收Fragment对象
     * 再一次进入的时候会重新创建Fragment
     * 非原来对象，我们无法控制，导致重叠
     * @param fragment
     */
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if(mStarFragment!=null&&fragment instanceof StarFragment){
            mStarFragment=(StarFragment)fragment;
        }
        if(mSquareFragment!=null&&fragment instanceof SquareFragment){
            mSquareFragment=(SquareFragment)fragment;
        }
        if(mChatFragment!=null&&fragment instanceof ChatFragment){
            mChatFragment=(ChatFragment)fragment;
        }
        if(mMyFragment!=null&&fragment instanceof MyFragment){
            mMyFragment=(MyFragment)fragment;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_star:
                checkMainTab(0);
                break;
            case R.id.ll_square:
                checkMainTab(1);
                break;
            case R.id.ll_chat:
                checkMainTab(2);
                break;
            case R.id.ll_my:
                checkMainTab(3);
                break;

        }
    }

    /**
     * 请求权限
     */
    private void requestPermiss() {
        request(PERSSION_REQUEST_CODE, new OnPermissionsResult() {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnFail(List<String> noPermissions) {
                Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if(resultCode== Activity.RESULT_OK){
            //说明上传头像成功
            if(requestCode==UPLOAD_REQUEST_CODE){
                checkToken();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }
}