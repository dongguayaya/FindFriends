package com.dongua.findfriends.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dongua.findfriends.MainActivity;
import com.dongua.findfriends.R;
import com.dongua.findfriends.test.TestActivity;
import com.dongua.framework.base.BaseUIActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.bmob.IMUser;
import com.dongua.framework.entity.Constants;
import com.dongua.framework.manager.DialogManager;
import com.dongua.framework.utils.SpUtils;
import com.dongua.framework.view.DialogView;
import com.dongua.framework.view.LoadingView;
import com.dongua.framework.view.TouchPicture;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;


/**
 * 1.点击发送按钮，弹出一个提示框，图片验证码，验证通过后
 * 2.发送验证码，同时按钮变成不可点击，按钮开始倒计时，倒计时结束，按钮可点击，文字变成发送
 * 3.通过手机号码和验证码进行登录
 * 4.登录成功之后获取本地对象
 */
public class LoginActivity extends BaseUIActivity implements View.OnClickListener {
    private EditText etPhone;
    private EditText etCode;
    private Button btnSendCode;
    private Button btnLogin;
    private TextView tvTestLogin;
    private TextView tvUserAgreement;
    private DialogView mCodeView;
    private TouchPicture mPicture;
    private static final int H_TIME=1001;
    //60s倒计时
    private static int TIME=60;
    private LoadingView mLoadingView;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage( Message msg) {
            switch (msg.what){
                case H_TIME:
                    TIME--;
                    btnSendCode.setText(TIME+"s");
                    if(TIME>0){
                        mHandler.sendEmptyMessageDelayed(H_TIME,1000);
                    }else{
                        btnSendCode.setEnabled(true);
                        btnSendCode.setText(getString(R.string.send));
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        initDialogView();
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        btnSendCode = (Button) findViewById(R.id.btn_send_code);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvTestLogin = (TextView) findViewById(R.id.tv_test_login);
        tvUserAgreement = (TextView) findViewById(R.id.tv_user_agreement);

        btnSendCode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvTestLogin.setOnClickListener(this);
        String phone=SpUtils.getInstance().getString(Constants.SP_PHONE,"");
        if(!TextUtils.isEmpty(phone)){
            etPhone.setText(phone);
        }

    }

    private void initDialogView() {
        mLoadingView=new LoadingView(this);
        mCodeView=DialogManager.getInstance().initView(this,R.layout.dialog_code_view);
        mPicture=mCodeView.findViewById(R.id.mPictureV);
        mPicture.setViewResultListener(new TouchPicture.OnViewResultListener() {
            @Override
            public void onResult() {
                DialogManager.getInstance().hide(mCodeView);
                sendSMS();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_code:
                DialogManager.getInstance().show(mCodeView);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_test_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {

        //判断手机号码和验证码不为空
         final String phone=etPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,getString(R.string.send_Null),Toast.LENGTH_SHORT).show();
            return;
        }
        String code=etCode.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,getString(R.string.code_Null),Toast.LENGTH_SHORT).show();
            return;
        }
        //显示loading
        mLoadingView.show("正在登陆...");
        BmobManager.getInstance().signOrLoginByMobilePhone(phone, code, new LogInListener<IMUser>() {
            @Override
            public void done(IMUser imUser, BmobException e) {
                if (e == null) {
                    mLoadingView.hide();
                    //登录成功
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //把手机号码保存下来
                    SpUtils.getInstance().putString(Constants.SP_PHONE,phone);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"ERROR"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 发送短信验证码
     */
    private void sendSMS() {
        //1.获取手机号码
        String phone=etPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,getString(R.string.send_Null),Toast.LENGTH_SHORT).show();
            return;
        }
        //2.请求短信验证码
        BmobManager.getInstance().requestSMS(phone, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e==null){
                    btnSendCode.setEnabled(false);
                    mHandler.sendEmptyMessage(H_TIME);
                    Toast.makeText(LoginActivity.this,"短信验证码发送成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"短信验证码发送失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}