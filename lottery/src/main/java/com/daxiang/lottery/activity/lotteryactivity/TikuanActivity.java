package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.TikuanRemark;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TikuanActivity extends BaseActivity {
    private String hdAmount;
    private UserInfo mUserInfo;
    // private com.caixiang.administrator.lottery.entity.BankInfoData mBankInfo;

    private TextView mBank_name;
    private EditText mBank_username;
    private EditText mDraw_money;
    private TextView mTikuanHint;
    private Button mTikuan_comit;
    private TextView mhdAmount;
    private HashMap<Integer, String> mMap = new HashMap<>();
    private String realName;
    private double minAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_tikuan);

        Intent intent = getIntent();
        mUserInfo = (UserInfo) intent.getSerializableExtra("userInfo");
        //mBankInfo = (BankInfoData) intent.getSerializableExtra("bankInfo");
        hdAmount = intent.getStringExtra("hdAmount");
        bindViews();
        addData();
        setListener();
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
                        JSONObject item = data.getJSONObject("item");
                        String cardTyp = mUserInfo.getData().getCardType();
                        String bankName = null;
                        for (int i = 1; i <= 151; i++) {
                            if ((i + "").equals(cardTyp)) {
                                bankName = item.getString("" + i);
                                break;
                            }
                        }
                        LotteryApp.bankName = bankName;
                        if (mUserInfo != null && mUserInfo.getData() != null) {
                            String bankCardId = mUserInfo.getData().getBankCard();
                            String number = bankCardId.substring(bankCardId.length() - 4, bankCardId.length());
                            mBank_name.setText(bankName + "(尾号" + number + ")");
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
        Bundle params1 = new Bundle();
        mHttpInterface.get(Url.TIKUAN_REMARK, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                TikuanRemark tikuanRemark = gson.fromJson(result, TikuanRemark.class);
                if (tikuanRemark.getCode() == 0) {
                    minAmount = tikuanRemark.getData().getMinAmount();
                    String remark = tikuanRemark.getData().getRemark();
                    mTikuanHint.setText(TextUtils.isEmpty(remark) ? "" : remark);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("我的提款");
        mTitleBar.mTextRegister.setText("提款记录");
        mTitleBar.mTextRegister.setVisibility(View.VISIBLE);
        mTitleBar.setTitleVisibility(true);
        mBank_name = (TextView) findViewById(R.id.bank_name);
        mTikuanHint = (TextView) findViewById(R.id.tikuanhint);
        mBank_username = (EditText) findViewById(R.id.bank_username);
        if (mUserInfo != null && mUserInfo.getData() != null)
            realName = mUserInfo.getData().getRealName();
        if (!TextUtils.isEmpty(realName)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(realName.substring(0, 1));
            for (int i = 0; i < realName.length() - 1; i++) {
                stringBuilder.append("*");
            }
            mBank_username.setText(stringBuilder);
            mBank_username.setFocusable(false);
        }
        mDraw_money = (EditText) findViewById(R.id.draw_money);
        mDraw_money.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mTikuan_comit = (Button) findViewById(R.id.tikuan_comit);
        mhdAmount = (TextView) findViewById(R.id.hdamount);
        String redStr = "￥" + hdAmount;
        String string = "可提现的金额" + redStr + "元，每次至少提现10元";
        SpannableStringBuilder ssb = new SpannableStringBuilder(string);
        ssb.setSpan(new ForegroundColorSpan(Color.RED), 6, redStr.length() + 6,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(Color.RED), string.length() - 3, string.length() - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mhdAmount.setText(ssb);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(40, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(40, intent);
                finish();
            }
        });
        //提款记录
        mTitleBar.mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TikuanActivity.this, TikuanRecordActivity.class);
                startActivity(intent);
            }
        });
        mDraw_money.addTextChangedListener(textWatcher);
        mTikuan_comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name = mBank_username.getText().toString();
                //防止连续点击
                if (ClickUtils.isFastClick(2000)) {
                    return;
                }
                final String money = mDraw_money.getText().toString();
                final String bankName = mBank_name.getText().toString();
                if (TextUtils.isEmpty(bankName)) {
                    Toast.makeText(TikuanActivity.this, "未获取到您绑定的银行卡信息", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(realName)) {
                    Toast.makeText(TikuanActivity.this, "开户姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(money)) {
                    Toast.makeText(TikuanActivity.this, "提款金额不能为空", Toast.LENGTH_SHORT).show();
                } else if (minAmount == 0) {
                    Toast.makeText(TikuanActivity.this, "未获取到最小提款金额", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(money) < minAmount) {
                    Toast.makeText(TikuanActivity.this, "每次提款金额不能少于" + minAmount + "元", Toast.LENGTH_SHORT).show();
                } else {
                    mDraw_money.setText("");
                    final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(TikuanActivity.this);
                    HttpInterface2 mHttpInterface = new HttpUtils2(TikuanActivity.this);
                    Bundle params = new Bundle();
                    params.putString("token", LotteryApp.token);
                    params.putString("amount", money);
                    //提现方式
                    params.putString("payWay", "2");
                    params.putString("timeStamp", System.currentTimeMillis() + "");

                    mHttpInterface.post(Url.DRAW_MONEY_URL, params, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            loadingDialogUtils.setDimiss();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getInt("code") == 0) {
                                    /*HintDialogUtils.setHintDialog(TikuanActivity.this);
                                    HintDialogUtils.setMessage("恭喜您！提款成功！");
                                    HintDialogUtils.setCancel("继续提款", new DialogHintInterface() {
                                        @Override
                                        public void callBack(View view) {
                                        }
                                    });
                                    HintDialogUtils.setConfirm("提款记录", new DialogHintInterface() {
                                        @Override
                                        public void callBack(View view) {
                                            Intent intent = new Intent(TikuanActivity.this, TikuanRecordActivity.class);
                                            startActivity(intent);
                                        }
                                    });*/
                                    Intent intent = new Intent(TikuanActivity.this, TikuanProcessActivity.class);
                                    intent.putExtra("money", money);
                                    intent.putExtra("bankName", bankName);
                                    intent.putExtra("name", realName);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(TikuanActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
