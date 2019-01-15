package com.daxiang.lottery.adapter.redpacket;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.RedpacketData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/17.
 */

public class RedpacketFormAdapter extends BaseAdapter {
    ArrayList<RedpacketData.DataBean.ItemsBean> redList;
    int[] redBgId = {R.mipmap.redbg, R.mipmap.bluebg, R.mipmap.greenbg, R.mipmap.orangebg};
    int selectRedId = 100000;

    public RedpacketFormAdapter(ArrayList<RedpacketData.DataBean.ItemsBean> redList) {
        this.redList = redList;
    }

    public void setSelectRedId(int selectRedId) {
        this.selectRedId = selectRedId;
    }

    @Override
    public int getCount() {
        return redList.size();
    }

    @Override
    public Object getItem(int position) {
        return redList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_redpacket, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mMoney = (TextView) convertView.findViewById(R.id.red_money);
            mViewHolder.mRule = (TextView) convertView.findViewById(R.id.red_rule);
            mViewHolder.mDate = (TextView) convertView.findViewById(R.id.red_date);
            mViewHolder.mRedStatus = (ImageView) convertView.findViewById(R.id.red_status);
            mViewHolder.mRedBg = (FrameLayout) convertView.findViewById(R.id.red_bg);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        RedpacketData.DataBean.ItemsBean itemsBean = redList.get(position);
        switch (itemsBean.getStatus()) {
            case "0":
            case "1":
                //已生效
                mViewHolder.mRedBg.setBackgroundResource(redBgId[position % 4]);
                mViewHolder.mRedStatus.setVisibility(View.GONE);
                break;
            case "2":
               //已过期
                mViewHolder.mRedBg.setBackgroundResource(R.mipmap.graybg);
                mViewHolder.mRedStatus.setImageResource(R.mipmap.expirebg);
                mViewHolder.mRedStatus.setVisibility(View.VISIBLE);
                break;
            case "3":
               //已使用
                mViewHolder.mRedBg.setBackgroundResource(R.mipmap.graybg);
                mViewHolder.mRedStatus.setImageResource(R.mipmap.usedbg);
                mViewHolder.mRedStatus.setVisibility(View.VISIBLE);
                break;
        }
        mViewHolder.mMoney.setText(itemsBean.getAmount());
        mViewHolder.mRule.setText("消费满" + itemsBean.getLimitAmount() + "可用");
        //截止日期
        long expiredTime = itemsBean.getExpiredTime();
        //获取日期
        long getEffectTime = itemsBean.getEffectTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        mViewHolder.mDate.setText(dateFormat.format(getEffectTime) + "-" + dateFormat.format(expiredTime));
        if (selectRedId == position) {
            mViewHolder.mRedStatus.setImageResource(R.mipmap.select_stamp);
            mViewHolder.mRedStatus.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView mMoney;
        TextView mRule;
        TextView mDate;
        FrameLayout mRedBg;
        ImageView mRedStatus;
    }
}
