package com.daxiang.lottery.fragment.jcfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BonusOptimizeActivity;
import com.daxiang.lottery.activity.JczqActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.adapter.ForecastAdapter;
import com.daxiang.lottery.adapter.jcadapter.RecyclerViewAdapter;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.SpliceCtr;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.OnClickJczqListener;
import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.utils.BunchMethod;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.ToastUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.utils.jcmath.Combination;
import com.daxiang.lottery.utils.jcmath.TheoryBonusMath;
import com.daxiang.lottery.view.GendanView;
import com.daxiang.lottery.view.JcBuyBottomViewForecast;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daxiang.lottery.constant.LotteryTypes.lotcode;

/**
 * Created by Android on 2018/3/19.
 */

public class ForecastFragment extends Fragment implements View.OnLayoutChangeListener {
    private View mInflate;
    private Context mContext;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    private View mFooterLine;
    ArrayList<JczqData.DataBean> mDataList = new ArrayList<>();
    private ForecastAdapter mAdapter;
    private String issue;
    private int minTJMoney;//最小发单金额
    private String tjMoneyMsg;//最小发单金额说明
    private int buyPrivilege;

    public void setData(String issue) {
        this.issue = issue;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflate = inflater.inflate(R.layout.fragment_forecast, null);
        mContext = getContext();
        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        minTJMoney = sp.getInt("minTJMoney", 0);
        tjMoneyMsg = sp.getString("tjMoneyMsg", "");
        buyPrivilege = SpUtils.getBuyPrivilege();
        initView();
        mAdapter = new ForecastAdapter(mContext, mDataList, ClickMatchListener);
        mListView.setAdapter(mAdapter);
        addListener();
        addData();
        return mInflate;
    }

    private JcBuyBottomViewForecast mBuyBottomView;
    private TextView mBonuses;
    private LinearLayout mImg_gendan;
    private TextView mTv_zhushu;
    private Button mBtn_bet_submit;
    //下面这两个是为了弹出软件盘时控制他们的显隐
    private LinearLayout mLl_buy_bttomview;
    private LinearLayout mLl_mutile;
    private LinearLayout mBonusOptimize;
    private TextView mMutile10;
    private TextView mMutile20;
    private TextView mMutile50;
    private TextView mMutile100;
    private NoDataView mNoDataView;
    private LinearLayout mLl_buy;
    private TextView mConfirmForecast;
    private LinearLayout mLl_forecast_rate;
    private TextView mForecast_rate;

    private void initView() {
        mNoDataView = (NoDataView) mInflate.findViewById(R.id.no_data);
        mLl_buy = (LinearLayout) mInflate.findViewById(R.id.ll_buy);
        mConfirmForecast = (TextView) mInflate.findViewById(R.id.confirm_forecast);
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mListView = (ListView) mInflate.findViewById(R.id.listView);
        mBonuses = (TextView) mInflate.findViewById(R.id.tv_max_bonuses);
        mBonusOptimize = (LinearLayout) mInflate.findViewById(R.id.btn_bonus_optimize);
        mBuyBottomView = (JcBuyBottomViewForecast) mInflate.findViewById(R.id.jc_buy_bottom);
        mLl_buy_bttomview = (LinearLayout) mInflate.findViewById(R.id.ll_buy_bttomview);
        mLl_mutile = (LinearLayout) mInflate.findViewById(R.id.ll_mutile);
        mMutile10 = (TextView) mInflate.findViewById(R.id.mutile10);
        mMutile20 = (TextView) mInflate.findViewById(R.id.mutile20);
        mMutile50 = (TextView) mInflate.findViewById(R.id.mutile50);
        mMutile100 = (TextView) mInflate.findViewById(R.id.mutile100);
        mTv_zhushu = (TextView) mInflate.findViewById(R.id.tv_zhushu);
        mImg_gendan = (LinearLayout) mInflate.findViewById(R.id.ll_recommend);
        mBtn_bet_submit = (Button) mInflate.findViewById(R.id.btn_bet_submit);
        mLl_forecast_rate = (LinearLayout) mInflate.findViewById(R.id.ll_forecast_rate);
        mForecast_rate = (TextView) mInflate.findViewById(R.id.forecast_rate);
        mFooterLine = mInflate.findViewById(R.id.footer_line);
        if (LotteryApp.recommendPrivilege == 2) {
            mImg_gendan.setVisibility(View.GONE);
            mBuyBottomView.setOne();
        } else {
            mImg_gendan.setVisibility(View.VISIBLE);
            mBuyBottomView.setTwo();
        }
        if (LotteryApp.buyPrivilege == 2) {
            mBtn_bet_submit.setText("暂停销售");
            mBtn_bet_submit.setEnabled(false);
        } else {
            mBtn_bet_submit.setText("立即预约");
            mBtn_bet_submit.setEnabled(true);
        }
    }

