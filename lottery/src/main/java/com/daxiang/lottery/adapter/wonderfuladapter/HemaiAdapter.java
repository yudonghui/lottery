package com.daxiang.lottery.adapter.wonderfuladapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.wonderfulactivity.GendanDetailActivity;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.entity.TogetherAndFollowData;
import com.daxiang.lottery.utils.HttpUtils2;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class HemaiAdapter extends BaseAdapter {
    List<TogetherAndFollowData.DataBean.ListBean> mItemsList;
    Context mContext;

    public HemaiAdapter(List<TogetherAndFollowData.DataBean.ListBean> mItemsList, Context mContext) {
        this.mItemsList = mItemsList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hemai, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTv_play_type = (TextView) convertView.findViewById(R.id.tv_play_type);
            mViewHolder.mTv_progress = (TextView) convertView.findViewById(R.id.tv_progress);
            mViewHolder.mTv_baodi = (TextView) convertView.findViewById(R.id.tv_baodi);
            mViewHolder.mUser_img = (ImageView) convertView.findViewById(R.id.user_img);
            mViewHolder.mTv_username = (TextView) convertView.findViewById(R.id.tv_username);
            mViewHolder.mTv_total_money = (TextView) convertView.findViewById(R.id.tv_total_money);
            mViewHolder.mTv_remaining = (TextView) convertView.findViewById(R.id.tv_remaining);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final TogetherAndFollowData.DataBean.ListBean itemsBean = mItemsList.get(position);
        mViewHolder.mTv_baodi.setText(itemsBean.getCommissionScale() + "%");
        mViewHolder.mTv_progress.setText(itemsBean.getBuyRatio() + "%");
        mViewHolder.mTv_play_type.setText(LotteryTypes.getTypes(String.valueOf(itemsBean.getLotCode())));
        mViewHolder.mTv_username.setText(itemsBean.getUserName());
        mViewHolder.mTv_total_money.setText(itemsBean.getTotalMoney() + "元");
        mViewHolder.mTv_remaining.setText((Double.parseDouble(itemsBean.getTotalMoney()) - itemsBean.getBuyedNum()) + "元");
        HttpUtils2.display(mViewHolder.mUser_img, Url.HEADER_ROOT_URL + itemsBean.getUserId());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GendanDetailActivity.class);
               /* intent.putExtra("order_id",itemsBean.getOrderCode());
                intent.putExtra("lid",itemsBean.getLotType());
                intent.putExtra("flag",false);
                intent.putExtra("ratio",itemsBean.getRatio());
                intent.putExtra("commission",itemsBean.getCommission());
                intent.putExtra("remainmoney",mViewHolder.mTv_remaining.getText().toString());*/
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemsbean", itemsBean);
                bundle.putBoolean("flag", false);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView mTv_play_type;
        private TextView mTv_progress;
        private TextView mTv_baodi;
        private ImageView mUser_img;
        private TextView mTv_username;
        private TextView mTv_total_money;
        private TextView mTv_remaining;
    }
}
