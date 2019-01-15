package com.daxiang.lottery.adapter.sfcandr9cadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.SfcAndRjcData;
import com.daxiang.lottery.view.HistoryDataView;
import com.daxiang.lottery.view.autotextview.AutofitTextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ChooseFormAdapter extends BaseAdapter {
    List<SfcAndRjcData.DataBean> dataList;
    private Context mContext;
    OnClickSfcListener mOnClickListener;
    HashMap<SfcAndRjcData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();
    HashMap<SfcAndRjcData.DataBean, Boolean> clickDetail = new HashMap<>();
    private int colorRed;
    private int colorGray;

    public void setData(Context mContext, List<SfcAndRjcData.DataBean> dataList, OnClickSfcListener mOnClickListener) {
        this.dataList = dataList;
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
        colorRed = mContext.getResources().getColor(R.color.red_txt);
        colorGray = mContext.getResources().getColor(R.color.gray_txt);
    }

    /* public ChooseFormAdapter(List<SfcAndRjcData.DataBean> dataList,OnClickSfcListener mOnClickListener){
         this.dataList=dataList;
         this.mOnClickListener = mOnClickListener;
     }*/
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.sfc_list_child_view, null);
            holder = new ViewHolder();
            holder.mHistoryView = (HistoryDataView) convertView.findViewById(R.id.historydata);
            holder.mLl_league = (LinearLayout) convertView.findViewById(R.id.ll_league);
            holder.mIconDetail = (ImageView) convertView.findViewById(R.id.icon_detail);
            holder.matchTextView = (TextView) convertView.findViewById(R.id.tv_competition);
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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SfcAndRjcData.DataBean dataBean = dataList.get(position);
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
        //胜负彩的战队详情
        holder.mIconDetail.setVisibility(View.GONE);
       /* if (clickDetail.containsKey(dataBean)) {
            holder.mIconDetail.setImageResource(R.mipmap.triangle_up);
            holder.mHistoryView.setVisibility(View.VISIBLE);
        } else {
            holder.mIconDetail.setImageResource(R.mipmap.triangle_down);
            holder.mHistoryView.setVisibility(View.GONE);
        }
        holder.mHistoryView.setData(dataBean.getGameId() + "");
        holder.mLl_league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickDetail.containsKey(dataBean)) {
                    clickDetail.remove(dataBean);
                    holder.mHistoryView.setVisibility(View.GONE);
                   // holder.mHistoryView.setAnimation(AnimationUtils.moveToViewLocation());
                    holder.mIconDetail.setImageResource(R.mipmap.triangle_down);
                } else {
                    clickDetail.put(dataBean, true);
                    holder.mHistoryView.setVisibility(View.VISIBLE);
                   // holder.mHistoryView.setAnimation(AnimationUtils.moveToViewBottom());
                    holder.mIconDetail.setImageResource(R.mipmap.triangle_up);
                }
            }
        });*/
        updateChildView(holder, dataBean);
        return convertView;
    }

    private void updateChildView(ViewHolder holder, SfcAndRjcData.DataBean dataBean) {
        //主队
        holder.homeButton.setText(dataBean.getTeamName0());
        //客队
        holder.guestButton.setText(dataBean.getTeamName1());
        //第一行
        holder.idTextView.setText("第" + dataBean.getSerialNo() + "场");
        //第二行
        holder.matchTextView.setText(dataBean.getLeagueName());
        //第三行 设置比赛的开赛日期
        long startTime = dataBean.getKickOffTime();
       /* long time1=startTime/60000;
        long time11=time1/60;
        long time2=time1%60;
        long time3=time11%24;*/
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String startDate = format.format(startTime);
        holder.endTextView.setText(startDate + "开赛");
        holder.rangQLeft.setVisibility(View.GONE);
        holder.rangQRight.setVisibility(View.GONE);
        holder.tv_rangqiu.setVisibility(View.GONE);

        holder.tv_host_sp.setText(dataBean.getOdds3());
        holder.tv_versus_sp.setText(dataBean.getOdds1());
        holder.tv_guest_sp.setText(dataBean.getOdds0());

        holder.tv_win_lable.setTag("主胜");
        holder.tv_ping_lable.setTag("平");
        holder.tv_loss_lable.setTag("主负");
        //因为在notifyDataSetChanged()方法执行的时候convertview是不为空的，不会重新执行findviewbyid
        //这样就导致点击过的项目，在清除后唤醒适配器，再点击就会崩溃，因为这个时候容器为空，但是小条目的tag值为true
        holder.jz_win_ll.setTag(false);
        holder.jz_ping_ll.setTag(false);
        holder.jz_loss_ll.setTag(false);
    }

    //点击每一个小的条目事件
    public void clickMatchView(View view, ViewHolder mViewHolder, SfcAndRjcData.DataBean dataBean) {
        LinearLayout mLinearLayout = (LinearLayout) view;

        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
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
        mOnClickListener.onClickSfcListener(clickMap);
    }

    private void recycleUse(SfcAndRjcData.DataBean dataBean, ViewHolder mViewHolder) {
        if (clickMap.containsKey(dataBean)) {
            HashMap<String, String> map = clickMap.get(dataBean);
            if (map.containsKey("主胜")) {
                mViewHolder.jz_win_ll.setBackgroundColor(colorRed);
                mViewHolder.tv_win_lable.setTextColor(Color.WHITE);
                mViewHolder.tv_host_sp.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_win_ll.setBackgroundResource(R.drawable.table_bg);
                // mViewHolder.mllNoRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_host_sp.setTextColor(colorGray);
                mViewHolder.tv_win_lable.setTextColor(colorGray);
            }
            if (map.containsKey("平")) {
                mViewHolder.jz_ping_ll.setBackgroundColor(colorRed);
                mViewHolder.tv_ping_lable.setTextColor(Color.WHITE);
                mViewHolder.tv_versus_sp.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_ping_ll.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang1.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_versus_sp.setTextColor(colorGray);
                mViewHolder.tv_ping_lable.setTextColor(colorGray);
            }
            if (map.containsKey("主负")) {
                mViewHolder.jz_loss_ll.setBackgroundColor(colorRed);
                mViewHolder.tv_guest_sp.setTextColor(Color.WHITE);
                mViewHolder.tv_loss_lable.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_loss_ll.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang0.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_guest_sp.setTextColor(colorGray);
                mViewHolder.tv_loss_lable.setTextColor(colorGray);
            }

        } else {
            mViewHolder.jz_loss_ll.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.jz_win_ll.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.jz_ping_ll.setBackgroundResource(R.drawable.table_bg);

            mViewHolder.tv_guest_sp.setTextColor(colorGray);
            mViewHolder.tv_loss_lable.setTextColor(colorGray);
            mViewHolder.tv_host_sp.setTextColor(colorGray);
            mViewHolder.tv_ping_lable.setTextColor(colorGray);
            mViewHolder.tv_versus_sp.setTextColor(colorGray);
            mViewHolder.tv_win_lable.setTextColor(colorGray);
        }
    }

    protected class ViewHolder {
        TextView homeButton;

        TextView guestButton;

        TextView matchTextView;

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

        int jzPingChecked;
        HistoryDataView mHistoryView;
        LinearLayout mLl_league;
        ImageView mIconDetail;
    }

    public interface OnClickSfcListener {
        void onClickSfcListener(HashMap<SfcAndRjcData.DataBean, HashMap<String, String>> clickMap);
    }
}
