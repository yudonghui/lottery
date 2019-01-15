package com.daxiang.lottery.forum.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.forum.activity.EditePostActivity;
import com.daxiang.lottery.forum.activity.MoreNewsActivity;
import com.daxiang.lottery.forum.activity.PostDetailActivity;
import com.daxiang.lottery.forum.base.BaseMvpFragment;
import com.daxiang.lottery.forum.bean.HeaderBean;
import com.daxiang.lottery.forum.contract.ForumContract;
import com.daxiang.lottery.forum.presenter.ForumPresenter;
import com.daxiang.lottery.forum.selfview.PersonalViewpager;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.FloatViewLayout;
import com.daxiang.lottery.view.ForumScrollView;
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

import static com.daxiang.lottery.R.id.browseNum;
import static com.daxiang.lottery.R.id.forumScrollView;
import static com.daxiang.lottery.R.id.likeNum;

/**
 * Created by Android on 2018/9/4.
 */

public class ForumFragment extends BaseMvpFragment<ForumPresenter> implements ForumContract.View {
    private View mTitleView;
    private TitleBar mTitlbar;
    private SmartRefreshLayout mRefresh;
    private ForumScrollView mScrollView;
    private ImageView mHeaderImage;
    private FrameLayout mLlHeader;
    private TextView mLlMore;
    private TextView mHeaderTitle;
    private TextView mBrowseNum;
    private TextView mReplyNum;
    private TextView mLikeNum;
    private TextView mDateTime;
    private TabLayout mTablayout;
    private TabLayout mTablayout2;
    private PersonalViewpager mViewPager;
    private ImageView mEditPost;
    private FloatViewLayout mFvl;
    private AllPostFragment mAllPostFragment;
    //private AllPostFragment mGodPostFragment;
    private AllPostFragment mMyPostFragment;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private String userId;

    @Override
    public int getInflateId() {
        return R.layout.fragment_forum;
    }

