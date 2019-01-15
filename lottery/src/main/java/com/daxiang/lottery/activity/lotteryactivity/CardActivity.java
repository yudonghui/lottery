package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.IDCardValidate;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class CardActivity extends BaseActivity {
    // Content View Elements

    private EditText mEt_name;
    private EditText mEt_idcard1;
    private LinearLayout mLl_card2;
    private EditText mEt_idcard2;
    private LinearLayout mLl_passward;
    private Button mComit;
    private TextView mText_obtain_code;
    //private EditText mEtPhone;
    private EditText mEtCode;
    private boolean mCardFlag;
    private String mName;
    private String mNumber;
    int time;
    private Handler mHandler = new Handler();
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_card);

        Intent intent = getIntent();
        mCardFlag = intent.getBooleanExtra("cardFlag", false);
        mName = intent.getStringExtra("name");
        mNumber = intent.getStringExtra("number");
        phone = LotteryApp.phone;
        bindViews();
        addListener();
    }

    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("身份证绑定");
        mTitleBar.setTitleVisibility(true);
        mEt_name = (EditText) findViewById(R.id.et_name);
        mEt_idcard1 = (EditText) findViewById(R.id.et_idcard1);
        mLl_card2 = (LinearLayout) findViewById(R.id.ll_card2);
        mEt_idcard2 = (EditText) findViewById(R.id.et_idcard2);
        mLl_passward = (LinearLayout) findViewById(R.id.ll_passward);
        mComit = (Button) findViewById(R.id.card_comit);
        //mEtPhone = (EditText) findViewById(R.id.edit_phone);
        mEtCode = (EditText) findViewById(R.id.edit_code);
        mText_obtain_code = (TextView) findViewById(R.id.text_obtain_code);
        if (mCardFlag) {
            mLl_card2.setVisibility(View.GONE);
            mLl_passward.setVisibility(View.GONE);
            mComit.setVisibility(View.GONE);
            //做一下判空，防止后台返回没有数据而崩溃
            if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mNumber)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(mName.substring(0, 1));
                for (int i = 0; i < mName.length() - 1; i++) {
                    stringBuilder.append("*");
                }
                mEt_name.setText(stringBuilder);
                mEt_idcard1.setText(mNumber.substring(0, 3) + "***" + mNumber.substring(mNumber.length() - 3, mNumber.length()));
            }
            mEt_name.setFocusable(false);
            mEt_idcard1.setFocusable(false);
        }
    }

    String timeStamp;
    String msgId;

    private void addListener() {
        /**
         * 获取验证码，
         * 分为两步：一 先请求网络判断用户是否存在
         *          二 如果用户存在再次请求网络要求发送验证码
         * */
        mText_obtain_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStamp = System.currentTimeMillis() + "";
                // final String phone = mEtPhone.getText().toString().trim();
                //判断手机号是否合法
                if (!StringUtil.isLegalPhoneNew(phone, 0)) {
                    Toast.makeText(CardActivity.this, "手机号不符合规范!请使用中国大陆手机号",
                            Toast.LENGTH_SHORT).show();
                } else {
                    final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(CardActivity.this);                        //满足要求后请求网络,查看用户是否存在
                    HttpInterface2 mHttpInterface = new HttpUtils2(CardActivity.this);
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

                                    HttpInterface2 mHttpInterface = new HttpUtils2(CardActivity.this);
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
                                } else {
                                    Toast.makeText(CardActivity.this, isExsitsResultData.getMsg(), Toast.LENGTH_SHORT).show();
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
        });

        mComit.setOnClickListener(comitListener);
    }

    View.OnClickListener comitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = mEt_name.getText().toString();
            String card1 = mEt_idcard1.getText().toString().replace("x","X");
            String card2 = mEt_idcard2.getText().toString().replace("x","X");

            String checkCode = mEtCode.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(CardActivity.this, "真实姓名不能为空", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(card1)) {
                Toast.makeText(CardActivity.this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(card2)) {
                Toast.makeText(CardActivity.this, "请再次输入身份证号", Toast.LENGTH_SHORT).show();
            } else if (!card1.equals(card2)) {
                Toast.makeText(CardActivity.this, "两次输入的身份证号不一致", Toast.LENGTH_SHORT).show();
            } else if (!card1.equals(IDCardValidate.validate_effective(card1))) {
                Toast.makeText(CardActivity.this, IDCardValidate.validate_effective(card1), Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(checkCode)) {
                Toast.makeText(CardActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                netWork(name, card1, checkCode);
            }
        }
    };

    private void netWork(final String name, final String card, String checkCode) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(CardActivity.this);
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("channelId", Number.CHANNELID);
        params.putString("idNo", card);
        params.putString("realName", name);
        params.putString("checkCode", checkCode);
        params.putString("msgId", msgId);
        mHttpInterface.post(Url.NICKNAME_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        Toast.makeText(CardActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        mCardFlag = true;
                        Intent intent = new Intent();
                        intent.putExtra("cardFlag", mCardFlag);// 放入返回值
                        LotteryApp.cardFlag = mCardFlag;
                        LotteryApp.realName = name;
                        intent.putExtra("name", name);
                        intent.putExtra("number", card);
                        setResult(200, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据
                        finish();// 结束当前的activity,等于点击返回按钮
                    } else {
                        Toast.makeText(CardActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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

  /*  @Override
    public void onBackPressed() {
       *//* Intent intent = new Intent();
        intent.putExtra("cardFlag", mCardFlag);// 放入返回值
        setResult(200, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据
        finish();// 结束当前的activity,等于点击返回按钮*//*
       super.onBackPressed();
    }*/
}
