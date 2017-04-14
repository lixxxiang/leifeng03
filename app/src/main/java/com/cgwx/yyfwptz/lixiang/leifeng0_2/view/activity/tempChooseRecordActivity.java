package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.example.zhaoshuang.weixinrecordeddemo.Record2Activity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.model.MediaRecorderConfig;
import mabeijianxi.camera.util.DeviceUtils;

public class tempChooseRecordActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button button1;
    @BindView(R.id.btn2)
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_temp_choose_record);
        ButterKnife.bind(this);
        initSmallVideo(this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                        .doH264Compress(true)
                        .smallVideoWidth(480)
                        .smallVideoHeight(360)
                        .recordTimeMax(6 * 1000)
                        .maxFrameRate(20)
                        .minFrameRate(8)
                        .captureThumbnailsTime(1)
                        .recordTimeMin((int) (1.5 * 1000))
                        .build();
                MediaRecorderActivity.goSmallVideoRecorder(tempChooseRecordActivity.this, SendSmallVideoActivity.class.getName(), config);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tempChooseRecordActivity.this,Record2Activity.class);
                startActivity(intent);
            }
        });
    }

    public static void initSmallVideo(Context context) {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(context);
    }
}
