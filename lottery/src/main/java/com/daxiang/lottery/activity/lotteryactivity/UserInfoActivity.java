package com.daxiang.lottery.activity.lotteryactivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.activity.ForgetPasswordActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.UserInfo;
import com.daxiang.lottery.utils.BitmapUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.SDCardUtils;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.CircleImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;


public class UserInfoActivity extends BaseActivity {
    UserInfo userInfo;
    //BankInfoData bankInfoData;
    // Content View Elements
    private LinearLayout mLl_QQ;
    private TextView mTv_QQ;
    private LinearLayout mLl_wexin;
    private TextView mTv_wexin;
    private LinearLayout mLl_userinfo_switch_avatar;
    private CircleImageView mIv_userinfo_avatar;
    private LinearLayout mLl_userinfo_nickname;
    private TextView mTv_userinfo_nickname;
    private LinearLayout mLl_userinfo_card;
    private TextView mTv_userinfo_card;
    private LinearLayout mLl_userinfo_bankcard;
    private TextView mTv_userinfo_bankcard;
    private LinearLayout mLl_userinfo_phone;
    private TextView mTv_userinfo_phone;
    private ImageView mImageNickname;
    private LinearLayout mChangePassword;
    private TextView mExitLogon;
    //是否绑定手机号的标志位
    private boolean phoneFlag;
    //是否绑定银行卡的标志位
    private boolean bankFlag;
    //是否绑定身份证的标志位
    private boolean cardFlag;
    //判断昵称是否修改
    private boolean nicknameFlag;
    private String mDisplayname;
    private String mIdentification;
    private String mMobile;
    private String userName;
    private AlertDialog mPhonedialog;
    private AlertDialog successUnbindDialog;
    private TextView mCamera;
    private TextView mPhoto;
    private Button mCancel;
    // private PopupWindow popupWindow;
    private View inflate;
    //private String headerUrl;
    private Dialog mDialog;
    private Window window;
    private String phone;
    //调用相机和相册
    //调用相机和相册
    private static final String IMAGE_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/donghui/photo";
    private static final String IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/demo.jpg";
    private String mPath;
    private static final int PERMISSIONS_REQUEST_PHOTO = 0x01;
    private static final int PERMISSIONS_REQUEST_FILE = 0x02;
    private static final int REQUEST_CODE_TAKING_PHOTO = 0x03;
    private static final int REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL = 0x04;
    private static final int REQUEST_CODE_CUT_PHOTO = 0x05;
    private static final int TARGET_HEAD_SIZE = 480;
    private Uri mUri;
    private boolean isTakePhoto = false;
    private boolean isGetPic = false;
    private Context mContext;
    private boolean qqFlag = false;
    private boolean wxFlag = false;
    private HttpInterface2 mHttpInterface;
    private String partnerCode;
    private SharedPreferences sp;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Path", mPath);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_user_info);
        sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        mContext = UserInfoActivity.this;
        Intent intent = getIntent();
        userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
        //bankInfoData = (BankInfoData) intent.getSerializableExtra("bankInfo");
        // headerUrl = intent.getStringExtra("headerUrl");

