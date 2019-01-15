package com.daxiang.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.daxiang.lottery.R;

/**
 * 自定义倒计时文本控件
 *
 * @author Administrator
 */
public class TimeTextView extends TextView implements Runnable {
    Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息
    private int[] times;
    private long mday, mhour, mmin, msecond;// 天，小时，分钟，秒
    private boolean run = false; // 是否启动了
    private endTimeListener timeListener ;
    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TimeTextView);
        array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TimeTextView);
        array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
    }
    public void setListener(endTimeListener timeListener ){
         this.timeListener=timeListener;
    }
    public interface endTimeListener{
        void callback();
    }
    public TimeTextView(Context context) {
        super(context);
    }

    public int[] getTimes() {
        return times;
    }

    public void setTimes(int[] times) {
        this.times = times;
        mday = times[0];
        mhour = times[1];
        mmin = times[2];
        msecond = times[3];
    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;
        if (msecond < 0&&mmin>0) {
            mmin--;
            msecond = 59;
            if (mmin < 0&&mhour>0) {
                mmin = 59;
                mhour--;
                if (mhour < 0&&mday>0) {
                    // 倒计时结束
                    mhour = 24;
                    mday--;
                }
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        // 标示已经启动
        run = true;

        ComputeTime();
        String strTime = mday + "天" + mhour + "小时" + mmin + "分"
                + msecond + "秒";
        if(mday<=0){
             strTime =  mhour + "小时" + mmin + "分"
                    + msecond + "秒";
        }
        if(mday<=0&&mhour<=0){
             strTime =  mmin + "分"
                    + msecond + "秒";
        }
        if(mday<=0&&mhour<=0&&mmin<=0){
             strTime =  mmin + "分"
                    + msecond + "秒";
        }
        if (mday <= 0 && mhour <= 0 && mmin <= 0 && msecond <= 0) {
            run=false;
            timeListener.callback();
            this.setText("停止销售");
            return;
        }
        this.setText(strTime);
        postDelayed(this, 1000);
    }

}
