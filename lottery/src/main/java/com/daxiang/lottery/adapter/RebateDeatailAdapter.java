package com.daxiang.lottery.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.entity.RebateDetailData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/28.
 */

public class RebateDeatailAdapter extends BaseAdapter {
    ArrayList<RebateDetailData.DataBean.ItemsBean.DetailBean> detailList;
    SimpleDateFormat dateFormat;

    public RebateDeatailAdapter(ArrayList<RebateDetailData.DataBean.ItemsBean.DetailBean> detailList) {
        this.detailList = detailList;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    }

    @Override
    public int getCount() {
        return detailList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_rebate_detail, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLotcode = (TextView) convertView.findViewById(R.id.lotcode);
            mViewHolder.mCreat_time = (TextView) convertView.findViewById(R.id.creat_time);
            mViewHolder.mUser_name = (TextView) convertView.findViewById(R.id.user_name);
            mViewHolder.mCost = (TextView) convertView.findViewById(R.id.cost);
            mViewHolder.mAmount = (TextView) convertView.findViewById(R.id.amount);
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        RebateDetailData.DataBean.ItemsBean.DetailBean dataBean = detailList.get(position);
        String userId = dataBean.getUserId();
        HttpUtils2.display(mViewHolder.mAvatar, Url.HEADER_ROOT_URL + userId);
        mViewHolder.mLotcode.setText(LotteryTypes.getTypes(dataBean.getLotCode()));
        String formatTime = dateFormat.format(Long.parseLong(dataBean.getCreateTime()));
        mViewHolder.mCreat_time.setText(formatTime);
        mViewHolder.mAmount.setText(dataBean.getAmount() + "");
        mViewHolder.mCost.setText(StringUtil.getString(dataBean.getCost()));
        mViewHolder.mUser_name.setText(dataBean.getUserName());
        return convertView;
    }

    class ViewHolder {
        private TextView mLotcode;
        //创建时间
        private TextView mCreat_time;
        //用户名
        private TextView mUser_name;
        //投注金额
        private TextView mCost;
        //返利
        private TextView mAmount;
        private ImageView mAvatar;
    }
}
