package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BankCardFormActivity;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.activity.H5Activity;
import com.daxiang.lottery.adapter.RechargeAdapter;
import com.daxiang.lottery.constant.Constants;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ActionDeclareData;
import com.daxiang.lottery.entity.QQData;
import com.daxiang.lottery.entity.RechargeMethodData;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.entity.WXData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.google.gson.Gson;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RechargeActivity extends BaseActivity implements IWXAPIEventHandler {
    private ArrayList<RechargeMethodData.DataBean.ItemsBean> mRechargeList;
    private UserInfo userInfo;
    private EditText mInput_money;
    private Button mBtn_input_delete;
    private TextView mTv_recharge_ten;
    private TextView mTv_recharge_tweenty;
    private TextView mTv_recharge_fifty;
    private TextView mTv_recharge_handred;
    private BillListView mListView;
    private Context mContext;
    //活动说明
    private LinearLayout mLlActionDeclare;
    private TextView mActionDeclare;
    private String rechargeStr = "U_PAY,WE_CHAT,ALIPAY_OFFLINE,ALIPLAY_ONLINE,LL_PAY,JD_PAY,QQ_PAY,WE_CHAT_H5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_recharge);
        mContext = this;
        Intent intent = getIntent();
        // mRechargeList = (ArrayList<RechargeMethodData.DataBean.ItemsBean>) intent.getSerializableExtra("mRechargeList");
        userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
        initViews();
        addData();

        addListener();
        LotteryApp.api.handleIntent(intent, this);
    }

    private void initViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("充值");
        mTitleBar.setTitleVisibility(true);
        mInput_money = (EditText) findViewById(R.id.input_money);
        // mInput_money.setFocusable(false);
        mBtn_input_delete = (Button) findViewById(R.id.btn_input_delete);
        mTv_recharge_ten = (TextView) findViewById(R.id.tv_recharge_ten);
        mTv_recharge_tweenty = (TextView) findViewById(R.id.tv_recharge_tweenty);
        mTv_recharge_fifty = (TextView) findViewById(R.id.tv_recharge_fifty);
        mTv_recharge_handred = (TextView) findViewById(R.id.tv_recharge_handred);
        mListView = (BillListView) findViewById(R.id.lv_recharge);
        mLlActionDeclare = (LinearLayout) findViewById(R.id.ll_action_declare);
        mActionDeclare = (TextView) findViewById(R.id.action_declare);
    }

    private void addData() {
        //获取所有的可以支持充值的方式
        HttpInterface2 mHttpInterface = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("channelId", Number.CHANNELID);
        params.putString("client", "2");
        mHttpInterface.post(Url.RECHARGE_METHOD_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                RechargeMethodData rechargeMethodData = gson.fromJson(result, RechargeMethodData.class);
                if (rechargeMethodData.getCode() == 0) {
                    mRechargeList = (ArrayList<RechargeMethodData.DataBean.ItemsBean>) rechargeMethodData.getData().getItems();
                    for (int i = 0; i < mRechargeList.size(); i++) {
                        if (!rechargeStr.contains(mRechargeList.get(i).getCode())) {//只显示已经接通的支付方式。
                            mRechargeList.remove(i);
                        }
                    }
                    //如果充值方式列表不为空，那么进行排序
                    orderData();
                    mListView.setAdapter(new RechargeAdapter(mRechargeList));
                }
            }

            @Override
            public void onError() {

            }
        });
        //获取活动说明内容
        getActionData();
    }

    private void getActionData() {
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        //活动状态 0：进行中 1：已暂停
        params.putString("activityStatus", "0");
        //活动类型00：注册活动 11：充值活动
        params.putString("activityType", "11");
        mHttpInterface.post(Url.USERVOUCHER_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                ActionDeclareData actionDeclareData = gson.fromJson(result, ActionDeclareData.class);
                if (actionDeclareData.getCode() == 0) {
                    List<ActionDeclareData.DataBean.ItemsBean> items = actionDeclareData.getData().getItems();
                    if (items != null && items.size() > 0) {
                        mLlActionDeclare.setVisibility(View.VISIBLE);
                        mActionDeclare.setText(items.get(0).getDescription());
                    } else mLlActionDeclare.setVisibility(View.GONE);
                } else mLlActionDeclare.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
    }

    private boolean isCheckStatus = false;

    private void addListener() {
        mTv_recharge_ten.setOnClickListener(MoneyListener);
        mTv_recharge_tweenty.setOnClickListener(MoneyListener);
        mTv_recharge_fifty.setOnClickListener(MoneyListener);
        mTv_recharge_handred.setOnClickListener(MoneyListener);
        //对显示钱的编辑框中的内容发生的变化进行监听。（为了让四个按钮的颜色跟随变化）
        mInput_money.addTextChangedListener(WatcherListener);
        mBtn_input_delete.setOnClickListener(InputDeleteListener);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String inputMoney = mInput_money.getText().toString().trim();
                if (TextUtils.isEmpty(inputMoney) || Integer.parseInt(inputMoney) == 0) {
                    Toast.makeText(RechargeActivity.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
                } else {
                    String money = Integer.parseInt(inputMoney) + "";
                    //商户ID
                    String merchantId = mRechargeList.get(position).getMerchantId();
                    //支付宝打开0 是网页打开，1是直接打开支付宝
                    String openType = mRechargeList.get(position).getOpenType();
                    flag = true;
                    switch (mRechargeList.get(position).getCode()) {
                        //京东支付
                        case "JD_PAY":
                            isCheckStatus = true;
                           jdpayWeb(money, merchantId);
                            break;
                        //QQ支付
                        case "QQ_PAY":
                            isCheckStatus = true;
                            qqPayWeb(money, merchantId);
                            break;
                        //支付宝充值(手动挂账)
                        case "ALIPAY_OFFLINE":
                            isCheckStatus = false;
                            alipayOffWeb(money, openType);
                            break;
                        //支付宝充值(自动到账)
                        case "ALIPAY_ONLINE":
                            isCheckStatus = true;
                            alipayOnWeb(money, merchantId, openType);
                            break;
                        //u储蓄卡支付
                        case "U_PAY":
                            isCheckStatus = false;
                            upayWeb(money);
                            break;
                        //微信支付
                        case "WE_CHAT":
                            isCheckStatus = true;
                            weixinWeb(money, merchantId);
                            break;
                        case "WE_CHAT_H5":
                            isCheckStatus = true;
                            weixinH5(money, merchantId);
                            break;
                        //连连支付
                        case "LL_PAY":
                            Intent intent = new Intent(mContext, BankCardFormActivity.class);
                            intent.putExtra("money", money);
                            startActivityForResult(intent, 2221);
                            break;
                    }
                }

            }
        });
    }

    //京东充值
    private void jdpayWeb(final String money, String merchantId) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        final HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("amount", money);
        params.putString("client", "2");
        params.putString("merchantId", merchantId);
        params.putString("remark", "京东充值");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.RECHARGE_JD, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                QQData qqData = gson.fromJson(result, QQData.class);
                int code = qqData.getCode();
                String msg = qqData.getMsg();
                if (code == 0) {
                    QQData.DataBean data = qqData.getData();
                    orderId = data.getItems().getOrderId();
                    String mwebUrl = data.getItems().getMwebUrl();

                 /*   //浏览器打开
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(mwebUrl);
                    intent.setData(content_url);
                    mContext.startActivity(intent);*/

                    //本app内打开页面
                    Intent intent = new Intent(mContext, H5Activity.class);
                    intent.putExtra("url", mwebUrl);
                    intent.putExtra("from", "jdpay");
                    mContext.startActivity(intent);

                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }


    //qq充值
    private void qqPayWeb(final String money, String merchantId) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        final HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("amount", money);
        params.putString("client", "2");
        params.putString("merchantId", merchantId);
        params.putString("remark", "QQ充值");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.RECHARGE_QQ, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                QQData qqData = gson.fromJson(result, QQData.class);
                int code = qqData.getCode();
                String msg = qqData.getMsg();
                if (code == 0) {
                    QQData.DataBean data = qqData.getData();
                    orderId = data.getItems().getOrderId();
                    String mwebUrl = data.getItems().getMwebUrl();


                   /* String strBase64 = new String(Base64.encode(mwebUrl.getBytes(), Base64.DEFAULT));
                    String qqPay = "mqqapi://forward/url?url_prefix=" + strBase64 + "&souce=oicqzone.com&version=1&src_type=web";*/
                    // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqPay)));
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


                    //本app内打开页面
                  /*  Intent intent = new Intent(mContext, PictureActivity.class);
                    intent.putExtra("url", mwebUrl);
                    mContext.startActivity(intent);*/
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });

    }

    //微信充值
    private void weixinWeb(final String money, String merchantId) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        final HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("amount", money);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("client", "2");
        params.putString("remark", "微信充值");
        params.putString("merchantId", merchantId);
        String ipAddress = NetworkUtils.getIPAddress(mContext);
        if (TextUtils.isEmpty(ipAddress)) {
            ipAddress = "10.0.3.15";
        }
        params.putString("ip", ipAddress);
        mHttp.post(Url.RECHARGE_WX_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                WXData wxData = gson.fromJson(result, WXData.class);
                if (wxData.getCode() == 0) {
                    WXData.DataBean.ItemsBean items = wxData.getData().getItems();
                    if ("0000".equals(items.getRespCode())) {
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
                    } else {
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage(items.getRespMsg());
                    }

                } else {
                    Toast.makeText(mContext, wxData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    //微信充值
    private void weixinH5(final String money, String merchantId) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        final HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("amount", money);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("client", "2");
        params.putString("orderDetail", "app_name=彩象彩票&package_name=com.daxiang.lottery");
        params.putString("remark", "微信充值");
        params.putString("merchantId", merchantId);
        String ipAddress = NetworkUtils.getIPAddress(mContext);
        if (TextUtils.isEmpty(ipAddress)) {
            ipAddress = "10.0.3.15";
        }
        params.putString("ip", "");
        mHttp.post(Url.RECHARGE_WX_H5_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                QQData qqData = gson.fromJson(result, QQData.class);
                int code = qqData.getCode();
                String msg = qqData.getMsg();
                if (code == 0) {
                    QQData.DataBean data = qqData.getData();
                    orderId = data.getItems().getOrderId();
                    String mwebUrl = data.getItems().getMwebUrl();
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


                    //本app内打开页面
                  /*  Intent intent = new Intent(mContext, PictureActivity.class);
                    intent.putExtra("url", mwebUrl);
                    mContext.startActivity(intent);*/
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    //支付宝充值(手动)
    private void alipayOffWeb(final String money, final String openType) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        final HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("channelName", "caixiang");
        params.putString("amount", money);
        params.putString("remark", "支付宝充值");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.RECHARGE_ALIPAY_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {


                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("code") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        final String tradeId = data.getString("orderId");
                        Bundle params1 = new Bundle();
                        params1.putString("token", LotteryApp.token);
                        params1.putString("channelName", "caixiang");
                        params1.putString("partnerCode", "ALIPAY_OFFLINE");
                        params1.putString("timeStamp", System.currentTimeMillis() + "");
                        //获取本公司的账户
                        mHttpInterface.post(Url.ALIPAY_URL, params1, new JsonInterface() {
                            @Override
                            public void callback(String result) {
                                loadingDialogUtils.setDimiss();
                                try {
                                    JSONObject jsonObject1 = new JSONObject(result);
                                    int code = jsonObject1.getInt("code");
                                    if (code == 0) {
                                        JSONObject data1 = jsonObject1.getJSONObject("data");
                                        JSONObject items = data1.getJSONObject("items");
                                        String merQrCode = items.getString("merQrCode");
                                        //跳转到支付宝
                                        Intent intent = new Intent(mContext, AlipayMyActivity.class);
                                        intent.putExtra("money", money);
                                        intent.putExtra("tradeNo", tradeId + "");
                                        intent.putExtra("merQrCode", merQrCode);
                                        intent.putExtra("openType", openType);
                                        startActivity(intent);
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
                        loadingDialogUtils.setDimiss();
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

    private final String GOALIPILURL = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=";
    private String orderId;

    //支付宝充值（自动到账）
    private void alipayOnWeb(final String money, String merchantId, final String openType) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(this);
        Bundle params = new Bundle();

        params.putString("token", LotteryApp.token);
        params.putString("amount", money);
        params.putString("remark", "支付宝充值");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("client", "2");
        String ipAddress = NetworkUtils.getIPAddress(mContext);
        if (TextUtils.isEmpty(ipAddress)) {
            ipAddress = "10.0.3.15";
        }
        params.putString("ip", ipAddress);
        params.putString("merchantId", merchantId);
        mHttp.post(Url.RECHARGE_ALIPAY_ON_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("code") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONObject items = data.getJSONObject("items");
                        String code_url = items.getString("code_url");
                        orderId = items.getString("orderId");
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
                    } else {
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("code") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String uPayReturn = data.getString("uPayReturn");
                        Intent intent = new Intent(mContext, UpayActivity.class);
                        intent.putExtra("uPayReturn", uPayReturn);
                        startActivity(intent);
                    } else {
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    //跳转到u付加载数据界面（用webview加载）
    private void upayWeb(final String money) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(this);
        Bundle params = new Bundle();

        params.putString("token", LotteryApp.token);
        params.putString("amount", money);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("returnUrl", "https://www.baidu.com");
        params.putString("client", "2");
        params.putString("remark", "U付充值");
        mHttp.post(Url.RECHARGE_RUL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("code") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String uPayReturn = data.getString("uPayReturn");
                        Intent intent = new Intent(mContext, UpayActivity.class);
                        intent.putExtra("uPayReturn", uPayReturn);
                        startActivityForResult(intent, 2222);
                    } else {
                        HintDialogUtils.setHintDialog(mContext);
                        HintDialogUtils.setMessage(jsonObject.getString("msg"));
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

    View.OnClickListener MoneyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String money = "";
            switch (v.getId()) {
                case R.id.tv_recharge_ten:
                    money = "100";
                    break;
                case R.id.tv_recharge_tweenty:
                    money = "200";
                    break;
                case R.id.tv_recharge_fifty:
                    money = "500";
                    break;
                case R.id.tv_recharge_handred:
                    money = "1000";
                    break;
            }
            //设置四个按钮的颜色
            setMoneyColor(money);
            //在编辑框中显示钱数
            mInput_money.setText(money);
        }
    };
    View.OnClickListener InputDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mInput_money.setText("");
        }
    };
    TextWatcher WatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = String.valueOf(s).length();
            if (length > 0) {
                setMoneyColor(String.valueOf(s));
                mBtn_input_delete.setVisibility(View.VISIBLE);
            } else mBtn_input_delete.setVisibility(View.GONE);
        }
    };

    private void setMoneyColor(String money) {
        switch (money) {
            case "100":
                mTv_recharge_ten.setTextColor(Color.WHITE);
                mTv_recharge_ten.setBackgroundResource(R.drawable.recharge_money_select_bg);
                mTv_recharge_tweenty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_tweenty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_fifty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_fifty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_handred.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_handred.setBackgroundResource(R.drawable.recharge_money_bg);
                break;
            case "200":
                mTv_recharge_tweenty.setTextColor(Color.WHITE);
                mTv_recharge_tweenty.setBackgroundResource(R.drawable.recharge_money_select_bg);
                mTv_recharge_ten.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_ten.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_fifty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_fifty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_handred.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_handred.setBackgroundResource(R.drawable.recharge_money_bg);
                break;
            case "500":
                mTv_recharge_fifty.setTextColor(Color.WHITE);
                mTv_recharge_fifty.setBackgroundResource(R.drawable.recharge_money_select_bg);
                mTv_recharge_tweenty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_tweenty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_ten.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_ten.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_handred.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_handred.setBackgroundResource(R.drawable.recharge_money_bg);
                break;
            case "1000":
                mTv_recharge_handred.setTextColor(Color.WHITE);
                mTv_recharge_handred.setBackgroundResource(R.drawable.recharge_money_select_bg);
                mTv_recharge_tweenty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_tweenty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_fifty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_fifty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_ten.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_ten.setBackgroundResource(R.drawable.recharge_money_bg);
                break;
            default:
                mTv_recharge_tweenty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_tweenty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_fifty.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_fifty.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_ten.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_ten.setBackgroundResource(R.drawable.recharge_money_bg);
                mTv_recharge_handred.setTextColor(Color.rgb(0Xff, 0X80, 0X00));
                mTv_recharge_handred.setBackgroundResource(R.drawable.recharge_money_bg);
                break;
        }
    }

    //标志是否点击了支付按钮
    private boolean flag = false;

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!flag) {
            return;
        }
        if (isCheckStatus) {//支付宝支付回来的时候需要请求充值状态。
            final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
            HttpInterface2 mHttp = new HttpUtils2(this);
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            params.putString("orderId", orderId);
            mHttp.post(Url.QUERY_CHARGE_STATUS, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    loadingDialogUtils.setDimiss();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code == 0) {
                            Toast.makeText(mContext, "充值完成！", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2221) {

        } else if (requestCode == 2222 && resultCode == 2223) {//U付支付回调
            Toast.makeText(mContext, "充值完成！", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("requestCode", requestCode + "----------");//10000
            Log.e("resultCode", resultCode + "----------");//88888
            String umpResultMessage = data.getStringExtra("umpResultMessage");//支付结果描述
            String umpResultCode = data.getStringExtra("umpResultCode");//支付结果
            Toast.makeText(this, umpResultMessage, Toast.LENGTH_SHORT).show();
        }
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
        Toast.makeText(this, "openid = " + baseReq.openId, Toast.LENGTH_SHORT).show();

        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                //goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                //goToShowMsg((ShowMessageFromWX.Req) baseReq);
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                Toast.makeText(this, "launch From Weixin", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp baseResp) {
        Toast.makeText(this, "openid = " + baseResp.openId, Toast.LENGTH_SHORT).show();

        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            Toast.makeText(this, "code = " + ((SendAuth.Resp) baseResp).code, Toast.LENGTH_SHORT).show();
        }

        String result = "";

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

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

}
