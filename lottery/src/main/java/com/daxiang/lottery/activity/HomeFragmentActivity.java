package com.daxiang.lottery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.NetWorkStateReceiver;
import com.daxiang.lottery.R;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.constant.Number;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.KeyBardHD;
import com.daxiang.lottery.entity.LoginResultData;
import com.daxiang.lottery.forum.fragment.ForumFragment;
import com.daxiang.lottery.fragment.HomeFragment;
import com.daxiang.lottery.fragment.LotteryFragment;
import com.daxiang.lottery.fragment.RecommendFragment;
import com.daxiang.lottery.fragment.ScoreFragment;
import com.daxiang.lottery.utils.DataCleanManager;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.VersionInfo;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.json.JSONException;
import org.json.JSONObject;

import static com.daxiang.lottery.LotteryApp.isLogin;

public class HomeFragmentActivity extends AppCompatActivity {
    private ImageView mRbtnHome;
    private ImageView mRbtnRecommend;
    private ImageView mRbtnScore;
    private ImageView mRbtnFaxian;
    private ImageView mRbtnLottery;
    private LinearLayout mFooter;
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private ScoreFragment mScoreFragment;
    private RecommendFragment mRecommendFragment;
    private ForumFragment mForumFragment;
    private LotteryFragment mLotteryFragment;
    private NetWorkData mNetWorkData;
    private int buyPrivilege;
    private int saleFlag;


    //  private ViewPager mViewPager;
    // private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);
        PushAgent.getInstance(this).onAppStart();
        buyPrivilege = SpUtils.getBuyPrivilege();
        saleFlag = SpUtils.getInt(SpUtils.SALE_FLAG);
        initState();
        //检查版本信息
        updaterVersion();
        // PushAgent.getInstance(this).onAppStart();
        //初始化控件
        initView();
        //初始化Fragment
        initFragment();
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mNetWorkData = new NetWorkData(this);
        mNetWorkData.launchImage();
        if (isLogin) {
            mNetWorkData.winningHint();//中奖提示
        }
        //注册一个动态广播
        registerBoradcastReceiver();
        //获取html的域名
        webHtml();//获取html页面域名，获取跟单限制的金额
        //addListener();
        //接受广播，判断屏幕是亮还是暗
        ScreenState();
        //友盟，添加标签
        mNetWorkData.addDeleteTag();

