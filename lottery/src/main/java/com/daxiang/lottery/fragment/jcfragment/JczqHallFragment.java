package com.daxiang.lottery.fragment.jcfragment;

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
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BonusOptimizeActivity;
import com.daxiang.lottery.activity.ForecastActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.CardActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.activity.lotteryactivity.ServiceAgreementActivity;
import com.daxiang.lottery.adapter.jcadapter.JcChooseFormAdapter;
import com.daxiang.lottery.adapter.jcadapter.JczqHhMatchAdapter;
import com.daxiang.lottery.adapter.jcadapter.JczqMatchAdapter;
import com.daxiang.lottery.adapter.jcadapter.JczqOtherAdapter;
import com.daxiang.lottery.adapter.jcadapter.RecyclerViewAdapter;
import com.daxiang.lottery.common.BuyCondition;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.common.MaxBunch;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.common.SpliceCtr;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.OnClickJczqListener;
import com.daxiang.lottery.cxinterface.SiftInterface;
import com.daxiang.lottery.dialog.LoadingDialog;
import com.daxiang.lottery.dialog.SiftDialog;
import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.utils.BunchMethod;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.ToastUtils;
import com.daxiang.lottery.utils.dialogutils.DialogAnimotion;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.jcmath.Combination;
import com.daxiang.lottery.utils.jcmath.TheoryBonusMath;
import com.daxiang.lottery.view.FloatViewLayout;
import com.daxiang.lottery.view.GendanView;
import com.daxiang.lottery.view.HemaiView;
import com.daxiang.lottery.view.JcBuyBottomView;
import com.daxiang.lottery.view.JcTitleBar;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.autotextview.AutofitTextView;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class JczqHallFragment extends Fragment implements View.OnLayoutChangeListener {
    private View mInflate;
    private View inflate;
    private String lotcode;
    private LinearLayout mClear;
    private AutofitTextView mRemark;
    private TextView mMinGames;
    private TextView mBonuses;
    private TextView mEnsure;
    private ImageView mForecast;
    private FloatViewLayout mFvl;
    private JcTitleBar mTitleBar;
    private LinearLayout mBonusOptimize;
    private ListView mChooseFormListView;
    private NoDataView mNoData;
    // private LinearLayout mBuyView;
    private ImageView mAddForm;
    private ImageView mClearForm;
    // private LinearLayout mChooseView;
    private JcBuyBottomView mBuyBottomView;
    private TextView mTv_zhushu;
    private ImageView mImg_hemai;
    private LinearLayout mImg_gendan;
    private Button mBtn_bet_submit;
    //下面这两个是为了弹出软件盘时控制他们的显隐
    private LinearLayout mLl_buy_bttomview;
    private LinearLayout mLl_mutile;
    private LinearLayout mLl_xieyi;
    private TextView mMutile10;
    private TextView mMutile20;
    private TextView mMutile50;
    private TextView mMutile100;
    //总投注数
    int shakes;
    int n = 1;
    //总投钱数
    int allMoney;
    //日期
    private SmartRefreshLayout mRefresh;
    private ExpandableListView mListView;
    //玩法
    private int playMethod;
    //理论奖金的最大值和最小值
    private double maxTheoryBonus = 0;
    private double minTheoryBonus = 0;
    private JcChooseFormAdapter mJcChooseFormAdapter;
    BaseExpandableListAdapter mJczq1Adapter;
    private int mMulti = 5;//默认5倍
    private boolean flag = false;
    private boolean mPlayMethodBunchs;
    private boolean siftFlag = true;
    private LoadingDialog mLoadingDialog;
    HttpInterface2 mHttpInterface;
    //在选择混合过关的时候判断是否可以是单关。
    private boolean isDg = false;
    private int minTJMoney;//最小发单金额
    private String tjMoneyMsg;//最小发单金额说明
    private int displayWidth;
    private int buyPrivilege;
    private int jczqState;

    public void setData(String lotcode, boolean mPlayMethodBunchs, JcTitleBar mTitleBar) {
        this.lotcode = lotcode;
        this.mPlayMethodBunchs = mPlayMethodBunchs;
        this.mTitleBar = mTitleBar;
        if (!mPlayMethodBunchs) {
            playMethod = 1;
        } else {
            playMethod = 6;
        }
    }

    public void setData(int playMethod, boolean mPlayMethodBunchs) {
        this.playMethod = playMethod;
        this.mPlayMethodBunchs = mPlayMethodBunchs;
        map.clear();
        mListData.clear();
        clearColletion();
        selectAdapter();
        addData();
    }

    RecyclerViewAdapter mAdapter;
    //
    public Activity mContext;
    public HashMap<String, ArrayList<JczqData.DataBean>> map = new HashMap<>();
    public ArrayList<ArrayList<JczqData.DataBean>> mListData = new ArrayList<>();
    //用于筛选
    public ArrayList<ArrayList<JczqData.DataBean>> mLists = new ArrayList<>();
    ArrayList<String> siftList = new ArrayList<>();
    //选择的场次
    HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentMap = new HashMap<>();
    //用于存放显示在选中列表所需要的内容填充的实体类
    ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList = new ArrayList<>();
    //用于存放选中的串的编号。
    HashMap<Integer, Integer> mBunchMap = new HashMap<>();
    //存放格式化的串。例如2*1
    ArrayList<String> mBunchList = new ArrayList<>();
    //notifi串的recyclerview用
    HashMap<Integer, Integer> mRvMap = new HashMap<>();
    //设的胆，存的是期号
    ArrayList<String> danTuoList = new ArrayList<>();
    //设的胆，键为position值为期号
    HashMap<Integer, String> danTuoMap = new HashMap<>();
    AlertDialog buyViewDialog;
    private View view;
    private MaxBunch maxBunch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_hall_jczq, null);
            SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            minTJMoney = sp.getInt("minTJMoney", 0);
            tjMoneyMsg = sp.getString("tjMoneyMsg", "");
            jczqState = sp.getInt(LotCode.JCZQ_CODE,1);
            buyPrivilege = SpUtils.getBuyPrivilege();
            initViews();
            mRefresh.autoRefresh();//走刷新的回调。回调里面有加载数据
            //  addData();
        }
        displayWidth = DisplayUtil.getDisplayWidth();
        // clearColletion();
        // mListData.clear();
        selectAdapter();
        // addData();
        maxBunch = new MaxBunch();
        addListener();
        return mInflate;
    }

    private void selectAdapter() {
        if (playMethod == LotteryTypes.HH) {
            setHhMatchAdapter();
        } else if (playMethod == LotteryTypes.SPF || playMethod == LotteryTypes.RQSPF) {
            setMatchAdapter();
        } else {
            setOtherAdapter();
        }
        for (int i = 0; i < mListData.size(); i++) {
            mListView.expandGroup(i);
        }
    }

    //混合过关适配器
    private void setHhMatchAdapter() {
        mJczq1Adapter = new JczqHhMatchAdapter();
        mListView.setAdapter(mJczq1Adapter);
        ((JczqHhMatchAdapter) mJczq1Adapter).setData(mContext, mChoosedContentMap,mListData, lotcode, new OnClickJczqListener() {
            @Override
            public void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap) {
                //每次点击都会回调过来点击的条目信息
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    //让球胜平负和胜平负过关适配器
    private void setMatchAdapter() {
        mJczq1Adapter = new JczqMatchAdapter();
        mListView.setAdapter(mJczq1Adapter);

        ((JczqMatchAdapter) mJczq1Adapter).setData(mContext, mListData, mPlayMethodBunchs, playMethod, new OnClickJczqListener() {
            @Override
            public void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap) {
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    //半全场，进球数，比分过关适配器
    private void setOtherAdapter() {
        mJczq1Adapter = new JczqOtherAdapter();
        mListView.setAdapter(mJczq1Adapter);
        ((JczqOtherAdapter) mJczq1Adapter).setData(mContext, mListData, mPlayMethodBunchs, playMethod, new OnClickJczqListener() {
            @Override
            public void onClickJcListener(HashMap<JczqData.DataBean, HashMap<String, String>> clickMap) {
                mChoosedContentMap = clickMap;
                setStake();
            }
        });
    }

    private void initViews() {
        mListView = (ExpandableListView) mInflate.findViewById(R.id.lv_jczq);
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mListView.setGroupIndicator(null);
        mClear = (LinearLayout) mInflate.findViewById(R.id.clear);
        mRemark = (AutofitTextView) mInflate.findViewById(R.id.remarkjc);
        mMinGames = (TextView) mInflate.findViewById(R.id.min_games);
        mEnsure = (TextView) mInflate.findViewById(R.id.ensure);
        mForecast = (ImageView) mInflate.findViewById(R.id.forecast);
        mFvl = (FloatViewLayout) mInflate.findViewById(R.id.floatViewLayout);
        LinearLayout mLl_bottom = (LinearLayout) mInflate.findViewById(R.id.ll_bottom);
        RelativeLayout mRl_bottom = (RelativeLayout) mInflate.findViewById(R.id.rl_bottom);

        if (buyPrivilege == 1) {
            mLl_bottom.setVisibility(View.GONE);
            mRl_bottom.setVisibility(View.GONE);
        } else {
            mLl_bottom.setVisibility(View.VISIBLE);
            mRl_bottom.setVisibility(View.VISIBLE);
        }

        mHttpInterface = new HttpUtils2(mContext);
        if (DateFormtUtils.isTimeRange(true)) {//售票期间
            String string = mContext.getResources().getString(R.string.buy_jz_time_remark);
            String introduce = mContext.getResources().getString(R.string.jz_dian_remark);
            String remark = "1." + string + "\n2." + introduce;
            mRemark.setText(remark);
        } else {//竞彩官网没有开售
            String string = getString(R.string.nobuy_time_remark);
            String introduce = mContext.getResources().getString(R.string.jz_dian_remark);
            String remark = "1." + string + "\n2." + introduce;
            mRemark.setText(remark);
        }
    }

    private void buyInitView() {
        inflate = View.inflate(mContext, R.layout.buy_view, null);
        final LinearLayout mLl_middle= (LinearLayout) inflate.findViewById(R.id.ll_middle);
        mChooseFormListView = (ListView) inflate.findViewById(R.id.lv_jczq_form);
        mClearForm = (ImageView) inflate.findViewById(R.id.image_clear_bet);
        mAddForm = (ImageView) inflate.findViewById(R.id.image_add_bet);
        mBonuses = (TextView) inflate.findViewById(R.id.tv_max_bonuses);
        mBonusOptimize = (LinearLayout) inflate.findViewById(R.id.btn_bonus_optimize);
        mBuyBottomView = (JcBuyBottomView) inflate.findViewById(R.id.jc_buy_bottom);
        mLl_buy_bttomview = (LinearLayout) inflate.findViewById(R.id.ll_buy_bttomview);
        mLl_mutile = (LinearLayout) inflate.findViewById(R.id.ll_mutile);
        mMutile10 = (TextView) inflate.findViewById(R.id.mutile10);
        mMutile20 = (TextView) inflate.findViewById(R.id.mutile20);
        mMutile50 = (TextView) inflate.findViewById(R.id.mutile50);
        mMutile100 = (TextView) inflate.findViewById(R.id.mutile100);
        mTv_zhushu = (TextView) inflate.findViewById(R.id.tv_zhushu);
        mImg_hemai = (ImageView) inflate.findViewById(R.id.img_hemai);
        mImg_gendan = (LinearLayout) inflate.findViewById(R.id.ll_recommend);
        mBtn_bet_submit = (Button) inflate.findViewById(R.id.btn_bet_submit);
        mLl_xieyi = (LinearLayout) inflate.findViewById(R.id.ll_xieyi);
        view = inflate.findViewById(R.id.view);


        mLl_buy_bttomview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mLl_buy_bttomview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height1 = mBuyBottomView.getHeight();
                int height2 = getResources().getDimensionPixelSize(R.dimen.footer_heigh);
                int height3 = height2+DisplayUtil.dip2px(40);
                int height4 = view.getHeight();
                int displayHeight = DisplayUtil.getDisplayHeight();
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        displayHeight-height1-height2-height3-height4,1.0f);

                mLl_middle.setLayoutParams(lp);
            }
        });


       /* if (LotteryApp.recommendPrivilege == 2) {
            mImg_gendan.setVisibility(View.GONE);
            mBuyBottomView.setOne();
        } else {
            mImg_gendan.setVisibility(View.VISIBLE);
            mBuyBottomView.setTwo();
        }*/
        mImg_gendan.setVisibility(View.VISIBLE);
        mBuyBottomView.setTwo();
      /*  if (LotteryApp.buyPrivilege == 2) {
            mBtn_bet_submit.setText("暂停销售");
            mBtn_bet_submit.setEnabled(false);
        } else {
            mBtn_bet_submit.setText("立即预约");
            mBtn_bet_submit.setEnabled(true);
        }*/
        buyViewDialog = new AlertDialog.Builder(mContext, R.style.HintDialog).create();

        buyViewDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                buyViewDialog.dismiss();
                choosedContentFormList.clear();
                danTuoList.clear();
                danTuoMap.clear();
                mBuyBottomView.mLlBunch.setVisibility(View.GONE);
                mBuyBottomView.mLine_Bunch.setVisibility(View.GONE);
                flag = false;
            }
        });
        buyAddListener();
    }

    private void buyAddListener() {
        //点击付款按钮
        mBtn_bet_submit.setOnClickListener(BuyBtnClick);
        //清除列表
        mClearForm.setOnClickListener(ClearFormClick);
        //添加更多
        mAddForm.setOnClickListener(AddClick);
        //投注倍数edittext
        mBuyBottomView.mEdit_buy_times.addTextChangedListener(MultiClick);
        //串数
        mBuyBottomView.mText_buy_types.setOnClickListener(BunchClick);
        //合买
        mImg_hemai.setOnClickListener(HemaiClickListener);
        //推荐
        mImg_gendan.setOnClickListener(GendanClickListener);
        //奖金优化
        mBonusOptimize.setOnClickListener(OptimizeClickListener);
        //
        view.setOnClickListener(AddClick);
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

    private void addData() {
        //    final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        //    loadingDialogUtils.setLoadingText("正在努力加载中......");
        siftFlag = true;
        //mListView.setRefreshing(true);
        // mLoadingDialog.setLoadingText("正努力在加载，请稍候！");
        if (!NetworkUtils.isNetworkAvailable(mContext)) {
            HintDialogUtils.setHintDialog(mContext);
            HintDialogUtils.setMessage("您的网络未连接，请连接后重试！");
            mRefresh.finishRefresh();
            mNoData.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            //mLoadingDialog.dismiss();
            return;
        }
        final Bundle params = new Bundle();
        params.putString("lotCode", lotcode);
        params.putString("status", "100");
        mHttpInterface.get(Url.JCZQ_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                //取消加载条
                //   loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                JczqData jczqData = gson.fromJson(result, JczqData.class);
                if (jczqData.getCode() == 0) {
                    List<JczqData.DataBean> data = jczqData.getData();
                    //用于排序
                    ArrayList<String> orderList = new ArrayList<String>();
                    for (int i = 0; i < data.size(); i++) {
                        ArrayList<JczqData.DataBean> list;
                        if (map.containsKey(data.get(i).getIssue())) {
                            list = map.get(data.get(i).getIssue());
                        } else {
                            list = new ArrayList<>();
                        }
                        list.add(data.get(i));
                        map.put(data.get(i).getIssue(), list);
                        if (!orderList.contains(data.get(i).getIssue())) {
                            orderList.add(data.get(i).getIssue());
                        }
                    }
                    for (int i = 0; i < orderList.size(); i++) {
                        mListData.add(map.get(orderList.get(i)));
                    }
                    mLists.clear();
                    mLists.addAll(mListData);
                    //如果为单关需要循环看一下比赛是否支持单关。将不支持的移除
                    deletenNoExist();
                    //看是否有数据
                    if (mListData != null && mListData.size() != 0) {
                        mListView.setVisibility(View.VISIBLE);
                        mNoData.setVisibility(View.GONE);
                    } else {
                        mListView.setVisibility(View.GONE);
                        mNoData.setVisibility(View.VISIBLE);
                    }

                    mJczq1Adapter.notifyDataSetChanged();

                    for (int i = 0; i < mListData.size(); i++) {
                        mListView.expandGroup(i);
                    }
                } else {
                    mListView.setVisibility(View.GONE);
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
            }
        });
    }

    private void addListener() {
        if (jczqState==0){
            mEnsure.setText("停售");
            mEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.gray_deep));
        }else {
            //点击确定按钮
            mEnsure.setOnClickListener(EnsureBtnClick);
            mEnsure.setText("确定");
            mEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.red_theme));
        }

        //清空
        mClear.setOnClickListener(ClearClick);
        //精彩预测
        mFvl.setMove(ForecastMoveListener);
        mForecast.setOnClickListener(ForecastListener);
        //筛选
        if (mTitleBar != null && mTitleBar.mTitlebarSift != null)
            mTitleBar.mTitlebarSift.setOnClickListener(SiftClickListener);
        //刷新数据
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                map.clear();
                mListData.clear();
                clearColletion();
                addData();
            }
        });
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
    View.OnClickListener GendanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // inflate.setFocusable(false);
            if (LotteryApp.isLogin) {
                BuyCondition buyCondition = new BuyCondition();
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
    View.OnClickListener HemaiClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // inflate.setFocusable(false);
            if (LotteryApp.isLogin) {
                if ("".equals(mBuyBottomView.mEdit_buy_times.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    HemaiView mHemaiView = new HemaiView(mContext);
                    //View view = View.inflate(mContext, R.layout.dialog_hemai_layout, null);
                    final AlertDialog hemaiDialog = new AlertDialog.Builder(mContext).create();
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
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        }
    };
    View.OnClickListener SiftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListData != null && mListData.size() != 0) {
                SiftDialog mDialog = new SiftDialog(mContext, mLists, siftList, siftFlag, new SiftInterface() {

                    @Override
                    public void siftCallBack(ArrayList<ArrayList<JczqData.DataBean>> mListData1, ArrayList<String> clickList, boolean siftFlag1) {
                        mListData.clear();
                        siftList.clear();
                        siftFlag = siftFlag1;
                        mListData.addAll(mListData1);
                        siftList.addAll(clickList);
                        mJczq1Adapter.notifyDataSetChanged();
                    }
                });
                mDialog.show();
                DialogAnimotion.setAnimotion(mDialog);
            }
        }
    };
    View.OnClickListener BunchClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    View.OnClickListener ClearFormClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearColletion();
            mJczq1Adapter.notifyDataSetChanged();
            buyViewDialog.dismiss();
            mBuyBottomView.mLlBunch.setVisibility(View.GONE);
            mBuyBottomView.mLine_Bunch.setVisibility(View.GONE);
            setStake();
            flag = false;
        }
    };
    View.OnClickListener AddClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buyViewDialog.dismiss();
            choosedContentFormList.clear();
            danTuoList.clear();
            danTuoMap.clear();
            mBuyBottomView.mLlBunch.setVisibility(View.GONE);
            mBuyBottomView.mLine_Bunch.setVisibility(View.GONE);
            flag = false;
        }
    };
    FloatViewLayout.MoveListener ForecastMoveListener = new FloatViewLayout.MoveListener() {
        @Override
        public void moveLeft(int left) {

            if (left < ((displayWidth / 2) - DisplayUtil.dip2px(25))) {
                mForecast.setBackgroundResource(R.mipmap.forecast_left);
            } else {
                mForecast.setBackgroundResource(R.mipmap.forecast_right);
            }
        }
    };
    View.OnClickListener ForecastListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ForecastActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener ClearClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearColletion();
            setStake();
            mJczq1Adapter.notifyDataSetChanged();

        }
    };
    //奖金优化
    View.OnClickListener OptimizeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMulti <= 1) {
                Toast.makeText(mContext, "单倍无法优化奖金!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (choosedContentFormList.size() <= 1 && !isDg) {
                Toast.makeText(mContext, "单场无法优化奖金!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(mContext, BonusOptimizeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("lotcode", lotcode);
            bundle.putInt("shakes", shakes);
            bundle.putInt("mMulti", mMulti);
            bundle.putInt("playMethod", playMethod);
            bundle.putBoolean("mPlayMethodBunchs", mPlayMethodBunchs);
            bundle.putSerializable("choosedContentFormList", (Serializable) choosedContentFormList);
            bundle.putSerializable("danTuoList", danTuoList);
            bundle.putSerializable("mBunchMap", mBunchMap);
            bundle.putSerializable("mChoosedContentMap", (Serializable) mChoosedContentMap);
            intent.putExtras(bundle);
            /* intent.putExtra("choosedContentFormList",(Serializable) choosedContentFormList);
            intent.putExtra("mBunchMap",mBunchMap);
            intent.putExtra("shakes",shakes);
            intent.putExtra("mMulti",mMulti);
            intent.putExtra("lotcode",lotcode);
            intent.putExtra("danTuoList",danTuoList);*/
            startActivity(intent);
        }
    };
    View.OnClickListener BuyBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String spliteStr;
            spliteStr = SpliceCtr.spliteStr1(choosedContentFormList, danTuoList, true);
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
                if (!LotteryApp.cardFlag) {
                    HintDialogUtils.setHintDialog(getContext());
                    HintDialogUtils.setMessage("您还没有绑定身份证，请先绑定身份证");
                    HintDialogUtils.setTitleVisiable(true);
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                        @Override
                        public void callBack(View view) {
                            //身份证未绑定
                            Intent intent = new Intent(getContext(), CardActivity.class);
                            startActivity(intent);
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
                    bundle.putString("lotcode", lotcode);
                    NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
                    netWorkData.orderForm();
                }
            } else {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        }
    };

    private void showDialog() {
        // buyViewDialog.setCanceledOnTouchOutside(false);
        //设置串框和倍数框可点击
        mBuyBottomView.mText_buy_types.setEnabled(true);
        mBuyBottomView.mEdit_buy_times.setEnabled(true);
        Window window = buyViewDialog.getWindow();
        window.setSoftInputMode(2);
        buyViewDialog.setView(inflate);
        buyViewDialog.show();
        //window.setWindowAnimations(R.style.anim_bottom_top);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //layoutParams.width = window.getWindowManager().getDefaultDisplay().getWidth();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        window.setGravity(Gravity.BOTTOM);

    }

    private boolean isFirst = true;//是否第一次点击确定按钮

    View.OnClickListener EnsureBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChoosedContentMap == null || mChoosedContentMap.size() == 0) {
                Toast.makeText(mContext, "请选择比赛", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isFirst) {
                buyInitView();
                isFirst = false;
            }
            choosedContentFormList.clear();
                   /*
                    * 用于存放选中的比赛。
                    * 第一个参数是spf-0 这样格式的字符串，前面是玩法后面是单关的状态值
                    *      1是支持单关，0是不支持单关
                    * 第二个参数没有意义。主要是利用HashMap的去重效果。
                    * */
            //  HashMap<String, Integer> isBunch = new HashMap<>();
            //遍历外层的hashmap
            for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
                HashMap<String, String> value = entry.getValue();
                //遍历内层的hashmap
                JczqData.DataBean dataBean = entry.getKey();
                String awary = dataBean.getGuestShortCn();
                String home = dataBean.getHomeShortCn();
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
                    //  isBunch.put(BunchMethod.getPlayMethod(entry1.getKey(), dataBean), 0);
                }
                choosedContentFormList.add(list);
            }

                    /*
                    * 如果isBunch只有一个元素，并且支持单关那么就可以显示单关。
                    * */
         /*   if (isBunch.size() == 1) {
                for (Map.Entry<String, Integer> entry : isBunch.entrySet()) {
                    String key = entry.getKey();
                    String[] split = key.split("\\-");
                    if (split[1].equals("1")) {
                        isDg = true;
                    } else isDg = false;
                }
            } else isDg = false;*/
            isDg = BunchMethod.getPlayMethodBoolean(mChoosedContentMap);
            //串关和单关
            if (mPlayMethodBunchs) {
                if (mChoosedContentMap.size() >= 2 || isDg) {
                    showDialog();
                    //Log.e("选择结果是：", choosedContentFormList.toString());
                    //填充选中后按确定按钮的listview
                    mJcChooseFormAdapter = new JcChooseFormAdapter(mDeleteItemListener);
                    mJcChooseFormAdapter.setData(true);
                    mJcChooseFormAdapter.setData(choosedContentFormList, mPlayMethodBunchs, playMethod);
                    mChooseFormListView.setAdapter(mJcChooseFormAdapter);
                    //底部注数的计算,每次点击确定按钮都初始化注数
                    mBunchMap.clear();
                    mBunchList.clear();
                    if (isDg && choosedContentFormList.size() == 1) {
                        mBunchMap.put(1, 1);
                        mBunchList.add("1*1");
                        mBuyBottomView.mText_buy_types.setText("1串1");
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
                    Toast.makeText(getContext(), "请至少选择两场比赛", Toast.LENGTH_SHORT).show();
                }

            } else {
                if (mChoosedContentMap.size() >= 1) {
                    showDialog();
                    //Log.e("选择结果是：", choosedContentFormList.toString());
                    //填充选中后按确定按钮的listview
                    mJcChooseFormAdapter = new JcChooseFormAdapter(mDeleteItemListenerDg);
                    mJcChooseFormAdapter.setData(true);
                    mJcChooseFormAdapter.setData(choosedContentFormList, mPlayMethodBunchs, playMethod);
                    mChooseFormListView.setAdapter(mJcChooseFormAdapter);
                    //底部注数的计算,每次点击确定按钮都初始化注数
                    mBunchMap.clear();
                    mBunchList.clear();
                    mBunchMap.put(1, 1);
                    mBunchList.add("1*1");
                    mBuyBottomView.mText_buy_types.setText("单关");
                    setStakeCount();
                    //填充注数和钱数
                    setStakesAndMoney();

                    //填充理论奖金
                    setBonus();
                } else {
                    Toast.makeText(getContext(), "请至少选择一场比赛", Toast.LENGTH_SHORT).show();
                }
                // setRecyclerView();
                //设置串框不可点击
                mBuyBottomView.mText_buy_types.setEnabled(false);
            }

        }
    };
    JcChooseFormAdapter.DeleteItemListener mDeleteItemListenerDg = new JcChooseFormAdapter.DeleteItemListener() {
        @Override
        public void deleteItem(int position) {
//当就剩一场比赛的时候再删除让页面消失
            if (choosedContentFormList.size() <= 1) {
                clearColletion();
                mJczq1Adapter.notifyDataSetChanged();
                buyViewDialog.dismiss();
                mBuyBottomView.mLlBunch.setVisibility(View.GONE);
                mBuyBottomView.mLine_Bunch.setVisibility(View.GONE);
                setStake();
                flag = false;
                buyViewDialog.dismiss();
                return;
            }

            choosedContentFormList.remove(position);
            mJcChooseFormAdapter.notifyDataSetChanged();
            //当recyclerView已经显示的时候唤醒适配器，否则会出现空指针异常
            if (flag) {
                // mJcChooseFormAdapter.setData(choosedContentFormList);
                mRvMap.clear();
                mAdapter.notifyDataSetChanged();
            }
            //重新让串的数据置为初始状态
            mBunchMap.clear();
            mBunchList.clear();
            mBunchMap.put(1, 1);
            mBunchList.add("1*1");
            mBuyBottomView.mText_buy_types.setText("单关");
            setStakeCount();
            setStakesAndMoney();

            //重新计算理论奖金
            setBonus();
        }

        @Override
        public void danItem(ArrayList<String> danList, HashMap<Integer, String> danMap) {
            //danTuoList.clear();
            danTuoList = danList;
            if (danTuoList.size() > 0) {
                isDg = false;
            } else isDg = true;
        }

    };
    JcChooseFormAdapter.DeleteItemListener mDeleteItemListener = new JcChooseFormAdapter.DeleteItemListener() {
        @Override
        public void deleteItem(int position) {
            //当就剩一条数据的时候再删除让这个购买的视图消失
            if (choosedContentFormList.size() <= 1) {
                clearColletion();
                mJczq1Adapter.notifyDataSetChanged();
                buyViewDialog.dismiss();
                mBuyBottomView.mLlBunch.setVisibility(View.GONE);
                mBuyBottomView.mLine_Bunch.setVisibility(View.GONE);
                setStake();
                flag = false;
                buyViewDialog.dismiss();
                return;
            }
            /**
             * 删除条目的同时还需要移除已经选中的相应的条目。
             * 删除的条目对应的期号，所对应的数据源中的数据
             * */
            Iterator<Map.Entry<JczqData.DataBean, HashMap<String, String>>> it = mChoosedContentMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<JczqData.DataBean, HashMap<String, String>> entry = it.next();
                if (entry.getKey().getSession().equals(choosedContentFormList.get(position).get(0).getMid())) {
                    it.remove();
                }
            }

            mJczq1Adapter.notifyDataSetChanged();
            setStake();

            choosedContentFormList.remove(position);
            mBunchMap.clear();
            mBunchList.clear();
            if (danTuoMap.containsKey(position)) {
                danTuoList.remove(danTuoMap.get(position));
                danTuoMap.remove(position);
            } else if (choosedContentFormList.size() == danTuoMap.size()) {
                danTuoList.clear();
                danTuoMap.clear();
            }
            if (danTuoList.size() > 0) {
                mBunchMap.put(danTuoList.size() + 1, danTuoList.size() + 1);
                mBunchList.add((danTuoList.size() + 1) + "*1");
                mBuyBottomView.mText_buy_types.setText((danTuoList.size() + 1) + "串1");
            } else {
                //重新让串的数据置为初始状态
                mBunchMap.put(2, 2);
                mBunchList.add("2*1");
                mBuyBottomView.mText_buy_types.setText("2串1");
            }
            mJcChooseFormAdapter.notifyDataSetChanged();
            //当recyclerView已经显示的时候唤醒适配器，否则会出现空指针异常
            if (flag) {
                // mJcChooseFormAdapter.setData(choosedContentFormList);
                // mRvMap.clear();
                setRecyclerView();
                mAdapter.notifyDataSetChanged();
            }
            /**
             * 当剩余两条数据的时候再删除，保留当前视图，不去计算注数
             * */
            if (choosedContentFormList.size() <= 1) {
                shakes = 0;
                mBuyBottomView.mText_buy_types.setText("");
                mBonuses.setText("0元");
                //设置串框不可点击
                mBuyBottomView.mText_buy_types.setEnabled(false);
                mBuyBottomView.mEdit_buy_times.setEnabled(false);
                setStakesAndMoney();
                return;
            }
            setStakeCount();
            setStakesAndMoney();
            //重新计算理论奖金
            setBonus();
        }

        @Override
        public void danItem(ArrayList<String> danList, HashMap<Integer, String> danMap) {
            // danTuoList.clear();
            danTuoList = danList;
            danTuoMap = danMap;
            if (danTuoList.size() > 0) {
                isDg = false;
            }
            // if (flag) {
            //mRvMap.clear();
            //mAdapter.notifyDataSetChanged();
            setRecyclerView();
            //  }

            mBunchMap.clear();
            mBunchList.clear();
            if (danTuoList.size() > 0) {
                mBunchMap.put(danTuoList.size() + 1, danTuoList.size() + 1);
                mBunchList.add((danTuoList.size() + 1) + "*1");
                mBuyBottomView.mText_buy_types.setText((danTuoList.size() + 1) + "串1");
            } else {
                mBunchMap.put(2, 2);
                mBunchList.add("2*1");
                mBuyBottomView.mText_buy_types.setText("2串1");
            }
            setStakeCount();
            setStakesAndMoney();
            //重新计算理论奖金
            setBonus();
        }
    };

    private void setRecyclerView() {

        int maxBunchs = maxBunch.getMaxBunch("jczq", choosedContentFormList);
        mAdapter = new RecyclerViewAdapter(isDg, maxBunchs, danTuoList.size(), mRvMap, choosedContentFormList, new RecyclerViewAdapter.BunchClickListener() {

            @Override
            public void onBunchClick(HashMap<Integer, Integer> mMap) {
                //mBunchMap.clear();
                mBunchMap = mMap;
                //填充串框
                setBunchView();
                //重新计算注数和钱数
                setStakeCount();
                setStakesAndMoney();
                //重新计算理论奖金shuo
                setBonus();
            }
        });
        mBuyBottomView.mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBuyBottomView.mRecyclerView.setLayoutManager(llm);
    }

    private void setStakeCount() {
        shakes = 0;
        //将每一场比赛选中的项数放入一个集合中
        ArrayList<Integer> mList = new ArrayList<>();
        //将被选择为胆的场次中的项数放入一个集合中
        ArrayList<Integer> danNumList = new ArrayList<>();
        for (int i = 0; i < choosedContentFormList.size(); i++) {
            if (!danTuoList.contains(choosedContentFormList.get(i).get(0).getMid())) {
                mList.add(choosedContentFormList.get(i).size());
            } else {
                danNumList.add(choosedContentFormList.get(i).size());
            }
        }
        //通过封装的算法得到在这个mlist集合中任意挑选bunchs个，每种组合方式的数据
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            for (List<Integer> list : Combination.of(mList, (entry.getKey()) - danTuoList.size())) {
                int ss = 1;
                for (int j = 0; j < (entry.getKey()) - danTuoList.size(); j++) {
                    ss = ss * list.get(j);
                }
                shakes = shakes + ss;
            }
        }
        for (int j = 0; j < danNumList.size(); j++) {
            shakes = shakes * danNumList.get(j);
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

    private void setStake() {
        //串关 至少选择两场比赛，单关 至少选择一场比赛
        if (mPlayMethodBunchs) {
            if (mChoosedContentMap.size() == 1) {
                boolean playMethodBoolean = BunchMethod.getPlayMethodBoolean(mChoosedContentMap);
                if (playMethodBoolean){
                    mMinGames.setText("已选" + mChoosedContentMap.size() + "场");
                }else {
                    mMinGames.setText("请至少选择两场比赛");
                }
            } else if (mChoosedContentMap.size() >= 2) {
                mMinGames.setText("已选" + mChoosedContentMap.size() + "场");
            } else {
                mMinGames.setText("请至少选择两场比赛");
            }
        } else {
            if (mChoosedContentMap.size() >= 1) {
                mMinGames.setText("已选" + mChoosedContentMap.size() + "场");
            } else {
                mMinGames.setText("请至少选择一场比赛");
            }
        }

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

    //设置最大最小理论奖金
    private void setBonus() {
        TheoryBonusMath mTheoryBonusMath = new TheoryBonusMath();
        String text = mTheoryBonusMath.countBonus(mChoosedContentMap, danTuoList, mBunchMap);
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

    private void clearColletion() {
        mBunchList.clear();
        mBunchMap.clear();
        danTuoList.clear();
        choosedContentFormList.clear();
        mChoosedContentMap.clear();
    }

    private void deletenNoExist() {
        if (!mPlayMethodBunchs) {
            if (playMethod == LotteryTypes.SPF) {
                ArrayList<ArrayList<JczqData.DataBean>> bb = new ArrayList<>();
                for (int i = 0; i < mListData.size(); i++) {
                    ArrayList<JczqData.DataBean> aa = new ArrayList<>();
                    for (int j = 0; j < mListData.get(i).size(); j++) {
                        int spfFu = mListData.get(i).get(j).getSpfDg();
                        if (spfFu == 0) {
                            aa.add(mListData.get(i).get(j));
                            //mListData.get(i).remove(j);
                        }
                    }
                    for (int m = 0; m < aa.size(); m++) {
                        mListData.get(i).remove(aa.get(m));
                    }
                    // Log.e("mListData.get(i).size()", mListData.get(i).size() + "");
                    //如果这个条目里面已经没有数据，那么存起来，循环完成后再从mListData中循环移除。
                    //不能在mListData循环中直接add或者remove  mListData中的数据
                    if (mListData.get(i).size() == 0) {
                        bb.add(mListData.get(i));
                    }
                }
                for (int n = 0; n < bb.size(); n++) {
                    mListData.remove(bb.get(n));
                }
            } else if (playMethod == LotteryTypes.RQSPF) {
                ArrayList<ArrayList<JczqData.DataBean>> bb = new ArrayList<>();
                for (int i = 0; i < mListData.size(); i++) {
                    ArrayList<JczqData.DataBean> aa = new ArrayList<>();
                    for (int j = 0; j < mListData.get(i).size(); j++) {
                        int rqSpfFu = mListData.get(i).get(j).getRqspfDg();
                        if (rqSpfFu == 0) {
                            aa.add(mListData.get(i).get(j));
                            //mListData.get(i).remove(j);
                        }
                    }
                    for (int m = 0; m < aa.size(); m++) {
                        mListData.get(i).remove(aa.get(m));
                    }
                    //Log.e("mListData.get(i).size()", mListData.get(i).size() + "");
                    if (mListData.get(i).size() == 0) {
                        bb.add(mListData.get(i));
                    }
                }
                for (int n = 0; n < bb.size(); n++) {
                    mListData.remove(bb.get(n));
                }
            }
        }
    }

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
        spliteStr = SpliceCtr.spliteStr1(choosedContentFormList, danTuoList, true);
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
        if (mJczq1Adapter == null) return;
        mJczq1Adapter.notifyDataSetChanged();
        //将这个列表视图取消
        buyViewDialog.dismiss();
        mBuyBottomView.mLlBunch.setVisibility(View.GONE);
        mBuyBottomView.mLine_Bunch.setVisibility(View.GONE);
        flag = false;
        setStake();
    }
}
