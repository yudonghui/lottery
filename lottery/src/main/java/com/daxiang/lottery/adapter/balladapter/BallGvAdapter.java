package com.daxiang.lottery.adapter.balladapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.OnClickBallListener;
import com.daxiang.lottery.view.BallButton;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class BallGvAdapter extends BaseAdapter {
    int gvNum;
    OnClickBallListener listener;
    private Context mContext;
    String lotcode;
    int prePosition;
    List<Integer> list;
    boolean isOmit;
    //遗漏模型
    HashMap<String, String> hashMap;

    public BallGvAdapter(int gvNum, String lotcode,
                         OnClickBallListener listener,
                         int prePosition, List<Integer> list,
                         HashMap<String, String> hashMap, boolean isOmit) {
        this.gvNum = gvNum;
        this.listener = listener;
        this.lotcode = lotcode;
        this.prePosition = prePosition;
        this.list = list;
        this.hashMap = hashMap;
        this.isOmit = isOmit;
    }

    public BallGvAdapter(int gvNum, String lotcode,
                         int prePosition, List<Integer> list,
                         HashMap<String, String> hashMap, boolean isOmit) {
        this.gvNum = gvNum;
        this.lotcode = lotcode;
        this.prePosition = prePosition;
        this.list = list;
        this.hashMap = hashMap;
        this.isOmit = isOmit;
    }

    @Override
    public int getCount() {
        return gvNum;
    }

    @Override
    public Object getItem(int position) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mContext = parent.getContext();
        final View inflate = View.inflate(mContext, R.layout.item_gv_ball, null);
        BallButton mBallButton8 = (BallButton) inflate.findViewById(R.id.btn_ball);
        TextView mTextView = (TextView) inflate.findViewById(R.id.omit_text);
        if (isOmit) mTextView.setVisibility(View.VISIBLE);
        else mTextView.setVisibility(View.GONE);
        if (gvNum == 12 || gvNum == 16) {
            mBallButton8.setColor("blue");
        } else {
            mBallButton8.setColor("red");
        }
        if ("35".equals(lotcode) || "33".equals(lotcode) || "10022".equals(lotcode) || "52".equals(lotcode)) {
            mBallButton8.setText(position + "");
            //随机选择的情况
            if (list != null && list.contains(position)) {
                mBallButton8.setSelectState(true);
            } else {
                mBallButton8.setSelectState(false);
            }
        } else if ("50".equals(lotcode)) {
            if (gvNum == 4) {//时时彩的大小单双
                mBallButton8.setText(getDxds(position));
                //随机选择的情况
                if (list != null && list.contains(getDxds(position))) {
                    mBallButton8.setSelectState(true);
                } else {
                    mBallButton8.setSelectState(false);
                }
            } else {
                mBallButton8.setText(position + "");
                //随机选择的情况
                if (list != null && list.contains(position)) {
                    mBallButton8.setSelectState(true);
                } else {
                    mBallButton8.setSelectState(false);
                }
            }
        } else {
            if (position + 1 < 10) {
                mBallButton8.setText("0" + (position + 1));
            } else {
                mBallButton8.setText("" + (position + 1));
            }
            //随机选择的情况
            if (list != null && list.contains(position + 1)) {
                mBallButton8.setSelectState(true);
            } else {
                mBallButton8.setSelectState(false);
            }
        }
        inflate.setTag(prePosition);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickListener(inflate);
            }
        });
//遗漏
        String text = mBallButton8.getText();
        if (text.length() < 2) {
            text = "0" + text;
        }
        if (hashMap != null) {
            mTextView.setText(TextUtils.isEmpty(hashMap.get(text)) ? "" : hashMap.get(text));
        }
        return inflate;
    }

    private String getDxds(int position) {
        switch (position) {
            case 0:
                return "大";
            case 1:
                return "小";
            case 2:
                return "单";
            case 3:
                return "双";
        }
        return "";
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
