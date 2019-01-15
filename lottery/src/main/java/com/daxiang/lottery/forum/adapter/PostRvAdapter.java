package com.daxiang.lottery.forum.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.forum.activity.PostDetailActivity;
import com.daxiang.lottery.forum.bean.PostBean;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.XCRoundRectImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Android on 2018/8/3.
 */

public class PostRvAdapter extends RecyclerView.Adapter<PostRvAdapter.ViewHolder> {
    Context mContext;
    List<PostBean.DataBean.ListBean> mPostList;
    MoreListener mMoreListener;
    private final int right;
    private int width;
    private int height;

    public PostRvAdapter(Context mContext, List<PostBean.DataBean.ListBean> mPostList, MoreListener mMoreListener) {
        this.mContext = mContext;
        this.mPostList = mPostList;
        this.mMoreListener = mMoreListener;
        int displayWidth = DisplayUtil.getDisplayWidth();
        width = DisplayUtil.dip2px(105);
        height = DisplayUtil.dip2px(73);
        right = DisplayUtil.dip2px(10);
    }

    public interface MoreListener {
        void callBack(int position);
    }

    @Override
    public PostRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.item_post, null);
        inflate.setOnClickListener(ItemListener);
        return new PostRvAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(PostRvAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        PostBean.DataBean.ListBean dataBean = mPostList.get(position);
        holder.setData(dataBean, position);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mSign;
        private TextView mDateTime;
        private ImageView mMore;
        private TextView mTitle;
        private TextView mSource;
        private TextView mAuthor;
        private TextView mBrowseNum;
        private TextView mCommentNum;
        private TextView mPraiseNum;
        private XCRoundRectImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mSign = (ImageView) itemView.findViewById(R.id.sign);
            mDateTime = (TextView) itemView.findViewById(R.id.dateTime);
            mMore = (ImageView) itemView.findViewById(R.id.more);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mSource = (TextView) itemView.findViewById(R.id.source);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
            mBrowseNum = (TextView) itemView.findViewById(R.id.browseNum);
            mCommentNum = (TextView) itemView.findViewById(R.id.commentNum);
            mPraiseNum = (TextView) itemView.findViewById(R.id.praiseNum);
            mImage = (XCRoundRectImageView) itemView.findViewById(R.id.image);
        }

        public void setData(final PostBean.DataBean.ListBean dataBean, final int position) {
            String createTime = dataBean.getCreateTime();
            String title = dataBean.getTitle();
            final int id = dataBean.getId();
            String qualityFlag = dataBean.getQualityFlag();
            final String userId = dataBean.getUserId();
            String userName = dataBean.getUserName();
            String browseNum = dataBean.getBrowseNum();
            String commentsNum = dataBean.getCommentsNum();
            final String likeNum = dataBean.getLikeNum();
            final String isLike = dataBean.getIsLike();
            String userFlag = dataBean.getUserFlag();
            String pictureUrl = dataBean.getPictureUrl();
            String time = DateFormtUtils.timeStamp2Date(createTime, "M-dd HH:mm");
            if (TextUtils.isEmpty(pictureUrl)) {
                mImage.setVisibility(View.GONE);
            } else {
                mImage.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
                layoutParams.rightMargin = right;
                mImage.setLayoutParams(layoutParams);
                Picasso.with(mContext)
                        .load(pictureUrl)
                        .placeholder(R.mipmap.default_post)
                        .error(R.mipmap.default_post)
                        .centerCrop()
                        .resize(width, height)
                        .config(Bitmap.Config.RGB_565)
                        .into(mImage);
            }


            mDateTime.setText(time);
            if (userId != null && userId.equals(LotteryApp.uid)) {
                mMore.setVisibility(View.VISIBLE);
                mMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMoreListener.callBack(position);
                    }
                });
            } else {
                mMore.setVisibility(View.GONE);
            }
            mTitle.setText(TextUtils.isEmpty(title) ? "" : title);
            if ("1".equals(qualityFlag)) {
                mSign.setVisibility(View.VISIBLE);
                mSign.setImageResource(R.mipmap.jinghua);
            } else {
                mSign.setVisibility(View.GONE);
            }
            mAuthor.setText(TextUtils.isEmpty(userName) ? "" : userName);
            if ("0".equals(userFlag)) {//普通用户
                mSource.setVisibility(View.GONE);
            } else if ("1".equals(userFlag)) {//专家
                mSource.setVisibility(View.VISIBLE);
                mSource.setText("专家");
            } else if ("2".equals(userFlag)) {//官方
                mSource.setVisibility(View.VISIBLE);
                mSource.setText("官方");
            }
            mBrowseNum.setText(TextUtils.isEmpty(browseNum) ? "-" : browseNum);
            mCommentNum.setText(TextUtils.isEmpty(commentsNum) ? "-" : commentsNum);
            mPraiseNum.setText(TextUtils.isEmpty(likeNum) ? "-" : likeNum);
            if ("1".equals(isLike)) {
                mPraiseNum.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.red_praise), null, null, null);
                mPraiseNum.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            } else {
                mPraiseNum.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
                mPraiseNum.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
            }
            Log.e(position + "条：", isLike + "");
            mPraiseNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ClickUtils.isFastClick(1000)) {
                        return;
                    }
                    if (!LotteryApp.isLogin) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        ((Activity)mContext).startActivityForResult(intent,2345);
                        return;
                    }
                    HttpInterface2 mHttp = new HttpUtils2(mContext);
                    final Bundle params = new Bundle();
                    params.putString("id", id + "");
                    params.putString("token", LotteryApp.token);
                    params.putString("target", "post");
                    params.putString("timeStamp", System.currentTimeMillis() + "");
                    if ("1".equals(isLike)) {
                        params.putString("isLike", "0");
                        mPraiseNum.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.gray_praise), null, null, null);
                        mPraiseNum.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                        mPraiseNum.setText((Integer.parseInt(likeNum) - 1) + "");
                    } else {
                        params.putString("isLike", "1");
                        mPraiseNum.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.praise_anim));
                        mPraiseNum.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.red_praise), null, null, null);
                        mPraiseNum.setTextColor(mContext.getResources().getColor(R.color.red_txt));
                        mPraiseNum.setText((Integer.parseInt(likeNum) + 1) + "");
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
                                        dataBean.setIsLike("0");
                                        dataBean.setLikeNum((Integer.parseInt(likeNum) - 1) + "");
                                    } else {
                                        dataBean.setIsLike("1");
                                        dataBean.setLikeNum((Integer.parseInt(likeNum) + 1) + "");
                                    }
                                } else {
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
        }
    }

    View.OnClickListener ItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            int position = (int) v.getTag();
            Intent intent = new Intent(mContext, PostDetailActivity.class);
            intent.putExtra("postId", mPostList.get(position).getId() + "");
            mContext.startActivity(intent);
            // Toast.makeText(mContext, "位置：" + position, Toast.LENGTH_SHORT).show();
        }
    };
}
