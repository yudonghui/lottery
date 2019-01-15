package com.daxiang.lottery.adapter.wonderfuladapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.wonderfulactivity.LotteryForum.AnotherUserActivity;
import com.daxiang.lottery.activity.wonderfulactivity.LotteryForum.CommentContentActivity;
import com.daxiang.lottery.entity.ForumData;
import com.daxiang.lottery.utils.dialogutils.IosDialogUtils;
import com.daxiang.lottery.view.BallGridView;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class ForumLvAdapter extends BaseAdapter {
    private int colorRed;
    private int colorGray;
    ArrayList<ForumData> mList;
    HashMap<Integer, ArrayList<String>> clickMap = new HashMap<>();
    Context mContext;

    public ForumLvAdapter(ArrayList<ForumData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        colorRed = mContext.getResources().getColor(R.color.red_theme);
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
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
            convertView = View.inflate(parent.getContext(), R.layout.item_lv_forum, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mImage_deader = (ImageView) convertView.findViewById(R.id.image_deader);
            mViewHolder.mLl_author_date = (LinearLayout) convertView.findViewById(R.id.ll_author_date);
            mViewHolder.mText_author = (TextView) convertView.findViewById(R.id.text_author);
            mViewHolder.mText_date = (TextView) convertView.findViewById(R.id.text_date);
            mViewHolder.mXiangxia = (ImageView) convertView.findViewById(R.id.xiangxia);
            mViewHolder.mText_content = (TextView) convertView.findViewById(R.id.text_content);
            mViewHolder.mGv_forum = (BallGridView) convertView.findViewById(R.id.gv_forum);
            mViewHolder.mLl_collection = (LinearLayout) convertView.findViewById(R.id.ll_collection);
            mViewHolder.mImage_collection = (ImageView) convertView.findViewById(R.id.image_collection);
            mViewHolder.mText_collection = (TextView) convertView.findViewById(R.id.text_collection);
            mViewHolder.mLl_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            mViewHolder.mImage_comment = (ImageView) convertView.findViewById(R.id.image_comment);
            mViewHolder.mText_comment = (TextView) convertView.findViewById(R.id.text_comment);
            mViewHolder.mLl_good = (LinearLayout) convertView.findViewById(R.id.ll_good);
            mViewHolder.mImage_good = (ImageView) convertView.findViewById(R.id.image_good);
            mViewHolder.mText_good = (TextView) convertView.findViewById(R.id.text_good);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //解决复用问题
        resolveRepeat(mViewHolder, position);
        mViewHolder.mText_content.setText(mList.get(position).getContent());
        mViewHolder.mImage_deader.setImageResource(mList.get(position).getReaderId());
        addListener(mViewHolder, position);
        ForumGvAdapter mGvAdapter = new ForumGvAdapter(mContext, mList.get(position).getImageNum());
        mViewHolder.mGv_forum.setAdapter(mGvAdapter);
        return convertView;
    }

    private void resolveRepeat(ViewHolder mViewHolder, int position) {
        if (clickMap.containsKey(position)) {
            if (clickMap.get(position).contains("collection")) {
                mViewHolder.mImage_collection.setImageResource(R.drawable.collection_red);
                mViewHolder.mText_collection.setTextColor(colorRed);
            } else {
                mViewHolder.mImage_collection.setImageResource(R.drawable.collection_gray);
                mViewHolder.mText_collection.setTextColor(colorGray);
            }
            if (clickMap.get(position).contains("comment")) {
                mViewHolder.mImage_comment.setImageResource(R.drawable.comment_red);
                mViewHolder.mText_comment.setTextColor(colorRed);
            } else {
                mViewHolder.mImage_comment.setImageResource(R.drawable.comment_gray);
                mViewHolder.mText_comment.setTextColor(colorGray);
            }
            if (clickMap.get(position).contains("good")) {
                mViewHolder.mImage_good.setImageResource(R.drawable.good_red);
                mViewHolder.mText_good.setTextColor(colorRed);
            } else {
                mViewHolder.mImage_good.setImageResource(R.drawable.good_gray);
                mViewHolder.mText_good.setTextColor(colorGray);
            }
        } else {
            mViewHolder.mImage_collection.setImageResource(R.drawable.collection_gray);
            mViewHolder.mText_collection.setTextColor(colorGray);
            mViewHolder.mImage_comment.setImageResource(R.drawable.comment_gray);
            mViewHolder.mText_comment.setTextColor(colorGray);
            mViewHolder.mImage_good.setImageResource(R.drawable.good_gray);
            mViewHolder.mText_good.setTextColor(colorGray);
        }
    }

    private void addListener(final ViewHolder mViewHolder, final int position) {
        //点击头像
        mViewHolder.mImage_deader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AnotherUserActivity.class));
            }
        });
        //点击标题和签名
        mViewHolder.mLl_author_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,AnotherUserActivity.class));
            }
        });
        //点击发帖内容
        mViewHolder.mText_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentContentActivity.class);
                intent.putExtra("num",mList.get(position).getImageNum());
                mContext.startActivity(intent);
            }
        });
        mViewHolder.mXiangxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IosDialogUtils.IosStyleDialog(mContext);
                IosDialogUtils.setFocusListener("关注",mDialogListener);
                IosDialogUtils.setSendtoListener("转发",mDialogListener);
            }
        });
        mViewHolder.mLl_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionFlag) {
                    //处于收藏状态,置成非收藏状态
                    collectionFlag = false;
                    mViewHolder.mImage_collection.setImageResource(R.drawable.collection_gray);
                    mViewHolder.mText_collection.setTextColor(colorGray);
                    exist(position, "collection");
                } else {
                    //处于未收藏状态，置成收藏状态
                    collectionFlag = true;
                    mViewHolder.mImage_collection.setImageResource(R.drawable.collection_red);
                    mViewHolder.mText_collection.setTextColor(colorRed);
                    noExist(position, "collection");
                }
            }
        });
        mViewHolder.mLl_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentFlag) {
                    //处于收藏状态,置成非收藏状态
                    commentFlag = false;
                    mViewHolder.mImage_comment.setImageResource(R.drawable.comment_gray);
                    mViewHolder.mText_comment.setTextColor(colorGray);
                    exist(position, "comment");
                } else {
                    //处于未收藏状态，置成收藏状态
                    commentFlag = true;
                    mViewHolder.mImage_comment.setImageResource(R.drawable.comment_red);
                    mViewHolder.mText_comment.setTextColor(colorRed);
                    noExist(position, "comment");
                }
            }
        });
        mViewHolder.mLl_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodFlag) {
                    //处于收藏状态,置成非收藏状态
                    goodFlag = false;
                    mViewHolder.mImage_good.setImageResource(R.drawable.good_gray);
                    mViewHolder.mText_good.setTextColor(colorGray);
                    exist(position, "good");
                } else {
                    //处于未收藏状态，置成收藏状态
                    goodFlag = true;
                    mViewHolder.mImage_good.setImageResource(R.drawable.good_red);
                    mViewHolder.mText_good.setTextColor(colorRed);
                    noExist(position, "good");
                }
            }
        });
    }

    IosDialogUtils.DialogListener mDialogListener = new IosDialogUtils.DialogListener() {
        @Override
        public void callBack(View view) {
            switch (view.getId()) {
                case R.id.buttonCamera:
                    //关注

                    break;
                case R.id.buttonPhoto_selector:
                    //转发

                    break;
            }
        }
    };

    private void exist(int position, String str) {
        clickMap.get(position).remove(str);
        if (clickMap.get(position).size() == 0) {
            clickMap.remove(position);
        }
    }

    private void noExist(int position, String str) {
        if (clickMap.containsKey(position)) {
            clickMap.get(position).add(str);
        } else {
            ArrayList<String> list = new ArrayList<>();
            list.add(str);
            clickMap.put(position, list);
        }
    }

    private boolean collectionFlag = false;
    private boolean commentFlag = false;
    private boolean goodFlag = false;

    class ViewHolder {
        private ImageView mImage_deader;
        private LinearLayout mLl_author_date;
        private TextView mText_author;
        private TextView mText_date;
        private ImageView mXiangxia;
        private TextView mText_content;
        private BallGridView mGv_forum;
        private LinearLayout mLl_collection;
        private ImageView mImage_collection;
        private TextView mText_collection;
        private LinearLayout mLl_comment;
        private ImageView mImage_comment;
        private TextView mText_comment;
        private LinearLayout mLl_good;
        private ImageView mImage_good;
        private TextView mText_good;
    }
}