    private void addListener() {
        mRefresh.setOnRefreshListener(OnrefreshListener);
        //点击付款按钮
        mBtn_bet_submit.setOnClickListener(BuyBtnClick);
        //投注倍数edittext
        mBuyBottomView.mEdit_buy_times.addTextChangedListener(MultiClick);
        //串数
        mBuyBottomView.mText_buy_types.setOnClickListener(BunchClick);
        //推荐
        mImg_gendan.setOnClickListener(GendanClickListener);
        //奖金优化
        mBonusOptimize.setOnClickListener(OptimizeClickListener);
        //立即预约
        mConfirmForecast.setOnClickListener(ConfirmForecastListener);
        //监听软键盘的弹出和隐藏
        mInflate.addOnLayoutChangeListener(this);
        mMutile10.setOnClickListener(click);
        mMutile20.setOnClickListener(click);
        mMutile50.setOnClickListener(click);
        mMutile100.setOnClickListener(click);
    }

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("issue", issue);
        mHttp.get(Url.FORECAST_MATCH, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                JczqData jczqData = gson.fromJson(result, JczqData.class);
                int code = jczqData.getCode();
                mDataList.clear();
                if (code == 0) {
                    mDataList.addAll(jczqData.getData());
                }
                if (mDataList.size() == 0) {
                    mNoDataView.setVisibility(View.VISIBLE);
                } else {
                    mNoDataView.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                    setBuyView();
                }
            }

