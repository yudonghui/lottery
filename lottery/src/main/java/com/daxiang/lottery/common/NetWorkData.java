package com.daxiang.lottery.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BuyActivity;
import com.daxiang.lottery.activity.ChampionActivity;
import com.daxiang.lottery.activity.H5Activity;
import com.daxiang.lottery.activity.JclqActivity;
import com.daxiang.lottery.activity.JczqActivity;
import com.daxiang.lottery.activity.NumberActivity;
import com.daxiang.lottery.activity.SfcAndRjcActivity;
import com.daxiang.lottery.activity.lotteryactivity.OrderFormActivity;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.LoginResultData;
import com.daxiang.lottery.entity.OrderFormBean;
import com.daxiang.lottery.entity.PictureData;
import com.daxiang.lottery.entity.PrivilegeData;
import com.daxiang.lottery.entity.UmengTag;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.AppUtils;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/5/11
 * @describe May the Buddha bless bug-free!!!
 */
public class NetWorkData {
    private Context mContext;
    private int shakes;
    private int mMulti;
    private String money;
    private String issue;
    //是否追加
    private int isChase = 0;
    private int requestCode;
    private String lotcode;
    private String order_type;
    private String wrate;
    private String bnum;
    private String pnum;
    private String desc;
    private String type;
    private double maxTheoryBonus;
    private String orderId;
    private String fromWhere;
    private String mutile;
    private boolean mFlag;
    //购买的方式，hemai，normal
    private String buyMethod;
    private String content;
    private HttpInterface2 mHttp;
    private int allCount;
    private String oneMoney;
    private String pickType;
    private String stopType;
    private String stopPrize;
    private String mGodName;
    private String mGodUserId;

    public NetWorkData(Context mContext, Bundle bundle) {
        this.mContext = mContext;
        requestCode = bundle.getInt("requestCode", 0);
        buyMethod = bundle.getString("buyMethod");
        content = bundle.getString("content");
        shakes = bundle.getInt("shakes", 0);
        mMulti = bundle.getInt("mMulti", 0);
        money = bundle.getString("money");
        issue = bundle.getString("issue");
        lotcode = bundle.getString("lotcode");
        order_type = bundle.getString("order_type");
        wrate = bundle.getString("wrate");//提成比例
        bnum = bundle.getString("bnum");//合买时候购买的金额
        pnum = bundle.getString("pnum");//合买时候保底金额
        desc = bundle.getString("desc");
        type = bundle.getString("type");
        isChase = bundle.getInt("isChase", 0);
        orderId = bundle.getString("orderId");
        fromWhere = bundle.getString("from");
        mutile = bundle.getString("mutile");
        mFlag = bundle.getBoolean("mFlag", false);
        mGodName = bundle.getString("mGodName");
        mGodUserId = bundle.getString("mGodUserId");
        maxTheoryBonus = bundle.getDouble("maxTheoryBonus", 0);

        allCount = bundle.getInt("allCount");
        oneMoney = bundle.getString("oneMoney");
        pickType = bundle.getString("pickType");
        stopType = bundle.getString("stopType");
        stopPrize = bundle.getString("stopPrize");
    }

