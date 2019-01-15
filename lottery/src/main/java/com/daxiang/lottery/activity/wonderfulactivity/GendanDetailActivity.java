package com.daxiang.lottery.activity.wonderfulactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.JoinerActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.activity.lotteryactivity.ServiceAgreementActivity;
import com.daxiang.lottery.adapter.JoinerAdapter;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.GameList;
import com.daxiang.lottery.entity.GendanDetailData;
import com.daxiang.lottery.entity.JoinData;
import com.daxiang.lottery.entity.OutTicketDetailData;
import com.daxiang.lottery.entity.RecommendBaseData;
import com.daxiang.lottery.score.ScoreDetailActivity;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.IsWinnig;
import com.daxiang.lottery.utils.Share;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.LotteryTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GendanDetailActivity extends BaseActivity implements View.OnLayoutChangeListener {

    private String mOrderCode;
    private String mOrderId;
    private int mLid;
    //合买还是推荐详情。true是推荐，false为合买
    private boolean mFlag;
    // Content View Elements

    private TextView mLottery_type;
    private TextView mLottery_issue;
    private ImageView mImage_user;
    private TextView mTv_user_name;
    private TextView mMutile;
    private TextView mTotale_money;
    private TextView mSingle_money;
    //提成比例
    private TextView mRatio;
    private EditText mEdit_mutil;
    private TextView mMutil_money;
    private TextView mBtn_copy;
    private TextView mTitleMutile;
    private TextView mTitleManifesto;
    private TextView mNeedMoney;
    private TextView mTitleSingleMoney;
    private TextView mTitleEditMutil;
    private TextView mUnit;
    private ImageView mLottery_status;
    private GendanDetailData.DataBean data;
    //合买传递的值   进度 比例 剩余钱
    //private int ratio;
    //private int mCommission;
    //private String remainMoney;
    private RecommendBaseData mItemsBean;
    //******************************************************
    private TextView mTextSchemeNumber;
    private TextView mTextCreatTime;
    private LotteryTextView mWinNumber;
    private TextView mLotteryState;
    private TextView mbuy_lottery_content;
    private TableLayout mNumberGame;
    private LinearLayout mFightLottery;
    private LinearLayout mVsTimes;
    private LinearLayout mLl_danbeijine;
    private LinearLayout mLl_tichengbili;
    private LinearLayout mLl_fuzhi;

    private TextView mMatchTitle;
    private TextView mStub;
    private TextView mVsTitle;
    private TextView mPassType;
    private TextView mOutTicketField;
    private TextView mOutTicketMethod;
    private TextView mOutTicketTimes;
    private TextView mOutTicketReward;
    private TextView mTitleBuyContent;
    private TextView mTitleBuyMutil;
    private TextView mTitleBuyMoney;
    private TextView mManifesto;
    private TextView mTitleOutTicketStatus;
    private BillListView mListView;
    private TextView mLookMore;
    private TextView mZhuKeTitle;
    private TextView mJoinTitle;
    private LinearLayout mLl_mutile;
    private TextView mOne;
    private TextView mTwo;
    private TextView mThree;
    private TextView mFour;
    private LinearLayout mLl_xieyi;
    private LinearLayout mVsDetail;
    private LinearLayout mOutTicketDetail;
    private LinearLayout mLlOutTicketDetail;
    private LotteryTextView mBuyNumber;
    private List<GendanDetailData.DataBean.GameListBean> gamesList = new ArrayList();
    private HashMap<String, GendanDetailData.DataBean.GameListBean> mapSort = new HashMap<>();
    private List<OutTicketDetailData.DataBean> mOutTicketList = new ArrayList<>();
    Map<String, List<StringBuilder>> map = new HashMap<>();
    private String playsResult;
    //存胆的期号
    ArrayList<String> danList = new ArrayList<>();
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private HttpInterface2 mHttp;
    private String splitDetail;
    private Context mContext;
    private String userNum = "0";
    private String manifesto;
    private String joinerUrl;
    private String winStatus;
    private String mIssueNo;
    private String mUserId;
    private LinearLayout mRootView;
    private int keyHeight;
    private LinearLayout mLl_godinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_gendan_detail);
        mContext = this;
        Intent intent = getIntent();
        mItemsBean = (RecommendBaseData) intent.getSerializableExtra("itemsbean");
        mFlag = intent.getBooleanExtra("flag", true);
        userNum = intent.getStringExtra("userNum");
        manifesto = intent.getStringExtra("manifesto");
        mLid = Integer.parseInt(mItemsBean.getLotCode());
        if (mItemsBean != null) {
            mOrderCode = mItemsBean.getOrderCode();
            mOrderId = mItemsBean.getOrderId();
            winStatus = mItemsBean.getWinStatus();
            mUserId = mItemsBean.getUserId();
        }
       /* mOrder_id = intent.getStringExtra("order_id");
        mLid = intent.getIntExtra("lid", 0);

        //合买中心详情需要用到的值。
        ratio=intent.getIntExtra("ratio",0);
        mCommission=intent.getIntExtra("commission",0);
        remainMoney=  intent.getStringExtra("remainmoney");*/

        bindViews();
        //获取屏幕高度
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        addData();
        addListener();
    }

    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.mShare.setVisibility(View.VISIBLE);
        mRootView = (LinearLayout) findViewById(R.id.gendan_detail_root);
        mLottery_type = (TextView) findViewById(R.id.lottery_type);
        mLottery_issue = (TextView) findViewById(R.id.lottery_issue);
        mImage_user = (ImageView) findViewById(R.id.image_user);
        mTv_user_name = (TextView) findViewById(R.id.tv_user_name);
        mMutile = (TextView) findViewById(R.id.mutile);
        mTotale_money = (TextView) findViewById(R.id.totale_money);
        mSingle_money = (TextView) findViewById(R.id.single_money);
        mJoinTitle = (TextView) findViewById(R.id.join_title);
        mLl_godinfo = (LinearLayout) findViewById(R.id.ll_godinfo);
        mOne = (TextView) findViewById(R.id.one);
        mTwo = (TextView) findViewById(R.id.two);
        mThree = (TextView) findViewById(R.id.three);
        mFour = (TextView) findViewById(R.id.four);
        //宣言
        mManifesto = (TextView) findViewById(R.id.manifesto);
        //提成比例
        mRatio = (TextView) findViewById(R.id.ratio);
        mListView = (BillListView) findViewById(R.id.lv_join_number);
        //查看更多
        mLookMore = (TextView) findViewById(R.id.look_more);
        mZhuKeTitle = (TextView) findViewById(R.id.duizhen_title_hint);
        mEdit_mutil = (EditText) findViewById(R.id.edit_mutil);
        mMutil_money = (TextView) findViewById(R.id.mutil_money);
        mBtn_copy = (TextView) findViewById(R.id.btn_copy);
        mLottery_status = (ImageView) findViewById(R.id.lottery_status);
        //一下为订单出票详情
        mTextSchemeNumber = (TextView) findViewById(R.id.scheme_number);
        mTextCreatTime = (TextView) findViewById(R.id.creat_time);
        //这里的中奖号码没有用到，因为中奖的时候已经开奖了，推荐中心就不会再去显示了
        mWinNumber = (LotteryTextView) findViewById(R.id.win_num);
        mLotteryState = (TextView) findViewById(R.id.lottery_state);
        mbuy_lottery_content = (TextView) findViewById(R.id.buy_lottery_content);
        mNumberGame = (TableLayout) findViewById(R.id.number_game);
        mPassType = (TextView) findViewById(R.id.pass_type);
        mOutTicketDetail = (LinearLayout) findViewById(R.id.out_ticket_detail);
        mLl_mutile = (LinearLayout) findViewById(R.id.ll_mutile);
        mLlOutTicketDetail = (LinearLayout) findViewById(R.id.ll_outticket_detail);
        mLl_xieyi = (LinearLayout) findViewById(R.id.ll_xieyi);
        mTitleBuyContent = (TextView) findViewById(R.id.buy_content);
        mTitleBuyMutil = (TextView) findViewById(R.id.buy_mutil);
        mTitleBuyMoney = (TextView) findViewById(R.id.buy_money);
        mTitleOutTicketStatus = (TextView) findViewById(R.id.out_ticket_status);
        mBuyNumber = (LotteryTextView) findViewById(R.id.buy_num);
        mVsTimes = (LinearLayout) findViewById(R.id.vs_times);
        mFightLottery = (LinearLayout) findViewById(R.id.fight_lottery);
        //需要控制显隐的控件
        mTitleMutile = (TextView) findViewById(R.id.title_mutile);
        mTitleManifesto = (TextView) findViewById(R.id.text_manifesto);
        mNeedMoney = (TextView) findViewById(R.id.need_money);
        mTitleSingleMoney = (TextView) findViewById(R.id.title_single_money);
        mTitleEditMutil = (TextView) findViewById(R.id.title_edit_mutil);
        mUnit = (TextView) findViewById(R.id.unit);
        mLl_danbeijine = (LinearLayout) findViewById(R.id.ll_danbeijine);
        mLl_tichengbili = (LinearLayout) findViewById(R.id.ll_tichengbili);
        mLl_fuzhi = (LinearLayout) findViewById(R.id.ll_fuzhi);
        if (mFlag) {
            mTitleBar.setTitle("推荐详情");
            splitDetail = "followBuy/detail/v1";
            joinerUrl = "followBuy/joiner";
            if ("0".equals(winStatus)) {
                if (mItemsBean != null) {
                    String deadline = mItemsBean.getDeadline();
                    long currentTime = System.currentTimeMillis();
                    if (currentTime >= Long.parseLong(deadline)) {
                        mBtn_copy.setVisibility(View.GONE);
                        mLl_danbeijine.setVisibility(View.GONE);
                        mLl_tichengbili.setVisibility(View.GONE);
                        mLl_fuzhi.setVisibility(View.GONE);
                        mLl_xieyi.setVisibility(View.GONE);
                    } else {
                        mBtn_copy.setVisibility(View.VISIBLE);
                        mLl_danbeijine.setVisibility(View.VISIBLE);
                        mLl_tichengbili.setVisibility(View.VISIBLE);
                        mLl_fuzhi.setVisibility(View.VISIBLE);
                        mLl_xieyi.setVisibility(View.VISIBLE);
                    }

                }
            } else {
                mBtn_copy.setVisibility(View.GONE);
                mLl_danbeijine.setVisibility(View.GONE);
                mLl_tichengbili.setVisibility(View.GONE);
                mLl_fuzhi.setVisibility(View.GONE);
                mLl_xieyi.setVisibility(View.GONE);
            }
        } else {
            mTitleBar.setTitle("合买详情");
            splitDetail = "togetherBuy/detail/v1";
            joinerUrl = "togetherBuy/joiner";
            mBtn_copy.setVisibility(View.VISIBLE);
        }
    }


    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        String url = Url.TOGETHER_DETAIL_URL + splitDetail + "?orderId=" + mOrderId + "&token=" + LotteryApp.token;
        mHttp = new HttpUtils2(this);
        mHttp.jsonByUrl(url, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                GendanDetailData gendanDetailData = gson.fromJson(result, GendanDetailData.class);
                String msg = gendanDetailData.getMsg();
                if (gendanDetailData.getCode() == 0) {
                    data = gendanDetailData.getData();
                    //填充詳細信息列表上面部分的控件
                    setView(data);
                    detailData();
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                Toast.makeText(mContext, "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
//参与者的数据
        joinData(loadingDialogUtils);
    }

    private void detailData() {
        if (data == null) return;
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String betTime = data.getOrderOriginal().getBetTime();
               /* String reg = "^\\d{4}-0[1-9]|1[1-2]-0[1-9]|[1-2]\\d|3[0-1] [0-2][0-4]:[0-6]\\d:[0-6]\\d$";
                Pattern p=Pattern.compile(reg);
                Matcher m=p.matcher(betTime);*/
            String time;
            //防止返回值不是一个毫秒值，而是直接格式化好的2016-12-09 17:39:06
            if (betTime.length() > 15) {
                time = betTime;
            } else {
                long printTime = Long.parseLong(betTime);
                time = format.format(printTime);
            }
            mTextCreatTime.setText(time);
            mTextSchemeNumber.setText(mOrderCode);
            //大乐透，任九场，胜负彩，排列三，排列五，
            if (23529 == mLid || 11 == mLid || 19 == mLid || 33 == mLid || 35 == mLid || 10022 == mLid || 23528 == mLid || 51 == mLid || 21406 == mLid || 52 == mLid) {
                mNumberGame.setVisibility(View.VISIBLE);
                mFightLottery.setVisibility(View.GONE);
                // mLlOutTicketDetail.setVisibility(View.GONE);
                mTitleBuyContent.setText("投注内容");
                mTitleBuyMutil.setText("跟单倍数：");
                mTitleBuyMoney.setText("投注金额");
                mTitleOutTicketStatus.setText("状态");

                String betContent = data.getOrderOriginal().getBetContent();
                numberLotteryData(betContent);
                mbuy_lottery_content.setHeight(mBuyNumber.getHeight());
                mLotteryState.setText(LotteryTypes.getStatus(data.getOrderOriginal().getOrderStatus()));

                //出票详情
                // HttpInterface mHttpInterface1 = new HttpUtils();
                Bundle params1 = new Bundle();
                params1.putString("token", LotteryApp.token);
                params1.putString("orderId", mOrderId);
                mHttp.get(Url.OUT_TICKET_DETAIL_URL, params1, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            Gson gson = new Gson();
                            OutTicketDetailData outTicketDetailData = gson.fromJson(result, OutTicketDetailData.class);
                            if (outTicketDetailData.getCode() == 0) {
                                mOutTicketList = outTicketDetailData.getData();
                                outTicketTableNumber();
                            }
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            } else {
                mNumberGame.setVisibility(View.GONE);
                mFightLottery.setVisibility(View.VISIBLE);
                gamesList = data.getGameList();
                mapSort = new HashMap<>();
                for (int m = 0; m < gamesList.size(); m++) {
                    GendanDetailData.DataBean.GameListBean value = gamesList.get(m);
                    String score = value.getScore();
                    //  Log.e("比分：", score + "");
                    mapSort.put(gamesList.get(m).getSession(), value);
                }
                //不是自己发起的不可看
                if (data.getGameList() != null && data.getGameList().size() != 0) {
                    //訂票詳情
                    List<Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>>> list = orderMap(table());
                    //List<Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>>> list  = CollectionUtils.table(mBuycontent,mData);
                    //填充控件
                    addWidge(list);
                    //出票详情
                    // HttpInterface mHttpInterface1 = new HttpUtils();
                    Bundle params1 = new Bundle();
                    params1.putString("token", LotteryApp.token);
                    params1.putString("orderId", mOrderId);
                    mHttp.get(Url.OUT_TICKET_DETAIL_URL, params1, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            if (!TextUtils.isEmpty(result)) {
                                Gson gson = new Gson();
                                OutTicketDetailData outTicketDetailData = gson.fromJson(result, OutTicketDetailData.class);
                                if (outTicketDetailData.getCode() == 0) {
                                    mOutTicketList = outTicketDetailData.getData();
                                    outTicketTable();
                                }
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                } else {
                    GendanDetailData.DataBean.TogetherBuyBean togetherBuy = data.getTogetherBuy();
                    if (togetherBuy != null) {
                        if (togetherBuy.getOpenType() == 2) {
                            mPassType.setText("很抱歉，该订单为参与后公开，暂时不能查看方案...");
                            mPassType.setTextColor(Color.BLUE);
                        } else if (togetherBuy.getOpenType() == 3) {
                            mPassType.setText("很抱歉，该订单为截止后公开，暂时不能查看方案...");
                            mPassType.setTextColor(Color.BLUE);
                        }
                    }
                }
            }
        }
    }

    private void setView(GendanDetailData.DataBean data) {
        if (data == null) return;
        GendanDetailData.DataBean.OrderOriginalBean order = data.getOrderOriginal();
        GendanDetailData.DataBean.TogetherBuyBean togetherOrderInf = data.getTogetherBuy();
        commission = togetherOrderInf.getCommission();
        String joinNum = togetherOrderInf.getJoinNum();
        if (TextUtils.isEmpty(commission))
            mJoinTitle.setText("参与人次 " + joinNum + "次");
        else mJoinTitle.setText("参与人次 " + joinNum + "次 提成金额" + commission + "元");
        mLottery_type.setText(LotteryTypes.getTypes(mLid + ""));
        HttpUtils2.display(mImage_user, Url.HEADER_ROOT_URL + mItemsBean.getUserId());
        mTv_user_name.setText(mItemsBean.getUserName());
        mTotale_money.setText(getSpannable(Double.parseDouble(mItemsBean.getTotalMoney()) + "元"));
        mRatio.setText(mItemsBean.getCommissionScale() + "%");
        if (mFlag) {
            mTitleEditMutil.setText("跟单倍数：");
            //参与人数
            mTitleManifesto.setText("参与人：");
            mNeedMoney.setText(getSpannable(userNum + "人"));
            //宣言
            mManifesto.setVisibility(View.VISIBLE);
            mManifesto.setText("宣言：" + manifesto);
            mIssueNo = order.getIssueNo();
            mLottery_issue.setText("第" + mIssueNo + "期");
            mMutile.setText((int) Double.parseDouble(mItemsBean.getTotalMoney()) / (int) Double.parseDouble(mItemsBean.getOneMoney()) + "");
            mSingle_money.setText(getSpannable(mItemsBean.getOneMoney() + "元"));
            double oneMoney = Double.parseDouble(mItemsBean.getOneMoney());
            if (oneMoney > 100) {
                mEdit_mutil.setText("1");
                mMutil_money.setText(getSpannable(oneMoney + "元"));
                mEdit_mutil.setSelection(1);
            } else {
                mEdit_mutil.setText("10");
                mEdit_mutil.setSelection(2);
                mMutil_money.setText(getSpannable(oneMoney * 10 + "元"));
            }

            switch (order.getOrderStatus()) {
                case "500":
                    mLottery_status.setImageResource(R.drawable.chupiao);
                    break;
                case "5000":
                case "1000":
                    mLottery_status.setImageResource(R.drawable.paijiang);
                    break;
                case "2000":
                case "1500":
                    mLottery_status.setImageResource(R.drawable.kaijiang);
                    break;
                case "210":
                case "240":
                case "200":
                    mLottery_status.setImageResource(R.drawable.faqi);
                    break;
                default:
                    mLottery_status.setImageResource(R.drawable.chupiaofail);
                    break;
            }
        } else {
            //mLottery_issue.setText(mItemsBean.get().substring(0, 10));
            mTitleMutile.setText("进度");
            mMutile.setText(mItemsBean.getBuyRatio() + "%");
            mTitleManifesto.setVisibility(View.VISIBLE);
            mNeedMoney.setVisibility(View.VISIBLE);
            mNeedMoney.setText(getSpannable((int) Double.parseDouble(mItemsBean.getTotalMoney()) - mItemsBean.getBuyedNum() + "元"));
            mTitleSingleMoney.setText("保底金额：");
            String guaranteeNum = togetherOrderInf.getGuaranteedMoney();
            int allmoney = (int) Double.parseDouble(togetherOrderInf.getTotalMoney());
            mSingle_money.setText(guaranteeNum + "元(" + (int) Double.parseDouble(guaranteeNum) * 100 / allmoney + "%)");
            mLottery_status.setVisibility(View.GONE);
            mBtn_copy.setText("参与合买");
            mUnit.setText("元");
            mMutil_money.setVisibility(View.GONE);
        }
    }

    private void addListener() {
        mRootView.addOnLayoutChangeListener(this);
        //点击头像进个人中心
        mImage_user.setOnClickListener(ImageUserListener);
        mLl_godinfo.setOnClickListener(ImageUserListener);
        //协议
        mLl_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ServiceAgreementActivity.class);
            }
        });
        //快速选择倍数
        mOne.setOnClickListener(MultilFastListener);
        mTwo.setOnClickListener(MultilFastListener);
        mThree.setOnClickListener(MultilFastListener);
        mFour.setOnClickListener(MultilFastListener);
        mEdit_mutil.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (data == null) return;
                String trim = mEdit_mutil.getText().toString().trim();
                if ("".equals(trim)) {
                    mEdit_mutil.setText("0");
                    mEdit_mutil.setSelection(1);
                    mMutil_money.setText(getSpannable(0 + "元"));
                } else {
                    if (!"0".equals(trim) && trim.startsWith("0")) {
                        int result = Integer.parseInt(trim);
                        mEdit_mutil.setText(result + "");
                        mEdit_mutil.setSelection((result + "").length());
                    }
                    int m = (int) Double.parseDouble(mEdit_mutil.getText().toString());
                    if (data != null && data.getTogetherBuy() != null) {
                        int i = (int) Double.parseDouble(data.getTogetherBuy().getOneMoney());
                        mMutil_money.setText(getSpannable(i * m + "元"));
                    }
                }
            }
        });
        //立即跟单，参与合买
        mBtn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(mEdit_mutil.getText().toString())) {
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
                int m = (int) Double.parseDouble(mEdit_mutil.getText().toString());
                int i = 1;
                if (mFlag) {
                    i = (int) Double.parseDouble(data.getTogetherBuy().getOneMoney());
                }
                Bundle bundle = new Bundle();
                bundle.putString("from", "gendanDetailActivity");
                bundle.putBoolean("mFlag", mFlag);
                bundle.putString("money", m * i + "");
                bundle.putString("mutile", m + "");
                bundle.putString("orderId", mOrderId);
                bundle.putString("lotcode", mLid + "");
                NetWorkData netWorkData = new NetWorkData(mContext, bundle);
                netWorkData.genheOrderForm();
            }
        });
        //查看更多，参与人
        mLookMore.setOnClickListener(LookMoreListener);
        //分享跟单
        mTitleBar.mShare.setOnClickListener(ShareListener);
    }

    private SpannableString getSpannable(String string) {
        if (string == null) return new SpannableString("");
        SpannableString ss = new SpannableString(string);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gray_txt)), string.length() - 1, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    //订票详情
    /*
    * 将购买记录的字符串分割，然后组装成一个map集合。
    * map集合键是封装的带有期号，主队，客队等信息的一个实体类，值是拼接list集合
    * 用于存放每一期比赛选择玩法拼接的字符串。
    * */
    public HashMap<GendanDetailData.DataBean.GameListBean, ArrayList<String>> table() {
        /**
         * 创建一个map集合，键是选中的实体类，
         * 里面包含有比赛的比分，等详细信息。值是拼接list集合
         * 用于存放每一期比赛选择玩法拼接的字符串。
         * */
        HashMap<GendanDetailData.DataBean.GameListBean, ArrayList<String>> widgeMap = new HashMap<>();
        String mBuycontent = data.getOrderOriginal().getBetContent();
        String[] first = mBuycontent.split("\\;");
        for (int n = 0; n < first.length; n++) {
            map.clear();
            String[] strTimes = first[n].split("\\|");

            //如果不是混合
            if (!strTimes[0].equals("HH")) {
                //如果不是混合的话前面就是玩法的标志
                String playMethod = LotteryTypes.getPlayMethod(strTimes[0]);
                //获取“|”中间的字符串
                String[] split1 = strTimes[1].split("\\,");
                //循环场次
                for (int i = 0; i < split1.length; i++) {

                    String keyMid = null;
                    ArrayList<String> widgeList = new ArrayList<>();
                    String[] split3 = split1[i].split("\\=|\\/");
                    keyMid = split3[0];
                    GameList gameList = mapSort.get(keyMid);
                    String presetScore = gameList.getPresetScore();//篮球的大小分。
                    String let = gameList.getLet();
                    for (int j = 1; j < split3.length; j++) {
                        StringBuilder mStringBuilder = new StringBuilder();
                        mStringBuilder.append(playMethod);
                        if ("RQSPF".equals(strTimes[0]) || "RFSF".equals(strTimes[0])) {
                            if (split3[j].contains("{")) {
                                String[] split = split3[j].split("\\{|\\}");//分割(是为了去掉赔率
                                playsResult = LotteryTypes.getRqspfStr(split[0]) + "{" + let.replaceAll("\\,", "  ") + "}" + split[2];
                            } else {
                                String[] split = split3[j].split("\\(");
                                playsResult = LotteryTypes.getRqspfStr(split[0]) + "(" + split[1];
                                // playsResult = LotteryTypes.getRqspfStr(split[0]);
                            }

                        } else {
                            String[] split = split3[j].split("\\(");
                            if ("BQC".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getBqcStr(split[0]) + "(" + split[1];
                            } else if ("SPF".equals(strTimes[0]) || "SF".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getSpf(split[0]) + "(" + split[1];
                            } else if ("DXF".equals(strTimes[0])) {
                                if (!TextUtils.isEmpty(presetScore)) {
                                    String s = presetScore.replaceAll("\\+", "");
                                    playsResult = LotteryTypes.getDxf(split[0]) + "{" + s + "}" + "(" + split[1];
                                } else {
                                    playsResult = LotteryTypes.getDxf(split[0]) + "(" + split[1];
                                }
                            } else if ("JQS".equals(strTimes[0])) {
                                if ("7".equals(split[0])) {
                                    playsResult = "7+(" + split[1];
                                } else {
                                    playsResult = split[0] + "(" + split[1];
                                }
                            } else if ("SFC".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getSfcStr(split[0]) + "(" + split[1];
                            } else {
                                if ("9:0".equals(split[0])) {
                                    playsResult = "胜其他(" + split[1];
                                } else if ("9:9".equals(split[0])) {
                                    playsResult = "平其他(" + split[1];
                                } else if ("0:9".equals(split[0])) {
                                    playsResult = "负其他(" + split[1];
                                } else {
                                    playsResult = split[0] + "(" + split[1];
                                }
                            }
                        }
                        mStringBuilder.append(playsResult);
                        widgeList.add(String.valueOf(mStringBuilder));
                    }
                    widgeMap.put(mapSort.get(keyMid), widgeList);
                }
            } else {
                //混合
                //取两个|中间的字符串进行分割
                String[] dantuoSplit = strTimes[1].split("\\$");
                //$符号前面和后面进行循环
                for (int j = 0; j < dantuoSplit.length; j++) {
                    String[] split = dantuoSplit[j].split("\\,");
                    for (int i = 0; i < split.length; i++) {

                        String[] split1 = split[i].split("\\>|\\=");
                        GameList gameList = mapSort.get(split1[1]);
                        String let = gameList.getLet();
                        //如果有胆，如果是$符号前面的也就是设胆的场，那么就将期号存起来
                        if (dantuoSplit.length > 1) {
                            if (j == 0) {
                                if (!danList.contains(split1[1])) {
                                    danList.add(split1[1]);
                                }
                            }
                        }
                        //两种情况，一是让球二是不让球
                        if ("RQSPF".equals(split1[0]) || "RFSF".equals(split1[0])) {
                            //RQSPF>20161107005=1{-1}(4.05)/3{-1}(6.35)/0{-1}(1.38);
                            List<StringBuilder> list;
                            //判断map中是否有这个场次,有的话取出来，没有创建
                            if (map.containsKey(split1[1])) {
                                list = map.get(split1[1]);
                                map.remove(split1[1]);
                            } else {
                                list = new ArrayList<>();
                            }
                            //把等号后面的字符串进行分离
                            String[] split2 = split1[2].split("\\/");
                            for (int k = 0; k < split2.length; k++) {
                                StringBuilder strBuilder = new StringBuilder();
                                if (split2[k].contains("{")) {
                                    //String[] split3 = split2[k].split("\\{|\\(");//加小括号的目的是去掉赔率
                                    String[] split3 = split2[k].split("\\{|\\}");
                                    strBuilder.append(LotteryTypes.getPlayMethod(split1[0]) + LotteryTypes.getRqSpf(split3[0]) + "{" + let.replaceAll("\\,", "  ") + "}" + split3[2]);
                                } else {
                                    String[] split3 = split2[k].split("\\(");
                                    strBuilder.append(LotteryTypes.getPlayMethod(split1[0]) + LotteryTypes.getRqSpf(split3[0]) + "(" + split3[1]);
                                    //strBuilder.append(LotteryTypes.getPlayMethod(split1[0]) + LotteryTypes.getRqSpf(split3[0]));
                                }
                                list.add(strBuilder);
                            }
                            map.put(split1[1], list);

                        } else {
                            //判断map中是否有这个场次
                            if (map.containsKey(split1[1])) {
                                List<StringBuilder> list = map.get(split1[1]);
                                map.remove(split1[1]);
                                sdfdsd(split1, list);
                            } else {
                                List<StringBuilder> list = new ArrayList<>();
                                sdfdsd(split1, list);
                            }
                        }

                    }
                }
                ArrayList<String> widgeList;
                for (Map.Entry<String, List<StringBuilder>> entry : map.entrySet()) {
                    String keyMid = entry.getKey();
                   /* ArrayList<String> widgeList = new ArrayList<>();
                    for (int j = 0; j < entry.getValue().size(); j++) {
                        widgeList.add(String.valueOf(entry.getValue().get(j)));
                    }*/
                    //当参与推荐的时候加上的判断（原因是 每场比赛只会显示一种投注，别的在widgemap中被覆盖了。）
                    if (widgeMap.containsKey(mapSort.get(keyMid))) {
                        widgeList = widgeMap.get(mapSort.get(keyMid));
                    } else {
                        widgeList = new ArrayList<>();
                    }

                    for (int j = 0; j < entry.getValue().size(); j++) {
                        String s = String.valueOf(entry.getValue().get(j));
                        //当参与推荐的时候加上的判断（原因是显示的投注方式会重复）
                        if (!widgeList.contains(s)) {
                            widgeList.add(s);
                        }

                    }
                    widgeMap.put(mapSort.get(keyMid), widgeList);
                }
            }
        }
        return widgeMap;
    }

    //出票详情(数字彩)
    private void outTicketTableNumber() {
        for (int i = 0; i < mOutTicketList.size(); i++) {
            View view = View.inflate(this, R.layout.out_ticket_detail, null);
            mOutTicketField = (TextView) view.findViewById(R.id.out_ticket_field);
            mOutTicketMethod = (TextView) view.findViewById(R.id.out_ticket_method);
            mOutTicketReward = (TextView) view.findViewById(R.id.out_ticket_reward);
            mOutTicketTimes = (TextView) view.findViewById(R.id.out_ticket_times);
            mOutTicketDetail.addView(view);
            //如果没有出票信息，返回
            if (mOutTicketList.get(i).getPureContent() == null || mOutTicketList.get(i).getPureContent().length() == 0) {
                return;
            }
            String pureContent = mOutTicketList.get(i).getPureContent();
            String buyContent;
            if (pureContent.contains("|")) {
                buyContent = pureContent.split("\\|")[1];
            } else buyContent = pureContent;
            //数字彩购买内容
            mOutTicketField.setText(buyContent);
            //数字彩购买倍数
            mOutTicketMethod.setText(mOutTicketList.get(i).getMulti());
            //数字彩购买金额
            mOutTicketTimes.setText((int) Double.parseDouble(mOutTicketList.get(i).getTotalMoney()) + "");
            /*
        * 设置状态，
        * 如果出票成功了，并且中奖了显示中奖金额，并且显示为红色
        * 否则，显示出票的状态，显示为灰色
        * */
            String margin = mOutTicketList.get(i).getMargin();
            String printStatus = mOutTicketList.get(i).getPrintStatus();
            if (margin != null && !"0.00".equals(margin) && "2".equals(printStatus)) {
                mOutTicketReward.setTextColor(Color.RED);
                mOutTicketReward.setText(mOutTicketList.get(i).getMargin());
            } else {
                mOutTicketReward.setTextColor(Color.GRAY);
                mOutTicketReward.setText(LotteryTypes.getPrintStatus(printStatus));
            }
        }
    }

    //出票详情
    private void outTicketTable() {
        for (int i = 0; i < mOutTicketList.size(); i++) {
            View view = View.inflate(this, R.layout.out_ticket_detail, null);
            mOutTicketField = (TextView) view.findViewById(R.id.out_ticket_field);
            mOutTicketMethod = (TextView) view.findViewById(R.id.out_ticket_method);
            mOutTicketReward = (TextView) view.findViewById(R.id.out_ticket_reward);
            mOutTicketTimes = (TextView) view.findViewById(R.id.out_ticket_times);
            mOutTicketDetail.addView(view);
            //如果没有出票信息，返回
            if (mOutTicketList.get(i).getPureContent() == null || mOutTicketList.get(i).getPureContent().length() == 0) {
                return;
            }
            String pureContent;
            if (TextUtils.isEmpty(mOutTicketList.get(i).getOddsContent())) {
                pureContent = mOutTicketList.get(i).getPureContent();
            } else {
                pureContent = mOutTicketList.get(i).getOddsContent();
            }
            String[] firstSplit = pureContent.split("\\|");
            //设置过关方式
            String[] fiveSplit = firstSplit[2].split("\\*");
            mOutTicketMethod.setText(fiveSplit[0] + "串" + fiveSplit[1]);
            //设置倍投
            mOutTicketTimes.setText(mOutTicketList.get(i).getMulti());
        /*
        * 设置状态，
        * 如果出票成功了，并且中奖了显示中奖金额，并且显示为红色
        * 否则，显示出票的状态，显示为灰色
        * */
            String margin = mOutTicketList.get(i).getCenterMargin();
            String printStatus = mOutTicketList.get(i).getPrintStatus();
            if (margin != null && !"0.00".equals(margin) && "2".equals(printStatus)) {
                mOutTicketReward.setTextColor(Color.RED);
                mOutTicketReward.setText(mOutTicketList.get(i).getCenterMargin());
            } else {
                mOutTicketReward.setTextColor(Color.GRAY);
                mOutTicketReward.setText(LotteryTypes.getPrintStatus(printStatus));
            }
            if (!"HH".equals(firstSplit[0])) {
                /**
                 * 设置场次
                 * */
                String[] twoSplit = firstSplit[1].split("\\,");
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < twoSplit.length; j++) {
                    String[] threeSplit = twoSplit[j].split("\\=|\\/");
                    if ("RQSPF".equals(firstSplit[0]) || "RFSF".equals(firstSplit[0]) || "DXF".equals(firstSplit[0])) {
                        for (int m = 1; m < threeSplit.length; m++) {
                            stringBuilder.append(addMatch(threeSplit[0]));
                            //让但是有时候返回的值确不包含{}，所以要进行判断
                            if (threeSplit[m].contains("{")) {
                                String[] fourSplit = threeSplit[m].split("\\{");
                                if ("DXF".equals(firstSplit[0])) {
                                    stringBuilder.append("|" + LotteryTypes.getDxf(fourSplit[0]));
                                } else {
                                    stringBuilder.append("|" + LotteryTypes.getRqSpf(fourSplit[0]));
                                }
                                stringBuilder.append("{" + fourSplit[1]);
                            } else {
                                String[] fourSplit = threeSplit[m].split("\\(");
                                if ("DXF".equals(firstSplit[0])) {
                                    stringBuilder.append("|" + LotteryTypes.getDxf(fourSplit[0]) + "(" + fourSplit[1]);
                                } else {
                                    stringBuilder.append("|" + LotteryTypes.getRqSpf(fourSplit[0]) + "(" + fourSplit[1]);
                                }
                                //stringBuilder.append("(" + fourSplit[1]);
                            }
                            stringBuilder.append("\n");
                        }
                    } else {
                        for (int m = 1; m < threeSplit.length; m++) {
                            stringBuilder.append(addMatch(threeSplit[0]));
                            String[] fourSplit = threeSplit[m].split("\\(");
                            if ("BQC".equals(firstSplit[0])) {
                                playsResult = LotteryTypes.getBqcStr(fourSplit[0]);
                            } else if ("SPF".equals(firstSplit[0]) || "SF".equals(firstSplit[0])) {
                                playsResult = LotteryTypes.getSpf(fourSplit[0]);
                            } else if ("SFC".equals(firstSplit[0])) {
                                playsResult = LotteryTypes.getSfcStr(fourSplit[0]);
                            } else if ("JQS".equals(firstSplit[0])) {
                                if ("7".equals(fourSplit[0])) {
                                    playsResult = "7+";
                                } else {
                                    playsResult = fourSplit[0];
                                }
                            } else {
                                if ("9:0".equals(fourSplit[0])) {
                                    playsResult = "胜其他";
                                } else if ("9:9".equals(fourSplit[0])) {
                                    playsResult = "平其他";
                                } else if ("0:9".equals(fourSplit[0])) {
                                    playsResult = "负其他";
                                } else {
                                    playsResult = fourSplit[0];
                                }
                            }
                            stringBuilder.append("|" + playsResult + "(" + fourSplit[1]);
                            //stringBuilder.append("(" + fourSplit[1] + ")");
                            stringBuilder.append("\n");
                        }
                    }
                }
                mOutTicketField.setText(stringBuilder);
            } else {
                //混合
                String[] twoSplit = firstSplit[1].split("\\,");
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < twoSplit.length; j++) {
                    String[] threeSplit = twoSplit[j].split("\\>|\\=|\\/");
                    if ("RQSPF".equals(threeSplit[0]) || "RFSF".equals(threeSplit[0]) || "DXF".equals(threeSplit[0])) {
                        for (int m = 2; m < threeSplit.length; m++) {
                            stringBuilder.append(addMatch(threeSplit[1]));
                            if (threeSplit[m].contains("{")) {
                                String[] fourSplit = threeSplit[m].split("\\{");
                                //让胜 让平 让负
                                if ("DXF".equals(threeSplit[0])) {
                                    stringBuilder.append("|" + LotteryTypes.getDxf(fourSplit[0]));
                                } else {
                                    stringBuilder.append("|" + LotteryTypes.getRqSpf(fourSplit[0]));
                                }
                                //让球（分）数
                                stringBuilder.append("{" + fourSplit[1]);
                            } else {
                                String[] fourSplit = threeSplit[m].split("\\(");
                                //让胜 让平 让负
                                if ("DXF".equals(threeSplit[0])) {
                                    stringBuilder.append("|" + LotteryTypes.getDxf(fourSplit[0]) + "(" + fourSplit[1]);
                                } else {
                                    stringBuilder.append("|" + LotteryTypes.getRqSpf(fourSplit[0]) + "(" + fourSplit[1]);
                                }
                                //让球（分）数
                                // stringBuilder.append("(" + fourSplit[1]);
                            }
                            stringBuilder.append("\n");
                        }
                    } else {

                        for (int m = 2; m < threeSplit.length; m++) {
                            stringBuilder.append(addMatch(threeSplit[1]));
                            String[] fourSplit = threeSplit[m].split("\\(");
                            if ("BQC".equals(threeSplit[0])) {
                                playsResult = LotteryTypes.getBqcStr(fourSplit[0]);
                            } else if ("SPF".equals(threeSplit[0]) || "SF".equals(threeSplit[0])) {
                                playsResult = LotteryTypes.getSpf(fourSplit[0]);
                            } else if ("SFC".equals(threeSplit[0])) {
                                playsResult = LotteryTypes.getSfcStr(fourSplit[0]);
                            } else if ("JQS".equals(threeSplit[0])) {
                                if ("7".equals(fourSplit[0])) {
                                    playsResult = "7+";
                                } else {
                                    playsResult = fourSplit[0];
                                }
                            } else {
                                if ("9:0".equals(fourSplit[0])) {
                                    playsResult = "胜其他";
                                } else if ("9:9".equals(fourSplit[0])) {
                                    playsResult = "平其他";
                                } else if ("0:9".equals(fourSplit[0])) {
                                    playsResult = "负其他";
                                } else {
                                    playsResult = fourSplit[0];
                                }
                            }
                            stringBuilder.append("|" + playsResult + "(" + fourSplit[1]);
                            //stringBuilder.append("(" + fourSplit[1] + ")");

                            stringBuilder.append("\n");
                        }
                    }
                }
                mOutTicketField.setText(stringBuilder);
            }
        }
    }


    //分割选择比赛的内容
    private void sdfdsd(String[] split1, List<StringBuilder> list) {
        GameList gameList = mapSort.get(split1[1]);
        String presetScore = gameList.getPresetScore();//大小分
        //把等号后面的字符串进行分离
        String[] split2 = split1[2].split("\\/");
        for (int j = 0; j < split2.length; j++) {
            StringBuilder strBuilder = new StringBuilder();
            //先拼接玩法
            strBuilder.append(LotteryTypes.getPlayMethod(split1[0]));
            //拼接玩法结果
            String[] split3 = split2[j].split("\\(|\\)");
            if ("BQC".equals(split1[0])) {
                playsResult = LotteryTypes.getBqcStr(split3[0]);
            } else if ("SPF".equals(split1[0]) || "SF".equals(split1[0])) {
                playsResult = LotteryTypes.getSpf(split3[0]);
            } else if ("DXF".equals(split1[0])) {
                if (!TextUtils.isEmpty(presetScore)) {
                    String s = (presetScore.replaceAll("\\+", "")).replaceAll("\\,", "  ");
                    playsResult = LotteryTypes.getDxf(split3[0]) + "{" + s + "}";
                } else {
                    playsResult = LotteryTypes.getDxf(split3[0]);
                }
            } else if ("JQS".equals(split1[0])) {
                if ("7".equals(split3[0])) {
                    playsResult = "7+";
                } else {
                    playsResult = split3[0];
                }
            } else if ("SFC".equals(split1[0])) {
                playsResult = LotteryTypes.getSfcStr(split3[0]);
            } else {
                if ("9:0".equals(split3[0])) {
                    playsResult = "胜其他";
                } else if ("9:9".equals(split3[0])) {
                    playsResult = "平其他";
                } else if ("0:9".equals(split3[0])) {
                    playsResult = "负其他";
                } else {
                    playsResult = split3[0];
                }
            }
            strBuilder.append(playsResult);
            //拼接赔率
            strBuilder.append("(" + split3[1] + ")");
            list.add(strBuilder);
        }
        map.put(split1[1], list);
    }

    // 填充控件
    public void addWidge(List<Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>>> list) {
        if (data == null) return;
        String mBuycontent = data.getOrderOriginal().getBetContent();
        String[] split = mBuycontent.split("\\;");
        HashMap<String, String> bunchMap = new HashMap<>();
        for (int j = 0; j < split.length; j++) {
            String[] strTimes = split[j].split("\\|");
            //填充过关方式
            String[] passType = strTimes[2].split("\\,");
            for (int i = 0; i < passType.length; i++) {
                String[] bunch = passType[i].split("\\*|\\_");
                bunchMap.put(bunch[0] + "串" + bunch[1], "");//利用hashmap集合去重
            }
        }
        StringBuilder passTypeStrBuilder = new StringBuilder();
        passTypeStrBuilder.append("过关方式：");
        for (Map.Entry<String, String> entry : bunchMap.entrySet()) {
            passTypeStrBuilder.append(entry.getKey() + "  ");
        }
        mPassType.setText(passTypeStrBuilder);
        for (Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>> entry : list) {
            GendanDetailData.DataBean.GameListBean key = entry.getKey();
            View view = View.inflate(this, R.layout.order_form_vs, null);
            view.setTag(key.getuMid());
            view.setOnClickListener(MatcherListener);
            mMatchTitle = (TextView) view.findViewById(R.id.matches_title);
            mVsDetail = (LinearLayout) view.findViewById(R.id.vs_detail);
            mStub = (TextView) view.findViewById(R.id.stub);
            mVsTitle = (TextView) view.findViewById(R.id.vs_title);
            mVsTimes.addView(view);
            for (int j = 0; j < entry.getValue().size(); j++) {
                //判断是否中奖
                GendanDetailData.DataBean.GameListBean dataBean = entry.getKey();
                String str = entry.getValue().get(j);

                TextView textView = new TextView(this);
                SpannableStringBuilder ssb = IsWinnig.isWinning2(dataBean, str, mLid + "");
                textView.setText(ssb);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                int dip2px = DisplayUtil.dip2px(5);
                textView.setPadding(dip2px, dip2px, dip2px, dip2px);
                textView.setBackgroundResource(R.drawable.table_shape);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams);
                mVsDetail.addView(textView);
            }
            //填充场次
            mMatchTitle.setText(addMatch(entry.getKey().getSession()));
            //填充比分和谁跟谁比赛
            addScore(entry.getKey().getSession());
        }
    }

    // 将年月日转换成星期
    private String addMatch(String split) {
        String substring = split.substring(0, 8);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date parse = format.parse(substring);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            String substring1 = split.substring(8, 11);
            return weekDays[i1 - 1] + substring1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    //最总比赛结果的比分
    private void addScore(String split) {
        String title;
        if (43 == mLid || 1001 == mLid) {
            mZhuKeTitle.setText("客队vs主队/投注选项");
            if (danList.contains(split)) {
                title = mapSort.get(split).getGuestShortCn() + "VS" + mapSort.get(split).getHomeShortCn() + "(胆)";
            } else {
                title = mapSort.get(split).getGuestShortCn() + "VS" + mapSort.get(split).getHomeShortCn();
            }
        } else {
            mZhuKeTitle.setText("主队vs客队/投注选项");
            if (danList.contains(split)) {
                title = mapSort.get(split).getHomeShortCn() + "VS" + mapSort.get(split).getGuestShortCn() + "(胆)";
            } else {
                title = mapSort.get(split).getHomeShortCn() + "VS" + mapSort.get(split).getGuestShortCn();
            }
        }
        int vs = title.indexOf("VS");
        SpannableStringBuilder ssb = new SpannableStringBuilder(title);
        ssb.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, DisplayUtil.sp2px(11), ColorStateList.valueOf(Color.RED), null), vs, vs + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (danList.contains(split)) {
            int dan = title.indexOf("(胆)");
            ssb.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, DisplayUtil.sp2px(11), ColorStateList.valueOf(Color.RED), null), dan, dan + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mVsTitle.setText(ssb);
        String score;
        if (mLid == 43 || 1001 == mLid) {
            score = mapSort.get(split).getScore();
        } else {
            score = mapSort.get(split).getFullScore();
        }
        if (TextUtils.isEmpty(score) || "null".equals(score)) {
            String liveScore = mapSort.get(split).getLiveScore();
            if (TextUtils.isEmpty(liveScore)) {
                mStub.setText("未开赛");
            } else mStub.setText("比赛中\n" + liveScore);
        } else mStub.setText(score);
    }

    //对已经封装好的填充控件用的集合进行排序
    private List<Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>>> orderMap(HashMap<GendanDetailData.DataBean.GameListBean, ArrayList<String>> widgeMap) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>>> list = new ArrayList<>(widgeMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>> lhs, Map.Entry<GendanDetailData.DataBean.GameListBean, ArrayList<String>> rhs) {
                return lhs.getKey().getSession().compareTo(rhs.getKey().getSession());
            }
        });

        return list;
    }

    private void numberLotteryData(final String mBuycontent) {
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("lotCode", mLid + "");
        params.putString("issueNo", mIssueNo);
        mHttpInterface.get(Url.WIN_NUMBER_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject mObject = new JSONObject(result);
                    JSONObject data = mObject.getJSONObject("data");
                    String awardNumber = data.getString("awardNumber");
                    if (!TextUtils.isEmpty(awardNumber) && !"null".equals(awardNumber)) {
                        mWinNumber.setAwardNum(awardNumber, mLid + "");
                        mBuyNumber.setBuyContent(awardNumber, mBuycontent, mLid + "");
                    } else {
                        mBuyNumber.setBuyContent("", mBuycontent, mLid + "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private int pn = 1;
    private int ps = 10;
    //private int totalRecords;//跟单人次
    private String commission;//提成金额

    private void joinData(final LoadingDialogUtils loadingDialogUtils) {
        Bundle params = new Bundle();
        params.putString("orderId", mOrderId);
        params.putString("pn", pn + "");
        params.putString("ps", ps + "");
        mHttp.get(Url.JOINER_URL + joinerUrl, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                JoinData joinData = gson.fromJson(result, JoinData.class);
                int code = joinData.getCode();
                if (code == 0) {
                    JoinData.DataBean data = joinData.getData();
                    int totalPages = data.getTotalPages();
                    int totalRecords = data.getTotalRecords();
                    if (TextUtils.isEmpty(commission))
                        mJoinTitle.setText("参与人次 " + totalRecords + "次");
                    else mJoinTitle.setText("参与人次 " + totalRecords + "次 提成金额" + commission + "元");
                    List<JoinData.DataBean.ListBean> joinList = data.getList();
                    if (totalPages > 1) {
                        mLookMore.setVisibility(View.VISIBLE);
                    } else {
                        mLookMore.setVisibility(View.GONE);
                    }
                    JoinerAdapter mAdapter = new JoinerAdapter(joinList);
                    mListView.setAdapter(mAdapter);
                } else {
                    // Toast.makeText(mContext,joinData.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    View.OnClickListener MatcherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LotCode.JCZQ_D_CODE.equals(mLid + "") || LotCode.JCZQ_CODE.equals(mLid + "")) {
                String mId = (String) v.getTag();
                if (TextUtils.isEmpty(mId)) return;
                Bundle bundle = new Bundle();
                bundle.putString("mId", mId);
                startActivity(ScoreDetailActivity.class, bundle);
            }
        }
    };
    View.OnClickListener ImageUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mUserId == null) return;
            Bundle bundle = new Bundle();
            bundle.putString("userId", mUserId);
            startActivity(GodInfoActivity.class, bundle);
            finish();
        }
    };
    View.OnClickListener LookMoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("orderId", mOrderId);
            bundle.putString("joinerUrl", joinerUrl);
            startActivity(JoinerActivity.class, bundle);
        }
    };
    View.OnClickListener ShareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences userinfo = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            String shareUrl = userinfo.getString("html_header", "") + "/order_copy/detail/";
            String title;
            String url;
            String text;
            if (mFlag) {
                title = mItemsBean.getUserName() + "发起了推荐！";
                url = shareUrl + mOrderId;
                text = "宣言：" + manifesto;
            } else {
                title = mItemsBean.getUserName() + "发起了合买！";
                url = Url.H5HEMAI_URL + mOrderId;
                text = "跟着大师中大奖！";
            }
            Share share = new Share(mContext);
            share.showShare(title, text, url);
        }
    };
    View.OnClickListener MultilFastListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.one:
                    mEdit_mutil.setText("20");
                    break;
                case R.id.two:
                    mEdit_mutil.setText("50");
                    break;
                case R.id.three:
                    mEdit_mutil.setText("100");
                    break;
                case R.id.four:
                    mEdit_mutil.setText("200");
                    break;

            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case View.VISIBLE:
                    mLl_mutile.setVisibility(View.VISIBLE);
                    break;
                case View.GONE:
                    mLl_mutile.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            handler.sendEmptyMessage(View.VISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            handler.sendEmptyMessage(View.GONE);
        }
    }
}
