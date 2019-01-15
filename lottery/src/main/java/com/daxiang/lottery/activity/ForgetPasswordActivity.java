package com.daxiang.lottery.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordActivity extends BaseNoTitleActivity {
    // Content View Elements

    private EditText mEdit_phone;
    private TextView mText_obtain_code;
    private EditText mEdit_code;
    private EditText mEdit_password_one;
    private EditText mEdit_password_two;
    private TextView mImage_change;
    private ImageView mBack;
    //true忘记密码。false修改密码
    private boolean changOrForget;
    private int time;
    private Handler mHandler = new Handler();
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        changOrForget = getIntent().getBooleanExtra("changOrForget", false);
        phone = getIntent().getStringExtra("phone");
        initView();
        setListener();
    }

    private void initView() {
        mBack= (ImageView) findViewById(R.id.back);
        mEdit_phone = (EditText) findViewById(R.id.edit_phone);
        mText_obtain_code = (TextView) findViewById(R.id.text_obtain_code);
        mEdit_code = (EditText) findViewById(R.id.edit_code);
        mEdit_password_one = (EditText) findViewById(R.id.edit_password_one);
        mEdit_password_two = (EditText) findViewById(R.id.edit_password_two);
        mImage_change = (TextView) findViewById(R.id.image_change);
        if (!changOrForget && !TextUtils.isEmpty(phone)) {
            mEdit_phone.setText(phone(phone));
            mEdit_phone.setFocusable(false);
        }

    }

    String timeStamp;
    String msgId;
    private String salt;

    private void setListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 获取验证码，
         * 分为两步：一 先请求网络判断用户是否存在
         *          二 如果用户存在再次请求网络要求发送验证码
         * */
        mText_obtain_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStamp = System.currentTimeMillis() + "";
                if (changOrForget) {
                    phone = mEdit_phone.getText().toString().trim();
                }
                if (TextUtils.isEmpty(phone)) {
                    //判断手机号是否为空
                    Toast.makeText(ForgetPasswordActivity.this, "请输入手机号码",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //判断手机号是否合法
                    if (!StringUtil.isLegalPhone(phone)) {
                        Toast.makeText(ForgetPasswordActivity.this, "手机号不符合规范!请使用中国大陆手机号",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        //满足要求后请求网络,查看用户是否存在
                        HttpInterface2 mHttpInterface = new HttpUtils2(ForgetPasswordActivity.this);
                        Bundle params = new Bundle();
                        params.putString("keyword", phone);
                        //params.putString("timeStamp", timeStamp);
                        mHttpInterface.get(Url.SALT_URL, params, new JsonInterface() {
                            @Override
                            public void callback(String result) {
                                if (result != null && result != "") {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        int code = jsonObject.getInt("code");
                                        String msg = jsonObject.getString("msg");
                                        if (code == 0) {
                                            JSONObject data = jsonObject.getJSONObject("data");
                                            salt = data.getString("salt");
                                            getCode(phone);
                                        } else {
                                            Toast.makeText(ForgetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    // Gson gson = new Gson();
                                    //IsExsitsResultData isExsitsResultData = gson.fromJson(result, IsExsitsResultData.class);
                                    //用户存在请求网络，获取验证码
                                    //if (isExsitsResultData.getCode() != Number.OBTAINCODE) {

                                }
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(ForgetPasswordActivity.this, "网络请求出错!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        });

        /**
         *  立即修改
         * 1.判断手机号，验证码，密码是否合法
         * 2.判断两次输入的密码是否一样
         * */
        mImage_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (changOrForget) {
                    phone = mEdit_phone.getText().toString().trim();
                }
                String passwordOne = mEdit_password_one.getText().toString().trim();
                String passwordTwo = mEdit_password_two.getText().toString().trim();
                String vcode = mEdit_code.getText().toString().trim();
                if (!StringUtil.isLegalPhone(phone)) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入规范的手机号或用户名",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(passwordOne) || TextUtils.isEmpty(passwordTwo)) {
                    Toast.makeText(ForgetPasswordActivity.this, "密码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (!passwordOne.equals(passwordTwo)) {
                    Toast.makeText(ForgetPasswordActivity.this, "两次输入的密码不同",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (!StringUtil.isLegalPassword(passwordOne)) {
                    Toast.makeText(ForgetPasswordActivity.this, "请设置密码规范的密码格式",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpInterface2 mHttpInterface = new HttpUtils2(ForgetPasswordActivity.this);
                Bundle params = new Bundle();
                params.putString("password", StringUtil.getMD5(passwordTwo + salt));
                params.putString("msgId", msgId);
                params.putString("phone", phone);
                params.putString("checkCode", vcode);
                params.putString("timeStamp", timeStamp);

                mHttpInterface.post(Url.FORGET_PASSWORD_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            //JSONObject data = jsonObject.getJSONObject("data");
                            int code = jsonObject.getInt("code");
                            Toast.makeText(ForgetPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            if (code == 0) {
                                SharedPreferences sp = ForgetPasswordActivity.this.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("username", "");
                                edit.putString("password", "");
                                edit.putBoolean("islogin", false);
                                edit.commit();
                                LotteryApp.isLogin = false;
                                LotteryApp.buyPrivilege = 2;
                                LotteryApp.recommendPrivilege = 2;
                                /*Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);*/
                                Intent intent = new Intent();
                                setResult(444, intent);
                                finish();
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
        //返回按钮
    }

    //获取验证码
    private void getCode(String phone) {
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


        HttpInterface2 mHttpInterface = new HttpUtils2(ForgetPasswordActivity.this);
        Bundle params = new Bundle();
        params.putString("channelId", Number.CHANNELID);
        params.putString("phone", phone);
        params.putString("msgType", "0");
        params.putString("timeStamp", timeStamp);
        mHttpInterface.post(Url.SMS_CODE_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                // Log.e("返回的数据",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if ("0".equals(jsonObject.getString("code"))) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        msgId = data.getString("msgId");
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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

    private String phone(String phone) {
        if (phone.length() < 6) {
            return phone;
        }
        String start = phone.substring(0, 3);
        String end = phone.substring(phone.length() - 3, phone.length());
        return start + "***" + end;
    }
}
