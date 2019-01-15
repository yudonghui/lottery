package com.daxiang.lottery.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.StringInterface;
import com.daxiang.lottery.entity.LLpayBean;
import com.daxiang.lottery.entity.PayBean;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;
import com.yintong.android.app.IPayService;
import com.yintong.android.app.IRemoteServiceCallback;
import com.yintong.secure.service.PayService;

import org.json.JSONObject;

/**
 * @author yudonghui
 * @date 2017/9/25
 * @describe May the Buddha bless bug-free!!!
 */
public class PaymentUtils {
    private Context mContext;
    public String RET_CODE_SUCCESS = "0000";// 0000 交易成功
    public String RET_CODE_PROCESS = "2008";// 2008 支付处理中
    public static final String RESULT_PAY_PROCESSING = "PROCESSING";
    private StringInterface mListener;

    public PaymentUtils(Context mContext, StringInterface mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }

    public void lianlianWeb(String money, final String cardNo, String cardType, String noAgree) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        final Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("amount", money);
        if (!TextUtils.isEmpty(cardNo))
            params.putString("cardNo", cardNo);
        params.putString("client", "2");
        params.putString("remark", "连连支付");
        params.putString("returnUrl", "https://www.baidu.com");
        if (!TextUtils.isEmpty(cardType) && !TextUtils.isEmpty(noAgree)) {
            params.putString("cardType", cardType);//卡类型
            params.putString("noAgree", noAgree);//卡签约号
        }
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.LL_PAY_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                LLpayBean lLpayBean = gson.fromJson(result, LLpayBean.class);
                int code = lLpayBean.getCode();
                if (code == 0) {
                    LLpayBean.DataBean data = lLpayBean.getData();
                    LLpayBean.DataBean.LlParamsBean llParams = data.getLlParams();
                    payMent(llParams, mContext);
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    public void buy(PayBean payBean, final String cardNo, String cardType, String noAgree) {
        String caseId = payBean.getCaseId();
        String merchantId = payBean.getMerchantId();
        String amount = payBean.getAmount();
        String voucherId = payBean.getVoucherId();
        String lotCode = payBean.getLotCode();
        String orderDetail = payBean.getOrderDetail();
        final String remark = payBean.getRemark();
        String balanceAccount = payBean.getBalanceAccount();
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        final Bundle params = new Bundle();
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("token", LotteryApp.token);
        params.putString("caseId", caseId);//方案号
        params.putString("payType", "1");//支付类型：0余额支付1第三方支付
        params.putString("merchantId", merchantId);//第三方支付id(可选)
        params.putString("balanceAccount", balanceAccount);//即使彩像账户有钱也要用第三方支付的话，balnceaccount 必须传0
        params.putString("amount", amount);//实际支付金额(订单金额减去红包金额)
        if (!TextUtils.isEmpty(voucherId))
            params.putString("voucherId", voucherId);//红包id(可选)
        params.putString("lotCode", lotCode);
        params.putString("client", "2");//客户端类型:1、pc  2、android  3、ios  4、h5
        String ipAddress = NetworkUtils.getIPAddress(mContext);
        if (TextUtils.isEmpty(ipAddress)) {
            ipAddress = "10.0.3.15";
        }
        params.putString("ip", ipAddress);//客户端ip
        params.putString("orderDetail", orderDetail);//订单明细
        params.putString("remark", remark);//备注
        params.putString("returnUrl", "https://www.baidu.com");
        if (!TextUtils.isEmpty(cardNo))
            params.putString("cardNo", cardNo);
        if (!TextUtils.isEmpty(cardType) && !TextUtils.isEmpty(noAgree)) {
            params.putString("cardType", cardType);//卡类型
            params.putString("noAgree", noAgree);//卡签约号
        }
        mHttp.post(Url.PAYMENT_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {

                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                LLpayBean lLpayBean = gson.fromJson(result, LLpayBean.class);
                int code = lLpayBean.getCode();
                String msg = lLpayBean.getMsg();
                if (code == 0) {
                    LLpayBean.DataBean data = lLpayBean.getData();
                    LLpayBean.DataBean.LlParamsBean llParams = data.getLlParams();
                    payMent(llParams, mContext);
                } else {
                    HintDialogUtils2 hintDialogUtils = new HintDialogUtils2(mContext);
                    hintDialogUtils.setMessage(msg);
                }

                /*if (code == 0) {
                    PaymentData.DataBean.ItemsBean items = paymentData.getData().getItems();
                    LLpayBean.DataBean.LlParamsBean llParamsBean = new LLpayBean.DataBean.LlParamsBean();
                    llParamsBean.setOid_partner(items.getOid_partner());
                    llParamsBean.setRisk_item(items.getRisk_item());
                    llParamsBean.setSign(items.getSign());
                    llParamsBean.setDt_order(items.getDt_order());
                    llParamsBean.setName_goods(items.getName_goods());
                    llParamsBean.setNotify_url(items.getNotify_url());
                    llParamsBean.setBusi_partner(items.getBusi_partner());
                    llParamsBean.setFlag_modify(items.getFlag_modify());
                    llParamsBean.setNo_order(items.getNo_order());
                    llParamsBean.setId_no(items.getId_no());
                    llParamsBean.setCard_no(items.getCard_no());
                    llParamsBean.setUser_id(items.getUser_id());
                    llParamsBean.setMoney_order(items.getMoney_order());
                    llParamsBean.setId_type(items.getId_type());
                    llParamsBean.setAcct_name(items.getAcct_name());
                    llParamsBean.setSign_type(items.getSign_type());
                    llParamsBean.setInfo_order(items.getInfo_order());
                    payMent(llParamsBean, mContext);
                } */

            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    public void payMent(final LLpayBean.DataBean.LlParamsBean llParams, final Context mContext) {
        if (payService == null) {
            // 绑定安全支付服务需要获取上下文环境，
            // 如果绑定不成功使用mActivity.getApplicationContext().bindService
            // 解绑时同理
            mContext.getApplicationContext().bindService(
                    new Intent(mContext, PayService.class),
                    mSecurePayConnection, Context.BIND_AUTO_CREATE);
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    // 等待安全支付服务绑定操作结束
                    // 注意：这里很重要，否则payService.pay()方法会失败
                    synchronized (lock) {
                        if (payService == null)
                            lock.wait();
                    }

                    // register a Callback for the service.
                    // 为安全支付服务注册一个回调
                    payService.registerCallback(mCallback);
                    String payInfo = StringUtil.toJSONString(llParams);
                    // 调用安全支付服务的pay方法
                    String strRet = "";
                    if (payInfo != null)
                        strRet = payService.pay(payInfo);


                    // set the flag to indicate that we have finished.
                    // unregister the Callback, and unbind the service.
                    // 将mbPaying置为false，表示支付结束
                    // 移除回调的注册，解绑安全支付服务
                    //  mbPaying = false;
                    payService.unregisterCallback(mCallback);
                    mContext.getApplicationContext().unbindService(
                            mSecurePayConnection);

                    // send the result back to caller.
                    // 发送交易结果
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = strRet;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();

                    // send the result back to caller.
                    // 发送交易结果
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = e.toString();
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    Integer lock = 0;
    IPayService payService = null;
    // 和安全支付服务建立连接
    private ServiceConnection mSecurePayConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            try {
                //
                // wake up the binder to continue.
                // 获得通信通道
                synchronized (lock) {
                    payService = IPayService.Stub.asInterface(service);
                    lock.notify();
                }
            } catch (Exception e) {
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            payService = null;
        }
    };
    /**
     * This implementation is used to receive callbacks from the remote service.
     * 实现安全支付的回调
     */
    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        /**
         * This is called by the remote service regularly to tell us about new
         * values. Note that IPC calls are dispatched through a thread pool
         * running in each process, so the code executing here will NOT be
         * running in our main thread like most other things -- so, to update
         * the UI, we need to use a Handler to hop over there. 通过IPC机制启动安全支付服务
         */
        public void startActivity(String packageName, String className,
                                  int iCallingPid, Bundle bundle) throws RemoteException {
            Intent intent = new Intent(Intent.ACTION_MAIN, null);

            if (bundle == null)
                bundle = new Bundle();
            // else ok.

            try {
                bundle.putInt("CallingPid", iCallingPid);
                intent.putExtras(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            intent.setClassName(packageName, className);
            mContext.startActivity(intent);
        }

        /**
         * when the msp loading dialog gone, call back this method.
         */
        @Override
        public boolean isHideLoadingScreen() throws RemoteException {
            return false;
        }

        /**
         * when the current trade is finished or cancelled, call back this
         * method.
         */
        @Override
        public void payEnd(boolean arg0, String arg1) throws RemoteException {

        }

    };
    private Handler mHandler = createHandler();

    private Handler createHandler() {
        return new Handler() {
            public void handleMessage(Message msg) {
                String strRet = (String) msg.obj;
                switch (msg.what) {
                    case 1: {
                        JSONObject objContent = StringUtil.string2JSON(strRet);
                        String retCode = objContent.optString("ret_code");
                        String retMsg = objContent.optString("ret_msg");
                        mListener.callBack(retCode, retMsg);
                    }
                    break;
                }
                super.handleMessage(msg);
            }
        };

    }
}
