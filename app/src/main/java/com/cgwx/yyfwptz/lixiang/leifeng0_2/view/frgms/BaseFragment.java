package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;

import android.app.Fragment;
import android.os.Bundle;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;

/**
 * Created by yyfwptz on 2017/3/28.
 */

public abstract class BaseFragment<T extends BasePresenter, V extends BaseViewInterface> extends Fragment{
    protected T fpresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fpresenter = getPresenter();
        fpresenter.attach((V)this);
    }

    protected abstract T getPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        fpresenter.deAttach();
    }
}
