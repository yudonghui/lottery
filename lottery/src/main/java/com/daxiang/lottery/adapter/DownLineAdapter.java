package com.daxiang.lottery.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.entity.DownLineData;
import com.daxiang.lottery.utils.HttpUtils2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/28.
 */

public class DownLineAdapter extends BaseAdapter {
    ArrayList<DownLineData.DataBeanX.ItemsBean.DataBean> itemsBeen;

    public DownLineAdapter(ArrayList<DownLineData.DataBeanX.ItemsBean.DataBean> itemsBeen) {
        this.itemsBeen = itemsBeen;
    }

    @Override
    public int getCount() {
        return itemsBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_downline, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mUserName = (TextView) convertView.findViewById(R.id.downline_username);
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.mCreatTime = (TextView) convertView.findViewById(R.id.creatTime);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        DownLineData.DataBeanX.ItemsBean.DataBean itemsBean = itemsBeen.get(position);
        String userName = itemsBean.getUserName();
        long createTime = itemsBean.getCreateTime();
        String userId = itemsBean.getUserId();
        mViewHolder.mUserName.setText(userName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(createTime);
        mViewHolder.mCreatTime.setText("注册时间: " + format);
        HttpUtils2.display(mViewHolder.mAvatar, Url.HEADER_ROOT_URL + userId);
        return convertView;
    }

    class ViewHolder {
        TextView mUserName;
        TextView mCreatTime;
        ImageView mAvatar;

    }
}
