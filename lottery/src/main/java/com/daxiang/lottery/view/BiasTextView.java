package com.daxiang.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.daxiang.lottery.R;

/**
 * @author yudonghui
 * @date 2017/7/7
 * @describe May the Buddha bless bug-free!!!
 */
public class BiasTextView extends TextView {
    public int getmDegrees() {
        return mDegrees;
    }

    public void setmDegrees(int mDegrees) {
        this.mDegrees = mDegrees;
        invalidate();
    }

    private int mDegrees;

    public BiasTextView(Context context) {
        super(context, null);
    }

    public BiasTextView(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.textViewStyle);
        this.setGravity(Gravity.CENTER);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BiasTextView);
        mDegrees = a.getDimensionPixelSize(R.styleable.BiasTextView_degree, 330);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        canvas.rotate(mDegrees, this.getWidth() / 2f, this.getHeight() / 2f);
        super.onDraw(canvas);
        canvas.restore();
    }

    public void adjustTvTextSize(int maxWidth, String text) {
        int avaiWidth = maxWidth - this.getPaddingLeft() - this.getPaddingRight();

        if (avaiWidth <= 0) {
            return;
        }

        TextPaint textPaintClone = new TextPaint(this.getPaint());
        // note that Paint text size works in px not sp
        float trySize = textPaintClone.getTextSize();

        while (textPaintClone.measureText(text) > avaiWidth) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        this.setText(text);
    }
}
