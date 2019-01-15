package com.daxiang.lottery.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.adapter.wonderfuladapter.GendanAdapter;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.KeyBardInterface;
import com.daxiang.lottery.entity.GodInfoData;
import com.daxiang.lottery.entity.GodInfoNumData;
import com.daxiang.lottery.entity.RecommendBaseData;
import com.daxiang.lottery.entity.TogetherAndFollowData;
import com.daxiang.lottery.forum.activity.MessageActivity;
import com.daxiang.lottery.forum.adapter.PostRvAdapter;
import com.daxiang.lottery.forum.bean.PostBean;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.KeyboardUtil;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.MyKeyBoardView;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.RedBlackView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.daxiang.lottery.R.id.money;

public class GodInfoActivity extends BaseNoTitleActivity {

    private String userId;
    private TitleBar mTitlebar;
    private ImageView mEdite;//编辑
    private TextView mBtnFollow;//关注按钮
    private TextView mFans;//粉丝
    private TextView mFollow;//关注
    private TextView mIntroduce;//简介
    private TextView mAllPrize;
    private ImageView mAvatar;
    private TextView mName;
    private TextView mMaxRed;
    private TextView mMaxBlack;
    private RedBlackView mRedBlackView;
    private LinearLayout mRed_ll;
    private TextView mRed_text;
    private LinearLayout mIng_ll;
    private TextView mIng_text;
    private LinearLayout mBlack_ll;
    private TextView mBlack_text;
    private ImageView mOne;
    private ImageView mTwo;
    private ImageView mThree;
    private ImageView mFour;
    private ImageView mFive;
    private ImageView mSix;
    private ImageView mSeven;
    private ImageView mEight;
    private ImageView mNine;
    private ImageView mTen;
    private LinearLayout mHit_ll;
    private LinearLayout mHit_ll2;
    private View mHit_line;
    private FrameLayout mHit_fl;
    private TextView mIncome;
    private TextView mPeoples;
    private TextView mMoney2;
    private TextView mLianhong;
    private TextView mMingzhong;
    private TextView mHuibao;
    private MyKeyBoardView mKeyBoardView;
    private NoDataView mNoData;
    private BillListView mListView;
    private SmartRefreshLayout mRefresh;
    private TextView mRecommend;
    private TextView mPost;
    private LinearLayout mLlRecommend;
    private LinearLayout mLlPost;
    private RecyclerView mPostRv;
    private NoDataView mNoDataPost;


    private Context mContext;
    private LoadingDialogUtils loadingDialogUtils;
    private GendanAdapter mAdapter;
    private HttpInterface2 mHttp;
    private List<RecommendBaseData> mItemsList = new ArrayList<>();
    private int focusFlag = 0;//0 没有关注，1关注
    private String introduce;//简介
    private NetWorkData mNetWorkData;

