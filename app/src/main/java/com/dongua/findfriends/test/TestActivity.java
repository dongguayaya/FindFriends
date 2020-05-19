package com.dongua.findfriends.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.dongua.findfriends.R;
import com.dongua.framework.view.TouchPicture;

public class TestActivity extends AppCompatActivity {

    private com.dongua.framework.view.TouchPicture touchPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        touchPicture=(TouchPicture)findViewById(R.id.Touch);
        touchPicture.setViewResultListener(new TouchPicture.OnViewResultListener() {
            @Override
            public void onResult() {
                Toast.makeText(TestActivity.this,"验证通过",Toast.LENGTH_SHORT).show();
            }
        });
    }

}