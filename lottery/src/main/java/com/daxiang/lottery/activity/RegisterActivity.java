package com.daxiang.lottery.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
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
import com.daxiang.lottery.entity.IsExsitsResultData;
import com.daxiang.lottery.entity.LoginResultData;
import com.daxiang.lottery.entity.PrivilegeData;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.RandomUtils;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEdit_password;
    private ImageView mPasswordState;
    private EditText mEdit_phone;
    private TextView mText_obtain_code;
    private EditText mEdit_code;
    private TextView mImage_register;
    private boolean flag = true;
    private ImageView mBack;
    // End Of Content View Elements
    int time;
    private Handler mHandler = new Handler();
    HttpInterface2 mHttpInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        setListener();
    }

    private void initView() {
        mEdit_password = (EditText) findViewById(R.id.edit_password);
        mPasswordState = (ImageView) findViewById(R.id.image_hint_password);
        mEdit_phone = (EditText) findViewById(R.id.edit_phone);
        mText_obtain_code = (TextView) findViewById(R.id.text_obtain_code);
        mEdit_code = (EditText) findViewById(R.id.edit_code);
        mImage_register = (TextView) findViewById(R.id.image_register);
        mBack = (ImageView) findViewById(R.id.back);
        mHttpInterface = new HttpUtils2(RegisterActivity.this);
    }

    String timeStamp;
    String msgId;

    private void setListener() {
        mPasswordState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    //显示密码
                    mEdit_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPasswordState.setImageResource(R.mipmap.login_open);
                    flag = false;
                } else {
                    //隐藏密码
                    mEdit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordState.setImageResource(R.mipmap.login_close);
                    flag = true;
                }
                //让光标始终在最后面
                Editable etable = mEdit_password.getText();
                Selection.setSelection(etable, etable.length());
            }
        });
        mText_obtain_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStamp = System.currentTimeMillis() + "";
                mEdit_phone.getText();
                final String phone = mEdit_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    //判断手机号是否为空
                    Toast.makeText(RegisterActivity.this, "请输入手机号码",
                            Toast.LENGTH_SHORT).show();
                } else if (!isLegalPhone(phone)) {
                    //判断手机号是否合法
                    Toast.makeText(RegisterActivity.this, "手机号不符合规范!请使用中国大陆手机号",
                            Toast.LENGTH_SHORT).show();
                } else {
                    final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(RegisterActivity.this);
                    //满足要求后请求网络,查看用户是否存在
                    Bundle params = new Bundle();
                    params.putString("phone", phone);
                    params.putString("timeStamp", timeStamp);
                    mHttpInterface.post(Url.PHONE_EXIST_URL, params, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            if (result != null && result != "") {
                                Gson gson = new Gson();
                                IsExsitsResultData isExsitsResultData = gson.fromJson(result, IsExsitsResultData.class);
                                //用户不存在请求网络，获取验证码
                                if (isExsitsResultData.getCode() == Number.OBTAINCODE) {
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
                                    //将请求的参数转换成字符串
                                    HttpInterface2 mHttpInterface = new HttpUtils2(RegisterActivity.this);
                                    Bundle params = new Bundle();
                                    params.putString("channelId", Number.CHANNELID);
                                    params.putString("phone", phone);
                                    params.putString("msgType", "0");
                                    params.putString("timeStamp", timeStamp);
                                    mHttpInterface.post(Url.SMS_CODE_URL, params, new JsonInterface() {
                                        @Override
                                        public void callback(String result) {
                                            loadingDialogUtils.setDimiss();
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
                                            loadingDialogUtils.setDimiss();
                                        }
                                    });
                                } else {
                                    loadingDialogUtils.setDimiss();
                                    Toast.makeText(RegisterActivity.this, isExsitsResultData.getMsg(), Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                loadingDialogUtils.setDimiss();
                            }
                        }

                        @Override
                        public void onError() {
                            loadingDialogUtils.setDimiss();
                            Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        //点击注册按钮
        mImage_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // final String username = mEdit_username.getText().toString().trim();
                final String password = mEdit_password.getText().toString().trim();
                final String vcode = mEdit_code.getText().toString().trim();
                /*if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else*/
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(vcode)) {
                    Toast.makeText(RegisterActivity.this, "验证码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }/* else if (!isLegalUsername(username)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名6-24位数字，字母，下划线组成",
                            Toast.LENGTH_SHORT).show();
                    return;
                }*/ else if (!isLegalPassword(password)) {
                    Toast.makeText(RegisterActivity.this, "请设置密码长度为6-24位",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpInterface2 mHttpInterface = new HttpUtils2(RegisterActivity.this);
                register(password, vcode);

            }
        });
        //点击返回按钮
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //给用户名添加一个得到和失去焦点的监听
        //mEdit_username.setOnFocusChangeListener(FocusListener);
    }

    private void register(final String password, String vcode) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(RegisterActivity.this);
        final String phone = mEdit_phone.getText().toString().trim();
        Bundle params = new Bundle();
        // params.putString("userName", username);
        //生成随机的六位
        final String salt = RandomUtils.createSalt();
        params.putString("password", StringUtil.getMD5(password + salt));
        params.putString("salt", salt);
        params.putString("channelId", Number.CHANNELID);
        params.putString("msgId", msgId);
        params.putString("phone", phone);
        params.putString("checkCode", vcode);
        params.putString("timeStamp", timeStamp);
        mHttpInterface.post(Url.REGISTER_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                LotteryApp.salt = salt;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //JSONObject data = jsonObject.getJSONObject("data");
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        Bundle params = new Bundle();
                        params.putString("userName", phone);
                        params.putString("password", StringUtil.getMD5(password + salt));
                        params.putString("timeStamp", System.currentTimeMillis() + "");
                        mHttpInterface.post(Url.LOGIN_URL, params, new JsonInterface() {
                            @Override
                            public void callback(String result) {
                                loadingDialogUtils.setDimiss();
                                if (TextUtils.isEmpty(result)) return;
                                Gson gson = new Gson();
                                LoginResultData loginResultData = gson.fromJson(result, LoginResultData.class);
                                if (loginResultData.getCode() == 0) {
                                    //登录返回值获取成功
                                    // LotteryApp.Privilege = Integer.parseInt(loginResultData.getData().getPrivilege());
                                    LotteryApp.token = loginResultData.getData().getToken();
                                    LotteryApp.uid = loginResultData.getData().getUserId();
                                    LotteryApp.userType = loginResultData.getData().getUserType();
                                    LotteryApp.isLogin = true;
                                    LotteryApp.isThird = false;
                                    SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                                    //实例化SharedPreferences.Editor对象
                                    SharedPreferences.Editor editor = sp.edit();
                                    //用putString的方法保存数据
                                    editor.putString("username", phone);
                                    editor.putString("password", password + salt);
                                    editor.putString("salt", salt);
                                    editor.putBoolean("islogin", true);
                                    //是否为第三方登录
                                    editor.putBoolean("isThird", false);
                                    //提交当前数据
                                    editor.commit();
                                    Toast.makeText(RegisterActivity.this, loginResultData.getMsg(), Toast.LENGTH_SHORT).show();
                                    //登录成功之后获取用户信息
                                    getUserInfo();
                                    Intent intent = new Intent();
                                    setResult(333, intent);
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, loginResultData.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                                getPrivilege();
                            }

                            @Override
                            public void onError() {
                                loadingDialogUtils.setDimiss();
                                Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        loadingDialogUtils.setDimiss();
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPrivilege() {
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

            }
        });
    }

    private UserInfo userInfo;

    private void getUserInfo() {

        //用户信息
        mHttpInterface = new HttpUtils2(this);
        Bundle params1 = new Bundle();
        params1.putString("token", LotteryApp.token);
        params1.putString("userId", LotteryApp.uid);
        params1.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.USER_INFO_URL, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                Gson gson = new Gson();
                userInfo = gson.fromJson(result, UserInfo.class);
                if (userInfo.getCode() == 0) {
                    long userId = userInfo.getData().getUserId();
                    LotteryApp.userType = userInfo.getData().getUserType();
                    Number.CHANNELIDBUY = userInfo.getData().getChannelId();
                    if (userId == 2000000) {
                        userInfo.getData().setSuggestDisplayName(sp.getString("username", ""));
                        userInfo.getData().setHeadImg(sp.getString("imagUrl", ""));
                    }
                    //身份证，银行卡，手机号 是否绑定的状态
                    setStatus(userInfo);
                    //在点击我的彩票时候没有登录的话跳转过来的时候。
                    Intent intent = new Intent();
                    setResult(40, intent);
                    finish();
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
        if (mMobile != null && mMobile.length() != 0) {
            LotteryApp.phoneFlag = true;
            LotteryApp.phone = userInfo.getData().getPhone();
        } else
            LotteryApp.phoneFlag = false;

    }

    //判断用户名是否合法
    private boolean isLegalUsername(String username) {
        String regex = "(?:\\d.*_)|(?:_.*\\d)|(?:[A-Za-z].*_)|(?:_.*[A-Za-z])|(?:[A-Za-z].*\\d)|(?:\\d.*[A-Za-z])";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        boolean log = m.matches() && username.length() >= 6 && username.length() <= 24;
        return log;
    }

    private boolean isLegalPhone(String phone) {
        String reg = "^1[3-9][0-9]{9}$";
        return Pattern.matches(reg, phone);
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
