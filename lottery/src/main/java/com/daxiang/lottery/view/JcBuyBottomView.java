package com.daxiang.lottery.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.utils.DisplayUtil;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class JcBuyBottomView extends LinearLayout {
    private View mInflate;
    private LinearLayout mLayout_bet_extra_new;
    public LinearLayout mLlBunch;
    public RelativeLayout mLayout_buy_times;
    public TextView mText_buy_types;
    public EditText mEdit_buy_times;
    private LinearLayout mBtn_bonus_optimize;
    private LinearLayout mFastBetLayout;
    private TextView mBet10;
    private TextView mBet20;
    private TextView mBet50;
    private TextView mBet100;
    //public TextView mTv_zhushu;
    //public TextView mTv_money;
    public RecyclerView mRecyclerView;

    //public ImageView mImg_hemai;
    //public ImageView mImg_gendan;
    //public Button mBtn_bet_submit;
    public JcBuyBottomView(Context context) {
        super(context);
        mInflate = View.inflate(context, R.layout.cx_jc_choosed_bottom_view, this);
    }

    public JcBuyBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflate = View.inflate(context, R.layout.cx_jc_choosed_bottom_view, this);
    }

    public View mLine_Bunch;//串的分割线
    public LinearLayout mLl_award_recommend;
    public LinearLayout mLlonsd;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = (RecyclerView) mInflate.findViewById(R.id.rv_bunch);
        mLine_Bunch = mInflate.findViewById(R.id.line_bunch);
        mLayout_bet_extra_new = (LinearLayout) mInflate.findViewById(R.id.layout_bet_extra_new);
        mLlonsd = (LinearLayout) mInflate.findViewById(R.id.ll_oonsd);
        mLlBunch = (LinearLayout) mInflate.findViewById(R.id.bets_way_radioGroup_new);
        mLl_award_recommend = (LinearLayout) mInflate.findViewById(R.id.ll_award_recommend);
        mLayout_buy_times = (RelativeLayout) mInflate.findViewById(R.id.layout_buy_times);
        mText_buy_types = (TextView) mInflate.findViewById(R.id.text_buy_types);
        mEdit_buy_times = (EditText) mInflate.findViewById(R.id.edit_buy_times);
        mBtn_bonus_optimize = (LinearLayout) mInflate.findViewById(R.id.btn_bonus_optimize);
        mFastBetLayout = (LinearLayout) mInflate.findViewById(R.id.fastBetLayout);
        mBet10 = (TextView) mInflate.findViewById(R.id.bet10);
        mBet20 = (TextView) mInflate.findViewById(R.id.bet20);
        mBet50 = (TextView) mInflate.findViewById(R.id.bet50);
        mBet100 = (TextView) mInflate.findViewById(R.id.bet100);
       /* mTv_zhushu = (TextView) findViewById(R.id.tv_zhushu);
        mTv_money = (TextView) findViewById(R.id.tv_money);
        mImg_hemai = (ImageView) findViewById(R.id.img_hemai);
        mImg_gendan = (ImageView) findViewById(R.id.img_gendan);
        mBtn_bet_submit = (Button) findViewById(R.id.btn_bet_submit);*/
    }

    public void setOne() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(133), LinearLayout.LayoutParams.MATCH_PARENT);
        mLl_award_recommend.setLayoutParams(layoutParams);
        mLl_award_recommend.setBackgroundResource(R.mipmap.one_award_recommend);

    }

    public void setTwo() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(245), RelativeLayout.LayoutParams.MATCH_PARENT);
        mLl_award_recommend.setLayoutParams(layoutParams);
        mLl_award_recommend.setBackgroundResource(R.mipmap.award_recommend_bg);
    }

    public void setAwardRecommend(int visi) {
        mLlonsd.setVisibility(visi);
    }
}
