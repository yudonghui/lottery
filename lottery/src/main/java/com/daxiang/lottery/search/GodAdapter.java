package com.daxiang.lottery.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.daxiang.lottery.R;

import java.util.List;

/**
 * Created by Android on 2018/3/15.
 */

public class GodAdapter extends TagAdapter<String> {
    private Context mContext;
    private TagFlowLayout mFlowLayout;

    public GodAdapter(List<String> datas, Context mContext, TagFlowLayout mFlowLayout) {
        super(datas);
        this.mContext = mContext;
        this.mFlowLayout = mFlowLayout;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView mTextView = (TextView) View.inflate(mContext, R.layout.item_tv,null);
        mTextView.setText(s);
        return mTextView;
    }
}
