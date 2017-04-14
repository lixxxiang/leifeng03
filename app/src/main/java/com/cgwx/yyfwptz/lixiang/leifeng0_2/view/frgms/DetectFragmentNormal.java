package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment.DetectFragmentNormalPresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Constants;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;

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

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class DetectFragmentNormal extends BaseFragment<DetectFragmentNormalPresenter, DetectFragmentNormal> implements CordovaInterface, BaseViewInterface {


    @BindView(R.id.cordovaWebView)
    SystemWebView systemWebView;
    @BindView(R.id.changeView)
    Button changeView;

    private CordovaWebView cordovaWebView;
    private ConfigXmlParser configXmlParser;
    private View view;
    protected CordovaPlugin activityResultCallback;
    protected int activityResultRequestCode;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private String content;
    private String URL;
    private FragmentManager fragmentManager;
    private DetectFragmentWithMap detectFragmentWithMap;

    public DetectFragmentNormal() {

    }

    public DetectFragmentNormal(String content){
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detect_fragment1,container,false);
        ButterKnife.bind(this, view);
        fpresenter.getURLRequest(Constants.detectFragmentNormal);
        systemWebView.loadUrl(URL);
        cordovaWebView = new CordovaWebViewImpl(new SystemWebViewEngine(systemWebView));
        configXmlParser = new ConfigXmlParser();
        configXmlParser.parse(getActivity());
        cordovaWebView.init(this, configXmlParser.getPluginEntries(), configXmlParser.getPreferences());
        fragmentManager = getFragmentManager();
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpresenter.changeFragment();
            }
        });

        return view;
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

    @Override
    protected DetectFragmentNormalPresenter getPresenter() {
        return new DetectFragmentNormalPresenter();
    }

    public void getURL(String URLresponse) {
        URL = URLresponse;
    }
}
