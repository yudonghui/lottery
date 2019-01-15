package com.daxiang.lottery.adapter.balladapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.LotteryResultListData;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.List;

/**
 * Created by GK on 2016/4/14.
 */
public class LotteryResultListAdapter extends BaseAdapter {
    List<LotteryResultListData.DataBean.ItemBean> mlist;
    Context mContext;
    LayoutInflater inflater;

    public LotteryResultListAdapter(Context mContext, List<LotteryResultListData.DataBean.ItemBean> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.lottery_resultlist_item, viewGroup, false);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_result_title);
            viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.layout_result);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LotteryResultListData.DataBean.ItemBean bean = mlist.get(i);
        String caizhong = String.valueOf(bean.getLotCode());
        if (caizhong.equals("11")) {
            viewHolder.tv_title.setText("胜负彩" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("13")) {
            viewHolder.tv_title.setText("篮彩单场" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("16")) {
            viewHolder.tv_title.setText("半全场" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("18")) {
            viewHolder.tv_title.setText("进球彩" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("19")) {
            viewHolder.tv_title.setText("任九场" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("21")) {
            viewHolder.tv_title.setText("金银彩" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("22")) {
            viewHolder.tv_title.setText("金银球" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("30")) {
            viewHolder.tv_title.setText("冠亚军" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("33")) {
            viewHolder.tv_title.setText("排列三" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("35")) {
            viewHolder.tv_title.setText("排列五" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("36")) {
            viewHolder.tv_title.setText("江西快3" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("50")) {
            viewHolder.tv_title.setText("重庆时时彩" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("41")) {
            viewHolder.tv_title.setText("北京单场足彩" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("42")) {
            viewHolder.tv_title.setText("竞彩足球" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("43")) {
            viewHolder.tv_title.setText("竞彩篮球" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("51")) {
            viewHolder.tv_title.setText("双色球" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("52")) {
            viewHolder.tv_title.setText("福彩3D" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("54")) {
            viewHolder.tv_title.setText("十一选五" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("10022")) {
            viewHolder.tv_title.setText("七星彩" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("21406")) {
            viewHolder.tv_title.setText("十一运夺金" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("23528")) {
            viewHolder.tv_title.setText("七乐彩" + "[" + bean.getIssue() + "期" + "]");
        } else if (caizhong.equals("23529")) {
            viewHolder.tv_title.setText("超级大乐透" + "[" + bean.getIssue() + "期" + "]");
        } else {
            viewHolder.tv_title.setVisibility(View.GONE);
            viewHolder.linearLayout.setVisibility(View.GONE);
        }
        String number = bean.getAwardNumber();
        //分割
        String[] splitnumber = strToArray(number);
        viewHolder.linearLayout.removeAllViews();
        //开始循环遍历
        for (int a = 0; a < splitnumber.length; a++) {
            TextView tv = new TextView(mContext);
            tv.setTextSize(16);
            tv.setTextColor(Color.WHITE);
            if (caizhong.equals("33")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            } else {
                tv.setBackgroundColor(Color.parseColor("#3b9c0e"));
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
                    ||caizhong.equals("36")||caizhong.equals("50")) {
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
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(35), DisplayUtil.dip2px(35));
                //layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            tv.setPadding(6, 0, 6, 0);
            tv.setText(splitnumber[a] + "");
            // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
            layoutParams.setMargins(5, 6, 5, 6);//4个参数按顺序分别是左上右下
            tv.setLayoutParams(layoutParams);
            viewHolder.linearLayout.addView(tv);
        }
        return view;
    }

    public class ViewHolder {
        TextView tv_title;
        LinearLayout linearLayout;
    }

    public String[] strToArray(String number) {
        String a = number.replace(":", ",");
        return a.split(",");
    }
}
