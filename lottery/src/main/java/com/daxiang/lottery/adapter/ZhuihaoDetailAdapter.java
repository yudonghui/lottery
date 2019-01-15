package com.daxiang.lottery.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.entity.ZhuihaoDetailBean;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/7/6
 * @describe May the Buddha bless bug-free!!!
 */
public class ZhuihaoDetailAdapter extends BaseAdapter {
    List<ZhuihaoDetailBean.DataBean.ListBean> mList;

    public ZhuihaoDetailAdapter(List<ZhuihaoDetailBean.DataBean.ListBean> mList) {
        this.mList = mList;
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
            convertView = View.inflate(parent.getContext(), R.layout.item_zhuihao_detail, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mIssue = (TextView) convertView.findViewById(R.id.issue);
            mViewHolder.mOrderStatus = (TextView) convertView.findViewById(R.id.orderStatus);
            mViewHolder.mAwardMsg = (TextView) convertView.findViewById(R.id.awardMsg);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        ZhuihaoDetailBean.DataBean.ListBean listBean = mList.get(position);
        String issueNo = listBean.getIssueNo();
        String orderStatus = listBean.getOrderStatus();
        String winStatus = listBean.getWinStatus();//0未开奖，1未中奖，2已中奖
        String aftertaxBonus = listBean.getAftertaxBonus();//中奖金额
        mViewHolder.mIssue.setText(TextUtils.isEmpty(issueNo) ? "" : issueNo);
        mViewHolder.mOrderStatus.setText(LotteryTypes.getStatus(orderStatus));
        if (!TextUtils.isEmpty(winStatus)) {
            if ("501".equals(orderStatus) || "600".equals(orderStatus)) {
                mViewHolder.mAwardMsg.setText("--");
            } else if ("0".equals(winStatus)) {
                mViewHolder.mAwardMsg.setText("等待开奖");
                mViewHolder.mAwardMsg.setTextColor(parent.getContext().getResources().getColor(R.color.blue_txt));
            } else if ("1".equals(winStatus)) {
                mViewHolder.mAwardMsg.setText("未中奖");
                mViewHolder.mAwardMsg.setTextColor(parent.getContext().getResources().getColor(R.color.deep_txt));
            } else {
                mViewHolder.mAwardMsg.setText(TextUtils.isEmpty(aftertaxBonus) ? "" : aftertaxBonus);
                mViewHolder.mAwardMsg.setTextColor(parent.getContext().getResources().getColor(R.color.red_txt));
            }
        }
        return convertView;
    }

    class ViewHolder {
        private TextView mIssue;
        private TextView mOrderStatus;
        private TextView mAwardMsg;
    }
}
