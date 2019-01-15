package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.HttpUtils2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BankActivity extends BaseActivity {
    // Content View Elements

    private TextView mBank_name;
    private EditText mBank_username;
    private EditText mBank_name_small;
    private EditText mBank_idcard;
    private Button mBank_comit;
    private LinearLayout mRlCode;
    private View mLine;
    private View inflate;
    AlertDialog mDialog;
    UserInfo userInfo;
    ArrayList<String> bankList = new ArrayList<>();
    private JSONObject item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_bank);
        Intent intent = getIntent();
        userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
        bindViews();
        addData();
        setView();

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
                        }
                        mBank_name.setText(item.getString(userInfo.getData().getCardType()));
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

    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("银行卡绑定");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.mTextRegister.setText("编辑");
        mTitleBar.mTextRegister.setVisibility(View.VISIBLE);
        mTitleBar.mTextRegister.setOnClickListener(EditListener);
        mBank_name = (TextView) findViewById(R.id.bank_name);
        mBank_username = (EditText) findViewById(R.id.bank_username);
        mBank_name_small = (EditText) findViewById(R.id.bank_name_small);
        mBank_idcard = (EditText) findViewById(R.id.bank_idcard);
        mBank_comit = (Button) findViewById(R.id.bank_comit);
        mRlCode = (LinearLayout) findViewById(R.id.ll_code);
        mLine = findViewById(R.id.line);
        inflate = View.inflate(BankActivity.this, R.layout.dialog_bank, null);

        mDialog = new AlertDialog.Builder(BankActivity.this).create();
    }

    private void setView() {
        if (userInfo == null || userInfo.getData() == null) return;
        UserInfo.DataBean data = userInfo.getData();
        mBank_name_small.setText(data.getBankBranch());
        mBank_idcard.setText(bank(data.getBankCard()));
        mBank_username.setText(data.getRealName());
        //开户银行卡号
        mBank_idcard.setFocusable(false);
        //开户人姓名
        mBank_username.setFocusable(false);
        //开户支行
        mBank_name_small.setFocusable(false);
        //如果银行卡绑定了之后，不再显示提交，获取验证码，和验证码
        mBank_comit.setVisibility(View.GONE);
        mRlCode.setVisibility(View.GONE);
        mLine.setVisibility(View.GONE);

    }

    View.OnClickListener EditListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BankActivity.this, BankEidteActivity.class);
            intent.putExtra("userInfo", userInfo);
            startActivityForResult(intent, 110);
        }
    };

    private String bank(String bank) {
        if (bank.length() < 8) {
            return bank;
        }
        String start = bank.substring(0, 4);
        String end = bank.substring(bank.length() - 4, bank.length());
        return start + "***" + end;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == 400) {
            setResult(400, data);
            finish();
        }
    }
}
