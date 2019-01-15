package com.daxiang.lottery.activity.wonderfulactivity.LotteryForum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.wonderfuladapter.FocusAdapter;
import com.daxiang.lottery.entity.ForumData;

import java.util.ArrayList;
public class FocusActivity extends AppCompatActivity {
    ListView mListView;
    private FocusAdapter mAdapter;
    private ArrayList<ForumData> mList;
    private int[] headerId = {R.drawable.jclq, R.drawable.jczq, R.drawable.jczqd, R.drawable.jclqd,
            R.drawable.dlt, R.drawable.sfc, R.drawable.rj, R.drawable.pls, R.drawable.plw, R.drawable.qxc};
   private String[] name={"张无忌","孙悟空","猪八戒","逍遥子","沙悟净","刘德华","周润发","成龙","岳云鹏","苍井空"};
    private String[] jianjie={"乾坤大挪移","72变","天蓬元帅","凌波微步","黑沙河","四大天王之一","赌神归来","又得奖了","贱","又出新片了"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        initView();
        addData();
        mAdapter = new FocusAdapter(mList,this);
        mListView.setAdapter(mAdapter);
    }


    private void initView() {
        mListView= (ListView) findViewById(R.id.lv_focus);
        mList=new ArrayList<>();
    }
    private void addData() {
        for (int i = 0; i < 10; i++) {
            ForumData forumData=new ForumData();
            forumData.setName(name[i]);
            forumData.setQianming(jianjie[i]);
            forumData.setReaderId(headerId[i]);
            mList.add(forumData);
        }
    }
}
