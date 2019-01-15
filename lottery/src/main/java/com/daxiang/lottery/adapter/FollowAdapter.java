package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.entity.FollowData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;

import java.util.List;

/**
 * Created by Android on 2018/3/26.
 */

public class FollowAdapter extends BaseAdapter {
    Context mContext;
    List<FollowData.DataBean.ListBean> FollowList;

    public FollowAdapter(Context mContext, List<FollowData.DataBean.ListBean> FollowList) {
        this.mContext = mContext;
        this.FollowList = FollowList;
    }

    @Override
    public int getCount() {
        return FollowList.size();
    }

    @Override
    public Object getItem(int position) {
        return FollowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_follow, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            mViewHolder.mRenzheng = (ImageView) convertView.findViewById(R.id.renzheng);
            mViewHolder.mUser_name = (TextView) convertView.findViewById(R.id.user_name);
            mViewHolder.mBuy_num = (TextView) convertView.findViewById(R.id.buy_num);
            convertView.setTag(mViewHolder);
        } else mViewHolder = (ViewHolder) convertView.getTag();
        FollowData.DataBean.ListBean listBean = FollowList.get(position);
        final String focusUserId = listBean.getFocusUserId();
        String headImg = listBean.getHeadImg();
        String userName = listBean.getUserName();
        String isCertified = listBean.getIsCertified();//是否是认证大神.//0普通大神，1认证大神
        String canBuy = listBean.getCanBuy();//发了一个推荐单
        mViewHolder.mUser_name.setText(TextUtils.isEmpty(userName) ? "" : userName);

        if (TextUtils.isEmpty(canBuy) || "0".equals(canBuy)) {
            mViewHolder.mBuy_num.setVisibility(View.GONE);
        } else {
            mViewHolder.mBuy_num.setVisibility(View.VISIBLE);
            mViewHolder.mBuy_num.setText(canBuy);
        }
        if ("1".equals(isCertified)) {
            mViewHolder.mRenzheng.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mRenzheng.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(headImg)) {
            HttpUtils2.display(mViewHolder.mAvatar, headImg);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastClick(2000)){
                    return;
                }
                Intent intent = new Intent(mContext, GodInfoActivity.class);
                intent.putExtra("userId", focusUserId);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView mAvatar;
        private ImageView mRenzheng;
        private TextView mUser_name;
        private TextView mBuy_num;
    }
}
