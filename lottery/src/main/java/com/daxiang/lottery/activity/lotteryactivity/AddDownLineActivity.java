package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.RandomUtils;
import com.daxiang.lottery.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class AddDownLineActivity extends BaseNoTitleActivity {
    private EditText mUserName;
    private EditText mPasswordFirst;
    private EditText mPasswordSecond;
    private TextView mConfirm;
    private Context mContext;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_down_line);
        mContext = this;
        initView();
        addListener();
        addData();
    }

    private void initView() {
        mBack= (ImageView) findViewById(R.id.back);
        mUserName = (EditText) findViewById(R.id.add_username);
        mPasswordFirst = (EditText) findViewById(R.id.edit_password1);
        mPasswordSecond = (EditText) findViewById(R.id.edit_password2);
        mConfirm = (TextView) findViewById(R.id.confirm_add);
    }

    private void addListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserName.getText().toString();
                String passwordFirst = mPasswordFirst.getText().toString();
                String passwordSecond = mPasswordSecond.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(mContext, "请输入用户名！", Toast.LENGTH_SHORT).show();
                } else if (StringUtil.isLegalPhone(userName)) {
                    Toast.makeText(mContext, "用户名不能为手机号！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordFirst)) {
                    Toast.makeText(mContext, "请输入密码！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passwordSecond)) {
                    Toast.makeText(mContext, "请再次输入密码！", Toast.LENGTH_SHORT).show();
                } else if (!passwordFirst.equals(passwordSecond)) {
                    Toast.makeText(mContext, "两次输入的密码不同！", Toast.LENGTH_SHORT).show();
                } else if (!isLegalPassword(passwordFirst)) {
                    Toast.makeText(mContext, "请设置密码长度为6-24位",
                            Toast.LENGTH_SHORT).show();
                }else {
                    HttpInterface2 mHttp = new HttpUtils2(mContext);
                    Bundle params = new Bundle();
                    params.putString("timeStamp", System.currentTimeMillis() + "");
                    params.putString("userName", userName);
                    String salt = RandomUtils.createSalt();
                    params.putString("password", StringUtil.getMD5(passwordFirst+salt));
                    params.putString("channelId", Number.CHANNELID);
                    params.putString("parentUserId", LotteryApp.uid);
                    params.putString("salt", salt);
                    mHttp.post(Url.COMMON_REGISTER_URL, params, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                if (code == 0) {
                                    Intent intent = new Intent();
                                    setResult(33, intent);
                                    finish();
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

            }
        });
    }

    private void addData() {

    }

    //判断密码是否合法
    private boolean isLegalPassword(String pwd) {
        String regex = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_`~!@#$%^*&()_-+=?";
        for (int i = 0; i < pwd.length(); i++) {
            if (!regex.contains(String.valueOf(pwd.charAt(i))))
                return false;
        }
        return pwd != null && pwd.length() >= 6
                && pwd.length() <= 24;
    }
}
