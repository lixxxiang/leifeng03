package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity;

import android.app.Activity;
import android.os.Bundle;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;

import butterknife.ButterKnife;


public abstract class BaseActivity<T extends BasePresenter, V extends BaseViewInterface> extends Activity {
    protected T presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
        presenter.attach((V)this);
    }

    protected abstract T getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deAttach();

    }
}