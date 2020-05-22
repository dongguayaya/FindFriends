package com.dongua.findfriends.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dongua.findfriends.R;
import com.dongua.framework.base.BaseBackActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.bmob.IMUser;
import com.dongua.framework.utils.CommonUtils;
import com.dongua.framework.utils.LogUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddFriendActivity extends BaseBackActivity implements View.OnClickListener {

    /**
     * 模拟用户数据
     * 根据条件查询
     * 推荐好友
     */
    private LinearLayout mLlToContact;
    private EditText mEtPhone;
    private ImageView mIvSearch;
    private RecyclerView mMSearchResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
    }

    private void initView() {
        mLlToContact = (LinearLayout) findViewById(R.id.ll_to_contact);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mMSearchResultView = (RecyclerView) findViewById(R.id.mSearchResultView);

        mLlToContact.setOnClickListener(this);
        mMSearchResultView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_to_contact:
                break;
            case R.id.iv_search:
                queryPhoneUser();
                break;
        }
    }

    /**
     * 电话号码查询
     */
    private void queryPhoneUser() {
        String phone=mEtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,getString(R.string.text_login_phone_null),Toast.LENGTH_SHORT
            ).show();
            return;
        }
        //2.查询
        BmobManager.getInstance().queryPhoneUser(phone, new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if(CommonUtils.isEmpty(list)){
                    IMUser imUser=list.get(0);
                    LogUtils.i("imUser+:"+imUser.toString());
                }
            }
        });
    }
}