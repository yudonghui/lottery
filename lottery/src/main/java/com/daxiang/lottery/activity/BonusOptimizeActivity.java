package com.daxiang.lottery.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.adapter.jcadapter.BonusCombineAdapter;
import com.daxiang.lottery.common.JcPlayMethod;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.dialog.LoadingDialog;
import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.jcmath.Combination;
import com.daxiang.lottery.utils.jcmath.TheoryBonusMath;
import com.daxiang.lottery.view.GendanView;
import com.daxiang.lottery.view.HemaiView;
import com.daxiang.lottery.view.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BonusOptimizeActivity extends BaseNoTitleActivity implements View.OnClickListener {
    //购买结果（共 场赛，过关方式：）
    private TextView mResultTextView;
    //购买计划
    private TextView mMoneyTextView;
    //奖金范围
    private TextView mBonusRangeTextView;
    //选择的场次
    ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList;
    ArrayList<String> danTuoList;
    HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentMap;
    //用于列表展示
    ArrayList<ArrayList<ChoosedContentFormBean>> formList = new ArrayList<>();
    StringBuilder bunchsStr = new StringBuilder();
    //用于存放选中的串的编号。
    HashMap<Integer, Integer> mBunchMap;
    private TextView mOptionWinStable;
    private TextView mOptionWinEasy;
    private TextView mOptionWinHigh;
    private TextView mHint;
    private TextView mBtnSubmit;
    private ListView mListView;
    private TitleBar mTitleBar;
    private ImageView mHemai;
    private LinearLayout mGendan;
    private int mMulti;
    private int shakes;
    private String mLotcode;
    private BonusCombineAdapter mAdapter;
    private LoadingDialog mLoadingDialog;
    String sumMoney;
    float minMoney;
    Context mContext;
    private int minTJMoney;//最小发单金额
    private String tjMoneyMsg;//最小发单金额说明

    // private BonusCombineAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_optimize);
        mContext = this;
        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        minTJMoney = sp.getInt("minTJMoney", 0);
        tjMoneyMsg = sp.getString("tjMoneyMsg", "");
        addIntent();
        initView();
        addData();
        mAdapter = new BonusCombineAdapter(orderFormList, orderOddsArray, orderMultiArray, mMulti);
        mListView.setAdapter(mAdapter);
        checkAt(mOptionWinStable.getId());
        addListener();
    }

    private void addIntent() {
        Intent intent = getIntent();
        choosedContentFormList = (ArrayList<ArrayList<ChoosedContentFormBean>>) intent.getSerializableExtra("choosedContentFormList");
        danTuoList = (ArrayList<String>) intent.getSerializableExtra("danTuoList");
        mBunchMap = (HashMap<Integer, Integer>) intent.getSerializableExtra("mBunchMap");
        mChoosedContentMap = (HashMap<JczqData.DataBean, HashMap<String, String>>) intent.getSerializableExtra("mChoosedContentMap");
        shakes = intent.getIntExtra("shakes", 0);
        mMulti = intent.getIntExtra("mMulti", 0);
        mLotcode = intent.getStringExtra("lotcode");
    }

    private void addData() {
        //将胆和拖对应的场次信息重新组成集合
        ArrayList<ChoosedContentFormBean> danList = new ArrayList<>();
        ArrayList<ChoosedContentFormBean> tuoList = new ArrayList<>();
        for (int i = 0; i < choosedContentFormList.size(); i++) {
            for (int j = 0; j < choosedContentFormList.get(i).size(); j++) {
                if (!danTuoList.contains(choosedContentFormList.get(i).get(j).getMid())) {
                    tuoList.add(choosedContentFormList.get(i).get(j));
                } else {
                    danList.add(choosedContentFormList.get(i).get(j));
                }
            }
        }
        Log.e("danlist", danList.toString());
        Log.e("tuoList", tuoList.toString());
        //用于去除同一场里面选择。需求是每场选择一个进行组合
        HashMap<String, Boolean> map = new HashMap<>();
        ArrayList<ArrayList<ChoosedContentFormBean>> danFormList = new ArrayList<>();
        ArrayList<ArrayList<ChoosedContentFormBean>> tuoFormList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            map.clear();
            danFormList.clear();
            tuoFormList.clear();
            //胆
            for (List<ChoosedContentFormBean> list : Combination.of(danList, danTuoList.size())) {
                ArrayList<ChoosedContentFormBean> b = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    map.put(list.get(i).getMid(), true);
                    b.add(list.get(i));
                }
                if (map.size() == list.size()) {
                    danFormList.add(b);
                }
                map.clear();
            }
            //拖
            for (List<ChoosedContentFormBean> list : Combination.of(tuoList, (entry.getKey()) - danTuoList.size())) {
                ArrayList<ChoosedContentFormBean> a = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    a.add(list.get(i));
                    map.put(list.get(i).getMid(), true);
                }
                if (map.size() == list.size()) {
                    tuoFormList.add(a);
                }
                map.clear();
            }
            for (int i = 0; i < danFormList.size(); i++) {
                ArrayList<ChoosedContentFormBean> danChoosedList = danFormList.get(i);
                for (int j = 0; j < tuoFormList.size(); j++) {
                    ArrayList<ChoosedContentFormBean> tuoChoosedList = tuoFormList.get(j);
                    tuoChoosedList.addAll(danChoosedList);
                    formList.add(tuoChoosedList);
                }
            }
        }
        sumShakes = mMulti * shakes;
        listData();
    }

    //((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((
    int sumShakes;
    float averagePrize;
    int mMultis = 0;
    ArrayList<List<ChoosedContentFormBean>> orderFormList = new ArrayList<>();
    //每注奖金
    ArrayList<Float> oddsList = new ArrayList<>();
    float[] orderOddsArray;
    // ArrayList<Float> orderOddsList = new ArrayList<>();
    //倍数
    // ArrayList<Integer> mMultiList = new ArrayList<>();
    int[] mMultiArray;
    // ArrayList<Integer> orderMultiList = new ArrayList<>();
    int[] orderMultiArray;
    float[] oddsArray;
    HashMap<Integer, ArrayList<ChoosedContentFormBean>> TestMap;
    int[] sort;
    float sum = 0;
    HashMap<ArrayList<ChoosedContentFormBean>, Integer> map = new HashMap<>();
    float[] moneyArray;

    private void listData() {
        oddsArray = new float[formList.size()];
        mMultiArray = new int[formList.size()];
        //每注奖金
        bonusData();
        //倍数
        mutilData();
        //将数据进行排序,得到对应的索引
        sort = new int[formList.size()];
        ArrayList<ArrayList<ChoosedContentFormBean>> TestList = new ArrayList<>();
        TestList.addAll(formList);
        Collections.sort(TestList, new Comparator<ArrayList<ChoosedContentFormBean>>() {
            @Override
            public int compare(ArrayList<ChoosedContentFormBean> lhs, ArrayList<ChoosedContentFormBean> rhs) {
                float odds1 = 1;
                float odds2 = 1;
                for (int i = 0; i < lhs.size(); i++) {
                    odds1 *= Float.parseFloat(lhs.get(i).getOdds());
                }
                for (int i = 0; i < rhs.size(); i++) {
                    odds2 *= Float.parseFloat(rhs.get(i).getOdds());
                }
                /*
                * 赔率大的在上面。降序排列
                * */
                if (odds1 > odds2) {
                    return 1;
                }
                if (odds1 == odds2) {
                    return 0;
                }
                return -1;
            }
        });
       /* ArrayList<ArrayList<ChoosedContentFormBean>> TestList=new ArrayList<>();
        ArrayList<ArrayList<ChoosedContentFormBean>> TestList2=new ArrayList<>();
        TestList2.addAll(formList);
        for (int index = 0; index < oddsArray.length - 1; index++) {     // 每一趟选择的最小数字依次放在左边
            int lowIndex = index;   // 每一趟的最小数据所对应的索引
            for (int j = index + 1; j < oddsArray.length; j++) {     // 得到每一趟比较的最小值的索引
                if (oddsArray[j] < oddsArray[lowIndex]) {
                    lowIndex = j;
                }
            }
            float temp = oddsArray[lowIndex];     // 将本趟的最小值放在左边
            oddsArray[lowIndex] = oddsArray[index];
            oddsArray[index] = temp;
            TestList.add(formList.get(lowIndex));
            TestList2.remove(formList.get(lowIndex));
            if(index==oddsArray.length-2){
                TestList.add(TestList2.get(0));
            }

        }*/
        LogUtils.e("TestList的值：", TestList.toString());
        //通过排序后的数组取出对应的索引，放入另外一个数组中
        for (int i = 0; i < TestList.size(); i++) {
            sort[i] = map.get(TestList.get(i));
        }
        //对所有的数据进行排序
        orderList();
        mBonusRangeTextView.setText(optimizeSize());
    }

    public void mutilData() {
        //平稳盈利时候平均的奖金，准备倍数的数据
        averagePrize = sumShakes / sum;
        for (int i = 0; i < oddsList.size(); i++) {
            int rint = (int) Math.rint(averagePrize / oddsList.get(i));
            //不能出现倍数为0的现象
            if (rint <= 0) {
                rint = 1;
            }
            mMultis += rint;
            mMultiArray[i] = rint;
        }
    }

    public void bonusData() {
        oddsList.clear();
        //准备每注奖金的数据
        for (int i = 0; i < formList.size(); i++) {
            float sumOdds = 1;
            for (int j = 0; j < formList.get(i).size(); j++) {
                sumOdds *= Float.parseFloat(formList.get(i).get(j).getOdds());
            }
            //每注奖金
            oddsList.add(sumOdds * 2);
            ArrayList<ChoosedContentFormBean> choosedList = formList.get(i);
            map.put(choosedList, i);
            oddsArray[i] = sumOdds * 2;
            sum += 1 / (sumOdds * 2);
        }
    }

    public void orderList() {
        orderFormList.clear();
        moneyArray = new float[formList.size()];
        orderMultiArray = new int[formList.size()];
        orderOddsArray = new float[formList.size()];
        for (int i = 0; i < sort.length; i++) {
            orderFormList.add(formList.get(sort[i]));
            if (i != 0) {
                orderMultiArray[i] = mMultiArray[sort[i]];
            } else {
                orderMultiArray[i] = sumShakes - (mMultis - mMultiArray[sort[i]]);
            }
            orderOddsArray[i] = oddsList.get(sort[i]);
            moneyArray[i] = orderMultiArray[i] * orderOddsArray[i];
        }
        LogUtils.e("orderFormList的值：", orderFormList.toString());
    }

    private String optimizeSize() {
        if (moneyArray == null || moneyArray.length <= 0) return "0.00~0.00";
        double minMoney = moneyArray[0];
        for (int i = 1; i < moneyArray.length; i++) {
            if (moneyArray[i] < 0) moneyArray[i] = -moneyArray[i];
            if (minMoney > moneyArray[i] && moneyArray[i] != 0) {
                minMoney = moneyArray[i];
            }
        }
        HashMap<String, Float> bonusMap = new HashMap<>();
        for (int i = 0; i < orderFormList.size(); i++) {

            String midStr = "";
            for (int j = 0; j < orderFormList.get(i).size(); j++) {
                midStr = midStr + orderFormList.get(i).get(j).getMid() + ";";
            }
            bonusMap.put(midStr, moneyArray[i]);
            LogUtils.e("数字的倍数：", moneyArray[i] + "");
        }
        TheoryBonusMath mTheoryBonusMath = new TheoryBonusMath();
        String text = mTheoryBonusMath.countBonusOptimize(mChoosedContentMap, bonusMap, danTuoList, mBunchMap);
        //return String.format("%.2f", minMoney) + "~" + String.format("%.2f", sumMoney);
        String[] split = text.split("\\-");
        sumMoney = String.format("%.2f", Double.parseDouble(split[1]));

        return String.format("%.2f", minMoney) + "~" + String.format("%.2f", Double.parseDouble(split[1])) + "元";
    }

    //)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
    private void initView() {
        mResultTextView = (TextView) findViewById(R.id.tv_bonus_result);
        bunchsStr.append("共" + choosedContentFormList.size() + "场比赛，过关方式：");
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            i++;
            if (i < mBunchMap.size()) {
                bunchsStr.append((entry.getKey()) + "串1,");
            } else {
                bunchsStr.append((entry.getKey()) + "串1");
            }
        }
        mResultTextView.setText(bunchsStr);
        //购买计划
        mMoneyTextView = (TextView) findViewById(R.id.tv_bonus_money);
        mMoneyTextView.setText("共" + shakes * mMulti + "注，" + shakes * mMulti * 2 + "元");
        //奖金范围
        mBonusRangeTextView = (TextView) findViewById(R.id.tv_bonus_range);
        //购买按钮
        mBtnSubmit = (TextView) findViewById(R.id.btn_bet_submit);
        mOptionWinStable = (TextView) findViewById(R.id.tv_win_stable);
        mOptionWinEasy = (TextView) findViewById(R.id.tv_win_easy);
        mOptionWinHigh = (TextView) findViewById(R.id.tv_win_high);
        mHint = (TextView) findViewById(R.id.text_hint);
        mListView = (ListView) findViewById(R.id.lv_bet_bonus);
        mOptionWinStable.setOnClickListener(this);
        mOptionWinEasy.setOnClickListener(this);
        mOptionWinHigh.setOnClickListener(this);
        mTitleBar = (TitleBar) findViewById(R.id.optimize_titlebar);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("奖金优化");
        //合买
        mHemai = (ImageView) findViewById(R.id.img_hemai);

        //推荐（跟单）
        mGendan = (LinearLayout) findViewById(R.id.ll_recommend);
        /*if (LotteryApp.recommendPrivilege == 2) {
            mGendan.setVisibility(View.GONE);
        }
        if (LotteryApp.buyPrivilege == 2) {
            mBtnSubmit.setText("暂停销售");
            mBtnSubmit.setEnabled(false);
        }*/
    }

    private void addListener() {
        //倍数发生改变的监听
        mAdapter.setOnTimesChangeListener(mOnTimesChangeListner);
        //购买按钮的点击事件
        mBtnSubmit.setOnClickListener(mBuyListener);
        //返回按键
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //合买
        mHemai.setOnClickListener(HemaiClickListener);
        //推荐
        mGendan.setOnClickListener(GendanClickListener);
    }

    View.OnClickListener GendanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // inflate.setFocusable(false);
            if (LotteryApp.isLogin) {
                if (sumShakes * 2 < minTJMoney) {
                    HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(mContext);
                    hintDialogUtils2.setMessage(tjMoneyMsg);
                    hintDialogUtils2.setTitle("温馨提示");
                    hintDialogUtils2.setTitleVisiable(true);
                } else {
                    GendanView mGendanView = new GendanView(mContext);
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
                startActivity(LoginActivity.class);
            }
        }
    };
    View.OnClickListener HemaiClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // inflate.setFocusable(false);
            if (LotteryApp.isLogin) {
                HemaiView mHemaiView = new HemaiView(mContext);
                //View view = View.inflate(mContext, R.layout.dialog_hemai_layout, null);
                final AlertDialog hemaiDialog = new AlertDialog.Builder(mContext).create();
                mHemaiView.setData(shakes, mMulti, new HemaiView.OnClickComit() {
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
            } else {
                startActivity(LoginActivity.class);
            }
        }
    };
    View.OnClickListener mBuyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (LotteryApp.isLogin) {
                if (!LotteryApp.phoneFlag) {
                    HintDialogUtils.setHintDialog(mContext);
                    HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                    HintDialogUtils.setTitleVisiable(true);
                    HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                        @Override
                        public void callBack(View view) {
                            //手机号未绑定
                            if (LotteryApp.isThird) {
                                startActivity(PhoneIsExistActivity.class);
                            } else {
                                Bundle params = new Bundle();
                                params.putBoolean("isBind", false);
                                startActivity(BindPhoneActivity.class, params);
                            }

                        }
                    });
                    return;
                }
                //拼接字符串
                String codes = splitStr();
                // Log.e("上传代码：", codes);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date();
                String issue = format.format(date);
                Bundle bundle = new Bundle();
                bundle.putInt("requestCode", 200);
                bundle.putString("buyMethod", "normal");
                bundle.putString("content", codes);
                bundle.putInt("shakes", shakes);
                bundle.putInt("mMulti", -1);
                bundle.putString("money", sumShakes * 2 + "");
                bundle.putString("issue", issue);
                bundle.putString("lotcode", mLotcode);
                NetWorkData netWorkData = new NetWorkData(BonusOptimizeActivity.this, bundle);
                netWorkData.orderForm();

            } else {
                startActivity(LoginActivity.class);
            }
        }

    };

    private void hemaiBuy(String str1, String str2, String str3, int type, boolean ghFlag) {
        if (!LotteryApp.phoneFlag) {
            HintDialogUtils.setHintDialog(mContext);
            HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
            HintDialogUtils.setTitleVisiable(true);
            HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                @Override
                public void callBack(View view) {
                    //手机号未绑定
                    if (LotteryApp.isThird) {
                        startActivity(PhoneIsExistActivity.class);
                    } else {
                        Bundle params = new Bundle();
                        params.putBoolean("isBind", false);
                        startActivity(BindPhoneActivity.class, params);
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
        StringBuilder bunchsStr = new StringBuilder();
        int m = 0;
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            m++;
            if (m < mBunchMap.size()) {
                bunchsStr.append((entry.getKey()) + "串1,");
            } else {
                bunchsStr.append((entry.getKey()) + "串1");
            }
        }
        //拼接字符串
        String codes = splitStr();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String issue = format.format(date);
        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", 200);
        bundle.putString("buyMethod", "hemai");
        bundle.putString("content", codes);
        bundle.putInt("shakes", shakes);
        bundle.putInt("mMulti", -1);
        bundle.putString("money", sumShakes * 2 + "");
        bundle.putString("issue", issue);
        bundle.putString("lotcode", mLotcode);
        bundle.putString("order_type", order_type + "");
        bundle.putString("wrate", wrate);
        bundle.putString("bnum", bnum);
        bundle.putString("pnum", pnum);
        bundle.putString("type", type + "");
        bundle.putString("desc", desc);
        bundle.putDouble("maxTheoryBonus", Double.parseDouble(sumMoney));
        NetWorkData netWorkData = new NetWorkData(BonusOptimizeActivity.this, bundle);
        netWorkData.orderForm();
    }

    //倍数发生改变的监听
    BonusCombineAdapter.OnTimesChangeListner mOnTimesChangeListner = new BonusCombineAdapter.OnTimesChangeListner() {
        @Override
        public void onTimesChangeListener(int[] MultiArray, int position) {
            orderMultiArray[position] = MultiArray[position];
            moneyArray[position] = orderOddsArray[position] * orderMultiArray[position];
            sumShakes = 0;
            for (int i = 0; i < orderMultiArray.length; i++) {
                sumShakes += orderMultiArray[i];
            }
            //重新设置购买计划
            mMoneyTextView.setText("共" + sumShakes + "注，" + sumShakes * 2 + "元");
            //重新设置奖金范围
            mBonusRangeTextView.setText(optimizeSize());
        }
    };

    private String splitStr() {
        StringBuilder stringBuilder = new StringBuilder();
        // if (playMethod == LotteryTypes.HH) {
        shakes = 0;
        for (int i = 0; i < orderFormList.size(); i++) {
            List<ChoosedContentFormBean> beanList = orderFormList.get(i);
            if (orderMultiArray[i] == 0) {
                continue;
            }
            stringBuilder.append("HH|");
            for (int j = 0; j < beanList.size(); j++) {
                String playCode = JcPlayMethod.getPlayMethod(beanList.get(j).getContent());
                String content = split(beanList.get(j), playCode);
                if ("RQSPF".equals(playCode)) {
                    stringBuilder.append(playCode + ">" + beanList.get(j).getMid() + "=" + content
                            + "{" + beanList.get(j).getLet() + "}(" + beanList.get(j).getOdds() + ")");
                } else {
                    stringBuilder.append(playCode + ">" + beanList.get(j).getMid() + "=" + content
                            + "(" + beanList.get(j).getOdds() + ")");
                }
                if (j == beanList.size() - 1) {
                    stringBuilder.append("|" + beanList.size() + "*1_" + orderMultiArray[i]);
                } else {
                    stringBuilder.append(",");
                }
            }
            if (i < orderFormList.size() - 1) {
                stringBuilder.append(";");
            }
            shakes++;
        }
       /* } else {
            for (int i = 0; i < orderFormList.size(); i++) {
                List<ChoosedContentFormBean> beanList = orderFormList.get(i);
                for (int j = 0; j < beanList.size(); j++) {
                    String playCode = JcPlayMethod.getPlayMethod(beanList.get(j).getContent());
                    String content = split(beanList.get(j), playCode);
                    stringBuilder.append(playCode + "|" + beanList.get(j).getMid() + "=" + content
                            + "(" + beanList.get(j).getOdds() + ")");
                    if (j == beanList.size() - 1) {
                        stringBuilder.append("|" + beanList.size() + "*1_" + orderMultiArray[i]);
                    } else {
                        stringBuilder.append(",");
                    }
                }
                if (i < orderFormList.size() - 1) {
                    stringBuilder.append(";");
                }
            }
        }*/
        return String.valueOf(stringBuilder);
    }

    private String split(ChoosedContentFormBean mChoosedContentFormBean, String playCode) {
        String content;
        // JczqSpliteData mData = new JczqSpliteData();
        switch (playCode) {
            case "SPF":
                content = LotteryTypes.getSpfByStr(mChoosedContentFormBean.getContent());
                // mData.setTitle(content);
                break;
            case "RQSPF":
                content = LotteryTypes.getRqSpfByStr(mChoosedContentFormBean.getContent());

                // mData.setLet(mChoosedContentFormBean.getLet());
                break;
            case "JQS":
                content = mChoosedContentFormBean.getContent();
                break;
            case "CBF":
                if (mChoosedContentFormBean.getContent().equals("胜其他")) {
                    content = "9:0";
                } else if (mChoosedContentFormBean.getContent().equals("平其他")) {
                    content = "9:9";
                } else if (mChoosedContentFormBean.getContent().equals("负其他")) {
                    content = "0:9";
                } else {
                    content = mChoosedContentFormBean.getContent();
                }
                break;
            case "BQC":
                content = LotteryTypes.getBqcByStr(mChoosedContentFormBean.getContent());
                break;
            default:
                content = null;
                break;
        }
        return content;
    }

    private void checkAt(int id) {
        if (id == this.mOptionWinStable.getId()) {
            this.mOptionWinStable.setBackgroundResource(R.drawable.sel_btn_left_pressed);
            this.mOptionWinStable.setTextColor(Color.WHITE);
            this.mOptionWinEasy.setBackgroundResource(R.drawable.sel_btn_middle_normal);
            this.mOptionWinEasy.setTextColor(getResources().getColor(R.color.red_txt));
            this.mOptionWinHigh.setBackgroundResource(R.drawable.sel_btn_right_normal);
            this.mOptionWinHigh.setTextColor(getResources().getColor(R.color.red_txt));
            mHint.setText(getString(R.string.hint_average));
            setAverageMultiple();
            mAdapter.notifyDataSetChanged();
        } else if (id == this.mOptionWinHigh.getId()) {
            this.mOptionWinStable.setBackgroundResource(R.drawable.sel_btn_left_normal);
            this.mOptionWinStable.setTextColor(getResources().getColor(R.color.red_txt));
            this.mOptionWinEasy.setBackgroundResource(R.drawable.sel_btn_middle_normal);
            this.mOptionWinEasy.setTextColor(getResources().getColor(R.color.red_txt));
            this.mOptionWinHigh.setBackgroundResource(R.drawable.sel_btn_right_pressed);
            this.mOptionWinHigh.setTextColor(Color.WHITE);
            mHint.setText(getString(R.string.hint_winhigh));
            setOptionWinHigh();
            mAdapter.notifyDataSetChanged();
        } else if (id == this.mOptionWinEasy.getId()) {
            this.mOptionWinStable.setBackgroundResource(R.drawable.sel_btn_left_normal);
            this.mOptionWinStable.setTextColor(getResources().getColor(R.color.red_txt));
            this.mOptionWinEasy.setBackgroundResource(R.drawable.sel_btn_middle_pressed);
            this.mOptionWinEasy.setTextColor(Color.WHITE);
            this.mOptionWinHigh.setBackgroundResource(R.drawable.sel_btn_right_normal);
            this.mOptionWinHigh.setTextColor(getResources().getColor(R.color.red_txt));
            mHint.setText(getString(R.string.hint_wineasy));
            setOptinWinEasy();
            mAdapter.notifyDataSetChanged();
        }
    }

    //平稳盈利
    private void setAverageMultiple() {
        //平稳盈利时候平均的奖金，准备倍数的数据
        averagePrize = sumShakes / sum;
        //Log.e("sumShakes", sumShakes + "&&&&" + sum);
        int summ = 0;
        for (int i = 1; i < orderFormList.size(); i++) {
            int rint = (int) Math.rint(averagePrize / orderOddsArray[i]);
            //不能出现倍数为0的现象
            if (rint <= 0) {
                rint = 1;
            }
            summ += rint;
            orderMultiArray[i] = rint;
            //Log.e("rint", rint + "");
            moneyArray[i] = orderMultiArray[i] * orderOddsArray[i];
        }
        orderMultiArray[0] = sumShakes - summ;
        moneyArray[0] = orderMultiArray[0] * orderOddsArray[0];
        String optimize = optimizeSize();
        mBonusRangeTextView.setText(optimize);
    }

    //奖金最高
    private void setOptionWinHigh() {
        int summ = 0;
        for (int i = 0; i < formList.size(); i++) {
            orderMultiArray[i] = (int) Math.ceil(sumShakes * 2 / orderOddsArray[i]);
            summ += orderMultiArray[i];
        }
        if (summ > sumShakes) {
            pass(summ);
            int mutils = 0;
            for (int i = 0; i < orderMultiArray.length; i++) {
                mutils += orderMultiArray[i];
            }
            if (mutils < sumShakes) {
                orderMultiArray[orderMultiArray.length - 1] = orderMultiArray[orderMultiArray.length - 1] + sumShakes - mutils;
            }
            for (int i = 0; i < orderMultiArray.length; i++) {
                moneyArray[i] = orderMultiArray[i] * orderOddsArray[i];
            }
            mBonusRangeTextView.setText(optimizeSize());
            //orderMultiArray[formList.size() - 1] = orderMultiArray[formList.size() - 1] + summ - sumShakes;
        } else {
            normalHigh();
        }
    }

    private void normalHigh() {
        int summ = 0;
        for (int i = 0; i < formList.size() - 1; i++) {
            orderMultiArray[i] = (int) Math.ceil(sumShakes * 2 / orderOddsArray[i]);
            moneyArray[i] = orderMultiArray[i] * orderOddsArray[i];
            summ += orderMultiArray[i];
            //Log.e("奖金最高每个条目", moneyArray[i] + "");
        }
        orderMultiArray[formList.size() - 1] = sumShakes - summ;
        moneyArray[formList.size() - 1] = orderMultiArray[formList.size() - 1] * orderOddsArray[formList.size() - 1];
        String optimize = optimizeSize();
        mBonusRangeTextView.setText(optimize);
    }

    //中奖最易
    private void setOptinWinEasy() {
        int summ = 0;
        for (int i = 0; i < formList.size(); i++) {
            orderMultiArray[i] = (int) Math.ceil(sumShakes * 2 / orderOddsArray[i]);
            summ += orderMultiArray[i];
        }
        if (summ > sumShakes) {
            pass(summ);
            int mutils = 0;
            for (int i = 0; i < orderMultiArray.length; i++) {
                mutils += orderMultiArray[i];
            }
            if (mutils < sumShakes) {
                orderMultiArray[0] = orderMultiArray[0] + sumShakes - mutils;
            }
            for (int i = 0; i < orderMultiArray.length; i++) {
                moneyArray[i] = orderMultiArray[i] * orderOddsArray[i];
            }
            mBonusRangeTextView.setText(optimizeSize());
            // orderMultiArray[0] = orderMultiArray[0] + summ - sumShakes;
        } else {
            normalEasy();
        }
    }

    private void normalEasy() {
        int summ = 0;
        for (int i = 1; i < formList.size(); i++) {
            orderMultiArray[i] = (int) Math.ceil(sumShakes * 2 / orderOddsArray[i]);
            summ += orderMultiArray[i];
            moneyArray[i] = orderMultiArray[i] * orderOddsArray[i];
        }
        orderMultiArray[0] = sumShakes - summ;
        moneyArray[0] = orderMultiArray[0] * orderOddsArray[0];
        String optimize = optimizeSize();
        mBonusRangeTextView.setText(optimize);
    }

    private void pass(int summ) {
        int diff;
        // 超出预算倍数
        double bl = sumShakes / summ, cur, sd;
        diff = summ - sumShakes;
        for (int i = 0, j = orderMultiArray.length; i < j; i++) {
            cur = Math.floor(orderMultiArray[i] * bl);
            sd = Math.min(diff, Math.max(1, orderMultiArray[i] - cur));
            if (sd < orderMultiArray[i]) {
                int temp = (int) (orderMultiArray[i] - sd);
                orderMultiArray[i] = temp;
                // orderMultiArray.set(i, temp);
                diff -= sd;
            }
            if (diff <= 0)
                break;
        }
        if (diff > 0) {
            // 如果没有扣清
            while (true) {
                boolean hasOp = false;
                for (int i = 0, j = orderMultiArray.length; i < j; i++) {
                    if (orderMultiArray[i] > 1) {
                        int temp = orderMultiArray[i] - 1;
                        orderMultiArray[i] = temp;
                        diff--;
                        hasOp = true;
                    }
                    if (diff <= 0) {
                        break;
                    }
                }
                if (!hasOp) {
                    break;
                }
            }
        }

    }


    private void loading() {
        mLoadingDialog = new LoadingDialog(BonusOptimizeActivity.this);
        mLoadingDialog.show();
        WindowManager.LayoutParams lp = mLoadingDialog.getWindow().getAttributes();
        lp.alpha = 0.8f;
        mLoadingDialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.mOptionWinEasy.getId() || v.getId() == this.mOptionWinHigh.getId() || v.getId() == this.mOptionWinStable.getId()) {
            checkAt(v.getId());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
