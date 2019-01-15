package com.daxiang.lottery.forum.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.GameList;
import com.daxiang.lottery.forum.adapter.PostCommentAdapter;
import com.daxiang.lottery.forum.base.BaseMvpActivity;
import com.daxiang.lottery.forum.bean.CommentsBean;
import com.daxiang.lottery.forum.bean.PostCommentBean;
import com.daxiang.lottery.forum.bean.PostDetailBean;
import com.daxiang.lottery.forum.bean.UserBean;
import com.daxiang.lottery.forum.contract.PostDetailContract;
import com.daxiang.lottery.forum.presenter.PostDetailPresenter;
import com.daxiang.lottery.score.ScoreDetailActivity;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.IsWinnig;
import com.daxiang.lottery.utils.ReportDialog;
import com.daxiang.lottery.utils.Share;
import com.daxiang.lottery.view.BillListView;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daxiang.lottery.R.id.userName;

public class PostDetailActivity extends BaseMvpActivity<PostDetailPresenter> implements PostDetailContract.View {
    private TitleBar mTitlBar;
    private ImageView mAvatar;
    private SmartRefreshLayout mRefresh;
    private TextView mTitle;
    private TextView mUserName;
    private TextView mDateTime;
    private TextView mFocus;
    private WebView mWebView;
    private TextView mBrowse;
    private TextView mComment;
    private TextView mPraise;
    private LinearLayout mLl_betContent;
    private LinearLayout mTable_layout;
    private LinearLayout mVsTimes;

    private NoDataView mNoData;
    private BillListView mListView;
    private EditText mContent;
    private LinearLayout mLlEditor;
    private ScrollView mScrollView;
    private TextView mSend;
    private TextView mDuizhenTile;
    private PostCommentAdapter mAdapter;
    private String postId;
    private String postUserId;
    private String isLike;
    private String isFocus;
    private int likeNum;//点赞次数
    private int commentsNum;//评论次数
    private PopupWindow mPopupWindow;
    private TextView mOne;
    private TextView mTwo;
    private View inflate;
    private String betContent;
    //存胆的期号
    ArrayList<String> danList = new ArrayList<>();
    Map<String, List<StringBuilder>> map = new HashMap<>();

    @Override
    public int getInflateId() {
        return R.layout.activity_post_detail;
    }

