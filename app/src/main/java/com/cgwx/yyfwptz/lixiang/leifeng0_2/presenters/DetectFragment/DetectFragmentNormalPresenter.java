package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.DetectFragmentNormalModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendStringListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentNormal;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.detectFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.detectFragmentWithMap;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity.radioGroup;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class DetectFragmentNormalPresenter extends BasePresenter<DetectFragmentNormal, DetectFragmentNormalModelImpl>{


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
        if (detectFragmentNormal != null)
            fTransaction.hide(detectFragmentNormal);
        if (detectFragmentWithMap == null) {
            detectFragmentWithMap = new DetectFragmentWithMap();
            fTransaction.add(R.id.ly_content, detectFragmentWithMap);
        } else
            fTransaction.show(detectFragmentWithMap);
        fTransaction.commit();

        radioGroup.setVisibility(View.INVISIBLE);
    }
}
