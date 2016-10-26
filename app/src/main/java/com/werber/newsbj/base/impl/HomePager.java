package com.werber.newsbj.base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.werber.newsbj.R;
import com.werber.newsbj.base.BasePager;

/**
 * Created by acer-pc on 2015/11/13.
 */
public class HomePager extends BasePager {

    public Button button;
    public Button load_btn;
    public Button advance_btn;
    public Button reload_btn;
    public Button  retreat_btn;

    private WebView show_wb;
    private EditText path_et;
    private ProgressBar webView_pb;
    public HomePager(Activity activity) {

        super(activity);
    }

    @Override
    public void initData() {
        mTvTitle.setText("新华BBC");
        mImgBtnMenu.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);

        View view=View.inflate(mActivity, R.layout.homepager,null);

        show_wb = (WebView) view.findViewById(R.id.show_wb);
        path_et = (EditText) view.findViewById(R.id.path_et);
        load_btn = (Button) view.findViewById(R.id.load_btn);
        advance_btn=(Button) view.findViewById(R.id.advance_btn);
        reload_btn=(Button)view.findViewById(R.id.reload_btn);
        retreat_btn=(Button)view.findViewById(R.id.retreat_btn);


        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = path_et.getText().toString().trim();
        if(TextUtils.isEmpty(trim)){
            Toast.makeText(mActivity, "不能输入为空", Toast.LENGTH_SHORT).show();
            return;
        }
//                加载用户输入的网址
                show_wb.loadUrl("http://"+trim);
            }
        });
        advance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(show_wb.canGoForward()){//进行一个状态的判断,看是否可以前进
                    show_wb.goForward();//可以前进,就进行前进操作
                }
            }
        });
        reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_wb.reload();
            }
        });
        retreat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(show_wb.canGoBack()){//进行一个状态的判断,看是否可以后退
                    show_wb.goBack();//可以前进,就进行后退操作
                }
            }
        });

        webView_pb = (ProgressBar) view.findViewById(R.id.webView_pb);
//        加载一个网页的方法
       show_wb.loadUrl("http://115.159.66.239/JDcom/");
//        使用webView对象,进行的一些初始化设置
        initWebview();
//        使用webViewSettings对webVIew进行一系列初始化的设置
        initWebViewSettings();

        //动态添加一个TextView
       /* TextView text=new TextView(mActivity);
        text.setText("新华BBC");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);*/

        //添加到Content中
        mFlContent.addView(view);

    }
    /**
     * 使用webView对象,进行的一些初始化设置
     */
    private void initWebview() {
//        此方法的作用是,当在webView进点击时,不跳转到游览器的设置(也就是不打开新的Activity),而是在本app里进行操作
        show_wb.setWebViewClient(new WebViewClient() {
            /**
             * 给WebView加一个事件监听对象（WebViewClient)并重写shouldOverrideUrlLoading，
             * 可以对网页中超链接按钮的响应
             * 当按下某个连接时WebViewClient会调用这个方法，并传递参数：当前响应的的url地址
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 此处可添加一些逻辑：是否拦截此url，自行处理
                // 下方2行代码是指在当前的webview中跳转到新的url
                view.loadUrl(url);
                return true;

            }
        });
//        触摸焦点起作用（如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件
        show_wb.requestFocus();

//      该监听事件是指UI(界面)发送改变时进行各监听.
        show_wb.setWebChromeClient(new WebChromeClient() {
            //     在WebView开始加载网页时，显示进度框；加载完毕时，隐藏进度框
            public void onProgressChanged(WebView view, int newProgress) {
//                进度条我们在布局文件里设置的是隐藏,在这里我们要让他显示出来
                webView_pb.setVisibility(View.VISIBLE);
//                加载进度条的进度
                webView_pb.setProgress(newProgress);
//                当进度条加载到一百时,我们要把他进行隐藏
                if (newProgress == 100) {
                    webView_pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            //通过title的参数,可以获取到访问网页的头.
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Toast.makeText(mActivity, "加载" + title + "中", Toast.LENGTH_SHORT).show();
                super.onReceivedTitle(view, title);
            }
        });}

    /**
     * 对网页控件进行一系列的初始化设置
     */

    private void initWebViewSettings() {
        WebSettings webSet = show_wb.getSettings();
//        webSet.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webSet.setJavaScriptEnabled(true);
        webSet.setSupportZoom(true);
//        是webView只加载文字,而不加载图片,为用户省流量.
//        webSet.setBlockNetworkImage(true);
    }

}
