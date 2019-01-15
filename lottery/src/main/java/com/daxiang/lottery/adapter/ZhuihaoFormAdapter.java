package com.daxiang.lottery.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.entity.ZhuiHaoBean;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.view.BiasTextView;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/7/6
 * @describe May the Buddha bless bug-free!!!
 */
public class ZhuihaoFormAdapter extends BaseAdapter {
    List<ZhuiHaoBean.DataBean.ListBean> mList;

    public ZhuihaoFormAdapter(List<ZhuiHaoBean.DataBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_zhuihao_form, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mDate = (TextView) convertView.findViewById(R.id.date);
            mViewHolder.mLotcode = (TextView) convertView.findViewById(R.id.lotcode);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.time);
            mViewHolder.mTotaleIssue = (TextView) convertView.findViewById(R.id.totaleIssue);
            mViewHolder.mOpenIssue = (TextView) convertView.findViewById(R.id.openIssue);
            mViewHolder.mStatus = (TextView) convertView.findViewById(R.id.status);
            mViewHolder.mIsWin = (ImageView) convertView.findViewById(R.id.isWin);
            mViewHolder.mText = (BiasTextView) convertView.findViewById(R.id.text);
            convertView.setTag(mViewHolder);
        } else mViewHolder = (ViewHolder) convertView.getTag();
        int pos = position % 2;
        if (pos == 1) {
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.order_bg));
        } else {
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.white));
        }
        ZhuiHaoBean.DataBean.ListBean listBean = mList.get(position);
        String createTime = listBean.getCreateTime();
        String lotCode = listBean.getLotCode();
        String totalNum = listBean.getTotalNum();//总期数
        String boughtNum = listBean.getBoughtNum();//已购买
        String winMoney = listBean.getWinMoney();//判断是否中奖
        String zhuihaoStatus = listBean.getZhuihaoStatus();//状态
        if ("200".equals(zhuihaoStatus)){
            mViewHolder.mStatus.setText(" | 追号中");
        }else if ("600".equals(zhuihaoStatus)){
            mViewHolder.mStatus.setText(" | 停止追号");
        }else if ("800".equals(zhuihaoStatus)){
            mViewHolder.mStatus.setText(" | 中奖停止");
        }else if ("1000".equals(zhuihaoStatus)){
            mViewHolder.mStatus.setText(" | 已完成");
        }else mViewHolder.mStatus.setText("");
        mViewHolder.mLotcode.setText(LotteryTypes.getTypes(lotCode));
        mViewHolder.mTotaleIssue.setText(totalNum);
        mViewHolder.mOpenIssue.setText(boughtNum);
        if (Double.parseDouble(winMoney) > 0) {
            mViewHolder.mIsWin.setVisibility(View.VISIBLE);
            mViewHolder.mText.setVisibility(View.VISIBLE);
            mViewHolder.mText.adjustTvTextSize(DisplayUtil.dip2px(65), "已中" + winMoney.split("\\.")[0] + "元");
        } else {
            mViewHolder.mIsWin.setVisibility(View.GONE);
            mViewHolder.mText.setVisibility(View.GONE);
        }
        // createTime = "2017-03-03 17:12:45";
        if (!TextUtils.isEmpty(createTime)) {
            String[] split = createTime.split("\\-|\\ |\\:");
            if (split.length == 6) {
                mViewHolder.mDate.setText(split[1] + "月\n" + split[2] + "日");
                mViewHolder.mTime.setText(split[3] + ":" + split[4] + "  共追");
            }
        }
        return convertView;
    }

    class ViewHolder {
        private TextView mDate;
        private TextView mLotcode;
        private TextView mTime;
        private TextView mTotaleIssue;
        private TextView mOpenIssue;
        private TextView mStatus;
        private ImageView mIsWin;
        private BiasTextView mText;
    }
}
