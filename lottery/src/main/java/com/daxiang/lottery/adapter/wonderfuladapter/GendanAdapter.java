package com.daxiang.lottery.adapter.wonderfuladapter;

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
import com.daxiang.lottery.utils.UnitUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class GendanAdapter extends BaseAdapter {
    List<RecommendBaseData> mItemsList;
    Context mContext;
    boolean flag = true;//true推荐大厅。false大神个人中心
    KeyBardInterface mKeyBardInterface;
    String orderBy = "totalGD";

    public GendanAdapter(Context mContext, List<RecommendBaseData> mItemsList, boolean flag, KeyBardInterface mKeyBardInterface) {
        this.mItemsList = mItemsList;
        this.mContext = mContext;
        this.flag = flag;
        this.mKeyBardInterface = mKeyBardInterface;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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
            convertView = View.inflate(parent.getContext(), R.layout.item_gendan, null);
            mViewHolder.mIsGod = (ImageView) convertView.findViewById(R.id.isGod);
            mViewHolder.tv_code = (TextView) convertView.findViewById(R.id.tv_code);
            mViewHolder.mAward = (TextView) convertView.findViewById(R.id.award);
            mViewHolder.tv_lotType = (TextView) convertView.findViewById(R.id.tv_lotteryType);
            mViewHolder.img_user = (ImageView) convertView.findViewById(R.id.image_user);
            mViewHolder.tv_userName = (TextView) convertView.findViewById(R.id.tv_user_name);
            mViewHolder.tv_theoreticalPrize = (TextView) convertView.findViewById(R.id.tv_theoreticalPrize);
            mViewHolder.tv_endTime = (TextView) convertView.findViewById(R.id.end_time);
            mViewHolder.tv_joinNum = (TextView) convertView.findViewById(R.id.join_number);
            mViewHolder.tv_money = (TextView) convertView.findViewById(R.id.scheme_money);
            mViewHolder.mWinState = (ImageView) convertView.findViewById(R.id.winstate);
            mViewHolder.mGendan = (TextView) convertView.findViewById(R.id.gendan);
            mViewHolder.mWinNum = (TextView) convertView.findViewById(R.id.winNum);
            mViewHolder.mLl_gendan = (LinearLayout) convertView.findViewById(R.id.ll_gendan);
            mViewHolder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHoleder) convertView.getTag();
        }
        final RecommendBaseData mItemsBean = mItemsList.get(position);
        final String userId = mItemsBean.getUserId();
        String userName = mItemsBean.getUserName();
        String aftertaxBonus = mItemsBean.getAftertaxBonus();//税后奖金
        final String winStatus = mItemsBean.getWinStatus();
        final String userNum = mItemsBean.getUserNum();
        String oneMoney = mItemsBean.getOneMoney();
        String totalMoney = mItemsBean.getTotalMoney();
        String deadline = mItemsBean.getDeadline();
        String totalBuy = mItemsBean.getTotalBuy();//跟买金额
        String winRate = mItemsBean.getWinRate();//命中率
        String gainRate = mItemsBean.getGainRate();//盈利率
        String winInfo = mItemsBean.getWinInfo();//连红数
        String isCertified = mItemsBean.getIsCertified();//0普通大神，1认证大神
        String LotteryType = String.valueOf(mItemsBean.getLotCode());
        long currentTime = System.currentTimeMillis();
        long deadTime = Long.parseLong(deadline);
        //图片链接拼接 http://test.51caixiang.com/files/avatar/512290000364200
        HttpUtils2.display(mViewHolder.img_user, Url.HEADER_ROOT_URL + mItemsBean.getUserId());
        if ("0".equals(winStatus)) {//0-未开奖；1-未中奖；2-已中奖
            mViewHolder.mAward.setText("预测奖金");
            if (currentTime >= deadTime) {//已经截期
                mViewHolder.mWinState.setVisibility(View.VISIBLE);
                mViewHolder.mWinState.setBackgroundResource(R.mipmap.waitting);
            } else {
                mViewHolder.mWinState.setVisibility(View.INVISIBLE);
            }
            if (LotteryType.equals("42") || LotteryType.equals("43") || LotteryType.equals("1000") || LotteryType.equals("1001")) {
                //DecimalFormat df = new DecimalFormat(".00");
                int theoreticalPrize = (int) mItemsBean.getTheoreticalPrize();
                if (theoreticalPrize < 100)
                    mViewHolder.tv_theoreticalPrize.setText(theoreticalPrize + "");
                else
                    mViewHolder.tv_theoreticalPrize.setText(theoreticalPrize + "+");
            } else {
                mViewHolder.tv_theoreticalPrize.setText("-");
            }
        } else {
            mViewHolder.mWinState.setVisibility(View.VISIBLE);
            mViewHolder.mAward.setText("税后奖金");
            if ("1".equals(winStatus)) {
                mViewHolder.mWinState.setBackgroundResource(R.mipmap.unwinstate);
                mViewHolder.tv_theoreticalPrize.setText("0.00");
            } else if ("2".equals(winStatus)) {
                mViewHolder.mWinState.setBackgroundResource(R.mipmap.winstate);
                mViewHolder.tv_theoreticalPrize.setText(aftertaxBonus);
            }
        }
        if ("0".equals(winStatus) && currentTime < deadTime) {//正在进行中
            mViewHolder.mLl_gendan.setVisibility(View.VISIBLE);
            mViewHolder.tv_userName.setText(TextUtils.isEmpty(userName) ? "--" : userName);
            mViewHolder.tv_code.setTextColor(mContext.getResources().getColor(R.color.orange_let));
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
                mViewHolder.tv_endTime.setText(endTime);
            }
        } else {//已经不能购买
            mViewHolder.mLl_gendan.setVisibility(View.GONE);
            mViewHolder.tv_code.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
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
            mViewHolder.tv_userName.setText(ss);
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
        mViewHolder.tv_joinNum.setText(userNum);
        //方案金额
        mViewHolder.tv_money.setText(TextUtils.isEmpty(totalMoney) ? "--" : totalMoney);
        if ("winRate".equals(orderBy)) {//命中率
            mViewHolder.tv_text.setText("命中率");
            mViewHolder.tv_code.setText(TextUtils.isEmpty(winRate) ? "--" : (winRate + "%"));
        } else if ("gainRate".equals(orderBy)) {//盈利率
            mViewHolder.tv_text.setText("盈利率");
            mViewHolder.tv_code.setText(TextUtils.isEmpty(gainRate) ? "--" : (gainRate + "%"));
        } else {
            mViewHolder.tv_text.setText("跟单金额");
            if (!TextUtils.isEmpty(totalBuy)) {
                mViewHolder.tv_code.setText(UnitUtils.amountConversion(Double.parseDouble(totalBuy)) + "元");
            } else mViewHolder.tv_code.setText("--");
        }


        mViewHolder.tv_lotType.setText(LotteryTypes.getTypes(LotteryType));
        if (flag) {//大神中心点击列表的头像不再跳转
            mViewHolder.img_user.setOnClickListener(new View.OnClickListener() {
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
            if ("1".equals(isCertified))
                mViewHolder.mIsGod.setVisibility(View.VISIBLE);
            else mViewHolder.mIsGod.setVisibility(View.GONE);
        } else mViewHolder.mIsGod.setVisibility(View.GONE);
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
        TextView tv_lotType;
        ImageView img_user;
        TextView tv_userName;
        //预测金额
        TextView tv_theoreticalPrize;
        //截止时间
        TextView tv_endTime;
        //参与人数
        TextView tv_joinNum;
        //方案金额（投注的单倍）
        TextView tv_money;
        //返奖率（预测除以方案）
        TextView tv_code;
        //中奖标志
        ImageView mWinState;
        //奖金
        TextView mAward;
        //是否是认证大神
        ImageView mIsGod;
        //跟单
        TextView mGendan;
        //
        LinearLayout mLl_gendan;
        //近期战绩
        TextView mWinNum;
        //跟单金额，盈利率 命中率 提示
        TextView tv_text;
    }
}
