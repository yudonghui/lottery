package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.StringInterface;
import com.daxiang.lottery.entity.PayBean;
import com.daxiang.lottery.utils.BankIdCheckUtils;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.PaymentUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.view.AddSpaceTextWatcher;

import org.json.JSONException;
import org.json.JSONObject;

public class BankCardActivity extends BaseActivity {
    private EditText mBankCardNum;
    private TextView mNext;
    private Context mContext;
    private String money;
    private PayBean payBean;
    private String from;//一种是充值，一种是支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_bank_card);
        mContext = this;
        money = getIntent().getStringExtra("money");
        payBean = (PayBean) getIntent().getSerializableExtra("payBean");
        from = getIntent().getStringExtra("from");
        initView();
        addData();
        addListener();
    }

    private void initView() {
        mTitleBar.setTitle("添加银行卡");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mBankCardNum = (EditText) findViewById(R.id.bank_card_num);
        AddSpaceTextWatcher addSpaceTextWatcher = new AddSpaceTextWatcher(mBankCardNum, 48);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.bankCardNumberType);
        mNext = (TextView) findViewById(R.id.next);
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止连续点击
                if (ClickUtils.isFastClick()) {
                    return;
                }
                final String cardNo1 = mBankCardNum.getText().toString();
                if (TextUtils.isEmpty(cardNo1)) {
                    Toast.makeText(mContext, "卡号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String cardNo = cardNo1.replace(" ", "");
                if (!BankIdCheckUtils.luhnCheck(cardNo)) {
                    Toast.makeText(mContext, "请输入正确的卡号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpInterface2 mHttp = new HttpUtils2(mContext);
                Bundle params = new Bundle();
                params.putString("cardNo", cardNo);
                params.putString("timeStamp", System.currentTimeMillis() + "");
                mHttp.post(Url.BANK_CARD_INFO, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                PaymentUtils paymentUtils = new PaymentUtils(mContext, PayResult);
                                if ("BuyActivity".equals(from))
                                    paymentUtils.buy(payBean, cardNo, null, null);
                                else
                                    paymentUtils.lianlianWeb(money, cardNo, null, null);
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
        });
    }

    StringInterface PayResult = new StringInterface() {
        @Override
        public void callBack(String type, String msg) {
            switch (type) {
                case "0000":// 0000 交易成功
                    HintDialogUtils.setHintDialog(mContext);
                    HintDialogUtils.setMessage("支付成功！");
                    HintDialogUtils.setVisibilityCancel();
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                        @Override
                        public void callBack(View view) {
                            Intent intent = new Intent();
                            setResult(2223, intent);
                            finish();
                        }
                    });
                    break;
                case "2008":// 2008 支付处理中
                    HintDialogUtils.setHintDialog(mContext);
                    HintDialogUtils.setMessage("支付处理中！");
                    HintDialogUtils.setVisibilityCancel();
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                        @Override
                        public void callBack(View view) {

                        }
                    });
                    break;
                default:
                    HintDialogUtils.setHintDialog(mContext);
                    HintDialogUtils.setMessage(msg);
                    HintDialogUtils.setVisibilityCancel();
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                        @Override
                        public void callBack(View view) {

                        }
                    });
                    break;
            }
        }
    };

    private void addData() {

    }
}
