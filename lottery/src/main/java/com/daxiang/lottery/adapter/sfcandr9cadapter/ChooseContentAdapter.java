package com.daxiang.lottery.adapter.sfcandr9cadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.SfcAndRjcData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ChooseContentAdapter extends BaseAdapter {
   // private ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList;
    private DeleteItemListener mDeleteItemListener;
    // private HashMap<SfcAndRjcData.DataBean, HashMap<String, String>> mChoosedContentMap;
    //存放所有选中的项所对应的这场比赛的所有信息
    private ArrayList<SfcAndRjcData.DataBean> list = new ArrayList<>();
    private ChooseFormAdapter.OnClickSfcListener mOnClickListener;
    private Context mContext;
    HashMap<SfcAndRjcData.DataBean, HashMap<String, String>> clickMap = new HashMap<>();

    public ChooseContentAdapter(HashMap<SfcAndRjcData.DataBean,
                                HashMap<String, String>> mChoosedContentMap,
                                DeleteItemListener mDeleteItemListener,
                                ChooseFormAdapter.OnClickSfcListener mOnClickListener) {
       // this.choosedContentFormList = choosedContentFormList;
        this.mDeleteItemListener = mDeleteItemListener;
        this.mOnClickListener = mOnClickListener;
        this.clickMap = mChoosedContentMap;
        for (Map.Entry<SfcAndRjcData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            list.add(entry.getKey());
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        mContext = parent.getContext();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_sfcandrjc_choose_content, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mHome = (TextView) convertView.findViewById(R.id.tv_host);
            mViewHolder.mAwary = (TextView) convertView.findViewById(R.id.tv_guest);
            mViewHolder.mImageClear = (ImageView) convertView.findViewById(R.id.image_clear_form);
            mViewHolder.jz_loss_ll = (LinearLayout) convertView.findViewById(R.id.jz_loss_ll);
            mViewHolder.tv_guest_sp = (TextView) convertView.findViewById(R.id.tv_guest_sp);
            mViewHolder.jz_win_ll = (LinearLayout) convertView.findViewById(R.id.jz_win_ll);
            mViewHolder.tv_host_sp = (TextView) convertView.findViewById(R.id.tv_host_sp);
            mViewHolder.tv_loss_lable = (TextView) convertView.findViewById(R.id.tv_loss_lable);
            mViewHolder.tv_win_lable = (TextView) convertView.findViewById(R.id.tv_win_lable);

            mViewHolder.tv_ping_lable = (TextView) convertView.findViewById(R.id.tv_ping_lable);
            mViewHolder.tv_versus_sp = (TextView) convertView.findViewById(R.id.tv_versus_sp);
            mViewHolder.jz_ping_ll = (LinearLayout) convertView.findViewById(R.id.jz_ping_ll);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //  final ArrayList<ChoosedContentFormBean> ContentFormList = choosedContentFormList.get(position);
        //((((((((((((((((((((((((((((((((((((((((((((((( (((((((((((((((((((((((((((((
        final SfcAndRjcData.DataBean dataBean = list.get(position);
        mViewHolder.jz_win_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.jz_win_ll, mViewHolder, dataBean);
            }
        });
        mViewHolder.jz_ping_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.jz_ping_ll, mViewHolder, dataBean);
            }
        });
        mViewHolder.jz_loss_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMatchView(mViewHolder.jz_loss_ll, mViewHolder, dataBean);
            }
        });
        recycleUse(dataBean, mViewHolder);
        mViewHolder.mHome.setText(dataBean.getTeamName0());
        mViewHolder.mAwary.setText(dataBean.getTeamName1());
        mViewHolder.tv_win_lable.setText("主胜");
        mViewHolder.tv_ping_lable.setText("平");
        mViewHolder.tv_loss_lable.setText("主负");
        mViewHolder.tv_host_sp.setText( dataBean.getOdds3());
        mViewHolder.tv_versus_sp.setText( dataBean.getOdds1());
        mViewHolder.tv_guest_sp.setText( dataBean.getOdds0());

        mViewHolder.tv_win_lable.setTag("主胜");
        mViewHolder.tv_ping_lable.setTag("平");
        mViewHolder.tv_loss_lable.setTag("主负");
        /*//因为在notifyDataSetChanged()方法执行的时候convertview是不为空的，不会重新执行findviewbyid
        //这样就导致点击过的项目，在清除后唤醒适配器，再点击就会崩溃，因为这个时候容器为空，但是小条目的tag值为true
        mViewHolder.jz_win_ll.setTag(false);
        mViewHolder.jz_ping_ll.setTag(false);
        mViewHolder.jz_loss_ll.setTag(false);*/
        //)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
        mViewHolder.mImageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteItemListener.deletItem(position);
            }
        });
        return convertView;
    }

    private void recycleUse(SfcAndRjcData.DataBean dataBean, ViewHolder mViewHolder) {
        if (clickMap.containsKey(dataBean)) {
            HashMap<String, String> map = clickMap.get(dataBean);
            if (map.containsKey("主胜")) {
                mViewHolder.jz_win_ll.setBackgroundColor(Color.RED);
                mViewHolder.jz_win_ll.setTag(true);
                mViewHolder.tv_win_lable.setTextColor(Color.WHITE);
                mViewHolder.tv_host_sp.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_win_ll.setBackgroundResource(R.drawable.table_bg);
                // mViewHolder.mllNoRang3.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_host_sp.setTextColor(0xff999999);
                mViewHolder.tv_win_lable.setTextColor(0xff666666);
                mViewHolder.jz_win_ll.setTag(false);
            }
            if (map.containsKey("平")) {
                mViewHolder.jz_ping_ll.setBackgroundColor(Color.RED);
                mViewHolder.tv_ping_lable.setTextColor(Color.WHITE);
                mViewHolder.tv_versus_sp.setTextColor(Color.WHITE);
                mViewHolder.jz_ping_ll.setTag(true);
            } else {
                mViewHolder.jz_ping_ll.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang1.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_versus_sp.setTextColor(0xff999999);
                mViewHolder.tv_ping_lable.setTextColor(0xff666666);
                mViewHolder.jz_ping_ll.setTag(false);
            }
            if (map.containsKey("主负")) {
                mViewHolder.jz_loss_ll.setTag(true);
                mViewHolder.jz_loss_ll.setBackgroundColor(Color.RED);
                mViewHolder.tv_guest_sp.setTextColor(Color.WHITE);
                mViewHolder.tv_loss_lable.setTextColor(Color.WHITE);
            } else {
                mViewHolder.jz_loss_ll.setTag(false);
                mViewHolder.jz_loss_ll.setBackgroundResource(R.drawable.table_bg);
                //mViewHolder.mllNoRang0.setBackgroundColor(Color.WHITE);
                mViewHolder.tv_guest_sp.setTextColor(0xff999999);
                mViewHolder.tv_loss_lable.setTextColor(0xff666666);
            }

        } else {
            mViewHolder.jz_loss_ll.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.jz_win_ll.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.jz_ping_ll.setBackgroundResource(R.drawable.table_bg);
            mViewHolder.jz_win_ll.setTag(false);
            mViewHolder.jz_ping_ll.setTag(false);
            mViewHolder.jz_loss_ll.setTag(false);
            mViewHolder.tv_guest_sp.setTextColor(0xff999999);
            mViewHolder.tv_loss_lable.setTextColor(0xff666666);
            mViewHolder.tv_host_sp.setTextColor(0xff999999);
            mViewHolder.tv_ping_lable.setTextColor(0xff666666);
            mViewHolder.tv_versus_sp.setTextColor(0xff999999);
            mViewHolder.tv_win_lable.setTextColor(0xff666666);
        }
    }

    //点击每一个小的条目事件
    public void clickMatchView(View view, ViewHolder mViewHolder, SfcAndRjcData.DataBean dataBean) {
        // Log.e("++++++++++++", "" + position);
        LinearLayout mLinearLayout = (LinearLayout) view;

        TextView mTitleTextView = (TextView) mLinearLayout.getChildAt(0);
        TextView mTextView = (TextView) mLinearLayout.getChildAt(1);
        if (mLinearLayout.getTag() == null || !(Boolean) mLinearLayout.getTag()) {
            mLinearLayout.setBackgroundColor(Color.RED);
            mTitleTextView.setTextColor(Color.WHITE);
            mTextView.setTextColor(Color.WHITE);
            mLinearLayout.setTag(true);
            HashMap<String, String> map;
            if (clickMap.containsKey(dataBean)) {
                map = clickMap.get(dataBean);
                String tag = (String) mTitleTextView.getTag();
                map.put(tag, mTextView.getText().toString());
                //chooseOddMap.put(tag, mTextView.getText().toString());
                clickMap.put(dataBean, map);
            } else {
                map = new HashMap<>();
                map.put((String) mTitleTextView.getTag(), mTextView.getText().toString());
                //chooseOddMap.put((String) mTitleTextView.getTag(), mTextView.getText().toString());
                clickMap.put(dataBean, map);
            }
        } else {
            mLinearLayout.setBackgroundResource(R.drawable.table_bg);
            mTitleTextView.setTextColor(0xff666666);
            mTextView.setTextColor(0xff999999);
            mLinearLayout.setTag(false);
            //进入这个方法证明clickmap中肯定有这个键。
            HashMap<String, String> map = clickMap.get(dataBean);
            map.remove((String) mTitleTextView.getTag());
            // chooseOddMap.remove((String) mTitleTextView.getTag());
            //如果内层的map集合中已经没有数据，也就是没有选中的了。那么需要把position从外层的clickmap中移除
            //否则把移除数据后的map再放入clickmap中。
            if (map == null || map.size() == 0) {
                clickMap.remove(dataBean);
            } else {
                clickMap.put(dataBean, map);
            }
        }
         mOnClickListener.onClickSfcListener(clickMap);
    }

    class ViewHolder {
        TextView mHome;
        TextView mAwary;
        ImageView mImageClear;
        LinearLayout jz_loss_ll;
        TextView tv_loss_lable;
        TextView tv_guest_sp;
        TextView tv_win_lable;
        LinearLayout jz_win_ll;
        TextView tv_host_sp;
        TextView tv_ping_lable;
        LinearLayout jz_ping_ll;
        TextView tv_versus_sp;

    }

    public interface DeleteItemListener {
        void deletItem(int position);
    }
}
