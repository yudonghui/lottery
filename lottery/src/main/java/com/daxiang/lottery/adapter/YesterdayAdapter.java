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
import com.daxiang.lottery.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Android on 2018/7/4.
 */

public class YesterdayAdapter extends RecyclerView.Adapter<YesterdayAdapter.ViewHolder> {
    private final int colorGray;
    private final int colorBlack;
    private final int colorRed;
    private final int resize;
    private Context mContext;
    private List<PlayingedBean.DataBean.FinishBean> mList;

    public YesterdayAdapter(Context mContext, List<PlayingedBean.DataBean.FinishBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
        colorRed = mContext.getResources().getColor(R.color.red_txt);
        resize = DisplayUtil.dip2px(45);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_yesterday_match, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(position);
        final String mid = mList.get(position).getMid();
        if (!TextUtils.isEmpty(mid)) {
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
        private LinearLayout mLinearLayout;
        private ImageView mHome_avatar;
        private ImageView mGuest_avatar;
        private TextView mHome_name;
        private TextView mScore;
        private TextView mGuest_name;

        public ViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            mHome_avatar = (ImageView) itemView.findViewById(R.id.home_avatar);
            mGuest_avatar = (ImageView) itemView.findViewById(R.id.guest_avatar);
            mHome_name = (TextView) itemView.findViewById(R.id.home_name);
            mScore = (TextView) itemView.findViewById(R.id.score);
            mGuest_name = (TextView) itemView.findViewById(R.id.guest_name);

        }

        public void setData(int position) {
            PlayingedBean.DataBean.FinishBean PlayedBean = mList.get(position);
            String h_pic = PlayedBean.getHome_logo();//主队头像
            String a_pic = PlayedBean.getAway_logo();//客队头像
            String home_short_cn = PlayedBean.getHome_name();//主队名称
            String guest_short_cn = PlayedBean.getAway_name();//客队名称
            String full_score = PlayedBean.getScore();
            String mid = PlayedBean.getMid();
            mHome_name.setText(TextUtils.isEmpty(home_short_cn) ? "--" : home_short_cn);
            mGuest_name.setText(TextUtils.isEmpty(guest_short_cn) ? "--" : guest_short_cn);
            mScore.setText(TextUtils.isEmpty(full_score) ? "--" : full_score);
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
