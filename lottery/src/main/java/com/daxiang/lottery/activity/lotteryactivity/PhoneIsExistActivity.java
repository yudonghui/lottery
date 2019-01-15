package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.IsExsitsResultData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class PhoneIsExistActivity extends BaseActivity {
    private EditText mPhone;
    private TextView mConfirm;
    private Context mContext;
    private HttpInterface2 mHttpInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_phone_is_exist);
        mContext = PhoneIsExistActivity.this;
        initView();
        addListener();
    }


    private void initView() {
        mTitleBar.setTitle("手机号");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mPhone = (EditText) findViewById(R.id.edit_phone);
        mConfirm = (TextView) findViewById(R.id.text_next);
        mHttpInterface = new HttpUtils2(mContext);
    }

    private void addListener() {
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = mPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    //判断手机号是否为空
                    Toast.makeText(mContext, "请输入手机号码",
                            Toast.LENGTH_SHORT).show();
                } else if (!isLegalUsername(phone, 0)) {
                    Toast.makeText(mContext, "手机号不符合规范!请使用中国大陆手机号",
                            Toast.LENGTH_SHORT).show();
                } else {
                    final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(PhoneIsExistActivity.this);
                    Bundle params = new Bundle();
                    params.putString("phone", phone);
                    params.putString("timeStamp", System.currentTimeMillis() + "");
                    mHttpInterface.post(Url.PHONE_EXIST_URL, params, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            loadingDialogUtils.setDimiss();
                            Gson gson = new Gson();
                            IsExsitsResultData isExsitsResultData = gson.fromJson(result, IsExsitsResultData.class);
                            /*
                            * 手机号没有被注册过，获取验证码，成功之后直接跳转页面绑定
                            * 手机号如果注册过，提示是否将手机号和第三方账号合并
                            *    是 获取验证码，成功之后跳转
                            *    否
                            * */
                            if (isExsitsResultData.getCode() == Number.OBTAINCODE) {
                                obtainCode(phone, false);
                            } else {
                                HintDialogUtils.setHintDialog(mContext);
                                HintDialogUtils.setMessage("手机号已存在，是否绑定当前账号？");
                                HintDialogUtils.setTitleVisiable(true);
                                HintDialogUtils.setTvCancel("否");
                                HintDialogUtils.setConfirm("是", new DialogHintInterface() {
                                    @Override
                                    public void callBack(View view) {
                                        obtainCode(phone, true);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onError() {
                            loadingDialogUtils.setDimiss();
                        }
                    });
                }
            }
        });
    }

    /*
    * 第二个参数  false 说明用户不存在
    *            true  说明用户存在
    * */
    private void obtainCode(final String phone, final boolean isExist) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(this);
        loadingDialogUtils.setLoadingText("正在送验证到" + phone);
        Bundle params = new Bundle();
        params.putString("channelId", Number.CHANNELID);
        params.putString("phone", phone);
        params.putString("msgType", "0");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.SMS_CODE_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if ("0".equals(jsonObject.getString("code"))) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String msgId = data.getString("msgId");
                        Intent intent = new Intent(mContext, ThirdBindPhoneActivity.class);
                        intent.putExtra("msgId", msgId);
                        intent.putExtra("phone", phone);
                        intent.putExtra("isExist", isExist);
                        startActivityForResult(intent, 111);
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
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 400) {
            Intent intent = new Intent();
            intent.putExtra("phone", data.getStringExtra("phone"));
            setResult(500, intent);
            finish();
        }
    }
}
