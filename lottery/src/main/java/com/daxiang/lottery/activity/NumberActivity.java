package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.cxinterface.ContinueInterface;
import com.daxiang.lottery.fragment.RuleFragment;
import com.daxiang.lottery.fragment.ballfragment.BallLotteryResultFragment;
import com.daxiang.lottery.fragment.ballfragment.HallFragment;
import com.daxiang.lottery.fragment.commonfragment.RecommendComFragment;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumberActivity extends BaseActivity {
    //private TitleBar mTitleBar;
    private ImageView mBack;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTextTitle;
    List<Fragment> mFragmentList;
    List<String> mTitleList;
    private DltViewPagerAdapter mDltViewPagerAdapter;
    private String mLotcode;
    private HallFragment mHallFragment;
    //是否从我的彩票随机选择大乐透跳转过来
    private boolean whereFlag;
    //精彩发现 跳转过来
    private String who;
    Intent intent;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initContentView(R.layout.activity_number);
        mContext = this;
        intent = getIntent();
        mLotcode = intent.getStringExtra("lotcode");
        whereFlag = intent.getBooleanExtra("whereFlag", true);
        who = intent.getStringExtra("who");
        initView();
        //viewpager数据源
        addFragment();
        //创建适配器
        mDltViewPagerAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mDltViewPagerAdapter);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        //tab_FindFragment_title.set
        //设置监听
        setListener();
        if ("result".equals(who)) {
            mViewPager.setCurrentItem(2);
            mTitleBar.setLlVisibility(false);
            mTitleBar.setTitleVisibility(true);
            // mTitleBar.setTitleSize(20);
            mTextTitle.setText(mTitleList.get(2));
        }
    }

    private PopupWindow mPopupWindow;

    private void dialogSelect() {
        View inflate = View.inflate(this, R.layout.dialog_select, null);
        Button btnZhixuan = (Button) inflate.findViewById(R.id.zhixuan);
        Button btnZusan = (Button) inflate.findViewById(R.id.zusan);
        Button btnZuliu = (Button) inflate.findViewById(R.id.zuliu);
        View mYinying = inflate.findViewById(R.id.yinying);
        btnZhixuan.setOnClickListener(new OnClick());
        btnZusan.setOnClickListener(new OnClick());
        btnZuliu.setOnClickListener(new OnClick());
        mYinying.setOnClickListener(DismissPopup);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    private ColorDrawable cd;

    private void dialogDantuo() {
        View inflate = View.inflate(NumberActivity.this, R.layout.dialog_dantuo, null);
        Button btnNormal = (Button) inflate.findViewById(R.id.normal);
        Button btnDantuo = (Button) inflate.findViewById(R.id.dantuo);
        View mYinying = inflate.findViewById(R.id.yinying);
        btnNormal.setOnClickListener(new OnClick());
        btnDantuo.setOnClickListener(new OnClick());
        mYinying.setOnClickListener(DismissPopup);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        // mPopupWindow.setAnimationStyle(R.style.anim_popup_top);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    private void dialogCqss() {
        View inflate = View.inflate(NumberActivity.this, R.layout.dialog_cqss_select, null);
        LinearLayout mLl_one_one = (LinearLayout) inflate.findViewById(R.id.ll_one_one);
        TextView mOne_one = (TextView) inflate.findViewById(R.id.one_one);
        LinearLayout mLl_one_two = (LinearLayout) inflate.findViewById(R.id.ll_one_two);
        TextView mOne_two = (TextView) inflate.findViewById(R.id.one_two);
        LinearLayout mLl_one_three = (LinearLayout) inflate.findViewById(R.id.ll_one_three);
        TextView mOne_three = (TextView) inflate.findViewById(R.id.one_three);
        LinearLayout mLl_two_one = (LinearLayout) inflate.findViewById(R.id.ll_two_one);
        TextView mTwo_one = (TextView) inflate.findViewById(R.id.two_one);
        LinearLayout mLl_two_two = (LinearLayout) inflate.findViewById(R.id.ll_two_two);
        TextView mTwo_two = (TextView) inflate.findViewById(R.id.two_two);
        LinearLayout mLl_two_three = (LinearLayout) inflate.findViewById(R.id.ll_two_three);
        TextView mTwo_three = (TextView) inflate.findViewById(R.id.two_three);
        LinearLayout mLl_three_one = (LinearLayout) inflate.findViewById(R.id.ll_three_one);
        TextView mThree_one = (TextView) inflate.findViewById(R.id.three_one);
        LinearLayout mLl_three_two = (LinearLayout) inflate.findViewById(R.id.ll_three_two);
        TextView mThree_two = (TextView) inflate.findViewById(R.id.three_two);
        LinearLayout mLl_three_three = (LinearLayout) inflate.findViewById(R.id.ll_three_three);
        TextView mThree_three = (TextView) inflate.findViewById(R.id.three_three);
        View mYinying = inflate.findViewById(R.id.yinying);
        mLl_one_one.setOnClickListener(new OnClick());
        mLl_one_two.setOnClickListener(new OnClick());
        mLl_one_three.setOnClickListener(new OnClick());
        mLl_two_one.setOnClickListener(new OnClick());
        mLl_two_two.setOnClickListener(new OnClick());
        mLl_two_three.setOnClickListener(new OnClick());
        mLl_three_one.setOnClickListener(new OnClick());
        mLl_three_two.setOnClickListener(new OnClick());
        mLl_three_three.setOnClickListener(new OnClick());
        mYinying.setOnClickListener(DismissPopup);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        // mPopupWindow.setAnimationStyle(R.style.anim_popup_top);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    private void dialogJxks() {
        View inflate = View.inflate(NumberActivity.this, R.layout.dialog_jxks_select, null);
        LinearLayout mLl_one_one = (LinearLayout) inflate.findViewById(R.id.ll_one_oneks);
        TextView mOne_one = (TextView) inflate.findViewById(R.id.one_oneks);
        LinearLayout mLl_one_two = (LinearLayout) inflate.findViewById(R.id.ll_one_twoks);
        TextView mOne_two = (TextView) inflate.findViewById(R.id.one_twoks);
        LinearLayout mLl_one_three = (LinearLayout) inflate.findViewById(R.id.ll_one_threeks);
        TextView mOne_three = (TextView) inflate.findViewById(R.id.one_threeks);
        LinearLayout mLl_two_one = (LinearLayout) inflate.findViewById(R.id.ll_two_oneks);
        TextView mTwo_one = (TextView) inflate.findViewById(R.id.two_oneks);
        LinearLayout mLl_two_two = (LinearLayout) inflate.findViewById(R.id.ll_two_twoks);
        TextView mTwo_two = (TextView) inflate.findViewById(R.id.two_twoks);
        LinearLayout mLl_two_three = (LinearLayout) inflate.findViewById(R.id.ll_two_threeks);
        TextView mTwo_three = (TextView) inflate.findViewById(R.id.two_threeks);
        LinearLayout mLl_three_one = (LinearLayout) inflate.findViewById(R.id.ll_three_oneks);
        TextView mThree_one = (TextView) inflate.findViewById(R.id.three_oneks);
        LinearLayout mLl_three_two = (LinearLayout) inflate.findViewById(R.id.ll_three_twoks);
        TextView mThree_two = (TextView) inflate.findViewById(R.id.three_twoks);
        View mYinying = inflate.findViewById(R.id.yinying);
        mLl_one_one.setOnClickListener(new OnClick());
        mLl_one_two.setOnClickListener(new OnClick());
        mLl_one_three.setOnClickListener(new OnClick());
        mLl_two_one.setOnClickListener(new OnClick());
        mLl_two_two.setOnClickListener(new OnClick());
        mLl_two_three.setOnClickListener(new OnClick());
        mLl_three_one.setOnClickListener(new OnClick());
        mLl_three_two.setOnClickListener(new OnClick());
        mYinying.setOnClickListener(DismissPopup);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        // mPopupWindow.setAnimationStyle(R.style.anim_popup_top);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    View.OnClickListener DismissPopup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPopupWindow != null && mPopupWindow.isShowing()) mPopupWindow.dismiss();
        }
    };

    private void dialogElevenSelect() {
        View inflate = View.inflate(NumberActivity.this, R.layout.dialog_eleven_select, null);
        TextView btnRandomTwo = (TextView) inflate.findViewById(R.id.random_two);
        TextView btnRandomThree = (TextView) inflate.findViewById(R.id.random_three);
        TextView btnRandomFour = (TextView) inflate.findViewById(R.id.random_four);
        TextView btnRandomFive = (TextView) inflate.findViewById(R.id.random_five);
        TextView btnRandomSix = (TextView) inflate.findViewById(R.id.random_six);
        TextView btnRandomSeven = (TextView) inflate.findViewById(R.id.random_seven);
        TextView btnRandomEight = (TextView) inflate.findViewById(R.id.random_eight);
        TextView btnFrontOne = (TextView) inflate.findViewById(R.id.front_one);
        TextView btnFrontTwo = (TextView) inflate.findViewById(R.id.front_two);
        TextView btnFrontThree = (TextView) inflate.findViewById(R.id.front_three);
        TextView btnFrontTwoGroup = (TextView) inflate.findViewById(R.id.front_two_group);
        TextView btnFrontThreeGroup = (TextView) inflate.findViewById(R.id.front_three_group);
        View mYinying = inflate.findViewById(R.id.yinying);
        btnRandomTwo.setOnClickListener(new OnClick());
        btnRandomThree.setOnClickListener(new OnClick());
        btnRandomFour.setOnClickListener(new OnClick());
        btnRandomFive.setOnClickListener(new OnClick());
        btnRandomSix.setOnClickListener(new OnClick());
        btnRandomSeven.setOnClickListener(new OnClick());
        btnRandomEight.setOnClickListener(new OnClick());
        btnFrontOne.setOnClickListener(new OnClick());
        btnFrontTwo.setOnClickListener(new OnClick());
        btnFrontThree.setOnClickListener(new OnClick());
        btnFrontTwoGroup.setOnClickListener(new OnClick());
        btnFrontThreeGroup.setOnClickListener(new OnClick());
        mYinying.setOnClickListener(DismissPopup);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_dlt);
        // mViewPager.setOffscreenPageLimit(3);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        // mTitleBar = (TitleBar) findViewById(R.id.text_daletou);
        if ("33".equals(mLotcode) || "52".equals(mLotcode)) {
            mTitleBar.setLlVisibility(true);
            mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "直选");
            final LinearLayout mLlTitleBar = (LinearLayout) mTitleBar.findViewById(R.id.ll_titlebar);
            dialogSelect();
            mLlTitleBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop(mLlTitleBar);
                }
            });
        } else if ("23529".equals(mLotcode) || "51".equals(mLotcode) || "23528".equals(mLotcode)) {
            //双色球 大乐透 七乐彩（这三个彩种有胆拖）
            mTitleBar.setLlVisibility(true);
            mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "普通");
            final LinearLayout mLlTitleBar = (LinearLayout) mTitleBar.findViewById(R.id.ll_titlebar);
            dialogDantuo();
            mLlTitleBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop(mLlTitleBar);
                    // setAlpha(0.6f);

                }
            });
        } else if ("21406".equals(mLotcode)) {
            mTitleBar.setLlVisibility(true);
            mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选二");
            final LinearLayout mLlTitleBar = (LinearLayout) mTitleBar.findViewById(R.id.ll_titlebar);
            dialogElevenSelect();
            mLlTitleBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop(mLlTitleBar);
                }
            });
        } else if ("50".equals(mLotcode)) {//重庆时时彩
            mTitleBar.setLlVisibility(true);
            mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "大小单双");
            final LinearLayout mLlTitleBar = (LinearLayout) mTitleBar.findViewById(R.id.ll_titlebar);
            dialogCqss();
            mLlTitleBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop(mLlTitleBar);
                }
            });
        } else if ("36".equals(mLotcode)) {//江西快三
            mTitleBar.setLlVisibility(true);
            mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "和值");
            final LinearLayout mLlTitleBar = (LinearLayout) mTitleBar.findViewById(R.id.ll_titlebar);
            dialogJxks();
            mLlTitleBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop(mLlTitleBar);
                }
            });
        } else {
            mTitleBar.setTitle(LotteryTypes.getTypes(mLotcode));
            mTitleBar.setTitleVisibility(true);
        }
        mTitleBar.setImageTitleVisibility(false);
        mBack = (ImageView) mTitleBar.findViewById(R.id.image_titlebar_back);
        mTextTitle = (TextView) mTitleBar.findViewById(R.id.titlebar);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("投注大厅");
        mTitleList.add("推荐中心");
        mTitleList.add("开奖结果");
        mTitleList.add("玩法介绍");
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTabLayout, DisplayUtil.dip2px(10), DisplayUtil.dip2px(10));
            }
        });
    }

    private void showPop(LinearLayout mLlTitleBar) {
        if (Build.VERSION.SDK_INT < 24) {
            mPopupWindow.showAsDropDown(mLlTitleBar);
        } else {
            int[] location = new int[2];
            mLlTitleBar.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            mPopupWindow.showAtLocation(mLlTitleBar, Gravity.NO_GRAVITY, 0, y + mLlTitleBar.getHeight());
        }
    }

    PopupWindow.OnDismissListener poponDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setAlpha(1);
        }
    };

    private void setAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    private void addFragment() {
        //如果是从我的彩票跳转过来的
        if (!whereFlag) {
            ArrayList<Integer> listRed = (ArrayList<Integer>) intent.getSerializableExtra("listRed");
            ArrayList<Integer> listBlue = (ArrayList<Integer>) intent.getSerializableExtra("listBlue");
            HashMap<Integer, List<Integer>> randomMap = new HashMap<>();
            randomMap.put(0, listRed);
            randomMap.put(1, listBlue);
            mHallFragment = new HallFragment();
            mHallFragment.setData(mLotcode, randomMap);
        } else {
            if ("continue".equals(who)) {
                String mBuycontent = intent.getStringExtra("mBuycontent");
                final String[] split = mBuycontent.split("\\;")[0].split("\\:");
                mHallFragment = new HallFragment();
                mHallFragment.setData(mLotcode, mBuycontent, new ContinueInterface() {

                    @Override
                    public void callBack() {
                        setCuntinue(split[1] + ":" + split[2]);
                    }
                });

            } else {
                mHallFragment = new HallFragment();
                mHallFragment.setData(mLotcode);
            }
        }
        RecommendComFragment mRecommendFragment = new RecommendComFragment();
        mRecommendFragment.setData(mLotcode, mContext);
        BallLotteryResultFragment mLotteryResultFragment = new BallLotteryResultFragment();
        mLotteryResultFragment.setData(mLotcode, mContext);
        RuleFragment mRuleFragment = new RuleFragment();
        mRuleFragment.setData(mLotcode, mContext);
        mFragmentList.add(mHallFragment);
        mFragmentList.add(mRecommendFragment);
        mFragmentList.add(mLotteryResultFragment);
        mFragmentList.add(mRuleFragment);
    }

    private void setListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    //如果是第一个fragment那么就注册震动
                    mHallFragment.setVibrator(true);
                    if ("33".equals(mLotcode) || "52".equals(mLotcode) || "21406".equals(mLotcode) || "23529".equals(mLotcode) || "51".equals(mLotcode) || "23528".equals(mLotcode)) {
                        mTitleBar.setLlVisibility(true);
                        mTitleBar.setTitleVisibility(false);
                    }
                    mTextTitle.setText(LotteryTypes.getTypes(mLotcode));

                } else {
                    //不是第一个就注销震动
                    mHallFragment.setVibrator(false);
                    if ("33".equals(mLotcode) || "52".equals(mLotcode) || "21406".equals(mLotcode) || "23529".equals(mLotcode) || "51".equals(mLotcode) || "23528".equals(mLotcode)) {
                        mTitleBar.setLlVisibility(false);
                        mTitleBar.setTitleVisibility(true);
                        //mTitleBar.setTitleSize(20);
                    }
                    mTextTitle.setText(mTitleList.get(position));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.zhixuan:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "直选");
                    mHallFragment.setData(3, 10, 1, "1:1", ConstantNum.ZHI_XUAN);
                    break;
                case R.id.zusan:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "组三");
                    mHallFragment.setData(1, 10, 2, "2:3", ConstantNum.ZU_3);
                    break;
                case R.id.zuliu:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "组六");
                    mHallFragment.setData(1, 10, 3, "3:3", ConstantNum.ZU_6);
                    break;
                case R.id.random_two:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选二");
                    mHallFragment.setData(1, 11, 2, "02:01", ConstantNum.REN_XUAN_2);
                    break;
                case R.id.random_three:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选三");
                    mHallFragment.setData(1, 11, 3, "03:01", ConstantNum.REN_XUAN_3);
                    break;
                case R.id.random_four:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选四");
                    mHallFragment.setData(1, 11, 4, "04:01", ConstantNum.REN_XUAN_4);
                    break;
                case R.id.random_five:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选五");
                    mHallFragment.setData(1, 11, 5, "05:01", ConstantNum.REN_XUAN_5);
                    break;
                case R.id.random_six:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选六");
                    mHallFragment.setData(1, 11, 6, "06:01", ConstantNum.REN_XUAN_6);
                    break;
                case R.id.random_seven:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选七");
                    mHallFragment.setData(1, 11, 7, "07:01", ConstantNum.REN_XUAN_7);
                    break;
                case R.id.random_eight:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选八");
                    mHallFragment.setData(1, 11, 8, "08:01", ConstantNum.REN_XUAN_8);
                    break;
                case R.id.front_one:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前一");
                    mHallFragment.setData(1, 11, 1, "01:01", ConstantNum.QIAN_1);
                    break;
                case R.id.front_two:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前二直选");
                    mHallFragment.setData(2, 11, 1, "09:01", ConstantNum.QIAN_2_ZHI_XUAN);
                    break;
                case R.id.front_three:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前三直选");
                    mHallFragment.setData(3, 11, 1, "10:01", ConstantNum.QIAN_3_ZHI_XUAN);
                    break;
                case R.id.front_two_group:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前二组选");
                    mHallFragment.setData(1, 11, 2, "11:01", ConstantNum.QIAN_2_ZU_XUAN);
                    break;
                case R.id.front_three_group:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前三组选");
                    mHallFragment.setData(1, 11, 3, "12:01", ConstantNum.QIAN_3_ZU_XUAN);
                    break;
                case R.id.ll_one_one:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "大小单双");
                    mHallFragment.setData(2, 4, 1, "10:1", ConstantNum.DA_XIAO_DAN_SHUANG);
                    break;
                case R.id.ll_one_two:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "五星通选");
                    mHallFragment.setData(5, 10, 1, "9:1", ConstantNum.WU_XING_TONG);
                    break;
                case R.id.ll_one_three:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "五星直选");
                    mHallFragment.setData(5, 10, 1, "5:1", ConstantNum.WU_XING_ZHI);
                    break;
                case R.id.ll_two_one:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "三星直选");
                    mHallFragment.setData(3, 10, 1, "3:1", ConstantNum.SAN_XING_ZHI);
                    break;
                case R.id.ll_two_two:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "三星组三");
                    mHallFragment.setData(1, 10, 2, "7:3", ConstantNum.SAN_XING_ZU_3);
                    break;
                case R.id.ll_two_three:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "三星组六");
                    mHallFragment.setData(1, 10, 3, "8:3", ConstantNum.SAN_XING_ZU_6);
                    break;
                case R.id.ll_three_one:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "二星组选");
                    mHallFragment.setData(1, 10, 2, "6:1", ConstantNum.ER_XING_ZU);
                    break;
                case R.id.ll_three_two:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "二星直选");
                    mHallFragment.setData(2, 10, 1, "2:1", ConstantNum.ER_XING_ZHI);
                    break;
                case R.id.ll_three_three:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "一星直选");
                    mHallFragment.setData(1, 10, 1, "1:1", ConstantNum.YI_XING_ZHI);
                    break;
                case R.id.ll_one_oneks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "和值");
                    mHallFragment.setData(0, 0, 0, "1", ConstantNum.HE_ZHI);
                    break;
                case R.id.ll_one_twoks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "三同号");
                    mHallFragment.setData(0, 0, 0, "2", ConstantNum.SAN_TONG_HAO);
                    break;
                case R.id.ll_one_threeks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "三不同号");
                    mHallFragment.setData(0, 0, 0, "3", ConstantNum.SAN_BU_TONG_HAO);
                    break;
                case R.id.ll_two_oneks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "三连号通选");
                    mHallFragment.setData(0, 0, 0, "4", ConstantNum.SAN_LIAN_HAO_TONG);
                    break;
                case R.id.ll_two_twoks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "二同号单选");
                    mHallFragment.setData(0, 0, 0, "5", ConstantNum.ER_TONG_HAO_DAN);
                    break;
                case R.id.ll_two_threeks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "二同号复选");
                    mHallFragment.setData(0, 0, 0, "6", ConstantNum.ER_TONG_HAO_FU);
                    break;
                case R.id.ll_three_oneks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "二不同号");
                    mHallFragment.setData(0, 0, 0, "7", ConstantNum.ER_BU_TONG_HAO);
                    break;
                case R.id.ll_three_twoks:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "三同号通选");
                    mHallFragment.setData(0, 0, 0, "8", ConstantNum.SAN_TONG_HAO_TONG);
                    break;
                case R.id.normal:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "普通");
                    mHallFragment.setDataDan("1:1", false, ConstantNum.PU_TONG);
                    break;
                case R.id.dantuo:
                    mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "胆拖");
                    mHallFragment.setDataDan("135:5", true, ConstantNum.DAN_TUO);
                    break;

            }
            mPopupWindow.dismiss();
        }
    }

    private void setCuntinue(String playmethod) {
        switch (playmethod) {
            case "2:3":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "组三");
                mHallFragment.setData(1, 10, 2, "2:3", ConstantNum.ZU_3);
                break;
            case "3:3":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "组六");
                mHallFragment.setData(1, 10, 3, "3:3", ConstantNum.ZU_6);
                break;
            case "02:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选二");
                mHallFragment.setData(1, 11, 2, "02:01", ConstantNum.REN_XUAN_2);
                break;
            case "03:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选三");
                mHallFragment.setData(1, 11, 3, "03:01", ConstantNum.REN_XUAN_3);
                break;
            case "04:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选四");
                mHallFragment.setData(1, 11, 4, "04:01", ConstantNum.REN_XUAN_4);
                break;
            case "05:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选五");
                mHallFragment.setData(1, 11, 5, "05:01", ConstantNum.REN_XUAN_5);
                break;
            case "06:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选六");
                mHallFragment.setData(1, 11, 6, "06:01", ConstantNum.REN_XUAN_6);
                break;
            case "07:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选七");
                mHallFragment.setData(1, 11, 7, "07:01", ConstantNum.REN_XUAN_7);
                break;
            case "08:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "任选八");
                mHallFragment.setData(1, 11, 8, "08:01", ConstantNum.REN_XUAN_8);
                break;
            case "01:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前一");
                mHallFragment.setData(1, 11, 1, "01:01", ConstantNum.QIAN_1);
                break;
            case "09:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前二直选");
                mHallFragment.setData(2, 11, 1, "09:01", ConstantNum.QIAN_2_ZHI_XUAN);
                break;
            case "10:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前三直选");
                mHallFragment.setData(3, 11, 1, "10:01", ConstantNum.QIAN_3_ZHI_XUAN);
                break;
            case "11:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前二组选");
                mHallFragment.setData(1, 11, 2, "11:01", ConstantNum.QIAN_2_ZU_XUAN);
                break;
            case "12:01":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "前三组选");
                mHallFragment.setData(1, 11, 3, "12:01", ConstantNum.QIAN_3_ZU_XUAN);
                break;
            case "135:5":
                mTitleBar.setLlText(LotteryTypes.getTypes(mLotcode) + "-" + "胆拖");
                mHallFragment.setDataDan("135:5", true, ConstantNum.DAN_TUO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 100) {//点击继续购彩
            if (mHallFragment != null) {
                mHallFragment.paymentFinish();
            }
        }
    }
}
