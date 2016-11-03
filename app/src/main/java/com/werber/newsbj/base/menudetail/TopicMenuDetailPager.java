package com.werber.newsbj.base.menudetail;

import android.app.Activity;
import android.view.View;

import com.werber.newsbj.R;
import com.werber.newsbj.base.BaseMenuDetailPager;

/**
 * 菜单详情页-专题
 */
public class TopicMenuDetailPager extends BaseMenuDetailPager {

    public TopicMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.topic,null);

       /* TextView text = new TextView(mActivity);
        text.setText("菜单详情页-专题");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);*/

        return view;
    }

}
