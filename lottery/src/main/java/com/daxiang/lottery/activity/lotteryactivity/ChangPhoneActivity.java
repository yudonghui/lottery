package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class ChangPhoneActivity extends BaseActivity {
    private EditText mEtOldPhone;
    private EditText mEtCode;
    private TextView mObtainCode;
    private TextView mTextNext;
    private String oldPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_chang_phone);
        Intent intent = getIntent();
        oldPhone = intent.getStringExtra("phone");
        initView();
        addListener();
    }

    private void initView() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("更换手机号");
        mEtOldPhone = (EditText) findViewById(R.id.edit_phone_old);
        mEtOldPhone.setText(oldPhone);
        //mEtOldPhone.setFocusable(false);
        mEtOldPhone.setEnabled(false);
        mEtCode = (EditText) findViewById(R.id.edit_code);
        mObtainCode = (TextView) findViewById(R.id.text_obtain_code);
        mTextNext = (TextView) findViewById(R.id.text_next);
    }

    private void addListener() {
        //获取验证码
        mObtainCode.setOnClickListener(ObtainCodeListener);
        //点击下一步
        mTextNext.setOnClickListener(CommitListener);
    }

    String timeStamp;
    String msgId;
    int time;
    private Handler mHandler = new Handler();
    View.OnClickListener ObtainCodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(ChangPhoneActivity.this);
            timeStamp = System.currentTimeMillis() + "";
            HttpInterface2 mHttpInterface = new HttpUtils2(ChangPhoneActivity.this);
            Bundle params = new Bundle();
            params.putString("phone", oldPhone);
            params.putString("timeStamp", timeStamp);
            mHttpInterface.post(Url.PHONE_EXIST_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {

                    if (result != null && result != "") {
                        Gson gson = new Gson();
                        IsExsitsResultData isExsitsResultData = gson.fromJson(result, IsExsitsResultData.class);
                        //用户存在请求网络，获取验证码
                        if (isExsitsResultData.getCode() != Number.OBTAINCODE) {
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


                            HttpInterface2 mHttpInterface = new HttpUtils2(ChangPhoneActivity.this);
                            Bundle params = new Bundle();
                            params.putString("channelId", Number.CHANNELID);
                            params.putString("phone", oldPhone);
                            params.putString("msgType", "0");
                            params.putString("timeStamp", timeStamp);
                            mHttpInterface.post(Url.SMS_CODE_URL, params, new JsonInterface() {
                                @Override
                                public void callback(String result) {
                                    loadingDialogUtils.setDimiss();
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
                                    loadingDialogUtils.setDimiss();
                                }
                            });
                        } else {
                            loadingDialogUtils.setDimiss();
                            Toast.makeText(ChangPhoneActivity.this, isExsitsResultData.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        loadingDialogUtils.setDimiss();
                    }
                }

                @Override
                public void onError() {
                    loadingDialogUtils.setDimiss();
                }
            });
        }
    };
    View.OnClickListener CommitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String oldPhone = mEtOldPhone.getText().toString().trim();
            String vcode = mEtCode.getText().toString().trim();
            if (vcode == null || vcode.length() == 0) {
                Toast.makeText(ChangPhoneActivity.this, "验证码不能为空",
                        Toast.LENGTH_SHORT).show();
            } else if (!isLegalUsername(oldPhone, 0)) {
                Toast.makeText(ChangPhoneActivity.this, "请输入规范的手机号",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            HttpInterface2 mHttpInterface = new HttpUtils2(ChangPhoneActivity.this);
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("msgId", msgId);
            params.putString("checkCode", vcode);
            params.putString("timeStamp", timeStamp);

            mHttpInterface.post(Url.CHECKCODE_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        //JSONObject data = jsonObject.getJSONObject("data");
                        int code = jsonObject.getInt("code");
                        Toast.makeText(ChangPhoneActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        if (code == 0) {
                            Intent intent = new Intent(ChangPhoneActivity.this, BindPhoneActivity.class);
                            intent.putExtra("oldPhone", oldPhone);
                            intent.putExtra("isBind", true);
                            startActivity(intent);
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
    };


    /* View.OnClickListener CommitLisner = new View.OnClickListener() {
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
                         Toast.makeText(ChangPhoneActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
