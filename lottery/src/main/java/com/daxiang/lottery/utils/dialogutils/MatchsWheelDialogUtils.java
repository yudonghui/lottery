package com.daxiang.lottery.utils.dialogutils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.view.autotextview.AutofitTextView;

/**
 * Created by Android on 2018/4/18.
 */

public class MatchsWheelDialogUtils {
    GridView mGridView;
    Context mContext;
    CenterClickListener mCenterOnClickListener;
    int roundsTotal;
    AlertDialog dialog;
    DialogAdapter mAdapter;
    int rounds;

    public MatchsWheelDialogUtils(Context mContext, int rounds, int roundsTotal, CenterClickListener mCenterOnClickListener) {
        this.mContext = mContext;
        this.roundsTotal = roundsTotal;
        this.rounds = rounds;
        this.mCenterOnClickListener = mCenterOnClickListener;
        View inflate = View.inflate(mContext, R.layout.dialog_matchs_wheel, null);
        mGridView = (GridView) inflate.findViewById(R.id.gv_dialog_center);
        mAdapter = new DialogAdapter();
        mGridView.setAdapter(mAdapter);
        dialog = new AlertDialog.Builder(mContext).create();
        dialog.setView(inflate);
        dialog.show();
        //DialogAnimotion.setAnimotionin(dialog);
    }

    public void show(int rounds) {
        if (dialog == null) return;
        this.rounds = rounds;
        dialog.show();
        mAdapter.notifyDataSetChanged();
    }

    class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return roundsTotal;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_matchs_wheel, null);
            final AutofitTextView mButton = (AutofitTextView) convertView.findViewById(R.id.tv_item);
            mButton.setText("第" + (position + 1) + "轮");
            if (rounds == position + 1) {
                mButton.setBackgroundResource(R.drawable.shape_btn);
            } else mButton.setBackgroundResource(R.drawable.shape_white_5);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCenterOnClickListener.onClick(position + 1);
                    dialog.dismiss();
                }
            });
            return convertView;
        }
    }

    public interface CenterClickListener {
        void onClick(int bounds);
    }
}
