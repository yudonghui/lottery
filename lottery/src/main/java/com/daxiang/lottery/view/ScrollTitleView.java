package com.daxiang.lottery.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daxiang.lottery.R;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.Vector;

/**
 * @author yudonghui
 * @date 2017/12/20
 * @describe May the Buddha bless bug-free!!!
 */
public class ScrollTitleView extends LinearLayout {
    private View mInflate;
    private Context mContext;
    private LinearLayout mLl_titlebar;
    private LinearLayout mLl_title;
    private View mTitleStatus;
    private int vpHeight;

    public ScrollTitleView(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.scroll_titlebar, this);
        init();
    }

    public ScrollTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.scroll_titlebar, this);
        init();
    }

    private void init() {
        vpHeight = DisplayUtil.dip2px(145);
        callbacks = new Vector<>();
        scanThread = new ScanThread(callbacks);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLl_titlebar = (LinearLayout) findViewById(R.id.ll_titlebar);
        mLl_title = (LinearLayout) findViewById(R.id.ll_title);
        mTitleStatus = (View) findViewById(R.id.titleStatus);
        int titleBarHeight = getResources().getDimensionPixelOffset(R.dimen.titlebar_heigh);
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dimensionPixelSize);
                setView(layoutParams);
            }
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleBarHeight);
            setTitleHeight(layout);
        }
    }

    /**
     * 获取待监控的view对象
     * 实时调起线程，监控是否scroll停止，来判断是否需要显示imageView
     *
     * @param targetView 需要监控的对象
     */
    //待滚动的容器
    private View targetView;
    //扫描target滚动停止线程是否正在运行
    private boolean isStarting = false;


    //扫描线程
    private ScanThread scanThread;
    //扫描线程是否需要停止，当Activity销毁时，避免OOM
    private boolean isQuit = false;
    //handler发出的延时任务队列
    private Vector<MyCallback> callbacks;
    private Handler mHandler = new Handler();

    public void tellMe(View targetView) {
        if (targetView == null)
            throw new IllegalArgumentException("please set targetView who to scrollTo");
        if (this.targetView == null)
            this.targetView = targetView;
        if (!isStarting) {
            new Thread(scanThread).start();
        }
    }

    private class ScanThread implements Runnable {
        private Vector<MyCallback> callbacks;

        public ScanThread(Vector<MyCallback> callbacks) {
            this.callbacks = callbacks;
        }

        @Override
        public void run() {
            isStarting = true;
            while (!isQuit) {
                try {
                    Thread.sleep(100);
                    MyCallback callback = new MyCallback();
                    callbacks.add(callback);
                    mHandler.postDelayed(callback, 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //线程退出，清空任务
            int size = callbacks.size();
            for (int i = 0; i < size; i++) {
                mHandler.removeCallbacks(callbacks.get(i));
            }
            //恢复默认值
            isQuit = false;
            isStarting = false;
        }
    }

    /**
     * 退出，终止扫描线程
     */
    public void clearCallBacks() {
        this.isQuit = true;
    }

    private class MyCallback implements Runnable {
        @Override
        public void run() {
            /**
             * 获取实时的卷动值，不要传递scroll值给我
             */
            int scrollY = targetView.getScrollY();
            if (scrollY <= 0) {
                mLl_titlebar.setAlpha(0);
                clearCallBacks();
            } else if (scrollY < vpHeight) {
                Log.e("透明度", (float) scrollY / vpHeight + "");
                mLl_titlebar.setAlpha((float) scrollY / vpHeight);
            } else {
                mLl_titlebar.setAlpha(1);
                clearCallBacks();
            }
        }
    }

    public void setView(LinearLayout.LayoutParams layoutParams) {
        mTitleStatus.setLayoutParams(layoutParams);
        mTitleStatus.setVisibility(VISIBLE);
    }

    public void setTitleHeight(LinearLayout.LayoutParams layoutParams) {
        mLl_title.setLayoutParams(layoutParams);
    }
}
