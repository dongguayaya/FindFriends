package com.dongua.framework.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import com.dongua.framework.utils.LogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 关于文件的辅助类
 */
public class FileHelper  {
    public static final int CAMERA_REQUEST_CEDE=1004;
    public static final int ALBUM_REQUEST_CEDE=1005;
    private static volatile FileHelper mInstence=null;
    private SimpleDateFormat simpleDateFormat;
    private File tempFie=null;
    private Uri imageUri;
    private FileHelper(){
         simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    }
    public static FileHelper getInstance(){
        if (mInstence==null){
            synchronized (FileHelper.class){
                if(mInstence==null){
                    mInstence=new FileHelper();
                }
            }
        }
        return mInstence;
    }

    /**
     * 跳转到相册
     * @param mActivity
     */
    public void toAlbum(Activity mActivity){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent,ALBUM_REQUEST_CEDE);
    }
    public File getTempFie(){
        return tempFie;
    }

    /**
     * 相机
     * @param mActivity
     */
    public void toCamera(Activity mActivity){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String fileName = simpleDateFormat.format(new Date());
            tempFie = new File(Environment.getExternalStorageDirectory(), fileName + ".jpg");
            //兼容android
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imageUri = Uri.fromFile(tempFie);
            } else {
                //利用FileProvider处理
                imageUri = FileProvider.getUriForFile(mActivity,
                        mActivity.getPackageName() + ".fileprovider", tempFie);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            LogUtils.i("imageUri:" + imageUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                mActivity.startActivityForResult(intent, CAMERA_REQUEST_CEDE);
            }
        }catch (Exception e){
            Toast.makeText(mActivity,"无法打开相机",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    /**
     * 通过Uri去系统查询真实地址
     * @param uri
     */
    public String getRealPathFromURI(Context mContext,Uri uri){
        String[] proj={MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader=new CursorLoader(mContext,uri,proj,null,null,null);
        Cursor cursor=cursorLoader.loadInBackground();
        int index=cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);

    }

}