    @Override
    public void init() {
        mTitleView = view.findViewById(R.id.titleView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleView.setVisibility(View.VISIBLE);
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dimensionPixelSize);
                mTitleView.setLayoutParams(layoutParams);
            }
        } else mTitleView.setVisibility(View.GONE);


        mTitlbar = (TitleBar) view.findViewById(R.id.titlbar);
        mTitlbar.mImageBack.setVisibility(View.GONE);
        mTitlbar.setImageTitleVisibility(false);
        mTitlbar.setTitleVisibility(true);
        mTitlbar.setTitle("发现");
        mTitlbar.mShare.setVisibility(View.VISIBLE);
        mTitlbar.mShare.setImageResource(R.drawable.self);
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);
        mScrollView = (ForumScrollView) view.findViewById(forumScrollView);
        mLlHeader = (FrameLayout) view.findViewById(R.id.fl_header);
        mHeaderImage = (ImageView) view.findViewById(R.id.headerImage);
        mLlMore = (TextView) view.findViewById(R.id.llMore);
        mHeaderTitle = (TextView) view.findViewById(R.id.headerTitle);
        mBrowseNum = (TextView) view.findViewById(browseNum);
        mReplyNum = (TextView) view.findViewById(R.id.replyNum);
        mLikeNum = (TextView) view.findViewById(likeNum);
        mDateTime = (TextView) view.findViewById(R.id.dateTime);
        mTablayout = (TabLayout) view.findViewById(R.id.tablayout);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);//设置TabLayout的模式
        mTablayout2 = (TabLayout) view.findViewById(R.id.tablayout2);
        mTablayout2.setTabMode(TabLayout.MODE_FIXED);//设置TabLayout的模式

        mViewPager = (PersonalViewpager) view.findViewById(R.id.viewPager);
        mEditPost = (ImageView) view.findViewById(R.id.editPost);
        mFvl = (FloatViewLayout) view.findViewById(R.id.floatViewLayout);

        mPresenter = new ForumPresenter(this);
        mAllPostFragment = new AllPostFragment();
        mAllPostFragment.setData(0, mContext, mViewPager);
        /*mGodPostFragment = new AllPostFragment();
        mGodPostFragment.setData(2, mContext);*/
        mMyPostFragment = new AllPostFragment();
        mMyPostFragment.setData(1, mContext, mViewPager);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mAllPostFragment);
        // mFragmentList.add(mGodPostFragment);
        mFragmentList.add(mMyPostFragment);

        mTitleList = new ArrayList<>();
        mTitleList.add("全部资讯");
        // mTitleList.add("神贴推荐");
        mTitleList.add("我的关注");
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(0)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(1)));
        // mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(2)));
        mTablayout2.addTab(mTablayout2.newTab().setText(mTitleList.get(0)));
        mTablayout2.addTab(mTablayout2.newTab().setText(mTitleList.get(1)));
        // mTablayout2.addTab(mTablayout2.newTab().setText(mTitleList.get(2)));
        mTablayout2.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTablayout2, DisplayUtil.dip2px(40), DisplayUtil.dip2px(40));
            }
        });
        mTablayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTablayout, DisplayUtil.dip2px(40), DisplayUtil.dip2px(40));
            }
        });
        DltViewPagerAdapter mVpAdapter = new DltViewPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout2.setupWithViewPager(mViewPager);
        addData();
    }

    private void addData() {
        Bundle parameters = new Bundle();
        if (!TextUtils.isEmpty(LotteryApp.uid)) {
            parameters.putString("userId", LotteryApp.uid);
        }
        if (mPresenter != null)
            mPresenter.getHeaderData(parameters, mContext);
    }
    public void refreshData(){
        addData();
        int currentItem = mViewPager.getCurrentItem();
        if (currentItem == 0) {
            mAllPostFragment.refresh();
        } else if (currentItem == 1) {
            mMyPostFragment.refresh();
        }
    }
    @Override
    public void addListener() {
        mFvl.setMove(MoveListener);
        mEditPost.setOnClickListener(EditPostListener);
        mScrollView.setOnScrollistener(OnScrollistener);
        mTitlbar.mShare.setOnClickListener(MyListener);
        mLlHeader.setOnClickListener(HeaderListener);
        mViewPager.setOnPageChangeListener(pageChangeListener);
        mLikeNum.setOnClickListener(LikeHeaderListener);
        mLlMore.setOnClickListener(LlMoreListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem == 0) {
                    mAllPostFragment.refresh();
                } else if (currentItem == 1) {
                    mMyPostFragment.refresh();
                }
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem == 0) {
                    mAllPostFragment.load();
                } else if (currentItem == 1) {
                    mMyPostFragment.load();
                }
                mRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefresh.finishLoadMore();
                    }
                }, 3000);
            }
        });
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mViewPager.resetHeight(position);
        }

        @Override
        public void onPageSelected(int position) {
          /*  if (position == 0) {
                if (itemNum0 < 4) {
                    mTablayout2.setVisibility(View.GONE);
                }
            } else {
                if (itemNum1 < 4) {
                    mTablayout2.setVisibility(View.GONE);
                }
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    View.OnClickListener LikeHeaderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            if (!LotteryApp.isLogin) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                return;
            }
            HttpInterface2 mHttp = new HttpUtils2(mContext);
            final Bundle params = new Bundle();
            params.putString("id", headerId + "");
            params.putString("token", LotteryApp.token);
            params.putString("target", "post");
            params.putString("timeStamp", System.currentTimeMillis() + "");
            if ("1".equals(isLikeHeader)) {
                isLikeHeader = "0";
                params.putString("isLike", "0");
                mLikeNum.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.white_praise), null, null, null);
                mLikeNum.setText((likeNumHeader - 1) + "");
                likeNumHeader = likeNumHeader - 1;
            } else {
                params.putString("isLike", "1");
                isLikeHeader = "1";
                mLikeNum.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.red_praise), null, null, null);
                mLikeNum.setText((likeNumHeader + 1) + "");
                likeNumHeader = likeNumHeader + 1;
            }
            mHttp.post(Url.POST_LIKE, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 0) {

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
    View.OnClickListener LlMoreListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(MoreNewsActivity.class);
        }
    };
    View.OnClickListener HeaderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("postId", headerId + "");
            startActivity(PostDetailActivity.class, bundle);
        }
    };
    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.isLogin || TextUtils.isEmpty(LotteryApp.uid)) {
                startActivity(LoginActivity.class);
                return;
            }
            Intent intent = new Intent(mContext, GodInfoActivity.class);
            intent.putExtra("userId", LotteryApp.uid);
            startActivity(intent);
        }
    };
    ForumScrollView.OnScrollistener OnScrollistener = new ForumScrollView.OnScrollistener() {
        @Override
        public void onScroll(int startY, int endY) {
            if (endY >= DisplayUtil.dip2px(170)) {
                mTablayout2.setVisibility(View.VISIBLE);
            } else {
                mTablayout2.setVisibility(View.GONE);
            }
            Log.e("滑动参数", "startY: " + startY + " endY: " + endY + " statusBarHeight: ");

        }
    };
    FloatViewLayout.MoveListener MoveListener = new FloatViewLayout.MoveListener() {
        @Override
        public void moveLeft(int left) {

        }
    };
    View.OnClickListener EditPostListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            startActivity(EditePostActivity.class);
        }
    };
    private int headerId;//头部热门帖子的id
    private String isLikeHeader;
    private int likeNumHeader;

    @Override
    public void getHeaderSuccess(String result) {
        mRefresh.finishRefresh();
        Gson gson = new Gson();
        HeaderBean headerBean = gson.fromJson(result, HeaderBean.class);
        int code = headerBean.getCode();
        String msg = headerBean.getMsg();
        if (code == 0) {
            HeaderBean.DataBean data = headerBean.getData();
            likeNumHeader = data.getLikeNum();//点赞次数
            String commentsNum = data.getCommentsNum();//评论次数
            String browseNum = data.getBrowseNum();//浏览次数
            headerId = data.getId();//帖子id
            isLikeHeader = data.getIsLike();//是否对该帖子点过赞，0没有，1点过
            String title = data.getTitle();
            String pictureUrl = data.getPictureUrl();
            String createTime = data.getCreateTime();
            mHeaderTitle.setText(TextUtils.isEmpty(title) ? "" : title);
            mBrowseNum.setText(TextUtils.isEmpty(browseNum) ? "" : browseNum);
            mReplyNum.setText(TextUtils.isEmpty(commentsNum) ? "" : commentsNum);
            mLikeNum.setText(likeNumHeader + "");
            mDateTime.setText(DateFormtUtils.timeStamp2Date(createTime, DateFormtUtils.MD_HM));
            if ("1".equals(isLikeHeader)) {
                mLikeNum.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.red_praise), null, null, null);
            } else {
                mLikeNum.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.white_praise), null, null, null);
            }
            if (!TextUtils.isEmpty(pictureUrl)) {
                mLlHeader.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(pictureUrl)
                        .placeholder(R.mipmap.default_post)
                        .error(R.mipmap.default_post)
                        .config(Bitmap.Config.RGB_565)
                        .into(mHeaderImage);
            } else {
                mLlHeader.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getHeaderError() {
        mRefresh.finishRefresh();
    }
}
