package com.daxiang.lottery.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.TwoCodeActivity;
import com.daxiang.lottery.activity.lotteryactivity.BankEidteActivity;
import com.daxiang.lottery.activity.lotteryactivity.BillRecordActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.CardActivity;
import com.daxiang.lottery.activity.lotteryactivity.ExpandActivity;
import com.daxiang.lottery.activity.lotteryactivity.NewsAdActivity;
import com.daxiang.lottery.activity.lotteryactivity.OrderFormActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.activity.lotteryactivity.PostMessageActivity;
import com.daxiang.lottery.activity.lotteryactivity.PushSettingActivity;
import com.daxiang.lottery.activity.lotteryactivity.RechargeActivity;
import com.daxiang.lottery.activity.lotteryactivity.SettingActivity;
import com.daxiang.lottery.activity.lotteryactivity.TikuanActivity;
import com.daxiang.lottery.activity.lotteryactivity.UserInfoActivity;
import com.daxiang.lottery.activity.lotteryactivity.redpacket.MyRedpacketActivity;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.BillWalletData;
import com.daxiang.lottery.entity.RechargeMethodData;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.view.CircleImageView;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.daxiang.lottery.constant.Number.THIRD_UID;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class LotteryFragment extends Fragment {
    private View v;
    // private TitleBar mTitleBar;
    private View mTitleView;
    private SmartRefreshLayout mRefresh;
    private ImageView mShare;
    private TextView mPhone;
    private LinearLayout mLlRecord;
    private LinearLayout mBillRecord;
    private LinearLayout mLl_wait;
    private LinearLayout mLl_payment;
    private LinearLayout mLl_zhuihao;
    private LinearLayout mLl_award_sucess;
    private LinearLayout mLlBalance;
    private LinearLayout mLlNewsAd;
    private LinearLayout mTiKuan;
    private LinearLayout mLl_recharge;
    private LinearLayout mLl_expand;
    private LinearLayout mLl_redpacket;
    private LinearLayout mLl_setting;
    private LinearLayout mLl_money;
    private LinearLayout mLl_liebiao;
    private LinearLayout mLl_record;
    private LinearLayout mPush_setting;
    private LinearLayout mPost_message;
    private View mExpandLine;
    private ImageView mOpen_hide;
    private TextView mGongGao;
    private TextView mNickname;
    private TextView mBalance;
    private CircleImageView mImageHeader;
    private HttpInterface2 mHttpInterface;
    //用户钱包信息
    BillWalletData billWalletData;
    //用户资料
    UserInfo userInfo = new UserInfo();
    //银行卡信息
    // private BankInfoData mBankInfoData = new BankInfoData();
    //是否绑定了银行卡
    boolean bankFlag;
    //获取支持的充值方式
    private ArrayList<RechargeMethodData.DataBean.ItemsBean> mRechargeList;
    //头像的接口
    private String headerUrl;
    private SharedPreferences sp;

    public void setData() {
        addData();
    }

    private String totalBalance;//账户余额

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_lottery, null);
        sp = getActivity().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        initView();
        addListener();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        addData();
    }

    private void initView() {
        mTitleView = v.findViewById(R.id.titleView);
        mShare = (ImageView) v.findViewById(R.id.share);
        mRefresh = (SmartRefreshLayout) v.findViewById(R.id.refresh);
        mLlRecord = (LinearLayout) v.findViewById(R.id.ll_bet_record);
        mLl_wait = (LinearLayout) v.findViewById(R.id.ll_wait);
        mLl_payment = (LinearLayout) v.findViewById(R.id.ll_payment);
        mLl_zhuihao = (LinearLayout) v.findViewById(R.id.ll_zhuihao);
        mLl_award_sucess = (LinearLayout) v.findViewById(R.id.ll_award_sucess);
        mPhone = (TextView) v.findViewById(R.id.tv_phone);
        mImageHeader = (CircleImageView) v.findViewById(R.id.image_header);
        mLlNewsAd = (LinearLayout) v.findViewById(R.id.news_ad);
        mLl_recharge = (LinearLayout) v.findViewById(R.id.chongzhi);
        mNickname = (TextView) v.findViewById(R.id.tv_nickname);
        //余额
        mBalance = (TextView) v.findViewById(R.id.tv_money_ss);
        mOpen_hide = (ImageView) v.findViewById(R.id.open_hide);
        mLlBalance = (LinearLayout) v.findViewById(R.id.ll_balance);
        mBillRecord = (LinearLayout) v.findViewById(R.id.bill_record);
        mTiKuan = (LinearLayout) v.findViewById(R.id.tikuan);
        mExpandLine = v.findViewById(R.id.expand_line);
        mLl_expand = (LinearLayout) v.findViewById(R.id.ll_expand);
        mLl_redpacket = (LinearLayout) v.findViewById(R.id.ll_redpacket);
        mPush_setting = (LinearLayout) v.findViewById(R.id.push_setting);
        mLl_setting = (LinearLayout) v.findViewById(R.id.ll_setting);
        mPost_message = (LinearLayout) v.findViewById(R.id.post_message);
        mLl_money = (LinearLayout) v.findViewById(R.id.ll_money);
        mLl_record = (LinearLayout) v.findViewById(R.id.ll_record);
        mLl_liebiao = (LinearLayout) v.findViewById(R.id.ll_liebiao);
        mGongGao = (TextView) v.findViewById(R.id.gonggao);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleView.setVisibility(View.VISIBLE);
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dimensionPixelSize);
                mTitleView.setLayoutParams(layoutParams);
            }
        } else mTitleView.setVisibility(View.GONE);
       /* if (LotteryApp.isBuy == 1) {
            mLl_liebiao.setVisibility(View.VISIBLE);
            mLl_record.setVisibility(View.VISIBLE);
            mLlRecord.setVisibility(View.VISIBLE);
            mLl_money.setVisibility(View.VISIBLE);
            mGongGao.setVisibility(View.GONE);
        } else {
            mLl_liebiao.setVisibility(View.GONE);
            mLl_record.setVisibility(View.GONE);
            mLlRecord.setVisibility(View.GONE);
            mLl_money.setVisibility(View.GONE);
            mGongGao.setVisibility(View.VISIBLE);
        }*/
    }


    private void addData() {
        if (LotteryApp.uid.equals(THIRD_UID + "")) {
            mExpandLine.setVisibility(View.GONE);
            mLl_expand.setVisibility(View.GONE);
        } else {
            if (LotteryApp.userType == null || "0".equals(LotteryApp.userType)) {
                mLl_expand.setVisibility(View.GONE);
                mExpandLine.setVisibility(View.GONE);
            } else {
                mLl_expand.setVisibility(View.VISIBLE);
                mExpandLine.setVisibility(View.VISIBLE);
            }
        }
        userInfoData();
        billData();
    }

    public void userInfoData() {
        //用户信息
        mHttpInterface = new HttpUtils2(getActivity());
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.USER_INFO_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                userInfo = gson.fromJson(result, UserInfo.class);
                if (userInfo.getCode() == 0) {
                    long userId = userInfo.getData().getUserId();
                    LotteryApp.userType = userInfo.getData().getUserType();
                    if (userId == 2000000) {
                        userInfo.getData().setSuggestDisplayName(sp.getString("username", ""));
                        userInfo.getData().setHeadImg(sp.getString("imagUrl", ""));
                    }
                    LotteryApp.phone = userInfo.getData().getPhone();
                    Number.CHANNELIDBUY = userInfo.getData().getChannelId();
                    LotteryApp.nikeName = userInfo.getData().getSuggestDisplayName();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", LotteryApp.nikeName);
                    edit.commit();
                    String portraitUrl = Url.HEADER_ROOT_URL + userInfo.getData().getUserId();
                    mNickname.setText(TextUtils.isEmpty(LotteryApp.nikeName) ? "--" : LotteryApp.nikeName);
                    mPhone.setText(StringUtil.phoneHint(LotteryApp.phone));
                    Picasso.with(getContext()).load(portraitUrl).error(R.mipmap.default_header).placeholder(R.mipmap.default_header)
                            .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(mImageHeader);
                    //身份证，银行卡，手机号 是否绑定的状态
                    setStatus(userInfo);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
            }
        });
    }

    public void billData() {//用户钱包数据
        //用户信息
        mHttpInterface = new HttpUtils2(getActivity());
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.BILL_WALLET_URL, params, new JsonInterface() {

            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();

                Gson gson = new Gson();
                billWalletData = gson.fromJson(result, BillWalletData.class);
                if (billWalletData.getCode() == 0) {
                    BillWalletData.DataBean data = billWalletData.getData();
                    totalBalance = data.getTotalBalance();
                    String totalBalance = data.getTotalBalance();
                    String freezeBalance = data.getFreezeBalance();//冻结金额
                    BigDecimal consumeBalance = new BigDecimal(totalBalance).subtract(new BigDecimal(freezeBalance));
                    LotteryApp.balance = totalBalance;
                    LotteryApp.consumeBanlance = consumeBalance + "";
                    mBalance.setText(LotteryApp.consumeBanlance);

                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();

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

    private void addListener() {
        //刷新数据
        mRefresh.setOnRefreshListener(scrollViewListener);
        //点击昵称
        mImageHeader.setOnClickListener(new NicknameOnclick());
        //账单明细
        mBillRecord.setOnClickListener(BillOnclick);
        //全部订单
        mLlRecord.setOnClickListener(RecordOnclick);
        //待支付
        mLl_payment.setOnClickListener(RecordOnclick);
        //待开奖
        mLl_wait.setOnClickListener(RecordOnclick);
        //已中奖
        mLl_award_sucess.setOnClickListener(RecordOnclick);
        //追号单
        mLl_zhuihao.setOnClickListener(RecordOnclick);
        //点击余额
        mLlBalance.setOnClickListener(BillOnclick);
        //显示或者隐藏金额
        mOpen_hide.setOnClickListener(OpenHideListener);
        //点击充值
        mLl_recharge.setOnClickListener(RechargeListener);
        //点击提款
        mTiKuan.setOnClickListener(TikuanListener);
        //新闻广告
        mLlNewsAd.setOnClickListener(NewsAdListener);
        //设置
        mLl_setting.setOnClickListener(SettingListener);
        //推广服务
        mLl_expand.setOnClickListener(ExpandListener);
        //我的红包
        mLl_redpacket.setOnClickListener(RedpacketListener);
        //推送设置
        mPush_setting.setOnClickListener(PushSettingListener);
        //帖子消息
        mPost_message.setOnClickListener(PostMessageListener);
        //分享
        //分享二位码
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("weixin://wxpay/bizpayurl?pr=UgaOMDG")));
                Intent intent = new Intent(getContext(), TwoCodeActivity.class);
                startActivity(intent);
            }
        });

    }

    //设置按钮
    View.OnClickListener SettingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        }
    };
    OnRefreshListener scrollViewListener = new OnRefreshListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            addData();
        }
    };
    //充值
    View.OnClickListener RechargeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            if (userInfo == null) return;
            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
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
                            startActivityForResult(intent, 10);
                        } else {
                            Intent intent = new Intent(getActivity(), BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivityForResult(intent, 10);
                        }
                    }
                });
            } else if (!LotteryApp.cardFlag) {
                HintDialogUtils.setHintDialog(getContext());
                HintDialogUtils.setMessage("您还没有绑定身份证，请先绑定身份证");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        //身份证未绑定
                        Intent intent = new Intent(getActivity(), CardActivity.class);
                        intent.putExtra("cardFlag", false);
                        intent.putExtra("name", userInfo.getData().getRealName());
                        intent.putExtra("number", userInfo.getData().getIdentification());
                        startActivityForResult(intent, 10);
                    }
                });
            } /*else if (!LotteryApp.bankFlag) {
                HintDialogUtils.setHintDialog(getContext());
                HintDialogUtils.setMessage("您还没有绑定银行卡，请先绑定银行卡");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        //银行卡未绑定
                        Intent intent = new Intent(getActivity(), BankActivity.class);
                        intent.putExtra("mDisplayname", userInfo.getData().getUserName());
                        intent.putExtra("bankFlag", false);
                        startActivityForResult(intent, 10);
                    }
                });

            }*/ else {
                //跳转到充值界面
                Intent intent = new Intent(getActivity(), RechargeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", userInfo);
                intent.putExtras(bundle);
                startActivityForResult(intent, 10);
            }
        }
    };

    //提款
    View.OnClickListener TikuanListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            if (userInfo == null || userInfo.getData() == null) return;
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
                            startActivityForResult(intent, 10);
                        } else {
                            Intent intent = new Intent(getContext(), BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivity(intent);
                        }
                    }
                });
            } else if (!LotteryApp.cardFlag) {
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
            } else if (!LotteryApp.bankFlag) {
                HintDialogUtils.setHintDialog(getContext());
                HintDialogUtils.setMessage("您还没有绑定银行卡，请先绑定银行卡");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        //银行卡未绑定
                        Intent intent = new Intent(getContext(), BankEidteActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userInfo", userInfo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            } else {
                if (billWalletData == null || billWalletData.getData() == null) return;
                Intent intent = new Intent(getActivity(), TikuanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", userInfo);
                //bundle.putSerializable("bankInfo", mBankInfoData);
                String hdAmount = billWalletData.getData().getFreeBalance();
                bundle.putString("hdAmount", hdAmount);
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
    }

    View.OnClickListener BillOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), BillRecordActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", billWalletData);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    private boolean openHide = true;
    View.OnClickListener OpenHideListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (openHide) {
                openHide = false;
                mOpen_hide.setImageResource(R.mipmap.hide_money);
                mBalance.setText("******");
            } else {
                openHide = true;
                mOpen_hide.setImageResource(R.mipmap.open_money);
                mBalance.setText(TextUtils.isEmpty(totalBalance) ? "--" : totalBalance);
            }
        }
    };
    View.OnClickListener RecordOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(3000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), OrderFormActivity.class);
            switch (v.getId()) {
                case R.id.ll_bet_record:
                    intent.putExtra("type", 0);
                    break;
                case R.id.ll_payment:
                    intent.putExtra("type", 1);
                    break;
                case R.id.ll_wait:
                    intent.putExtra("type", 2);
                    break;
                case R.id.ll_award_sucess:
                    intent.putExtra("type", 3);
                    break;
                case R.id.ll_zhuihao:
                    intent.putExtra("type", 4);
                    break;
            }
            startActivity(intent);
        }
    };

    class NicknameOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            if (userInfo == null) return;
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("userInfo", userInfo);
            intent.putExtras(bundle);
            startActivityForResult(intent, 10);
        }
    }

    View.OnClickListener NewsAdListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), NewsAdActivity.class);
            startActivity(intent);
        }
    };
    //推广服务
    View.OnClickListener ExpandListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getContext(), ExpandActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener PushSettingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getContext(), PushSettingActivity.class));
        }
    };
    View.OnClickListener PostMessageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getContext(), PostMessageActivity.class));
        }
    };
    View.OnClickListener RedpacketListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击全部玩法的时候跳出来多个框
            if (ClickUtils.isFastClick()) {
                return;
            }
            Intent intent = new Intent(getContext(), MyRedpacketActivity.class);
            startActivity(intent);
        }
    };
}
