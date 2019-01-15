package com.daxiang.lottery.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.HomeLotteryData;
import com.daxiang.lottery.fragment.HomeFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android on 2018/3/9.
 */

public class LotteryFormAdapter extends DragGridAdapter<HomeLotteryData.DataBean.Item1Bean> {
    Map<String, Integer> mMap = new HashMap<>();
    int week_index;

    public LotteryFormAdapter(List<HomeLotteryData.DataBean.Item1Bean> mHomeLotteryList) {
        super(mHomeLotteryList);
        mMap.put("42", R.drawable.jczq);
        mMap.put("43", R.drawable.jclq);
        mMap.put("1000", R.drawable.jczqd);
        mMap.put("1001", R.drawable.jclqd);
        mMap.put("23529", R.drawable.dlt);
        mMap.put("11", R.drawable.sfc);
        mMap.put("19", R.drawable.rj);
        mMap.put("33", R.drawable.pls);
        mMap.put("35", R.drawable.plw);
        mMap.put("10022", R.drawable.qxc);
        mMap.put("23528", R.drawable.qlc);
        mMap.put("51", R.drawable.ssq);
        mMap.put("21406", R.drawable.syydj);
        mMap.put("52", R.drawable.fcsd);
        mMap.put("36", R.drawable.jxks);
        mMap.put("50", R.drawable.cqss);
        mMap.put("30", R.drawable.gyj);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week_index = 7;
        } else {
            week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        List<HomeLotteryData.DataBean.Item1Bean> list = getList();
        HomeLotteryData.DataBean.Item1Bean dataBean = list.get(position);
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_gv, null);
        TextView mTitle = (TextView) convertView.findViewById(R.id.text_title);
        TextView mRule = (TextView) convertView.findViewById(R.id.text_rule);
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.image);
        ImageView mImageCover = (ImageView) convertView.findViewById(R.id.image_cover);
        ImageView mImageToday = (ImageView) convertView.findViewById(R.id.todaykaijiang);
        String name = dataBean.getLotName();
        int state = dataBean.getState();
        String info = dataBean.getDescription();
        String lotcode = dataBean.getLotCode();
        mTitle.setText(name);

        mImageView.setImageResource(mMap.get(lotcode));


        switch (lotcode) {
            case "51":
                if (week_index == 2 || week_index == 4 || week_index == 7) {
                    mImageToday.setImageResource(R.mipmap.todaykaijiang);
                    mImageToday.setVisibility(View.VISIBLE);
                } else mImageToday.setVisibility(View.INVISIBLE);
                setPool(mRule, HomeFragment.Lottery_SSQ, info);
                break;
            case "23529":
                if (week_index == 1 || week_index == 3 || week_index == 6) {
                    mImageToday.setImageResource(R.mipmap.todaykaijiang);
                    mImageToday.setVisibility(View.VISIBLE);
                } else mImageToday.setVisibility(View.INVISIBLE);
                setPool(mRule, HomeFragment.Lottery_DLT, info);
                break;
            case "23528":
                if (week_index == 1 || week_index == 3 || week_index == 5) {
                    mImageToday.setImageResource(R.mipmap.todaykaijiang);
                    mImageToday.setVisibility(View.VISIBLE);
                } else mImageToday.setVisibility(View.INVISIBLE);
                setPool(mRule, HomeFragment.Lottery_QLC, info);
                break;
            case "10022":
                if (week_index == 2 || week_index == 5 || week_index == 7) {
                    mImageToday.setImageResource(R.mipmap.todaykaijiang);
                    mImageToday.setVisibility(View.VISIBLE);
                } else mImageToday.setVisibility(View.INVISIBLE);
                setPool(mRule, HomeFragment.Lottery_QXC, info);
                break;
            default:
                mRule.setText(info);
                mImageToday.setVisibility(View.INVISIBLE);
                break;

        }
        if (state == 0) {
            mImageCover.setImageResource(R.mipmap.stop_sale);
            mImageCover.setVisibility(View.VISIBLE);
        } else {
            mImageCover.setVisibility(View.GONE);
        }

        return convertView;
    }

    private void setPool(TextView mRule, String lottery1, String info) {
        if (lottery1 == null) {
            return;
        }
        String lottery = lottery1.split("\\.")[0];
        if (lottery != null && !"0".equals(lottery) && !TextUtils.isEmpty(lottery)) {
            if (lottery.length() == 10) {
                String str = lottery.substring(0, 2);
                String str2 = lottery.substring(2, 4);
                // mRule.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                mRule.setText("奖池:" + str + "." + str2 + "亿元");
            } else if (lottery.length() == 9) {
                String str = lottery.substring(0, 1);
                String str2 = lottery.substring(2, 4);
                /// mRule.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                mRule.setText("奖池:" + str + "." + str2 + "亿元");
            } else if (lottery.length() <= 8) {
                int str = (int) (Long.parseLong(lottery) / 10000);
                // mRule.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                mRule.setText("奖池:" + str + "万元");
            }
        } else {
            mRule.setText(info);
        }
    }
}
