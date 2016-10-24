package com.werber.newsbj.base.impl;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.werber.newsbj.R;
import com.werber.newsbj.base.BasePager;

public class GovPager extends BasePager {

    private Button show_bt;
    private Button vanish_bt;

    public GovPager(Activity activity) {

        super(activity);
    }

    @Override
    public void initData() {
        mTvTitle.setText("七嘴八舌");
        mImgBtnMenu.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);
        View view=View.inflate(mActivity, R.layout.govpager,null);
        show_bt = (Button)view.findViewById(R.id.show_bt);
        vanish_bt = (Button) view.findViewById(R.id.vanish_bt);

        //监听两个按钮的button事件
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.show_bt:
                        show_bt.setText("Today is a nice day!");
                    case R.id.vanish_bt:
                        show_bt.setText("Tomorrow is nice!");
                }

            }
        };
        //添加到Content中
        mFlContent.addView(view);
    }
}
