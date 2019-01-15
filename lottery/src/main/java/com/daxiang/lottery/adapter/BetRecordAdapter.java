package com.daxiang.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.entity.BetRecordData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class BetRecordAdapter extends BaseAdapter {
    List<BetRecordData.DataBean.ListBean> mList;
    private Context mContext;
    private String orderType;
    private String status;
    private String mTypes;
    private long createTime;
    private List<Integer> mIntegerList = new ArrayList<>();

    public BetRecordAdapter(List<BetRecordData.DataBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        mContext = parent.getContext();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_bet_record, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTime_month = (TextView) convertView.findViewById(R.id.time_month);
            mViewHolder.mLottype = (TextView) convertView.findViewById(R.id.lottype);
            mViewHolder.mOrdertype = (TextView) convertView.findViewById(R.id.ordertype);
            mViewHolder.mLotissue = (TextView) convertView.findViewById(R.id.lotissue);
            mViewHolder.mTime_day = (TextView) convertView.findViewById(R.id.time_day);
            mViewHolder.mAllmoney = (TextView) convertView.findViewById(R.id.allmoney);
            mViewHolder.mStatus = (TextView) convertView.findViewById(R.id.status);
            mViewHolder.mLl_together = (LinearLayout) convertView.findViewById(R.id.ll_together);
            mViewHolder.mGod_line = convertView.findViewById(R.id.god_line);
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            mViewHolder.mTogetherName = (TextView) convertView.findViewById(R.id.togetherName);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        int pos = position % 2;
        if (pos == 1) {
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.order_bg));
        } else {
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.white));
        }
        final BetRecordData.DataBean.ListBean itemsBean = mList.get(position);
        int orderStatus = itemsBean.getOrderStatus();
        int mOrderType = itemsBean.getOrderType();
        String betTime = itemsBean.getBetTime();
        String issueNo = itemsBean.getIssueNo();
        String totalPrice = itemsBean.getTotalPrice();
        final String supUserId = itemsBean.getSupUserId();
        String supUserName = itemsBean.getSupUserName();

        if (mOrderType == 8 || mOrderType == 3) {//参与合买，参与跟单
            mViewHolder.mLl_together.setVisibility(View.VISIBLE);
            mViewHolder.mGod_line.setVisibility(View.VISIBLE);
            HttpUtils2.display(mViewHolder.mAvatar, Url.HEADER_ROOT_URL + supUserId);
            mViewHolder.mTogetherName.setText(TextUtils.isEmpty(supUserName) ? "--" : supUserName);
        } else {
            mViewHolder.mLl_together.setVisibility(View.GONE);
            mViewHolder.mGod_line.setVisibility(View.GONE);
        }

        status = LotteryTypes.getStatus(orderStatus + "");
        this.orderType = " | " + LotteryTypes.getOrderType(mOrderType);
        if (betTime.contains(":")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = simpleDateFormat.parse(betTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            createTime = date.getTime();
        } else {
            createTime = Long.parseLong(betTime);
        }
        mViewHolder.creatTime = createTime;
        SimpleDateFormat mFormat = new SimpleDateFormat("M-d+HH:mm");
        String format = mFormat.format(createTime);
        String[] split = format.split("\\+");
        //设定月日
        mViewHolder.mTime_month.setText(split[0]);
        mViewHolder.mTime_day.setText(split[1]);
        //设定购买类型，如自购，追加购买等
        mViewHolder.mOrdertype.setText(this.orderType);
        //设定期号
        mViewHolder.mLotissue.setText("第" + issueNo + "期");
        String buyMoney = "购买金额: " + totalPrice + "元";
        SpannableStringBuilder ssb = new SpannableStringBuilder(buyMoney);
        ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.gray_txt)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.deep_txt)), buyMoney.length() - 1, buyMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mViewHolder.mAllmoney.setText(ssb);
        //设定目前的状态 如等待开奖，中奖
        if ("已派奖".equals(status)) {
            mViewHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.red_theme));
            mViewHolder.mStatus.setText("已中" + itemsBean.getAftertaxBonus() + "元");
        } else if ("中奖".equals(status)) {
            mViewHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.red_theme));
            mViewHolder.mStatus.setText("派奖中");
        } else {
            mViewHolder.mStatus.setText(status);
            mViewHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
        }

        //设定彩种类型
        mTypes = LotteryTypes.getTypes(itemsBean.getLotCode());
        mViewHolder.mLottype.setText(mTypes);
        mViewHolder.mLl_together.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (supUserId == null) return;
                if (ClickUtils.isFastClick(2000)) {
                    return;
                }
                Intent intent = new Intent(mContext, GodInfoActivity.class);
                intent.putExtra("userId", supUserId);//发起人的uid
                mContext.startActivity(intent);
            }
        });
        //设置监听事件
       /* convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderFormDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderCode", itemsBean.getOrderCode());
                bundle.putString("orderId", itemsBean.getOrderId());
                bundle.putString("status", mViewHolder.mStatus.getText().toString());
                bundle.putString("allmoney", itemsBean.getTotalPrice());
                bundle.putString("buycontent", itemsBean.getBetContent());
                bundle.putString("lotCode", itemsBean.getLotCode());
                // String issue = itemsBean.getLotIssue().substring(0, 7);
                bundle.putString("issue", itemsBean.getIssueNo());
                bundle.putLong("creattime", mViewHolder.creatTime);
                bundle.putString("margin", itemsBean.getAftertaxBonus());
                bundle.putString("lottypenum", itemsBean.getLotCode());
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });*/
        return convertView;
    }

    class ViewHolder {

        private TextView mTime_month;
        private TextView mLottype;
        private TextView mOrdertype;
        private TextView mLotissue;
        private TextView mTime_day;
        private TextView mAllmoney;
        private TextView mStatus;
        private long creatTime;
        private LinearLayout mLl_together;
        private View mGod_line;
        private ImageView mAvatar;
        private TextView mTogetherName;
    }
}
