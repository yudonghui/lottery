package com.daxiang.lottery.score;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.FutureData;

import java.util.List;

/**
 * Created by Android on 2018/3/29.
 */

public class FutureAdapter extends BaseAdapter {
    private final int colorBlack;
    private final int colorGray;
    private Context mContext;
    List<FutureData> mFutureList;
    String homeId;//传过来是标黑字体的队伍

    public FutureAdapter(Context mContext, List<FutureData> mFutureList, String homeId) {
        this.mContext = mContext;
        this.mFutureList = mFutureList;
        this.homeId = homeId;
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
    }

    @Override
    public int getCount() {
        return mFutureList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFutureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_future, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl = (LinearLayout) convertView.findViewById(R.id.ll);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.name);
            mViewHolder.mDate = (TextView) convertView.findViewById(R.id.date);
            mViewHolder.mHomeTeam = (TextView) convertView.findViewById(R.id.homeTeam);
            mViewHolder.mScore = (TextView) convertView.findViewById(R.id.score);
            mViewHolder.mGuestTeam = (TextView) convertView.findViewById(R.id.guestTeam);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        FutureData futureData = mFutureList.get(position);
        String name = futureData.getLeague_name();
        String date = futureData.getDate();
        String home = futureData.getHome_name();
        String home_id = futureData.getHome_id();
        String away_id = futureData.getAway_id();
        String guest = futureData.getAway_name();
        String time = futureData.getDays_apart();
        mViewHolder.mName.setText(TextUtils.isEmpty(name) ? "--" : name);
        mViewHolder.mDate.setText(TextUtils.isEmpty(date) ? "--" : date);
        mViewHolder.mHomeTeam.setText(TextUtils.isEmpty(home) ? "--" : home);
        mViewHolder.mGuestTeam.setText(TextUtils.isEmpty(guest) ? "--" : guest);
        mViewHolder.mTime.setText((TextUtils.isEmpty(time) ? "--" : time) + "天后");
        if (position % 2 == 0) {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }
        if (!TextUtils.isEmpty(home_id) && home_id.equals(homeId))
            mViewHolder.mHomeTeam.setTextColor(colorBlack);
        else
            mViewHolder.mHomeTeam.setTextColor(colorGray);
        if (!TextUtils.isEmpty(away_id) && away_id.equals(homeId))
            mViewHolder.mGuestTeam.setTextColor(colorBlack);
        else
            mViewHolder.mGuestTeam.setTextColor(colorGray);
        return convertView;
    }

    class ViewHolder {
        private LinearLayout mLl;
        private TextView mName;
        private TextView mDate;
        private TextView mHomeTeam;
        private TextView mScore;
        private TextView mGuestTeam;
        private TextView mTime;
    }
}
