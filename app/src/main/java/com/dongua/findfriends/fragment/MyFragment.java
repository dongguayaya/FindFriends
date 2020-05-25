package com.dongua.findfriends.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongua.findfriends.R;
import com.dongua.framework.base.BaseFragment;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.bmob.IMUser;
import com.dongua.framework.helper.GlideHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView mIvMePhoto;
    private TextView mTvNickname;
    private TextView mTvServerStatus;
    private LinearLayout mLlMeInfo;
    private LinearLayout mLlNewFriend;
    private LinearLayout mLlPrivateSet;
    private LinearLayout mLlShare;
    private LinearLayout mLlNotice;
    private LinearLayout mLlSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mIvMePhoto=view.findViewById(R.id.iv_me_photo);
        mTvNickname=view.findViewById(R.id.tv_nickname);

        mLlMeInfo=view.findViewById(R.id.ll_me_info);
        mLlNewFriend=view.findViewById(R.id.ll_new_friend);
        mLlPrivateSet=view.findViewById(R.id.ll_private_set);
        mLlShare=view.findViewById(R.id.ll_share);
        mLlSetting=view.findViewById(R.id.ll_setting);

        mLlMeInfo.setOnClickListener(this);
        mLlNewFriend.setOnClickListener(this);
        mLlPrivateSet.setOnClickListener(this);
        mLlShare.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);

        loadMeInfo();

    }

    private void loadMeInfo() {
        IMUser imUser= BmobManager.getInstance().getUser();
        GlideHelper.loadUrl(getActivity(),imUser.getPhoto(),mIvMePhoto);
        mTvNickname.setText(imUser.getNickName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_me_info:
                break;
            case R.id.ll_new_friend:
                break;
            case R.id.ll_private_set:
                break;
            case R.id.ll_share:
                break;
            case R.id.ll_setting:
                break;
        }
    }
}
