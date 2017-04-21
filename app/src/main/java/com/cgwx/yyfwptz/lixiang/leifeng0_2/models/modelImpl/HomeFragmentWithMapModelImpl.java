package com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl;


import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.HomeFragmentWithMapModelInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendArrayListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Constants;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;


/**
 * Created by yyfwptz on 2017/3/27.
 */

public class HomeFragmentWithMapModelImpl implements HomeFragmentWithMapModelInterface {
    /**
     * 入参为定位信息 查库获取一定范围内的所有点 用数组表示
     */
    Icon icon1 = new Icon();
    Icon icon2 = new Icon();
    Icon icon3 = new Icon();
    Icon icon4 = new Icon();
    Icon icon5 = new Icon();
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;


    public void insert() {
        icon1.setLatitude(44.000000);
        icon1.setLangitude(125.410000);

        icon2.setLatitude(44.030000);
        icon2.setLangitude(125.440000);

        icon3.setLatitude(44.060000);
        icon3.setLangitude(125.470000);

        icon4.setLatitude(44.090000);
        icon4.setLangitude(125.500000);

        icon5.setLatitude(44.120000);
        icon5.setLangitude(125.530000);
    }

    Icon icons[] = {icon1, icon2, icon3, icon4, icon5};


    @Override
    public void getIcons(OnSendArrayListener listener) {
        mLocationClient = new LocationClient(MainActivity.mainActivity.getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        mLocationClient.registerLocationListener(mBDLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        insert();
        listener.sendArray(icons);
    }


    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                /**
                 * 虽然没什么办法 但是经纬度只能这样取了
                 */
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr();
                Log.i("address", address + " latitude:" + latitude + " longitude:" + longitude);
                /**
                 * 查库操作balabala
                 */

                if (mLocationClient.isStarted())
                    mLocationClient.stop();
            }
        }
    }

    @Override
    public void geturl(String request, OnSendStringListener listener) {
        if(request.equals(Constants.homeFragmentWithMapUpPullURL)){
            listener.sendString(Constants.homeFragmentWithMapUpPullURL);
        }
    }
}
