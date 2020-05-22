package com.dongua.findfriends.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dongua.findfriends.R;
import com.dongua.framework.base.BaseBackActivity;
import com.dongua.framework.bmob.BmobManager;
import com.dongua.framework.helper.FileHelper;
import com.dongua.framework.manager.DialogManager;
import com.dongua.framework.utils.LogUtils;
import com.dongua.framework.view.DialogView;
import com.dongua.framework.view.LoadingView;

import java.io.File;

import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;



public class FirstUploadActivity extends BaseBackActivity implements View.OnClickListener {

    /**
     * @param mActivity
     * @param requestCode
     */
    public static void startActivity(Activity mActivity, int requestCode) {
        Intent intent = new Intent(mActivity, FirstUploadActivity.class);
        mActivity.startActivityForResult(intent, requestCode);
    }
    private CircleImageView ivphoto;
    private EditText etNikename;
    private Button mButton;
    private TextView mTvCamera;
    private TextView mTvAblum;
    private TextView mTvCancel;
    private DialogView mPhotoSelectView;
    private LoadingView mLoadingView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_upload);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        initPhotoView();
        ivphoto = findViewById(R.id.iv_photo);
        etNikename = findViewById(R.id.et_nickname);
        mButton = findViewById(R.id.btn_upload);

        ivphoto.setOnClickListener(this);
        mButton.setOnClickListener(this);

        mButton.setEnabled(false);
        etNikename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    if(uploadFile!=null){
                        mButton.setEnabled(true);
                    }else {
                        mButton.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 初始化选择框
     */
    private void initPhotoView() {
        mLoadingView=new LoadingView(this);
        mLoadingView.setLodingText("正在上传头像...");
        mPhotoSelectView = DialogManager.getInstance().initView(this, R.layout.dialog_select_photo, Gravity.BOTTOM);
        mTvCamera = (TextView) mPhotoSelectView.findViewById(R.id.tv_camera);
        mTvCamera.setOnClickListener(this);
        mTvAblum = (TextView) mPhotoSelectView.findViewById(R.id.tv_ablum);
        mTvAblum.setOnClickListener(this);
        mTvCancel = (TextView) mPhotoSelectView.findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                DialogManager.getInstance().hide(mPhotoSelectView);
                //跳转到相机
                FileHelper.getInstance().toCamera(this);
                break;
            case R.id.tv_ablum:
                DialogManager.getInstance().hide(mPhotoSelectView);
                FileHelper.getInstance().toAlbum(this);
                //跳转到相册
                break;
            case R.id.tv_cancel:
                DialogManager.getInstance().hide(mPhotoSelectView);
                break;
            case R.id.iv_photo:
                //显示选择提示框
                DialogManager.getInstance().show(mPhotoSelectView);
                break;
            case R.id.btn_upload:
                uploadPhoto();
                break;
        }
    }

    //上传
    private void uploadPhoto() {
        //如果条件没有满足
        String nikeName=etNikename.getText().toString().trim();
        mLoadingView.show();
        BmobManager.getInstance().uploadFirstPhoto(nikeName, uploadFile, new BmobManager.OnUploadPhotoListener() {
            @Override
            public void OnUpdateDone() {
                mLoadingView.hide();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void OnUpdateFail(BmobException e) {
                mLoadingView.hide();
                Toast.makeText(FirstUploadActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File uploadFile=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        LogUtils.i("requestCode:"+requestCode);
        if(resultCode==Activity.RESULT_OK){
            if(requestCode==FileHelper.CAMERA_REQUEST_CEDE){
                uploadFile=FileHelper.getInstance().getTempFie();
            }else if(requestCode==FileHelper.ALBUM_REQUEST_CEDE){
                Uri uri=data.getData();
                if(uri!=null){
                    //String path=uri.getPath();
                    //获取真实地址
                    String path=FileHelper.getInstance().getRealPathFromURI(this,uri);
                    if(!TextUtils.isEmpty(path)){
                        uploadFile=new File(path);

                    }
                }

            }

        }
        //设置头像
        if(uploadFile!=null){
            Bitmap mBitmap=BitmapFactory.decodeFile(uploadFile.getPath());
            ivphoto.setImageBitmap(mBitmap);
            //判断当前的输入框
            String nikeName=etNikename.getText().toString().trim();
            mButton.setEnabled(!TextUtils.isEmpty(nikeName));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
