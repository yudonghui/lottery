package com.daxiang.lottery.score;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.CollectionListener;
import com.daxiang.lottery.entity.ColletionDb;
import com.daxiang.lottery.utils.DbManager;
import com.squareup.picasso.Picasso;

import org.xutils.ex.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/8
 * @describe May the Buddha bless bug-free!!!
 */
public class ScoreAdapter extends BaseExpandableListAdapter {
    ArrayList<ArrayList<ScoreBean.DataBean.ItemsBean>> mListData;
    Context mContext;
    DbManager dbManager = new DbManager();
    HashMap<String, String> map = new HashMap<>();
    CollectionListener mCollectionListener;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};


    public void setData(ArrayList<ArrayList<ScoreBean.DataBean.ItemsBean>> mListData, Context mContext,
                        CollectionListener mCollectionListener) {
        this.mContext = mContext;
        this.mListData = mListData;
        this.mCollectionListener = mCollectionListener;
        //将数据库中的收藏取出来
        List<ColletionDb> colletionDbs = null;
        try {
            colletionDbs = dbManager.queryAll();
            if (colletionDbs != null) {
                for (int i = 0; i < colletionDbs.size(); i++) {
                    String match_id = colletionDbs.get(i).getMatch_id();
                    map.put(match_id, match_id);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getGroupCount() {
        if (mListData == null || mListData.size() == 0) {
            return 0;
        } else {
            return mListData.size();
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_match_day_category, null);
            holder = new GroupHolder();
            holder.mTitleTextView = (TextView) convertView.findViewById(R.id.text_match_date);
            holder.mIconXia = (ImageView) convertView.findViewById(R.id.icon_xia);

            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        String issue = mListData.get(groupPosition).get(0).getDate();


        // String substring = split.substring(0, 8);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(issue);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            holder.mTitleTextView.setText(issue + weekDays[i1 - 1] + "共" + mListData.get(groupPosition).size() + "场比赛");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mIconXia.setImageResource(isExpanded ? R.drawable.icon_xia2 : R.drawable.icon_xia);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder mChildHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_score_child2, null);
            mChildHolder = new ChildHolder();
            mChildHolder.mTv_week = (TextView) convertView.findViewById(R.id.tv_week);
            mChildHolder.mTv_lname = (TextView) convertView.findViewById(R.id.tv_lname);
            mChildHolder.mTv_start_time = (TextView) convertView.findViewById(R.id.tv_start_time);
            mChildHolder.mTv_host_name = (TextView) convertView.findViewById(R.id.tv_host_name);
            mChildHolder.mScore = (TextView) convertView.findViewById(R.id.score);
            mChildHolder.mTv_status = (TextView) convertView.findViewById(R.id.tv_status);
            mChildHolder.mTv_away_name = (TextView) convertView.findViewById(R.id.tv_away_name);
            mChildHolder.mCollection = (ImageView) convertView.findViewById(R.id.collection);
            mChildHolder.mLets = (TextView) convertView.findViewById(R.id.lets);
            mChildHolder.mHalf_score = (TextView) convertView.findViewById(R.id.half_score);
            mChildHolder.mPoint = (TextView) convertView.findViewById(R.id.point);
            mChildHolder.mAway_avatar = (ImageView) convertView.findViewById(R.id.away_avatar);
            mChildHolder.mHost_avatar = (ImageView) convertView.findViewById(R.id.host_avatar);
            convertView.setTag(mChildHolder);
        } else {
            mChildHolder = (ChildHolder) convertView.getTag();
        }
        final ScoreBean.DataBean.ItemsBean itemsBean = mListData.get(groupPosition).get(childPosition);
        String home_short_cn = itemsBean.getHome_short_cn();//主队
        String guest_short_cn = itemsBean.getGuest_short_cn();//客队
        String league_short_cn = itemsBean.getLeague_short_cn();//比赛类型。
        String half_score = itemsBean.getHalf_score();//半场比分
        String num = itemsBean.getNum();//周四012
        String let = itemsBean.getLet();//让球
        String minute = itemsBean.getMinute();//剩余时间
        String date_time = itemsBean.getDate_time();//比赛时间
        String full_score = itemsBean.getFull_score();//当前比分
        String status = itemsBean.getStatus();//状态。Played
        String a_pic = itemsBean.getA_pic();//客队头像
        String h_pic = itemsBean.getH_pic();//主队头像

        mChildHolder.mTv_week.setText(TextUtils.isEmpty(num) ? "" : num);
        mChildHolder.mTv_lname.setText(TextUtils.isEmpty(league_short_cn) ? "" : league_short_cn);
        mChildHolder.mTv_start_time.setText(TextUtils.isEmpty(date_time) ? "" : date_time);
        mChildHolder.mTv_host_name.setText(TextUtils.isEmpty(home_short_cn) ? "" : home_short_cn);
        mChildHolder.mTv_away_name.setText(TextUtils.isEmpty(guest_short_cn) ? "" : guest_short_cn);
        mChildHolder.mScore.setText(TextUtils.isEmpty(full_score) ? "VS" : full_score);
        if (!TextUtils.isEmpty(a_pic))
            Picasso.with(mContext)
                    .load(a_pic)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.mipmap.guest_team)
                    .error(R.mipmap.guest_team)
                    .into(mChildHolder.mAway_avatar);
        //  HttpUtils2.display(mChildHolder.mAway_avatar, a_pic, R.mipmap.guest_team);
        if (!TextUtils.isEmpty(h_pic))
            Picasso.with(mContext)
                    .load(h_pic)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.mipmap.home_team)
                    .error(R.mipmap.home_team)
                    .into(mChildHolder.mHost_avatar);
        // HttpUtils2.display(mChildHolder.mHost_avatar, h_pic, R.mipmap.home_team);
        if ("Played".equals(status)) {
            mChildHolder.mTv_status.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mChildHolder.mTv_status.setText("已完赛");
            mChildHolder.mPoint.setVisibility(View.GONE);
        } else if ("Fixture".equals(status)) {
            mChildHolder.mTv_status.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
            mChildHolder.mTv_status.setText("未开始");
            mChildHolder.mPoint.setVisibility(View.GONE);
        } else if ("Playing".equals(status)) {
            mChildHolder.mPoint.setVisibility(View.VISIBLE);
            mChildHolder.mTv_status.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            mChildHolder.mTv_status.setText(TextUtils.isEmpty(minute) ? "--" : (minute));
            // TranslateAnimation tAnimation = new TranslateAnimation(0f, 0f, 0f, -80); //位移动画效果
            AlphaAnimation aAnimation = new AlphaAnimation(1, 0); //透明度动画效果
            aAnimation.setDuration(1000);
            aAnimation.setRepeatCount(100);
            mChildHolder.mPoint.setAnimation(aAnimation);
        } else {
            mChildHolder.mPoint.setVisibility(View.GONE);
            mChildHolder.mTv_status.setText(TextUtils.isEmpty(minute) ? "--" : (minute));
            mChildHolder.mTv_status.setTextColor(mContext.getResources().getColor(R.color.deep_txt));
        }
        mChildHolder.mHalf_score.setText(TextUtils.isEmpty(half_score) ? "" : half_score);
        mChildHolder.mLets.setText(TextUtils.isEmpty(let) ? "" : let);
        mChildHolder.mCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColletionDb colletionDb = new ColletionDb();
                colletionDb.setMatch_id(itemsBean.getuMid());
                if (map.containsKey(itemsBean.getuMid())) {
                    try {
                        dbManager.deleteWhere(itemsBean.getuMid());
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    map.remove(itemsBean.getuMid());
                    mChildHolder.mCollection.setImageResource(R.drawable.icon_star);
                } else {
                    try {
                        dbManager.inserts(colletionDb);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    map.put(itemsBean.getuMid(), itemsBean.getuMid());
                    mChildHolder.mCollection.setImageResource(R.drawable.icon_star2);
                }
                mCollectionListener.collListener("");
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_id = mListData.get(groupPosition).get(childPosition).getuMid();
                if (!TextUtils.isEmpty(m_id)) {

                    Intent intent = new Intent(mContext, ScoreDetailActivity.class);
                    intent.putExtra("mId", m_id);
                    intent.putExtra("from", "score");
                    mContext.startActivity(intent);

                }
            }
        });
        //处理复用后遗症
        if (map.containsKey(itemsBean.getuMid())) {
            mChildHolder.mCollection.setImageResource(R.drawable.icon_star2);
        } else {
            mChildHolder.mCollection.setImageResource(R.drawable.icon_star);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        TextView mTitleTextView;
        ImageView mIconXia;
    }

    class ChildHolder {
        TextView mTv_week;
        TextView mTv_lname;
        TextView mTv_start_time;
        TextView mTv_host_name;
        TextView mScore;
        TextView mTv_status;
        TextView mTv_away_name;
        ImageView mCollection;
        TextView mLets;
        TextView mHalf_score;
        TextView mPoint;
        ImageView mHost_avatar;
        ImageView mAway_avatar;
    }
}
