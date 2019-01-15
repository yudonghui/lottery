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
import com.daxiang.lottery.entity.HistoryBean;
import com.daxiang.lottery.entity.JczqData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.view.HistoryDataView;
import com.daxiang.lottery.view.autotextview.AutofitTextView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class JczqMatchAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<JczqData.DataBean>> data;
    private Context mContext;
    private int playMethod;
    private OnClickJczqListener mOnClickJcListener;
    protected LayoutInflater mInflater;
    HashMap<JczqData.DataBean, HistoryBean.DataBean.ItemsBean> clickDetail = new HashMap<>();
    private boolean flag;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    HashMap<JczqData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();
    private int greenColor;
    private int redColor;
    private int orangeColor;
    private  int grayColor;


    /*ArrayList<ArrayList<JczqData.DataBean>> dataOut=new ArrayList<>();
    ArrayList<JczqData.DataBean> dataIn=new ArrayList<>();*/
    public void setData(Activity mContext, ArrayList<ArrayList<JczqData.DataBean>> data, boolean flag, int playMethod, OnClickJczqListener mOnClickJcListener) {
        this.data = data;
        this.mContext = mContext;
        this.mOnClickJcListener = mOnClickJcListener;
        this.playMethod = playMethod;
        this.flag = flag;
        mInflater = LayoutInflater.from(mContext);
        greenColor = mContext.getResources().getColor(R.color.green_let);
        redColor = mContext.getResources().getColor(R.color.red_txt);
        orangeColor = mContext.getResources().getColor(R.color.orange_let);
        grayColor = mContext.getResources().getColor(R.color.gray_txt);
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

        final ChildHolder holder;
        // int key = groupPosition * 1000 + childPosition;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.jczq_list_child_view, null);
            holder = new ChildHolder();
            holder.mHistoryView = (HistoryDataView) convertView.findViewById(R.id.historydata);
            holder.mLl_league = (LinearLayout) convertView.findViewById(R.id.ll_league);
            holder.mIconDetail = (ImageView) convertView.findViewById(R.id.icon_detail);
            holder.matchTextView = (AutofitTextView) convertView.findViewById(R.id.tv_competition);
            holder.idTextView = (TextView) convertView.findViewById(R.id.tv_game_no);
            holder.endTextView = (AutofitTextView) convertView.findViewById(R.id.tv_deadline);
            holder.homeButton = (TextView) convertView.findViewById(R.id.tv_host);
            holder.guestButton = (TextView) convertView.findViewById(R.id.tv_guest);
            holder.tv_rangqiu = (TextView) convertView.findViewById(R.id.tv_rangqiu);
            holder.rangQLeft = (TextView) convertView.findViewById(R.id.rangQLeft);
            holder.rangQRight = (TextView) convertView.findViewById(R.id.rangQRight);
            holder.jz_loss_ll = (LinearLayout) convertView.findViewById(R.id.jz_loss_ll);
            holder.tv_guest_sp = (TextView) convertView.findViewById(R.id.tv_guest_sp);
            holder.jz_win_ll = (LinearLayout) convertView.findViewById(R.id.jz_win_ll);
            holder.tv_host_sp = (TextView) convertView.findViewById(R.id.tv_host_sp);
            holder.tv_loss_lable = (TextView) convertView.findViewById(R.id.tv_loss_lable);
            holder.tv_win_lable = (TextView) convertView.findViewById(R.id.tv_win_lable);

            holder.tv_ping_lable = (TextView) convertView.findViewById(R.id.tv_ping_lable);
            holder.tv_versus_sp = (TextView) convertView.findViewById(R.id.tv_versus_sp);
            holder.jz_ping_ll = (LinearLayout) convertView.findViewById(R.id.jz_ping_ll);
            holder.mDg = (ImageView) convertView.findViewById(R.id.image_dg);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        final JczqData.DataBean dataBean = data.get(groupPosition).get(childPosition);
        final String mId=dataBean.getuMid();
        holder.jz_win_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(holder.jz_win_ll, holder, dataBean);
            }
        });
        holder.jz_ping_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(holder.jz_ping_ll, holder, dataBean);
            }
        });
        holder.jz_loss_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(holder.jz_loss_ll, holder, dataBean);
            }
        });
        recycleUse(dataBean, holder);
        if (clickDetail.containsKey(dataBean)) {
            holder.mIconDetail.setImageResource(R.mipmap.triangle_up);
            holder.mHistoryView.setVisibility(View.VISIBLE);
            holder.mHistoryView.setView(clickDetail.get(dataBean),mId);
        } else {
            holder.mIconDetail.setImageResource(R.mipmap.triangle_down);
            holder.mHistoryView.setVisibility(View.GONE);
        }
        holder.mLl_league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickDetail.containsKey(dataBean)) {
                    clickDetail.remove(dataBean);
                    holder.mHistoryView.setVisibility(View.GONE);
                    holder.mIconDetail.setImageResource(R.mipmap.triangle_down);
                } else {
                    holder.mHistoryView.setVisibility(View.VISIBLE);
                    holder.mIconDetail.setImageResource(R.mipmap.triangle_up);
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
                                holder.mHistoryView.setView(data,mId);
                                clickDetail.put(dataBean, data);
                            }else {
                                holder.mHistoryView.setView(null, mId);
                                clickDetail.put(dataBean,null);
                            } /*else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }*/
                        }

                        @Override
                        public void onError() {
                            holder.mHistoryView.setView(null, mId);
                            clickDetail.put(dataBean,null);
                        }
                    });
                }
            }
        });
        updateChildView(holder, dataBean);
        return convertView;
    }

    private void updateChildView(ChildHolder holder, JczqData.DataBean dataBean) {
        String rankA = dataBean.getRankA();
        String rankH = dataBean.getRankH();
        String numHost = StringUtil.getNumber(rankH);
        String numGuest = StringUtil.getNumber(rankA);
        String rankHost = TextUtils.isEmpty(numHost) ? "" : ("[" + numHost + "]");
        String rankGuest = TextUtils.isEmpty(numGuest) ? "" : ("[" + numGuest + "]");
        holder.homeButton.setText(rankHost + dataBean.getHomeShortCn());
        holder.guestButton.setText(rankGuest+dataBean.getGuestShortCn());

        holder.idTextView.setText(dataBean.getLeagueShortCn());

        //设置比赛场次的编号
        holder.matchTextView.setText(JcPlayMethod.getFormatIssue(dataBean.getIssue(), dataBean.getSession()));

        //设置比赛的截止日期
        String jzdt = dataBean.getStopSaleTime();
        if (!TextUtils.isEmpty(jzdt)&&!jzdt.contains("-")){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            holder.endTextView.setText(format.format(Long.parseLong(jzdt)) + "截止");
        }

        //判断是串关还是单关
        if (flag) {
            if (playMethod == LotteryTypes.RQSPF) {
                if (dataBean.getLet() < 0) {

                    holder.rangQLeft.setTextColor(greenColor);
                    holder.rangQRight.setTextColor(greenColor);
                    holder.tv_rangqiu.setTextColor(greenColor);
                    holder.tv_rangqiu.setText(dataBean.getLet() + "");
                } else {
                    holder.rangQLeft.setTextColor(orangeColor);
                    holder.rangQRight.setTextColor(orangeColor);
                    holder.tv_rangqiu.setTextColor(orangeColor);
                    holder.tv_rangqiu.setText("+" + dataBean.getLet());
                }
                /*
                * 在串关的时候，判断哪个是单关
                * */
                if (dataBean.getRqspfDg() == 1)
                    holder.mDg.setVisibility(View.VISIBLE);
                else holder.mDg.setVisibility(View.INVISIBLE);

                if (dataBean.getRqspfFs() == 1) {
                    holder.tv_host_sp.setText(dataBean.getLetOdds3());
                    holder.tv_versus_sp.setText(dataBean.getLetOdds1());
                    holder.tv_guest_sp.setText(dataBean.getLetOdds0());

                    holder.tv_win_lable.setTag("让胜");
                    holder.tv_ping_lable.setTag("让平");
                    holder.tv_loss_lable.setTag("让负");
                } else {
                    holder.jz_win_ll.setClickable(false);
                    holder.jz_ping_ll.setClickable(false);
                    holder.jz_loss_ll.setClickable(false);
                    holder.tv_host_sp.setText("未开售");
                    holder.tv_versus_sp.setText("未开售");
                    holder.tv_guest_sp.setText("未开售");
                }

            } else if (playMethod == LotteryTypes.SPF) {
                holder.rangQLeft.setVisibility(View.GONE);
                holder.rangQRight.setVisibility(View.GONE);
                holder.tv_rangqiu.setVisibility(View.GONE);
                holder.tv_host_sp.setText(dataBean.getOdds3());
                 /*
                * 在串关的时候，判断哪个是单关
                * */
                if (dataBean.getSpfDg() == 1)
                    holder.mDg.setVisibility(View.VISIBLE);
                else holder.mDg.setVisibility(View.INVISIBLE);

                if (dataBean.getSpfFs() == 1) {
                    holder.tv_host_sp.setText(dataBean.getOdds3());
                    holder.tv_versus_sp.setText(dataBean.getOdds1());
                    holder.tv_guest_sp.setText(dataBean.getOdds0());

                    holder.tv_win_lable.setTag("主胜");
                    holder.tv_ping_lable.setTag("平");
                    holder.tv_loss_lable.setTag("主负");

                } else {
                    holder.jz_win_ll.setClickable(false);
                    holder.jz_ping_ll.setClickable(false);
                    holder.jz_loss_ll.setClickable(false);
                    holder.tv_host_sp.setText("未开售");
                    holder.tv_versus_sp.setText("未开售");
                    holder.tv_guest_sp.setText("未开售");
                }
            }
        } else {
            if (playMethod == LotteryTypes.RQSPF) {
                if (dataBean.getLet() < 0) {
                    holder.rangQLeft.setTextColor(greenColor);
                    holder.rangQRight.setTextColor(greenColor);
                    holder.tv_rangqiu.setTextColor(greenColor);
                    holder.tv_rangqiu.setText(dataBean.getLet() + "");
                } else {
                    holder.rangQLeft.setTextColor(orangeColor);
                    holder.rangQRight.setTextColor(orangeColor);
                    holder.tv_rangqiu.setTextColor(orangeColor);
                    holder.tv_rangqiu.setText("+" + dataBean.getLet());
                }
                if (dataBean.getRqspfDg() == 1) {
                    holder.tv_host_sp.setText(dataBean.getLetOdds3());
                    holder.tv_versus_sp.setText(dataBean.getLetOdds1());
                    holder.tv_guest_sp.setText(dataBean.getLetOdds0());

                    holder.tv_win_lable.setTag("让胜");
                    holder.tv_ping_lable.setTag("让平");
                    holder.tv_loss_lable.setTag("让负");
                } else {
                    holder.jz_win_ll.setClickable(false);
                    holder.jz_ping_ll.setClickable(false);
                    holder.jz_loss_ll.setClickable(false);
                    holder.tv_host_sp.setText("未开售");
                    holder.tv_versus_sp.setText("未开售");
                    holder.tv_guest_sp.setText("未开售");
                }

            } else if (playMethod == LotteryTypes.SPF) {
                holder.rangQLeft.setVisibility(View.GONE);
                holder.rangQRight.setVisibility(View.GONE);
                holder.tv_rangqiu.setVisibility(View.GONE);
                if (dataBean.getSpfDg() == 1) {
                    holder.tv_host_sp.setText(dataBean.getOdds3());
                    holder.tv_versus_sp.setText(dataBean.getOdds1());
                    holder.tv_guest_sp.setText(dataBean.getOdds0());

                    holder.tv_win_lable.setTag("主胜");
                    holder.tv_ping_lable.setTag("平");
                    holder.tv_loss_lable.setTag("主负");

                } else {
                    holder.jz_win_ll.setClickable(false);
                    holder.jz_ping_ll.setClickable(false);
                    holder.jz_loss_ll.setClickable(false);
                    holder.tv_host_sp.setText("未开售");
                    holder.tv_versus_sp.setText("未开售");
                    holder.tv_guest_sp.setText("未开售");
                }
            }
        }

        //因为在notifyDataSetChanged()方法执行的时候convertview是不为空的，不会重新执行findviewbyid
        //这样就导致点击过的项目，在清除后唤醒适配器，再点击就会崩溃，因为这个时候容器为空，但是小条目的tag值为true
        holder.jz_win_ll.setTag(false);
        holder.jz_ping_ll.setTag(false);
        holder.jz_loss_ll.setTag(false);
    }

    //点击每一个小的条目事件
    public void clickMatchView(View view, ChildHolder mViewHolder, JczqData.DataBean dataBean) {
        // Log.e("++++++++++++", "" + position);
        LinearLayout mLinearLayout = (LinearLayout) view;

        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
        TextView mTextView = (TextView) mLinearLayout.getChildAt(1);
        if (mLinearLayout.getTag() == null || !(Boolean) mLinearLayout.getTag()) {
            mLinearLayout.setBackgroundColor(redColor);
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
            mTitleTextView.setTextColor(grayColor);
            mTextView.setTextColor(grayColor);
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

    private void recycleUse(JczqData.DataBean dataBean, ChildHolder mViewHolder) {
        if (clickMap.containsKey(dataBean)) {
            HashMap<String, String> map = clickMap.get(dataBean);
            if (map.containsKey("主胜") || map.containsKey("让胜")) {
                mViewHolder.jz_win_ll.setBackgroundColor(redColor);
                mViewHolder.tv_win_lable.setTextColor(Color.WHITE);
                mViewHolder.tv_host_sp.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_win_ll.setBackgroundResource(R.drawable.table_bg);
                // mViewHolder.mllNoRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_host_sp.setTextColor(grayColor);
                mViewHolder.tv_win_lable.setTextColor(grayColor);
            }
            if (map.containsKey("平") || map.containsKey("让平")) {
                mViewHolder.jz_ping_ll.setBackgroundColor(redColor);
                mViewHolder.tv_ping_lable.setTextColor(Color.WHITE);
                mViewHolder.tv_versus_sp.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_ping_ll.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang1.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_versus_sp.setTextColor(grayColor);
                mViewHolder.tv_ping_lable.setTextColor(grayColor);
            }
            if (map.containsKey("主负") || map.containsKey("让负")) {
                mViewHolder.jz_loss_ll.setBackgroundColor(redColor);
                mViewHolder.tv_guest_sp.setTextColor(Color.WHITE);
                mViewHolder.tv_loss_lable.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_loss_ll.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang0.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_guest_sp.setTextColor(grayColor);
                mViewHolder.tv_loss_lable.setTextColor(grayColor);
            }

        } else {
            mViewHolder.jz_loss_ll.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.jz_win_ll.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.jz_ping_ll.setBackgroundResource(R.drawable.table_bg);

            mViewHolder.tv_guest_sp.setTextColor(grayColor);
            mViewHolder.tv_loss_lable.setTextColor(grayColor);
            mViewHolder.tv_host_sp.setTextColor(grayColor);
            mViewHolder.tv_ping_lable.setTextColor(grayColor);
            mViewHolder.tv_versus_sp.setTextColor(grayColor);
            mViewHolder.tv_win_lable.setTextColor(grayColor);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    protected class ChildHolder {
        TextView homeButton;

        TextView guestButton;

        AutofitTextView matchTextView;

        TextView idTextView;

        AutofitTextView endTextView;

        TextView tv_rangqiu;

        TextView rangQLeft;

        TextView rangQRight;

        LinearLayout jz_loss_ll;

        int jzLossChecked;

        TextView tv_loss_lable;

        TextView tv_guest_sp;

        TextView tv_win_lable;

        LinearLayout jz_win_ll;

        int jzWinChecked;

        TextView tv_host_sp;

        TextView tv_ping_lable;

        LinearLayout jz_ping_ll;

        TextView tv_versus_sp;
        HistoryDataView mHistoryView;
        LinearLayout mLl_league;
        ImageView mIconDetail;
        private ImageView mDg;
        int jzPingChecked;

        public ChildHolder() {

        }

    }

    protected class GroupHolder {
        TextView mTitleTextView;

        ImageView mIconXia;
    }

}
