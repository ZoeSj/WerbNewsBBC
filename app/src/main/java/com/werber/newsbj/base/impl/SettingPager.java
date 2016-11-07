package com.werber.newsbj.base.impl;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.werber.newsbj.R;
import com.werber.newsbj.base.BasePager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 这里面要添加的是一个聊天机器人,主要用于聊天通讯的
 * Created by Zoe on 2016/11/3.
 */
public class SettingPager extends BasePager implements View.OnClickListener, HttpGetDataListener {

    private HttpData httpData;
    private List<ListData> lists;
    private ListView lv;
    private EditText sendtext;
    private Button send_btn;
    private String content_str;
    private TextAdapter adapter;
    private String[] welcome_array;
    private double currentTime=0, oldTime = 0;
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        mTvTitle.setText("个人设置");
        mImgBtnMenu.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);
        View view = View.inflate(mActivity, R.layout.setting, null);
        lv = (ListView) view.findViewById(R.id.lv);
        sendtext = (EditText) view.findViewById(R.id.sendText);
        send_btn = (Button) view.findViewById(R.id.send_btn);
        lists = new ArrayList<ListData>();
        send_btn.setOnClickListener(this);
        adapter = new TextAdapter(lists, mActivity);
        lv.setAdapter(adapter);
        ListData listData;
        listData = new ListData(getRandomWelcomeTips(), ListData.RECEIVER,
                getTime());
        lists.add(listData);
       /* TextView text=new TextView(mActivity);
        text.setText("设置中心");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);*/



        //添加到Content中
        mFlContent.addView(view);

    }

    private String getRandomWelcomeTips() {
        String welcome_tip = null;
        welcome_array = mActivity.getResources()
                .getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random() * (welcome_array.length - 1));
        welcome_tip = welcome_array[index];
        return welcome_tip;
    }
    @Override
    public void getDataUrl(String data) {
        // System.out.println(data);
        parseText(data);
    }
    public void parseText(String str) {
        try {
            JSONObject jb = new JSONObject(str);
            // System.out.println(jb.getString("code"));
            // System.out.println(jb.getString("text"));
            ListData listData;
            listData = new ListData(jb.getString("text"), ListData.RECEIVER,
                    getTime());
            lists.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        getTime();
        content_str = sendtext.getText().toString();
        sendtext.setText("");
        String dropk = content_str.replace(" ", "");
        String droph = dropk.replace("\n", "");
        ListData listData;
        listData = new ListData(content_str, ListData.SEND, getTime());
        lists.add(listData);
        if (lists.size() > 30) {
            for (int i = 0; i < lists.size(); i++) {
                lists.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
        httpData = (HttpData) new HttpData(
                "http://www.tuling123.com/openapi/api?key=6af9822f5491fadfc142b53818bbd63a&info="
                        + droph, this).execute();
    }

    private String getTime() {
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        if (currentTime - oldTime >= 500) {
            oldTime = currentTime;
            return str;
        } else {
            return "";
        }

    }
}
