package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.daxiang.lottery.utils.HttpUtils2;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class BindPhoneActivity extends BaseActivity {
    private EditText mEtNewPhone;
    private EditText mEtCode;
    private TextView mObtainCode;
    private TextView mTextCommit;
    private String oldPhone;
    private boolean phoneFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_bind_phone);
        Intent intent = getIntent();
        oldPhone = intent.getStringExtra("oldPhone");
        phoneFlag = intent.getBooleanExtra("isBind", false);
        initView();
        addListener();
    }

    private void initView() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mEtNewPhone = (EditText) findViewById(R.id.edit_phone_old);
        mEtCode = (EditText) findViewById(R.id.edit_code);
        mObtainCode = (TextView) findViewById(R.id.text_obtain_code);
        mTextCommit = (TextView) findViewById(R.id.text_next);
    }

    private void addListener() {
        //获取验证码
        mObtainCode.setOnClickListener(ObtainCodeListener);
        if (phoneFlag) {
            mTitleBar.setTitle("更换手机号");
            mTextCommit.setOnClickListener(CommitListener);
        } else {
            mTitleBar.setTitle("绑定手机号");
            mTextCommit.setOnClickListener(CommitListener2);
        }

    }

    String timeStamp;
    String msgId;
    int time;
    private Handler mHandler = new Handler();
    View.OnClickListener ObtainCodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            timeStamp = System.currentTimeMillis() + "";
            final String newPhone = mEtNewPhone.getText().toString().trim();
            if (TextUtils.isEmpty(newPhone)) {
                //判断手机号是否为空
                Toast.makeText(BindPhoneActivity.this, "请输入手机号码",
                        Toast.LENGTH_SHORT).show();
            } else {
                //判断手机号是否合法
                if (!isLegalUsername(newPhone, 0)) {
                    Toast.makeText(BindPhoneActivity.this, "手机号不符合规范!请使用中国大陆手机号",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //满足要求后请求网络,查看用户是否存在
                    HttpInterface2 mHttpInterface = new HttpUtils2(BindPhoneActivity.this);
                    Bundle params = new Bundle();
                    params.putString("phone", newPhone);
                    params.putString("timeStamp", timeStamp);
                    mHttpInterface.post(Url.PHONE_EXIST_URL, params, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            if (result != null && result != "") {
                                Gson gson = new Gson();
                                IsExsitsResultData isExsitsResultData = gson.fromJson(result, IsExsitsResultData.class);
                                //用户bu存在请求网络，获取验证码
                                if (isExsitsResultData.getCode() == Number.OBTAINCODE) {

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
                                    HttpInterface2 mHttpInterface = new HttpUtils2(BindPhoneActivity.this);
                                    Bundle params = new Bundle();
                                    params.putString("channelId", Number.CHANNELID);
                                    params.putString("phone", newPhone);
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

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                                } else {
                                    Toast.makeText(BindPhoneActivity.this, isExsitsResultData.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });

                }
            }
        }
    };
    //已经绑定过了，用于更改手机号的请求
    View.OnClickListener CommitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String newPhone = mEtNewPhone.getText().toString().trim();
            String vcode = mEtCode.getText().toString().trim();
            if (TextUtils.isEmpty(vcode)) {
                Toast.makeText(BindPhoneActivity.this, "验证码不能为空",
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(newPhone)) {
                Toast.makeText(BindPhoneActivity.this, "手机号不能为空",
                        Toast.LENGTH_SHORT).show();
            } else if (!isLegalUsername(newPhone, 0)) {
                Toast.makeText(BindPhoneActivity.this, "请输入规范的手机号",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            HttpInterface2 mHttpInterface = new HttpUtils2(BindPhoneActivity.this);
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("msgId", msgId);
            params.putString("checkCode", vcode);
            params.putString("phone", oldPhone);
            params.putString("newPhone", newPhone);
            params.putString("channelId", Number.CHANNELID);
            params.putString("timeStamp", timeStamp);
            mHttpInterface.post(Url.CHANG_MOBILE_BIND_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        //JSONObject data = jsonObject.getJSONObject("data");
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            Intent intent = new Intent();
                            intent.putExtra("phone", newPhone);
                            setResult(500, intent);
                            finish();
                        } else {
                            Toast.makeText(BindPhoneActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
    //当用户没有绑定手机号的时候，第一次绑定手机号的请求
    View.OnClickListener CommitListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String newPhone = mEtNewPhone.getText().toString().trim();
            String vcode = mEtCode.getText().toString().trim();
            if (TextUtils.isEmpty(vcode)) {
                Toast.makeText(BindPhoneActivity.this, "验证码不能为空",
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(newPhone)) {
                Toast.makeText(BindPhoneActivity.this, "手机号不能为空",
                        Toast.LENGTH_SHORT).show();
            } else if (!isLegalUsername(newPhone, 0)) {
                Toast.makeText(BindPhoneActivity.this, "请输入规范的手机号",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            HttpInterface2 mHttpInterface = new HttpUtils2(BindPhoneActivity.this);
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("msgId", msgId);
            params.putString("checkCode", vcode);
            params.putString("phone", newPhone);
            //params.putString("channelId", Number.CHANNELID);
            params.putString("userId", LotteryApp.uid);
            params.putString("timeStamp", timeStamp);
            mHttpInterface.post(Url.ADD_MOBILE_BIND_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        //JSONObject data = jsonObject.getJSONObject("data");
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            Intent intent = new Intent();
                            intent.putExtra("phone", newPhone);
                            setResult(500, intent);
                            finish();
                        } else
                            Toast.makeText(BindPhoneActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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

    /* View.OnClickListener CommitListener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String oldPhone = mEtOldPhone.getText().toString().trim();
             String vcode = mEtCode.getText().toString().trim();
             if (!isLegalUsername(oldPhone, 0)) {
                 Toast.makeText(ChangPhoneActivity.this, "请输入规范的手机号",
                         Toast.LENGTH_SHORT).show();
                 return;
             }
             HttpInterface2 mHttpInterface = new HttpUtils2();
             Bundle params = new Bundle();
             params.putString("token", LotteryApp.token);
             params.putString("phone", oldPhone);
             params.putString("msgId", msgId);
             params.putString("newPhone", newPhone);
             params.putString("checkCode", vcode);
             params.putString("channelId", "6000001");
             params.putString("timeStamp", timeStamp);

             mHttpInterface.post(Url.CHANG_MOBILE_BIND_URL, params, new JsonInterface() {
                 @Override
                 public void callback(String result) {
                     try {
                         JSONObject jsonObject = new JSONObject(result);
                         //JSONObject data = jsonObject.getJSONObject("data");
                         int code = jsonObject.getInt("code");
                         Toast.makeText(BindPhoneActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                         if (code == 0) {
                             finish();
                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             });
         }
     };
 */
    //判断用户名是否合法
    private boolean isLegalUsername(String username, int type) {
        if (type == 0) {
            String reg = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";
            return Pattern.matches(reg, username);
        } else {
            return username != null && username.length() >= 6
                    && username.length() <= 24;
        }
    }
}
