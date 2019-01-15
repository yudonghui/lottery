package com.daxiang.lottery.adapter.balladapter;

import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.AwardResultData;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/20
 * @describe May the Buddha bless bug-free!!!
 */
public class LotteryResultAdapter extends BaseAdapter {
    List<AwardResultData.DataBean.ItemsBean> mlist;

    public LotteryResultAdapter(List<AwardResultData.DataBean.ItemsBean> mlist) {
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        if (mlist == null) return 0;
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_result_bg, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mIssue = (TextView) convertView.findViewById(R.id.issue);
            mViewHolder.mRedNum = (TextView) convertView.findViewById(R.id.redNum);
            mViewHolder.mBlueNum = (TextView) convertView.findViewById(R.id.blueNum);
            mViewHolder.mUpDown = (ImageView) convertView.findViewById(R.id.up_down);
            convertView.setTag(mViewHolder);
        } else mViewHolder = (ViewHolder) convertView.getTag();
        AwardResultData.DataBean.ItemsBean itemsBean = mlist.get(position);
        List<String> redNumber = itemsBean.getRedNumber();
        List<String> blueNumber = itemsBean.getBlueNumber();
        String issue = itemsBean.getIssueNo();
        if (!TextUtils.isEmpty(issue)) {
            /*String substring = issue.substring(3, issue.length());
            mViewHolder.mIssue.setText(substring + "期");*/
            mViewHolder.mIssue.setText(issue + "期");
        }
        if (redNumber != null && redNumber.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < redNumber.size(); i++) {
                sb.append(redNumber.get(i) + "  ");
            }
            mViewHolder.mRedNum.setText(sb);
        }
        if (blueNumber != null && blueNumber.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < blueNumber.size(); i++) {
                sb.append(blueNumber.get(i) + "  ");
            }
            mViewHolder.mBlueNum.setText(sb);
        }
        if (position == mlist.size()-1) {
            mViewHolder.mUpDown.setImageResource(R.mipmap.up);
        } else mViewHolder.mUpDown.setImageResource(R.mipmap.up_down);
        return convertView;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

    class ViewHolder {
        TextView mIssue;
        TextView mRedNum;
        TextView mBlueNum;
        ImageView mUpDown;
    }
}
