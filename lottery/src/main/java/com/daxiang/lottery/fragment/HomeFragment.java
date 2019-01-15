package com.daxiang.lottery.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.ForecastActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.LotteryFormActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.NewsAdActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.activity.wonderfulactivity.LotteryResultActivity;
import com.daxiang.lottery.adapter.HomeLotteryAdapter;
import com.daxiang.lottery.adapter.HotGendanAdapter;
import com.daxiang.lottery.adapter.HotMatchAdapter;
import com.daxiang.lottery.adapter.LiveAdapter;
import com.daxiang.lottery.adapter.NewsAdapter;
import com.daxiang.lottery.adapter.YesterdayAdapter;
import com.daxiang.lottery.common.IntentSkip;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.KeyBardHD;
import com.daxiang.lottery.cxinterface.KeyBardInterface;
import com.daxiang.lottery.entity.HomeAd;
import com.daxiang.lottery.entity.HomeLotteryData;
import com.daxiang.lottery.entity.HotMatchData;
import com.daxiang.lottery.entity.HotRecommendData;
import com.daxiang.lottery.entity.NewsData;
import com.daxiang.lottery.entity.PictureData;
import com.daxiang.lottery.entity.PlayingedBean;
import com.daxiang.lottery.entity.RecommendBaseData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.KeyboardUtil;
import com.daxiang.lottery.utils.LogUtils;
import com.daxiang.lottery.utils.Logger;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.ReaderFileUtils;
import com.daxiang.lottery.utils.VersionInfo;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.view.BallGridView;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.CostomAdvertisementCarousel;
import com.daxiang.lottery.view.CostomAdvertisementCarousel2;
import com.daxiang.lottery.view.MyKeyBoardView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.daxiang.lottery.R.id.money;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HomeFragment extends Fragment {
    private LinearLayout mLl;
    private SmartRefreshLayout mRefresh;
    private View v;
    private BallGridView mGridView;
    private BillListView mListView;
    private ViewPager mViewPager;
    private LinearLayout llpoints;
    private FrameLayout mFl_left;
    private LinearLayout mLl_right_top;
    private LinearLayout mLl_right_bottom;
    //  private ScrollTitleView mScrollTitleView;
    private CostomAdvertisementCarousel2 mAdCAC;
    private ImageView mBanner_Ad;
    private LinearLayout mLl_ad;
    private LinearLayout mLl_notice;
    private CostomAdvertisementCarousel mNoticeCAC;
    List<HomeLotteryData.DataBean.Item1Bean> mHomeLotteryList;
    List<HomeLotteryData.DataBean.Item2Bean> awardPoolList;
    List<PictureData.DataBean.ItemsBean> results = new ArrayList<>();
    List<RecommendBaseData> mHotRecomList = new ArrayList<>();
    List<HotMatchData.DataBean> mHotMatchList = new ArrayList<>();
    private int prePosition = 1;
    Handler mHandler = new Handler();
    private int i = 1;
    List<ImageView> listImage = new ArrayList<>();
    HttpInterface2 mHttpInterface = new HttpUtils2(getActivity());
    private NewsAdapter newsAdapter;
    private HomeLotteryAdapter homeLotteryAdapter;
    //用于控制是否自动轮播viewpager
    private boolean refreshing = true;
    public static String Lottery_DLT;

    public static String Lottery_SSQ;

    public static String Lottery_QLC;
    public static String Lottery_QXC;
    public Context mContext;
    private int startY;
    private int titleBarHeight;
    private int backHeight;
    private LinearLayout.LayoutParams mPointParams;
    private HotGendanAdapter mHotGendanAdapter;
    private RecyclerView mRecyclerView;
    private View mHotLine;
    private LinearLayout mHotMatch;
    private View mHotMatchLine;
    private LinearLayout mHotGendan;
    private LinearLayout mYesterdayMatch;
    private RecyclerView mYesterdayRv;
    private View mYesterdayLine;
    private LinearLayout mLiveMatch;
    private RecyclerView mLiveRv;
    private View mLiveLine;
    private YesterdayAdapter mYesterdayAdapter;
    private LiveAdapter mLiveAdapter;
    private HotMatchAdapter mHotMatchAdapter;
    private int buyPrivilege;//1审核完成，0审核状态
    private int saleFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, null);
        mContext = getActivity();
        buyPrivilege = SpUtils.getBuyPrivilege();
        saleFlag = SpUtils.getInt(SpUtils.SALE_FLAG);
        titleBarHeight = getResources().getDimensionPixelOffset(R.dimen.titlebar_heigh);
        //viewpager的高度
        backHeight = getResources().getDimensionPixelOffset(R.dimen.vp_heigh);
        //初始化控件
        initView();
        daufltData();
        //获取数据源
        setData();
       /* //中奖通知
        winningData();*/
        setListener();
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("时间HomeonPause", System.currentTimeMillis() + "");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("时间HomeonStop", System.currentTimeMillis() + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("时间HomeonDestroy", System.currentTimeMillis() + "");
    }

    private void initView() {
        keybardView();//点击跟单出来的键盘
        mLl = (LinearLayout) v.findViewById(R.id.ll);
        // mScrollTitleView = (ScrollTitleView) v.findViewById(R.id.scrollTitleView);
        mRefresh = (SmartRefreshLayout) v.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mHotLine = v.findViewById(R.id.hot_line);
        mHotGendan = (LinearLayout) v.findViewById(R.id.hot_gendan);
        mHotMatch = (LinearLayout) v.findViewById(R.id.hot_match);
        mHotMatchLine = v.findViewById(R.id.hot_match_line);
        mViewPager = (ViewPager) v.findViewById(R.id.vp_news);
        mViewPager.setFocusable(true);
        mViewPager.setFocusableInTouchMode(true);
        mGridView = (BallGridView) v.findViewById(R.id.gv_lottery);
        mListView = (BillListView) v.findViewById(R.id.hot_gendan_lv);
        mGridView.setFocusable(false);
        llpoints = (LinearLayout) v.findViewById(R.id.ll_points);
        mLl_ad = (LinearLayout) v.findViewById(R.id.ll_ad);
        mAdCAC = (CostomAdvertisementCarousel2) v.findViewById(R.id.ad_carousel);
        mLl_notice = (LinearLayout) v.findViewById(R.id.ll_notice);
        mNoticeCAC = (CostomAdvertisementCarousel) v.findViewById(R.id.notice_carousel);
        mBanner_Ad = (ImageView) v.findViewById(R.id.banner_ad);

        mFl_left = (FrameLayout) v.findViewById(R.id.fl_left);
        mLl_right_top = (LinearLayout) v.findViewById(R.id.ll_right_top);
        mLl_right_bottom = (LinearLayout) v.findViewById(R.id.ll_right_bottom);

        mYesterdayMatch = (LinearLayout) v.findViewById(R.id.yesterday_match);
        mYesterdayRv = (RecyclerView) v.findViewById(R.id.yesterday_rv);
        mYesterdayLine = v.findViewById(R.id.yesterday_line);

        mLiveMatch = (LinearLayout) v.findViewById(R.id.live_match);
        mLiveRv = (RecyclerView) v.findViewById(R.id.live_rv);
        mLiveLine = v.findViewById(R.id.live_line);

        mAdCAC.setListener();//公告能够点击
        if (buyPrivilege == 0 && saleFlag == 0) {
            mLl_notice.setVisibility(View.VISIBLE);
        } else {
            mLl_notice.setVisibility(View.GONE);
        }
        mHomeLotteryList = new ArrayList<>();
        awardPoolList = new ArrayList<>();
        homeLotteryAdapter = new HomeLotteryAdapter(mHomeLotteryList);
        mGridView.setAdapter(homeLotteryAdapter);

        newsAdapter = new NewsAdapter(listImage, results, getContext());
        mViewPager.setAdapter(newsAdapter);

        mHotGendanAdapter = new HotGendanAdapter(mContext, mHotRecomList, true, KeyBardListener);
        mListView.setAdapter(mHotGendanAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mHotMatchAdapter = new HotMatchAdapter(mContext, mHotMatchList);
        mRecyclerView.setAdapter(mHotMatchAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mYesterdayRv.setLayoutManager(linearLayoutManager2);
        mYesterdayAdapter = new YesterdayAdapter(mContext, mPlayedList);
        mYesterdayRv.setAdapter(mYesterdayAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mLiveRv.setLayoutManager(linearLayoutManager3);
        mLiveAdapter = new LiveAdapter(mContext, mPlayingList);
        mLiveRv.setAdapter(mLiveAdapter);


        mPointParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(7), DisplayUtil.dip2px(7));
        mPointParams.leftMargin = 12;
    }

    private View mKeyBoardBg;
    private LinearLayout mLl_two;
    private TextView mOne_mutil;
    private TextView mTwo_mutil;
    private TextView mThree_mutil;
    private TextView mFour_mutil;
    private ImageView mCancel;
    private LinearLayout mLl_keybard;
    private MyKeyBoardView mKeyboardView;
    private LinearLayout mLl_one;
    private EditText mEdit_buy_times;
    private TextView mMoney;
    private KeyboardUtil keyboardUtil;

    private void keybardView() {
        mLl_two = (LinearLayout) v.findViewById(R.id.ll_two);
        mOne_mutil = (TextView) v.findViewById(R.id.one_mutil);
        mTwo_mutil = (TextView) v.findViewById(R.id.two_mutil);
        mThree_mutil = (TextView) v.findViewById(R.id.three_mutil);
        mFour_mutil = (TextView) v.findViewById(R.id.four_mutil);
        mLl_keybard = (LinearLayout) v.findViewById(R.id.ll_keybard);
        mKeyBoardBg = v.findViewById(R.id.keyboard_bg);
        mCancel = (ImageView) v.findViewById(R.id.cancel);
        mKeyboardView = (MyKeyBoardView) v.findViewById(R.id.keyboard_view);
        mEdit_buy_times = (EditText) v.findViewById(R.id.edit_buy_times);
        mMoney = (TextView) v.findViewById(money);
        mLl_one = (LinearLayout) v.findViewById(R.id.ll_one);
        keyboardUtil = new KeyboardUtil((Activity) mContext, mKeyboardView);
    }

    private void daufltData() {
        ImageView iv = new ImageView(getActivity());
        iv.setBackgroundResource(R.drawable.apptitler);
        listImage.add(iv);
        newsAdapter.notifyDataSetChanged();

        SharedPreferences sp = getActivity().getSharedPreferences("flag", Context.MODE_PRIVATE);
        String json = sp.getString("json", "");
        if (json == null || json.length() == 0) {
            json = ReaderFileUtils.ReadTxtFile(getContext(), "lottery.json");
        }
        Gson gson = new Gson();
        HomeLotteryData homeLotteryData = gson.fromJson(json, HomeLotteryData.class);
        mHomeLotteryList.addAll(homeLotteryData.getData().getItem1());
        homeLotteryAdapter.notifyDataSetChanged();

    }

    private void setData() {
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            //新闻滚动条，
            newsData();
            //彩象公告
            adData();
            //彩像中奖通知
            hotsData();
            //gridView的数据加载
            gridViewData();
            //热门推荐
            hotRecommend();
            //活动
            actionData();
            //昨日赛事，直播赛事
            playingedData();
            //获取启动图
            new NetWorkData(mContext).launchImage();

        } else {
            mRefresh.finishRefresh();
            HintDialogUtils.setHintDialog(getContext());
            HintDialogUtils.setMessage("您的网络未连接，请连接后重试！");
            HintDialogUtils.setConfirm("重新加载", new DialogHintInterface() {
                @Override
                public void callBack(View view) {
                    //results.clear();
                    setData();
                }
            });
        }
    }

    List<PlayingedBean.DataBean.FixtureBean> mPlayingList = new ArrayList<>();
    List<PlayingedBean.DataBean.FinishBean> mPlayedList = new ArrayList<>();

    private void playingedData() {

        Bundle params = new Bundle();
        mHttpInterface.get(Url.FINISHGAME, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                PlayingedBean playingedBean = gson.fromJson(result, PlayingedBean.class);
                int code = playingedBean.getCode();
                String msg = playingedBean.getMsg();
                if (code == 0) {
                    PlayingedBean.DataBean data = playingedBean.getData();
                    List<PlayingedBean.DataBean.FinishBean> finish = data.getFinish();
                    List<PlayingedBean.DataBean.FixtureBean> fixture = data.getFixture();
                    if (finish != null && finish.size() > 0) {
                        mYesterdayMatch.setVisibility(View.VISIBLE);
                        mYesterdayRv.setVisibility(View.VISIBLE);
                        mYesterdayLine.setVisibility(View.VISIBLE);
                        mPlayedList.clear();
                        mPlayedList.addAll(finish);
                        mYesterdayAdapter.notifyDataSetChanged();
                    } else {
                        mYesterdayMatch.setVisibility(View.GONE);
                        mYesterdayRv.setVisibility(View.GONE);
                        mYesterdayLine.setVisibility(View.GONE);
                    }
                    if (fixture != null && fixture.size() > 0) {
                        mLiveMatch.setVisibility(View.VISIBLE);
                        mLiveRv.setVisibility(View.VISIBLE);
                        mLiveLine.setVisibility(View.VISIBLE);
                        mPlayingList.clear();
                        mPlayingList.addAll(fixture);
                        mLiveAdapter.notifyDataSetChanged();
                    } else {
                        mLiveMatch.setVisibility(View.GONE);
                        mLiveRv.setVisibility(View.GONE);
                        mLiveLine.setVisibility(View.GONE);
                    }
                } else {

                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private String remarks = "";

    private void actionData() {
        //新闻头条加载
        Bundle params = new Bundle();
        params.putString("category", "5");
        params.putString("typeFlag", "activity_android");
        params.putString("pageSize", "10");
        params.putString("pageNumber", "1");
        params.putString("status", "0");
        params.putString("token", "1");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.NEWS_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                PictureData pictureData = gson.fromJson(result, PictureData.class);
                if (pictureData.getCode() == 0) {
                    PictureData.DataBean data = pictureData.getData();
                    List<PictureData.DataBean.ItemsBean> items = data.getItems();
                    if (items != null && items.size() > 0) {
                        PictureData.DataBean.ItemsBean itemsBean = items.get(0);
                        remarks = itemsBean.getRemarks();
                        String logoUrl = itemsBean.getLogoURL();
                        if (!TextUtils.isEmpty(logoUrl)) {
                            mBanner_Ad.setVisibility(View.VISIBLE);
                            Picasso.with(getContext())
                                    .load(Url.ROOT_URL + logoUrl)
                                    .config(Bitmap.Config.RGB_565)
                                    .placeholder(R.mipmap.banner_ad)
                                    .error(R.mipmap.banner_ad)
                                    .into(mBanner_Ad);
                        } else {
                            mBanner_Ad.setVisibility(View.GONE);
                        }
                    }
                } else {
                    mBanner_Ad.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError() {
            }
        });
    }

    private void hotMatchAdata() {
        mHttpInterface.get(Url.HOT_MATCH, new Bundle(), new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                HotMatchData hotMatchData = gson.fromJson(result, HotMatchData.class);
                int code = hotMatchData.getCode();
                String msg = hotMatchData.getMsg();
                if (code == 0) {
                    mHotMatchList.clear();
                    mHotMatchList.addAll(hotMatchData.getData());
                    if (mHotMatchList.size() == 0 || buyPrivilege == 1) {
                        mHotMatch.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        mHotMatchLine.setVisibility(View.GONE);
                    } else {
                        mHotMatch.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mHotMatchLine.setVisibility(View.VISIBLE);
                    }
                    mHotMatchAdapter.setSaleState(jczqState);
                    mHotMatchAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError() {

            }
        });
    }

    private void hotRecommend() {
        Bundle params = new Bundle();
        mHttpInterface.get(Url.HOT_RECOMMEND, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                HotRecommendData gendanData = gson.fromJson(result, HotRecommendData.class);
                if (gendanData.getCode() == 0) {
                    mHotRecomList.clear();
                    List<HotRecommendData.DataBean> data = gendanData.getData();
                    mHotRecomList.addAll(data);
                    if (mHotRecomList.size() == 0 || buyPrivilege == 1) {
                        mHotGendan.setVisibility(View.GONE);
                        mListView.setVisibility(View.GONE);
                        mHotLine.setVisibility(View.GONE);
                    } else {
                        mHotGendan.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.VISIBLE);
                        mHotLine.setVisibility(View.VISIBLE);
                    }
                    mHotGendanAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    private List<HomeAd.DataBean> mNoticeList = new ArrayList<>();

    private void hotsData() {
        mHttpInterface.jsonByUrl(Url.AWARD_AD, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                HomeAd homeAd = gson.fromJson(result, HomeAd.class);
                if (homeAd.getCode() == 0 && homeAd.getData().size() > 0) {
                    mNoticeList.clear();
                    mNoticeList.addAll(homeAd.getData());
                    if (mNoticeList != null && mNoticeList.size() != 0) {
                        mNoticeCAC.setList(mNoticeList);
                        mNoticeCAC.startScroll();
                    }
                } else {
                    mLl_notice.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    List<NewsData.DataBean.ItemsBean> mAdList = new ArrayList<>();

    private void adData() {
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("pageSize", "10");
        params.putString("pageNumber", "1");
        params.putString("category", "1");//1公告，3新闻资讯
        //状态(0，正常 1，隐藏) 无则获取所有
        params.putString("status", "0");
        mHttpInterface.post(Url.NEWS_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                NewsData newsData = gson.fromJson(result, NewsData.class);
                if (newsData.getCode() == 0 && newsData.getData().getItems().size() > 0) {
                    mAdList.clear();
                    NewsData.DataBean data = newsData.getData();
                    List<NewsData.DataBean.ItemsBean> items = data.getItems();
                    mAdList.addAll(items);
                    if (mAdList != null && mAdList.size() != 0) {
                        mAdCAC.setList(mAdList);
                        mAdCAC.startScroll();
                    }
                } else {
                    mLl_ad.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private int jczqState = 1;//竞彩足球的销售状态 1在销售 0停售

    private void gridViewData() {
        if (saleFlag == 1) {//停止销售
            mGridView.setVisibility(View.GONE);
            return;
        } else mGridView.setVisibility(View.VISIBLE);
        //gridview加载数据
        Bundle params = new Bundle();
        params.putString("type", "android");
        params.putString("version", VersionInfo.getAppVersionName(getContext()));
        mHttpInterface.get(Url.HOME_LOTTERY_URL, params, new JsonInterface() {

            @Override
            public void callback(String result) {

                mRefresh.finishRefresh();
                Gson gson = new Gson();
                final HomeLotteryData homeLotteryData = gson.fromJson(result, HomeLotteryData.class);
                if (homeLotteryData.getCode() == 0) {
                    //将请求的数据进行保存，在没有网络的时候用
                    SharedPreferences sp = getActivity().getSharedPreferences("flag", Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEdit = sp.edit();
                    mEdit.putString("json", result);
                    mEdit.commit();

                    mHomeLotteryList.clear();
                    List<HomeLotteryData.DataBean.Item1Bean> item1 = homeLotteryData.getData().getItem1();
                    SharedPreferences userinfoSp = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    String lotCodeStr = userinfoSp.getString("lotCodeStr", "");
                    if (!TextUtils.isEmpty(lotCodeStr)) {
                        String[] split = lotCodeStr.split("\\:");
                        if (split.length == item1.size()) {
                            for (int j = 0; j < split.length; j++) {
                                for (int k = 0; k < item1.size(); k++) {
                                    HomeLotteryData.DataBean.Item1Bean item1Bean = item1.get(k);
                                    String lotCode = item1Bean.getLotCode();
                                    if (lotCode.equals(split[j])) {
                                        mHomeLotteryList.add(item1Bean);
                                        break;
                                    }
                                }
                            }
                        } else {
                            mHomeLotteryList.addAll(item1);
                        }


                    } else {
                        mHomeLotteryList.addAll(item1);
                    }
                    SharedPreferences.Editor edit = userinfoSp.edit();
                    //把顺序存起来
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < mHomeLotteryList.size(); i++) {
                        HomeLotteryData.DataBean.Item1Bean item1Bean = mHomeLotteryList.get(i);
                        String lotCode = item1Bean.getLotCode();
                        stringBuilder.append(lotCode + ":");
                        edit.putInt(lotCode, item1Bean.getState());
                        if (LotCode.JCZQ_CODE.equals(lotCode)) {
                            jczqState = item1Bean.getState();
                        }
                    }
                    // spEditor.putString("lotCodeStr", "");
                    edit.putString("lotCodeStr", String.valueOf(stringBuilder));
                    edit.commit();


                    awardPoolList = homeLotteryData.getData().getItem2();
                    for (int i = 0; i < awardPoolList.size(); i++) {
                        String seLotid = awardPoolList.get(i).getLotCode();
                        String awardPool = awardPoolList.get(i).getAwardPool();
                        switch (seLotid) {
                            case "51":
                                Lottery_SSQ = awardPool;
                                break;
                            case "23529":
                                Lottery_DLT = awardPool;
                                break;
                            case "10022":
                                Lottery_QXC = awardPool;
                                break;
                            case "23528":
                                Lottery_QLC = awardPool;
                                break;
                        }
                    }
                    homeLotteryAdapter.notifyDataSetChanged();
                } else {
                    Logger.e("homeLotteryData", homeLotteryData.getMsg());
                }
                //热门赛事
                hotMatchAdata();
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                hotMatchAdata();
            }
        });
    }

    private void newsData() {
        //新闻头条加载
        Bundle params = new Bundle();
        params.putString("category", "5");
        params.putString("typeFlag", "banner-android");
        params.putString("pageSize", "10");
        params.putString("pageNumber", "1");
        params.putString("status", "0");
        params.putString("token", "1");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.NEWS_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                // LogUtils.e("轮播图返回", result);
                Gson gson = new Gson();
                PictureData pictureData = gson.fromJson(result, PictureData.class);
                if (pictureData.getCode() == 0) {
                    PictureData.DataBean data = pictureData.getData();
                    results.clear();
                    results.addAll(data.getItems());
                    //清空集合
                    listImage.clear();
                    llpoints.removeAllViews();
                    ImageView imageView1 = new ImageView(getActivity());
                    imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.with(getContext())
                            .load(Url.ROOT_URL + results.get(results.size() - 1).getLogoURL())
                            .placeholder(R.drawable.apptitler)
                            .config(Bitmap.Config.RGB_565)
                            .into(imageView1);
                    listImage.add(imageView1);
                    for (int i = 0; i < results.size(); i++) {
                        ImageView iv = new ImageView(getActivity());
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Picasso.with(getContext()).load(Url.ROOT_URL + results.get(i).getLogoURL()).placeholder(R.drawable.apptitler).config(Bitmap.Config.RGB_565).into(iv);
                        listImage.add(iv);
                        //加小圆点
                        View view = new View(getContext());
                        view.setBackgroundResource(R.drawable.chase_normal);
                        view.setLayoutParams(mPointParams);
                        llpoints.addView(view);
                    }

                    ImageView imageView2 = new ImageView(getActivity());
                    imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.with(getContext()).load(Url.ROOT_URL + results.get(0).getLogoURL()).placeholder(R.drawable.apptitler).config(Bitmap.Config.RGB_565).into(imageView2);
                    listImage.add(imageView2);
                    //newsAdapter.setData(results);
                    newsAdapter.notifyDataSetChanged();
                    //请求数据完成后才允许自动轮播
                    refreshing = false;
                } else {
                    newsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError() {
                newsAdapter.notifyDataSetChanged();
            }
        });
    }

    int currentPosition;
    private boolean flag = false;

    private void setListener() {
        mEdit_buy_times.addTextChangedListener(TextWatcherListener);
        mKeyBoardBg.setOnClickListener(KeyBoardBgListener);
        mFl_left.setOnClickListener(FlLeftListener);
        mLl_right_top.setOnClickListener(RightTopListener);
        mLl_right_bottom.setOnClickListener(RightBottomListener);
        mBanner_Ad.setOnClickListener(BannerListener);
        mEdit_buy_times.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.attachTo(mEdit_buy_times);
                return false;
            }
        });
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //下拉刷新的时候，请求完数据才能自动轮播
                if (!refreshing) {
                    if (i < listImage.size() - 1) {
                        mViewPager.setCurrentItem(i);
                        i++;
                        mHandler.postDelayed(this, 2000);
                    } else {
                        // mViewPager.setCurrentItem(1);
                        i = 1;
                        mHandler.post(this);
                    }
                } else {
                    i = 1;
                    mHandler.postDelayed(this, 2000);
                }

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e("onPageSelected", position + "");
                if (listImage.size() > 2) { //多于1，才会循环跳转
                    if (position < 1) { //首位之前，跳转到末尾（N）
                        currentPosition = listImage.size() - 2;
                        flag = true;
                        //mViewPager.setCurrentItem(currentPosition, false);
                    } else if (position > listImage.size() - 2) { //末位之后，跳转到首位（1）
                        currentPosition = 1;
                        flag = true;
                        //mViewPager.setCurrentItem(currentPosition, false); //false:不显示跳转过程的动画
                    } else {
                        currentPosition = position;
                    }


                    // homeLotteryAdapter.notifyDataSetChanged();
                    i = currentPosition;
                    if (currentPosition > 0 && currentPosition < listImage.size() - 1) {
                        llpoints.getChildAt(prePosition - 1).setBackgroundResource(R.drawable.chase_normal);
                        llpoints.getChildAt(currentPosition - 1).setBackgroundResource(R.drawable.chase_press);
                    }
                    prePosition = currentPosition;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE && flag) {
                    mViewPager.setCurrentItem(currentPosition, false);
                    flag = false;
                }
                LogUtils.e("onPageScrollStateChanged", state + "");
            }
        });


        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //数据请求中不让viewpager自动轮播
                refreshing = true;
                mNoticeCAC.stopScroll();
                mAdCAC.stopScroll();
                //results.clear();
                //listImage.clear();
                setData();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 7) {
                    Intent intent = new Intent(mContext, LotteryFormActivity.class);
                    ((Activity) mContext).startActivityForResult(intent, 1111);
                } else {
                    IntentSkip intentSkip = new IntentSkip();
                    intentSkip.skipLotcode(mContext, mHomeLotteryList.get(position).getLotCode());
                }
            }
        });

    }

    private double oneMoney;
    private RecommendBaseData listBean;
    private int ver;
    private int mMulti;
    private KeyBardHD mKeyBardHD;

    public void setListener(KeyBardHD mKeyBardHD) {
        this.mKeyBardHD = mKeyBardHD;
    }

    KeyBardInterface KeyBardListener = new KeyBardInterface() {

        @Override
        public void callBack(RecommendBaseData listBea, int position) {

            if (listBea == null) return;
            mKeyBardHD.hint();
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
            ver = displayHeight - y - DisplayUtil.dip2px(141 + 230 + 55);
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
            keyboardUtil.setOnOkClick(OkListener);
            mOne_mutil.setOnClickListener(MutilListener);
            mThree_mutil.setOnClickListener(MutilListener);
            mTwo_mutil.setOnClickListener(MutilListener);
            mFour_mutil.setOnClickListener(MutilListener);
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

    private void hintKey() {
        mLl_keybard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        mKeyBardHD.display();
        if (ver < 0)
            mRefresh.scrollBy(0, ver);
    }

    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hintKey();
        }
    };
    View.OnClickListener KeyBoardBgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hintKey();
        }
    };

    public void hiddenKey() {
        if (keyboardUtil != null && mLl_keybard.getVisibility() == View.VISIBLE) {
            hintKey();
        }

    }

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
            hintKey();//隐藏键盘
            int i = (int) Double.parseDouble(listBean.getOneMoney());
            Bundle bundle = new Bundle();
            bundle.putString("from", "gendanDetailActivity");
            bundle.putBoolean("mFlag", true);
            bundle.putString("money", mMulti * i + "");
            bundle.putString("mutile", mMulti + "");
            bundle.putString("orderId", listBean.getOrderId());
            bundle.putString("lotcode", listBean.getLotCode() + "");
            bundle.putString("mGodName", listBean.getUserName());
            bundle.putString("mGodUserId", listBean.getUserId());
            NetWorkData netWorkData = new NetWorkData(mContext, bundle);
            netWorkData.genheOrderForm();
        }
    };
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
    View.OnClickListener FlLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), LotteryResultActivity.class);
            startActivity(intent);
        }
    };


    View.OnClickListener RightTopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ForecastActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener RightBottomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), NewsAdActivity.class);
            intent.putExtra("newsOrAd", "news");
            startActivity(intent);
        }
    };
    View.OnClickListener BannerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new NetWorkData(mContext).setSkip(remarks);
        }
    };

    public void setLotteryChange() {
        gridViewData();
    }
}
