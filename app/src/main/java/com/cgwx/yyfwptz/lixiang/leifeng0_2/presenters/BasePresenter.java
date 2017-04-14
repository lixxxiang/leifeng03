package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.BaseModelInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;
import java.lang.ref.WeakReference;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public abstract class BasePresenter <T extends BaseViewInterface, M extends BaseModelInterface>{
    private WeakReference<T> weakReference;
    protected M model;
    public void attach(T t) {
        weakReference = new WeakReference<>(t);
        model = getModel();
    }

    public void deAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    public T getView() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
    protected abstract M getModel();


}
