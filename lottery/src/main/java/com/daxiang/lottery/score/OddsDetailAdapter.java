package com.daxiang.lottery.score;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.OddsDetailData;

import java.util.List;

/**
 * Created by Android on 2018/3/29.
 */

public class OddsDetailAdapter extends BaseAdapter {
    private final int colorGreen;
    private final int colorRed;
    private final int colorBlack;
    private Context mContext;
    List<OddsDetailData.DataBean.ItemsBean> mOddsList;
    int type;//1,欧赔  2,亚盘

    public OddsDetailAdapter(Context mContext, List<OddsDetailData.DataBean.ItemsBean> mOddsList, int type) {
        this.mContext = mContext;
        this.mOddsList = mOddsList;
        this.type = type;
        colorGreen = mContext.getResources().getColor(R.color.match_ping);
        colorRed = mContext.getResources().getColor(R.color.red_txt);
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
    }

    @Override
    public int getCount() {
        return mOddsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOddsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_odds_detail, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl = (LinearLayout) convertView.findViewById(R.id.ll);
            mViewHolder.mWinOdds = (TextView) convertView.findViewById(R.id.winOdds);
            mViewHolder.mPingOdds = (TextView) convertView.findViewById(R.id.pingOdds);
            mViewHolder.mLostOdds = (TextView) convertView.findViewById(R.id.lostOdds);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(mViewHolder);
        } else mViewHolder = (ViewHolder) convertView.getTag();
        OddsDetailData.DataBean.ItemsBean itemsBean = mOddsList.get(position);
        String time = itemsBean.getTime();
        String left = type == 1 ? itemsBean.getOh() : itemsBean.getHlevel();
        String middle = type == 1 ? itemsBean.getOd() : itemsBean.getHandicap();
        String right = type == 1 ? itemsBean.getOa() : itemsBean.getLlevel();

        if (position < mOddsList.size() - 1) {//说明还有下面一条，可以比较
            OddsDetailData.DataBean.ItemsBean itemsBean2 = mOddsList.get(position + 1);
            String left2 = type == 1 ? itemsBean2.getOh() : itemsBean2.getHlevel();
            String middle2 = type == 1 ? itemsBean2.getOd() : itemsBean2.getHandicap();
            String right2 = type == 1 ? itemsBean2.getOa() : itemsBean2.getLlevel();

            if (!TextUtils.isEmpty(left) && !TextUtils.isEmpty(left2)) {
                double start = Double.parseDouble(left2);
                double now = Double.parseDouble(left);
                if (now > start) {
                    mViewHolder.mWinOdds.setTextColor(colorRed);
                } else if (now < start) {
                    mViewHolder.mWinOdds.setTextColor(colorGreen);
                } else {
                    mViewHolder.mWinOdds.setTextColor(colorBlack);
                }
            }
            if (!TextUtils.isEmpty(right) && !TextUtils.isEmpty(right2)) {
                double start = Double.parseDouble(right2);
                double now = Double.parseDouble(right);
                if (now > start) {
                    mViewHolder.mLostOdds.setTextColor(colorRed);
                } else if (now < start) {
                    mViewHolder.mLostOdds.setTextColor(colorGreen);
                } else {
                    mViewHolder.mLostOdds.setTextColor(colorBlack);
                }
            }
            if (type == 1) {
                if (!TextUtils.isEmpty(middle) && !TextUtils.isEmpty(middle2)) {
                    double start = Double.parseDouble(middle2);
                    double now = Double.parseDouble(middle);
                    if (now > start) {
                        mViewHolder.mPingOdds.setTextColor(colorRed);
                    } else if (now < start) {
                        mViewHolder.mPingOdds.setTextColor(colorGreen);
                    } else {
                        mViewHolder.mPingOdds.setTextColor(colorBlack);
                    }
                }
            }
        } else {
            mViewHolder.mWinOdds.setTextColor(colorBlack);
            mViewHolder.mLostOdds.setTextColor(colorBlack);
            mViewHolder.mPingOdds.setTextColor(colorBlack);
        }
        mViewHolder.mLostOdds.setText(TextUtils.isEmpty(right) ? "--" : right);
        mViewHolder.mPingOdds.setText(TextUtils.isEmpty(middle) ? "--" : middle);
        mViewHolder.mWinOdds.setText(TextUtils.isEmpty(left) ? "--" : left);
        mViewHolder.mTime.setText(TextUtils.isEmpty(time) ? "--" : time);
        if (position % 2 == 0) {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));

        }

        return convertView;
    }

    class ViewHolder {
        private LinearLayout mLl;
        private TextView mWinOdds;
        private TextView mPingOdds;
        private TextView mLostOdds;
        private TextView mTime;
    }
}
