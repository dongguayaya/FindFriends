package com.dongua.findfriends;

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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dongua.findfriends.fragment.ChatFragment;
import com.dongua.findfriends.fragment.MyFragment;
import com.dongua.findfriends.fragment.SquareFragment;
import com.dongua.findfriends.fragment.StarFragment;
import com.dongua.findfriends.service.CloudService;
import com.dongua.framework.base.BaseUIActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.entity.Constants;
import com.dongua.framework.manager.DialogManager;
import com.dongua.framework.utils.SpUtils;
import com.dongua.framework.view.DialogView;

import java.util.List;

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

    //星球
    private ImageView ivsquare;
    private TextView tvsquare;
    private LinearLayout llsquare;
    private SquareFragment mSquareFragment=null;
    private FragmentTransaction mSquareTransaction=null;

    //星球
    private ImageView ivmy;
    private TextView tvmy;
    private LinearLayout llmy;
    private MyFragment mMyFragment=null;
    private FragmentTransaction mMyTransaction=null;
    //申请运行时的权限
    private static final int PERSSION_REQUEST_CODE=1000;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!checkWindowPermissions()){
            requestWindowPermissions(1001);
        }

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


}