package com.daxiang.lottery.view.stickyView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.ScoreBoardBean;

import java.util.List;

import static com.daxiang.lottery.R.id.finished;
import static com.daxiang.lottery.R.id.integral;
import static com.daxiang.lottery.R.id.spf;

/**
 * Created by cpf on 2018/1/16.
 */

public class PerformerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int colorRed;
    private final int colorBlack;
    private Context mContext;
    List<ScoreBoardBean.DataBean.ItemsBean> mDataList;
    String home_id;
    String away_id;

    public PerformerListAdapter(Context mContext, List<ScoreBoardBean.DataBean.ItemsBean> mDataList, String home_id, String away_id) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.home_id = home_id;
        this.away_id = away_id;
        colorRed = mContext.getResources().getColor(R.color.red_txt);
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 2222) {
            return new ContentVH(LayoutInflater.from(mContext).inflate(R.layout.item_integrate, parent, false));
        }
        return new TitleVH(LayoutInflater.from(mContext).inflate(R.layout.adapter_title, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScoreBoardBean.DataBean.ItemsBean itemsBean = mDataList.get(position);

        if (holder instanceof ContentVH) {
            ((ContentVH) holder).bindData(itemsBean);
        }
        if (holder instanceof TitleVH) {
            ((TitleVH) holder).bindData(itemsBean);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getItemType();
    }


    class TitleVH extends RecyclerView.ViewHolder {

        TextView mTv;

        public TitleVH(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.title);
            itemView.setTag(true);
        }

        public void bindData(ScoreBoardBean.DataBean.ItemsBean itemsBean) {
            String title = itemsBean.getTitle();
            mTv.setText(TextUtils.isEmpty(title) ? "--" : title);
        }

    }

    class ContentVH extends RecyclerView.ViewHolder {

        private LinearLayout mLl;
        private TextView mRank;
        private TextView mFinished;
        private TextView mSpf;
        private TextView mInLost;
        private TextView mIntegral;

        public ContentVH(View itemView) {
            super(itemView);
            mLl = (LinearLayout) itemView.findViewById(R.id.ll);
            mRank = (TextView) itemView.findViewById(R.id.rank);
            mFinished = (TextView) itemView.findViewById(finished);
            mSpf = (TextView) itemView.findViewById(spf);
            mInLost = (TextView) itemView.findViewById(R.id.inLost);
            mIntegral = (TextView) itemView.findViewById(integral);
            itemView.setTag(false);
        }

        public void bindData(ScoreBoardBean.DataBean.ItemsBean itemsBean) {
            String win_draw_lose = itemsBean.getWdl();
            String point = itemsBean.getPoint();
            String match_end = itemsBean.getMatch_completed();
            String team_id = itemsBean.getTeam_id();
            String name = itemsBean.getTeam_name();
            String rank = itemsBean.getRank();
            String get_lose_ball = itemsBean.getGln();
            mRank.setText((TextUtils.isEmpty(rank) ? "" : rank) + "  " + name);
            mFinished.setText(TextUtils.isEmpty(match_end) ? "--" : match_end);
            mInLost.setText(TextUtils.isEmpty(get_lose_ball) ? "--" : get_lose_ball);
            mIntegral.setText(TextUtils.isEmpty(point) ? "--" : point);
            mSpf.setText(TextUtils.isEmpty(win_draw_lose) ? "--" : win_draw_lose);
            if (!TextUtils.isEmpty(team_id)) {
                if (team_id.equals(home_id) || team_id.equals(away_id)) {
                    mRank.setTextColor(colorRed);
                    mFinished.setTextColor(colorRed);
                    mInLost.setTextColor(colorRed);
                    mIntegral.setTextColor(colorRed);
                    mSpf.setTextColor(colorRed);

                } else {
                    mRank.setTextColor(colorBlack);
                    mFinished.setTextColor(colorBlack);
                    mInLost.setTextColor(colorBlack);
                    mIntegral.setTextColor(colorBlack);
                    mSpf.setTextColor(colorBlack);
                }
            }
        }

    }
}
