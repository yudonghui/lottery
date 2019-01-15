package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.BillRecordDetailActivity;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.entity.BillStreamData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class BillStreamAdapter extends BaseAdapter {
    List<BillStreamData.DataBean.ItemsBean> mTransList;
    private Context mContext;

    public void setData(List<BillStreamData.DataBean.ItemsBean> mTransList) {
        this.mTransList = mTransList;
    }

    @Override
    public int getCount() {
        return mTransList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mContext = parent.getContext();
            convertView = View.inflate(mContext, R.layout.item_bill_stream, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mDate = (TextView) convertView.findViewById(R.id.item_bill_date);
            mViewHolder.mMoney = (TextView) convertView.findViewById(R.id.item_bill_money);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_bill_title);
            mViewHolder.mBalance = (TextView) convertView.findViewById(R.id.item_bill_banlance);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final BillStreamData.DataBean.ItemsBean transListBean = mTransList.get(position);
        //设定时间
        long createdTime = transListBean.getCreateTime();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String timeStr = mFormat.format(createdTime);
        mViewHolder.mDate.setText(timeStr);
        //设定彩种和期号
        final int transType = transListBean.getTradeType();
        final String subject;
        if (transListBean.getRemark() == null) {
            subject = LotteryTypes.getMoneyStatus(transType);
        } else {
            subject = LotteryTypes.getMoneyStatus(transType) + "  " + transListBean.getRemark();
        }
        double balance = transListBean.getBalance();
        if (transType != 12 && transType != 13) {
            mViewHolder.mBalance.setText("余额：" + balance);
        }
        mViewHolder.mTitle.setText(subject);
        //设定赚钱还是赔钱
        final double money = transListBean.getAmount();
        if (transType == 1 || transType == 3 || transType == 4 || transType == 10 || transType == 12) {
            mViewHolder.mMoney.setText("-" + money + "元");
            mViewHolder.mMoney.setTextColor(Color.GRAY);
        } else {
            mViewHolder.mMoney.setTextColor(Color.RED);
            mViewHolder.mMoney.setText("+" + money + "元");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pid = mTransList.get(position).getTradeId();
                Intent intent = new Intent(mContext, BillRecordDetailActivity.class);
                intent.putExtra("pid", pid);
                intent.putExtra("subject", subject);
                intent.putExtra("money", money);
                intent.putExtra("transType", transType);
                intent.putExtra("timeStr", timeStr);
                intent.putExtra("origin",transListBean.getRemark());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mTitle;
        TextView mDate;
        TextView mMoney;
        TextView mBalance;
    }
}
