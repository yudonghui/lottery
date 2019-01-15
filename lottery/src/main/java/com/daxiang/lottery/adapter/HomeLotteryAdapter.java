package com.daxiang.lottery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.HomeLotteryData;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HomeLotteryAdapter extends BaseAdapter {
    List<HomeLotteryData.DataBean.Item1Bean> mHomeLotteryList;
    Context mContext;
    Map<String, Integer> mMap = new HashMap<>();
    int week_index;

    public HomeLotteryAdapter(List<HomeLotteryData.DataBean.Item1Bean> mHomeLotteryList) {
        this.mHomeLotteryList = mHomeLotteryList;
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
    public int getCount() {
        if (mHomeLotteryList.size() > 7)
            return 8;
        else
            return mHomeLotteryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHomeLotteryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        mContext = parent.getContext();
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_home_gv2, null);
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.text_title);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.mImageCover = (ImageView) convertView.findViewById(R.id.image_cover);
            viewHolder.mImageToday = (ImageView) convertView.findViewById(R.id.todaykaijiang);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 7) {//最后一个
            viewHolder.mImageView.setImageResource(R.mipmap.more_lottery);
            viewHolder.mTitle.setText("更多彩种");
            viewHolder.mImageToday.setVisibility(View.INVISIBLE);
        } else {
            String name = mHomeLotteryList.get(position).getLotName();
            int state = mHomeLotteryList.get(position).getState();
            viewHolder.mTitle.setText(TextUtils.isEmpty(name) ? "--" : name);
            viewHolder.mImageView.setImageResource(mMap.get(mHomeLotteryList.get(position).getLotCode()));
            final String lotcode = mHomeLotteryList.get(position).getLotCode();
            switch (lotcode) {
                case "51":
                    if (week_index == 2 || week_index == 4 || week_index == 7) {
                        viewHolder.mImageToday.setImageResource(R.mipmap.todaykaijiang);
                        viewHolder.mImageToday.setVisibility(View.VISIBLE);
                    } else viewHolder.mImageToday.setVisibility(View.INVISIBLE);
                    break;
                case "23529":
                    if (week_index == 1 || week_index == 3 || week_index == 6) {
                        viewHolder.mImageToday.setImageResource(R.mipmap.todaykaijiang);
                        viewHolder.mImageToday.setVisibility(View.VISIBLE);
                    } else viewHolder.mImageToday.setVisibility(View.INVISIBLE);
                    break;
                case "23528":
                    if (week_index == 1 || week_index == 3 || week_index == 5) {
                        viewHolder.mImageToday.setImageResource(R.mipmap.todaykaijiang);
                        viewHolder.mImageToday.setVisibility(View.VISIBLE);
                    } else viewHolder.mImageToday.setVisibility(View.INVISIBLE);
                    break;
                case "10022":
                    if (week_index == 2 || week_index == 5 || week_index == 7) {
                        viewHolder.mImageToday.setImageResource(R.mipmap.todaykaijiang);
                        viewHolder.mImageToday.setVisibility(View.VISIBLE);
                    } else viewHolder.mImageToday.setVisibility(View.INVISIBLE);
                    break;
                default:
                    viewHolder.mImageToday.setVisibility(View.INVISIBLE);
                    break;

            }
            if (state == 0) {//停止销售
                viewHolder.mImageCover.setVisibility(View.VISIBLE);
                viewHolder.mImageCover.setImageResource(R.mipmap.stop_sale);
            } else {
                viewHolder.mImageCover.setVisibility(View.GONE);
            }

        }

        return convertView;
    }

    class ViewHolder {
        ImageView mImageView;
        ImageView mImageCover;
        TextView mTitle;
        ImageView mImageToday;
    }
}
