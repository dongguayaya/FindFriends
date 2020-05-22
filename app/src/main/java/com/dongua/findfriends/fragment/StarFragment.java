package com.dongua.findfriends.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dongua.findfriends.R;
import com.dongua.findfriends.ui.AddFriendActivity;
import com.dongua.framework.adapter.CloudTagAdapter;
import com.dongua.framework.base.BaseFragment;
import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

public class StarFragment extends BaseFragment implements View.OnClickListener {
    private ImageView ivcamera;
    private ImageView ivadd;
    private TagCloudView mCloudView;
    private LinearLayout llrandom;
    private LinearLayout llsoul;
    private LinearLayout llfate;
    private LinearLayout lllove;
    private CloudTagAdapter mCloudTagAdapter;
    private List<String> mStarList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_star,null);
        initView(view);
        return view;
    }


    /**
     * 初始化View
     */
    private void initView(View view) {
        ivcamera=view.findViewById(R.id.iv_camera);
        ivadd=view.findViewById(R.id.iv_add);
        mCloudView=view.findViewById(R.id.mCloudView);
        llrandom=view.findViewById(R.id.ll_random);
        llsoul=view.findViewById(R.id.ll_soul);
        llfate=view.findViewById(R.id.ll_fate);
        lllove=view.findViewById(R.id.ll_love);
        ivcamera.setOnClickListener(this);
        ivadd.setOnClickListener(this);
        llsoul.setOnClickListener(this);
        llfate.setOnClickListener(this);
        llrandom.setOnClickListener(this);
        lllove.setOnClickListener(this);
        for(int i=0;i<100;i++){
            mStarList.add("Star"+i);
        }
        //数据绑定
        mCloudTagAdapter=new CloudTagAdapter(getActivity(),mStarList);
        mCloudView.setAdapter(mCloudTagAdapter);

        //jiantingdianjishijian
        mCloudView.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Toast.makeText(getActivity(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_camera:
                //扫描
                break;
            case R.id.iv_add:
                //添加好友
                startActivity(new Intent(getActivity(), AddFriendActivity.class));
                break;
            case R.id.ll_random:
                //随机匹配
                break;
            case R.id.ll_soul:
                //灵魂匹配
                break;
            case R.id.ll_fate:
                //缘分匹配
                break;
            case R.id.ll_love:
                //恋爱匹配
                break;
        }
    }
}
