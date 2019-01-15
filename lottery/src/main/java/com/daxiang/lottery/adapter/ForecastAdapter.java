package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.OnClickJczqListener;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.score.ScoreDetailActivity;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Android on 2018/3/19.
 */

public class ForecastAdapter extends BaseAdapter {
    private final int colorBlack;
    private final int colorWhite;
    private Context mContext;
    private OnClickJczqListener mOnClickJcListener;
    ArrayList<JczqData.DataBean> mDataList;
    HashMap<JczqData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public ForecastAdapter(Context mContext, ArrayList<JczqData.DataBean> mDataList, OnClickJczqListener mOnClickJcListener) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.mOnClickJcListener = mOnClickJcListener;
        //colorRed = mContext.getResources().getColor(R.color.red_txt);
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
        colorWhite = mContext.getResources().getColor(R.color.white);
    }

    @Override
    public int getCount() {
        if (mDataList == null) return 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_forecast, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mDate_issue = (TextView) convertView.findViewById(R.id.date_issue);
            mViewHolder.mForecast_rate = (TextView) convertView.findViewById(R.id.forecast_rate);
            mViewHolder.mForecast_rate_text = (TextView) convertView.findViewById(R.id.forecast_rate_text);
            mViewHolder.mRl_name = (RelativeLayout) convertView.findViewById(R.id.Rl_name);
            mViewHolder.mHome_name = (TextView) convertView.findViewById(R.id.home_name);
            mViewHolder.mVs_score = (TextView) convertView.findViewById(R.id.vs_score);
            mViewHolder.mHome_avatar = (ImageView) convertView.findViewById(R.id.home_avatar);
            mViewHolder.mGuest_avatar = (ImageView) convertView.findViewById(R.id.guest_avatar);
            mViewHolder.mGuest_name = (TextView) convertView.findViewById(R.id.guest_name);
            mViewHolder.mHome_title = (TextView) convertView.findViewById(R.id.home_title);
            mViewHolder.mPing_title = (TextView) convertView.findViewById(R.id.ping_title);
            mViewHolder.mGuest_title = (TextView) convertView.findViewById(R.id.guest_title);
            mViewHolder.mHome_square = (TextView) convertView.findViewById(R.id.home_square);
            mViewHolder.mPing_square = (TextView) convertView.findViewById(R.id.ping_square);
            mViewHolder.mGuest_square = (TextView) convertView.findViewById(R.id.guest_square);
            mViewHolder.mHome_odds = (TextView) convertView.findViewById(R.id.home_odds);
            mViewHolder.mPing_odds = (TextView) convertView.findViewById(R.id.ping_odds);
            mViewHolder.mGuest_odds = (TextView) convertView.findViewById(R.id.guest_odds);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final JczqData.DataBean dataBean = mDataList.get(position);
        //解决复用点击事件错乱的问题
        recycleUse(dataBean, mViewHolder);
        final int let = dataBean.getLet();
        final String uMid = dataBean.getuMid();
        String content = dataBean.getContent();
        String score = dataBean.getScore();
        String away = dataBean.getAway();
        String home = dataBean.getHome();
        String awayLogo = dataBean.getAwayLogo();
        String homeLogo = dataBean.getHomeLogo();
        final String leagueName = dataBean.getLeagueName();
        String odds1 = dataBean.getOdds1();
        String odds0 = dataBean.getOdds0();
        String odds3 = dataBean.getOdds3();
        String letOdds0 = dataBean.getLetOdds0();
        String letOdds1 = dataBean.getLetOdds1();
        String letOdds3 = dataBean.getLetOdds3();
        String rate = dataBean.getRate();
        String stopSaleTime = dataBean.getStopSaleTime();
        String session = dataBean.getSession();
        if (!TextUtils.isEmpty(awayLogo)) {
            // HttpUtils2.display(mViewHolder.mGuest_avatar, awayLogo, R.mipmap.guest_team);
            Picasso.with(mContext)
                    .load(awayLogo)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.mipmap.guest_team)
                    .error(R.mipmap.guest_team)
                    .into(mViewHolder.mGuest_avatar);
        }
        if (!TextUtils.isEmpty(homeLogo)) {
            // HttpUtils2.display(mViewHolder.mHome_avatar, homeLogo, R.mipmap.home_team);
            Picasso.with(mContext)
                    .load(homeLogo)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.mipmap.home_team)
                    .error(R.mipmap.home_team)
                    .into(mViewHolder.mHome_avatar);
        }
        if (!TextUtils.isEmpty(rate)) {
            String rate2 = rate.substring(0, rate.length() - 1);
            rate = (int) Double.parseDouble(rate2) + "%";
        }
        String homeStr = home + (let == 0 ? "" : ("(" + (let < 0 ? let : ("+" + let)) + ")"));
        SpannableString ss = new SpannableString(homeStr);
        if (let < 0)
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.green_let)), home.length(), homeStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        else if (let > 0) {
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.orange_let)), home.length(), homeStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        mViewHolder.mHome_name.setText(ss);
        mViewHolder.mGuest_name.setText(TextUtils.isEmpty(away) ? "--" : away);
        mViewHolder.mVs_score.setText(TextUtils.isEmpty(score) ? "VS" : score);

        if (let == 0) {
            mViewHolder.mHome_title.setText("主胜");
            mViewHolder.mPing_title.setText("平");
            mViewHolder.mGuest_title.setText("主负");
            mViewHolder.mHome_odds.setText("主胜" + odds3);
            mViewHolder.mPing_odds.setText("平" + odds1);
            mViewHolder.mGuest_odds.setText("主负" + odds0);
        } else {
            mViewHolder.mHome_title.setText("让胜");
            mViewHolder.mPing_title.setText("让平");
            mViewHolder.mGuest_title.setText("让负");
            mViewHolder.mHome_odds.setText("让胜" + letOdds3);
            mViewHolder.mPing_odds.setText("让平" + letOdds1);
            mViewHolder.mGuest_odds.setText("让负" + letOdds0);
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime >= Long.parseLong(stopSaleTime)) {//当前时间已经大于截期了。
            mViewHolder.mRl_name.setVisibility(View.GONE);
        } else {//还没有截期
            mViewHolder.mRl_name.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(score)) {//比赛还没有结束
            mViewHolder.mForecast_rate.setText(TextUtils.isEmpty(rate) ? "--" : rate);
            mViewHolder.mForecast_rate.setBackgroundResource(R.mipmap.forecast_circle);
            mViewHolder.mForecast_rate_text.setText("预测概率");
            mViewHolder.mForecast_rate_text.setBackgroundResource(R.mipmap.forecast_square);
            if (content.contains("3")) {//预测有主胜或者让球主胜
                mViewHolder.mHome_square.setBackgroundResource(R.drawable.shape_red_recommend);
                mViewHolder.mHome_square.setText("荐");
            } else {
                mViewHolder.mHome_square.setBackgroundResource(R.drawable.shape_gray_forecast);
                mViewHolder.mHome_square.setText("");
            }
            if (content.contains("1")) {//预测有平或者让球平
                mViewHolder.mPing_square.setBackgroundResource(R.drawable.shape_red_recommend);
                mViewHolder.mPing_square.setText("荐");
            } else {
                mViewHolder.mPing_square.setBackgroundResource(R.drawable.shape_gray_forecast);
                mViewHolder.mPing_square.setText("");
            }
            if (content.contains("0")) {//预测有主负或者让球主负
                mViewHolder.mGuest_square.setBackgroundResource(R.drawable.shape_red_recommend);
                mViewHolder.mGuest_square.setText("荐");
            } else {
                mViewHolder.mGuest_square.setBackgroundResource(R.drawable.shape_gray_forecast);
                mViewHolder.mGuest_square.setText("");
            }
        } else {//比赛结束 三种情况，推荐了没中，推荐了中了，没有推荐
            boolean isWin = false;
            if (content.contains("3")) {
                if (isWinng(let, "3", score)) {
                    isWin = true;
                    mViewHolder.mHome_square.setBackgroundResource(R.drawable.shape_red_recommend);
                    mViewHolder.mHome_square.setText("荐");
                } else {
                    mViewHolder.mHome_square.setBackgroundResource(R.drawable.shape_gray_recommend);
                    mViewHolder.mHome_square.setText("荐");
                }
            } else {
                mViewHolder.mHome_square.setBackgroundResource(R.drawable.shape_gray_forecast);
                mViewHolder.mHome_square.setText("");
            }
            if (content.contains("1")) {
                if (isWinng(let, "1", score)) {
                    isWin = true;
                    mViewHolder.mPing_square.setBackgroundResource(R.drawable.shape_red_recommend);
                    mViewHolder.mPing_square.setText("荐");
                } else {
                    mViewHolder.mPing_square.setBackgroundResource(R.drawable.shape_gray_recommend);
                    mViewHolder.mPing_square.setText("荐");
                }
            } else {
                mViewHolder.mPing_square.setBackgroundResource(R.drawable.shape_gray_forecast);
                mViewHolder.mPing_square.setText("");
            }
            if (content.contains("0")) {
                if (isWinng(let, "0", score)) {
                    isWin = true;
                    mViewHolder.mGuest_square.setBackgroundResource(R.drawable.shape_red_recommend);
                    mViewHolder.mGuest_square.setText("荐");
                } else {
                    mViewHolder.mGuest_square.setBackgroundResource(R.drawable.shape_gray_recommend);
                    mViewHolder.mGuest_square.setText("荐");
                }
            } else {
                mViewHolder.mGuest_square.setBackgroundResource(R.drawable.shape_gray_forecast);
                mViewHolder.mGuest_square.setText("");
            }
            mViewHolder.mForecast_rate_text.setText("预测概率" + rate);
            if (isWin) {
                mViewHolder.mForecast_rate.setText("命中");
                mViewHolder.mForecast_rate.setBackgroundResource(R.mipmap.forecast_circle);
                mViewHolder.mForecast_rate_text.setBackgroundResource(R.mipmap.forecast_square);
            } else {
                mViewHolder.mForecast_rate.setText("未中");
                mViewHolder.mForecast_rate.setBackgroundResource(R.mipmap.forecast_circle_gray);
                mViewHolder.mForecast_rate_text.setBackgroundResource(R.mipmap.forecast_square_gray);
            }

        }

        if (!TextUtils.isEmpty(session) && session.length() == 11) {
            String issue = session.substring(8, 11);
            String time = session.substring(0, 8);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date parse = null;
            try {
                parse = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            String stopStr = DateFormtUtils.longToDate(Long.parseLong(stopSaleTime));
            mViewHolder.mDate_issue.setText(weekDays[i1 - 1] + issue + " " + leagueName + " " + stopStr);
        }

        mViewHolder.mHome_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (let == 0)
                    clickMatchView(mViewHolder.mHome_title, "主胜", dataBean.getOdds3(), dataBean);
                else
                    clickMatchView(mViewHolder.mHome_title, "让胜", dataBean.getLetOdds3(), dataBean);
            }
        });
        mViewHolder.mPing_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (let == 0)
                    clickMatchView(mViewHolder.mPing_title, "平", dataBean.getOdds1(), dataBean);
                else
                    clickMatchView(mViewHolder.mPing_title, "让平", dataBean.getLetOdds1(), dataBean);
            }
        });
        mViewHolder.mGuest_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (let == 0)
                    clickMatchView(mViewHolder.mGuest_title, "主负", dataBean.getOdds0(), dataBean);
                else
                    clickMatchView(mViewHolder.mGuest_title, "让负", dataBean.getLetOdds0(), dataBean);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(uMid)) return;
                Intent intent = new Intent(mContext, ScoreDetailActivity.class);
                intent.putExtra("mId", uMid);
                intent.putExtra("from", "history");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private void clickMatchView(TextView mTextView, String title, String odds, JczqData.DataBean dataBean) {
        if (mTextView.getTag() == null || !(boolean) mTextView.getTag()) {
            mTextView.setTextColor(colorWhite);
            mTextView.setBackgroundResource(R.drawable.shape_btn);
            mTextView.setTag(true);
            HashMap<String, String> map;
            if (clickMap.containsKey(dataBean)) {
                map = clickMap.get(dataBean);
                map.put(title, odds);
                clickMap.put(dataBean, map);
            } else {
                map = new HashMap<>();
                map.put(title, odds);
                clickMap.put(dataBean, map);
            }
        } else {
            mTextView.setTextColor(colorBlack);
            mTextView.setBackgroundResource(R.drawable.shape_whitebg_gray);
            mTextView.setTag(false);
            //进入这个方法证明clickmap中肯定有这个键。
            HashMap<String, String> map = clickMap.get(dataBean);
            map.remove(title);
            if (map == null || map.size() == 0) {
                clickMap.remove(dataBean);
            } else {
                clickMap.put(dataBean, map);
            }
        }
        mOnClickJcListener.onClickJcListener(clickMap);
    }

    private void recycleUse(JczqData.DataBean dataBean, ViewHolder mViewHolder) {
        if (clickMap.containsKey(dataBean)) {
            HashMap<String, String> map = clickMap.get(dataBean);
            int let = dataBean.getLet();
            if (map.containsKey(let == 0 ? "主胜" : "让胜")) {
                mViewHolder.mHome_title.setTextColor(colorWhite);
                mViewHolder.mHome_title.setBackgroundResource(R.drawable.shape_btn);
                mViewHolder.mHome_title.setTag(true);
            } else {
                mViewHolder.mHome_title.setTextColor(colorBlack);
                mViewHolder.mHome_title.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mHome_title.setTag(false);
            }
            if (map.containsKey(let == 0 ? "平" : "让平")) {
                mViewHolder.mPing_title.setTextColor(colorWhite);
                mViewHolder.mPing_title.setBackgroundResource(R.drawable.shape_btn);
                mViewHolder.mPing_title.setTag(true);
            } else {
                mViewHolder.mPing_title.setTextColor(colorBlack);
                mViewHolder.mPing_title.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mPing_title.setTag(false);
            }
            if (map.containsKey(let == 0 ? "主负" : "让负")) {
                mViewHolder.mGuest_title.setTextColor(colorWhite);
                mViewHolder.mGuest_title.setBackgroundResource(R.drawable.shape_btn);
                mViewHolder.mGuest_title.setTag(true);
            } else {
                mViewHolder.mGuest_title.setTextColor(colorBlack);
                mViewHolder.mGuest_title.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mViewHolder.mGuest_title.setTag(false);
            }
        } else {
            mViewHolder.mHome_title.setTextColor(colorBlack);
            mViewHolder.mHome_title.setBackgroundResource(R.drawable.shape_whitebg_gray);
            mViewHolder.mHome_title.setTag(false);

            mViewHolder.mPing_title.setTextColor(colorBlack);
            mViewHolder.mPing_title.setBackgroundResource(R.drawable.shape_whitebg_gray);
            mViewHolder.mPing_title.setTag(false);

            mViewHolder.mGuest_title.setTextColor(colorBlack);
            mViewHolder.mGuest_title.setBackgroundResource(R.drawable.shape_whitebg_gray);
            mViewHolder.mGuest_title.setTag(false);
        }
    }


    /*
    * 让球
    * 购买的内容
    * 最终的比分
    * */
    private boolean isWinng(int let, String content, String scroe) {
        String[] split = scroe.split("\\:");
        int score1 = Integer.parseInt(split[0]);
        int score2 = Integer.parseInt(split[1]);
        if (let + score1 > score2) {
            return "3".equals(content);
        } else if (let + score1 < score2) {
            return "0".equals(content);
        } else {
            return "1".equals(content);
        }
    }

    class ViewHolder {
        private TextView mDate_issue;
        private TextView mForecast_rate;
        private TextView mForecast_rate_text;
        private RelativeLayout mRl_name;
        private TextView mHome_name;
        private ImageView mHome_avatar;
        private ImageView mGuest_avatar;
        private TextView mGuest_name;
        private TextView mVs_score;
        private TextView mHome_title;
        private TextView mPing_title;
        private TextView mGuest_title;
        private TextView mHome_odds;
        private TextView mPing_odds;
        private TextView mGuest_odds;
        private TextView mPing_square;
        private TextView mHome_square;
        private TextView mGuest_square;
    }
}
