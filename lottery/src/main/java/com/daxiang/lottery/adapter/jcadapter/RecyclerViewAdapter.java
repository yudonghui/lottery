package com.daxiang.lottery.adapter.jcadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.holder.BunchHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<BunchHolder> {
    ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList;
    private Context mContext;
    String[] str;
    BunchClickListener mListener;
    // ArrayList<Integer> mList=new ArrayList<>();
    private HashMap<Integer, Integer> mMap;
    int danNum;
    //  int playMethod;
    int maxBunch;
    boolean isDg;

    public RecyclerViewAdapter(boolean isDg, int maxBunch, int danNum, HashMap<Integer, Integer> mMap, ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList, BunchClickListener mListener) {
        this.choosedContentFormList = choosedContentFormList;
        this.mListener = mListener;
        this.mMap = mMap;
        this.danNum = danNum;
        this.maxBunch = maxBunch;
        this.isDg = isDg;
        if (isDg && danNum < 1) {
            //没有胆 并且支持单关
            str = new String[]{"单关", "2串1", "3串1", "4串1", "5串1", "6串1", "7串1", "8串1"};
        } else {
            str = new String[]{"2串1", "3串1", "4串1", "5串1", "6串1", "7串1", "8串1"};
        }
    }

    @Override
    public BunchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = View.inflate(mContext, R.layout.item_recyclerview, null);
        BunchHolder bunchHolder = new BunchHolder(inflate);
        return bunchHolder;
    }

    @Override
    public void onBindViewHolder(final BunchHolder holder, final int position) {
        holder.mTextView.setText(str[position]);
        if (isDg && choosedContentFormList.size() == 1 && position == 0) {//只选了单关
            holder.mTextView.setTextColor(Color.WHITE);
            holder.mTextView.setBackgroundResource(R.drawable.shape_btn);
            holder.mTextView.setTag(true);
            mMap.put(position + 1, position + 1);
        } else if (isDg && danNum == 0 && choosedContentFormList.size() > 1) {//包含单关但是选了不只一场
            if (position == 1) {
                holder.mTextView.setTextColor(Color.WHITE);
                holder.mTextView.setBackgroundResource(R.drawable.shape_btn);
                holder.mTextView.setTag(true);
                mMap.put(position + 1, position + 1);
            } else {
                holder.mTextView.setTextColor(Color.BLACK);
                holder.mTextView.setBackgroundResource(R.drawable.shape_whitebg_gray);
                holder.mTextView.setTag(true);
            }
        } else {
            if (position < danNum - 1) {
                holder.mTextView.setBackgroundResource(R.drawable.shape_bonus);
                holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                holder.mTextView.setTag(false);
            } else if (danNum == 0 && position == 0) {
                holder.mTextView.setTextColor(Color.WHITE);
                holder.mTextView.setBackgroundResource(R.drawable.shape_btn);
                holder.mTextView.setTag(true);
                mMap.put(position + 2, position + 2);
            } else if (position == danNum - 1) {
                holder.mTextView.setTextColor(Color.WHITE);
                holder.mTextView.setBackgroundResource(R.drawable.shape_btn);
                mMap.put(position + 2, position + 2);
                holder.mTextView.setTag(true);
            } else {
                holder.mTextView.setTextColor(Color.BLACK);
                holder.mTextView.setBackgroundResource(R.drawable.shape_whitebg_gray);
                holder.mTextView.setTag(true);
            }
        }
        if (isDg) {
            if (position >= choosedContentFormList.size()) {
                holder.mTextView.setBackgroundResource(R.drawable.shape_bonus);
                holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                holder.mTextView.setTag(false);
            }
        } else {
            if (position >= choosedContentFormList.size() - 1) {
                holder.mTextView.setBackgroundResource(R.drawable.shape_bonus);
                holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.gray_txt));
                holder.mTextView.setTag(false);
            }
        }
        /*if (position == danNum - 1) {
            holder.mTextView.setTextColor(Color.RED);
            holder.mTextView.setBackgroundResource(R.drawable.hongkuang);
            mMap.put(position, position);
        } else {
            holder.mTextView.setTextColor(Color.BLACK);
            holder.mTextView.setBackgroundResource(R.drawable.huikuang);
        }*/
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Boolean) holder.mTextView.getTag()) {
                    return;
                }
                TextView view = (TextView) v;
                if ((isDg && danNum == 0) || (isDg && choosedContentFormList.size() == 1 && position == 0)) {
                    if (mMap.containsKey(position + 1)) {
                        view.setTextColor(Color.BLACK);
                        view.setBackgroundResource(R.drawable.shape_whitebg_gray);
                        mMap.remove(position + 1);
                    } else {
                        view.setTextColor(Color.WHITE);
                        view.setBackgroundResource(R.drawable.shape_btn);
                        mMap.put(position + 1, position + 1);
                    }
                } else {
                    if (mMap.containsKey(position + 2)) {
                        view.setTextColor(Color.BLACK);
                        view.setBackgroundResource(R.drawable.shape_whitebg_gray);
                        mMap.remove(position + 2);
                    } else {
                        view.setTextColor(Color.WHITE);
                        view.setBackgroundResource(R.drawable.shape_btn);
                        mMap.put(position + 2, position + 2);
                    }
                }
                Log.e("选中的", mMap.size() + "");
                mListener.onBunchClick(mMap);
                //Log.e("+++++++++", mMap.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (isDg) {//单关的时候第一个是一串一。所以不用减1
           /* if ((choosedContentFormList.size()) < maxBunch) {
                return choosedContentFormList.size();
            } else {
                return maxBunch;
            }*/
            return maxBunch;
        } else {
          /*  if ((choosedContentFormList.size() - 1) < maxBunch) {
                return choosedContentFormList.size() - 1;
            } else {
                return maxBunch - 1;
            }*/
            return maxBunch - 1;
        }
    }

    public interface BunchClickListener {
        void onBunchClick(HashMap<Integer, Integer> bunchMap);
    }
}
