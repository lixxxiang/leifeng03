package com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface;

import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.BaseModelInterface;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public interface HomeFragmentNormalModelInterface extends BaseModelInterface{
    void geturl(String request, OnSendStringListener listener);
}
