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
import com.daxiang.lottery.entity.MatchScheduleData;

import java.util.List;

import static com.daxiang.lottery.R.id.guest;

/**
 * Created by Android on 2018/3/28.
 */

public class MatchScheduleAdapter extends BaseAdapter {
    Context mContext;
    List<MatchScheduleData.DataBean.ItemsBean.ListBean> mDataList;

    public MatchScheduleAdapter(Context mContext, List<MatchScheduleData.DataBean.ItemsBean.ListBean> mDataList) {
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
            convertView = View.inflate(mContext, R.layout.item_match_schedule, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl = (LinearLayout) convertView.findViewById(R.id.ll);
            mViewHolder.mDate = (TextView) convertView.findViewById(R.id.date);
            mViewHolder.mHome = (TextView) convertView.findViewById(R.id.home);
            mViewHolder.mScoreTime = (TextView) convertView.findViewById(R.id.scoreTime);
            mViewHolder.mGuest = (TextView) convertView.findViewById(guest);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        MatchScheduleData.DataBean.ItemsBean.ListBean itemsBean = mDataList.get(position);
        String away_name = itemsBean.getAway_name();
        String home_name = itemsBean.getHome_name();
        String score = itemsBean.getScore();
        String date = itemsBean.getDate();
        mViewHolder.mHome.setText(TextUtils.isEmpty(home_name) ? "--" : home_name);
        mViewHolder.mGuest.setText(TextUtils.isEmpty(away_name) ? "--" : away_name);
        mViewHolder.mDate.setText(TextUtils.isEmpty(date) ? "--" : date);
        mViewHolder.mScoreTime.setText(TextUtils.isEmpty(score) ? "--" : score);
        if (position%2==0){
            mViewHolder.mLl.setBackgroundColor(Color.WHITE);
        }else {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }
        return convertView;
    }

    class ViewHolder {
        private LinearLayout mLl;
        private TextView mDate;
        private TextView mHome;
        private TextView mScoreTime;
        private TextView mGuest;
    }
}
