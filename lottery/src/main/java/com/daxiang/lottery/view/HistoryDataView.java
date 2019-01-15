package com.daxiang.lottery.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.HistoryBean;
import com.daxiang.lottery.score.ScoreDetailActivity;

/**
 * @author yudonghui
 * @date 2017/6/16
 * @describe May the Buddha bless bug-free!!!
 */
public class HistoryDataView extends LinearLayout {

    private View mInflate;
    private LinearLayout mDetail_content;
    private TextView mHistory_vs;
    private TextView mHome_rank;
    private TextView mGuest_rank;
    private TextView mOddsS;
    private TextView mOddsP;
    private TextView mOddsF;
    private TextView mHome_recent;
    private TextView mGuest_recent;
    private TextView mWinRate;
    private TextView mDrawRate;
    private TextView mLostRate;
    private TextView mDetail;
    private Context mContext;

    public HistoryDataView(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.history_data, this);
    }

    public HistoryDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.history_data, this);
    }

    /*  public HistoryDataView(Context context, AttributeSet attrs, int defStyleAttr) {
          super(context, attrs, defStyleAttr);
          mContext = context;
          mInflate = View.inflate(context, R.layout.history_data, this);
      }
  */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDetail_content = (LinearLayout) mInflate.findViewById(R.id.detail_content);
        mHistory_vs = (TextView) mInflate.findViewById(R.id.history_vs);
        mHome_rank = (TextView) mInflate.findViewById(R.id.home_rank);
        mGuest_rank = (TextView) mInflate.findViewById(R.id.guest_rank);
        mOddsS = (TextView) mInflate.findViewById(R.id.oddsS);
        mOddsP = (TextView) mInflate.findViewById(R.id.oddsP);
        mOddsF = (TextView) mInflate.findViewById(R.id.oddsF);
        mHome_recent = (TextView) mInflate.findViewById(R.id.home_recent);
        mGuest_recent = (TextView) mInflate.findViewById(R.id.guest_recent);
        mWinRate = (TextView) mInflate.findViewById(R.id.winRate);
        mDrawRate = (TextView) mInflate.findViewById(R.id.drawRate);
        mLostRate = (TextView) mInflate.findViewById(R.id.lostRate);
        mDetail = (TextView) mInflate.findViewById(R.id.detail);
        mDetail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mId))return;
                Intent intent = new Intent(mContext, ScoreDetailActivity.class);
                intent.putExtra("mId", mId);
                intent.putExtra("from", "history");
                mContext.startActivity(intent);
            }
        });
    }

    private String mId;


    public void setView(HistoryBean.DataBean.ItemsBean itemsBean, String mId) {
        this.mId = mId;
        if (itemsBean == null) {
            mHistory_vs.setText("暂无两队交锋信息");
            return;
        }
        String ha = itemsBean.getHa();//双方近期的对阵信息.
        String rank_a = itemsBean.getRank_a();//客队排名
        String rank_h = itemsBean.getRank_h();//主队排名

        HistoryBean.DataBean.ItemsBean.HaHdaBean ha_hda = itemsBean.getHa_hda();
        String a = ha_hda.getA();//客队近期的对阵信息
        String h = ha_hda.getH();//主队近期的对阵信息
        HistoryBean.DataBean.ItemsBean.OddsBean odds = itemsBean.getOdds();
        String oa = odds.getOa();//主负 平均欧赔
        String od = odds.getOd();//平 平均欧赔
        String oh = odds.getOh();//主胜 平均欧赔

        HistoryBean.DataBean.ItemsBean.RateBean rate = itemsBean.getRate();
        String win = rate.getWin();//彩象预测主胜
        String draw = rate.getDraw();//彩象预测主平
        String lose = rate.getLose();//彩象预测主负


        mHistory_vs.setText(TextUtils.isEmpty(ha) ? "暂无两队交锋信息" : ha);
        mHome_rank.setText(TextUtils.isEmpty(rank_h) ? "--" : rank_h);
        mGuest_rank.setText(TextUtils.isEmpty(rank_a) ? "--" : rank_a);
        mOddsS.setText(TextUtils.isEmpty(oh) ? "--" : oh);
        mOddsP.setText(TextUtils.isEmpty(od) ? "--" : od);
        mOddsF.setText(TextUtils.isEmpty(oa) ? "--" : oa);
        mHome_recent.setText(TextUtils.isEmpty(h) ? "--" : h);
        mGuest_recent.setText(TextUtils.isEmpty(a) ? "--" : a);
        mWinRate.setText(TextUtils.isEmpty(win) ? "--" : win);
        mDrawRate.setText(TextUtils.isEmpty(draw) ? "--" : draw);
        mLostRate.setText(TextUtils.isEmpty(lose) ? "--" : lose);
    }
}
