package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.DetectFragmentNormalModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.detectFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.detectFragmentWithMap;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class DetectFragmentNormalPresenter extends BasePresenter<DetectFragmentNormal, DetectFragmentNormalModelImpl> {


    @Override
    protected DetectFragmentNormalModelImpl getModel() {
        return new DetectFragmentNormalModelImpl();
    }

    public void getURLRequest(String detectFragmentNormal) {
        model.geturl(detectFragmentNormal, new OnSendStringListener() {
            @Override
            public void sendString(String string) {
                getView().getURL(string);
            }
        });
    }

    public void changeFragment() {
        FragmentManager fragmentManager = MainActivity.mainActivity.getFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        fTransaction.hide(detectFragmentNormal);
        fTransaction.show(detectFragmentWithMap);
        fTransaction.commit();
    }
}
