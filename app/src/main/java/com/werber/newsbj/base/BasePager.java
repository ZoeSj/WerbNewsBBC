package com.werber.newsbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.werber.newsbj.R;
import com.werber.newsbj.activity.MainActivity;

/**
 * 就是一个类
 * 五个标签类的基类
 * Created by Zoe on 2016/11/3.
 */
public class BasePager {

    public Activity mActivity;
    public View mRootView;
    public TextView mTvTitle;//标题
    public FrameLayout mFlContent;//内容
    public ImageButton mImgBtnMenu;
    public ImageButton imBtnPhotoType;


    public BasePager(Activity activity){
        mActivity=activity;

        initView();
    }

    public void initView(){

        mRootView=View.inflate(mActivity, R.layout.base_pager,null);
        mTvTitle= (TextView) mRootView.findViewById(R.id.tv_title);
        mFlContent= (FrameLayout) mRootView.findViewById(R.id.fl_content);
        mImgBtnMenu= (ImageButton) mRootView.findViewById(R.id.Imbtn_menu);

        imBtnPhotoType= (ImageButton) mRootView.findViewById(R.id.Imbtn_photo_type);

        mImgBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
    }

    public void initData(){

    }

    /**
     * 切换SlidingMenu开关状态
     */
    private void toggleSlidingMenu() {
        MainActivity mainUi= (MainActivity) mActivity;
        SlidingMenu slidingMenu=mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }


    //设置SlidingMenu是否出现
    public void setSlidingMenuEnable(Boolean enabled){
        MainActivity mainUi= (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        if(enabled){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
