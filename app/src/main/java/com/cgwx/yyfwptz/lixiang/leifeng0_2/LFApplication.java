package com.cgwx.yyfwptz.lixiang.leifeng0_2;

import android.app.Application;

import com.yixia.camera.VCamera;

import java.io.File;

/**
 * Created by yyfwptz on 2017/3/25.
 */

public class LFApplication extends Application{

    public static String VIDEO_PATH =  "/sdcard/LeiFengRecordedDemo/";

    @Override
    public void onCreate() {
        super.onCreate();
        VIDEO_PATH += String.valueOf(System.currentTimeMillis());
        File file = new File(VIDEO_PATH);
        if(!file.exists()) file.mkdirs();
        VCamera.setVideoCachePath(VIDEO_PATH);
        VCamera.setDebugMode(true);
        VCamera.initialize(this);
    }
}
