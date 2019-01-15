package com.daxiang.lottery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.BillWalletData;
import com.daxiang.lottery.entity.LoginResultData;
import com.daxiang.lottery.entity.LoginResultErrorData;
import com.daxiang.lottery.entity.PrivilegeData;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public class LoginActivity extends BaseNoTitleActivity {
    // Content View Elements

    private EditText mEdit_username;
    private EditText mEdit_password;
    private ImageView mImage_password;
    private TextView mText_login;
    private TextView mRegister;
    private ImageView mClose;
    private TextView mText_forget_password;
    private boolean flag;
    private HttpInterface2 mHttpInterface;
    private ImageView mWeiXin;
    private ImageView mQQ;
    private String partnerCode;
    private static final int LOGIN_SUCCESS = 0;
    private static final int LOGIN_CANCEL = 2;

    private static final int LOGIN_FAIL = 1;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
        setListener();
    }

    private void initView() {
        mEdit_username = (EditText) findViewById(R.id.edit_username);
        mEdit_password = (EditText) findViewById(R.id.edit_password);
        mImage_password = (ImageView) findViewById(R.id.image_password);
        mText_login = (TextView) findViewById(R.id.text_login);
        mRegister = (TextView) findViewById(R.id.register);
        mClose = (ImageView) findViewById(R.id.close);
        mText_forget_password = (TextView) findViewById(R.id.text_forget_password);
        //三方登录
        mWeiXin = (ImageView) findViewById(R.id.weixin_login);
        mQQ = (ImageView) findViewById(R.id.qq_login);
        mHttpInterface = new HttpUtils2(LoginActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        String username = sp.getString("username","");
        mEdit_username.setText(TextUtils.isEmpty(username)?"":username);
    }

    private void setListener() {
        //点击注册按钮跳转到注册页面
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // startActivity(intent);
                startActivityForResult(intent, 222);
            }
        });
        mWeiXin.setOnClickListener(ThirdLoginListener);
        mQQ.setOnClickListener(ThirdLoginListener);
        //点击登录按钮
        mText_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = mEdit_username.getText().toString().trim();
                final String password = mEdit_password.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "请输入手机号或用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入登录密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //获取salt
                Bundle params1 = new Bundle();
                params1.putString("keyword", username);
                final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
                mHttpInterface.get(Url.SALT_URL, params1, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        loadingDialogUtils.setDimiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                String salt = data.getString("salt");
                                loginWeb(salt, username, password);
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {
                        loadingDialogUtils.setDimiss();
                        Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //点击后退按钮
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(30, intent);
                finish();
            }
        });

        //点击密码显隐控制
        mImage_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    //显示密码
                    mEdit_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mImage_password.setImageResource(R.mipmap.login_open);
                    flag = false;
                } else {
                    //隐藏密码
                    mEdit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mImage_password.setImageResource(R.mipmap.login_close);
                    flag = true;
                }
                //让光标始终在最后面
                Editable etable = mEdit_password.getText();
                Selection.setSelection(etable, etable.length());
            }
        });
        //点击忘记密码
        mText_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent.putExtra("changOrForget", true);
                startActivity(intent);
            }
        });
    }

    private void loginWeb(final String salt, final String username, final String password) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        params.putString("userName", username);
        params.putString("password", StringUtil.getMD5(password + salt));
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.LOGIN_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                if (result != null && result != "" && result.length() > 100) {
                    Gson gson = new Gson();
                    LoginResultData loginResultData = gson.fromJson(result, LoginResultData.class);
                    if (loginResultData.getCode() == 0) {
                        //登录返回值获取成功
                        // LotteryApp.Privilege = Integer.parseInt(loginResultData.getData().getPrivilege());
                        LotteryApp.token = loginResultData.getData().getToken();
                        LotteryApp.uid = loginResultData.getData().getUserId();
                        LotteryApp.userType = loginResultData.getData().getUserType();
                        Number.CHANNELIDBUY = loginResultData.getData().getChannelId();
                        LotteryApp.isLogin = true;
                        LotteryApp.isThird = false;
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                        //实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = sp.edit();
                        //用putString的方法保存数据
                        editor.putString("username", username);
                        editor.putString("password", password + salt);
                        editor.putString("salt", salt);
                        editor.putBoolean("islogin", true);
                        //是否为第三方登录
                        editor.putBoolean("isThird", false);
                        //提交当前数据
                        editor.commit();
                        //登录成功之后获取用户信息
                        getUserInfo();
                        //钱包信息获取
                        getBill();
                        Intent intent = new Intent();
                        intent.setAction(ConstantNum.HOME_ACTIVITY);
                        intent.putExtra("type", ConstantNum.LOGIN_SUCESS);
                        sendBroadcast(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, loginResultData.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    getPrivilege();
                } else if (result != null && result != "") {
                    //输入的密码或者账号错误
                    Gson gson = new Gson();
                    LoginResultErrorData loginResultErrorData = gson.fromJson(result, LoginResultErrorData.class);
                    if (loginResultErrorData.getCode() == 1121) {
                        Toast.makeText(LoginActivity.this, loginResultErrorData.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, loginResultErrorData.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private void getBill() {
        //用户钱包
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.BILL_WALLET_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                BillWalletData billWalletData = gson.fromJson(result, BillWalletData.class);
                if (billWalletData.getCode() == 0) {
                    BillWalletData.DataBean data = billWalletData.getData();
                    LotteryApp.balance = data.getTotalBalance();
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
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.USER_INFO_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                Gson gson = new Gson();
                userInfo = gson.fromJson(result, UserInfo.class);
                if (userInfo.getCode() == 0) {
                    long userId = userInfo.getData().getUserId();
                    if (userId == 2000000) {
                        userInfo.getData().setSuggestDisplayName(sp.getString("username", ""));
                        userInfo.getData().setHeadImg(sp.getString("imagUrl", ""));
                    }
                    LotteryApp.userType = userInfo.getData().getUserType();
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

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(30, intent);

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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

    private LoadingDialogUtils mDialogUtils;
    //第三方登录
    View.OnClickListener ThirdLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.weixin_login:
                    mDialogUtils = new LoadingDialogUtils(mContext);
                    partnerCode = "WECHAT";
                    UMShareAPI umShareAPI = UMShareAPI.get(mContext);
                    umShareAPI.getPlatformInfo((Activity) mContext, SHARE_MEDIA.WEIXIN, authListener);
                    break;
                case R.id.qq_login:


                    break;
            }
        }
    };

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            mDialogUtils.setDimiss();
            openId=data.get("uid");
            //String platName=data.get("name");
            Message msg = Message.obtain();
            msg.what = LOGIN_SUCCESS;
            msg.obj = data;
            handler.sendMessage(msg);

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Message msg = Message.obtain();
            msg.what = LOGIN_FAIL;
            handler.sendMessage(msg);
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Message msg = Message.obtain();
            msg.what = LOGIN_CANCEL;
            handler.sendMessage(msg);
        }
    };


    String openId;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOGIN_SUCCESS:
                    final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
                    Map<String, String> data = (Map<String, String>) msg.obj;
                    final String nickName = data.get("name");
                    final String imagUrl = data.get("iconurl");
                    Bundle params = new Bundle();
                    params.putString("channelId", Number.CHANNELID);
                    params.putString("partnerCode", partnerCode);
                    params.putString("timeStamp", System.currentTimeMillis() + "");
                    params.putString("openId", openId);
                    params.putString("userName", nickName);
                    mHttpInterface.post(Url.THIRD_LOGIN_URL, params, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            loadingDialogUtils.setDimiss();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                if (code == 0) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    LotteryApp.token = data.getString("token");
                                    LotteryApp.uid = data.getString("userId");
                                    LotteryApp.userType = data.getString("userType");
                                    LotteryApp.isLogin = true;
                                    LotteryApp.isThird = true;
                                    SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                                    //实例化SharedPreferences.Editor对象
                                    SharedPreferences.Editor editor = sp.edit();
                                    //用putString的方法保存数据
                                    editor.putString("partnerCode", partnerCode);
                                    editor.putString("password", openId);
                                    editor.putString("username", nickName);
                                    editor.putString("imagUrl", imagUrl);
                                    editor.putString("openId", openId);
                                    editor.putBoolean("islogin", true);
                                    //是否为第三方登录
                                    editor.putBoolean("isThird", true);
                                    //提交当前数据
                                    editor.commit();
                                    getUserInfo();
                                } else {
                                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                                getPrivilege();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError() {
                            loadingDialogUtils.setDimiss();
                        }
                    });
                    // LogUtils.e("登录成功","headImg:"+headImg+"openId:"+openId+"name:"+name+"gender:"+gender);
                    break;
                case LOGIN_FAIL:
                    Toast.makeText(LoginActivity.this, "第三方登录失败", Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_CANCEL:
                    Toast.makeText(LoginActivity.this, "第三方登录取消", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注册成功，同时进行登录，登录成功后会传递过来，这时候需要关闭这个登录界面。
        if (resultCode == 333) {
            Intent intent = new Intent();
            setResult(40, intent);
            finish();
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }

    }
}
