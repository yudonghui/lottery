package com.daxiang.lottery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.SearchData;
import com.daxiang.lottery.utils.HttpUtils2;

import java.util.ArrayList;

/**
 * Created by Android on 2018/3/15.
 */

public class SearchGodAdapter extends BaseAdapter {
    ArrayList<SearchData.DataBean> dataList;
    private Context mContext;

    public SearchGodAdapter(Context mContext, ArrayList<SearchData.DataBean> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_search_god, null);
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.avatar);
        TextView mTextView = (TextView) convertView.findViewById(R.id.user_name);
        SearchData.DataBean dataBean = dataList.get(position);
        String headImg = dataBean.getHeadImg();
        String userId = dataBean.getUserId();
        String userName = dataBean.getUserName();
        mTextView.setText(TextUtils.isEmpty(userName) ? "--" : userName);
        if (!TextUtils.isEmpty(headImg)) {
            HttpUtils2.display(mImageView, headImg);
        }
        return convertView;
    }
}
