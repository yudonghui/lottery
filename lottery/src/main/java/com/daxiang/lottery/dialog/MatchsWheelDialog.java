package com.daxiang.lottery.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.daxiang.lottery.R;
import com.daxiang.lottery.view.autotextview.AutofitTextView;

import java.util.List;

/**
 * Created by Android on 2018/4/23.
 */

public class MatchsWheelDialog {
    private Context mContext;
    private int rounds;
    List<String> round_list;
    private CenterClickListener mCenterOnClickListener;
    private View mInflate;
    private GridView mGridView;
    private View mYinying;
    private DialogAdapter mAdapter;
    private PopupWindow mPopupWindow;

    public MatchsWheelDialog(Context mContext, int rounds, List<String> round_list, CenterClickListener mCenterOnClickListener) {
        this.mContext = mContext;
        this.round_list = round_list;
        this.rounds = rounds;
        this.mCenterOnClickListener = mCenterOnClickListener;
        mInflate = View.inflate(mContext, R.layout.dialog_matchs_wheel, null);
        mGridView = (GridView) mInflate.findViewById(R.id.gv_dialog_center);
        mYinying = mInflate.findViewById(R.id.yinying);
        mYinying.setOnClickListener(DismissPopup);
        mAdapter = new DialogAdapter();
        mGridView.setAdapter(mAdapter);
        mPopupWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    public void show(View view, int rounds) {
        if (mPopupWindow == null) return;
        this.rounds = rounds;
        mAdapter.notifyDataSetChanged();
        if (Build.VERSION.SDK_INT < 24) {
            mPopupWindow.showAsDropDown(view);
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, y + view.getHeight());
        }
    }

    View.OnClickListener DismissPopup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPopupWindow != null && mPopupWindow.isShowing()) mPopupWindow.dismiss();
        }
    };

    class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return round_list.size();
        }

        @Override
        public Object getItem(int position) {
            return round_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.item_matchs_wheel, null);
            final AutofitTextView mButton = (AutofitTextView) convertView.findViewById(R.id.tv_item);
            String text = round_list.get(position);
            mButton.setText(text);
            if (rounds == position + 1) {
                mButton.setBackgroundResource(R.drawable.hongkuang);
            } else mButton.setBackgroundResource(R.drawable.huikuang);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCenterOnClickListener.onClick(position + 1);
                    mPopupWindow.dismiss();
                }
            });
            return convertView;
        }
    }

    public interface CenterClickListener {
        void onClick(int bounds);
    }
}