    private PostRvAdapter mRvAdapter;
    List<PostBean.DataBean.ListBean> mPostList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_god_info);
        mContext = this;
        mHttp = new HttpUtils2(mContext);
        userId = getIntent().getStringExtra("userId");
        mNetWorkData = new NetWorkData(mContext);
        initView();
        keyboardUtil = new KeyboardUtil(this, mKeyBoardView);
        //推单列表
        mAdapter = new GendanAdapter(mContext, mItemsList, false, KeyBardListener);
        mListView.setAdapter(mAdapter);
        //帖子动态
        mRvAdapter = new PostRvAdapter(mContext, mPostList, moreListener);
        mPostRv.setAdapter(mRvAdapter);

        HttpUtils2.display(mAvatar, Url.HEADER_ROOT_URL + userId);
        loadingDialogUtils = new LoadingDialogUtils(mContext);
        addData();
        recommendData();//推荐的订单
        infoData();//个人简介
        addListener();
    }

    private void initView() {
        mTitlebar = (TitleBar) findViewById(R.id.titlebar_god);
        mTitlebar.setImageTitleVisibility(false);
        mTitlebar.setTitleVisibility(true);
        mTitlebar.setTitle("大神详情");
        mTitlebar.mShare.setImageResource(R.mipmap.bell);
        mTitlebar.setFocusable(true);
        mTitlebar.setFocusableInTouchMode(true);//这三个设置是为了刚进入这个界面的时候不是显示最下面。
        mTitlebar.requestFocus();//参考网址http://blog.csdn.net/jifashihan/article/details/51918345

        mAvatar = (ImageView) findViewById(R.id.avatar);
        mAllPrize = (TextView) findViewById(R.id.allPrize);
        mName = (TextView) findViewById(R.id.name);
        mIncome = (TextView) findViewById(R.id.income);
        mPeoples = (TextView) findViewById(R.id.peoples);
        mMoney2 = (TextView) findViewById(R.id.money2);
        mLianhong = (TextView) findViewById(R.id.lianhong);
        mMingzhong = (TextView) findViewById(R.id.mingzhong);
        mHuibao = (TextView) findViewById(R.id.huibao);
        mListView = (BillListView) findViewById(R.id.listview);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mEdite = (ImageView) findViewById(R.id.edit);
        mBtnFollow = (TextView) findViewById(R.id.btn_follow);
        mFans = (TextView) findViewById(R.id.fans);
        mFollow = (TextView) findViewById(R.id.follow);
        mIntroduce = (TextView) findViewById(R.id.text_introduce);
        mMaxRed = (TextView) findViewById(R.id.maxRed);
        mMaxBlack = (TextView) findViewById(R.id.maxBlack);
        mRedBlackView = (RedBlackView) findViewById(R.id.redBlackView);
        mOne = (ImageView) findViewById(R.id.one);
        mTwo = (ImageView) findViewById(R.id.two);
        mThree = (ImageView) findViewById(R.id.three);
        mFour = (ImageView) findViewById(R.id.four);
        mFive = (ImageView) findViewById(R.id.five);
        mSix = (ImageView) findViewById(R.id.six);
        mSeven = (ImageView) findViewById(R.id.seven);
        mEight = (ImageView) findViewById(R.id.eight);
        mNine = (ImageView) findViewById(R.id.nine);
        mTen = (ImageView) findViewById(R.id.ten);
        mHit_fl = (FrameLayout) findViewById(R.id.hit_fl);
        mHit_line = findViewById(R.id.hit_line);
        mHit_ll = (LinearLayout) findViewById(R.id.hit_ll);
        mHit_ll2 = (LinearLayout) findViewById(R.id.hit_ll2);
        mRed_ll = (LinearLayout) findViewById(R.id.red_ll);
        mRed_text = (TextView) findViewById(R.id.red_text);
        mIng_ll = (LinearLayout) findViewById(R.id.ing_ll);
        mIng_text = (TextView) findViewById(R.id.ing_text);
        mBlack_ll = (LinearLayout) findViewById(R.id.black_ll);
        mBlack_text = (TextView) findViewById(R.id.black_text);


        mRecommend = (TextView) findViewById(R.id.recommend);
        mPost = (TextView) findViewById(R.id.post);
        mLlRecommend = (LinearLayout) findViewById(R.id.ll_recommend);
        mLlPost = (LinearLayout) findViewById(R.id.ll_post);
        mPostRv = (RecyclerView) findViewById(R.id.recyclerView);
        mNoDataPost = (NoDataView) findViewById(R.id.no_data_post);

        LinearLayoutManager linearLayout = new LinearLayoutManager(mContext);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mPostRv.setLayoutManager(linearLayout);

        mPostRv.setHasFixedSize(true);//固定自身size不受adapter变化影响
        //消除滑动卡顿现象 ...
        mPostRv.setNestedScrollingEnabled(false);//限制recyclerview自身滑动特性,滑动全部靠scrollview完成

        /*
        * 点击立即跟单弹出的界面
        * */
        mOne_mutil = (TextView) findViewById(R.id.one_mutil);
        mTwo_mutil = (TextView) findViewById(R.id.two_mutil);
        mThree_mutil = (TextView) findViewById(R.id.three_mutil);
        mFour_mutil = (TextView) findViewById(R.id.four_mutil);
        mLl_keybard = (LinearLayout) findViewById(R.id.ll_keybard);
        mKeyBoardView = (MyKeyBoardView) findViewById(R.id.keyboard_view);
        mCancel = (ImageView) findViewById(R.id.cancel);
        mKeyBoardBg = findViewById(R.id.keyboard_bg);
        mEdit_buy_times = (EditText) findViewById(R.id.edit_buy_times);
        mMoney = (TextView) findViewById(money);
        if (LotteryApp.isLogin) {
            if (userId.equals(LotteryApp.uid)) {
                mEdite.setVisibility(View.VISIBLE);
                mBtnFollow.setVisibility(View.GONE);
            } else {
                mEdite.setVisibility(View.GONE);
                mBtnFollow.setVisibility(View.VISIBLE);
            }
        } else {
            mEdite.setVisibility(View.GONE);
            mBtnFollow.setVisibility(View.GONE);
        }


        inflate = View.inflate(mContext, R.layout.item_popup, null);
        mDelete = (TextView) inflate.findViewById(R.id.buttonCamera);
        mShare = (TextView) inflate.findViewById(R.id.buttonPhoto_selector);
        mCancelPop = (Button) inflate.findViewById(R.id.buttoncancle);
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        window = mDialog.getWindow();
        mDialog.setContentView(inflate);
        mDelete.setText("删除");
        mShare.setText("分享");


    }

    private void addData() {
        mHttp.jsonByUrl(Url.USER_BUYINFO_URL + userId + "/" + ConstantNum.DAYS, new JsonInterface() {
            @Override
            public void callback(String result) {
                // loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                GodInfoData godInfoData = gson.fromJson(result, GodInfoData.class);
                int code = godInfoData.getCode();
                String msg = godInfoData.getMsg();
                if (code == 0) {
                    GodInfoData.DataBean data = godInfoData.getData();
                    String totalTC = data.getTotalTC();//累计提成
                    String userName = data.getUserName();//用户名
                    String allPrize = data.getAllPrize();//累计中奖金额
                    String gdBuyNum = data.getGdBuyNum();//累计跟单人数
                    String gdBuyMoney = data.getGdBuyMoney();//累计跟单金额
                    String contWin = data.getContWin();//连续命中数（连红）
                    String winRate = data.getWinRate();//命中率
                    String gainRate = data.getGainRate();//回报率
                    String totalGD = data.getTotalGD();//近三个月累计跟单金额
                    String gdBuyPrize = data.getGdBuyPrize();//跟单中奖金额
                    mAllPrize.setText(TextUtils.isEmpty(allPrize) ? "--" : StringUtil.getString(allPrize) + "元");
                    mPeoples.setText(TextUtils.isEmpty(gdBuyNum) ? "--" : StringUtil.getString(gdBuyNum) + "人");
                    mMoney2.setText(TextUtils.isEmpty(gdBuyPrize) ? "--" : StringUtil.getString(gdBuyPrize) + "元");
                    if (TextUtils.isEmpty(contWin) || "0".equals(contWin)) {
                        mLianhong.setText("暂无");
                    } else
                        mLianhong.setText(contWin + "连红");
                    mMingzhong.setText(TextUtils.isEmpty(winRate) ? "0.00" : winRate + "%");
                    mHuibao.setText(TextUtils.isEmpty(gainRate) ? "0.00" : gainRate + "%");
                    mIncome.setText("近3月累计跟单" + (TextUtils.isEmpty(totalGD) ? "--" : totalGD + "元"));
                    mName.setText(TextUtils.isEmpty(userName) ? "" : userName);
                } else {
                    //  Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                // loadingDialogUtils.setDimiss();
            }
        });
        Bundle params = new Bundle();
        params.putString("userId", userId);
        mHttp.get(Url.GOD_HIT, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int maxContWin = data.getInt("maxContWin");//最长连红
                        int maxContLose = data.getInt("maxContLose");//最长连黑
                        String recentWinInfo = data.getString("recentWinInfo");//由远及近近期中奖情况(0-未开奖；1-未中奖；2-已中奖)
                        mMaxRed.setText("最长连红: " + maxContWin);
                        mMaxBlack.setText("最长连黑: " + maxContLose);
                        if (TextUtils.isEmpty(recentWinInfo)) return;
                        String[] split = recentWinInfo.split("\\,");
                        totalNum = split.length;
                        redNum = 0;
                        blackNum = 0;
                        ingNum = 0;
                        if (split.length < 10) {
                            for (int i = 0; i < 10 - split.length; i++) {
                                setView(i, 3);
                            }
                            for (int i = 10 - split.length; i < 10; i++) {
                                setView(i, Integer.parseInt(split[i + split.length - 10]));
                            }
                        } else {
                            for (int i = 0; i < split.length; i++) {
                                setView(i, Integer.parseInt(split[i]));
                            }
                        }
                        mRedBlackView.setBlackNum(blackNum);
                        mRedBlackView.setRedNum(redNum);
                        mRedBlackView.setIngNum(ingNum);
                        mRedBlackView.setTotalNum(totalNum);
                        if (split.length == 0) {
                            mHit_ll.setVisibility(View.GONE);
                            mHit_ll2.setVisibility(View.GONE);
                            mHit_fl.setVisibility(View.GONE);
                            mHit_line.setVisibility(View.GONE);
                        } else {
                            mHit_ll.setVisibility(View.VISIBLE);
                            mHit_ll2.setVisibility(View.VISIBLE);
                            mHit_fl.setVisibility(View.VISIBLE);
                            mHit_line.setVisibility(View.VISIBLE);
                            if (blackNum == 0) {
                                mBlack_ll.setVisibility(View.GONE);
                            } else {
                                mBlack_ll.setVisibility(View.VISIBLE);
                                mBlack_text.setText("黑" + blackNum + "场");
                            }
                            if (redNum == 0) {
                                mRed_ll.setVisibility(View.GONE);
                            } else {
                                mRed_ll.setVisibility(View.VISIBLE);
                                mRed_text.setText("红" + redNum + "场");
                            }
                            if (ingNum == 0) {
                                mIng_ll.setVisibility(View.GONE);
                            } else {
                                mIng_ll.setVisibility(View.VISIBLE);
                                mIng_text.setText("进行中" + ingNum + "场");
                            }
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

    private void messageNumData() {
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        mHttp.get(Url.POST_MESSAGENUM, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int systemMessageNum = data.getInt("systemMessageNum");
                        int postMessageNum = data.getInt("postMessageNum");
                        if (userId.equals(LotteryApp.uid)) {
                            if (flag == 2) {
                                if ((systemMessageNum + postMessageNum) > 0) {
                                    mTitlebar.mShare.setVisibility(View.VISIBLE);
                                    mTitlebar.mPoint.setVisibility(View.VISIBLE);
                                } else {
                                    mTitlebar.mShare.setVisibility(View.VISIBLE);
                                    mTitlebar.mPoint.setVisibility(View.GONE);
                                }
                            } else {
                                mTitlebar.mShare.setVisibility(View.GONE);
                                mTitlebar.mPoint.setVisibility(View.GONE);
                            }

                        } else {
                            mTitlebar.mShare.setVisibility(View.GONE);
                            mTitlebar.mPoint.setVisibility(View.GONE);
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

    private void infoData() {
        Bundle params = new Bundle();
        params.putString("myUid", TextUtils.isEmpty(LotteryApp.uid) ? "0" : LotteryApp.uid);
        params.putString("targetUid", userId);
        mHttp.get(Url.GOD_INFO_NUM, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                GodInfoNumData godInfoNumData = gson.fromJson(result, GodInfoNumData.class);
                int code = godInfoNumData.getCode();
                if (code == 0) {
                    GodInfoNumData.DataBean data = godInfoNumData.getData();
                    int fansNum = data.getFansNum();//粉丝数量
                    focusFlag = data.getFocusFlag();//0 没有关注，1关注
                    int focusNum = data.getFocusNum();//关注数量
                    introduce = data.getUserDesc();//个人简介
                    mFans.setText("粉丝: " + fansNum);
                    mFollow.setText("关注: " + focusNum);
                    mIntroduce.setText("简介: " + (TextUtils.isEmpty(introduce) ? "" : introduce));
                    if (focusFlag == 0) {
                        mBtnFollow.setText("关注");
                        mBtnFollow.setBackgroundResource(R.drawable.shape_orange_10);
                    } else {
                        mBtnFollow.setText("已关注");
                        mBtnFollow.setBackgroundResource(R.drawable.shape_white_line);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private int pn = 1;
    private int ps = 10;
    private int totalPage = 1;

    private void recommendData() {
        // if (LotteryApp.isLogin) {
        Bundle params = new Bundle();
        params.putString("userId", userId);
        params.putString("pn", pn + "");
        params.putString("ps", ps + "");
        mHttp.get(Url.GOD_INFO_FORM, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                TogetherAndFollowData gendanData = gson.fromJson(result, TogetherAndFollowData.class);
                String msg = gendanData.getMsg();
                if (gendanData.getCode() == 0) {
                    totalPage = gendanData.getData().getTotalPages();
                    List<TogetherAndFollowData.DataBean.ListBean> items = gendanData.getData().getList();
                    mItemsList.addAll(items);
                } else {
                    // Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
                if (mItemsList.size() == 0) {//如果没有正在进行中的那么进来就先显示已完成的
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                if (mItemsList.size() == 0) {//如果没有正在进行中的那么进来就先显示已完成的
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }
        });
    }

    private int pnPost = 1;
    private int psPost = 10;
    private int totalPagePost = 1;

    private void postData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("toUserId", userId);
        params.putString("userId", LotteryApp.uid);
        params.putString("pageIndex", pnPost + "");
        params.putString("pageSize", psPost + "");
        params.putString("formType", "2");
        mHttp.get(Url.POST_FORM, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                PostBean postBean = gson.fromJson(result, PostBean.class);
                String msg = postBean.getMsg();
                if (postBean.getCode() == 0) {
                    PostBean.DataBean data = postBean.getData();
                    totalPagePost = data.getTotalPages();
                    mPostList.addAll(data.getList());
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
                if (mPostList.size() == 0) {
                    mNoDataPost.setVisibility(View.VISIBLE);
                } else {
                    mNoDataPost.setVisibility(View.GONE);
                }
                mRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                loadingDialogUtils.setDimiss();
                if (mPostList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addListener() {
        mTitlebar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAvatar.setOnClickListener(AvatarListener);
        mTitlebar.mShare.setOnClickListener(MessageListener);//举报
        mEdite.setOnClickListener(EditeListener);//编辑
        mBtnFollow.setOnClickListener(BtnFollowListener);//关注按钮
        mFans.setOnClickListener(FansListener);//粉丝
        mFollow.setOnClickListener(FollowListener);//关注的人

        mRecommend.setOnClickListener(TitleListener);
        mPost.setOnClickListener(TitleListener);

        mDelete.setOnClickListener(DeleteListener);
        mShare.setOnClickListener(ShareListener);
        mCancelPop.setOnClickListener(CancelPopListener);

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (flag == 1) {
                    pn = 1;
                    mItemsList.clear();
                    addData();
                    recommendData();
                } else {
                    pnPost = 1;
                    mPostList.clear();
                    addData();
                    postData();
                    messageNumData();
                }

            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (flag == 1) {
                    if (pn < totalPage) {
                        pn++;
                        recommendData();
                    } else {
                        mRefresh.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(mContext, "已经是最后一页", Toast.LENGTH_SHORT).show();
                                mRefresh.finishLoadMore();
                            }
                        }, 1000);
                    }
                } else {
                    if (pnPost < totalPagePost) {
                        pnPost++;
                        postData();
                    } else {
                        mRefresh.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(mContext, "已经是最后一页", Toast.LENGTH_SHORT).show();
                                mRefresh.finishLoadMore();
                            }
                        }, 1000);
                    }
                }

            }
        });
    }

    View.OnClickListener MessageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTitlebar.mPoint.setVisibility(View.GONE);
            Intent intent = new Intent(mContext, MessageActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener EditeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, EditeIntroduceActivity.class);
            intent.putExtra("introduce", introduce);
            startActivityForResult(intent, 666);
        }
    };
    View.OnClickListener BtnFollowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.isLogin) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                return;
            }
            final String url;
            if (focusFlag == 0) {//还没有关注的状态
                url = "add";
            } else {//已关注的状态
                url = "cancel";
            }
            Bundle params = new Bundle();
            params.putString("userId", LotteryApp.uid);
            params.putString("focusUserId", userId);
            mHttp.get(Url.FOCUS_GOD + url, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            if ("add".equals(url)) {
                                focusFlag = 1;
                                mBtnFollow.setText("已关注");
                                mBtnFollow.setBackgroundResource(R.drawable.shape_white_line);
                            } else {
                                focusFlag = 0;
                                mBtnFollow.setText("关注");
                                mBtnFollow.setBackgroundResource(R.drawable.shape_orange_10);
                            }
                            infoData();
                            mNetWorkData.addDeleteTag();
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
    private int flag = 1;//1是推单详情，2是帖子动态
    private boolean isFirst = true;//第一次点击帖子动态需要加载数据
    View.OnClickListener TitleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.recommend) {//推单详情
                if (flag == 2) {
                    flag = 1;
                    mTitlebar.mShare.setVisibility(View.GONE);
                    mTitlebar.mPoint.setVisibility(View.GONE);
                    mRecommend.setTextColor(getResources().getColor(R.color.deep_txt));
                    mRecommend.setBackgroundResource(R.drawable.shape_left_radius_white);
                    mPost.setTextColor(getResources().getColor(R.color.gray_txt));
                    mPost.setBackgroundResource(R.drawable.shape_right_radius_gray);
                    mLlRecommend.setVisibility(View.VISIBLE);
                    mLlPost.setVisibility(View.GONE);
                }

            } else {//帖子动态
                if (flag == 1) {
                    flag = 2;
                    if (userId.equals(LotteryApp.uid)) {
                        mTitlebar.mShare.setVisibility(View.VISIBLE);
                        //mTitlebar.mPoint.setVisibility(View.VISIBLE);
                    }
                    mRecommend.setTextColor(getResources().getColor(R.color.gray_txt));
                    mRecommend.setBackgroundResource(R.drawable.shape_left_radius_gray);
                    mPost.setTextColor(getResources().getColor(R.color.deep_txt));
                    mPost.setBackgroundResource(R.drawable.shape_right_radius_white);
                    mLlRecommend.setVisibility(View.GONE);
                    mLlPost.setVisibility(View.VISIBLE);
                }
                if (isFirst) {
                    isFirst = false;
                    postData();
                }
                messageNumData();
            }
        }
    };
    View.OnClickListener FollowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, FansFollowActivity.class);
            intent.putExtra("userId", userId);//大神的uid
            intent.putExtra("fansfollow", "follow");
            startActivityForResult(intent, 222);
        }
    };
    View.OnClickListener FansListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, FansFollowActivity.class);
            intent.putExtra("userId", userId);//大神的uid
            intent.putExtra("fansfollow", "fans");
            startActivityForResult(intent, 222);
        }
    };
    View.OnClickListener AvatarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (userId == null) return;
            final Dialog dialog = new Dialog(mContext, R.style.dialog_fullscreen);
            ImageView imageView = new ImageView(mContext);
            Picasso.with(mContext)
                    .load(Url.HEADER_ROOT_URL + userId)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.mipmap.default_god_avatar)
                    .error(R.mipmap.default_god_avatar)
                    .into(imageView);
            dialog.setContentView(imageView);
            dialog.setCanceledOnTouchOutside(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    };
    private int totalNum = 0;
    private int redNum = 0;
    private int blackNum = 0;
    private int ingNum = 0;

    private void setView(int position, int type) {//0-未开奖；1-未中奖；2-已中奖;3-没有买
        switch (position) {
            case 0:
                if (type == 0) {
                    mOne.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mOne.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mOne.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mOne.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 1:
                if (type == 0) {
                    mTwo.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mTwo.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mTwo.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mTwo.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 2:
                if (type == 0) {
                    mThree.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mThree.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mThree.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mThree.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 3:
                if (type == 0) {
                    mFour.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mFour.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mFour.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mFour.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 4:
                if (type == 0) {
                    mFive.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mFive.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mFive.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mFive.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 5:
                if (type == 0) {
                    mSix.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mSix.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mSix.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mSix.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 6:
                if (type == 0) {
                    mSeven.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mSeven.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mSeven.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mSeven.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 7:
                if (type == 0) {
                    mEight.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mEight.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mEight.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mEight.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 8:
                if (type == 0) {
                    mNine.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mNine.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mNine.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mNine.setImageResource(R.mipmap.no_buy);
                }
                break;
            case 9:
                if (type == 0) {
                    mTen.setImageResource(R.mipmap.ing_bet);
                    ingNum++;
                } else if (type == 1) {
                    mTen.setImageResource(R.mipmap.black_bet);
                    blackNum++;
                } else if (type == 2) {
                    mTen.setImageResource(R.mipmap.red_bet);
                    redNum++;
                } else if (type == 3) {
                    mTen.setImageResource(R.mipmap.no_buy);
                }
                break;
        }
    }

    private TextView mOne_mutil;
    private TextView mTwo_mutil;
    private TextView mThree_mutil;
    private TextView mFour_mutil;
    private ImageView mCancel;

    private TextView mDelete;
    private TextView mShare;
    private Button mCancelPop;
    private View inflate;
    private Dialog mDialog;
    private Window window;

    private View mKeyBoardBg;
    private LinearLayout mLl_keybard;
    private EditText mEdit_buy_times;
    private TextView mMoney;
    private KeyboardUtil keyboardUtil;
    private double oneMoney;
    private RecommendBaseData listBean;
    private int ver;
    KeyBardInterface KeyBardListener = new KeyBardInterface() {

        @Override
        public void callBack(RecommendBaseData listBea, int position) {
            if (listBea == null) return;
            listBean = listBea;
            String mOneMoney = listBean.getOneMoney();
            if (TextUtils.isEmpty(mOneMoney)) return;
            oneMoney = Double.parseDouble(mOneMoney);
            if (oneMoney < 100) {
                mMulti = 10;
            } else {
                mMulti = 1;
            }
            mLl_keybard.setVisibility(View.VISIBLE);

            View childAt = mListView.getChildAt(position);
            int[] childLocation = new int[2];
            childAt.getLocationOnScreen(childLocation);
            int y = childLocation[1] + DisplayUtil.getStatusBarHeight();
            int displayHeight = DisplayUtil.getDisplayHeight();
            ver = displayHeight - y - DisplayUtil.dip2px(141 + 230 + 45);
            Log.e("距离：", ver + "");
            if (ver < 0)
                mRefresh.scrollBy(0, -ver);

            mMoney.setText(oneMoney * mMulti + "元");
            mEdit_buy_times.setText(mMulti + "");
            mEdit_buy_times.setSelection((mMulti + "").length());
            keyboardUtil.attachTo(mEdit_buy_times);
            mEdit_buy_times.setFocusable(true);
            mEdit_buy_times.setFocusableInTouchMode(true);
            mEdit_buy_times.requestFocus();
            mCancel.setOnClickListener(CancelListener);
            mKeyBoardBg.setOnClickListener(KeyBoardBgListener);
            keyboardUtil.setOnOkClick(OkListener);
            mOne_mutil.setOnClickListener(MutilListener);
            mThree_mutil.setOnClickListener(MutilListener);
            mTwo_mutil.setOnClickListener(MutilListener);
            mFour_mutil.setOnClickListener(MutilListener);
            mEdit_buy_times.addTextChangedListener(TextWatcherListener);
            mEdit_buy_times.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    keyboardUtil.attachTo(mEdit_buy_times);
                    return false;
                }
            });
        }
    };
    View.OnClickListener MutilListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.one_mutil:
                    mEdit_buy_times.setText("20");
                    mEdit_buy_times.setSelection(2);
                    mMulti = 20;
                    break;
                case R.id.two_mutil:
                    mEdit_buy_times.setText("50");
                    mEdit_buy_times.setSelection(2);
                    mMulti = 50;
                    break;
                case R.id.three_mutil:
                    mEdit_buy_times.setText("100");
                    mEdit_buy_times.setSelection(3);
                    mMulti = 100;
                    break;
                case R.id.four_mutil:
                    mEdit_buy_times.setText("200");
                    mEdit_buy_times.setSelection(3);
                    mMulti = 200;
                    break;
            }
            mMoney.setText(mMulti * oneMoney + "元");
        }
    };
    private int selectPosition;
    PostRvAdapter.MoreListener moreListener = new PostRvAdapter.MoreListener() {
        @Override
        public void callBack(int position) {
            selectPosition = position;
            mDialog.show();
            window.setGravity(Gravity.BOTTOM);
        }
    };
    View.OnClickListener DeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            PostBean.DataBean.ListBean listBean = mPostList.get(selectPosition);
            int id = listBean.getId();
            Bundle params = new Bundle();
            params.putString("id", id + "");
            params.putString("auditFlag", "4");
            params.putString("token", LotteryApp.token);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            mHttp.post(Url.POST_UPDATE, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code == 0) {
                            pnPost = 1;
                            mPostList.clear();
                            // addData();
                            postData();
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
    };
    View.OnClickListener ShareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            PostBean.DataBean.ListBean listBean = mPostList.get(selectPosition);
            int id = listBean.getId();
            Toast.makeText(mContext, "分享条目" + selectPosition + "帖子id" + id, Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener CancelPopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLl_keybard.setVisibility(View.GONE);
            keyboardUtil.hideKeyboard();
            if (ver < 0)
                mRefresh.scrollBy(0, ver);
        }
    };
    View.OnClickListener KeyBoardBgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLl_keybard.setVisibility(View.GONE);
            keyboardUtil.hideKeyboard();
            if (ver < 0)
                mRefresh.scrollBy(0, ver);
        }
    };
    KeyboardUtil.OnOkClick OkListener = new KeyboardUtil.OnOkClick() {
        @Override
        public void onOkClick() {//确定购买
            if (mMulti == 0) {
                Toast.makeText(mContext, "您输入倍数为0", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!LotteryApp.isLogin) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                return;
            }
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
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            startActivity(intent);
                        }

                    }
                });
                return;
            }
            mLl_keybard.setVisibility(View.GONE);
            keyboardUtil.hideKeyboard();
            if (ver < 0)
                mRefresh.scrollBy(0, ver);
            int i = (int) Double.parseDouble(listBean.getOneMoney());
            Bundle bundle = new Bundle();
            bundle.putString("from", "gendanDetailActivity");
            bundle.putBoolean("mFlag", true);
            bundle.putString("money", mMulti * i + "");
            bundle.putString("mutile", mMulti + "");
            bundle.putString("orderId", listBean.getOrderId());
            bundle.putString("lotcode", listBean.getLotCode() + "");
            NetWorkData netWorkData = new NetWorkData(mContext, bundle);
            netWorkData.genheOrderForm();
        }
    };
    private int mMulti;
    TextWatcher TextWatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = String.valueOf(s).length();
            if (length == 0) {
                mMulti = 0;

            } else if (Integer.parseInt(String.valueOf(s)) > 50000) {
                mMulti = 50000;
                mEdit_buy_times.setText("50000");
                mEdit_buy_times.setSelection("50000".length());
                Toast.makeText(mContext, "最大输入50000", Toast.LENGTH_SHORT).show();
            } else if (length > 1 && Integer.parseInt(String.valueOf(s)) == 0) {
                mEdit_buy_times.setText("0");
                mMulti = 0;
            } else {
                mMulti = Integer.parseInt(String.valueOf(s));
            }
            mMoney.setText(mMulti * oneMoney + "元");
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyboardUtil != null && mLl_keybard.getVisibility() == View.VISIBLE) {
            keyboardUtil.hideKeyboard();
            mLl_keybard.setVisibility(View.GONE);
            if (ver < 0)
                mRefresh.scrollBy(0, ver);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 666 && resultCode == 888) {//个人简介修改回来
            infoData();
        } else if (requestCode == 222 && resultCode == 333) {//粉丝 关注 回来
            infoData();
        } else if (requestCode == 2345 && resultCode == 40) {
            if (flag == 1) {
                pn = 1;
                mItemsList.clear();
                addData();
                recommendData();
            } else {
                pnPost = 1;
                mPostList.clear();
                addData();
                postData();
                messageNumData();
            }
        }
    }
}