    public NetWorkData(Context mContext) {
        this.mContext = mContext;
    }
    public NetWorkData(Context mContext,CallBackListener mListener){

    }
    public interface CallBackListener{
        void success(String result);
        void error();
    }
    public void orderForm() {
        //防止连续点击
        if (ClickUtils.isFastClick(2000)) {
            return;
        }
        if (!TextUtils.isEmpty(money)) {
            if (Double.parseDouble(money) > 100000) {
                Toast.makeText(mContext, "单笔投注金额不能超过10万！", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            Toast.makeText(mContext, "单笔投注金额不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        mHttp = new HttpUtils2(mContext);
        switch (buyMethod) {
            case "normal":
                normalBuy();
                break;
            case "hemai":
                hemaiBuy();
                break;
        }
    }

    //参与合买或者跟单
    public void genheOrderForm() {
        //防止连续点击
        if (ClickUtils.isFastClick(2000)) {
            return;
        }
        mHttp = new HttpUtils2(mContext);
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        String url;
        Bundle bundle = new Bundle();
        if (mFlag) {
            url = "followBuy/create/v1";
            bundle.putString("multi", mutile + "");
        } else {
            url = "togetherBuy/create/v1";
            bundle.putString("buyNum", mutile + "");
        }
        bundle.putString("orderId", orderId);
        bundle.putString("money", money);
        bundle.putString("channel", Number.CHANNELIDBUY);
        bundle.putString("token", LotteryApp.token);
        bundle.putString("client", "android");
        bundle.putString("version", AppUtils.getVersionName(mContext));
        mHttp.postH(url, bundle, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                intent(result, money, "参与");
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }


    //正常购买 1
    private void normalBuy() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        if (allCount > 1) {//大于1说明是数字彩的追号功能开启
            params.putString("allCount", allCount + "");
            params.putString("oneMoney", oneMoney);
            params.putString("pickType", pickType);
            params.putString("stopType", stopType);
            params.putString("stopPrize", stopPrize);
            params.putString("orderType", "2");
        } else params.putString("orderType", "0");
        params.putString("token", LotteryApp.token);
        params.putString("channel", Number.CHANNELIDBUY);
        params.putString("client", "android");
        params.putString("version", AppUtils.getVersionName(mContext));
        params.putString("content", content);
        //params.putString("codes","HH|SPF>20160825003=3(3.70),SPF>20160825002=3(1.38),RQSPF>20160825002=3{-1}(2.38)|2*1");
        //params.putString("codes","HH|RQSPF>20160825002=3{-1}(2.38),SPF>20160825003=3(3.70),SPF>20160825002=3(1.38)|2*1");
        params.putString("lotCode", lotcode);
        params.putString("money", money);
        params.putString("multi", mMulti + "");
        params.putString("issue", issue);
        params.putString("playType", "0");
        params.putString("betNum", shakes + "");
        params.putString("isChase", isChase + "");

        mHttp.postH(Url.BUYLOTTERY_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                intent(result, money, "自购");
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    //发起合买或者跟单
    private void hemaiBuy() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("content", content);
        params.putString("client", "android");
        params.putString("version", AppUtils.getVersionName(mContext));
        params.putString("issue", issue);
        params.putString("lotCode", lotcode);
        params.putString("money", money);
        params.putString("multi", mMulti + "");
        params.putString("betNum", shakes + "");
        //is_chase是否追加(默认0)(用于大乐透)0：不是，1：是
        params.putString("isChase", isChase + "");
        //order_type 0:代购,1:发起合买,2:追号,3:参与合买,7:发起跟单
        params.putString("orderType", order_type + "");
        params.putString("playType", "0");
        params.putString("channel", Number.CHANNELIDBUY);
        params.putString("totalNum", money);
        params.putString("ratio", wrate);
        params.putString("buyNum", bnum);
        params.putString("baodiNum", pnum);
        params.putString("secret", type + "");
        params.putString("title", "第" + issue + "期合买");
        params.putString("desc", desc);
        if (mMulti == -1) {//奖金优化
            String bonus;
            if (maxTheoryBonus > 100) {
                String bonusString = (int) maxTheoryBonus + "";
                String substring = bonusString.substring(0, 1);
                String substring1 = bonusString.substring(1, 2);
                if (Integer.parseInt(substring1) < 5) {
                    bonus = substring + "0" + getNumber(bonusString.length());
                } else bonus = substring + "5" + getNumber(bonusString.length());
            } else {
                bonus = (int) maxTheoryBonus + "";
            }
            params.putString("theoreticalPrize", bonus);
        } else {//没有奖金优化
            String bonus;
            if (maxTheoryBonus * 2 * mMulti > 100) {
                String bonusString = ((int) (maxTheoryBonus * 2 * mMulti)) + "";
                String substring = bonusString.substring(0, 1);
                String substring1 = bonusString.substring(1, 2);
                if (Integer.parseInt(substring1) < 5) {
                    bonus = substring + "0" + getNumber(bonusString.length());
                } else bonus = substring + "5" + getNumber(bonusString.length());
            } else {
                bonus = ((int) maxTheoryBonus * 2 * mMulti) + "";
            }
            params.putString("theoreticalPrize", bonus);
        }

        mHttp.postH(Url.BUYLOTTERY_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                //合买的时候需要付认购的钱。
                if ("1".equals(order_type)) intent(result, bnum, "推荐");
                else intent(result, money, "推荐");
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private String getNumber(int number) {
        switch (number) {
            case 3:
                return "0";
            case 4:
                return "00";
            case 5:
                return "000";
            case 6:
                return "0000";
            case 7:
                return "00000";
            case 8:
                return "000000";
            case 9:
                return "0000000";
            case 10:
                return "00000000";
        }
        return "";
    }



    public void checkLoginStatus() {
        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        boolean islogin = sp.getBoolean("islogin", false);
        boolean isThird = sp.getBoolean("isThird", false);
        //如果是登录状态的话直接登录
        if (islogin&&!LotteryApp.isLogin) {//sp中存的是登录状态，但是isLogin确实false。这个时候需要进行一次登录。
            HttpInterface2 mHttpInterface=new HttpUtils2(mContext);
            //是登录状态，但是不是第三方登录
            if (!isThird) {
                final String username = sp.getString("username", "");
                final String password = sp.getString("password", "");
                final String salt = sp.getString("salt", "");

                Bundle params = new Bundle();
                params.putString("userName", username);
                params.putString("password", StringUtil.getMD5(password));
                params.putString("timeStamp", System.currentTimeMillis() + "");
                mHttpInterface.post(Url.LOGIN_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        if (result != null && result != "" && result.length() > 100) {
                            Gson gson = new Gson();
                            LoginResultData loginResultData = gson.fromJson(result, LoginResultData.class);
                            LogUtils.e("返回结果是", result);
                            if (loginResultData.getCode() == 0) {
                                //登录返回值获取成功
                                LotteryApp.token = loginResultData.getData().getToken();
                                LotteryApp.uid = loginResultData.getData().getUserId();
                                LotteryApp.nikeName=loginResultData.getData().getSuggestDisplayName();
                                LotteryApp.userType = loginResultData.getData().getUserType();
                                Number.CHANNELIDBUY = loginResultData.getData().getChannelId();
                                LotteryApp.salt = salt;
                                LotteryApp.isLogin = true;
                                LotteryApp.isThird = false;
                                SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                                //实例化SharedPreferences.Editor对象
                                SharedPreferences.Editor editor = sp.edit();
                                //用putString的方法保存数据
                                editor.putString("username", username);
                                editor.putString("password", password);
                                editor.putString("salt", salt);
                                editor.putBoolean("islogin", true);
                                editor.putBoolean("isThird", false);
                                //提交当前数据
                                editor.commit();
                                //刷新用户信息
                              refreshUserInfo();
                            }
                        } else {
                        }
                        getPrivilege();
                    }

                    @Override
                    public void onError() {

                    }
                });
            } else {
                //是第三方登录的状态
                final String partnerCode = sp.getString("partnerCode", "");
                final String openId = sp.getString("openId", "");
                Bundle params = new Bundle();
                params.putString("channelId", Number.CHANNELID);
                params.putString("partnerCode", partnerCode);
                //params.putString("headImg",headImg);
                params.putString("timeStamp", System.currentTimeMillis() + "");
                params.putString("openId", openId);
                mHttpInterface.post(Url.THIRD_LOGIN_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                LotteryApp.token = data.getString("token");
                                LotteryApp.uid = data.getString("userId");
                                LotteryApp.isLogin = true;
                                LotteryApp.isThird = true;
                                //Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                                //实例化SharedPreferences.Editor对象
                                SharedPreferences.Editor editor = sp.edit();
                                //用putString的方法保存数据
                                editor.putString("partnerCode", partnerCode);
                                editor.putString("password", openId);
                                editor.putBoolean("islogin", true);
                                //是否为第三方登录
                                editor.putBoolean("isThird", true);
                                //提交当前数据
                                editor.commit();
                                //刷新用户信息
                                NetWorkData netWorkData = new NetWorkData(mContext);
                                netWorkData.refreshUserInfo();
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            }
                            getPrivilege();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            }

        }

    }

    private void getPrivilege() {
        HttpInterface2 mHttpInterface = new HttpUtils2(mContext);
        final Bundle params = new Bundle();
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        mHttpInterface.post(Url.PRIVILEGE_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                LogUtils.e("获取用户权限", result);
                Gson gson = new Gson();
                PrivilegeData privilegeData = gson.fromJson(result, PrivilegeData.class);
                List<PrivilegeData.DataBean.ItemsBean> items = privilegeData.getData().getItems();
                for (int i = 0; i < items.size(); i++) {
                    if ("buy_right".equals(items.get(i).getKeyi())) {
                        LotteryApp.buyPrivilege = items.get(i).getValue();
                    }
                    if ("recommend_right".equals(items.get(i).getKeyi())) {
                        LotteryApp.recommendPrivilege = items.get(i).getValue();
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }


    public void refreshUserInfo() {
        HttpInterface2 mHttpInterface = new HttpUtils2(mContext);
        Bundle params1 = new Bundle();
        params1.putString("token", LotteryApp.token);
        params1.putString("userId", LotteryApp.uid);
        params1.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.USER_INFO_URL, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(result, UserInfo.class);
                if (userInfo.getCode() == 0) {
                    long userId = userInfo.getData().getUserId();
                    LotteryApp.userType = userInfo.getData().getUserType();
                   /* if (userId == 2000000) {
                        userInfo.getData().setSuggestDisplayName(sp.getString("username", ""));
                        userInfo.getData().setHeadImg(sp.getString("imagUrl", ""));
                    }*/
                    LotteryApp.phone = userInfo.getData().getPhone();
                    Number.CHANNELIDBUY = userInfo.getData().getChannelId();
                    LotteryApp.nikeName = userInfo.getData().getSuggestDisplayName();
                    //身份证，银行卡，手机号 是否绑定的状态
                    setStatus(userInfo);

                }
            }

            @Override
            public void onError() {
            }
        });
    }

    private void setStatus(UserInfo userInfo) {
        //身份证号
        String mIdentification = userInfo.getData().getIdentification();
        if (mIdentification != null && mIdentification.length() != 0) {
            LotteryApp.cardFlag = true;
            LotteryApp.realName = userInfo.getData().getRealName();
        } else {
            LotteryApp.cardFlag = false;
        }
        //银行卡号
        String bankCard = userInfo.getData().getBankCard();
        if (bankCard != null && bankCard.length() != 0)
            LotteryApp.bankFlag = true;

        else
            LotteryApp.bankFlag = false;

        //手机号
        String mMobile = userInfo.getData().getPhone();
        if (mMobile != null && mMobile.length() != 0)
            LotteryApp.phoneFlag = true;
        else
            LotteryApp.phoneFlag = false;

    }

    private void intent(String result, String money, String buyType) {
        Gson gson = new Gson();
        OrderFormBean orderFormBean = gson.fromJson(result, OrderFormBean.class);
        if (orderFormBean.getCode() == 0) {
            OrderFormBean.DataBean data = orderFormBean.getData();
            String orderCode = data.getOrderCode();
            String remark = data.getRemark();
            if (!TextUtils.isEmpty(orderCode) && !TextUtils.isEmpty(remark)) {
                Intent intent = new Intent(mContext, BuyActivity.class);
                intent.putExtra("orderCode", orderCode);
                intent.putExtra("remark", remark);
                intent.putExtra("lotcode", lotcode);
                intent.putExtra("money", money);
                intent.putExtra("allCount", allCount);
                intent.putExtra("content", content);
                intent.putExtra("buyType", buyType);
                intent.putExtra("mGodUserId", mGodUserId);
                intent.putExtra("mGodName", mGodName);
                ((Activity) mContext).startActivityForResult(intent, requestCode);
            }
        } else {
            HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(mContext);
            hintDialogUtils2.setMessage(orderFormBean.getMsg());
        }
    }

    public void winningHint() {
        HttpUtils2 mHttpInterface = new HttpUtils2(mContext);
        mHttpInterface.jsonByUrl(Url.WINNING_URL + "?token=" + LotteryApp.token, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("code") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String orderId = data.getString("orderId");
                        String userId = data.getString("userId");
                        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                        String ordernumber = sp.getString(userId, "");
                        if (!orderId.equals(ordernumber)) {
                            View inflate = View.inflate(mContext, R.layout.dialog_winning_hint, null);
                            ImageView mClose = (ImageView) inflate.findViewById(R.id.close);
                            ImageView mDetail = (ImageView) inflate.findViewById(R.id.detail_btn);

                            final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
                            dialog.setContentView(inflate);
                            dialog.show();
                            Window window = dialog.getWindow();
                            window.setWindowAnimations(R.style.anim_in_out);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(userId, orderId);
                            editor.commit();
                            //点击叉叉
                            mClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            //点击查看详情
                            mDetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(mContext, OrderFormActivity.class);
                                    mContext.startActivity(intent);
                                }
                            });
                        }
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

    public void setSkip(String remarks) {
        if (remarks == null || remarks.length() == 0) return;
        String[] link = remarks.split("\\|");
        switch (link[0]) {
            case "-1":
                //浏览器自己打开
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(link[1]);
                intent.setData(content_url);
                mContext.startActivity(intent);
                break;
            case "1":
                //跳转某一个购彩界面界面
                String lotcode = link[1];
                if ("1000".equals(lotcode)) {
                    Intent intent2 = new Intent(mContext, JczqActivity.class);
                    intent2.putExtra("lotcode", "42");
                    intent2.putExtra("bunch", false);
                    mContext.startActivity(intent2);
                } else if ("42".equals(lotcode)) {
                    Intent intent2 = new Intent(mContext, JczqActivity.class);
                    intent2.putExtra("lotcode", lotcode);
                    intent2.putExtra("bunch", true);
                    mContext.startActivity(intent2);
                } else if ("11".equals(lotcode) || "19".equals(lotcode)) {
                    Intent intent2 = new Intent(mContext, SfcAndRjcActivity.class);
                    intent2.putExtra("lotcode", lotcode);
                    mContext.startActivity(intent2);
                } else if ("1001".equals(lotcode)) {
                    Intent intent2 = new Intent(mContext, JclqActivity.class);
                    intent2.putExtra("lotcode", "43");
                    intent2.putExtra("bunch", false);
                    mContext.startActivity(intent2);
                } else if ("43".equals(lotcode)) {
                    Intent intent2 = new Intent(mContext, JclqActivity.class);
                    intent2.putExtra("lotcode", lotcode);
                    intent2.putExtra("bunch", true);
                    mContext.startActivity(intent2);
                } else if ("30".equals(lotcode)) {
                    mContext.startActivity(new Intent(mContext, ChampionActivity.class));
                } else {
                    Intent intent2 = new Intent(mContext, NumberActivity.class);
                    intent2.putExtra("lotcode", lotcode);
                    intent2.putExtra("whereFlag", true);
                    mContext.startActivity(intent2);
                }
                break;
            case "0":
                //本app内打开页面
                Intent intent1 = new Intent(mContext, H5Activity.class);
                intent1.putExtra("url", link[1]);
                mContext.startActivity(intent1);
                break;
            case "2":
                //跳转界面
                IntentSkip.skip(mContext, link[1]);

        }
    }

    public void addDeleteTag() {
        if (!LotteryApp.isLogin) return;
        final TagManager tagManager = PushAgent.getInstance(mContext).getTagManager();
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        mHttp.get(Url.PUSH_TAG, params, new JsonInterface() {
            @Override
            public void callback(String result) {//先请求后台存的tag。成功后再去删除
                final UmengTag umengTag = new Gson().fromJson(result, UmengTag.class);
                int code = umengTag.getCode();
                if (code == 0) {
                    tagManager.getTags(new TagManager.TagListCallBack() {
                        @Override
                        public void onMessage(boolean b, List<String> list) {
                            String[] tag = new String[list.size()];
                            for (int i = 0; i < list.size(); i++) {
                                tag[i] = list.get(i);
                                Log.e("所有的tag", tag[i]);
                            }
                            tagManager.deleteTags(new TagManager.TCallBack() {//删除
                                @Override
                                public void onMessage(boolean b, ITagManager.Result result) {//删除成功之后再添加。
                                    List<UmengTag.DataBean> data = umengTag.getData();
                                    if (data != null) {
                                        String[] tag1 = new String[data.size()];
                                        for (int i = 0; i < data.size(); i++) {
                                            UmengTag.DataBean dataBean = data.get(i);
                                            tag1[i] = dataBean.getUserTag();
                                        }
                                        tagManager.addTags(new TagManager.TCallBack() {
                                            @Override
                                            public void onMessage(boolean b, ITagManager.Result result) {
                                                Log.e("添加tag结果:", b + result.toString());
                                            }
                                        }, tag1);
                                    }
                                }
                            }, tag);
                        }
                    });


                }
            }

            @Override
            public void onError() {

            }
        });
    }

    public void addUmTag() {
        if (!LotteryApp.isLogin) return;
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        mHttp.get(Url.PUSH_TAG, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                UmengTag umengTag = new Gson().fromJson(result, UmengTag.class);
                int code = umengTag.getCode();
                if (code == 0) {
                    List<UmengTag.DataBean> data = umengTag.getData();
                    if (data != null) {
                        String[] tag = new String[data.size()];
                        for (int i = 0; i < data.size(); i++) {
                            UmengTag.DataBean dataBean = data.get(i);
                            tag[i] = dataBean.getUserTag();
                            Log.e("添加tag", tag[i]);
                        }
                        PushAgent.getInstance(mContext).getTagManager().addTags(new TagManager.TCallBack() {
                            @Override
                            public void onMessage(boolean b, ITagManager.Result result) {
                                Log.e("添加tag", "添加tag成功");
                            }
                        }, tag);
                    }

                }
            }

            @Override
            public void onError() {

            }
        });
    }

    public void deleteUmengTag() {
        final TagManager tagManager = PushAgent.getInstance(mContext).getTagManager();
        tagManager.getTags(new TagManager.TagListCallBack() {
            @Override
            public void onMessage(boolean b, List<String> list) {
                String[] tag = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    tag[i] = list.get(i);
                }
                tagManager.deleteTags(new TagManager.TCallBack() {
                    @Override
                    public void onMessage(boolean b, ITagManager.Result result) {

                    }
                }, tag);
            }
        });
    }

    public void launchImage() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("category", "5");
        params.putString("typeFlag", "launch");
        params.putString("pageSize", "10");
        params.putString("pageNumber", "1");
        params.putString("status", "0");
        params.putString("token", "1");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.NEWS_URL, params, new JsonInterface() {

            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                PictureData pictureData = gson.fromJson(result, PictureData.class);
                if (pictureData.getCode() == 0
                        && pictureData.getData() != null
                        && pictureData.getData().getItems() != null
                        && pictureData.getData().getItems().size() > 0) {
                    String logoURL = pictureData.getData().getItems().get(0).getLogoURL();
                    SpUtils.setString("launchImage", Url.ROOT_URL + logoURL);
                }
            }

            @Override
            public void onError() {

            }
        });
    }
    public void deletePost(){

    }
}
