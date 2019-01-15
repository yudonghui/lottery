package com.daxiang.lottery.activity.wonderfulactivity.LotteryForum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.wonderfuladapter.ForumLvAdapter;
import com.daxiang.lottery.entity.ForumData;
import com.daxiang.lottery.view.TitleBar;

import java.util.ArrayList;
public class CollectionActivity extends AppCompatActivity {
    private TitleBar mTitleBar;
    private TextView mAll_lottery;
    private TextView mJc_lottery;
    private LinearLayout invis;
    private TextView mNumber_lottery;
    private TextView mGao_lottery;
    private TextView mForum_title;
    private TextView mForum_titleAction;
    private ListView mLv_forum;
    private ForumLvAdapter mLvAdapter;
    private ArrayList<ForumData> mList;
    private String[] content = {"还在俗尘看世人来来往往匆忙流转，眼中不见了最净的风骨，我混迹于人群中，寻寻觅觅等下一刻的彩霞。黄昏来的太过突兀，许久不曾静待世事的妖媚与纯粹了，眼前的风物凄凄宿雨收是回忆里的絮语。逝过的花入睡了，不忍欺它的一片寂静，可予它的也只是悄悄掠过生命的轴承。",
            "还在俗尘看世人来来往往匆忙流转，眼中不见了最净的风骨，我混迹于人群中，寻寻觅觅等下一刻的彩霞。黄昏来的太过突兀，许久不曾静待世事的妖媚与纯粹了，眼前的风物凄凄宿雨收是回忆里的絮语。逝过的花入睡了，不忍欺它的一片寂静，可予它的也只是悄悄掠过生命的轴承。",
            "林木还是那样的深沉，风雨疏骤扰过的也只是片片枫叶的余情，拟订谁若不语的沉默。",
            "如歌亦如墨，清扬浓厚是凤楼传来的千古流芳，我怎悟得透汗青的文戏。簌簌是独立的挽留，是声的呼唤，亦是情的感怀。不遇是归期无期消歇后的数落，希冀总是难料事实的自由流浪。",
            "踏进你的领地，偶闻脉搏的跳动，呼吸变得薄弱清凉，素浅的纱裙封禁了不该的风尘。云霞纵横四海之内，却是少了你的这一处，或是绿意早已足够，奢求终不是你的做派。萤火虫之舞恍恍惚惚成了惊艳的流星，听叶与叶的呢喃密语，轻许一个誓言。",
            "树苍茫了面颜，有着一些悠老的韵味，真情还在憧憬，岁月终会馈赠痕迹斑斑的伤悲。",
            "离枝叶子洒落了最后一次的珠泪，旋律奏起生命的哀歌，顷刻间缠绵悱恻的星光斑斓。",
            "我路过你的领地，惊扰了尘世之外的宁静，贪恋片刻的温暖与少顷的深情。",
            "林木深深，缠绵入骨的顷刻深情，",
            "终会长久不衰，你的世界从此盛世长宁。还是那么喧闹的繁杂，那么刺眼的云霞，我站立苍苍的长空下，我是属于这世间的。等秋风远去，我还能在哪一个梦里寻到你的飞花一叶，不期而遇总会碰触一缕纯粹的曙光。"};
    private int[] headerId = {R.drawable.jclq, R.drawable.jczq, R.drawable.jczqd, R.drawable.jclqd,
            R.drawable.dlt, R.drawable.sfc, R.drawable.rj, R.drawable.pls, R.drawable.plw, R.drawable.qxc};
    private int colorRed;
    private int colorGray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuz);
        initView();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        addData();
        mLvAdapter = new ForumLvAdapter(mList, this);
        mLv_forum.setAdapter(mLvAdapter);
        addListener();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar_forum);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("我的收藏");
        mTitleBar.mShare.setImageResource(R.drawable.yonghu);
        mTitleBar.mShare.setVisibility(View.VISIBLE);
        mLv_forum = (ListView) findViewById(R.id.lv_forum);
        invis = (LinearLayout) findViewById(R.id.invis);

        View header = View.inflate(this, R.layout.stick_header, null);//头部内容
        mAll_lottery = (TextView) header.findViewById(R.id.all_lottery);
        mJc_lottery = (TextView) header.findViewById(R.id.jc_lottery);
        mNumber_lottery = (TextView) header.findViewById(R.id.number_lottery);
        mGao_lottery = (TextView) header.findViewById(R.id.gao_lottery);
        mLv_forum.addHeaderView(header);

        View action = View.inflate(this, R.layout.stick_action, null);//头部内容
        mForum_titleAction = (TextView) action.findViewById(R.id.forum_title);
        mForum_title = (TextView) findViewById(R.id.forum_title);
        mLv_forum.addHeaderView(action);
        mList = new ArrayList<>();
        colorRed = getResources().getColor(R.color.red_theme);
        colorGray = getResources().getColor(R.color.gray_txt);
    }

    private void addData() {
        for (int i = 0; i < 10; i++) {
            ForumData forumData = new ForumData();
            forumData.setContent(content[i]);
            forumData.setReaderId(headerId[i]);
            forumData.setImageNum(i);
            mList.add(forumData);
        }
    }

    private void addListener() {
        mAll_lottery.setOnClickListener(selectorListener);
        mJc_lottery.setOnClickListener(selectorListener);
        mNumber_lottery.setOnClickListener(selectorListener);
        mGao_lottery.setOnClickListener(selectorListener);
        mTitleBar.mShare.setOnClickListener(UserListener);
        mLv_forum.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    invis.setVisibility(View.VISIBLE);
                } else {
                    invis.setVisibility(View.GONE);
                }
            }
        });
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    View.OnClickListener UserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(CollectionActivity.this,ForumUserActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener selectorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all_lottery:
                    mAll_lottery.setTextColor(colorRed);
                    mAll_lottery.setBackgroundResource(R.drawable.shap_forum_red);
                    mJc_lottery.setTextColor(colorGray);
                    mJc_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mNumber_lottery.setTextColor(colorGray);
                    mNumber_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mGao_lottery.setTextColor(colorGray);
                    mGao_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    break;
                case R.id.jc_lottery:
                    mAll_lottery.setTextColor(colorGray);
                    mAll_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mJc_lottery.setTextColor(colorRed);
                    mJc_lottery.setBackgroundResource(R.drawable.shap_forum_red);
                    mNumber_lottery.setTextColor(colorGray);
                    mNumber_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mGao_lottery.setTextColor(colorGray);
                    mGao_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    break;
                case R.id.number_lottery:
                    mAll_lottery.setTextColor(colorGray);
                    mAll_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mJc_lottery.setTextColor(colorGray);
                    mJc_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mNumber_lottery.setTextColor(colorRed);
                    mNumber_lottery.setBackgroundResource(R.drawable.shap_forum_red);
                    mGao_lottery.setTextColor(colorGray);
                    mGao_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    break;
                case R.id.gao_lottery:
                    mAll_lottery.setTextColor(colorGray);
                    mAll_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mJc_lottery.setTextColor(colorGray);
                    mJc_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mNumber_lottery.setTextColor(colorGray);
                    mNumber_lottery.setBackgroundResource(R.drawable.shap_forum_gray);
                    mGao_lottery.setTextColor(colorRed);
                    mGao_lottery.setBackgroundResource(R.drawable.shap_forum_red);
                    break;
            }
        }
    };
    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
