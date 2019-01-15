package com.daxiang.lottery.activity.wonderfulactivity.LotteryForum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.daxiang.lottery.R;

import java.util.ArrayList;
public class GalleryActivity extends AppCompatActivity {
    ViewPager mViewPager;
    int[] imageId = {R.drawable.jclq, R.drawable.jczq, R.drawable.jczqd, R.drawable.jclqd,
            R.drawable.dlt, R.drawable.sfc, R.drawable.rj, R.drawable.pls, R.drawable.plw, R.drawable.qxc};
    int n;
    int position;
    ArrayList<ImageView> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
        Intent intent = getIntent();
        n = intent.getIntExtra("n", 0);
        position = intent.getIntExtra("position", 0);

        mViewPager = (ViewPager) findViewById(R.id.vp_forum);
        //准备数据源
        for (int i = 0; i < n; i++) {
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(imageId[i]);
            mList.add(imageView);
        }
        ViewpagerAdapter mAdapter = new ViewpagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
    }

    class ViewpagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            mList.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
}
