package com.daxiang.lottery.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Constants;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.entity.RankingData;
import com.daxiang.lottery.utils.HttpUtils2;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/22
 * @describe May the Buddha bless bug-free!!!
 */
public class RankingAdapter extends BaseAdapter {
    List<RankingData.DataBean> data;
    String title;

    public RankingAdapter(List<RankingData.DataBean> data, String title) {
        this.data = data;
        this.title = title;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_ranking, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.name);
            mViewHolder.mBlackTv = (TextView) convertView.findViewById(R.id.blackTv);
            mViewHolder.mRedTv = (TextView) convertView.findViewById(R.id.redTv);
            mViewHolder.mGrayTv = (TextView) convertView.findViewById(R.id.grayTv);
            mViewHolder.mCanBuy = (TextView) convertView.findViewById(R.id.canBuy);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        RankingData.DataBean dataBean = data.get(position);
        String userId = dataBean.getUserId();
        String userName = dataBean.getUserName();
        String canBuy = dataBean.getCanBuy();//有几个正在进行的推荐
        if (TextUtils.isEmpty(canBuy) || "0".equals(canBuy)) {
            mViewHolder.mCanBuy.setVisibility(View.GONE);
        } else {
            mViewHolder.mCanBuy.setVisibility(View.VISIBLE);
            mViewHolder.mCanBuy.setText(canBuy);
        }
        switch (title) {
            case Constants.JIANGJIN://奖金榜。后面显示两行。灰红
                String allPrize = dataBean.getAllPrize();
                mViewHolder.mBlackTv.setText("累计奖金");
                mViewHolder.mBlackTv.setTextColor(parent.getContext().getResources().getColor(R.color.gray_txt));
                mViewHolder.mRedTv.setText(TextUtils.isEmpty(allPrize) ? "0" : allPrize + "元");
                mViewHolder.mGrayTv.setVisibility(View.GONE);
                break;
            case Constants.RENQI://人气榜。后面显示三行。黑红灰
                String totalJoin = dataBean.getTotalJoin();//跟买人数
                String totalCost = dataBean.getTotalCost();//跟买总金额
                mViewHolder.mBlackTv.setText("累计跟买人数");
                mViewHolder.mRedTv.setText(TextUtils.isEmpty(totalJoin) ? "0" : totalJoin + "人");
                mViewHolder.mGrayTv.setText("累计跟买金额: " + (TextUtils.isEmpty(totalCost) ? "0" : totalCost) + "元");
                break;
            case Constants.HUIBAO://回报榜。后面显示两行。灰红
             /*   String totalCost = dataBean.getTotalCost();//共计购买金额
                String totalPrize = dataBean.getTotalPrize();//共计中奖金额*/
                String gainRate = dataBean.getGainRate();//回报率
                mViewHolder.mBlackTv.setText("回报率");
                mViewHolder.mRedTv.setText(TextUtils.isEmpty(gainRate) ? "0" : (gainRate + "%"));
                mViewHolder.mGrayTv.setVisibility(View.GONE);
                break;
            case Constants.MINGZHONG://命中榜。后面显示两行。红灰
                String totalBuy = dataBean.getTotalBuy();//共计购买的订单数
                String totalWin = dataBean.getTotalWin();//共计中奖订单数
                String winRate = dataBean.getWinRate();//命中率
                mViewHolder.mBlackTv.setVisibility(View.GONE);
                mViewHolder.mRedTv.setText(totalBuy + "中" + totalWin);
                mViewHolder.mGrayTv.setText("命中率: " + (TextUtils.isEmpty(winRate) ? "" : (winRate + "%")));
                break;
            case Constants.LIANHONG://连红榜。后面显示一行。红
                String maxNum = dataBean.getMaxNum();//最大连红数
                mViewHolder.mBlackTv.setVisibility(View.GONE);
                mViewHolder.mGrayTv.setVisibility(View.GONE);
                mViewHolder.mRedTv.setText(TextUtils.isEmpty(maxNum) ? "0" : maxNum + "连红");
                break;
            case Constants.HUOYUE://活跃榜。后面显示三行。黑红灰
                String buyNum = dataBean.getBuyNum();
                mViewHolder.mGrayTv.setVisibility(View.GONE);
                mViewHolder.mBlackTv.setText("累计购彩");
                mViewHolder.mRedTv.setText(TextUtils.isEmpty(buyNum) ? "0" : buyNum + "单");
                break;
        }
        mViewHolder.mName.setText(TextUtils.isEmpty(userName) ? "" : userName);
        HttpUtils2.display(mViewHolder.mAvatar, Url.HEADER_ROOT_URL + userId);
        return convertView;
    }

    class ViewHolder {
        ImageView mAvatar;
        TextView mName;
        TextView mBlackTv;
        TextView mRedTv;
        TextView mGrayTv;
        TextView mCanBuy;
    }
}
