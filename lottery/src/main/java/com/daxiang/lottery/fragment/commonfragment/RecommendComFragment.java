package com.daxiang.lottery.fragment.commonfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.adapter.wonderfuladapter.GendanAdapter;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.KeyBardInterface;
import com.daxiang.lottery.entity.RecommendBaseData;
import com.daxiang.lottery.entity.TogetherAndFollowData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.KeyboardUtil;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.MyKeyBoardView;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.daxiang.lottery.R.id.money;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class RecommendComFragment extends Fragment {
    private View mInflate;
    private String mLotcode;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    private NoDataView mNoData;
    private int page = 1;
    private int totalPage = 0;
    private List<RecommendBaseData> mItemsList = new ArrayList<>();
    private GendanAdapter mAdapter;
    private Context mContext;
    private boolean isRefresh;
    private int buyPrivilege;

    public void setData(String mLotcode, Context mContext) {
        this.mLotcode = mLotcode;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_recommend, null);
            buyPrivilege = SpUtils.getBuyPrivilege();
            initView();
        }
        keyboardUtil = new KeyboardUtil(getActivity(), mKeyBoardView);
        // mItemsList.clear();
        // LoadingDialogUtils.loading(getContext());
        mAdapter = new GendanAdapter(getContext(), mItemsList, true, KeyBardListener);
        mListView.setAdapter(mAdapter);
        addListener();
        return mInflate;
    }

    private void initView() {
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mListView = (ListView) mInflate.findViewById(R.id.lv_gendan);
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        mNoData.setButtonText(mNoData.REFRESH);
          /*
        * 点击立即跟单弹出的界面
        * */
        mOne_mutil = (TextView) mInflate.findViewById(R.id.one_mutil);
        mTwo_mutil = (TextView) mInflate.findViewById(R.id.two_mutil);
        mThree_mutil = (TextView) mInflate.findViewById(R.id.three_mutil);
        mFour_mutil = (TextView) mInflate.findViewById(R.id.four_mutil);
        mLl_keybard = (LinearLayout) mInflate.findViewById(R.id.ll_keybard);
        mKeyBoardView = (MyKeyBoardView) mInflate.findViewById(R.id.keyboard_view);
        mCancel = (ImageView) mInflate.findViewById(R.id.cancel);
        mKeyBoardBg = mInflate.findViewById(R.id.keyboard_bg);
        mEdit_buy_times = (EditText) mInflate.findViewById(R.id.edit_buy_times);
        mMoney = (TextView) mInflate.findViewById(money);
    }

    private boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            addData();
        }
    }

    HttpInterface2 mHttp = new HttpUtils2(getActivity());

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(getContext());
        Bundle params = new Bundle();
        params.putString("lotCode", mLotcode);
        params.putString("orderBy", "totalGD");// 	totalGD-累计跟单；winRate-命中率；gainRate-盈利率，默认值：totalGD
        params.putString("pn", page + "");
        params.putString("ps", "15");
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
                    //如果页数为0的话那么就是没有数据,或者是审核中
                    if (totalPage == 0||buyPrivilege==1) {
                        mNoData.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.GONE);
                    } else {
                        mNoData.setVisibility(View.GONE);
                        mListView.setVisibility(View.VISIBLE);
                    }
                    List<TogetherAndFollowData.DataBean.ListBean> items = gendanData.getData().getList();
                    List<TogetherAndFollowData.DataBean.ListBean> items2 = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        int lotType = Integer.parseInt(items.get(i).getLotCode());
                        if (lotType == Integer.parseInt(mLotcode)) {
                            items2.add(items.get(i));
                        }
                    }
                    mItemsList.addAll(items2);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                mNoData.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }
        });
    }

    private void addListener() {
        mNoData.setOnClickListener(RefreshListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mItemsList.clear();
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

    }

    View.OnClickListener RefreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mItemsList.clear();
            page = 1;
            totalPage = 1;
            addData();
        }
    };

    private TextView mOne_mutil;
    private TextView mTwo_mutil;
    private TextView mThree_mutil;
    private TextView mFour_mutil;
    private ImageView mCancel;
    private View mKeyBoardBg;
    private LinearLayout mLl_keybard;
    private MyKeyBoardView mKeyBoardView;
    private EditText mEdit_buy_times;
    private TextView mMoney;
    private KeyboardUtil keyboardUtil;
    private double oneMoney;
    private RecommendBaseData listBean;
    KeyBardInterface KeyBardListener = new KeyBardInterface() {

        @Override
        public void callBack(RecommendBaseData listBea, int position) {
            if (listBea == null) return;
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

            mMoney.setText(oneMoney * mMulti + "元");
            mEdit_buy_times.setText(mMulti + "");
            mEdit_buy_times.setSelection((mMulti + "").length());
            keyboardUtil.attachTo(mEdit_buy_times);
            mEdit_buy_times.setFocusable(true);
            mEdit_buy_times.setFocusableInTouchMode(true);
            mEdit_buy_times.requestFocus();
            mCancel.setOnClickListener(CancelListener);
            mKeyBoardBg.setOnClickListener(KeyBoardBgListener);
            keyboardUtil.setOnOkClick(OkListener);
            mOne_mutil.setOnClickListener(MutilListener);
            mThree_mutil.setOnClickListener(MutilListener);
            mTwo_mutil.setOnClickListener(MutilListener);
            mFour_mutil.setOnClickListener(MutilListener);
            mEdit_buy_times.addTextChangedListener(TextWatcherListener);
            mEdit_buy_times.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    keyboardUtil.attachTo(mEdit_buy_times);
                    return false;
                }
            });
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
            mLl_keybard.setVisibility(View.GONE);
            keyboardUtil.hideKeyboard();
        }
    };
    View.OnClickListener KeyBoardBgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLl_keybard.setVisibility(View.GONE);
            keyboardUtil.hideKeyboard();
        }
    };
    KeyboardUtil.OnOkClick OkListener = new KeyboardUtil.OnOkClick() {
        @Override
        public void onOkClick() {//确定购买
            if (mMulti == 0) {
                Toast.makeText(mContext, "您输入倍数为0", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!LotteryApp.isLogin) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
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
                            Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivity(intent);
                        }

                    }
                });
                return;
            }
            mLl_keybard.setVisibility(View.GONE);
            keyboardUtil.hideKeyboard();
            int i = (int) Double.parseDouble(listBean.getOneMoney());
            Bundle bundle = new Bundle();
            bundle.putString("from", "gendanDetailActivity");
            bundle.putBoolean("mFlag", true);
            bundle.putString("money", mMulti * i + "");
            bundle.putString("mutile", mMulti + "");
            bundle.putString("orderId", listBean.getOrderId());
            bundle.putString("lotcode", listBean.getLotCode() + "");
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
}
