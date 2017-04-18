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
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentWithMap2;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.MoreFragment;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.PrivateFragment;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class MainActivityPresenter extends BasePresenter<MainActivity, MainActivityModelImpl> {

    private PrivateFragment privateFragment;
    private MoreFragment moreFragment;
    public static HomeFragmentNormal homeFragmentNormal;
//    public static HomeFragmentWithMap homeFragmentWithMap;
    public static HomeFragmentWithMap2 homeFragmentWithMap2;
    public static DetectFragmentNormal detectFragmentNormal;
    public static DetectFragmentWithMap detectFragmentWithMap;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected MainActivityModelImpl getModel() {
        return new MainActivityModelImpl();
    }

    public void performOnClick() {
        Toast.makeText(MainActivity.mainActivity, "show record view", Toast.LENGTH_SHORT).show();
    }

    public void createFragment(int checkedId) {
        fragmentManager = MainActivity.mainActivity.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);

        switch (checkedId) {
            case R.id.rb_home:
                if (detectFragmentNormal != null)
                    fragmentTransaction.hide(detectFragmentNormal);
                if (detectFragmentWithMap != null)
                    fragmentTransaction.hide(detectFragmentWithMap);
                if (homeFragmentNormal == null) {
                    homeFragmentNormal = new HomeFragmentNormal();
//                    homeFragmentWithMap = new HomeFragmentWithMap();

                    homeFragmentWithMap2 = new HomeFragmentWithMap2();

                    detectFragmentNormal = new DetectFragmentNormal();
                    detectFragmentWithMap = new DetectFragmentWithMap();
                    fragmentTransaction.add(R.id.ly_content, homeFragmentNormal);
//                    fragmentTransaction.add(R.id.ly_content, homeFragmentWithMap);

                    fragmentTransaction.add(R.id.ly_content, homeFragmentWithMap2);

                    fragmentTransaction.add(R.id.ly_content, detectFragmentNormal);
                    fragmentTransaction.add(R.id.ly_content, detectFragmentWithMap);
//                    fragmentTransaction.hide(homeFragmentWithMap);
                    fragmentTransaction.hide(homeFragmentWithMap2);

                    fragmentTransaction.hide(detectFragmentNormal);
                    fragmentTransaction.hide(detectFragmentWithMap);
                } else if (detectFragmentNormal.isHidden())
//                    fragmentTransaction.show(homeFragmentWithMap);
                fragmentTransaction.show(homeFragmentWithMap2);

                else
                    fragmentTransaction.show(homeFragmentNormal);
                break;
            case R.id.rb_detect:
                if (homeFragmentNormal != null)
                    fragmentTransaction.hide(homeFragmentNormal);
//                if (homeFragmentWithMap != null)
//                    fragmentTransaction.hide(homeFragmentWithMap);
                if (homeFragmentWithMap2 != null)
                    fragmentTransaction.hide(homeFragmentWithMap2);

                if (homeFragmentNormal.isHidden())
                    fragmentTransaction.show(detectFragmentWithMap);
//                else if (homeFragmentWithMap.isHidden())
                else if (homeFragmentWithMap2.isHidden())

                    fragmentTransaction.show(detectFragmentNormal);
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
        fragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragmentNormal != null) fragmentTransaction.hide(homeFragmentNormal);
        if (detectFragmentNormal != null) fragmentTransaction.hide(detectFragmentNormal);
        if (privateFragment != null) fragmentTransaction.hide(privateFragment);
        if (moreFragment != null) fragmentTransaction.hide(moreFragment);
    }

}
