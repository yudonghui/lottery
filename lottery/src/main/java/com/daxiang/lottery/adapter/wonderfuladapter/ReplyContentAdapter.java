package com.daxiang.lottery.adapter.wonderfuladapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.CommentContentData;

import java.util.List;
/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class ReplyContentAdapter extends BaseAdapter {
   List<CommentContentData.ReplyBean> mList;
    Context mContext;
    public ReplyContentAdapter(Context mContext,List<CommentContentData.ReplyBean> mList){
        this.mList=mList;
        this.mContext=mContext;
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
        convertView=View.inflate(mContext, R.layout.item_reply_content,null);
        TextView mTextView= (TextView) convertView.findViewById(R.id.reply_content);
        String commentNickname = mList.get(position).getCommentNickname();
        String replyNickname = mList.get(position).getReplyNickname();
        String replyContent = mList.get(position).getReplyContent();
        SpannableString ss = new SpannableString(replyNickname + "回复" + commentNickname + ":" + replyContent);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),0,
                replyNickname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE),replyNickname.length()+2,
                replyNickname.length()+commentNickname.length()+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //为回复的人昵称添加点击事件
        ss.setSpan(new TextSpanClick(true), 0,
                replyNickname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //为评论的人的添加点击事件
        ss.setSpan(new TextSpanClick(false),replyNickname.length()+2,
                replyNickname.length()+commentNickname.length()+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(ss);
        //添加点击事件时，必须设置
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        return convertView;
    }
    private final class TextSpanClick extends ClickableSpan {
        private boolean status;
        public TextSpanClick(boolean status){
            this.status = status;
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);//取消下划线
        }
        @Override
        public void onClick(View v) {
            String msgStr = "";
            if(status){
                msgStr = "我是回复的人";
            }else{
                msgStr = "我是评论的人";
            }
            Toast.makeText(mContext, msgStr, Toast.LENGTH_SHORT).show();
        }
    }

}
