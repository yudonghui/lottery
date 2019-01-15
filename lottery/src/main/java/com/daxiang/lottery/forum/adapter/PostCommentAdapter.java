package com.daxiang.lottery.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.forum.bean.CommentsBean;
import com.daxiang.lottery.forum.bean.PostCommentBean;
import com.daxiang.lottery.forum.bean.UserBean;
import com.daxiang.lottery.forum.selfview.CommentsView;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.HttpUtils2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/8/22.
 */

public class PostCommentAdapter extends BaseAdapter {
    List<PostCommentBean.DataBean.ListBean> commentList;
    Context mContext;
    onItemClickListener mCommentListener;

    public PostCommentAdapter(List<PostCommentBean.DataBean.ListBean> commentList,
                              Context mContext,
                              onItemClickListener mCommentListener) {
        this.commentList = commentList;
        this.mContext = mContext;
        this.mCommentListener = mCommentListener;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_comment, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            mViewHolder.mUserName = (TextView) convertView.findViewById(R.id.userName);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.time);
            mViewHolder.mReply = (TextView) convertView.findViewById(R.id.reply);
            mViewHolder.mPraise = (TextView) convertView.findViewById(R.id.praise);
            mViewHolder.mContent = (TextView) convertView.findViewById(R.id.content);
            mViewHolder.mCommentView = (CommentsView) convertView.findViewById(R.id.commentView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final PostCommentBean.DataBean.ListBean listBean = commentList.get(position);
        String content = listBean.getContent();
        long createTime = listBean.getCreateTime();
        String userId = listBean.getUserId();
        String userName = listBean.getUserName();
        int replyNum = listBean.getReplyNum();
        final int likeNum = listBean.getLikeNum();
        final String isLike = listBean.getIsLike();
        final int id = listBean.getId();

        HttpUtils2.display(mViewHolder.mAvatar, Url.HEADER_ROOT_URL + userId, R.mipmap.default_header);

        mViewHolder.mUserName.setText(TextUtils.isEmpty(userName) ? "" : userName);
        mViewHolder.mContent.setText(TextUtils.isEmpty(content) ? "" : content);
        mViewHolder.mReply.setText(replyNum + "");
        mViewHolder.mPraise.setText(likeNum + "");
        if ("1".equals(isLike)) {
            mViewHolder.mPraise.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.red_praise), null, null, null);
            mViewHolder.mPraise.setTextColor(mContext.getResources().getColor(R.color.red_txt));
        } else {
            mViewHolder.mPraise.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
            mViewHolder.mPraise.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
        }
        mViewHolder.mTime.setText(DateFormtUtils.timeDifference(createTime));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentListener.onItemClick(position);
            }
        });

        List<PostCommentBean.DataBean.ListBean.ReplyInfoModelsBean> replyInfoModels = listBean.getReplyInfoModels();
        if (replyInfoModels == null || replyInfoModels.size() == 0) {
            mViewHolder.mCommentView.setVisibility(View.GONE);
        } else {
            mViewHolder.mCommentView.setVisibility(View.VISIBLE);
            /**
             * 封装对于帖子评论的回复 以及回复的回复
             * */
            List<CommentsBean> mList = new ArrayList<>();
            for (int i = 0; i < replyInfoModels.size(); i++) {
                CommentsBean commentsBean = new CommentsBean();
                PostCommentBean.DataBean.ListBean.ReplyInfoModelsBean replyBean = replyInfoModels.get(i);
                commentsBean.setCommentsId(replyBean.getCommentsId());
                commentsBean.setContent(replyBean.getReplyContent());
                commentsBean.setToReplyId(replyBean.getId());

                UserBean commentsUser = new UserBean();
                commentsUser.setUserName(replyBean.getUserName());
                commentsUser.setUserId(replyBean.getUserId());
                commentsBean.setCommentsUser(commentsUser);
                if (replyBean.getReplyFlag() == 0) {//针对回复的回复
                    UserBean replyUser = new UserBean();
                    replyUser.setUserName(replyBean.getToUserName());
                    replyUser.setUserId(replyBean.getToUserId());
                    commentsBean.setReplyUser(replyUser);
                }
                mList.add(commentsBean);
            }
            mViewHolder.mCommentView.setList(mList);
            mViewHolder.mCommentView.notifyDataSetChanged();
            mViewHolder.mCommentView.setOnItemClickListener(new CommentsView.onItemClickListener() {
                @Override
                public void onItemClick(int position, CommentsBean bean) {
                    mCommentListener.onItemClick(position, bean);
                }

                @Override
                public void onUserClick(String commentsId, UserBean bean) {
                    mCommentListener.onUserClick(commentsId, bean);
                }

                @Override
                public void onContentClick(int replyPosition, CommentsBean bean) {
                    mCommentListener.onContentClick(position, replyPosition, bean);
                }
            });
        }

        mViewHolder.mPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (ClickUtils.isFastClick(1000)) {
                    return;
                }
                if (!LotteryApp.isLogin) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                HttpInterface2 mHttp = new HttpUtils2(mContext);
                final Bundle params = new Bundle();
                params.putString("id", id + "");
                params.putString("token", LotteryApp.token);
                params.putString("target", "comment");
                params.putString("timeStamp", System.currentTimeMillis() + "");
                if ("1".equals(isLike)) {
                    params.putString("isLike", "0");
                    mViewHolder.mPraise.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
                    mViewHolder.mPraise.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                    mViewHolder.mPraise.setText((likeNum - 1) + "");
                } else {
                    params.putString("isLike", "1");
                    mViewHolder.mPraise.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.praise_anim));
                    mViewHolder.mPraise.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.red_praise), null, null, null);
                    mViewHolder.mPraise.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                    mViewHolder.mPraise.setText((likeNum + 1) + "");
                }
                mHttp.post(Url.POST_LIKE, params, new JsonInterface() {
                    @Override
                    public void callback(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                if ("1".equals(isLike)) {
                                    listBean.setIsLike("0");
                                    listBean.setLikeNum(likeNum - 1);
                                    // mPraise.setImageResource(R.mipmap.gray_praise);
                                    // mPraiseNum.setText((Integer.parseInt(likeNum) - 1) + "");
                                } else {
                                    listBean.setIsLike("1");
                                    listBean.setLikeNum(likeNum + 1);
                                    // mPraise.setImageResource(R.mipmap.red_praise);
                                    // mPraiseNum.setText((Integer.parseInt(likeNum) + 1) + "");
                                }
                            }else {
                                Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                            }
                            notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError() {
                        notifyDataSetChanged();
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView mAvatar;
        private TextView mUserName;
        private TextView mTime;
        private TextView mReply;
        private TextView mPraise;
        private TextView mContent;
        private CommentsView mCommentView;

    }

    /**
     * 定义一个用于回调的接口
     */
    public interface onItemClickListener {
        //点击评论的整个条目
        void onItemClick(int position);

        //点击回复整个条目
        void onItemClick(int position, CommentsBean bean);

        //点击评论人
        void onUserClick(String commentsId, UserBean bean);

        //点击评论内容
        void onContentClick(int commentPosition, int replyPosition, CommentsBean bean);
    }
}
