package com.werber.newsbj.base.impl;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.werber.newsbj.R;
import com.werber.newsbj.base.BasePager;

import java.io.File;


public class SmartPager extends BasePager {

    VideoView videoView;
    MediaController mController;
    public SmartPager(Activity activity) {

        super(activity);
    }

    @Override
    public void initData() {
        mTvTitle.setText("校园广播");
        mImgBtnMenu.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);
        View view=View.inflate(mActivity, R.layout.smartpager,null);
        mActivity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        // 获取界面上VideoView组件
        videoView = (VideoView) view.findViewById(R.id.video);
        // 创建MediaController对象
        mController = new MediaController(mActivity);
        File video = new File("/storage/emulated/0/DCIM/Camera/hello.mp4");
        if(video.exists())
        {
            videoView.setVideoPath(video.getAbsolutePath());
            // 设置videoView与mController建立关联
            videoView.setMediaController(mController);
            // 设置mController与videoView建立关联
            mController.setMediaPlayer(videoView);
            // 让VideoView获取焦点
            videoView.requestFocus();
        }

       /* TextView text=new TextView(mActivity);
        text.setText("特色广播");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);*/

        //添加到Content中
        mFlContent.addView(view);

    }

}
