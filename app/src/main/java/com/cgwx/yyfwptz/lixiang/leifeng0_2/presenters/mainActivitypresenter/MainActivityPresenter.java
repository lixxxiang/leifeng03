package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.Toast;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.MainActivityModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentNormal;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentNormal;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentWithMap;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.MoreFragment;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.PrivateFragment;
import com.yixia.camera.util.Log;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class MainActivityPresenter extends BasePresenter<MainActivity, MainActivityModelImpl> {

    private PrivateFragment privateFragment;
    private MoreFragment moreFragment;
    public static HomeFragmentNormal homeFragmentNormal;
    public static HomeFragmentWithMap homeFragmentWithMap;
    public static DetectFragmentNormal detectFragmentNormal;
    public static DetectFragmentWithMap detectFragmentWithMap;

    private FragmentManager fragmentManager;
    private FragmentTransaction beginfTransaction;



    @Override
    protected MainActivityModelImpl getModel() {
        return new MainActivityModelImpl();
    }

    public void performOnClick() {
        Toast.makeText(MainActivity.mainActivity, "show record view", Toast.LENGTH_SHORT).show();
    }

    public void createFragment(int checkedId) {
        fragmentManager = MainActivity.mainActivity.getFragmentManager();
        beginfTransaction = fragmentManager.beginTransaction();
        hideAllFragment(beginfTransaction);

        switch (checkedId) {
            case R.id.rb_home:

                if (homeFragmentNormal == null && homeFragmentWithMap == null) {
                    homeFragmentNormal = new HomeFragmentNormal();
                    homeFragmentWithMap = new HomeFragmentWithMap();
                    beginfTransaction.add(R.id.ly_content, homeFragmentWithMap);
                    beginfTransaction.add(R.id.ly_content, homeFragmentNormal);
                    beginfTransaction.hide(homeFragmentNormal);
                    beginfTransaction.hide(homeFragmentWithMap);
                    beginfTransaction.show(homeFragmentNormal);
                    beginfTransaction.commit();
                } else if (homeFragmentWithMap.isHidden()) {
                    beginfTransaction.show(homeFragmentNormal);
                    beginfTransaction.commit();
                } else if (homeFragmentNormal.isHidden()) {
                    beginfTransaction.show(homeFragmentWithMap);
                    beginfTransaction.commit();
                }
                if (homeFragmentNormal == null) Log.e("TAG-home", "homeFragmentNormal is null");
                else if (homeFragmentNormal.isHidden())
                    Log.e("TAG-home", "homeFragmentNormal is hidden");
                else Log.e("TAG-home", "homeFragmentNormal is show");
                if (homeFragmentWithMap == null)
                    Log.e("TAG-homemap", "homeFragmentWithMap is null");
                else if (homeFragmentWithMap.isHidden())
                    Log.e("TAG-homemap", "homeFragmentWithMap is hidden");
                else Log.e("TAG-homemap", "homeFragmentWithMap is show");
                if (detectFragmentNormal == null)
                    Log.e("TAG-detect", "detectFragmentNormal is null");
                else if (detectFragmentNormal.isHidden())
                    Log.e("TAG-detect", "detectFragmentNormal is hidden");
                else Log.e("TAG-detect", "detectFragmentNormal is show");
                if (detectFragmentWithMap == null)
                    Log.e("TAG-detectmap", "detectFragmentWithMap is null");
                else if (detectFragmentWithMap.isHidden())
                    Log.e("TAG-detectmap", "detectFragmentWithMap is hidden");
                else Log.e("TAG-detectmap", "detectFragmentWithMap is show");

                break;
            case R.id.rb_detect:

                if (detectFragmentNormal == null && detectFragmentWithMap == null) {
                    detectFragmentNormal = new DetectFragmentNormal();
                    detectFragmentWithMap = new DetectFragmentWithMap();
                    beginfTransaction.add(R.id.ly_content, detectFragmentWithMap);
                    beginfTransaction.add(R.id.ly_content, detectFragmentNormal);
                    beginfTransaction.hide(detectFragmentWithMap);
                    beginfTransaction.hide(detectFragmentNormal);
                    beginfTransaction.show(detectFragmentNormal);
                    beginfTransaction.commit();
                } else if (detectFragmentWithMap.isHidden()) {
                    beginfTransaction.show(detectFragmentNormal);
                    beginfTransaction.hide(detectFragmentWithMap);
                    beginfTransaction.commit();
                } else if (detectFragmentNormal.isHidden()) {
                    beginfTransaction.show(detectFragmentWithMap);
                    beginfTransaction.hide(detectFragmentNormal);
                    beginfTransaction.commit();
                }
                if (homeFragmentNormal == null) Log.e("TAG-home2", "homeFragmentNormal is null");
                else if (homeFragmentNormal.isHidden())
                    Log.e("TAG-home2", "homeFragmentNormal is hidden");
                else Log.e("TAG-home2", "homeFragmentNormal is show");
                if (homeFragmentWithMap == null)
                    Log.e("TAG-homemap2", "homeFragmentWithMap is null");
                else if (homeFragmentWithMap.isHidden())
                    Log.e("TAG-homemap2", "homeFragmentWithMap is hidden");
                else Log.e("TAG-homemap2", "homeFragmentWithMap is show");
                if (detectFragmentNormal == null)
                    Log.e("TAG-detect2", "detectFragmentNormal is null");
                else if (detectFragmentNormal.isHidden())
                    Log.e("TAG-detect2", "detectFragmentNormal is hidden");
                else Log.e("TAG-detect2", "detectFragmentNormal is show");
                if (detectFragmentWithMap == null)
                    Log.e("TAG-detectmap2", "detectFragmentWithMap is null");
                else if (detectFragmentWithMap.isHidden())
                    Log.e("TAG-detectmap2", "detectFragmentWithMap is hidden");
                else Log.e("TAG-detectmap2", "detectFragmentWithMap is show");

                break;
//            case R.id.rb_private:
//                if (privateFragment == null) {
//                    privateFragment = new PrivateFragment();
//                    fTransaction.add(R.id.ly_content, privateFragment);
//                } else {
//                    fTransaction.show(privateFragment);
//                }
//                fTransaction.commit();
//
//                break;
//            case R.id.rb_more:
//                if (moreFragment == null) {
//                    moreFragment = new MoreFragment();
//                    fTransaction.add(R.id.ly_content, moreFragment);
//                } else {
//                    fTransaction.show(moreFragment);
//                }
//                fTransaction.commit();
//
//                break;
        }
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragmentNormal != null) fragmentTransaction.hide(homeFragmentNormal);
        if (detectFragmentNormal != null) fragmentTransaction.hide(detectFragmentNormal);
        if (privateFragment != null) fragmentTransaction.hide(privateFragment);
        if (moreFragment != null) fragmentTransaction.hide(moreFragment);
    }

}