        Intent intent = new Intent();
        intent.setAction("finish");
        intent.putExtra("type", ConstantNum.FINISH);
        sendBroadcast(intent);

    }

    private void webHtml() {
        HttpInterface2 mHttpUtils = new HttpUtils2(this);
        mHttpUtils.get(Url.HTML_HEADER, new Bundle(), new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        String data = jsonObject.getString("data");
                        SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("html_header", data);
                        edit.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError() {

            }
        });
        mHttpUtils.get(Url.GENDAN_MONEY, new Bundle(), new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int maxMulti = data.getInt("maxMulti");//最大投注倍数
                        int maxBetMoney = data.getInt("maxBetMoney");//最大投注金额
                        int minTJMoney = data.getInt("minTJMoney");//最小推荐金额
                        String timeForPay = data.getString("timeForPay");//生成订单之后限制时间
                        String tjMoneyMsg = data.getString("TJMoneyMsg");//最小推荐金额说明
                        String leShan = data.getString("leShan");//是否是乐善玩法
                        SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putInt("maxMulti", maxMulti);
                        edit.putInt("minTJMoney", minTJMoney);
                        edit.putInt("maxBetMoney", maxBetMoney);
                        edit.putString("tjMoneyMsg", tjMoneyMsg);
                        edit.putString("timeForPay", timeForPay);
                        edit.putString("leShan", leShan);
                        edit.commit();
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
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
    }

    private String apkUrl;

    private void updaterVersion() {
        final int appVersionCode = VersionInfo.getAppVersionCode(this);
        HttpInterface2 mHttp = new HttpUtils2(this);
        Bundle bundle = new Bundle();
        bundle.putString("channelId", Number.CHANNELID);
        mHttp.get(Url.VERSION_URL, bundle, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        final JSONObject data = jsonObject.getJSONObject("data");
                        //线上的版本号
                        int verCode = data.getInt("verCode");
                        String updateLevel = data.getString("updateLevel");

                        if (appVersionCode < verCode) {
                            //如果版本本地的版本号和发布的版本号不同，那么就提示是否更新
                            HintDialogUtils.setHintDialog(HomeFragmentActivity.this);
                            HintDialogUtils.setTitleVisiable(true);
                            HintDialogUtils.setTitle("发现新版本 " + data.getString("verName"));
                            HintDialogUtils.setVersionMessage("【更新说明】\n" + data.getString("updateInfo"));
                           /* HintDialogUtils.setCancelable(false);
                            HintDialogUtils.setVisibilityCancel();*/
                            HintDialogUtils.setConfirm("立即更新", new DialogHintInterface() {

                                @Override
                                public void callBack(View view) {
                                    try {
                                        apkUrl = data.getString("dlUrl");
                                        checkSDPermission();
                                        // updateApk();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            if ("1".equals(updateLevel)) {
                                //等于1的时候需要强制更新
                                HintDialogUtils.setCancelable(false);
                                HintDialogUtils.setVisibilityCancel();
                            } else {
                                HintDialogUtils.setTvCancel("以后再说");
                            }
                        }
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

    private int PERMISSIONS_CODE = 100;

    private void checkSDPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showPhotoPerDialog();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
        } else {
            //有读写sd卡的权限
            updateApk();
        }

    }

    private void showPhotoPerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("需要获取访问您的存储权限，以确保您可以正常拍照。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(HomeFragmentActivity.this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
                    }
                }).create().show();
    }

    private void updateApk() {
        HttpInterface2 mHttp = new HttpUtils2(this);
        mHttp.updateFile(apkUrl, this, new JsonInterface() {
            @Override
            public void callback(String result) {

            }

            @Override
            public void onError() {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        //授权成功后的逻辑
                        updateApk();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
                    }
                }
            }
        }
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mLotteryFragment = new LotteryFragment();
        mHomeFragment.setListener(KeyBardHDListener);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl, mHomeFragment).add(R.id.fl, mLotteryFragment).show(mHomeFragment).hide(mLotteryFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        mRbtnHome = (ImageView) findViewById(R.id.rbtn_home);
        mRbtnScore = (ImageView) findViewById(R.id.rbtn_score);
        mRbtnRecommend = (ImageView) findViewById(R.id.rbtn_recommend);
        mRbtnFaxian = (ImageView) findViewById(R.id.rbtn_faxian);
        mRbtnLottery = (ImageView) findViewById(R.id.rbtn_lottery);
        mFooter = (LinearLayout) findViewById(R.id.footer);
        mRbtnHome.setOnClickListener(clickListener);
        mRbtnScore.setOnClickListener(clickListener);
        mRbtnRecommend.setOnClickListener(clickListener);
        mRbtnFaxian.setOnClickListener(clickListener);
        mRbtnLottery.setOnClickListener(clickListener);

        if (buyPrivilege == 0 && saleFlag == 0) {
            mRbtnRecommend.setVisibility(View.VISIBLE);
        } else {
            mRbtnRecommend.setVisibility(View.GONE);
        }
    }

    KeyBardHD KeyBardHDListener = new KeyBardHD() {
        @Override
        public void hint() {
            mFooter.setVisibility(View.GONE);
        }

        @Override
        public void display() {
            mFooter.setVisibility(View.VISIBLE);
        }
    };
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            switch (v.getId()) {
                case R.id.rbtn_home:
                    setHome(fragmentTransaction);
                    break;
                case R.id.rbtn_score:
                    setScore(fragmentTransaction);
                    break;
                case R.id.rbtn_recommend:
                    setRecommend(fragmentTransaction);
                    break;
                case R.id.rbtn_faxian:
                    setFaxian(fragmentTransaction);
                    break;
                case R.id.rbtn_lottery:
                    setLottery(fragmentTransaction);
                    break;
            }
            fragmentTransaction.commit();
            Log.e("时间提交之后", System.currentTimeMillis() + "");
        }
    };

    private void setHome(FragmentTransaction fragmentTransaction) {
        mRbtnHome.setImageResource(R.mipmap.tab_home_true);
        mRbtnScore.setImageResource(R.mipmap.tab_score_false);
        mRbtnRecommend.setImageResource(R.mipmap.tab_recommend_false);
        mRbtnFaxian.setImageResource(R.mipmap.tab_wonderful_false);
        mRbtnLottery.setImageResource(R.mipmap.tab_lottery_false);
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            mHomeFragment.setListener(KeyBardHDListener);
            fragmentTransaction.add(R.id.fl, mHomeFragment);
        }
        if (mScoreFragment != null) {
            fragmentTransaction.hide(mScoreFragment);
        }
        if (mRecommendFragment != null) {
            fragmentTransaction.hide(mRecommendFragment);
        }
        if (mForumFragment != null) {
            fragmentTransaction.hide(mForumFragment);
        }
        if (mLotteryFragment != null) {
            fragmentTransaction.hide(mLotteryFragment);
        }
        fragmentTransaction.show(mHomeFragment);
    }

    private void setScore(FragmentTransaction fragmentTransaction) {
        mRbtnHome.setImageResource(R.mipmap.tab_home_false);
        mRbtnScore.setImageResource(R.mipmap.tab_score_true);
        mRbtnRecommend.setImageResource(R.mipmap.tab_recommend_false);
        mRbtnFaxian.setImageResource(R.mipmap.tab_wonderful_false);
        mRbtnLottery.setImageResource(R.mipmap.tab_lottery_false);
        if (mScoreFragment == null) {
            mScoreFragment = new ScoreFragment();
            fragmentTransaction.add(R.id.fl, mScoreFragment);
        }
        fragmentTransaction.show(mScoreFragment);
        Log.e("时间显示之后", System.currentTimeMillis() + "");
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mRecommendFragment != null) {
            fragmentTransaction.hide(mRecommendFragment);
        }
        if (mForumFragment != null) {
            fragmentTransaction.hide(mForumFragment);
        }
        if (mLotteryFragment != null) {
            fragmentTransaction.hide(mLotteryFragment);
        }
    }

    private void setRecommend(FragmentTransaction fragmentTransaction) {
        mRbtnHome.setImageResource(R.mipmap.tab_home_false);
        mRbtnScore.setImageResource(R.mipmap.tab_score_false);
        mRbtnRecommend.setImageResource(R.mipmap.tab_recommend_true);
        mRbtnFaxian.setImageResource(R.mipmap.tab_wonderful_false);
        mRbtnLottery.setImageResource(R.mipmap.tab_lottery_false);
        if (mRecommendFragment == null) {
            mRecommendFragment = new RecommendFragment();
            mRecommendFragment.setListener(KeyBardHDListener);
            fragmentTransaction.add(R.id.fl, mRecommendFragment);
        }
        fragmentTransaction.show(mRecommendFragment);
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mScoreFragment != null) {
            fragmentTransaction.hide(mScoreFragment);
        }
        if (mForumFragment != null) {
            fragmentTransaction.hide(mForumFragment);
        }
        if (mLotteryFragment != null) {
            fragmentTransaction.hide(mLotteryFragment);
        }

    }

    private void setFaxian(FragmentTransaction fragmentTransaction) {
        mRbtnHome.setImageResource(R.mipmap.tab_home_false);
        mRbtnScore.setImageResource(R.mipmap.tab_score_false);
        mRbtnRecommend.setImageResource(R.mipmap.tab_recommend_false);
        mRbtnFaxian.setImageResource(R.mipmap.tab_wonderful_true);
        mRbtnLottery.setImageResource(R.mipmap.tab_lottery_false);
        if (mForumFragment == null) {
            mForumFragment = new ForumFragment();
            fragmentTransaction.add(R.id.fl, mForumFragment);
        } else {
            mForumFragment.refreshData();//每次点击论坛都刷新界面
        }
        fragmentTransaction.show(mForumFragment);
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mScoreFragment != null) {
            fragmentTransaction.hide(mScoreFragment);
        }
        if (mRecommendFragment != null) {
            fragmentTransaction.hide(mRecommendFragment);
        }
        if (mLotteryFragment != null) {
            fragmentTransaction.hide(mLotteryFragment);
        }

    }

    private void setLottery(FragmentTransaction fragmentTransaction) {
        if (!LotteryApp.isLogin) {
            Intent intent = new Intent(HomeFragmentActivity.this, LoginActivity.class);
            startActivityForResult(intent, 10);
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_in_from_bottom);
        } else {
            mRbtnHome.setImageResource(R.mipmap.tab_home_false);
            mRbtnScore.setImageResource(R.mipmap.tab_score_false);
            mRbtnRecommend.setImageResource(R.mipmap.tab_recommend_false);
            mRbtnFaxian.setImageResource(R.mipmap.tab_wonderful_false);
            mRbtnLottery.setImageResource(R.mipmap.tab_lottery_true);
            if (mLotteryFragment == null) {
                mLotteryFragment = new LotteryFragment();
                fragmentTransaction.add(R.id.fl, mLotteryFragment);
            }
            fragmentTransaction.show(mLotteryFragment);
            if (mHomeFragment != null) {
                fragmentTransaction.hide(mHomeFragment);
            }
            if (mScoreFragment != null) {
                fragmentTransaction.hide(mScoreFragment);
            }
            if (mRecommendFragment != null) {
                fragmentTransaction.hide(mRecommendFragment);
            }
            if (mForumFragment != null) {
                fragmentTransaction.hide(mForumFragment);
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (requestCode == 1111 && resultCode == 2222) {
            mHomeFragment.setLotteryChange();
        } else if (requestCode == 10 && resultCode == 20) {
            setLottery(fragmentTransaction);
            if (mLotteryFragment != null) mLotteryFragment.setData();
        } else if (requestCode == 10 && resultCode == 30) {//登录完成之后
            setHome(fragmentTransaction);
            mNetWorkData.addDeleteTag();
        } else if (requestCode == 2345 && resultCode == 40) {
            setFaxian(fragmentTransaction);
            mForumFragment.refreshData();
        } else if (resultCode == 40) {
            setLottery(fragmentTransaction);
            mNetWorkData.addDeleteTag();
            if (mLotteryFragment != null) mLotteryFragment.setData();
        } else if (resultCode == 50) {
            //退出登录
            setHome(fragmentTransaction);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
          /*  HintDialogUtils hintDialogUtils = new HintDialogUtils();
            hintDialogUtils.setHintDialog(this);
            hintDialogUtils.setTitle("退出软件");
            hintDialogUtils.setTitleVisiable(true);
            hintDialogUtils.setMessage("确认退出彩象彩票？");
            hintDialogUtils.setConfirm("确定", new DialogHintInterface() {
                @Override
                public void callBack(View view) {
                   *//* DataCleanManager.clearAllCache(HomeFragmentActivity.this);
                    finish();
                    System.exit(0);*//*
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            });*/
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBatInfoReceiver != null)
            unregisterReceiver(mBatInfoReceiver);
        if (br != null)
            unregisterReceiver(br);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            Toast.makeText(HomeFragmentActivity.this, "再按一次离开彩象彩票", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
            if (mRecommendFragment != null)
                mRecommendFragment.hiddenKey();
            mHomeFragment.hiddenKey();
        } else {
            DataCleanManager.clearAllCache(HomeFragmentActivity.this);
            finish();
            System.exit(0);
           /* DataCleanManager.clearAllCache(this);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);*/
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        if (mScoreFragment != null) mScoreFragment.setStart();
        Log.e("HomeFragmentActivity", "onRestart");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private NetWorkStateReceiver netWorkStateReceiver;

    @Override
    protected void onResume() {
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        super.onResume();
        MobclickAgent.onResume(this);
        Log.e("HomeFragmentActivity", "onResume");
    }

    @Override
    protected void onPause() {
        unregisterReceiver(netWorkStateReceiver);
        super.onPause();
        MobclickAgent.onPause(this);
        if (mScoreFragment != null) mScoreFragment.setPause();
        Log.e("HomeFragmentActivity", "onPause");
    }

    private void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantNum.HOME_ACTIVITY);
        registerReceiver(br, intentFilter);
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConstantNum.HOME_ACTIVITY)) {
                int type = intent.getIntExtra("type", 0);
                switch (type) {
                    case ConstantNum.LOGIN_SUCESS://登录成功
                        mNetWorkData.addDeleteTag();//登录成功后把上传自己在友盟的tag
                        new NetWorkData(HomeFragmentActivity.this).winningHint();//中奖信息提醒
                        break;
                    case ConstantNum.PAYMENT_SUCESS://支付完成
                        if (mLotteryFragment != null) mLotteryFragment.billData();//刷新钱包数据
                        break;
                }
            }
        }
    };
    private BroadcastReceiver mBatInfoReceiver;

    private void ScreenState() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                //Log.d("onReceive:", "onReceive");
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.e("屏幕打开", "screen on");
                    /*
                    * 如果是登录状态
                    * 判断token值是否过期，
                    *  如果过期了那么就重新登录一次，
                    *   否则不执行任何操作
                    * */
                    token();
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    //Log.e("屏幕关闭", "screen off");
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    //Log.e("屏幕解锁", "screen unlock");
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    // Log.e("最后一个", " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                }
            }
        };
        //Log.e("registerReceiver", "registerReceiver");
        registerReceiver(mBatInfoReceiver, filter);
    }

    private void token() {
        if (LotteryApp.isLogin) {
            //如果是已经登录的状态。
            HttpInterface2 mHttp = new HttpUtils2(this);
            Bundle params = new Bundle();
            params.putString("timeStamp", System.currentTimeMillis() + "");
            params.putString("token", LotteryApp.token);
            mHttp.post(Url.ISTOKENVALID_URL, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == Number.TOKEN_YES) {
                            //token过期，进行登录
                            login();
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

    private void login() {
        SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        HttpInterface2 mHttp = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("userName", username);
        params.putString("password", StringUtil.getMD5(password));
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.LOGIN_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                LoginResultData loginResultData = gson.fromJson(result, LoginResultData.class);
                if (loginResultData.getCode() == 0) {
                    LotteryApp.token = loginResultData.getData().getToken();
                    LotteryApp.uid = loginResultData.getData().getUserId();
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
