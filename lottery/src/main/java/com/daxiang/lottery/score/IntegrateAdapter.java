package com.daxiang.lottery.score;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.IntegrateData;

import java.util.List;

import static com.daxiang.lottery.R.id.finished;
import static com.daxiang.lottery.R.id.integral;
import static com.daxiang.lottery.R.id.spf;

/**
 * Created by Android on 2018/3/28.
 */

public class IntegrateAdapter extends BaseAdapter {
    private Context mContext;
    List<IntegrateData> mDataList;

    public IntegrateAdapter(Context mContext, List<IntegrateData> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
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
            convertView = View.inflate(mContext, R.layout.item_integrate, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl = (LinearLayout) convertView.findViewById(R.id.ll);
            mViewHolder.mRank = (TextView) convertView.findViewById(R.id.rank);
            mViewHolder.mFinished = (TextView) convertView.findViewById(finished);
            mViewHolder.mSpf = (TextView) convertView.findViewById(spf);
            mViewHolder.mInLost = (TextView) convertView.findViewById(R.id.inLost);
            mViewHolder.mIntegral = (TextView) convertView.findViewById(integral);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            mViewHolder.mLl.setBackgroundColor(Color.WHITE);
        } else {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }
        IntegrateData integrateData = mDataList.get(position);

        String win_draw_lose = integrateData.getWin_draw_lose();
        String point = integrateData.getPoint();
        String match_end = integrateData.getMatch_end();
        String name = integrateData.getName();
        String rank = integrateData.getRank();
        String get_lose_ball = integrateData.getGet_lose_ball();
        mViewHolder.mRank.setText((TextUtils.isEmpty(rank) ? "" : rank)+name);
        mViewHolder.mFinished.setText(TextUtils.isEmpty(match_end) ? "--" : match_end);
        mViewHolder.mInLost.setText(TextUtils.isEmpty(get_lose_ball) ? "--" : get_lose_ball);
        mViewHolder.mIntegral.setText(TextUtils.isEmpty(point) ? "--" : point);
        mViewHolder.mSpf.setText(TextUtils.isEmpty(win_draw_lose) ? "--" : win_draw_lose);
        return convertView;
    }

    class ViewHolder {
        private LinearLayout mLl;
        private TextView mRank;
        private TextView mFinished;
        private TextView mSpf;
        private TextView mInLost;
        private TextView mIntegral;
    }
}
