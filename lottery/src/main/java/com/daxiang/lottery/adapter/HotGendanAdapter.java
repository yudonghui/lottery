package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.wonderfulactivity.GendanDetailActivity;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.KeyBardInterface;
import com.daxiang.lottery.entity.RecommendBaseData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Android on 2018/3/8.
 */

public class HotGendanAdapter extends BaseAdapter {
    private final int colorBlack;
    List<RecommendBaseData> mItemsList;
    Context mContext;
    boolean flag = true;//true推荐大厅。false大神个人中心
    KeyBardInterface mKeyBardInterface;

    public HotGendanAdapter(Context mContext, List<RecommendBaseData> mItemsList, boolean flag, KeyBardInterface mKeyBardInterface) {
        this.mItemsList = mItemsList;
        this.mContext = mContext;
        this.flag = flag;
        this.mKeyBardInterface = mKeyBardInterface;
        colorBlack = mContext.getResources().getColor(R.color.deep_txt);
    }

    @Override
    public int getCount() {
        return mItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHoleder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHoleder();
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_gendan, null);
            mViewHolder.mImage_user = (ImageView) convertView.findViewById(R.id.image_user);
            mViewHolder.mTv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            mViewHolder.mGendan_logo = (TextView) convertView.findViewById(R.id.gendan_logo);
            mViewHolder.mWinNum = (TextView) convertView.findViewById(R.id.winNum);
            mViewHolder.mOne = (TextView) convertView.findViewById(R.id.one);
            mViewHolder.mTwo = (TextView) convertView.findViewById(R.id.two);
            mViewHolder.mThree = (TextView) convertView.findViewById(R.id.three);
            mViewHolder.mFour = (TextView) convertView.findViewById(R.id.four);
            mViewHolder.mThree_title = (TextView) convertView.findViewById(R.id.three_title);
            mViewHolder.mLl_gendan = (LinearLayout) convertView.findViewById(R.id.ll_gendan);
            mViewHolder.mTv_lotteryType = (TextView) convertView.findViewById(R.id.tv_lotteryType);
            mViewHolder.mEnd_time = (TextView) convertView.findViewById(R.id.end_time);
            mViewHolder.mGendan = (TextView) convertView.findViewById(R.id.gendan);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHoleder) convertView.getTag();
        }
        final RecommendBaseData mItemsBean = mItemsList.get(position);
        final String userId = mItemsBean.getUserId();
        String remark = mItemsBean.getRemark();
        String userName = mItemsBean.getUserName();
        String aftertaxBonus = mItemsBean.getAftertaxBonus();//税后奖金
        final String winStatus = mItemsBean.getWinStatus();
        final String userNum = mItemsBean.getUserNum();
        String oneMoney = mItemsBean.getOneMoney();
        String totalMoney = mItemsBean.getTotalMoney();
        String deadline = mItemsBean.getDeadline();
        String totalBuy = mItemsBean.getTotalBuy();//跟买金额
        String winInfo = mItemsBean.getWinInfo();//连红数
        String isCertified = mItemsBean.getIsCertified();//0普通大神，1认证大神
        String LotteryType = String.valueOf(mItemsBean.getLotCode());
        long currentTime = System.currentTimeMillis();
        long deadTime = Long.parseLong(deadline);
        String oneMoneyStr = (int) Double.parseDouble(oneMoney) + "元";
        SpannableString ss1 = new SpannableString(oneMoneyStr);
        ss1.setSpan(new ForegroundColorSpan(colorBlack), oneMoneyStr.length() - 1, oneMoneyStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mViewHolder.mFour.setText(ss1);
        if ("跟单最多".equals(remark)) {
            mViewHolder.mGendan_logo.setBackgroundResource(R.mipmap.blue_more);
            mViewHolder.mGendan_logo.setText(remark);
        } else {
            mViewHolder.mGendan_logo.setBackgroundResource(R.mipmap.orange_more);
            mViewHolder.mGendan_logo.setText(TextUtils.isEmpty(remark) ? "" : remark);
        }
        //图片链接拼接 http://test.51caixiang.com/files/avatar/512290000364200
        HttpUtils2.display(mViewHolder.mImage_user, Url.HEADER_ROOT_URL + mItemsBean.getUserId());
        String mThreeString = "";
        if ("0".equals(winStatus)) {//0-未开奖；1-未中奖；2-已中奖
            mViewHolder.mThree_title.setText("预测奖金");
            if (LotteryType.equals("42") || LotteryType.equals("43") || LotteryType.equals("1000") || LotteryType.equals("1001")) {
                //DecimalFormat df = new DecimalFormat(".00");
                int theoreticalPrize = (int) mItemsBean.getTheoreticalPrize();
                if (theoreticalPrize < 100)
                    mThreeString = theoreticalPrize + "元";
                    //mViewHolder.mThree.setText(theoreticalPrize + "");
                else
                    mThreeString = theoreticalPrize + "+元";
                // mViewHolder.mThree.setText(theoreticalPrize + "+");
            } else {
                mThreeString = "-元";
                //mViewHolder.mThree.setText("-元");
            }
        } else {
            //mViewHolder.mWinState.setVisibility(View.VISIBLE);
            mViewHolder.mThree_title.setText("税后奖金");
            if ("1".equals(winStatus)) {
                //  mViewHolder.mWinState.setBackgroundResource(R.mipmap.unwinstate);
                mThreeString = "0.00元";
                // mViewHolder.mThree.setText("0.00");
            } else if ("2".equals(winStatus)) {
                // mViewHolder.mWinState.setBackgroundResource(R.mipmap.winstate);
                mThreeString = aftertaxBonus + "元";
                //  mViewHolder.mThree.setText(aftertaxBonus);
            }
        }
        SpannableString ss4 = new SpannableString(mThreeString);
        ss4.setSpan(new ForegroundColorSpan(colorBlack), mThreeString.length() - 1, mThreeString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mViewHolder.mThree.setText(ss4);
        if ("0".equals(winStatus) && currentTime < deadTime) {//正在进行中
            mViewHolder.mLl_gendan.setVisibility(View.VISIBLE);
            mViewHolder.mTv_user_name.setText(TextUtils.isEmpty(userName) ? "--" : userName);
            //mViewHolder.tv_code.setTextColor(mContext.getResources().getColor(R.color.orange_let));
            if (TextUtils.isEmpty(winInfo)) {
                mViewHolder.mWinNum.setText("近期战绩: 无");
            } else {
                String string = "近期战绩: ";
                String winInfoSS = string + winInfo;
                SpannableString ss = new SpannableString(winInfoSS);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), string.length(), winInfoSS.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mViewHolder.mWinNum.setText(ss);
            }
            //截止日期
            if (!TextUtils.isEmpty(deadline)) {
                long deadLine = Long.parseLong(deadline);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                Date date = new Date(deadLine);
                String endTime = dateFormat.format(date);
                mViewHolder.mEnd_time.setText("截止日期: " + endTime);
            }
        } else {//已经不能购买
            mViewHolder.mLl_gendan.setVisibility(View.GONE);
            // mViewHolder.tv_code.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
            String winInfoSS;
            if (TextUtils.isEmpty(winInfo) || !flag) {
                winInfoSS = userName;
            } else {
                winInfoSS = userName + "(" + winInfo + ")";
            }
            SpannableString ss = new SpannableString(winInfoSS);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), userName.length(), winInfoSS.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(0.7f), userName.length(), winInfoSS.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            //发起推荐的人昵称+中奖数
            mViewHolder.mTv_user_name.setText(ss);
            //截止日期
            if (!TextUtils.isEmpty(deadline)) {
                long deadLine = Long.parseLong(deadline);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                Date date = new Date(deadLine);
                String endTime = dateFormat.format(date);
                mViewHolder.mWinNum.setText("截止时间：" + endTime);
            }
        }
        //参与人数
        String userNumStr = userNum + "人";
        SpannableString ss3 = new SpannableString(userNumStr);
        ss3.setSpan(new ForegroundColorSpan(colorBlack), userNumStr.length() - 1, userNumStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mViewHolder.mTwo.setText(ss3);
        //方案金额
        String totalMoneyStr = (int) Double.parseDouble(totalMoney) + "元";
        SpannableString ss2 = new SpannableString(totalMoneyStr);
        ss2.setSpan(new ForegroundColorSpan(colorBlack), totalMoneyStr.length() - 1, totalMoneyStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mViewHolder.mOne.setText(ss2);

        if (!TextUtils.isEmpty(totalBuy)) {
            // mViewHolder.tv_code.setText(UnitUtils.amountConversion(Double.parseDouble(totalBuy)) + "元");
        }

        mViewHolder.mTv_lotteryType.setText(LotteryTypes.getTypes(LotteryType));
        if (flag) {//大神中心点击列表的头像不再跳转
            mViewHolder.mImage_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ClickUtils.isFastClick(2000)) {
                        return;
                    }
                    Intent intent = new Intent(mContext, GodInfoActivity.class);
                    intent.putExtra("userId", userId);
                    mContext.startActivity(intent);
                }
            });
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止连续点击
                if (ClickUtils.isFastClick(2000)) {
                    return;
                }
                Intent intent = new Intent(mContext, GendanDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemsbean", mItemsBean);
                bundle.putBoolean("flag", true);
                bundle.putString("userNum", userNum);
                bundle.putString("manifesto", mItemsBean.getDeclaration());
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });
        mViewHolder.mGendan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyBardInterface.callBack(mItemsBean, position);
            }
        });
        return convertView;
    }

    public class ViewHoleder {

        private ImageView mImage_user;
        private TextView mTv_user_name;
        private TextView mGendan_logo;
        private TextView mWinNum;
        private TextView mOne;
        private TextView mTwo;
        private TextView mThree;
        private TextView mFour;
        private TextView mThree_title;
        private LinearLayout mLl_gendan;
        private TextView mTv_lotteryType;
        private TextView mEnd_time;
        private TextView mGendan;
    }
}
