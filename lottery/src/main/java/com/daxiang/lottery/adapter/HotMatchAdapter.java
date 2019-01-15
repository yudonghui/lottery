package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.entity.HotMatchData;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android on 2018/3/8.
 */

public class HotMatchAdapter extends RecyclerView.Adapter<HotMatchAdapter.ViewHolder> {
    private final int colorGray;
    private final int colorBlack;
    private final int colorRed;
    private int jczqState = 1;
    List<HotMatchData.DataBean> mHotMatchList;
    private HashMap<Integer, HashMap<Integer, String>> clickMap = new HashMap<>();//key条目位置，key3,1,0胜平负，value赔率
    private HashMap<Integer, Integer> mutilMap = new HashMap<>();//key条目位置，value倍数。
    Context mContext;

    public HotMatchAdapter(Context mContext, List<HotMatchData.DataBean> mHotMatchList) {
        this.mContext = mContext;
        this.mHotMatchList = mHotMatchList;
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
        colorRed = mContext.getResources().getColor(R.color.red_txt);
    }

    public void setSaleState(int jczqState) {
        this.jczqState = jczqState;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_hot_match, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (jczqState == 1)
            setListener(holder, position);
        HotMatchData.DataBean dataBean = mHotMatchList.get(position);
        holder.setData(dataBean, position);
    }

