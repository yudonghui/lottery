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
import com.daxiang.lottery.entity.ScoreBoardBean;

import java.util.List;

import static com.daxiang.lottery.R.id.finished;
import static com.daxiang.lottery.R.id.integral;
import static com.daxiang.lottery.R.id.spf;

/**
 * Created by Android on 2018/4/18.
 */

public class ScoreBoardAdapter extends BaseAdapter {
    private final int colorRed;
    private final int colorBlack;
    private Context mContext;
    List<ScoreBoardBean.DataBean.ItemsBean> mDataList;
    String home_id;
    String away_id;

    public ScoreBoardAdapter(Context mContext, List<ScoreBoardBean.DataBean.ItemsBean> mDataList, String home_id, String away_id) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.home_id = home_id;
        this.away_id = away_id;
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
        ScoreBoardBean.DataBean.ItemsBean itemsBean = mDataList.get(position);

        String win_draw_lose = itemsBean.getWdl();
        String point = itemsBean.getPoint();
        String match_end = itemsBean.getMatch_completed();
        String team_id = itemsBean.getTeam_id();
        String name = itemsBean.getTeam_name();
        String rank = itemsBean.getRank();
        String get_lose_ball = itemsBean.getGln();
        mViewHolder.mRank.setText((TextUtils.isEmpty(rank) ? "" : rank) + "  " + name);
        mViewHolder.mFinished.setText(TextUtils.isEmpty(match_end) ? "--" : match_end);
        mViewHolder.mInLost.setText(TextUtils.isEmpty(get_lose_ball) ? "--" : get_lose_ball);
        mViewHolder.mIntegral.setText(TextUtils.isEmpty(point) ? "--" : point);
        mViewHolder.mSpf.setText(TextUtils.isEmpty(win_draw_lose) ? "--" : win_draw_lose);
        if (!TextUtils.isEmpty(team_id)) {
            if (team_id.equals(home_id) || team_id.equals(away_id)) {
                mViewHolder.mRank.setTextColor(colorRed);
                mViewHolder.mFinished.setTextColor(colorRed);
                mViewHolder.mInLost.setTextColor(colorRed);
                mViewHolder.mIntegral.setTextColor(colorRed);
                mViewHolder.mSpf.setTextColor(colorRed);

            }else {
                mViewHolder.mRank.setTextColor(colorBlack);
                mViewHolder.mFinished.setTextColor(colorBlack);
                mViewHolder.mInLost.setTextColor(colorBlack);
                mViewHolder.mIntegral.setTextColor(colorBlack);
                mViewHolder.mSpf.setTextColor(colorBlack);
            }
        }
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
