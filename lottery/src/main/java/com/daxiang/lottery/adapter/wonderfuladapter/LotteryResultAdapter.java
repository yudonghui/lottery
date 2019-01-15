package com.daxiang.lottery.adapter.wonderfuladapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.JclqActivity;
import com.daxiang.lottery.activity.JczqActivity;
import com.daxiang.lottery.activity.NumberActivity;
import com.daxiang.lottery.activity.SfcAndRjcActivity;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.entity.LotteryResultMode;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GK on 2016/4/14.
 */
public class LotteryResultAdapter extends BaseAdapter {
    List<LotteryResultMode.DataBean> mDataBean = new ArrayList<>();
    public Context mContext;
    private LayoutInflater inflater;

    public LotteryResultAdapter(Context mContext, List<LotteryResultMode.DataBean> mDataBean) {
        this.mContext = mContext;
        this.mDataBean = mDataBean;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDataBean.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
//        if (view == null) {
        viewHolder = new ViewHolder();
        view = inflater.inflate(R.layout.lottery_result_item, viewGroup, false);
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_result_title);
        viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.layout_result);
        viewHolder.mFrlayout = (FrameLayout) view.findViewById(R.id.fl_result);
        viewHolder.mImageView = (ImageView) view.findViewById(R.id.jc_result);
        viewHolder.mScore = (TextView) view.findViewById(R.id.jc_score);
        view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
        LotteryResultMode.DataBean bean = mDataBean.get(i);
        String lotName = bean.getLotName();
        final String caizhong = String.valueOf(bean.getLotCode());
        if (caizhong.equals("42")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setJc(viewHolder.linearLayout, bean, viewHolder);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //防止连续点击
                    if (ClickUtils.isFastClick()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, JczqActivity.class);
                    intent.putExtra("lotcode", "42");
                    intent.putExtra("bunch", true);
                    intent.putExtra("who", "result");
                    mContext.startActivity(intent);
                }
            });
            return view;
        } else if (caizhong.equals("43")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setJc(viewHolder.linearLayout, bean, viewHolder);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //防止连续点击
                    if (ClickUtils.isFastClick()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, JclqActivity.class);
                    intent.putExtra("lotcode", "43");
                    intent.putExtra("bunch", true);
                    intent.putExtra("who", "result");
                    mContext.startActivity(intent);
                }
            });
            return view;
        }
        //拿到字段 1,2,0,3
        String number = bean.getAwardNumber();
        //分割
        String[] splitnumber = strToArray(number);
        if (caizhong.equals("23529")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("23528")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("21406")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("10022")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("51")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("52")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("35")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("33")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("19")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("11")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("36")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("50")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("13")) {
            viewHolder.tv_title.setText("篮彩单场" + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("16")) {
            viewHolder.tv_title.setText("半全场" + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("21")) {
            viewHolder.tv_title.setText("金银彩" + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("22")) {
            viewHolder.tv_title.setText("金银球" + "  [" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("30")) {
            viewHolder.tv_title.setText("冠亚军" + "  [" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("41")) {
            viewHolder.tv_title.setText("北京单场足彩" + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("42")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("43")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else if (caizhong.equals("54")) {
            viewHolder.tv_title.setText(lotName + "  [" + bean.getIssue() + "期" + "]");
            setSytle(viewHolder.linearLayout, caizhong, splitnumber);
        } else {
            viewHolder.tv_title.setVisibility(View.GONE);
            viewHolder.linearLayout.removeAllViews();
            viewHolder.linearLayout.setVisibility(View.GONE);
        }
        if (caizhong.equals("11") || caizhong.equals("19")) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //防止连续点击
                    if (ClickUtils.isFastClick()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, SfcAndRjcActivity.class);
                    intent.putExtra("lotcode", caizhong);
                    intent.putExtra("who", "result");
                    mContext.startActivity(intent);
                }
            });
        } else {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //防止连续点击
                    if (ClickUtils.isFastClick()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, NumberActivity.class);
                    intent.putExtra("lotcode", caizhong);
                    intent.putExtra("who", "result");
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
    }

    public void setJc(LinearLayout layout, LotteryResultMode.DataBean bean, ViewHolder mViewHolder) {
        mViewHolder.mFrlayout.setVisibility(View.VISIBLE);
        if ("42".equals(bean.getLotCode())) {
            mViewHolder.mImageView.setImageResource(R.drawable.jczq);
            mViewHolder.mScore.setBackgroundResource(R.drawable.shape_jz);
            String score = bean.getScore();
            String totalStr = bean.getHomeTeam() + "  " + score + "  " + bean.getGuestTeam();
            mViewHolder.mScore.setText(getSpan(totalStr, score));
        } else if ("43".equals(bean.getLotCode())) {
            mViewHolder.mImageView.setImageResource(R.drawable.jclq);
            mViewHolder.mScore.setBackgroundResource(R.drawable.shape_jl);
            String score = bean.getScore();
            String totalStr = bean.getGuestTeam() + "  " + score + "  " + bean.getHomeTeam();
            mViewHolder.mScore.setText(getSpan(totalStr, score));
        }
    }

    public SpannableString getSpan(String totalStr, String score) {
        int indexOf = totalStr.indexOf(score);
        SpannableString ss = new SpannableString(totalStr);
        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf, indexOf + score.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public void setSytle(LinearLayout linlayout, String caizhong, String[] splitnumber) {
        //开始循环遍历
        linlayout.removeAllViews();
        for (int a = 0; a < splitnumber.length; a++) {
            TextView tv = new TextView(mContext);
            tv.setTextSize(16);
            tv.setTextColor(Color.WHITE);
            if (caizhong.equals("33")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            }
            if (caizhong.equals("35")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            } else if (caizhong.equals("51")) {
                if (a == splitnumber.length - 1) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setBackgroundResource(R.drawable.widget_ball_red);
                    tv.setGravity(Gravity.CENTER);
                }
            }
            if (caizhong.equals("52") || caizhong.equals("54")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            }

            if (caizhong.equals("10022") || caizhong.equals("21406")
                    || caizhong.equals(LotCode.K3_CODE) || caizhong.equals(LotCode.SSC_CODE)) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            }
            if (caizhong.equals("23528")) {
                if (a == splitnumber.length - 1) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setBackgroundResource(R.drawable.widget_ball_red);
                    tv.setGravity(Gravity.CENTER);
                }
            }
            if (caizhong.equals("23529")) {
                if (a == splitnumber.length - 2) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else if (a == splitnumber.length - 1) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setBackgroundResource(R.drawable.widget_ball_red);
                    tv.setGravity(Gravity.CENTER);
                }
            }
            LinearLayout.LayoutParams layoutParams;
            if (caizhong.equals("16") || caizhong.equals("19") || caizhong.equals("11")) {
                tv.setPadding(10, 6, 10, 6);
                tv.setBackgroundResource(R.drawable.shape_rjc);
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5, 6, 0, 6);//4个参数按顺序分别是左上右下
            } else {
                tv.setPadding(6, 0, 6, 0);
                layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(35), DisplayUtil.dip2px(35));
                layoutParams.setMargins(5, 6, 5, 6);//4个参数按顺序分别是左上右下
            }

            tv.setText(splitnumber[a] + "");
            tv.setLayoutParams(layoutParams);
            linlayout.addView(tv);
        }
    }

    public class ViewHolder {
        TextView tv_title;
        LinearLayout linearLayout;
        FrameLayout mFrlayout;
        ImageView mImageView;
        TextView mScore;
    }

    public String[] strToArray(String number) {
        String a = number.replace(":", ",");
        return a.split(",");
    }
}
