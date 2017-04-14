package com.cgwx.yyfwptz.lixiang.leifeng0_2.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;

/**
 * Created by yyfwptz on 2017/4/14.
 */

public class CheckPermission {

//    public static FragmentManager fragmentManager;

    public static void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (MainActivity.mainActivity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                MainActivity.mainActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_REQUEST_COARSE_LOCATION);
            if (MainActivity.mainActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                MainActivity.mainActivity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_COARSE_LOCATION);
            if (MainActivity.mainActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                MainActivity.mainActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_COARSE_LOCATION);
        }
    }

//    public static void changeFragment(Fragment fragment1, Fragment fragment2){
//        fragmentManager = MainActivity.mainActivity.getFragmentManager();
//        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
//        if (fragment1 != null)
//            fTransaction.hide(fragment1);
//        if (fragment2 == null) {
//            fragment2 = new HomeFragmentWithMap();
//            fTransaction.add(R.id.ly_content, fragment2);
//        } else
//            fTransaction.show(fragment2);
//        fTransaction.commit();
//    }
}