    @Override
    public void init() {
        mPresenter = new PostDetailPresenter(this);
        mTitlBar = (TitleBar) findViewById(R.id.titlBar);
        mTitlBar.setImageTitleVisibility(false);
        mTitlBar.mShare.setImageResource(R.mipmap.white_more);
        mTitlBar.mShare.setVisibility(View.VISIBLE);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mTitle = (TextView) findViewById(R.id.detailTitle);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mUserName = (TextView) findViewById(userName);
        mDateTime = (TextView) findViewById(R.id.dateTime);
        mFocus = (TextView) findViewById(R.id.focus);
        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mBrowse = (TextView) findViewById(R.id.browse);
        mComment = (TextView) findViewById(R.id.comment);
        mPraise = (TextView) findViewById(R.id.praise);
        mLl_betContent = (LinearLayout) findViewById(R.id.ll_betContent);
        mTable_layout = (LinearLayout) findViewById(R.id.table_layout);
        mVsTimes = (LinearLayout) findViewById(R.id.vs_times);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mListView = (BillListView) findViewById(R.id.listView);
        mContent = (EditText) findViewById(R.id.content);
        mLlEditor = (LinearLayout) findViewById(R.id.ll_editor);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mSend = (TextView) findViewById(R.id.send);
        mDuizhenTile = (TextView) findViewById(R.id.duizhen_title_hint);

        inflate = View.inflate(mContext, R.layout.more_pop, null);//弹出的排序用的框
        // inflate=getActivity().getLayoutInflater().inflate(R.layout.order_god_pop,mScrollView,false);
        mOne = (TextView) inflate.findViewById(R.id.one);
        mTwo = (TextView) inflate.findViewById(R.id.two);
        mOne.setOnClickListener(ShareListener);//分享
        mTwo.setOnClickListener(ReportListener);//举报
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(DisplayUtil.dip2px(130));
        mPopupWindow.setHeight(DisplayUtil.dip2px(100));
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOnDismissListener(poponDismissListener);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        postId = getIntent().getStringExtra("postId");
        mAdapter = new PostCommentAdapter(commentList, mContext, ReplayListener);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void addListener() {
        mComment.setOnClickListener(CommentListener);//评论
        mSend.setOnClickListener(SendListener);//发送
        mPraise.setOnClickListener(PraiseListener);//点赞帖子
        mTitlBar.mImageBack.setOnClickListener(BackListener);
        mTitlBar.mShare.setOnClickListener(MoreListener);//头部导航栏，更多
        mFocus.setOnClickListener(FocusListener);//关注
        mScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > DisplayUtil.getDisplayHeight() / 3)) {
                    // mLlEdit.setVisibility(View.VISIBLE);
                    //Toast.makeText(getContext(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(1);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > DisplayUtil.getDisplayHeight() / 3)) {
                    // mLlEdit.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(2);
                }
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pn = 1;
                commentList.clear();
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pn < totalPages) {
                    pn++;
                    commentData();
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
        });
    }

    View.OnClickListener ShareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.dismiss();
            Share share = new Share(mContext, getString(R.string.share_text), "", "");
            share.showShare(Url.POST_SHARE_URL + postId + "?showH5=no");
        }
    };
    View.OnClickListener ReportListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.dismiss();
            ReportDialog reportDialog = new ReportDialog();
            reportDialog.selecte(mContext, postId, null);
        }
    };
    PopupWindow.OnDismissListener poponDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
            lp.alpha = 1f;
            ((Activity) mContext).getWindow().setAttributes(lp);
        }
    };
    View.OnClickListener BackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    View.OnClickListener FocusListener = new View.OnClickListener() {
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
            final String url;
            if ("1".equals(isFocus)) {
                url = "cancel";
            } else {
                url = "add";
            }
            HttpInterface2 mHttp = new HttpUtils2(mContext);
            Bundle params = new Bundle();
            params.putString("userId", LotteryApp.uid);
            params.putString("focusUserId", postUserId);
            mHttp.get(Url.FOCUS_GOD + url, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            if ("add".equals(url)) {
                                isFocus = "1";
                                mFocus.setText("已关注");
                                // mFocus.setBackgroundResource(R.drawable.shape_white_line);
                            } else {
                                isFocus = "0";
                                mFocus.setText("关注");
                                //mFocus.setBackgroundResource(R.drawable.shape_orange_10);
                            }
                            NetWorkData mNetWorkData = new NetWorkData(mContext);
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

    View.OnClickListener MoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
            lp.alpha = 0.6f;
            ((Activity) mContext).getWindow().setAttributes(lp);
            mPopupWindow.showAsDropDown(mTitlBar.mShare, -DisplayUtil.dip2px(60), -DisplayUtil.dip2px(5));
        }
    };
    View.OnClickListener PraiseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ClickUtils.isFastClick(1000)) {
                return;
            }
            if (!LotteryApp.isLogin) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivityForResult(intent, 2345);
                return;
            }
            HttpInterface2 mHttp = new HttpUtils2(mContext);
            final Bundle params = new Bundle();
            params.putString("id", postId);
            params.putString("token", LotteryApp.token);
            params.putString("target", "post");
            params.putString("timeStamp", System.currentTimeMillis() + "");
            if ("1".equals(isLike)) {
                isLike = "0";
                params.putString("isLike", "0");
                mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
                mPraise.setText((likeNum - 1) + "");
                mPraise.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                likeNum = likeNum - 1;
            } else {
                params.putString("isLike", "1");
                isLike = "1";
                mPraise.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.praise_anim));
                mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.red_praise), null, null, null);
                mPraise.setText((likeNum + 1) + "");
                mPraise.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                likeNum = likeNum + 1;
            }
            mHttp.post(Url.POST_LIKE, params, new JsonInterface() {
                @Override
                public void callback(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");

                        if (code != 0) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            if ("1".equals(isLike)) {
                                isLike = "0";
                                mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
                                mPraise.setText((likeNum - 1) + "");
                                mPraise.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                                likeNum = likeNum - 1;
                            } else {
                                isLike = "1";
                                mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.red_praise), null, null, null);
                                mPraise.setText((likeNum + 1) + "");
                                mPraise.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                                likeNum = likeNum + 1;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError() {
                    if ("1".equals(isLike)) {
                        isLike = "0";
                        mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
                        mPraise.setText((likeNum - 1) + "");
                        mPraise.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                        likeNum = likeNum - 1;
                    } else {
                        isLike = "1";
                        mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.red_praise), null, null, null);
                        mPraise.setText((likeNum + 1) + "");
                        mPraise.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                        likeNum = likeNum + 1;
                    }
                }
            });
        }
    };

    View.OnClickListener SendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!LotteryApp.isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if (TextUtils.isEmpty(LotteryApp.nikeName)) {
                Toast.makeText(mContext, "无法获取您的昵称", Toast.LENGTH_SHORT).show();
                new NetWorkData(mContext).refreshUserInfo();
                return;
            }
            String content = mContent.getText().toString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(mContext, "请输入发送内容", Toast.LENGTH_SHORT).show();
                return;
            }
            showLoading();
            if (replyFlag == 2) {
                Bundle parameters = new Bundle();
                parameters.putString("userName", LotteryApp.nikeName);
                parameters.putString("postId", postId);
                parameters.putString("postUserId", postUserId);
                parameters.putString("token", LotteryApp.token);
                parameters.putString("content", content);
                parameters.putString("timeStamp", System.currentTimeMillis() + "");
                mPresenter.sendCommentData(parameters, mContext);
            } else {
                Bundle parameters = new Bundle();
                parameters.putString("commentsId", commentsId + "");
                parameters.putString("postId", postId);
                parameters.putString("token", LotteryApp.token);
                parameters.putString("userName", LotteryApp.nikeName);
                parameters.putString("toReplyId", toReplyId);
                parameters.putString("toUserId", toUserId);
                parameters.putString("toUserName", toUserName);
                parameters.putString("replyContent", content);
                parameters.putString("replyFlag", replyFlag + "");
                parameters.putString("timeStamp", System.currentTimeMillis() + "");
                mPresenter.sendReplyData(parameters, mContext);
            }
        }
    };
    private int replyFlag = 2;//1针对评论回复, 0针对回复回复,2对帖子进行评论
    View.OnClickListener CommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            softControll();
            //isComment = true;
            replyFlag = 2;
            mContent.setHint("请输入评论内容");
        }
    };
    private String commentsId;
    private String toReplyId;
    private String toUserId;
    private String toUserName;
    private int commentPosition;
    private int replyPosition;
    PostCommentAdapter.onItemClickListener ReplayListener = new PostCommentAdapter.onItemClickListener() {

        @Override
        public void onItemClick(int position, CommentsBean bean) {

        }

        @Override
        public void onItemClick(int position) {
            softControll();
            //isComment = true;
            replyFlag = 1;
            PostCommentBean.DataBean.ListBean listBean = commentList.get(position);
            commentsId = listBean.getId() + "";
            toReplyId = "0";
            replyFlag = 1;
            toUserId = listBean.getUserId();
            toUserName = listBean.getUserName();

            mContent.setHint("回复" + toUserName + "的评论");
        }

        @Override
        public void onUserClick(String commentsId, UserBean bean) {
            if (bean == null) return;
            Bundle bundle = new Bundle();
            bundle.putString("userId", bean.getUserId());
            startActivity(GodInfoActivity.class, bundle);

        }

        @Override
        public void onContentClick(int commentPositio, int replyPositio, CommentsBean bean) {
            softControll();
            commentPosition = commentPositio;
            replyPosition = replyPositio;
            commentsId = bean.getCommentsId();
            toReplyId = bean.getToReplyId();
            replyFlag = 0;

            UserBean commentsUser = bean.getCommentsUser();
            toUserId = commentsUser.getUserId();
            toUserName = commentsUser.getUserName();

            mContent.setHint("回复" + toUserName);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://监听到软键盘弹起

                    break;
                case 2://监听到软件盘关闭

                    break;
            }
        }
    };

    private void softControll() {
        //    mLlEdit.setVisibility(View.VISIBLE);
        mContent.setFocusable(true);
        mContent.setFocusableInTouchMode(true);
        mContent.requestFocus();
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        toggleInput();

    }

    private int pn = 1;
    private int ps = 10;
    private int totalPages = 1;

    @Override
    public void addData() {
        Bundle param = new Bundle();
        param.putString("postId", postId);
        param.putString("userId", LotteryApp.uid);
        mPresenter.getPostDetailData(param, mContext);
        commentData();

    }

    private void commentData() {
        Bundle parameters = new Bundle();
        parameters.putString("postId", postId);
        parameters.putString("userId", LotteryApp.uid);
        parameters.putString("pageIndex", pn + "");
        parameters.putString("pageSize", ps + "");
        mPresenter.getCommentData(parameters, mContext);
    }

    PostDetailBean.DataBean postDetailData;

    @Override
    public void getPostDetailSuccess(String result) {
        dismissLoading();
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
        Gson gson = new Gson();
        PostDetailBean postDetailBean = gson.fromJson(result, PostDetailBean.class);
        int code = postDetailBean.getCode();
        String msg = postDetailBean.getMsg();
        if (code == 0) {
            postDetailData = postDetailBean.getData();
            String content = postDetailData.getContent();
            betContent = postDetailData.getBetContent();
            String userName = postDetailData.getUserName();
            postUserId = postDetailData.getUserId();
            commentsNum = postDetailData.getCommentsNum();
            likeNum = postDetailData.getLikeNum();
            int browseNum = postDetailData.getBrowseNum();
            String title = postDetailData.getTitle();
            long createTime = postDetailData.getCreateTime();
            isLike = postDetailData.getIsLike();

            if (LotteryApp.uid.equals(postUserId)) {
                mFocus.setVisibility(View.GONE);
            } else {
                mFocus.setVisibility(View.VISIBLE);
            }

            isFocus = postDetailData.getIsFocus();
            if ("1".equals(isFocus)) {
                mFocus.setText("已关注");
            } else {
                mFocus.setText("关注");
            }

            mTitle.setText(TextUtils.isEmpty(title) ? "" : title);
            mUserName.setText(TextUtils.isEmpty(userName) ? "" : userName);
            String time = DateFormtUtils.timeStamp2Date(createTime + "", DateFormtUtils.YMD_HM) + "    阅读: " + browseNum;
            mDateTime.setText(time);

            HttpUtils2.display(mAvatar, Url.HEADER_ROOT_URL + postUserId);

            String html = "<head><style>img{max-width:100%;}</style></head>" + content;
            mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

            mBrowse.setText(browseNum + "");
            mComment.setText(commentsNum + "");
            mPraise.setText(likeNum + "");

            if (TextUtils.isEmpty(isLike) || "0".equals(isLike)) {
                mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
            } else {
                mPraise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.red_praise), null, null, null);
            }
            if (!TextUtils.isEmpty(betContent)) {
                mLl_betContent.setVisibility(View.VISIBLE);
                String[] session = betContent.split("\\=");
                //所有期号拼接的字符串，逗号分隔
                StringBuilder sessions = new StringBuilder();
                for (int i = 0; i < session.length - 1; i++) {
                    //取后11位拼接
                    sessions.append(session[i].substring(session[i].length() - 11, session[i].length()));
                    if (i != session.length - 2) sessions.append(",");
                }
                Bundle parameters = new Bundle();
                parameters.putString("sessionId", String.valueOf(sessions));
                parameters.putString("lotCode", "竞彩篮球".equals(postDetailData.getPostFlag()) ? "43" : "42");
                mPresenter.getMatchBySession(parameters, mContext);
            } else {
                mLl_betContent.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getPostDetailError() {
        dismissLoading();
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }


    List<PostCommentBean.DataBean.ListBean> commentList = new ArrayList<>();

    @Override
    public void getCommentSuccess(String result) {
        dismissLoading();
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
        Gson gson = new Gson();
        PostCommentBean postCommentBean = gson.fromJson(result, PostCommentBean.class);
        int code = postCommentBean.getCode();
        String msg = postCommentBean.getMsg();
        if (code == 0) {
            PostCommentBean.DataBean data = postCommentBean.getData();
            totalPages = data.getTotalPages();
            commentList.addAll(data.getList());
            mAdapter.notifyDataSetChanged();
        } else {
            //  Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
        if (commentList.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else mNoData.setVisibility(View.GONE);
    }

    @Override
    public void getCommentError() {
        dismissLoading();
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
        if (commentList.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else mNoData.setVisibility(View.GONE);
    }

    @Override
    public void sendCommentSuccess(String result) {//评论帖子成功
        dismissLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 0) {
                commentList.clear();
                pn = 1;
                commentsNum++;
                mComment.setText(commentsNum + "");
                commentData();
                replyFlag = 2;//恢复到直接评论帖子的状态
                mContent.setText("");
                mContent.setHint("请输入评论内容......");
                hideInput(mContent);
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendCommentError() {
        dismissLoading();
    }

    @Override
    public void sendReplySuccess(String result) {//回复评论或者回复回复成功
        dismissLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 0) {
                List<PostCommentBean.DataBean.ListBean.ReplyInfoModelsBean> replyInfoModels = commentList.get(commentPosition).getReplyInfoModels();
                PostCommentBean.DataBean.ListBean.ReplyInfoModelsBean bean = new PostCommentBean.DataBean.ListBean.ReplyInfoModelsBean();
                bean.setUserName(LotteryApp.nikeName);
                bean.setUserId(LotteryApp.uid);
                bean.setCommentsId(commentsId);
                bean.setPostId(postId);
                bean.setToReplyId(toReplyId);
                bean.setToUserName(toUserName);
                bean.setToUserId(toUserId);
                bean.setReplyContent(mContent.getText().toString());
                bean.setReplyFlag(replyFlag);
                replyInfoModels.add(bean);
                mAdapter.notifyDataSetChanged();
                replyFlag = 2;//恢复到直接评论帖子的状态
                mContent.setText("");
                mContent.setHint("请输入评论内容......");
                hideInput(mContent);
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendReplyError() {
        dismissLoading();
    }

    @Override
    public void getMatchBySessionSuccess(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 0) {
                JSONObject dataJson = jsonObject.getJSONObject("data");
                JSONArray items = dataJson.getJSONArray("items");
                if (items == null) return;
                ArrayList<GameList> gameLists = new ArrayList<>();
                Gson mGson = new Gson();
                for (int i = 0; i < items.length(); i++) {
                    JSONObject jsonObject2 = items.getJSONObject(i);
                    GameList gameList = mGson.fromJson(jsonObject2.toString(), GameList.class);
                    gameLists.add(gameList);
                }
                //选中的购买内容的封装成一个map集合 并排序
                List<Map.Entry<GameList, ArrayList<String>>> list = orderMap(table(gameLists));
                addWidge(list);
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMatchBySessionError() {

    }

    //对已经封装好的填充控件用的集合进行排序
    private List<Map.Entry<GameList, ArrayList<String>>> orderMap(HashMap<GameList, ArrayList<String>> widgeMap) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<GameList, ArrayList<String>>> list = new ArrayList<>(widgeMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<GameList, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<GameList, ArrayList<String>> lhs, Map.Entry<GameList, ArrayList<String>> rhs) {
                return lhs.getKey().getSession().compareTo(rhs.getKey().getSession());
            }
        });

        return list;
    }

    Map<String, GameList> mapSort;
    String playsResult;

    private HashMap<GameList, ArrayList<String>> table(List<GameList> mData) {
        mapSort = new HashMap<>();
        for (int m = 0; m < mData.size(); m++) {
            mapSort.put(mData.get(m).getSession(), mData.get(m));
        }
        HashMap<GameList, ArrayList<String>> widgeMap = new HashMap<>();
        String[] first = betContent.split("\\;");
        for (int n = 0; n < first.length; n++) {
            //  map.clear();
            String[] strTimes = first[n].split("\\|");

            //如果不是混合
            if (!strTimes[0].equals("HH")) {
                //SPF|20180904002=3(1.56),20180904001=1(3.50)|2*1
                //如果不是混合的话前面就是玩法的标志
                String playMethod = LotteryTypes.getPlayMethod(strTimes[0]);
                //获取“|”中间的字符串
                String[] split1 = strTimes[1].split("\\,");
                //循环场次
                for (int i = 0; i < split1.length; i++) {

                    String keyMid = null;
                    ArrayList<String> widgeList = new ArrayList<>();
                    String[] split3 = split1[i].split("\\=|\\/");
                    keyMid = split3[0];
                    GameList gameList = mapSort.get(keyMid);
                    // gameList.setPresetScore("252,212.5,209,203");
                    String presetScore = gameList.getPresetScore();//篮球的大小分。
                    String let = gameList.getLet();
                    for (int j = 1; j < split3.length; j++) {
                        StringBuilder mStringBuilder = new StringBuilder();
                        mStringBuilder.append(playMethod);
                        //HH|DXF>20180125301=3(1.80),RFSF>20180125301=3{-8.5}(1.81),RFSF>20180124309=3{-3.5}(1.75)/0{-3.5}(1.75)|2*1
                        if ("RQSPF".equals(strTimes[0]) || "RFSF".equals(strTimes[0])) {
                            if (split3[j].contains("{")) {
                                String[] split = split3[j].split("\\{|\\}");//分割(是为了去掉赔率
                                playsResult = LotteryTypes.getRqspfStr(split[0]) + "{" + let.replaceAll("\\,", "  ") + "}" + split[2];
                            } else {
                                String[] split = split3[j].split("\\(");
                                playsResult = LotteryTypes.getRqspfStr(split[0]) + "(" + split[1];
                                // playsResult = LotteryTypes.getRqspfStr(split[0]);
                            }


                        } else {
                            String[] split = split3[j].split("\\(");
                            if ("BQC".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getBqcStr(split[0]) + "(" + split[1];
                                // playsResult = LotteryTypes.getBqcStr(split[0]);
                            } else if ("SPF".equals(strTimes[0]) || "SF".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getSpf(split[0]) + "(" + split[1];
                                // playsResult = LotteryTypes.getSpf(split[0]);
                            } else if ("DXF".equals(strTimes[0])) {
                                if (!TextUtils.isEmpty(presetScore)) {
                                    String s = (presetScore.replaceAll("\\+", "")).replaceAll("\\,", "  ");
                                    playsResult = LotteryTypes.getDxf(split[0]) + "{" + s + "}" + "(" + split[1];
                                } else {
                                    playsResult = LotteryTypes.getDxf(split[0]) + "(" + split[1];
                                }
                                //  playsResult = LotteryTypes.getDxf(split[0]);
                            } else if ("JQS".equals(strTimes[0])) {
                                if ("7".equals(split[0])) {
                                    playsResult = "7+(" + split[1];
                                    // playsResult = "7+";
                                } else {
                                    playsResult = split[0] + "(" + split[1];
                                    // playsResult = split[0];
                                }
                            } else if ("SFC".equals(strTimes[0])) {
                                playsResult = LotteryTypes.getSfcStr(split[0]) + "(" + split[1];
                                // playsResult = LotteryTypes.getSfcStr(split[0]);
                            } else {
                                if ("9:0".equals(split[0])) {
                                    playsResult = "胜其他(" + split[1];
                                    // playsResult = "胜其他(";
                                } else if ("9:9".equals(split[0])) {
                                    playsResult = "平其他(" + split[1];
                                    //playsResult = "平其他";
                                } else if ("0:9".equals(split[0])) {
                                    playsResult = "负其他(" + split[1];
                                    //  playsResult = "负其他";
                                } else {
                                    playsResult = split[0] + "(" + split[1];
                                    // playsResult = split[0];
                                }
                            }
                        }
                        mStringBuilder.append(playsResult);
                        widgeList.add(String.valueOf(mStringBuilder));
                    }
                    widgeMap.put(mapSort.get(keyMid), widgeList);
                }
            } else {
                //混合
                //取两个|中间的字符串进行分割
                String[] dantuoSplit = strTimes[1].split("\\$");
                //$符号前面和后面进行循环
                for (int j = 0; j < dantuoSplit.length; j++) {
                    String[] split = dantuoSplit[j].split("\\,");
                    for (int i = 0; i < split.length; i++) {
                        String[] split1 = split[i].split("\\>|\\=");
                        GameList gameList = mapSort.get(split1[1]);
                        String let = gameList.getLet();
                        //如果有胆，如果是$符号前面的也就是设胆的场，那么就将期号存起来
                        if (dantuoSplit.length > 1) {
                            if (j == 0) {
                                if (!danList.contains(split1[1])) {
                                    danList.add(split1[1]);
                                }
                            }
                        }
                        //两种情况，一是让球二是不让球
                        if ("RQSPF".equals(split1[0]) || "RFSF".equals(split1[0])) {
                            //RQSPF>20161107005=1{-1}(4.05)/3{-1}(6.35)/0{-1}(1.38);
                            List<StringBuilder> list;
                            //判断map中是否有这个场次,有的话取出来，没有创建
                            if (map.containsKey(split1[1])) {
                                list = map.get(split1[1]);
                                map.remove(split1[1]);
                            } else {
                                list = new ArrayList<>();
                            }
                            //把等号后面的字符串进行分离
                            String[] split2 = split1[2].split("\\/");
                            for (int k = 0; k < split2.length; k++) {
                                StringBuilder strBuilder = new StringBuilder();
                                if (split2[k].contains("{")) {
                                    //String[] split3 = split2[k].split("\\{|\\(");//加小括号的目的是去掉赔率
                                    String[] split3 = split2[k].split("\\{|\\}");
                                    strBuilder.append(LotteryTypes.getPlayMethod(split1[0]) + LotteryTypes.getRqSpf(split3[0]) + "{" + let.replaceAll("\\,", "  ") + "}" + split3[2]);
                                } else {
                                    String[] split3 = split2[k].split("\\(");
                                    strBuilder.append(LotteryTypes.getPlayMethod(split1[0]) + LotteryTypes.getRqSpf(split3[0]) + "(" + split3[1]);
                                    //strBuilder.append(LotteryTypes.getPlayMethod(split1[0]) + LotteryTypes.getRqSpf(split3[0]));
                                }
                                list.add(strBuilder);
                            }
                            map.put(split1[1], list);

                        } else {
                            //判断map中是否有这个场次
                            if (map.containsKey(split1[1])) {
                                List<StringBuilder> list = map.get(split1[1]);
                                map.remove(split1[1]);
                                sdfdsd(split1, list);
                            } else {
                                List<StringBuilder> list = new ArrayList<>();
                                sdfdsd(split1, list);
                            }
                        }

                    }
                }
                ArrayList<String> widgeList;
                for (Map.Entry<String, List<StringBuilder>> entry : map.entrySet()) {
                    String keyMid = entry.getKey();
                    //当参与推荐的时候加上的判断（原因是 每场比赛只会显示一种投注，别的在widgemap中被覆盖了。）
                    if (widgeMap.containsKey(mapSort.get(keyMid))) {
                        widgeList = widgeMap.get(mapSort.get(keyMid));
                    } else {
                        widgeList = new ArrayList<>();
                    }

                    for (int j = 0; j < entry.getValue().size(); j++) {
                        String s = String.valueOf(entry.getValue().get(j));
                        //当参与推荐的时候加上的判断（原因是显示的投注方式会重复）
                        if (!widgeList.contains(s)) {
                            widgeList.add(s);
                        }

                    }
                    widgeMap.put(mapSort.get(keyMid), widgeList);
                }
            }
        }
        return widgeMap;
    }

    //分割选择比赛的内容
    private void sdfdsd(String[] split1, List<StringBuilder> list) {
        GameList gameList = mapSort.get(split1[1]);
        // gameList.setPresetScore("252,212.5,209,203");
        String presetScore = gameList.getPresetScore();//大小分
        //把等号后面的字符串进行分离
        String[] split2 = split1[2].split("\\/");
        for (int j = 0; j < split2.length; j++) {
            StringBuilder strBuilder = new StringBuilder();
            //先拼接玩法
            strBuilder.append(LotteryTypes.getPlayMethod(split1[0]));
            //拼接玩法结果
            String[] split3 = split2[j].split("\\(|\\)");
            if ("BQC".equals(split1[0])) {
                playsResult = LotteryTypes.getBqcStr(split3[0]);
            } else if ("SPF".equals(split1[0]) || "SF".equals(split1[0])) {
                playsResult = LotteryTypes.getSpf(split3[0]);
            } else if ("DXF".equals(split1[0])) {
                if (!TextUtils.isEmpty(presetScore)) {
                    String s = (presetScore.replaceAll("\\+", "")).replaceAll("\\,", "  ");
                    playsResult = LotteryTypes.getDxf(split3[0]) + "{" + s + "}";
                } else {
                    playsResult = LotteryTypes.getDxf(split3[0]);
                }

            } else if ("JQS".equals(split1[0])) {
                if ("7".equals(split3[0])) {
                    playsResult = "7+";
                } else {
                    playsResult = split3[0];
                }
            } else if ("SFC".equals(split1[0])) {
                playsResult = LotteryTypes.getSfcStr(split3[0]);
            } else {
                if ("9:0".equals(split3[0])) {
                    playsResult = "胜其他";
                } else if ("9:9".equals(split3[0])) {
                    playsResult = "平其他";
                } else if ("0:9".equals(split3[0])) {
                    playsResult = "负其他";
                } else {
                    playsResult = split3[0];
                }
            }
            strBuilder.append(playsResult);
            //拼接赔率
            strBuilder.append("(" + split3[1] + ")");
            list.add(strBuilder);
        }
        map.put(split1[1], list);
    }

    // 填充控件
    public void addWidge(List<Map.Entry<GameList, ArrayList<String>>> list) {
        if (postDetailData == null) return;
        String postFlag = postDetailData.getPostFlag();
        if ("竞彩篮球".equals(postFlag)) {
            mDuizhenTile.setText("客队vs主队/投注选项");
        } else {
            mDuizhenTile.setText("主队vs客队/投注选项");
        }
        for (Map.Entry<GameList, ArrayList<String>> entry : list) {
            //判断是否中奖
            GameList dataBean = entry.getKey();
            View view = View.inflate(this, R.layout.item_post_match, null);
            view.setTag(dataBean.getuMid());
            view.setOnClickListener(MatcherListener);
            TextView mSession = (TextView) view.findViewById(R.id.session);
            TextView mTeamName = (TextView) view.findViewById(R.id.teamName);
            TextView mScheme = (TextView) view.findViewById(R.id.scheme);
            mVsTimes.addView(view);
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            for (int j = 0; j < entry.getValue().size(); j++) {
                String str = entry.getValue().get(j);
                if (str == null) return;
                ssb.append(IsWinnig.isWinning2(dataBean, str, "竞彩篮球".equals(postFlag) ? "43" : "42"));
                if (j != entry.getValue().size() - 1) ssb.append("\n");
            }
            mScheme.setText(ssb);
            //填充场次
            mSession.setText(DateFormtUtils.addMatch(entry.getKey().getSession()));
            //填充比分和谁跟谁比赛
            String title;
            String awary = dataBean.getGuestShortCn();
            String home = dataBean.getHomeShortCn();
            String session = dataBean.getSession();
             /*
        * 竞彩足球的主队在前面，客队在后面
        * 竞彩篮球的主队在后面，客队在前面
        * */
            if ("竞彩篮球".equals(postFlag)) {
                if (danList.contains(session)) {
                    title = awary + "VS" + home + "(胆)";
                } else {
                    title = awary + "VS" + home;
                }
            } else {
                if (danList.contains(session)) {
                    title = home + "VS" + awary + "(胆)";
                } else {
                    title = home + "VS" + awary;
                }
            }
            int vs = title.indexOf("VS");
            SpannableStringBuilder ssbTeam = new SpannableStringBuilder(title);
            ssbTeam.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, DisplayUtil.sp2px(11), ColorStateList.valueOf(getResources().getColor(R.color.red_theme)), null), vs, vs + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (danList.contains(session)) {
                int dan = title.indexOf("(胆)");
                ssbTeam.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, DisplayUtil.sp2px(11), ColorStateList.valueOf(getResources().getColor(R.color.red_theme)), null), dan, dan + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            mTeamName.setText(ssbTeam);
        }
    }

    View.OnClickListener MatcherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (postDetailData == null) return;
            if ("竞彩足球".equals(postDetailData.getPostFlag()) || "不分类".equals(postDetailData.getPostFlag())) {
                String mId = (String) v.getTag();
                if (TextUtils.isEmpty(mId)) return;
                Bundle bundle = new Bundle();
                bundle.putString("mId", mId);
                startActivity(ScoreDetailActivity.class, bundle);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2345 && resultCode == 40) {//登录完成刷新当前界面
            pn = 1;
            commentList.clear();
            addData();
        }
    }
}
