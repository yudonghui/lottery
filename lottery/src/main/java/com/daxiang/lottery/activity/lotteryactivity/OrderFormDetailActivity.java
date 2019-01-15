package com.daxiang.lottery.activity.lotteryactivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.activity.BonusRuleActivity;
import com.daxiang.lottery.activity.BuyActivity;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.NumberActivity;
import com.daxiang.lottery.common.IntentSkip;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.GameList;
import com.daxiang.lottery.entity.OrderOriginal;
import com.daxiang.lottery.entity.OutTicketDetailData;
import com.daxiang.lottery.entity.TogetherBuy;
import com.daxiang.lottery.score.ScoreDetailActivity;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.IsWinnig;
import com.daxiang.lottery.utils.ScreenshotUtils;
import com.daxiang.lottery.utils.Share;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.FloatViewLayout;
import com.daxiang.lottery.view.LotteryTextView;
import com.daxiang.lottery.view.TitleBar;
import com.daxiang.lottery.view.autotextview.AutofitTextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONArray;
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


public class OrderFormDetailActivity extends BaseNoTitleActivity {

    private String mOrderId;
    private String mOrderCode;
    private int mStatus;
    private String mAllmoney;
    private String mBuycontent;
    private String mLotCode;
    private String mIssue;
    private String mMargin;//总中奖金额（包括乐善）
    private long mCreattime;
    private TitleBar mTitleBar;
    private TextView mTextLottype;
    //  private TextView mContinueBuy;
    private TextView mTextIssue;
    private TextView mTextAllmoney;
    private TextView mTextTickeState;
    private TextView mTextTaxMoney;
    private TextView mTextSchemeNumber;
    private TextView mTextCreatTime;
    private TextView mDuizhenTile;
    private LotteryTextView mWinNumber;
    private TextView mLotteryState;
    private TextView mbuy_lottery_content;
    private TableLayout mNumberGame;
    private LinearLayout mFightLottery;
    private LinearLayout mVsTimes;
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
    private TextView mLookOutDetail;//查看出票详情
    private TextView mTitleBuyMoney;
    private TextView mTitleOutTicketStatus;
    private LinearLayout mLl_continue;//继续投注
    // //private TextView mContinueNum;//继续投注本号码
    // private TextView mContinueLotcode;//继续投注本彩种
    //  private TextView mContinueLotcodeJc;//继续投注本彩种竞彩
    private TextView mContinues;//继续支付或者继续购彩

    private FrameLayout mLl_share;
    private LinearLayout mLl_share2;
    private ImageView mLottery_logo;
    private TextView mLottery_name;
    private TextView mLottery_issue;
    private AutofitTextView mBuyMoney;
    private AutofitTextView mWinMoney;
    private AutofitTextView mWinRatio;
    private ImageView mTwo_code;
    private LinearLayout mLl_header;

    private TextView mShare_order;
    private View mShare_line;

