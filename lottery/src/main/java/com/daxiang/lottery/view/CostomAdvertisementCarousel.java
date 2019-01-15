package com.daxiang.lottery.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.HomeAd;
import com.daxiang.lottery.utils.StringUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public class CostomAdvertisementCarousel extends LinearLayout {
    private TextView mTextView1, mTextView2;
    private Handler handler;
    private boolean isShow;
    private int startY1, endY1, startY2, endY2;
    private Runnable runnable;
    private List<HomeAd.DataBean> list;
    private int position = 0;
    private Context mContext;

    public CostomAdvertisementCarousel(Context context) {
        this(context, null);
        mContext = context;
    }

    public CostomAdvertisementCarousel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public CostomAdvertisementCarousel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_scroll_banner, this);
        mTextView1 = (TextView) view.findViewById(R.id.textView1);
        mTextView2 = (TextView) view.findViewById(R.id.textView2);
        addListener();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                isShow = !isShow;
                position = position + 1;
                if (position >= list.size())
                    position = 0;
                String userName = list.get(position).getUserName();
                String prize = list.get(position).getPrize();
                String lotCode = list.get(position).getLotCode();
                String hintName = StringUtil.hintString(userName);
                String hint = "恭喜" + hintName + "投注" + lotCode + "获得" + prize + "元奖金";
                int index = hint.indexOf(prize);
                SpannableStringBuilder ss = new SpannableStringBuilder(hint);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)),
                        index, hint.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (isShow) {
                    mTextView1.setText(ss);
                } else {
                    mTextView2.setText(ss);
                }
               /* if (isShow) {
                    mTv1.setText(content1);
                    mTv2.setText(content2);
                } else {
                    mTv3.setText(content1);
                    mTv4.setText(content2);
                }*/
                startY2 = isShow ? 0 : getHeight();
                endY2 = isShow ? -getHeight() : 0;
                startY1 = isShow ? getHeight() : 0;
                endY1 = isShow ? 0 : -getHeight();
                ObjectAnimator.ofFloat(mTextView1, "translationY", startY1, endY1).setDuration(300).start();
                ObjectAnimator.ofFloat(mTextView2, "translationY", startY2, endY2).setDuration(300).start();
                handler.postDelayed(runnable, 3000);
            }
        };

    }

    private void addListener() {
        mTextView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTextView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setListener(){
        mTextView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
            }
        });
        mTextView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<HomeAd.DataBean> getList() {
        return list;
    }

    public void setList(List<HomeAd.DataBean> list) {
        this.list = list;
    }

    public void startScroll() {
        handler.post(runnable);
    }

    public void stopScroll() {
        handler.removeCallbacks(runnable);
    }
}
