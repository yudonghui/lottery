package com.daxiang.lottery.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.CollectionListener;
import com.daxiang.lottery.entity.ColletionDb;
import com.daxiang.lottery.entity.ScoreBasketData;
import com.daxiang.lottery.utils.DbManager;

import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class ScoreBasketAdapter extends BaseAdapter {
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    List<ScoreBasketData.MatchsBean> data;
    HashMap<String, String> map = new HashMap<>();
    DbManager dbManager = new DbManager();
    CollectionListener mCollectionListener;
    public ScoreBasketAdapter(List<ScoreBasketData.MatchsBean> data,CollectionListener mCollectionListener) {
        this.data = data;
        this.mCollectionListener= mCollectionListener;
        //将数据库中的收藏取出来
        List<ColletionDb> colletionDbs = null;
        try {
            colletionDbs = dbManager.queryAll();
            if(colletionDbs!=null){
                for (int i = 0; i < colletionDbs.size(); i++) {
                    String match_id = colletionDbs.get(i).getBasket_match_id();
                    map.put(match_id,match_id);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ChildHolder mChildHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_score_child, null);
            mChildHolder = new ChildHolder();
            mChildHolder.mTv_week = (TextView) convertView.findViewById(R.id.tv_week);
            mChildHolder.mTv_name = (TextView) convertView.findViewById(R.id.tv_lname);
            mChildHolder.mTv_start_time = (TextView) convertView.findViewById(R.id.tv_start_time);
            mChildHolder.mTv_away_name = (TextView) convertView.findViewById(R.id.tv_away_name);
            mChildHolder.mHost_score = (TextView) convertView.findViewById(R.id.host_score);
            mChildHolder.mAway_score = (TextView) convertView.findViewById(R.id.away_score);
            mChildHolder.mTv_status = (TextView) convertView.findViewById(R.id.tv_status);
            mChildHolder.mTv_host_name = (TextView) convertView.findViewById(R.id.tv_host_name);
            mChildHolder.mCollection = (ImageView) convertView.findViewById(R.id.collection);
            convertView.setTag(mChildHolder);
        } else {
            mChildHolder = (ChildHolder) convertView.getTag();
        }
        final ScoreBasketData.MatchsBean matchesBean = data.get(position);
        mChildHolder.mTv_week.setText(matchesBean.getWeek());
        mChildHolder.mTv_name.setText(matchesBean.getLname());
        String start_time = matchesBean.getStart_time();
        String split = start_time.substring(start_time.length()-5,start_time.length());

        mChildHolder.mTv_start_time.setText(split);
        String host_name = matchesBean.getHost_name();
        String[] split1 = host_name.split("\\;");
        mChildHolder.mTv_host_name.setText(split1[split1.length-1]);
        mChildHolder.mTv_away_name.setText(matchesBean.getAway_name());
        if ("完".equals(matchesBean.getStatus())) {
            mChildHolder.mHost_score.setText(matchesBean.getHost_score_finish());
            mChildHolder.mAway_score.setText(matchesBean.getAway_score_finish());
            mChildHolder.mTv_status.setText("已完赛");
        } else {
            if ("未".equals(matchesBean.getStatus())) {
                mChildHolder.mTv_status.setText("VS");
            } else {
                mChildHolder.mHost_score.setText(matchesBean.getHost_score_finish());
                mChildHolder.mAway_score.setText(matchesBean.getAway_score_finish());
                mChildHolder.mTv_status.setText(matchesBean.getStatus());
            }
        }
        mChildHolder.mCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColletionDb colletionDb=new ColletionDb();
                colletionDb.setBasket_match_id(matchesBean.getMatch_id());
                if (map.containsKey(matchesBean.getMatch_id())) {
                    try {
                        dbManager.deleteWhereBasket(matchesBean.getMatch_id());
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    map.remove(matchesBean.getMatch_id());
                    mChildHolder.mCollection.setImageResource(R.drawable.icon_star);
                } else {
                    try {
                        dbManager.inserts(colletionDb);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    map.put(matchesBean.getMatch_id(), matchesBean.getMatch_id());
                    mChildHolder.mCollection.setImageResource(R.drawable.icon_star2);
                }
                mCollectionListener.collListener("");
            }
        });
        //处理复用后遗症
        if(map.containsKey(matchesBean.getMatch_id())){
            mChildHolder.mCollection.setImageResource(R.drawable.icon_star2);
        }else {
            mChildHolder.mCollection.setImageResource(R.drawable.icon_star);
        }
        return convertView;
    }

    class ChildHolder {
        private TextView mTv_week;
        private TextView mTv_name;
        private TextView mTv_start_time;
        private TextView mTv_away_name;
        private TextView mHost_score;
        private TextView mAway_score;
        private TextView mTv_status;
        private TextView mTv_host_name;
        private ImageView mCollection;
    }
}