    private TextView mHintOdds;
    private LinearLayout mVsDetail;
    private LinearLayout mOutTicketDetail;
    private View mOutTicketLine;
    private LinearLayout mLl_footer;
    private View mLine_footer;
    private LinearLayout mLlOutTicketDetail;
    private LotteryTextView mBuyNumber;
    private LinearLayout mLl_together;
    private ScrollView mScrollView;
    private ImageView mAvatar;
    private TextView mTogetherName;
    private ImageView mBonusImg;
    private ImageView mThanksgiving;
    private FloatViewLayout mFvl;
    Map<String, List<StringBuilder>> map = new HashMap<>();
    private List<GameList> mData = new ArrayList<>();
    private int colorBlack;
    private int colorRed;
    private int colorGray;
    //存胆的期号
    ArrayList<String> danList = new ArrayList<>();
    Map<String, GameList> mapSort;
    private List<OutTicketDetailData.DataBean> mOutTicketList;
    private String playsResult;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    Context mContext;
    private int orderType;//参与推荐，还是自购，还是发起推荐等。
    private Gson mGson;
    private String mGodUserId;//发单人uid
    private String mGodName;//发单人名字

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String cxWebUrl;
    private String extraMargin;//乐善奖金

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form_detail);
        mContext = OrderFormDetailActivity.this;
        Intent intent = getIntent();
        orderType = intent.getIntExtra("orderType", 999);
        mOrderCode = intent.getStringExtra("orderCode");
        mOrderId = intent.getStringExtra("orderId");
        mStatus = intent.getIntExtra("status", 0);
        mAllmoney = intent.getStringExtra("allmoney");
        mBuycontent = intent.getStringExtra("buycontent");
        mLotCode = intent.getStringExtra("lotCode");
        mIssue = intent.getStringExtra("issue");
        mCreattime = intent.getLongExtra("creattime", 0);
        mMargin = intent.getStringExtra("margin");
        extraMargin = intent.getStringExtra("extraMargin");
        /*extraMargin = "12.34";
        mMargin="30";*/
        SharedPreferences userinfo = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        cxWebUrl = userinfo.getString("html_header", "") + "/mobile/download";
        colorBlack = getResources().getColor(R.color.deep_txt);
        colorRed = getResources().getColor(R.color.red_txt);
        colorGray = getResources().getColor(R.color.gray_txt);
        initView();
        mGson = new Gson();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        addData();
        addListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebarDetail);
        mTitleBar.mShare.setVisibility(View.VISIBLE);
        // mContinueBuy = (TextView) findViewById(R.id.continue_buy);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mDuizhenTile = (TextView) findViewById(R.id.duizhen_title_hint);
        mTextLottype = (TextView) findViewById(R.id.lottype);
        mTextIssue = (TextView) findViewById(R.id.issue);
        mTextAllmoney = (TextView) findViewById(R.id.allmoney);
        mTextTickeState = (TextView) findViewById(R.id.ticke_state);
        mTextTaxMoney = (TextView) findViewById(R.id.tax_money);
        mTextSchemeNumber = (TextView) findViewById(R.id.scheme_number);
        mTextCreatTime = (TextView) findViewById(R.id.creat_time);
        mWinNumber = (LotteryTextView) findViewById(R.id.win_num);
        mNumberGame = (TableLayout) findViewById(R.id.number_game);
        mLotteryState = (TextView) findViewById(R.id.lottery_state);
        mBuyNumber = (LotteryTextView) findViewById(R.id.buy_num);
        mFightLottery = (LinearLayout) findViewById(R.id.fight_lottery);
        mLookOutDetail = (TextView) findViewById(R.id.look_out_detail);
        mOutTicketLine = findViewById(R.id.out_ticket_detail_line);
        mOutTicketDetail = (LinearLayout) findViewById(R.id.out_ticket_detail);
        mLlOutTicketDetail = (LinearLayout) findViewById(R.id.ll_outticket_detail);
        mTitleBuyContent = (TextView) findViewById(R.id.buy_content);
        mTitleBuyMutil = (TextView) findViewById(R.id.buy_mutil);
        mTitleBuyMoney = (TextView) findViewById(R.id.buy_money);
        mTitleOutTicketStatus = (TextView) findViewById(R.id.out_ticket_status);
        mLl_continue = (LinearLayout) findViewById(R.id.ll_continue);
        mContinues = (TextView) findViewById(R.id.continues);
        mVsTimes = (LinearLayout) findViewById(R.id.vs_times);
        mPassType = (TextView) findViewById(R.id.pass_type);
        mbuy_lottery_content = (TextView) findViewById(R.id.buy_lottery_content);
        mLl_together = (LinearLayout) findViewById(R.id.ll_together);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mTogetherName = (TextView) findViewById(R.id.togetherName);
        mHintOdds = (TextView) findViewById(R.id.hint_odds);
        mLl_footer = (LinearLayout) findViewById(R.id.ll_footer);
        mLine_footer = findViewById(R.id.line_footer);
        mThanksgiving = (ImageView) findViewById(R.id.thanksgiving);
        mBonusImg = (ImageView) findViewById(R.id.bonus_img);
        mFvl = (FloatViewLayout) findViewById(R.id.floatViewLayout);
        mTitleBar.setTitle("认购详情");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        if (mStatus == 100) mContinues.setText("继续付款");
        else mContinues.setText("继续购彩");
        mLl_share = (FrameLayout) findViewById(R.id.ll_share);
        mLottery_logo = (ImageView) findViewById(R.id.lottery_logo);
        mLottery_name = (TextView) findViewById(R.id.lottery_name);
        mLottery_issue = (TextView) findViewById(R.id.lottery_issue);
        mBuyMoney = (AutofitTextView) findViewById(R.id.buyMoney);
        mWinMoney = (AutofitTextView) findViewById(R.id.winMoney);
        mWinRatio = (AutofitTextView) findViewById(R.id.winRatio);
        mTwo_code = (ImageView) findViewById(R.id.two_code);
        mLl_header = (LinearLayout) findViewById(R.id.ll_header);
        mShare_line = findViewById(R.id.share_line);
        mShare_order = (TextView) findViewById(R.id.share_order);
        mLl_share2 = (LinearLayout) findViewById(R.id.ll_share2);
        if ("42".equals(mLotCode) || "43".equals(mLotCode)) {
            mFvl.setVisibility(View.VISIBLE);
        } else mFvl.setVisibility(View.GONE);

    }

    public void addData() {
        int resize = DisplayUtil.dip2px(200);
        Bitmap image = CodeUtils.createImage(cxWebUrl, resize, resize);
        if (image != null)
            mTwo_code.setImageBitmap(image);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(mCreattime);
        mTextLottype.setText(LotteryTypes.getTypes(mLotCode));
        mLottery_name.setText(LotteryTypes.getTypes(mLotCode));
        mLottery_logo.setImageResource(LotCode.getResId(mLotCode));
        mTextAllmoney.setText((TextUtils.isEmpty(mAllmoney) ? "--" : mAllmoney) + "元");
        mBuyMoney.setText((TextUtils.isEmpty(mAllmoney) ? "--" : (int) Double.parseDouble(mAllmoney)) + "元");
        mTextCreatTime.setText(time);
        mTextIssue.setText("第" + mIssue + "期");
        mLottery_issue.setText("第" + mIssue + "期");
        mTextTickeState.setText(LotteryTypes.getStatus(mStatus + ""));
        if (mStatus == 1000)
            mThanksgiving.setVisibility(View.VISIBLE);
        else
            mThanksgiving.setVisibility(View.GONE);
        mTextSchemeNumber.setText(mOrderCode);
     /*   extraMargin="0.00";
        mMargin="2222";*/
        if (TextUtils.isEmpty(extraMargin) || Float.parseFloat(extraMargin) <= 0) {
            if (!TextUtils.isEmpty(mMargin)) {
                if (mStatus == 2000) {
                    mTextTaxMoney.setText("派奖中");
                } else {
                    mTextTaxMoney.setText(mMargin + "元");
                    mWinMoney.setText((int) Double.parseDouble(mMargin) + "元");
                    int aas = (int) (Double.parseDouble(mMargin) * 100 / Double.parseDouble(mAllmoney));
                    mWinRatio.setText(aas + "%");
                }
            } else {
                mTextTaxMoney.setText("--");
            }
        } else {//乐善玩法，并且中奖.两种情况，一种投注码中奖，一种是投注码没有中奖。
            SpannableStringBuilder ssb;
            if (extraMargin.equals(mMargin)) {//投注号码没有中奖,乐善号码中奖
                String totalStr = "0.00元/" + extraMargin + "元\n(开奖码/乐善码)";
                ssb = new SpannableStringBuilder(totalStr);
                ssb.setSpan(new ForegroundColorSpan(colorBlack), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new ForegroundColorSpan(colorGray), totalStr.length() - 9, totalStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                double margin = Double.parseDouble(mMargin);
                double extramargin = Double.parseDouble(extraMargin);
                String totalStr = (margin - extramargin) + "元/" + extraMargin + "元\n(开奖码/乐善码)";
                ssb = new SpannableStringBuilder(totalStr);
                ssb.setSpan(new ForegroundColorSpan(colorGray), totalStr.length() - 9, totalStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            mTextTaxMoney.setText(ssb);
        }

        //订票详情
        if (LotCode.JCLQ_CODE.equals(mLotCode) || LotCode.JCLQ_D_CODE.equals(mLotCode) ||
                LotCode.JCZQ_CODE.equals(mLotCode) || LotCode.JCZQ_D_CODE.equals(mLotCode)) {
            mNumberGame.setVisibility(View.GONE);
            mFightLottery.setVisibility(View.VISIBLE);
            addDataJcOrder();
        } else {
            mNumberGame.setVisibility(View.VISIBLE);
            mFightLottery.setVisibility(View.GONE);
            addDataNumberOrder();
        }
    }

    private void addDataJcOrder() {//竞彩订票详情
        //订票详情
        HttpInterface2 mHttpInterface = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        final String substring = mOrderCode.substring(0, 2);
        String stype = null;
        switch (substring) {
            case "HM":
                stype = "togetherBuy/detail/v1";
                break;
            case "GC":
                stype = "scheme/detail/v1";

                break;
            case "FZ":
                stype = "followBuy/detail/v1";
                break;
        }
        params.putString("token", LotteryApp.token);
        params.putString("orderId", mOrderId);
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        mHttpInterface.get(Url.RECORD_DETAIL_URL + stype, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                /**
                 * 两种情况，一种是正常购买的信息。另外是推荐，追号，合买的信息
                 * */
                if ("GC".equals(substring)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code == 0) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            jsonArrayToBean(data);//将jsonarray转换成一个list集合并且排序填充控件
                        } else {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getInt("code") == 0) {
                            JSONObject jsondata = jsonObject.getJSONObject("data");
                            JSONObject orderOriginal = jsondata.getJSONObject("orderOriginal");
                            JSONArray gamesList = jsondata.getJSONArray("gameList");
                            JSONObject togetherBuy = jsondata.getJSONObject("togetherBuy");//跟的谁的单
                            TogetherBuy togetherBuyBean = mGson.fromJson(togetherBuy.toString(), TogetherBuy.class);
                            OrderOriginal orderOriginalBean = mGson.fromJson(orderOriginal.toString(), OrderOriginal.class);
                            if (mBuycontent == null || mBuycontent.length() == 0) {
                                mBuycontent = orderOriginalBean.getBetContent();
                            }
                            setTogetherView(togetherBuyBean);
                            jsonArrayToBean(gamesList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private void setTogetherView(TogetherBuy togetherBuyBean) {
        if (togetherBuyBean == null) return;
        if (orderType == 8 || orderType == 3) {//参与合买，参与跟单
            mLl_together.setVisibility(View.VISIBLE);
            mGodUserId = togetherBuyBean.getUserId();
            HttpUtils2.display(mAvatar, Url.HEADER_ROOT_URL + mGodUserId);
            mGodName = togetherBuyBean.getUserName();
            mTogetherName.setText(TextUtils.isEmpty(mGodName) ? "--" : mGodName);
        } else mLl_together.setVisibility(View.GONE);
    }

    private void jsonArrayToBean(JSONArray array) throws JSONException {
        if (array == null) return;
        mData.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            GameList gameList = mGson.fromJson(jsonObject.toString(), GameList.class);
            mData.add(gameList);
        }
        mapSort = new HashMap<>();
        for (int m = 0; m < mData.size(); m++) {
            mapSort.put(mData.get(m).getSession(), mData.get(m));
        }
        //选中的购买内容的封装成一个map集合 并排序
        List<Map.Entry<GameList, ArrayList<String>>> list = orderMap(table());
        //List<Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>>> list  = CollectionUtils.table(mBuycontent,mData);
        //填充控件
        addWidge(list);
    }

    private void addDataNumberOrder() {//数字彩订票详情
        mTitleBuyContent.setText("投注内容");
        mTitleBuyMutil.setText("投注金额");
        mTitleBuyMoney.setText("倍数");
        mTitleOutTicketStatus.setText("状态");

        if (TextUtils.isEmpty(mBuycontent)) {
            final String substring = mOrderCode.substring(0, 2);
            String stype = null;
            switch (substring) {
                case "HM":
                    stype = "togetherBuy/detail";
                    break;
                case "GC":
                    stype = "scheme/detail";
                    break;
                case "FZ":
                    stype = "followBuy/detail";
                    break;
            }
            final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
            HttpInterface2 mHttpInterface = new HttpUtils2(this);
            Bundle params = new Bundle();
            params.putString("orderId", mOrderId);
            params.putString("token", LotteryApp.token);
            mHttpInterface.get(Url.RECORD_DETAIL_URL + stype, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    loadingDialogUtils.setDimiss();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code == 0) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject orderOriginal = data.getJSONObject("orderOriginal");
                            mBuycontent = orderOriginal.getString("betContent");
                            if ("null".equals(mBuycontent)) {
                                mBuycontent = "";
                            }
                            if (LotCode.GJ_CODE.equals(mLotCode)) {
                                gjLotteryData();
                            } else {
                                numberLotteryData();
                            }
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError() {
                    loadingDialogUtils.setDimiss();
                }
            });
        } else {
            if (LotCode.GJ_CODE.equals(mLotCode)) {
                gjLotteryData();
            } else {
                numberLotteryData();
            }
        }
    }

    private boolean isShowOut = false;

    private void addDataJcOut() {//竞彩出票详情
        //出票详情(竞彩)
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttpInterface1 = new HttpUtils2(mContext);
        Bundle params1 = new Bundle();
        params1.putString("token", LotteryApp.token);
        params1.putString("orderId", mOrderId);
        mHttpInterface1.get(Url.OUT_TICKET_DETAIL_URL, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    OutTicketDetailData outTicketDetailData = gson.fromJson(result, OutTicketDetailData.class);
                    String msg = outTicketDetailData.getMsg();
                    if (outTicketDetailData.getCode() == 0) {
                        isShowOut = true;
                        mLlOutTicketDetail.setVisibility(View.VISIBLE);
                        mOutTicketLine.setVisibility(View.VISIBLE);
                        mLookOutDetail.setVisibility(View.GONE);
                        mOutTicketList = outTicketDetailData.getData();
                        outTicketTable();
                    } else Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private void addDataNumberOut() {//数字彩出票详情
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttpInterface1 = new HttpUtils2(mContext);
        Bundle params1 = new Bundle();
        params1.putString("token", LotteryApp.token);
        params1.putString("orderId", mOrderId);
        mHttpInterface1.get(Url.OUT_TICKET_DETAIL_URL, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    OutTicketDetailData outTicketDetailData = gson.fromJson(result, OutTicketDetailData.class);
                    String msg = outTicketDetailData.getMsg();
                    if (outTicketDetailData.getCode() == 0) {
                        isShowOut = true;
                        mLlOutTicketDetail.setVisibility(View.VISIBLE);
                        mOutTicketLine.setVisibility(View.VISIBLE);
                        mLookOutDetail.setVisibility(View.GONE);
                        mOutTicketList = outTicketDetailData.getData();
                        outTicketTableNumber();
                    } else Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    //订票详情
    /*
    * 将购买记录的字符串分割，然后组装成一个map集合。
    * map集合键是封装的带有期号，主队，客队等信息的一个实体类，值是拼接list集合
    * 用于存放每一期比赛选择玩法拼接的字符串。
    * */
    public HashMap<GameList, ArrayList<String>> table() {
        /**
         * 创建一个map集合，键是选中的实体类，
         * 里面包含有比赛的比分，等详细信息。值是拼接list集合
         * 用于存放每一期比赛选择玩法拼接的字符串。
         * */
        HashMap<GameList, ArrayList<String>> widgeMap = new HashMap<>();
        Log.e("订单显示串", mBuycontent);
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
                    // gameList.setPresetScore("252,212.5,209,203");
                    String presetScore = gameList.getPresetScore();//篮球的大小分。
                    String let = gameList.getLet();
                    for (int j = 1; j < split3.length; j++) {
                        StringBuilder mStringBuilder = new StringBuilder();
                        mStringBuilder.append(playMethod);
                        //HH|DXF>20180125301=3(1.80),RFSF>20180125301=3{-8.5}(1.81),RFSF>20180124309=3{-3.5}(1.75)/0{-3.5}(1.75)|2*1
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
                                // playsResult = LotteryTypes.getBqcStr(split[0]);
                            } else if ("SPF".equals(strTimes[0]) || "SF".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getSpf(split[0]) + "(" + split[1];
                                // playsResult = LotteryTypes.getSpf(split[0]);
                            } else if ("DXF".equals(strTimes[0])) {
                                if (!TextUtils.isEmpty(presetScore)) {
                                    String s = (presetScore.replaceAll("\\+", "")).replaceAll("\\,", "  ");
                                    playsResult = LotteryTypes.getDxf(split[0]) + "{" + s + "}" + "(" + split[1];
                                } else {
                                    playsResult = LotteryTypes.getDxf(split[0]) + "(" + split[1];
                                }
                                //  playsResult = LotteryTypes.getDxf(split[0]);
                            } else if ("JQS".equals(strTimes[0])) {
                                if ("7".equals(split[0])) {
                                    playsResult = "7+(" + split[1];
                                    // playsResult = "7+";
                                } else {
                                    playsResult = split[0] + "(" + split[1];
                                    // playsResult = split[0];
                                }
                            } else if ("SFC".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getSfcStr(split[0]) + "(" + split[1];
                                // playsResult = LotteryTypes.getSfcStr(split[0]);
                            } else {
                                if ("9:0".equals(split[0])) {
                                    playsResult = "胜其他(" + split[1];
                                    // playsResult = "胜其他(";
                                } else if ("9:9".equals(split[0])) {
                                    playsResult = "平其他(" + split[1];
                                    //playsResult = "平其他";
                                } else if ("0:9".equals(split[0])) {
                                    playsResult = "负其他(" + split[1];
                                    //  playsResult = "负其他";
                                } else {
                                    playsResult = split[0] + "(" + split[1];
                                    // playsResult = split[0];
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

    private void gjLotteryData() {//冠亚军开奖情况
        String type;
        if (mBuycontent.contains("GJ")) {
            type = "0";
        } else {
            type = "1";
        }
       /* mWinNumber.setAwardNum("01", mLotCode);
        mBuyNumber.setBuyContent("01", mBuycontent, mLotCode);*/
        mBuyNumber.setBuyContent("", mBuycontent, mLotCode);
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("type", type);
        mHttpInterface.get(Url.GJ_RESULT, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject mObject = new JSONObject(result);
                    JSONObject data = mObject.getJSONObject("data");
                    JSONObject items = data.getJSONObject("items");
                    String awardNumber = items.getString("team_code");
                    if (!TextUtils.isEmpty(awardNumber) && !"null".equals(awardNumber)) {
                        mWinNumber.setAwardNum(awardNumber, mBuycontent, mLotCode);
                        mBuyNumber.setBuyContent(awardNumber, mBuycontent, mLotCode);
                    } else {
                        mBuyNumber.setBuyContent("", mBuycontent, mLotCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                mBuyNumber.setBuyContent("", mBuycontent, mLotCode);
            }
        });
           /* mBuyNumber.setLotId(Integer.parseInt(mLotCode));
            mBuyNumber.setBuyContent(mBuycontent);*/
        mbuy_lottery_content.setHeight(mBuyNumber.getHeight());
        mLotteryState.setText(LotteryTypes.getStatus(mStatus + ""));
        if (mStatus == 1000)
            mThanksgiving.setVisibility(View.VISIBLE);
        else
            mThanksgiving.setVisibility(View.GONE);
    }

    private void numberLotteryData() {//数字彩开奖情况
        mBuyNumber.setBuyContent("", mBuycontent, mLotCode);
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("lotCode", mLotCode);
        params.putString("issueNo", mIssue);
        mHttpInterface.get(Url.WIN_NUMBER_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject mObject = new JSONObject(result);
                    JSONObject data = mObject.getJSONObject("data");
                    String awardNumber = data.getString("awardNumber");
                  /*  awardNumber="0,3,3,3,3,0,3,3,0,3,0,3,*,0";
                    mBuycontent="#,#,3,3,3,0,3,#,#,3,0,3,3,#:1:1";
                    mLotCode="19";*/
                    if (!TextUtils.isEmpty(awardNumber) && !"null".equals(awardNumber)) {
                        mWinNumber.setAwardNum(awardNumber, mLotCode);
                        mBuyNumber.setBuyContent(awardNumber, mBuycontent, mLotCode);
                    } else {
                        mBuyNumber.setBuyContent("", mBuycontent, mLotCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });
           /* mBuyNumber.setLotId(Integer.parseInt(mLotCode));
            mBuyNumber.setBuyContent(mBuycontent);*/
        mbuy_lottery_content.setHeight(mBuyNumber.getHeight());
        mLotteryState.setText(LotteryTypes.getStatus(mStatus + ""));
        if (mStatus == 1000)
            mThanksgiving.setVisibility(View.VISIBLE);
        else
            mThanksgiving.setVisibility(View.GONE);
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
            String extraContent = mOutTicketList.get(i).getExtraContent();//乐善玩法
            String extraMargin = mOutTicketList.get(i).getExtraMargin();//乐善奖金
            String buyContent;
            if (pureContent.contains("|")) {
                buyContent = pureContent.split("\\|")[1];
            } else buyContent = pureContent;
            StringBuilder stringBuilder = new StringBuilder();
            //  if (!TextUtils.isEmpty(extraContent)) {//大乐透的乐善玩法

            // }

            if (buyContent.contains("$")) {
                stringBuilder.append("[投注码]");
                if (buyContent.contains("-")) {//这个是有蓝球和红球的胆拖。
                    String[] split = buyContent.split("\\-");
                    if (split[0].contains("$")) {
                        String[] split1 = split[0].split("\\$");
                        stringBuilder.append("胆:" + split1[0] + "拖:" + split1[1] + " | ");
                    } else {
                        stringBuilder.append(split[0] + " | ");
                    }
                    if (split[1].contains("$")) {
                        String[] split1 = split[1].split("\\$");
                        stringBuilder.append("胆:" + split1[0] + "拖:" + split1[1]);
                    } else {
                        stringBuilder.append(split[1]);
                    }
                } else {//这个是只有红球的胆拖。
                    String[] split = buyContent.split("\\$");
                    stringBuilder.append("胆:" + split[0] + "拖:" + split[1]);
                }
            } else {

                if (LotCode.GJ_CODE.equals(mLotCode)) {
                    if (TextUtils.isEmpty(mBuycontent)) return;
                    String title;
                    String logo;
                    if (mBuycontent.contains("GYJ")) {
                        title = "[冠亚军]";
                        logo = "GYJ";
                    } else {
                        title = "[冠军]";
                        logo = "GJ";
                    }
                    stringBuilder.append(title);
                    String[] split = buyContent.split("\\,");
                    for (int j = 0; j < split.length; j++) {
                        stringBuilder.append(mWinNumber.getName(split[j], logo));
                        if (j < split.length - 1) stringBuilder.append(",");
                    }
                } else {
                    stringBuilder.append("[投注码]");
                    stringBuilder.append(buyContent.replace("-", " | "));
                }
            }
            if (!TextUtils.isEmpty(extraContent)) {
                String buyStr = stringBuilder.toString();
                int start = stringBuilder.length();
                stringBuilder.append("\n[乐善码]");
                String lesan = extraContent.replace("+", " | ");
                stringBuilder.append(lesan);
                String[] extra = extraContent.split("\\+");
                String[] buy = buyStr.split("\\|");
                String[] red = extra[0].split(" ");
                String[] blue = extra[1].split(" ");
                SpannableStringBuilder ssb = new SpannableStringBuilder(stringBuilder);

                for (int j = 0; j < red.length; j++) {
                    if (buy[0].contains(red[j])) {
                        int indexOf = buy[0].indexOf(red[j]);
                        ssb.setSpan(new ForegroundColorSpan(colorRed),
                                indexOf, indexOf + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                for (int j = 0; j < blue.length; j++) {
                    if (buy[1].contains(blue[j])) {
                        int indexOf = buy[0].length() + buy[1].indexOf(blue[j])+1;
                        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue_txt)),
                                indexOf, indexOf + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                ssb.setSpan(new ForegroundColorSpan(colorBlack), start, start + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new ForegroundColorSpan(colorBlack), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mOutTicketField.setText(ssb);
            } else {
                int indexOf = stringBuilder.indexOf("]");
                SpannableStringBuilder ssb = new SpannableStringBuilder(stringBuilder);
                ssb.setSpan(new ForegroundColorSpan(colorBlack), 0, indexOf + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //数字彩购买内容
                mOutTicketField.setText(ssb);
            }
            //数字彩购买倍数
            mOutTicketTimes.setText(mOutTicketList.get(i).getMulti());
            //数字彩购买金额
            mOutTicketMethod.setText((int) Double.parseDouble(mOutTicketList.get(i).getTotalMoney()) + "");
            /*
        * 设置状态，
        * 如果出票成功了，并且中奖了显示中奖金额，并且显示为红色
        * 否则，显示出票的状态，显示为灰色
        * */
            String margin = mOutTicketList.get(i).getCenterMargin();
            String printStatus = mOutTicketList.get(i).getPrintStatus();
          /*  printStatus="2";
            extraMargin="0.00";
            margin="2222";*/
            if (TextUtils.isEmpty(extraContent)) {
                if (!TextUtils.isEmpty(margin) && Float.parseFloat(margin) > 0 && "2".equals(printStatus)) {
                    mOutTicketReward.setTextColor(Color.RED);
                    mOutTicketReward.setText(mOutTicketList.get(i).getCenterMargin());
                } else {
                    mOutTicketReward.setTextColor(Color.GRAY);
                    mOutTicketReward.setText(LotteryTypes.getPrintStatus(printStatus));
                }
            } else {
               /* margin = "100";
                extraMargin = "12";*/
                if (!TextUtils.isEmpty(margin) && Float.parseFloat(margin) > 0 && "2".equals(printStatus)) {//出票成功，并且中奖
                    mOutTicketReward.setTextColor(colorRed);
                    SpannableStringBuilder ssb;
                    if (TextUtils.isEmpty(extraMargin) || Float.parseFloat(extraMargin) <= 0) {//投注码中奖，但是乐善码没有中奖
                        String totalStr = margin + "元/" + "0.00元\n(开奖码/乐善码)";
                        ssb = new SpannableStringBuilder(totalStr);
                        ssb.setSpan(new ForegroundColorSpan(colorBlack), totalStr.length() - 16, totalStr.length() - 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ssb.setSpan(new ForegroundColorSpan(colorGray), totalStr.length() - 9, totalStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (margin.equals(extraMargin)) {//总奖金和乐善奖金相同，说明投注没有中奖，乐善中奖
                        String totalStr = "0.00元/" + extraMargin + "元\n(开奖码/乐善码)";
                        ssb = new SpannableStringBuilder(totalStr);
                        ssb.setSpan(new ForegroundColorSpan(colorBlack), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ssb.setSpan(new ForegroundColorSpan(colorGray), totalStr.length() - 9, totalStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {//都中奖了
                        double v = Double.parseDouble(margin);//总奖金
                        double v1 = Double.parseDouble(extraMargin);//乐善奖金
                        String aaa = (v - v1) + "元";
                        String totalStr = (v - v1) + "元/" + extraMargin + "元\n(开奖码/乐善码)";
                        ssb = new SpannableStringBuilder(totalStr);
                        ssb.setSpan(new ForegroundColorSpan(colorBlack), aaa.length(), aaa.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ssb.setSpan(new ForegroundColorSpan(colorGray), totalStr.length() - 9, totalStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    mOutTicketReward.setText(ssb);

                } else {
                    mOutTicketReward.setTextColor(Color.GRAY);
                    mOutTicketReward.setText(LotteryTypes.getPrintStatus(printStatus));
                }
            }
        }
    }

    //出票详情（竞彩）
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
        // gameList.setPresetScore("252,212.5,209,203");
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
    public void addWidge(List<Map.Entry<GameList, ArrayList<String>>> list) {
        String[] split = mBuycontent.split("\\;");
        // for (int n = 0; n < split.length; n++) {
        HashMap<String, String> bunchMap = new HashMap<>();
        for (int j = 0; j < split.length; j++) {
            String[] strTimes = split[j].split("\\|");
            //填充过关方式
            String[] passType = strTimes[2].split("\\,");
            for (int i = 0; i < passType.length; i++) {
                String[] bunch = passType[i].split("\\*|\\_");
                bunchMap.put(bunch[0] + "串" + bunch[1], "");
            }
        }
        StringBuilder passTypeStrBuilder = new StringBuilder();
        passTypeStrBuilder.append("过关方式：");
        for (Map.Entry<String, String> entry : bunchMap.entrySet()) {
            passTypeStrBuilder.append(entry.getKey() + "   ");
        }
        mPassType.setText(passTypeStrBuilder);
        if ("43".equals(mLotCode)) {
            mDuizhenTile.setText("客队vs主队/投注选项");
        } else {
            mDuizhenTile.setText("主队vs客队/投注选项");
        }
        for (Map.Entry<GameList, ArrayList<String>> entry : list) {
            //判断是否中奖
            GameList dataBean = entry.getKey();
            View view = View.inflate(this, R.layout.order_form_vs, null);
            view.setTag(dataBean.getuMid());
            view.setOnClickListener(MatcherListener);
            mMatchTitle = (TextView) view.findViewById(R.id.matches_title);
            mVsDetail = (LinearLayout) view.findViewById(R.id.vs_detail);
            mStub = (TextView) view.findViewById(R.id.stub);
            mVsTitle = (TextView) view.findViewById(R.id.vs_title);
            mVsTimes.addView(view);
            for (int j = 0; j < entry.getValue().size(); j++) {
                String str = entry.getValue().get(j);
                if (str == null) return;
                TextView textView = new TextView(this);
                SpannableStringBuilder ssb = IsWinnig.isWinning2(dataBean, str, mLotCode);
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
            return "";
        }
    }

    //最总比赛结果的比分
    private void addScore(String split) {
        String title;
        String awary = mapSort.get(split).getGuestShortCn();
        String home = mapSort.get(split).getHomeShortCn();
        /*
        * 竞彩足球的主队在前面，客队在后面
        * 竞彩篮球的主队在后面，客队在前面
        * */
        if ("43".equals(mLotCode)) {
            if (danList.contains(split)) {
                title = awary + "VS" + home + "(胆)";
            } else {
                title = awary + "VS" + home;
            }
        } else {
            if (danList.contains(split)) {
                title = home + "VS" + awary + "(胆)";
            } else {
                title = home + "VS" + awary;
            }
        }
        int vs = title.indexOf("VS");
        SpannableStringBuilder ssb = new SpannableStringBuilder(title);
        ssb.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, DisplayUtil.sp2px(11), ColorStateList.valueOf(getResources().getColor(R.color.red_theme)), null), vs, vs + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (danList.contains(split)) {
            int dan = title.indexOf("(胆)");
            ssb.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, DisplayUtil.sp2px(11), ColorStateList.valueOf(getResources().getColor(R.color.red_theme)), null), dan, dan + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mVsTitle.setText(ssb);

        GameList dataBean = mapSort.get(split);
        String score;
        if ("43".equals(mLotCode) || "1001".equals(mLotCode)) {
            score = dataBean.getScore();
            mVsTitle.setTextColor(getResources().getColor(R.color.deep_txt));
        } else {
            score = dataBean.getFullScore();
            mVsTitle.setTextColor(getResources().getColor(R.color.blue_txt));
        }
        if (TextUtils.isEmpty(score) || "null".equals(score)) {
            String liveScore = dataBean.getLiveScore();
            if (TextUtils.isEmpty(liveScore)) {
                mStub.setText("未开赛");
            } else mStub.setText("比赛中\n" + liveScore);
        } else mStub.setText(score);

    }

    //对已经封装好的填充控件用的集合进行排序
    private List<Map.Entry<GameList, ArrayList<String>>> orderMap(HashMap<GameList, ArrayList<String>> widgeMap) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<GameList, ArrayList<String>>> list = new ArrayList<>(widgeMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<GameList, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<GameList, ArrayList<String>> lhs, Map.Entry<GameList, ArrayList<String>> rhs) {
                return lhs.getKey().getSession().compareTo(rhs.getKey().getSession());
            }
        });

        return list;
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.mShare.setOnClickListener(ShareClick);
        //是否展开出票详情
        mLookOutDetail.setOnClickListener(LookOutDetailListener);
        //继续投注本彩种
        mContinues.setOnClickListener(ContinueLotcodeListener);
        //如果是参与推荐.这里显示发单人
        mLl_together.setOnClickListener(TogetherListener);
        //奖金算法
        mBonusImg.setOnClickListener(BonusImgListener);
        //奖金算法里面控件移动的监听
        mFvl.setMove(ForecastMoveListener);
    }

    FloatViewLayout.MoveListener ForecastMoveListener = new FloatViewLayout.MoveListener() {
        @Override
        public void moveLeft(int left) {//控件离左边的距离

        }
    };
    View.OnClickListener MatcherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LotCode.JCZQ_D_CODE.equals(mLotCode) || LotCode.JCZQ_CODE.equals(mLotCode)) {
                String mId = (String) v.getTag();
                if (TextUtils.isEmpty(mId)) return;
                Bundle bundle = new Bundle();
                bundle.putString("mId", mId);
                startActivity(ScoreDetailActivity.class, bundle);
            }
        }
    };

    View.OnClickListener BonusImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("lotCode", mLotCode);
            startActivity(BonusRuleActivity.class, bundle);
        }
    };
    View.OnClickListener TogetherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mGodUserId == null) return;
            Bundle bundle = new Bundle();
            bundle.putString("userId", mGodUserId);
            startActivity(GodInfoActivity.class, bundle);
        }
    };
    View.OnClickListener LookOutDetailListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LotCode.JCLQ_CODE.equals(mLotCode) || LotCode.JCLQ_D_CODE.equals(mLotCode) ||
                    LotCode.JCZQ_CODE.equals(mLotCode) || LotCode.JCZQ_D_CODE.equals(mLotCode)) {
                addDataJcOut();
            } else {
                addDataNumberOut();
            }
        }
    };
    View.OnClickListener ContinueLotcodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mStatus == 100) {//待付款
                continuePayment();
            } else {
                if ("11".equals(mLotCode) || "19".equals(mLotCode)
                        || "42".equals(mLotCode) || "43".equals(mLotCode)
                        || "1000".equals(mLotCode) || "1001".equals(mLotCode)) {
                    IntentSkip intentSkip = new IntentSkip();
                    intentSkip.skipLotcode(mContext, mLotCode);
                } else {
                    if (!TextUtils.isEmpty(mBuycontent)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("lotcode", mLotCode);
                        bundle.putString("who", "continue");
                        bundle.putString("mBuycontent", mBuycontent);
                        startActivity(NumberActivity.class, bundle);
                    }
                }
            }
        }
    };
    View.OnClickListener ShareClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
            } else {
                shareClick();
            }

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareClick();
                }
                break;
        }
    }

    private void shareClick() {
        if (mStatus == 5000) {
            mHintOdds.setVisibility(View.GONE);
        }
        Message obtain = Message.obtain();
        handler.sendMessageDelayed(obtain, 100);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bmp;
            if (mStatus != 5000) {
                bmp = ScreenshotUtils.getBitmapByView(mScrollView);
            } else {
                bmp = ScreenshotUtils.getScrollViewBitmap(mLl_share, mLl_share2);
                mHintOdds.setVisibility(View.VISIBLE);
            }
            Share share = new Share(mContext);
            share.showShare(bmp);

            /*mLl_share.setVisibility(View.GONE);
            mLl_header.setVisibility(View.VISIBLE);
            mLookOutDetail.setVisibility(View.VISIBLE);
            mOutTicketLine.setVisibility(View.VISIBLE);
            mLl_footer.setVisibility(View.VISIBLE);
            mLine_footer.setVisibility(View.VISIBLE);
            if (isShowOut)
                mLlOutTicketDetail.setVisibility(View.VISIBLE);*/
        }
    };

    private void continuePayment() {
        HintDialogUtils.setHintDialog(mContext);
        HintDialogUtils.setTitleVisiable(true);
        HintDialogUtils.setMessage("你确定继续付款吗？");
        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
            @Override
            public void callBack(View view) {
                // Intent intent=new Intent(mContext,BuyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderCode", mOrderCode);
                bundle.putString("money", mAllmoney);
                bundle.putString("mGodUserId", mGodUserId);
                bundle.putString("mGodName", mGodName);
                bundle.putString("lotcode", mLotCode);
                bundle.putLong("mCreattime", mCreattime);
                bundle.putString("remark", LotteryTypes.getOrderType(orderType) + "_" + mOrderCode);
                startActivityForResult(BuyActivity.class, bundle, 111);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        * 333 从继续支付支付完成后返回回来的时候
        * */
        if (resultCode == 333) {
            Intent intent = new Intent();
            setResult(444, intent);
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("OrderFormDetail Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

