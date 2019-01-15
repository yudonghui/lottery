package com.daxiang.lottery.adapter.jcadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.daxiang.lottery.cxinterface.OnClickJclqListener;
import com.daxiang.lottery.dialog.AllPlayMethodDialogJL;
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
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class JclqHhMatchAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<JclqData.DataBean>> data;
    private String lotcode;
    private Context mContext;
    private OnClickJclqListener mOnClickJcListener;
    protected LayoutInflater mInflater;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    HashMap<JclqData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();
    // HashMap<String, String> chooseOddMap = new HashMap<>();
    int clickColor;
    int clickColorTxt;
    private int orangeColor;
    private int greenColor;

    public void setData(Activity mContext, ArrayList<ArrayList<JclqData.DataBean>> data, String lotcode, OnClickJclqListener mOnClickJcListener) {
        this.lotcode = lotcode;
        this.data = data;
        this.mContext = mContext;
        this.mOnClickJcListener = mOnClickJcListener;
        mInflater = LayoutInflater.from(mContext);
        clickColor = mContext.getResources().getColor(R.color.red_txt);
        clickColorTxt = mContext.getResources().getColor(R.color.gray_txt);
        orangeColor = mContext.getResources().getColor(R.color.orange_let);
        greenColor = mContext.getResources().getColor(R.color.green_let);
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
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_jclq_lv, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl_league = (LinearLayout) convertView.findViewById(R.id.ll_league);
            mViewHolder.mText_game_title = (TextView) convertView.findViewById(R.id.text_game_title);
            mViewHolder.mText_game_id = (AutofitTextView) convertView.findViewById(R.id.text_game_id);
            mViewHolder.mText_game_endtime = (AutofitTextView) convertView.findViewById(R.id.text_game_endtime);
            mViewHolder.mLl_host_block = (LinearLayout) convertView.findViewById(R.id.ll_host_block);
            mViewHolder.mTv_awary = (TextView) convertView.findViewById(R.id.tv_awary);
            mViewHolder.mTv_home = (TextView) convertView.findViewById(R.id.tv_home);
            mViewHolder.mHh_spf_rqspf_ll = (LinearLayout) convertView.findViewById(R.id.hh_spf_rqspf_ll);
            mViewHolder.mHh_spf_ll = (LinearLayout) convertView.findViewById(R.id.hh_spf_ll);
            mViewHolder.mLl_awary_win = (LinearLayout) convertView.findViewById(R.id.ll_awary_win);
            mViewHolder.mAwary_win_title = (TextView) convertView.findViewById(R.id.awary_win_title);
            mViewHolder.mAwary_win_odds = (TextView) convertView.findViewById(R.id.awary_win_odds);
            mViewHolder.mLl_home_win = (LinearLayout) convertView.findViewById(R.id.ll_home_win);
            mViewHolder.mHome_win_title = (TextView) convertView.findViewById(R.id.home_win_title);
            mViewHolder.mHome_win_odds = (TextView) convertView.findViewById(R.id.home_win_odds);
            mViewHolder.mHh_rqspf_ll = (LinearLayout) convertView.findViewById(R.id.hh_rqspf_ll);
            mViewHolder.mRangQcolor = (TextView) convertView.findViewById(R.id.rangQcolor);
            mViewHolder.mLl_big = (LinearLayout) convertView.findViewById(R.id.ll_big);
            mViewHolder.mBig_score_title = (TextView) convertView.findViewById(R.id.big_score_title);
            mViewHolder.mBig_score_odds = (TextView) convertView.findViewById(R.id.big_score_odds);
            mViewHolder.mMiddle_score = (TextView) convertView.findViewById(R.id.middle_score);
            mViewHolder.mLl_small = (LinearLayout) convertView.findViewById(R.id.ll_small);
            mViewHolder.mSmall_score_title = (TextView) convertView.findViewById(R.id.small_score_title);
            mViewHolder.mSmall_score_odds = (TextView) convertView.findViewById(R.id.small_score_odds);
            mViewHolder.mAll_play_method = (TextView) convertView.findViewById(R.id.all_play_method);
            mViewHolder.mRangqiu = (TextView) convertView.findViewById(R.id.tv_rangqiu);
            mViewHolder.mRangFen = (TextView) convertView.findViewById(R.id.tv_rangfen);
            mViewHolder.mRfsfDg = (ImageView) convertView.findViewById(R.id.rfsf_dg);
            mViewHolder.mDxfDg = (ImageView) convertView.findViewById(R.id.dxf_dg);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final JclqData.DataBean dataBean = data.get(groupPosition).get(childPosition);

        mViewHolder.mLl_awary_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.mLl_awary_win, mViewHolder.mAwary_win_title, mViewHolder.mAwary_win_odds, mViewHolder, dataBean);
            }
        });
        mViewHolder.mLl_home_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.mLl_home_win, mViewHolder.mHome_win_title, mViewHolder.mHome_win_odds, mViewHolder, dataBean);
            }
        });
        mViewHolder.mLl_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.mLl_big, mViewHolder.mBig_score_title, mViewHolder.mBig_score_odds, mViewHolder, dataBean);
            }
        });
        mViewHolder.mLl_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.mLl_small, mViewHolder.mSmall_score_title, mViewHolder.mSmall_score_odds, mViewHolder, dataBean);
            }
        });

        //解决复用点击事件错乱的问题
        recycleUse(dataBean, mViewHolder);


        mViewHolder.mTv_awary.setText(dataBean.getGuestShortCn() + "(客)");
        mViewHolder.mTv_home.setText(dataBean.getHomeShortCn() + "(主)");
        if (!TextUtils.isEmpty(dataBean.getLet())) {
            float let = Float.parseFloat(dataBean.getLet());
            if (let < 0) {
                mViewHolder.mRangFen.setBackgroundColor(greenColor);
                mViewHolder.mRangqiu.setTextColor(greenColor);
                mViewHolder.mRangqiu.setText("(" + let + ")");
            } else {
                mViewHolder.mRangFen.setBackgroundColor(orangeColor);
                mViewHolder.mRangqiu.setTextColor(orangeColor);
                mViewHolder.mRangqiu.setText("(+" + let + ")");
            }
        }
        mViewHolder.mText_game_title.setText(dataBean.getLeagueShortCn());

        //设置比赛场次的编号
        mViewHolder.mText_game_id.setText(JcPlayMethod.getFormatIssue(dataBean.getIssue(), dataBean.getSession()));
        //设置比赛的截止日期
        long jzdt = dataBean.getStopSaleTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mViewHolder.mText_game_endtime.setText(format.format(jzdt) + "截止");
        if (!TextUtils.isEmpty(dataBean.getPresetScore()))
            mViewHolder.mMiddle_score.setText(Float.parseFloat(dataBean.getPresetScore()) + "");
        else mViewHolder.mMiddle_score.setText("--");
        /*
        * 判断是否支持单关玩法
        * */
        if (dataBean.getRfsfDg() == 1) mViewHolder.mRfsfDg.setVisibility(View.VISIBLE);
        else mViewHolder.mRfsfDg.setVisibility(View.INVISIBLE);
        if (dataBean.getDxfDg() == 1) mViewHolder.mDxfDg.setVisibility(View.VISIBLE);
        else mViewHolder.mDxfDg.setVisibility(View.INVISIBLE);

        if (dataBean.getRfsfFs() == 1) {
            mViewHolder.mAwary_win_odds.setText(dataBean.getLetOdds0());
            mViewHolder.mHome_win_odds.setText(dataBean.getLetOdds3());
            mViewHolder.mAwary_win_title.setTag("客胜[让]");
            mViewHolder.mHome_win_title.setTag("主胜[让]");

        } else {
            mViewHolder.mLl_awary_win.setClickable(false);
            mViewHolder.mLl_home_win.setClickable(false);

            mViewHolder.mAwary_win_odds.setText("--");
            mViewHolder.mHome_win_odds.setText("--");
        }
        if (dataBean.getDxfFs() == 1) {
            mViewHolder.mBig_score_odds.setText(dataBean.getLargeScore());
            mViewHolder.mSmall_score_odds.setText(dataBean.getSmallScore());

            mViewHolder.mBig_score_title.setTag("大分");
            mViewHolder.mSmall_score_title.setTag("小分");
        } else {
            mViewHolder.mLl_big.setClickable(false);
            mViewHolder.mLl_small.setClickable(false);

            mViewHolder.mBig_score_odds.setText("未开售");
            mViewHolder.mSmall_score_odds.setText("未开售");
        }
        //因为在notifyDataSetChanged()方法执行的时候convertview是不为空的，不会重新执行findviewbyid
        //这样就导致点击过的项目，在清除后唤醒适配器，再点击就会崩溃，因为这个时候容器为空，但是小条目的tag值为true
        mViewHolder.mLl_awary_win.setTag(false);
        mViewHolder.mLl_home_win.setTag(false);
        mViewHolder.mLl_big.setTag(false);
        mViewHolder.mLl_small.setTag(false);
        mViewHolder.mAll_play_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止连续点击全部玩法的时候跳出来多个框
                if (ClickUtils.isFastClick()) {
                    return;
                }
                final AllPlayMethodDialogJL mDialog = new AllPlayMethodDialogJL((Activity) mContext, dataBean, clickMap.get(dataBean), new AllPlayMethodDialogJL.OnOddsChooseFinishListener() {
                    @Override
                    public void onChooseFinish(HashMap<String, String> choosedOddMap) {
                        //mDialog.dismiss();
                        clickMap.remove(dataBean);
                        setAllPlayMethod(choosedOddMap, mViewHolder);
                        mViewHolder.mLl_awary_win.setTag(choosedOddMap.containsKey("客胜[让]") ? true : false);
                        mViewHolder.mLl_home_win.setTag(choosedOddMap.containsKey("主胜[让]") ? true : false);
                        mViewHolder.mLl_big.setTag(choosedOddMap.containsKey("大分") ? true : false);
                        mViewHolder.mLl_small.setTag(choosedOddMap.containsKey("小分") ? true : false);

                        updateSpfOrRqspfButtonCheckStatus(mViewHolder.mLl_awary_win, mViewHolder.mAwary_win_title, mViewHolder.mAwary_win_odds, mViewHolder);
                        updateSpfOrRqspfButtonCheckStatus(mViewHolder.mLl_home_win, mViewHolder.mHome_win_title, mViewHolder.mHome_win_odds, mViewHolder);
                        updateSpfOrRqspfButtonCheckStatus(mViewHolder.mLl_big, mViewHolder.mBig_score_title, mViewHolder.mBig_score_odds, mViewHolder);
                        updateSpfOrRqspfButtonCheckStatus(mViewHolder.mLl_small, mViewHolder.mSmall_score_title, mViewHolder.mSmall_score_odds, mViewHolder);
                        if (choosedOddMap != null && choosedOddMap.size() != 0) {
                            clickMap.put(dataBean, choosedOddMap);
                        }
                        mOnClickJcListener.onClickJcListener(clickMap);
                    }
                });
                mDialog.show();
                DialogAnimotion.setAnimotion(mDialog);
            }
        });
        return convertView;
    }

    //点击每一个小的条目事件
    public void clickMatchView(LinearLayout mLinearLayout, TextView mTitleTextView, TextView mTextView, ViewHolder mViewHolder, JclqData.DataBean dataBean) {
        /*// Log.e("++++++++++++", "" + position);
        LinearLayout mLinearLayout = (LinearLayout) view;
        //标题
        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
        //赔率
        TextView mTextView = (TextView) mLinearLayout.getChildAt(1);*/
        if (mLinearLayout.getTag() == null || !(Boolean) mLinearLayout.getTag()) {
            mLinearLayout.setBackgroundColor(clickColor);
            mTitleTextView.setTextColor(Color.WHITE);
            mTextView.setTextColor(Color.WHITE);
            mLinearLayout.setTag(true);
            HashMap<String, String> map;
            if (clickMap.containsKey(dataBean)) {
                map = clickMap.get(dataBean);
                String tag = (String) mTitleTextView.getTag();
                map.put(tag, mTextView.getText().toString());
                //chooseOddMap.put(tag, mTextView.getText().toString());
                clickMap.put(dataBean, map);
            } else {
                map = new HashMap<>();
                map.put((String) mTitleTextView.getTag(), mTextView.getText().toString());
                //chooseOddMap.put((String) mTitleTextView.getTag(), mTextView.getText().toString());
                clickMap.put(dataBean, map);
            }
            setAllPlayMethod(map, mViewHolder);
        } else {
            mLinearLayout.setBackgroundResource(R.drawable.table_bg);
            mTitleTextView.setTextColor(clickColorTxt);
            mTextView.setTextColor(clickColorTxt);
            mLinearLayout.setTag(false);
            //进入这个方法证明clickmap中肯定有这个键。
            HashMap<String, String> map = clickMap.get(dataBean);
            map.remove((String) mTitleTextView.getTag());
            // chooseOddMap.remove((String) mTitleTextView.getTag());
            //如果内层的map集合中已经没有数据，也就是没有选中的了。那么需要把position从外层的clickmap中移除
            //否则把移除数据后的map再放入clickmap中。
            if (map == null || map.size() == 0) {
                clickMap.remove(dataBean);
            } else {
                clickMap.put(dataBean, map);
            }
            setAllPlayMethod(map, mViewHolder);
        }
        mOnClickJcListener.onClickJcListener(clickMap);
    }

    //全部玩法的设置
    public void setAllPlayMethod(HashMap<String, String> map, ViewHolder mViewHolder) {
        if (map == null || map.size() == 0) {
            mViewHolder.mAll_play_method.setTextColor(clickColorTxt);
            mViewHolder.mAll_play_method.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mAll_play_method.setText("全部玩法");
        } else {
            mViewHolder.mAll_play_method.setTextColor(Color.WHITE);
            mViewHolder.mAll_play_method.setBackgroundColor(clickColor);
            mViewHolder.mAll_play_method.setText("已选" + map.size() + "项");
        }
    }

    //解决复用问题
    private void recycleUse(JclqData.DataBean dataBean, ViewHolder mViewHolder) {
        if (clickMap.containsKey(dataBean)) {
            HashMap<String, String> map = clickMap.get(dataBean);
            if (map.containsKey("客胜[让]")) {
                mViewHolder.mLl_awary_win.setBackgroundColor(clickColor);
                mViewHolder.mAwary_win_title.setTextColor(Color.WHITE);
                mViewHolder.mAwary_win_odds.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mLl_awary_win.setBackgroundResource(R.drawable.table_bg);
                // mViewHolder.mllNoRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.mAwary_win_odds.setTextColor(clickColorTxt);
                mViewHolder.mAwary_win_title.setTextColor(clickColorTxt);
            }
            if (map.containsKey("主胜[让]")) {
                mViewHolder.mLl_home_win.setBackgroundColor(clickColor);
                mViewHolder.mHome_win_odds.setTextColor(Color.WHITE);
                mViewHolder.mHome_win_title.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mLl_home_win.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang0.setBackgroundColor(Color.WHITE);
                mViewHolder.mHome_win_odds.setTextColor(clickColorTxt);
                mViewHolder.mHome_win_title.setTextColor(clickColorTxt);
            }
            if (map.containsKey("大分")) {
                mViewHolder.mLl_big.setBackgroundColor(clickColor);
                mViewHolder.mBig_score_odds.setTextColor(Color.WHITE);
                mViewHolder.mBig_score_title.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mLl_big.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.mBig_score_odds.setTextColor(clickColorTxt);
                mViewHolder.mBig_score_title.setTextColor(clickColorTxt);
            }
            if (map.containsKey("小分")) {
                mViewHolder.mLl_small.setBackgroundColor(clickColor);
                mViewHolder.mSmall_score_odds.setTextColor(Color.WHITE);
                mViewHolder.mSmall_score_title.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mLl_small.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllRang1.setBackgroundColor(Color.WHITE);
                mViewHolder.mSmall_score_odds.setTextColor(clickColorTxt);
                mViewHolder.mSmall_score_title.setTextColor(clickColorTxt);
            }

            mViewHolder.mAll_play_method.setTextColor(Color.WHITE);
            mViewHolder.mAll_play_method.setBackgroundColor(clickColor);
            mViewHolder.mAll_play_method.setText("已选" + map.size() + "项");

        } else {
            mViewHolder.mLl_awary_win.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mLl_home_win.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mLl_big.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mLl_small.setBackgroundResource(R.drawable.table_bg);


            mViewHolder.mAwary_win_odds.setTextColor(clickColorTxt);
            mViewHolder.mAwary_win_title.setTextColor(clickColorTxt);
            mViewHolder.mHome_win_odds.setTextColor(clickColorTxt);
            mViewHolder.mHome_win_title.setTextColor(clickColorTxt);

            mViewHolder.mBig_score_odds.setTextColor(clickColorTxt);
            mViewHolder.mBig_score_title.setTextColor(clickColorTxt);
            mViewHolder.mSmall_score_odds.setTextColor(clickColorTxt);
            mViewHolder.mSmall_score_title.setTextColor(clickColorTxt);

            mViewHolder.mAll_play_method.setTextColor(clickColorTxt);
            mViewHolder.mAll_play_method.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mAll_play_method.setText("全部玩法");
        }
    }

    private void updateSpfOrRqspfButtonCheckStatus(LinearLayout mLinearLayout, TextView mTitleTextView, TextView mTextView, ViewHolder mViewHolder) {

       /* LinearLayout mLinearLayout = (LinearLayout) view;

        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
        TextView mTextView = (TextView) mLinearLayout.getChildAt(1);*/
        if ((Boolean) mLinearLayout.getTag()) {
            mLinearLayout.setBackgroundColor(clickColor);
            mTitleTextView.setTextColor(Color.WHITE);
            mTextView.setTextColor(Color.WHITE);
            mLinearLayout.setTag(true);
        } else {
            mLinearLayout.setBackgroundResource(R.drawable.table_bg);
            mTitleTextView.setTextColor(clickColorTxt);
            mTextView.setTextColor(clickColorTxt);
            mLinearLayout.setTag(false);
        }
    }

    class GroupHolder {
        TextView mTitleTextView;
        ImageView mIconXia;
    }

    class ViewHolder {
        LinearLayout mLl_league;
        TextView mText_game_title;
        AutofitTextView mText_game_id;
        AutofitTextView mText_game_endtime;
        LinearLayout mLl_host_block;
        TextView mTv_awary;
        TextView mTv_home;
        LinearLayout mHh_spf_rqspf_ll;
        LinearLayout mHh_spf_ll;
        LinearLayout mLl_awary_win;
        TextView mAwary_win_title;
        TextView mAwary_win_odds;
        LinearLayout mLl_home_win;
        TextView mHome_win_title;
        TextView mHome_win_odds;
        LinearLayout mHh_rqspf_ll;
        TextView mRangQcolor;
        LinearLayout mLl_big;
        TextView mBig_score_title;
        TextView mBig_score_odds;
        TextView mMiddle_score;
        LinearLayout mLl_small;
        TextView mSmall_score_title;
        TextView mSmall_score_odds;
        TextView mAll_play_method;
        TextView mRangqiu;
        TextView mRangFen;
        ImageView mRfsfDg;
        ImageView mDxfDg;
    }
}
