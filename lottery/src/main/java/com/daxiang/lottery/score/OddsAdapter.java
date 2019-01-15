package com.daxiang.lottery.score;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.OddsData;

import java.util.List;

/**
 * Created by Android on 2018/4/11.
 */

public class OddsAdapter extends BaseAdapter {
    private final int colorGreen;
    private final int colorRed;
    private final int colorBlack;
    Context mContext;
    List<OddsData.DataBean.ItemsBean> mDataList;
    int type;//1,欧赔率  2,亚盘

    public OddsAdapter(Context mContext, List<OddsData.DataBean.ItemsBean> mDataList, int type) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.type = type;
        colorGreen = mContext.getResources().getColor(R.color.match_ping);
        colorRed = mContext.getResources().getColor(R.color.red_txt);
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_odds, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl = (LinearLayout) convertView.findViewById(R.id.ll);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.name);
            mViewHolder.mWinOdds = (TextView) convertView.findViewById(R.id.winOdds);
            mViewHolder.mPingOdds = (TextView) convertView.findViewById(R.id.pingOdds);
            mViewHolder.mLostOdds = (TextView) convertView.findViewById(R.id.lostOdds);
            mViewHolder.mCurWinOdds = (TextView) convertView.findViewById(R.id.curWinOdds);
            mViewHolder.mCurPingOdds = (TextView) convertView.findViewById(R.id.curPingOdds);
            mViewHolder.mCurLostOdds = (TextView) convertView.findViewById(R.id.curLostOdds);
            convertView.setTag(mViewHolder);
        } else mViewHolder = (ViewHolder) convertView.getTag();
        if (position % 2 == 0) {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }
        OddsData.DataBean.ItemsBean itemsBean = mDataList.get(position);
        String now_llevel = type == 1 ? itemsBean.getNow_oa() : itemsBean.getNow_llevel();//右边即时水,即时负率
        String now_hlevel = type == 1 ? itemsBean.getNow_oh() : itemsBean.getNow_hlevel();//左边即时水,即时胜率
        String start_llevel = type == 1 ? itemsBean.getStart_oa() : itemsBean.getStart_llevel();//右边初时水,初时负率
        String start_hlevel = type == 1 ? itemsBean.getStart_oh() : itemsBean.getStart_hlevel();//左边初时水,初时胜率
        String start_handicap = type == 1 ? itemsBean.getStart_od() : itemsBean.getStart_handicap();//初时盘口初时平
        String now_handicap = type == 1 ? itemsBean.getNow_od() : itemsBean.getNow_handicap();//即时盘口,即时平
        String name = itemsBean.getName();//公司名字
        mViewHolder.mName.setText(TextUtils.isEmpty(name) ? "--" : name);
        mViewHolder.mWinOdds.setText(TextUtils.isEmpty(start_hlevel) ? "--" : start_hlevel);
        mViewHolder.mPingOdds.setText(TextUtils.isEmpty(start_handicap) ? "--" : start_handicap);
        mViewHolder.mLostOdds.setText(TextUtils.isEmpty(start_llevel) ? "--" : start_llevel);

        mViewHolder.mCurWinOdds.setText(TextUtils.isEmpty(now_hlevel) ? "--" : now_hlevel);
        mViewHolder.mCurPingOdds.setText(TextUtils.isEmpty(now_handicap) ? "--" : now_handicap);
        mViewHolder.mCurLostOdds.setText(TextUtils.isEmpty(now_llevel) ? "--" : now_llevel);

        if (!TextUtils.isEmpty(start_hlevel) && !TextUtils.isEmpty(now_hlevel)) {
            double start = Double.parseDouble(start_hlevel);
            double now = Double.parseDouble(now_hlevel);
            if (now > start) {
                mViewHolder.mCurWinOdds.setTextColor(colorRed);
            } else if (now < start) {
                mViewHolder.mCurWinOdds.setTextColor(colorGreen);
            } else {
                mViewHolder.mCurWinOdds.setTextColor(colorBlack);
            }
        }
        if (!TextUtils.isEmpty(start_llevel) && !TextUtils.isEmpty(now_llevel)) {
            double start = Double.parseDouble(start_llevel);
            double now = Double.parseDouble(now_llevel);
            if (now > start) {
                mViewHolder.mCurLostOdds.setTextColor(colorRed);
            } else if (now < start) {
                mViewHolder.mCurLostOdds.setTextColor(colorGreen);
            } else {
                mViewHolder.mCurLostOdds.setTextColor(colorBlack);
            }
        }
        if (type == 1) {//欧赔中间是平的赔率也有变化
            if (!TextUtils.isEmpty(start_handicap) && !TextUtils.isEmpty(now_handicap)) {
                double start = Double.parseDouble(start_handicap);
                double now = Double.parseDouble(now_handicap);
                if (now > start) {
                    mViewHolder.mCurPingOdds.setTextColor(colorRed);
                } else if (now < start) {
                    mViewHolder.mCurPingOdds.setTextColor(colorGreen);
                } else {
                    mViewHolder.mCurPingOdds.setTextColor(colorBlack);
                }
            }
        }
        Log.e("时间：", System.currentTimeMillis() + "");
        return convertView;
    }

    class ViewHolder {
        private LinearLayout mLl;
        private TextView mName;
        private TextView mWinOdds;
        private TextView mPingOdds;
        private TextView mLostOdds;
        private TextView mCurWinOdds;
        private TextView mCurPingOdds;
        private TextView mCurLostOdds;
    }
}
