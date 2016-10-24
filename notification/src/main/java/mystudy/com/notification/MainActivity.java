package mystudy.com.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button show;
    private Button vanish;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //控件初始化的操作
        initView();
    }

    /**
     * 控件初始化的操作
     */
    private void initView() {
        show = (Button)findViewById(R.id.show_bt);
        vanish = (Button)findViewById(R.id.vanish_bt);
        show.setOnClickListener(this);
        vanish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //就是让通知栏里显示通知
            case R.id.show_bt:
                //进程间通信.主要是把一个动作交给另外一个应用程序来做的意图,就用PendingIntent包裹一下.
                //pendingIntent,可以打开一个四大组件,用其静态方法,get....
                //getActivity,1 上下文,2 请求码用不到就为0,3 意图对象,4 指定其点击后的状态标识
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel://110"));
//                Intent intent = new Intent(this,JumpActivity.class);//跳转到指定的Activity,不用StartActivity.
                PendingIntent pendingIntent=  PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                //获取系统的通知服务,上面的Intent操作就是用户点击通知栏时,自动进入拨号界面,
                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //直接new出来,该方式在安卓3.0以后已经过时了
//                Notification notification=new Notification();
                //使用链式调用的方法,创建Notification对象的同时往里面进行设置,是主流的创建方式,Builder的参数是上下文.
                Notification notification = new Notification.Builder(this)
                        //设置通知栏的标题
                        .setContentTitle("小狐狸爱吃糖")
                        //设置通知栏的通知内容
                        .setContentText("Here Are!!!")
                        //设置通知栏的图片(就是通知以来,在显示电量那一行出现的小图标)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //设置通知栏的大图片(就是通知栏拉下来显示的图标)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        //设置通知栏被点击后会执行的意图(比如跳转到指定的activity,比如打电话等等)
                        .setContentIntent(pendingIntent)
                        //当通知栏提示被点击执行时,通知栏会消失在桌面,不设置此方法,默认通知栏提示被点击后依然存在.所以必用此方法.
                        .setAutoCancel(true)
//                        .setSound(Uri.parse("")) //设置通知栏提示到来时的声音是什么.
                        //设置当通知栏提示到来时,手机每一次震动的时长.使用此功能记着加权限.<uses-permission android:name="android.permission.VIBRATE"/>
//                        .setVibrate(new long[]{100,200,300}) //不设置没有震动
                        // 使用系统默认声音，震动，led灯等设置
//                        .setDefaults(Notification.DEFAULT_ALL)
                        .build();
                //FLAG_NO_CLEAR 使通知栏提示取消不掉,一直存在.   FLAG_Auto_CLEAR 通知栏提示只要被点击一次就不会存在.
                notification.flags=Notification.FLAG_NO_CLEAR;
                //让通知显示在状态栏里.参数1,给通知起的ID,方便对其单独的操作(比如用cancel把指定通知取消掉) 参数2,就是Notification对象.
                notificationManager.notify(1, notification);
                break;
            //就是让通知栏里隐藏通知
            case  R.id.vanish_bt:
                //使用NotificationManager对象,取消掉指定int标识的通知提示.
                notificationManager.cancel(1);
                break;
            default:
        }
    }
}
