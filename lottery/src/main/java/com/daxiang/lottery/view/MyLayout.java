package com.daxiang.lottery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MyLayout extends LinearLayout {
    private Scroller mScroller;
    private GestureDetector mGestureDetector;
    private int heightGg;

    public void setData(int heightGg) {
        this.heightGg = heightGg;
    }

    public MyLayout(Context context) {
        this(context, null);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context,
                new GestureListenerImpl());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 必须执行postInvalidate()从而调用computeScroll()
            // 其实,在此调用invalidate();亦可
            postInvalidate();
        }
        super.computeScroll();
    }

    private float downLocation;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float y = event.getY();
                if (y - downLocation < -heightGg / 2 || (y - downLocation > 0 && y - downLocation < heightGg / 2)) {
                    // 手指抬起时回到最初位置
                    prepareScroll(0, 0);
                } else prepareScroll(0, -heightGg);

                break;
            case MotionEvent.ACTION_DOWN:
                downLocation = event.getY();
                return mGestureDetector.onTouchEvent(event);
            default:
                // 其余情况交给GestureDetector手势处理
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    class GestureListenerImpl implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            int disY = (int) ((distanceY - 0.5) / 2);
            beginScroll(0, disY);
            return false;
        }

        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

    }

    // 滚动到初始位置
    protected void prepareScroll(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        beginScroll(dx, dy);
    }

    // 设置滚动的相对偏移
    protected void beginScroll(int dx, int dy) {
        // 第一,二个参数起始位置;第三,四个滚动的偏移量
        int finalX = mScroller.getFinalX();
        int finalY = mScroller.getFinalY();
        Log.e("λ��finalX",finalX+"");
        Log.e("λ��finalY",finalY+"");
        Log.e("λ��dx",dx+"");
        Log.e("λ��dy",dy+"");
        mScroller.startScroll(finalX, finalY, dx,
                dy);
        // 必须执行invalidate()从而调用computeScroll()
        invalidate();
    }


}