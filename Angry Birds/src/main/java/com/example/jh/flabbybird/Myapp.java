package com.example.jh.flabbybird;

import android.app.Application;
import android.content.Context;

/**
 * Created by jh on 2016/8/3.
 */
public class Myapp extends Application {
    private static Context context;

    //	创建MyApp的时候会调用这个方法，MyApp是在程序一运行的时候就会创建。
    @Override
    public void onCreate() {
        context = this;
    }

    /** 获取Application类型的上下文，因为上下文很常用，所以我们在Applicaiton中提供这样的变量，方便使用 */
    public static Context getContext() {
        return context;
    }
}
