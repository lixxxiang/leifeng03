package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.HomeFragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.HomeFragmentNormalModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.HomeFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.homeFragmentWithMap2;


/**
 * Created by yyfwptz on 2017/3/27.
 */

public class HomeFragmentNormalPresenter extends BasePresenter<HomeFragmentNormal, HomeFragmentNormalModelImpl> {

    @Override
    protected HomeFragmentNormalModelImpl getModel() {
        return new HomeFragmentNormalModelImpl();
    }


    public void changeFragment() {
        FragmentManager fragmentManager = MainActivity.mainActivity.getFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        fTransaction.hide(homeFragmentNormal);
//        fTransaction.show(homeFragmentWithMap);
        fTransaction.show(homeFragmentWithMap2);

        fTransaction.commit();
    }

    public void getURLRequest(String request) {
        model.geturl(request, new OnSendStringListener() {
            @Override
            public void sendString(String string) {
                getView().getURL(string);
            }
        });
    }
}
