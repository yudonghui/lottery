package com.daxiang.lottery.activity.lotteryactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;
public class SuggestionActivity extends BaseActivity {
    private EditText mEdit_suggestion_content;
    private EditText mEdit_suggestion_contact;

    // End Of Content View Elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_suggestion);

        bindViews();
        addListener();
    }

    private void bindViews() {

        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("意见反馈");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleRegisterVisibility(true);
        mTitleBar.mTextRegister.setText("发送");
        mEdit_suggestion_content = (EditText) findViewById(R.id.edit_suggestion_content);
        mEdit_suggestion_contact = (EditText) findViewById(R.id.edit_suggestion_contact);
    }
    private void addListener() {
        mTitleBar.mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=mEdit_suggestion_content.getText().toString();
                String  contact=mEdit_suggestion_contact.getText().toString();
                if(content==null||content.length()==0){
                    Toast.makeText(SuggestionActivity.this,"请输入反馈内容",Toast.LENGTH_SHORT).show();
                }else {
                    HttpInterface2 mHttp=new HttpUtils2(SuggestionActivity.this);
                    Bundle params = new Bundle();
                    params.putString("category","0");
                    params.putString("feedbackTitle","android");
                    params.putString("userId", LotteryApp.uid);
                    params.putString("feedbackContent",content);
                    params.putString("status","0");
                    if (!TextUtils.isEmpty(contact)){
                        params.putString("remarks",contact);
                    }
                    params.putString("token",LotteryApp.token);
                    params.putString("timeStamp", System.currentTimeMillis()+"");
                    mHttp.post(Url.USER_BACK_SMS, params,new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            //Log.e("result",result);
                            try {
                                JSONObject jsonObject=new JSONObject(result);
                                int code = jsonObject.getInt("code");
                                HintDialogUtils.setHintDialog(SuggestionActivity.this);
                                HintDialogUtils.setVisibilityCancel();
                                if(code==0){
                                    HintDialogUtils.setMessage("已收到您的宝贵意见，非常感谢");

                                }else {
                                    HintDialogUtils.setMessage("发送失败");
                                }
                                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                                    @Override
                                    public void callBack(View view) {
                                        finish();
                                    }
                                });
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

}
