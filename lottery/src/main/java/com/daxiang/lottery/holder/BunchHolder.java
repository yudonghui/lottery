package com.daxiang.lottery.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.daxiang.lottery.R;
/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class BunchHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;
    public BunchHolder(View itemView) {
        super(itemView);
        mTextView= (TextView) itemView.findViewById(R.id.text_bunch);
    }
}
