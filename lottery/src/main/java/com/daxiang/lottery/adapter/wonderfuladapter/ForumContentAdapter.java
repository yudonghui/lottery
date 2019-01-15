package com.daxiang.lottery.adapter.wonderfuladapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.CommentContentData;
import com.daxiang.lottery.view.BillListView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class ForumContentAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CommentContentData> mList;

    public ForumContentAdapter(Context mContext, ArrayList<CommentContentData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_comment_content, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mImage_deader = (ImageView) convertView.findViewById(R.id.image_deader);
            mViewHolder.mLl_author_date = (LinearLayout) convertView.findViewById(R.id.ll_author_date);
            mViewHolder.mText_author = (TextView) convertView.findViewById(R.id.text_author);
            mViewHolder.mText_date = (TextView) convertView.findViewById(R.id.text_date);
            mViewHolder.mLl_good = (LinearLayout) convertView.findViewById(R.id.ll_good);
            mViewHolder.mImage_good = (ImageView) convertView.findViewById(R.id.image_good);
            mViewHolder.mZan_number = (TextView) convertView.findViewById(R.id.zan_number);
            mViewHolder.mComment_content = (TextView) convertView.findViewById(R.id.comment_content);
            mViewHolder.mLv_reply = (BillListView) convertView.findViewById(R.id.lv_reply);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.mComment_content.setText(mList.get(position).getCommentContent());
        mViewHolder.mImage_deader.setImageResource(mList.get(position).getId());
        mViewHolder.mText_author.setText(mList.get(position).getCommentNickname());
        List<CommentContentData.ReplyBean> replyList = mList.get(position).getReplyList();
        ReplyContentAdapter replyContentAdapter = new ReplyContentAdapter(mContext, replyList);
        mViewHolder.mLv_reply.setAdapter(replyContentAdapter);
        return convertView;
    }

    class ViewHolder {
        ImageView mImage_deader;
        LinearLayout mLl_author_date;
        TextView mText_author;
        TextView mText_date;
        LinearLayout mLl_good;
        ImageView mImage_good;
        TextView mZan_number;
        TextView mComment_content;
       BillListView mLv_reply;
    }
}
