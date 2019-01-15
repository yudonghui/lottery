package com.daxiang.lottery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.BaseBean;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/17
 * @describe May the Buddha bless bug-free!!!
 */
public class HistoryAdapter extends BaseAdapter {
    private final int colorBlack;
    private final int colorGray;
    List<BaseBean.ArrBean> mList;
    Context mContext;
    String homeId;//传过来是标黑字体的队伍

    public HistoryAdapter(Context mContext, List<BaseBean.ArrBean> mList, String homeId) {
        this.mList = mList;
        this.mContext = mContext;
        this.homeId = homeId;
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_history, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mArea = (TextView) convertView.findViewById(R.id.area);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.time);
            mViewHolder.mHomeTeam = (TextView) convertView.findViewById(R.id.homeTeam);
            mViewHolder.mScore = (TextView) convertView.findViewById(R.id.score);
            mViewHolder.mGuestTeam = (TextView) convertView.findViewById(R.id.guestTeam);
            mViewHolder.mResult = (TextView) convertView.findViewById(R.id.result);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        BaseBean.ArrBean dataBean = mList.get(position);
        String matchName = dataBean.getLeague_name();//赛事 名称
        String matchDate = dataBean.getDate();//日期
        String h = dataBean.getHome_name();//主队
        String a = dataBean.getAway_name();//客队
        String fullScore = dataBean.getScore();//比分
        String teamRs = dataBean.getWdr_detail();//结果
        String away_id = dataBean.getAway_id();
        String home_id = dataBean.getHome_id();
        mViewHolder.mArea.setText(TextUtils.isEmpty(matchName) ? "--" : matchName);
        mViewHolder.mTime.setText(TextUtils.isEmpty(matchDate) ? "--" : matchDate);
        mViewHolder.mHomeTeam.setText(TextUtils.isEmpty(h) ? "--" : h);
        mViewHolder.mGuestTeam.setText(TextUtils.isEmpty(a) ? "--" : a);
        mViewHolder.mScore.setText(TextUtils.isEmpty(fullScore) ? "--" : fullScore);
        if (!TextUtils.isEmpty(home_id) && home_id.equals(homeId))
            mViewHolder.mHomeTeam.setTextColor(colorBlack);
        else
            mViewHolder.mHomeTeam.setTextColor(colorGray);
        if (!TextUtils.isEmpty(away_id) && away_id.equals(homeId))
            mViewHolder.mGuestTeam.setTextColor(colorBlack);
        else
            mViewHolder.mGuestTeam.setTextColor(colorGray);
        if (!TextUtils.isEmpty(teamRs)) {
            switch (teamRs) {
                case "胜":
                    mViewHolder.mResult.setText("胜");
                    mViewHolder.mResult.setBackgroundResource(R.drawable.shape_win_circle);
                    break;
                case "平":
                    mViewHolder.mResult.setText("平");
                    mViewHolder.mResult.setBackgroundResource(R.drawable.shape_ping_circle);
                    break;
                case "负":
                    mViewHolder.mResult.setText("负");
                    mViewHolder.mResult.setBackgroundResource(R.drawable.shape_lost_circle);
                    break;
                default:
                    mViewHolder.mResult.setBackgroundResource(R.drawable.shape_win_circle);
                    break;
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView mArea;
        TextView mTime;
        TextView mHomeTeam;
        TextView mScore;
        TextView mGuestTeam;
        TextView mResult;
    }
}
