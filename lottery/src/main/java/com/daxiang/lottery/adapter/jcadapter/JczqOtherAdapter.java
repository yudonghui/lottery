package com.daxiang.lottery.adapter.jcadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.common.JcPlayMethod;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.OnClickJczqListener;
import com.daxiang.lottery.dialog.AllPlayMethodDialog;
import com.daxiang.lottery.dialog.JczqBqcDialog;
import com.daxiang.lottery.dialog.JczqCbfDialog;
import com.daxiang.lottery.dialog.JczqJqsDialog;
import com.daxiang.lottery.entity.HistoryBean;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.DialogAnimotion;
import com.daxiang.lottery.view.HistoryDataView;
import com.daxiang.lottery.view.autotextview.AutofitTextView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class JczqOtherAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<JczqData.DataBean>> data;
    private Context mContext;
    private int playMethod;
    private OnClickJczqListener mOnClickJcListener;
    protected LayoutInflater mInflater;
    HashMap<JczqData.DataBean, HistoryBean.DataBean.ItemsBean> clickDetail = new HashMap<>();
    private boolean flag;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    HashMap<JczqData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();

    public void setData(Activity mContext, ArrayList<ArrayList<JczqData.DataBean>> data, boolean flag, int playMethod, OnClickJczqListener mOnClickJcListener) {
        this.data = data;
        this.mContext = mContext;
        this.mOnClickJcListener = mOnClickJcListener;
        this.playMethod = playMethod;
        this.flag = flag;
        mInflater = LayoutInflater.from(mContext);
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
            holder.mIconXia = (ImageView) convertView.findViewById(R.id.icon_xia);
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
        holder.mIconXia.setImageResource(isExpanded ? R.drawable.icon_xia2 : R.drawable.icon_xia);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_bf_bqc_jqs, null);
            childHolder = new ChildHolder();
            childHolder.mHistoryView = (HistoryDataView) convertView.findViewById(R.id.historydata);
            childHolder.mLl_league = (LinearLayout) convertView.findViewById(R.id.ll_league);
            childHolder.mIconDetail = (ImageView) convertView.findViewById(R.id.icon_detail);
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
        final JczqData.DataBean dataBean = data.get(groupPosition).get(childPosition);
        final String mId = dataBean.getuMid();
        //点击事件
        setListener(childHolder, dataBean);
        //解决复用问题
        recycleUse(dataBean, childHolder);
        if (clickDetail.containsKey(dataBean)) {
            childHolder.mIconDetail.setImageResource(R.mipmap.triangle_up);
            childHolder.mHistoryView.setVisibility(View.VISIBLE);
            childHolder.mHistoryView.setView(clickDetail.get(dataBean), mId);
        } else {
            childHolder.mIconDetail.setImageResource(R.mipmap.triangle_down);
            childHolder.mHistoryView.setVisibility(View.GONE);
        }
        childHolder.mLl_league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickDetail.containsKey(dataBean)) {
                    clickDetail.remove(dataBean);
                    childHolder.mHistoryView.setVisibility(View.GONE);
                    childHolder.mIconDetail.setImageResource(R.mipmap.triangle_down);
                } else {
                    childHolder.mHistoryView.setVisibility(View.VISIBLE);
                    childHolder.mIconDetail.setImageResource(R.mipmap.triangle_up);
                    HttpInterface2 mHttp = new HttpUtils2(mContext);
                    Bundle params = new Bundle();
                    params.putString("matchId", mId);
                    mHttp.get(Url.HISTORY_DATA, params, new JsonInterface() {
                        @Override
                        public void callback(String result) {
                            Gson gson = new Gson();
                            HistoryBean historyBean = gson.fromJson(result, HistoryBean.class);
                            int code = historyBean.getCode();
                            String msg = historyBean.getMsg();
                            if (code == 0) {
                                HistoryBean.DataBean.ItemsBean data = historyBean.getData().getItems();
                                childHolder.mHistoryView.setView(data, mId);
                                clickDetail.put(dataBean, data);
                            }else {
                                childHolder.mHistoryView.setView(null, mId);
                                clickDetail.put(dataBean,null);
                            } /*else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }*/
                        }

                        @Override
                        public void onError() {
                            childHolder.mHistoryView.setView(null, mId);
                            clickDetail.put(dataBean,null);
                        }
                    });
                }
            }
        });
        //填充控件
        updateChildView(childHolder, dataBean);
        return convertView;
    }

    private void recycleUse(JczqData.DataBean dataBean, ChildHolder holder) {
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


    private void updateChildView(ChildHolder holder, JczqData.DataBean dataBean) {
        String rankA = dataBean.getRankA();
        String rankH = dataBean.getRankH();
        String numHost = StringUtil.getNumber(rankH);
        String numGuest = StringUtil.getNumber(rankA);
        String rankHost = TextUtils.isEmpty(numHost) ? "" : ("[" + numHost + "]");
        String rankGuest = TextUtils.isEmpty(numGuest) ? "" : ("[" + numGuest + "]");
        holder.textHome.setText(rankHost + dataBean.getHomeShortCn());
        holder.textGuest.setText(rankGuest + dataBean.getGuestShortCn());

        holder.textMatch.setText(dataBean.getLeagueShortCn());

        //设置比赛场次的编号
        holder.idTextView.setText(JcPlayMethod.getFormatIssue(dataBean.getIssue(), dataBean.getSession()));
        //设置比赛的截止日期
        String jzdt = dataBean.getStopSaleTime();
        if (!TextUtils.isEmpty(jzdt)&&!jzdt.contains("-")){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            holder.textEnd.setText(format.format(Long.parseLong(jzdt)) + "截止");
        }
    }

    private void setListener(final ChildHolder holder, final JczqData.DataBean dataBean) {
        holder.jzItemSelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastClick()) {
                    return;
                }
                if (playMethod == LotteryTypes.CBF) {
                    JczqCbfDialog dialog = new JczqCbfDialog((Activity) mContext, dataBean, clickMap.get(dataBean), new AllPlayMethodDialog.OnOddsChooseFinishListener() {

                        @Override
                        public void onChooseFinish(HashMap<String, String> choosedOddMap) {
                            dialogCallback(dataBean, choosedOddMap, holder);
                        }
                    });
                    dialog.show();
                    DialogAnimotion.setAnimotion(dialog);
                } else if (playMethod == LotteryTypes.JQS) {
                    JczqJqsDialog dialog = new JczqJqsDialog((Activity) mContext, dataBean, clickMap.get(dataBean), new AllPlayMethodDialog.OnOddsChooseFinishListener() {

                        @Override
                        public void onChooseFinish(HashMap<String, String> choosedOddMap) {
                            dialogCallback(dataBean, choosedOddMap, holder);
                        }
                    });
                    dialog.show();
                    DialogAnimotion.setAnimotion(dialog);

                } else if (playMethod == LotteryTypes.BQC) {
                    JczqBqcDialog dialog = new JczqBqcDialog((Activity) mContext, dataBean, clickMap.get(dataBean), new AllPlayMethodDialog.OnOddsChooseFinishListener() {
                        @Override
                        public void onChooseFinish(HashMap<String, String> choosedOddMap) {
                            dialogCallback(dataBean, choosedOddMap, holder);
                        }
                    });
                    dialog.show();
                    DialogAnimotion.setAnimotion(dialog);

                }
            }
        });
    }

    private void dialogCallback(JczqData.DataBean dataBean, HashMap<String, String> choosedOddMap, ChildHolder holder) {
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
        holder.jzItemSelectTextView.setBackgroundColor(mContext.getResources().getColor(R.color.red_txt));
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
        HistoryDataView mHistoryView;
        LinearLayout mLl_league;
        ImageView mIconDetail;
    }

    protected class GroupHolder {

        ImageView mIconXia;
        TextView mTitleTextView;
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
