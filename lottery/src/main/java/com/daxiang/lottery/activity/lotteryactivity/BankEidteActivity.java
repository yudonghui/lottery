package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.IsExsitsResultData;
import com.daxiang.lottery.entity.SendMsgData;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.BankIdCheckUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.WheelView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BankEidteActivity extends BaseActivity {
    // Content View Elements

    private TextView mBank_name;
    private EditText mBank_username;
    private EditText mBank_name_small;
    private EditText mBank_idcard;
    private TextView mText_obtain_code;
    //private EditText mEtPhone;
    private EditText mEtCode;
    private Button mBank_comit;
    // private RelativeLayout mRlPhone;
    private LinearLayout mRlCode;
    private View mLine;
    private View inflate;
    private WheelView mWheelView;
    private TextView mCancel;
    private TextView mConfirm;
    private int bankID = 1;
    private String selectedIndex;
    AlertDialog mDialog;
    UserInfo userInfo;
    ArrayList<String> bankList = new ArrayList<>();
    private JSONObject item;
    int time;
    private Handler mHandler = new Handler();
    private String phone;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       initContentView(R.layout.activity_bank_eidte);
        mContext=this;
        Intent intent = getIntent();
        userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
        phone = LotteryApp.phone;
        bindViews();
        addData();
        addListener();
    }
    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("银行卡绑定");
        mTitleBar.setTitleVisibility(true);
        mBank_name = (TextView) findViewById(R.id.bank_name);
        mBank_username = (EditText) findViewById(R.id.bank_username);
        mBank_name_small = (EditText) findViewById(R.id.bank_name_small);
        mBank_idcard = (EditText) findViewById(R.id.bank_idcard);
        mBank_comit = (Button) findViewById(R.id.bank_comit);
        //mEtPhone= (EditText) findViewById(R.id.edit_phone);
        mEtCode = (EditText) findViewById(R.id.edit_code);
        mText_obtain_code = (TextView) findViewById(R.id.text_obtain_code);
        // mRlPhone= (RelativeLayout) findViewById(R.id.rl_phone);
        mRlCode = (LinearLayout) findViewById(R.id.ll_code);
        mLine = findViewById(R.id.line);
        inflate = View.inflate(mContext, R.layout.dialog_bank, null);
        mWheelView = (WheelView) inflate.findViewById(R.id.wheelview);
        mCancel = (TextView) inflate.findViewById(R.id.cancel);
        mConfirm = (TextView) inflate.findViewById(R.id.confirm);

        mDialog = new AlertDialog.Builder(mContext).create();
        //只能以绑定了身份证的人的银行卡绑定，所以名字直接是身份证的名字，不能更改
        if (LotteryApp.realName != null) {
            mBank_username.setText(LotteryApp.realName);
        }
        mBank_username.setFocusable(false);
    }
    private void addListener() {
        /**
         * 获取验证码，
         * 分为两步：一 先请求网络判断用户是否存在
         *          二 如果用户存在再次请求网络要求发送验证码
         * */
        mText_obtain_code.setOnClickListener(ObtainCodeListener);

        mConfirm.setOnClickListener(confirmListener);
        mCancel.setOnClickListener(CancelListener);
        mBank_comit.setOnClickListener(comitListener);
        mBank_name.setOnClickListener(bankNameListener);
    }
    private void addData() {
        //访问网络获取银行的id
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.BANKID_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("code") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        item = data.getJSONObject("item");
                        for (int i = 1; i <= 151; i++) {
                            bankList.add(item.getString("" + i));
                            // bankList[i-1]=item.getString(""+i);
                        }
                       // mBank_name.setText(item.getString(userInfo.getData().getCardType()));
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
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };
    View.OnClickListener confirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String seletedItem = mWheelView.getSeletedItem();
            selectedIndex = mWheelView.getSeletedIndex() + "1";
            mBank_name.setText(seletedItem);
            mDialog.dismiss();
            // Log.e("返回值：",seletedIndex+seletedItem);
        }
    };
    View.OnClickListener bankNameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (bankList.size() != 0) {
                Intent intent = new Intent(mContext, BankListActivity.class);
                intent.putExtra("bankList", bankList);
                startActivityForResult(intent, 2222);
            }
        }
    };
    String timeStamp;
    String msgId;
    View.OnClickListener ObtainCodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            timeStamp = System.currentTimeMillis() + "";
            // final String phone = mEtPhone.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                //判断手机号是否为空
                return;
            } else {
                final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
                //满足要求后请求网络,查看用户是否存在
                HttpInterface2 mHttpInterface = new HttpUtils2(mContext);
                Bundle params = new Bundle();
                params.putString("phone", phone);
                params.putString("timeStamp", timeStamp);
                mHttpInterface.post(Url.PHONE_EXIST_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        if (result != null && result != "") {
                            Gson gson = new Gson();
                            IsExsitsResultData isExsitsResultData = gson.fromJson(result, IsExsitsResultData.class);
                            //用户存在请求网络，获取验证码
                            if (isExsitsResultData.getCode() != Number.OBTAINCODE) {
                                Gson gson1 = new Gson();
                                //请求的参数放入一个实体类中
                                SendMsgData mSendMsgData = new SendMsgData();
                                mSendMsgData.setMobile(phone);
                                //将请求的参数转换成字符串
                                String s = gson1.toJson(mSendMsgData);

                                HttpInterface2 mHttpInterface = new HttpUtils2(mContext);
                                Bundle params = new Bundle();
                                params.putString("channelId", Number.CHANNELID);
                                params.putString("phone", phone);
                                params.putString("msgType", "0");
                                params.putString("timeStamp", timeStamp);
                                mHttpInterface.post(Url.SMS_CODE_URL, params, new JsonInterface() {
                                    @Override
                                    public void callback(String result) {
                                        loadingDialogUtils.setDimiss();
                                        // Log.e("返回的数据",result);
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            if ("0".equals(jsonObject.getString("code"))) {

                                                time = 60;
                                                //mObtainCode.setTextColor(getResources().getColor(R.color.gr));
                                                mHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (time == 0) {
                                                            mText_obtain_code.setText("重新获取");
                                                            mText_obtain_code.setEnabled(true);
                                                            return;
                                                        } else {
                                                            time--;
                                                            mText_obtain_code.setText(time + "");
                                                            mText_obtain_code.setEnabled(false);
                                                        }
                                                        mHandler.postDelayed(this, 1000);
                                                    }
                                                });

                                                JSONObject data = jsonObject.getJSONObject("data");
                                                msgId = data.getString("msgId");
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
                                loadingDialogUtils.setDimiss();
                                Toast.makeText(mContext, isExsitsResultData.getMsg(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            loadingDialogUtils.setDimiss();
                        }
                    }

                    @Override
                    public void onError() {
                        loadingDialogUtils.setDimiss();
                    }
                });
            }
        }
    };
    View.OnClickListener comitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String str1 = mBank_name.getText().toString();
            final String str3 = mBank_name_small.getText().toString();
            final String str4 = mBank_idcard.getText().toString();
            String str5 = mEtCode.getText().toString();
            String str6 = mBank_username.getText().toString();
            if (TextUtils.isEmpty(str1)) {
                Toast.makeText(mContext, "请输入开户银行名称", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(str4)) {
                Toast.makeText(mContext, "请输入银行卡号", Toast.LENGTH_SHORT).show();
            } else if (!BankIdCheckUtils.luhnCheck(str4)) {
                Toast.makeText(mContext, "请输入正确的银行卡号", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(str5)) {
                Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
            } else {
                // 访问网络
                HttpInterface2 mHttpInterface = new HttpUtils2(mContext);
                Bundle params = new Bundle();
                params.putString("token", LotteryApp.token);
                params.putString("userId", LotteryApp.uid);
                params.putString("timeStamp", System.currentTimeMillis() + "");
                params.putString("channelId", Number.CHANNELID);
                params.putString("bankCard", str4);
                if (TextUtils.isEmpty(str3)) {
                    params.putString("bankBranch", str1);
                } else {
                    params.putString("bankBranch", str1 + str3);

                }
                params.putString("cardType", selectedIndex + "");
                params.putString("checkCode", str5);
                params.putString("msgId", msgId);
                mHttpInterface.post(Url.NICKNAME_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        LogUtils.e("result", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);

                            if (jsonObject.getInt("code") == 0) {
                                Intent intent = new Intent();
                                intent.putExtra("bankFlag", true);
                                intent.putExtra("bankCard", str4);
                                intent.putExtra("cardType",selectedIndex);
                                intent.putExtra("bankNameSmall",str3);
                                setResult(400, intent);
                                finish();
                            } else {
                                Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 3333) {
            //获取到选取的银行名字
            String bankName = data.getStringExtra("bankName");
            if (!TextUtils.isEmpty(bankName)) {
                mBank_name.setText(bankName);
                //循环比较名字所对应的银行id
                for (int i = 0; i < bankList.size(); i++) {
                    try {
                        if (bankName.equals(item.getString(i + ""))) {
                            selectedIndex = i + "";
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


}
