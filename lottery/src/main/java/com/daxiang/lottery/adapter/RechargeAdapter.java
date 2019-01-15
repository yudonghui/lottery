package com.daxiang.lottery.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.RechargeMethodData;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/1/10.
 */
public class RechargeAdapter extends BaseAdapter {
    ArrayList<RechargeMethodData.DataBean.ItemsBean> mRechargeList;

    public RechargeAdapter(ArrayList<RechargeMethodData.DataBean.ItemsBean> mRechargeList) {
        this.mRechargeList = mRechargeList;
    }

    @Override
    public int getCount() {
        return mRechargeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRechargeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        RechargeMethodData.DataBean.ItemsBean itemsBean = mRechargeList.get(position);
        if (itemsBean.getClient().contains("2")) {
            view = View.inflate(parent.getContext(), R.layout.item_recharge, null);
            ImageView mImageView = (ImageView) view.findViewById(R.id.image_title);
            TextView mTitle = (TextView) view.findViewById(R.id.text_title);
            TextView mDesc = (TextView) view.findViewById(R.id.text_desc);
            mTitle.setText(itemsBean.getName());
            mDesc.setText(itemsBean.getDescription());
            int imageId = 0;
            switch (itemsBean.getCode()) {
                //京东支付
                case "JD_PAY":
                    imageId = R.mipmap.pay_wepay;
                    break;
                case "QQ_PAY":
                    imageId = R.mipmap.qq_pay;
                    break;

                //支付宝充值(手动转账)
                case "ALIPAY_OFFLINE":
                    imageId = R.mipmap.pay_alipay_off;
                    break;
                //支付宝充值（自动转账）
                case "ALIPAY_ONLINE":
                    imageId = R.mipmap.pay_alipay_on;
                    break;
                //u储蓄卡支付
                case "U_PAY":
                    imageId = R.mipmap.pay_savingscard;
                    break;
                //微信支付
                case "WE_CHAT":
                    imageId = R.mipmap.weixin;
                    break;
                //连连支付
                case "LL_PAY":
                    imageId = R.mipmap.lianlianpay;
                    break;
            }
            if (imageId != 0) {
                mImageView.setImageResource(imageId);
            }
        }

        return view;
    }
}
