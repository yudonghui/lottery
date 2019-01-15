package com.daxiang.lottery.forum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.forum.bean.CommentsBean;
import com.daxiang.lottery.forum.bean.MessageBean;
import com.daxiang.lottery.forum.bean.UserBean;
import com.daxiang.lottery.forum.selfview.CommentsView;
import com.daxiang.lottery.forum.selfview.FlexibleRoundedBitmapDisplayer;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/8/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context mContext;
    List<MessageBean.DataBean.ListBean> mMessageList;
    ClickCallBack mClickCallBack;

    public MessageAdapter(Context mContext,
                          List<MessageBean.DataBean.ListBean> mMessageList,
                          ClickCallBack mClickCallBack) {
        this.mContext = mContext;
        this.mMessageList = mMessageList;
        this.mClickCallBack = mClickCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.item_message, null);
        inflate.setOnClickListener(ItemListener);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        MessageBean.DataBean.ListBean dataBean = mMessageList.get(position);
        holder.setData(dataBean, position);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mComment_avatar;
        private TextView mComment_nikename;
        private ImageView mMore;
        private TextView mContent;
        private LinearLayout mMiddle_gray;
        private ImageView mAvatar;
        private TextView mTitle;
        private TextView mNikename;
        private TextView mTime;
        private TextView mReply;
        private CommentsView mCommentView;

        public ViewHolder(View itemView) {
            super(itemView);
            mComment_avatar = (ImageView) itemView.findViewById(R.id.comment_avatar);
            mComment_nikename = (TextView) itemView.findViewById(R.id.comment_nikename);
            mMore = (ImageView) itemView.findViewById(R.id.more);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mMiddle_gray = (LinearLayout) itemView.findViewById(R.id.middle_gray);
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mNikename = (TextView) itemView.findViewById(R.id.nikename);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mReply = (TextView) itemView.findViewById(R.id.reply);
            mCommentView = (CommentsView) itemView.findViewById(R.id.commentView);
            mCommentView.setTextSize(13);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.getDisplayWidth() - DisplayUtil.dip2px(70), DisplayUtil.dip2px(50));
            layoutParams.leftMargin = DisplayUtil.dip2px(60);
            layoutParams.rightMargin = DisplayUtil.dip2px(10);
            mMiddle_gray.setLayoutParams(layoutParams);
        }

        public void setData(MessageBean.DataBean.ListBean dataBean, final int position) {
            String createTime = dataBean.getCreateTime();
            String userName = dataBean.getUserName();
            String content = dataBean.getContent();
            String userId = dataBean.getUserId();//
            int toReplyId = dataBean.getToReplyId();
            String postUserName = dataBean.getPostUserName();//发帖人姓名
            String postTitle = dataBean.getPostTitle();//帖子标题
            String postPictureUrl = dataBean.getPostPictureUrl();//帖子图片
            MessageBean.DataBean.ListBean.ReplyInfoBean replyInfo = dataBean.getReplyInfo();


            mTitle.setText(TextUtils.isEmpty(postTitle) ? "" : postTitle);
            mComment_nikename.setText(TextUtils.isEmpty(userName) ? "" : userName);
            mContent.setText(TextUtils.isEmpty(content) ? "" : content);
            mNikename.setText(TextUtils.isEmpty(postUserName) ? "" : postUserName);
            HttpUtils2.display(mComment_avatar, Url.HEADER_ROOT_URL + userId, R.mipmap.default_header);
            if (!TextUtils.isEmpty(createTime))
                mTime.setText(DateFormtUtils.timeDifference(Long.parseLong(createTime)));
            //String postPictureUrl = "http://img0.imgtn.bdimg.com/it/u=1223347380,2741136198&fm=26&gp=0.jpg";
            if (!TextUtils.isEmpty(postPictureUrl)) {
                mAvatar.setVisibility(View.VISIBLE);
                setRoundedImage(postPictureUrl, DisplayUtil.dip2px(10),
                        FlexibleRoundedBitmapDisplayer.CORNER_TOP_LEFT | FlexibleRoundedBitmapDisplayer.CORNER_BOTTOM_LEFT,
                        R.mipmap.test_bg, mAvatar);
            } else {
                mAvatar.setVisibility(View.GONE);
            }
            if (replyInfo == null) {
                mCommentView.setVisibility(View.GONE);
            } else {
                /**
                 * 封装对于帖子评论的回复 以及回复的回复
                 * */
                List<CommentsBean> mList = new ArrayList<>();
                mCommentView.setVisibility(View.VISIBLE);
                CommentsBean commentsBean = new CommentsBean();
                commentsBean.setCommentsId(replyInfo.getCommnetId() + "");
                commentsBean.setContent(replyInfo.getContent());
                commentsBean.setToReplyId(toReplyId + "");
                UserBean commentsUser = new UserBean();
                commentsUser.setUserName(replyInfo.getUserName());
                commentsUser.setUserId(replyInfo.getUserId());
                commentsBean.setCommentsUser(commentsUser);
                if (replyInfo.getReplyType() == 0) {//针对回复的回复
                    UserBean replyUser = new UserBean();
                    replyUser.setUserName(replyInfo.getToUserName());
                    replyUser.setUserId(replyInfo.getToUserId());
                    commentsBean.setReplyUser(replyUser);
                }
                mList.add(commentsBean);
                mCommentView.setList(mList);
                mCommentView.notifyDataSetChanged();
                mCommentView.setOnItemClickListener(new CommentsView.onItemClickListener() {
                    @Override
                    public void onItemClick(int position, CommentsBean bean) {
                    }

                    @Override
                    public void onUserClick(String commentsId, UserBean bean) {
                        mClickCallBack.onUserClick(commentsId, bean);
                    }

                    @Override
                    public void onContentClick(int replyPosition, CommentsBean bean) {
                    }
                });
            }
            mReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickCallBack.callBack(position, 3);
                }
            });
            mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickCallBack.callBack(position, 1);
                }
            });
            mMiddle_gray.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickCallBack.callBack(position, 2);
                }
            });
        }
    }
    /*
         * 设置图片---自定义图片4个角中的指定角为圆角
         * @param url 图片的url
         * @param cornerRadius 圆角像素大小
         * @param corners 自定义圆角:<br>
         * 以下参数为FlexibleRoundedBitmapDisplayer中静态变量:<br>
         * CORNER_NONE　无圆角<br>
         * CORNER_ALL 全为圆角<br>
         * CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT　指定圆角（选其中若干组合  ） <br>
         * @param image url为空时加载该图片
         * @param imageView 要设置图片的ImageView
         */

    public void setRoundedImage(String url, int cornerRadius, int corners, int defaultImage, ImageView imageView) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).imageDownloader(
                new BaseImageDownloader(mContext, 60 * 1000, 60 * 1000)) // connectTimeout超时时间
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage).showStubImage(defaultImage)
                .showImageForEmptyUri(defaultImage)//url为空时显示的图片
                .showImageOnFail(defaultImage)//加载失败显示的图片
                .cacheInMemory()//内存缓存
                .cacheOnDisc()//磁盘缓存
                .displayer(new FlexibleRoundedBitmapDisplayer(cornerRadius, corners)) // 自定义增强型BitmapDisplayer
                .build();
        imageLoader.displayImage(url, imageView, options);

    }

    View.OnClickListener ItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            int position = (int) v.getTag();

        }
    };

    public interface ClickCallBack {
        //type 1更多，2中间帖子，3回复
        void callBack(int position, int type);
        //评论ID bean用户信息
        void onUserClick(String commentsId, UserBean bean);
    }

}
