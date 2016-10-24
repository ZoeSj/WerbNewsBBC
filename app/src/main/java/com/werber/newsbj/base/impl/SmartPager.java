package com.werber.newsbj.base.impl;

import android.app.Activity;
import android.view.View;

import com.werber.newsbj.R;
import com.werber.newsbj.base.BasePager;


public class SmartPager extends BasePager {
    public SmartPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        mTvTitle.setText("校园广播");
        setSlidingMenuEnable(true);
        View view=View.inflate(mActivity, R.layout.smartpager,null);
        initView();
        /*TextView text=new TextView(mActivity);
        text.setText("特色广播");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);*/

        //添加到Content中
        mFlContent.addView(view);

    }

}
