package com.daxiang.lottery.adapter.jcadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.daxiang.lottery.cxinterface.OnClickJclqListener;
import com.daxiang.lottery.entity.JclqData;
import com.daxiang.lottery.view.autotextview.AutofitTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class JclqMatchAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<JclqData.DataBean>> data;
    private String lotcode;
    private Context mContext;
    private boolean flag;
    private int playMethod;
    private OnClickJclqListener mOnClickJcListener;
    protected LayoutInflater mInflater;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    HashMap<JclqData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();
    private int colorRed;
    private int colorOrange;
    private int colorGreen;
    private int colorGray;
    // HashMap<String, String> chooseOddMap = new HashMap<>();

    public void setData(Activity mContext, ArrayList<ArrayList<JclqData.DataBean>> data, boolean flag, int playMethod, OnClickJclqListener mOnClickJcListener) {
        this.data = data;
        this.mContext = mContext;
        this.mOnClickJcListener = mOnClickJcListener;
        this.playMethod = playMethod;
        this.flag = flag;
        mInflater = LayoutInflater.from(mContext);
        colorRed = mContext.getResources().getColor(R.color.red_txt);
        colorOrange = mContext.getResources().getColor(R.color.orange_let);
        colorGreen = mContext.getResources().getColor(R.color.green_let);
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
            convertView = mInflater.inflate(R.layout.jclq_list_child_view, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLl_league = (LinearLayout) convertView.findViewById(R.id.ll_league);
            mViewHolder.mText_game_title = (TextView) convertView.findViewById(R.id.text_game_title);
            mViewHolder.mText_game_id = (AutofitTextView) convertView.findViewById(R.id.text_game_id);
            mViewHolder.mText_game_endtime = (AutofitTextView) convertView.findViewById(R.id.text_game_endtime);
            mViewHolder.mLl_awary_win = (LinearLayout) convertView.findViewById(R.id.ll_awary_win);
            mViewHolder.mAwary_win_title = (TextView) convertView.findViewById(R.id.awary_win_title);
            mViewHolder.mAwary_win_odds = (TextView) convertView.findViewById(R.id.awary_win_odds);
            mViewHolder.mLl_home_win = (LinearLayout) convertView.findViewById(R.id.ll_home_win);
            mViewHolder.mHome_win_title = (TextView) convertView.findViewById(R.id.home_win_title);
            mViewHolder.mHome_win_odds = (TextView) convertView.findViewById(R.id.home_win_odds);
            mViewHolder.mRangqiu = (TextView) convertView.findViewById(R.id.tv_rangqiu);
            mViewHolder.mTv_guest = (TextView) convertView.findViewById(R.id.tv_guest);
            mViewHolder.mTv_host = (TextView) convertView.findViewById(R.id.tv_host);
            mViewHolder.mDg = (ImageView) convertView.findViewById(R.id.image_dg);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final JclqData.DataBean dataBean = data.get(groupPosition).get(childPosition);

        mViewHolder.mLl_awary_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.mLl_awary_win, mViewHolder, dataBean);
            }
        });
        mViewHolder.mLl_home_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.mLl_home_win, mViewHolder, dataBean);
            }
        });

        //解决复用点击事件错乱的问题
        recycleUse(dataBean, mViewHolder);
        if (playMethod == LotteryTypes.RQSPF) {
            mViewHolder.mAwary_win_title.setText("客胜[让]");
            mViewHolder.mHome_win_title.setText("主胜[让]");
        } else if (playMethod == LotteryTypes.SPF) {
            mViewHolder.mAwary_win_title.setText("客胜");
            mViewHolder.mHome_win_title.setText("主胜");
        } else {
            mViewHolder.mAwary_win_title.setText("大分");
            mViewHolder.mHome_win_title.setText("小分");
        }

        mViewHolder.mTv_guest.setText(dataBean.getGuestShortCn());
        mViewHolder.mTv_host.setText(dataBean.getHomeShortCn());
        mViewHolder.mText_game_title.setText(dataBean.getLeagueShortCn());
        //设置比赛场次的编号
        mViewHolder.mText_game_id.setText(JcPlayMethod.getFormatIssue(dataBean.getIssue(),dataBean.getSession()));
        //设置比赛的截止日期
        long jzdt = dataBean.getStopSaleTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mViewHolder.mText_game_endtime.setText(format.format(jzdt) + "截止");

        //判断是串关还是单关
        if (flag) {
            if (playMethod == LotteryTypes.RQSPF) {
                mViewHolder.mRangqiu.setVisibility(View.VISIBLE);
                //是否支持单关
                if (dataBean.getRfsfDg() == 1) mViewHolder.mDg.setVisibility(View.VISIBLE);
                else mViewHolder.mDg.setVisibility(View.INVISIBLE);

                if (dataBean.getRfsfFs() == 1) {
                    float let = Float.parseFloat(dataBean.getLet());
                    if (let < 0) {
                        mViewHolder.mRangqiu.setTextColor(colorGreen);
                    } else {
                        mViewHolder.mRangqiu.setTextColor(colorOrange);
                    }
                    mViewHolder.mAwary_win_odds.setText(dataBean.getLetOdds0());
                    mViewHolder.mHome_win_odds.setText(dataBean.getLetOdds3());
                    mViewHolder.mAwary_win_title.setTag("客胜[让]");
                    mViewHolder.mHome_win_title.setTag("主胜[让]");
                    mViewHolder.mRangqiu.setVisibility(View.VISIBLE);
                    mViewHolder.mRangqiu.setText("(" + let + ")");
                } else {
                    mViewHolder.mLl_awary_win.setClickable(false);
                    mViewHolder.mLl_home_win.setClickable(false);
                    mViewHolder.mAwary_win_odds.setText("未开售");
                    mViewHolder.mHome_win_odds.setText("未开售");
                }

            } else if (playMethod == LotteryTypes.SPF) {
                mViewHolder.mRangqiu.setVisibility(View.GONE);
                //判断是否支持单关
                if (dataBean.getSfDg() == 1) mViewHolder.mDg.setVisibility(View.VISIBLE);
                else mViewHolder.mDg.setVisibility(View.INVISIBLE);

                if (dataBean.getSfFs() == 1) {
                    mViewHolder.mAwary_win_odds.setText(dataBean.getOdds0());
                    mViewHolder.mHome_win_odds.setText(dataBean.getOdds3());
                    mViewHolder.mAwary_win_title.setTag("客胜");
                    mViewHolder.mHome_win_title.setTag("主胜");
                    mViewHolder.mRangqiu.setVisibility(View.GONE);
                } else {
                    mViewHolder.mLl_awary_win.setClickable(false);
                    mViewHolder.mLl_home_win.setClickable(false);
                    mViewHolder.mAwary_win_odds.setText("未开售");
                    mViewHolder.mHome_win_odds.setText("未开售");
                }
            } else if (playMethod == LotteryTypes.CBF) {
                mViewHolder.mRangqiu.setVisibility(View.GONE);
                //判断是否支持单关
                if (dataBean.getDxfDg() == 1) mViewHolder.mDg.setVisibility(View.VISIBLE);
                else mViewHolder.mDg.setVisibility(View.INVISIBLE);

                if (dataBean.getDxfFs() == 1) {
                    mViewHolder.mAwary_win_odds.setText(dataBean.getLargeScore());
                    mViewHolder.mHome_win_odds.setText(dataBean.getSmallScore());
                    mViewHolder.mAwary_win_title.setTag("大分");
                    mViewHolder.mHome_win_title.setTag("小分");
                    mViewHolder.mRangqiu.setVisibility(View.GONE);
                } else {
                    mViewHolder.mLl_awary_win.setClickable(false);
                    mViewHolder.mLl_home_win.setClickable(false);
                    mViewHolder.mAwary_win_odds.setText("未开售");
                    mViewHolder.mHome_win_odds.setText("未开售");
                }
            }
        } else {
            if (playMethod == LotteryTypes.RQSPF) {
                mViewHolder.mRangqiu.setVisibility(View.VISIBLE);
                if (dataBean.getRfsfDg() == 1) {
                    float let = Float.parseFloat(dataBean.getLet());
                    if (let < 0) {
                        mViewHolder.mRangqiu.setTextColor(colorGreen);
                    } else {
                        mViewHolder.mRangqiu.setTextColor(colorOrange);
                    }
                    mViewHolder.mAwary_win_odds.setText(dataBean.getLetOdds0());
                    mViewHolder.mHome_win_odds.setText(dataBean.getLetOdds3());
                    mViewHolder.mAwary_win_title.setTag("客胜[让]");
                    mViewHolder.mHome_win_title.setTag("主胜[让]");
                    mViewHolder.mRangqiu.setVisibility(View.VISIBLE);
                    mViewHolder.mRangqiu.setText("(" + let + ")");
                } else {
                    mViewHolder.mLl_awary_win.setClickable(false);
                    mViewHolder.mLl_home_win.setClickable(false);
                    mViewHolder.mAwary_win_odds.setText("未开售");
                    mViewHolder.mHome_win_odds.setText("未开售");
                }

            } else if (playMethod == LotteryTypes.SPF) {
                mViewHolder.mRangqiu.setVisibility(View.GONE);
                if (dataBean.getSfDg() == 1) {
                    mViewHolder.mAwary_win_odds.setText(dataBean.getOdds0());
                    mViewHolder.mHome_win_odds.setText(dataBean.getOdds3());
                    mViewHolder.mAwary_win_title.setTag("客胜");
                    mViewHolder.mHome_win_title.setTag("主胜");
                    mViewHolder.mRangqiu.setVisibility(View.GONE);
                } else {
                    mViewHolder.mLl_awary_win.setClickable(false);
                    mViewHolder.mLl_home_win.setClickable(false);

                    mViewHolder.mAwary_win_odds.setText("未开售");
                    mViewHolder.mHome_win_odds.setText("未开售");
                }
            } else if (playMethod == LotteryTypes.CBF) {
                mViewHolder.mRangqiu.setVisibility(View.GONE);
                if (dataBean.getDxfDg() == 1) {
                    mViewHolder.mAwary_win_odds.setText(dataBean.getLargeScore());
                    mViewHolder.mHome_win_odds.setText(dataBean.getSmallScore());
                    mViewHolder.mAwary_win_title.setTag("大分");
                    mViewHolder.mHome_win_title.setTag("小分");
                    mViewHolder.mRangqiu.setVisibility(View.GONE);
                } else {
                    mViewHolder.mLl_awary_win.setClickable(false);
                    mViewHolder.mLl_home_win.setClickable(false);
                    mViewHolder.mAwary_win_odds.setText("未开售");
                    mViewHolder.mHome_win_odds.setText("未开售");
                }
            }
        }
        //因为在notifyDataSetChanged()方法执行的时候convertview是不为空的，不会重新执行findviewbyid
        //这样就导致点击过的项目，在清除后唤醒适配器，再点击就会崩溃，因为这个时候容器为空，但是小条目的tag值为true
        mViewHolder.mLl_awary_win.setTag(false);
        mViewHolder.mLl_home_win.setTag(false);
        return convertView;
    }

    //点击每一个小的条目事件
    public void clickMatchView(View view, ViewHolder mViewHolder, JclqData.DataBean dataBean) {
        // Log.e("++++++++++++", "" + position);
        LinearLayout mLinearLayout = (LinearLayout) view;
        //标题
        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
        //赔率
        TextView mTextView = (TextView) mLinearLayout.getChildAt(1);
        if (mLinearLayout.getTag() == null || !(Boolean) mLinearLayout.getTag()) {
            mLinearLayout.setBackgroundColor(colorRed);
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
        } else {
            mLinearLayout.setBackgroundResource(R.drawable.table_bg);
            mTitleTextView.setTextColor(colorGray);
            mTextView.setTextColor(colorGray);
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
        }
        mOnClickJcListener.onClickJcListener(clickMap);
    }

    //解决复用问题
    private void recycleUse(JclqData.DataBean dataBean, ViewHolder mViewHolder) {
        if (clickMap.containsKey(dataBean)) {
            HashMap<String, String> map = clickMap.get(dataBean);
            if (map.containsKey("客胜[让]")) {
                mViewHolder.mLl_awary_win.setBackgroundColor(Color.RED);
                mViewHolder.mAwary_win_title.setTextColor(Color.WHITE);
                mViewHolder.mAwary_win_odds.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mLl_awary_win.setBackgroundResource(R.drawable.table_bg);
                // mViewHolder.mllNoRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.mAwary_win_odds.setTextColor(colorGray);
                mViewHolder.mAwary_win_title.setTextColor(colorGray);
            }
            if (map.containsKey("主胜[让]")) {
                mViewHolder.mLl_home_win.setBackgroundColor(Color.RED);
                mViewHolder.mHome_win_odds.setTextColor(Color.WHITE);
                mViewHolder.mHome_win_title.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mLl_home_win.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang0.setBackgroundColor(Color.WHITE);
                mViewHolder.mHome_win_odds.setTextColor(colorGray);
                mViewHolder.mHome_win_title.setTextColor(colorGray);
            }

        } else {
            mViewHolder.mLl_awary_win.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mLl_home_win.setBackgroundResource(R.drawable.table_bg);


            mViewHolder.mAwary_win_odds.setTextColor(colorGray);
            mViewHolder.mAwary_win_title.setTextColor(colorGray);
            mViewHolder.mHome_win_odds.setTextColor(colorGray);
            mViewHolder.mHome_win_title.setTextColor(colorGray);
        }
    }

    class GroupHolder {
        TextView mTitleTextView;
        ImageView mIconXia;
    }

    class ViewHolder {
        private LinearLayout mLl_league;
        private TextView mText_game_title;
        private AutofitTextView mText_game_id;
        private AutofitTextView mText_game_endtime;
        private TextView mTv_guest;
        private TextView mTv_host;
        private LinearLayout mLl_awary_win;
        private TextView mAwary_win_title;
        private TextView mAwary_win_odds;
        private LinearLayout mLl_home_win;
        private TextView mHome_win_title;
        private TextView mHome_win_odds;
        private TextView mRangqiu;
        private ImageView mDg;
    }
}

