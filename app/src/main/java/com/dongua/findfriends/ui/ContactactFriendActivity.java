package com.dongua.findfriends.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongua.findfriends.R;
import com.dongua.findfriends.adapter.AddFriendAdapter;
import com.dongua.findfriends.model.AddFriendModel;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.bmob.IMUser;
import com.dongua.framework.utils.CommonUtils;
import com.dongua.framework.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContactactFriendActivity extends AppCompatActivity {

    private RecyclerView mMContactView;
    private Map<String ,String> mContactMap=new HashMap<>();
    private AddFriendAdapter mAddFriendAdapter;
    private List<AddFriendModel> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactact_friend);

        initView();

    }

    private void locadUser() {
        if(mContactMap.size()>0){
            for( final Map.Entry<String,String>entry:mContactMap.entrySet()){
                BmobManager.getInstance().queryPhoneUser(entry.getValue(), new FindListener<IMUser>() {
                    @Override
                    public void done(List<IMUser> list, BmobException e) {
                        if(e==null){
                            if(CommonUtils.isEmpty(list)){
                                IMUser imUser=list.get(0);
                                addContent(imUser,entry.getKey(),entry.getValue() );
                            }
                        }
                    }
                });
            }
        }
    }

    private void initView() {
        mMContactView = (RecyclerView) findViewById(R.id.mContactView);
        mMContactView.setLayoutManager(new LinearLayoutManager(this));
        mMContactView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAddFriendAdapter=new AddFriendAdapter(this,mList);
        mMContactView.setAdapter(mAddFriendAdapter);
        mAddFriendAdapter.setOnClickListener(new AddFriendAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {

            }
        });
        loadContact();
        locadUser();
    }

    private void loadContact() {
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String name;
        String phone;
        while(cursor.moveToNext()){
            name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            LogUtils.i("name:"+name+"phone:"+phone);
            phone=phone.replace(" ","").replace("-","");
            mContactMap.put(phone,name);

        }
    }
    private void addContent(IMUser imUser,String name,String phone){
        AddFriendModel model=new AddFriendModel();
        model.setType(AddFriendAdapter.TYPE_CONTENT);
        model.setUserId(imUser.getObjectId());
        model.setPhoto(imUser.getPhoto());
        model.setSex(imUser.isSex());
        model.setAge(imUser.getAge());
        model.setNikeName(imUser.getNickName());
        model.setDesc(imUser.getDesc());
        model.setContact(true);
        model.setContactName(name);
        model.setContactPhone(phone);
        mList.add(model);
        mAddFriendAdapter.notifyDataSetChanged();
    }
}
