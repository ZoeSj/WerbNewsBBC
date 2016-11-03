package com.werber.newsbj.base.impl;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.werber.newsbj.R;

/**
 * Created by Administrator on 2016/10/9.
 */
public class fragment5 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment5,null);
        return view;
    }
}