        initView();
        if (savedInstanceState != null) mPath = savedInstanceState.getString("Path");
        setView();
        addListener();
    }

    private void initView() {
        mHttpInterface = new HttpUtils2(mContext);
        userName = userInfo.getData().getRealName();
        mIdentification = userInfo.getData().getIdentification();
        mTitleBar.setTitle("个人信息");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mLl_userinfo_switch_avatar = (LinearLayout) findViewById(R.id.ll_userinfo_switch_avatar);
        mIv_userinfo_avatar = (CircleImageView) findViewById(R.id.iv_userinfo_avatar);
        //头像
        if (userInfo != null && userInfo.getCode() == 0) {
            Picasso.with(this).load(userInfo.getData().getHeadImg()).error(R.mipmap.default_header).placeholder(R.mipmap.default_header).memoryPolicy(MemoryPolicy.NO_CACHE).into(mIv_userinfo_avatar);
        }
        mLl_userinfo_nickname = (LinearLayout) findViewById(R.id.ll_userinfo_nickname);
        mTv_userinfo_nickname = (TextView) findViewById(R.id.tv_userinfo_nickname);
        mImageNickname = (ImageView) findViewById(R.id.image_nickname);
        mLl_userinfo_card = (LinearLayout) findViewById(R.id.ll_userinfo_card);
        mTv_userinfo_card = (TextView) findViewById(R.id.tv_userinfo_card);
        mLl_userinfo_bankcard = (LinearLayout) findViewById(R.id.ll_userinfo_bankcard);
        mTv_userinfo_bankcard = (TextView) findViewById(R.id.tv_userinfo_bankcard);
        mLl_userinfo_phone = (LinearLayout) findViewById(R.id.ll_userinfo_phone);
        mTv_userinfo_phone = (TextView) findViewById(R.id.tv_userinfo_phone);
        mLl_QQ = (LinearLayout) findViewById(R.id.ll_qq);
        mTv_QQ = (TextView) findViewById(R.id.tv_qq);
        mLl_wexin = (LinearLayout) findViewById(R.id.ll_weixin);
        mTv_wexin = (TextView) findViewById(R.id.tv_weixin);
        mChangePassword = (LinearLayout) findViewById(R.id.ll_change_password);
        mExitLogon = (TextView) findViewById(R.id.btn_logout);

        inflate = View.inflate(UserInfoActivity.this, R.layout.item_popup, null);
        mCamera = (TextView) inflate.findViewById(R.id.buttonCamera);
        mPhoto = (TextView) inflate.findViewById(R.id.buttonPhoto_selector);
        mCancel = (Button) inflate.findViewById(R.id.buttoncancle);
        mDialog = new Dialog(UserInfoActivity.this, R.style.ActionSheetDialogStyle);
        window = mDialog.getWindow();
        mDialog.setContentView(inflate);

        File file = new File(IMAGE_SAVE_DIR);
        if (!file.exists()) file.mkdirs();
    }

    private void setView() {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }

        //用户名
        if (!StringUtil.isChang(userInfo.getData().getUserName())) {
            mDisplayname = userInfo.getData().getUserName();
            mTv_userinfo_nickname.setText(mDisplayname);
            nicknameFlag = false;
        } else {
            mTv_userinfo_nickname.setText("未设置用户名");
            nicknameFlag = true;
        }
        //身份证号
        mIdentification = userInfo.getData().getIdentification();
        if (LotteryApp.cardFlag) {
            mTv_userinfo_card.setText("已绑定");
            userName = userInfo.getData().getRealName();
            cardFlag = true;
        } else {
            mTv_userinfo_card.setText("未绑定");
            cardFlag = false;
        }
        //银行卡号
        String bankCard = userInfo.getData().getBankCard();
        if (LotteryApp.bankFlag) {
            mTv_userinfo_bankcard.setText("已绑定");
            bankFlag = true;
        } else {
            mTv_userinfo_bankcard.setText("未绑定");
            bankFlag = false;
        }
        //手机号
        mMobile = userInfo.getData().getPhone();
        if (LotteryApp.phoneFlag) {
            mTv_userinfo_phone.setText(StringUtil.phoneHint(userInfo.getData().getPhone()));
            phoneFlag = true;
        } else {
            mTv_userinfo_phone.setText("未绑定");
            phoneFlag = false;
        }
        //QQ号/微信号
        /*
        * third    格式"QQ;WEIXIN"
        * nickName 格式"QQ:yudonghui;WEIXIN:huidongyu"
        * */
        String third = userInfo.getData().getPartnerCode();
        String nickName = userInfo.getData().getNickName();
        if (userInfo.getData().getUserId() == 2000000) {
            switch (sp.getString("partnerCode", "")) {
                case "QQ":
                    mTv_QQ.setText(sp.getString("username", ""));
                    break;
                case "WECHAT":
                    mTv_wexin.setText(sp.getString("username", ""));
                    break;
            }
            return;
        }

        if (!TextUtils.isEmpty(third)) {
            String[] split = third.split("\\;");
            for (int i = 0; i < split.length; i++) {
                String[] split1 = nickName.split("\\;");
                switch (split[i]) {
                    case "QQ":
                        for (int j = 0; j < split1.length; j++) {
                            String[] split2 = split1[j].split("\\:");
                            if ("QQ".equals(split2[0])) {
                                mTv_QQ.setText(split2[1]);
                                break;
                            }
                        }
                        /*
                        * 如果已经绑定了，但是没有昵称的情况
                        * */
                        if (mTv_QQ.getText().equals("未绑定")) {
                            mTv_QQ.setText("已绑定");
                        }
                        qqFlag = true;
                        break;
                    case "WECHAT":
                        for (int j = 0; j < split1.length; j++) {
                            String[] split2 = split1[j].split("\\:");
                            if ("WECHAT".equals(split2[0])) {
                                mTv_wexin.setText(split2[1]);
                                break;
                            }
                        }
                        /*
                        * 如果已经绑定了，但是没有昵称的情况
                        * */
                        if (mTv_wexin.getText().equals("未绑定")) {
                            mTv_wexin.setText("已绑定");
                        }
                        wxFlag = true;
                        break;
                }
            }
        }
    }

    private void addListener() {
        //更换头像
        mLl_userinfo_switch_avatar.setOnClickListener(AvatarListener);
        //拍照
        mCamera.setOnClickListener(CameraListener);
        //从相册中选取
        mPhoto.setOnClickListener(PhotoListener);
        //取消
        mCancel.setOnClickListener(CancelListener);
        //绑定身份证
        mLl_userinfo_card.setOnClickListener(CardListener);
        //绑定银行卡
        mLl_userinfo_bankcard.setOnClickListener(BankListener);
        //设置昵称,如果有昵称就设置为不可点击，昵称只能设置一次
        // nicknameFlag=true;
        if (nicknameFlag) {
            mTv_userinfo_nickname.setOnClickListener(NickNameListener);
        } else {
            mTv_userinfo_nickname.setOnClickListener(null);
            mImageNickname.setVisibility(View.GONE);
        }
        //绑定和解绑手机号
        mLl_userinfo_phone.setOnClickListener(PhoneListener);
        //绑定和解绑QQ
        mLl_QQ.setOnClickListener(ThirdListener);
        //绑定和解绑微信
        mLl_wexin.setOnClickListener(ThirdListener);
        //修改密码
        mChangePassword.setOnClickListener(ChangePasswordListener);
        //退出登录
        mExitLogon.setOnClickListener(ExitLogonListener);
        //返回按钮
        mTitleBar.mImageBack.setOnClickListener(BackListener);

    }

    //返回键
    View.OnClickListener BackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            setResult(40, intent);
            finish();
        }
    };

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(40, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //拍照
    View.OnClickListener CameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            operTakePhoto();
        }
    };
    //从相册中选取
    View.OnClickListener PhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            operChoosePic();
        }
    };
    //取消
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };
    View.OnClickListener AvatarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.phoneFlag && LotteryApp.isThird) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                        startActivityForResult(intent, 100);
                    }
                });
            } else {
                mDialog.show();
                window.setGravity(Gravity.BOTTOM);
            }
        }
    };
    View.OnClickListener ExitLogonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sp = UserInfoActivity.this.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            //  edit.putString("username", "");
            edit.putString("password", "");
            edit.putString("partnerCode", "");
            edit.putBoolean("islogin", false);
            edit.commit();

            new NetWorkData(mContext).deleteUmengTag();//删除友盟的设置的标签
            LotteryApp.isLogin = false;
            LotteryApp.uid="";
            LotteryApp.token="";
            LotteryApp.nikeName="";
            // LotteryApp.buyPrivilege = 2;
            // LotteryApp.recommendPrivilege = 2;
            Intent intent = new Intent();
            setResult(50, intent);
            finish();
        }
    };
    View.OnClickListener ChangePasswordListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //第三方登录，未绑定手机号的时候
            if (userInfo.getData().getUserId() == Number.THIRD_UID) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                        startActivityForResult(intent, 100);
                    }
                });
            } else if (!LotteryApp.phoneFlag) {
                HintDialogUtils.setHintDialog(UserInfoActivity.this);
                HintDialogUtils.setMessage("您还没有绑定手机号，是否绑定手机号？");
                HintDialogUtils.setConfirm("是", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(UserInfoActivity.this, BindPhoneActivity.class);
                        intent.putExtra("isBind", false);
                        startActivityForResult(intent, 100);

                    }
                });
                HintDialogUtils.setTvCancel("否");
            } else {
                Intent intent = new Intent(UserInfoActivity.this, ForgetPasswordActivity.class);
                intent.putExtra("changOrForget", false);
                intent.putExtra("phone", LotteryApp.phone);
                startActivityForResult(intent, 100);
            }
        }
    };
    View.OnClickListener PhoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (phoneFlag) {
                HintDialogUtils.setHintDialog(UserInfoActivity.this);
                HintDialogUtils.setMessage("是否更换手机号");
                HintDialogUtils.setConfirm("是", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(UserInfoActivity.this, ChangPhoneActivity.class);
                        intent.putExtra("phone", mMobile);
                        startActivityForResult(intent, 100);
                    }
                });
                HintDialogUtils.setTvCancel("否");
            } else {
                HintDialogUtils.setHintDialog(UserInfoActivity.this);
                HintDialogUtils.setMessage("是否绑定手机号");
                HintDialogUtils.setConfirm("是", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        if (LotteryApp.isThird) {
                            Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                            startActivityForResult(intent, 100);
                        } else {
                            Intent intent = new Intent(UserInfoActivity.this, BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivityForResult(intent, 100);
                        }
                    }
                });
                HintDialogUtils.setTvCancel("否");
            }
        }
    };
    View.OnClickListener NickNameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserInfoActivity.this, NickNameActivity.class);
            startActivityForResult(intent, 100);
        }
    };
    View.OnClickListener CardListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.phoneFlag) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        //手机号未绑定
                        if (LotteryApp.isThird) {
                            Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                            startActivityForResult(intent, 100);
                        } else {
                            Intent intent = new Intent(mContext, BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivity(intent);
                        }
                    }
                });
            } else {
                Intent intent = new Intent(UserInfoActivity.this, CardActivity.class);
                intent.putExtra("cardFlag", cardFlag);
                intent.putExtra("name", userName);
                intent.putExtra("number", mIdentification);
                startActivityForResult(intent, 100);
            }

        }
    };
    View.OnClickListener BankListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.phoneFlag) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                    @Override
                    public void callBack(View view) {
                        //手机号未绑定
                        if (LotteryApp.isThird) {
                            Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                            startActivityForResult(intent, 100);
                        } else {
                            Intent intent = new Intent(mContext, BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivity(intent);
                        }
                    }
                });
            } else if (!cardFlag) {
                //没有绑定身份证的话提示然后跳转到绑定身份证的界面
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("为了您的账号安全，请先绑定身份证！");
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(UserInfoActivity.this, CardActivity.class);
                        intent.putExtra("cardFlag", cardFlag);
                        intent.putExtra("name", userInfo.getData().getRealName());
                        intent.putExtra("number", userInfo.getData().getIdentification());
                        startActivityForResult(intent, 100);
                    }
                });

            } else {
                if (bankFlag) {
                    Intent intent = new Intent(UserInfoActivity.this, BankActivity.class);
                    intent.putExtra("mDisplayname", userName);
                    intent.putExtra("userInfo", userInfo);
                    startActivityForResult(intent, 100);
                } else {
                    Intent intent = new Intent(UserInfoActivity.this, BankEidteActivity.class);
                    intent.putExtra("mDisplayname", userName);
                    intent.putExtra("userInfo", userInfo);
                    startActivityForResult(intent, 100);
                }
            }
        }
    };
    View.OnClickListener ThirdListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //如果手机号没有绑定的话提示去绑定手机号
            if (!LotteryApp.phoneFlag) {
                HintDialogUtils.setHintDialog(mContext);
                HintDialogUtils.setMessage("请先绑定手机号！");
                HintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                    @Override
                    public void callBack(View view) {
                        //手机号未绑定
                        if (LotteryApp.isThird) {
                            Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                            startActivityForResult(intent, 100);
                        } else {
                            Intent intent = new Intent(mContext, BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivity(intent);
                        }
                    }
                });
                return;
            }
            switch (v.getId()) {
                case R.id.ll_qq:


                    break;
                case R.id.ll_weixin:
                    HintDialogUtils.setHintDialog(mContext);
                    HintDialogUtils.setTvCancel("否");
                    if (wxFlag) {
                        HintDialogUtils.setMessage("是否解除微信绑定？");
                        HintDialogUtils.setConfirm("是", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {
                                partnerCode = "WECHAT";
                                unbundThird();
                            }
                        });
                    } else {
                        HintDialogUtils.setMessage("是否绑定微信？");
                        HintDialogUtils.setConfirm("是", new DialogHintInterface() {
                            @Override
                            public void callBack(View view) {
                                partnerCode = "WECHAT";
                                UMShareAPI umShareAPI = UMShareAPI.get(mContext);
                                umShareAPI.getPlatformInfo((Activity) mContext, SHARE_MEDIA.WEIXIN, authListener);
                            }
                        });
                    }
                    break;
            }
        }
    };

    private void unbundThird() {
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("partnerCode", partnerCode);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("phone", userInfo.getData().getPhone());
        mHttpInterface.post(Url.THIRD_BIND_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        if (partnerCode.equals("QQ")) {
                            mTv_QQ.setText("未绑定");
                            qqFlag = false;
                        } else if (partnerCode.equals("WECHAT")) {
                            mTv_wexin.setText("未绑定");
                            wxFlag = false;
                        }
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
            openId = data.get("uid");
            nickName = data.get("name");
            if (TextUtils.isEmpty(nickName)) {
                nickName = "已绑定";
            }
            Bundle params = new Bundle();
            params.putString("token", LotteryApp.token);
            params.putString("partnerCode", partnerCode);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            params.putString("openId", openId);
            // params.putString("phone", userInfo.getData().getPhone());
            params.putString("nickName", nickName);
            mHttpInterface.post(Url.THIRD_BIND_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code == 0) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            if (partnerCode.equals("QQ")) {
                                mTv_QQ.setText(nickName);
                                qqFlag = true;
                            } else if (partnerCode.equals("WECHAT")) {
                                mTv_wexin.setText(nickName);
                                wxFlag = true;
                            }
                        } else {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {

        }
    };

    String nickName;
    String openId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            cardFlag = data.getBooleanExtra("cardFlag", false);
            LotteryApp.cardFlag = cardFlag;
            if (cardFlag) {
                mTv_userinfo_card.setText("已绑定");
                userName = data.getStringExtra("name");
                mIdentification = data.getStringExtra("number");
                userInfo.getData().setRealName(userName);
                userInfo.getData().setIdentification(mIdentification);
            } else {
                mTv_userinfo_card.setText("未绑定");
            }
        } else if (resultCode == 300) {
            String nickName = data.getStringExtra("nickName");
            if (nickName != null && nickName.length() != 0) {
                mTv_userinfo_nickname.setText(nickName);
                mTv_userinfo_nickname.setOnClickListener(null);
            }
        } else if (resultCode == 400) {
            //绑定银行卡的返回值
            bankFlag = data.getBooleanExtra("bankFlag", false);
            userInfo.getData().setBankCard(data.getStringExtra("bankCard"));
            userInfo.getData().setBankBranch(data.getStringExtra("bankNameSmall"));
            userInfo.getData().setCardType(data.getStringExtra("cardType"));
            LotteryApp.bankFlag = bankFlag;
            if (bankFlag) {
                mTv_userinfo_bankcard.setText("已绑定");

            } else {
                mTv_userinfo_bankcard.setText("未绑定");
            }
        } else if (resultCode == 500) {
            //绑定手机号
            if (!TextUtils.isEmpty(data.getStringExtra("phone"))) {
                mTv_userinfo_phone.setText(StringUtil.phoneHint(data.getStringExtra("phone")));
                phoneFlag = true;
                LotteryApp.phoneFlag = phoneFlag;
                LotteryApp.phone = data.getStringExtra("phone");
            }
            finish();
        } else if (resultCode == 444) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
            //修改密码
            Toast.makeText(UserInfoActivity.this, "密码修改成功，请重新登录！", Toast.LENGTH_SHORT).show();
        }
        //调用相机和相册以及裁剪后的回调
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKING_PHOTO: // 拍照的结果
                    dealTakePhotoThenZoom();

                    break;
                case REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL://选择图片的结果
                    dealChoosePhotoThenZoom(data);
                    break;
                case REQUEST_CODE_CUT_PHOTO: // 剪裁图片的结果
                    dealZoomPhoto();
                    break;
            }
        }


        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            // Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * 处理拍照并剪裁
     */
    private void dealTakePhotoThenZoom() {
        startPhotoZoom(Uri.fromFile(new File(mPath + ".jpg")), TARGET_HEAD_SIZE);
    }

    /**
     * 处理选择图片并剪裁
     */
    private void dealChoosePhotoThenZoom(Intent data) {
        Uri uri = data.getData();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
            }
            startPhotoZoom(Uri.fromFile(new File(IMAGE_SAVE_PATH)), TARGET_HEAD_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 剪裁图片
     */

    private void startPhotoZoom(Uri uri, int size) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);

            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);
            //   intent.putExtra("return-data", true);
            mUri = Uri.parse("file:///" + IMAGE_SAVE_PATH);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, REQUEST_CODE_CUT_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将头像数据上传到服务器
     */
    private void dealZoomPhoto() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        try {
            if (mUri != null) {
                final Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUri));
                if (bitmap != null) {
                    boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                    if (b) {
                        File file = new File(IMAGE_SAVE_PATH);
                        /*FormFile formFile=new FormFile(file.getName(),file,"document","image*//*");
                        boolean isSuccess= HttpRequestUtil.uploadFile(Url.CHANGE_HEADER_URL,null,formFile);
                        if(isSuccess){
                            Toast.makeText(UserInfoActivity.this, "文件上传成功", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(UserInfoActivity.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                        }*/
                        RequestParams entity = new RequestParams(Url.UPDATE_IMAGE);
                        entity.addBodyParameter("file", file);
                        entity.addBodyParameter("userId",LotteryApp.uid);
                        entity.addBodyParameter("path","avatar");
                        entity.addBodyParameter("token", LotteryApp.token);
                        x.http().post(entity, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                loadingDialogUtils.setDimiss();
                                if (result == null || result.length() == 0) {
                                    Toast.makeText(UserInfoActivity.this, "修改头像失败！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    String code = jsonObject.getString("code");
                                    String msg = jsonObject.getString("msg");
                                    if ("0".equals(code)) {
                                        mIv_userinfo_avatar.setImageBitmap(bitmap);
                                    }
                                    Toast.makeText(UserInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                loadingDialogUtils.setDimiss();
                                //LogUtils.e("错误结果： ", ex.getMessage());
                                Toast.makeText(UserInfoActivity.this, "修改头像失败！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照操作
     */
    private void operTakePhoto() {
        isTakePhoto = true;
        isGetPic = false;
        if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果sdk大于等于23那么提示是否获取调取相机的授权。否则直接请求授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserInfoActivity.this, Manifest.permission.CAMERA))
                showPhotoPerDialog();
            else
                ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO);
        } else takePhoto();
    }

    /**
     * 选择图片操作
     */
    private void operChoosePic() {
        isTakePhoto = false;
        isGetPic = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showFilePerDialog();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_FILE);
        } else getPictureFromLocal();
    }

    /**
     * 拍照权限提示
     */
    private void showPhotoPerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("需要获取访问您的相机权限，以确保您可以正常拍照。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO);
                    }
                }).create().show();
    }

    /**
     * 文件权限提示
     */
    private void showFilePerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("PhotoDemo需要获取存储文件权限，以确保可以正常保存拍摄或选取的图片。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSIONS_REQUEST_FILE);
                    }
                }).create().show();
    }

    /**
     * 权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_PHOTO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isTakePhoto) takePhoto();
                    if (isGetPic) getPictureFromLocal();
                }
            }
            case PERMISSIONS_REQUEST_FILE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dealZoomPhoto();
                break;
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        String mUUID = UUID.randomUUID().toString();
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        mPath = SDCardUtils.getStorageDirectory() + mUUID;
        File file = new File(mPath + ".jpg");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_CODE_TAKING_PHOTO);
    }

    /**
     * 从本地选择图片
     */
    private void getPictureFromLocal() {
        Intent innerIntent =
                new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL);
    }



    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


}
