package com.daxiang.lottery.adapter.wonderfuladapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.ForumData;

import java.util.ArrayList;
/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class FocusAdapter extends BaseAdapter {
    ArrayList<ForumData> mList;
    Context mContext;
    ArrayList<Integer> clickList = new ArrayList<>();

    public FocusAdapter(ArrayList<ForumData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_lv_focus, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mImage_deader = (ImageView) convertView.findViewById(R.id.image_deader);
            mViewHolder.mLl_author_date = (LinearLayout) convertView.findViewById(R.id.ll_author_date);
            mViewHolder.mText_name = (TextView) convertView.findViewById(R.id.text_name);
            mViewHolder.mText_introduce = (TextView) convertView.findViewById(R.id.text_introduce);
            mViewHolder.mText_isfocus = (TextView) convertView.findViewById(R.id.text_isfocus);
            convertView.setTag(convertView);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mImage_deader.setImageResource(mList.get(position).getReaderId());
        mViewHolder.mText_introduce.setText(mList.get(position).getQianming());
        mViewHolder.mText_name.setText(mList.get(position).getName());
        mViewHolder.mText_isfocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickList.contains(position)) {
                    mViewHolder.mText_isfocus.setText("关注");
                    mViewHolder.mText_isfocus.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                    clickList.remove(position);
                } else {
                    mViewHolder.mText_isfocus.setText("取消关注");
                    mViewHolder.mText_isfocus.setTextColor(mContext.getResources().getColor(R.color.red_theme));
                    clickList.add(position);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView mImage_deader;
        private LinearLayout mLl_author_date;
        private TextView mText_name;
        private TextView mText_introduce;
        private TextView mText_isfocus;
    }
}
