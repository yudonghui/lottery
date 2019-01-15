package com.daxiang.lottery.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.BillRecordActivity;
import com.daxiang.lottery.activity.lotteryactivity.CardActivity;
import com.daxiang.lottery.activity.lotteryactivity.PaymentResultActivity;
import com.daxiang.lottery.activity.lotteryactivity.UpayActivity;
import com.daxiang.lottery.activity.lotteryactivity.redpacket.RedpacketActivity;
import com.daxiang.lottery.adapter.BuyRechargeAdapter;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.constant.Constants;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.BillWalletData;
import com.daxiang.lottery.entity.BuyData;
import com.daxiang.lottery.entity.PayBean;
import com.daxiang.lottery.entity.PaymentData;
import com.daxiang.lottery.entity.RechargeMethodData;
import com.daxiang.lottery.entity.RedpacketData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.ToastUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.TimeTextView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static java.lang.Double.parseDouble;
import static java.lang.System.currentTimeMillis;

public class BuyActivity extends AppCompatActivity {
    private TitleBar mTitleBar;
    private TextView mLottery_type;
    private TextView mOrder_money;
    private View mLineGod;
    private LinearLayout mLlGod;
    private TextView mGod_name;
    private ImageView mRight;
    private TextView mRed_money;
    private TextView mCx_money;
    private ImageView mOn_off;
    private LinearLayout mLl_surplus_money;
    private TextView mSurplus_money;
    private TimeTextView mTimeTv;
    private LinearLayout mLlRedpacket;
    // private ImageView mAlipay_onoff;
    // private ImageView mWx_onoff;
    private LinearLayout mSelect_zhifu;
    private BillListView mListView;
    private TextView mConfirm_buy;
    private Context mContext;
    private HttpInterface2 mHttpInterface;
    private String remark;
    private String orderCode;
    //是否用彩象的账户
    private boolean isCxAccount = true;
    //是否用微信直接购买
    // private boolean isWxAccount = false;
    //剩余需要第三方支付的钱数
    private double surplusMoney = 0;
    //可用红包金额
    private int redMoney = 0;
    private ArrayList<RechargeMethodData.DataBean.ItemsBean> mRechargeList = new ArrayList<>();
    private BuyRechargeAdapter mAdapter;
    // private HashMap<Integer, View> viewMap = new HashMap<>();
    //默认下面的充值方式是不选中的状态,preposition的值代表了当前选中的是哪一中支付方式。
    private int prePosition = 100;
    //红包对应的位置(默认是第一个)
    private int redpacketId = 0;
    private List<RedpacketData.DataBean.ItemsBean> items = new ArrayList<>();
    private String lotcode;
    private String money;
    private String voucher = "";
    private String payType = "0";
    //标志是否点击了支付按钮
    private boolean flag = false;
    private int allCount;
    private String content;//购买的字符串
    private String buyType;
    private long mCreattime;
    private String mGodName;//发单人名字
    private String mGodUserId;//发单人uid
    private String rechargeStr = "U_PAY,WE_CHAT,ALIPLAY_ONLINE,LL_PAY,JD_PAY,QQ_PAY,WE_CHAT_H5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        mContext = this;
        mHttpInterface = new HttpUtils2(mContext);
        Intent intent = getIntent();
        buyType = intent.getStringExtra("buyType");
        orderCode = intent.getStringExtra("orderCode");
        remark = intent.getStringExtra("remark");
        lotcode = intent.getStringExtra("lotcode");
        money = intent.getStringExtra("money");
        allCount = intent.getIntExtra("allCount", 0);
        content = intent.getStringExtra("content");
        mCreattime = intent.getLongExtra("mCreattime", 0);
        mGodName = intent.getStringExtra("mGodName");
        mGodUserId = intent.getStringExtra("mGodUserId");
        initView();
        timeClock();//倒计时
        refreshWallet();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        addData();
        addListener();
        //注册一个动态广播
        registerBoradcastReceiver();
    }

    private boolean isBuy = true;

    private void timeClock() {
        final TextView mOrderTime = (TextView) findViewById(R.id.orderTime);
        SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        String timeForPay = sp.getString("timeForPay", "");//订单有效期
        if (TextUtils.isEmpty(timeForPay)) return;
        long time = Long.parseLong(timeForPay) * 60000;
        if (mCreattime == 0) {
            mTimeTv.setTimes(DateFormtUtils.obtainTime(time));
        } else {
            long timeC = System.currentTimeMillis() - mCreattime;
            Log.e("时间", timeC + "");
            if (timeC > time) {
                mTimeTv.setTimes(DateFormtUtils.obtainTime(0));
            } else {
                mTimeTv.setTimes(DateFormtUtils.obtainTime(time - timeC));
            }
        }
        mTimeTv.setListener(new TimeTextView.endTimeListener() {
            @Override
            public void callback() {
                mOrderTime.setText("订单已失效");
                mTimeTv.setVisibility(View.GONE);
                isBuy = false;
            }
        });
        if (!mTimeTv.isRun()) mTimeTv.run();
    }


    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.buy_titlebar);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("支付订单");
        mTitleBar.setTitleVisibility(true);
        mLineGod = findViewById(R.id.line_god);
        mLlGod = (LinearLayout) findViewById(R.id.ll_god);
        mGod_name = (TextView) findViewById(R.id.god_name);
        mLottery_type = (TextView) findViewById(R.id.lottery_type);
        mOrder_money = (TextView) findViewById(R.id.order_money);
        mRed_money = (TextView) findViewById(R.id.red_money);
        mCx_money = (TextView) findViewById(R.id.cx_money);
        mSelect_zhifu = (LinearLayout) findViewById(R.id.select_fangshi);
        mOn_off = (ImageView) findViewById(R.id.on_off);
        mLl_surplus_money = (LinearLayout) findViewById(R.id.ll_surplus_money);
        mSurplus_money = (TextView) findViewById(R.id.surplus_money);
        mListView = (BillListView) findViewById(R.id.buy_method_lv);
        mConfirm_buy = (TextView) findViewById(R.id.confirm_buy);
        mLlRedpacket = (LinearLayout) findViewById(R.id.ll_redpacket);
        mRight = (ImageView) findViewById(R.id.right);
        mTimeTv = (TimeTextView) findViewById(R.id.timeTextView);
        if (allCount > 1) mLlRedpacket.setVisibility(View.GONE);
        else mLlRedpacket.setVisibility(View.VISIBLE);

        mLottery_type.setText(LotteryTypes.getTypes(lotcode));
        mOrder_money.setText(money + "元");
        mCx_money.setText(LotteryApp.consumeBanlance + "元");

        if (!TextUtils.isEmpty(mGodName)) {
            mGod_name.setText(mGodName);
            mLlGod.setVisibility(View.VISIBLE);
            mLineGod.setVisibility(View.VISIBLE);
        }
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //是否用彩象的账户里面的钱进行购买
        mOn_off.setOnClickListener(CxAccountBuy);
        mListView.setOnItemClickListener(ListViewListener);
        //支付按钮
        mConfirm_buy.setOnClickListener(BuyClickListener);
        //点击查看自己的红包
        mLlRedpacket.setOnClickListener(RedpacketListener);
    }

    private boolean isThird;//是否第三方支付

    private void addData() {
        //获取红包的数据
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params1 = new Bundle();
        params1.putString("token", LotteryApp.token);
        params1.putString("orderAmount", money);
        params1.putString("status", "1");
        params1.putString("pageNum", "1");
        params1.putString("pageSize", "1");
        params1.putString("orderByColumn", "amount");
        params1.putString("rule", "1");
        params1.putString("timeStamp", currentTimeMillis() + "");
        mHttpInterface.post(Url.USERREDINFO_URL, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                RedpacketData redpacketData = gson.fromJson(result, RedpacketData.class);
                int code = redpacketData.getCode();
                String msg = redpacketData.getMsg();
                if (code == 0) {
                    items = redpacketData.getData().getItems();
                    if (items != null && items.size() != 0
                            && parseDouble(money) >= parseDouble(items.get(0).getLimitAmount())) {
                        redMoney = Integer.parseInt(items.get(0).getAmount());

                        voucher = items.get(0).getId();
                        mRed_money.setText(redMoney + "元");
                        mRed_money.setTextColor(getResources().getColor(R.color.red_theme));
                        mRight.setVisibility(View.VISIBLE);
                    } else {
                        mRed_money.setText("无可用红包");
                        mRight.setVisibility(View.GONE);
                        mRed_money.setTextColor(getResources().getColor(R.color.gray_txt));
                    }
                } else {
                    //Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
                if (allCount > 1) redMoney = 0;//追号的时候不能用红包

                //如果购买金额，比钱包里面的金额大。那么就默认微信支付
                if ((Double.parseDouble(money) - redMoney) > Double.parseDouble(LotteryApp.consumeBanlance)) {
                    isThird = true;
                    mListView.setVisibility(View.VISIBLE);
                    mSelect_zhifu.setVisibility(View.VISIBLE);
                    mLl_surplus_money.setVisibility(View.VISIBLE);
                    BigDecimal subtract = new BigDecimal(money).subtract(new BigDecimal(redMoney)).subtract(new BigDecimal(LotteryApp.consumeBanlance));
                    surplusMoney = subtract.doubleValue();
                    mSurplus_money.setText("￥" + subtract);
                } else {
                    surplusMoney = 0;
                    isThird = false;
                    mListView.setVisibility(View.GONE);
                    mSelect_zhifu.setVisibility(View.GONE);
                    mLl_surplus_money.setVisibility(View.GONE);
                }
                //当红包的的数据请求完成之后再请求，第三方支付的列表
                rechargeForm();
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private void rechargeForm() {
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", currentTimeMillis() + "");
        params.putString("channelId", Number.CHANNELID);
        params.putString("client", "2");
        mHttpInterface.post(Url.RECHARGE_METHOD_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                RechargeMethodData rechargeMethodData = gson.fromJson(result, RechargeMethodData.class);
                if (rechargeMethodData.getCode() == 0) {
                    mRechargeList = (ArrayList<RechargeMethodData.DataBean.ItemsBean>) rechargeMethodData.getData().getItems();
                    //如果充值方式列表不为空，那么进行排序
                    for (int i = 0; i < mRechargeList.size(); i++) {
                        if (!rechargeStr.contains(mRechargeList.get(i).getCode())) {//只显示已经接通的支付方式。
                            mRechargeList.remove(i);
                        }
                    }
                    orderData();
                    /*
                    * 传递点击回调的目的是如果账户资金不够，默认的会第一个
                    * */
                    if (isThird) {
                        merchantId = mRechargeList.get(0).getMerchantId();
                        rechargeType = mRechargeList.get(0).getCode();
                        mAdapter = new BuyRechargeAdapter(mRechargeList, mRechargeList.get(0).getCode());
                        mListView.setAdapter(mAdapter);
                    } else {
                        mAdapter = new BuyRechargeAdapter(mRechargeList, "");
                        mListView.setAdapter(mAdapter);
                    }

                }
            }

            @Override
            public void onError() {

            }
        });
    }

    View.OnClickListener CxAccountBuy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mRechargeList == null || mRechargeList.size() == 0) return;
            //点击选择是否用彩象账户支付的时候先把viewmap清空。
            if (isCxAccount) {
                /*如果不选择彩像用户扣款，那么默认为微信支付
                *
                * */
                mListView.setVisibility(View.VISIBLE);
                mSelect_zhifu.setVisibility(View.VISIBLE);
                mLl_surplus_money.setVisibility(View.VISIBLE);
                BigDecimal subtract = new BigDecimal(money).subtract(new BigDecimal(redMoney));
                surplusMoney = subtract.doubleValue();
                /*surplusMoney = Double.parseDouble(money) - redMoney;
                surplusMoney = Double.parseDouble(new DecimalFormat("#.00").format(surplusMoney));*/
                mSurplus_money.setText("￥" + subtract);
                merchantId = mRechargeList.get(0).getMerchantId();
                rechargeType = mRechargeList.get(0).getCode();
                isCxAccount = false;
                mOn_off.setImageResource(R.drawable.btn_off);
                mAdapter.setData(rechargeType);
                mAdapter.notifyDataSetChanged();
                // mListView.setAdapter(mAdapter);
            } else {
                isCxAccount = true;
                mOn_off.setImageResource(R.drawable.btn_on);
                //如果购买金额，比钱包里面的金额大。那么就默认微信支付
                if (parseDouble(money) - redMoney > parseDouble(LotteryApp.consumeBanlance)) {
                    mListView.setVisibility(View.VISIBLE);
                    mSelect_zhifu.setVisibility(View.VISIBLE);
                    mLl_surplus_money.setVisibility(View.VISIBLE);
                    BigDecimal subtract = new BigDecimal(money).subtract(new BigDecimal(redMoney)).subtract(new BigDecimal(LotteryApp.consumeBanlance));
                    surplusMoney = subtract.doubleValue();
                    mSurplus_money.setText("￥" + subtract);
                    merchantId = mRechargeList.get(0).getMerchantId();
                    rechargeType = mRechargeList.get(0).getCode();
                    mAdapter.setData(rechargeType);
                    mAdapter.notifyDataSetChanged();
                } else {
                    surplusMoney = 0;
                    mListView.setVisibility(View.GONE);
                    mSelect_zhifu.setVisibility(View.GONE);
                    mLl_surplus_money.setVisibility(View.GONE);
                }

                // mListView.setAdapter(mAdapter);
            }
        }
    };
    private String merchantId;
    //支付宝打开0 是网页打开，1是直接打开支付宝
    private String openType;
    //第三方支付的类型
    private String rechargeType;
    AdapterView.OnItemClickListener ListViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mAdapter.setData(mRechargeList.get(position).getCode());
            mAdapter.notifyDataSetChanged();
            //商户ID
            merchantId = mRechargeList.get(position).getMerchantId();
            //支付宝打开0 是网页打开，1是直接打开支付宝
            openType = mRechargeList.get(position).getOpenType();
            //第三方支付的类型
            rechargeType = mRechargeList.get(position).getCode();
            // mRechargeList.get(position)
        }
    };
    View.OnClickListener BuyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isBuy) {//订单已失效
                ToastUtils.showToast(mContext, "订单已失效，请重新下单！");
                return;
            }
            if ((surplusMoney != 0 && "U_PAY".equals(rechargeType) && !LotteryApp.cardFlag)
                    || (surplusMoney != 0 && "LL_PAY".equals(rechargeType) && !LotteryApp.cardFlag)) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("您还没有绑定身份证，请先绑定身份证");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        //身份证未绑定
                        Intent intent = new Intent(mContext, CardActivity.class);
                        intent.putExtra("cardFlag", false);
                        startActivity(intent);
                    }
                });
            } else if (surplusMoney != 0 && "LL_PAY".equals(rechargeType)) {
                flag = true;
                PayBean payBean = new PayBean();
                payBean.setCaseId(orderCode);
                payBean.setAmount(new BigDecimal(money).subtract(new BigDecimal(redMoney)) + "");
                payBean.setMerchantId(merchantId);
                payBean.setOrderDetail(remark);
                payBean.setRemark(remark);
                payBean.setVoucherId(voucher);
                payBean.setLotCode(lotcode);
                String balanceAccount;
                if (isCxAccount) balanceAccount = LotteryApp.balance;
                else balanceAccount = "0";
                payBean.setBalanceAccount(balanceAccount);
                Intent intent = new Intent(mContext, BankCardFormActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("payBean", payBean);
                bundle.putString("from", "BuyActivity");
                intent.putExtras(bundle);
                startActivityForResult(intent, 2221);
            } else {
                payment(orderCode, remark);
            }

        }
    };

    private void orderData() {
        Collections.sort(mRechargeList, new Comparator<RechargeMethodData.DataBean.ItemsBean>() {
            @Override
            public int compare(RechargeMethodData.DataBean.ItemsBean lhs, RechargeMethodData.DataBean.ItemsBean rhs) {
                if (lhs.getSort() > rhs.getSort()) {
                    return -1;
                } else if (lhs.getSort() < rhs.getSort()) {
                    return 1;
                } else {
                    return 0;
                }

            }
        });
    }

    View.OnClickListener RedpacketListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String string = mRed_money.getText().toString();
            if ("无可用红包".equals(string)) return;
            Intent intent = new Intent(mContext, RedpacketActivity.class);
            intent.putExtra("position", redpacketId);
            intent.putExtra("money", money);
            startActivityForResult(intent, 111);
        }
    };

    private void rechargeSelect() {
        mListView.setAdapter(mAdapter);
    }


    private void payment(String caseId, String orderDetail) {
        //防止连续点击
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        update();
        Log.e("时间E", System.currentTimeMillis() + "");
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        final Bundle params = new Bundle();
        params.putString("timeStamp", currentTimeMillis() + "");
        params.putString("token", LotteryApp.token);
        params.putString("caseId", caseId);//方案号

        if (surplusMoney == 0) {
            payType = "0";
            params.putString("payType", "0");//支付类型：0余额支付1第三方支付 //还需支付金额如果为0那么就认为是余额支付
        } else {
            payType = "1";
            params.putString("payType", "1");//支付类型：0余额支付1第三方支付
            params.putString("merchantId", merchantId);//第三方支付id(可选)
        }
        String balanceAccount; //即使彩像账户有钱也要用第三方支付的话，balnceaccount 必须传0
        if (isCxAccount) balanceAccount = LotteryApp.balance;
        else balanceAccount = "0";
        params.putString("balanceAccount", balanceAccount);//账户余额
        params.putString("amount", new BigDecimal(money).subtract(new BigDecimal(redMoney)) + "");//实际支付金额(订单金额减去红包金额)

        if (!TextUtils.isEmpty(voucher))
            params.putString("voucherId", voucher);//红包id(可选)
        params.putString("lotCode", lotcode);
        params.putString("client", "2");//客户端类型:1pc2android3ios4h5
        String ipAddress = NetworkUtils.getIPAddress(mContext);
        if (TextUtils.isEmpty(ipAddress)) {
            ipAddress = "10.0.3.15";
        }
        if ("WE_CHAT_H5".equals(rechargeType)) {
            params.putString("ip", "");//客户端ip
        } else {
            params.putString("ip", ipAddress);//客户端ip
        }
        params.putString("orderDetail", orderDetail);//订单明细
        if ("U_PAY".equals(rechargeType))
            params.putString("returnUrl", "https://www.baidu.com");//U付支付成功后跳转地址（U付支付必传）
        params.putString("remark", orderDetail);//备注
        mHttp.post(Url.PAYMENT_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                // MobclickAgent.reportError(mContext, content + "####" + LotteryApp.phone);
                send();//刷新钱包数据
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                PaymentData paymentData = gson.fromJson(result, PaymentData.class);
                int code = paymentData.getCode();
                String msg = paymentData.getMsg();
                if (code == 0) {
                    if (surplusMoney == 0) {
                        Intent intent = new Intent(mContext, PaymentResultActivity.class);
                        intent.putExtra("allMoney", money);
                        intent.putExtra("allCount", allCount);
                        startActivityForResult(intent, 101);
                        setResult(100, new Intent());
                        finish();
                        return;
                    }
                    flag = true;
                    PaymentData.DataBean.ItemsBean items = paymentData.getData().getItems();
                    switch (rechargeType) {

                        //京东支付
                        case "JD_PAY":
                            jdpayWeb(items);
                            break;
                        //QQ支付
                        case "QQ_PAY":
                            qqPayWeb(items);
                            break;

                        //支付宝支付(手动挂账)
                        case "ALIPAY_OFFLINE":
                            break;
                        //支付宝支付(自动到账)
                        case "ALIPAY_ONLINE":
                            String code_url = items.getCode_url();
                            alipayOnWeb(code_url);
                            break;
                        //u储蓄卡支付
                        case "U_PAY":
                            String uPayReturn = paymentData.getData().getuPayReturn();
                            upayWeb(uPayReturn);
                            break;
                        //微信支付
                        case "WE_CHAT":
                            weixinWeb(items);
                            break;
                        case "WE_CHAT_H5":
                            weixinH5(items);
                            //连连支付
                        case "LL_PAY":

                            break;
                    }
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                Toast.makeText(mContext, "网络请求错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //QQ支付
    private void qqPayWeb(PaymentData.DataBean.ItemsBean items) {
        if (items == null) return;
        String mwebUrl = items.getMwebUrl();
        if (TextUtils.isEmpty(mwebUrl)) return;
        try {
            //浏览器打开
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(mwebUrl);
            intent.setData(content_url);
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "您可能没有安装QQ", Toast.LENGTH_SHORT).show();
        }

    }

    //京东支付
    private void jdpayWeb(PaymentData.DataBean.ItemsBean items) {
        if (items == null) return;
        String mwebUrl = items.getMwebUrl();
        Intent intent = new Intent(mContext, H5Activity.class);
        intent.putExtra("url", mwebUrl);
        intent.putExtra("from", "jdpay");
        mContext.startActivity(intent);
    }

    private void update() {
        if (TextUtils.isEmpty(content)) return;
        BuyData buyData = new BuyData();
        buyData.setName(LotteryApp.nikeName + money + buyType);
        buyData.setPhone(LotteryApp.phone);
        buyData.setContent(content);
        buyData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
            }
        });
    }

    //支付宝支付
    private final String GOALIPILURL = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=";

    private void alipayOnWeb(String code_url) {
        if (!TextUtils.isEmpty(code_url)) {
            try {
                if ("1".equals(openType)) {
                    //直接打开支付宝
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOALIPILURL + code_url)));
                } else {
                    //跳转到页面之后再打开支付宝
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(code_url)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, "请安装支付宝!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //微信支付
    private void weixinWeb(PaymentData.DataBean.ItemsBean items) {
        if (items == null) return;
        String respCode = items.getRespCode();
        if ("0000".equals(respCode)) {
            PayReq req = new PayReq();
            req.appId = Constants.WX_APP_ID;
            req.partnerId = items.getPartnerid();
            req.prepayId = items.getPrepayid();
            req.packageValue = items.getPackageX();
            req.nonceStr = items.getNoncestr();
            req.timeStamp = items.getTimestamp();
            req.sign = items.getSign();
            // req.extData = "app data"; // optional
            // Toast.makeText(RechargeActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
            // 在支付之前，如果应用没有注册到微信，应该先将应用注册到微信
            LotteryApp.api.sendReq(req);
        }
    }

    //微信支付
    private void weixinH5(PaymentData.DataBean.ItemsBean items) {
        if (items == null) return;
        String mwebUrl = items.getMwebUrl();
        if (TextUtils.isEmpty(mwebUrl)) return;
        try {
            //浏览器打开
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(mwebUrl);
            intent.setData(content_url);
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "您可能没有安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private void upayWeb(String uPayReturn) {
        if (TextUtils.isEmpty(uPayReturn)) return;
        if (!LotteryApp.cardFlag) {
            HintDialogUtils.setHintDialog(mContext);
            HintDialogUtils.setMessage("您还没有绑定身份证，请先绑定身份证");
            HintDialogUtils.setTitleVisiable(true);
            HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                @Override
                public void callBack(View view) {
                    //身份证未绑定
                    Intent intent = new Intent(mContext, CardActivity.class);
                    intent.putExtra("cardFlag", false);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(mContext, UpayActivity.class);
            intent.putExtra("uPayReturn", uPayReturn);
            startActivityForResult(intent, 223);
        }
    }

    //刷新用户钱包数据
    private void refreshWallet() {
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", currentTimeMillis() + "");
        mHttpInterface.post(Url.BILL_WALLET_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                BillWalletData billWalletData = gson.fromJson(result, BillWalletData.class);
                if (billWalletData.getCode() == 0) {
                    BillWalletData.DataBean data = billWalletData.getData();
                    String totalBalance = data.getTotalBalance();
                    String freezeBalance = data.getFreezeBalance();
                  /*  int consumeBalance = (int) (Double.parseDouble(totalBalance) - Double.parseDouble(freezeBalance))*100;
                    LotteryApp.balance = totalBalance;
                    double ini = ((double) consumeBalance) / 100;
                    LotteryApp.consumeBanlance = ini + "";
                    mCx_money.setText(LotteryApp.consumeBanlance + "元");*/
                    BigDecimal consumeBalance = new BigDecimal(totalBalance).subtract(new BigDecimal(freezeBalance));
                    LotteryApp.balance = totalBalance;
                    LotteryApp.consumeBanlance = consumeBalance + "";
                    mCx_money.setText("￥" + LotteryApp.consumeBanlance + "元");
                }
            }

            @Override
            public void onError() {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadReceiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (TextUtils.isEmpty(orderCode)) return;
        //没有点击了支付的时候。不判断订单是否成功
        if (!flag) {
            return;
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        params.putString("timeStamp", currentTimeMillis() + "");
        params.putString("outTradeNo", orderCode);
        mHttpInterface.post(Url.GETBUY_RESULT_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                send();
                loadingDialogUtils.setDimiss();
                //刷新钱包
                refreshWallet();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");

                    if (code == 0) {
                        Intent intent = new Intent(mContext, PaymentResultActivity.class);
                        intent.putExtra("allMoney", money);
                        intent.putExtra("allCount", allCount);
                        startActivityForResult(intent, 101);
                        setResult(100, new Intent());
                        finish();
                        /*hintDialogUtils.setMessage("恭喜您购彩成功");
                        hintDialogUtils.setCancel("继续购彩", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {
                                flag = false;
                                Intent intent = new Intent();
                                setResult(100, intent);
                                finish();
                            }
                        });
                        hintDialogUtils.setConfirm("我的彩票", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {
                                flag = false;
                                Intent intent = new Intent(mContext, OrderFormActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                finish();
                            }
                        });*/
                    } else {
                        HintDialogUtils2 hintDialogUtils = new HintDialogUtils2(mContext);
                        hintDialogUtils.setTitleVisiable(true);
                        hintDialogUtils.setTitle("温馨提示");
                        hintDialogUtils.setMessage(getString(R.string.buy_notification));
                        hintDialogUtils.setCancel("取消", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {
                                flag = false;
                            }
                        });
                        hintDialogUtils.setConfirm("账单明细", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {
                                flag = false;
                                Intent intent = new Intent(mContext, BillRecordActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                finish();
                            }
                        });
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
    }

    private void send() {
        Intent intent = new Intent();
        intent.setAction(ConstantNum.HOME_ACTIVITY);
        intent.putExtra("type", ConstantNum.PAYMENT_SUCESS);
        sendBroadcast(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 222) {
            //红包id
            voucher = data.getStringExtra("voucher");
            //从选择红包界面回来时候
            redMoney = Integer.parseInt(data.getStringExtra("redMoney"));
            //还需支付钱数
            if (parseDouble(money) - redMoney > parseDouble(LotteryApp.consumeBanlance)) {
                mListView.setVisibility(View.VISIBLE);
                mSelect_zhifu.setVisibility(View.VISIBLE);
                if (mRechargeList != null && mRechargeList.size() > 0) {
                    merchantId = mRechargeList.get(0).getMerchantId();
                    rechargeType = mRechargeList.get(0).getCode();
                }
                mLl_surplus_money.setVisibility(View.VISIBLE);
                BigDecimal subtract = new BigDecimal(money).subtract(new BigDecimal(redMoney)).subtract(new BigDecimal(LotteryApp.consumeBanlance));
                surplusMoney = subtract.doubleValue();
                mSurplus_money.setText("￥" + subtract);
            } else {
                surplusMoney = 0;
                mListView.setVisibility(View.GONE);
                mSelect_zhifu.setVisibility(View.GONE);
                mLl_surplus_money.setVisibility(View.GONE);
            }
            //选择的红包对应的位置
            redpacketId = data.getIntExtra("position", 99999);
            if (redpacketId == 99999) {
                //当不使用红包的时候判断是否有可用红包，有的话显示“有可用红包”，没有就显示“无可用红包”
                mRed_money.setTextColor(getResources().getColor(R.color.gray_txt));
                if (items != null && items.size() != 0
                        && parseDouble(money) > parseDouble(items.get(0).getLimitAmount())) {
                    mRed_money.setText("有可用红包");
                    mRight.setVisibility(View.VISIBLE);
                } else {
                    mRed_money.setText("无可用红包");
                    mRight.setVisibility(View.GONE);
                }
            } else {
                mRed_money.setText(redMoney + "元");
                mRight.setVisibility(View.VISIBLE);
                mRed_money.setTextColor(getResources().getColor(R.color.red_theme));
            }
        } else if (requestCode == 223 && resultCode == 311) {
            //u付返回
            onRestart();
        } else if (requestCode == 2221 && resultCode == 2222) {
            //连连支付返回
            onRestart();
        } else if (requestCode == 101 && resultCode == 102) {
            setResult(100, data);
            finish();
        }

    }

    private void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.PAYMENT_SUCEESS);
        registerReceiver(broadReceiver, intentFilter);
    }

    private BroadcastReceiver broadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.PAYMENT_SUCEESS)) {
                onRestart();
            }
        }
    };
}