    @Override
    public int getItemCount() {
        return mHotMatchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHome_avatar;
        private TextView mTeam_name;
        private TextView mEnd_time;
        private ImageView mGuest_avatar;
        private LinearLayout mLl_home;
        private TextView mHome_title;
        private TextView mHome_odds;
        private LinearLayout mLl_ping;
        private TextView mPing_title;
        private TextView mPing_odds;
        private LinearLayout mLl_guest;
        private TextView mGuest_title;
        private TextView mGuest_odds;
        private TextView mHome_forecast;
        private TextView mPing_forecast;
        private TextView mGuest_forecast;
        private TextView mMoney_one;
        private TextView mMoney_two;
        private TextView mMoney_three;
        private TextView mForecast_award;
        private TextView mBuy_confirm;

        private RelativeLayout mRl_mutile;
        private LinearLayout mLl_bottom;

        public ViewHolder(View itemView) {
            super(itemView);
            mHome_avatar = (ImageView) itemView.findViewById(R.id.home_avatar);
            mTeam_name = (TextView) itemView.findViewById(R.id.team_name);
            mEnd_time = (TextView) itemView.findViewById(R.id.end_time);
            mGuest_avatar = (ImageView) itemView.findViewById(R.id.guest_avatar);
            mLl_home = (LinearLayout) itemView.findViewById(R.id.ll_home);
            mHome_title = (TextView) itemView.findViewById(R.id.home_title);
            mHome_odds = (TextView) itemView.findViewById(R.id.home_odds);
            mLl_ping = (LinearLayout) itemView.findViewById(R.id.ll_ping);
            mPing_title = (TextView) itemView.findViewById(R.id.ping_title);
            mPing_odds = (TextView) itemView.findViewById(R.id.ping_odds);
            mLl_guest = (LinearLayout) itemView.findViewById(R.id.ll_guest);
            mGuest_title = (TextView) itemView.findViewById(R.id.guest_title);
            mGuest_odds = (TextView) itemView.findViewById(R.id.guest_odds);
            mHome_forecast = (TextView) itemView.findViewById(R.id.home_forecast);
            mPing_forecast = (TextView) itemView.findViewById(R.id.ping_forecast);
            mGuest_forecast = (TextView) itemView.findViewById(R.id.guest_forecast);
            mMoney_one = (TextView) itemView.findViewById(R.id.money_one);
            mMoney_two = (TextView) itemView.findViewById(R.id.money_two);
            mMoney_three = (TextView) itemView.findViewById(R.id.money_three);
            mForecast_award = (TextView) itemView.findViewById(R.id.forecast_award);
            mBuy_confirm = (TextView) itemView.findViewById(R.id.buy_confirm);

            mRl_mutile = (RelativeLayout) itemView.findViewById(R.id.rl_mutile);
            mLl_bottom = (LinearLayout) itemView.findViewById(R.id.ll_bottom);
            Log.e("执行了", "执行了");
        }

        public void setData(HotMatchData.DataBean dataBean, int position) {
            mMoney_one.setTextColor(colorRed);
            mMoney_one.setBackgroundResource(R.drawable.shape_red_whitebg);

            mMoney_two.setTextColor(colorBlack);
            mMoney_two.setBackgroundResource(R.drawable.shape_whitebg_gray);

            mMoney_three.setTextColor(colorBlack);
            mMoney_three.setBackgroundResource(R.drawable.shape_whitebg_gray);

            Log.e("位置", position + "");
            String league_short_cn = dataBean.getLeague_short_cn();
            long stopTime = dataBean.getStop_sale_time();
            long startTime = dataBean.getKick_off_time();
            String h_pic = dataBean.getH_pic();//主队头像
            String a_pic = dataBean.getA_pic();//客队头像
            String home_short_cn = dataBean.getHome_short_cn();//主队简称
            String guest_short_cn = dataBean.getGuest_short_cn();//客队简称
            HotMatchData.DataBean.OddsBean odds = dataBean.getOdds();
            String odds3 = odds.getOdds3();//主胜赔率
            String odds1 = odds.getOdds1();//平 赔率
            String odds0 = odds.getOdds0();//客胜赔率

            HotMatchData.DataBean.RateBean rate = dataBean.getRate();
            String win = rate.getWin();//主胜概率
            String draw = rate.getDraw();//平概率
            String lose = rate.getLose();//客胜概率

            mHome_forecast.setText(win);
            mPing_forecast.setText(draw);
            mGuest_forecast.setText(lose);

            double winD = Double.parseDouble(win.replaceAll("\\%", " "));
            double drawD = Double.parseDouble(draw.replaceAll("\\%", " "));
            double loseD = Double.parseDouble(lose.replaceAll("\\%", " "));
            double[] aaa = new double[]{winD, drawD, loseD};
            double max = 0;
            int index = 0;
            for (int i = 0; i < aaa.length; i++) {
                if (aaa[i] > max) {
                    max = aaa[i];
                    index = i;
                }
            }
            if (jczqState==1){
                if (index == 0) {
                    mLl_home.setBackgroundResource(R.drawable.shape_red_whitebg);
                    mHome_title.setTextColor(colorRed);
                    mHome_odds.setTextColor(colorRed);
                    mLl_ping.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mPing_title.setTextColor(colorBlack);
                    mPing_odds.setTextColor(colorGray);
                    mLl_guest.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mGuest_title.setTextColor(colorBlack);
                    mGuest_odds.setTextColor(colorGray);
                    HashMap<Integer, String> map = new HashMap<>();
                    map.put(3, odds3);
                    clickMap.put(position, map);
                } else if (index == 1) {
                    mLl_home.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mHome_title.setTextColor(colorBlack);
                    mHome_odds.setTextColor(colorGray);
                    mLl_ping.setBackgroundResource(R.drawable.shape_red_whitebg);
                    mPing_title.setTextColor(colorRed);
                    mPing_odds.setTextColor(colorRed);
                    mLl_guest.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mGuest_title.setTextColor(colorBlack);
                    mGuest_odds.setTextColor(colorGray);
                    HashMap<Integer, String> map = new HashMap<>();
                    map.put(1, odds1);
                    clickMap.put(position, map);
                } else if (index == 2) {
                    mLl_home.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mHome_title.setTextColor(colorBlack);
                    mHome_odds.setTextColor(colorGray);
                    mLl_ping.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mPing_title.setTextColor(colorBlack);
                    mPing_odds.setTextColor(colorGray);
                    mLl_guest.setBackgroundResource(R.drawable.shape_red_whitebg);
                    mGuest_title.setTextColor(colorRed);
                    mGuest_odds.setTextColor(colorRed);
                    HashMap<Integer, String> map = new HashMap<>();
                    map.put(0, odds0);
                    clickMap.put(position, map);
                }
                mRl_mutile.setVisibility(View.VISIBLE);
                mLl_bottom.setVisibility(View.VISIBLE);
                mEnd_time.setText(DateFormtUtils.longToDate(stopTime) + "截止");
                mutilMap.put(position, 10);
                setMoney(mForecast_award, position);//投注的金额。
            }else {
                mRl_mutile.setVisibility(View.GONE);
                mLl_bottom.setVisibility(View.GONE);
                mLl_home.setBackgroundResource(R.drawable.shape_white);
                mLl_ping.setBackgroundResource(R.drawable.shape_white);
                mLl_guest.setBackgroundResource(R.drawable.shape_white);
                mEnd_time.setText(DateFormtUtils.longToDate(startTime) + "开赛");
            }

            mTeam_name.setText(TextUtils.isEmpty(league_short_cn) ? "--" : league_short_cn);

            mHome_title.setText((TextUtils.isEmpty(home_short_cn) ? home_short_cn : home_short_cn) + " 胜");
            mHome_odds.setText(TextUtils.isEmpty(odds3) ? "--" : odds3);

            mPing_title.setText("平");
            mPing_odds.setText(TextUtils.isEmpty(odds1) ? "--" : odds1);

            mGuest_title.setText((TextUtils.isEmpty(guest_short_cn) ? "--" : guest_short_cn) + " 胜");
            mGuest_odds.setText(TextUtils.isEmpty(odds0) ? "--" : odds0);


            if (!TextUtils.isEmpty(h_pic)) {
                Picasso.with(mContext)
                        .load(h_pic)
                        .config(Bitmap.Config.RGB_565)
                        .error(R.mipmap.home_team)
                        .placeholder(R.mipmap.home_team)
                        .into(mHome_avatar);
            }
            if (!TextUtils.isEmpty(a_pic)) {
                Picasso.with(mContext)
                        .load(a_pic)
                        .config(Bitmap.Config.RGB_565)
                        .error(R.mipmap.guest_team)
                        .placeholder(R.mipmap.guest_team)
                        .into(mGuest_avatar);
            }


        }


    }

