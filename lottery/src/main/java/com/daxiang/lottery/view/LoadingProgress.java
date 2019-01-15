package com.daxiang.lottery.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
//自定义控件，加载的小圆点来回切换
public class LoadingProgress extends View
{
    private int ballWidth;

    private int ballHeight;

    public LoadingProgress(Context context)
    {
        super(context);
        init();

    }

    public LoadingProgress(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private float desity;

    private void init()
    {
        desity = getResources().getDisplayMetrics().density;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ballWidth = (int) (10 * desity);
        ballHeight = (int) (10 * desity);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension((int) (ballWidth * 2 + (12 * desity)), ballHeight);
        if (currentPostion == -1)
        {
            currentPostion = getMeasuredWidth() / 2;
        }
    }

    private Paint mPaint;

    private int currentPostion = -1;

    private boolean b;

    private long lastTime;

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int speed = 0;
        long time = SystemClock.elapsedRealtime();
        if (time - lastTime > 50)
        {
            speed = w / 10;
        }
        else
        {
            speed = (int) (w * (time - lastTime) / 50 / 10);
        }

        mPaint.setColor(0xfffc9500);
        canvas.drawCircle(w - currentPostion, h / 2, ballHeight / 2, mPaint);
        mPaint.setColor(0xff0094eb);
        canvas.drawCircle(currentPostion, h / 2, ballHeight / 2, mPaint);
        if (currentPostion >= w - ballWidth || currentPostion <= ballWidth)
        {
            b = !b;
        }

        if (b)
        {
            currentPostion -= speed;
        }
        else
        {
            currentPostion += speed;
        }

        if (currentPostion > w - ballWidth)
        {
            currentPostion = w - ballWidth;
        }
        else if (currentPostion < ballWidth)
        {
            currentPostion = ballWidth;
        }

        postInvalidateDelayed(50);
        lastTime = SystemClock.elapsedRealtime();
    }

}
