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
    private FragmentTransaction fTransaction;

    @Override
    protected MainActivityModelImpl getModel() {
        return new MainActivityModelImpl();
    }

    public void performOnClick() {
        Toast.makeText(MainActivity.mainActivity, "show record view", Toast.LENGTH_SHORT).show();
    }

    public void createFragment(int checkedId) {
        fragmentManager = MainActivity.mainActivity.getFragmentManager();
        fTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fTransaction);

//        if (homeFragmentWithMapbak == null ) {
//            homeFragmentWithMapbak = new HomeFragmentWithMapbak();
//            fTransaction.add(R.id.ly_content, homeFragmentWithMapbak);
//        } else {
//            fTransaction.show(homeFragmentNormal);
//        }
        if (homeFragmentWithMap == null ) {
            homeFragmentWithMap = new HomeFragmentWithMap();
            fTransaction.add(R.id.ly_content, homeFragmentWithMap);
        } else {
            fTransaction.show(homeFragmentNormal);
        }

        switch (checkedId) {
            case R.id.rb_home:
                if (homeFragmentNormal == null ) {
                    homeFragmentNormal = new HomeFragmentNormal();
                    fTransaction.add(R.id.ly_content, homeFragmentNormal);
                } else {
                    fTransaction.show(homeFragmentNormal);
                }
                break;
            case R.id.rb_detect:
                if (detectFragmentNormal == null) {
                    detectFragmentNormal = new DetectFragmentNormal();
                    fTransaction.add(R.id.ly_content, detectFragmentNormal);
                } else {
                    fTransaction.show(detectFragmentNormal);
                }
                break;
            case R.id.rb_private:
                if (privateFragment == null) {
                    privateFragment = new PrivateFragment();
                    fTransaction.add(R.id.ly_content, privateFragment);
                } else {
                    fTransaction.show(privateFragment);
                }
                break;
            case R.id.rb_more:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    fTransaction.add(R.id.ly_content, moreFragment);
                } else {
                    fTransaction.show(moreFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragmentNormal != null) fragmentTransaction.hide(homeFragmentNormal);
        if (detectFragmentNormal != null) fragmentTransaction.hide(detectFragmentNormal);
        if (privateFragment != null) fragmentTransaction.hide(privateFragment);
        if (moreFragment != null) fragmentTransaction.hide(moreFragment);
    }

}
