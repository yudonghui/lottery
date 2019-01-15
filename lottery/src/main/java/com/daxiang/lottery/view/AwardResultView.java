package com.daxiang.lottery.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.AwardDetailData;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.StringUtil;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/21
 * @describe May the Buddha bless bug-free!!!
 */
public class AwardResultView extends LinearLayout {
    private View mInflate;
    private LinearLayout mLlResult;
    private Context mContext;

    public AwardResultView(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.award_result_view, this);
    }

    public AwardResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.award_result_view, this);
    }

    public AwardResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflate = View.inflate(context, R.layout.award_result_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLlResult = (LinearLayout) mInflate.findViewById(R.id.ll_result);
    }

    public void setData(List<AwardDetailData.DataBean.ItemsBean.DetailBean> mList) {
        for (int i = 0; i < mList.size(); i++) {
            View inflate = View.inflate(mContext, R.layout.award_result_item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    DisplayUtil.dip2px(30));
            inflate.setLayoutParams(layoutParams);
            TextView mAwards = (TextView) inflate.findViewById(R.id.awards);//奖项
            TextView mStakes = (TextView) inflate.findViewById(R.id.stakes);//中奖注数
            TextView mMoney = (TextView) inflate.findViewById(R.id.money);//中奖金额
            AwardDetailData.DataBean.ItemsBean.DetailBean detailBean = mList.get(i);
            mAwards.setText(TextUtils.isEmpty(detailBean.getItemName()) ? "" : detailBean.getItemName());
            mStakes.setText(TextUtils.isEmpty(detailBean.getNumber()) ? "" : detailBean.getNumber());
            mMoney.setText(TextUtils.isEmpty(detailBean.getAmount()) ? "" : (StringUtil.getString(detailBean.getAmount())));
            mLlResult.addView(inflate);
        }
    }
}
