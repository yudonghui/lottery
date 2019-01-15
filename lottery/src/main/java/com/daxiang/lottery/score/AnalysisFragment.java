package com.daxiang.lottery.score;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.IntegrateActivity;
import com.daxiang.lottery.adapter.HistoryAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.BaseBean;
import com.daxiang.lottery.entity.FutureData;
import com.daxiang.lottery.entity.HistoryDeatailBean;
import com.daxiang.lottery.entity.IntegrateData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.MatchView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/16
 * @describe May the Buddha bless bug-free!!!
 */
public class AnalysisFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private View view;
    private FrameLayout mHistory_vs;
    private ImageView mHistory_vs_img;
    private TextView mHistory_times;
    private BillListView mVs_lv;
    private FrameLayout mHistory_home;
    private ImageView mHistory_home_img;
    private TextView mHome_times;
    private BillListView mHome_lv;
    private FrameLayout mHistory_guest;
    private ImageView mHistory_guest_img;
    private TextView mGuest_times;
    private BillListView mGuest_lv;
    private LinearLayout mLlVs;
    private LinearLayout mLlHome;
    private LinearLayout mLlGuest;

    private MatchView mHistoryMv;
    private FrameLayout mFl_integral;
    private ImageView mIntegral_img;
    private LinearLayout mLl_integral;
    private BillListView mIntegral_lv;
    private TextView mIntegralS;
    private MatchView mHomeMv;
    private LinearLayout mLl_guest;
    private MatchView mGuestMv;
    private FrameLayout mFl_home_future;
    private TextView mHome_future;
    private ImageView mHome_future_img;
    private LinearLayout mLl_home_future;
    private BillListView mHome_future_lv;
    private FrameLayout mFl_guest_future;
    private TextView mGuest_future;
    private ImageView mGuest_future_img;
    private LinearLayout mLl_guest_future;
    private BillListView mGuest_future_lv;
    private TextView mHomeBall;//主队进失球
    private TextView mGuestBall;//客队进失球


    private List<BaseBean.ArrBean> mAwayList = new ArrayList<>();
    private List<BaseBean.ArrBean> mHomeList = new ArrayList<>();
    private List<BaseBean.ArrBean> mVsList = new ArrayList<>();
    private List<FutureData> mHomeFutureList = new ArrayList<>();
    private List<FutureData> mAwayFutureList = new ArrayList<>();
    private List<IntegrateData> mPointList = new ArrayList<>();
    private FutureAdapter mHomeFutureAdapter;
    private FutureAdapter mAwayFutureAdapter;
    private HistoryAdapter mAdapterVS;
    private HistoryAdapter mHomeHisAdapter;
    private HistoryAdapter mAwayHisAdapter;
    private String league_id;//联赛id
    private String away_id;//客队id
    private String home_id;//主队id
    private String league_name;//联赛名字
    String mId;
    private IntegrateAdapter mIntegrateAdapter;

    public void setVp(Context mContext, String mId) {
        this.mContext = mContext;
        this.mId = mId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_history, null);
            initView();
        }
        mContext = getContext();
        addListener();
        return view;
    }

    private boolean isFirst = true;
    private LoadingDialogUtils mLoadingDialogUtils;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("生命周期", "setUserVisibleHint");
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            mLoadingDialogUtils = new LoadingDialogUtils(mContext);
            addData();
        }
    }

    private void initView() {
        mHistory_vs = (FrameLayout) view.findViewById(R.id.history_vs);
        mHistory_vs_img = (ImageView) view.findViewById(R.id.history_vs_img);
        mHistory_times = (TextView) view.findViewById(R.id.history_times);
        mVs_lv = (BillListView) view.findViewById(R.id.vs_lv);
        mHistory_home = (FrameLayout) view.findViewById(R.id.history_home);
        mHistory_home_img = (ImageView) view.findViewById(R.id.history_home_img);
        mHome_times = (TextView) view.findViewById(R.id.home_times);
        mHome_lv = (BillListView) view.findViewById(R.id.home_lv);
        mHistory_guest = (FrameLayout) view.findViewById(R.id.history_guest);
        mHistory_guest_img = (ImageView) view.findViewById(R.id.history_guest_img);
        mGuest_times = (TextView) view.findViewById(R.id.guest_times);
        mGuest_lv = (BillListView) view.findViewById(R.id.guest_lv);
        mLlVs = (LinearLayout) view.findViewById(R.id.ll_vs);
        mLlHome = (LinearLayout) view.findViewById(R.id.ll_home);
        mLlGuest = (LinearLayout) view.findViewById(R.id.ll_guest);
        mHomeBall = (TextView) view.findViewById(R.id.home_ball);
        mGuestBall = (TextView) view.findViewById(R.id.guest_ball);

        mHistoryMv = (MatchView) view.findViewById(R.id.historyMv);
        mFl_integral = (FrameLayout) view.findViewById(R.id.fl_integral);
        mIntegral_img = (ImageView) view.findViewById(R.id.integral_img);
        mLl_integral = (LinearLayout) view.findViewById(R.id.ll_integral);
        mIntegral_lv = (BillListView) view.findViewById(R.id.integral_lv);
        mIntegralS = (TextView) view.findViewById(R.id.integralS);
        mHomeMv = (MatchView) view.findViewById(R.id.homeMv);
        mLl_guest = (LinearLayout) view.findViewById(R.id.ll_guest);
        mGuestMv = (MatchView) view.findViewById(R.id.guestMv);
        mFl_home_future = (FrameLayout) view.findViewById(R.id.fl_home_future);
        mHome_future = (TextView) view.findViewById(R.id.home_future);
        mHome_future_img = (ImageView) view.findViewById(R.id.home_future_img);
        mLl_home_future = (LinearLayout) view.findViewById(R.id.ll_home_future);
        mHome_future_lv = (BillListView) view.findViewById(R.id.home_future_lv);
        mFl_guest_future = (FrameLayout) view.findViewById(R.id.fl_guest_future);
        mGuest_future = (TextView) view.findViewById(R.id.guest_future);
        mGuest_future_img = (ImageView) view.findViewById(R.id.guest_future_img);
        mLl_guest_future = (LinearLayout) view.findViewById(R.id.ll_guest_future);
        mGuest_future_lv = (BillListView) view.findViewById(R.id.guest_future_lv);

    }


    private void addData() {

        HttpInterface2 mHttp = new HttpUtils2(mContext);
        //历史交锋数据
        Bundle params = new Bundle();
        params.putString("mId", mId);
        mHttp.get(Url.HISTORY_DETAIL_DATA, params, new JsonInterface() {

            @Override
            public void callback(String result) {
                mLoadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                HistoryDeatailBean historyDeatailBean = gson.fromJson(result, HistoryDeatailBean.class);
                int code = historyDeatailBean.getCode();
                String msg = historyDeatailBean.getMsg();
                if (code == 0) {
                    HistoryDeatailBean.DataBean data = historyDeatailBean.getData();
                    HistoryDeatailBean.DataBean.ItemsBean items = data.getItems();
                    league_id = items.getLeague_id();
                    home_id = items.getHome_id();
                    away_id = items.getAway_id();
                    league_name = items.getLeague_name();
                    setView(items);
                } else Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                mLoadingDialogUtils.setDimiss();
            }
        });


    }

    private void addListener() {
        mHistory_vs.setOnClickListener(this);
        mHistory_home.setOnClickListener(this);
        mHistory_guest.setOnClickListener(this);
        mFl_integral.setOnClickListener(this);//联赛积分
        mFl_home_future.setOnClickListener(this);//主队未来三场比赛
        mFl_guest_future.setOnClickListener(this);//客队未来三场比赛
        mIntegralS.setOnClickListener(IntegralsListener);//查看完整积分榜
    }

    private void setView(HistoryDeatailBean.DataBean.ItemsBean items) {
        String away_name = items.getAway_name();
        String home_name = items.getHome_name();
        String away_id = items.getAway_id();
        String home_id = items.getHome_id();
        HistoryDeatailBean.DataBean.ItemsBean.HisoryBean hisory = items.getHisory();
        HistoryDeatailBean.DataBean.ItemsBean.HomeHistoryBean home_history = items.getHome_history();
        HistoryDeatailBean.DataBean.ItemsBean.AwayHistoryBean away_history = items.getAway_history();
        HistoryDeatailBean.DataBean.ItemsBean.PointBean point = items.getPoint();

        mVsList.clear();
        mHomeList.clear();
        mAwayList.clear();
        mHomeFutureList.clear();
        mAwayFutureList.clear();
        mPointList.clear();
        mVsList.addAll(hisory.getArr());
        mHomeList.addAll(home_history.getArr());
        mAwayList.addAll(away_history.getArr());
        mHomeFutureList.addAll(items.getHome_futuregame());
        mAwayFutureList.addAll(items.getAway_futuregame());
        //两队历史战绩
        if (mVsList.size() == 0) {
            mLlVs.setVisibility(View.GONE);
            mHistory_vs.setVisibility(View.GONE);
        } else {
            mLlVs.setVisibility(View.VISIBLE);
            mHistory_vs.setVisibility(View.VISIBLE);
            mAdapterVS = new HistoryAdapter(mContext, mVsList, home_id);
            mVs_lv.setAdapter(mAdapterVS);
            int draw_num = hisory.getDraw_num();//主平
            int lose_num = hisory.getLose_num();//主负
            int win_num = hisory.getWin_num();//主胜
            mHistory_times.setText("历史交战 " + win_num + " 胜 " + draw_num + " 平 " + lose_num + " 负");
            mHistoryMv.setVisibility(View.VISIBLE);
            mHistoryMv.setNum(win_num, draw_num, lose_num);
        }
        //主队历史战绩
        if (mHomeList.size() == 0) {
            mLlHome.setVisibility(View.GONE);
            mHistory_home.setVisibility(View.GONE);
        } else {
            mLlHome.setVisibility(View.VISIBLE);
            mHistory_home.setVisibility(View.VISIBLE);
            mHomeHisAdapter = new HistoryAdapter(mContext, mHomeList, home_id);
            mHome_lv.setAdapter(mHomeHisAdapter);
            int lose_ball = home_history.getLose_ball();
            int get_ball = home_history.getGet_ball();
            int draw_num = home_history.getDraw_num();//主平
            int lose_num = home_history.getLose_num();//主负
            int win_num = home_history.getWin_num();//主胜
            mHome_times.setText(home_name + " " + win_num + " 胜 " + draw_num + " 平 " + lose_num + " 负");
            mHomeMv.setVisibility(View.VISIBLE);
            mHomeMv.setNum(win_num, draw_num, lose_num);
            mHomeBall.setText(home_name + "进" + get_ball + "球, 失" + lose_ball + "球");
        }
        //客队历史战绩
        if (mAwayList.size() == 0) {
            mLlGuest.setVisibility(View.GONE);
            mHistory_guest.setVisibility(View.GONE);
        } else {
            mLlGuest.setVisibility(View.VISIBLE);
            mHistory_guest.setVisibility(View.VISIBLE);
            mAwayHisAdapter = new HistoryAdapter(mContext, mAwayList, away_id);
            mGuest_lv.setAdapter(mAwayHisAdapter);
            int lose_ball = away_history.getLose_ball();
            int get_ball = away_history.getGet_ball();
            int draw_num = away_history.getDraw_num();//主平
            int lose_num = away_history.getLose_num();//主负
            int win_num = away_history.getWin_num();//主胜
            mGuest_times.setText(away_name + " " + win_num + " 胜 " + draw_num + " 平 " + lose_num + " 负");
            mGuestMv.setVisibility(View.VISIBLE);
            mGuestMv.setNum(win_num, draw_num, lose_num);
            mGuestBall.setText(away_name + "进" + get_ball + "球, 失" + lose_ball + "球");
        }

        //主队未来比赛
        if (mHomeFutureList.size() == 0) {
            mLl_home_future.setVisibility(View.GONE);
            mFl_home_future.setVisibility(View.GONE);
        } else {
            mLl_home_future.setVisibility(View.VISIBLE);
            mFl_home_future.setVisibility(View.VISIBLE);
            mHomeFutureAdapter = new FutureAdapter(mContext, mHomeFutureList, home_id);
            mHome_future_lv.setAdapter(mHomeFutureAdapter);
        }
        //客队未来比赛
        if (mAwayFutureList.size() == 0) {
            mLl_guest_future.setVisibility(View.GONE);
            mFl_guest_future.setVisibility(View.GONE);
        } else {
            mLl_guest_future.setVisibility(View.VISIBLE);
            mFl_guest_future.setVisibility(View.VISIBLE);
            mAwayFutureAdapter = new FutureAdapter(mContext, mAwayFutureList, away_id);
            mGuest_future_lv.setAdapter(mAwayFutureAdapter);
        }
        if (point != null && (point.getAway_point() != null || point.getHome_point() != null)) {
            mLl_integral.setVisibility(View.VISIBLE);
            mFl_integral.setVisibility(View.VISIBLE);
            IntegrateData away_point = point.getAway_point();
            IntegrateData home_point = point.getHome_point();
            if (away_point != null) {
                away_point.setName(away_name);
                mPointList.add(away_point);
            }
            if (home_point != null) {
                home_point.setName(home_name);
                mPointList.add(home_point);
            }
            mIntegrateAdapter = new IntegrateAdapter(mContext, mPointList);
            mIntegral_lv.setAdapter(mIntegrateAdapter);
        } else {
            mLl_integral.setVisibility(View.GONE);
            mFl_integral.setVisibility(View.GONE);
        }

    }

    View.OnClickListener IntegralsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(league_id))return;
            if (ClickUtils.isFastClick()) {
                return;
            }
            Intent intent = new Intent(mContext, IntegrateActivity.class);
            intent.putExtra("from","AnalysisFragment");
            intent.putExtra("name", league_name);
            intent.putExtra("away_id",away_id);
            intent.putExtra("home_id",home_id);
            intent.putExtra("league_id", league_id);
            startActivity(intent);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_vs:
                if (mLlVs.getVisibility() == View.VISIBLE) {
                    mLlVs.setVisibility(View.GONE);
                    mHistory_vs_img.setImageResource(R.drawable.icon_xia2);
                } else {
                    mLlVs.setVisibility(View.VISIBLE);
                    mHistory_vs_img.setImageResource(R.drawable.icon_xia);
                }
                break;
            case R.id.history_home:
                if (mLlHome.getVisibility() == View.VISIBLE) {
                    mLlHome.setVisibility(View.GONE);
                    mHistory_home_img.setImageResource(R.drawable.icon_xia2);
                } else {
                    mLlHome.setVisibility(View.VISIBLE);
                    mHistory_home_img.setImageResource(R.drawable.icon_xia);
                }
                break;
            case R.id.history_guest:
                if (mLlGuest.getVisibility() == View.VISIBLE) {
                    mLlGuest.setVisibility(View.GONE);
                    mHistory_guest_img.setImageResource(R.drawable.icon_xia2);
                } else {
                    mLlGuest.setVisibility(View.VISIBLE);
                    mHistory_guest_img.setImageResource(R.drawable.icon_xia);
                }
                break;
            case R.id.fl_integral:
                if (mLl_integral.getVisibility() == View.VISIBLE) {
                    mLl_integral.setVisibility(View.GONE);
                    mIntegral_img.setImageResource(R.drawable.icon_xia2);
                } else {
                    mLl_integral.setVisibility(View.VISIBLE);
                    mIntegral_img.setImageResource(R.drawable.icon_xia);
                }
                break;
            case R.id.fl_home_future:
                if (mLl_home_future.getVisibility() == View.VISIBLE) {
                    mLl_home_future.setVisibility(View.GONE);
                    mHome_future_img.setImageResource(R.drawable.icon_xia2);
                } else {
                    mLl_home_future.setVisibility(View.VISIBLE);
                    mHome_future_img.setImageResource(R.drawable.icon_xia);
                }
                break;
            case R.id.fl_guest_future:
                if (mLl_guest_future.getVisibility() == View.VISIBLE) {
                    mLl_guest_future.setVisibility(View.GONE);
                    mGuest_future_img.setImageResource(R.drawable.icon_xia2);
                } else {
                    mLl_guest_future.setVisibility(View.VISIBLE);
                    mGuest_future_img.setImageResource(R.drawable.icon_xia);
                }
                break;

        }
    }
}
