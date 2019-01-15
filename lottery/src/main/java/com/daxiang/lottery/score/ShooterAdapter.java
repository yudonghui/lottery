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
import com.daxiang.lottery.entity.ShooterData;

import java.util.List;

/**
 * Created by Android on 2018/3/28.
 */

public class ShooterAdapter extends BaseAdapter {
    Context mContext;
    List<ShooterData.DataBean.ItemsBean> mDataList;

    public ShooterAdapter(Context mContext, List<ShooterData.DataBean.ItemsBean> mDataList) {
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
            convertView = View.inflate(mContext, R.layout.item_shooter, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl = (LinearLayout) convertView.findViewById(R.id.ll);
            mViewHolder.mRank = (TextView) convertView.findViewById(R.id.rank);
            mViewHolder.mUserName = (TextView) convertView.findViewById(R.id.userName);
            mViewHolder.mTeamName = (TextView) convertView.findViewById(R.id.teamName);
            mViewHolder.mInNum = (TextView) convertView.findViewById(R.id.inNum);
            mViewHolder.mDianQiu = (TextView) convertView.findViewById(R.id.dianQiu);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        ShooterData.DataBean.ItemsBean shooterData = mDataList.get(position);
        String rank = shooterData.getRank();
        String dianqiu = shooterData.getPenalty();
        String inNum = shooterData.getGet_ball();
        String teamName = shooterData.getTeam_name();
        String userName = shooterData.getPlayer_name();
        mViewHolder.mRank.setText(TextUtils.isEmpty(rank) ? "--" : rank);
        mViewHolder.mDianQiu.setText(TextUtils.isEmpty(dianqiu) ? "--" : dianqiu);
        mViewHolder.mInNum.setText(TextUtils.isEmpty(inNum) ? "--" : inNum);
        mViewHolder.mTeamName.setText(TextUtils.isEmpty(teamName) ? "--" : teamName);
        mViewHolder.mUserName.setText(TextUtils.isEmpty(userName) ? "--" : userName);
        if (position % 2 == 0) {
            mViewHolder.mLl.setBackgroundColor(Color.WHITE);
        } else {
            mViewHolder.mLl.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }
        return convertView;
    }

    class ViewHolder {
        private LinearLayout mLl;
        private TextView mRank;
        private TextView mUserName;
        private TextView mTeamName;
        private TextView mInNum;
        private TextView mDianQiu;
    }
}
