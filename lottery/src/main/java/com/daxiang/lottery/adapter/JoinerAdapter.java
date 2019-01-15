package com.daxiang.lottery.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.JoinData;
import com.daxiang.lottery.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class JoinerAdapter extends BaseAdapter {
    List<JoinData.DataBean.ListBean> joinList;

    public JoinerAdapter(List<JoinData.DataBean.ListBean> joinList) {
        this.joinList = joinList;
    }

    @Override
    public int getCount() {
        if (joinList != null) {
            return joinList.size();
        } else return 0;

    }

    @Override
    public Object getItem(int position) {
        return joinList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_joiner, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mJoin_username = (TextView) convertView.findViewById(R.id.join_username);
            mViewHolder.mJoin_money = (TextView) convertView.findViewById(R.id.join_money);
            mViewHolder.mJoin_time = (TextView) convertView.findViewById(R.id.join_time);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        JoinData.DataBean.ListBean listBean = joinList.get(position);
        String userName = listBean.getUserName();
        String userId = listBean.getUserId();
        mViewHolder.mJoin_username.setText(StringUtil.hintString(userName));
        if ("7110921521400241".equals(userId) && "612290001112100".equals(LotteryApp.uid)) {
            mViewHolder.mJoin_username.setTextColor(parent.getContext().getResources().getColor(R.color.red_txt));
        } else {
            mViewHolder.mJoin_username.setTextColor(parent.getContext().getResources().getColor(R.color.deep_txt));
        }
        mViewHolder.mJoin_money.setText(listBean.getTotalPrice() + "");
        String betTime = listBean.getBetTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        if (betTime.contains("-") || betTime.contains(":")) {
            try {
                time = dateFormat1.parse(betTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            time = Long.parseLong(betTime);
        }
        String formatTime = dateFormat.format(time);
        mViewHolder.mJoin_time.setText(formatTime);
        return convertView;
    }

    class ViewHolder {
        private TextView mJoin_username;
        private TextView mJoin_money;
        private TextView mJoin_time;
    }
}
