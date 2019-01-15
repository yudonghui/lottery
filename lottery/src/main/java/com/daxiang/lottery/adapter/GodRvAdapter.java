package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.entity.RankingData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/12/14
 * @describe May the Buddha bless bug-free!!!
 */
public class GodRvAdapter extends RecyclerView.Adapter<GodRvAdapter.ViewHolder> {

    private final int right;
    private final int top;
    private final int bottom;
    private final int anInt;
    private final int anInt1;
    private final int update;
    private Context mContext;
    private final int verspace;
    private final int left;
    private int resize;
    private int circle;
    List<RankingData.DataBean> mGodList;

    public GodRvAdapter(Context mContext, List<RankingData.DataBean> mGodList) {
        this.mGodList = mGodList;
        this.mContext = mContext;
        int displayWidth = DisplayUtil.getDisplayWidth();
        verspace = DisplayUtil.dip2px(135);
        resize = (displayWidth - verspace) * 2 / 9;
        anInt = DisplayUtil.dip2px(8);
        anInt1 = DisplayUtil.dip2px(7);
        update = DisplayUtil.dip2px(1);
        circle = resize - anInt;
        left = DisplayUtil.dip2px(15);
        right = DisplayUtil.dip2px(15);
        top = DisplayUtil.dip2px(20);
        bottom = DisplayUtil.dip2px(5);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_god_rv, null);
        inflate.setOnClickListener(ItemListener);
        ViewHolder viewHolder = new ViewHolder(inflate);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        RankingData.DataBean dataBean = mGodList.get(position);
        holder.setData(dataBean, position);
    }

    @Override
    public int getItemCount() {
        return mGodList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIsGod;
        private CircleImageView mAvatar;
        private ImageView mAvatar_box;
        private TextView mName;
        private TextView mCanBuy;

        public ViewHolder(View itemView) {
            super(itemView);
            mIsGod = (ImageView) itemView.findViewById(R.id.isGod);
            mAvatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            mAvatar_box = (ImageView) itemView.findViewById(R.id.avatar_box);
            mName = (TextView) itemView.findViewById(R.id.name);
            mCanBuy = (TextView) itemView.findViewById(R.id.canBuy);
        }

        public void setData(RankingData.DataBean dataBean, int position) {
            if (position == 0) {
                mAvatar_box.setImageResource(R.mipmap.gold_box);
                mAvatar_box.setVisibility(View.VISIBLE);
                mIsGod.setVisibility(View.GONE);
            } else if (position == 1) {
                mAvatar_box.setImageResource(R.mipmap.silver_box);
                mAvatar_box.setVisibility(View.VISIBLE);
                mIsGod.setVisibility(View.GONE);
            } else if (position == 2) {
                mAvatar_box.setImageResource(R.mipmap.copper_box);
                mAvatar_box.setVisibility(View.VISIBLE);
                mIsGod.setVisibility(View.GONE);
            } else {
                mAvatar_box.setVisibility(View.GONE);
                mIsGod.setVisibility(View.VISIBLE);
            }
           /* FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(circle, circle);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(resize, resize);
            FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(circle + DisplayUtil.dip2px(0.5), circle + DisplayUtil.dip2px(0.5));
            layoutParams.setMargins(left + anInt / 2, top + anInt / 2, right + anInt / 2, bottom + anInt / 2);
            layoutParams2.setMargins(left-update, top-update, right+update, bottom+update);
            layoutParams3.setMargins(left + anInt1 / 2, top + anInt1 / 2, right + anInt1 / 2, bottom + anInt1 / 2);
            mAvatar.setLayoutParams(layoutParams);
            mAvatar_box.setLayoutParams(layoutParams2);
            mView.setLayoutParams(layoutParams3);*/
            String userName = dataBean.getUserName();
            String userId = dataBean.getUserId();
            String canBuy = dataBean.getCanBuy();//有几个正在进行的推荐
            if (TextUtils.isEmpty(canBuy) || "0".equals(canBuy)) {
                mCanBuy.setVisibility(View.GONE);
            } else {
                mCanBuy.setVisibility(View.VISIBLE);
                mCanBuy.setText(canBuy);
            }
            mName.setText(TextUtils.isEmpty(userName) ? "--" : userName);
            if (!TextUtils.isEmpty(userId)) {
                //  HttpUtils2.display(mAvatar, Url.CHANGE_HEADER_URL + "?userId=" + userId);
                Picasso.with(mContext)
                        .load(Url.HEADER_ROOT_URL + userId)
                        .config(Bitmap.Config.RGB_565)
                        .error(R.mipmap.default_header)
                        .placeholder(R.mipmap.default_header)
                        .into(mAvatar);
            }
        }
    }

    View.OnClickListener ItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ClickUtils.isFastClick(2000)){
                return;
            }
            int position = (int) v.getTag();
            Intent intent = new Intent(mContext, GodInfoActivity.class);
            intent.putExtra("userId", mGodList.get(position).getUserId());
            mContext.startActivity(intent);
        }
    };
}
