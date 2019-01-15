package com.daxiang.lottery.adapter.jcadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.common.JcPlayMethod;
import com.daxiang.lottery.cxinterface.OnClickJclqListener;
import com.daxiang.lottery.dialog.JclqSfcDialog;
import com.daxiang.lottery.entity.JclqData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.dialogutils.DialogAnimotion;
import com.daxiang.lottery.view.autotextview.AutofitTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class JclqOtherAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<JclqData.DataBean>> data;
    private Context mContext;
    private int playMethod;
    private OnClickJclqListener mOnClickJcListener;
    protected LayoutInflater mInflater;
    private boolean flag;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    HashMap<JclqData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();
    private int colorRed;
    private int colorGray;
    public void setData(Activity mContext, ArrayList<ArrayList<JclqData.DataBean>> data, boolean flag, int playMethod, OnClickJclqListener mOnClickJcListener) {
        this.data = data;
        this.mContext = mContext;
        this.mOnClickJcListener = mOnClickJcListener;
        this.playMethod = playMethod;
        this.flag = flag;
        mInflater = LayoutInflater.from(mContext);
        colorRed = mContext.getResources().getColor(R.color.red_txt);
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
    }

    @Override
    public int getGroupCount() {
        if (data == null || data.size() == 0) {
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
        // String substring = split.substring(0, 8);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(issue);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            holder.mTitleTextView.setText(issue + weekDays[i1 - 1] + "共" + data.get(groupPosition).size() + "场比赛");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mIconXia.setImageResource(isExpanded? R.drawable.icon_xia2 : R.drawable.icon_xia);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_bf_bqc_jqs, null);
            childHolder = new ChildHolder();
            childHolder.idTextView = (AutofitTextView) convertView.findViewById(R.id.text_game_id);
            childHolder.textEnd = (AutofitTextView) convertView.findViewById(R.id.text_game_endtime);
            childHolder.textMatch = (TextView) convertView.findViewById(R.id.text_game_title);
            childHolder.textHome = (TextView) convertView.findViewById(R.id.tv_jz_item_hostname);
            childHolder.textGuest = (TextView) convertView.findViewById(R.id.tv_jz_item_guestname);
            childHolder.jzItemSelectTextView = (TextView) convertView.findViewById(R.id.tv_jz_item_select);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        JclqData.DataBean dataBean = data.get(groupPosition).get(childPosition);
        //点击事件
        setListener(childHolder, dataBean);
        //解决复用问题
        recycleUse(dataBean, childHolder);
        //填充控件
        updateChildView(childHolder, dataBean);
        return convertView;
    }

    private void recycleUse(JclqData.DataBean dataBean, ChildHolder holder) {
        if (clickMap.containsKey(dataBean)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : clickMap.get(dataBean).entrySet()) {
                stringBuilder.append(entry.getKey() + ";");
            }
            showView(holder, stringBuilder);

        } else {
            defaultView(holder);
        }
    }

    private void updateChildView(ChildHolder holder, JclqData.DataBean dataBean) {
        //客队
        holder.textHome.setText(dataBean.getGuestShortCn()+"客");
        //主队
        holder.textGuest.setText(dataBean.getHomeShortCn()+"主");

        holder.textMatch.setText(dataBean.getLeagueShortCn());

        //设置比赛场次的编号
        holder.idTextView.setText(JcPlayMethod.getFormatIssue(dataBean.getIssue(),dataBean.getSession()));
        //设置比赛的截止日期
        long jzdt = dataBean.getStopSaleTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        holder.textEnd.setText(format.format(jzdt) + "截止");
    }

    private void setListener(final ChildHolder holder, final JclqData.DataBean dataBean) {
        holder.jzItemSelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastClick()) {
                    return;
                }
                JclqSfcDialog jclqSfcDialog = new JclqSfcDialog((Activity) mContext, dataBean, clickMap.get(dataBean), new JclqSfcDialog.OnOddsChooseFinishListener() {
                    @Override
                    public void onChooseFinish(HashMap<String, String> choosedOddMap) {
                        dialogCallback(dataBean, choosedOddMap, holder);
                    }
                });
                jclqSfcDialog.show();
                DialogAnimotion.setAnimotion(jclqSfcDialog);
            }
        });
    }

    private void dialogCallback(JclqData.DataBean dataBean, HashMap<String, String> choosedOddMap, ChildHolder holder) {
        clickMap.remove(dataBean);
        if (choosedOddMap != null && choosedOddMap.size() != 0) {
            clickMap.put(dataBean, choosedOddMap);
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : choosedOddMap.entrySet()) {
                stringBuilder.append(entry.getKey() + ";");
            }
            showView(holder, stringBuilder);
        } else {
            defaultView(holder);
        }
        mOnClickJcListener.onClickJcListener(clickMap);
    }

    private void showView(ChildHolder holder, StringBuilder stringBuilder) {
        holder.jzItemSelectTextView.setText(stringBuilder);
        holder.jzItemSelectTextView.setTextColor(Color.WHITE);
        holder.jzItemSelectTextView.setBackgroundColor(colorRed);
    }

    private void defaultView(ChildHolder holder) {
        holder.jzItemSelectTextView.setText("请选择投注内容");
        holder.jzItemSelectTextView.setTextColor(Color.BLACK);
        holder.jzItemSelectTextView.setBackgroundResource(R.drawable.table_bg);
    }

    protected class ChildHolder {
        TextView textHome;

        TextView textGuest;

        TextView textMatch;

        AutofitTextView idTextView;

        AutofitTextView textEnd;

        TextView jzItemSelectTextView;
    }

    protected class GroupHolder {
        TextView mTitleTextView;
        ImageView mIconXia;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
