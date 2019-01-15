package com.daxiang.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.daxiang.lottery.R;
import com.daxiang.lottery.utils.DisplayUtil;

/**
 * Created by Android on 2018/1/29.
 */

public class RedBlackView extends View {
    private Context mContext;
    private RectF mRectF;
    private Paint paintRed;
    private Paint paintBlack;
    private Paint paintIng;
    private int redNum;
    private int blackNum;
    private int ingNum;
    private int totalNum;
    private float circleWidth;//圆形的宽度
    private float textSize;//文字的大小
    private Paint paintCenter;
    private Paint paintRedText;
    private Paint paintBlackText;
    private Paint paintIngText;
    private int dp10;
    private int dp15;
    private int dp20;
    private int dp5;
    private int dp3;
    private int dp1;
    private int dp30;

    public RedBlackView(Context context) {
        super(context);
    }


    public RedBlackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RedBlackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        dp1 = DisplayUtil.dip2px(0.5);
        dp3 = DisplayUtil.dip2px(3);
        dp5 = DisplayUtil.dip2px(5);
        dp10 = DisplayUtil.dip2px(10);
        dp15 = DisplayUtil.dip2px(15);
        dp20 = DisplayUtil.dip2px(20);
        dp30 = DisplayUtil.dip2px(30);
        mRectF = new RectF();
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RedBlackView);
        redNum = typedArray.getInteger(R.styleable.RedBlackView_rbv_red, 0);
        blackNum = typedArray.getInteger(R.styleable.RedBlackView_rbv_black, 0);
        ingNum = typedArray.getInteger(R.styleable.RedBlackView_rbv_ing, 0);
        totalNum = typedArray.getInteger(R.styleable.RedBlackView_rbv_total, 10);
        circleWidth = typedArray.getDimension(R.styleable.RedBlackView_rbv_width, DisplayUtil.dip2px(30));
        textSize = typedArray.getDimension(R.styleable.RedBlackView_rbv_textSize, 12);
        if (redNum == 0 && blackNum == 0 && ingNum == 0) {
            blackNum = 10;
        }
        typedArray.recycle();
    }

    private void initPaint() {

        /*
        * Paint.Style.FILL    :填充内部
        * Paint.Style.FILL_AND_STROKE  ：填充内部和描边
        * Paint.Style.STROKE  ：仅描边
        * */
        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setColor(mContext.getResources().getColor(R.color.red_txt));
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(circleWidth);

        paintBlack = new Paint();
        paintBlack.setAntiAlias(true);
        paintBlack.setColor(mContext.getResources().getColor(R.color.gray_box));
        paintBlack.setStyle(Paint.Style.STROKE);
        paintBlack.setStrokeWidth(circleWidth);

        paintIng = new Paint();
        paintIng.setAntiAlias(true);
        paintIng.setColor(mContext.getResources().getColor(R.color.orange_let));
        paintIng.setStyle(Paint.Style.STROKE);
        paintIng.setStrokeWidth(circleWidth);

        paintCenter = new Paint();
        paintCenter.setAntiAlias(true);
        paintCenter.setColor(mContext.getResources().getColor(R.color.deep_txt));
        paintCenter.setTextSize(textSize);
        paintCenter.setTextAlign(Paint.Align.CENTER);

        paintRedText = new Paint();
        paintRedText.setAntiAlias(true);
        paintRedText.setColor(mContext.getResources().getColor(R.color.red_txt));
        paintRedText.setTextSize(textSize);
        paintRedText.setTextAlign(Paint.Align.RIGHT);

        paintBlackText = new Paint();
        paintBlackText.setAntiAlias(true);
        paintBlackText.setColor(mContext.getResources().getColor(R.color.gray_txt));
        paintBlackText.setTextSize(textSize);
        paintBlackText.setTextAlign(Paint.Align.LEFT);

        paintIngText = new Paint();
        paintIngText.setAntiAlias(true);
        paintIngText.setColor(mContext.getResources().getColor(R.color.orange_let));
        paintIngText.setTextSize(textSize);
        paintIngText.setTextAlign(Paint.Align.LEFT);

    }

    private float centerX;
    private float centerY;
    private float r;//外半径
    private float redX;
    private float redY;
    private float blackX;
    private float blackY;
    private float ingX;
    private float ingY;
    private float redAngle;
    private float blackAngle;
    private float ingAngle;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 48;
        int height = 48;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = Math.min(width, widthSize);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = Math.min(height, heightSize);
        }
        mRectF.left = (width - height + circleWidth) / 2;
        mRectF.top = circleWidth / 2;
        mRectF.right = (width + height - circleWidth) / 2;
        mRectF.bottom = height - circleWidth / 2;
        centerX = width / 2;
        centerY = height / 2 + circleWidth / 4;
        r = height / 2;
        redAngle = redNum * 360 / totalNum;
        blackAngle = blackNum * 360 / totalNum;
        ingAngle = ingNum * 360 / totalNum;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*canvas.drawCircle(100,100,50,paintRed);*/

        canvas.drawArc(mRectF, -90, blackAngle == 0 ? 0 : (blackAngle + 2), false, paintBlack);
        canvas.drawArc(mRectF, -90 + blackAngle, ingAngle == 0 ? 0 : (ingAngle + 2), false, paintIng);
        canvas.drawArc(mRectF, -90 + blackAngle + ingAngle, redAngle == 0 ? 0 : (redAngle + 2), false, paintRed);
        canvas.drawText("近" + totalNum + "单状态", centerX, centerY, paintCenter);
       /* if (redAngle == 0) {
            redX = 0;
            redY = 0;
        } else {
            if (redAngle < 90) {
                Log.e("度数", Math.sin(-30 * Math.PI / 180) + "");

                if (redAngle < 40) {
                    redX = (float) (mRectF.left + r - r * Math.sin((Math.PI / 180) * (redAngle / 2))) - dp10;
                    redY = (float) (r - r * Math.cos((Math.PI / 180) * (redAngle / 2)));
                } else {
                    redX = (float) (mRectF.left + r - r * Math.sin((Math.PI / 180) * (redAngle / 2)));
                    redY = (float) (r - r * Math.cos((Math.PI / 180) * (redAngle / 2))) - dp5;
                }

                paintRedText.setStrokeWidth(dp3);
                canvas.drawPoint(redX - dp10, redY, paintRedText);
                paintRedText.setStrokeWidth(dp1);
                canvas.drawLine(redX - dp10, redY, mRectF.left - dp30, redY, paintRedText);
                canvas.drawLine(mRectF.left - dp30, redY, mRectF.left - dp30, r, paintRedText);
                canvas.drawLine(mRectF.left - dp30, r, mRectF.left - 2 * dp20, r, paintRedText);
                canvas.drawText("红" + redNum + "场", mRectF.left - 2 * dp20 - dp5, r + dp5, paintRedText);
            } else {
                redX = mRectF.left;
                redY = r;
                paintRedText.setStrokeWidth(dp3);
                canvas.drawPoint(redX - dp15, redY, paintRedText);
                paintRedText.setStrokeWidth(dp1);
                canvas.drawLine(redX - dp15, redY, redX - 2 * dp20, redY, paintRedText);
                canvas.drawText("红" + redNum + "场", redX - 2 * dp20 - dp5, redY + dp5, paintRedText);
            }
        }
        if (ingAngle == 0) {
            ingX = 0;
            ingY = 0;
        } else {
            if (redAngle < 90) {
                ingX = (float) (mRectF.left + r - Math.abs(r * Math.cos((Math.PI / 180) * (90 - ingAngle - redAngle))));
                ingY = (float) (r - r * Math.sin((Math.PI / 180) * (90 - ingAngle - redAngle)));
                paintIngText.setStrokeWidth(dp3);
                canvas.drawPoint(ingX - dp15, ingY - dp20, paintIngText);
                paintIngText.setStrokeWidth(dp1);
                canvas.drawLine(ingX - dp15, ingY - dp20, mRectF.left - dp20, ingY - dp20, paintIngText);
                canvas.drawLine(mRectF.left - dp20, ingY - dp20, mRectF.left - dp20, r + 2 * dp20, paintIngText);
                canvas.drawLine(mRectF.left - dp20, r + 2 * dp20, mRectF.left - 2 * dp20, r + 2 * dp20, paintIngText);
                paintIngText.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("进行中" + ingNum + "场", mRectF.left - 2 * dp20 - dp5, r + 2 * dp20 + dp5, paintIngText);
            } else if (redAngle >= 90 && redAngle < 180) {
                ingX = (float) (mRectF.left + r - Math.abs(r * Math.cos((Math.PI / 180) * (redAngle - 90)))) - dp15;
                ingY = (float) (r + r * Math.sin((Math.PI / 180) * (redAngle - 90))) + dp5;
                paintIngText.setStrokeWidth(dp3);
                canvas.drawPoint(ingX, ingY, paintIngText);
                paintIngText.setStrokeWidth(dp1);
                canvas.drawLine(ingX, ingY, mRectF.left - 2 * dp20, ingY, paintIngText);
                paintIngText.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("进行中" + ingNum + "场", mRectF.left - 2 * dp20 - dp5, ingY + dp5, paintIngText);
            } else {
                if (blackAngle < 90) {
                    ingX = (float) (mRectF.left + r + Math.abs(r * Math.cos((Math.PI / 180) * (90 - blackAngle - ingAngle / 2))));
                    ingY = (float) (r - r * Math.sin((Math.PI / 180) * (90 - blackAngle - ingAngle / 2))) + dp5;
                    paintIngText.setStrokeWidth(dp3);
                    canvas.drawPoint(ingX, ingY, paintIngText);
                    paintIngText.setStrokeWidth(dp1);
                    canvas.drawLine(ingX, ingY, ingX + 2 * dp20, ingY, paintIngText);
                    paintIngText.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("进行中" + ingNum + "场", ingX + 2 * dp20 + dp5, ingY + dp5, paintIngText);
                } else {
                    ingX = (float) (mRectF.left + r + Math.abs(r * Math.sin((Math.PI / 180) * (180 - ingAngle - blackAngle)))) + dp10;
                    ingY = (float) (r + r * Math.cos((Math.PI / 180) * (180 - ingAngle - blackAngle)));
                    paintIngText.setStrokeWidth(dp3);
                    canvas.drawPoint(ingX + dp10, ingY - dp10, paintIngText);
                    paintIngText.setStrokeWidth(dp1);
                    canvas.drawLine(ingX + dp10, ingY - dp10, mRectF.right + 2 * dp20, ingY - dp10, paintIngText);
                    // canvas.drawLine(mRectF.left - dp20, ingY - dp20, mRectF.left - dp20, r + 2 * dp20, paintIngText);
                    //canvas.drawLine(mRectF.left - dp20, r + 2 * dp20, mRectF.left - 2 * dp20, r + 2 * dp20, paintIngText);
                    paintIngText.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("进行中" + ingNum + "场", mRectF.right + 2 * dp20 + dp5, ingY - dp5, paintIngText);
                }

            }
        }
        if (blackAngle == 0) {
            blackX = 0;
            blackY = 0;
        } else {
            if (blackAngle < 90) {
                //  Log.e("度数", Math.sin(30 * Math.PI / 180) + "");
                blackX = (float) (mRectF.left + r + r * Math.sin((Math.PI / 180) * (blackAngle / 2)));
                blackY = (float) (r - r * Math.cos((Math.PI / 180) * (blackAngle / 2))) + dp5;
                paintBlackText.setStrokeWidth(dp3);
                canvas.drawPoint(blackX, blackY, paintBlackText);
                paintBlackText.setStrokeWidth(dp1);
                canvas.drawLine(blackX, blackY, mRectF.right + 2 * dp20, blackY, paintBlackText);
                // canvas.drawLine(mRectF.right + dp20, blackY - dp10, mRectF.right + dp20, r, paintBlackText);
                //canvas.drawLine(mRectF.right + dp20, r, mRectF.right + 2 * dp20, r, paintBlackText);
                canvas.drawText("黑" + blackNum + "场", mRectF.right + 2 * dp20 + dp5, blackY + dp5, paintBlackText);
            } else {
                blackX = mRectF.right;
                blackY = r;
                paintBlackText.setStrokeWidth(dp3);
                canvas.drawPoint(blackX + dp15, blackY, paintBlackText);
                paintBlackText.setStrokeWidth(dp1);
                canvas.drawLine(blackX + dp15, blackY, blackX + 2 * dp20, blackY, paintBlackText);
                canvas.drawText("黑" + blackNum + "场", blackX + 2 * dp20 + dp5, blackY + dp5, paintBlackText);
            }
        }*/
    }

    public void setRedNum(int redNum) {
        this.redNum = redNum;
    }

    public void setBlackNum(int blackNum) {
        this.blackNum = blackNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public void setIngNum(int ingNum) {
        this.ingNum = ingNum;
    }
}
