package com.daxiang.lottery.fragment.sfcr9cfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.daxiang.lottery.activity.lotteryactivity.ServiceAgreementActivity;
import com.daxiang.lottery.adapter.sfcandr9cadapter.ChooseContentAdapter;
import com.daxiang.lottery.adapter.sfcandr9cadapter.ChooseFormAdapter;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.dialog.LoadingDialog;
import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.entity.EndDateData;
import com.daxiang.lottery.entity.ServerDateData;
import com.daxiang.lottery.entity.SfcAndRjcData;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.ToastUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.utils.jcmath.Combination;
import com.daxiang.lottery.view.GendanView;
import com.daxiang.lottery.view.HemaiView;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TimeTextView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class SfcHallFragment extends Fragment implements View.OnLayoutChangeListener {
    private View mInflate;
    private String mLotcode;
    private View inflate;
    private View line;
    private LinearLayout mShake;
    private TimeTextView mTextDate;
    private SmartRefreshLayout mRefresh;
    private TextView mTextEndDate;
    private LinearLayout mSssss;
    private ListView mPullListView;
    private LinearLayout mClear;
    private TextView mTextEnsure;
    private TextView mGroup;
    private ListView mListViewContent;
    private TextView mTv_betCounts;
    private TextView mTv_money;
    private EditText mMultiEt;
    private TextView mSelectBallMin;
    private Button mBuyBtn;
    private ImageView mHemai;
    private Button mGendan;
    private ImageView mAddImage;
    private ImageView mClearImage;
    private NoDataView mNoData;
    private LinearLayout mLlZhuijia;
    private LinearLayout mLl_xieyi;
    //下面这两个是为了弹出软件盘时控制他们的显隐
    private LinearLayout mLl_buy_bttomview;
    private LinearLayout mLl_mutile;
    private TextView mMutile10;
    private TextView mMutile20;
    private TextView mMutile50;
    private TextView mMutile100;
    private ChooseFormAdapter mChooseFormAdapter;
    private ChooseContentAdapter mChooseContentAdapter;
    private EndDateData mEndDateData;
    AlertDialog buyViewDialog;
    private String issue;
    private int mMulti = 5;
    //总投注数
    private int shakes;
    private boolean flag = false;
    private int minTimes;
    private View view;
    private LoadingDialog mLoadingDialog;
    List<SfcAndRjcData.DataBean> dataList = new ArrayList<>();
    HashMap<SfcAndRjcData.DataBean, HashMap<String, String>> mChoosedContentMap = new HashMap<>();
    //存放点击确定按钮之后，在列表上面显示出来的数据源。
    ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList = new ArrayList<>();
    private int buyPrivilege;
    private int sfcState;
    private int r9cState;

    public void setData(String mLotcode) {
        this.mLotcode = mLotcode;
    }

    private int minTJMoney;//最小发单金额
    private String tjMoneyMsg;//最小发单金额说明

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_sfc_hall, null);
            SharedPreferences sp = getContext().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            minTJMoney = sp.getInt("minTJMoney", 0);
            tjMoneyMsg = sp.getString("tjMoneyMsg", "");
            sfcState = sp.getInt(LotCode.SFC_CODE,1);
            r9cState = sp.getInt(LotCode.R9C_CODE,1);
            buyPrivilege = SpUtils.getBuyPrivilege();
            initView();
            setEndDate();
        }
        checkLottery();
        mChooseFormAdapter = new ChooseFormAdapter();
        mChooseFormAdapter.setData(getContext(), dataList, new ChooseFormAdapter.OnClickSfcListener() {
            @Override
            public void onClickSfcListener(HashMap<SfcAndRjcData.DataBean, HashMap<String, String>> clickMap) {
                mChoosedContentMap = clickMap;
                setStake();
            }
        });

        mPullListView.setAdapter(mChooseFormAdapter);
        //addData();
        addListener();
        return mInflate;
    }

    private void checkLottery() {
        if ("11".equals(mLotcode)) {
            minTimes = 14;
        } else {
            minTimes = 9;
        }
    }

    private void initView() {
        mGroup = (TextView) mInflate.findViewById(R.id.min_games);
        mGroup.setText("已选0场");

        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mSssss = (LinearLayout) mInflate.findViewById(R.id.sssss);
        mClear = (LinearLayout) mInflate.findViewById(R.id.clear);
        mTextEnsure = (TextView) mInflate.findViewById(R.id.ensure);
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        mTextDate = (TimeTextView) mInflate.findViewById(R.id.timeTextView);
        mTextEndDate = (TextView) mInflate.findViewById(R.id.text_lottery_date);
        mPullListView = (ListView) mInflate.findViewById(R.id.pull_listview);
        line = mInflate.findViewById(R.id.linesfc);
        line.setVisibility(View.GONE);
        mShake = (LinearLayout) mInflate.findViewById(R.id.ll_shake);
        mShake.setVisibility(View.GONE);

        if (buyPrivilege == 1) {
            mSssss.setVisibility(View.GONE);
        } else {
            mSssss.setVisibility(View.VISIBLE);
        }

    }

    private void buyInitView() {
        //dialog的布局文件
        inflate = View.inflate(getContext(), R.layout.ball_buyform_dialog, null);
        view = inflate.findViewById(R.id.view);
        final LinearLayout mLl_middle= (LinearLayout) inflate.findViewById(R.id.ll_middle);
        buyViewDialog = new AlertDialog.Builder(getContext(), R.style.HintDialog).create();
        buyViewDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                choosedContentFormList.clear();
            }
        });
        mAddImage = (ImageView) inflate.findViewById(R.id.image_add_bet);
        mClearImage = (ImageView) inflate.findViewById(R.id.image_clear_bet);
        LinearLayout mLlAddBet = (LinearLayout) inflate.findViewById(R.id.ll_add_bet);
        LinearLayout mLlAddBetAuto = (LinearLayout) inflate.findViewById(R.id.ll_add_bet_auto);
        LinearLayout mLlClearBet = (LinearLayout) inflate.findViewById(R.id.ll_clear_bet);
        mLlAddBet.setVisibility(View.GONE);
        mLlAddBetAuto.setVisibility(View.GONE);
        mLlClearBet.setVisibility(View.GONE);
        mAddImage.setVisibility(View.VISIBLE);
        mClearImage.setVisibility(View.VISIBLE);
        mMultiEt = (EditText) inflate.findViewById(R.id.edit_buy_times);
        mMultiEt.setText("5");
        mListViewContent = (ListView) inflate.findViewById(R.id.lv_select_bets);
        mTv_betCounts = (TextView) inflate.findViewById(R.id.tv_zhushu);
        mTv_money = (TextView) inflate.findViewById(R.id.tv_money);
        mBuyBtn = (Button) inflate.findViewById(R.id.btn_bet_submit);
        mHemai = (ImageView) inflate.findViewById(R.id.img_hemai);
        mGendan = (Button) inflate.findViewById(R.id.recommend_btn);

       /* if (LotteryApp.recommendPrivilege == 2) {
            mGendan.setVisibility(View.GONE);
        } else {
            mGendan.setVisibility(View.VISIBLE);
        }*/
        mGendan.setVisibility(View.VISIBLE);
      /*  if (LotteryApp.buyPrivilege == 2) {
            mBuyBtn.setText("暂停销售");
            mBuyBtn.setEnabled(false);
        } else {
            mBuyBtn.setText("立即预约");
            mBuyBtn.setEnabled(true);
        }*/
        mLl_buy_bttomview = (LinearLayout) inflate.findViewById(R.id.ll_buy_bttomview);
        mLl_mutile = (LinearLayout) inflate.findViewById(R.id.ll_mutile);
        mLl_xieyi = (LinearLayout) inflate.findViewById(R.id.ll_xieyi);
        mMutile10 = (TextView) inflate.findViewById(R.id.mutile10);
        mMutile20 = (TextView) inflate.findViewById(R.id.mutile20);
        mMutile50 = (TextView) inflate.findViewById(R.id.mutile50);
        mMutile100 = (TextView) inflate.findViewById(R.id.mutile100);
        mLlZhuijia = (LinearLayout) inflate.findViewById(R.id.rr_zhuijia);
        mLlZhuijia.setVisibility(View.GONE);
        LinearLayout llstop = (LinearLayout) inflate.findViewById(R.id.ll_stop);
        llstop.setVisibility(View.GONE);
        TextView lianxu = (TextView) inflate.findViewById(R.id.lianxu);
        lianxu.setVisibility(View.GONE);
        EditText issues = (EditText) inflate.findViewById(R.id.edit_buy_issue);
        issues.setVisibility(View.GONE);
        TextView qi = (TextView) inflate.findViewById(R.id.qi);
        qi.setVisibility(View.GONE);
        mLl_buy_bttomview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mLl_buy_bttomview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height2 = getResources().getDimensionPixelSize(R.dimen.footer_heigh);
                int height3 = height2;
                int height5=getResources().getDimensionPixelSize(R.dimen.footer_buy);
                int height4 = view.getHeight();
                int displayHeight = DisplayUtil.getDisplayHeight();
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        displayHeight-height2-height3-height4-height5,1.0f);

                mLl_middle.setLayoutParams(lp);
            }
        });
        buyAddListener();
    }

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(getActivity());
        loadingDialogUtils.setLoadingText("正在努力加载中......");
        HttpInterface2 mHttpInterface = new HttpUtils2(getActivity());
        Bundle params = new Bundle();
        params.putString("lotCode", mLotcode);
        params.putString("issueNo", issue);
        mHttpInterface.get(Url.SFC_RJC_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                SfcAndRjcData sfcAndRjcData = gson.fromJson(result, SfcAndRjcData.class);

                if (sfcAndRjcData.getCode() == 0) {
                    dataList.clear();
                    dataList.addAll(sfcAndRjcData.getData());
                    // mPullListView.setAdapter(mChooseFormAdapter);
                    mChooseFormAdapter.notifyDataSetChanged();
                }
                if (dataList != null && dataList.size() != 0) {
                    mPullListView.setVisibility(View.VISIBLE);
                    mNoData.setVisibility(View.GONE);
                } else {
                    mPullListView.setVisibility(View.GONE);
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                if (dataList != null && dataList.size() != 0) {
                    mPullListView.setVisibility(View.VISIBLE);
                    mNoData.setVisibility(View.GONE);
                } else {
                    mPullListView.setVisibility(View.GONE);
                    mNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addListener() {
        //清空所有选中的球
        mClear.setOnClickListener(new ClearOnClickListener());
        //点击确定按钮
        if ((LotCode.R9C_CODE.equals(mLotcode)&&r9cState==0)||
                (LotCode.SFC_CODE.equals(mLotcode)&&sfcState==0)){
            mTextEnsure.setText("停售");
            mTextEnsure.setBackgroundColor(getContext().getResources().getColor(R.color.gray_deep));
        }else {
            //点击确定按钮
            mTextEnsure.setOnClickListener(new EnsureOnClickListener());
            mTextEnsure.setText("确定");
            mTextEnsure.setBackgroundColor(getContext().getResources().getColor(R.color.red_theme));
        }

        //下拉刷新
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                dataList.clear();
                clearColletion();
                addData();
            }
        });
    }

    private void buyAddListener() {
        //点击付款按钮
        mBuyBtn.setOnClickListener(new BuyBtnOnClickListener());
        //点击的添加更多
        mAddImage.setOnClickListener(new AddOnClickListener());
        view.setOnClickListener(new AddOnClickListener());
        //点击清空列表
        mClearImage.setOnClickListener(new ClearFormOnClickListener());
        //投注倍数的监控
        mMultiEt.addTextChangedListener(new EditChangeListener());
        //合买
        mHemai.setOnClickListener(HemaiListener);
        //跟单
        mGendan.setOnClickListener(GendanListener);
        //协议
        mLl_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ServiceAgreementActivity.class));
            }
        });
        //监听软键盘的弹出和隐藏
        inflate.addOnLayoutChangeListener(this);
        mMutile10.setOnClickListener(click);
        mMutile20.setOnClickListener(click);
        mMutile50.setOnClickListener(click);
        mMutile100.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mutile10:
                    mMultiEt.setText("20");
                    mMultiEt.setSelection(2);
                    break;
                case R.id.mutile20:
                    mMultiEt.setText("50");
                    mMultiEt.setSelection(2);
                    break;
                case R.id.mutile50:
                    mMultiEt.setText("100");
                    mMultiEt.setSelection(3);
                    break;
                case R.id.mutile100:
                    mMultiEt.setText("200");
                    mMultiEt.setSelection(3);
                    break;
            }
        }
    };

    private void setStake() {
        mGroup.setText("已选" + mChoosedContentMap.size() + "场");
    }

    //本期截止时间
    private void setEndDate() {
        final HttpInterface2 mHttpInterface = new HttpUtils2(getActivity());
        mHttpInterface.jsonByUrl(Url.END_DATE_URL + "?lotCode=" + mLotcode, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                mEndDateData = gson.fromJson(result, EndDateData.class);
                if (mEndDateData.getCode() == 0) {
                    issue = mEndDateData.getData().getIssueNo();
                    addData();
                    mHttpInterface.jsonByUrl(Url.SERVER_DATE_URL, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            //获取服务器时间
                            Gson gson = new Gson();
                            ServerDateData serverDateData = gson.fromJson(result, ServerDateData.class);
                            if (serverDateData.getCode() == 0) {
                                mTextEndDate.setText("距" + mEndDateData.getData().getIssueNo() + "期截止:");
                                long seDsendtime = Long.parseLong(mEndDateData.getData().getStopSaleTime());
                                //服务器时间
                                long data = serverDateData.getData();
                                long endTime = seDsendtime - data;
                                int[] times = DateFormtUtils.obtainTime(endTime);
                                mTextDate.setTimes(times);
                                mTextDate.setListener(new TimeTextView.endTimeListener() {
                                    @Override
                                    public void callback() {
                                        setEndDate();
                                    }
                                });
                                if (!mTextDate.isRun()) {
                                    mTextDate.run();
                                }
                            }
                        }

                        @Override
                        public void onError() {
                            mNoData.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                mNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    View.OnClickListener HemaiListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LotteryApp.isLogin) {
                if ("".equals(mMultiEt.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    HemaiView mHemaiView = new HemaiView(getContext());
                    //View view = View.inflate(mContext, R.layout.dialog_hemai_layout, null);
                    final AlertDialog hemaiDialog = new AlertDialog.Builder(getContext()).create();
                    mHemaiView.setData(shakes, mMulti, new HemaiView.OnClickComit() {
                        //str1提成，str2认购，str3保底
                        @Override
                        public void onClick(String str1, String str2, String str3, int type) {
                            hemaiDialog.dismiss();
                            if (type == 0) {
                                return;
                            }
                            //提交订单
                            hemaiBuy(str1, str2, str3, type, true);
                        }
                    });
                    hemaiDialog.setView(mHemaiView);
                    hemaiDialog.show();
                }
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        }
    };
    View.OnClickListener GendanListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            // inflate.setFocusable(false);
            if (LotteryApp.isLogin) {
                if ("".equals(mMultiEt.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else if (shakes * mMulti * 2 < minTJMoney) {
                    HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(getContext());
                    hintDialogUtils2.setMessage(tjMoneyMsg);
                    hintDialogUtils2.setTitle("温馨提示");
                    hintDialogUtils2.setTitleVisiable(true);
                } else {
                    GendanView mGendanView = new GendanView(getContext());
                    //View view = View.inflate(mContext, R.layout.dialog_hemai_layout, null);
                    final Dialog gendanDialog = new Dialog(getContext(), R.style.ActionSheetDialogStyle);
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

    class ClearOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clearColletion();
            setStake();
            mChooseFormAdapter.notifyDataSetChanged();

        }
    }

    private void clearColletion() {
        mGroup.setText("已选0场");
        if (mMultiEt != null)
            mMultiEt.setText(5 + "");
        mChoosedContentMap.clear();
        choosedContentFormList.clear();
    }

    private boolean isFirst = true;

    class EnsureOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (mChoosedContentMap.size() >= minTimes) {
                if (isFirst) {
                    buyInitView();
                    isFirst = false;
                }
                showDialog();
                //将点击选中的对象组成的集合进行转换成列表展示需要用到的集合。
                sdfsd();
                //Log.e("选择结果是：", choosedContentFormList.toString());
                //填充选中后按确定按钮的listview
                mChooseContentAdapter = new ChooseContentAdapter(mChoosedContentMap, mDeleteItemListener, mOnClickSfcListener);
                mListViewContent.setAdapter(mChooseContentAdapter);
                setStakeCount();
                //填充注数和钱数
                setStakesAndMoney();
            } else {
                Toast.makeText(getContext(), "请至少选择" + minTimes + "场比赛", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //选中之后点击确定按钮之后，在选中的列表上，执行点击事件后的回调
    ChooseFormAdapter.OnClickSfcListener mOnClickSfcListener = new ChooseFormAdapter.OnClickSfcListener() {
        @Override
        public void onClickSfcListener(HashMap<SfcAndRjcData.DataBean, HashMap<String, String>> clickMap) {
            mChoosedContentMap = clickMap;
            // mChooseContentAdapter.notifyDataSetChanged();
           /* mChooseContentAdapter = new ChooseContentAdapter(mChoosedContentMap, mDeleteItemListener, mOnClickSfcListener);
            mListViewContent.setAdapter(mChooseContentAdapter);*/
            setStake();
            sdfsd();
            mChooseFormAdapter.notifyDataSetChanged();
            //如果选中的项目减少了，不到至少选择的数量，那么直接让注数量归零.然后跳出
            if (mChoosedContentMap.size() < minTimes) {
                shakes = 0;
                setStakesAndMoney();
                return;
            }
            setStakeCount();
            setStakesAndMoney();
        }
    };
    ChooseContentAdapter.DeleteItemListener mDeleteItemListener = new ChooseContentAdapter.DeleteItemListener() {
        @Override
        public void deletItem(int position) {

            //删除条目的同时还需要移除已经选中的相应的条目。
          /*  for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
                if (entry.getKey().getMid().equals(choosedContentFormList.get(position).get(0).getMid())) {
                    mChoosedContentMap.remove(entry.getKey());
                }
            }*/
            //当就剩两条数据的时候再删除让这个购买的视图消失
            if (choosedContentFormList.size() <= minTimes) {
                clearColletion();
                mChooseFormAdapter.notifyDataSetChanged();
                buyViewDialog.dismiss();
                setStake();
                flag = false;
                return;
            }
            Iterator<Map.Entry<SfcAndRjcData.DataBean, HashMap<String, String>>> it = mChoosedContentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<SfcAndRjcData.DataBean, HashMap<String, String>> entry = it.next();
                if ((entry.getKey().getGameId() + "").equals(choosedContentFormList.get(position).get(0).getMid())) {
                    it.remove();
                }
            }
            mChooseContentAdapter = new ChooseContentAdapter(mChoosedContentMap, mDeleteItemListener, mOnClickSfcListener);
            mListViewContent.setAdapter(mChooseContentAdapter);
            setStake();
            choosedContentFormList.remove(position);
            // mChooseContentAdapter.notifyDataSetChanged();
            mChooseFormAdapter.notifyDataSetChanged();
            setStakeCount();
            setStakesAndMoney();
        }
    };

    private void setStakeCount() {
        shakes = 0;
        //将每一场比赛选中的项数放入一个集合中
        ArrayList<Integer> mList = new ArrayList<>();
        for (int i = 0; i < choosedContentFormList.size(); i++) {
            mList.add(choosedContentFormList.get(i).size());
        }
        for (List<Integer> list : Combination.of(mList, minTimes)) {
            int ss = 1;
            for (int j = 0; j < list.size(); j++) {
                ss = ss * list.get(j);
            }
            shakes = shakes + ss;
        }
    }

    private void setStakesAndMoney() {
        mTv_betCounts.setText("共" + shakes + "注");
        //mMulti = Integer.parseInt(mBuyBottomView.mEdit_buy_times.getText().toString());
        mTv_money.setText("合计：" + shakes * mMulti * 2 + "元");
    }

    class BuyBtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

          /*  if (mPlayMethodBunchs) {
                spliteStr = SpliceCtr.spliteStr1(choosedContentFormList, danTuoList);
            } else {
                spliteStr = SpliceCtr.spliteStr(choosedContentFormList, danTuoList);
            }*/
            if (choosedContentFormList.size() < minTimes) {
                Toast.makeText(getContext(), "请选择" + minTimes + "场比赛", Toast.LENGTH_SHORT).show();
                return;
            }
            //需要判断是否为登录状态，如果不是就跳转到登录界面
            if (LotteryApp.isLogin) {
                if (!LotteryApp.phoneFlag) {
                    HintDialogUtils.setHintDialog(getContext());
                    HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                    HintDialogUtils.setTitleVisiable(true);
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                        @Override
                        public void callBack(View view) {
                            //手机号未绑定
                            if (LotteryApp.isThird) {
                                Intent intent = new Intent(getContext(), PhoneIsExistActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getContext(), BindPhoneActivity.class);
                                intent.putExtra("isBind", false);
                                startActivity(intent);
                            }

                        }
                    });
                    return;
                }
                if ("".equals(mMultiEt.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    String buyStr = splitBuyStr();
                    Bundle bundle = new Bundle();
                    bundle.putInt("requestCode", 200);
                    bundle.putString("buyMethod", "normal");
                    bundle.putString("content", buyStr);
                    bundle.putInt("shakes", shakes);
                    bundle.putInt("mMulti", mMulti);
                    bundle.putString("money", shakes * mMulti * 2 + "");
                    bundle.putString("issue", issue);
                    bundle.putString("lotcode", mLotcode);
                    NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
                    netWorkData.orderForm();
                }
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        }
    }

    class AddOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            buyViewDialog.dismiss();
            choosedContentFormList.clear();
        }
    }

    class ClearFormOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clearColletion();
            mChooseFormAdapter.notifyDataSetChanged();
            // mChooseContentAdapter.notifyDataSetChanged();
            buyViewDialog.dismiss();
            setStake();
        }
    }

    class EditChangeListener implements TextWatcher {

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
                mMultiEt.setText("50000");
                mMultiEt.setSelection("50000".length());
                ToastUtils.showToast(getContext(), "最大输入50000");
            } else if (length > 1 && Integer.parseInt(String.valueOf(s)) == 0) {
                mMultiEt.setText("0");
                mMulti = 0;
            } else {
                mMulti = Integer.parseInt(String.valueOf(s));
            }
            setStakesAndMoney();
        }
    }

    private String splitBuyStr() {
        StringBuilder mStrBuilder = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            if (mChoosedContentMap.containsKey(dataList.get(i))) {
                HashMap<String, String> mMap = mChoosedContentMap.get(dataList.get(i));
                for (Map.Entry<String, String> entry : mMap.entrySet()) {
                    switch (entry.getKey()) {
                        case "主胜":
                            mStrBuilder.append("3");
                            break;
                        case "平":
                            mStrBuilder.append("1");
                            break;
                        case "主负":
                            mStrBuilder.append("0");
                            break;
                    }
                }
            } else {
                mStrBuilder.append("#");
            }
            if (i == 13) {
                mStrBuilder.append(":1:1");
            } else {
                mStrBuilder.append(",");
            }
        }
        return String.valueOf(mStrBuilder);
    }

    private void showDialog() {
        // buyViewDialog.setCanceledOnTouchOutside(false);
        Window window = buyViewDialog.getWindow();
        window.setSoftInputMode(2);
        buyViewDialog.setView(inflate);
        buyViewDialog.show();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        //layoutParams.width = window.getWindowManager().getDefaultDisplay().getWidth();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
    }

    private void loading() {
        mLoadingDialog = new LoadingDialog(getContext());
        mLoadingDialog.show();
        WindowManager.LayoutParams lp = mLoadingDialog.getWindow().getAttributes();
        lp.alpha = 0.8f;
        mLoadingDialog.getWindow().setAttributes(lp);
    }

    //将点击选中的对象组成的集合进行转换。
    private void sdfsd() {
        choosedContentFormList.clear();
        //遍历外层的hashmap
        for (Map.Entry<SfcAndRjcData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            HashMap<String, String> value = entry.getValue();
            //遍历内层的hashmap
            String awary = entry.getKey().getTeamName1();
            String home = entry.getKey().getTeamName0();
            int oddId = entry.getKey().getGameId();
            ArrayList<ChoosedContentFormBean> list = new ArrayList<>();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                ChoosedContentFormBean mContentBean = new ChoosedContentFormBean();
                mContentBean.setAwary(awary);
                mContentBean.setHome(home);
                mContentBean.setMid(oddId + "");
                mContentBean.setContent(entry1.getKey());
                mContentBean.setOdds(entry1.getValue());
                list.add(mContentBean);
            }
            choosedContentFormList.add(list);
        }
    }

    private void hemaiBuy(String str1, String str2, String str3, int type, boolean ghFlag) {
        if (!LotteryApp.phoneFlag) {
            HintDialogUtils.setHintDialog(getContext());
            HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
            HintDialogUtils.setTitleVisiable(true);
            HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                @Override
                public void callBack(View view) {
                    //手机号未绑定
                    if (LotteryApp.isThird) {
                        Intent intent = new Intent(getContext(), PhoneIsExistActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), BindPhoneActivity.class);
                        intent.putExtra("isBind", false);
                        startActivity(intent);
                    }

                }
            });
            return;
        }
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
        String buyStr = splitBuyStr();
        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", 200);
        bundle.putString("buyMethod", "hemai");
        bundle.putString("content", buyStr);
        bundle.putInt("shakes", shakes);
        bundle.putInt("mMulti", mMulti);
        bundle.putString("money", shakes * mMulti * 2 + "");
        bundle.putString("issue", issue);
        bundle.putString("lotcode", mLotcode);
        bundle.putString("order_type", order_type + "");
        bundle.putString("wrate", wrate);
        bundle.putString("bnum", bnum);
        bundle.putString("pnum", pnum);
        bundle.putString("type", type + "");
        bundle.putString("desc", desc);
        NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
        netWorkData.orderForm();

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > DisplayUtil.getDisplayHeight() / 3)) {
            mLl_buy_bttomview.setVisibility(View.INVISIBLE);
            mLl_mutile.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > DisplayUtil.getDisplayHeight() / 3)) {
            mLl_buy_bttomview.setVisibility(View.VISIBLE);
            mLl_mutile.setVisibility(View.INVISIBLE);
            //Toast.makeText(getContext(), "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }
    }

    public void paymentFinish() {
        //冲购买页面支付完成后返回回来
        //购买成功后清空所有,点击继续购彩
        clearColletion();
        mChooseFormAdapter.notifyDataSetChanged();
        //将这个列表视图取消
        buyViewDialog.dismiss();
        setStake();
    }
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            //冲购买页面支付完成后返回回来
            //购买成功后清空所有,点击继续购彩
            clearColletion();
            mChooseFormAdapter.notifyDataSetChanged();
            //将这个列表视图取消
            buyViewDialog.dismiss();
            setStake();
        }
    }*/
}