    private void setListener(final ViewHolder mViewHolder, final int position) {
        HotMatchData.DataBean dataBean = mHotMatchList.get(position);
        final String session = dataBean.getSession();
        HotMatchData.DataBean.OddsBean odds = dataBean.getOdds();
        final String odds3 = odds.getOdds3();
        final String odds1 = odds.getOdds1();
        final String odds0 = odds.getOdds0();
        mViewHolder.mLl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<Integer, String> map = clickMap.get(position);
                if (map.containsKey(3)) {
                    map.remove(3);
                    mViewHolder.mLl_home.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mViewHolder.mHome_title.setTextColor(colorBlack);
                    mViewHolder.mHome_odds.setTextColor(colorGray);
                } else {
                    map.put(3, odds3);
                    mViewHolder.mLl_home.setTag(true);
                    mViewHolder.mLl_home.setBackgroundResource(R.drawable.shape_red_whitebg);
                    mViewHolder.mHome_title.setTextColor(colorRed);
                    mViewHolder.mHome_odds.setTextColor(colorRed);
                }
                setMoney(mViewHolder.mForecast_award, position);
            }
        });
        mViewHolder.mLl_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<Integer, String> map = clickMap.get(position);
                if (map.containsKey(1)) {
                    map.remove(1);
                    mViewHolder.mLl_ping.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mViewHolder.mPing_title.setTextColor(colorBlack);
                    mViewHolder.mPing_odds.setTextColor(colorGray);
                } else {
                    map.put(1, odds1);
                    mViewHolder.mLl_ping.setBackgroundResource(R.drawable.shape_red_whitebg);
                    mViewHolder.mPing_title.setTextColor(colorRed);
                    mViewHolder.mPing_odds.setTextColor(colorRed);
                }
                setMoney(mViewHolder.mForecast_award, position);
            }
        });
        mViewHolder.mLl_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<Integer, String> map = clickMap.get(position);
                if (map.containsKey(0)) {
                    map.remove(0);
                    mViewHolder.mLl_guest.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mViewHolder.mGuest_title.setTextColor(colorBlack);
                    mViewHolder.mGuest_odds.setTextColor(colorGray);
                } else {
                    map.put(0, odds0);
                    mViewHolder.mLl_guest.setBackgroundResource(R.drawable.shape_red_whitebg);
                    mViewHolder.mGuest_title.setTextColor(colorRed);
                    mViewHolder.mGuest_odds.setTextColor(colorRed);
                }
                setMoney(mViewHolder.mForecast_award, position);
            }
        });
        mViewHolder.mMoney_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mMoney_one.setBackgroundResource(R.drawable.shape_red_whitebg);
                mViewHolder.mMoney_one.setTextColor(colorRed);
                mViewHolder.mMoney_two.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mMoney_two.setTextColor(colorBlack);
                mViewHolder.mMoney_three.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mMoney_three.setTextColor(colorBlack);
                mutilMap.put(position, 10);
                setMoney(mViewHolder.mForecast_award, position);
            }
        });
        mViewHolder.mMoney_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mMoney_one.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mMoney_one.setTextColor(colorBlack);
                mViewHolder.mMoney_two.setBackgroundResource(R.drawable.shape_red_whitebg);
                mViewHolder.mMoney_two.setTextColor(colorRed);
                mViewHolder.mMoney_three.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mMoney_three.setTextColor(colorBlack);
                mutilMap.put(position, 20);
                setMoney(mViewHolder.mForecast_award, position);
            }
        });
        mViewHolder.mMoney_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mMoney_one.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mMoney_one.setTextColor(colorBlack);
                mViewHolder.mMoney_two.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mMoney_two.setTextColor(colorBlack);
                mViewHolder.mMoney_three.setBackgroundResource(R.drawable.shape_red_whitebg);
                mViewHolder.mMoney_three.setTextColor(colorRed);
                mutilMap.put(position, 50);
                setMoney(mViewHolder.mForecast_award, position);
            }
        });
        mViewHolder.mBuy_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LotteryApp.isLogin) {
                    if (!LotteryApp.phoneFlag) {
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                        HintDialogUtils.setTitleVisiable(true);
                        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                            @Override
                            public void callBack(View view) {
                                //手机号未绑定
                                if (LotteryApp.isThird) {
                                    Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext, BindPhoneActivity.class);
                                    intent.putExtra("isBind", false);
                                    mContext.startActivity(intent);
                                }

                            }
                        });
                    } else {
                        HashMap<Integer, String> map = clickMap.get(position);
                        int mutile = mutilMap.get(position);
                        if (map == null || map.size() == 0) {
                            Toast.makeText(mContext, "请选择投注内容", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("SPF|" + session + "=");
                        int i = 0;
                        for (Map.Entry<Integer, String> entry : map.entrySet()) {
                            int key = entry.getKey();
                            String value = entry.getValue();
                            if (i == 0)
                                sb.append(key + "(" + value + ")");
                            else sb.append("/" + key + "(" + value + ")");
                            i++;
                        }
                        sb.append("|1*1");
                        Bundle bundle = new Bundle();
                        bundle.putInt("requestCode", 200);
                        bundle.putString("buyMethod", "normal");
                        bundle.putString("content", String.valueOf(sb));
                        bundle.putInt("shakes", map.size());
                        bundle.putInt("mMulti", mutile);
                        bundle.putString("money", mutile * map.size() * 2 + "");
                        bundle.putString("issue", session);
                        bundle.putString("lotcode", "42");
                        Log.e("投注内容", bundle.toString());
                        NetWorkData netWorkData = new NetWorkData(mContext, bundle);
                        netWorkData.orderForm();

                    }
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void setMoney(TextView mForecast_award, int position) {
        int mutil = mutilMap.get(position);//倍数
        HashMap<Integer, String> map = clickMap.get(position);
        String buyMoney = mutil * map.size() * 2 + "";
        String string = "投注金额: " + buyMoney + "元";
        int indexOf = string.indexOf(buyMoney);
        SpannableString ss = new SpannableString(string);
        ss.setSpan(new ForegroundColorSpan(colorRed), indexOf, indexOf + buyMoney.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mForecast_award.setText(ss);
    }

    View.OnClickListener ItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
