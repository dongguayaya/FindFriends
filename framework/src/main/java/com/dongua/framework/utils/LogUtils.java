package com.dongua.framework.utils;

import android.text.TextUtils;
import android.util.Log;

import com.dongua.framework.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    private static SimpleDateFormat mSimpleDateFormat=
            new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    public static void i(String text){
            if(BuildConfig.LOG_DEBUG){
                if(!TextUtils.isEmpty(text)) {
                    Log.i(BuildConfig.LOG_TAG, text);
                    writeToFile(text);
                }
            }
    }
    public static void e(String text){
        if(BuildConfig.LOG_DEBUG){
            if(!TextUtils.isEmpty(text)) {
                Log.e(BuildConfig.LOG_TAG, text);
                writeToFile(text);
            }
        }
    }
    public static void writeToFile(String text){
        //文件路径
        String filename="/sdcard/FindFriends/FindFriends.log";
        //时间+内存
        String log=mSimpleDateFormat.format(new Date())+" "+text;
        //检查父路径
        File fileGroup=new File("/sdcard/FindFriends/");
        if(!fileGroup.exists()){
            fileGroup.mkdirs();
        }
        //开始写入
        FileOutputStream fileOutputStream=null;
        BufferedWriter bufferedWriter=null;
        try {
             fileOutputStream=new FileOutputStream(filename,true);
             //编码问题 GBK 正确存入中文
             bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.forName("gbk")));
            bufferedWriter.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
