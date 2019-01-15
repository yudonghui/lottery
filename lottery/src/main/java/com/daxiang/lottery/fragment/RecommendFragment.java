package com.daxiang.lottery.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.H5Activity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.RankingActivity;
import com.daxiang.lottery.activity.SearchGodActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.adapter.GodRvAdapter;
import com.daxiang.lottery.adapter.wonderfuladapter.GendanAdapter;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.Constants;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.KeyBardHD;
import com.daxiang.lottery.cxinterface.KeyBardInterface;
import com.daxiang.lottery.entity.RankingData;
import com.daxiang.lottery.entity.RecommendBaseData;
import com.daxiang.lottery.entity.TogetherAndFollowData;
import com.daxiang.lottery.fragment.userfragment.BaseYuFragment;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.KeyboardUtil;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.MyKeyBoardView;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.daxiang.lottery.R.id.money;
import static com.daxiang.lottery.constant.Constants.HUIBAO;
import static com.daxiang.lottery.constant.Constants.MINGZHONG;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class RecommendFragment extends BaseYuFragment {
    private BillListView mListView;
    private View mTitleView;
    private TitleBar mTitleBar;
    private NoDataView mNoData;
    private int page = 1;
    private int totalPage = 0;
    private List<RecommendBaseData> mItemsList = new ArrayList<>();
    private GendanAdapter mAdapter;
    private View view;
    private Context mContext;
    private SmartRefreshLayout mRefresh;
    private LinearLayout mLl_renqi;
    private LinearLayout mLl_huibao;
    private LinearLayout mLl_mingzhong;
    private LinearLayout mLl_lianhong;
    private NoDataView mStopping;
    private RecyclerView mRecyclerView;
    private LinearLayout mLl_one;
    private EditText mEdit_buy_times;
    private TextView mMoney;
    private View mKeyBoardBg;
    private LinearLayout mLl_two;
    private TextView mOne_mutil;
    private TextView mTwo_mutil;
    private TextView mThree_mutil;
    private TextView mFour_mutil;
    private ImageView mCancel;
    private ImageView mOrder_img;
    private TextView mOrder_text;
    private LinearLayout mLl_keybard;
    private LinearLayout mLl_todayRecommend;
    private TextView mTodayRecommend;
    private View mTodayRecommend_line;
    private LinearLayout mLl_yesterday;
    private LinearLayout mLlOrder;
    private TextView mYesterday;
    private View mYesterday_line;
    List<RankingData.DataBean> mGodList = new ArrayList<>();
    private GodRvAdapter mGodRvAdapter;
    private KeyboardUtil keyboardUtil;
    private KeyBardHD mKeyBardHD;
    private MyKeyBoardView mMyKeyBoardView;
    private LinearLayout mLl_search;
    private PopupWindow mPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_gendan, null);
        mContext = getContext();
        initView();
        keyboardUtil = new KeyboardUtil(getActivity(), mMyKeyBoardView);
        mAdapter = new GendanAdapter(mContext, mItemsList, true, KeyBardListener);
        mListView.setAdapter(mAdapter);
        mGodRvAdapter = new GodRvAdapter(mContext, mGodList);
        mRecyclerView.setAdapter(mGodRvAdapter);
        addData();
        addListener();
        return view;
    }

    private void initView() {
        mTitleView = view.findViewById(R.id.titleView);
        mTitleBar = (TitleBar) view.findViewById(R.id.titlebar_gendan);
        mTitleBar.mImageBack.setVisibility(View.GONE);
        mTitleBar.mLeftView.setText("说明");
        mTitleBar.mLeftView.setVisibility(View.VISIBLE);
        mTitleBar.mShare.setImageResource(R.drawable.self);
        mTitleBar.mShare.setVisibility(View.VISIBLE);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("推荐大厅");
        mLl_search = (LinearLayout) view.findViewById(R.id.ll_search);
        mStopping = (NoDataView) view.findViewById(R.id.stopping);
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);
        mLl_renqi = (LinearLayout) view.findViewById(R.id.ll_renqi);
        mLl_huibao = (LinearLayout) view.findViewById(R.id.ll_huibao);
        mLl_mingzhong = (LinearLayout) view.findViewById(R.id.ll_mingzhong);
        mLl_lianhong = (LinearLayout) view.findViewById(R.id.ll_lianhong);
        mNoData = (NoDataView) view.findViewById(R.id.no_data);
        mListView = (BillListView) view.findViewById(R.id.lv_gendan);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLl_one = (LinearLayout) view.findViewById(R.id.ll_one);
        mEdit_buy_times = (EditText) view.findViewById(R.id.edit_buy_times);
        mMoney = (TextView) view.findViewById(money);
        mLl_two = (LinearLayout) view.findViewById(R.id.ll_two);
        mOne_mutil = (TextView) view.findViewById(R.id.one_mutil);
        mTwo_mutil = (TextView) view.findViewById(R.id.two_mutil);
        mThree_mutil = (TextView) view.findViewById(R.id.three_mutil);
        mFour_mutil = (TextView) view.findViewById(R.id.four_mutil);
        mLl_keybard = (LinearLayout) view.findViewById(R.id.ll_keybard);
        mKeyBoardBg = view.findViewById(R.id.keyboard_bg);
        mCancel = (ImageView) view.findViewById(R.id.cancel);
        mMyKeyBoardView = (MyKeyBoardView) view.findViewById(R.id.keyboard_view);
        mOrder_img = (ImageView) view.findViewById(R.id.order_img);
        mOrder_text = (TextView) view.findViewById(R.id.order_text);
        mLl_todayRecommend = (LinearLayout) view.findViewById(R.id.ll_todayRecommend);
        mTodayRecommend = (TextView) view.findViewById(R.id.todayRecommend);
        mTodayRecommend_line = (View) view.findViewById(R.id.todayRecommend_line);
        mLl_yesterday = (LinearLayout) view.findViewById(R.id.ll_yesterday);
        mYesterday = (TextView) view.findViewById(R.id.yesterday);
        mYesterday_line = (View) view.findViewById(R.id.yesterday_line);
        mLlOrder = (LinearLayout) view.findViewById(R.id.ll_order);
        if (inflate == null) {
            initPop();
        }
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleView.setVisibility(View.VISIBLE);
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dimensionPixelSize);
                mTitleView.setLayoutParams(layoutParams);
            }
        } else mTitleView.setVisibility(View.GONE);
    }

    public void setListener(KeyBardHD mKeyBardHD) {
        this.mKeyBardHD = mKeyBardHD;
    }

    HttpInterface2 mHttp = new HttpUtils2(mContext);
    private String orderBy = "totalGD";//totalGD-累计跟单；winRate-命中率；gainRate-盈利率，默认值：totalGD
    private boolean isToday = true;//true代表今天的推单，false代表昨日推单。

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        if (isToday) {
            params.putString("orderBy", orderBy);//是否可购买：空值/true(进行中)/false(已完成),默认值：true
            params.putString("buyFlag", "true");
        } else {
            params.putString("buyFlag", "false");
        }
        params.putString("pn", page + "");
        params.putString("ps", "15");
        // if (LotteryApp.isLogin) {
        mHttp.get(Url.USER_FOLLOWBUY_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                if (isRefresh) {//如果是下拉刷新。清空数据源。
                    mItemsList.clear();
                    isRefresh = false;
                }
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                TogetherAndFollowData gendanData = gson.fromJson(result, TogetherAndFollowData.class);
                if (gendanData.getCode() == 0) {
                    totalPage = gendanData.getData().getTotalPages();
                    List<TogetherAndFollowData.DataBean.ListBean> items = gendanData.getData().getList();
                    mItemsList.addAll(items);
                }
                mAdapter.notifyDataSetChanged();
                if (mItemsList.size() == 0) mNoData.setVisibility(View.VISIBLE);
                else mNoData.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                mNoData.setVisibility(View.VISIBLE);
            }
        });
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        mHttp.jsonByUrl(Url.GODINFO, new JsonInterface() {
            @Override
            public void callback(String result) {
                mGodList.clear();
                Gson gson = new Gson();
                RankingData rankingData = gson.fromJson(result, RankingData.class);
                int code = rankingData.getCode();
                String msg = rankingData.getMsg();
                if (code == 0) {
                    mGodList.addAll(rankingData.getData());
                    mGodRvAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    private boolean isRefresh;

    private void addListener() {
        mTitleBar.mLeftView.setOnClickListener(RecommendRuleListner);
        mLl_search.setOnClickListener(SearchListener);
        mEdit_buy_times.addTextChangedListener(TextWatcherListener);
        mKeyBoardBg.setOnClickListener(KeyBoardBgListener);
        mOrder_img.setOnClickListener(PopupListener);
        mOrder_text.setOnClickListener(PopupListener);
        mLl_todayRecommend.setOnClickListener(LlListener);
        mLl_yesterday.setOnClickListener(LlListener);
        mEdit_buy_times.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.attachTo(mEdit_buy_times);
                return false;
            }
        });
        mTitleBar.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LotteryApp.isLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", LotteryApp.uid);
                    startActivity(GodInfoActivity.class, bundle);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 1;
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (page < totalPage) {
                    page++;
                    addData();
                } else {
                    mRefresh.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(mContext, "已经是最后一页", Toast.LENGTH_SHORT).show();
                            mRefresh.finishLoadMore();
                        }
                    }, 1000);
                }
            }
        });

        mLl_renqi.setOnClickListener(BangDanListener);
        mLl_huibao.setOnClickListener(BangDanListener);
        mLl_mingzhong.setOnClickListener(BangDanListener);
        mLl_lianhong.setOnClickListener(BangDanListener);
    }

    View.OnClickListener LlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_todayRecommend:
                    if (!isToday) {//当前所在不是今日神单才刷新数据
                        isToday = true;
                        page = 1;
                        setToday();
                        isRefresh = true;
                        addData();
                    }
                    break;
                case R.id.ll_yesterday:
                    if (isToday) {//当前所在是今日神单才刷新数据
                        isToday = false;
                        page = 1;
                        setYesterday();
                        isRefresh = true;
                        addData();
                    }
                    break;
            }

        }
    };

    private void setToday() {
        mTodayRecommend.setTextColor(getResources().getColor(R.color.red_txt));
        mTodayRecommend_line.setVisibility(View.VISIBLE);
        mYesterday.setTextColor(getResources().getColor(R.color.deep_txt));
        mYesterday_line.setVisibility(View.INVISIBLE);
        mLlOrder.setVisibility(View.VISIBLE);
    }

    private void setYesterday() {
        mTodayRecommend.setTextColor(getResources().getColor(R.color.deep_txt));
        mTodayRecommend_line.setVisibility(View.INVISIBLE);
        mYesterday.setTextColor(getResources().getColor(R.color.red_txt));
        mYesterday_line.setVisibility(View.VISIBLE);
        mLlOrder.setVisibility(View.GONE);
    }

    private TextView mProfit_rate;
    private TextView mHit_rate;
    private TextView mGendan_money;
    private TextView mMy_follow;
    private View inflate;
    View.OnClickListener PopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
            lp.alpha = 0.6f;
            ((Activity) mContext).getWindow().setAttributes(lp);
            mPopupWindow.showAsDropDown(mOrder_img, -DisplayUtil.dip2px(50), -DisplayUtil.dip2px(5));
        }
    };

    private void initPop() {
        inflate = View.inflate(mContext, R.layout.order_god_pop, null);//弹出的排序用的框
        // inflate=getActivity().getLayoutInflater().inflate(R.layout.order_god_pop,mScrollView,false);
        mProfit_rate = (TextView) inflate.findViewById(R.id.profit_rate);
        mHit_rate = (TextView) inflate.findViewById(R.id.hit_rate);
        mGendan_money = (TextView) inflate.findViewById(R.id.gendan_money);
        mMy_follow = (TextView) inflate.findViewById(R.id.my_follow);
        mProfit_rate.setOnClickListener(ProfitListener);//盈利率
        mHit_rate.setOnClickListener(HitListener);//命中率
        mGendan_money.setOnClickListener(GeandanMoney);//跟单金额
        mMy_follow.setOnClickListener(FollowListener);//我的关注
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(DisplayUtil.dip2px(130));
        mPopupWindow.setHeight(DisplayUtil.dip2px(135));
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOnDismissListener(poponDismissListener);
    }

    View.OnClickListener ProfitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProfit_rate.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            mHit_rate.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mGendan_money.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mMy_follow.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mOrder_text.setText("盈利率");
            mPopupWindow.dismiss();
            orderBy = "gainRate";
            mItemsList.clear();
            page = 1;
            mAdapter.setOrderBy(orderBy);
            addData();
        }
    };
    View.OnClickListener HitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProfit_rate.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mHit_rate.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            mGendan_money.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mMy_follow.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mOrder_text.setText("命中率");
            mPopupWindow.dismiss();
            orderBy = "winRate";//命中率
            mItemsList.clear();
            page = 1;
            mAdapter.setOrderBy(orderBy);
            addData();
        }
    };
    View.OnClickListener GeandanMoney = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProfit_rate.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mHit_rate.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mGendan_money.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            mMy_follow.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mOrder_text.setText("跟单金额");
            mPopupWindow.dismiss();
            orderBy = "totalGD";//跟单金额
            mItemsList.clear();
            page = 1;
            mAdapter.setOrderBy(orderBy);
            addData();
        }
    };
    View.OnClickListener FollowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProfit_rate.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mHit_rate.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mGendan_money.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mMy_follow.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            mPopupWindow.dismiss();
            //Toast.makeText(mContext, "我的关注", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener SearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(SearchGodActivity.class);
        }
    };
    View.OnClickListener RecommendRuleListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle params = new Bundle();
            params.putString("from", "RecommendFragment");
            startActivity(H5Activity.class, params);
        }
    };
    View.OnClickListener BangDanListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_renqi://人气榜
                    Bundle params = new Bundle();
                    params.putString("title", Constants.RENQI);
                    startActivity(RankingActivity.class, params);
                    break;
                case R.id.ll_huibao://回报榜
                    Bundle params2 = new Bundle();
                    params2.putString("title", HUIBAO);
                    startActivity(RankingActivity.class, params2);
                    break;
                case R.id.ll_mingzhong://命中榜
                    Bundle params3 = new Bundle();
                    params3.putString("title", MINGZHONG);
                    startActivity(RankingActivity.class, params3);
                    break;
                case R.id.ll_lianhong://连红榜
                    Bundle params4 = new Bundle();
                    params4.putString("title", Constants.LIANHONG);
                    startActivity(RankingActivity.class, params4);
                    break;

            }
        }
    };
    private double oneMoney;
    private RecommendBaseData listBean;
    private int ver;
    KeyBardInterface KeyBardListener = new KeyBardInterface() {

        @Override
        public void callBack(RecommendBaseData listBea, int position) {
            if (listBea == null) return;
            mKeyBardHD.hint();
            listBean = listBea;
            String mOneMoney = listBean.getOneMoney();
            if (TextUtils.isEmpty(mOneMoney)) return;
            oneMoney = Double.parseDouble(mOneMoney);
            if (oneMoney < 100) {
                mMulti = 10;
            } else {
                mMulti = 1;
            }
            mLl_keybard.setVisibility(View.VISIBLE);


            View childAt = mListView.getChildAt(position);
            int[] childLocation = new int[2];
            childAt.getLocationOnScreen(childLocation);
            int y = childLocation[1] + DisplayUtil.getStatusBarHeight();
            int displayHeight = DisplayUtil.getDisplayHeight();
            ver = displayHeight - y - DisplayUtil.dip2px(141 + 230 + 45);
            Log.e("距离：", ver + "");
            if (ver < 0)
                mRefresh.scrollBy(0, -ver);


            mMoney.setText(oneMoney * mMulti + "元");
            mEdit_buy_times.setText(mMulti + "");
            mEdit_buy_times.setSelection((mMulti + "").length());
            keyboardUtil.attachTo(mEdit_buy_times);
            mEdit_buy_times.setFocusable(true);
            mEdit_buy_times.setFocusableInTouchMode(true);
            mEdit_buy_times.requestFocus();
            mCancel.setOnClickListener(CancelListener);
            keyboardUtil.setOnOkClick(OkListener);
            mOne_mutil.setOnClickListener(MutilListener);
            mThree_mutil.setOnClickListener(MutilListener);
            mTwo_mutil.setOnClickListener(MutilListener);
            mFour_mutil.setOnClickListener(MutilListener);
        }
    };
    View.OnClickListener MutilListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.one_mutil:
                    mEdit_buy_times.setText("20");
                    mEdit_buy_times.setSelection(2);
                    mMulti = 20;
                    break;
                case R.id.two_mutil:
                    mEdit_buy_times.setText("50");
                    mEdit_buy_times.setSelection(2);
                    mMulti = 50;
                    break;
                case R.id.three_mutil:
                    mEdit_buy_times.setText("100");
                    mEdit_buy_times.setSelection(3);
                    mMulti = 100;
                    break;
                case R.id.four_mutil:
                    mEdit_buy_times.setText("200");
                    mEdit_buy_times.setSelection(3);
                    mMulti = 200;
                    break;
            }
            mMoney.setText(mMulti * oneMoney + "元");
        }
    };
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hintKey();
        }
    };
    View.OnClickListener KeyBoardBgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hintKey();
        }
    };

    private void displayKey() {
        mLl_keybard.setVisibility(View.VISIBLE);
        keyboardUtil.showKeyboard();
        mKeyBardHD.hint();
    }

    private void hintKey() {
        mLl_keybard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        mKeyBardHD.display();
        if (ver < 0)
            mRefresh.scrollBy(0, ver);
    }

    KeyboardUtil.OnOkClick OkListener = new KeyboardUtil.OnOkClick() {
        @Override
        public void onOkClick() {//确定购买
            if (mMulti == 0) {
                Toast.makeText(mContext, "您输入倍数为0", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!LotteryApp.isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if (!LotteryApp.phoneFlag) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        //手机号未绑定
                        if (LotteryApp.isThird) {
                            startActivity(PhoneIsExistActivity.class);

                        } else {
                            Bundle params = new Bundle();
                            params.putBoolean("isBind", false);
                            startActivity(BindPhoneActivity.class, params);
                        }

                    }
                });
                return;
            }
            hintKey();//隐藏键盘
            int i = (int) Double.parseDouble(listBean.getOneMoney());
            Bundle bundle = new Bundle();
            bundle.putString("from", "gendanDetailActivity");
            bundle.putBoolean("mFlag", true);
            bundle.putString("money", mMulti * i + "");
            bundle.putString("mutile", mMulti + "");
            bundle.putString("orderId", listBean.getOrderId());
            bundle.putString("lotcode", listBean.getLotCode() + "");
            bundle.putString("mGodName", listBean.getUserName());
            bundle.putString("mGodUserId", listBean.getUserId());
            NetWorkData netWorkData = new NetWorkData(mContext, bundle);
            netWorkData.genheOrderForm();
        }
    };
    private int mMulti;
    TextWatcher TextWatcherListener = new TextWatcher() {
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
                mEdit_buy_times.setText("50000");
                mEdit_buy_times.setSelection("50000".length());
                Toast.makeText(mContext, "最大输入50000", Toast.LENGTH_SHORT).show();
            } else if (length > 1 && Integer.parseInt(String.valueOf(s)) == 0) {
                mEdit_buy_times.setText("0");
                mMulti = 0;
            } else {
                mMulti = Integer.parseInt(String.valueOf(s));
            }
            mMoney.setText(mMulti * oneMoney + "元");
        }
    };
    PopupWindow.OnDismissListener poponDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
            lp.alpha = 1f;
            ((Activity) mContext).getWindow().setAttributes(lp);
        }
    };

    public void hiddenKey() {
        if (keyboardUtil != null && mLl_keybard.getVisibility() == View.VISIBLE) {
            hintKey();
        }

    }
}
