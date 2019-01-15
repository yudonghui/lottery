package com.daxiang.lottery.fragment.ballfragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.H5Activity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.activity.lotteryactivity.ServiceAgreementActivity;
import com.daxiang.lottery.adapter.BallKsAdapter;
import com.daxiang.lottery.adapter.balladapter.BallLvAdapter;
import com.daxiang.lottery.adapter.balladapter.LotteryResultAdapter;
import com.daxiang.lottery.adapter.balladapter.SelectBallAdapter;
import com.daxiang.lottery.common.ComputeZhuShu;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.common.DiceThread2;
import com.daxiang.lottery.common.DiceThread3;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.RandomNumber;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.ClearBallInterface;
import com.daxiang.lottery.cxinterface.ContinueInterface;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.OnClickBallListener;
import com.daxiang.lottery.dialog.LoadingDialog;
import com.daxiang.lottery.entity.AwardResultData;
import com.daxiang.lottery.entity.EndDateData;
import com.daxiang.lottery.entity.OmitData;
import com.daxiang.lottery.entity.ServerDateData;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.MathUtils;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.ToastUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.FloatViewLayout;
import com.daxiang.lottery.view.GendanView;
import com.daxiang.lottery.view.HemaiView;
import com.daxiang.lottery.view.MyScrollView;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TimeTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class HallFragment extends Fragment implements View.OnLayoutChangeListener {
    private View mInflate;
    private View inflate;
    private String lotcode;
    private TextView mTextRemark;//如果中奖的奖金
    private LinearLayout mClear;
    private TextView mTextEnsure;
    private TextView mGroup;
    // private LinearLayout mLinearLayout;
    private ListView mListViewContent;
    private LinearLayout mLl_dxds;
    private TextView mBig;
    private TextView mSmall;
    private TextView mSingle;
    private TextView mDoubles;
    private TextView mFastNum;
    private LinearLayout mSssss;
    private ImageView mLesan_introduce;
    private FloatViewLayout mFvl;
    private TextView mTv_betCounts;
    private TextView mTv_money;
    private TextView mIssue;
    private TimeTextView mTextDate;
    private EditText mMulti;
    private EditText mBuyIssue;
    private TextView mSelectBallMin;
    private TextView mTextEndDate;
    private Button mBuyBtn;
    private ImageView mHemai;
    private Button mGendan;
    private LinearLayout mLlZhuijia;
    private ImageView mIvZhuijia;
    //下面这两个是为了弹出软件盘时控制他们的显隐
    private LinearLayout mLl_buy_bttomview;
    private LinearLayout mLl_mutile;
    private LinearLayout mLl_stop;
    private ImageView mImgStop;
    private EditText mMoneyStop;//中奖多少钱之后停止追号
    private TextView mMutile10;
    private TextView mMutile20;
    private TextView mMutile50;
    private TextView mMutile100;
    private MyScrollView mScrollView;
    private FrameLayout mFl_saizi;
    private ImageView mImageDice;
    private ImageView mImageDice2;
    private ImageView mImageDice3;
    private ListView mResultLV;
    private FrameLayout frameLayout;
    //上面阴影部分，点击让dialog消失
    private View view;
    //摇一摇
    private LinearLayout mShake;
    private Vibrator mVibrator;
    private EndDateData endDateData;
    private LinearLayout mLlAddBet;
    private LinearLayout mLlAddBetAuto;
    private LinearLayout mLlClearBet;
    private LinearLayout mLl_xieyi;
    private NoDataView mNoData;
    int lvNum;
    int gvNum;
    int minNum = 1;
    int minBlueNum;
    int sumGroups;
    int gvBlueNum;
    private float lastX;
    private float lastY;
    private float lastZ;
    private long lastShake = 0;
    private long lastUpdate = 0;
    private SensorManager mSensorManager;
    private SensorListener mSensorListener;
    private AlertDialog buyViewDialog;
    String playMethod = "1:1";
    private int m = 1;
    private int buyIssueNum = 1;//购买多少期
    private boolean isStop = false;//是否停止追号。false不停止一直卖完。true 根据中奖钱数确定什么时候停止追号
    private int isZuiJia = 0;
    private int isZuiJiaMoney = 2;
    private ListView mListView;
    private GridView mGridView;
    private BallLvAdapter mBallLvAdapter;
    private BallKsAdapter mBallKsAdapter;
    private Map<Integer, List<String>> mapBall = new HashMap<>();
    private List<Integer> redBallLengthList = new ArrayList<>();
    private List<StringBuilder> ballStrList = new ArrayList<>();
    private List<StringBuilder> buyBallStrList = new ArrayList<>();
    private Map<Integer, List<Integer>> randomMap = new HashMap<>();
    private Map<Integer, List<Integer>> randomMapBall = new HashMap<>();
    //用于拼接球，满足上传的格式
    //StringBuilder strBuilder = new StringBuilder();
    List<String> mListGroup = new ArrayList<>();
    private SelectBallAdapter mSelectBallAdapter8;
    private long sumGroup;
    private LoadingDialog mLoadingDialog;
    //摇一注声音
    private SoundPool mSndPool;
    private HashMap<Integer, Integer> mSoundPoolMap = new HashMap<Integer, Integer>();
    //遗漏模型
    //OmitData.DataBean data;
    String omitResult;
    private LinearLayout mLlOmit;
    private TextView mTextOmit;
    private ImageView mImageOmit;
    //是否为胆拖玩法 true 胆拖，false普通
    private boolean isDan;
    private String whoFocus;//1 购买的倍数获取焦点。2 追号的期数获取焦点  3追号截止的中奖金额获取焦点
    private MathUtils mMathUtils;//计算注数的工具类
    HashMap<Integer, HashMap<Integer, TextView>> mViewMap = new HashMap<>();
    private int buyPrivilege;
    private int dltState;
    private int pl3State;
    private int pl5State;
    private int qxcState;
    private int qlcState;
    private int ssqState;
    private int fc3dState;
    private int sd11x5State;
    private int k3State;
    private int sscState;

    /*
    * isVibrator=true 说明所在的界面是第一个，那么就需要响应震动效果。
    *  false 说明是合买，推荐或者开奖结果。这个时候不能响应震动。
    * */
    public void setVibrator(boolean isVibrator) {
        if (mSensorManager != null) {
            if (isVibrator) {
                mSensorManager.registerListener(mSensorListener,
                        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                mSensorManager.unregisterListener(mSensorListener);
            }
        }
    }

    public void setData(String lotcode) {
        this.lotcode = lotcode;
    }

    public void setDataDan(String playMethod, boolean isDan, int mType) {
        this.playMethod = playMethod;
        this.isDan = isDan;
        this.mType = mType;
        if (isDan) {
            mShake.setVisibility(View.GONE);
            mSensorManager.unregisterListener(mSensorListener);
        } else {
            mShake.setVisibility(View.VISIBLE);
            mSensorManager.registerListener(mSensorListener,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if ("23529".equals(lotcode)) {
            if (isDan) {
                // mSelectBallMin.setText("前区至少选择1个胆码5个拖码，后区至少选择2个拖码");
                minNum = 5;
                minBlueNum = 2;
                lvNum = 4;
                gvNum = 35;
                gvBlueNum = 12;
            } else {
                // mSelectBallMin.setText("至少选择5个红球2个蓝球");
                minNum = 5;
                minBlueNum = 2;
                lvNum = 2;
                gvNum = 35;
                gvBlueNum = 12;
            }
        } else if ("51".equals(lotcode)) {
            if (isDan) {
                //  mSelectBallMin.setText("红球至少选择1个胆码6个拖码，至少选择1个蓝球");
                lvNum = 3;
                gvNum = 33;
                gvBlueNum = 16;
                minNum = 6;
                minBlueNum = 1;
            } else {
                // mSelectBallMin.setText("至少选择6个红球1个蓝球");
                lvNum = 2;
                gvNum = 33;
                gvBlueNum = 16;
                minNum = 6;
                minBlueNum = 1;
            }
        } else {
            //七乐彩
            if (isDan) {
                // mSelectBallMin.setText("至少选择1个胆码7个拖码");
                lvNum = 2;
                gvNum = 30;
                minNum = 7;
            } else {
                //mSelectBallMin.setText("请至少选择7个红球");
                lvNum = 1;
                gvNum = 30;
                minNum = 7;
            }
        }
        if (mSelectBallMin != null)
            mSelectBallMin.setText(StringUtil.getSpannableString(mContext, lotcode, playMethod, minNum));
        clearCollection();
        omitData();

    }

    public void setData(String lotcode, HashMap<Integer, List<Integer>> randomMap) {
        this.lotcode = lotcode;
        this.randomMap = randomMap;
    }

    private String mBuycontent;
    private ContinueInterface mContinueCallback;//回调给activity目的是选择的玩法不同对应的列表不同

    public void setData(String lotcode, String mBuycontent, ContinueInterface mContinueCallback) {
        this.lotcode = lotcode;
        this.mBuycontent = mBuycontent;
        this.mContinueCallback = mContinueCallback;
        if (mBuycontent.contains("$")) isDan = true;
    }

    private int mType;//每种玩儿法自己给定一个标记

    public void setData(int lvNum, int gvNum, int minNum, String playMethod, int mType) {
        this.lvNum = lvNum;
        this.gvNum = gvNum;
        this.minNum = minNum;
        this.mType = mType;
        this.playMethod = playMethod;
        mTextRemark.setVisibility(View.GONE);
        clearCollection();
        if ("36".equals(lotcode) && "1".equals(playMethod)) {
            mFastNum.setVisibility(View.VISIBLE);
            mLl_dxds.setVisibility(View.VISIBLE);
        } else {
            mFastNum.setVisibility(View.GONE);
            mLl_dxds.setVisibility(View.GONE);
        }
        mSelectBallMin.setText(StringUtil.getSpannableString(mContext, lotcode, playMethod, minNum));
        omitData();
    }

    private Context mContext;
    int dp60 = DisplayUtil.dip2px(60);
    int dp50 = DisplayUtil.dip2px(50);
    int dp40 = DisplayUtil.dip2px(40);
    private int minTJMoney;//最小发单金额
    private String tjMoneyMsg;//最小发单金额说明

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        mMathUtils = new MathUtils();
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_hall, null);
            SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            minTJMoney = sp.getInt("minTJMoney", 0);
            tjMoneyMsg = sp.getString("tjMoneyMsg", "");
            dltState = sp.getInt(LotCode.DLT_CODE, 1);
            pl3State = sp.getInt(LotCode.PL3_CODE, 1);
            pl5State = sp.getInt(LotCode.PL5_CODE, 1);
            qxcState = sp.getInt(LotCode.QXC_CODE, 1);
            qlcState = sp.getInt(LotCode.QLC_CODE, 1);
            ssqState = sp.getInt(LotCode.SSQ_CODE, 1);
            fc3dState = sp.getInt(LotCode.FC3D_CODE, 1);
            sd11x5State = sp.getInt(LotCode.SD11X5_CODE, 1);
            k3State = sp.getInt(LotCode.K3_CODE, 1);
            sscState = sp.getInt(LotCode.SSC_CODE, 1);
            buyPrivilege = SpUtils.getBuyPrivilege();
            initView();
            //开奖结果数据
            resultData();
            //遗漏数据请求
            omitData();
            setEndDate();
        }
        mVibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
        checkedLottery();
        adapter();
        addListener();
        //从继续购彩跳转
        if (!TextUtils.isEmpty(mBuycontent)) {
            mContinueCallback.callBack();
            String[] split = mBuycontent.split("\\;");
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split("\\:");//将玩法分割出来
                playMethod = split1[1] + ":" + split1[2];
                if ("23529".equals(lotcode) || "51".equals(lotcode) || "23528".equals(lotcode)) {
                    String[] split2 = split1[0].split("\\$|\\|");
                    for (int j = 0; j < split2.length; j++) {
                        String[] split3 = split2[j].split("\\,");//每一行选中的号码分割出来
                        ArrayList<String> strings = new ArrayList<>();
                        for (int k = 0; k < split3.length; k++) {
                            strings.add(split3[k]);
                        }
                        mapBall.put(j, strings);
                    }
                } else if ("33".equals(lotcode) || "35".equals(lotcode) || "52".equals(lotcode) || "10022".equals(lotcode)) {
                    if ("1:1".equals(playMethod)) {//直选玩法。每一行选中的 数字没有任何分割。组选只有一行。
                        String[] split2 = split1[0].split("\\,");
                        for (int j = 0; j < split2.length; j++) {
                            ArrayList<String> strings = new ArrayList<>();
                            for (int k = 0; k < split2[j].length(); k++) {
                                char c = split2[j].charAt(k);
                                strings.add(String.valueOf(c));
                            }
                            mapBall.put(j, strings);
                        }
                    } else {//只有一行。
                        String[] split2 = split1[0].split("\\,");
                        ArrayList<String> strings = new ArrayList<>();
                        for (int j = 0; j < split2.length; j++) {
                            strings.add(split2[j]);
                        }
                        mapBall.put(0, strings);
                    }
                } else if ("21406".equals(lotcode)) {
                    String[] split2 = split1[0].split("\\,");
                    ArrayList<String> strings = new ArrayList<>();
                    for (int j = 0; j < split2.length; j++) {
                        strings.add(split2[j]);
                    }
                    mapBall.put(0, strings);
                }
                jisuanzushu();//计算注数
                if (i == split.length - 1) isShowBuyDialog = true;
                ensure(1);//添加到指定的容器中。
            }
        }
        //从我的彩票跳转过来的情况
        if (randomMap.size() > 0) {
            randomMapBall = randomMap;
            for (int m = 0; m < 2; m++) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < randomMapBall.get(m).size(); i++) {
                    int j = randomMapBall.get(m).get(i);
                    if (j < 10) {
                        list.add("0" + j);
                    } else {
                        list.add("" + j);
                    }
                }
                mapBall.put(m, list);
            }
            omitData();
            setGroup(1);
            sumGroup = 1;
        }
        //加载音效
        loadSound();
        return mInflate;
    }

    private void setGroup(long group) {
        if (group == 0) {
            mGroup.setText("共" + 0 + "注");
        } else {
            String sumMoney = group * 2 + "元";
            String string = "共" + group + "注  共计" + sumMoney;
            SpannableString ss = new SpannableString(string);
            ForegroundColorSpan what = new ForegroundColorSpan(getResources().getColor(R.color.red_txt));
            ss.setSpan(what, string.length() - sumMoney.length(), string.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mGroup.setText(ss);
        }
    }

    private void resultData() {
        String url = Url.AWARD_RESULT + "?lotCode=" + lotcode + "&pageNumber=1&pageSize=10";
        HttpInterface2 mHttp = new HttpUtils2(getActivity());
        mHttp.jsonByUrl(url, new JsonInterface() {

            @Override
            public void callback(String result) {
                Gson gson = new Gson();

                AwardResultData awardResultData = gson.fromJson(result, AwardResultData.class);
                if (awardResultData.getCode() == 0) {
                    List<AwardResultData.DataBean.ItemsBean> items = awardResultData.getData().getItems();
                    LotteryResultAdapter lotteryResultAdapter = new LotteryResultAdapter(items);
                    mResultLV.setAdapter(lotteryResultAdapter);
                } else {
                    Toast.makeText(getContext(), awardResultData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void checkedLottery() {
        switch (lotcode) {
            case "23529":
                minNum = 5;
                minBlueNum = 2;
                lvNum = 2;
                gvNum = 35;
                gvBlueNum = 12;
                //  mSelectBallMin.setText("至少选择5个红球2个蓝球");
                break;
            case "33":
                //从0开始排列三
                lvNum = 3;
                gvNum = 10;
                //   mSelectBallMin.setText("每位至少选1个号");
                break;
            case "50":
                //大小单双
                lvNum = 2;
                gvNum = 4;
                playMethod = "10:1";
                //  mSelectBallMin.setText("每位选择1个属性，猜中开奖号码后2位的大小单双属性即中奖4元");
                break;
            case "35":
                //从0开始
                lvNum = 5;
                gvNum = 10;
                // mSelectBallMin.setText("请选全5位号码");
                break;
            case "10022":
                //七星彩从0开始
                lvNum = 7;
                gvNum = 10;
                // mSelectBallMin.setText("请选全7位号码");
                break;
            case "23528":
                //七乐彩从1开始
                lvNum = 1;
                gvNum = 30;
                minNum = 7;
                // mSelectBallMin.setText("请至少选择7个红球");
                break;
            case "51":
                //双色球从1开始
                lvNum = 2;
                gvNum = 33;
                gvBlueNum = 16;
                minNum = 6;
                minBlueNum = 1;
                // mSelectBallMin.setText("至少选择6个红球1个蓝球");
                break;
            case "52":
                //福彩3D从0开始
                lvNum = 3;
                gvNum = 10;
                //   mSelectBallMin.setText("请选全3位号码");
                break;
            case "36":
                //江西快三
                mFastNum.setVisibility(View.VISIBLE);
                mLl_dxds.setVisibility(View.VISIBLE);
              /*  String hint = "猜开奖号码的和";
                SpannableString ss = new SpannableString(hint);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.deep_txt)), 0, hint.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new RelativeSizeSpan(1.2f), 0, hint.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mSelectBallMin.setText(ss);*/
                playMethod = "1";
                mType = ConstantNum.HE_ZHI;
                break;
            case "21406":
                //从0开始
                lvNum = 1;
                gvNum = 11;
                playMethod = "02:01";
                minNum = 2;
                //  mSelectBallMin.setText("至少选择2个号");
                break;
        }
        mSelectBallMin.setText(StringUtil.getSpannableString(mContext, lotcode, playMethod, minNum));
        setGroup(0);
    }

    HttpInterface2 mHttp = new HttpUtils2(getActivity());

    private void omitData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        mHttp.jsonByUrl(Url.OMIT_URL + "?lotCode=" + lotcode, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                OmitData omitDltData = gson.fromJson(result, OmitData.class);
                if (omitDltData.getCode() == 0) {
                    omitResult = result;
                    adapter();
                } else {
                    adapter();
                    Toast.makeText(getActivity(), omitDltData.getMsg(), Toast.LENGTH_SHORT).show();
                }
                /*if (TextUtils.isEmpty(omitResult)) {
                    mNoData.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                } else {
                    mNoData.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mNoData.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }
        });
    }

    private void adapter() {
        if ("36".equals(lotcode)) {//江西快三
            mBallKsAdapter = new BallKsAdapter(mViewMap, mContext, lotcode, new RedOnClick(), randomMapBall, omitResult, playMethod);
            mBallKsAdapter.setData(isOmit);
            mListView.setAdapter(mBallKsAdapter);
        } else {
            mBallLvAdapter = new BallLvAdapter(lvNum, gvNum, lotcode, new RedOnClick(), randomMapBall, omitResult, playMethod, mContext);
            //mBallLvAdapter.setData(lvNum, gvNum, lotcode, new RedOnClick(), randomMapBall, omitResult, playMethod);
            mBallLvAdapter.setData(isOmit);
            mListView.setAdapter(mBallLvAdapter);
        }
    }

    private void initView() {
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        mLlOmit = (LinearLayout) mInflate.findViewById(R.id.ll_omit);
        mSssss = (LinearLayout) mInflate.findViewById(R.id.sssss);
        mImageOmit = (ImageView) mInflate.findViewById(R.id.image_omit);
        mTextOmit = (TextView) mInflate.findViewById(R.id.text_omit);
        mShake = (LinearLayout) mInflate.findViewById(R.id.image_shake);
        mSelectBallMin = (TextView) mInflate.findViewById(R.id.min_select_ball);
        mIssue = (TextView) mInflate.findViewById(R.id.issue);
        mLesan_introduce = (ImageView) mInflate.findViewById(R.id.lesan_introduce);
        mFvl = (FloatViewLayout) mInflate.findViewById(R.id.floatViewLayout);
        mTextDate = (TimeTextView) mInflate.findViewById(R.id.timeTextView);
        mTextEndDate = (TextView) mInflate.findViewById(R.id.text_lottery_date);
        mGroup = (TextView) mInflate.findViewById(R.id.min_games);
        mListView = (ListView) mInflate.findViewById(R.id.lv_ball);
        mClear = (LinearLayout) mInflate.findViewById(R.id.clear);
        mTextRemark = (TextView) mInflate.findViewById(R.id.remark);
        mTextEnsure = (TextView) mInflate.findViewById(R.id.ensure);
        mLl_dxds = (LinearLayout) mInflate.findViewById(R.id.ll_dxds);
        mBig = (TextView) mInflate.findViewById(R.id.big);
        mSmall = (TextView) mInflate.findViewById(R.id.small);
        mSingle = (TextView) mInflate.findViewById(R.id.single);
        mDoubles = (TextView) mInflate.findViewById(R.id.doubles);
        mFastNum = (TextView) mInflate.findViewById(R.id.fastNum);
        mFl_saizi = (FrameLayout) mInflate.findViewById(R.id.fl_saizi);
        mImageDice = (ImageView) mInflate.findViewById(R.id.imageDice);
        mImageDice2 = (ImageView) mInflate.findViewById(R.id.imageDice2);
        mImageDice3 = (ImageView) mInflate.findViewById(R.id.imageDice3);

        if (buyPrivilege == 0) {
            mSssss.setVisibility(View.VISIBLE);
        } else {
            mSssss.setVisibility(View.GONE);
        }

        //  mLinearLayout = (LinearLayout) mInflate.findViewById(R.id.ll_dialog_window);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //下滑出来的开奖结果
        mResultLV = (ListView) mInflate.findViewById(R.id.result_lv);
        mScrollView = (MyScrollView) mInflate.findViewById(R.id.mylayout);
        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        String leShan = sp.getString("leShan", "false");
        if ("23529".equals(lotcode) && "true".equals(leShan)) {
            mFvl.setVisibility(View.VISIBLE);
        } else mFvl.setVisibility(View.GONE);


        ViewTreeObserver viewTreeObserver = mResultLV.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                int measuredHeight = mResultLV.getMeasuredHeight();
                mScrollView.setData(measuredHeight);
                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {//第一个条目出现
                    View firstVisibleItemView = mListView.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {//第一个条目的顶部
                        mScrollView.setScroll(true);
                    } else mScrollView.setScroll(false);
                } else mScrollView.setScroll(false);
            }
        });
    }

    private void buyInitView() {
        //点击确定后的视图
        inflate = View.inflate(getContext(), R.layout.ball_buyform_dialog, null);
        view = inflate.findViewById(R.id.view);
        final LinearLayout mLl_middle = (LinearLayout) inflate.findViewById(R.id.ll_middle);
        mLlAddBet = (LinearLayout) inflate.findViewById(R.id.ll_add_bet);
        mLlAddBetAuto = (LinearLayout) inflate.findViewById(R.id.ll_add_bet_auto);
        mLlClearBet = (LinearLayout) inflate.findViewById(R.id.ll_clear_bet);
        mMulti = (EditText) inflate.findViewById(R.id.edit_buy_times);
        mBuyIssue = (EditText) inflate.findViewById(R.id.edit_buy_issue);
        mListViewContent = (ListView) inflate.findViewById(R.id.lv_select_bets);
        mTv_betCounts = (TextView) inflate.findViewById(R.id.tv_zhushu);
        mTv_money = (TextView) inflate.findViewById(R.id.tv_money);
        mBuyBtn = (Button) inflate.findViewById(R.id.btn_bet_submit);
        mHemai = (ImageView) inflate.findViewById(R.id.img_hemai);
        mGendan = (Button) inflate.findViewById(R.id.recommend_btn);

       /* if (LotteryApp.recommendPrivilege == 2) {
            mGendan.setVisibility(View.GONE);
        } else {
            mGendan.setVisibility(View.VISIBLE);
        }*/
        mGendan.setVisibility(View.VISIBLE);
       /* if (LotteryApp.buyPrivilege == 2) {
            mBuyBtn.setText("暂停销售");
            mBuyBtn.setEnabled(false);
        } else {
            mBuyBtn.setText("立即预约");
            mBuyBtn.setEnabled(true);
        }*/
        mLl_buy_bttomview = (LinearLayout) inflate.findViewById(R.id.ll_buy_bttomview);
        mLl_mutile = (LinearLayout) inflate.findViewById(R.id.ll_mutile);
        mLl_stop = (LinearLayout) inflate.findViewById(R.id.ll_stop);
        mImgStop = (ImageView) inflate.findViewById(R.id.img_stop);
        mMoneyStop = (EditText) inflate.findViewById(R.id.edit_money);

        mLl_xieyi = (LinearLayout) inflate.findViewById(R.id.ll_xieyi);
        mMutile10 = (TextView) inflate.findViewById(R.id.mutile10);
        mMutile20 = (TextView) inflate.findViewById(R.id.mutile20);
        mMutile50 = (TextView) inflate.findViewById(R.id.mutile50);
        mMutile100 = (TextView) inflate.findViewById(R.id.mutile100);
        mLlZhuijia = (LinearLayout) inflate.findViewById(R.id.rr_zhuijia);
        mIvZhuijia = (ImageView) inflate.findViewById(R.id.img_zhuijia);
        mLl_buy_bttomview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mLl_buy_bttomview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height1 = getResources().getDimensionPixelSize(R.dimen.footer_heigh);
                int height2 = getResources().getDimensionPixelSize(R.dimen.footer_heigh);
                int height3 = height2;
                int height5 = getResources().getDimensionPixelSize(R.dimen.footer_buy);
                int height4 = view.getHeight();
                int displayHeight = DisplayUtil.getDisplayHeight();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        displayHeight - height1 - height2 - height3 - height4 - height5, 1.0f);

                mLl_middle.setLayoutParams(lp);
            }
        });
        if ("23529".equals(lotcode)) {

            /*mLlZhuijia = (LinearLayout) mInflate.findViewById(R.id.rr_zhuijia);
            mIvZhuijia = (ImageView) mInflate.findViewById(R.id.img_zhuijia);*/
            mLlZhuijia.setVisibility(View.VISIBLE);
            mLlZhuijia.setOnClickListener(zhuijiaListener);
        } else {
            mLlZhuijia.setVisibility(View.GONE);
        }
        buyViewDialog = new AlertDialog.Builder(getContext(), R.style.HintDialog).create();
        buyViewDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mSensorManager.registerListener(mSensorListener,
                        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);

                mapBall.clear();
                hashMapView.clear();
                randomMapBall.clear();
                adapter();
                setGroup(0);
            }
        });
        buyAddListener();
    }

    private void buyAddListener() {
        //点击付款按钮
        mBuyBtn.setOnClickListener(new BuyBtnOnClickListener());
        //点击的手动添加
        mLlAddBet.setOnClickListener(new AddOnClickListener());
        //点击自动添加
        mLlAddBetAuto.setOnClickListener(AddBetAutoListener);
        view.setOnClickListener(new AddOnClickListener());
        //点击清空列表
        mLlClearBet.setOnClickListener(new ClearFormOnClickListener());
        //投注倍数的监控
        mMulti.setOnFocusChangeListener(OnFocusListener);//投注的倍数。获取焦点
        mBuyIssue.setOnFocusChangeListener(OnFocusListener);//追号的期数
        mMoneyStop.setOnFocusChangeListener(OnFocusListener);//追号停止的中奖钱数

        mMulti.addTextChangedListener(new EditChangeListener());//投注倍数内容变化
        mBuyIssue.addTextChangedListener(BuyIssueChangeListener);
        mMoneyStop.addTextChangedListener(MoneyStopListener);
        mImgStop.setOnClickListener(IsStopListener);
        //合买
        mHemai.setOnClickListener(HemaiListener);
        //跟单
        mGendan.setOnClickListener(GendanListener);
        //监听软键盘的弹出和隐藏
        inflate.addOnLayoutChangeListener(this);
        mMutile10.setOnClickListener(click);
        mMutile20.setOnClickListener(click);
        mMutile50.setOnClickListener(click);
        mMutile100.setOnClickListener(click);
        mLl_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ServiceAgreementActivity.class));
            }
        });
    }

    private HashMap<String, String> hezhiDxds = new HashMap<>();//和值的大小单双
    View.OnClickListener DxdsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.big) {
                if (hezhiDxds.containsKey("大")) {
                    hezhiDxds.remove("大");
                    mBig.setTextColor(getResources().getColor(R.color.red_txt));
                    mBig.setBackgroundResource(R.drawable.shape_whitebg_gray);
                } else {
                    mBig.setTextColor(getResources().getColor(R.color.white));
                    mBig.setBackgroundResource(R.drawable.shape_btn);
                    mSmall.setTextColor(getResources().getColor(R.color.red_txt));
                    mSmall.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    hezhiDxds.put("大", "大");
                    if (hezhiDxds.containsKey("小")) {
                        hezhiDxds.remove("小");
                    }
                }
            } else if (v.getId() == R.id.small) {
                if (hezhiDxds.containsKey("小")) {
                    hezhiDxds.remove("小");
                    mSmall.setTextColor(getResources().getColor(R.color.red_txt));
                    mSmall.setBackgroundResource(R.drawable.shape_whitebg_gray);
                } else {
                    mBig.setTextColor(getResources().getColor(R.color.red_txt));
                    mBig.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mSmall.setTextColor(getResources().getColor(R.color.white));
                    mSmall.setBackgroundResource(R.drawable.shape_btn);
                    hezhiDxds.put("小", "小");
                    if (hezhiDxds.containsKey("大")) {
                        hezhiDxds.remove("大");
                    }
                }
            } else if (v.getId() == R.id.single) {
                if (hezhiDxds.containsKey("单")) {
                    hezhiDxds.remove("单");
                    mSingle.setTextColor(getResources().getColor(R.color.red_txt));
                    mSingle.setBackgroundResource(R.drawable.shape_whitebg_gray);
                } else {
                    mSingle.setTextColor(getResources().getColor(R.color.white));
                    mSingle.setBackgroundResource(R.drawable.shape_btn);
                    mDoubles.setTextColor(getResources().getColor(R.color.red_txt));
                    mDoubles.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    hezhiDxds.put("单", "单");
                    if (hezhiDxds.containsKey("双")) {
                        hezhiDxds.remove("双");
                    }
                }
            } else {
                if (hezhiDxds.containsKey("双")) {
                    hezhiDxds.remove("双");
                    mDoubles.setTextColor(getResources().getColor(R.color.red_txt));
                    mDoubles.setBackgroundResource(R.drawable.shape_whitebg_gray);
                } else {
                    mSingle.setTextColor(getResources().getColor(R.color.red_txt));
                    mSingle.setBackgroundResource(R.drawable.shape_whitebg_gray);
                    mDoubles.setTextColor(getResources().getColor(R.color.white));
                    mDoubles.setBackgroundResource(R.drawable.shape_btn);
                    hezhiDxds.put("双", "双");
                    if (hezhiDxds.containsKey("单")) {
                        hezhiDxds.remove("单");
                    }
                }
            }
            dxdsNumber();
            adapter();
        }
    };
    private HashMap<String, View> hashMapView = new HashMap<>();//key第几行-号码 value对应的view

    class RedOnClick implements OnClickBallListener {

        @Override
        public void OnClickListener(View view) {

            if ("36".equals(lotcode)) {//快三玩儿法
                int tag = (int) view.getTag();
                LinearLayout mLlBall = (LinearLayout) view.findViewById(R.id.ll_ball);
                TextView mTextNum = (TextView) view.findViewById(R.id.text_ball);
                TextView mRemark = (TextView) view.findViewById(R.id.remark);
                String text = mTextNum.getText().toString();
                if ("5".equals(playMethod)) {
                    if (tag == 0 && mapBall.get(1) != null) {
                        for (int i = 0; i < mapBall.get(1).size(); i++) {
                            if (text.contains(mapBall.get(1).get(i))) {
                                View view1 = hashMapView.get("1-" + mapBall.get(1).get(i));
                                LinearLayout mLlBall1 = (LinearLayout) view1.findViewById(R.id.ll_ball);
                                TextView mTextNum1 = (TextView) view1.findViewById(R.id.text_ball);
                                TextView mRemark1 = (TextView) view1.findViewById(R.id.remark);
                                mBallKsAdapter.unSelect(mLlBall1, mTextNum1, mRemark1);
                                hashMapView.remove("1-" + mapBall.get(1).get(i));
                                mapBall.get(1).remove(i);
                                break;
                            }
                        }
                    }
                    if (tag == 1 && mapBall.get(0) != null) {
                        for (int i = 0; i < mapBall.get(0).size(); i++) {
                            if (mapBall.get(0).get(i).contains(text)) {
                                View view1 = hashMapView.get("0-" + mapBall.get(0).get(i));//
                                LinearLayout mLlBall1 = (LinearLayout) view1.findViewById(R.id.ll_ball);
                                TextView mTextNum1 = (TextView) view1.findViewById(R.id.text_ball);
                                TextView mRemark1 = (TextView) view1.findViewById(R.id.remark);
                                mBallKsAdapter.unSelect(mLlBall1, mTextNum1, mRemark1);
                                hashMapView.remove("0-" + mapBall.get(0).get(i));
                                mapBall.get(0).remove(i);
                                break;
                            }
                        }
                    }
                    if (mapBall.containsKey(tag)) {
                        if (mapBall.get(tag).contains(text)) {
                            mBallKsAdapter.unSelect(mLlBall, mTextNum, mRemark);
                            mapBall.get(tag).remove(text);
                            hashMapView.remove(tag + "-" + text);
                        } else {
                            mBallKsAdapter.select(mLlBall, mTextNum, mRemark);
                            mapBall.get(tag).add(text);
                            hashMapView.put(tag + "-" + text, view);
                        }
                    } else {
                        mBallKsAdapter.select(mLlBall, mTextNum, mRemark);
                        List<String> list = new ArrayList<>();
                        list.add(text);
                        mapBall.put(tag, list);
                        hashMapView.put(tag + "-" + text, view);
                    }
                } else {
                    if (mapBall.containsKey(tag)) {
                        if (mapBall.get(tag).contains(mTextNum.getText().toString())) {
                            mBallKsAdapter.unSelect(mLlBall, mTextNum, mRemark);
                            mapBall.get(tag).remove(mTextNum.getText().toString());
                        } else {
                            mBallKsAdapter.select(mLlBall, mTextNum, mRemark);
                            mapBall.get(tag).add(mTextNum.getText().toString());
                        }
                    } else {
                        mBallKsAdapter.select(mLlBall, mTextNum, mRemark);
                        List<String> list = new ArrayList<>();
                        list.add(mTextNum.getText().toString());
                        mapBall.put(tag, list);
                    }
                }
            } else {
                //Log.e("+++++++++++++", String.valueOf(tag));
                String string = (String) view.getTag();
                if (string == null) return;
                String[] split = string.split("\\-");
                if (string.length() < 2) return;
                int tag = Integer.parseInt(split[0]);
                TextView mBall = (TextView) view;
                //选择的号码
                String text = mBall.getText().toString();
                if ("09:01".equals(playMethod) || "10:01".equals(playMethod)
                        || "135:5".equals(playMethod) || "135:1".equals(playMethod)) {
                    if ("23529".equals(lotcode)) {


                 /*  * 如果点击的是第一行，那么查看第二行里面有没有选择这个号码，
                   * 选择了提示不能重复选。否则倒过来判断
                   **/
                        if (tag == 0 && mapBall.get(1) != null) {
                            for (int i = 0; i < mapBall.get(1).size(); i++) {
                                if (text.equals(mapBall.get(1).get(i))) {
                                    Toast.makeText(getContext(), "不能选择重复的号码", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        if (tag == 1 && mapBall.get(0) != null) {
                            for (int i = 0; i < mapBall.get(0).size(); i++) {
                                if (text.equals(mapBall.get(0).get(i))) {
                                    Toast.makeText(getContext(), "不能选择重复的号码", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        if (tag == 2 && mapBall.get(3) != null) {
                            for (int i = 0; i < mapBall.get(3).size(); i++) {
                                if (text.equals(mapBall.get(3).get(i))) {
                                    Toast.makeText(getContext(), "不能选择重复的号码", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        if (tag == 3 && mapBall.get(2) != null) {
                            for (int i = 0; i < mapBall.get(2).size(); i++) {
                                if (text.equals(mapBall.get(2).get(i))) {
                                    Toast.makeText(getContext(), "不能选择重复的号码", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    } else if ("51".equals(lotcode)) {

                 /*  * 如果点击的是第一行，那么查看第二行里面有没有选择这个号码，
                   * 选择了提示不能重复选。否则倒过来判断
                   **/
                        if (tag == 0 && mapBall.get(1) != null) {
                            for (int i = 0; i < mapBall.get(1).size(); i++) {
                                if (text.equals(mapBall.get(1).get(i))) {
                                    Toast.makeText(getContext(), "不能选择重复的号码", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        if (tag == 1 && mapBall.get(0) != null) {
                            for (int i = 0; i < mapBall.get(0).size(); i++) {
                                if (text.equals(mapBall.get(0).get(i))) {
                                    Toast.makeText(getContext(), "不能选择重复的号码", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    } else {
                        Iterator it = mapBall.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            //这个判断的目的是只判断除自身这一行除外的行中的数字有没有相同的。
                            if ((int) entry.getKey() != tag) {
                                List<String> value = (List<String>) entry.getValue();
                                for (int i = 0; i < value.size(); i++) {
                                    if (text.equals(value.get(i))) {
                                        Toast.makeText(getContext(), "不能选择重复的号码", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    if (mapBall.containsKey(tag)) {
                        if (mapBall.get(tag).contains(text)) {
                            mBallLvAdapter.setUnSelect(mBall);
                            mapBall.get(tag).remove(text);
                        } else {
                            if ("23529".equals(lotcode)) {

                          /*  * 如果点击的是第一行或者第三行，判断已经选择了几个球。
                            * 超过限制的话不让点击。
                            **/
                                if (tag == 0) {
                                    if (mapBall.get(0) != null && mapBall.get(0).size() >= 4) {
                                        Toast.makeText(getContext(), "最多选择4个前区胆码", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                if (tag == 2) {
                                    if (mapBall.get(2) != null && mapBall.get(2).size() >= 1) {
                                        Toast.makeText(getContext(), "最多选择1个后区胆码", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                            if ("51".equals(lotcode)) {
                                if (tag == 0) {
                                    if (mapBall.get(0) != null && mapBall.get(0).size() >= 5) {
                                        Toast.makeText(getContext(), "最多选择5个前区胆码", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                            if ("23528".equals(lotcode)) {
                                if (tag == 0) {
                                    if (mapBall.get(0) != null && mapBall.get(0).size() >= 6) {
                                        Toast.makeText(getContext(), "最多选择6个胆码", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                            mBallLvAdapter.setSelect(mBall);
                            mapBall.get(tag).add(text);
                        }
                    } else {
                        mBallLvAdapter.setSelect(mBall);
                        List<String> list = new ArrayList<>();
                        list.add(text);
                        mapBall.put(tag, list);
                    }

                } else if ("50".equals(lotcode) && "10:1".equals(playMethod)) {//时时彩 大小单双单独处理
                    if (mapBall.get(tag) != null && mapBall.get(tag).size() > 0) {
                        TextView button = (TextView) hashMapView.get(tag + "");
                        if (!text.equals(button.getText().toString())) {
                            mBallLvAdapter.setUnSelect(button);
                            hashMapView.remove(tag);
                            mapBall.get(tag).clear();
                        }
                    }

                    if (mapBall.containsKey(tag)) {
                        if (mapBall.get(tag).contains(text)) {
                            mBallLvAdapter.setUnSelect(mBall);
                            mapBall.get(tag).remove(text);
                            hashMapView.remove(tag + "");
                        } else {
                            mBallLvAdapter.setSelect(mBall);
                            mapBall.get(tag).add(text);
                            hashMapView.put(tag + "", mBall);
                        }
                    } else {
                        mBallLvAdapter.setSelect(mBall);
                        List<String> list = new ArrayList<>();
                        list.add(text);
                        mapBall.put(tag, list);
                        hashMapView.put(tag + "", mBall);
                    }
                } else {
                    if (mapBall.containsKey(tag)) {
                        if (mapBall.get(tag).contains(text)) {
                            mBallLvAdapter.setUnSelect(mBall);
                            mapBall.get(tag).remove(text);
                        } else {
                            mBallLvAdapter.setSelect(mBall);
                            mapBall.get(tag).add(text);
                        }
                    } else {
                        mBallLvAdapter.setSelect(mBall);
                        List<String> list = new ArrayList<>();
                        list.add(text);
                        mapBall.put(tag, list);
                    }
                    // adapter();
                }
            }
            //计算注数
            jisuanzushu();
        }
    }

    private void jisuanzushu() {
        ComputeZhuShu mComputeZhuShu = new ComputeZhuShu();
        sumGroup = mComputeZhuShu.compute(mContext, lotcode, playMethod, mapBall, isDan,
                minNum, minBlueNum, lvNum, mMathUtils, mTextRemark);
        setGroup(sumGroup);
    }

    private void addListener() {
        //是否显示遗漏
        mLlOmit.setOnClickListener(OmitListener);
        //清空所有选中的球
        mClear.setOnClickListener(new ClearOnClickListener());
        //点击确定按钮
        if ((LotCode.DLT_CODE.equals(lotcode) && dltState == 0) ||
                (LotCode.PL3_CODE.equals(lotcode) && pl3State == 0) ||
                (LotCode.PL5_CODE.equals(lotcode) && pl5State == 0) ||
                (LotCode.QXC_CODE.equals(lotcode) && qxcState == 0) ||
                (LotCode.QLC_CODE.equals(lotcode) && qlcState == 0) ||
                (LotCode.SSQ_CODE.equals(lotcode) && ssqState == 0) ||
                (LotCode.FC3D_CODE.equals(lotcode) && fc3dState == 0) ||
                (LotCode.SD11X5_CODE.equals(lotcode) && sd11x5State == 0) ||
                (LotCode.K3_CODE.equals(lotcode) && k3State == 0) ||
                (LotCode.SSC_CODE.equals(lotcode) && sscState == 0)) {
            mTextEnsure.setText("停售");
            mTextEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.gray_deep));
        } else {
            //点击确定按钮
            mTextEnsure.setOnClickListener(new EnsureOnClickListener());
            mTextEnsure.setText("确定");
            mTextEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.red_theme));
        }
        mBig.setOnClickListener(DxdsListener);
        mSmall.setOnClickListener(DxdsListener);
        mSingle.setOnClickListener(DxdsListener);
        mDoubles.setOnClickListener(DxdsListener);
        //点击摇一摇
        mShake.setOnClickListener(new ShakeFormOnClickListener());
        //乐善玩法介绍
        mLesan_introduce.setOnClickListener(LesanListener);
        //乐善
        mFvl.setMove(ForecastMoveListener);
    }

    private boolean isOmit = true;
    View.OnClickListener OmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(omitResult)) {
                return;
            }
            randomMapBall.clear();
            for (Map.Entry<Integer, List<String>> entry : mapBall.entrySet()) {
                Integer key = entry.getKey();
                List<String> value = entry.getValue();
                List<Integer> integers = new ArrayList<>();
                for (int i = 0; i < value.size(); i++) {
                    String s = value.get(i);
                    int i1 = Integer.parseInt(s);
                    integers.add(i1);
                }
                randomMapBall.put(key, integers);
            }
            if (isOmit) {
                mImageOmit.setImageResource(R.drawable.omit_gray);
                mTextOmit.setTextColor(getResources().getColor(R.color.gray_txt));
                isOmit = false;
            } else {
                mImageOmit.setImageResource(R.drawable.omit_red);
                mTextOmit.setTextColor(getResources().getColor(R.color.red_theme));
                isOmit = true;
            }
            adapter();
        }
    };
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mutile10:
                    if ("1".equals(whoFocus))
                        mMulti.setText("20");
                    else if ("2".equals(whoFocus)) mBuyIssue.setText("10");
                    else mMoneyStop.setText("50");
                    break;
                case R.id.mutile20:
                    if ("1".equals(whoFocus))
                        mMulti.setText("50");
                    else if ("2".equals(whoFocus)) mBuyIssue.setText("20");
                    else mMoneyStop.setText("100");
                    break;
                case R.id.mutile50:
                    if ("1".equals(whoFocus))
                        mMulti.setText("100");
                    else if ("2".equals(whoFocus)) mBuyIssue.setText("30");
                    else mMoneyStop.setText("200");
                    break;
                case R.id.mutile100:
                    if ("1".equals(whoFocus))
                        mMulti.setText("200");
                    else if ("2".equals(whoFocus)) mBuyIssue.setText("50");
                    else mMoneyStop.setText("500");
                    break;
            }
        }
    };
    boolean zjFlag = true;
    View.OnClickListener zhuijiaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (zjFlag) {
                isZuiJia = 1;
                isZuiJiaMoney = 3;
                mIvZhuijia.setImageResource(R.drawable.zj_true);
                if (isDan)
                    playMethod = "135:1";
                else playMethod = "2:1";
                zjFlag = false;
            } else {
                selectZuijia();
            }
            m = Integer.parseInt(mMulti.getText().toString());
            mTv_money.setText("共" + sumGroups * isZuiJiaMoney * m * buyIssueNum + "元");
        }
    };

    private void selectZuijia() {
        isZuiJia = 0;
        isZuiJiaMoney = 2;
        mIvZhuijia.setImageResource(R.drawable.zj_false);
        if (isDan) playMethod = "135:5";
        else playMethod = "1:1";
        zjFlag = true;
    }

    View.OnClickListener HemaiListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LotteryApp.isLogin) {
                if ("".equals(mMulti.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    HemaiView mHemaiView = new HemaiView(getContext());
                    //View view = View.inflate(mContext, R.layout.dialog_hemai_layout, null);
                    final AlertDialog hemaiDialog = new AlertDialog.Builder(getContext()).create();
                    mHemaiView.setData(sumGroups, Integer.parseInt(mMulti.getText().toString()), new HemaiView.OnClickComit() {
                        //str1提成，str2认购，str3保底
                        @Override
                        public void onClick(String str1, String str2, String str3, int type) {
                            hemaiDialog.dismiss();
                            if (type == 0) {
                                return;
                            }
                            //提交订单
                            hemaiBuy(str1, str2, str3, type, true);
                        }
                    });
                    hemaiDialog.setView(mHemaiView);
                    hemaiDialog.show();
                }
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        }
    };
    View.OnClickListener GendanListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // inflate.setFocusable(false);
            if (LotteryApp.isLogin) {
                if ("".equals(mMulti.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else if (sumGroups * isZuiJiaMoney * m * buyIssueNum < minTJMoney) {
                    HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(mContext);
                    hintDialogUtils2.setMessage(tjMoneyMsg);
                    hintDialogUtils2.setTitle("温馨提示");
                    hintDialogUtils2.setTitleVisiable(true);
                } else {
                    GendanView mGendanView = new GendanView(getContext());
                    //View view = View.inflate(mContext, R.layout.dialog_hemai_layout, null);
                    final Dialog gendanDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
                    mGendanView.setData(new GendanView.OnclickgdComit() {
                        @Override
                        public void onClickComit(String str1, String str2, int GenDanType) {
                            gendanDialog.dismiss();
                            if (GenDanType == 0) {
                                return;
                            }
                            hemaiBuy(str1, str2, "", GenDanType, false);
                            //提交订单
                        }
                    });
                    gendanDialog.setContentView(mGendanView);
                    gendanDialog.show();
                }
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        }
    };

    class ClearOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clearCollection();
            adapter();
        }
    }

    View.OnFocusChangeListener OnFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v.getId() == R.id.edit_buy_times && hasFocus) {//购买的倍数获取焦点
                whoFocus = "1";
                mMutile10.setText("20倍");
                mMutile20.setText("50倍");
                mMutile50.setText("100倍");
                mMutile100.setText("200倍");
            } else if (v.getId() == R.id.edit_buy_issue && hasFocus) {//追号的期数获取焦点
                whoFocus = "2";
                mMutile10.setText("10期");
                mMutile20.setText("20期");
                mMutile50.setText("30期");
                mMutile100.setText("50期");
            } else if (v.getId() == R.id.edit_money && hasFocus) {//追号停止的中奖钱数
                whoFocus = "3";
                mMutile10.setText("50元");
                mMutile20.setText("100元");
                mMutile50.setText("200元");
                mMutile100.setText("500元");
            }
        }
    };

    /**
     * 点击确定按钮后控制一个布局文件的显隐
     * 当选中的球满足要求的时候显示出购买的列表布局。
     * 在列表的布局上面可以对选中的球进行相应的一些操作。
     * 包括添加更多，需要把列表布局gone同时对上次添加的球的map集合进行清空。
     * 清空列表 把布局gone同时清空listview的数据源
     */
    class EnsureOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            isShowBuyDialog = true;
            ensure(1);
        }
    }

    private boolean isShowBuyDialog = false;//是否显示购买界面。true显示，否则不显示
    private boolean isFirst = true;//是否是第一次。false 不是，true是第一次

    private void ensure(int type) {//1,正常的点击确定的按钮 2，点击机选1注
        if (isFirst) {
            buyInitView();
            isFirst = false;
        }
        switch (lotcode) {
            case "23529":
                if (isDan) {
                    redBlueBallDanTuoDlt(type);
                } else
                    redBlueBall(type);
                break;
            case "33":
                //排列三从0开始,直选是三行，每行至少一个
                if ("1:1".equals(playMethod)) {
                    redBallLines(type);
                } else {
                    redBalls(type);
                }
                break;
            case "35":
                //从0开始,每排至少选择一个
                //当mapBall集合中每排都有选中的球的时候点击确定才执行相应的操作
                redBallLines(type);
                break;
            case "10022":
                //七星彩从0开始,每排至少选择一个
                //当mapBall集合中每排都有选中的球的时候点击确定才执行相应的操作
                redBallLines(type);
                break;
            case "23528":
                //七乐彩从1开始,至少选择7个球
                if (isDan)
                    dantuoQlc(type);
                else redBalls(type);
                break;
            case "51":
                //双色球从1开始
                if (isDan) {
                    redBlueBallDanTuoSsq(type);
                } else
                    redBlueBall(type);
                break;
            case "52":
                //福彩3D从0开始,直选是三行，每行至少一个
                if ("1:1".equals(playMethod)) {
                    redBallLines(type);
                } else {
                    redBalls(type);
                }
                break;
            case "21406":
                //从1开始,共11个球
                if ("09:01".equals(playMethod) || "10:01".equals(playMethod)) {
                    elevenBallLines(type);
                } else {
                    redBalls(type);
                }
                break;
            case "36":
                jxksBall(type);
                break;
        }
    }

    class BuyBtnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //需要判断是否为登录状态，如果不是就跳转到登录界面
            if (LotteryApp.isLogin) {
                if (!LotteryApp.phoneFlag) {
                    HintDialogUtils.setHintDialog(getContext());
                    HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                    HintDialogUtils.setTitleVisiable(true);
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                        @Override
                        public void callBack(View view) {
                            //手机号未绑定
                            if (LotteryApp.isThird) {
                                Intent intent = new Intent(getContext(), PhoneIsExistActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getContext(), BindPhoneActivity.class);
                                intent.putExtra("isBind", false);
                                startActivity(intent);
                            }

                        }
                    });
                    return;
                }
                if ("".equals(mMulti.getText().toString().trim())) {
                    Toast.makeText(getContext(), "倍数不能为空", Toast.LENGTH_SHORT).show();
                } else if (endDateData == null || endDateData.getCode() != 0) {
                    Toast.makeText(getContext(), "未获取到可售期号", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < buyBallStrList.size(); i++) {
                        if (i == buyBallStrList.size() - 1) {
                            if (isZuiJia == 1) {
                                String[] split = String.valueOf(buyBallStrList.get(i)).split(":");
                                sb.append(split[0] + ":" + playMethod);
                            } else {
                                sb.append(buyBallStrList.get(i));
                            }
                        } else {
                            if (isZuiJia == 1) {
                                String[] split = String.valueOf(buyBallStrList.get(i)).split(":");
                                sb.append(split[0] + ":" + playMethod + ";");
                            } else {
                                sb.append(buyBallStrList.get(i) + ";");
                            }
                        }
                    }
                    String money = sumGroups * isZuiJiaMoney * (Integer.parseInt(mMulti.getText().toString())) * buyIssueNum + "";
                    Bundle bundle = new Bundle();
                    bundle.putInt("allCount", buyIssueNum);//追号总期数
                    bundle.putString("oneMoney", sumGroups * isZuiJiaMoney * (Integer.parseInt(mMulti.getText().toString())) + "");//单笔金额
                    bundle.putString("pickType", "1");//追号方式：1.自选；2.机选
                    bundle.putString("stopType", stopType);//1.追号结束停止；2.中奖(满指定额度)即停止
                    bundle.putString("stopPrize", stopPrize);//中奖即停止时的中奖额度，不填写时默认值为0.00
                    bundle.putInt("requestCode", 200);
                    bundle.putString("buyMethod", "normal");
                    bundle.putString("content", String.valueOf(sb));
                    bundle.putInt("shakes", sumGroups);
                    bundle.putInt("mMulti", Integer.parseInt(mMulti.getText().toString()));
                    bundle.putString("money", money);
                    bundle.putString("issue", endDateData.getData().getIssueNo());
                    //bundle.putString("mBunchList",mBunchList);
                    bundle.putInt("isChase", isZuiJia);
                    bundle.putString("lotcode", lotcode);
                    NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
                    netWorkData.orderForm();
                }
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        }
    }

    View.OnClickListener AddBetAutoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //随机选择红球
            //clearCollection();
            mapBall.clear();
            hashMapView.clear();
            randomMapBall.clear();
            randomNumber = new RandomNumber();
            randomNumber.setNum(mContext, lotcode, lvNum, minNum, gvNum, minBlueNum, gvBlueNum);
            if (lotcode.equals(LotCode.K3_CODE)) {//快三的随机选择单独处理
                if (mType == ConstantNum.ER_TONG_HAO_FU || mType == ConstantNum.ER_BU_TONG_HAO)
                    mImageDice2.setVisibility(View.GONE);
                else mImageDice2.setVisibility(View.VISIBLE);
                if (mType == ConstantNum.SAN_LIAN_HAO_TONG) {
                    List<Integer> list = new ArrayList<>();
                    List<String> listmap = new ArrayList<>();
                    list.add(0);
                    listmap.add("123   234   345   456");
                    randomMapBall.put(0, list);
                    mapBall.put(0, listmap);
                    setGroup(1);
                    sumGroup = 1;
                } else if (mType == ConstantNum.SAN_TONG_HAO_TONG) {
                    List<Integer> list = new ArrayList<>();
                    List<String> listmap = new ArrayList<>();
                    list.add(0);
                    listmap.add("111   222   333   444   555   666");
                    randomMapBall.put(0, list);
                    mapBall.put(0, listmap);
                    setGroup(1);
                    sumGroup = 1;
                } else {
                    randomNumber.getK3Auto(mType, mapBall, randomMapBall);
                }
            } else {
                randomNumber.getRandom(mType, mapBall, randomMapBall);
                if (ConstantNum.ZU_3 == mType) {
                    setGroup(2);
                    sumGroup = 2;
                } else {
                    setGroup(1);
                    sumGroup = 1;
                }
            }
            ensure(2);
        }
    };

    class AddOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            buyViewDialog.dismiss();
            //当购买界面消失的时候，重新注册传感器。
            registerSensor();
            mapBall.clear();
            hashMapView.clear();
            randomMapBall.clear();
            adapter();
            setGroup(0);
        }
    }

    class ClearFormOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            buyViewDialog.dismiss();
            //当购买界面消失的时候，重新注册传感器。
            registerSensor();
            clearCollection();
            adapter();
        }
    }

    private void redBalls(int type) {
        if (mapBall.size() > 0) {
            if (mapBall.get(0).size() >= minNum) {
                if (isShowBuyDialog && type == 1)
                    showDialog();
                StringBuilder mBallNumStr = new StringBuilder();
                List<String> strings = mapBall.get(0);
                for (int i = 0; i < strings.size(); i++) {
                    mBallNumStr.append(strings.get(i) + " ");
                }
                ballStrList.add(mBallNumStr);
                mListGroup.add(sumGroup + "");
                if (type == 1) {
                    mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                        @Override
                        public void clearBall(int position) {
                            mListGroup.remove(position);
                            buyBallStrList.remove(position);
                            ballStrList.remove(position);
                            mSelectBallAdapter8.notifyDataSetChanged();
                            groupBallMoney(mListGroup);
                        }
                    }, lotcode);
                    mListViewContent.setAdapter(mSelectBallAdapter8);
                } else {
                    mSelectBallAdapter8.notifyDataSetChanged();
                }
                groupBallMoney(mListGroup);
                //每次点击拼接选中的号码用于付款的时候作为参数

                splitBallql();
            } else {
                Toast.makeText(getContext(), "请至少选择" + minNum + "个号码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "请至少选择" + minNum + "个号码", Toast.LENGTH_SHORT).show();
        }

    }

    private void jxksBall(int type) {//点击确定按钮
        if ("5".equals(playMethod)) {
            if (mapBall.size() < 2 || mapBall.get(0).size() == 0 || mapBall.get(1).size() == 0) {
                Toast.makeText(getContext(), "请至少选择一个同号和一个不同号", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if ("3".equals(playMethod)) {
            if (mapBall.get(0) == null || mapBall.get(0).size() < 3) {
                Toast.makeText(getContext(), "请至少选择三个号码", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if ("7".equals(playMethod)) {
            if (mapBall.get(0) == null || mapBall.get(0).size() < 2) {
                Toast.makeText(getContext(), "请至少选择二个号码", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (mapBall.get(0) == null || mapBall.get(0).size() == 0) {
                Toast.makeText(getContext(), "请至少选择一个号", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (isShowBuyDialog && type == 1)
            showDialog();
        //将选中的球拼接成字符串
        StringBuilder mBallNumStr = new StringBuilder();
        if ("5".equals(playMethod)) {
            for (int i = 0; i < mapBall.get(0).size(); i++) {
                for (int j = 0; j < mapBall.get(1).size(); j++) {
                    String string = mapBall.get(0).get(i) + mapBall.get(1).get(j);
                    mBallNumStr.append(string + " ");
                }
            }
        } else {
            for (int j = 0; j < mapBall.get(0).size(); j++) {
                mBallNumStr.append(mapBall.get(0).get(j) + " ");
            }
        }
        ballStrList.add(mBallNumStr);
        mListGroup.add(sumGroup + "");
        if (type == 1) {
            mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                @Override
                public void clearBall(int position) {
                    mListGroup.remove(position);
                    buyBallStrList.remove(position);
                    ballStrList.remove(position);
                    mSelectBallAdapter8.notifyDataSetChanged();
                    groupBallMoney(mListGroup);
                }
            }, lotcode);
            mListViewContent.setAdapter(mSelectBallAdapter8);
        } else {
            mSelectBallAdapter8.notifyDataSetChanged();
        }

        groupBallMoney(mListGroup);
        //每次点击拼接选中的号码用于付款的时候作为参数

        splitBallJxks();

    }

    private void splitBallJxks() {
        StringBuilder strBuilder = new StringBuilder();
        for (int j = 0; j < mapBall.get(0).size(); j++) {
            if (j == mapBall.get(0).size() - 1) {
                //每一行的最后一个号码后面不加空格
                strBuilder.append(mapBall.get(0).get(j));
            } else {
                strBuilder.append(mapBall.get(0).get(j) + ",");
            }
        }
        if (mapBall.get(1) != null && mapBall.get(1).size() > 0) {
            strBuilder.append("|");
            for (int j = 0; j < mapBall.get(1).size(); j++) {
                if (j == mapBall.get(1).size() - 1) {
                    //每一行的最后一个号码后面不加空格
                    strBuilder.append(mapBall.get(1).get(j));
                } else {
                    strBuilder.append(mapBall.get(1).get(j) + ",");
                }
            }
        }
        strBuilder.append(":");
        strBuilder.append(LotteryTypes.getPlayMethod(lotcode, playMethod));
        buyBallStrList.add(strBuilder);
    }

    private void dantuoQlc(int type) {
        if (mapBall.get(0) == null || mapBall.get(0).size() == 0) {
            Toast.makeText(getContext(), "请至少选择1个胆码！", Toast.LENGTH_SHORT).show();
        } else if (mapBall.get(1) == null || mapBall.get(1).size() < minNum + 1 - mapBall.get(0).size()) {
            Toast.makeText(getContext(), "请至少选择" + (minNum + 1 - mapBall.get(0).size()) + "个拖码！", Toast.LENGTH_SHORT).show();
        } else {
            if (isShowBuyDialog && type == 1)
                showDialog();
            //将选中的球拼接成字符串
            StringBuilder mBallNumStr = new StringBuilder();
            StringBuilder mBuyStr = new StringBuilder();
            mBallNumStr.append("(");
            for (int i = 0; i < mapBall.get(0).size(); i++) {
                if (i == mapBall.get(0).size() - 1) {
                    mBallNumStr.append(mapBall.get(0).get(i) + ")");
                    mBuyStr.append(mapBall.get(0).get(i) + "$");
                } else {
                    mBallNumStr.append(mapBall.get(0).get(i) + " ");
                    mBuyStr.append(mapBall.get(0).get(i) + ",");
                }
            }
            for (int i = 0; i < mapBall.get(1).size(); i++) {
                if (i == mapBall.get(1).size() - 1) {
                    mBallNumStr.append(mapBall.get(1).get(i) + " ");
                    mBuyStr.append(mapBall.get(1).get(i) + ":" + playMethod);
                } else {
                    mBallNumStr.append(mapBall.get(1).get(i) + " ");
                    mBuyStr.append(mapBall.get(1).get(i) + ",");
                }
            }

            //将付款需要用到的字符串存起来
            buyBallStrList.add(mBuyStr);

            ballStrList.add(mBallNumStr);
            mListGroup.add(sumGroup + "");
            if (type == 1) {
                mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                    @Override
                    public void clearBall(int position) {
                        mListGroup.remove(position);
                        buyBallStrList.remove(position);
                        ballStrList.remove(position);
                        mSelectBallAdapter8.notifyDataSetChanged();
                        groupBallMoney(mListGroup);
                    }
                }, lotcode);
                mListViewContent.setAdapter(mSelectBallAdapter8);
            } else {
                mSelectBallAdapter8.notifyDataSetChanged();
            }
            groupBallMoney(mListGroup);
        }
    }

    private void elevenBallLines(int type) {
        if (mapBall.size() == lvNum) {
            if (isShowBuyDialog && type == 1)
                showDialog();
            //将选中的球拼接成字符串
            StringBuilder mBallNumStr = new StringBuilder();
            for (int i = 0; i < mapBall.size(); i++) {
                for (int j = 0; j < mapBall.get(i).size(); j++) {
                    mBallNumStr.append(mapBall.get(i).get(j) + " ");
                }
            }
            ballStrList.add(mBallNumStr);
            mListGroup.add(sumGroup + "");
            if (type == 1) {
                mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                    @Override
                    public void clearBall(int position) {
                        mListGroup.remove(position);
                        buyBallStrList.remove(position);
                        ballStrList.remove(position);
                        mSelectBallAdapter8.notifyDataSetChanged();
                        groupBallMoney(mListGroup);
                    }
                }, lotcode);
                mListViewContent.setAdapter(mSelectBallAdapter8);
            } else {
                mSelectBallAdapter8.notifyDataSetChanged();
            }
            groupBallMoney(mListGroup);
            //每次点击拼接选中的号码用于付款的时候作为参数

            splitBallEleven();
        } else {
            Toast.makeText(getContext(), "请至少选择" + lvNum + "个号码", Toast.LENGTH_SHORT).show();
        }
    }

    private void redBallLines(int type) {
        //当mapBall集合中每排都有选中的球的时候点击确定才执行相应的操作
        if (mapBall.size() == lvNum) {
            if (isShowBuyDialog && type == 1)
                showDialog();
            //将选中的球拼接成字符串
            StringBuilder mBallNumStr = new StringBuilder();
            for (int i = 0; i < mapBall.size(); i++) {
                for (int j = 0; j < mapBall.get(i).size(); j++) {
                    mBallNumStr.append(mapBall.get(i).get(j));
                }
                mBallNumStr.append(" ");
            }
            ballStrList.add(mBallNumStr);
            mListGroup.add(sumGroup + "");
            if (type == 1) {
                mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                    @Override
                    public void clearBall(int position) {
                        mListGroup.remove(position);
                        buyBallStrList.remove(position);
                        ballStrList.remove(position);
                        mSelectBallAdapter8.notifyDataSetChanged();
                        groupBallMoney(mListGroup);
                    }
                }, lotcode);
                mListViewContent.setAdapter(mSelectBallAdapter8);
            } else {
                mSelectBallAdapter8.notifyDataSetChanged();
            }
            groupBallMoney(mListGroup);
            //每次点击拼接选中的号码用于付款的时候作为参数

            splitBallwq();
            // redBallLengthList.add(mapBall.get(0).size() * 3);
        } else {
            Toast.makeText(getContext(), "请至少选择" + lvNum + "个号码", Toast.LENGTH_SHORT).show();
        }
    }

    private void redBlueBall(int type) {
        if (mapBall.size() != lvNum || mapBall.get(0).size() < minNum || mapBall.get(1).size() < minBlueNum) {
            Toast.makeText(getContext(), "至少选择" + minNum + "个红球" + minBlueNum + "个蓝球", Toast.LENGTH_SHORT).show();
        } else {
            if (isShowBuyDialog && type == 1)
                showDialog();
            //将选中的球拼接成字符串
            StringBuilder mBallNumStr = new StringBuilder();
            for (int i = 0; i < mapBall.size(); i++) {
                for (int j = 0; j < mapBall.get(i).size(); j++) {
                    mBallNumStr.append(mapBall.get(i).get(j) + " ");
                }
            }
            ballStrList.add(mBallNumStr);
            mListGroup.add(sumGroup + "");
            if (type == 1) {
                mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                    @Override
                    public void clearBall(int position) {
                        redBallLengthList.remove(position);
                        mListGroup.remove(position);
                        buyBallStrList.remove(position);
                        ballStrList.remove(position);
                        mSelectBallAdapter8.notifyDataSetChanged();
                        groupBallMoney(mListGroup);
                    }
                }, lotcode);
                mListViewContent.setAdapter(mSelectBallAdapter8);
            } else {
                mSelectBallAdapter8.notifyDataSetChanged();
            }
            groupBallMoney(mListGroup);
            //每次点击拼接选中的号码用于付款的时候作为参数

            splitBallds();
            redBallLengthList.add(mapBall.get(0).size() * 3);
        }
    }

    private void redBlueBallDanTuoDlt(int type) {//1,正常的点击确定的按钮 2，点击机选1注
        if (mapBall.get(0) == null || mapBall.get(0).size() == 0) {
            Toast.makeText(getContext(), "请至少选择1个前区胆码！", Toast.LENGTH_SHORT).show();
        } else if (mapBall.get(1) == null || mapBall.get(1).size() < minNum + 1 - mapBall.get(0).size()) {
            Toast.makeText(getContext(), "请至少选择" + (minNum + 1 - mapBall.get(0).size()) + "个前区拖码！", Toast.LENGTH_SHORT).show();
        } else if (mapBall.get(3) == null || mapBall.get(3).size() < minBlueNum) {
            Toast.makeText(getContext(), "请至少选择" + minBlueNum + "个后区拖码！", Toast.LENGTH_SHORT).show();
        } else {
            if (isShowBuyDialog && type == 1)
                showDialog();
            //将选中的球拼接成字符串
            StringBuilder mBallNumStr = new StringBuilder();
            StringBuilder mBuyStr = new StringBuilder();
            mBallNumStr.append("(");
            for (int i = 0; i < mapBall.get(0).size(); i++) {
                if (i == mapBall.get(0).size() - 1) {
                    mBallNumStr.append(mapBall.get(0).get(i) + ")");
                    mBuyStr.append(mapBall.get(0).get(i) + "$");
                } else {
                    mBallNumStr.append(mapBall.get(0).get(i) + " ");
                    mBuyStr.append(mapBall.get(0).get(i) + ",");
                }

            }
            for (int i = 0; i < mapBall.get(1).size(); i++) {
                if (i == mapBall.get(1).size() - 1) {
                    mBallNumStr.append(mapBall.get(1).get(i) + " ");
                    mBuyStr.append(mapBall.get(1).get(i) + "|");
                } else {
                    mBallNumStr.append(mapBall.get(1).get(i) + " ");
                    mBuyStr.append(mapBall.get(1).get(i) + ",");
                }
            }
            //红球的长度用于在购买的dialog弹出来的时候显示颜色的区分。
            redBallLengthList.add(String.valueOf(mBallNumStr).length());
            /*
            * 第三行，后去胆可以没有，有也只能一个。
            * */
            if (mapBall.get(2) != null && mapBall.get(2).size() > 0) {
                mBallNumStr.append("(" + mapBall.get(2).get(0) + ")");
                mBuyStr.append(mapBall.get(2).get(0) + "$");
            }
            for (int i = 0; i < mapBall.get(3).size(); i++) {
                if (i == mapBall.get(3).size() - 1) {
                    mBallNumStr.append(mapBall.get(3).get(i) + " ");
                    mBuyStr.append(mapBall.get(3).get(i) + ":" + playMethod);
                } else {
                    mBallNumStr.append(mapBall.get(3).get(i) + " ");
                    mBuyStr.append(mapBall.get(3).get(i) + ",");
                }
            }
            //将付款需要用到的字符串存起来
            buyBallStrList.add(mBuyStr);

            ballStrList.add(mBallNumStr);
            mListGroup.add(sumGroup + "");
            if (type == 1) {
                mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                    @Override
                    public void clearBall(int position) {
                        mListGroup.remove(position);
                        redBallLengthList.remove(position);
                        buyBallStrList.remove(position);
                        ballStrList.remove(position);
                        mSelectBallAdapter8.notifyDataSetChanged();
                        groupBallMoney(mListGroup);
                    }
                }, lotcode);
                mListViewContent.setAdapter(mSelectBallAdapter8);
            } else {
                mSelectBallAdapter8.notifyDataSetChanged();
            }
            groupBallMoney(mListGroup);

        }
    }

    private void redBlueBallDanTuoSsq(int type) {
        if (mapBall.get(0) == null || mapBall.get(0).size() == 0) {
            Toast.makeText(getContext(), "请至少选择1个前区胆码！", Toast.LENGTH_SHORT).show();
        } else if (mapBall.get(1) == null || mapBall.get(1).size() < minNum + 1 - mapBall.get(0).size()) {
            Toast.makeText(getContext(), "请至少选择" + (minNum + 1 - mapBall.get(0).size()) + "个前区拖码！", Toast.LENGTH_SHORT).show();
        } else if (mapBall.get(2) == null || mapBall.get(2).size() < minBlueNum) {
            Toast.makeText(getContext(), "请至少选择" + minBlueNum + "个蓝球！", Toast.LENGTH_SHORT).show();
        } else {
            if (isShowBuyDialog && type == 1)
                showDialog();
            //将选中的球拼接成字符串
            StringBuilder mBallNumStr = new StringBuilder();
            StringBuilder mBuyStr = new StringBuilder();
            mBallNumStr.append("(");
            for (int i = 0; i < mapBall.get(0).size(); i++) {
                if (i == mapBall.get(0).size() - 1) {
                    mBallNumStr.append(mapBall.get(0).get(i) + ")");
                    mBuyStr.append(mapBall.get(0).get(i) + "$");
                } else {
                    mBallNumStr.append(mapBall.get(0).get(i) + " ");
                    mBuyStr.append(mapBall.get(0).get(i) + ",");
                }
            }
            for (int i = 0; i < mapBall.get(1).size(); i++) {
                if (i == mapBall.get(1).size() - 1) {
                    mBallNumStr.append(mapBall.get(1).get(i) + " ");
                    mBuyStr.append(mapBall.get(1).get(i) + "|");
                } else {
                    mBallNumStr.append(mapBall.get(1).get(i) + " ");
                    mBuyStr.append(mapBall.get(1).get(i) + ",");
                }
            }
            //红球的长度用于在购买的dialog弹出来的时候显示颜色的区分。
            redBallLengthList.add(String.valueOf(mBallNumStr).length());
            for (int i = 0; i < mapBall.get(2).size(); i++) {
                if (i == mapBall.get(2).size() - 1) {
                    mBallNumStr.append(mapBall.get(2).get(i) + " ");
                    mBuyStr.append(mapBall.get(2).get(i) + ":" + playMethod);
                } else {
                    mBallNumStr.append(mapBall.get(2).get(i) + " ");
                    mBuyStr.append(mapBall.get(2).get(i) + ",");
                }
            }
            //将付款需要用到的字符串存起来
            buyBallStrList.add(mBuyStr);

            ballStrList.add(mBallNumStr);
            mListGroup.add(sumGroup + "");
            if (type == 1) {
                mSelectBallAdapter8 = new SelectBallAdapter(ballStrList, redBallLengthList, mListGroup, new ClearBallInterface() {
                    @Override
                    public void clearBall(int position) {
                        mListGroup.remove(position);
                        redBallLengthList.remove(position);
                        buyBallStrList.remove(position);
                        ballStrList.remove(position);
                        mSelectBallAdapter8.notifyDataSetChanged();
                        groupBallMoney(mListGroup);
                    }
                }, lotcode);
                mListViewContent.setAdapter(mSelectBallAdapter8);
            } else {
                mSelectBallAdapter8.notifyDataSetChanged();
            }
            groupBallMoney(mListGroup);
        }
    }

    //清空集合collection
    private void clearCollection() {
        mListGroup.clear();
        mapBall.clear();
        hashMapView.clear();
        ballStrList.clear();
        buyBallStrList.clear();
        redBallLengthList.clear();
        randomMapBall.clear();
        setGroup(0);
        if (mMulti != null) mMulti.setText(1 + "");
        if (isZuiJia == 1) {
            selectZuijia();
        }
    }

    //拼接十一运的直选
    private void splitBallEleven() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < mapBall.size(); i++) {
            for (int j = 0; j < mapBall.get(i).size(); j++) {
                if (j == mapBall.get(i).size() - 1) {
                    //每一行的最后一个号码后面不加空格
                    strBuilder.append(mapBall.get(i).get(j));
                } else {
                    strBuilder.append(mapBall.get(i).get(j) + ",");
                }
            }
            //如果是最后一行就加个：否则加|
            if (i == mapBall.size() - 1) {
                strBuilder.append(":");
            } else {
                strBuilder.append("|");
            }
        }
        strBuilder.append(playMethod);
        buyBallStrList.add(strBuilder);
    }

    //拼接大乐透或者双色球选中号码（付款时候用）
    private void splitBallds() {
        StringBuilder strBuilder = new StringBuilder();
        List<String> strings = mapBall.get(0);
        for (int j = 0; j < strings.size(); j++) {
            if (j == strings.size() - 1) {
                strBuilder.append(strings.get(j) + "|");
            } else {
                strBuilder.append(strings.get(j) + ",");
            }
        }
        List<String> string = mapBall.get(1);
        for (int j = 0; j < string.size(); j++) {
            if (j == string.size() - 1) {
                strBuilder.append(string.get(j) + ":");
            } else {
                strBuilder.append(string.get(j) + ",");
            }
        }
        strBuilder.append(playMethod);
        buyBallStrList.add(strBuilder);
    }

    //拼接排列五和七星彩选中号码(付款时候用)
    private void splitBallwq() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < lvNum; i++) {
            for (int j = 0; j < mapBall.get(i).size(); j++) {
                strBuilder.append(mapBall.get(i).get(j));
            }
            if (i == lvNum - 1) {
                strBuilder.append(":");
            } else {
                strBuilder.append(",");
            }
        }
        strBuilder.append(playMethod);
        buyBallStrList.add(strBuilder);
    }

    //拼接七乐彩选中号码(付款时候用)
    private void splitBallql() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < mapBall.get(0).size(); i++) {
            if (i == mapBall.get(0).size() - 1) {
                strBuilder.append(mapBall.get(0).get(i) + ":");
            } else {
                strBuilder.append(mapBall.get(0).get(i) + ",");
            }
        }
        strBuilder.append(playMethod);
        buyBallStrList.add(strBuilder);
    }

    private void groupBallMoney(List<String> mListGroup) {
        sumGroups = 0;
        for (int i = 0; i < mListGroup.size(); i++) {
            sumGroups = sumGroups + Integer.parseInt(mListGroup.get(i));
        }
        mTv_betCounts.setText("共" + sumGroups + "注");
        mTv_money.setText("共" + sumGroups * isZuiJiaMoney * m * buyIssueNum + "元");
    }

    HttpInterface2 mHttpInterface = new HttpUtils2(getActivity());

    //本期截止时间
    private void setEndDate() {

        mHttpInterface.jsonByUrl(Url.END_DATE_URL + "?lotCode=" + lotcode, new JsonInterface() {

            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                endDateData = gson.fromJson(result, EndDateData.class);
                if (endDateData.getCode() == 0) {
                    mHttpInterface.jsonByUrl(Url.SERVER_DATE_URL, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            //获取服务器时间
                            Gson gson = new Gson();
                            ServerDateData serverDateData = gson.fromJson(result, ServerDateData.class);
                            if (serverDateData.getCode() == 0) {
                                mTextEndDate.setText("距" + endDateData.getData().getIssueNo() + "期截止:");
                                mIssue.setText(endDateData.getData().getIssueNo() + "期");
                                long seDsendtime = Long.parseLong(endDateData.getData().getStopSaleTime());
                                long data = serverDateData.getData();
                                long endTime = seDsendtime - data;
                                int[] times = DateFormtUtils.obtainTime(endTime);
                                mTextDate.setTimes(times);
                                /*
                                * 当本期结束后重新获取期号
                                * */
                                mTextDate.setListener(new TimeTextView.endTimeListener() {
                                    @Override
                                    public void callback() {
                                        setEndDate();
                                    }
                                });
                                if (!mTextDate.isRun()) {
                                    mTextDate.run();
                                }
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });

                }
            }

            @Override
            public void onError() {

            }
        });
    }

    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if ("".equals(mMulti.getText().toString().trim())) {
                // mTv_betCounts.setText("共0注");
                mTv_money.setText("共" + 0 + "元");
                return;
            }
           /* m = Integer.parseInt(mMulti.getText().toString().trim());
            if (m > 50000) {
                mMulti.setText("50000");
                mMulti.setSelection("50000".length());
                ToastUtils.showToast(mContext,"最大输入50000");
                m = 50000;
            }*/

            int length = String.valueOf(s).length();
            if (length == 0) {
                m = 0;

            } else if (Integer.parseInt(String.valueOf(s)) > 50000) {
                m = 50000;
                mMulti.setText("50000");
                mMulti.setSelection("50000".length());
                ToastUtils.showToast(mContext, "最大输入50000");
            } else if (length > 1 && Integer.parseInt(String.valueOf(s)) == 0) {
                mMulti.setText("0");
                m = 0;
            } else {
                m = Integer.parseInt(String.valueOf(s));
            }

            mTv_money.setText("共" + sumGroups * isZuiJiaMoney * m * buyIssueNum + "元");

        }
    }

    TextWatcher BuyIssueChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ("".equals(mBuyIssue.getText().toString().trim())) {
                // mTv_betCounts.setText("共0注");
                mTv_money.setText("共" + 0 + "元");
                return;
            }
            buyIssueNum = Integer.parseInt(mBuyIssue.getText().toString().trim());
            if (buyIssueNum > 50) {
                mBuyIssue.setText("50");
                buyIssueNum = 50;
            }
            mTv_money.setText("共" + sumGroups * isZuiJiaMoney * m * buyIssueNum + "元");

        }
    };
    private String stopPrize = "100";
    private String stopType = "1";//默认追号结束停止
    TextWatcher MoneyStopListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ("".equals(mMoneyStop.getText().toString().trim())) {
                stopPrize = "0";
                return;
            }
            stopPrize = mMoneyStop.getText().toString().trim();
        }
    };
    View.OnClickListener IsStopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ("1".equals(stopType)) {
                stopType = "2";
                mImgStop.setImageResource(R.drawable.zj_true);
            } else {
                stopType = "1";
                mImgStop.setImageResource(R.drawable.zj_false);
            }
        }
    };
    View.OnClickListener LesanListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, H5Activity.class);
            intent.putExtra("from", "HallFragment");
            startActivity(intent);
        }
    };
    FloatViewLayout.MoveListener ForecastMoveListener = new FloatViewLayout.MoveListener() {
        @Override
        public void moveLeft(int left) {
        }
    };

    class ShakeFormOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
          /*  if (TextUtils.isEmpty(omitResult)) {
                return;
            }*/
            //指定震动的时间
            mVibrator.vibrate(500);
            randomBall();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isDan) {
            mSensorListener = new SensorListener();
            mSensorManager.registerListener(mSensorListener,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
        mSensorManager.unregisterListener(mSensorListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorListener);
        mSensorManager.unregisterListener(mSensorListener);
    }

    //摇一摇传感器监听
    class SensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (TextUtils.isEmpty(omitResult)) {
                return;
            }
            float[] values = event.values;
            int sensor = event.sensor.getType();
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                long curTime = System.currentTimeMillis();
                // only allow one update every 100ms.
                if (curTime - lastUpdate > 600) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;
                    float x = values[0];
                    float y = values[1];
                    float z = values[2];

                    if (Math.abs(values[0]) > 13 || Math.abs(values[1]) > 13 || Math.abs(values[2]) > 13) {
                        mVibrator.vibrate(500);
                        // 播放音效
                        // mSndPool.play(mSoundPoolMap.get(0), (float) 0.2, (float) 0.2, 0, 0, (float) 0.6);
                        lastShake = System.currentTimeMillis();
                        randomBall();
                    }
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

    //摇一注声音效果
    @SuppressWarnings("deprecation")
    private void loadSound() {
        /*
        * 第一个参数 —— 同时播放的流的最大数量
        * 第二个参数 —— 流的类型，一般为STREAM_MUSIC(具体在AudioManager类中列出)
        * 第三个参数 —— 采样率转化质量，当前无效果，使用0作为默认值
        * */
        mSndPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        new Thread() {
            public void run() {
                mSoundPoolMap.put(0, mSndPool.load(getActivity(), R.raw.lvjiao, 1));
            }
        }.start();
    }

    private void dxdsNumber() {
        mapBall.clear();
        randomMapBall.clear();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> listRandom = new ArrayList<>();
        if (hezhiDxds == null) return;
        if (hezhiDxds.size() == 1) {
            if (hezhiDxds.containsKey("大")) {
                list.add("11");
                listRandom.add(8);
                list.add("12");
                listRandom.add(9);
                list.add("13");
                listRandom.add(10);
                list.add("14");
                listRandom.add(11);
                list.add("15");
                listRandom.add(12);
                list.add("16");
                listRandom.add(13);
                list.add("17");
                listRandom.add(14);
                list.add("18");
                listRandom.add(15);
            } else if (hezhiDxds.containsKey("小")) {
                list.add("3");
                listRandom.add(0);
                list.add("4");
                listRandom.add(1);
                list.add("5");
                listRandom.add(2);
                list.add("6");
                listRandom.add(3);
                list.add("7");
                listRandom.add(4);
                list.add("8");
                listRandom.add(5);
                list.add("9");
                listRandom.add(6);
                list.add("10");
                listRandom.add(7);
            } else if (hezhiDxds.containsKey("单")) {
                list.add("3");
                listRandom.add(0);
                list.add("5");
                listRandom.add(2);
                list.add("7");
                listRandom.add(4);
                list.add("9");
                listRandom.add(6);
                list.add("11");
                listRandom.add(8);
                list.add("13");
                listRandom.add(10);
                list.add("15");
                listRandom.add(12);
                list.add("17");
                listRandom.add(14);
            } else if (hezhiDxds.containsKey("双")) {
                list.add("4");
                listRandom.add(1);
                list.add("6");
                listRandom.add(3);
                list.add("8");
                listRandom.add(5);
                list.add("10");
                listRandom.add(7);
                list.add("12");
                listRandom.add(9);
                list.add("14");
                listRandom.add(11);
                list.add("16");
                listRandom.add(13);
                list.add("18");
                listRandom.add(15);
            }
        } else {
            if (hezhiDxds.containsKey("大") && hezhiDxds.containsKey("单")) {
                list.add("11");
                listRandom.add(8);
                list.add("13");
                listRandom.add(10);
                list.add("15");
                listRandom.add(12);
                list.add("17");
                listRandom.add(14);
            } else if (hezhiDxds.containsKey("小") && hezhiDxds.containsKey("单")) {
                list.add("3");
                listRandom.add(0);
                list.add("5");
                listRandom.add(2);
                list.add("7");
                listRandom.add(4);
                list.add("9");
                listRandom.add(6);
            } else if (hezhiDxds.containsKey("大") && hezhiDxds.containsKey("双")) {
                list.add("12");
                listRandom.add(9);
                list.add("14");
                listRandom.add(11);
                list.add("16");
                listRandom.add(13);
                list.add("18");
                listRandom.add(15);
            } else if (hezhiDxds.containsKey("小") && hezhiDxds.containsKey("双")) {
                list.add("4");
                listRandom.add(1);
                list.add("6");
                listRandom.add(3);
                list.add("8");
                listRandom.add(5);
                list.add("10");
                listRandom.add(7);
            }
        }
        mapBall.put(0, list);
        randomMapBall.put(0, listRandom);
        jisuanzushu();
    }

    private void addRandomBall() {

    }

    private RandomNumber randomNumber;

    /*
    *获取随机号码，并且把选中的数据放进连个map集合中，
     * mapBall 是选中的内容，key是第几行，value是选中的内容
    * randomMapBall是选中内容所在的位置。key是第几行，value是选中的内容在这一行的位置
    * */
    private void randomBall() {
        //随机选择红球
        //clearCollection();
        mapBall.clear();
        hashMapView.clear();
        randomMapBall.clear();
        randomNumber = new RandomNumber();
        randomNumber.setNum(mContext, lotcode, lvNum, minNum, gvNum, minBlueNum, gvBlueNum);
        if (lotcode.equals(LotCode.K3_CODE)) {//快三的随机选择单独处理
            if (mType == ConstantNum.ER_TONG_HAO_FU || mType == ConstantNum.ER_BU_TONG_HAO)
                mImageDice2.setVisibility(View.GONE);
            else mImageDice2.setVisibility(View.VISIBLE);
            if (mType == ConstantNum.SAN_LIAN_HAO_TONG) {
                List<Integer> list = new ArrayList<>();
                List<String> listmap = new ArrayList<>();
                list.add(0);
                listmap.add("123   234   345   456");
                randomMapBall.put(0, list);
                mapBall.put(0, listmap);
                setGroup(1);
                sumGroup = 1;
                adapter();
            } else if (mType == ConstantNum.SAN_TONG_HAO_TONG) {
                List<Integer> list = new ArrayList<>();
                List<String> listmap = new ArrayList<>();
                list.add(0);
                listmap.add("111   222   333   444   555   666");
                randomMapBall.put(0, list);
                mapBall.put(0, listmap);
                setGroup(1);
                sumGroup = 1;
                adapter();
            } else {
                mFl_saizi.setVisibility(View.VISIBLE);
                randomNumber.getK3(diceHandler, mFl_saizi, mImageDice, mImageDice2, mImageDice3);
            }
        } else {
            randomNumber.getRandom(mType, mapBall, randomMapBall);
            if (ConstantNum.ZU_3 == mType) {
                setGroup(2);
                sumGroup = 2;
            } else {
                setGroup(1);
                sumGroup = 1;
            }
            adapter();
        }
    }

    private void showDialog() {
        mSensorManager.unregisterListener(mSensorListener);

        Window window = buyViewDialog.getWindow();
        window.setSoftInputMode(2);
        buyViewDialog.setView(inflate);
        buyViewDialog.show();
        // window.setWindowAnimations(R.style.anim_bottom_top);
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        window.setGravity(Gravity.BOTTOM);
    }


    private void hemaiBuy(String str1, String str2, String str3, int type, boolean ghFlag) {
        if (!LotteryApp.phoneFlag) {
            HintDialogUtils.setHintDialog(getContext());
            HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
            HintDialogUtils.setTitleVisiable(true);
            HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                @Override
                public void callBack(View view) {
                    //手机号未绑定
                    if (LotteryApp.isThird) {
                        Intent intent = new Intent(getContext(), PhoneIsExistActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), BindPhoneActivity.class);
                        intent.putExtra("isBind", false);
                        startActivity(intent);
                    }

                }
            });
            return;
        }
        String wrate, bnum, pnum, desc;
        int order_type;
        if (ghFlag) {
            wrate = str1;
            bnum = str2;
            pnum = str3;
            desc = "跟着成功人的脚步走";
            order_type = 1;
        } else {
            wrate = str2;
            bnum = "0";
            pnum = "0";
            desc = str1;
            order_type = 7;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buyBallStrList.size(); i++) {
            if (i == buyBallStrList.size() - 1) {
                if (isZuiJia == 1) {
                    String[] split = String.valueOf(buyBallStrList.get(i)).split(":");
                    sb.append(split[0] + ":" + playMethod);
                } else {
                    sb.append(buyBallStrList.get(i));
                }
            } else {
                if (isZuiJia == 1) {
                    String[] split = String.valueOf(buyBallStrList.get(i)).split(":");
                    sb.append(split[0] + ":" + playMethod + ";");
                } else {
                    sb.append(buyBallStrList.get(i) + ";");
                }
            }
        }
        String money = sumGroups * isZuiJiaMoney * (Integer.parseInt(mMulti.getText().toString())) + "";
        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", 200);
        bundle.putString("buyMethod", "hemai");
        bundle.putString("content", String.valueOf(sb));
        bundle.putInt("shakes", sumGroups);
        bundle.putInt("mMulti", Integer.parseInt(mMulti.getText().toString()));
        bundle.putString("money", money);
        bundle.putString("issue", endDateData.getData().getIssueNo());
        bundle.putString("lotcode", lotcode);
        bundle.putInt("isChase", isZuiJia);
        bundle.putString("order_type", order_type + "");
        bundle.putString("wrate", wrate);
        bundle.putString("bnum", bnum);
        bundle.putString("pnum", pnum);
        bundle.putString("type", type + "");
        bundle.putString("desc", desc);
        NetWorkData netWorkData = new NetWorkData(getContext(), bundle);
        netWorkData.orderForm();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > DisplayUtil.getDisplayHeight() / 3)) {
            mLl_buy_bttomview.setVisibility(View.INVISIBLE);
            mLl_mutile.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > DisplayUtil.getDisplayHeight() / 3)) {
            mLl_buy_bttomview.setVisibility(View.VISIBLE);
            mLl_mutile.setVisibility(View.INVISIBLE);
        }
    }

    private void registerSensor() {
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void paymentFinish() {
        //冲购买页面支付完成后返回回来
        buyViewDialog.dismiss();
        //当购买界面消失的时候，重新注册传感器。
        registerSensor();
        clearCollection();
        setGroup(0);
        mTv_betCounts.setText("共" + 0 + "注");
        mTv_money.setText("共" + 0 + "元");
        clearCollection();
        adapter();
    }

    private float selectX;
    private float selectY;
    private float selectX2;
    private float selectY2;
    private float selectX3;
    private float selectY3;
    final Handler diceHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    resetDiceImage();

                    break;
                case 2:
                    if (randomArray == null || randomArray.length == 0) return;
                    if (mType == ConstantNum.HE_ZHI) {
                        int randomNumber = randomArray[0] + randomArray[1] + randomArray[2];
                        List<Integer> list = new ArrayList<>();
                        List<String> listmap = new ArrayList<>();
                        list.add(randomNumber - 3);
                        listmap.add(randomNumber + "");
                        randomMapBall.put(0, list);
                        mapBall.put(0, listmap);
                        xiaoshi(new int[]{randomNumber - 3, randomNumber - 3, randomNumber - 3});
                    } else if (mType == ConstantNum.SAN_TONG_HAO) {
                        int randomNumber = randomArray[0];
                        List<Integer> list = new ArrayList<>();
                        List<String> listmap = new ArrayList<>();
                        list.add(randomNumber - 1);
                        listmap.add(randomNumber + "" + randomNumber + "" + randomNumber);
                        randomMapBall.put(0, list);
                        mapBall.put(0, listmap);
                        xiaoshi(new int[]{randomNumber - 1, randomNumber - 1, randomNumber - 1});
                    } else if (mType == ConstantNum.SAN_BU_TONG_HAO) {
                        List<Integer> list = new ArrayList<>();
                        List<String> listmap = new ArrayList<>();
                        list.add(randomArray[0] - 1);
                        list.add(randomArray[1] - 1);
                        list.add(randomArray[2] - 1);
                        listmap.add(randomArray[0] + "");
                        listmap.add(randomArray[1] + "");
                        listmap.add(randomArray[2] + "");
                        randomMapBall.put(0, list);
                        mapBall.put(0, listmap);
                        xiaoshi(new int[]{randomArray[0] - 1, randomArray[1] - 1, randomArray[2] - 1});
                    } else if (mType == ConstantNum.ER_TONG_HAO_FU) {//两个筛子
                        int randomNumber = randomArray[0];
                        List<Integer> list = new ArrayList<>();
                        List<String> listmap = new ArrayList<>();
                        list.add(randomNumber - 1);
                        listmap.add(randomNumber + "" + randomNumber);
                        randomMapBall.put(0, list);
                        mapBall.put(0, listmap);
                        xiaoshi(new int[]{randomNumber - 1, randomNumber - 1, randomNumber - 1});
                    } else if (mType == ConstantNum.ER_BU_TONG_HAO) {
                        List<Integer> list = new ArrayList<>();
                        List<String> listmap = new ArrayList<>();
                        list.add(randomArray[0] - 1);
                        list.add(randomArray[1] - 1);
                        listmap.add(randomArray[0] + "");
                        listmap.add(randomArray[1] + "");
                        randomMapBall.put(0, list);
                        mapBall.put(0, listmap);
                        xiaoshi(new int[]{randomArray[0] - 1, randomArray[1] - 1, randomArray[1] - 1});
                    } else if (mType == ConstantNum.ER_TONG_HAO_DAN) {
                        List<Integer> list = new ArrayList<>();
                        List<String> listmap = new ArrayList<>();
                        list.add(randomArray[0] - 1);
                        listmap.add(randomArray[0] + "" + randomArray[1]);
                        randomMapBall.put(0, list);
                        mapBall.put(0, listmap);
                        List<Integer> list2 = new ArrayList<>();
                        List<String> listmap2 = new ArrayList<>();
                        list2.add(randomArray[2] - 1);
                        listmap2.add(randomArray[2] + "");
                        randomMapBall.put(1, list2);
                        mapBall.put(1, listmap2);
                        xiaoshi(new int[]{randomArray[0] - 1, randomArray[1] - 1, randomArray[2] - 1});
                    }
                    break;
                case 3:
                    mFl_saizi.setVisibility(View.GONE);
                    setGroup(1);
                    sumGroup = 1;
                    adapter();
                    break;
                default:
                    break;
            }
        }

    };
    private int xiaoTime = 1500;//筛子所用的消失的时间

    private void xiaoshi(int[] number) {
        int statusHeight = DisplayUtil.getStatusBarHeight();
        TextView mTextView = mViewMap.get(0).get(number[0]);
        int[] outLocation = new int[2];
        mTextView.getLocationOnScreen(outLocation);
        selectY = outLocation[1] - dp50;
        selectX = outLocation[0] + dp40;

        TextView mTextView2 = mViewMap.get(0).get(number[1]);
        int[] outLocation2 = new int[2];
        mTextView2.getLocationOnScreen(outLocation2);
        selectY2 = outLocation2[1] - dp60;
        selectX2 = outLocation2[0] + dp40;
        TextView mTextView3;
        if (mType != ConstantNum.ER_TONG_HAO_DAN)
            mTextView3 = mViewMap.get(0).get(number[2]);
        else mTextView3 = mViewMap.get(1).get(number[2]);
        int[] outLocation3 = new int[2];
        mTextView3.getLocationOnScreen(outLocation3);
        selectY3 = outLocation3[1] - dp60;
        selectX3 = outLocation3[0] + dp40;
        //AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, selectX - randomNumber.x, 0, selectY - randomNumber.y - statusHeight);
        TranslateAnimation translateAnimation2 = new TranslateAnimation(0, selectX2 - randomNumber.x2, 0, selectY2 - randomNumber.y2 - statusHeight);
        TranslateAnimation translateAnimation3 = new TranslateAnimation(0, selectX3 - randomNumber.x3, 0, selectY3 - randomNumber.y3 - statusHeight);
        AnimationSet animationSet = new AnimationSet(true);
        //  animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(xiaoTime);
        //  animationSet.setFillAfter(true);
        AnimationSet animationSet2 = new AnimationSet(true);
        //   animationSet2.addAnimation(alphaAnimation);
        animationSet2.addAnimation(scaleAnimation);
        animationSet2.addAnimation(translateAnimation2);
        animationSet2.setDuration(xiaoTime);
        //  animationSet2.setFillAfter(true);

        AnimationSet animationSet3 = new AnimationSet(true);
        //   animationSet3.addAnimation(alphaAnimation);
        animationSet3.addAnimation(scaleAnimation);
        animationSet3.addAnimation(translateAnimation3);
        animationSet3.setDuration(xiaoTime);
        //  animationSet3.setFillAfter(true);

        mImageDice.startAnimation(animationSet);
        if (mType != ConstantNum.ER_TONG_HAO_FU && mType != ConstantNum.ER_BU_TONG_HAO)
            mImageDice2.startAnimation(animationSet2);
        mImageDice3.startAnimation(animationSet3);

        new Thread(new DiceThread3(diceHandler, xiaoTime)).start();
    }

    private int[] randomArray;
    private int stopTime = 1000;//旋转完成之后的停留时间

    private void resetDiceImage() {
        if (randomNumber == null) return;
        randomArray = randomNumber.getRandom(mType);
        if (randomArray != null && randomArray.length == 3) {
            Toast.makeText(mContext, randomArray[0] + "" + randomArray[1] + "" + randomArray[2],
                    Toast.LENGTH_SHORT).show();
            mImageDice.setImageDrawable(getResources().getDrawable(
                    getResources().getIdentifier("dice_" + randomArray[0], "drawable",
                            mContext.getPackageName())));
            mImageDice2.setImageDrawable(getResources().getDrawable(
                    getResources().getIdentifier("dice_" + randomArray[1], "drawable",
                            mContext.getPackageName())));
            mImageDice3.setImageDrawable(getResources().getDrawable(
                    getResources().getIdentifier("dice_" + randomArray[2], "drawable",
                            mContext.getPackageName())));
            new Thread(new DiceThread2(diceHandler, stopTime)).start();
        } else if (randomArray != null && randomArray.length == 2) {
            Toast.makeText(mContext, randomArray[0] + "" + randomArray[1],
                    Toast.LENGTH_SHORT).show();
            mImageDice.setImageDrawable(getResources().getDrawable(
                    getResources().getIdentifier("dice_" + randomArray[0], "drawable",
                            mContext.getPackageName())));
            mImageDice3.setImageDrawable(getResources().getDrawable(
                    getResources().getIdentifier("dice_" + randomArray[1], "drawable",
                            mContext.getPackageName())));
            new Thread(new DiceThread2(diceHandler, stopTime)).start();
        }
    }
}
