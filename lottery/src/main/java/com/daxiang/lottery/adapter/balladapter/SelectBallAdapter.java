package com.daxiang.lottery.adapter.balladapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.ClearBallInterface;

import java.util.List;
/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class SelectBallAdapter extends BaseAdapter {
    private List<StringBuilder> ballStrList;
    private List<Integer> redBallLengthList;
    private List<String> mListGroup;
    private Context mContext;
    String lotcode;
    private ClearBallInterface mClearBallInterface;

    public SelectBallAdapter(List<StringBuilder> ballStrList, List<Integer> redBallLengthList, List<String> mListGroup, ClearBallInterface mClearBallInterface, String lotcode) {
        this.ballStrList = ballStrList;
        this.redBallLengthList = redBallLengthList;
        this.mListGroup = mListGroup;
        this.mClearBallInterface = mClearBallInterface;
        this.lotcode = lotcode;
    }

    @Override
    public int getCount() {
        return ballStrList.size();
    }

    @Override
    public Object getItem(int position) {
        return ballStrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mContext = parent.getContext();
            convertView = View.inflate(mContext, R.layout.item_listview_select_ball, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.image_clear_bet);
            mViewHolder.mBallNum = (TextView) convertView.findViewById(R.id.text_ballnum);
            mViewHolder.mTimes = (TextView) convertView.findViewById(R.id.text_times);
            mViewHolder.mMoney = (TextView) convertView.findViewById(R.id.text_money);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(ballStrList.get(position));
        if("23529".equals(lotcode)||"51".equals(lotcode)){
            ssb.setSpan(new ForegroundColorSpan(Color.BLUE), redBallLengthList.get(position), ssb.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mViewHolder.mBallNum.setText(ssb);

        int sum = Integer.parseInt(mListGroup.get(position));
        mViewHolder.mTimes.setText(sum + "注");
        mViewHolder.mMoney.setText(sum * 2 + "元");
        mViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClearBallInterface.clearBall(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView mImageView;
        TextView mBallNum;
        TextView mTimes;
        TextView mMoney;
    }
}
