package com.daxiang.lottery.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.LoginResultData;
import com.daxiang.lottery.entity.PrivilegeData;
import com.daxiang.lottery.utils.AppUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.StringUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LaunchActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mJump;
    private HttpInterface2 mHttpInterface;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isJump) return;
                    isJump = true;
                    Intent intent = new Intent(LaunchActivity.this, HomeFragmentActivity.class);
                    startActivity(intent);
                    // finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        registerBoradcastReceiver();
        mHttpInterface = new HttpUtils2(this);
        status();//是否是审核状态
        //检查登录状态
        checkLoginStatus();
        initView();
        //启动页图片
        launchImage();
        skip();

    }

    private void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("finish");
        registerReceiver(br, intentFilter);
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("finish")) {
                int type = intent.getIntExtra("type", 0);
                switch (type) {
                    case ConstantNum.FINISH:
                        finish();
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (br != null)
            unregisterReceiver(br);
    }

    private boolean isJump = false;

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.image_welcome);
        mJump = (TextView) findViewById(R.id.jump);
        mJump.setVisibility(View.VISIBLE);
        // mImageView.setAnimation(alphaAnimation);
    }

    private void skip() {
        mJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isJump) return;
                isJump = true;
                Intent intent = new Intent(LaunchActivity.this, HomeFragmentActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message().obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }, 3000);
    }

    private void status() {
        Bundle bundle = new Bundle();
        bundle.putString("appName", "Daxiang");
        bundle.putString("osType", "2");
        bundle.putString("version", AppUtils.getVersionName(this));
        bundle.putString("channel", Number.CHANNELID);
        mHttpInterface.get(Url.ISBUY, bundle, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        //0是可以购买，审核完成；1是审核中
                        int isReview = data.getInt("rechargeSwitch");
                        //0可以购买，1不可以购买
                        int saleFlag = data.getInt("saleFlag");
                        SpUtils.setBuyPrivilege(SpUtils.IS_BUY, isReview);
                        SpUtils.setInt(SpUtils.SALE_FLAG, saleFlag);
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

    private void launchImage() {
        String launchImage = SpUtils.getString("launchImage");
        int height = DisplayUtil.getDisplayHeight();
        int width = DisplayUtil.getDisplayWidth();
        if (!TextUtils.isEmpty(launchImage)) {
            Picasso.with(LaunchActivity.this)
                    .load(launchImage)
                    .resize(width, height)
                    // .placeholder(R.mipmap.launch_white)
                    .config(Bitmap.Config.RGB_565)
                    // .onlyScaleDown()
                    .into(mImageView);
        }
    }

    private void checkLoginStatus() {
        SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        boolean islogin = sp.getBoolean("islogin", false);
        boolean isThird = sp.getBoolean("isThird", false);
        //如果是登录状态的话直接登录
        if (islogin) {
            //是登录状态，但是不是第三方登录
            if (!isThird) {
                final String username = sp.getString("username", "");
                final String password = sp.getString("password", "");
                final String salt = sp.getString("salt", "");
                Bundle params = new Bundle();
                params.putString("userName", username);
                params.putString("password", StringUtil.getMD5(password));
                params.putString("timeStamp", System.currentTimeMillis() + "");
                mHttpInterface.post(Url.LOGIN_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        if (result != null && result != "" && result.length() > 100) {
                            Gson gson = new Gson();
                            LoginResultData loginResultData = gson.fromJson(result, LoginResultData.class);
                            LogUtils.e("返回结果是", result);
                            if (loginResultData.getCode() == 0) {
                                //登录返回值获取成功
                                LotteryApp.token = loginResultData.getData().getToken();
                                LotteryApp.uid = loginResultData.getData().getUserId();
                                LotteryApp.nikeName = loginResultData.getData().getSuggestDisplayName();
                                LotteryApp.userType = loginResultData.getData().getUserType();
                                Number.CHANNELIDBUY = loginResultData.getData().getChannelId();
                                LotteryApp.salt = salt;
                                LotteryApp.isLogin = true;
                                LotteryApp.isThird = false;
                                SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                                //实例化SharedPreferences.Editor对象
                                SharedPreferences.Editor editor = sp.edit();
                                //用putString的方法保存数据
                                editor.putString("username", username);
                                editor.putString("password", password);
                                editor.putString("salt", salt);
                                editor.putBoolean("islogin", true);
                                editor.putBoolean("isThird", false);
                                //提交当前数据
                                editor.commit();
                                //刷新用户信息
                                NetWorkData netWorkData = new NetWorkData(LaunchActivity.this);
                                netWorkData.refreshUserInfo();
                            }
                        } else {
                        }
                        getPrivilege();
                    }

                    @Override
                    public void onError() {

                    }
                });
            } else {
                //是第三方登录的状态
                final String partnerCode = sp.getString("partnerCode", "");
                final String openId = sp.getString("openId", "");
                Bundle params = new Bundle();
                params.putString("channelId", Number.CHANNELID);
                params.putString("partnerCode", partnerCode);
                //params.putString("headImg",headImg);
                params.putString("timeStamp", System.currentTimeMillis() + "");
                params.putString("openId", openId);
                mHttpInterface.post(Url.THIRD_LOGIN_URL, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                LotteryApp.token = data.getString("token");
                                LotteryApp.uid = data.getString("userId");
                                LotteryApp.isLogin = true;
                                LotteryApp.isThird = true;
                                //Toast.makeText(LaunchActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                                //实例化SharedPreferences.Editor对象
                                SharedPreferences.Editor editor = sp.edit();
                                //用putString的方法保存数据
                                editor.putString("partnerCode", partnerCode);
                                editor.putString("password", openId);
                                editor.putBoolean("islogin", true);
                                //是否为第三方登录
                                editor.putBoolean("isThird", true);
                                //提交当前数据
                                editor.commit();
                                //刷新用户信息
                                NetWorkData netWorkData = new NetWorkData(LaunchActivity.this);
                                netWorkData.refreshUserInfo();
                            } else {
                                Toast.makeText(LaunchActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            getPrivilege();
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

            @Override
            public void onError() {

            }
        });
    }
}
