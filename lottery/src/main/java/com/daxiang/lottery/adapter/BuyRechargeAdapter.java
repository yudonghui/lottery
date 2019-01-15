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
 * Created by Administrator on 2017/3/14.
 */

public class BuyRechargeAdapter extends BaseAdapter {
    ArrayList<RechargeMethodData.DataBean.ItemsBean> mRechargeList;
    //boolean isWxAccount;
    String code;

    public BuyRechargeAdapter(ArrayList<RechargeMethodData.DataBean.ItemsBean> mRechargeList,
                              String code) {
        this.mRechargeList = mRechargeList;
        this.code = code;
    }

    /* public void setData(boolean isWxAccount){
         this.isWxAccount=isWxAccount;
     }*/
    public void setData(String code) {
        this.code = code;
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
            view = View.inflate(parent.getContext(), R.layout.item_buy_recharge, null);
            ImageView mImageView = (ImageView) view.findViewById(R.id.image_recharge);
            TextView mTitle = (TextView) view.findViewById(R.id.text_recharge);
            ImageView mOnOff = (ImageView) view.findViewById(R.id.onoff);
            mTitle.setText(itemsBean.getName());
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
                //连连支付
                case "LL_PAY":
                    imageId = R.mipmap.lianlianpay;
                    break;
                //u储蓄卡支付
                case "U_PAY":
                    imageId = R.mipmap.pay_savingscard;
                    break;
                //微信支付
                case "WE_CHAT":
                    imageId = R.mipmap.weixin;
                    break;
            }
            if (imageId != 0) {
                mImageView.setImageResource(imageId);
            }
            if (itemsBean.getCode().equals(code)) {
                mOnOff.setImageResource(R.drawable.selected);
            } else mOnOff.setImageResource(R.drawable.unselected);
        }

        return view;
    }

    public interface RechargeSelect {
        void callBack(int position, View mOnOff);
    }
}
