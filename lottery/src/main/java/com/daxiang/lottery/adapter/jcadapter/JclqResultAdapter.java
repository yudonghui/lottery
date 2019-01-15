package com.daxiang.lottery.adapter.jcadapter;

import android.content.Context;
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
 * Created by Administrator on 2016/10/26 0026.
 */
public class JclqResultAdapter extends BaseExpandableListAdapter {

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
        return data.size();
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
            holder.mIconXia= (ImageView) convertView.findViewById(R.id.icon_xia);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        String issue = data.get(groupPosition).get(0).getIssue();
        holder.mTitleTextView.setText(issue + "  " + data.get(groupPosition).size() + "场比赛");

        holder.mIconXia.setImageResource(isExpanded? R.drawable.icon_xia2 : R.drawable.icon_xia);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder mChildHolder = null;
        if (convertView == null) {
            mChildHolder = new ChildHolder();
            convertView = mInflater.inflate(R.layout.lottery_bb_adpter, parent, false);
            mChildHolder.mLl_root= (LinearLayout) convertView.findViewById(R.id.ll_root);
            mChildHolder.tv_mid = (TextView) convertView.findViewById(R.id.tv_mid);
            mChildHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mChildHolder.tv_awary = (TextView) convertView.findViewById(R.id.tv_awary);
            mChildHolder.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
            mChildHolder.tv_home = (TextView) convertView.findViewById(R.id.tv_home);
            mChildHolder.tv_let = (TextView) convertView.findViewById(R.id.tv_let);
            mChildHolder.tv_win_lose = (TextView) convertView.findViewById(R.id.tv_win_lose);
            mChildHolder.tv_awary_win = (TextView) convertView.findViewById(R.id.tv_awary_win);
            mChildHolder.tv_score_number = (TextView) convertView.findViewById(R.id.tv_score_number);
            mChildHolder.tv_max_minScore = (TextView) convertView.findViewById(R.id.tv_max_minScore);
            convertView.setTag(mChildHolder);
        } else {
            mChildHolder = (ChildHolder) convertView.getTag();
        }
        if (childPosition%2==0){
            mChildHolder.mLl_root.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            mChildHolder.mLl_root.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }
        LotteryResultData.DataBean.ItemBean dataBean = data.get(groupPosition).get(childPosition);
        String sublet = dataBean.getSession().substring(8, 11);
        mChildHolder.tv_mid.setText(sublet);
        mChildHolder.tv_name.setText(dataBean.getLeagueName());
        mChildHolder.tv_awary.setText(dataBean.getGuestTeam());
        mChildHolder.tv_score.setText(dataBean.getScore());
        mChildHolder.tv_home.setText(dataBean.getHomeTeam());
        float let1 = Float.parseFloat(dataBean.getLet());
        if(let1<0){
            mChildHolder.tv_let.setTextColor(mContext.getResources().getColor(R.color.green_let));
            mChildHolder.tv_let.setText("(" + let1 + ")");
        }else {
            mChildHolder.tv_let.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            mChildHolder.tv_let.setText("(+" + let1 + ")");
        }

        /*
        * 胜负   胜分差 1-5    6-10   11-15  16-20   21-25    26+   （6个等级）
        */
        String score = dataBean.getScore();
        if(score==null||score.length()==0) score="0:0" ;
        String[] data = score.split("\\:");
        int awary = Integer.parseInt(data[0]);
        int home = Integer.parseInt(data[1]);
        if (awary > home) {
            int awaryWin = awary - home;
            mChildHolder.tv_win_lose.setText("客胜");
            mChildHolder.tv_win_lose.setTextColor(mContext.getResources().getColor(R.color.green_let));
            if (awaryWin <=5 && awaryWin >= 1) {
                mChildHolder.tv_awary_win.setText("1-5分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.green_let));
            } else if (awaryWin <=10 && awaryWin >= 6) {
                mChildHolder.tv_awary_win.setText("6-10分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.green_let));
            } else if (awaryWin <=15 && awaryWin >= 11) {
                mChildHolder.tv_awary_win.setText("11-15分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.green_let));
            } else if (awaryWin <=20 && awaryWin >= 16) {
                mChildHolder.tv_awary_win.setText("16-20分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.green_let));
            } else if (awaryWin <=25 && awaryWin >= 21) {
                mChildHolder.tv_awary_win.setText("21-25分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.green_let));
            } else {
                mChildHolder.tv_awary_win.setText("26+分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.green_let));
            }
        } else {
            mChildHolder.tv_win_lose.setText("主胜");
            mChildHolder.tv_win_lose.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            int homeWinner = home - awary;
            if (homeWinner <=5 && homeWinner >= 1) {
                mChildHolder.tv_awary_win.setText("1-5分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            } else if (homeWinner <=10 && homeWinner >= 6) {
                mChildHolder.tv_awary_win.setText("6-10分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            } else if (homeWinner <=15 && homeWinner >= 11) {
                mChildHolder.tv_awary_win.setText("11-15分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            } else if (homeWinner <=20 && homeWinner >= 16) {
                mChildHolder.tv_awary_win.setText("16-20分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            } else if (homeWinner <=25 && homeWinner >= 21) {
                mChildHolder.tv_awary_win.setText("21-25分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            } else {
                mChildHolder.tv_awary_win.setText("26+分");
                mChildHolder.tv_awary_win.setTextColor(mContext.getResources().getColor(R.color.red_txt));
            }
        }
        //让分
        double let = Double.valueOf(dataBean.getLet().replace("+","")).doubleValue();
        if ((home + let) > awary) {
            mChildHolder.tv_score_number.setText("主胜");
            mChildHolder.tv_score_number.setTextColor(mContext.getResources().getColor(R.color.red_txt));
        } else if ((home + let) < awary) {
            mChildHolder.tv_score_number.setText("客胜");
            mChildHolder.tv_score_number.setTextColor(mContext.getResources().getColor(R.color.green_let));
        }
        //大小分
        double preScore = Double.valueOf(dataBean.getPresetScore().replace("+","")).doubleValue();
        //int preScore = Integer.parseInt(String.valueOf(dataBean.getPreScore()));
        if (home+awary>preScore){
            mChildHolder.tv_max_minScore.setText(">"+preScore);
            mChildHolder.tv_max_minScore.setTextColor(mContext.getResources().getColor(R.color.red_txt));
        }else if (home+awary<preScore){
            mChildHolder.tv_max_minScore.setText("<"+preScore);
            mChildHolder.tv_max_minScore.setTextColor(mContext.getResources().getColor(R.color.green_let));
        }
        return convertView;
    }
    class ChildHolder{
        //返回的数据
        LinearLayout mLl_root;
        //篮球  客 主场之分； 客左，主右
        TextView tv_mid;
        TextView tv_name;
        TextView tv_home;
        TextView tv_let;
        TextView tv_score;
        TextView tv_awary;
        TextView tv_score_number;//让分
        TextView tv_win_lose; //胜负
        TextView tv_awary_win;   //胜分差
        TextView tv_max_minScore;//大小分
    }
    class GroupHolder {
        TextView mTitleTextView;
        ImageView mIconXia;
    }
}
