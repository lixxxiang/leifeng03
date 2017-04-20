package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;

import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;;
import com.baidu.mapapi.map.MapStatusUpdate;;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.TextureMapView;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment.DetectFragmentWithMapPresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Utils.checkPermission;


/**
 * Created by Jay on 2015/8/28 0028.
 */

public class DetectFragmentWithMap extends BaseFragment<DetectFragmentWithMapPresenter, DetectFragmentWithMap> implements BaseViewInterface {
    public static TextureMapView mapView = null;
    public static BaiduMap baiduMap;
    public static LocationClient mlocationClient;
    public static Context context;
    private View view;

    @BindView(R.id.changeView)
    Button changeView;
    private FragmentManager fragmentManager;
    private Icon[] icons;
    private MapStatusUpdate mapStatusUpdate;
    private Resources res;
    //qwfeqfqegwrgwr
    //自定义图标
    public static BitmapDescriptor mIconLocation;
    //testtt
    //eteete
    //dfsfdsdfsdf
    public static BitmapDescriptor bitmapDescriptor;

    public static MyOrientationListener myOrientationListener;
    //定位图层显示方式
    public static MyLocationConfiguration.LocationMode locationMode;

    public DetectFragmentWithMap() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.detect_fragment_with_map, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        mapView = (TextureMapView) view.findViewById(R.id.bmapView);
//        requestLocButton = (Button) view.findViewById(R.id.button1);
        /**
         * to presenter
         */
        checkPermission();
        /**
         * 传参 定位点坐标
         */
//        fpresenter.setLocationMode();
        res=getResources();
        fpresenter.initLocation(res);
        fpresenter.getIcons();
        fpresenter.setIcon(icons);

//        myOrientationListener.start();
        fragmentManager = getFragmentManager();

        fpresenter.a(res);
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpresenter.changeFragment();
            }
        });
        return view;
    }

    @Override
    protected DetectFragmentWithMapPresenter getPresenter() {
        return new DetectFragmentWithMapPresenter();
    }




    @Override
    public void onResume() {
        super.onResume();
        mapView.setVisibility(View.VISIBLE);
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.setVisibility(View.INVISIBLE);
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mlocationClient.stop();
        mapView.onDestroy();
        myOrientationListener.stop();
    }





    @Override
    public void onStart() {
        super.onStart();
        myOrientationListener.start();
    }

    public void getIcons(Object[] objects) {
        icons = (Icon[]) objects;
    }

}