package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.view.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

public class NickNameActivity extends AppCompatActivity {
    // Content View Elements

    private TitleBar mTitlebar;
    private EditText mEt_nickname;
    private Button mNickname_comit;
    private HttpInterface2 mHttpInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        bindViews();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mNickname_comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string = mEt_nickname.getText().toString();
                if (TextUtils.isEmpty(string)) {
                    Toast.makeText(NickNameActivity.this, "您还没填写您的用户名哦", Toast.LENGTH_SHORT).show();
                } else if (StringUtil.isLegalPhone(string)){
                    Toast.makeText(NickNameActivity.this,"用户名不能为手机号！",Toast.LENGTH_SHORT).show();

                } else {

                /*
                * 先判断一下用户名是否存在，如果存在不允许注册，如果不存在才提交注册申请
                * */
                    Bundle params1 = new Bundle();
                    params1.putString("timeStamp", System.currentTimeMillis() + "");
                    params1.putString("userName", string);
                    mHttpInterface.post(Url.USERNAME_EXIST_URL, params1, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                //状态码 1170：用户名存在； 1120：用户名不存在
                                if (code == 1120) {
                                    nicknameWeb(string);
                                } else {
                                    Toast.makeText(NickNameActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    private void nicknameWeb(final String string) {
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("channelId", Number.CHANNELID);
        params.putString("userName", string);
        mHttpInterface.post(Url.NICKNAME_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if ("0".equals(jsonObject.getString("code"))) {
                        Intent intent = new Intent();
                        intent.putExtra("nickName", string);
                        setResult(300, intent);
                        finish();
                    } else {
                        Toast.makeText(NickNameActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
        mTitlebar = (TitleBar) findViewById(R.id.nickname_titlebar);
        mTitlebar.setImageTitleVisibility(false);
        mTitlebar.setTitle("设置用户名");
        mTitlebar.setTitleVisibility(true);
        mEt_nickname = (EditText) findViewById(R.id.et_nickname);
        mNickname_comit = (Button) findViewById(R.id.nickname_comit);
        mTitlebar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("displayname", "");
                setResult(300, intent);
                finish();
            }
        });
        mHttpInterface = new HttpUtils2(NickNameActivity.this);
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        intent.putExtra("cardFlag", "");// 放入返回值
        setResult(300, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据
        finish();// 结束当前的activity,等于点击返回按钮
        return true;
    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
