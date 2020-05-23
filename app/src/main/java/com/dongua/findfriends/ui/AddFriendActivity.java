package com.dongua.findfriends.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongua.findfriends.R;
import com.dongua.findfriends.adapter.AddFriendAdapter;
import com.dongua.findfriends.model.AddFriendModel;
import com.dongua.framework.base.BaseBackActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.bmob.IMUser;
import com.dongua.framework.utils.CommonUtils;
import com.dongua.framework.utils.LogUtils;

import java.util.ArrayList;
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
    private AddFriendAdapter mAddFriendAdapter;
    private List<AddFriendModel>mList=new ArrayList<>();
    private View include_empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
    }

    /**
     * 初始化 View
     */
    private void initView() {
        include_empty_view=findViewById(R.id.include_empty_view);
        mLlToContact = (LinearLayout) findViewById(R.id.ll_to_contact);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mMSearchResultView = (RecyclerView) findViewById(R.id.mSearchResultView);
        //列表的实现
        mMSearchResultView.setLayoutManager(new LinearLayoutManager(this));
        mMSearchResultView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAddFriendAdapter=new AddFriendAdapter(this,mList);
        mMSearchResultView.setAdapter(mAddFriendAdapter);

        mAddFriendAdapter.setOnClickListener(new AddFriendAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {
                Toast.makeText(AddFriendActivity.this,"position",Toast.LENGTH_SHORT).show();
            }
        });
        mLlToContact.setOnClickListener(this);
        mMSearchResultView.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);

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
        //过滤自己
//        String phoneNum=BmobManager.getInstance().getUser().getMobilePhoneNumber();
//        LogUtils.i("phoneNumber"+phoneNum);
//        if(phone.equals(phone)){
//            Toast.makeText(this,"不能查询自己",Toast.LENGTH_SHORT).show();
//            return;
//        }
        //2.查询
        BmobManager.getInstance().queryPhoneUser(phone, new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if(e!=null){
                    return;
                }
                if(CommonUtils.isEmpty(list)){
                    IMUser imUser=list.get(0);
                    include_empty_view.setVisibility(View.GONE);
                    mMSearchResultView.setVisibility(View.VISIBLE);

                    mList.clear();
                    addTitle("查询结果");
                    addContent(imUser);
                    mAddFriendAdapter.notifyDataSetChanged();
                    //推荐
                    pushUser();
                }else{
                    //显示空数据
                    include_empty_view.setVisibility(View.VISIBLE);
                    mMSearchResultView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void pushUser() {
        //查询所有好友，取100
        BmobManager.getInstance().queryAllUser(new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if(e==null){
                    if(CommonUtils.isEmpty(list)){
                        addTitle("推荐好友");
                        int num=(list.size()<=100)?list.size():100;
                        for(int i=0;i<num;i++){
                            //也不能自己推荐自己
//                            String phoneNum=BmobManager.getInstance().getUser().getMobilePhoneNumber();
//                            if(list.get(i).getMobilePhoneNumber().equals(phoneNum)){
//                                //跳过本次循环
//                                continue;
//                            }
                            addContent(list.get(i));
                        }
                        mAddFriendAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 添加头部
     * @param title
     */
    private void addTitle(String title){
        AddFriendModel model=new AddFriendModel();
        model.setType(mAddFriendAdapter.TVPE_TITLE);
        model.setTitle(title);
        mList.add(model);

    }

    /**
     * 添加内容
     * @param imUser
     */
    private void addContent(IMUser imUser){
        AddFriendModel model=new AddFriendModel();
        model.setType(mAddFriendAdapter.TYPE_CONTENT);
        model.setUserId(imUser.getObjectId());
        model.setPhoto(imUser.getPhoto());
        model.setSex(imUser.isSex());
        model.setAge(imUser.getAge());
        model.setNikeName(imUser.getNickName());
        model.setDesc(imUser.getDesc());
        mList.add(model);

    }
}