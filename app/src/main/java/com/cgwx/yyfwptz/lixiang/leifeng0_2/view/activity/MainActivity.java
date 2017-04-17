package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Distance.getDistance;


public class MainActivity extends BaseActivity<MainActivityPresenter, MainActivity> implements RadioGroup.OnCheckedChangeListener, BaseViewInterface{
    public static MainActivity mainActivity;

    @BindView(R.id.rb_home)
    RadioButton radioButton;
    @BindView(R.id.record)
    ImageView record;
    public static RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainActivity = this;
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab_bar);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton.setChecked(true);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("getDidddd", String.valueOf(getDistance(44,125.41,44.03,125.44)));
//                presenter.performOnClick();//test
//                Intent intent = new Intent(MainActivity.this, getLocationDemo.class);
//                MainActivity.this.startActivity(intent);】
                Intent intent = new Intent(MainActivity.this, tempChooseRecordActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected MainActivityPresenter getPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        presenter.createFragment(checkedId);
        Log.e("TAG", ""+checkedId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
