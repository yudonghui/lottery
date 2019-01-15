package com.daxiang.lottery.activity.lotteryactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
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
import com.daxiang.lottery.entity.PrivilegeData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.RandomUtils;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ThirdBindPhoneActivity extends BaseActivity {
    /*
    * isExist  true 说明用户已经存在了，只需要填写验证码绑定即可
    *          false 说明用户不存在，填写验证码的同时还需要设定密码
    *
    * */
    private String msgId;
    private boolean isExist;
    private TextView mPhone;
    private EditText mCode;
    private TextView mObtainCode;
    private EditText mPassword;
    private TextView mCommit;
    private TextView mHint;
    private LinearLayout linearLayout;
    private String phone;
    private Context mContext;
    private HttpInterface2 mHttpInterface;
    private SharedPreferences sp;
    private String partnerCode;
    private String nickName;
    private String imagUrl;
    private String openId;
    private int time = 60;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_third_bind_phone);
        sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        partnerCode = sp.getString("partnerCode", "");
        nickName = sp.getString("username", "");
        imagUrl = sp.getString("imagUrl", "");
        openId = sp.getString("openId", "");
        mContext = ThirdBindPhoneActivity.this;
        Intent intent = getIntent();
        msgId = intent.getStringExtra("msgId");
        isExist = intent.getBooleanExtra("isExist", false);
        phone = intent.getStringExtra("phone");
        initView();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (time == 0) {
                    mObtainCode.setText("重新获取");
                    mObtainCode.setEnabled(true);
                    return;
                } else {
                    time--;
                    mObtainCode.setText(time + "");
                    mObtainCode.setEnabled(false);
                }
                mHandler.postDelayed(this, 1000);
            }
        });
        addListener();
    }

    private void initView() {
        mHttpInterface = new HttpUtils2(mContext);
        mPhone = (TextView) findViewById(R.id.text_phone);
        mCode = (EditText) findViewById(R.id.edit_code);
        mObtainCode = (TextView) findViewById(R.id.text_obtain_code);
        mPassword = (EditText) findViewById(R.id.edit_password);
        mCommit = (TextView) findViewById(R.id.text_commint);
        mHint = (TextView) findViewById(R.id.third_shuoming);
        linearLayout = (LinearLayout) findViewById(R.id.ll_passward);
        mPhone.setText(phone);
        if (!isExist) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            mHint.setVisibility(View.VISIBLE);
        }
    }

    private void addListener() {
        mObtainCode.setOnClickListener(ObtainCodeListener);
        if (isExist) {
            //老用户
            mCommit.setOnClickListener(CommitListener);
        } else {
            //用户不存在，新用户
            mCommit.setOnClickListener(CommitNewListener);
        }

    }

    View.OnClickListener ObtainCodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            time = 60;
            //mObtainCode.setTextColor(getResources().getColor(R.color.gr));

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (time == 0) {
                        mObtainCode.setText("重新获取");
                        mObtainCode.setEnabled(true);
                        return;
                    } else {
                        time--;
                        mObtainCode.setText(time + "");
                        mObtainCode.setEnabled(false);
                    }
                    mHandler.postDelayed(this, 1000);
                }
            });
            Bundle params = new Bundle();
            params.putString("channelId", Number.CHANNELID);
            params.putString("phone", phone);
            params.putString("msgType", "0");
            params.putString("timeStamp", System.currentTimeMillis() + "");
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
    };
    View.OnClickListener CommitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String checkCode = mCode.getText().toString();
            if (TextUtils.isEmpty(checkCode)) {
                Toast.makeText(mContext, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("partnerCode", partnerCode);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            params.putString("openId", openId);
            params.putString("phone", phone);
            params.putString("nickName", nickName);
            params.putString("checkCode", checkCode);
            params.putString("msgId", msgId);
            //  params.putString("password",password);
            params.putString("channelId", Number.CHANNELID);
            params.putString("headImg", imagUrl);
            mHttpInterface.post(Url.THIRD_BIND_PHONE_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code == 0) {
                            LotteryApp.token = jsonObject.getJSONObject("data").getString("token");
                            LotteryApp.uid = jsonObject.getJSONObject("data").getString("userId");
                            LotteryApp.phoneFlag = true;
                            LotteryApp.phone = phone;
                            //获取用户权限
                            getPrivilege(loadingDialogUtils);
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            setResult(400, intent);
                            finish();
                        } else {
                            loadingDialogUtils.setDimiss();
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
    };
    View.OnClickListener CommitNewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String password = mPassword.getText().toString();
            String checkCode = mCode.getText().toString();
            if (TextUtils.isEmpty(checkCode)) {
                Toast.makeText(mContext, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(mContext, "密码不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isLegalPassword(password)) {
                Toast.makeText(mContext, "请设置密码长度为6-24位",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
            final Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("partnerCode", partnerCode);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            params.putString("openId", openId);
            params.putString("phone", phone);
            params.putString("nickName", nickName);
            params.putString("checkCode", checkCode);
            params.putString("msgId", msgId);
            final String salt = RandomUtils.createSalt();
            params.putString("password", StringUtil.getMD5(password + salt));
            params.putString("salt", salt);
            params.putString("channelId", Number.CHANNELID);
            params.putString("headImg", imagUrl);
            mHttpInterface.post(Url.THIRD_BIND_PHONE_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        if (code == 0) {
                            LotteryApp.token = jsonObject.getJSONObject("data").getString("token");
                            LotteryApp.uid = jsonObject.getJSONObject("data").getString("userId");
                            LotteryApp.salt = salt;
                            LotteryApp.phoneFlag = true;
                            LotteryApp.phone = phone;
                            //获取用户权限
                            getPrivilege(loadingDialogUtils);
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            setResult(400, intent);
                            finish();
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
        }
    };

    private void getPrivilege(final LoadingDialogUtils loadingDialogUtils) {
        final Bundle params = new Bundle();
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        mHttpInterface.post(Url.PRIVILEGE_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                LogUtils.e("获取用户权限", result);
                Gson gson = new Gson();
                PrivilegeData privilegeData = gson.fromJson(result, PrivilegeData.class);
                if (privilegeData.getCode() == 0) {
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
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    //判断密码是否合法
    private boolean isLegalPassword(String pwd) {
        //String regex="^[\\d\\w!@#\\$%\\^&\\*\\(\\)\\[\\]\\{\\}\\/\\:\\;\\\"\\'<>,\\.\\?\\-\\|]{6,24}$^[\\d\\w_]{6,24}$";
       /* Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pwd);
        boolean matches = m.matches();*/
        String regex = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_`~!@#$%^*&()_-+=?";
        for (int i = 0; i < pwd.length(); i++) {
            if (!regex.contains(String.valueOf(pwd.charAt(i))))
                return false;
        }
        return pwd != null && pwd.length() >= 6
                && pwd.length() <= 24;
    }
}
