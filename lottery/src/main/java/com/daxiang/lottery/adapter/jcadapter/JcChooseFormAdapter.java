package com.daxiang.lottery.adapter.jcadapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.entity.ChoosedContentFormBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class JcChooseFormAdapter extends BaseAdapter {
    private ArrayList<ArrayList<ChoosedContentFormBean>> mContentList;
    private Context mContext;
    DeleteItemListener itemListener;
    ArrayList<String> danList = new ArrayList<>();
    private boolean flag;
    private boolean noClick = false;
    private boolean isFootBall;//true是足球  false是篮球
    int maxBunch;
    // ArrayList<ImageView> imagList = new ArrayList<>();
    HashMap<Integer, String> mMap = new HashMap<>();
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public JcChooseFormAdapter(DeleteItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setData(boolean isFootBall) {
        this.isFootBall = isFootBall;
    }

    public void setData(ArrayList<ArrayList<ChoosedContentFormBean>> mContentList, boolean flag, int playMethod) {
        this.mContentList = mContentList;
        this.flag = flag;
        if (playMethod == LotteryTypes.RQSPF || playMethod == LotteryTypes.SPF || playMethod == LotteryTypes.HH) {
            maxBunch = 8;
        } else if (playMethod == LotteryTypes.CBF || playMethod == LotteryTypes.BQC) {
            maxBunch = 4;
        } else if (playMethod == LotteryTypes.JQS) {
            maxBunch = 6;
        }
    }

    @Override
    public int getCount() {
        if (mContentList == null) {
            return 0;
        } else {
            return mContentList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        mContext = parent.getContext();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_jc_choose_content, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mSession = (TextView) convertView.findViewById(R.id.session);
            mViewHolder.mHome = (TextView) convertView.findViewById(R.id.tv_host);
            // mViewHolder.mLeftBracket = (TextView) convertView.findViewById(R.id.rangQLeft);
            // mViewHolder.mLet = (TextView) convertView.findViewById(R.id.tv_rangqiu);
            // mViewHolder.mRightBracket = (TextView) convertView.findViewById(R.id.rangQRight);
            mViewHolder.mAwary = (TextView) convertView.findViewById(R.id.tv_guest);
            mViewHolder.mChooseContent = (TextView) convertView.findViewById(R.id.choose_content);
            mViewHolder.mDan = (ImageView) convertView.findViewById(R.id.dan);
            mViewHolder.mImageClear = (ImageView) convertView.findViewById(R.id.image_clear_form);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (!flag) {
            mViewHolder.mDan.setVisibility(View.INVISIBLE);
        } else {
            mViewHolder.mDan.setVisibility(View.VISIBLE);
        }

        final ArrayList<ChoosedContentFormBean> ContentFormList = mContentList.get(position);
        String session = ContentFormList.get(0).getMid();
        String home = ContentFormList.get(0).getHome();
        String awary = ContentFormList.get(0).getAwary();
        if (!TextUtils.isEmpty(session)) {
            String year = session.substring(0, 8);
            String mid = session.substring(8, 11);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            try {
                Date parse = format.parse(year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(parse);
                int i1 = cal.get(Calendar.DAY_OF_WEEK);
                String string = weekDays[i1 - 1] + mid;
                mViewHolder.mSession.setText(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        StringBuilder mStrBuilder = new StringBuilder();
        for (int i = 0; i < ContentFormList.size(); i++) {
            mStrBuilder.append(ContentFormList.get(i).getContent() + ";");
        }
        mViewHolder.mChooseContent.setText(mStrBuilder.toString());

        if (ContentFormList.get(0).getLet() < 0) {
            if (isFootBall) {//足球
                String let = "(" + (int) ContentFormList.get(0).getLet() + ")";//足球让球为整数。篮球让分是小数
                String homeS = home + let;
                SpannableString spannableString = new SpannableString(homeS);
                spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.green_let)),home.length(),homeS.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mViewHolder.mHome.setText(spannableString);
                mViewHolder.mAwary.setText(awary);
            } else {//篮球
                String let = "(" + ContentFormList.get(0).getLet() + ")";
                String homeS = home + let;
                SpannableString spannableString = new SpannableString(homeS);
                spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.green_let)),home.length(),homeS.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mViewHolder.mHome.setText(awary);
                mViewHolder.mAwary.setText(spannableString);
            }
        } else {
            if (isFootBall) {//足球
                String let = "(" + (int) ContentFormList.get(0).getLet() + ")";//足球让球为整数。篮球让分是小数
                String homeS = home + let;
                SpannableString spannableString = new SpannableString(homeS);
                spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.orange_let)),home.length(),homeS.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mViewHolder.mHome.setText(spannableString);
                mViewHolder.mAwary.setText(awary);
            } else {//篮球
                String let = "(" + ContentFormList.get(0).getLet() + ")";
                String homeS = home + let;
                SpannableString spannableString = new SpannableString(homeS);
                spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.orange_let)),home.length(),homeS.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mViewHolder.mHome.setText(awary);
                mViewHolder.mAwary.setText(spannableString);
            }
        }
        if (mContentList.size() > 2) {
            mViewHolder.mDan.setImageResource(R.drawable.dan_normal);
            /**
             * 如果mMap中包含这个position那么就说明已经点击过了，肯定是红色的，
             * 如果mMap中不包含这个position 那么就说明没有被点击到，这个时候要做一个
             * 判断，看看是否串数小于选中条目的条数，以及是否小于该玩法的最大串数，
             * 如果满足条件就设置成中红，并且让其可点击，否则设置成暗红，并让其不可点击。
             * */
            if (mMap.containsKey(position)) {
                //mViewHolder.mDan.setClickable(true);
                mViewHolder.mDan.setImageResource(R.drawable.dan_select);
                mViewHolder.mDan.setTag(false);
            } else {
                if (mMap.size() < mContentList.size() - 1 && mMap.size() < maxBunch - 1) {
                    // mViewHolder.mDan.setClickable(true);
                    mViewHolder.mDan.setImageResource(R.drawable.dan_normal);
                    mViewHolder.mDan.setTag(false);
                } else {
                    // mViewHolder.mDan.setClickable(false);
                    // mViewHolder.mDan.setOnClickListener(null);
                    mViewHolder.mDan.setImageResource(R.drawable.dan_no);
                    mViewHolder.mDan.setTag(true);
                }
            }
        } else {
            mViewHolder.mDan.setImageResource(R.drawable.dan_no);
            //  mViewHolder.mDan.setOnClickListener(null);
            //  mViewHolder.mDan.setClickable(false);
            mViewHolder.mDan.setTag(true);
        }
        mViewHolder.mDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Boolean) mViewHolder.mDan.getTag()) {
                    return;
                }
                if (mContentList.size() > 2) {
                    //比分串关最大是4串1，进球数串关最大是6串1，半全场串关最大是4串1

                    if (danList.contains(ContentFormList.get(0).getMid())) {
                        mViewHolder.mDan.setImageResource(R.drawable.dan_normal);
                        danList.remove(ContentFormList.get(0).getMid());
                        mMap.remove(position);
                    } else {
                        mViewHolder.mDan.setImageResource(R.drawable.dan_select);
                        danList.add(ContentFormList.get(0).getMid());
                        mMap.put(position, ContentFormList.get(0).getMid());
                    }
                    itemListener.danItem(danList, mMap);

                }
                notifyDataSetChanged();
            }
        });
        mViewHolder.mImageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.deleteItem(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mSession;
        TextView mHome;
        TextView mAwary;
        // TextView mLet;
        //TextView mLeftBracket;
        // TextView mRightBracket;
        TextView mChooseContent;
        ImageView mDan;
        ImageView mImageClear;
    }

    public interface DeleteItemListener {
        void deleteItem(int position);

        void danItem(ArrayList<String> danList, HashMap<Integer, String> danMap);
    }
}
