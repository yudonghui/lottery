package com.daxiang.lottery.utils.dialogutils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.daxiang.lottery.R;

import java.util.ArrayList;
/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class CenterDialogUtils {
    GridView mGridView;
    String[] lotteryStr = {"全部彩种", "竞彩足球", "竞彩篮球", "大乐透",
            "胜负彩", "任9场", "排列三", "排列五", "七星彩",
            "七乐彩", "双色球", "十一运", "3D"};
    String[] lotteryCode = {"000", "42", "43", "23529", "11", "19", "33", "35", "10022",
            "23528", "51", "21406", "52"};
    Context mContext;
    ArrayList<String> clickList;
    CenterOnClickListener mCenterOnClickListener;
    AlertDialog dialog;

    public void selecte(Context mContext, ArrayList<String> clickList, CenterOnClickListener mCenterOnClickListener) {
        this.mContext = mContext;
        this.clickList = clickList;
        this.mCenterOnClickListener = mCenterOnClickListener;
        View inflate = View.inflate(mContext, R.layout.dialog_center, null);
        mGridView = (GridView) inflate.findViewById(R.id.gv_dialog_center);
        DialogAdapter mAdapter = new DialogAdapter();
        mGridView.setAdapter(mAdapter);
        dialog = new AlertDialog.Builder(mContext).create();
        dialog.setView(inflate);
        dialog.show();
        DialogAnimotion.setAnimotion(dialog);
    }

    class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lotteryStr.length;
        }

        @Override
        public Object getItem(int position) {
            return lotteryStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_sift_dialog, null);
            final TextView mButton = (TextView) convertView.findViewById(R.id.tv_item);
            mButton.setText(lotteryStr[position]);
            if (clickList == null || clickList.size() == 0) {
                if (position == 0) {
                    mButton.setBackgroundResource(R.drawable.shape_btn);
                    clickList.add(lotteryStr[position]);
                } else {
                    mButton.setBackgroundResource(R.drawable.shape_whitebg_gray);
                }
            } else {
                if (clickList.contains(mButton.getText().toString())) {
                    mButton.setBackgroundResource(R.drawable.shape_btn);
                } else {
                    mButton.setBackgroundResource(R.drawable.shape_whitebg_gray);
                }
            }

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickList.clear();
                    clickList.add(mButton.getText().toString());
                    mCenterOnClickListener.onClick(clickList, lotteryCode[position]);
                    dialog.dismiss();
                }
            });
            return convertView;
        }
    }

    public interface CenterOnClickListener {
        void onClick(ArrayList<String> clickList, String lotteryType);
    }
}