            @Override
            public void onError() {
                mNoDataView.setVisibility(View.VISIBLE);
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
            }
        });
    }

    private void setBuyView() {
        int noStop = 0;//没有截期的数量
        int winNum = 0;//命中的数量
        int matchEnd = 0;//比赛已经结束的数量
        for (int i = 0; i < mDataList.size(); i++) {
            JczqData.DataBean dataBean = mDataList.get(i);
            String stopSaleTime = dataBean.getStopSaleTime();
            int let = dataBean.getLet();
            String content = dataBean.getContent();
            String score = dataBean.getScore();
            if (!TextUtils.isEmpty(score))
                matchEnd++;
            if (winningRate(let, content, score))
                winNum++;
            long sumGroup = System.currentTimeMillis();
            try {
                if (Long.parseLong(stopSaleTime) > sumGroup) noStop++;
            } catch (Exception e) {

            }

        }
        if (noStop >= 2&&buyPrivilege==0) {//没有截期的大于等于2那么就可以在该页面进行投注
            mLl_buy.setVisibility(View.VISIBLE);
            mConfirmForecast.setVisibility(View.GONE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(130));
            mFooterLine.setLayoutParams(lp);
        } else {
            mLl_buy.setVisibility(View.GONE);
            mConfirmForecast.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(50));
            mFooterLine.setLayoutParams(lp);
        }
        if (noStop < mDataList.size() && matchEnd > 0) {
            mLl_forecast_rate.setVisibility(View.VISIBLE);
            int totalNum = mDataList.size();
            int stopNum = totalNum - noStop;//得到截期的数量，包括了比赛结束的。
            int unMatchEnd = totalNum - matchEnd;//未开奖的数量。总数量减去比赛结束的。包括没截期的和已经截期还没有比赛结束的。
            String rate = winNum * 100 / matchEnd + "%";//中奖的比赛，除以 结束的比赛。得到当前的概率。
            if (matchEnd == totalNum) {//预测的赛事全部比赛结束
                String text = "精选预测" + totalNum + "场,命中" + winNum + "场,命中率" + rate;
              /*  int rateIndex = text.indexOf(rate);
                int totalIndex = text.indexOf(totalNum + "");
                int winIndex = text.indexOf(winNum + "");
                SpannableString ss = new SpannableString(text);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), rateIndex, rateIndex + rate.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), totalIndex, totalIndex + (totalNum + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), winIndex, winIndex + (winNum + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);*/
                mForecast_rate.setText(getNumColor(text));
            } else {//未比赛完
                String text = "精选预测" + totalNum + "场,命中" + winNum + "场,未开奖" + unMatchEnd + "场,命中率" + rate;
                /*int rateIndex = text.indexOf(rate);
                int totalIndex = text.indexOf(totalNum + "");
                int winIndex = text.indexOf(winNum + "");
                int unMatchEndIndex = text.indexOf(unMatchEnd + "");
                SpannableString ss = new SpannableString(text);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), rateIndex, rateIndex + rate.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), totalIndex, totalIndex + (totalNum + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), winIndex, winIndex + (winNum + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_txt)), unMatchEndIndex, unMatchEndIndex + (unMatchEnd + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);*/
                mForecast_rate.setText(getNumColor(text));
            }


        } else {
            mLl_forecast_rate.setVisibility(View.GONE);
        }
    }

    public SpannableStringBuilder getNumColor(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if ((a >= '0' && a <= '9') || a == '%') {
                style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }

    private boolean winningRate(int let, String content, String scroe) {
        if (TextUtils.isEmpty(scroe)) return false;
        String[] split = scroe.split("\\:");
        int score1 = Integer.parseInt(split[0]);
        int score2 = Integer.parseInt(split[1]);
        if (let + score1 > score2) {
            return content.contains("3");
        } else if (let + score1 < score2) {
            return content.contains("0");
        } else {
            return content.contains("1");
        }
    }

    //选择的场次
    HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentMap = new HashMap<>();
    //用于存放显示在选中列表所需要的内容填充的实体类
    ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList = new ArrayList<>();
    OnClickJczqListener ClickMatchListener = new OnClickJczqListener() {
        @Override
        public void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap) {
            //每次点击都会回调过来点击的条目信息
            mChoosedContentMap = clickMap;
            setContent();
        }
    };
    //在选择混合过关的时候判断是否可以是单关。
    private boolean isDg = false;
    //用于存放选中的串的编号。
    HashMap<Integer, Integer> mBunchMap = new HashMap<>();
    //存放格式化的串。例如2*1
    ArrayList<String> mBunchList = new ArrayList<>();
    //notifi串的recyclerview用
    HashMap<Integer, Integer> mRvMap = new HashMap<>();
    //总投注数
    int shakes;
    private int mMulti = 5;//默认5倍

    private void setContent() {
        choosedContentFormList.clear();
         /*
          * 用于存放选中的比赛。
          * 第一个参数是spf-0 这样格式的字符串，前面是玩法后面是单关的状态值
          *      1是支持单关，0是不支持单关
          * 第二个参数没有意义。主要是利用HashMap的去重效果。
          * */
        HashMap<String, Integer> isBunch = new HashMap<>();
        //遍历外层的hashmap
        for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            HashMap<String, String> value = entry.getValue();
            //遍历内层的hashmap
            JczqData.DataBean dataBean = entry.getKey();
            String awary = dataBean.getAway();
            String home = dataBean.getHome();
            int let = dataBean.getLet();
            String mid = dataBean.getSession();
            ArrayList<ChoosedContentFormBean> list = new ArrayList<>();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                ChoosedContentFormBean mContentBean = new ChoosedContentFormBean();
                mContentBean.setAwary(awary);
                mContentBean.setHome(home);
                mContentBean.setContent(entry1.getKey());
                mContentBean.setLet(let);
                mContentBean.setMid(mid);
                mContentBean.setOdds(entry1.getValue());
                list.add(mContentBean);
                isBunch.put(BunchMethod.getPlayMethod(entry1.getKey(), dataBean), 0);
            }
            choosedContentFormList.add(list);
        }
         /*
                    * 如果isBunch只有一个元素，并且支持单关那么就可以显示单关。
                    * */
        if (isBunch.size() == 1) {
            for (Map.Entry<String, Integer> entry : isBunch.entrySet()) {
                String key = entry.getKey();
                String[] split = key.split("\\-");
                if (split[1].equals("1")) {
                    isDg = true;
                } else isDg = false;
            }
        } else isDg = false;
        if (mChoosedContentMap.size() >= 2 || isDg) {
            mBunchMap.clear();
            mBunchList.clear();
            if (isDg && mChoosedContentMap.size() == 1) {
                mBunchMap.put(1, 1);
                mBunchList.add("1*1");
                mBuyBottomView.mText_buy_types.setText("单关");
            } else {
                mBunchMap.put(2, 2);
                mBunchList.add("2*1");
                mBuyBottomView.mText_buy_types.setText("2串1");
            }
            setStakeCount();
            //填充注数和钱数
            setStakesAndMoney();
            //填充理论奖金
            setBonus();
            setRecyclerView();
        } else {
            String string = "共" + 0 + "注" + mMulti + "倍";
            mTv_zhushu.setText(string);
            mBonuses.setText("0元");
        }
    }

    private void setStakeCount() {
        shakes = 0;
        //将每一场比赛选中的项数放入一个集合中
        ArrayList<Integer> mList = new ArrayList<>();
        for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            HashMap<String, String> value = entry.getValue();
            mList.add(value.size());
        }
        //通过封装的算法得到在这个mlist集合中任意挑选bunchs个，每种组合方式的数据
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            for (List<Integer> list : Combination.of(mList, entry.getKey())) {
                int ss = 1;
                for (int j = 0; j < (entry.getKey()); j++) {
                    ss = ss * list.get(j);
                }
                shakes = shakes + ss;
            }
        }

    }

    private void setStakesAndMoney() {

        //mMulti = Integer.parseInt(mBuyBottomView.mEdit_buy_times.getText().toString());
        String zhushu = shakes * mMulti * 2 + "";
        String string = "共" + shakes + "注" + mMulti + "倍，合计：" + zhushu + "元";
        int indexOf = string.indexOf(zhushu);
        SpannableString ssb = new SpannableString(string);
        ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf, indexOf + zhushu.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv_zhushu.setText(ssb);
    }

    //理论奖金的最大值和最小值
    private double maxTheoryBonus = 0;
    private double minTheoryBonus = 0;

    //设置最大最小理论奖金
    private void setBonus() {
        TheoryBonusMath mTheoryBonusMath = new TheoryBonusMath();
        String text = mTheoryBonusMath.countBonus(mChoosedContentMap, new ArrayList<String>(), mBunchMap);
        String[] split = text.split("-");
        minTheoryBonus = Double.parseDouble(split[0]);
        maxTheoryBonus = Double.parseDouble(split[1]);
        setBonusView();
    }

    private void setBonusView() {
        DecimalFormat format = new DecimalFormat("0.00");
        if (mBunchMap.size() > 0 && minTheoryBonus != maxTheoryBonus) {
            mBonuses.setVisibility(View.VISIBLE);
            mBonuses.setText(format.format(minTheoryBonus * 2 * mMulti) + "~" + format.format(maxTheoryBonus * 2 * mMulti) + "元");
        } else if (minTheoryBonus == maxTheoryBonus) {
            mBonuses.setVisibility(View.VISIBLE);
            mBonuses.setText(format.format(minTheoryBonus * 2 * mMulti) + "元");
        } else {
            mBonuses.setVisibility(View.GONE);
        }

    }

    private RecyclerViewAdapter mRvAdapter;

    private void setRecyclerView() {

        int maxBunchs = 8;//胜平负 最大是八串
        mRvAdapter = new RecyclerViewAdapter(isDg, maxBunchs, 0, mRvMap, choosedContentFormList, new RecyclerViewAdapter.BunchClickListener() {

            @Override
            public void onBunchClick(HashMap<Integer, Integer> mMap) {
                //mBunchMap.clear();
                mBunchMap = mMap;
                //填充串框
                setBunchView();
                //重新计算注数和钱数
                setStakeCount();
                setStakesAndMoney();
                //重新计算理论奖金
                setBonus();
            }
        });
        mBuyBottomView.mRecyclerView.setAdapter(mRvAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBuyBottomView.mRecyclerView.setLayoutManager(llm);
    }

    //底部串数的显示
    private void setBunchView() {
        mBunchList.clear();
        if (mBunchMap.size() > 1) {
            int n = -1;
            int m = 10;
            for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
                //遍历map集合挑选出最大串数和最小的串数
                if (entry.getKey() > n) {
                    n = entry.getKey();
                }
                if (entry.getKey() < m) {
                    m = entry.getKey();
                }
                mBunchList.add((entry.getKey()) + "*1");
            }
            mBuyBottomView.mText_buy_types.setText((m) + "...," + (n) + "串1");
        } else {
            for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
                mBuyBottomView.mText_buy_types.setText((entry.getKey()) + "串1");

                mBunchList.add((entry.getKey()) + "*1");
            }
        }
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mutile10:
                    mBuyBottomView.mEdit_buy_times.setText("20");
                    mBuyBottomView.mEdit_buy_times.setSelection(2);
                    break;
                case R.id.mutile20:
                    mBuyBottomView.mEdit_buy_times.setText("50");
                    mBuyBottomView.mEdit_buy_times.setSelection(2);
                    break;
                case R.id.mutile50:
                    mBuyBottomView.mEdit_buy_times.setText("100");
                    mBuyBottomView.mEdit_buy_times.setSelection(3);
                    break;
                case R.id.mutile100:
                    mBuyBottomView.mEdit_buy_times.setText("200");
                    mBuyBottomView.mEdit_buy_times.setSelection(3);
                    break;
            }
        }
    };

    OnRefreshListener OnrefreshListener=new OnRefreshListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            addData();
        }
    };
    View.OnClickListener ConfirmForecastListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            Intent intent = new Intent(mContext, JczqActivity.class);
            intent.putExtra("lotcode", "42");
            intent.putExtra("bunch", true);
            startActivity(intent);
        }
    };
    View.OnClickListener OptimizeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMulti <= 1) {
                Toast.makeText(mContext, "无法优化奖金!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (choosedContentFormList.size() <= 1) {
                Toast.makeText(mContext, "无法优化奖金!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(mContext, BonusOptimizeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("lotcode", "42");
            bundle.putInt("shakes", shakes);
            bundle.putInt("mMulti", mMulti);
            bundle.putSerializable("choosedContentFormList", (Serializable) choosedContentFormList);
            bundle.putSerializable("danTuoList", new ArrayList<String>());
            bundle.putSerializable("mBunchMap", mBunchMap);
            bundle.putSerializable("mChoosedContentMap", (Serializable) mChoosedContentMap);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    View.OnClickListener GendanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // inflate.setFocusable(false);
            if (LotteryApp.isLogin) {
                if ("".equals(mBuyBottomView.mEdit_buy_times.getText().toString().trim())) {
                    Toast.makeText(mContext, "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else if (shakes * mMulti * 2 < minTJMoney) {
                    HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(mContext);
                    hintDialogUtils2.setMessage(tjMoneyMsg);
                    hintDialogUtils2.setTitle("温馨提示");
                    hintDialogUtils2.setTitleVisiable(true);
                } else {
                    GendanView mGendanView = new GendanView(mContext);
                    //View view = View.inflate(mContext, R.layout.dialog_hemai_layout, null);
                    final Dialog gendanDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
                    mGendanView.setData(new GendanView.OnclickgdComit() {
                        @Override
                        public void onClickComit(String str1, String str2, int GenDanType) {
                            gendanDialog.dismiss();
                            if (GenDanType == 0) {
                                return;
                            }
                            hemaiBuy(str1, str2, "", GenDanType, false);
                            //提交订单
                        }
                    });
                    gendanDialog.setContentView(mGendanView);
                    gendanDialog.show();
                }
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        }
    };
    private boolean flag = false;
    View.OnClickListener BunchClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChoosedContentMap.size() >= 2)
                if (!flag) {
                    mBuyBottomView.mLlBunch.setVisibility(View.VISIBLE);
                    mBuyBottomView.mLine_Bunch.setVisibility(View.VISIBLE);
                    flag = true;
                    //setRecyclerView();
                } else {
                    mBuyBottomView.mLlBunch.setVisibility(View.GONE);
                    mBuyBottomView.mLine_Bunch.setVisibility(View.GONE);
                    flag = false;
                }
        }
    };
    TextWatcher MultiClick = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = String.valueOf(s).length();
            if (length == 0) {
                mMulti = 0;

            } else if (Integer.parseInt(String.valueOf(s)) > 50000) {
                mMulti = 50000;
                mBuyBottomView.mEdit_buy_times.setText("50000");
                mBuyBottomView.mEdit_buy_times.setSelection("50000".length());
                ToastUtils.showToast(mContext, "最大输入50000");
            } else if (length > 1 && Integer.parseInt(String.valueOf(s)) == 0) {
                mBuyBottomView.mEdit_buy_times.setText("0");
                mMulti = 0;
            } else {
                mMulti = Integer.parseInt(String.valueOf(s));
            }

            setStakesAndMoney();
            setBonusView();
        }
    };
    View.OnClickListener BuyBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String spliteStr = SpliceCtr.spliteStr1(choosedContentFormList, new ArrayList<String>(), true);
            Log.e("投注串", spliteStr);
            //需要判断是否为登录状态，如果不是就跳转到登录界面
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
                    return;
                }
                if ("".equals(mBuyBottomView.mEdit_buy_times.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else {

                    StringBuilder bunchsStr = new StringBuilder();
                    int i = 0;
                    for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
                        i++;
                        if (i < mBunchMap.size()) {
                            bunchsStr.append((entry.getKey()) + "串1,");
                        } else {
                            bunchsStr.append((entry.getKey()) + "串1");
                        }
                    }
                    if (!NetworkUtils.isNetworkAvailable(mContext)) {
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage("您的网络未连接，请连接后重试！");
                        return;
                    }
                    //串的集合进行遍历
                    StringBuilder strBuilder = new StringBuilder();
                    strBuilder.append(spliteStr);
                    for (int j = 0; j < mBunchList.size(); j++) {
                        if (j == mBunchList.size() - 1) {
                            strBuilder.append(mBunchList.get(j));
                        } else {
                            strBuilder.append(mBunchList.get(j) + ",");
                        }
                    }
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    Date date = new Date();
                    String issue = format.format(date);
                    Bundle bundle = new Bundle();
                    bundle.putInt("requestCode", 200);
                    bundle.putString("buyMethod", "normal");
                    bundle.putString("content", String.valueOf(strBuilder));
                    bundle.putInt("shakes", shakes);
                    bundle.putInt("mMulti", mMulti);
                    bundle.putString("money", shakes * mMulti * 2 + "");
                    bundle.putString("issue", issue);
                    bundle.putString("lotcode", "42");
                    NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
                    netWorkData.orderForm();
                }
            } else {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        }
    };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://隐藏键盘的时候
                    mLl_mutile.setVisibility(View.GONE);
                    mLl_buy_bttomview.setVisibility(View.VISIBLE);
                    break;
                case 2://显示键盘的时候
                    mLl_mutile.setVisibility(View.VISIBLE);
                    mLl_buy_bttomview.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void hemaiBuy(String str1, String str2, String str3, int type, boolean ghFlag) {
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
            return;
        }
        //wrate 提成比例，bnum认购   pnum保底 desc 描述
        String wrate, bnum, pnum, desc;
        int order_type;
        if (ghFlag) {
            wrate = str1;
            bnum = str2;
            pnum = str3;
            desc = "跟着成功人的脚步走";
            order_type = 1;
        } else {
            wrate = str2;
            bnum = "0";
            pnum = "0";
            desc = str1;
            order_type = 7;
        }
        final String spliteStr;
        spliteStr = SpliceCtr.spliteStr1(choosedContentFormList, new ArrayList<String>(), true);
        StringBuilder bunchsStr = new StringBuilder();
        int m = 0;
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            m++;
            if (m < mBunchMap.size()) {
                bunchsStr.append((entry.getKey()) + "串1,");
            } else {
                bunchsStr.append((entry.getKey()) + "串1");
            }
        }
        //串的集合进行遍历
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(spliteStr);
        for (int i = 0; i < mBunchList.size(); i++) {
            if (i == mBunchList.size() - 1) {
                strBuilder.append(mBunchList.get(i));
            } else {
                strBuilder.append(mBunchList.get(i) + ",");
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String issue = format.format(date);
        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", 200);
        bundle.putString("buyMethod", "hemai");
        bundle.putString("content", String.valueOf(strBuilder));
        bundle.putInt("shakes", shakes);
        bundle.putInt("mMulti", mMulti);
        bundle.putString("money", shakes * mMulti * 2 + "");
        bundle.putString("issue", issue);
        bundle.putString("lotcode", lotcode);
        bundle.putString("order_type", order_type + "");
        bundle.putString("wrate", wrate);
        bundle.putString("bnum", bnum);
        bundle.putString("pnum", pnum);
        bundle.putString("type", type + "");
        bundle.putString("desc", desc);
        bundle.putDouble("maxTheoryBonus", maxTheoryBonus);
        NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
        netWorkData.orderForm();

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > DisplayUtil.getDisplayHeight() / 3)) {

            //Toast.makeText(getContext(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(2);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > DisplayUtil.getDisplayHeight() / 3)) {
            // Message msg = Message.obtain();
            mHandler.sendEmptyMessage(1);
            //Toast.makeText(getContext(), "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }
    }
}
