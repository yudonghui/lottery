package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.PlayingedBean;
import com.daxiang.lottery.score.ScoreDetailActivity;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Android on 2018/7/4.
 */

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {
    private final int colorGray;
    private final int colorBlack;
    private final int colorRed;
    private Context mContext;
    private List<PlayingedBean.DataBean.FixtureBean> mList;

    public LiveAdapter(Context mContext, List<PlayingedBean.DataBean.FixtureBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
        colorRed = mContext.getResources().getColor(R.color.red_txt);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_live_match, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(position);
        final String mid = mList.get(position).getMid();
        if (!TextUtils.isEmpty(mid)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ScoreDetailActivity.class);
                    intent.putExtra("mId", mid);
                    intent.putExtra("from", "score");
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHome_avatar;
        private TextView mTeam_name;
        private TextView mEnd_time;
        private ImageView mGuest_avatar;
        private LinearLayout mLl_home;
        private TextView mHome_title;
        private TextView mHome_odds;
        private LinearLayout mLl_ping;
        private TextView mPing_title;
        private TextView mPing_odds;
        private LinearLayout mLl_guest;
        private TextView mGuest_title;
        private TextView mGuest_odds;

        public ViewHolder(View itemView) {
            super(itemView);
            mHome_avatar = (ImageView) itemView.findViewById(R.id.home_avatar);
            mTeam_name = (TextView) itemView.findViewById(R.id.team_name);
            mEnd_time = (TextView) itemView.findViewById(R.id.end_time);
            mGuest_avatar = (ImageView) itemView.findViewById(R.id.guest_avatar);
            mLl_home = (LinearLayout) itemView.findViewById(R.id.ll_home);
            mHome_title = (TextView) itemView.findViewById(R.id.home_title);
            mHome_odds = (TextView) itemView.findViewById(R.id.home_odds);
            mLl_ping = (LinearLayout) itemView.findViewById(R.id.ll_ping);
            mPing_title = (TextView) itemView.findViewById(R.id.ping_title);
            mPing_odds = (TextView) itemView.findViewById(R.id.ping_odds);
            mLl_guest = (LinearLayout) itemView.findViewById(R.id.ll_guest);
            mGuest_title = (TextView) itemView.findViewById(R.id.guest_title);
            mGuest_odds = (TextView) itemView.findViewById(R.id.guest_odds);
        }

        public void setData(int position) {
            PlayingedBean.DataBean.FixtureBean playingBean = mList.get(position);
            String h_pic = playingBean.getHome_logo();//主队头像
            String a_pic = playingBean.getAway_logo();//客队头像
            String score = playingBean.getScore();
            long time = playingBean.getTime();
            String minute = playingBean.getMinute();
            String home_short_cn = playingBean.getHome_name();//主队名称
            String guest_short_cn = playingBean.getAway_name();//客队名称

            String odd0 = playingBean.getOdd0();
            String odd1 = playingBean.getOdd1();
            String odd3 = playingBean.getOdd3();


            mHome_title.setText(TextUtils.isEmpty(home_short_cn) ? "--" : home_short_cn);
            mGuest_title.setText(TextUtils.isEmpty(guest_short_cn) ? "--" : guest_short_cn);

            mHome_odds.setText(TextUtils.isEmpty(odd3) ? "--" : odd3);
            mPing_odds.setText(TextUtils.isEmpty(odd1) ? "--" : odd1);
            mGuest_odds.setText(TextUtils.isEmpty(odd0) ? "--" : odd0);
            if (TextUtils.isEmpty(minute)){
                mEnd_time.setText(DateFormtUtils.longToDate(time) + "开赛");
            }else {
                mEnd_time.setText(minute+"'");
            }
            mTeam_name.setText(TextUtils.isEmpty(score) ? "VS" : score);
            if (!TextUtils.isEmpty(h_pic)) {
                Picasso.with(mContext)
                        .load(h_pic)
                        .config(Bitmap.Config.RGB_565)
                        .placeholder(R.mipmap.home_team)
                        .error(R.mipmap.home_team)
                        .into(mHome_avatar);
            }
            if (!TextUtils.isEmpty(a_pic)) {
                Picasso.with(mContext)
                        .load(a_pic)
                        .config(Bitmap.Config.RGB_565)
                        .placeholder(R.mipmap.guest_team)
                        .error(R.mipmap.guest_team)
                        .into(mGuest_avatar);
            }
        }
    }
}
