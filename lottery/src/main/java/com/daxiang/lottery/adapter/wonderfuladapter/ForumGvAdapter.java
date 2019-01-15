package com.daxiang.lottery.adapter.wonderfuladapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.wonderfulactivity.LotteryForum.GalleryActivity;

import java.util.ArrayList;
/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class ForumGvAdapter extends BaseAdapter {
    int n;
    ArrayList<ImageView> list=new ArrayList<>();
    Context mContext;
    int[] imageId={R.drawable.jclq, R.drawable.jczq, R.drawable.jczqd, R.drawable.jclqd,
            R.drawable.dlt, R.drawable.sfc, R.drawable.rj, R.drawable.pls, R.drawable.plw, R.drawable.qxc};
    public ForumGvAdapter(Context mContext,int n){
        this.mContext=mContext;
        this.n=n;
    }
    @Override
    public int getCount() {
        return n;
    }

    @Override
    public Object getItem(int position) {
        return imageId[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=View.inflate(parent.getContext(),R.layout.item_gv_forum,null);
        ImageView mImageView= (ImageView) view.findViewById(R.id.image_forum);
        mImageView.setImageResource(imageId[position]);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, GalleryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                bundle.putInt("n",n);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}
