package com.daxiang.lottery.adapter.jcadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.LotteryResultData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class JczqResultAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<LotteryResultData.DataBean.ItemBean>> data;
    private String lotcode;
    private Context mContext;
    protected LayoutInflater mInflater;

    public void setData(Context mContext, String lotcode, ArrayList<ArrayList<LotteryResultData.DataBean.ItemBean>> data) {
        this.data = data;
        this.lotcode = lotcode;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).get(childPosition);
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_match_day_category, null);
            holder = new GroupHolder();
            holder.mTitleTextView = (TextView) convertView.findViewById(R.id.text_match_date);
            holder.mIconXia = (ImageView) convertView.findViewById(R.id.icon_xia);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        String issue = data.get(groupPosition).get(0).getIssue();
        holder.mTitleTextView.setText(issue + "  " + data.get(groupPosition).size() + "场比赛");

        holder.mIconXia.setImageResource(isExpanded ? R.drawable.icon_xia2 : R.drawable.icon_xia);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder mChilderHolder;
        if (convertView == null) {
            mChilderHolder = new ChildHolder();
            convertView = mInflater.inflate(R.layout.lottery_fb_adpter, parent, false);
            mChilderHolder.mLl_root= (LinearLayout) convertView.findViewById(R.id.ll_root);
            mChilderHolder.tv_mid = (TextView) convertView.findViewById(R.id.tv_mid);
            mChilderHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mChilderHolder.tv_home = (TextView) convertView.findViewById(R.id.home);
            mChilderHolder.tv_let = (TextView) convertView.findViewById(R.id.tv_let);
            mChilderHolder.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
            mChilderHolder.tv_awary = (TextView) convertView.findViewById(R.id.tv_awary);
            mChilderHolder.tv_score_number = (TextView) convertView.findViewById(R.id.tv_score_number);
            mChilderHolder.tv_ping = (TextView) convertView.findViewById(R.id.tv_ping);
            mChilderHolder.tv_kesheng = (TextView) convertView.findViewById(R.id.tv_kesheng);
            mChilderHolder.tv_shengping = (TextView) convertView.findViewById(R.id.tv_shengping);

            convertView.setTag(mChilderHolder);
        } else {
            mChilderHolder = (ChildHolder) convertView.getTag();
        }
        if (childPosition%2==0){
            mChilderHolder.mLl_root.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            mChilderHolder.mLl_root.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }

        LotteryResultData.DataBean.ItemBean dataBean = data.get(groupPosition).get(childPosition);

        String mid = dataBean.getSession();
        mChilderHolder.tv_mid.setText(mid.substring(mid.length() - 3, mid.length()));
        mChilderHolder.tv_name.setText(dataBean.getLeagueName());
        mChilderHolder.tv_home.setText(dataBean.getHomeTeam());
        mChilderHolder.tv_let.setText("(" + dataBean.getLet() + ")");
        mChilderHolder.tv_score.setText(dataBean.getScore());
        mChilderHolder.tv_awary.setText(dataBean.getGuestTeam());
        //总分
        String score = dataBean.getScore();
        if (TextUtils.isEmpty(score)) {
            score = "0:0";
        }
        String[] data = score.split("\\:");
        int home = 0;
        int awary = 0;
        if (data.length == 2) {
            home = Integer.parseInt(data[0]);
            awary = Integer.parseInt(data[1]);
        }
        int socrenum = home + awary;
        mChilderHolder.tv_score_number.setText(socrenum + "");
        //判断胜负平
        if (home > awary) {
            //主大于客队
            mChilderHolder.tv_ping.setText("主胜");
            mChilderHolder.tv_ping.setTextColor(mContext.getResources().getColor(R.color.red_txt));
        } else if (home == awary) {
            mChilderHolder.tv_ping.setText("平");
            mChilderHolder.tv_ping.setTextColor(mContext.getResources().getColor(R.color.purple));
        } else {
            mChilderHolder.tv_ping.setText("客胜");
            mChilderHolder.tv_ping.setTextColor(mContext.getResources().getColor(R.color.green_let));
        }
        //判断让球
        int let = Integer.parseInt(String.valueOf(dataBean.getLet()));
        if (let<0)mChilderHolder.tv_let.setTextColor(mContext.getResources().getColor(R.color.green_let));
        else mChilderHolder.tv_let.setTextColor(mContext.getResources().getColor(R.color.red_txt));
        // homelet=home-let;  //主队分数减去让球分数 的到的结果
        if ((home + let) > awary) {
            mChilderHolder.tv_kesheng.setText("主胜");
            mChilderHolder.tv_kesheng.setTextColor(mContext.getResources().getColor(R.color.red_txt));
        } else if ((home + let) < awary) {
            mChilderHolder.tv_kesheng.setText("客胜");
            mChilderHolder.tv_kesheng.setTextColor(mContext.getResources().getColor(R.color.green_let));
        } else {
            mChilderHolder.tv_kesheng.setText("平");
            mChilderHolder.tv_kesheng.setTextColor(mContext.getResources().getColor(R.color.purple));
        }
        //判断半全场
        /*
        * 胜胜        胜平      胜负
        * 平胜        平平      平负
        * 负负        负平       负胜
        * 1、先拿到半场的比赛结果
        * 2、拿到全场比赛结果
        * */

        String halfScore = dataBean.getHalfScore();
        if (TextUtils.isEmpty(halfScore)) {
            halfScore = "0:0";
        }
        String[] scoreHalfdate = halfScore.split("\\:");
        if (scoreHalfdate.length < 2) return convertView;
        int halfHome = Integer.parseInt(scoreHalfdate[0]);
        int halfAwary = Integer.parseInt(scoreHalfdate[1]);
        if (halfHome > halfAwary && home > awary) {
            mChilderHolder.tv_shengping.setText("胜胜");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.red_theme));
        } else if (halfHome > halfAwary && home == awary) {
            mChilderHolder.tv_shengping.setText("胜平");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.red_theme));
        } else if (halfHome > halfAwary && home < awary) {
            mChilderHolder.tv_shengping.setText("胜负");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.red_theme));
        } else if (halfHome == halfAwary && home > awary) {
            mChilderHolder.tv_shengping.setText("平胜");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.purple));
        } else if (halfHome == halfAwary && home == awary) {
            mChilderHolder.tv_shengping.setText("平平");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.purple));
        } else if (halfHome == halfAwary && home < awary) {
            mChilderHolder.tv_shengping.setText("平负");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.purple));
        } else if (halfHome < halfAwary && home < awary) {
            mChilderHolder.tv_shengping.setText("负负");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.green_let));
        } else if (halfHome < halfAwary && home == awary) {
            mChilderHolder.tv_shengping.setText("负平");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.green_let));
        } else {
            mChilderHolder.tv_shengping.setText("负胜");
            mChilderHolder.tv_shengping.setTextColor(mContext.getResources().getColor(R.color.green_let));
        }

        return convertView;
    }

    class ChildHolder {
        LinearLayout mLl_root;
        //返回的数据
        //篮球  客 主场之分； 客左，主右
        TextView tv_mid;
        TextView tv_name;
        TextView tv_home;
        TextView tv_let;
        TextView tv_score;
        TextView tv_awary;
        TextView tv_score_number;
        TextView tv_ping; //胜负平
        TextView tv_kesheng;   //让球
        TextView tv_shengping;//半全场
    }

    class GroupHolder {
        TextView mTitleTextView;
        ImageView mIconXia;
    }
}
