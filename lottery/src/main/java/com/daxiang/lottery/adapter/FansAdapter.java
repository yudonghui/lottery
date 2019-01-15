package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.FansData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Android on 2018/3/26.
 */

public class FansAdapter extends BaseAdapter {
    Context mContext;
    HashMap<Integer, FansData.DataBean.ListBean> mMap = new HashMap<>();

    public FansAdapter(Context mContext, List<FansData.DataBean.ListBean> FansList) {
        this.mContext = mContext;
        mMap.clear();
        for (int i = 0; i < FansList.size(); i++) {
            mMap.put(i, FansList.get(i));
        }
    }

    @Override
    public int getCount() {
        return mMap.size();
    }

    @Override
    public Object getItem(int position) {
        return mMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_fans, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            mViewHolder.mRenzheng = (ImageView) convertView.findViewById(R.id.renzheng);
            mViewHolder.mUser_name = (TextView) convertView.findViewById(R.id.user_name);
            mViewHolder.mBtnFollow = (TextView) convertView.findViewById(R.id.btn_follow);
            convertView.setTag(mViewHolder);
        } else mViewHolder = (ViewHolder) convertView.getTag();
        final FansData.DataBean.ListBean listBean = mMap.get(position);
        final String fansUserId = listBean.getFansUserId();
        final int focusFlag = listBean.getFocusFlag();//0未关注，1已关注
        String headImg = listBean.getHeadImg();
        String userName = listBean.getUserName();
        String isCertified = listBean.getIsCertified();
        if (focusFlag == 0) {
            mViewHolder.mBtnFollow.setBackgroundResource(R.drawable.shape_orange_20);
            mViewHolder.mBtnFollow.setTextColor(mContext.getResources().getColor(R.color.orange_let));
            mViewHolder.mBtnFollow.setText("关注");
        } else {
            mViewHolder.mBtnFollow.setBackgroundColor(Color.WHITE);
            mViewHolder.mBtnFollow.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
            mViewHolder.mBtnFollow.setText("已关注");
        }
        if ("1".equals(isCertified)) {
            mViewHolder.mRenzheng.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mRenzheng.setVisibility(View.GONE);
        }
        if (fansUserId.equals(LotteryApp.uid))mViewHolder.mBtnFollow.setVisibility(View.GONE);
        mViewHolder.mUser_name.setText(TextUtils.isEmpty(userName) ? "" : userName);
        if (!TextUtils.isEmpty(headImg)) {
            HttpUtils2.display(mViewHolder.mAvatar, headImg);
        }
        mViewHolder.mBtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data(listBean, position, mViewHolder.mBtnFollow);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastClick(2000)){
                    return;
                }
                Intent intent = new Intent(mContext, GodInfoActivity.class);
                intent.putExtra("userId", fansUserId + "");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private void data(final FansData.DataBean.ListBean listBean, final int position, final TextView mBtnFollow) {
        if (!LotteryApp.isLogin) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            return;
        }
        String fansUserId = listBean.getFansUserId();
        int focusFlag = listBean.getFocusFlag();
        final String url;
        if (focusFlag == 0) {//还没有关注的状态
            url = "add";
        } else {//已关注的状态
            url = "cancel";
        }
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        params.putString("focusUserId", fansUserId);
        mHttp.get(Url.FOCUS_GOD + url, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        if ("add".equals(url)) {
                            listBean.setFocusFlag(1);
                            mMap.put(position, listBean);

                            mBtnFollow.setBackgroundColor(Color.WHITE);
                            mBtnFollow.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                            mBtnFollow.setText("已关注");
                        } else {
                            listBean.setFocusFlag(0);
                            mMap.put(position, listBean);

                            mBtnFollow.setBackgroundResource(R.drawable.shape_orange_20);
                            mBtnFollow.setTextColor(mContext.getResources().getColor(R.color.orange_let));
                            mBtnFollow.setText("关注");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    class ViewHolder {
        private ImageView mAvatar;
        private ImageView mRenzheng;
        private TextView mUser_name;
        private TextView mBtnFollow;
    }
}
