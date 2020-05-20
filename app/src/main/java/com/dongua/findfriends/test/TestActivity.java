package com.dongua.findfriends.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dongua.findfriends.R;
import com.dongua.framework.base.BaseUIActivity;
import com.dongua.framework.bmob.MyData;
import com.dongua.framework.utils.LogUtils;
import com.dongua.framework.view.TouchPicture;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TestActivity extends BaseUIActivity implements View.OnClickListener {

    private Button button;
    private com.dongua.framework.view.TouchPicture touchPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        button=findViewById(R.id.add);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                MyData myData=new MyData();
                myData.setName("张8");
                myData.setSex(0);
                myData.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            LogUtils.i("新增成功:"+s);
                        }
                    }
                });
                break;
        }

    }
}