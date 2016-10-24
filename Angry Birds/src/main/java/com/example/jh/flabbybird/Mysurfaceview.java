package com.example.jh.flabbybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jh on 2016/8/2.
 */
public class Mysurfaceview extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    /**
     * 与SurfaceHolder绑定的Canvas
     */
    private Canvas mCanvas;
    /**
     * 用于绘制的线程
     */
    private Thread t;
    /**
     * 线程的控制开关
     */
    private boolean isRunning;
    /**
     * 当前View的尺寸
     */
    private int mWidth;
    private int mHeight;
    //控制画布的范围
    private RectF mGamePanelRect = new RectF();
    /**
     * 背景
     */
    private Bitmap mBg;
    /**
     * *********鸟相关**********************
     */
    private Bird mBird;
    private Bitmap mBirdBitmap;
    private Paint mPaint;
    /**
     * 地板
     */
    private Floor mFloor;
    private Bitmap mFloorBg;

    private int mSpeed;

    /**
     * *********管道相关**********************
     */
    /**
     * 管道
     */
    private Bitmap mPipeTop;
    private Bitmap mPipeBottom;
    private RectF mPipeRect;
    private int mPipeWidth;
    /**
     * 管道的宽度 60dp
     */
    private static final int PIPE_WIDTH = 60;

    private List<Pipe> mPipes = new ArrayList<Pipe>();

    /**
     * 游戏的状态
     *
     * @author zhy
     */
    private enum GameStatus {
        WAITING, RUNNING, STOP
    }

    /**
     * 记录游戏的状态
     */
    private GameStatus mStatus = GameStatus.WAITING;

    /**
     * 触摸上升的距离，因为是上升，所以为负值
     */
    private static final int TOUCH_UP_SIZE = -10;
    /**
     * 将上升的距离转化为px；这里多存储一个变量，变量在run中计算
     */
    private final int mBirdUpDis = UiUtils.dp2px(getContext(), TOUCH_UP_SIZE);

    private int mTmpBirdDis;
    /**
     * 鸟自动下落的距离
     */
    private final int mAutoDownSpeed = UiUtils.dp2px(getContext(), 2);
    /**
     * 两个管道间距离
     */
    private final int PIPE_DIS_BETWEEN_TWO = UiUtils.dp2px(getContext(), 100);
    /**
     * 记录移动的距离，达到 PIPE_DIS_BETWEEN_TWO 则生成一个管道
     */
    private int mTmpMoveDistance;

    /**
     * 记录需要移除的管道
     */
    private List<Pipe> mNeedRemovePipe = new ArrayList<Pipe>();


    //构造方法
    public Mysurfaceview(Context context) {
        this(context, null);
    }

    public Mysurfaceview(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取surfaceholder，是为了
        mHolder = getHolder();
        mHolder.addCallback(this);
        //初始化图片
        initBitmaps();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        // 初始化速度
        mSpeed = UiUtils.dp2px(getContext(), 2);


        setZOrderOnTop(true);// 设置画布 背景透明
        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);
        mPipeWidth = UiUtils.dp2px(getContext(), PIPE_WIDTH);

    }

    private void initBitmaps() {
        mBg = loadImageByResId(R.mipmap.bg);
        mBirdBitmap = loadImageByResId(R.mipmap.chat_with_agent);
        mFloorBg = loadImageByResId(R.mipmap.floor);
        mPipeTop = loadImageByResId(R.mipmap.hhhhh);
        mPipeBottom = loadImageByResId(R.mipmap.hhhhh);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // 开启线程
        isRunning = true;
        t = new Thread(this);
        t.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 通知关闭线程
        isRunning = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            switch (mStatus) {
                case WAITING:
                    mStatus = GameStatus.RUNNING;
                    break;
                case RUNNING:
                    mTmpBirdDis = mBirdUpDis;

                    break;
            }

        }

        return true;

    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();
            logic();
            draw();
            long end = System.currentTimeMillis();

            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        mGamePanelRect.set(0, 0, w, h);
        // 初始化mBird
        mBird = new Bird(getContext(), mWidth, mHeight, mBirdBitmap);
        // 初始化地板
        mFloor = new Floor(mWidth, mHeight, mFloorBg);
        // 初始化管道范围
        mPipeRect = new RectF(0, 0, mPipeWidth, mHeight);
        Pipe pipe = new Pipe(getContext(), w, h, mPipeTop, mPipeBottom);
        mPipes.add(pipe);

    }

    private void draw() {
        try {
            // 获得canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                // drawSomething..
                drawBg();
                drawBird();
                drawFloor();
                drawPipes();
                // 更新我们地板绘制的x坐标
                mFloor.setX(mFloor.getX() - mSpeed);
            }
        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void drawBird() {
        mBird.draw(mCanvas);
    }

    /**
     * 绘制管道
     */
    private void drawPipes() {
        for (Pipe pipe : mPipes) {
            pipe.setX(pipe.getX() - mSpeed);
            pipe.draw(mCanvas, mPipeRect);
        }
    }

    private void drawFloor() {
        mFloor.draw(mCanvas, mPaint);
    }

    /**
     * 绘制背景
     */
    private void drawBg() {
        mCanvas.drawBitmap(mBg, null, mGamePanelRect, null);
    }


    /**
     * 根据resId加载图片
     *
     * @param resId
     * @return
     */
    private Bitmap loadImageByResId(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    /**
     * 处理一些逻辑上的计算
     */
    private void logic() {
        switch (mStatus) {
            case RUNNING:

                // 管道移动
                for (Pipe pipe : mPipes) {
                    if (pipe.getX() < -mPipeWidth) {
                        mNeedRemovePipe.add(pipe);
                        continue;
                    }
                    pipe.setX(pipe.getX() - mSpeed);
                }
                //移除管道
                mPipes.removeAll(mNeedRemovePipe);

                //  Log.e("TAG", "现存管道数量：" + mPipes.size());
                // 更新我们地板绘制的x坐标，地板移动
                mFloor.setX(mFloor.getX() - mSpeed);
                // 管道
                mTmpMoveDistance += mSpeed;
                // 生成一个管道
                if (mTmpMoveDistance >= PIPE_DIS_BETWEEN_TWO) {
                    Pipe pipe = new Pipe(getContext(), getWidth(), getHeight(),
                            mPipeTop, mPipeBottom);
                    mPipes.add(pipe);
                    mTmpMoveDistance = 0;
                }
                //默认下落，点击时瞬间上升
                mTmpBirdDis += mAutoDownSpeed;
                mBird.setY(mBird.getY() + mTmpBirdDis);
                checkGameOver();
                break;

            case STOP: // 鸟落下
                // 如果鸟还在空中，先让它掉下来
                if (mBird.getY() < mFloor.getY() - mBird.getWidth()) {
                    mTmpBirdDis += mAutoDownSpeed;
                    mBird.setY(mBird.getY() + mTmpBirdDis);
                } else {
                    mStatus = GameStatus.WAITING;
                    initPos();
                }
                break;
            default:
                break;
        }

    }

    /**
     * 重置鸟的位置等数据
     */
    private void initPos() {
        mPipes.clear();
        mNeedRemovePipe.clear();
        //重置鸟的位置
        mBird.setY(mHeight * 2 / 3);
        //重置下落速度
        mTmpBirdDis = 0;
        mTmpMoveDistance = 0;
    }

    private void checkGameOver() {

        // 如果触碰地板，gg
        if (mBird.getY() > mFloor.getY() - mBird.getHeight()) {
            mStatus = GameStatus.STOP;
        }
        // 如果撞到管道
        for (Pipe wall : mPipes) {
            //已经穿过的
            if (wall.getX() + mPipeWidth < mBird.getX()) {
                continue;
            }
            if (wall.touchBird(mBird)) {
                mStatus = GameStatus.STOP;
                break;
            }
        }
    }
}
