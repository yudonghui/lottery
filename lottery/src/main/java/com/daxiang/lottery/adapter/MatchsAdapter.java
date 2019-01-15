package com.daxiang.lottery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.MatchsData;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Android on 2018/3/28.
 */

public class MatchsAdapter extends BaseAdapter {
    List<MatchsData.DataBean.ItemsBean> mDataList;
    private Context mContext;

    public MatchsAdapter(Context mContext, List<MatchsData.DataBean.ItemsBean> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_matchs, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        MatchsData.DataBean.ItemsBean itemsBean = mDataList.get(position);
        String league_name = itemsBean.getLeague_name();
        String icon = itemsBean.getIcon();
        mViewHolder.mName.setText(TextUtils.isEmpty(league_name) ? "" : league_name);
        if (!TextUtils.isEmpty(icon)) {
            Picasso.with(mContext)
                    .load(icon)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.mipmap.matchs_default_img)
                    .error(R.mipmap.matchs_default_img)
                    .into(mViewHolder.mAvatar);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView mAvatar;
        private TextView mName;
    }
}
