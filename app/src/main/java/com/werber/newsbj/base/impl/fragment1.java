package com.werber.newsbj.base.impl;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.werber.newsbj.R;

/**
 * Created by Administrator on 2016/10/1.
 */
public class fragment1 extends Fragment {

    private Activity homeActivity;
    private EditText activity_et;
    private View view;
    private TextView fragment_tv;
    private EditText fragment_et;
    private TextView activity_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, null);
        homeActivity = getActivity();
        ActivityInit();
        fragemtnInit();
        return view;
    }
    private void fragemtnInit() {
        fragment_tv = (TextView) view.findViewById(R.id.fragment_tv);
        fragment_et = (EditText) view.findViewById(R.id.fragment_et);
        activity_tv = (TextView) homeActivity.findViewById(R.id.Activity_tv);
        Button fragment_callChild = (Button) view.findViewById(R.id.fragment_callChild);
        fragment_callChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = fragment_et.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(homeActivity,"不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                activity_tv.setText(trim);
            }
        });

    }
    private void ActivityInit() {
        Button Activity_callChild =(Button) homeActivity.findViewById(R.id.Activity_callChild);
        activity_et = (EditText) homeActivity.findViewById(R.id.Activity_et);

        Activity_callChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String trim = activity_et.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(homeActivity,"不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                fragment_tv.setText(trim);
            }
        });
    }
}
