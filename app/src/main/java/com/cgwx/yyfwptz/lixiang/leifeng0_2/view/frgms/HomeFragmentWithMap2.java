package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.TextureMapView;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.HomeFragment.HomeFragmentWithMap2Presenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Constants;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;
import com.yinglan.scrolllayout.ScrollLayout;
import com.yixia.camera.util.Log;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Utils.checkPermission;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity.record;


/**
 * Created by Jay on 2015/8/28 0028.
 */

public class HomeFragmentWithMap2 extends BaseFragment<HomeFragmentWithMap2Presenter, HomeFragmentWithMap2> implements BaseViewInterface, CordovaInterface {
    public static TextureMapView mapView = null;
    public static BaiduMap baiduMap;
    public static LocationClient mlocationClient;
    public static Context context;
    private View view;


    @BindView(R.id.changeView)
    Button changeView;
    @BindView(R.id.cordovaWebView)
    SystemWebView systemWebView;

    private FragmentManager fragmentManager;
    private Icon[] icons;
    private Resources resources;
    public static BitmapDescriptor mIconLocation;
    public static BitmapDescriptor bitmapDescriptor;
    public static MyOrientationListener myOrientationListener;
    public static MyLocationConfiguration.LocationMode locationMode;
    public static ScrollLayout scrollLayout;
    protected CordovaPlugin activityResultCallback;
    protected int activityResultRequestCode;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private CordovaWebView cordovaWebView;
    private ConfigXmlParser configXmlParser;
    private String URL;


    public HomeFragmentWithMap2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.home_fragment_with_map2test, container, false);

        checkPermission();

        ButterKnife.bind(this, view);
        context = getActivity();
        resources = getResources();
        fragmentManager = getFragmentManager();

        scrollLayout = (ScrollLayout) view.findViewById(R.id.scroll_down_layout);
        mapView = (TextureMapView) view.findViewById(R.id.bmapView);
        fpresenter.getURLRequest(Constants.homeFragmentWithMapUpPullURL);
        systemWebView.loadUrl(URL);
        cordovaWebView = new CordovaWebViewImpl(new SystemWebViewEngine(systemWebView));
        configXmlParser = new ConfigXmlParser();
        configXmlParser.parse(getActivity());
        cordovaWebView.init(this, configXmlParser.getPluginEntries(), configXmlParser.getPreferences());
        systemWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("Ta","d");
//                        record.setVisibility(View.INVISIBLE);   https://github.com/yescpu/KeyboardChangeListener
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });

        fpresenter.initialScrollLayout(scrollLayout);
        fpresenter.initLocation(resources);
        fpresenter.getIcons();
        fpresenter.setIcon(icons);
        fpresenter.setLocation(resources);
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpresenter.changeFragment();
            }
        });
        return view;
    }

    @Override
    protected HomeFragmentWithMap2Presenter getPresenter() {
        return new HomeFragmentWithMap2Presenter();
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

    @Override
    public void startActivityForResult(CordovaPlugin cordovaPlugin, Intent intent, int i) {
        setActivityResultCallback(cordovaPlugin);
        try {
            startActivityForResult(intent, i);
        } catch (RuntimeException e) {
            activityResultCallback = null;
            throw e;
        }
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin cordovaPlugin) {
        if (activityResultCallback != null) {
            activityResultCallback.onActivityResult(activityResultRequestCode, Activity.RESULT_CANCELED, null);
        }
        this.activityResultCallback = cordovaPlugin;
    }

    @Override
    public Object onMessage(String s, Object o) {
        if ("exit".equals(s)) {
            getActivity().finish();
        }
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    @Override
    public void requestPermission(CordovaPlugin cordovaPlugin, int i, String s) {

    }

    @Override
    public void requestPermissions(CordovaPlugin cordovaPlugin, int i, String[] strings) {

    }

    @Override
    public boolean hasPermission(String s) {
        return false;
    }

    public void getURL(String URLresponse) {
        URL = URLresponse;
    }

}