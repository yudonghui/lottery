package com.daxiang.lottery.score;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/9
 * @describe May the Buddha bless bug-free!!!
 */
public class ScoreDetailAdapter extends BaseAdapter {
    private final int colorGrayBg;
    private final int colorWhiteBg;
    private Context mContext;
    List<ScoreDetailBean.DataBean.ItemsBean.EventBean> eventList;

    public ScoreDetailAdapter(Context mContext, List<ScoreDetailBean.DataBean.ItemsBean.EventBean> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
        colorGrayBg = mContext.getResources().getColor(R.color.gray_bg);
        colorWhiteBg = mContext.getResources().getColor(R.color.white);
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold mViewHold;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_event, null);
            mViewHold = new ViewHold();
            mViewHold.mHomeTeam = (TextView) convertView.findViewById(R.id.homeTeam);
            mViewHold.mHomeStatus = (ImageView) convertView.findViewById(R.id.homeStatus);
            mViewHold.mTime = (TextView) convertView.findViewById(R.id.time);
            mViewHold.mGuestStatus = (ImageView) convertView.findViewById(R.id.guestStatus);
            mViewHold.mGuestTeam = (TextView) convertView.findViewById(R.id.guestTeam);
            mViewHold.mLlHome = (LinearLayout) convertView.findViewById(R.id.ll_home);
            mViewHold.mLlGuest = (LinearLayout) convertView.findViewById(R.id.ll_guest);
            convertView.setTag(mViewHold);
        } else {
            mViewHold = (ViewHold) convertView.getTag();
        }
        ScoreDetailBean.DataBean.ItemsBean.EventBean eventBean = eventList.get(position);
        //1-入球；2-点球；3-乌龙；4-黄牌；5-红牌；6-两黄变红；7-换人
        String homeEvent = eventBean.getHomeEvent();
        String awayEvent = eventBean.getAwayEvent();
        String eventTime = eventBean.getEventTime();
        String awayDetail = eventBean.getAwayDetail();
        String homeDetail = eventBean.getHomeDetail();

        mViewHold.mTime.setText(TextUtils.isEmpty(eventTime) ? "" : eventTime);
        if ("7".equals(homeEvent)) {
            mViewHold.mHomeTeam.setText(" ");
            if (!TextUtils.isEmpty(homeDetail)) {
                String[] split = homeDetail.split("\\|");
                if (split != null && split.length == 2) {
                    String replace = homeDetail.replace("|", "\n");
                    SpannableString spannableString = new SpannableString(replace);
                    spannableString.setSpan(new RelativeSizeSpan(0.8f), split[0].length(), replace.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewHold.mHomeTeam.setText(spannableString);
                }
            }

        } else {
            mViewHold.mHomeTeam.setText(TextUtils.isEmpty(homeDetail) ? " " : homeDetail);
        }
        mViewHold.mHomeStatus.setImageResource(getResId(homeEvent));

        if ("7".equals(awayEvent)) {
            mViewHold.mGuestTeam.setText(" ");
            if (!TextUtils.isEmpty(awayDetail)) {
                String[] split = awayDetail.split("\\|");
                if (split != null && split.length == 2) {
                    String replace = awayDetail.replace("|", "\n");
                    SpannableString spannableString = new SpannableString(replace);
                    spannableString.setSpan(new RelativeSizeSpan(0.8f), split[0].length(), replace.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewHold.mGuestTeam.setText(spannableString);
                }
            }

        } else {
            mViewHold.mGuestTeam.setText(TextUtils.isEmpty(awayDetail) ? " " : awayDetail);
        }
        mViewHold.mGuestStatus.setImageResource(getResId(awayEvent));

        if (position % 2 == 0) {
            mViewHold.mLlHome.setBackgroundColor(colorWhiteBg);
            mViewHold.mLlGuest.setBackgroundColor(colorWhiteBg);
            mViewHold.mTime.setBackgroundColor(colorGrayBg);
        } else {
            mViewHold.mLlHome.setBackgroundColor(colorGrayBg);
            mViewHold.mLlGuest.setBackgroundColor(colorGrayBg);
            mViewHold.mTime.setBackgroundColor(colorWhiteBg);
        }
        return convertView;
    }

    class ViewHold {
        TextView mHomeTeam;
        ImageView mHomeStatus;
        TextView mTime;
        ImageView mGuestStatus;
        TextView mGuestTeam;
        LinearLayout mLlHome;
        LinearLayout mLlGuest;
    }

    private int getResId(String eventCode) {
        if (TextUtils.isEmpty(eventCode)) return 0;
        int resId = 0;
        switch (eventCode) {
            case "1":
                resId = R.mipmap.jinqiu;
                break;
            case "2":
                resId = R.mipmap.dianqiu;
                break;
            case "3":
                resId = R.mipmap.wulong;
                break;
            case "4":
                resId = R.mipmap.yellow_card;
                break;
            case "5":
                resId = R.mipmap.red_card;
                break;
            case "6":
                resId = R.mipmap.yellow_red;
                break;
            case "7":
                resId = R.mipmap.huanren;
                break;
            default:
                resId = 0;
                break;

        }
        return resId;
    }
}
