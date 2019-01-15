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
 * Created by Administrator on 2017/2/23.
 */

public class BillTypeDialogUtils {
    GridView mGridView;
    String[] lotteryStr = {"账单明细", "支付明细", "平台补款", "平台扣款",
            "提款明细", "提成明细", "彩票返奖", "充值明细", "撤单返款",
            "返点明细", "合买保底退款", "支付宝转账"};
    String[] lotteryCode = {"0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "11"};
    Context mContext;
    ArrayList<String> clickList;
    CenterClickListener mCenterOnClickListener;
    AlertDialog dialog;

    public void selecte(Context mContext, ArrayList<String> clickList, CenterClickListener mCenterOnClickListener) {
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

    public interface CenterClickListener {
        void onClick(ArrayList<String> clickList, String type);
    }
}
