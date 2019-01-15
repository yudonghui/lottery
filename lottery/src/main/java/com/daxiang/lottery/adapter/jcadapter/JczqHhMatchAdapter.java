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
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.cxinterface.OnClickJczqListener;
import com.daxiang.lottery.dialog.AllPlayMethodDialog;
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
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class JczqHhMatchAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<JczqData.DataBean>> data;
    private String lotcode;
    private Context mContext;
    private OnClickJczqListener mOnClickJcListener;
    protected LayoutInflater mInflater;
    int rangColorX;
    int rangColorD;
    int rangColorBgD;
    int textColor;
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    HashMap<JczqData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();
    HashMap<JczqData.DataBean, HistoryBean.DataBean.ItemsBean> clickDetail = new HashMap<>();

    // HashMap<String, String> chooseOddMap = new HashMap<>();
    public void setData(Activity mContext, HashMap<JczqData.DataBean, HashMap<String, String>> clickMap, ArrayList<ArrayList<JczqData.DataBean>> data, String lotcode, OnClickJczqListener mOnClickJcListener) {
        this.lotcode = lotcode;
        this.data = data;
        if (clickMap != null) {
            this.clickMap = clickMap;
        }
        this.mContext = mContext;
        this.mOnClickJcListener = mOnClickJcListener;
        mInflater = LayoutInflater.from(mContext);
        rangColorX = mContext.getResources().getColor(R.color.green_let);
        rangColorD = mContext.getResources().getColor(R.color.red_txt);
        rangColorBgD = mContext.getResources().getColor(R.color.orange_let);
        textColor = mContext.getResources().getColor(R.color.gray_txt);
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
        if (data.get(groupPosition) == null || data.get(groupPosition).size() == 0) {
            return convertView;
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // mContext = parent.getContext();
        ViewHolder mViewHolder = null;
        //加载合买的布局文件并且填充
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_jczq_lv, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mHh_spf_ll = (LinearLayout) convertView.findViewById(R.id.hh_spf_ll);
            mViewHolder.mHh_rqspf_ll = (LinearLayout) convertView.findViewById(R.id.hh_rqspf_ll);
            mViewHolder.mHistoryView = (HistoryDataView) convertView.findViewById(R.id.historydata);
            mViewHolder.mLl_league = (LinearLayout) convertView.findViewById(R.id.ll_league);
            mViewHolder.mIconDetail = (ImageView) convertView.findViewById(R.id.icon_detail);
            mViewHolder.mHost = (TextView) convertView.findViewById(R.id.tv_host);
            mViewHolder.mRangQiuNum = (TextView) convertView.findViewById(R.id.tv_rangqiu);
            mViewHolder.mGuest = (TextView) convertView.findViewById(R.id.tv_guest);
            mViewHolder.mGameTitle = (TextView) convertView.findViewById(R.id.text_game_title);
            mViewHolder.mGameId = (AutofitTextView) convertView.findViewById(R.id.text_game_id);
            mViewHolder.mGameEndTime = (AutofitTextView) convertView.findViewById(R.id.text_game_endtime);
            mViewHolder.mNoRang3 = (TextView) convertView.findViewById(R.id.tv_pop_spf_0);
            mViewHolder.mNoRang1 = (TextView) convertView.findViewById(R.id.tv_pop_spf_1);
            mViewHolder.mNoRang0 = (TextView) convertView.findViewById(R.id.tv_pop_spf_2);
            mViewHolder.mRang3 = (TextView) convertView.findViewById(R.id.tv_pop_rqspf_0);
            mViewHolder.mRang1 = (TextView) convertView.findViewById(R.id.tv_pop_rqspf_1);
            mViewHolder.mRang0 = (TextView) convertView.findViewById(R.id.tv_pop_rqspf_2);
            mViewHolder.mRangQLeft = (TextView) convertView.findViewById(R.id.rangQLeft);
            mViewHolder.mRangQRight = (TextView) convertView.findViewById(R.id.rangQRight);
            mViewHolder.mllNoRang3 = (LinearLayout) convertView.findViewById(R.id.ll_pop_spf_0);
            mViewHolder.mllNoRang1 = (LinearLayout) convertView.findViewById(R.id.ll_pop_spf_1);
            mViewHolder.mllNoRang0 = (LinearLayout) convertView.findViewById(R.id.ll_pop_spf_2);
            mViewHolder.mllRang3 = (LinearLayout) convertView.findViewById(R.id.ll_pop_rqspf_0);
            mViewHolder.mllRang1 = (LinearLayout) convertView.findViewById(R.id.ll_pop_rqspf_1);
            mViewHolder.mllRang0 = (LinearLayout) convertView.findViewById(R.id.ll_pop_rqspf_2);
            mViewHolder.mTitleNoRang3 = (TextView) convertView.findViewById(R.id.tv_spf3_title);
            mViewHolder.mTitleNoRang1 = (TextView) convertView.findViewById(R.id.tv_spf1_title);
            mViewHolder.mTitleNoRang0 = (TextView) convertView.findViewById(R.id.tv_spf0_title);
            mViewHolder.mTitleRang3 = (TextView) convertView.findViewById(R.id.tv_rqspf3_title);
            mViewHolder.mTitleRang1 = (TextView) convertView.findViewById(R.id.tv_rqspf1_title);
            mViewHolder.mTitleRang0 = (TextView) convertView.findViewById(R.id.tv_rqspf0_title);
            mViewHolder.mAllPlayMethod = (TextView) convertView.findViewById(R.id.tv_jz_item_select);
            mViewHolder.mRangColor = (TextView) convertView.findViewById(R.id.rangQcolor);
            mViewHolder.mSpfDg = (ImageView) convertView.findViewById(R.id.spf_dg);
            mViewHolder.mRqspfDg = (ImageView) convertView.findViewById(R.id.rqspf_dg);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final JczqData.DataBean dataBean = data.get(groupPosition).get(childPosition);
        final String mId = dataBean.getuMid();
        final ViewHolder finalMViewHolder = mViewHolder;
        mViewHolder.mllNoRang0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(finalMViewHolder.mllNoRang0, finalMViewHolder.mTitleNoRang0, finalMViewHolder.mNoRang0, finalMViewHolder, dataBean);
            }
        });
        mViewHolder.mllNoRang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(finalMViewHolder.mllNoRang1, finalMViewHolder.mTitleNoRang1, finalMViewHolder.mNoRang1, finalMViewHolder, dataBean);
            }
        });
        mViewHolder.mllNoRang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(finalMViewHolder.mllNoRang3, finalMViewHolder.mTitleNoRang3, finalMViewHolder.mNoRang3, finalMViewHolder, dataBean);
            }
        });
        mViewHolder.mllRang0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(finalMViewHolder.mllRang0, finalMViewHolder.mTitleRang0, finalMViewHolder.mRang0, finalMViewHolder, dataBean);
            }
        });
        mViewHolder.mllRang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(finalMViewHolder.mllRang1, finalMViewHolder.mTitleRang1, finalMViewHolder.mRang1, finalMViewHolder, dataBean);
            }
        });
        mViewHolder.mllRang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(finalMViewHolder.mllRang3, finalMViewHolder.mTitleRang3, finalMViewHolder.mRang3, finalMViewHolder, dataBean);
            }
        });
        //解决复用点击事件错乱的问题
        recycleUse(dataBean, mViewHolder);
        if (clickDetail.containsKey(dataBean)) {
            mViewHolder.mIconDetail.setImageResource(R.mipmap.triangle_up);
            mViewHolder.mHistoryView.setVisibility(View.VISIBLE);
            mViewHolder.mHistoryView.setView(clickDetail.get(dataBean), mId);
        } else {
            mViewHolder.mIconDetail.setImageResource(R.mipmap.triangle_down);
            mViewHolder.mHistoryView.setVisibility(View.GONE);
        }
        String rankA = dataBean.getRankA();
        String rankH = dataBean.getRankH();
        String numHost = StringUtil.getNumber(rankH);
        String numGuest = StringUtil.getNumber(rankA);
        String rankHost = TextUtils.isEmpty(numHost) ? "" : ("[" + numHost + "]");
        String rankGuest = TextUtils.isEmpty(numGuest) ? "" : ("[" + numGuest + "]");
        mViewHolder.mHost.setText(rankHost + dataBean.getHomeShortCn());
        mViewHolder.mGuest.setText(rankGuest + dataBean.getGuestShortCn());
        if (dataBean.getLet() > 0) {
            mViewHolder.mRangQLeft.setTextColor(rangColorBgD);
            mViewHolder.mRangQRight.setTextColor(rangColorBgD);
            mViewHolder.mRangQiuNum.setTextColor(rangColorBgD);
            mViewHolder.mRangQiuNum.setText("+" + dataBean.getLet());
            mViewHolder.mRangColor.setBackgroundColor(rangColorBgD);
        } else {
            mViewHolder.mRangQLeft.setTextColor(rangColorX);
            mViewHolder.mRangQRight.setTextColor(rangColorX);
            mViewHolder.mRangQiuNum.setTextColor(rangColorX);
            mViewHolder.mRangColor.setBackgroundColor(rangColorX);
            mViewHolder.mRangQiuNum.setText(dataBean.getLet() + "");
        }
        //设置比赛场次的编号
        mViewHolder.mGameId.setText(JcPlayMethod.getFormatIssue(dataBean.getIssue(), dataBean.getSession()));
        mViewHolder.mGameTitle.setText(dataBean.getLeagueShortCn());

        //设置比赛的截止日期
        String jzdt = dataBean.getStopSaleTime();
        if (!TextUtils.isEmpty(jzdt) && !jzdt.contains("-")) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            mViewHolder.mGameEndTime.setText(format.format(Long.parseLong(jzdt)) + "截止");
        }

        /*
        * 单关标志的显示和隐藏
        * */
        if (dataBean.getSpfDg() == 1) {
            mViewHolder.mSpfDg.setVisibility(View.VISIBLE);
            //mViewHolder.mHh_spf_ll.setBackgroundResource(R.drawable.shape_red_dg);
        } else {
            mViewHolder.mSpfDg.setVisibility(View.INVISIBLE);
            // mViewHolder.mHh_spf_ll.setBackgroundResource(R.drawable.shape_gray_dg);
        }
        if (dataBean.getRqspfDg() == 1) {
            mViewHolder.mRqspfDg.setVisibility(View.VISIBLE);
            // mViewHolder.mHh_rqspf_ll.setBackgroundResource(R.drawable.shape_red_dg);
        } else {
            mViewHolder.mRqspfDg.setVisibility(View.INVISIBLE);
            //mViewHolder.mHh_rqspf_ll.setBackgroundResource(R.drawable.shape_gray_dg);
        }

        if (dataBean.getSpfFs() == 1) {
            mViewHolder.mNoRang3.setText(dataBean.getOdds3());
            mViewHolder.mNoRang1.setText(dataBean.getOdds1());
            mViewHolder.mNoRang0.setText(dataBean.getOdds0());

            mViewHolder.mTitleNoRang3.setTag("主胜");
            mViewHolder.mTitleNoRang1.setTag("平");
            mViewHolder.mTitleNoRang0.setTag("主负");

        } else {
            mViewHolder.mllNoRang0.setClickable(false);
            mViewHolder.mllNoRang1.setClickable(false);
            mViewHolder.mllNoRang3.setClickable(false);

            mViewHolder.mNoRang3.setText("未开售");
            mViewHolder.mNoRang1.setText("未开售");
            mViewHolder.mNoRang0.setText("未开售");
        }
        if (dataBean.getRqspfFs() == 1) {
            mViewHolder.mRang3.setText(dataBean.getLetOdds3());
            mViewHolder.mRang1.setText(dataBean.getLetOdds1());
            mViewHolder.mRang0.setText(dataBean.getLetOdds0());

            mViewHolder.mTitleRang3.setTag("让胜");
            mViewHolder.mTitleRang1.setTag("让平");
            mViewHolder.mTitleRang0.setTag("让负");
        } else {
            mViewHolder.mllRang0.setClickable(false);
            mViewHolder.mllRang1.setClickable(false);
            mViewHolder.mllRang3.setClickable(false);

            mViewHolder.mRang3.setText("未开售");
            mViewHolder.mRang1.setText("未开售");
            mViewHolder.mRang0.setText("未开售");
        }
        //因为在notifyDataSetChanged()方法执行的时候convertview是不为空的，不会重新执行findviewbyid
        //这样就导致点击过的项目，在清除后唤醒适配器，再点击就会崩溃，因为这个时候容器为空，但是小条目的tag值为true
        mViewHolder.mllNoRang0.setTag(false);
        mViewHolder.mllNoRang1.setTag(false);
        mViewHolder.mllNoRang3.setTag(false);
        mViewHolder.mllRang0.setTag(false);
        mViewHolder.mllRang1.setTag(false);
        mViewHolder.mllRang3.setTag(false);
        final ViewHolder finalMViewHolder1 = mViewHolder;
        mViewHolder.mAllPlayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止连续点击全部玩法的时候跳出来多个框
                if (ClickUtils.isFastClick()) {
                    return;
                }
                final AllPlayMethodDialog mDialog = new AllPlayMethodDialog((Activity) mContext, dataBean, clickMap.get(dataBean), new AllPlayMethodDialog.OnOddsChooseFinishListener() {
                    @Override
                    public void onChooseFinish(HashMap<String, String> choosedOddMap) {
                        //mDialog.dismiss();
                        clickMap.remove(dataBean);
                        setAllPlayMethod(choosedOddMap, finalMViewHolder);
                        finalMViewHolder1.mllNoRang3.setTag(choosedOddMap.containsKey("主胜") ? true : false);
                        finalMViewHolder1.mllNoRang1.setTag(choosedOddMap.containsKey("平") ? true : false);
                        finalMViewHolder1.mllNoRang0.setTag(choosedOddMap.containsKey("主负") ? true : false);
                        finalMViewHolder1.mllRang3.setTag(choosedOddMap.containsKey("让胜") ? true : false);
                        finalMViewHolder1.mllRang1.setTag(choosedOddMap.containsKey("让平") ? true : false);
                        finalMViewHolder1.mllRang0.setTag(choosedOddMap.containsKey("让负") ? true : false);

                        updateSpfOrRqspfButtonCheckStatus(finalMViewHolder1.mllNoRang0, finalMViewHolder.mTitleNoRang0, finalMViewHolder.mNoRang0, finalMViewHolder1);
                        updateSpfOrRqspfButtonCheckStatus(finalMViewHolder1.mllNoRang1, finalMViewHolder.mTitleNoRang1, finalMViewHolder.mNoRang1, finalMViewHolder1);
                        updateSpfOrRqspfButtonCheckStatus(finalMViewHolder1.mllNoRang3, finalMViewHolder.mTitleNoRang3, finalMViewHolder.mNoRang3, finalMViewHolder1);
                        updateSpfOrRqspfButtonCheckStatus(finalMViewHolder1.mllRang0, finalMViewHolder.mTitleRang0, finalMViewHolder.mRang0, finalMViewHolder1);
                        updateSpfOrRqspfButtonCheckStatus(finalMViewHolder1.mllRang1, finalMViewHolder.mTitleRang1, finalMViewHolder.mRang1, finalMViewHolder1);
                        updateSpfOrRqspfButtonCheckStatus(finalMViewHolder1.mllRang3, finalMViewHolder.mTitleRang3, finalMViewHolder.mRang3, finalMViewHolder1);
                        if (choosedOddMap != null && choosedOddMap.size() != 0) {
                            clickMap.put(dataBean, choosedOddMap);
                        }
                        mOnClickJcListener.onClickJcListener(clickMap);
                    }
                });
                mDialog.show();
                DialogAnimotion.setAnimotion(mDialog);
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
             /*   View inflate = View.inflate(mContext, R.layout.jczq_pop_view_hh, null);
                alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.show();
                alertDialog.setContentView(inflate);*/
            }
        });
        mViewHolder.mLl_league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickDetail.containsKey(dataBean)) {
                    clickDetail.remove(dataBean);
                    finalMViewHolder.mHistoryView.setVisibility(View.GONE);
                    finalMViewHolder.mIconDetail.setImageResource(R.mipmap.triangle_down);
                } else {
                    finalMViewHolder.mHistoryView.setVisibility(View.VISIBLE);
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
                                finalMViewHolder.mHistoryView.setView(data, mId);
                                clickDetail.put(dataBean, data);
                            } else {
                                finalMViewHolder.mHistoryView.setView(null, mId);
                                clickDetail.put(dataBean, null);
                            }

                        }

                        @Override
                        public void onError() {
                            finalMViewHolder.mHistoryView.setView(null, mId);
                            clickDetail.put(dataBean, null);
                        }
                    });
                    finalMViewHolder.mIconDetail.setImageResource(R.mipmap.triangle_up);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //点击每一个小的条目事件
    public void clickMatchView(LinearLayout mLinearLayout, TextView mTitleTextView, TextView mTextView, ViewHolder mViewHolder, JczqData.DataBean dataBean) {
       /* // Log.e("++++++++++++", "" + position);
        LinearLayout mLinearLayout = (LinearLayout) view;

        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
        TextView mTextView = (TextView) mLinearLayout.getChildAt(1);*/
        if (mLinearLayout.getTag() == null || !(Boolean) mLinearLayout.getTag()) {
            mLinearLayout.setBackgroundColor(rangColorD);
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
            mTitleTextView.setTextColor(textColor);
            mTextView.setTextColor(textColor);
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
            mViewHolder.mAllPlayMethod.setTextColor(textColor);
            mViewHolder.mAllPlayMethod.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mAllPlayMethod.setText("全部玩法");
        } else {
            mViewHolder.mAllPlayMethod.setTextColor(Color.WHITE);
            mViewHolder.mAllPlayMethod.setBackgroundColor(rangColorD);
            mViewHolder.mAllPlayMethod.setText("已选" + map.size() + "项");
        }
    }

    private void recycleUse(JczqData.DataBean dataBean, ViewHolder mViewHolder) {
        if (clickMap.containsKey(dataBean)) {
            HashMap<String, String> map = clickMap.get(dataBean);
            if (map.containsKey("主胜")) {
                mViewHolder.mllNoRang3.setBackgroundColor(rangColorD);
                mViewHolder.mNoRang3.setTextColor(Color.WHITE);
                mViewHolder.mTitleNoRang3.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mllNoRang3.setBackgroundResource(R.drawable.table_bg);
                // mViewHolder.mllNoRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.mNoRang3.setTextColor(textColor);
                mViewHolder.mTitleNoRang3.setTextColor(textColor);
            }
            if (map.containsKey("平")) {
                mViewHolder.mllNoRang1.setBackgroundColor(rangColorD);
                mViewHolder.mNoRang1.setTextColor(Color.WHITE);
                mViewHolder.mTitleNoRang1.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mllNoRang1.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang1.setBackgroundColor(Color.WHITE);
                mViewHolder.mNoRang1.setTextColor(textColor);
                mViewHolder.mTitleNoRang1.setTextColor(textColor);
            }
            if (map.containsKey("主负")) {
                mViewHolder.mllNoRang0.setBackgroundColor(rangColorD);
                mViewHolder.mNoRang0.setTextColor(Color.WHITE);
                mViewHolder.mTitleNoRang0.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mllNoRang0.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang0.setBackgroundColor(Color.WHITE);
                mViewHolder.mNoRang0.setTextColor(textColor);
                mViewHolder.mTitleNoRang0.setTextColor(textColor);
            }
            if (map.containsKey("让胜")) {
                mViewHolder.mllRang3.setBackgroundColor(rangColorD);
                mViewHolder.mRang3.setTextColor(Color.WHITE);
                mViewHolder.mTitleRang3.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mllRang3.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.mRang3.setTextColor(textColor);
                mViewHolder.mTitleRang3.setTextColor(textColor);
            }
            if (map.containsKey("让平")) {
                mViewHolder.mllRang1.setBackgroundColor(rangColorD);
                mViewHolder.mRang1.setTextColor(Color.WHITE);
                mViewHolder.mTitleRang1.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mllRang1.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllRang1.setBackgroundColor(Color.WHITE);
                mViewHolder.mRang1.setTextColor(textColor);
                mViewHolder.mTitleRang1.setTextColor(textColor);
            }
            if (map.containsKey("让负")) {
                mViewHolder.mllRang0.setBackgroundColor(rangColorD);
                mViewHolder.mRang0.setTextColor(Color.WHITE);
                mViewHolder.mTitleRang0.setTextColor(Color.WHITE);
            } else {
                mViewHolder.mllRang0.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllRang0.setBackgroundColor(Color.WHITE);
                mViewHolder.mRang0.setTextColor(textColor);
                mViewHolder.mTitleRang0.setTextColor(textColor);
            }
            mViewHolder.mAllPlayMethod.setTextColor(Color.WHITE);
            mViewHolder.mAllPlayMethod.setBackgroundColor(rangColorD);
            mViewHolder.mAllPlayMethod.setText("已选" + map.size() + "项");

        } else {
            mViewHolder.mllRang3.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mllRang1.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mllRang0.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mllNoRang3.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mllNoRang1.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mllNoRang0.setBackgroundResource(R.drawable.table_bg);


            mViewHolder.mNoRang3.setTextColor(textColor);
            mViewHolder.mTitleNoRang3.setTextColor(textColor);
            mViewHolder.mNoRang1.setTextColor(textColor);
            mViewHolder.mTitleNoRang1.setTextColor(textColor);
            mViewHolder.mNoRang0.setTextColor(textColor);
            mViewHolder.mTitleNoRang0.setTextColor(textColor);

            mViewHolder.mRang3.setTextColor(textColor);
            mViewHolder.mTitleRang3.setTextColor(textColor);
            mViewHolder.mRang1.setTextColor(textColor);
            mViewHolder.mTitleRang1.setTextColor(textColor);
            mViewHolder.mRang0.setTextColor(textColor);
            mViewHolder.mTitleRang0.setTextColor(textColor);

            mViewHolder.mAllPlayMethod.setTextColor(textColor);
            mViewHolder.mAllPlayMethod.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.mAllPlayMethod.setText("全部玩法");
        }
    }

    private void updateSpfOrRqspfButtonCheckStatus(LinearLayout mLinearLayout, TextView mTitleTextView, TextView mTextView, ViewHolder mViewHolder) {

       /* LinearLayout mLinearLayout = (LinearLayout) view;

        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
        TextView mTextView = (TextView) mLinearLayout.getChildAt(1);*/
        if ((Boolean) mLinearLayout.getTag()) {
            mLinearLayout.setBackgroundColor(rangColorD);
            mTitleTextView.setTextColor(Color.WHITE);
            mTextView.setTextColor(Color.WHITE);
            mLinearLayout.setTag(true);
        } else {
            mLinearLayout.setBackgroundResource(R.drawable.table_bg);
            mTitleTextView.setTextColor(textColor);
            mTextView.setTextColor(textColor);
            mLinearLayout.setTag(false);
        }
    }

    class ViewHolder {
        LinearLayout mHh_spf_ll;
        LinearLayout mHh_rqspf_ll;
        TextView mRang0;
        TextView mRang1;
        TextView mRang3;
        TextView mNoRang0;
        TextView mNoRang1;
        TextView mNoRang3;
        AutofitTextView mGameEndTime;
        AutofitTextView mGameId;
        TextView mGameTitle;
        TextView mGuest;
        TextView mRangQiuNum;
        TextView mHost;
        TextView mRangQLeft;
        TextView mRangQRight;
        LinearLayout mllNoRang3;
        LinearLayout mllNoRang1;
        LinearLayout mllNoRang0;
        LinearLayout mllRang3;
        LinearLayout mllRang1;
        LinearLayout mllRang0;
        TextView mTitleNoRang3;
        TextView mTitleNoRang1;
        TextView mTitleNoRang0;
        TextView mTitleRang3;
        TextView mTitleRang1;
        TextView mTitleRang0;
        TextView mAllPlayMethod;
        TextView mRangColor;
        ImageView mSpfDg;
        ImageView mRqspfDg;
        HistoryDataView mHistoryView;
        LinearLayout mLl_league;
        ImageView mIconDetail;
    }

    class GroupHolder {
        TextView mTitleTextView;
        ImageView mIconXia;
    }
}
